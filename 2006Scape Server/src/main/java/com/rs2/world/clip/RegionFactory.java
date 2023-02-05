package com.rs2.world.clip;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.archive.Archive;
import org.apollo.cache.archive.ArchiveEntry;
import org.apollo.util.CompressionUtil;

public class RegionFactory {
	
	private static Region[] regions;
	
	public static Region[] getRegions() {
		return regions;
	}

	public static void load(IndexedFileSystem fs) throws IOException {
		//GameEngine.getLogger(Region.class).info("Loading region configurations...");
		Archive archive = Archive.decode(fs.getFile(0, 5));
		ArchiveEntry entry = archive.getEntry("map_index");
		ByteBuffer buffer = entry.getBuffer();

		int size = buffer.capacity() / 7;
		regions = new Region[size];
		int[] regionIds = new int[size];
		int[] mapGroundFileIds = new int[size];
		int[] mapObjectsFileIds = new int[size];
		boolean[] isMembers = new boolean[size];
		/**
		 * Seems to be that regions consist of
		 * regionIds (16 bits)
		 * groundFileIds (16 bits)
		 * objectsFileIds (16 bits)
		 * isMembers (8 bits)
		 */
		for (int i = 0; i < size; i++) {
			regionIds[i] = buffer.getShort() & 0xFFFF;
			mapGroundFileIds[i] = buffer.getShort() & 0xFFFF;
			mapObjectsFileIds[i] = buffer.getShort() & 0xFFFF;
			isMembers[i] = buffer.get() == 0;
		}
		for (int i = 0; i < size; i++) {
			regions[i] = new Region(regionIds[i], isMembers[i]);
		}
		//GameEngine.getLogger(Region.class).info(size + " Regions created.");
		//GameEngine.getLogger(Region.class).info("Populating regions...");
		for (int i = 0; i < size; i++) {
			//GameEngine.getLogger(Region.class).info("Region: " + i + " RegionId: " + regionIds[i] + " ObjectsId: " + mapObjectsFileIds[i]
			//		+ " ClippingsId: " + mapGroundFileIds[i]);				
			byte[] file1 = CompressionUtil.degzip(fs.getFile(4, mapObjectsFileIds[i]));
			byte[] file2 = CompressionUtil.degzip(fs.getFile(4, mapGroundFileIds[i]));
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
		//GameEngine.getLogger(Region.class).info("Region configuration done.");
	}
	
	/**
	 * Regions in runescape are chunks of the map.
	 * They are comprised of 64x64 blocks of x,y positions on 4 possibly height levels (z).
	 * 
	 * This code populates those positions.
	 * 
	 * @param regionId
	 * @param str1
	 * @param str2
	 */
	private static void loadMaps(int regionId, ByteStream str1, ByteStream str2) {
		int regionX = (regionId >> 8) * 64; // Region ID is bitshifted to get X position
		int regionY = (regionId & 0xff) * 64; // Region ID is bitshifted and AND'd against 0xff to get Y position
		int[][][] positionArray = new int[4][64][64];
		/**
		 * z seems to be the height (map level?) (0 through 3) (I'm told these loop for additional levels)
		 * x seems to be X position (of 64 possible positions)
		 * y seems to be Y position (of 64 possible positions)
		 */
		for (int localz = 0; localz < 4; localz++) { // height (z coord)
			for (int localx = 0; localx < 64; localx++) { // x coord
				for (int localy = 0; localy < 64; localy++) { // y coord
					while (true) { // we loop through each position x,y,z
						int v = str2.getUByte(); //Reading the bytestream, I guess the map is read and loaded bottom to top, left to right.
						if (v == 0) {
							break;
						} else if (v == 1) {
							str2.skip(1);
							break;
						} else if (v <= 49) {
							str2.skip(1);
						} else if (v <= 81) {
							positionArray[localz][localx][localy] = v - 49; // Clipping data is gathered.
						}
					}
				}
			}
		}
		/**
		 * Clipping data is validated and added.
		 */
		for (int localz = 0; localz < 4; localz++) {
			for (int localx = 0; localx < 64; localx++) {
				for (int localy = 0; localy < 64; localy++) {
					if ((positionArray[localz][localx][localy] & 1) == 1) { 
						int height = localz;
						if ((positionArray[1][localx][localy] & 2) == 2) {
							height--;
						}
						if (height >= 0 && height <= 3) {
							//GameEngine.getLogger(Region.class).debug("Adding clipping at x,y " + (regionX + localx) + "," + (regionY + localy) + " at height: " + localz);
							Region.addClipping(regionX + localx, regionY + localy, height, 0x200000);
						}
					}
				}
			}
		}
		/**
		 * Object data.
		 */
		int objectId = -1;
		int incr;
		while ((incr = str1.getUSmart()) != 0) {
			objectId += incr;
			int location = 0;
			int incr2;
			while ((incr2 = str1.getUSmart()) != 0) {
				location += incr2 - 1;
				int objectX = location >> 6 & 0x3f;
				int objectY = location & 0x3f;
				int objectHeight = location >> 12;
				int objectData = str1.getUByte();
				int type = objectData >> 2;
				int direction = objectData & 0x3;
				if (objectX < 0 || objectX >= 64 || objectY < 0 || objectY >= 64) {
					continue; //Checks the object position is not outside the bounds of a region (0-64)
				}
				if ((positionArray[1][objectX][objectY] & 2) == 2) {
					objectHeight--;
				}
				if (objectHeight >= 0 && objectHeight <= 3) {
					Region.addObject(objectId, regionX + objectX, regionY + objectY, objectHeight,
							type, direction, false);
				}
			}
		}
	}
}
