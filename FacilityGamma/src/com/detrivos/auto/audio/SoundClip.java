package com.detrivos.auto.audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public class SoundClip {
	
	protected Clip clip;
	
	public static SoundClip shoot1 = new SoundClip("sounds/shoot1.wav");
	public static SoundClip hurt = new SoundClip("sounds/hurt.wav");
	public static SoundClip hp = new SoundClip("sounds/hp.wav");
	public static SoundClip explosion1 = new SoundClip("sounds/explosion1.wav");

	public SoundClip(String path) {
		try {
			URL url = this.getClass().getClassLoader().getResource(path);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public static void play(SoundClip sc, float volume) {
		sc.clip.setFramePosition(0);
		FloatControl gainControl = (FloatControl) sc.clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(volume);
		sc.clip.start();
	}
}
