package gui.video_playback;
import java.io.File;

import javax.swing.JOptionPane;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.media.Media;
/**
 * 
 * PlayVideo class plays the video chosen by the user
 *
 */
public class PlayVideo {
	static EmbeddedMediaPlayer video = null;
	public PlayVideo(File videoFile, EmbeddedMediaPlayerComponent mediaPlayerComponent){
		video = mediaPlayerComponent.getMediaPlayer();

		if (videoFile != null){
			video.playMedia(videoFile.getPath());
		} 
		
		
	}
	
	public static EmbeddedMediaPlayer getVideo(){
		return video;
	}
	
	public static void noVideoMessage(){
		JOptionPane option = new JOptionPane();
		JOptionPane.showMessageDialog(option, "You have not chosen a video file to play, Please click on the Open button");
	}
}
