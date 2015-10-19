package gui;
import functionality.Audio;
import functionality.MergeAudioAndVideo;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AudioPanel extends JPanel{
	Audio[] ListAudio = new Audio[5];
	ArrayList<Audio> arrAudio = new ArrayList<Audio>();
	private static EmbeddedMediaPlayer video;
	JButton addAudioBtn = null;
	JButton mergeBtn = null;
	Box audioBox;
	JTextArea audioSelected;
	OpenAudio audio;
	File audioFile;
	static File videoFile;
	Audio audioObj;
	boolean audioChosen = false;
	
	public AudioPanel(){
		
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
							audioChosen = true;
							//audioSelected.setText(audioFile.getName());
							audioSelected.append(audioFile.getName()+"\n");
							audioObj = new Audio(path, audioFile.getName());
							audioObj.setTime((int) video.getTime());
							arrAudio.add(audioObj);
							System.out.println("audio added");

							for (int i=0; i<arrAudio.size(); i++){
								ListAudio[i] = arrAudio.get(i);
								System.out.println(ListAudio[i].getTime());
							}
						}
						
						
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
						String videoPath = videoFile.getAbsolutePath();
						MergeAudioAndVideo mergeTask = new MergeAudioAndVideo(videoPath,ListAudio);
						mergeTask.execute();
					}
				});
				AudioPanelConstraints.gridy = 0;
				AudioPanelConstraints.gridx = 1;
				AudioPanelConstraints.gridheight = 2;
				AudioPanelConstraints.gridwidth = 2;
				AudioPanelConstraints.insets = new Insets(5,5,5,5);
				mergeBtn.setMargin(new java.awt.Insets(1, 2, 1, 2));
				add(mergeBtn, AudioPanelConstraints);
	}
	
	public static void setVideo(EmbeddedMediaPlayer runningVideo){
		video = runningVideo;
	}
	
	public static void setVideoFile(File vFile){
		videoFile = vFile;
	}
	
}
