package com.rs2.game.content.skills.crafting;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;

public class Spinning extends CraftingData {

	public static int[][] BEFORE_AFTER = { { 1737, 1759, 3, 1 },
			{ 1779, 1777, 15, 10 } };

	public static void showSpinning(Player player) {
		player.getPacketSender().sendChatInterface(8880);
		player.getPacketSender().sendString("What would you like to make?", 8879);
		player.getPacketSender().sendFrame246(8883, 180, 1737); // left
		player.getPacketSender().sendFrame246(8884, 180, 1779); // middle
		player.getPacketSender().sendFrame246(8885, 180, 6051); // right
		player.getPacketSender().sendString("Wool", 8889);
		player.getPacketSender().sendString("Flax", 8893);
		player.getPacketSender().sendString("Magic tree", 8897);
		player.clickedSpinning = true;
	}

	public static void getAmount(Player c, int amount) {
		c.doAmount = amount;
		spinItem(c);
		c.isSpinning = true;
	}

	public static void spinItem(final Player player) {
		player.getPacketSender().closeAllWindows();
		for (int[] element : BEFORE_AFTER) {
			final int before = element[0];
			final int after = element[1];
			final int exp = element[2];
			final int level = element[3];
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					if (player.isSpinning == true) {
						if (player.getItemAssistant().playerHasItem(before)) {
							if (player.playerLevel[Constants.CRAFTING] < level) {
								player.getDialogueHandler().sendStatement("You need a crafting level of " + level + " to do this.");
								return;
							}
							player.startAnimation(896);
							player.getItemAssistant().deleteItem(before, 1);
							player.getItemAssistant().addItem(after, 1);
							player.getPlayerAssistant().addSkillXP(exp, Constants.CRAFTING);
							player.getPacketSender().sendMessage("You spin the " + DeprecatedItems.getItemName(before) + " into a " + DeprecatedItems.getItemName(after) + ".");
							player.doAmount--;
						}

						if (player.doAmount <= 0 || player.isSpinning == false) {
							container.stop();
							return;
						}
					}

				}

				@Override
				public void stop() {
					player.isSpinning = false;
					player.startAnimation(65535);
					return;
				}
			}, 3);
		}
	}
}
