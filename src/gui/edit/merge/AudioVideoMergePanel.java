package gui.edit.merge;
import functionality.audio.Audio;
import functionality.merge.MergeAudioAndVideo;
import gui.edit.audio.OpenAudio;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class AudioVideoMergePanel extends JPanel{
	Audio[] ListAudio = new Audio[5];
	ArrayList<Audio> arrAudio = new ArrayList<Audio>();
	private static EmbeddedMediaPlayer video;
	static JButton addAudioBtn = null;
	static JButton mergeBtn = null;
	Box audioBox;
	JTextArea audioSelected;
	OpenAudio audio;
	File audioFile;
	static File videoFile;
	Audio audioObj;
	boolean audioChosen = false;
	int countBtnClick=0;
	static JLabel processLabel;

	public AudioVideoMergePanel(){
		

		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(100, 200));
		GridBagConstraints AudioPanelConstraints = new GridBagConstraints();
		AudioPanelConstraints.fill = GridBagConstraints.BOTH;
		AudioPanelConstraints.gridy = 0;
		AudioPanelConstraints.gridx = 0;
		setBorder(BorderFactory.createTitledBorder("Merge Audio and Video"));

		//Adding the Jlist to show the different audio options
		//JList<Audio> audioList = new JList<Audio>(ListAudio); //give it the array
		//audioList.setPreferredSize(new Dimension(100, 100));
		//audioList.setBounds(65, 203, 148, 200);
		//JScrollPane audioPane = new JScrollPane(audioList);
		AudioPanelConstraints.gridy = 0;
		AudioPanelConstraints.gridx = 0;
		AudioPanelConstraints.insets = new Insets(5,5,5,5);
		//add(audioPane, AudioPanelConstraints);
		
		audioBox = Box.createVerticalBox();
		add(audioBox, AudioPanelConstraints);

		audioSelected = new JTextArea();
		TitledBorder titleBorder = BorderFactory.createTitledBorder("Selected MP3 files");
		audioSelected.setBorder(titleBorder);
		audioSelected.setEditable(false);
		audioSelected.setLineWrap(true);
		audioSelected.setWrapStyleWord(true);
		audioBox.add(audioSelected, AudioPanelConstraints);
		audioBox.setPreferredSize(new Dimension(200, 100));


		addAudioBtn = new JButton("Add Audio");
		addAudioBtn.addActionListener(new ActionListener(){

			int count = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				audio = new OpenAudio();
				audioFile = audio.getAudioFile();
				String path = audioFile.getAbsolutePath().toString();
				if (audioFile != null){
					enableMergeBtn();
					addCount();
					audioChosen = true;
					//audioSelected.setText(audioFile.getName());
					int timeBox = (int) video.getTime() / 1000;
					System.out.println((int) video.getTime());
					audioSelected.append(audioFile.getName()+" -"+timeBox+" Sec\n");
					audioObj = new Audio(path, audioFile.getName());
					audioObj.setTime((int) video.getTime());
					arrAudio.add(audioObj);
					System.out.println("audio added");

					for (int i=0; i<arrAudio.size(); i++){
						ListAudio[i] = arrAudio.get(i);
						System.out.println(ListAudio[i].getTime());
					}
				}

				disableAddAudioBtn();
			}


		});




		AudioPanelConstraints.gridy = 3;
		AudioPanelConstraints.gridx = 0;
		AudioPanelConstraints.insets = new Insets(5,5,5,5);
		addAudioBtn.setMargin(new java.awt.Insets(1, 2, 1, 2));
		add(addAudioBtn, AudioPanelConstraints);



		mergeBtn = new JButton("Merge");
		mergeBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){
				SaveMergedFile outputVideo = new SaveMergedFile();
				String outputVideoFile;
				String[] outputVideoPathSplit;
				if (outputVideo.getSaveDestination() != null){
					outputVideoFile = outputVideo.getSaveDestination().getName();
					outputVideoPathSplit = outputVideoFile.split(".avi");
					if (!outputVideoPathSplit[0].matches("[A-Za-z0-9_]+")){
						JOptionPane option = new JOptionPane();
						JOptionPane.showMessageDialog(option, "Please choose and alphanumeric name with no spaces.");
					} else {
					String workingDirectory = System.getProperty("user.dir");
					File f = new File(outputVideo.getSaveDestination().getParentFile()+ "/" + outputVideoPathSplit[0] + ".avi");
					String path = f.getAbsolutePath();
					System.out.println(f.getAbsolutePath());
					if (!f.exists()){
						String videoPath = videoFile.getAbsolutePath();
						MergeAudioAndVideo mergeTask = new MergeAudioAndVideo(videoPath,ListAudio,path);
						mergeTask.execute();
						//System.out.println("executing it");
					} else {
						JOptionPane option = new JOptionPane();
						JOptionPane.showMessageDialog(option, "The file already exists. Please choose another filename.");
						//System.out.println("exists");

					}
				}

				}

			}
		});
		AudioPanelConstraints.gridy = 0;
		AudioPanelConstraints.gridx = 1;
		AudioPanelConstraints.gridheight = 2;
		AudioPanelConstraints.gridwidth = 2;
		AudioPanelConstraints.insets = new Insets(5,5,5,5);
		mergeBtn.setMargin(new java.awt.Insets(1, 2, 1, 2));
		add(mergeBtn, AudioPanelConstraints);
		
		addAudioBtn.setEnabled(false);
		mergeBtn.setEnabled(false);
		
		processLabel = new JLabel("Processing...Please wait a few moments!");
		AudioPanelConstraints.gridy = 0;
		AudioPanelConstraints.gridx = 3;
		processLabel.setVisible(false);
		add(processLabel, AudioPanelConstraints);
	}

	public static void setVideo(EmbeddedMediaPlayer runningVideo){
		video = runningVideo;
	}

	public static void setVideoFile(File vFile){
		videoFile = vFile;
	}

	public void addCount(){
		countBtnClick++;
	}

	public void disableAddAudioBtn(){
		if (countBtnClick == 5){
			addAudioBtn.setEnabled(false);
		}
		
	}
	
	public static void enableAddAudio(){
		addAudioBtn.setEnabled(true);
		
	}
	
	public static void enableMergeBtn(){
		mergeBtn.setEnabled(true);
	}
	
	public static void showProcessingMessage(){
		processLabel.setVisible(true);
	}
	public static void hideProcessingMessage(){
		processLabel.setVisible(false);
	}

}
