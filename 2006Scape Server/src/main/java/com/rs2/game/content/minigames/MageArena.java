/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

package com.rs2.game.content.minigames;

import com.rs2.game.items.ItemAssistant;
import com.rs2.game.players.Player;

/**
 * @author D
 */
public class MageArena {

	private final Player player;

	public MageArena(Player c) {
		this.player = c;
	}

	private final int[] shopItems = {
		6908, 6910, 6912, 6914, 6916, 6918,
		6920, 6922, 6924, 6889, 6926, 1391,
		4695, 4696, 4698, 4697, 4694, 4699,
		564, 561, 560, 563, 565, 6891
	};

	private final int[] shopItemsN = {
		100, 100, 100, 100, 100, 100,
		100, 100, 100, 100, 100, 100,
		100, 100, 100, 100, 100, 100,
		100, 100, 100, 100, 100, 100
	};

	private final String[] interfaceText = { "Magic Training Arena Shop",
			"Telekinetic Pizazz Points", "Enchantment Pizazz Points",
			"Graveyard Pizazz Points", "Alchemist Pizazz Points" };

	public void openShop() {

		for (int i = 0; i < shopItems.length; i++) {
			player.getPacketSender().sendUpdateItems(15948, shopItems, shopItemsN);
		}

		for (int i = 15950; i < interfaceText.length; i++) {
			player.getPacketSender().sendString(interfaceText[i - 15950], i);
		}

		player.getPacketSender().sendString(Integer.toString(player.telekineticPoints), 15955);
		player.getPacketSender().sendString(Integer.toString(player.enchantmentPoints), 15956);
		player.getPacketSender().sendString(Integer.toString(player.graveyardPoints), 15957);
		player.getPacketSender().sendString(Integer.toString(player.alchemyPoints), 15958);
		player.getPacketSender().showInterface(15944);
	}

	public int getTelVal(int itemId) {

		switch (itemId) {
		case 6922:
			return 175;
		case 6918:
			return 350;
		case 6916:
			return 400;
		case 6924:
			return 450;
		case 6920:
			return 120;
		case 1391:
			return 1;
		case 6908:
			return 30;
		case 6910:
			return 60;
		case 6912:
			return 60;
		case 6914:
			return 150;
		case 6889:
			return 500;
		case 6926:
			return 200;

		case 4695:
		case 4694:
		case 4696:
		case 4697:
		case 4698:
		case 4699:
			return 1;

		case 564:
		case 561:
			return 0;

		case 560:
		case 563:
		case 565:
			return 2;

		default:
			return 1;

		}
	}

	public int getAlchVal(int itemId) {

		switch (itemId) {
		case 6922:
			return 225;
		case 6918:
			return 400;
		case 6916:
			return 450;
		case 6924:
			return 500;
		case 6920:
			return 120;
		case 1391:
			return 2;
		case 6908:
			return 30;
		case 6910:
			return 60;
		case 6912:
			return 200;
		case 6914:
			return 240;
		case 6889:
			return 550;
		case 6926:
			return 300;

		case 4695:
		case 4694:
		case 4696:
		case 4697:
		case 4698:
		case 4699:
			return 1;

		case 564:
		case 561:
			return 1;

		case 560:
		case 563:
		case 565:
			return 2;

		default:
			return 1;

		}
	}

	public int getEnchVal(int itemId) {

		switch (itemId) {
		case 6922:
			return 1300;
		case 6918:
			return 3000;
		case 6916:
			return 4000;
		case 6924:
			return 5000;
		case 6920:
			return 1200;
		case 1391:
			return 20;
		case 6908:
			return 300;
		case 6910:
			return 600;
		case 6912:
			return 1500;
		case 6914:
			return 2400;
		case 6889:
			return 6000;
		case 6926:
			return 2000;

		case 4695:
		case 4694:
		case 4696:
		case 4697:
		case 4698:
		case 4699:
			return 15;

		case 564:
		case 561:
			return 5;

		case 560:
		case 563:
		case 565:
			return 25;

		default:
			return 1;

		}
	}

	public int getGraveValue(int itemId) {

		switch (itemId) {
		case 6922:
			return 175;
		case 6918:
			return 350;
		case 6916:
			return 400;
		case 6924:
			return 450;
		case 6920:
			return 120;
		case 1391:
			return 2;
		case 6908:
			return 30;
		case 6910:
			return 60;
		case 6912:
			return 150;
		case 6914:
			return 240;
		case 6889:
			return 500;
		case 6926:
			return 200;

		case 4695:
		case 4694:
		case 4696:
		case 4697:
		case 4698:
		case 4699:
			return 1;

		case 564:
		case 561:
			return 1;

		case 560:
		case 563:
		case 565:
			return 2;

		default:
			return 1;

		}
	}

	public void sendItemValue(int itemId) {
		player.getPacketSender().sendMessage(
				ItemAssistant.getItemName(itemId) + " costs "
						+ getGraveValue(itemId) + " Graveyard points, "
						+ getAlchVal(itemId) + " Alchemy points,");
		player.getPacketSender().sendMessage(
				"" + getEnchVal(itemId) + " Enchantment points, and "
						+ getTelVal(itemId) + " Telekinetic points.");
	}

	public void buyItem(int itemId) {
		// If player already unlocked bones to peaches spell
		if (itemId == 6926 && player.unlockedBonesToPeaches) {
			player.getPacketSender().sendMessage("You've already unlocked this spell.");
			return;
		}

		int graveValue = getGraveValue(itemId);
		int alchValue = getAlchVal(itemId);
		int enchantValue = getEnchVal(itemId);
		int teleValue = getTelVal(itemId);

		if (
			graveValue > player.graveyardPoints ||
			alchValue > player.alchemyPoints ||
			enchantValue > player.enchantmentPoints ||
			teleValue > player.telekineticPoints
		) {
			player.getPacketSender().sendMessage("You don't have enough Pizazz Points to buy that.");
			return;
		}

		player.graveyardPoints -= graveValue;
		player.alchemyPoints -= alchValue;
		player.enchantmentPoints -= enchantValue;
		player.telekineticPoints -= teleValue;
		if (itemId == 6926) {
			player.unlockedBonesToPeaches = true;
		} else {
			player.getItemAssistant().addItem(itemId, 1);
		}
	}

}
