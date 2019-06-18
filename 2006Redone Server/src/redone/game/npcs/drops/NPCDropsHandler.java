package redone.game.npcs.drops;

import redone.Server;
import redone.game.items.ItemList;
import redone.util.Misc;

/**
 * Npc Drops Handler
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class NPCDropsHandler {

	public static int // found on http://runescape.wikia.com/wiki/Drop_rate
			ALWAYS = 0,
			COINSRATE = 3, CHICKEN_RATE = 75 / 100,
			COMMON = 2 + r(48),
			UNCOMMON = 51 + r(49), RARE = 101 + r(411), VERY_RARE = 513;

	/**
	 * Handles the npc drops for the npc names.
	 * 
	 * @param NPCId
	 * @return
	 */
	public static final int[][] NPC_DROPS(String npc, int NPCId) {
		if (npc.equals("man") || npc.equals("woman") || npc.equals("drunken_man")) {
			return NPCDrops.man;
		} else if (npc.equals("skeletal_wyvern")) {
			return NPCDrops.wyvern;
		} else if (npc.equals("dark_beast")) {
			return NPCDrops.darkbeast;
		} else if (npc.equals("shade")) {
			return NPCDrops.shade;
		} else if (npc.equals("watchman")) {
			return NPCDrops.watchman;
		} else if (npc.equals("river_troll")) {
			return NPCDrops.rivertroll;
		} else if (npc.equals("cave_crawler")) {
			return NPCDrops.cavecrawler;
		} else if (npc.equals("thief")) {
			return NPCDrops.thief;
		} else if (npc.equals("tzhaar-xil") || npc.equals("Tzhaar-Xil")) {
			return NPCDrops.tzhaarxil;
		} else if (npc.equals("tzhaar-ket") || npc.equals("Tzhaar-Ket")) {
			return NPCDrops.tzhaarket;
		} else if (npc.equals("tzhaar-hur") || npc.equals("Tzhaar-Hur")) {
			return NPCDrops.tzhaarhur;
		} else if (npc.equals("tzhaar-mej") || npc.equals("Tzhaar-Mej")) {
			return NPCDrops.tzhaarmej;
		} else if (npc.equals("tree_spirit")) {
			return NPCDrops.treespirit;
		} else if (npc.equals("unicorn")) {
			return NPCDrops.unicorn;
		} else if (npc.equals("evil_chicken")) {
			return NPCDrops.evilchicken;
		} else if (npc.equals("white_knight")) {
			return NPCDrops.whiteknight;
		} else if (npc.equals("black_knight")) {
			return NPCDrops.blackknight;
		} else if (npc.equals("bear")) {
			return NPCDrops.bear;
		} else if (npc.equals("jogre")) {
			return NPCDrops.jogre;
		} else if (npc.equals("ogre")) {
			return NPCDrops.ogre;
		} else if (npc.equals("chaos_druid")) {
			return NPCDrops.chaosdruid;
		} else if (npc.equals("jailer")) {
			return NPCDrops.jailer;
		} else if (npc.equals("fire_giant") || npc.equals("Fire_giant")) {
			return NPCDrops.firegiant;
		} else if (npc.equals("basilisk")) {
			return NPCDrops.basilisk;
		} else if (npc.equals("baby_blue_dragon")
				|| npc.equals("baby_red_dragon") || npc.equals("baby_dragon")) {
			return NPCDrops.babybluedragon;
		} else if (npc.equals("red_dragon")) {
			return NPCDrops.reddragon;
		} else if (npc.equals("elf_warrior")) {
			return NPCDrops.elfwarrior;
		} else if (npc.equals("dagannoth")) {
			return NPCDrops.dagannoth;
		} else if (npc.equals("giant_mole")) {
			return NPCDrops.giantmole;
		} else if (npc.equals("dagannoth_supreme")) {
			return NPCDrops.dagannothsupereme;
		} else if (npc.equals("chaos_elemental")) {
			return NPCDrops.chaoselemental;
		} else if (npc.equals("dagannoth_prime")) {
			return NPCDrops.dagannothprime;
		} else if (npc.equals("dagannoth_rex")) {
			return NPCDrops.daggannothrex;
		} else if (npc.equals("monkey_guard")) {
			return NPCDrops.monkeyguard;
		} else if (npc.equals("monk")) {
			return NPCDrops.monk;
		} else if (npc.equals("abyssal_demon")) {
			return NPCDrops.abyssaldemon;
		} else if (npc.equals("pyrefiend")) {
			return NPCDrops.pyrefiend;
		} else if (npc.equals("aberrant_spectre")
				|| npc.equals("aberrant_specter")
				|| npc.equals("Aberant_specter")) {
			return NPCDrops.abberantspectre;
		} else if (npc.equals("earth_warrior")) {
			return NPCDrops.earthwarrior;
		} else if (npc.equals("gargoyle")) {
			return NPCDrops.gargoyle;
		} else if (npc.equals("dust_devil") || npc.equals("dustdevil")) {
			return NPCDrops.dustdevil;
		} else if (npc.equals("cockatrice")) {
			return NPCDrops.cockatrice;
		} else if (npc.equals("infernal_mage")) {
			return NPCDrops.infernalmage;
		} else if (npc.equals("nechryael")) {
			return NPCDrops.nechryael;
		} else if (npc.equals("bloodveld")) {
			return NPCDrops.bloodveld;
		} else if (npc.equals("turoth")) {
			return NPCDrops.turoth;
		} else if (npc.equals("banshee")) {
			return NPCDrops.banshee;
		} else if (npc.equals("crawling_hand")) {
			return NPCDrops.crawlinghand;
		} else if (npc.equals("highwayman")) {
			return NPCDrops.highwayman;
		} else if (npc.equals("wild_dog") || npc.equals("battle_mage")) {
			return NPCDrops.alwaysbones;
		} else if (npc.equals("kalphite_queen")) {
			return NPCDrops.kalphitequeen;
		} else if (npc.equals("kalphite_worker")) {
			return NPCDrops.kalphiteworker;
		} else if (npc.equals("kalphite_soldier")) {
			return NPCDrops.kalphitesolider;
		} else if (npc.equals("kalphite_guardian")) {
			return NPCDrops.kalphiteguardian;
		} else if (npc.equals("bat") || npc.equals("giant_bat")) {
			return NPCDrops.bat;
		} else if (npc.equals("bronze_dragon")) {
			return NPCDrops.bronzedragon;
		} else if (npc.equals("black_dragon")) {
			return NPCDrops.blackdragon;
		} else if (npc.equals("iron_dragon")) {
			return NPCDrops.irondragon;
		} else if (npc.equals("steel_dragon")) {
			return NPCDrops.steeldragon;
		} else if (npc.equals("moss_giant")) {
			return NPCDrops.mossgiant;
		} else if (npc.equals("greater_demon")) {
			return NPCDrops.greaterdemon;
		} else if (npc.equals("black_demon")) {
			return NPCDrops.blackdemon;
		} else if (npc.equals("dwarf")) {
			return NPCDrops.dwarf;
		} else if (npc.equals("jelly")) {
			return NPCDrops.jelly;
		} else if (npc.equals("rock_crab")) {
			return NPCDrops.rockcrab;
		} else if (npc.equals("rockslug")) {
			return NPCDrops.rockslug;
		} else if (npc.equals("king_black_dragon")) {
			return NPCDrops.kingblackdragon;
		} else if (npc.equals("green_dragon")) {
			return NPCDrops.greendragon;
		} else if (npc.equals("blue_dragon")) {
			return NPCDrops.bluedragon;
		} else if (npc.equals("goblin")) {
			return NPCDrops.goblin;
		} else if (npc.equals("lesser_demon") || npc.equals("Lesser_demon")
				|| npc.equals("lesserdemon")) {
			return NPCDrops.lesserdemon;
		} else if (npc.equals("guard") || npc.equals("jail_guard")) {
			return NPCDrops.guard;
		} else if (npc.equals("al-kharid_warrior")) {
			return NPCDrops.alkharidwarrior;
		} else if (npc.equals("ice_warrior")) {
			return NPCDrops.icewarrior;
		} else if (npc.equals("kurask")) {
			return NPCDrops.kurask;
		} else if (npc.equals("ice_giant")) {
			return NPCDrops.icegiant;
		} else if (npc.equals("hobgoblin")) {
			return NPCDrops.hobgoblin;
		} else if (npc.equals("pirate")) {
			return NPCDrops.pirate;
		} else if (npc.equals("zombie")) {
			return NPCDrops.zombie;
		} else if (npc.equals("skeleton")) {
			return NPCDrops.skeleton;
		} else if (npc.equals("deadly_red_spider")) {
			return NPCDrops.deadlyredspider;
		} else if (npc.equals("rat")) {
			return NPCDrops.rat;
		} else if (npc.equals("imp")) {
			return NPCDrops.imp;
		} else if (npc.equals("cow") || npc.equals("cow_calf")) {
			return NPCDrops.cow;
		} else if (npc.equals("chicken") || npc.equals("rooster")) {
			return NPCDrops.chicken;
		} else if (npc.equals("hill_giant")) {
			return NPCDrops.hillgiant;
		} else if (npc.equals("giant_rat")) {
			return NPCDrops.giantrat;
		} else if (npc.equals("dark_wizard")) {
			return NPCDrops.darkwizard;
		} else {
			return NPCDrops.DEFAULT;
		}
	}

	/**
	 * Gets the item name
	 * 
	 * @param ItemID
	 * @return
	 */
	public static int i(String ItemName) {
		return getItemId(ItemName);
	}

	/**
	 * Item name main method
	 * 
	 * @param itemName
	 * @return
	 */
	public static int getItemId(String itemName) {
		for (ItemList i : Server.itemHandler.ItemList) {
			if (i != null) {
				if (i.itemName.equalsIgnoreCase(itemName)) {
					return i.itemId;
				}
			}
		}
		return -1;
	}

	/**
	 * Misc.random in shorter form
	 * 
	 * @param random
	 * @return
	 */
	public static int r(int random) {
		return Misc.random(random);
	}

}
