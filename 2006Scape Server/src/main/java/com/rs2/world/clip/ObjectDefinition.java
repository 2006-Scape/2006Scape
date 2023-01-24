package com.rs2.world.clip;

import org.apollo.jagcached.fs.IndexedFileSystem;

public final class ObjectDefinition {

	public static ObjectDefinition getObjectDef(int i) {
		for (int j = 0; j < 20; j++) {
			if (cache[j].type == i) {
				return cache[j];
			}
		}

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDefinition class46 = cache[cacheIndex];
		class46.type = i;
		class46.setDefaults();
		byte[] buffer = archive.get(i);
		if (buffer != null && buffer.length > 0) {
			class46.readValues(new ByteStreamExt(buffer));
		}
		return class46;
	}

	private void setDefaults() {
		name = null;
		width = 1;
		length = 1;
		solid = true;
		impenetrable = true;
		hasActions = false;
		clipped = true;
	}

/*	
Currently broken. Archive class from Apollo is backed by read only bytebuffers. TODO look at taking whole class from apollo.

public static void loadConfig(IndexedFileSystem fs) throws IOException {
		Archive configs = Archive.decode(fs.getFile(0, 2));
		ArchiveEntry dat = configs.getEntry("loc.dat");
		ArchiveEntry idx = configs.getEntry("loc.idx");
		
		archive = new MemoryArchive(new ByteStream(dat.getBuffer().array()),
				new ByteStream(idx.getBuffer().array()));
		cache = new ObjectDefinition[20];
		for (int k = 0; k < 20; k++) {
			cache[k] = new ObjectDefinition();
		}
		System.out.println("[ObjectDef] DONE LOADING OBJECT CONFIGURATION");
	}*/
	
	public static void loadConfig(IndexedFileSystem fs) {
		archive = new MemoryArchive(new ByteStream(getBuffer("loc.dat")),
				new ByteStream(getBuffer("loc.idx")));
		cache = new ObjectDefinition[20];
		for (int k = 0; k < 20; k++) {
			cache[k] = new ObjectDefinition();
		}
		System.out.println("[ObjectDef] DONE LOADING OBJECT CONFIGURATION");
	}

	private static byte[] getBuffer(String s) {
		try {
			java.io.File f = new java.io.File("./data/world/object/" + s);
			if (!f.exists()) {
				return null;
			}
			byte[] buffer = new byte[(int) f.length()];
			java.io.DataInputStream dis = new java.io.DataInputStream(
					new java.io.FileInputStream(f));
			dis.readFully(buffer);
			dis.close();
			return buffer;
		} catch (Exception e) {
		}
		return null;
	}

	private void readValues(ByteStreamExt stream) {
		int[] modelId = null;
		int[] modelType = null;
		
		int flag = -1;
		boolean actions = false;
		
		do {
			int type = stream.readUnsignedByte();
			if (type == 0) {
				break;
			}
			if (type == 1) {
				int len = stream.readUnsignedByte();
				if (len > 0) {
					if (modelId == null) {
						modelType = new int[len];
						modelId = new int[len];
						for (int k1 = 0; k1 < len; k1++) {
							modelId[k1] = stream.readUnsignedWord();
							modelType[k1] = stream.readUnsignedByte();
						}
					} else {
						stream.currentOffset += len * 3;
					}
				}
			} else if (type == 2) {
				name = stream.readNewString();
			} else if (type == 5) {
				int len = stream.readUnsignedByte();
				if (len > 0) {
					if (modelId == null) {
						modelType = null;
						modelId = new int[len];
						for (int l1 = 0; l1 < len; l1++) {
							modelId[l1] = stream.readUnsignedWord();
						}
					} else {
						stream.currentOffset += len * 2;
					}
				}
			} else if (type == 14) {
				width = stream.readUnsignedByte();
			} else if (type == 15) {
				length = stream.readUnsignedByte();
			} else if (type == 17) {
				solid = false;
			} else if (type == 18) {
				impenetrable = false;
			} else if (type == 19) {
				flag = stream.readUnsignedByte();
				hasActions = flag == 1;
			} else if (type == 21) {
				// aBoolean762 = true;
			} else if (type == 22) {
			} else if (type == 23) {
				// aBoolean764 = true;
			} else if (type == 24) {
				stream.readUnsignedWord();
			} else if (type == 27) {
				continue;
			} else if (type == 28) {
				/* anInt775 = */ stream.readUnsignedByte();
			} else if (type == 29) {
				stream.readSignedByte();
			} else if (type == 39) {
				stream.readSignedByte();
			} else if (type >= 30 && type < 39) {
				actions = true;
				stream.readNewString();
				hasActions = true;
			} else if (type == 40) {
				int i1 = stream.readUnsignedByte();
				stream.skip(i1 * 4);
			} else if (type == 41) {
				int l = stream.readUnsignedByte();
				stream.skip(l * 4);
			} else if (type == 42) {
				int l = stream.readUnsignedByte();
				stream.skip(l);
			} else if (type == 60) {
				/* anInt746 = */ stream.readUnsignedWord();
			} else if (type == 62) {
			} else if (type == 64) {
				clipped = false;
			} else if (type == 65) {
				stream.readUnsignedWord();
			} else if (type == 66) {
				stream.readUnsignedWord();
			} else if (type == 67) {
				stream.readUnsignedWord();
			} else if (type == 68) {
				/* anInt758 = */ stream.readUnsignedWord();
			} else if (type == 69) {
				/* anInt768 = */ stream.readUnsignedByte();
			} else if (type == 70) {
				stream.readSignedWord();
			} else if (type == 71) {
				stream.readSignedWord();
			} else if (type == 72) {
				stream.readSignedWord();
			} else if (type == 73) {
			//	aBoolean736 = true;
			} else if (type == 74) {
			} else if (type == 75) {
				stream.readUnsignedByte();
			} else if (type == 77 || type == 92) {
				stream.skip(4);
				if (type == 92) {
					stream.readUnsignedWord();
				}
				int count = stream.readUnsignedByte();
				for (int j2 = 0; j2 <= count; j2++) {
					stream.readUnsignedWord();
				}
			} else if (type == 78) {
				stream.skip(3);
			} else if (type == 79) {
				stream.skip(5);
				int l = stream.readUnsignedByte();
				stream.skip(l * 2);
			} else if (type == 81) {
				stream.skip(1);
			} else if (type == 82 || type == 88 || type == 89 || type == 90
					|| type == 91 || type == 94 || type == 95 || type == 96
					|| type == 97) {
				continue;
			} else if (type == 93) {
				stream.skip(2);
			} else if (type == 249) {
				int l = stream.readUnsignedByte();
				for (int ii = 0; ii < l; ii++) {
					boolean b = stream.readUnsignedByte() == 1;
					stream.skip(3);
					if (b) {
						stream.readNewString();
					} else {
						stream.skip(4);
					}
				}
			} else {
				System.out.println("Unknown config: " + type);
			}
		} while (true);
		if (flag == -1) {
			hasActions = modelId != null && (modelType == null || modelType[0] == 10);
			if (actions) {
				hasActions = true;
			}
		}
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

	public String name;
	private int width;
	private int type;
	private boolean impenetrable;
	private int length;
	private boolean solid;
	private static int cacheIndex;
	private boolean hasActions;
	private boolean clipped;
	private static ObjectDefinition[] cache;
	private static MemoryArchive archive;

}
