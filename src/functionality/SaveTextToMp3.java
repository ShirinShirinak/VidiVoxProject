package functionality;
import javax.swing.SwingWorker;

public class SaveTextToMp3 extends SwingWorker<Void, Void> {

	private String _saveIt;
	private String _filename;
	
	public SaveTextToMp3(String saveIt, String filename){
		this._saveIt = saveIt;
		//removing characters that will disrupt bash commands
		_saveIt = removeBadChars(_saveIt);
		this._filename = filename;
	}
/*	public void saySomething(String message) {
		SayItWithFestival speaker = new SayItWithFestival(message);
		speaker.execute();
	}*/
	
	@Override
	public Void doInBackground() throws Exception {
		//all in one go: pipe commentary text into text2wav, save as wav, convert to mp3, delete the wav file
		String cmd = "echo \'" + this._saveIt + "\' " + "| text2wave -o " + _filename + ".wav; ffmpeg -i " + _filename + ".wav -codec:a libmp3lame -qscale:a 2 " + _filename + ".mp3; rm -f "+ _filename + ".wav";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();
		return null;
		
		//old
		//String cmd = "echo \'" + this._saveIt + "\' " + "| text2wave -scale 50 -o preview.wav";
		//ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		//Process process = builder.start();
		
		//return null;
	}
	
	//method for removing apostrophes and quotation marks from a string (prevents disruption of bash commands)
	public String removeBadChars(String tofix) {
		String fixed = tofix;
		fixed.replace("'", "");
		fixed.replace("\"", "");
		fixed.replace("$", "\\$");
		return fixed;
	}
}