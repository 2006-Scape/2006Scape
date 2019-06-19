import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

import sun.audio.AudioPlayer;

public class SoundPlayer implements Runnable {

	private AudioInputStream stream;
	private DataLine.Info info;
	private Clip sound;

	private InputStream soundStream;
	private Thread player;
	private int delay;
	private int soundLevel;
	private InputStream arg0;
	public static int volume;

	/**
	 * Initializes the sound player.
	 * @param stream
	 * @param level
	 * @param delay
	 */
	public SoundPlayer(InputStream stream, int level, int delay) {
		if (level == 0 || volume == 4 || level - volume <= 0) {
			return;
		}
		this.soundStream = stream;
		this.soundLevel = level;
		this.delay = delay;
		player = new Thread(this);
		player.start();
	}

	/**
	 * Plays the sound.
	 */
	public void run() {
		try {
			AudioPlayer.player.start(arg0);
			stream = AudioSystem.getAudioInputStream(soundStream);
			info = new DataLine.Info(Clip.class, stream.getFormat());
			sound = (Clip) AudioSystem.getLine(info);
			sound.open(stream);
			FloatControl volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(getDecibels(soundLevel - getVolume()));
			if (delay > 0) {
				Thread.sleep(delay);
			}
			sound.start();
			while (sound.isActive()) {
				Thread.sleep(250);
			}
			Thread.sleep(10000);
			sound.close();
			stream.close();
			player.interrupt();
		} catch (Exception e) {
			player.interrupt();
			e.printStackTrace();
		}
	}

	/**
	 * Sets the client's volume level.
	 * @param level
	 */
	public static void setVolume(int level) {
		volume = level;
	}

	/**
	 * Returns the client's volume level.
	 */
	public static int getVolume() {
		return volume;
	}

	/**
	 * Returns the decibels for a given volume level.
	 * @param level
	 * @return
	 */
	public float getDecibels(int level) {
		switch (level) {
			case 0: // 4 in player options
				return (float) -1.0f;
			case 1: // 3
				return (float) -5.0f;
			case 2: // 2
				return (float) -10.0f;
			case 3: // 1
				return (float) -15.0f;
			case 4: // off
				return (float) -100.0f;
			default:
				return (float) 0.0f;
		}
	}
}