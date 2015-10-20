package functionality;

import java.io.File;

import javax.swing.SwingWorker;

public class MergeAudioAndVideo extends SwingWorker<Void, String>{
	Audio[] ListAudioToMerge = new Audio[5];
	String videoPath = null;
	String saveDes = System.getProperty("user.home") + "/temp";
	private ProcessBuilder mergeBuilder;
	private ProcessBuilder audioDelayBuilder;
	Process processAudioDelay;
	Process processMerge;
	String tempMp3s = "";
	String videoDestination;

	public MergeAudioAndVideo(String videoPath, Audio[] ListAudio, String destination ){
		this.videoPath = videoPath;
		ListAudioToMerge = ListAudio;
		videoDestination = destination;

	}
	@Override
	protected Void doInBackground() throws Exception {
		String cmd = null;
		int audioCount = 0;
		for (int i=0; i<ListAudioToMerge.length; i++){
			if (ListAudioToMerge[i] != null){
				cmd = "ffmpeg -i "+ListAudioToMerge[i].getAudioPath()+" -filter_complex adelay="+ListAudioToMerge[i].getTime()+" "+ saveDes+i+".mp3";
				audioDelayBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);

				System.out.println("in the background task - mp3 delay");
				tempMp3s+=" -i "+saveDes+i+".mp3";
				audioCount++;
				System.out.println(ListAudioToMerge.length);
			}
			processAudioDelay = audioDelayBuilder.start();
		}
		System.out.println(tempMp3s);
		//processAudioDelay.waitFor();
		System.out.println(audioCount);

		cmd = "ffmpeg -i "+videoPath+tempMp3s+" -filter_complex amix=inputs="+(audioCount+1)+":duration=first "+videoDestination;
		System.out.println(cmd);
		mergeBuilder = new ProcessBuilder("/bin/bash","-c", cmd);
		processMerge = mergeBuilder.start();
		System.out.println("in the background task - merging");
		
		/*for (int i=0; i<audioCount; i++){
			cmd = "rm "+ saveDes+i+ ".mp3";
			System.out.println(cmd);
			mergeBuilder = new ProcessBuilder("/bin/bash","-c", cmd);
			processMerge = mergeBuilder.start();
			System.out.println("deleting files");
		}*/
		
		

		//String cmd = "echo 'hello world' | festival --tts";
		//mergeBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
		//mergeBuilder = new ProcessBuilder("/bin/bash", "-c", "ffmpeg", "-i", videoPath, "-i", audioPath, "-filter_complex", "adelay="+audioTime, "amix=inputs=2", ":duration=first", saveDes);


		//Process process = mergeBuilder.start();
		return null;
	}

}
