package redone.game.items.impl;

import redone.game.content.music.sound.SoundList;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * @author Genesis
 */

public class HandleEmpty {

	public static boolean canEmpty(Client c, int id) {
		return filledToEmpty(c, id) != -1;
	}

	public static int filledToEmpty(Client c, int id) {
		String itemName = ItemAssistant.getItemName(id);
		if (!itemName.contains("Ring") && !itemName.contains("necklace")) {
		if (itemName.contains("(3)") || itemName.contains("(4)") || itemName.contains("(2)") || itemName.contains("(1)")  || itemName.contains("Weapon poison")) {
			if (id != 1712 && id != 1710 && id != 1708 && id != 1706) {
				c.getItemAssistant().deleteItem(id,
						c.getItemAssistant().getItemSlot(id), 1);
				c.getItemAssistant().addItem(229, 1);
				c.getActionSender().sendMessage("You empty the vial.");
				}
			}
		}
		switch (id) {
		case 1937: // Jugs
		case 1989:
		case 1991:
		case 1993:
		case 3729:
			return 1935;
		case 227: // Vial of Water
			return 229;
		case 1927: // Buckets
		case 1929:
			return 1925;
		}
		return -1;
	}

	public static void handleEmptyItem(Client c, int itemId, int giveItem) {
		final String name = ItemAssistant.getItemName(itemId);
		c.getActionSender().sendMessage("You empty your " + name + ".");
		c.getItemAssistant().deleteItem(itemId, 1);
		c.getItemAssistant().addItem(giveItem, 1);
		c.getActionSender().sendSound(SoundList.EMPTY, 100, 0);
	}

}
