package org.apollo.cache.decoder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.archive.Archive;
import org.apollo.cache.def.NpcDefinition;
import org.apollo.util.BufferUtil;

/**
 * Decodes npc data from the {@code npc.dat} file into {@link NpcDefinition}s.
 *
 * @author Major
 */
public final class NpcDefinitionDecoder implements Runnable {

	/**
	 * The IndexedFileSystem.
	 */
	private final IndexedFileSystem fs;

	/**
	 * Creates the NpcDefinitionDecoder.
	 *
	 * @param fs The {@link IndexedFileSystem}.
	 */
	public NpcDefinitionDecoder(IndexedFileSystem fs) {
		this.fs = fs;
	}

	@Override
	public void run() {
		try {
			Archive config = fs.getArchive(0, 2);
			ByteBuffer data = config.getEntry("npc.dat").getBuffer();
			ByteBuffer idx = config.getEntry("npc.idx").getBuffer();

			int count = idx.getShort(), index = 2;
			int[] indices = new int[count];

			for (int i = 0; i < count; i++) {
				indices[i] = index;
				index += idx.getShort();
			}

			NpcDefinition[] definitions = new NpcDefinition[count];
			for (int i = 0; i < count; i++) {
				data.position(indices[i]);
				definitions[i] = decode(i, data);
			}

			NpcDefinition.init(definitions);
		} catch (IOException e) {
			throw new UncheckedIOException("Error decoding NpcDefinitions.", e);
		}
	}

	/**
	 * Decodes a single definition.
	 *
	 * @param id The npc's id.
	 * @param buffer The buffer.
	 * @return The {@link NpcDefinition}.
	 */
	private NpcDefinition decode(int id, ByteBuffer buffer) {
		NpcDefinition definition = new NpcDefinition(id);

		while (true) {
			int opcode = buffer.get() & 0xFF;

			if (opcode == 0) {
				return definition;
			} else if (opcode == 1) {
				int length = buffer.get() & 0xFF;
				int[] models = new int[length];
				for (int index = 0; index < length; index++) {
					models[index] = buffer.getShort();
				}
			} else if (opcode == 2) {
				definition.setName(BufferUtil.readString(buffer));
			} else if (opcode == 3) {
				definition.setDescription(BufferUtil.readString(buffer));
			} else if (opcode == 12) {
				definition.setSize(buffer.get());
			} else if (opcode == 13) {
				definition.setStandAnimation(buffer.getShort());
			} else if (opcode == 14) {
				definition.setWalkAnimation(buffer.getShort());
			} else if (opcode == 17) {
				definition
						.setWalkAnimations(buffer.getShort(), buffer.getShort(), buffer.getShort(), buffer.getShort());
			} else if (opcode >= 30 && opcode < 40) {
				String action = BufferUtil.readString(buffer);
				if (action.equals("hidden")) {
					action = null;
				}

				definition.setInteraction(opcode - 30, action);
			} else if (opcode == 40) {
				int length = buffer.get() & 0xFF;
				int[] originalColours = new int[length];
				int[] replacementColours = new int[length];

				for (int index = 0; index < length; index++) {
					originalColours[index] = buffer.getShort();
					replacementColours[index] = buffer.getShort();
				}
			} else if (opcode == 60) {
				int length = buffer.get() & 0xFF;
				int[] additionalModels = new int[length];

				for (int index = 0; index < length; index++) {
					additionalModels[index] = buffer.getShort();
				}
			} else if (opcode >= 90 && opcode <= 92) {
				buffer.getShort(); // Dummy
			} else if (opcode == 95) {
				definition.setCombatLevel(buffer.getShort());
			} else if (opcode == 97 || opcode == 98) {
				buffer.getShort();
			} else if (opcode == 100 || opcode == 101) {
				buffer.get();
			} else if (opcode == 102 || opcode == 103) {
				buffer.getShort();
			} else if (opcode == 106) {
				wrap(buffer.getShort());
				wrap(buffer.getShort());

				int count = buffer.get() & 0xFF;
				int[] morphisms = new int[count + 1];
				Arrays.setAll(morphisms, index -> wrap(buffer.getShort()));
			}
		}
	}

	/**
	 * Wraps a morphism value around, returning -1 if the specified value is 65,535.
	 *
	 * @param value The value.
	 * @return -1 if {@code value} is 65,535, otherwise {@code value}.
	 */
	private int wrap(int value) {
		return value == 65_535 ? -1 : value;
	}

}