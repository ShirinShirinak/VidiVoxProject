package functionality.audio;

import java.io.File;

public class Audio {
	String audioPath = null;
	String audioName = null;
	public int time = 0;
	
	public Audio(String path, String name){
		audioPath = path;
		audioName = name;
		
	}
	
	public void setTime(int time){
		this.time = time;
	}
	
	public String getAudioPath(){
		return audioPath;
	}
	
	public String getAudioName(){
		return audioName;
	}
	
	public int getTime(){
		return time;
	}
}
