package com.rebotted.game.content.skills.thieving;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.content.skills.SkillHandler;
import com.rebotted.game.items.ItemAssistant;
import com.rebotted.game.players.Client;
import com.rebotted.util.Misc;

/**
 * Thieveother
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class ThieveOther {
	
	private static boolean isPicking = false;
	
	private static final int[][] LOCKED_DOORS = {{2550, 2674, 3305}, {2551, 2674, 3304}};
	
	public static boolean lockedDoor(Client client, int objectType) {
		for (int[] element : LOCKED_DOORS) {
			int objectId = element[0];
			int x = element[1];
			int y = element[2];
			if (objectType == objectId && client.absX == x && client.absY == y) {
				client.getPacketSender().sendMessage("The door is locked.");
				return false;
			}
		}
		return true;
	}
	
	public static void stealFromChest(Client client, int level, int exp, int reward, int amount) {
		if (client.playerLevel[client.playerThieving] < level) {
			client.getPacketSender().sendMessage("You need " + level + " thieving to thieve this chest.");
			return;
		}
		if (!SkillHandler.THIEVING) {
			client.getPacketSender().sendMessage("Thieving is currently disabled.");
			return;
		}
		client.getItemAssistant().addItem(reward, amount);
		client.getPlayerAssistant().addSkillXP(exp, client.playerThieving);
		client.getPacketSender().sendMessage("You steal " + ItemAssistant.getItemName(reward) + " from the chest.");
	}
	
	public static void pickLock(final Client client, int level, final double exp, final int x, final int y, final int hardness, boolean lock) {
		if (!client.getItemAssistant().playerHasItem(1523, 1) && lock) {
			client.getPacketSender().sendMessage("You need a lock pick to do that.");
			return;
		}
		if (client.playerLevel[client.playerThieving] < level) {
			client.getPacketSender().sendMessage("You need " + level + " thieving to thieve this chest.");
			return;
		}
		if (!SkillHandler.THIEVING) {
			client.getPacketSender().sendMessage("Thieving is currently disabled.");
			return;
		}
		if (isPicking == true) {
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
				client.getPlayerAssistant().addSkillXP(exp, client.playerThieving);
				container.stop();
			}
			@Override
			public void stop() {
				isPicking = false;
			}
		}, 3);
	}
}