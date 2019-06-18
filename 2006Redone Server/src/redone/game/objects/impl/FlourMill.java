package redone.game.objects.impl;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;
import redone.world.clip.Region;

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
	public static void grainOnHopper(Client c, int objectID, int itemId) {
		if (itemId == GRAIN) {
			// Grain amount - flour amount. Prevents putting more than 30
			if (c.grain == LIMIT - c.flourAmount || c.flourAmount == LIMIT) {
				c.getActionSender().sendMessage(
						"You can't put anymore grain into the hopper.");
				return;
			}
			c.startAnimation(832);
			c.getItemAssistant().deleteItem(GRAIN, 1);
			c.grain++;// + 1
			c.getActionSender().sendMessage(
					"You put the grain in the hopper.");
		} else {
			c.getActionSender().sendMessage("Nothing interesting happens.");
		}
	}

	/**
	 * When player operates the lever.
	 * 
	 * @param c
	 */
	public static void hopperControl(final Client c) {
		if (c.grain > 0) {
			if (c.flourAmount == LIMIT) {
				c.getActionSender().sendMessage(
						"There is currently too much grain in the hopper.");
				return;
			}
			c.getActionSender().sendMessage(
					"You operate the hopper. The grain slides down the chute.");
			c.startAnimation(832);
			  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (c.heightLevel == 2) {
						return;
					}
					if (c.heightLevel == 0) {
						c.getActionSender().object(FULL_FLOUR_BIN, 3166, 3306, 0, 10);
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
			c.getActionSender().sendMessage(
					"You operate the hopper. Nothing interesting happens.");
		}
	}

	/**
	 * Emptys the flour bin...
	 * 
	 * @param c
	 */
	public static void emptyFlourBin(Client c) {
		if (c.getItemAssistant().playerHasItem(EMPTY_POT, 1)
				&& c.flourAmount > 0) {
			c.getItemAssistant().deleteItem(EMPTY_POT, 1);
			c.getItemAssistant().addItem(POT_OF_FLOUR, 1);
			c.getActionSender().sendMessage(
					"You fill a pot with flour from the bin.");
			c.flourAmount--;
			if (c.flourAmount < 0) {
				c.flourAmount = 0;
			}
			if (c.flourAmount == 0) {
				c.getActionSender().object(EMPTY_FLOUR_BIN, 3166, 3306, 0, 10);
				Region.addObject(EMPTY_FLOUR_BIN, 3166, 3306, 0, 10, 0, false);
				c.getActionSender().sendMessage(
						"The flour bin is now empty.");
			}
		} else {
			c.getActionSender().sendMessage(
					"You don't have an empty pot to fill flour with.");
		}
	}
}
