package com.rs2.game.npcs.impl;

import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.players.Player;

/**
 * @author Tom
 */

public class MilkCow {

	/**
	 * The empty bucket Id
	 */
	private static int BUCKET = 1925;

	/**
	 * The bucket of milk Id
	 */
	private static int BUCKET_OF_MILK = 1927;

	public static void milk(final Player player) {
		if (!player.getItemAssistant().playerHasItem(BUCKET)) {
			player.getPacketSender().sendMessage("You need a bucket in order to milk this cow.");
			return;
		} else {
			player.startAnimation(2305);
			player.milking = true;
			CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					player.getItemAssistant().deleteItem(BUCKET, 1);
					player.getPacketSender().sendMessage("You milk the cow.");
					player.getItemAssistant().addItem(BUCKET_OF_MILK, 1);
					container.stop();
				}

				@Override
				public void stop() {
					player.milking = false;
				}
			}, 7);
			return;
		}
	}

}
