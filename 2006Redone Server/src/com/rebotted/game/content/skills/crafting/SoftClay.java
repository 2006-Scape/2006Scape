package com.rebotted.game.content.skills.crafting;

import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.content.randomevents.RandomEventHandler;
import com.rebotted.game.players.Player;

/**
 * Soft Clay
 * @author Andrew (Mr Extremez)
 */

public class SoftClay {

	public static final int SOFT_CLAY = 1761, CLAY = 434;

	public static void makeClay(final Player c) {
		if (!c.getItemAssistant().playerHasItem(CLAY)) {
			c.getPacketSender().sendMessage("You need clay to do this.");
			return;
		}
		   CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				int amountToSubtract = c.getItemAssistant().getItemAmount(CLAY);
				int amountToAdd = amountToSubtract;
				c.doAmount = amountToSubtract;
				c.addAmount = amountToAdd;
				if (c.getItemAssistant().playerHasItem(CLAY)) {
					c.getItemAssistant().deleteItem(CLAY, c.doAmount);
					c.doAmount--;
					c.getItemAssistant().addItem(SOFT_CLAY, c.addAmount);
					c.addAmount++;
					RandomEventHandler.addRandom(c);
					if (c.doAmount == 0) {
						c.getPacketSender().sendMessage("You have ran out of clay to turn to soft clay.");
						container.stop();
					}
					if (c.disconnected) {
						container.stop();
					}
				}
	        }
			@Override
				public void stop() {
					
				}
		}, 1);
	}

}
