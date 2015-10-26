package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

import org.omg.Messaging.SyncScopeHelper;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import functionality.MergeIt;
import functionality.SaveTextToMp3;
import functionality.SayItWithFestival;
/**
 * 
 * MainPlayer is the main GUI component in the VIDIVOX prototype.
 *
 */
public class MainPlayer {
	//private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	public EmbeddedMediaPlayer video;// cp to PBP
	private JPanel contentPane;
	boolean play = false; // cp to PBP
	static boolean running = false; //cp to PBP
	boolean FileChosen = false; //copied to PlayBackPanel
	BackgroundTask task; //cp to PBP
	private String currentTask = null; //cp to PBP
	JButton forwardBtn, backwardBtn, chooseAudioBtn;// cp to PBP
	JButton pauseBtn; //cp to PBP
	private static JProgressBar bar = null;
	File audio, audioFile1, audioFile2, audioFile3;
	File videoFile; //copied to PlayBackPanel
	boolean AudioChosen = false;
	boolean isPaused = false; // cp to PBP
	String[] audioStringList = new String[3];
	OpenAudio audio1, audio2, audio3;
	private static String videoName = "";
	private static JFrame frame;
	

	public MainPlayer() {
		//creating the JFrame
		frame = new JFrame("VidiVox");


		contentPane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//new
		TopPanel topPanel = new TopPanel();
		BottomPanel bottomPanel = new BottomPanel();
		//Panel topPanel = new Panel(new GridBagLayout());
		//topPanel.setBounds(0, 0, 3000, 2000);
		//JPanel bottomPanel = new JPanel(new GridBagLayout());
		//c.gridheight = 20;
		//c.gridwidth = 30;
		//Splitting the contentsPane into two separate parts
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
		contentPane.add(splitPane);
		//mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

		//mediaPlayerComponent.setPreferredSize(new Dimension(1000, 500));
		//topPanel.setPreferredSize(new Dimension(1000, 500));
		//c.gridx = 0;
		//c.gridy = 0;
		//topPanel.add(mediaPlayerComponent, c);
		//topPanel.setSize(500, 500);
		
		frame.setContentPane(contentPane);
		frame.setLocation(150, 100);
		frame.setSize(1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


		//==========FESTIVAL COMMENTARY============
/*
		//Adding a jtxtfield for the text-to-speech user input
		final JTextField festivalInput1 = new JTextField();
		//Adding constraints to align the jtextfield
		GridBagConstraints festivalInputConstraints1 = new GridBagConstraints();
		festivalInputConstraints1.fill = GridBagConstraints.HORIZONTAL;
		festivalInputConstraints1.gridy = 1;
		festivalInputConstraints1.gridx = 1;
		festivalInputConstraints1.gridwidth = 4;
		festivalInputConstraints1.weighty = 0.5;
		festivalInputConstraints1.insets = new Insets(10,10,10,0);
		festivalInput1.setPreferredSize(new Dimension(90, 30));
		bottomPanel.add(festivalInput1, festivalInputConstraints1);

		//adding a jlabel "enter commentary text:"
		JLabel festivalLabel = new JLabel("Enter commentary text: ");
		GridBagConstraints festivalLabelConstraints = new GridBagConstraints();
		festivalLabelConstraints.gridy = 1;
		festivalLabelConstraints.gridx = 0;
		bottomPanel.add(festivalLabel, festivalLabelConstraints);

		//Adding "Listen" button: allows user to preview festival commentary alone
		//NOTE: Change later or add a new button so that video and festival commentary are previewed together
		JButton listenbtn = new JButton("Listen");
		listenbtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String toSay = festivalInput1.getText();
				//if festival input box is empty, tell user it cannot be empty
				if (toSay.isEmpty()) {
					JOptionPane option = new JOptionPane();
					JOptionPane.showMessageDialog(option, "Please write the commentary text first.");
					//if there are too many words, festival slows down and eventually stops.
					//Any phrases over the word limit are not allowed.
				} else if (toSay.split("\\s").length > 30) {
					JOptionPane option = new JOptionPane();
					JOptionPane.showMessageDialog(option, "Only phrases of up to thirty words are allowed. Please shorten the commentary.");
					//if there are no problems, the user can listen to the commentary
				} else {
					SayItWithFestival speaker = new SayItWithFestival(toSay);
					try {
						speaker.doInBackground();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}

		});
		GridBagConstraints listenbtnConstraints = new GridBagConstraints();
		listenbtnConstraints.gridy = 1;
		listenbtnConstraints.gridx = 5;
		listenbtnConstraints.insets = new Insets(5,5,5,5);
		bottomPanel.add(listenbtn, listenbtnConstraints);


		//adding a JButton for saving the festival commentary to an mp3 file
		JButton saveCommentarybtn = new JButton("Save Commentary");
		saveCommentarybtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String toSave = festivalInput1.getText();
				//if festival input box is empty, tell user it cannot be empty
				if (toSave.isEmpty()) {
					JOptionPane option = new JOptionPane();
					JOptionPane.showMessageDialog(option, "Please write the commentary text first.");
					//if word limit is surpassed, inform user
				} else if (toSave.split(" ").length > 30){
					JOptionPane option = new JOptionPane();
					JOptionPane.showMessageDialog(option, "Only phrases of up to thirty words are allowed. Please shorten the commentary.");
					//else save commentary as mp3
				} else {
					JOptionPane option = new JOptionPane();
					String filename = new String();
					while(true) {
						filename = JOptionPane.showInputDialog(option, "Please enter a filename for the mp3: ");
						if(filename==null) { //user pressed cancel
							return;
						} else if (filename.equals("")) { //user submitted empty string
							JOptionPane.showMessageDialog(option, "The filename cannot be empty");
							continue;
						} else if (filename.contains(" ")) { //filename contains spaces
							JOptionPane.showMessageDialog(option, "The filename cannot contain whitespace characters");
							continue;
						} else {
							String workingDirectory = System.getProperty("user.dir");
							//System.out.println(workingDirectory + "/" + filename);
							File f = new File(workingDirectory + "/" + filename + ".mp3");
							if(f.exists()) {
								JOptionPane.showMessageDialog(option, "The file already exists. Please choose another filename.");
								continue;
							}
							SaveTextToMp3 save = new SaveTextToMp3(toSave, filename);
							try {
								save.doInBackground();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//next: need to make sure following line only appears if saving really worked. (get message from save obj)
							JOptionPane.showMessageDialog(option, "Commentary saved successfully.");
							return;
						}
					}

				}

			}
		});
		GridBagConstraints saveComntrybtnConstraints = new GridBagConstraints();
		saveComntrybtnConstraints.gridy = 1;
		saveComntrybtnConstraints.gridx = 6;
		saveComntrybtnConstraints.insets = new Insets(5,5,5,5);
		bottomPanel.add(saveCommentarybtn, saveComntrybtnConstraints);


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
		bottomPanel.add(mergeAudioVideo, mergebtnConstraints);

		//Adding the Jlist to show the different audio options
		JList<String> audioList = new JList<String>(audioStringList); //give it the array
		audioList.setPreferredSize(new Dimension(150, 100));
		audioList.setBounds(65, 203, 148, 200);
		JScrollPane pane = new JScrollPane(audioList);
		GridBagConstraints audioListConstraints = new GridBagConstraints();
		audioListConstraints.gridy = 2;
		audioListConstraints.gridx = 1;
		audioListConstraints.insets = new Insets(5,5,5,5);
		bottomPanel.add(pane, audioListConstraints);

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
		bottomPanel.add(chooseAudioBtn, chooseAudioBtnConstraints);
		
		final JButton addAudioBtn = new JButton("Add Audio");
		addAudioBtn.addActionListener(new ActionListener(){
			int count = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (audioFile1  != null){
					AudioChosen = true;
					String audioName1 = audioFile1.getPath();
					audioStringList[count] = audioName1;
				}
				count++;
				if (count == 3){
					count = 0;
					chooseAudioBtn.setEnabled(false);
					addAudioBtn.setEnabled(false);
				}
				//System.out.println(audioFile1.getAbsolutePath());
				
				
			}
			
		});
		
		//System.out.println(count);
		GridBagConstraints addAudioBtnConstraints = new GridBagConstraints();
		addAudioBtnConstraints.gridy = 3;
		addAudioBtnConstraints.gridx = 1;
		addAudioBtnConstraints.insets = new Insets(5,5,5,5);
		addAudioBtn.setMargin(new java.awt.Insets(1, 2, 1, 2));
		bottomPanel.add(addAudioBtn, addAudioBtnConstraints);
*/	
	}
	
	

	public static void setVideoName(String name){
		frame.setTitle(name);
	}

	public static JProgressBar getProgressBar(){
		return bar;
	}
	public EmbeddedMediaPlayer getVideo(){
		return video;
	}


}


