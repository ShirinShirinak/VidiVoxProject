package gui;
import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * 
 * OpenFile class opens a video file chosen by the user
 *
 */
 
public class OpenFile extends JPanel{
	private final JFileChooser fc;
	private static File videoFile = null;
	public OpenFile(){
		super(new BorderLayout());
		
		//User chooses the video file to use
		 fc = new JFileChooser();
		 FileNameExtensionFilter fcFilter = new FileNameExtensionFilter("AVI VIDEOS", "avi");
		 fc.setFileFilter(fcFilter);
		 
		 int returnVal = fc.showOpenDialog(OpenFile.this);
		 
		 if (returnVal == JFileChooser.APPROVE_OPTION){
			 videoFile = fc.getSelectedFile();
			 //System.out.println("you selected "+ videoFile.getName());
			 AudioPanel.setVideoFile(videoFile);
			 
		 }
		 
	}
	
	public static File getVideoFile(){
		return videoFile;
	}
}
