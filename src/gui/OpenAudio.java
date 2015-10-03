package gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * 
 * OpenAudio class opens a audio file of mp3 format.
 *
 */
public class OpenAudio extends JPanel{
	private final JFileChooser fc;
	private static File AudioFile = null;
	public OpenAudio(){
		super(new BorderLayout());

		//User chooses the file via GUI component JFileChooser
		fc = new JFileChooser();
		FileNameExtensionFilter fcFilter = new FileNameExtensionFilter("MP3 FILE", "mp3");
		fc.setFileFilter(fcFilter);

		int returnVal = fc.showOpenDialog(OpenAudio.this);

		if (returnVal == JFileChooser.APPROVE_OPTION){
			AudioFile = fc.getSelectedFile();

		}

	}

	public static File getAudioFile(){
		return AudioFile;
	}
}
