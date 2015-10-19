package functionality;

import javax.swing.SwingWorker;

public class MergeAudioAndVideo extends SwingWorker<Void, String>{
	String videoPath;
	String audioPath;
	int audioTime;
	String saveDes;
	private ProcessBuilder mergeBuilder;
	
	public MergeAudioAndVideo(String videoPath, String audioPath, int audioTime){
		this.videoPath = videoPath;
		this.audioPath = audioPath;
		this.audioTime = audioTime;
		saveDes = System.getProperty("user.home") + "/testMine.avi";
	}
	@Override
	protected Void doInBackground() throws Exception {
		//String cmd = "ffmpeg -i "+videoPath+" -i "+audioPath+" -filter_complex ";
		mergeBuilder = new ProcessBuilder("/bin/bash", "-c", "ffmpeg", "-i", videoPath, "-i", audioPath, "-filter_complex", "adelay="+audioTime, "amix=inputs=2", "-map", "0:v", "-c:a", saveDes);
		
		
		Process process = mergeBuilder.start();
		return null;
	}

}
