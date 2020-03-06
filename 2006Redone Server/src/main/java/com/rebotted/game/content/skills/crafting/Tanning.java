package com.rebotted.game.content.skills.crafting;

import com.rebotted.game.players.Player;

public class Tanning extends CraftingData {

	public static void sendTanningInterface(final Player c) {
		c.getPacketSender().showInterface(14670);
		for (final tanningData t : tanningData.values()) {
			c.getPacketSender().itemOnInterface(t.getItemFrame(), 250,
					t.getLeatherId());
			c.getPacketSender().sendString(t.getName(), t.getNameFrame());
			if (c.getItemAssistant().playerHasItem(995, t.getPrice())) {
				c.getPacketSender().sendString(
						"@gre@Price: " + t.getPrice(), t.getCostFrame());
			} else {
				c.getPacketSender().sendString(
						"@red@Price: " + t.getPrice(), t.getCostFrame());
			}
		}
	}

	public static void tanHide(final Player player, final int buttonId) {
		for (final tanningData t : tanningData.values()) {
			if (buttonId == t.getButtonId(buttonId)) {
				int amount = player.getItemAssistant().getItemAmount(t.getHideId());
				if (amount > t.getAmount(buttonId)) {
					amount = t.getAmount(buttonId);
				}
				int price = amount * t.getPrice();
				int coins = player.getItemAssistant().getItemAmount(995);
				if (price > coins) {
					price = coins - coins % t.getPrice();
				}
				if (amount > 0 && price == 0) {
					player.getPacketSender().sendMessage("You do not have enough coins to tan this hide.");
					return;
				}
				amount = price / t.getPrice();
				final int hide = t.getHideId();
				final int leather = t.getLeatherId();
				if (player.getItemAssistant().playerHasItem(995, price)) {
					if (player.getItemAssistant().playerHasItem(hide)) {
						player.getItemAssistant().deleteItem(hide, amount);
						player.getItemAssistant().deleteItem(995, player.getItemAssistant().getItemSlot(995), price);
						player.getItemAssistant().addItem(leather, amount);
						player.getPacketSender().sendMessage("The tanner tans the hides for you.");
					} else {
						player.getPacketSender().sendMessage("You do not have any hides to tan.");
						return;
					}
				} else {
					player.getPacketSender().sendMessage(
							"You do not have enough coins to tan this hide.");
					return;
				}
			}
		}
	}
}
