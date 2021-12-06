package com.rs2;

import com.rs2.game.players.Player;

public class GameConstants {

	/**
	 * The Variables Below Can Be Also Changed On Server Startup By Using The ConfigLoader
	 *
	 * SERVER_NAME Sets The Name The Server Will Use
	 * WEBSITE_LINK Defines The Server Website Links
	 * WORLD Sets The Servers World ID
	 * MAX_PLAYERS Sets The Maximum Amount Of Players Allow To Be Logged In At Once
	 * TIMEOUT Sets The Amount Of Time Before A Player Timeouts From A Bad Connection
	 * SAVE_TIMER Sets In Seconds How Often The Server Shouls Auto-Save All Characters
	 * RESPAWN_X Sets The X Coordinate That You Will Respawn At After Death
	 * RESPAWN_Y Sets The Y Coordinate That You Will Respawn At After Death
	 * FILE_SERVER Sets Whether The FileServer Should Run With The Server
	 * SERVER_DEBUG Sets Whether The Server Should Start In Debug Mode
	 * MEMBERS_ONLY Sets Whether The World Is Members Only
	 * TUTORIAL_ISLAND Sets Enables/Disables Tutorial Island For Players On First Login
	 * PARTY_ROOM_DISABLED Enables/Disables The Party Room Should Be Disabled
	 * CLUES_ENABLED Enables/Disables Clue Scrolls
	 * ITEM_REQUIREMENTS Enables/Disables Item Requirements for All Players
	 * ADMIN_CAN_TRADE Defines Whether Admins Can Trade
	 * ADMIN_DROP_ITEMS Defines Whether Admins Can Drop Items
	 * ADMIN_CAN_SELL_ITEMS Defines Whether Admins Can Sell Items
	 * VARIABLE_XP_RATE Allows Players To Choose An XP Rate(x1,x2,x5,x10)
	 * XP_RATE Sets The XP Rate Multiplier For All Players/Skills
	 */
	public static String SERVER_NAME = "2006Scape", WEBSITE_LINK = "https://2006Scape.org";
	public static int WORLD = 1, MAX_PLAYERS = 200, TIMEOUT = 60, SAVE_TIMER = 120,
			RESPAWN_X = 3222, RESPAWN_Y = 3218;
	public static boolean FILE_SERVER = true, SERVER_DEBUG = false, MEMBERS_ONLY = false, TUTORIAL_ISLAND = false,
			PARTY_ROOM_DISABLED = false, CLUES_ENABLED = true, ITEM_REQUIREMENTS = true,
			ADMIN_CAN_TRADE = false, ADMIN_DROP_ITEMS = false, ADMIN_CAN_SELL_ITEMS = false, VARIABLE_XP_RATE = true;
	public static double XP_RATE = Player.xpRate;


	/**
	 * The Variables Below Should Only Be Changed If You Understand What You Are Doing
	 */

	public final static String SERVER_VERSION = "Server Stage v " + GameConstants.TEST_VERSION + ".";
	public final static boolean WEBSITE_TOTAL_CHARACTERS_INTEGRATION = false;
	public final static double TEST_VERSION = 2.3;
	
	public final static int ITEM_LIMIT = 15000, MAXITEM_AMOUNT = Integer.MAX_VALUE, CLIENT_VERSION = 999999,
			IPS_ALLOWED = 250, CONNECTION_DELAY = 100,
			MESSAGE_DELAY = 6000, REQ_AMOUNT = 150;
	
	public final static boolean sendServerPackets = false, SOUND = true, GUILDS = true,
			PRINT_OBJECT_ID = false, EXPERIMENTS = false;
	
	public static int[] SIDEBARS = { 2423, 3917, 638, 3213, 1644, 5608, 1151,
			18128, 5065, 5715, 2449, 904, 147, 962 };

	public final static int[] FUN_WEAPONS = { 2460, 2461, 2462, 2463, 2464,
			2465, 2466, 2467, 2468, 2469, 2470, 2471, 2471, 2473, 2474, 2475,
			2476, 2477 }; // fun weapons for dueling

	
	public final static int DUELING_RESPAWN_X = 3362;
	
	public final static int DUELING_RESPAWN_Y = 3263;
	
	public final static int NO_TELEPORT_WILD_LEVEL = 20;
	
	public final static int CASTLE_WARS_X = 2439;
	
	public final static int CASTLE_WARS_Y = 3087;
	
	public final static int NPC_RANDOM_WALK_DISTANCE = 5;
	
	public final static int NPC_FOLLOW_DISTANCE = 10;
	
	public final static String[] UNDEAD = {
		"armoured zombie", "ankous", "banshee", "crawling hand", "dried zombie", "ghost", "ghostly warrior", "ghast",
		"mummy", "mighty banshee", "reventant imp", "reventant goblin",  "reventant icefiend",  "reventant pyrefiend",
		"reventant hobgoblin",  "reventant vampyre",  "reventant werewolf", "reventant cyclops", "reventant darkbeast",
		"reventant demon", "reventant ork",  "reventant hellhound", "reventant knight", "reventant dragon",
		"shade", "skeleton", "skeleton brute", "skeleton thug", "skeleton warload", "summoned zombie",
		"skorge", "tortured soul", "undead chicken", "undead cow", "undead one", "undead troll", "zombie", "zombie rat", "zogre"
	};
	
	public final static int CYCLE_TIME = 600;
	
	public final static int BUFFER_SIZE = 10000;

	public final static int ATTACK = 0, DEFENCE = 1, STRENGTH = 2,
			HITPOINTS = 3, RANGED = 4, PRAYER = 5, MAGIC = 6, COOKING = 7,
			WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11,
			CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15,
			AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19,
			RUNECRAFTING = 20;
}
