package gui;

import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class BackgroundProgressBar extends SwingWorker<Void, Integer>{
	static EmbeddedMediaPlayer video = null;
	JProgressBar bar = null;
	protected BackgroundProgressBar(EmbeddedMediaPlayer video, JProgressBar bar){
		this.video = video;
		this.bar = bar;
	}
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		int i = (int) (video.getTime()/1000);
		publish(i);
		return null;
	}

	@Override
	protected void process(List<Integer> chunks){
		//System.out.println("process() done by EDT? " +SwingUtilities.isEventDispatchThread());
		for (int i : chunks){
			MainPlayer.getProgressBar().setValue(i);
		}	
		
	}
}
