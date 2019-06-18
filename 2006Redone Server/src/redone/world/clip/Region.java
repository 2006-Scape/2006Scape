package redone.world.clip;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import redone.game.objects.Objects;

public class Region {
	
	private ArrayList<Objects> realObjects = new ArrayList<Objects>();
	 
	public static Region getRegion(int x, int y) {
	    int regionX = x >> 3;
	    int regionY = y >> 3;
	    int regionId = (regionX / 8 << 8) + regionY / 8;
	    for (Region region : regions) {
	        if (region.id() == regionId) {
	            return region;
	        }
	    }
	    return null;
	}
	
	public static boolean blockedShot(int x, int y, int z) {
		return (getClipping(x, y, z) & 0x20000) == 0;
	}
	 
	public static boolean objectExists(int id, int x, int y, int z) {
	    Region r = getRegion(x, y);
	    if (r == null)
	        return false;
	    for (Objects o : r.realObjects) {
	        if (o.objectId == id) {
	            if (o.objectX == x && o.objectY == y && o.objectHeight == z) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

	private void addClip(int x, int y, int height, int shift) {
		int regionAbsX = (id >> 8) * 64;
		int regionAbsY = (id & 0xff) * 64;
		if (clips[height] == null) {
			clips[height] = new int[64][64];
		}
		clips[height][x - regionAbsX][y - regionAbsY] |= shift;
	}
	
	public static boolean canMove(int startX, int startY, int endX, int endY, int height, int xLength, int yLength) {
		int diffX = endX - startX;
		int diffY = endY - startY;
		int max = Math.max(Math.abs(diffX), Math.abs(diffY));
		for (int ii = 0; ii < max; ii++) {
			int currentX = endX - diffX;
			int currentY = endY - diffY;
			for (int i = 0; i < xLength; i++) {
				for (int i2 = 0; i2 < yLength; i2++)
					if (diffX < 0 && diffY < 0) {
						if ((getClipping((currentX + i) - 1,
								(currentY + i2) - 1, height) & 0x128010e) != 0
								|| (getClipping((currentX + i) - 1, currentY
										+ i2, height) & 0x1280108) != 0
								|| (getClipping(currentX + i,
										(currentY + i2) - 1, height) & 0x1280102) != 0)
							return false;
					} else if (diffX > 0 && diffY > 0) {
						if ((getClipping(currentX + i + 1, currentY + i2 + 1,
								height) & 0x12801e0) != 0
								|| (getClipping(currentX + i + 1,
										currentY + i2, height) & 0x1280180) != 0
								|| (getClipping(currentX + i,
										currentY + i2 + 1, height) & 0x1280120) != 0)
							return false;
					} else if (diffX < 0 && diffY > 0) {
						if ((getClipping((currentX + i) - 1, currentY + i2 + 1,
								height) & 0x1280138) != 0
								|| (getClipping((currentX + i) - 1, currentY
										+ i2, height) & 0x1280108) != 0
								|| (getClipping(currentX + i,
										currentY + i2 + 1, height) & 0x1280120) != 0)
							return false;
					} else if (diffX > 0 && diffY < 0) {
						if ((getClipping(currentX + i + 1, (currentY + i2) - 1,
								height) & 0x1280183) != 0
								|| (getClipping(currentX + i + 1,
										currentY + i2, height) & 0x1280180) != 0
								|| (getClipping(currentX + i,
										(currentY + i2) - 1, height) & 0x1280102) != 0)
							return false;
					} else if (diffX > 0 && diffY == 0) {
						if ((getClipping(currentX + i + 1, currentY + i2,
								height) & 0x1280180) != 0)
							return false;
					} else if (diffX < 0 && diffY == 0) {
						if ((getClipping((currentX + i) - 1, currentY + i2,
								height) & 0x1280108) != 0)
							return false;
					} else if (diffX == 0 && diffY > 0) {
						if ((getClipping(currentX + i, currentY + i2 + 1,
								height) & 0x1280120) != 0)
							return false;
					} else if (diffX == 0
							&& diffY < 0
							&& (getClipping(currentX + i, (currentY + i2) - 1,
									height) & 0x1280102) != 0)
						return false;

			}

			if (diffX < 0)
				diffX++;
			else if (diffX > 0)
				diffX--;
			if (diffY < 0)
				diffY++;
			else if (diffY > 0)
				diffY--;
		}

		return true;
	}

	private int getClip(int x, int y, int height) {
		int regionAbsX = (id >> 8) * 64;
		int regionAbsY = (id & 0xff) * 64;
		if (clips[height] == null) {
			return 0;
		}
		return clips[height][x - regionAbsX][y - regionAbsY];
	}

	private static void addClipping(int x, int y, int height, int shift) {
		int regionX = x >> 3;
		int regionY = y >> 3;
		int regionId = (regionX / 8 << 8) + regionY / 8;
		for (Region r : regions) {
			if (r.id() == regionId) {
				r.addClip(x, y, height, shift);
				break;
			}
		}
	}

	private static Region[] regions;
	private final int id;
	private final int[][][] clips = new int[4][][];
	private boolean members = false;

	public Region(int id, boolean members) {
		this.id = id;
		this.members = members;
	}

	public int id() {
		return id;
	}

	public boolean members() {
		return members;
	}

	public static boolean isMembers(int x, int y, int height) {
		if (x >= 3272 && x <= 3320 && y >= 2752 && y <= 2809) {
			return false;
		}
		if (x >= 2640 && x <= 2677 && y >= 2638 && y <= 2679) {
			return false;
		}
		int regionX = x >> 3;
		int regionY = y >> 3;
		int regionId = (regionX / 8 << 8) + regionY / 8;
		for (Region r : regions) {
			if (r.id() == regionId) {
				return r.members();
			}
		}
		return false;
	}

	private static void addClippingForVariableObject(int x, int y, int height,
			int type, int direction, boolean flag) {
		if (type == 0) {
			if (direction == 0) {
				addClipping(x, y, height, 128);
				addClipping(x - 1, y, height, 8);
			} else if (direction == 1) {
				addClipping(x, y, height, 2);
				addClipping(x, y + 1, height, 32);
			} else if (direction == 2) {
				addClipping(x, y, height, 8);
				addClipping(x + 1, y, height, 128);
			} else if (direction == 3) {
				addClipping(x, y, height, 32);
				addClipping(x, y - 1, height, 2);
			}
		} else if (type == 1 || type == 3) {
			if (direction == 0) {
				addClipping(x, y, height, 1);
				addClipping(x - 1, y, height, 16);
			} else if (direction == 1) {
				addClipping(x, y, height, 4);
				addClipping(x + 1, y + 1, height, 64);
			} else if (direction == 2) {
				addClipping(x, y, height, 16);
				addClipping(x + 1, y - 1, height, 1);
			} else if (direction == 3) {
				addClipping(x, y, height, 64);
				addClipping(x - 1, y - 1, height, 4);
			}
		} else if (type == 2) {
			if (direction == 0) {
				addClipping(x, y, height, 130);
				addClipping(x - 1, y, height, 8);
				addClipping(x, y + 1, height, 32);
			} else if (direction == 1) {
				addClipping(x, y, height, 10);
				addClipping(x, y + 1, height, 32);
				addClipping(x + 1, y, height, 128);
			} else if (direction == 2) {
				addClipping(x, y, height, 40);
				addClipping(x + 1, y, height, 128);
				addClipping(x, y - 1, height, 2);
			} else if (direction == 3) {
				addClipping(x, y, height, 160);
				addClipping(x, y - 1, height, 2);
				addClipping(x - 1, y, height, 8);
			}
		}
		if (flag) {
			if (type == 0) {
				if (direction == 0) {
					addClipping(x, y, height, 65536);
					addClipping(x - 1, y, height, 4096);
				} else if (direction == 1) {
					addClipping(x, y, height, 1024);
					addClipping(x, y + 1, height, 16384);
				} else if (direction == 2) {
					addClipping(x, y, height, 4096);
					addClipping(x + 1, y, height, 65536);
				} else if (direction == 3) {
					addClipping(x, y, height, 16384);
					addClipping(x, y - 1, height, 1024);
				}
			}
			if (type == 1 || type == 3) {
				if (direction == 0) {
					addClipping(x, y, height, 512);
					addClipping(x - 1, y + 1, height, 8192);
				} else if (direction == 1) {
					addClipping(x, y, height, 2048);
					addClipping(x + 1, y + 1, height, 32768);
				} else if (direction == 2) {
					addClipping(x, y, height, 8192);
					addClipping(x + 1, y + 1, height, 512);
				} else if (direction == 3) {
					addClipping(x, y, height, 32768);
					addClipping(x - 1, y - 1, height, 2048);
				}
			} else if (type == 2) {
				if (direction == 0) {
					addClipping(x, y, height, 66560);
					addClipping(x - 1, y, height, 4096);
					addClipping(x, y + 1, height, 16384);
				} else if (direction == 1) {
					addClipping(x, y, height, 5120);
					addClipping(x, y + 1, height, 16384);
					addClipping(x + 1, y, height, 65536);
				} else if (direction == 2) {
					addClipping(x, y, height, 20480);
					addClipping(x + 1, y, height, 65536);
					addClipping(x, y - 1, height, 1024);
				} else if (direction == 3) {
					addClipping(x, y, height, 81920);
					addClipping(x, y - 1, height, 1024);
					addClipping(x - 1, y, height, 4096);
				}
			}
		}
	}

	private static void addClippingForSolidObject(int x, int y, int height,
			int xLength, int yLength, boolean flag) {
		int clipping = 256;
		if (flag) {
			clipping += 0x20000;
		}
		for (int i = x; i < x + xLength; i++) {
			for (int i2 = y; i2 < y + yLength; i2++) {
				addClipping(i, i2, height, clipping);
			}
		}
	}

	public static void addObject(int objectId, int x, int y, int height, int type, int direction, boolean startUp) {
		if (ObjectDef.getObjectDef(objectId) == null) {
		}
		int xLength;
		int yLength;
		if (direction != 1 && direction != 3) {
			xLength = ObjectDef.getObjectDef(objectId).xLength();
			yLength = ObjectDef.getObjectDef(objectId).yLength();
		} else {
			xLength = ObjectDef.getObjectDef(objectId).yLength();
			yLength = ObjectDef.getObjectDef(objectId).xLength();
		}
		if (type == 22) {
			if (ObjectDef.getObjectDef(objectId).hasActions()
					&& ObjectDef.getObjectDef(objectId).aBoolean767()) {
				addClipping(x, y, height, 0x200000);
			}
		} else if (type >= 9) {
			if (ObjectDef.getObjectDef(objectId).aBoolean767()) {
				addClippingForSolidObject(x, y, height, xLength, yLength,
						ObjectDef.getObjectDef(objectId).solid());
			}
		} else if (type >= 0 && type <= 3) {
			if (ObjectDef.getObjectDef(objectId).aBoolean767()) {
				addClippingForVariableObject(x, y, height, type, direction,
						ObjectDef.getObjectDef(objectId).solid());
			}
		}
		Region r = getRegion(x, y);
		if (r != null) {
		    if (startUp)
		        r.realObjects.add(new Objects(objectId, x, y, height, direction, type, 0));
		    else if (!objectExists(objectId, x, y, height))
		        r.realObjects.add(new Objects(objectId, x, y, height, direction, type, 0));
		}
	}

	public static int getClipping(int x, int y, int height) {
		if (height > 3) {
			height = 0;
		}
		int regionX = x >> 3;
		int regionY = y >> 3;
		int regionId = (regionX / 8 << 8) + regionY / 8;
		for (Region r : regions) {
			if (r.id() == regionId) {
				return r.getClip(x, y, height);
			}
		}
		return 0;
	}

	public static boolean getClipping(int x, int y, int height, int moveTypeX,
			int moveTypeY) {
		try {
			if (height > 3) {
				height = 0;
			}
			int checkX = x + moveTypeX;
			int checkY = y + moveTypeY;
			if (moveTypeX == -1 && moveTypeY == 0) {
				return (getClipping(x, y, height) & 0x1280108) == 0;
			} else if (moveTypeX == 1 && moveTypeY == 0) {
				return (getClipping(x, y, height) & 0x1280180) == 0;
			} else if (moveTypeX == 0 && moveTypeY == -1) {
				return (getClipping(x, y, height) & 0x1280102) == 0;
			} else if (moveTypeX == 0 && moveTypeY == 1) {
				return (getClipping(x, y, height) & 0x1280120) == 0;
			} else if (moveTypeX == -1 && moveTypeY == -1) {
				return (getClipping(x, y, height) & 0x128010e) == 0
						&& (getClipping(checkX - 1, checkY, height) & 0x1280108) == 0
						&& (getClipping(checkX - 1, checkY, height) & 0x1280102) == 0;
			} else if (moveTypeX == 1 && moveTypeY == -1) {
				return (getClipping(x, y, height) & 0x1280183) == 0
						&& (getClipping(checkX + 1, checkY, height) & 0x1280180) == 0
						&& (getClipping(checkX, checkY - 1, height) & 0x1280102) == 0;
			} else if (moveTypeX == -1 && moveTypeY == 1) {
				return (getClipping(x, y, height) & 0x1280138) == 0
						&& (getClipping(checkX - 1, checkY, height) & 0x1280108) == 0
						&& (getClipping(checkX, checkY + 1, height) & 0x1280120) == 0;
			} else if (moveTypeX == 1 && moveTypeY == 1) {
				return (getClipping(x, y, height) & 0x12801e0) == 0
						&& (getClipping(checkX + 1, checkY, height) & 0x1280180) == 0
						&& (getClipping(checkX, checkY + 1, height) & 0x1280120) == 0;
			} else {
				System.out.println("[FATAL ERROR]: At getClipping: " + x + ", "
						+ y + ", " + height + ", " + moveTypeX + ", "
						+ moveTypeY);
				return false;
			}
		} catch (Exception e) {
			return true;
		}
	}

	public static void load() {
		try {
			File f = new File("./Data/world/map_index");
			byte[] buffer = new byte[(int) f.length()];
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			dis.readFully(buffer);
			dis.close();
			ByteStream in = new ByteStream(buffer);
			int size = in.length() / 7;
			regions = new Region[size];
			int[] regionIds = new int[size];
			int[] mapGroundFileIds = new int[size];
			int[] mapObjectsFileIds = new int[size];
			boolean[] isMembers = new boolean[size];
			for (int i = 0; i < size; i++) {
				regionIds[i] = in.getUShort();
				mapGroundFileIds[i] = in.getUShort();
				mapObjectsFileIds[i] = in.getUShort();
				isMembers[i] = in.getUByte() == 0;
			}
			for (int i = 0; i < size; i++) {
				regions[i] = new Region(regionIds[i], isMembers[i]);
			}
			for (int i = 0; i < size; i++) {
				byte[] file1 = getBuffer(new File("./Data/world/map/"
						+ mapObjectsFileIds[i] + ".gz"));
				byte[] file2 = getBuffer(new File("./Data/world/map/"
						+ mapGroundFileIds[i] + ".gz"));
				if (file1 == null || file2 == null) {
					continue;
				}
				try {
					loadMaps(regionIds[i], new ByteStream(file1),
							new ByteStream(file2));
				} catch (Exception e) {
					System.out.println("Error loading map region: "
							+ regionIds[i]);
				}
			}
			System.out.println("[Region] DONE LOADING REGION CONFIGURATIONS");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadMaps(int regionId, ByteStream str1, ByteStream str2) {
		int absX = (regionId >> 8) * 64;
		int absY = (regionId & 0xff) * 64;
		int[][][] someArray = new int[4][64][64];
		for (int i = 0; i < 4; i++) {
			for (int i2 = 0; i2 < 64; i2++) {
				for (int i3 = 0; i3 < 64; i3++) {
					while (true) {
						int v = str2.getUByte();
						if (v == 0) {
							break;
						} else if (v == 1) {
							str2.skip(1);
							break;
						} else if (v <= 49) {
							str2.skip(1);
						} else if (v <= 81) {
							someArray[i][i2][i3] = v - 49;
						}
					}
				}
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int i2 = 0; i2 < 64; i2++) {
				for (int i3 = 0; i3 < 64; i3++) {
					if ((someArray[i][i2][i3] & 1) == 1) {
						int height = i;
						if ((someArray[1][i2][i3] & 2) == 2) {
							height--;
						}
						if (height >= 0 && height <= 3) {
							addClipping(absX + i2, absY + i3, height, 0x200000);
						}
					}
				}
			}
		}
		int objectId = -1;
		int incr;
		while ((incr = str1.getUSmart()) != 0) {
			objectId += incr;
			int location = 0;
			int incr2;
			while ((incr2 = str1.getUSmart()) != 0) {
				location += incr2 - 1;
				int localX = location >> 6 & 0x3f;
				int localY = location & 0x3f;
				int height = location >> 12;
				int objectData = str1.getUByte();
				int type = objectData >> 2;
				int direction = objectData & 0x3;
				if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
					continue;
				}
				if ((someArray[1][localX][localY] & 2) == 2) {
					height--;
				}
				if (height >= 0 && height <= 3) {
					addObject(objectId, absX + localX, absY + localY, height,
							type, direction, false);
				}
			}
		}
	}

	public static byte[] getBuffer(File f) throws Exception {
		if (!f.exists()) {
			return null;
		}
		byte[] buffer = new byte[(int) f.length()];
		DataInputStream dis = new DataInputStream(new FileInputStream(f));
		dis.readFully(buffer);
		dis.close();
		byte[] gzipInputBuffer = new byte[999999];
		int bufferlength = 0;
		GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(
				buffer));
		do {
			if (bufferlength == gzipInputBuffer.length) {
				System.out
						.println("Error inflating data.\nGZIP buffer overflow.");
				break;
			}
			int readByte = gzip.read(gzipInputBuffer, bufferlength,
					gzipInputBuffer.length - bufferlength);
			if (readByte == -1) {
				break;
			}
			bufferlength += readByte;
		} while (true);
		byte[] inflated = new byte[bufferlength];
		System.arraycopy(gzipInputBuffer, 0, inflated, 0, bufferlength);
		buffer = inflated;
		if (buffer.length < 10) {
			return null;
		}
		return buffer;
	}

}
