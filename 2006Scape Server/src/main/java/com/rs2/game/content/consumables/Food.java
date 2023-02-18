package com.rs2.game.content.consumables;

import java.util.HashMap;

import org.apollo.cache.def.ItemDefinition;

import com.rs2.Constants;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.items.impl.RareProtection;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

import static com.rs2.game.content.StaticItemList.*;
import static com.rs2.game.content.consumables.Food.FoodType.*;

public class Food {

	public enum FoodType { FOOD, DRINK };
	
	public enum FoodToEat {
		Easter_Egg_(EASTER_EGG, 12, NULL_0, FOOD, false),
		Pumpkin_(PUMPKIN, 14, NULL_0, FOOD, false),
		Half_Jug_of_Wine_(HALF_FULL_WINE_JUG, 7, JUG, DRINK, false),
		CUP_OF_TEA_(CUP_OF_TEA_1978, 2, EMPTY_CUP, DRINK, true),
		CUP_OF_TEA2_(CUP_OF_TEA, 2, EMPTY_CUP, DRINK, true),
		LEMON_(LEMON, 2, NULL_0, FOOD, false),
		LIME_(LIME, 2, NULL_0, FOOD, false),
		PINEAPPLE_(PINEAPPLE, 2, NULL_0, FOOD, false),
		CHOCOLATE_BAR_(CHOCOLATE_BAR, 2, NULL_0, FOOD, false),
		Wine_(JUG_OF_WINE, 11, JUG, DRINK, false),
		MACKEREL_(MACKEREL, 6, NULL_0, FOOD, false),
		MANTA_(MANTA_RAY, 22, NULL_0, FOOD, false),
		SHARK_(SHARK, 20, NULL_0, FOOD, false),
		LOBSTER_(LOBSTER, 12, NULL_0, FOOD, false),
		KARAMBWAN_(COOKED_KARAMBWAN, 18, NULL_0, FOOD, false),
		TROUT_(TROUT, 7, NULL_0, FOOD, false),
		SALMON_(SALMON, 9, NULL_0, FOOD, false),
		SWORDFISH_(SWORDFISH, 14, NULL_0, FOOD, false),
		TUNA_(TUNA, 10, NULL_0, FOOD, false),
		MONKFISH_(MONKFISH, 16, NULL_0, FOOD, false),
		SEA_TURTLE_(SEA_TURTLE, 21, NULL_0, FOOD, false),
		CABBAGE_(CABBAGE, 1, NULL_0, FOOD, false),
		CABBAGE_SOUTH_OF_FALADOR_(CABBAGE_1967, 1, NULL_0, FOOD, false),
		SPINACH_(SPINACH_ROLL, 2, NULL_0, FOOD, false),
		CAKE_(CAKE, 4, _23_CAKE, FOOD, false),
		CAKE2_(_23_CAKE, 4, SLICE_OF_CAKE, FOOD, false),
		SLICE_OF_CAKE_(SLICE_OF_CAKE, 4, NULL_0, FOOD, false),
		BASS_(BASS, 13, NULL_0, FOOD, false),
		COD_(COD, 7, NULL_0, FOOD, false),
		POTATO_(POTATO, 1, NULL_0, FOOD, false),
		BAKED_POTATO_(BAKED_POTATO, 4, NULL_0, FOOD, false),
		POTATO_WITH_CHEESE_(POTATO_WITH_CHEESE, 16, NULL_0, FOOD, false),
		EGG_POTATO_(EGG_POTATO, 16, NULL_0, FOOD, false),
		CHILLI_POTATO_(CHILLI_POTATO, 14, NULL_0, FOOD, false),
		MUSHROOM_POTATO_(MUSHROOM_POTATO, 20, NULL_0, FOOD, false),
		TUNA_POTATO_(TUNA_POTATO, 22, NULL_0, FOOD, false),
		SHRIMPS_(SHRIMPS, 3, NULL_0, FOOD, false),
		HERRING_(HERRING, 5, NULL_0, FOOD, false),
		SARDINE_(SARDINE, 4, NULL_0, FOOD, false),
		CHOCOLATE_CAKE_(CHOCOLATE_CAKE, 5, _23_CHOCOLATE_CAKE, FOOD, false),
		HALF_CHOCOLATE_CAKE_(_23_CHOCOLATE_CAKE, 5, CHOCOLATE_SLICE, FOOD, false),
		CHOCOLATE_SLICE_(CHOCOLATE_SLICE, 5, NULL_0, FOOD, false),
		ANCHOVIES_(ANCHOVIES, 2, NULL_0, FOOD, false),
		PLAIN_PIZZA_(PLAIN_PIZZA, 7, _12_PLAIN_PIZZA, FOOD, false),
		HALF_PLAIN_PIZZA_(_12_PLAIN_PIZZA, 7, NULL_0, FOOD, false),
		MEAT_PIZZA_(MEAT_PIZZA, 8, _12_MEAT_PIZZA, FOOD, false),
		CHICKEN_(COOKED_CHICKEN, 3, NULL_0, FOOD, false),
		MEAT_(COOKED_MEAT, 2, NULL_0, FOOD, false),
		HALF_MEAT_PIZZA_(_12_MEAT_PIZZA, 8, NULL_0, FOOD, false),
		ANCHOVY_PIZZA_(ANCHOVY_PIZZA, 9, _12_ANCHOVY_PIZZA, FOOD, false),
		HALF_ANCHOVY_PIZZA_(_12_ANCHOVY_PIZZA, 9, NULL_0, FOOD, false),
		PINEAPPLE_PIZZA_(PINEAPPLE_PIZZA, 11, _12PINEAPPLE_PIZZA, FOOD, false),
		HALF_PINEAPPLE_PIZZA_(_12PINEAPPLE_PIZZA, 11, NULL_0, FOOD, false),
		BREAD_(BREAD, 5, NULL_0, FOOD, false),
		APPLE_PIE_(APPLE_PIE, 7, HALF_AN_APPLE_PIE, FOOD, false),
		HALF_APPLE_PIE_(HALF_AN_APPLE_PIE, 7, PIE_DISH, FOOD, false),
		REDBERRY_PIE_(REDBERRY_PIE, 5, HALF_A_REDBERRY_PIE, FOOD, false),
		HALF_REDBERRY_PIE_(HALF_A_REDBERRY_PIE, 5, PIE_DISH, FOOD, false),
		Ugthanki_kebab_(UGTHANKI_KEBAB, 2, NULL_0, FOOD, false),
		SEAWEED_(EDIBLE_SEAWEED, 4, NULL_0, FOOD, false),
		MEAT_PIE_(MEAT_PIE, 6, HALF_A_MEAT_PIE, FOOD, false),
		HALF_MEAT_PIE_(HALF_A_MEAT_PIE, 6, PIE_DISH, FOOD, false),
		SUMMER_PIE_(SUMMER_PIE, 11, HALF_A_SUMMER_PIE, FOOD, false),
		HALF_SUMMER_PIE_(HALF_A_SUMMER_PIE, 11, PIE_DISH, FOOD, false),
		PIKE_(PIKE, 8, NULL_0, FOOD, false),
		POTATO_WITH_BUTTER_(POTATO_WITH_BUTTER, 14, NULL_0, FOOD, false),
		SLICED_BANANA_(SLICED_BANANA, 2, NULL_0, FOOD, false),
		BANANA_(BANANA, 2, NULL_0, FOOD, false),
		PEACH_(PEACH, 8, NULL_0, FOOD, false),
		ORANGE_(ORANGE, 2, NULL_0, FOOD, false),
		PINEAPPLE_RINGS_(PINEAPPLE_RING, 2, NULL_0, FOOD, false),
		PINEAPPLE_CHUNKS_(PINEAPPLE_CHUNKS, 2, NULL_0, FOOD, false),
		EASTER_EGG_(EASTER_EGG_7928, 1, NULL_0, FOOD, false),
		EASTER_EGG2_(EASTER_EGG_7929, 1, NULL_0, FOOD, false),
		EASTER_EGG3_(EASTER_EGG_7930, 1, NULL_0, FOOD, false),
		EASTER_EGG4_(EASTER_EGG_7931, 1, NULL_0, FOOD, false),
		EASTER_EGG5_(EASTER_EGG_7932, 1, NULL_0, FOOD, false),
		EASTER_EGG6_(EASTER_EGG_7933, 1, NULL_0, FOOD, false),
		PURPLE_SWEETS_(10476, 9, NULL_0, FOOD, false),
		POT_OF_CREAM_(POT_OF_CREAM, 1, NULL_0, FOOD, false),
		FILED_RATION_(FIELD_RATION, 9, NULL_0, FOOD, false),
		STEW_(STEW, 11, BOWL, FOOD, false),
		CURRY_(CURRY, 19, BOWL, DRINK, false),
		SPICY_SAUCE_(SPICY_SAUCE, 2, BOWL, FOOD, false),
		CHILLI_CON_CARNE_(CHILLI_CON_CARNE, 5, BOWL, FOOD, false),
		SCRAMBLED_EGG_(SCRAMBLED_EGG, 5, BOWL, FOOD, false),
		EGG_AND_TOMATO_(EGG_AND_TOMATO, 8, BOWL, FOOD, false),
		FRIED_ONIONS_(FRIED_ONIONS_7085, 5, BOWL, FOOD, false),
		FRIED_MUSHROOMS_(FRIED_MUSHROOMS, 5, BOWL, FOOD, false),
		MUSHROOM_AND_ONION_(MUSHROOM__ONION, 11, BOWL, FOOD, false),
		TUNA_AND_CORN_(TUNA_AND_CORN, 13, BOWL, FOOD, false),
		BANANA_STEW_(BANANA_STEW, 11, BOWL, FOOD, false),
		TOAD_CRUNCHIES_(TOAD_CRUNCHIES, 8, NULL_0, FOOD, false),
		SPICY_CRUNCHIES_(SPICY_CRUNCHIES, 7, NULL_0, FOOD, false),
		WORM_CRUNCHIES_(WORM_CRUNCHIES, 8, NULL_0, FOOD, false),
		CHOCCHIP_CRUNCHIES_(CHOCCHIP_CRUNCHIES, 7, NULL_0, FOOD, false),
		FRUIT_BATTA_(FRUIT_BATTA_2277, 11, NULL_0, FOOD, false),
		TOAD_BATTA_(TOAD_BATTA_2255, 11, NULL_0, FOOD, false),
		WORM_BATTA_(WORM_BATTA_2253, 11, NULL_0, FOOD, false),
		VEGETABLE_BATTA_(VEGETABLE_BATTA_2281, 11, NULL_0, FOOD, false),
		CHEESE_TOMATO_BATTA_(CHEESETOM_BATTA_2259, 11, NULL_0, FOOD, false),
		WORM_HOLE_(WORM_HOLE, 12, NULL_0, FOOD, false),
		VEG_BALL_(VEG_BALL, 12, NULL_0, FOOD, false),
		TANGLED_TOADS_LEGS_(TANGLED_TOADS_LEGS, 15, NULL_0, FOOD, false),
		CHOCOLATE_BOMB_(CHOCOLATE_BOMB, 15, NULL_0, FOOD, false),
		PRE_TOAD_CRUNCH_(TOAD_CRUNCHIES_2243, 7, NULL_0, FOOD, false),
		PRE_SPICE_CRUNCH_(SPICY_CRUNCHIES_2241, 7, NULL_0, FOOD, false),
		PRE_WORM_CRUNCH_(WORM_CRUNCHIES_2237, 8, NULL_0, FOOD, false),
		PRE_CHOC_CRUNCH_(CHOCCHIP_CRUNCHIES_2239, 7, NULL_0, FOOD, false),
		PRE_FRUIT_BATTA_(FRUIT_BATTA, 11, NULL_0, FOOD, false),
		PRE_TOAD_BATTA_(TOAD_BATTA, 11, NULL_0, FOOD, false),
		PRE_WORM_BATTA_(WORM_BATTA, 11, NULL_0, FOOD, false),
		PRE_VEG_BATTA_(VEGETABLE_BATTA, 11, NULL_0, FOOD, false),
		PRE_CHEESETOM_BATTA_(CHEESETOM_BATTA, 11, NULL_0, FOOD, false),
		PRE_WORM_HOLE_(WORM_HOLE_2233, 12, NULL_0, FOOD, false),
		PRE_VEG_BALL_(VEG_BALL_2235, 12, NULL_0, FOOD, false),
		PRE_TOADS_LEGS_(TANGLED_TOADS_LEGS_2231, 15, NULL_0, FOOD, false),
		PRE_CHOC_BOMB_(CHOCOLATE_BOMB_2229, 15, NULL_0, FOOD, false),
		FRUIT_BLAST_(FRUIT_BLAST_2084, 9, COCKTAIL_GLASS, DRINK, false),
		PINE_PUNCH_(PINEAPPLE_PUNCH_2048, 9, COCKTAIL_GLASS, DRINK, false),
		PRE_FRUIT_BLAST_(FRUIT_BLAST, 9, COCKTAIL_GLASS, DRINK, false),
		PRE_PINE_PUNCH_(PINEAPPLE_PUNCH, 9, COCKTAIL_GLASS, DRINK, false),
		RABBIT_(COOKED_RABBIT, 5, NULL_0, FOOD, false),
		UGTHANKI_MEAT_(UGTHANKI_MEAT, 3, NULL_0, FOOD, false),
		ROAST_BIRD_MEAT_(9980, 6, NULL_0, FOOD, false),
		THIN_SNAIL_(THIN_SNAIL_MEAT, 5, NULL_0, FOOD, false),
		SPIDER_ON_STICK_(SPIDER_ON_STICK_6297, 7, NULL_0, FOOD, false),
		SPIDER_ON_SHAFT_(SPIDER_ON_SHAFT_6299, 7, NULL_0, FOOD, false),
		ROAST_RABBIT_(ROAST_RABBIT, 7, NULL_0, FOOD, false),
		LEAN_SNAIL_(LEAN_SNAIL_MEAT, 8, NULL_0, FOOD, false),
		ROAST_BEAST_MEAT_(9988, 8, NULL_0, FOOD, false),
		FAT_SNAIL_(FAT_SNAIL_MEAT, 9, NULL_0, FOOD, false),
		SLIMY_EEL_(COOKED_SLIMY_EEL, 8, NULL_0, FOOD, false),
		COOKED_CHOMPY_(COOKED_CHOMPY, 10, NULL_0, FOOD, false),
		COOKED_FISHCAKE_(COOKED_FISHCAKE, 11, NULL_0, FOOD, false),
		RAINBOW_FISH_(10136, 11, NULL_0, FOOD, false),
		CAVE_EEL_(CAVE_EEL, 9, NULL_0, FOOD, false),
		COOKED_JUBBLY_(COOKED_JUBBLY, 15, NULL_0, FOOD, false),
		LAVA_EEL_(LAVA_EEL, 11, NULL_0, FOOD, false),
		CHOCOLATEY_MILK_(CHOCOLATEY_MILK, 4, BUCKET, DRINK, false),
		BANDAGES_(BANDAGES, 3, NULL_0, FOOD, false),
		TOMATO_(TOMATO, 2, NULL_0, FOOD, false),
		CHEESE_(CHEESE, 2, NULL_0, FOOD, false);

		private int id;
		private int heal;
		private int replace;
		private FoodType type;
		private boolean foodEffect;

		private FoodToEat(int id, int heal, int replaceWith, FoodType type,
				boolean foodEffect) {
			this.id = id;
			this.heal = heal;
			this.replace = replaceWith;
			this.type = type;
			this.foodEffect = foodEffect;
		}

		private boolean hasEffect() {
			return foodEffect;
		}

		private int getId() {
			return id;
		}

		private FoodType getType() {
			return type;
		}

		private int getHeal() {
			return heal;
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
		if (player.isDead || player.playerLevel[Constants.HITPOINTS] <= 0) {
			return;
		}
		if (player.duelRule[6]) {
			player.getPacketSender().sendMessage("You may not eat in this duel.");
			return;
		}
		if (!RareProtection.eatDupedItem(player, id)) {
			return;
		}
		if (System.currentTimeMillis() - player.foodDelay >= 1800 && player.playerLevel[Constants.HITPOINTS] > 0) {
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
			if (f.getType() == FoodType.FOOD) {
				if (id == 1965) {
					player.getPacketSender().sendMessage("You eat the cabbage. Yuck!");
				} else if (id == 1967){
					player.getPacketSender().sendMessage("You eat the cabbage. It seems to taste nicer than normal.");
				} else if (id == 2185){
					player.getPacketSender().sendMessage("You pour over an obscene amount of cream and dust with chocolate dust. Mmmm.");					
				} else {
					player.getPacketSender().sendMessage("You eat the " + ItemDefinition.lookup(f.id).getName() + ".");
					player.getPacketSender().sendMessage("It heals some health.");
				}
			} else if (f.getType() == FoodType.DRINK) {
				if (id == 2955) {
					player.getPacketSender().sendMessage("It tastes like something just died in your mouth.");
				} else {
					player.getPacketSender().sendMessage("You drink the " + ItemDefinition.lookup(f.id).getName() + ".");
				}
			}

			if (f.getType() == FoodType.FOOD) {
				player.getPacketSender().sendSound(SoundList.FOOD_EAT, 100, 0);
			} else if (f.getType() == FoodType.DRINK) {
				player.getPacketSender().sendSound(SoundList.DRINK, 100, 0);
			}
			player.foodDelay = System.currentTimeMillis();
			if (player.playerLevel[Constants.HITPOINTS] < player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) {
				player.playerLevel[Constants.HITPOINTS] += f.getHeal();
				player.getPacketSender().sendMessage("It heals some health.");
				if (player.playerLevel[Constants.HITPOINTS] > player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) {
					player.playerLevel[Constants.HITPOINTS] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS]);
				}
			}
			player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
		}
	}

	public static void foodEffect(Player player, int id) {
		switch (id) {
		case 1978:
		case 712:
			player.forcedChat("Aaah, nothing like a nice cuppa tea!");
			break;
		case 1907:
			if (player.playerLevel[Constants.MAGIC] < 50) {
				player.playerLevel[Constants.MAGIC] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.MAGIC]) + 2;
			} else {
				player.playerLevel[Constants.MAGIC] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.MAGIC]) + 3;
			}
			if (player.playerLevel[Constants.STRENGTH] < 4) {
				player.playerLevel[Constants.STRENGTH] = 1;
			}
			if (player.playerLevel[Constants.ATTACK] < 5) {
				player.playerLevel[Constants.ATTACK] = 1;
			} else {
				player.playerLevel[Constants.ATTACK] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.ATTACK]) - 4;
			}
			if (player.playerLevel[Constants.DEFENCE] < 4) {
				player.playerLevel[Constants.DEFENCE] = 1;
			} else {
				player.playerLevel[Constants.DEFENCE] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.DEFENCE]) - 3;
			}
			if (player.playerLevel[Constants.STRENGTH] < 4) {
				player.playerLevel[Constants.STRENGTH] = 1;
			} else {
				player.playerLevel[Constants.STRENGTH] = player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.STRENGTH]) - 3;
			}
			player.getPlayerAssistant().refreshSkill(0);
			player.getPlayerAssistant().refreshSkill(Constants.DEFENCE);
			player.getPlayerAssistant().refreshSkill(Constants.STRENGTH);
			player.getPlayerAssistant().refreshSkill(Constants.MAGIC);
			break;
		}
	}

	public static boolean isFood(int id) {
		return FoodToEat.food.containsKey(id);
	}
}