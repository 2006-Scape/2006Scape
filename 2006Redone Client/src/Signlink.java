import java.applet.Applet;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class Signlink implements Runnable {

	public static final void startpriv(InetAddress inetaddress) {
		threadliveid = (int) (Math.random() * 99999999D);
		if (active) {
			try {
				Thread.sleep(500L);
			} catch (Exception _ex) {
			}
			active = false;
		}
		socketreq = 0;
		threadreq = null;
		dnsreq = null;
		saveReq = null;
		urlreq = null;
		socketip = inetaddress;
		Thread thread = new Thread(new Signlink());
		thread.setDaemon(true);
		thread.start();
		while (!active) {
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}
	}

	enum Position {
		LEFT, RIGHT, NORMAL
	};

	private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
	private Position curPosition;

	@Override
	public void run() {
		active = true;
		String s = findcachedir();
		try {
			cache_dat = new RandomAccessFile(s + "main_file_cache.dat", "rw");
			for (int j = 0; j < 5; j++)
				cache_idx[j] = new RandomAccessFile(s + "main_file_cache.idx" + j, "rw");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		for (int i = threadliveid; threadliveid == i;) {
			if (socketreq != 0) {
				try {
					socket = new Socket(socketip, socketreq);
				} catch (Exception _ex) {
					socket = null;
				}
				socketreq = 0;
			} else if (threadreq != null) {
				Thread thread = new Thread(threadreq);
				thread.setDaemon(true);
				thread.start();
				thread.setPriority(threadreqpri);
				threadreq = null;
			} else if (dnsreq != null) {
				try {
					dns = InetAddress.getByName(dnsreq).getHostName();
				} catch (Exception _ex) {
					dns = "unknown";
				}
				dnsreq = null;
			} else if (saveReq != null) {
				if (savebuf != null)
					try {
						FileOutputStream fileoutputstream = new FileOutputStream(s + saveReq);
						fileoutputstream.write(savebuf, 0, savelen);
						fileoutputstream.close();
					} catch (Exception _ex) {
					}
				if (waveplay) {
					String wave = s + saveReq;
					waveplay = false;
					AudioInputStream audioInputStream = null;
					try {
						audioInputStream = AudioSystem.getAudioInputStream(new File(wave));
					} catch (UnsupportedAudioFileException e1) {
						e1.printStackTrace();
						return;
					} catch (IOException e1) {
						e1.printStackTrace();
						return;
					}
					AudioFormat format = audioInputStream.getFormat();
					SourceDataLine auline = null;
					DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
					try {
						auline = (SourceDataLine) AudioSystem.getLine(info);
						auline.open(format);
					} catch (LineUnavailableException e) {
						e.printStackTrace();
						return;
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
					if (auline.isControlSupported(FloatControl.Type.PAN)) {
						FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);
						if (curPosition == Position.RIGHT)
							pan.setValue(1.0f);
						else if (curPosition == Position.LEFT)
							pan.setValue(-1.0f);
					}
					auline.start();
					int nBytesRead = 0;
					byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
					try {
						while (nBytesRead != -1) {
							nBytesRead = audioInputStream.read(abData, 0,
									abData.length);
							if (nBytesRead >= 0)
								auline.write(abData, 0, nBytesRead);
						}
					} catch (IOException e) {
						e.printStackTrace();
						return;
					} finally {
						auline.drain();
						auline.close();
					}
				}
				if (play) {
					midi = s + saveReq;
					try {
						if (music != null) {
							music.stop();
							music.close();
						}
						playMidi(midi);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					play = false;
				}
				saveReq = null;
			} else if (urlreq != null) {
				try {
					System.out.println("urlstream");
					urlstream = new DataInputStream((new URL(mainapp.getCodeBase(), urlreq)).openStream());
				} catch (Exception _ex) {
					urlstream = null;
				}
				urlreq = null;
			}
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}
	}

	/**
	 * Plays the specified midi sequence.
	 * @param location
	 */
	private void playMidi(String location) {
		music = null;
		synthesizer = null;
		sequence = null;
		File midiFile = new File(location);
		try {
			sequence = MidiSystem.getSequence(midiFile);
			music = MidiSystem.getSequencer();
			music.open();
			music.setSequence(sequence);
		} catch (Exception e) {
			System.err.println("Problem loading MIDI file.");
			e.printStackTrace();
			return;
		}
		if (music instanceof Synthesizer) {
			synthesizer = (Synthesizer) music;
		} else {
			try {
				synthesizer = MidiSystem.getSynthesizer();
				synthesizer.open();
				if (synthesizer.getDefaultSoundbank() == null) {
					music.getTransmitter().setReceiver(MidiSystem.getReceiver());
				} else {
					music.getTransmitter().setReceiver(synthesizer.getReceiver());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		music.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		music.start();
		setVolume(midiVolume);
	}

	/**
	 * Sets the volume for the midi synthesizer.
	 * @param value
	 */
	public static boolean setVolume(int value) {
		if (synthesizer == null) {
			return false;
		}
		int CHANGE_VOLUME = 7;
		midiVolume = value;
		if (synthesizer.getDefaultSoundbank() == null) {
			try {
				ShortMessage volumeMessage = new ShortMessage();
				for (int i = 0; i < 16; i++) {
					volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, CHANGE_VOLUME, midiVolume);
					volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, 39, midiVolume);
					MidiSystem.getReceiver().send(volumeMessage, -1);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			try {
				MidiChannel[] channels = synthesizer.getChannels();
				for (int c = 0; channels != null && c < channels.length; c++) {
					channels[c].controlChange(CHANGE_VOLUME, midiVolume);
					channels[c].controlChange(39, midiVolume);
				}
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * Fades the volume of the midi out.
	 * Stops the current midi playing.
	 */
	public static void fadeOut() {
		if (music == null) {
			return;
		}
		int volume = midiVolume;
		if (music.isRunning()) {
			for (int index = midiVolume; index > 0 && volume > 0; index--) {
				volume--;
				setVolume(volume);
			}
		}
	}

	public static Sequencer music = null;
	public static Sequence sequence = null;
	public static Synthesizer synthesizer = null;

	public static String findcachedir() {
		String path = System.getProperty("user.home") + "/.2006redone_file_system/";
		File file = new File(path);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				return secondDir();
			}
		}
		return path;
	}

	public static String secondDir() {
		File file = new File("C:/.2006redone_file_system/");
		if (!file.exists()) {
			file.mkdirs();
		}
		return file.toString();
	}

	public static synchronized Socket opensocket(int i) throws IOException {
		for (socketreq = i; socketreq != 0;) {
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}

		if (socket == null) {
			throw new IOException("could not open socket");
		} else {
			return socket;
		}
	}

	public static synchronized DataInputStream openurl(String s) throws IOException {
		for (urlreq = s; urlreq != null;) {
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}

		if (urlstream == null) {
			throw new IOException("could not open: " + s);
		} else {
			return urlstream;
		}
	}

	public static synchronized void dnslookup(String s) {
		dns = s;
		dnsreq = s;
	}

	public static synchronized void startthread(Runnable runnable, int i) {
		threadreqpri = i;
		threadreq = runnable;
	}

	public static synchronized boolean wavesave(byte abyte0[], int i) {
		if (i > 0x1e8480) {
			return false;
		}
		if (saveReq != null) {
			return false;
		} else {
			wavepos = (wavepos + 1) % 5;
			savelen = i;
			savebuf = abyte0;
			waveplay = true;
			saveReq = "sound" + wavepos + ".wav";
			return true;
		}
	}

	public static synchronized boolean wavereplay() {
		if (saveReq != null) {
			return false;
		} else {
			savebuf = null;
			waveplay = true;
			saveReq = "sound" + wavepos + ".wav";
			return true;
		}
	}

	public static synchronized void saveMidi(byte abyte0[], int i) {
		if (i > 0x1e8480) {
			return;
		}
		if (saveReq != null) {
		} else {
			midiPos = (midiPos + 1) % 5;
			savelen = i;
			savebuf = abyte0;
			play = true;
			saveReq = "jingle" + midiPos + ".mid";
		}
	}

	public static void reporterror(String s) {
		System.out.println("Error: " + s);
	}

	private Signlink() {
	}

	public static final int clientversion = 317;
	public static int storeid = 32;
	public static RandomAccessFile cache_dat = null;
	public static final RandomAccessFile[] cache_idx = new RandomAccessFile[5];
	public static boolean sunjava;
	public static Applet mainapp = null;
	private static boolean active;
	private static int threadliveid;
	private static InetAddress socketip;
	private static int socketreq;
	private static Socket socket = null;
	private static int threadreqpri = 1;
	private static Runnable threadreq = null;
	private static String dnsreq = null;
	public static String dns = null;
	private static String urlreq = null;
	private static DataInputStream urlstream = null;
	public static boolean reporterror = true;
	public static String errorname = "";
	public static Midi midii = new Midi();
    private static int savelen;
    private static String saveReq = null;
    private static byte savebuf[] = null;
    public static boolean play;
    private static int midiPos;
    public static String midi = null;
    public static int midiVolume;
    public static int midifade;
    private static boolean waveplay;
    private static int wavepos;
    public static String wave = null;
    public static int wavevol;

}