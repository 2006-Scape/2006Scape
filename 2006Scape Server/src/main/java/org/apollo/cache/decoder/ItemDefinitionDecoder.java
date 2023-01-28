package org.apollo.cache.decoder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.archive.Archive;
import org.apollo.cache.def.ItemDefinition;
import org.apollo.util.BufferUtil;

/**
 * Decodes item data from the {@code obj.dat} file into {@link ItemDefinition}s.
 *
 * @author Graham
 */
public final class ItemDefinitionDecoder implements Runnable {

	/**
	 * The  IndexedFileSystem.
	 */
	private final IndexedFileSystem fs;

	/**
	 * Creates the ItemDefinitionDecoder.
	 *
	 * @param fs The {@link IndexedFileSystem}.
	 */
	public ItemDefinitionDecoder(IndexedFileSystem fs) {
		this.fs = fs;
	}

	@Override
	public void run() {
		try {
			Archive config = fs.getArchive(0, 2);
			ByteBuffer data = config.getEntry("obj.dat").getBuffer();
			ByteBuffer idx = config.getEntry("obj.idx").getBuffer();

			int count = idx.getShort(), index = 2;
			int[] indices = new int[count];
			for (int i = 0; i < count; i++) {
				indices[i] = index;
				index += idx.getShort();
			}

			ItemDefinition[] definitions = new ItemDefinition[count];
			for (int i = 0; i < count; i++) {
				data.position(indices[i]);
				definitions[i] = decode(i, data);
			}

			ItemDefinition.init(definitions);
		} catch (IOException e) {
			throw new UncheckedIOException("Error decoding ItemDefinitions.", e);
		}
	}

	/**
	 * Decodes a single definition.
	 *
	 * @param id The item's id.
	 * @param buffer The buffer.
	 * @return The {@link ItemDefinition}.
	 */
	private ItemDefinition decode(int id, ByteBuffer buffer) {
		ItemDefinition definition = new ItemDefinition(id);
		while (true) {
			int opcode = buffer.get() & 0xFF;

			if (opcode == 0) {
				return definition;
			} else if (opcode == 1) {
				buffer.getShort();
			} else if (opcode == 2) {
				definition.setName(BufferUtil.readString(buffer));
			} else if (opcode == 3) {
				definition.setDescription(BufferUtil.readString(buffer));
			} else if (opcode >= 4 && opcode <= 8 || opcode == 10) {
				buffer.getShort();
			} else if (opcode == 11) {
				definition.setStackable(true);
			} else if (opcode == 12) {
				definition.setValue(buffer.getInt());
			} else if (opcode == 16) {
				definition.setMembersOnly(true);
			} else if (opcode == 23) {
				buffer.getShort();
				buffer.get();
			} else if (opcode == 24) {
				buffer.getShort();
			} else if (opcode == 25) {
				buffer.getShort();
				buffer.get();
			} else if (opcode == 26) {
				buffer.getShort();
			} else if (opcode >= 30 && opcode < 35) {
				String str = BufferUtil.readString(buffer);
				if (str.equalsIgnoreCase("hidden")) {
					str = null;
				}
				definition.setGroundAction(opcode - 30, str);
			} else if (opcode >= 35 && opcode < 40) {
				definition.setInventoryAction(opcode - 35, BufferUtil.readString(buffer));
			} else if (opcode == 40) {
				int colourCount = buffer.get() & 0xFF;
				for (int i = 0; i < colourCount; i++) {
					buffer.getShort();
					buffer.getShort();
				}
			} else if (opcode == 78 || opcode == 79 || (opcode >= 90 && opcode <= 93) || opcode == 95) {
				buffer.getShort();
			} else if (opcode == 97) {
				definition.setNoteInfoId(buffer.getShort() & 0xFFFF);
			} else if (opcode == 98) {
				definition.setNoteGraphicId(buffer.getShort() & 0xFFFF);
			} else if (opcode >= 100 && opcode < 110) {
				buffer.getShort();
				buffer.getShort();
			} else if (opcode >= 110 && opcode <= 112) {
				buffer.getShort();
			} else if (opcode == 113 || opcode == 114) {
				buffer.get();
			} else if (opcode == 115) {
				definition.setTeam(buffer.get() & 0xFF);
			}
		}
	}

}