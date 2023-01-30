package com.rs2.game.content.consumables;

import java.util.HashMap;

import com.rs2.GameConstants;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.items.impl.RareProtection;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

public class Food {

	public static enum FoodToEat {
		Easter_Egg(1961, 12, "Easter Egg", 0, "Food", false), 
		Pumpkin(1959, 14, "Pumpkin", 0, "Food", false), 
		Half_Jug_of_Wine(1989, 7, "Half Full Wine Jug", 1935, "Drink", false), 
		CUP_OF_TEA(1978, 2 + Misc.random(1), "Cup of Tea", 1980, "Drink", true), 
		CUP_OF_TEA2(712, 2 + Misc.random(1), "Cup of Tea", 1980, "Drink", true), 
		LEMON(2102, 2,"Lemon", 0, "Food", false), LIME(2120, 2, "Lime", 0, "Food", false), 
		PINEAPPLE(2114, 2, "Pineapple", 0, "Food", false), 
		CHOCOLATE_BAR(1973, 2, "Chocolate Bar", 0, "Food", false), 
		Wine(1993, 11, "Wine", 1935, "Drink", false), 
		MACKEREL(355, 6, "Mackerel", 0, "Food", false),
		MANTA(391, 22, "Manta Ray", 0, "Food", false), 
		SHARK(385, 20, "Shark", 0, "Food", false), 
		LOBSTER(379, 12, "Lobster", 0, "Food", false), 
		KARAMBWAN(3144, 18, "Karambwan", 0, "Food", false), 
		TROUT(333, 7, "Trout", 0, "Food", false), 
		SALMON(329, 9, "Salmon", 0, "Food", false), 
		SWORDFISH(373, 14, "Swordfish", 0, "Food", false), 
		TUNA(361, 10, "Tuna", 0, "Food", false), 
		MONKFISH(7946, 16, "Monkfish", 0, "Food", false), 
		SEA_TURTLE(397, 21, "Sea Turtle", 0, "Food", false), 
		CABBAGE(1965, 1, "Cabbage", 0, "Food", false), 
		CABBAGE_SOUTH_OF_FALADOR(1967, 1, "Cabbage", 0, "Food", false), 
		SPINACH(1969, 2, "Spinach Roll", 0, "Food", false), 
		CAKE(1891, 4, "Cake", 1893, "Food", false), 
		CAKE2(1893, 4, "2/3 Cake", 1895, "Food", false), 
		SLICE_OF_CAKE(1895, 4, "2/3 Cake", 0, "Food", false), 
		BASS(365, 13, "Bass", 0, "Food", false), 
		COD(339, 7, "Cod", 0, "Food", false), 
		POTATO(1942, 1, "Potato", 0, "Food", false), 
		BAKED_POTATO(6701, 4, "Baked Potato", 0, "Food", false), 
		POTATO_WITH_CHEESE(6705, 16, "Potato with Cheese", 0, "Food", false), 
		EGG_POTATO(7056, 16, "Egg Potato", 0, "Food", false), 
		CHILLI_POTATO(7054, 14, "Chilli Potato", 0, "Food", false), 
		MUSHROOM_POTATO(7058, 20, "Mushroom Potato", 0, "Food", false), 
		TUNA_POTATO(7060, 22, "Tuna Potato", 0, "Food", false), 
		SHRIMPS(315, 3, "Shrimps", 0, "Food", false), 
		HERRING(347, 5, "Herring", 0, "Food", false), 
		SARDINE(325, 4, "Sardine", 0, "Food", false), 
		CHOCOLATE_CAKE(1897, 5, "Chocolate Cake", 1899, "Food", false), 
		HALF_CHOCOLATE_CAKE(1899, 5, "2/3 Chocolate Cake", 1901, "Food", false), 
		CHOCOLATE_SLICE(1901, 5, "Chocolate Slice", 0, "Food", false), 
		ANCHOVIES(319, 2, "Anchovies", 0, "Food", false), 
		PLAIN_PIZZA(2289, 7, "Plain Pizza", 2291, "Food", false), 
		HALF_PLAIN_PIZZA(2291, 7, "1/2 Plain pizza", 0, "Food", false), 
		MEAT_PIZZA(2293, 8, "Meat Pizza", 2295, "Food", false), 
		CHICKEN(2140, 3, "Chicken", 0, "Food", false), 
		MEAT(2142, 2, "Meat", 0, "Food", false), 
		HALF_MEAT_PIZZA(2295, 8, "1/2 Meat Pizza", 0, "Food", false), 
		ANCHOVY_PIZZA(2297, 9, "Anchovy Pizza", 2299, "Food", false), 
		HALF_ANCHOVY_PIZZA(2299, 9, "1/2 Anchovy Pizza", 0, "Food", false), 
		PINEAPPLE_PIZZA(2301, 11, "Pineapple Pizza", 2303, "Food", false), 
		HALF_PINEAPPLE_PIZZA(2303, 11, "1/2 Pineapple Pizza", 0, "Food", false), 
		BREAD(2309, 5, "Bread", 0, "Food", false), 
		APPLE_PIE(2323, 7, "Apple Pie", 2335, "Food", false), 
		HALF_APPLE_PIE(2335, 7, "Half Apple Pie", 2313, "Food", false), 
		REDBERRY_PIE(2325, 5, "Redberry Pie", 2333, "Food", false), 
		HALF_REDBERRY_PIE(2333, 5, "Half Redberry Pie", 2313, "Food", false), 
		Ugthanki_kebab(1883, 2, "Ugthanki kebab", 0, "Food", false),
		SEAWEED(403, 4, "Edible Seaweed", 0, "Food", false),
		MEAT_PIE(2327, 6, "Meat Pie", 2331, "Food", false), 
		HALF_MEAT_PIE(2331, 6, "Half Meat Pie", 2313, "Food", false), 
		SUMMER_PIE(7218, 11, "Summer Pie", 7220, "Food", false), 
		HALF_SUMMER_PIE(7220, 11, "Half Summer Pie", 2313, "Food", false), 
		PIKE(351, 8, "Pike", 0, "Food", false), 
		POTATO_WITH_BUTTER(6703, 14, "Potato with Butter", 0, "Food", false), 
		SLICED_BANANA(3162, 2, "Sliced Banana", 0, "Food", false),
		BANANA(1963, 2, "Banana", 0, "Food", false), 
		PEACH(6883, 8, "Peach", 0, "Food", false), 
		ORANGE(2108, 2, "Orange", 0, "Food", false), 
		PINEAPPLE_RINGS(2118, 2, "Pineapple Rings", 0, "Food", false), 
		PINEAPPLE_CHUNKS(2116, 2, "Pineapple Chunks", 0, "Food", false), 
		EASTER_EGG(7928, 1, "Easter Egg", 0, "Food", false), 
		EASTER_EGG2(7929, 1, "Easter Egg", 0, "Food", false), 
		EASTER_EGG3(7930, 1, "Easter Egg", 0, "Food", false), 
		EASTER_EGG4(7931, 1, "Easter Egg", 0, "Food", false), 
		EASTER_EGG5(7932, 1, "Easter Egg", 0, "Food", false), 
		EASTER_EGG6(7933, 1, "Easter Egg", 0, "Food", false), 
		PURPLE_SWEETS(10476, 9, "Purple Sweets", 0, "Food", false), 
		POT_OF_CREAM(2130, 1, "Pot of cream", 0, "Food", false), 
		FILED_RATION(7934, 9, "Field Ration", 0, "Food", false),
		STEW(2003, 11, "Stew", 1923, "Food", false), 
		CURRY(2011, 19, "Curry", 1923, "Drink", false),
		SPICY_SAUCE(7072, 2, "Spicy sauce", 1923, "Food", false),
		CHILLI_CON_CARNE(7062, 5, "Chilli con carne", 1923, "Food", false),
		SCRAMBLED_EGG(7078, 5, "Scrambled egg", 1923, "Food", false),
		EGG_AND_TOMATO(7064, 8, "Egg and tomato", 1923, "Food", false),
		FRIED_ONIONS(7085, 5, "Fried onions", 1923, "Food", false),
		FRIED_MUSHROOMS(7082, 5, "Fried mushrooms", 1923, "Food", false),
		MUSHROOM_AND_ONION(7066, 11, "Mushroom and onion", 1923, "Food", false),
		TUNA_AND_CORN(7068, 13, "Tuna and corn", 1923, "Food", false),
		BANANA_STEW(4016, 11, "Banana stew", 1923, "Food", false),
		TOAD_CRUNCHIES(2217, 8, "Toad crunchies", 0, "Food", false),
		SPICY_CRUNCHIES(2213, 7, "Spicy crunchies", 0, "Food", false),
		WORM_CRUNCHIES(2205, 8, "Worm crunchies", 0, "Food", false),
		CHOCCHIP_CRUNCHIES(2209, 7, "Chocchip crunchies", 0, "Food", false),
		FRUIT_BATTA(2277, 11, "Fruit batta", 0, "Food", false),
		TOAD_BATTA(2255, 11, "Toad batta", 0, "Food", false),
		WORM_BATTA(2253, 11, "Worm batta", 0, "Food", false),
		VEGETABLE_BATTA(2281, 11, "Vegetable batta", 0, "Food", false),
		CHEESE_TOMATO_BATTA(2259, 11, "Cheese+tom batta", 0, "Food", false),
		WORM_HOLE(2191, 12, "Worm hole", 0, "Food", false),
		VEG_BALL(2195, 12, "Veg ball", 0, "Food", false),
		TANGLED_TOADS_LEGS(2187, 15, "Tangled toad's legs", 0, "Food", false),
		CHOCOLATE_BOMB(2185, 15, "Chocolate bomb", 0, "Food", false),
		PRE_TOAD_CRUNCH(2243, 7, "Premade t'd crunch", 0, "Food", false),
		PRE_SPICE_CRUNCH(2241, 7, "Premade s'y crunch", 0, "Food", false),
		PRE_WORM_CRUNCH(2237, 8, "Premade w'm crun'", 0, "Food", false),
		PRE_CHOC_CRUNCH(2239, 7, "Premade ch' crunch", 0, "Food", false),
		PRE_FRUIT_BATTA(2225, 11, "Premade fr't batta", 0, "Food", false),
		PRE_TOAD_BATTA(2221, 11, "Premade t'd batta", 0, "Food", false),
		PRE_WORM_BATTA(2219, 11, "Premade w'm batta", 0, "Food", false),
		PRE_VEG_BATTA(2227, 11, "Premade veg batta", 0, "Food", false),
		PRE_CHEESETOM_BATTA(2223, 11, "Premade c+t batta", 0, "Food", false),
		PRE_WORM_HOLE(2233, 12, "Premade worm hole", 0, "Food", false),
		PRE_VEG_BALL(2235, 12, "Premade veg ball", 0, "Food", false),
		PRE_TOADS_LEGS(2231, 15, "Premade ttl", 0, "Food", false),
		PRE_CHOC_BOMB(2229, 15, "Premade choc bomb", 0, "Food", false),
		FRUIT_BLAST(2084, 9, "Fruit blast", 2026, "Drink", false),
		PINE_PUNCH(2048, 9, "Pineapple punch", 2026, "Drink", false),
		PRE_FRUIT_BLAST(2034, 9, "Premade fr' blast", 2026, "Drink", false),
		PRE_PINE_PUNCH(2036, 9, "Premade p' punch", 2026, "Drink", false),
		RABBIT(3228, 5, "Cooked rabbit", 0, "Food", false),
		UGTHANKI_MEAT(1861, 3, "Ugthanki meat", 0, "Food", false),
		ROAST_BIRD_MEAT(9980, 6, "Roast bird meat", 0, "Food", false),
		THIN_SNAIL(3369, 5, "Thin snail meat", 0, "Food", false),
		SPIDER_ON_STICK(6297, 7, "Spider on stick", 0, "Food", false),
		SPIDER_ON_SHAFT(6299, 7, "Spider on shaft", 0, "Food", false),
		ROAST_RABBIT(7223, 7, "Roast rabbit", 0, "Food", false),
		LEAN_SNAIL(3371, 8, "Lean snail meat", 0, "Food", false),
		ROAST_BEAST_MEAT(9988, 8, "Roast beast meat", 0, "Food", false),
		FAT_SNAIL(3373, 9, "Fat snail meat", 0, "Food", false),
		SLIMY_EEL(3381, 8, "Cooked slimy eel", 0, "Food", false),
		COOKED_CHOMPY(2878, 10, "Cooked chompy", 0, "Food", false),
		COOKED_FISHCAKE(7530, 11, "Cooked fishcake", 0, "Food", false),
		RAINBOW_FISH(10136, 11, "Rainbow fish", 0, "Food", false),
		CAVE_EEL(5003, 9, "Cave eel", 0, "Food", false),
		COOKED_JUBBLY(7568, 15, "Cooked jubbly", 0, "Food", false),
		LAVA_EEL(2149, 11, "Lava eel", 0, "Food", false),
		CHOCOLATEY_MILK(1977, 4, "Chocolatey milk", 1925, "Drink", false),
		BANDAGES(4049, 3, "Bandages", 0, "Food", false),
		TOMATO(1982, 2, "Tomato", 0, "Food", false),
        CHEESE(1985, 2, "Cheese", 0, "Food", false);                

		private int id;
		private int heal;
		private String name;
		private int replace;
		private String type;
		private boolean foodEffect;

		private FoodToEat(int id, int heal, String name, int replaceWith,
				String type, boolean foodEffect) {
			this.id = id;
			this.heal = heal;
			this.name = name;
			replace = replaceWith;
			this.type = type;
			this.foodEffect = foodEffect;
		}

		private boolean hasEffect() {
			return foodEffect;
		}

		private int getId() {
			return id;
		}

		private String getType() {
			return type;
		}

		private int getHeal() {
			return heal;
		}

		public String getName() {
			return name;
		}

		public int replaceWith() {
			return replace;
		}

		public static HashMap<Integer, FoodToEat> food = new HashMap<Integer, FoodToEat>();

		static {
			for (FoodToEat f : FoodToEat.values()) {
				food.put(f.getId(), f);
			}
		}
	}
	
	public int random(int r) {
		return Misc.random(r);
	}

	public static void eat(Player player, int id, int slot) {
		if (player.isDead || player.playerLevel[GameConstants.HITPOINTS] <= 0) {
			return;
		}
		if (player.duelRule[6]) {
			player.getPacketSender().sendMessage("You may not eat in this duel.");
			return;
		}
		if (!RareProtection.eatDupedItem(player, id)) {
			return;
		}
		if (System.currentTimeMillis() - player.foodDelay >= 1800 && player.playerLevel[GameConstants.HITPOINTS] > 0) {
			player.getCombatAssistant().resetPlayerAttack();
			player.attackTimer += 2;
			player.startAnimation(829);
			player.getItemAssistant().deleteItem(id, slot, 1);
			FoodToEat f = FoodToEat.food.get(id);
			if (f.hasEffect()) {
				foodEffect(player, id);
			}
			if (f.replaceWith() > 0) {
				player.getItemAssistant().addItem(f.replaceWith(), 1);
			}
			if (f.getType().equalsIgnoreCase("Food")) {
				if (id == 1965) {
					player.getPacketSender().sendMessage("You eat the cabbage. Yuck!");
				} else if (id == 1967){
					player.getPacketSender().sendMessage("You eat the cabbage. It seems to taste nicer than normal.");
				} else if (id == 2185){
					player.getPacketSender().sendMessage("You pour over an obscene amount of cream and dust with chocolate dust. Mmmm.");					
				} else {
					player.getPacketSender().sendMessage("You eat the " + f.getName() + ".");
					player.getPacketSender().sendMessage("It heals some health.");
				}
			} else if (f.getType().equalsIgnoreCase("Drink")) {
				if (id == 2955) {
					player.getPacketSender().sendMessage("It tastes like something just died in your mouth.");
				} else {
					player.getPacketSender().sendMessage("You drink the " + f.getName() + ".");
				}
			}

			if (f.getType().equalsIgnoreCase("Food")) {
				player.getPacketSender().sendSound(SoundList.FOOD_EAT, 100, 0);
			} else if (f.getType().equalsIgnoreCase("Drink")) {
				player.getPacketSender().sendSound(SoundList.DRINK, 100, 0);
			}
			player.foodDelay = System.currentTimeMillis();
			if (player.playerLevel[GameConstants.HITPOINTS] < player.getLevelForXP(player.playerXP[GameConstants.HITPOINTS])) {
				player.playerLevel[GameConstants.HITPOINTS] += f.getHeal();
				player.getPacketSender().sendMessage("It heals some health.");
				if (player.playerLevel[GameConstants.HITPOINTS] > player.getLevelForXP(player.playerXP[GameConstants.HITPOINTS])) {
					player.playerLevel[GameConstants.HITPOINTS] = player.getLevelForXP(player.playerXP[GameConstants.HITPOINTS]);
				}
			}
			player.getPlayerAssistant().refreshSkill(GameConstants.HITPOINTS);
		}
	}

	public static void foodEffect(Player player, int id) {
		switch (id) {
		case 1978:
		case 712:
			player.forcedChat("Aaah, nothing like a nice cuppa tea!");
			break;
		case 1907:
			if (player.playerLevel[GameConstants.MAGIC] < 50) {
				player.playerLevel[GameConstants.MAGIC] = player.getPlayerAssistant().getLevelForXP(player.playerXP[GameConstants.MAGIC]) + 2;
			} else {
				player.playerLevel[GameConstants.MAGIC] = player.getPlayerAssistant().getLevelForXP(player.playerXP[GameConstants.MAGIC]) + 3;
			}
			if (player.playerLevel[GameConstants.STRENGTH] < 4) {
				player.playerLevel[GameConstants.STRENGTH] = 1;
			}
			if (player.playerLevel[GameConstants.ATTACK] < 5) {
				player.playerLevel[GameConstants.ATTACK] = 1;
			} else {
				player.playerLevel[GameConstants.ATTACK] = player.getPlayerAssistant().getLevelForXP(player.playerXP[GameConstants.ATTACK]) - 4;
			}
			if (player.playerLevel[GameConstants.DEFENCE] < 4) {
				player.playerLevel[GameConstants.DEFENCE] = 1;
			} else {
				player.playerLevel[GameConstants.DEFENCE] = player.getPlayerAssistant().getLevelForXP(player.playerXP[GameConstants.DEFENCE]) - 3;
			}
			if (player.playerLevel[GameConstants.STRENGTH] < 4) {
				player.playerLevel[GameConstants.STRENGTH] = 1;
			} else {
				player.playerLevel[GameConstants.STRENGTH] = player.getPlayerAssistant().getLevelForXP(player.playerXP[GameConstants.STRENGTH]) - 3;
			}
			player.getPlayerAssistant().refreshSkill(0);
			player.getPlayerAssistant().refreshSkill(GameConstants.DEFENCE);
			player.getPlayerAssistant().refreshSkill(GameConstants.STRENGTH);
			player.getPlayerAssistant().refreshSkill(GameConstants.MAGIC);
			break;
		}
	}

	public static boolean isFood(int id) {
		return FoodToEat.food.containsKey(id);
	}
}