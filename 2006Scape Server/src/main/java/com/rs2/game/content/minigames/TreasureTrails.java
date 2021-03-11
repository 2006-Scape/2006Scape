package com.rs2.game.content.minigames;

import com.rs2.game.players.Player;
import com.rs2.util.Misc;

public class TreasureTrails {

	public static int lowLevelReward[] = {
			// Weapons
			853, // Maple shortbow
			851, // Maple longbow
			1327, // Black scimitar
			1233, // Black Dagger (p)
			5682, // Black Dagger (p+)
			5700, // Black Dagger (p++)
			6611, // White scimitar
			6591, // White dagger
			6593, // White dagger(p)
			6595, // White dagger(p+)
			6597, // White dagger(p++)
			6599, // White halberd
			6603, // White magic staff
			// Armor
			6619, // White boots
			6615, // White chainbody
			6623, // White full helm
			6629, // White gloves
			6633, // White kiteshield
			6617, // White platebody
			6625, // White platelegs
			6627, // White plateskirt
			6631, // White sq shield
			2587, // Black full helm (t)
			2589, // Black kiteshield (t)
			2583, // Black platebody (t)
			2585, // Black platelegs (t)
			3472, // Black plateskirt (t)
			2595, // Black full helm (g)
			2597, // Black kiteshield (g)
			2591, // Black platebody (g)
			2593, // Black platelegs (g)
			3473, // Black plateskirt (g)
			7388, // Blue skirt (t)
			7396, // Blue wizard hat (t)
			7392, // Blue wizard robe (t)
			7386, // Blue skirt (g)
			7394, // Blue wizard hat (g)
			7390, // Blue wizard robe (g)
			7364, // Studded body (t)
			7368, // Studded chaps (t)
			7362, // Studded body (g)
			7366, // Studded chaps (g)
			// Jewelery
			1639, // Emerald ring
			1641, // Ruby ring
			1637, // Sapphire ring
			// Misc
			2524, // Black toy horsey
			2520, // Brown toy horsey
			2526, // Grey toy horsey
			2522, // White toy horsey
			6541, // Mouse toy
			7771, // Toy cat
			7767, // Toy mouse
			7763, // Toy doll
			3721, // Toy ship
			7759, // Toy soldier
			6856, // Bobble hat
			2978, // Chompy bird hat
			2979, // Chompy bird hat
			2980, // Chompy bird hat
			2981, // Chompy bird hat
			2982, // Chompy bird hat
			2983, // Chompy bird hat
			2984, // Chompy bird hat
			2985, // Chompy bird hat
			2986, // Chompy bird hat
			2987, // Chompy bird hat
			2988, // Chompy bird hat
			2989, // Chompy bird hat
			2990, // Chompy bird hat
			2991, // Chompy bird hat
			2992, // Chompy bird hat
			2993, // Chompy bird hat
			2994, // Chompy bird hat
			2995, // Chompy bird hat
			6345, // Villager hat
			6355, // Villager hat
			6365, // Villager hat
			6375, // Villager hat
			6862, // Woolly hat
			6860, // Tri-jester hat
			6858, // Jester hat
			6182, // Lederhosen hat
			6547, // Doctors hat
			6548, // Nurse hat
			6665, // Mudskipper hat
	};
	public static int mediumLevelReward[] = {
			// Weapons
			1329, // Mithril scimitar
			1331, // Adamant scimitar
			1397, // Air battlestaff
			1399, // Earth battlestaff
			1393, // Fire battlestaff
			3053, // Lava battlestaff
			6562, // Mud battlestaff
			1395, // Water battlestaff
			857, // Yew shortbow
			855, // Yew longbow
			// Armor
			2605, // Adamant full helm (t)
			2603, // Adamant kiteshield (t)
			2599, // Adamant platebody (t)
			2601, // Adamant platelegs (t)
			3474, // Adamant plateskirt (t)
			2613, // Adamant full helm (g)
			2611, // Adamant kiteshield (g)
			2607, // Adamant platebody (g)
			2609, // Adamant platelegs (g)
			3475, // Adamant plateskirt (g)
			7372, // Green d'hide body (t)
			7380, // Green d'hide chaps (t)
			7370, // Green d'hide body (g)
			7378, // Green d'hide chaps (g)
			// Jewelery
			2568, // Ring of forging
			2570, // Ring of life
			2550, // Ring of recoil
			1727, // Amulet of magic
			1725, // Amulet of strength
			// Misc
			2581, // Robin hood hat
			2577, // Ranger boots
			2651, // Pirate's hat
			2631, //Highwayman mask
	};
	public static int highLevelReward[] = {
			// Weapons
			1333, // Rune scimitar
			1229, // Rune dagger(p)
			5678, // Rune dagger(p+)
			5696, // Rune dagger(p++)
			1215, // Dragon dagger
			1231, // Dragon dagger(p)
			5680, // Dragon dagger(p+)
			5698, // Dragon dagger(p++)
			1249, // Dragon spear
			3176, // Dragon spear(kp)
			1263, // Dragon spear(p)
			5716, // Dragon spear(p+)
			5730, // Dragon spear(p++)
			1359, // Rune axe
			6739, // Dragon axe
			3054, // Mystic lava staff
			6563, // Mystic mud staff
			1403, // Mystic water staff
			1405, // Mystic air staff
			1407, // Mystic earth staff
			1401, // Mystic fire staff
			861, // Magic shortbow
			859, // Magic longbow
			// Armor
			3486, // Gilded full helm
			3488, // Gilded kiteshield
			3481, // Gilded platebody
			3483, // Gilded platelegs
			3485, // Gilded plateskirt
			2627, // Rune full helm (t)
			2629, // Rune kiteshield (t)
			2623, // Rune platebody (t)
			2625, // Rune platelegs (t)
			3477, // Rune plateskirt (t)
			2619, // Rune full helm (g)
			2621, // Rune kiteshield (g)
			2615, // Rune platebody (g)
			2617, // Rune platelegs (g)
			3476, // Rune plateskirt (g)
			7400, // Enchanted hat
			7398, // Enchanted robe
			7399, // Enchanted top
			6920, // Infinity boots
			6924, // Infinity bottoms
			6922, // Infinity gloves
			6918, // Infinity hat
			6916, // Infinity top
			4097, // Mystic boots
			4107, // Mystic boots (dark)
			4117, // Mystic boots (light)
			4095, // Mystic gloves
			4105, // Mystic gloves (dark)
			4115, // Mystic gloves (light)
			4089, // Mystic hat
			4099, // Mystic hat (dark)
			4109, // Mystic hat (light)
			4093, // Mystic robe bottom
			4103, // Mystic robe bottom (dark)
			4113, // Mystic robe bottom (light)
			4091, // Mystic robe top
			4101, // Mystic robe top (dark)
			4111, // Mystic robe top (light)
			7376, // Blue d'hide body (t)
			7384, // Blue d'hide chaps (t)
			7374, // Blue d'hide body (g)
			7382, // Blue d'hide chaps (g)
			2491, // Black d'hide vamb
			2497, // Black d'hide chaps
			2503, // Black d'hide body
			2513, // Dragon chainbody
			1149, // Dragon med helm
			4087, // Dragon platelegs
			4585, // Dragon plateskirt
			1187, // Dragon sq shield
			2414, // Zamorak cape
			2657, // Zamorak full helm
			2659, // Zamorak kiteshield
			2653, // Zamorak platebody
			2655, // Zamorak platelegs
			3478, // Zamorak plateskirt
			2413, // Guthix cape
			2673, // Guthix full helm
			2675, // Guthix kiteshield
			2669, // Guthix platebody
			2671, // Guthix platelegs
			3480, // Guthix plateskirt
			2412, // Saradomin cape
			2665, // Saradomin full helm
			2667, // Saradomin kiteshield
			2661, // Saradomin platebody
			2663, // Saradomin platelegs
			3479, // Saradomin plateskirt
			// Jewelery
			1631, // Uncut dragonstone
			1615, // Dragonstone
			1702, // Dragonstone amulet
			1645, // Dragonstone ring
			2572, // Ring of wealth
			2552, // Ring of dueling(8)
			6733, // Archers ring
			6737, // Berserker ring
			6040, // Amulet of nature
			1497, // Amulet of othanian
			1731, // Amulet of power
			6585, // Amulet of fury
			// Misc
			7927, // Easter ring
			1050, // Santa hat
			1042, // Blue partyhat
			1044, // Green partyhat
			1046, // Purple partyhat
			1038, // Red partyhat
			1048, // White partyhat
			1040, // Yellow partyhat
	};

	public static int lowLevelStacks[] = {
			995, // Coins
			380, // Lobster
			555, // Water rune
			558, // Mind rune
			556, // Air rune
			559, // Body rune
			557, // Earth rune
			554, // Fire rune
			884, // Iron arrow
			885, // Iron arrow(p)
			5617, // Iron arrow(p+)
			5623, // Iron arrow(p++)
	};
	public static int mediumLevelStacks[] = {
			995, // Coins
			374, // Swordfish
			890, // Adamant arrow
			891, // Adamant arrow(p)
			5620, // Adamant arrow(p+)
			5626, // Adamant arrow(p++)
			563, // Law rune
			561, // Nature rune
			562, // Chaos rune
			564, // Cosmic rune
	};
	public static int highLevelStacks[] = {
			995, // Coins
			386, // Shark
			892, // Rune arrow
			893, // Rune arrow(p)
			5621, // Rune arrow(p+)
			5627, // Rune arrow(p++)
			4697, // Smoke rune
			566, // Soul rune
			4694, // Steam rune
			4699, // Lava rune
			4695, // Mist rune
			4698, // Mud rune
			565, // Blood rune
			560, // Death rune
			4696, // Dust rune
			535, // Babydragon bones
			537, // Dragon bones
	};


	public static void addClueReward(Player player, int clueLevel) {
		int chanceReward = Misc.random(2);
		if (clueLevel == 0) {
			switch (chanceReward) {
			case 0:
				displayReward(player,
						Misc.randomArrayItem(lowLevelReward), 1,
						Misc.randomArrayItem(lowLevelReward), 1,
						Misc.randomArrayItem(lowLevelReward), 1,
						Misc.randomArrayItem(lowLevelStacks), Misc.random(50, 150)
				);
				break;
			case 1:
				displayReward(player,
						Misc.randomArrayItem(lowLevelReward), 1,
						Misc.randomArrayItem(lowLevelReward), 1,
						Misc.randomArrayItem(lowLevelStacks), Misc.random(50, 150)
				);
				break;
			case 2:
				displayReward(player,
						Misc.randomArrayItem(lowLevelReward), 1,
						Misc.randomArrayItem(lowLevelStacks), Misc.random(50, 150)
				);
				break;
			}
		} else if (clueLevel == 1) {
			switch (chanceReward) {
			case 0:
				displayReward(player,
						Misc.randomArrayItem(mediumLevelReward), 1,
						Misc.randomArrayItem(mediumLevelReward), 1,
						Misc.randomArrayItem(mediumLevelReward), 1,
						Misc.randomArrayItem(mediumLevelStacks), Misc.random(50, 150)
				);
				break;
			case 1:
				displayReward(player,
						Misc.randomArrayItem(mediumLevelReward), 1,
						Misc.randomArrayItem(mediumLevelReward), 1,
						Misc.randomArrayItem(mediumLevelStacks), Misc.random(50, 150)
				);
				break;
			case 2:
				displayReward(player,
						Misc.randomArrayItem(mediumLevelReward), 1,
						Misc.randomArrayItem(mediumLevelStacks), Misc.random(50, 150)
				);
				break;
			}
		} else if (clueLevel == 2) {
			switch (chanceReward) {
			case 0:
				displayReward(player,
						Misc.randomArrayItem(highLevelReward), 1,
						Misc.randomArrayItem(highLevelReward), 1,
						Misc.randomArrayItem(highLevelReward), 1,
						Misc.randomArrayItem(highLevelStacks), Misc.random(50, 150)
				);
				break;
			case 1:
				displayReward(player,
						Misc.randomArrayItem(highLevelReward), 1,
						Misc.randomArrayItem(highLevelReward), 1,
						Misc.randomArrayItem(highLevelStacks), Misc.random(50, 150)
				);
				break;
			case 2:
				displayReward(player,
						Misc.randomArrayItem(highLevelReward), 1,
						Misc.randomArrayItem(highLevelStacks), Misc.random(50, 150)
				);
				break;
			}
		}
	}
	public static void displayReward(Player c, int item, int amount) {
		displayReward(c, item, amount, -1, 1);
	}

	public static void displayReward(Player c, int item, int amount, int item2, int amount2) {
		displayReward(c, item, amount, item2, amount2, -1, 1);
	}

	public static void displayReward(Player c, int item, int amount, int item2, int amount2, int item3, int amount3) {
		displayReward(c, item, amount, item2, amount2, item3, amount3, -1, 1);
	}

	public static void displayReward(Player c, int item, int amount, int item2, int amount2, int item3, int amount3, int item4, int amount4) {
		int[] items = { item, item2, item3, item4 };
		int[] amounts = { amount, amount2, amount3, amount4 };
		c.outStream.createFrameVarSizeWord(53);
		c.outStream.writeWord(6963);
		c.outStream.writeWord(items.length);
		for (int i = 0; i < items.length; i++) {
			if (c.playerItemsN[i] > 254) {
				c.outStream.writeByte(255);
				c.outStream.writeDWord_v2(amounts[i]);
			} else {
				c.outStream.writeByte(amounts[i]);
			}
			if (items[i] > 0) {
				c.outStream.writeWordBigEndianA(items[i] + 1);
			} else {
				c.outStream.writeWordBigEndianA(0);
			}
		}
		c.outStream.endFrameVarSizeWord();
		c.flushOutStream();
		for (int i = 0; i < items.length; i++) {
			if (items[i] > 0) c.getItemAssistant().addOrDropItem(items[i], amounts[i]);
		}
		c.getPacketSender().showInterface(6960);
	}

}
