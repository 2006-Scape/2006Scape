package com.rs2.world.clip;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.archive.Archive;

public final class ObjectDefinition {

	private static ObjectDefinition[] definitions;

	public static ObjectDefinition getObjectDef(int i) {
		return definitions[i];
	}

	public static void loadConfig(IndexedFileSystem fs) throws IOException {
		Archive config = Archive.decode(fs.getFile(0, 2));
		ByteBuffer data = config.getEntry("loc.dat").getBuffer();
		ByteBuffer idx = config.getEntry("loc.idx").getBuffer();
		int count = idx.getShort(), index = 2;
		int[] indices = new int[count];
		for (int i = 0; i < count; i++) {
			indices[i] = index;
			index += idx.getShort();
		}
		definitions = new ObjectDefinition[count];
		for (int i = 0; i < count; i++) {
			data.position(indices[i]);
			definitions[i] = readValues(i, data);
		}
		System.out.println("[ObjectDef] DONE LOADING OBJECT CONFIGURATION");
	}

	private static ObjectDefinition readValues(int id, ByteBuffer data) {
		ObjectDefinition def = new ObjectDefinition();
		def.type = id;
		int[] modelId = null;
		int[] modelType = null;

		int flag = -1;
		boolean actions = false;
		do {
			int type = data.get() & 0xFF;
			if (type == 0) {
				break;
			}
			if (type == 1) {
				int len = data.get() & 0xFF;
				if (len > 0) {
					if (modelId == null) {
						modelType = new int[len];
						modelId = new int[len];
						for (int k1 = 0; k1 < len; k1++) {
							modelId[k1] = data.getShort() & 0xFFFF;
							modelType[k1] = data.get() & 0xFF;
						}
					} else {
						for (int i = 0; i < len; i++) {
							data.getShort();
							data.get();
						}
					}
				}
			} else if (type == 2) {
				def.name = readString(data);
			} else if (type == 3) {
				readString(data);
			} else if (type == 5) {
				int len = data.get() & 0xFF;
				if (len > 0) {
					if (modelId == null) {
						modelType = null;
						modelId = new int[len];
						for (int l1 = 0; l1 < len; l1++) {
							modelId[l1] = data.getShort() & 0xFFFF;
						}
					} else {
						for (int i = 0; i < len; i++) {
							data.getShort();
						}
					}
				}
			} else if (type == 14) {
				def.width = data.get() & 0xFF;
			} else if (type == 15) {
				def.length = data.get() & 0xFF;
			} else if (type == 17) {
				def.solid = false;
			} else if (type == 18) {
				def.impenetrable = false;
			} else if (type == 19) {
				flag = data.get() & 0xFF;
				def.hasActions = flag == 1;
			} else if (type == 21) {
				// aBoolean762 = true;
			} else if (type == 22) {
			} else if (type == 23) {
				// aBoolean764 = true;
			} else if (type == 24) {
				data.getShort();
			} else if (type == 27) {
				continue;
			} else if (type == 28) {
				data.get();
			} else if (type == 29) {
				data.get();
			} else if (type == 39) {
				data.get();
			} else if (type >= 30 && type < 39) {
				actions = true;
				readString(data);
				def.hasActions = true;
			} else if (type == 40) {
				int amount = data.get() & 0xFF;
				for (int i = 0; i < amount; i++) {
					data.getShort();
					data.getShort();
				}
			} else if (type == 60) {
				data.getShort();
			} else if (type == 62) {
			} else if (type == 64) {
				def.clipped = false;
			} else if (type == 65) {
				data.getShort();
			} else if (type == 66) {
				data.getShort();
			} else if (type == 67) {
				data.getShort();
			} else if (type == 68) {
				data.getShort();
			} else if (type == 69) {
				data.get();
			} else if (type == 70) {
				data.getShort();
			} else if (type == 71) {
				data.getShort();
			} else if (type == 72) {
				data.getShort();
			} else if (type == 73) {
				// #TODO obstructive = true;
			} else if (type == 74) {
			} else if (type == 75) {
				data.get();
			} else if (type == 77) {
				data.getShort();
				data.getShort();
				int count = data.get() & 0xFF;
				for (int i = 0; i <= count; i++) {
					data.getShort();
				}
			} else {
				System.out.println("Unknown  config: " + type);
				System.exit(0);
			}
		} while (true);
		if (flag == -1) {
			def.hasActions = modelId != null && (modelType == null || modelType[0] == 10);
			if (actions) {
				def.hasActions = true;
			}
		}
		return def;
	}

	/**
	 * Reads a string from the specified {@link ByteBuffer}.
	 *
	 * @param buffer The buffer.
	 * @return The string.
	 */
	public static String readString(ByteBuffer buffer) {
		StringBuilder bldr = new StringBuilder();
		char character;
		while ((character = (char) buffer.get()) != 10) {
			bldr.append(character);
		}
		return bldr.toString();
	}

	private ObjectDefinition() {
		type = -1;
	}

	/*
	 * TODO is this needed? Only called by type 22 objects (ground decorations/map signs).
	 */
	public boolean hasActions() {
		return hasActions;
	}

	public boolean hasName() {
		return name != null && name.length() > 1;
	}

	public boolean solid() {
		return clipped;
	}

	public int xLength() {
		return width;
	}

	public int yLength() {
		return length;
	}

	public boolean aBoolean767() {
		return solid;
	}

	public boolean isUnshootable() {
		return impenetrable;
	}

	public String name = null;
	private int width = 1;
	@SuppressWarnings("unused")
	private int type;
	private boolean impenetrable = true;
	private int length = 1;
	private boolean solid = true;
	private boolean hasActions = false;
	private boolean clipped = true;
}
