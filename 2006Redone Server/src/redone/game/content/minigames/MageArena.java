/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

package redone.game.content.minigames;

import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * @author D
 */
public class MageArena {

	private final Client c;

	public MageArena(Client c) {
		this.c = c;
	}

	private final int telePoints = 0;
	private final int enchantPoints = 0;
	private final int gravePoints = 0;
	private final int alchPoints = 0;

	private final int[] shopItems = { 6908, 6910, 6912, 6914, 6916, 6918, 6920,
			6922, 6924, 6889, 6926, 1391, 4695, 4696, 4698, 4697, 4694, 4699,
			564, 561, 560, 563, 565, 6891 };

	private final String[] interfaceText = { "Magic Training Arena Shop",
			"Telekinetic Pizazz Points", "Enchantment Pizazz Points",
			"Graveyard Pizazz Points", "Alchemist Pizazz Points" };

	public void openShop() {

		for (int i = 0; i < shopItems.length; i++) {
			c.getPlayerAssistant().sendFrame34(15948, shopItems[i], i, 100);
		}

		for (int i = 15950; i < interfaceText.length; i++) {
			c.getPlayerAssistant().sendFrame126(interfaceText[i - 15950], i);
		}

		c.getPlayerAssistant().sendFrame126(Integer.toString(telePoints), 15955);
		c.getPlayerAssistant().sendFrame126(Integer.toString(enchantPoints),
				15956);
		c.getPlayerAssistant().sendFrame126(Integer.toString(gravePoints), 15957);
		c.getPlayerAssistant().sendFrame126(Integer.toString(alchPoints), 15958);
		c.getPlayerAssistant().showInterface(15944);
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

	public void sendMessage(int itemId) {
		c.getActionSender().sendMessage(
				ItemAssistant.getItemName(itemId) + " costs "
						+ getGraveValue(itemId) + " Graveyard points, "
						+ getAlchVal(itemId) + " Alchemy points,");
		c.getActionSender().sendMessage(
				"" + getEnchVal(itemId) + " Enchantment points, and "
						+ getTelVal(itemId) + " Telekinetic points.");
	}

}
