package com.rs2.game.content.skills.crafting;

import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.players.Player;

/**
 * Soft Clay
 * @author Andrew (Mr Extremez)
 */

public class SoftClay {

	public static final int SOFT_CLAY = 1761, CLAY = 434;

	public static void makeClay(final Player player) {
		if (!player.getItemAssistant().playerHasItem(CLAY)) {
			player.getPacketSender().sendMessage("You need clay to do this.");
			return;
		}
		player.isSpinning = true;
		player.doAmount = player.getItemAssistant().getItemAmount(CLAY);
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
	        @Override
	            public void execute(CycleEventContainer container) {
				if (player.getItemAssistant().playerHasItem(CLAY) && player.isSpinning == true) {
					player.startAnimation(896);
					player.getItemAssistant().deleteItem(CLAY, 1);
					player.getItemAssistant().addItem(SOFT_CLAY, 1);
					player.doAmount--;
					RandomEventHandler.addRandom(player);
					player.getPacketSender().sendMessage("You turn the clay into soft clay.");
					if (player.disconnected || player.isSpinning == false || player.doAmount == 0) {
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