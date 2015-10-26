package gui.video_playback;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.Timer;

import functionality.playback.BackgroundTask;
import gui.edit.merge.AudioVideoMergePanel;
import gui.high_level_panels.MainPlayer;
import gui.high_level_panels.TopPanel;
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
	OpenFile file;
	JButton PlayBtn = null;
	JButton volumeBtn = null;
	JSlider volumeBar = null;
	public static EmbeddedMediaPlayer video;
	private EmbeddedMediaPlayerComponent mediaPlayerComponent = null;

	
	public PlayBackPanel(){
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();

		JButton FileChooserBtn = new JButton("Choose Video");
		FileChooserBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (videoFile != null){
					JOptionPane option = new JOptionPane();
					int reply = JOptionPane.showConfirmDialog(option, "Would you like to load a new video?");
					if (reply == JOptionPane.YES_OPTION){
						
						file = new OpenFile();
						if (file.getVideoFile() != null){

							FileChosen = true;
							videoFile = file.getVideoFile();
							mediaPlayerComponent = TopPanel.getMPComponent();
							new PlayVideo(file.getVideoFile(), mediaPlayerComponent);
							video = PlayVideo.getVideo();
							ProgressPanel.setVideo(video);
							AudioVideoMergePanel.setVideo(video);
							MainPlayer.setVideoName("VidiVox - Playing: "+videoFile.getName());
						}
					}
				} else {
					//Opening the file through OpenFile class
					file = new OpenFile();
					if (file.getVideoFile() != null){
						enablePlayBtn();
						FileChosen = true;
						videoFile = file.getVideoFile();
					}
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
		PlayBtn = new JButton("Play");
		PlayBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent c){
				if (FileChosen == true){

					if ((running == false)){
						enableButtons();
						
						AudioVideoMergePanel.enableAddAudio();
						ProgressPanel.enableProgressBar();
						play = true;
						running = true;
						//Playing the video using the PlayVideo class
						mediaPlayerComponent = TopPanel.getMPComponent();
						new PlayVideo(file.getVideoFile(), mediaPlayerComponent);
						video = PlayVideo.getVideo();
						ProgressPanel.setVideo(video);
						AudioVideoMergePanel.setVideo(video);
						MainPlayer.setVideoName("VidiVox - Playing: "+videoFile.getName());
						video.addMediaPlayerEventListener(new MediaPlayerEventAdapter(){

							public void finished(MediaPlayer player){
								running = false;
								MainPlayer.setVideoName("VidiVox");
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
		volumeBtn = new JButton("Mute");
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
		
		volumeBar = new JSlider(0, 200, 100);
		volumeBar.addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseDragged(MouseEvent e) {
				video.setVolume(volumeBar.getValue());
				int volumeLevel = video.getVolume();
			}
		});
		con.gridx = 5;
		add(volumeBar, con);
		
		volumeBar.setEnabled(false);
		PlayBtn.setEnabled(false);
		backwardBtn.setEnabled(false);
		forwardBtn.setEnabled(false);
		volumeBtn.setEnabled(false);
	}

	public static String getTask(){
		return currentTask;
	}

	public static EmbeddedMediaPlayer getPlayingVideo(){
		return video;
	}
	
	public void enablePlayBtn(){
		PlayBtn.setEnabled(true);
	}
	
	public void enableButtons(){
		backwardBtn.setEnabled(true);
		forwardBtn.setEnabled(true);
		volumeBtn.setEnabled(true);
		volumeBar.setEnabled(true);
	}

}
