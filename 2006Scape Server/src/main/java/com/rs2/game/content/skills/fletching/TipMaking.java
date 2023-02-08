package com.rs2.game.content.skills.fletching;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;

/**
 * @author Tom
 */

public class TipMaking {

	public enum Data {
		OPAL_BOLT_TIPS(1755, 1609, 45, 12, 11, 1.5),
		PEARL_BOLT_TIPS1(1755, 411, 46, 6, 41, 3.2),
		PEARL_BOLT_TIPS2(1755, 413, 46, 24, 41, 3.2);

		public int item1, item2, product, quantity, level;
		public double xp;

		public static Data forId(int itemUsed, int usedWith) {
			for (Data itemData : Data.values()) {
				if (itemData.item1 == itemUsed && itemData.item2 == usedWith
					|| itemData.item2 == itemUsed && itemData.item1 == usedWith) {
					return itemData;
				}
			}
			return null;
		}

		private Data(int item1, int item2, int product, int quantity, int level, double xp) {
			this.item1 = item1;
			this.item2 = item2;
			this.product = product;
			this.quantity = quantity;
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

		public int getQuantity() {
			return quantity;
		}

		public int getLevel() {
			return level;
		}

		public double getXp() {
			return xp;
		}
	}

	public static boolean makeTips(final Player player, int itemUsed, int usedWith) {
		final Data itemData = Data.forId(itemUsed, usedWith);
		if (itemData == null) {
			return false;
		}
		if (player.playerLevel[Constants.FLETCHING] < itemData.getLevel()) {
			player.getDialogueHandler().sendStatement(
					"You need a fletching level of " + itemData.getLevel()
							+ " to do this");
			player.nextChat = 0;
			return false;
		}
		if (!player.getItemAssistant().playerHasItem(itemData.getItem1())
				|| !player.getItemAssistant().playerHasItem(itemData.getItem2())) {
			player.getDialogueHandler().sendStatement(
					"You need "
							+ DeprecatedItems.getItemName(itemData.getItem1())
							+ " and "
							+ DeprecatedItems.getItemName(itemData.getItem2())
							+ " to make this.");
			player.nextChat = 0;
			return false;
		}
		if (player.getItemAssistant().freeSlots(itemData.getProduct(), itemData.getQuantity()) < 1) {
			player.getPacketSender().sendMessage("Not enough space in your inventory.");
			return false;
		}
		if (!player.playerIsFletching) {
			player.playerIsFletching = true;
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (!player.getItemAssistant().playerHasItem(itemData.getItem1()) || !player.getItemAssistant().playerHasItem(itemData.getItem2())
							|| player.isWoodcutting || player.isCrafting || player.isMoving || player.isMining || player.isBusy || player.isShopping || player.isSmithing || player.isFiremaking || player.isSpinning || player.isPotionMaking || player.playerIsFishing || player.isBanking || player.isSmelting || player.isTeleporting || player.isHarvesting || player.playerIsCooking || player.isPotCrafting) {
						container.stop();
						return;
					}
					player.getPacketSender().sendSound(SoundList.CUT_GEM, 100, 0);
					player.getItemAssistant().deleteItem(itemData.getItem2(), 1);
					player.getItemAssistant().addItem(itemData.getProduct(), itemData.getQuantity());
					player.getPacketSender().sendMessage(
							"You cut the "
									+ DeprecatedItems.getItemName(itemData.getItem2())
									+ " in to "
									+ itemData.getQuantity()
									+ " "
									+ DeprecatedItems.getItemName(itemData.getProduct()) + ".");
					player.getPlayerAssistant().addSkillXP(itemData.getQuantity() * itemData.getXp(), Constants.FLETCHING);
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
