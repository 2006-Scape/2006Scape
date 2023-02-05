package com.rs2.world.clip;

import java.util.ArrayList;

import org.apollo.cache.def.ObjectDefinition;

import com.rs2.game.objects.Objects;

public class Region {
	
	private ArrayList<Objects> realObjects = new ArrayList<Objects>();
	private final int id;
	private final int[][][] clips = new int[4][][];
	private final int[][][] projectileClips = new int[4][][];
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

	public static boolean isMembers(int x, int y) {
		if (x >= 3272 && x <= 3320 && y >= 2752 && y <= 2809) {
			return false;
		}
		if (x >= 2640 && x <= 2677 && y >= 2638 && y <= 2679) {
			return false;
		}
		return getRegion(x, y).members();
	}
	
	/**
	 * Takes X Y coordinates, gives a region object
	 * 
	 * @param x coordinate X
	 * @param y coordinate Y
	 * @return Region object
	 */
	public static Region getRegion(int x, int y) {
	    int regionId = getRegionId(x,y);
	    for (Region region : RegionFactory.getRegions()) {
	        if (region.id() == regionId) {
	            return region;
	        }
	    }
	    return null;
	}
	
	/**
	 * Calculates regionId from X Y coordinates
	 * 
	 * @param x coordinate X
	 * @param y coordinate Y
	 * @return ID of target region
	 */
	public static int getRegionId(int x, int y) {
	    int regionX = x >> 3;
	    int regionY = y >> 3;
	    int regionId = (regionX / 8 << 8) + regionY / 8;
	    return regionId;
	}

	public static Objects getObject(int id, int x, int y, int z) {
		Region r = getRegion(x, y);
		if (r == null)
			return null;
		for (Objects o : r.realObjects) {
			if (o.objectId == id) {
				if (o.objectX == x && o.objectY == y && o.objectHeight == z) {
					return o;
				}
			}
		}
		return null;
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

	private void removeClip(int x, int y, int height) {
		final int regionAbsX = (id >> 8) * 64;
		final int regionAbsY = (id & 0xff) * 64;
		if (clips[height] == null) {
			clips[height] = new int[64][64];
		}
		clips[height][x - regionAbsX][y - regionAbsY] = 0;
	}

	/**
	 * Nothing calls this...
	 * 
	 * @param x
	 * @param y
	 * @param height
	 */
	public void removeClipping(int x, int y, int height) {
		for (Region r : RegionFactory.getRegions()) {
			if (r.id() == getRegionId(x,y)) {
				r.removeClip(x, y, height);
				break;
			}
		}
	}

	private void addProjectileClip(int x, int y, int height, int shift) {
		int regionAbsX = (id >> 8) * 64;
		int regionAbsY = (id & 0xff) * 64;
		if (projectileClips[height] == null) {
			projectileClips[height] = new int[64][64];
		}
		projectileClips[height][x - regionAbsX][y - regionAbsY] |= shift;
	}

	public static boolean canMove(int x, int y, int z, int direction) {
		if (direction == 6) {
			if ((Region.getClipping(x, y - 1, z) & 0x1280102) == 0) {
				return true;
			}
		} else if (direction == 3) {
			if ((Region.getClipping(x - 1, y, z) & 0x1280108) == 0) {
				return true;
			}
		} else if (direction == 1) {
			if ((Region.getClipping(x, y + 1, z) & 0x1280120) == 0) {
				return true;
			}
		} else if (direction == 4) {
			if ((Region.getClipping(x + 1, y, z) & 0x1280180) == 0) {
				return true;
			}
		} else if (direction == 5) {
			if ((Region.getClipping(x - 1, y - 1, z) & 0x128010e) == 0
					&& (Region.getClipping(x - 1, y, z) & 0x1280108) == 0
					&& (Region.getClipping(x, y - 1, z) & 0x1280102) == 0) {
				return true;
			}
		} else if (direction == 0) {
			if ((Region.getClipping(x - 1, y + 1, z) & 0x1280138) == 0
					&& (Region.getClipping(x - 1, y, z) & 0x1280108) == 0
					&& (Region.getClipping(x, y + 1, z) & 0x1280120) == 0) {
				return true;
			}
		} else if (direction == 7) {
			if ((Region.getClipping(x + 1, y - 1, z) & 0x1280183) == 0
					&& (Region.getClipping(x + 1, y, z) & 0x1280180) == 0
					&& (Region.getClipping(x, y - 1, z) & 0x1280102) == 0) {
				return true;
			}
		} else if (direction == 2) {
			if ((Region.getClipping(x + 1, y + 1, z) & 0x12801e0) == 0
					&& (Region.getClipping(x + 1, y, z) & 0x1280180) == 0
					&& (Region.getClipping(x, y + 1, z) & 0x1280120) == 0) {
				return true;
			}
		} else if (direction == -1) {
			throw new IllegalArgumentException("Invalid direction: " + direction);
		}

		return false;
	}

	public static boolean canShoot(int x, int y, int z, int direction) {
		if (direction == 0) {
			return !projectileBlockedNorthWest(x, y, z) && !projectileBlockedNorth(x, y, z)
					&& !projectileBlockedWest(x, y, z);
		} else if (direction == 1) {
			return !projectileBlockedNorth(x, y, z);
		} else if (direction == 2) {
			return !projectileBlockedNorthEast(x, y, z) && !projectileBlockedNorth(x, y, z)
					&& !projectileBlockedEast(x, y, z);
		} else if (direction == 3) {
			return !projectileBlockedWest(x, y, z);
		} else if (direction == 4) {
			return !projectileBlockedEast(x, y, z);
		} else if (direction == 5) {
			return !projectileBlockedSouthWest(x, y, z) && !projectileBlockedSouth(x, y, z)
					&& !projectileBlockedWest(x, y, z);
		} else if (direction == 6) {
			return !projectileBlockedSouth(x, y, z);
		} else if (direction == 7) {
			return !projectileBlockedSouthEast(x, y, z) && !projectileBlockedSouth(x, y, z)
					&& !projectileBlockedEast(x, y, z);
		}
		return false;
	}

	public static boolean projectileBlockedNorth(int x, int y, int z) {
		return (getProjectileClipping(x, y + 1, z) & 0x1280120) != 0;
	}

	public static boolean projectileBlockedEast(int x, int y, int z) {
		return (getProjectileClipping(x + 1, y, z) & 0x1280180) != 0;
	}

	public static boolean projectileBlockedSouth(int x, int y, int z) {
		return (getProjectileClipping(x, y - 1, z) & 0x1280102) != 0;
	}

	public static boolean projectileBlockedWest(int x, int y, int z) {
		return (getProjectileClipping(x - 1, y, z) & 0x1280108) != 0;
	}

	public static boolean projectileBlockedNorthEast(int x, int y, int z) {
		return (getProjectileClipping(x + 1, y + 1, z) & 0x12801e0) != 0;
	}

	public static boolean projectileBlockedNorthWest(int x, int y, int z) {
		return (getProjectileClipping(x - 1, y + 1, z) & 0x1280138) != 0;
	}

	public static boolean projectileBlockedSouthEast(int x, int y, int z) {
		return (getProjectileClipping(x + 1, y - 1, z) & 0x1280183) != 0;
	}

	public static boolean projectileBlockedSouthWest(int x, int y, int z) {
		return (getProjectileClipping(x - 1, y - 1, z) & 0x128010e) != 0;
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

	private int getProjectileClip(int x, int y, int height) {
		int regionAbsX = (id >> 8) * 64;
		int regionAbsY = (id & 0xff) * 64;
		if (projectileClips[height] == null) {
			return 0;
		}
		return projectileClips[height][x - regionAbsX][y - regionAbsY];
	}

	/**
	 * Adds clipping to whichever region matches provided XYZ.
	 * 
	 * @param x coordinate X
	 * @param y coordinate Y
	 * @param height coordinate Z
	 * @param shift uuuuh shift?
	 */
	public static void addClipping(int x, int y, int height, int shift) {
		for (Region r : RegionFactory.getRegions()) {
			if (r.id() == getRegionId(x, y)) {
				r.addClip(x, y, height, shift);
				break;
			}
		}
	}

	private static void addProjectileClipping(int x, int y, int height, int shift) {
		for (Region r : RegionFactory.getRegions()) {
			if (r.id() == getRegionId(x,y)) {
				r.addProjectileClip(x, y, height, shift);
				break;
			}
		}
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

	private static void addProjectileClippingForVariableObject(int x, int y, int height,
													 int type, int direction, boolean flag) {
		if (type == 0) {
			if (direction == 0) {
				addProjectileClipping(x, y, height, 128);
				addProjectileClipping(x - 1, y, height, 8);
			} else if (direction == 1) {
				addProjectileClipping(x, y, height, 2);
				addProjectileClipping(x, y + 1, height, 32);
			} else if (direction == 2) {
				addProjectileClipping(x, y, height, 8);
				addProjectileClipping(x + 1, y, height, 128);
			} else if (direction == 3) {
				addProjectileClipping(x, y, height, 32);
				addProjectileClipping(x, y - 1, height, 2);
			}
		} else if (type == 1 || type == 3) {
			if (direction == 0) {
				addProjectileClipping(x, y, height, 1);
				addProjectileClipping(x - 1, y, height, 16);
			} else if (direction == 1) {
				addProjectileClipping(x, y, height, 4);
				addProjectileClipping(x + 1, y + 1, height, 64);
			} else if (direction == 2) {
				addProjectileClipping(x, y, height, 16);
				addProjectileClipping(x + 1, y - 1, height, 1);
			} else if (direction == 3) {
				addProjectileClipping(x, y, height, 64);
				addProjectileClipping(x - 1, y - 1, height, 4);
			}
		} else if (type == 2) {
			if (direction == 0) {
				addProjectileClipping(x, y, height, 130);
				addProjectileClipping(x - 1, y, height, 8);
				addProjectileClipping(x, y + 1, height, 32);
			} else if (direction == 1) {
				addProjectileClipping(x, y, height, 10);
				addProjectileClipping(x, y + 1, height, 32);
				addProjectileClipping(x + 1, y, height, 128);
			} else if (direction == 2) {
				addProjectileClipping(x, y, height, 40);
				addProjectileClipping(x + 1, y, height, 128);
				addProjectileClipping(x, y - 1, height, 2);
			} else if (direction == 3) {
				addProjectileClipping(x, y, height, 160);
				addProjectileClipping(x, y - 1, height, 2);
				addProjectileClipping(x - 1, y, height, 8);
			}
		}
		if (flag) {
			if (type == 0) {
				if (direction == 0) {
					addProjectileClipping(x, y, height, 65536);
					addProjectileClipping(x - 1, y, height, 4096);
				} else if (direction == 1) {
					addProjectileClipping(x, y, height, 1024);
					addProjectileClipping(x, y + 1, height, 16384);
				} else if (direction == 2) {
					addProjectileClipping(x, y, height, 4096);
					addProjectileClipping(x + 1, y, height, 65536);
				} else if (direction == 3) {
					addProjectileClipping(x, y, height, 16384);
					addProjectileClipping(x, y - 1, height, 1024);
				}
			}
			if (type == 1 || type == 3) {
				if (direction == 0) {
					addProjectileClipping(x, y, height, 512);
					addProjectileClipping(x - 1, y + 1, height, 8192);
				} else if (direction == 1) {
					addProjectileClipping(x, y, height, 2048);
					addProjectileClipping(x + 1, y + 1, height, 32768);
				} else if (direction == 2) {
					addProjectileClipping(x, y, height, 8192);
					addProjectileClipping(x + 1, y + 1, height, 512);
				} else if (direction == 3) {
					addProjectileClipping(x, y, height, 32768);
					addProjectileClipping(x - 1, y - 1, height, 2048);
				}
			} else if (type == 2) {
				if (direction == 0) {
					addProjectileClipping(x, y, height, 66560);
					addProjectileClipping(x - 1, y, height, 4096);
					addProjectileClipping(x, y + 1, height, 16384);
				} else if (direction == 1) {
					addProjectileClipping(x, y, height, 5120);
					addProjectileClipping(x, y + 1, height, 16384);
					addProjectileClipping(x + 1, y, height, 65536);
				} else if (direction == 2) {
					addProjectileClipping(x, y, height, 20480);
					addProjectileClipping(x + 1, y, height, 65536);
					addProjectileClipping(x, y - 1, height, 1024);
				} else if (direction == 3) {
					addProjectileClipping(x, y, height, 81920);
					addProjectileClipping(x, y - 1, height, 1024);
					addProjectileClipping(x - 1, y, height, 4096);
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

	private static void addProjectileClippingForSolidObject(int x, int y, int height,
												  int xLength, int yLength, boolean flag) {
		int clipping = 256;
		if (flag) {
			clipping += 0x20000;
		}
		for (int i = x; i < x + xLength; i++) {
			for (int i2 = y; i2 < y + yLength; i2++) {
				addProjectileClipping(i, i2, height, clipping);
			}
		}
	}

	/**
	 * 
	 * Adds object to region
	 * 
	 * @param objectId
	 * @param x
	 * @param y
	 * @param height
	 * @param type
	 * @param direction
	 * @param startUp
	 */
	public static void addObject(int objectId, int x, int y, int height, int type, int direction, boolean startUp) {
		if (ObjectDefinition.lookup(objectId) == null) {
		}
		int xLength;
		int yLength;
		if (direction != 1 && direction != 3) {
			xLength = ObjectDefinition.lookup(objectId).getWidth();
			yLength = ObjectDefinition.lookup(objectId).getLength();
		} else {
			xLength = ObjectDefinition.lookup(objectId).getLength();
			yLength = ObjectDefinition.lookup(objectId).getWidth();
		}
		if (type == 22) {
			if (ObjectDefinition.lookup(objectId).isInteractive()
					&& ObjectDefinition.lookup(objectId).isSolid()) {
				addClipping(x, y, height, 0x200000);
				if (ObjectDefinition.lookup(objectId).isImpenetrable()) {
					addProjectileClipping(x, y, height, 0x200000);
				}
			}
		} else if (type >= 9) {
			if (ObjectDefinition.lookup(objectId).isSolid()) {
				addClippingForSolidObject(x, y, height, xLength, yLength,
						ObjectDefinition.lookup(objectId).isClipped());
				if (ObjectDefinition.lookup(objectId).isImpenetrable()) {
					addProjectileClippingForSolidObject(x, y, height, xLength, yLength,
							ObjectDefinition.lookup(objectId).isClipped());
				}
			}
		} else if (type >= 0 && type <= 3) {
			if (ObjectDefinition.lookup(objectId).isSolid()) {
				addClippingForVariableObject(x, y, height, type, direction,
						ObjectDefinition.lookup(objectId).isClipped());
				if (ObjectDefinition.lookup(objectId).isImpenetrable()) {
					addProjectileClippingForVariableObject(x, y, height, type, direction, ObjectDefinition.lookup(objectId).isClipped());
				}
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
			height = 0; //this doesn't seem good
		}
		for (Region r : RegionFactory.getRegions()) {
			if (r.id() == getRegionId(x,y)) {
				return r.getClip(x, y, height);
			}
		}
		return 0;
	}

	public static int getProjectileClipping(int x, int y, int height) {
		if (height > 3) {
			height = 0;
		}
		for (Region r : RegionFactory.getRegions()) {
			if (r.id() == getRegionId(x,y)) {
				return r.getProjectileClip(x, y, height);
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

}
