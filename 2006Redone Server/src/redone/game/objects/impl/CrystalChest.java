package redone.game.objects.impl;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;
import redone.util.Misc;

public class CrystalChest {

	private static final int[] CHEST_REWARDS = { 1079, 1093, 526, 1969, 371,
			2363, 451 };
	public static final int[] KEY_HALVES = { 985, 987 };
	public static final int KEY = 989;
	private static final int DRAGONSTONE = 1631;
	private static final int OPEN_ANIMATION = 881;

	public static void makeKey(Client c) {
		if (c.getItemAssistant().playerHasItem(toothHalf(), 1)
				&& c.getItemAssistant().playerHasItem(loopHalf(), 1)) {
			c.getItemAssistant().deleteItem(toothHalf(), 1);
			c.getItemAssistant().deleteItem(loopHalf(), 1);
			c.getItemAssistant().addItem(KEY, 1);
		}
	}

	public static boolean canOpen(Client c) {
		if (c.getItemAssistant().playerHasItem(KEY)) {
			return true;
		} else {
			c.getActionSender().sendMessage("The chest is locked");
			return false;
		}
	}

	public static void searchChest(final Client c, final int id, final int x,
			final int y) {
		if (canOpen(c)) {
			c.getActionSender().sendMessage(
					"You unlock the chest with your key.");
			c.getItemAssistant().deleteItem(KEY, 1);
			c.startAnimation(OPEN_ANIMATION);
			c.getActionSender().checkObjectSpawn(id + 1, x, y, 2, 10);
			  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					c.getItemAssistant().addItem(DRAGONSTONE, 1);
					c.getItemAssistant().addItem(995, Misc.random(8230));
					c.getItemAssistant().addItem(
							CHEST_REWARDS[Misc.random(getLength() - 1)], 1);
					c.getActionSender().sendMessage(
							"You find some treasure in the chest.");
					c.getActionSender().checkObjectSpawn(id, x, y, 2, 10);
					container.stop();
				}
				@Override
					public void stop() {
						
					}
			}, 3);
		}
	}

	public static int getLength() {
		return CHEST_REWARDS.length;
	}

	public static int toothHalf() {
		return KEY_HALVES[0];
	}

	public static int loopHalf() {
		return KEY_HALVES[1];
	}
}
