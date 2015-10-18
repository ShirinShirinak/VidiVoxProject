package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import functionality.Audio;
import functionality.MergeIt;
import functionality.SaveTextToMp3;
import functionality.SayItWithFestival;

public class EditPanel extends JPanel{
	
	
	public EditPanel(){
		setLayout(new GridBagLayout());
		GridBagConstraints EditPanelConstrants = new GridBagConstraints();
		EditPanelConstrants.fill = GridBagConstraints.BOTH;
		EditPanelConstrants.gridy = 0;
		EditPanelConstrants.gridx = 0;
		setBorder(BorderFactory.createTitledBorder("Edit Tools"));
		
		FestivalPanel festivalPanel = new FestivalPanel();
		add(festivalPanel, EditPanelConstrants);
		
		EditPanelConstrants.gridy = 1;
		AudioPanel audioPanel = new AudioPanel();
		//audioPanel.setPreferredSize(new Dimension(300,300));
		add(audioPanel, EditPanelConstrants);
		
		
		

	/*	

				//Adding a JButton for merging audio with video
				JButton mergeAudioVideo = new JButton("Save Audio and Video");
				mergeAudioVideo.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if (FileChosen == true){

							new OpenAudio();
							if (OpenAudio.getAudioFile() != null){
								JOptionPane message = new JOptionPane();
								AudioChosen = true;
								audio = OpenAudio.getAudioFile();
								JOptionPane nameOption = new JOptionPane();

								String videoName = videoFile.getPath();
								String audioName = audio.getPath();
								while (true){
									String newFilename = JOptionPane.showInputDialog(nameOption, "Please enter a filename for the new video: ");
									if (newFilename == ""){
										JOptionPane.showMessageDialog(message, "the filename cannot be empty");
										continue;
									} else if (newFilename == null){
										return;
									} else if (newFilename.contains(" ")) { //filename contains spaces
										JOptionPane.showMessageDialog(message, "The filename cannot contain whitespace characters");
										continue;
									} else {
										MergeIt mergedVideo = new MergeIt(audioName, videoName, newFilename);
										mergedVideo.execute();
										return;
									}
								}
							}
						} else {
							JOptionPane videoMessage = new JOptionPane();
							JOptionPane.showMessageDialog(videoMessage, "Please choose a video first");
						}
					}
				});
				GridBagConstraints mergebtnConstraints = new GridBagConstraints();
				mergebtnConstraints.gridy = 1;
				mergebtnConstraints.gridx = 7;
				mergebtnConstraints.insets = new Insets(5,5,5,5);
				add(mergeAudioVideo, mergebtnConstraints);
		 */			

		/*
				
		/*		
				final JButton chooseAudioBtn = new JButton("Choose Audio");
				chooseAudioBtn.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						//audioStringList
						if (FileChosen == true){
							//JOptionPane audioOption = new JOptionPane();
							//JOptionPane audioMessage = new JOptionPane();
							//String videoName = videoFile.getPath();
							audio1 = new OpenAudio();
							audioFile1 = audio1.getAudioFile();
							while (i < 3){
								if (i == 0){
									audio1 = new OpenAudio();
								} else if (i == 1){
									audio2 = new OpenAudio();
								} else {
									audio3 = new OpenAudio();
								}

								i++;
							}

						}
					}

				});

				GridBagConstraints chooseAudioBtnConstraints = new GridBagConstraints();
				chooseAudioBtnConstraints.gridy = 2;
				chooseAudioBtnConstraints.gridx = 2;
				chooseAudioBtnConstraints.insets = new Insets(5,5,5,5);
				chooseAudioBtn.setMargin(new java.awt.Insets(1, 2, 1, 2));
				add(chooseAudioBtn, chooseAudioBtnConstraints);


				*/

	}
}
