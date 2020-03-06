package com.rebotted.game.content.music.sound;

import com.rebotted.game.players.Player;

/**
 * Sounds
 * @author Andrew
 */

public class SoundList {

	Player c;

	public SoundList(Player player) {
		this.c = player;
	}

	/**
	 * Skilling
	 */

	public static final int COOK_ITEM = 357;
	public static final int MINING_ORE = 1331;
	public static final int ANVIL = 468;
	public static final int BONE_BURY = 380;
	public static final int FIRE_LIGHT = 375, FIRE_SUCCESSFUL = 608, FIRST_ATTEMPT = 2584;
	public static final int TREE_CUT_BEGIN = 471, TREE_CUTTING = 472, TREE_EMPTY = 473;
	public static final int FISHING = 289;// Cast out Net/Cage/Harpoon
	public static final int START_FLY_FISHING = 377, FLY_FISHING = 378;
	public static final int RUNECRAFTING = 481;
	public static final int RAKE_WEEDS = 1323;// farming
	public static final int SMELTING_ORE = 469;// smelting ore in furnace
	public static final int SMITHING_ANVIL = 468;
	public static final int STUNNED = 458;// stunned from thieving
	public static final int PESTLE_MOTAR = 373;
	public static final int PROSPECTING = 431;
	public static final int CUT_GEM = 464;

	/**
	 * Skill Level Up Sounds
	 */
	//Each are different lengths, so a songDelay in the sendQuickSong method would have to be changed per skill sound
	//Some of these are level up sounds from 50-99, while some are only heard when unlocking certain items/armour
	//Some might not be level up sounds
	public static final int LEVEL_UP_RUNECRAFTING_UNKNOWN = 66;//?
	public static final int LEVEL_UP_UNKNOWN = 67; //?
	public static final int LEVEL_UP_COOKING = 68;
	public static final int LEVEL_UP_UNKNOWN2 = 69;//?
	public static final int LEVEL_UP_FIREMAKING = 71;
	public static final int LEVEL_UP_RUNECRAFTING = 72;
	public static final int LEVEL_UP_THIEVING = 73;
	public static final int LEVEL_UP_RANGED = 74;
	public static final int LEVEL_UP_FLETCHING = 77;
	public static final int LEVEL_UP_SMITHING = 78; //? I can't hear this?
	public static final int LEVEL_UP_PRAYER2 = 79;
	public static final int LEVEL_UP_UNKNOWN5 = 80; //?
	public static final int LEVEL_UP_UNKNOWN6 = 82; //?
	public static final int LEVEL_UP_PRAYER = 83;
	public static final int LEVEL_UP_ATTACK_UNKNOWN = 84; //?
	public static final int LEVEL_UP_FISHING = 85;
	public static final int LEVEL_UP_MAGIC_UNKNOWN = 86; //?
	public static final int LEVEL_UP_MAGIC = 87;
	public static final int LEVEL_UP_DEFENCE = 88;
	public static final int LEVEL_UP_HERBLORE = 90;
	public static final int LEVEL_UP_ATTACK = 91;
	public static final int LEVEL_UP_WOODCUTTING = 92;
	public static final int LEVEL_UP_UNKNOWN7 = 94; //?
	public static final int LEVEL_UP_UNKNOWN8 = 95; //?
	public static final int LEVEL_UP_FLETCHING_UNKNOWN = 96; //?
	public static final int LEVEL_UP_FISHING_UNKNOWN = 98; //?
	public static final int LEVEL_UP_MINING = 99;
	public static final int LEVEL_UP_CRAFTING = 100;
	public static final int LEVEL_UP_UNKNOWN9 = 101; //?
	public static final int LEVEL_UP_MAGIC2 = 102;
	public static final int LEVEL_UP_UNKNOWN10 = 103;//?
	public static final int LEVEL_UP_UNKNOWN11 = 104;//?
	public static final int LEVEL_UP_UNKNOWN12 = 105;//?
	public static final int LEVEL_UP_STRENGTH = 107;
	public static final int LEVEL_UP_UNKNOWN13 = 108;//?
	public static final int LEVEL_UP_UNKNOWN14 = 109;//?

	/**
	 * Death Sound
	 */

	public static final int PLAYER_DEATH_SOUND = 75;

	/**
	 * Prayer
	 */

	// public static final int RECHARGE_PRAYER = 442;
	// public static final int PROTECT_PRAYER = 444;
	public static final int PROTECT_MELEE = 433;
	public static final int PROTECT_MAGIC = 438;
	public static final int PROTECT_RANGE = 444;
	public static final int PROT_MAGE = 438;
	public static final int NO_PRAY = 435;
	public static final int PRAYER_TO_LOW = 447;

	/**
	 * Magic
	 */

	public static final int MAGE_SPLASH = 193, MAGE_FAIL = 941;
	public static final int TELEBLOCK_CAST = 1185, TELEBLOCK_HIT = 1183;
	public static final int LOW_ALCHEMY = 224, HIGH_ALCHEMY = 223;
	public static final int ICE_BLITZ = 1110;
	public static final int TELEPORT = 202;
	public static final int BLOOD_RUSH = 984, BLOOD_BITZ = 985;
	public static final int ANCIENT_BLOOD = 986;
	public static final int BLOOD_RUSH_SPLASH = 991;
	public static final int WIND_STRIKE = 992;
	public static final int SUPERHEAT = 217, SUPERHEAT_FAIL = 218;
	public static final int BONES_TO_BANNAS = 227;

	/**
	 * Combat
	 */

	public static final int SHOOT_ARROW = 370;

	/**
	 * Click item
	 */

	public static final int FOOD_EAT = 317, DRINK = 334;
	public static final int ITEM_PICKUP = 356, ITEM_DROP = 376;

	/**
	 * Random Event
	 */

	public static final int EXPLODING_ROCK = 429, EXPLODING_ROCK_2 = 432;
	public static final int KISS_FROG = 652;

	/**
	 * Minigame
	 */

	public static final int DUEL_WON = 77, DUEL_LOST = 76;

	/**
	 * Objects
	 */

	public static final int DITCH = 2462;
	public static final int JUMPING_STONES = 455;
	public static final int PICKABLE = 358;
	public static final int SLASH_WEB = 237;
	public static final int OPEN_DOOR = 326;
	public static final int OPEN_GATE = 1328;

	/**
	 * Items
	 */

	public static final int EMPTY = 334;
}
