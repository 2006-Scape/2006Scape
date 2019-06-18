import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

/**
 * Midi file playing.
 * 
 * @author Primadude.
 */
public class Midi implements Runnable {

	/**
	 * The name of the midi file.
	 */
	String midiFileName;

	/**
	 * The dir of the midi file.
	 */
	String midiSaveDir;

	/**
	 * The Sequence object.
	 */
	Sequence sequence;

	/**
	 * The Sequencer object.
	 */
	Sequencer sequencer;

	/**
	 * The Synthesizer object.
	 */
	Synthesizer synthesizer;

	/**
	 * Gets the name of the midi file.
	 * 
	 * @return The midiFileName variable.
	 */
	public String getMidiFileName() {
		return midiFileName;
	}

	/**
	 * Gets the midi file directory.
	 * 
	 * @return The midiSaveDir variable.
	 */
	public String getMidiSaveDir() {
		return midiSaveDir;
	}

	/**
	 * Sets the midi file name.
	 * 
	 * @param midiFileName
	 *            The String to set the file name to.
	 */
	public void setMidiFileName(String midiFileName) {
		this.midiFileName = midiFileName;
	}

	/**
	 * Sets the midi directory.
	 * 
	 * @param midiFileName
	 *            The String to set the file directory to.
	 */
	public void setMidiSaveDir(String midiSaveDir) {
		this.midiSaveDir = midiSaveDir;
	}

	/**
	 * Checks if the sequenced is finished. If the sequence is finished, it is
	 * faded out and closed.
	 */
	@Override
	public void run() {
		while (sequencer != null) {
			if (sequencer.getTickPosition() >= sequencer.getTickLength()) {
				fadeOut();
			}
		}
	}

	/**
	 * Sets the volume of the midi that is playing.
	 * 
	 * @param volume
	 *            The volume of the midi sound.
	 * @return True or false.
	 */
	public boolean setVolume(double value) {
		try {
			Receiver receiver = MidiSystem.getReceiver();
			ShortMessage volumeMessage = new ShortMessage();

			for (int i = 0; i < 16; i++) {
				volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, 7, (int) (value * 127.0));
				receiver.send(volumeMessage, -1);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Fades the volume of the midi out. Stops the current midi playing.
	 */
	public void fadeOut() {
		double volume = 1;
		while (volume - 0.05 > 0 && setVolume(volume)) {
			try {
				Thread.sleep(150);
			} catch (Exception exception) {
			}
			volume -= 0.025;
		}
		try {
			if (synthesizer != null) {
				synthesizer.close();
				synthesizer = null;
			}
			if (sequencer != null) {
				if (sequencer.isOpen()) {
					sequencer.stop();
				}
				sequencer.close();
			}
		} catch (Exception exception) {
		}
	}

	/**
	 * Starts playing the midi.
	 */
	public void startMidi() {
		String midiDir = getMidiFileName() + getMidiSaveDir();
		new Thread(this);

		try {
			if (sequencer != null) {
				fadeOut();
			}
			sequencer = null;
			sequence = null;

			File file = new File(midiDir);

			if (file.exists()) {
				sequence = MidiSystem.getSequence(file);
			}
			sequencer = MidiSystem.getSequencer();
			sequencer.setSequence(sequence);
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();

			if (synthesizer.getDefaultSoundbank() == null) {
				sequencer.getTransmitter().setReceiver(MidiSystem.getReceiver());
			} else {
				sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
			}
			sequencer.open();
			sequencer.start();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}