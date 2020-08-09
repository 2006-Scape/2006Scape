package com.rebotted.game.objects;

import com.rebotted.game.players.Player;
import com.rebotted.world.clip.ObjectDefinition;

/**
 * ObjectDefaults
 * @author Andrew (Mr Extremez)
 */

public class ObjectDefaults {
	
	private static int 
		SOUTH = 0,
		NORTH = 1,
		EAST = 2,
		WEST = 3;
	
	public static int getObjectType(Player player, int objectType) {
		String objectName = ObjectDefinition.getObjectDef(objectType).name;
		if (objectName.contains("Wardrobe") || objectName.contains("chest") || objectName.contains("Cupboard") || objectName.contains("Coffin")) {
			return 10;
		} else if (objectName.contains("Curtain")) {
			if ((player.getX() >= 3298 && player.getX() <= 3300) && (player.getY() >= 3186 && player.getY() <= 3188)) {
				return 9;
			}
		}
		return 0;
	}
	
	/**
	 * object x
	 * object y
	 * object face
	 * object height
	 */
	private static final int[][] GET_FACE = {
			{3209, 3217, 2, 1}, {3382, 3270, 1, 0}, {3381, 3269, 2, 0},
			{3309, 3120, 3, 0}, {3112, 3355, 2, 0}, {3112, 3356, 2, 0}, 
			{3104, 3360, 2, 0}, {3104, 3356, 2, 0}, {3096, 3356, 2, 0},
			{3096, 3361, 2, 0}, {3114, 3362, 3, 1}, {3118, 3362, 3, 1}, 
			{3113, 3368, 1, 1}, {3104, 3368, 1, 1}, {3118, 3359, 2, 1}, 
			{3112, 3360, 2, 2}, {3112, 3358, 2, 2}, {3049, 3383, 2, 0},
			{3042, 3375, 2, 0}, {3042, 3376, 2, 0}, {3046, 3367, 1, 0}, 
			{3047, 3367, 1, 0}, {3045, 3361, 3, 1}, {3216, 3487, 3, 1}, 
			{3224, 3493, 2, 1}, {3224, 3495, 2, 1}, {3201, 3483, 1, 0},
			{3201, 3484, 1, 0}, {3203, 3481, 1, 0}, {3192, 3273, 3, 0}, 
			{3185, 3274, 2, 0}, {3096, 3266, 1, 0}, {3096, 3267, 1, 0}, 
			{3087, 3261, 1, 0}, {3084, 3256, 3, 0}, {3024, 3259, 3, 0},
			{3023, 3259, 3, 0}, {3027, 3262, 1, 1}, {3026, 3259, 3, 1}, 
			{3013, 3249, 1, 0}, {3013, 3234, 3, 1}, {3014, 3234, 3, 1},
			{3016, 3205, 3, 1}, {3009, 3206, 1, 1}, {3009, 3207, 1, 1},
			{3016, 3207, 3, 0}, {2657, 3322, 2, 1}, {3090, 3479, 2, 0},
			{3090, 3476, 2, 0}, {3096, 3469, 3, 0}, {2970, 3214, 2, 1}, 
			{2765, 3505, 1, 1}, {2748, 3495, 2, 2}, {3303, 3172, 2, 0}, 
			{3300, 3177, 1, 0}, {3282, 3169, 1, 0}, {3282, 3168, 1, 0}, 
			{3299, 3169, 2, 1}, {3300, 3169, 2, 1}, {3301, 3169, 2, 1}, 
			{3319, 3137, 3, 0}, {3324, 3137, 3, 0}, {3099, 3373, 1, 0},
			{2620, 3291, 3, 1}, {2578, 3295, 1, 1}, {2575, 3292, 3, 0},
			{2578, 3295, 2, 1}, {2579, 3295, 2, 1},	{3023, 3259, 3, 1},
			{3012, 3452, 3, 0}, {3021, 3449, 3, 0}, {3016, 3256, 2, 0},
			{3016, 3261, 2, 0}, {2649, 3361, 3, 0}, {3090, 9899, 2, 0},
			{3089, 9899, 2, 0}
	};
	
	public static int getObjectFace(Player player, int objectType) {
		//default face
		int face = SOUTH;
		for (final int[] element : GET_FACE) {
			if (player.objectX == element[0] && player.objectY == element[1] && player.getH() == element[3]) {
				return face = element[2];
			} else {
				switch (objectType) {
				case 6910:
					if (player.objectX == 3263) {
						return face = WEST;
					}
				case 1529:
				case 1528:
					if (getObjectType(player, objectType) == 9 || player.objectX == 3315 || player.objectX == 3172 || player.objectX == 3259) {
						return face = WEST;
					} else if (player.objectX == 3313 || player.objectX == 3317) {
						return face = EAST;
					} else if (player.objectX == 3287 || player.objectX == 3292) {
						return face = NORTH;
					}
				case 388:
				case 389:
					if (player.objectX == 3112 || player.objectX == 3104 || player.objectX == 3096) {
						return face = EAST;
					}
				case 14879:
					return face = NORTH;
				case 1568:
					return face = (player.objectX == 3405) ? EAST : SOUTH;
				case 375:
				case 376:
				case 377:
					if (player.objectX == 3096) {
						return face = NORTH;
					} else if (player.objectX == 3263) {
						return face = WEST;
					} else if (player.objectX == 2892) {
						return face = WEST;
					}
				case 399:
				case 398:
					return face = (player.objectX == 3096 && player.objectY == 3469) ? WEST : (player.objectX == 3096) ? SOUTH : EAST;
				case 3193:
					return face = (player.objectX == 3381) ? EAST : NORTH;
				case 378:
					if (player.objectX == 3096) {
						return face = NORTH;
					} else if (player.objectX == 3263) {
						return face = WEST;
					} else if (player.objectX == 2892) {
						return face = WEST;
					}
				case 3194:
					switch (player.objectX) {
						case 3381:
							return face = EAST;
						case 3309:
							return face = WEST;
						default:
							return face = NORTH;
					}
				case 348:
				case 349:
					if (player.objectX == 2971) {
						return face = NORTH;
					}
				case 350:
				case 351:
					if (player.objectY == 3382) {
						return face = WEST;
					}
				}
			}
		}
		return face;
	}

}