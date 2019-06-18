package redone.game.npcs.impl;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;

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

	public static void milk(final Client c) {
		if (!c.getItemAssistant().playerHasItem(BUCKET)) {
			c.getActionSender().sendMessage("You need a bucket in order to milk this cow.");
			return;
		} else {
			c.startAnimation(2305);
			c.milking = true;
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					c.getItemAssistant().deleteItem2(BUCKET, 1);
					c.getActionSender().sendMessage("You milk the cow.");
					c.getItemAssistant().addItem(BUCKET_OF_MILK, 1);
					container.stop();
				}

				@Override
				public void stop() {
					c.milking = false;
				}
			}, 7);
			return;
		}
	}

}
