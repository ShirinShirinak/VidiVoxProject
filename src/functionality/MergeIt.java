package functionality;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class MergeIt extends SwingWorker<Void, Void>{

	
	private String _audio;
	private String _video;
	private String _newFilename;
	
	public MergeIt(String audio, String video, String newFilename) {
		this._audio = audio;
		this._video = video;
		this._newFilename = newFilename;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		//removing audio and adding commentary
		String cmd = "ffmpeg -y -i " + _video + " -i " + _audio + " -map 0:v -map 1:a "+_newFilename+".avi";
		//overlaying commentary onto video (doesn't work)
		//String cmd = "ffmpeg -y -i " + this._video + " -i " + this._audio + " -c:v copy -c:a copy output.avi";
		//ffmpeg -y -i big_buck_bunny_1_minute.avi -i preview.mp3 -c:v copy -c:a copy output.mp4"
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();
		return null;
	}
	
	public void done(){
		JOptionPane option = new JOptionPane();
		JOptionPane.showMessageDialog(option, "Video was saved successfully!");
	}

	
}
