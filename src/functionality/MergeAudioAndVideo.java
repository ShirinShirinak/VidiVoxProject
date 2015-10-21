package functionality;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class MergeAudioAndVideo extends SwingWorker<Void, String>{
	Audio[] ListAudioToMerge = new Audio[5];
	String videoPath = null;
	private String workingDirectory = System.getProperty("user.dir");
	File tempFolder;
	private String saveDestinationTempFiles;
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

		tempFolder = new File(workingDirectory+System.getProperty("file.separator")+"TemporaryFolder");
		if (!tempFolder.exists()){
			tempFolder.mkdir();
		} 
		saveDestinationTempFiles = tempFolder+ System.getProperty(File.separator)+"temp"; //tempDirectory system.getProperty(file.separator)

	}
	@Override
	protected Void doInBackground() throws Exception {
		String cmd = null;
		int audioCount = 0;
		for (int i=0; i<ListAudioToMerge.length; i++){
			if (ListAudioToMerge[i] != null){
				cmd = "ffmpeg -i "+ListAudioToMerge[i].getAudioPath()+" -filter_complex adelay="+ListAudioToMerge[i].getTime()+" "+ saveDestinationTempFiles+i+".mp3";
				audioDelayBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);

				System.out.println("in the background task - mp3 delay");
				tempMp3s+=" -i "+saveDestinationTempFiles+i+".mp3";
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



		String directory = tempFolder.getAbsolutePath();

		String cmd2 = "pwd; rm file.mp3";

		ProcessBuilder builder = new ProcessBuilder("bash", "-c",cmd2);

		builder.directory(new File(directory));
		builder.redirectErrorStream(true);

		Process process;
		try {
			process = builder.start();


			InputStream stdout = process.getInputStream();
			//InputStream stderr = process.getErrorStream();

			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));

			String line = null;
			while ((line = stdoutBuffered.readLine()) != null ) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		String[] tempAudioFiles = TempFolder.list();
		for(String audio: tempAudioFiles){
		    File currentAudioFile = new File(TempFolder.getPath(),audio);
		    currentAudioFile.delete();
		    System.out.println("deleting temp files");
		}
		 */
		/*
		for (int i=0; i<audioCount; i++){
			cmd = "rm -f  "+ saveDestinationTempFiles+i+ ".mp3";
			System.out.println(cmd);
			ProcessBuilder deleteBuilder = new ProcessBuilder("/bin/bash","-c", cmd);
			//deleteBuilder.redirectErrorStream(true);
			Process processDelete = deleteBuilder.start();
			//int exitStatus = processDelete.waitFor();
			//System.out.println("deleting files "+ exitStatus);

			InputStream stdout = processDelete.getInputStream();
			//InputStream stderr = process.getErrorStream();

			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));

			String line = null;
			while ((line = stdoutBuffered.readLine()) != null ) {
				System.out.println(line);
			}
		}
		 */


		//String cmd = "echo 'hello world' | festival --tts";
		//mergeBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
		//mergeBuilder = new ProcessBuilder("/bin/bash", "-c", "ffmpeg", "-i", videoPath, "-i", audioPath, "-filter_complex", "adelay="+audioTime, "amix=inputs=2", ":duration=first", saveDes);


		//Process process = mergeBuilder.start();
		return null;
	}

	public void done(){
		JOptionPane option = new JOptionPane();
		JOptionPane.showMessageDialog(option, "The file is saved successfully at "+videoDestination);
	}

}
