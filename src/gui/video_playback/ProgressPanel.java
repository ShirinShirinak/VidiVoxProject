package gui.video_playback;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.metal.MetalProgressBarUI;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class ProgressPanel extends JPanel{
	static JProgressBar bar;
	private static EmbeddedMediaPlayer video;
	static boolean skipped = false;
	static int progressBarVal;
	static Timer timer;

	public ProgressPanel(){
		setLayout(new GridBagLayout());


		bar = new JProgressBar(0, 100);//Min & Max
		bar.setPreferredSize(new Dimension(1000, 20));
		bar.setValue(0);
		bar.setStringPainted(true);
		bar.setForeground(Color.green);
		ProgressBarUI ui = new MetalProgressBarUI(){
			  
			  protected Color getSelectionForeground() {
			    return Color.black;
			  }
			  
			  protected Color getSelectionBackground(){
			    return Color.black;
			  }
			};
			bar.setUI( ui );
		GridBagConstraints progressConstraints = new GridBagConstraints();
		progressConstraints.gridx = 0;
		progressConstraints.gridy = 0;
		add(bar,progressConstraints);
		bar.setEnabled(false);

		bar.addMouseListener(new MouseAdapter() {            
			public void mouseClicked(MouseEvent e) {
				skipped = true;
				//barTask.cancel(true);
				int value = bar.getValue();
				//Find the mouse position
				int mouseX = e.getX();
				//Computes how far along the mouse is relative to the component width then multiply it by the progress bar's maximum value.
				progressBarVal = (int)Math.round(((double)mouseX / (double)bar.getWidth()) * bar.getMaximum());

				float progressVideo = (float) progressBarVal / 100;
				//System.out.println(progressVideo);
				// System.out.println((float) progressBarVal / 100);
				//System.out.println(progressBarVal);
				//System.out.println(videoLength);
				//System.out.println(progressVideoVal);
				video.setPosition(progressVideo); //the position of the video, makes video jump to this point
				//bar.setValue(progressBarVal);
				//System.out.println(progressBarVal);
				//video.setTime(arg0);
				//System.out.println(video.getTime() );


			}                                     
		});
		
		timer = new Timer(100, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println(video.getTime()+" "+ video.getLength() +"      "+ (100.0*video.getTime() / video.getLength()));
				if (skipped == false){
					int i = (int) (video.getTime()/1000);
					//System.out.println(video.getTime() + "from timer");
					//bar.setValue(i);
				} else {
					//int i = (int) (video.getTime()/1000);
					//bar.setValue(progressBarVal);
					skipped = false;
				}
				bar.setValue((int) (100.0*video.getTime() / video.getLength()));
			}

		});
	}

	public static void setVideo(EmbeddedMediaPlayer Runningvideo){
		video = Runningvideo;
		
		timer.start();
	}
	
	public static void enableProgressBar(){
		bar.setEnabled(true);
	}


}
