package com.rs2.game.npcs.impl;

import com.rs2.GameEngine;
import com.rs2.game.npcs.Npc;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerSave;
import com.rs2.world.clip.Region;

public class Pets {
	
	public static final int RATS_NEEDED_TO_GROW = 10;

	//npc id, item id
	private final static int[][] CATS = { 
			{ 3504, 7583 }, //hell kitten
			{ 3506, 7584 }, //lazy hell kitten
			{ 766, 1560 }, //pet kitten
			{ 3507, 7585 }, //wily hellcat
			{ 765, 1559 }, //pet kitten
			{ 764, 1558 }, //pet kitten
			{ 763, 1557 }, //pet kitten
			{ 762, 1556 }, //pet kitten
			{ 761, 1555 }, //pet kitten
			{ 768, 1561 }, //pet cat
			{ 769, 1562 }, //pet cat
			{ 770, 1563 }, //pet cat
			{ 771, 1564 }, //pet cat
			{ 772, 1565 },//pet cat
			{ 773, 1566 } //pet cat
		};

	public static final int[] CAT_ITEMS = { 
			1555, 1556, 1557, 1558, 1559, 1560,
			1561, 1562, 1563, 1564, 1565, 7585, 7583, 7584 
		};
	
	public static boolean isCatItem(int itemId) {
		for (int i = 0; i < CAT_ITEMS.length; i++) {
			if (itemId == CAT_ITEMS[i]) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isCat(int npcId) {
		for (int i = 0; i < CATS.length; i++) {
			if (npcId == CATS[i][0]) {
				return true;
			}
		}
		return false;
	}


	public static void dropPet(Player player, int itemId, int slot) {
		if (player.hasNpc) {
			player.getPacketSender().sendMessage("You already dropped your " + NpcHandler.getNpcListName(summonItemId(itemId)) + ".");
			return;
		}
		player.getItemAssistant().deleteItem(itemId, slot, player.playerItemsN[slot]);
		player.hasNpc = true;
		player.getPacketSender().sendMessage("You drop your " + NpcHandler.getNpcListName(summonItemId(itemId)) + ".");
		int offsetX = 0;
		int offsetY = 0;
		if (Region.getClipping(player.getX() - 1, player.getY(), player.heightLevel, -1, 0)) {
			offsetX = -1;
		} else if (Region.getClipping(player.getX() + 1, player.getY(), player.heightLevel, 1, 0)) {
			offsetX = 1;
		} else if (Region.getClipping(player.getX(), player.getY() - 1, player.heightLevel, 0, -1)) {
			offsetY = -1;
		} else if (Region.getClipping(player.getX(), player.getY() + 1, player.heightLevel, 0, 1)) {
			offsetY = 1;
		}
		GameEngine.npcHandler.spawnNpc3(player, summonItemId(itemId), player.absX+offsetX, player.absY+offsetY, player.heightLevel, 0, 120, 25, 200, 200, false, false, true);			
		PlayerSave.saveGame(player);
	}
	
	public void quickPickup(Player player, int id) {
			for (Npc i : NpcHandler.npcs) {
				if (i == null) {
					continue;
				}
				if (i.npcType == id) {
					i.absX = 0;
					i.absY = 0;
					i = null;
				}
			}
	}


	public void pickUpPet(Player player, int id) {
		if (player.getItemAssistant().hasFreeSlots(1)) {
			for (Npc i : NpcHandler.npcs) {
				if (i == null) {
					continue;
				}
				if (i.npcType == id) {
					player.startAnimation(827);
					i.absX = 0;
					i.absY = 0;
					i = null;
					for (int[] element : CATS) {
						if (element[0] == id) {
							player.getItemAssistant().addItem(element[1], 1);
						}
					}
				}
			}
		} else {
			player.getPacketSender().sendMessage("You do not have enough space in your inventory to do that.");
		}
	}

	public static int summonItemId(int itemId) {
		for (int i = 0; i < CATS.length; i++) {
			if (itemId == CATS[i][1]) {
				return CATS[i][0];
			}
		}
		return 0;
	}
	
}
