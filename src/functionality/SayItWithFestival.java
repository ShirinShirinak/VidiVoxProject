package functionality;
import javax.swing.SwingWorker;

//similar to class SayItWithFestival in assignment 2.


public class SayItWithFestival extends SwingWorker<Void, Void> {
	private String sayIt;
	
	public SayItWithFestival (String phrase){
		this.sayIt = phrase;
		//removing characters that cause a problem in bash
		this.sayIt = removeBadChars(this.sayIt);
	}	
	@Override
	public Void doInBackground() throws Exception {
		String cmd = "echo '"+sayIt + "' | festival --tts";
		//cmd = "echo \""+"(Parameter.set 'Duration_Stretch 2.0) (SayText "+"\\"+"\""+sayIt+"\\"+"\""+")\""+" | festival";
		System.out.println(cmd);
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();
		return null;
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

