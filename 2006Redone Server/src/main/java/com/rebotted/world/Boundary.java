package com.rebotted.world;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import com.rebotted.game.npcs.Npc;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
/**
 * @author Andrew (Mr Extremez) - added all the boundaries 
 * @author Jason http://www.rune-server.org/members/jason - made the system
 * @date Mar 2, 2014
 */
public class Boundary {


	int minX, minY, highX, highY;
	int height;

	/**
	 * 
	 * @param minX
	 *            The south-west x coordinate
	 * @param minY
	 *            The south-west y coordinate
	 * @param highX
	 *            The north-east x coordinate
	 * @param highY
	 *            The north-east y coordinate
	 */
	public Boundary(int minX, int highX, int minY, int highY) {
		this.minX = minX;
		this.minY = minY;
		this.highX = highX;
		this.highY = highY;
		height = -1;
	}

	/**
	 * 
	 * @param minX
	 *            The south-west x coordinate
	 * @param minY
	 *            The south-west y coordinate
	 * @param highX
	 *            The north-east x coordinate
	 * @param highY
	 *            The north-east y coordinate
	 * @param height
	 *            The height of the boundary
	 */
	public Boundary(int minX, int highX, int minY, int highY, int height) {
		this.minX = minX;
		this.minY = minY;
		this.highX = highX;
		this.highY = highY;
		this.height = height;
	}
	
	/**
	 * 
	 * @param player
	 *            The player object
	 * @param boundaries
	 *            The array of Boundary objects
	 * @return
	 */
	public static boolean isIn(Player player, Boundary[] boundaries) {
		for (Boundary b : boundaries) {
			if (b.height >= 0) {
				if (player.getH() != b.height) {
					continue;
				}
			}
			if (player.getX() >= b.minX && player.getX() <= b.highX && player.getY() >= b.minY && player.getY() <= b.highY) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param player
	 *            The player object
	 * @param boundaries
	 *            The boundary object
	 * @return
	 */
	public static boolean isIn(Player player, Boundary boundaries) {
		if (boundaries.height >= 0) {
			if (player.getH() != boundaries.height) {
				return false;
			}
		}
		return player.getX() >= boundaries.minX && player.getX() <= boundaries.highX && player.getY() >= boundaries.minY && player.getY() <= boundaries.highY;
	}

	/**
	 * 
	 * @param npc
	 *            The npc object
	 * @param boundaries
	 *            The boundary object
	 * @return
	 */
	public static boolean isIn(Npc npc, Boundary boundaries) {
		if (boundaries.height >= 0) {
			if (npc.heightLevel != boundaries.height) {
				return false;
			}
		}
		return npc.getX() >= boundaries.minX && npc.getX() <= boundaries.highX && npc.getY() >= boundaries.minY && npc.getY() <= boundaries.highY;
	}

	public static boolean isIn(Npc npc, Boundary[] boundaries) {
		for (Boundary boundary : boundaries) {
			if (boundary.height >= 0) {
				if (npc.heightLevel != boundary.height) {
					return false;
				}
			}
			if (npc.getX() >= boundary.minX && npc.getX() <= boundary.highX && npc.getY() >= boundary.minY && npc.getY() <= boundary.highY) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInSameBoundary(Player player1, Player player2, Boundary[] boundaries) {
		Optional<Boundary> boundary1 = Arrays.asList(boundaries).stream().filter(b -> isIn(player1, b)).findFirst();
		Optional<Boundary> boundary2 = Arrays.asList(boundaries).stream().filter(b -> isIn(player2, b)).findFirst();
		if (!boundary1.isPresent() || !boundary2.isPresent()) {
			return false;
		}
		return Objects.equals(boundary1.get(), boundary2.get());
	}

	public static int entitiesInArea(Boundary boundary) {
		int i = 0;
		for (Player player : PlayerHandler.players)
			if (player != null)
				if (isIn(player, boundary))
					i++;
		return i;
	}

	/**
	 * Returns the centre point of a boundary as a {@link Coordinate}
	 * 
	 * @param boundary The boundary of which we want the centre.
	 * @return The centre point of the boundary, represented as a {@link Coordinate}.
	 */
	public static Coordinate centre(Boundary boundary) {
		int x = (boundary.minX + boundary.highX) / 2;
		int y = (boundary.minY + boundary.highY) / 2;
		if (boundary.height >= 0) {
			return new Coordinate(x, y, boundary.height);			
		} else {
			return new Coordinate(x, y, 0);			
		}
	}
	
	//low x, high x, low y, high y
	
	public static final Boundary F2P = new Boundary(2944, 3328, 3097, 3515);
	public static final Boundary TUTORIAL = new Boundary(3055, 3150, 3054, 3128);
	public static final Boundary CRANDOR = new Boundary(2813, 2867, 3226, 3307);
	public static final Boundary[] IN_F2P = {F2P, TUTORIAL, CRANDOR }; 
	public static final Boundary LUMBRIDGE = new Boundary(3134, 3266, 3131, 3317);
	public static final Boundary WIZARDS_TOWER = new Boundary(3094, 3124, 3141, 3172);
	public static final Boundary FALADOR = new Boundary(2945, 3066, 3303, 3390);
	public static final Boundary VARROCK = new Boundary(3172, 3289, 3368, 3504);
	public static final Boundary DRAYNOR = new Boundary(3079, 3149, 3226, 3382);
	public static final Boundary BARB = new Boundary(3072, 3098, 3399, 3445);
	public static final Boundary GOBLIN_VILLAGE = new Boundary(2945, 2970, 3475, 3515);
	public static final Boundary EDGEVILLE = new Boundary(3072, 3126, 3459, 3517);
	public static final Boundary PORT_SARIM = new Boundary(3327, 3423, 3131, 3324);
	public static final Boundary RIMMINGTON = new Boundary(3327, 3423, 3131, 3324);
	public static final Boundary AL_KHARID = new Boundary(3327, 3423, 3131, 3324);
	
	public static final Boundary[] BANK_AREA = new Boundary[] {
		new Boundary(3205, 3212, 3217, 3224, 2), //Lumbridge
		new Boundary(3264, 3273, 3160, 3174),//Al Kharid
		new Boundary(2436, 2453, 5174, 5186),//TzHaar
		new Boundary(2842, 2860, 2950, 2957),//Shilo
		new Boundary(3456, 3492, 3200, 3215),//Burgh d rott
		new Boundary(3377, 3386, 3266, 3275),//Duel
		new Boundary(3087, 3098, 3239, 3248),//Draynor
		new Boundary(3248, 3260, 3414, 3423),//Varrock East
		new Boundary(3183, 3193, 3432, 3446),//Varrock West
		new Boundary(3088, 3100, 3486, 3501),//Edge
		new Boundary(3009, 3020, 3352, 3358),//Fally East
		new Boundary(2942, 2950, 3365, 3374),//Fally West
		new Boundary(2804, 2815, 3438, 3447),//Catherby
		new Boundary(2718, 2733, 3485, 3500),//Seers
		new Boundary(2610, 2622, 3326, 3338),//North ardougne
		new Boundary(2645, 2660, 3281, 3288),//South ardougne
		new Boundary(2607, 2618, 3087, 3098),//Yanille
		new Boundary(2442, 2444, 3081, 3084),//Castle Wars
		new Boundary(2348, 2358, 3159, 3168),//Lleyta
		new Boundary(2324, 2334, 3685, 3694),//Piscatoris
		new Boundary(2442, 2448, 3420, 3430),//Tree Gnome Stronghold
		new Boundary(2440, 2453, 3478, 3491, 1),//Grand Tree Area
		new Boundary(3113, 3131, 3118, 3131),//Tut
		new Boundary(2885, 2895, 3422, 3433),//Nardah
		new Boundary(3685, 3694, 3461, 3473),//Phasmatys
		new Boundary(2530, 2550, 4705, 4725),//Mage Bank
		new Boundary(2834, 2841, 10204, 10215),//Keldagrim
		new Boundary(2379, 2386, 4453, 4462),//Zanaris
		new Boundary(2582, 2591, 3417, 3423),//Fishing Guild
		new Boundary(3509, 3515, 3475, 3483),//Canifis
		new Boundary(3297, 3311, 3115, 3133),//Shantay Pass
		new Boundary(3035, 3049, 4967, 4977, 1),//Rogues Den
	};
	
	public static final Boundary ZAMMY_WAIT = new Boundary(2409, 2431, 9511, 9535);
	public static final Boundary SARA_WAIT = new Boundary(2368, 2392, 9479, 9498);

	public static final Boundary[] MULTI = new Boundary[] { 
		new Boundary(3136, 3327, 3519, 3607), new Boundary(2360, 2445, 5045, 5125), new Boundary(2256, 2287, 4680, 4711),
		new Boundary(3190, 3327, 3648, 3839),  new Boundary(3200, 3390, 3840, 3967), new Boundary(2992, 3007, 3912, 3967),
		new Boundary(2946, 2959, 3816, 3831), new Boundary(3008, 3199, 3856, 3903), new Boundary(3008, 3071, 3600, 3711),
		new Boundary(3072, 3327, 3608, 3647), new Boundary(2624, 2690, 2550, 2619), new Boundary(2667, 2685, 3712, 3730),
		new Boundary(2371, 2422, 5062, 5117), new Boundary(2896, 2927, 3595, 3630), new Boundary(2892, 2932, 4435, 4464),
		new Boundary(3279, 3307, 3156, 3179)
	};
	
	//jungle
	public static final Boundary[] KARAMAJA = new Boundary[] {
		new Boundary(2745, 3007, 2876, 3095), new Boundary(2747, 2944, 3096, 3131)
	};
	
	//jungle
	public static final Boundary[] MUSA_POINT = new Boundary[] {
		new Boundary(2817, 2917, 3192, 3204), new Boundary(2817, 2961, 3131, 3191)
	};
	
	//jungle
	public static final Boundary BRIMHAVEN = new Boundary(2688, 2815, 3131, 3258);
	
	//desert
	public static final Boundary DESERT = new Boundary(3137, 3517, 2747, 3130, 0);
	//desert - no heat
	public static final Boundary NARDAH = new Boundary(3392, 3455, 2876, 2940);
	public static final Boundary BANDIT_CAMP = new Boundary(3151, 3192, 2963, 2986);
	public static final Boundary MINING_CAMP = new Boundary(3267, 3311, 3000, 3043);
	public static final Boundary BEDABIN = new Boundary(3160, 3187, 3015, 3046);
	public static final Boundary UZER = new Boundary(3462, 3503, 3068, 3109);
	public static final Boundary AGILITY_PYRAMID = new Boundary(3329, 3391, 2812, 2855);
	public static final Boundary PYRAMID = new Boundary(3217, 3250, 2881, 2908);
	public static final Boundary SOPHANEM = new Boundary(3273, 3323, 2749, 2806);
	public static final Boundary MENAPHOS = new Boundary(3200, 3266, 2749, 2806);
	public static final Boundary POLLIVNEACH = new Boundary(3329, 3377, 2936, 3002);
	public static final Boundary SHANTAY_PASS = new Boundary(3298, 3312, 3304, 3303);
	
	public static final Boundary[] NO_HEAT = {NARDAH, BANDIT_CAMP, MINING_CAMP, BEDABIN, UZER, AGILITY_PYRAMID, PYRAMID, SOPHANEM, MENAPHOS, POLLIVNEACH, SHANTAY_PASS};
	
	//mortyania
	public static final Boundary MORTYANIA = new Boundary(3401, 3773, 3157, 3577);
	
	//wild
	public static final Boundary[] WILDERNESS = new Boundary[] { new Boundary(2941, 3392, 3518, 3966), new Boundary(2941, 3392, 9922, 10366) };
	public static final Boundary IN_LESSER = new Boundary(3108, 3112, 3156, 3158, 2);
	public static final Boundary IN_DUEL = new Boundary(3331, 3391, 3242, 3260);
	public static final Boundary[] IN_DUEL_AREA = new Boundary[] { new Boundary(3322, 3394, 3195, 3291), new Boundary(3311, 3323, 3223, 3248) };
	
	public static final Boundary TRAWLER_GAME = new Boundary (2808, 2811, 3415, 3425);
	public static final Boundary PITS_WAIT = new Boundary (2394, 2404, 5169, 5175);
	
	public static final Boundary[] LUMB_BUILDING = new Boundary[] { new Boundary(3205, 3216, 3209, 3228), new Boundary(3229, 3233, 3206, 3208), new Boundary(3228, 3233, 3201, 3205), new Boundary(3230, 3237, 3195, 3198), new Boundary(3238, 3229, 3209, 3211),
			   new Boundary(3240, 3247, 3204, 3215), new Boundary(3247, 3252, 3190, 3195), new Boundary(3227, 3230, 3212, 3216), new Boundary(3227, 3230, 3221, 3225), new Boundary(3229, 3232, 3236, 3241),
			   new Boundary(3209, 3213, 3243, 3250), new Boundary(3222, 3229, 3252, 3257), new Boundary(3184, 3192, 3270, 3275), new Boundary(3222, 3224, 3292, 3294), new Boundary(3225, 3230, 3287, 3228),
			   new Boundary(3243, 3248, 3244, 3248), new Boundary(3202, 3205, 3167, 3170), new Boundary(3231, 3238, 3151, 3155), new Boundary(3233, 3234, 3156, 3156), new Boundary(3163, 3170, 3305, 3308),
			   new Boundary(3165, 3168, 3303, 3310) };
	
	public static final Boundary[] DRAYNOR_BUILDING = new Boundary[] { new Boundary(3097, 3102, 3277, 3281), new Boundary(3088, 3092, 3273, 3276), new Boundary(3096, 3102, 3266, 3270), new Boundary(3089, 3095, 3265, 3268), new Boundary(3083, 3088, 3256, 3261),
			   new Boundary(3087, 3094, 3251, 3255), new Boundary(3121, 3130, 3240, 3246), new Boundary(3102, 3112, 3162, 3165), new Boundary(3107, 3111, 3166, 3166), new Boundary(3103, 3115, 3157, 3161),
			   new Boundary(3105, 3114, 3156, 3156), new Boundary(3105, 3113, 3155, 3155), new Boundary(3106, 3112, 3154, 3154), new Boundary(3092, 3097, 3240, 3246) };
	
	public static final Boundary ARDOUGNE_ZOO = new Boundary(2593, 2639, 3265, 3288);
	public static final Boundary APE_ATOLL = new Boundary(2694, 2811, 2691, 2805);
	public static final Boundary BARROWS = new Boundary(3543, 3584, 3265, 3311);
	public static final Boundary BARROWS_UNDERGROUND = new Boundary(3529, 3581, 9673, 9722);
	public static final Boundary PC_BOAT = new Boundary(2660, 2663, 2638, 2643);
	public static final Boundary PC_GAME = new Boundary(2624, 2690, 2550, 2619);
	public static final Boundary FIGHT_CAVES = new Boundary(2360, 2445, 5045, 5125);
	public static final Boundary PIRATE_HOUSE = new Boundary(3038, 3044, 3949, 3959);
	public static final Boundary[] FIGHT_PITS = new Boundary[] { new Boundary(2378, 3415, 5133, 5167), new Boundary(2394, 2404, 5169, 5174) };

}