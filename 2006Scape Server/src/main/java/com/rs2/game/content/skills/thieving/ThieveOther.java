package com.rs2.game.content.skills.thieving;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

/**
 * Thieveother
 * @author Andrew (Mr Extremez)
 */

public class ThieveOther {
	
	private static boolean isPicking = false;
	private static final int[][] LOCKED_DOORS = {{2550, 2674, 3305}, {2551, 2674, 3304}};
	public static boolean lockedDoor(Player player, int objectType) {
		for (int[] element : LOCKED_DOORS) {
			int objectId = element[0];
			int x = element[1];
			int y = element[2];
			if (objectType == objectId && player.absX == x && player.absY == y) {
				player.getPacketSender().sendMessage("The door is locked.");
				return false;
			}
		}
		return true;
	}

	public static void pickLock(final Player client, int level, final double exp, final int x, final int y, final int hardness, boolean lock) {
		if (!client.getItemAssistant().playerHasItem(1523, 1) && lock) {
			client.getPacketSender().sendMessage("You need a lock pick to do that.");
			return;
		}

		if (playerHasRequiredThievingLevel(client, level) && thievingEnabled(client)) {
			if (isPicking) {
				client.getPacketSender().sendMessage("You are already picking a lock.");
				return;
			}
			isPicking = true;

			client.getPacketSender().sendMessage("You attempt to pick the lock.");
			CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (Misc.random(10) < hardness) {
						client.getPacketSender().sendMessage("You fail to pick the lock.");
						container.stop();
						return;
					}
					client.getPlayerAssistant().movePlayer(x, y, client.heightLevel);
					client.getPacketSender().sendMessage("You manage to pick the lock.");
					client.getPlayerAssistant().addSkillXP(exp, Constants.THIEVING);
					container.stop();
				}
				@Override
				public void stop() {
					isPicking = false;
				}
			}, 3);
		}
	}

	public static boolean playerHasRequiredThievingLevel(final Player client, int level) {
		if (client.playerLevel[Constants.THIEVING] < level) {
			client.getPacketSender().sendMessage("You need " + level + " thieving to thieve this chest.");
			return false;
		}

		return true;
	}

	public static boolean thievingEnabled(final Player client) {
		if (!SkillHandler.THIEVING) {
			client.getPacketSender().sendMessage("Thieving is currently disabled.");
			return false;
		}

		return true;
	}

}
