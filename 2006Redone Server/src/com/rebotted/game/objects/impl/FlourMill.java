package com.rebotted.game.objects.impl;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.players.Player;
import com.rebotted.world.clip.Region;

/**
 * @author darkside1222
 */

public class FlourMill {

	public final static int EMPTY_POT = 1931, POT_OF_FLOUR = 1933,
			GRAIN = 1947, EMPTY_FLOUR_BIN = 1781, FULL_FLOUR_BIN = 1782;
	/**
	 * Limits the amount of flour. (RS-Limit = 30)
	 */
	public static int LIMIT = 30;

	/**
	 * Item on object.(Use grain on hopper)
	 * 
	 * @param c
	 */
	public static void grainOnHopper(Player c, int objectID, int itemId) {
		if (itemId == GRAIN) {
			// Grain amount - flour amount. Prevents putting more than 30
			if (c.grain == LIMIT - c.flourAmount || c.flourAmount == LIMIT) {
				c.getPacketSender().sendMessage(
						"You can't put anymore grain into the hopper.");
				return;
			}
			c.startAnimation(832);
			c.getItemAssistant().deleteItem(GRAIN, 1);
			c.grain++;// + 1
			c.getPacketSender().sendMessage(
					"You put the grain in the hopper.");
		} else {
			c.getPacketSender().sendMessage("Nothing interesting happens.");
		}
	}

	/**
	 * When player operates the lever.
	 * 
	 * @param c
	 */
	public static void hopperControl(final Player c) {
		if (c.grain > 0) {
			if (c.flourAmount == LIMIT) {
				c.getPacketSender().sendMessage(
						"There is currently too much grain in the hopper.");
				return;
			}
			c.getPacketSender().sendMessage(
					"You operate the hopper. The grain slides down the chute.");
			c.startAnimation(832);
			  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (c.heightLevel == 2) {
						return;
					}
					if (c.heightLevel == 0) {
						c.getPacketSender().object(FULL_FLOUR_BIN, 3166, 3306, 0, 10);
						Region.addObject(FULL_FLOUR_BIN, 3166, 3306, 0, 10, 0, false);
						container.stop();
					}
				}
				@Override
					public void stop() {
						
					}
			}, 1);
			c.flourAmount += c.grain;
			if (c.flourAmount > LIMIT) {
				c.flourAmount = LIMIT;// Flour amount returns to limit.
			}
			c.grain = 0;
		} else {
			c.startAnimation(832);
			c.getPacketSender().sendMessage(
					"You operate the hopper. Nothing interesting happens.");
		}
	}

	/**
	 * Emptys the flour bin...
	 * 
	 * @param player
	 */
	public static void emptyFlourBin(Player player) {
		if (player.getItemAssistant().playerHasItem(EMPTY_POT, 1)
				&& player.flourAmount > 0) {
			player.getItemAssistant().deleteItem(EMPTY_POT, 1);
			player.getItemAssistant().addItem(POT_OF_FLOUR, 1);
			player.getPacketSender().sendMessage(
					"You fill a pot with flour from the bin.");
			player.flourAmount--;
			if (player.flourAmount < 0) {
				player.flourAmount = 0;
			}
			if (player.flourAmount == 0) {
				player.getPacketSender().object(EMPTY_FLOUR_BIN, 3166, 3306, 0, 10);
				Region.addObject(EMPTY_FLOUR_BIN, 3166, 3306, 0, 10, 0, false);
				player.getPacketSender().sendMessage(
						"The flour bin is now empty.");
			}
		} else {
			player.getPacketSender().sendMessage(
					"You don't have an empty pot to fill flour with.");
		}
	}
}
