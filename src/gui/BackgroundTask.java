package gui;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class BackgroundTask extends SwingWorker<Void, Integer>{
	EmbeddedMediaPlayer videoFile;
	protected BackgroundTask(EmbeddedMediaPlayer videoFile){
		this.videoFile = videoFile;

	}

	@Override
	protected Void doInBackground() throws Exception {
		int videoLength = ((int)videoFile.getLength()) / 2200;
		String currentTask = PlayBackPanel.getTask();
		if (currentTask == ">>"){
			for(int i = 0; i< videoLength; i++) {
				videoFile.skip(2000);
				Thread.sleep(200);
			}
		}
		if (currentTask == "<<"){
			for(int i = 0; i< videoLength; i++) {
				videoFile.skip(-2000);
				Thread.sleep(200);
			}
		}

		return null;
	}

	public void done(){

		try {
			get();
		} catch (CancellationException | InterruptedException | ExecutionException e){

		}
	}



}
