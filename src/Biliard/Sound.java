package Biliard;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	Clip clip;
	AudioInputStream inputStream;
	FloatControl volume;
	int vol;

	public void loadSound(String filePath) throws Throwable, IOException {
		inputStream= AudioSystem.getAudioInputStream(new File(filePath));
		clip=AudioSystem.getClip();
		clip.open(inputStream);
	}
	
	public void playSound() throws Throwable{
		clip.start(); //  so i dont hear the same song 200000 times
	}
	
	public void stopSound(){
		clip.stop();
	}
}