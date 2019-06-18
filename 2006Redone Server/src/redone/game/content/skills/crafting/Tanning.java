package redone.game.content.skills.crafting;

import redone.game.players.Client;

public class Tanning extends CraftingData {

	public static void sendTanningInterface(final Client c) {
		c.getPlayerAssistant().showInterface(14670);
		for (final tanningData t : tanningData.values()) {
			c.getActionSender().itemOnInterface(t.getItemFrame(), 250,
					t.getLeatherId());
			c.getPlayerAssistant().sendFrame126(t.getName(), t.getNameFrame());
			if (c.getItemAssistant().playerHasItem(995, t.getPrice())) {
				c.getPlayerAssistant().sendFrame126(
						"@gre@Price: " + t.getPrice(), t.getCostFrame());
			} else {
				c.getPlayerAssistant().sendFrame126(
						"@red@Price: " + t.getPrice(), t.getCostFrame());
			}
		}
	}

	public static void tanHide(final Client c, final int buttonId) {
		for (final tanningData t : tanningData.values()) {
			if (buttonId == t.getButtonId(buttonId)) {
				int amount = c.getItemAssistant().getItemCount(t.getHideId());
				if (amount > t.getAmount(buttonId)) {
					amount = t.getAmount(buttonId);
				}
				int price = amount * t.getPrice();
				int coins = c.getItemAssistant().getItemCount(995);
				if (price > coins) {
					price = coins - coins % t.getPrice();
				}
				if (price == 0) {
					c.getActionSender().sendMessage(
							"You do not have enough coins to tan this hide.");
					return;
				}
				amount = price / t.getPrice();
				final int hide = t.getHideId();
				final int leather = t.getLeatherId();
				if (c.getItemAssistant().playerHasItem(995, price)) {
					if (c.getItemAssistant().playerHasItem(hide)) {
						c.getItemAssistant().deleteItem(hide, amount);
						c.getItemAssistant().deleteItem(995,
								c.getItemAssistant().getItemSlot(995), price);
						c.getItemAssistant().addItem(leather, amount);
						c.getActionSender().sendMessage(
								"The tanner tans the hides for you.");
					} else {
						c.getActionSender().sendMessage(
								"You do not have any hides to tan.");
						return;
					}
				} else {
					c.getActionSender().sendMessage(
							"You do not have enough coins to tan this hide.");
					return;
				}
			}
		}
	}
}
