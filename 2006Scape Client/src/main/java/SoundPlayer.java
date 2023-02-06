import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.*;

public class SoundPlayer implements Runnable {

	private AudioInputStream stream;
	private DataLine.Info info;
	private SourceDataLine sound;

	private InputStream soundStream;
	private Thread player;
	private int delay;
	private int soundLevel;
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
			stream = AudioSystem.getAudioInputStream(soundStream);

			AudioFormat format = stream.getFormat();
			info = new DataLine.Info(SourceDataLine.class, format);
			sound = (SourceDataLine) AudioSystem.getLine(info);
			sound.open(format);

			FloatControl volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(getDecibels(soundLevel - getVolume()));
			if (delay > 0) {
				Thread.sleep(delay);
			}
			sound.start();

			int nBytesRead = 0;
			int EXTERNAL_BUFFER_SIZE = 524288;
			byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
			try {
				while (nBytesRead != -1) {
					nBytesRead = stream.read(abData, 0, abData.length);
					if (nBytesRead >= 0) {
						sound.write(abData, 0, nBytesRead);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				sound.drain();
				sound.close();
			}

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