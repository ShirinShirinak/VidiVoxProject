package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class PlayBackPanel extends JPanel{
	boolean FileChosen = false;
	static boolean running = false; 
	boolean play = false;
	private static String currentTask = null;
	BackgroundTask task;
	JButton forwardBtn, backwardBtn, pauseBtn;
	boolean isPaused = false;
	File videoFile;
	public static EmbeddedMediaPlayer video;
	private EmbeddedMediaPlayerComponent mediaPlayerComponent = null;

	public PlayBackPanel(){
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();

		JButton FileChooserBtn = new JButton("Choose Video");
		FileChooserBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//Opening the file through OpenFile class
				new OpenFile();
				if (OpenFile.getVideoFile() != null){
					FileChosen = true;
					videoFile = OpenFile.getVideoFile();
				}

			}

		});

		con.gridx = 0;
		con.gridy = 0;
		con.insets = new Insets(5,5,5,5);
		add(FileChooserBtn, con);

		/////////////////////////////////////////////////////////////////////////////////Rewind
		backwardBtn = new JButton("<<");
		backwardBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e){

				if ((running == true) && (FileChosen == true)){
					//Using the setEnabled to control the user click
					backwardBtn.setEnabled(false);
					play = false;
					//Checks to see if the user decided to fast-forward instead of fast-backward
					if (currentTask == ">>"){
						task.cancel(true);
						backwardBtn.setEnabled(false);
						forwardBtn.setEnabled(true);
					}
					currentTask = "<<";
					task = new BackgroundTask(video);
					task.execute();
				} else {
					JOptionPane option = new JOptionPane();
					JOptionPane.showMessageDialog(option, "You need to click on the play button first in order to do fastforward.");
				}
			}
		});
		con.gridx = 1;
		add(backwardBtn, con);

		/////////////////////////////////////////////////////////////////////////////////Play
		final JButton PlayBtn = new JButton("Play");
		PlayBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent c){
				if (FileChosen == true){

					if ((running == false)){
						AudioPanel.enableAddAudio();
						ProgressPanel.enableProgressBar();
						play = true;
						running = true;
						//Playing the video using the PlayVideo class
						mediaPlayerComponent = TopPanel.getMPComponent();
						new PlayVideo(OpenFile.getVideoFile(), mediaPlayerComponent);
						video = PlayVideo.getVideo();
						ProgressPanel.setVideo(video);
						AudioPanel.setVideo(video);
						
						video.addMediaPlayerEventListener(new MediaPlayerEventAdapter(){
							
							public void finished(MediaPlayer player){
								running = false;
								video.stop();
							}
						});
						PlayBtn.setText("Pause");

					} else {
						backwardBtn.setEnabled(true);
						forwardBtn.setEnabled(true);
						if (task != null){
							task.cancel(true);
							
						}

						if (isPaused == false){
							video.pause();
							isPaused = true;
							PlayBtn.setText("Play");
							//System.out.println(running);
						} else {
							video.pause();
							isPaused = false;
							PlayBtn.setText("Pause");
							//System.out.println(running);
						}

					}



				} else {
					PlayVideo.noVideoMessage();
				}


			}

		});
		con.gridx = 2;
		add(PlayBtn, con);

		/////////////////////////////////////////////////////////////////////////////////FastForward
		forwardBtn = new JButton(">>");
		forwardBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if ((running == true) && (FileChosen == true)){
					forwardBtn.setEnabled(false);
					play = false;
					//checks to see if the user has decided to fast-backward instead of the fast-forward
					if (currentTask == "<<"){
						task.cancel(true);
						backwardBtn.setEnabled(true);
						forwardBtn.setEnabled(false);
					}
					currentTask = ">>";
					task = new BackgroundTask(video);

					task.execute();



				} else {
					JOptionPane option = new JOptionPane();
					JOptionPane.showMessageDialog(option, "You need to click on the play button first in order to do fastbackward.");
				}



			}

		});
		con.gridx = 3;
		add(forwardBtn, con);
		/////////////////////////////////////////////////////////////////////////////////Volume
		final JButton volumeBtn = new JButton("Mute");
		volumeBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if (volumeBtn.getText().equals("Mute")){

					video.mute(true);
					volumeBtn.setText("Volume");
				} else {

					video.mute(false);
					volumeBtn.setText("Mute");
				}
			}
		});
		con.gridx = 4;
		add(volumeBtn, con);
	}

	public static String getTask(){
		return currentTask;
	}
	
	public static EmbeddedMediaPlayer getPlayingVideo(){
		return video;
	}
}
