package functionality;

import javax.swing.SwingWorker;

public class MergeAudioAndVideo extends SwingWorker<Void, String>{
	Audio[] ListAudioToMerge = new Audio[5];
	String videoPath = null;
	String saveDes = System.getProperty("user.home") + "/testMine.avi";
	private ProcessBuilder mergeBuilder;
	
	public MergeAudioAndVideo(String videoPath, Audio[] ListAudio ){
		this.videoPath = videoPath;
		ListAudioToMerge = ListAudio;
		
		
	}
	@Override
	protected Void doInBackground() throws Exception {
		//String cmd = "echo 'hello world' | festival --tts";
		//mergeBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
		//mergeBuilder = new ProcessBuilder("/bin/bash", "-c", "ffmpeg", "-i", videoPath, "-i", audioPath, "-filter_complex", "adelay="+audioTime, "amix=inputs=2", ":duration=first", saveDes);
		
		
		//Process process = mergeBuilder.start();
		return null;
	}

}
