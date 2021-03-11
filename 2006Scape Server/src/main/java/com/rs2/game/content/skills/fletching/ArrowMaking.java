package com.rs2.game.content.skills.fletching;

import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.items.ItemAssistant;
import com.rs2.game.players.Player;

/**
 * @author Tom
 */

public class ArrowMaking {

	public enum Data {

		ARROW_SHAFT(314, 52, 53, 1, 0.4), BRONZE_ARROW(39, 53, 882, 1, 1.3), IRON_ARROW(
				40, 53, 884, 15, 2.5), STEEL_ARROW(41, 53, 886, 30, 5), MITHRIL_ARROW(
				42, 53, 888, 45, 7.5), ADAMANT_ARROW(43, 53, 890, 60, 10), RUNE_ARROW(
				44, 53, 892, 75, 12.5),

		BRONZE_DART(819, 314, 806, 1, 1.8), IRON_DART(820, 314, 807, 22, 3.8), STEEL_DART(
				821, 314, 808, 37, 7.5), MITHRIL_DART(822, 314, 809, 52, 11.2), ADAMANT_DART(
				823, 314, 810, 67, 15), RUNE_DART(824, 314, 811, 81, 18.8),

		BRONZE_BRUTAL_ARROW(4819, 53, 4773, 7, 1.4), IRON_BRUTAL_ARROW(4820,
				53, 4778, 18, 2.6), STEEL_BRUTAL_ARROW(1539, 53, 4783, 33, 5.1), BLACK_BRUTAL_ARROW(
				4821, 53, 4788, 38, 6.4), MITHRIL_BRUTAL_ARROW(4822, 53, 4793,
				49, 7.5), ADAMANT_BRUTAL_ARROW(4823, 53, 4798, 62, 10.1), RUNE_BRUTAL_ARROW(
				4824, 53, 4803, 77, 12.5),

		PEARL_BOLT(46, 877, 880, 41, 3.2);

		public int item1, item2, product, level;
		public double xp;

		public static Data forId(int itemUsed, int usedWith) {
			for (Data arrowData : Data.values()) {
				if (arrowData.item1 == itemUsed && arrowData.item2 == usedWith
						|| arrowData.item2 == itemUsed
						&& arrowData.item1 == usedWith) {
					return arrowData;
				}
			}
			return null;
		}

		private Data(int item1, int item2, int product, int level, double xp) {
			this.item1 = item1;
			this.item2 = item2;
			this.product = product;
			this.level = level;
			this.xp = xp;
		}

		public int getItem1() {
			return item1;
		}

		public int getItem2() {
			return item2;
		}

		public int getProduct() {
			return product;
		}

		public int getLevel() {
			return level;
		}

		public double getXp() {
			return xp;
		}
	}

	public static boolean makeArrow(final Player player, int itemUsed, int usedWith) {
		final Data arrowData = Data.forId(itemUsed, usedWith);
		if (arrowData == null) {
			return false;
		}
		if (player.isWoodcutting) {
			return false;
		}
		if (player.playerLevel[9] < arrowData.getLevel()) {
			player.getDialogueHandler().sendStatement(
					"You need a fletching level of " + arrowData.getLevel()
							+ " to do this");
			player.nextChat = 0;
			return false;
		}
		if (!player.getItemAssistant().playerHasItem(arrowData.getItem1())
				|| !player.getItemAssistant().playerHasItem(arrowData.getItem2())) {
			player.getDialogueHandler().sendStatement(
					"You need 15 "
							+ ItemAssistant.getItemName(arrowData.getItem1())
							+ " and 15 "
							+ ItemAssistant.getItemName(arrowData.getItem2())
							+ " to make this.");
			player.nextChat = 0;
			return false;
		}
		if (player.getItemAssistant().freeSlots() < 1 && !player.getItemAssistant().playerHasItem(arrowData.getProduct())) {
			player.getPacketSender().sendMessage("Not enough space in your inventory.");
			return false;
		}
		if (!player.playerIsFletching)
		{
			player.playerIsFletching = true;
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				int factor = 1;
				final int multiplier = factor;
				int count1 = player.getItemAssistant().getItemAmount(arrowData.getItem1()) < 15 ? player
						.getItemAssistant().getItemAmount(arrowData.getItem1()) : 15;
				int count2 = player.getItemAssistant().getItemAmount(arrowData.getItem2()) < 15 ? player
						.getItemAssistant().getItemAmount(arrowData.getItem2()) : 15;
				final int count = count1 < count2 ? count1 : count2;
				@Override
				public void execute(CycleEventContainer container) {
					if (!player.getItemAssistant().playerHasItem(arrowData.getItem1(),
							count)
							|| !player.getItemAssistant().playerHasItem(
							arrowData.getItem2(), count) || player.isWoodcutting || player.isCrafting || player.isMoving || player.isMining || player.isBusy || player.isShopping || player.isSmithing || player.isFiremaking || player.isSpinning || player.isPotionMaking || player.playerIsFishing || player.isBanking || player.isSmelting || player.isTeleporting || player.isHarvesting || player.playerIsCooking || player.isPotCrafting) {
						container.stop();
						return;
					}
					player.getPacketSender().sendSound(375, 100, 0);
					player.getItemAssistant().deleteItem(arrowData.getItem1(), count);
					player.getItemAssistant().deleteItem(arrowData.getItem2(), count);
					player.getItemAssistant().addItem(arrowData.getProduct(),
							count / multiplier);
					player.getPacketSender().sendMessage(
							"You attach the "
									+ ItemAssistant.getItemName(arrowData
									.getItem1())
									+ " to "
									+ count
									/ multiplier
									+ " "
									+ ItemAssistant.getItemName(arrowData
									.getItem2()) + "s.");
					player.getPlayerAssistant().addSkillXP(
							count / multiplier * arrowData.getXp(), 9);
				}

				@Override
				public void stop() {
					player.playerIsFletching = false;
				}
			}, 1);
		}
		return true;
	}

}
