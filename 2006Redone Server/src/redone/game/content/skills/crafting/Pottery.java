package redone.game.content.skills.crafting;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * @author Tom
 */

public class Pottery {

	public static int unFire = 896;
	public static int Fire = 899;
	public static int softClay = 1761;

	public static void showUnfire(Client c) {
		c.getPlayerAssistant().sendChatInterface(8938);
		c.getPlayerAssistant().sendFrame126("What would you like to make?", 8879);
		c.getPlayerAssistant().sendFrame246(8941, 120, 1787); // first
		c.getPlayerAssistant().sendFrame246(8942, 150, 1789); // second
		c.getPlayerAssistant().sendFrame246(8943, 150, 1791); // third
		c.getPlayerAssistant().sendFrame246(8944, 120, 5352); // 4th
		c.getPlayerAssistant().sendFrame246(8945, 150, 4438); // 5th
		c.getPlayerAssistant().sendFrame126("Pot", 8949);
		c.getPlayerAssistant().sendFrame126("Pie Dish", 8953);
		c.getPlayerAssistant().sendFrame126("Bowl", 8957);
		c.getPlayerAssistant().sendFrame126("Plant pot", 8961);
		c.getPlayerAssistant().sendFrame126("Pot lid", 8965);
		c.showedUnfire = true;
	}

	public static void showFire(Client c) {
		c.getPlayerAssistant().sendChatInterface(8938);
		c.getPlayerAssistant().sendFrame126("What would you like to make?", 8879);
		c.getPlayerAssistant().sendFrame246(8941, 120, 1931); // first
		c.getPlayerAssistant().sendFrame246(8942, 150, 2313); // second
		c.getPlayerAssistant().sendFrame246(8943, 150, 1923); // third
		c.getPlayerAssistant().sendFrame246(8944, 120, 5350); // 4th
		c.getPlayerAssistant().sendFrame246(8945, 150, 4440); // 5th
		c.getPlayerAssistant().sendFrame126("Pot", 8949);
		c.getPlayerAssistant().sendFrame126("Pie Dish", 8953);
		c.getPlayerAssistant().sendFrame126("Bowl", 8957);
		c.getPlayerAssistant().sendFrame126("Plant pot", 8961);
		c.getPlayerAssistant().sendFrame126("Pot lid", 8965);
		c.showedFire = true;
	}

	public static void makeUnfire(final Client c, final int id,
			final double xp, final int level, int amount) {
		c.getPlayerAssistant().closeAllWindows();
		c.doAmount = amount;
		c.isPotCrafting = true;
		if (c.getItemAssistant().playerHasItem(softClay)
				&& c.playerLevel[12] >= level && c.isPotCrafting == true) {
			c.startAnimation(unFire);
			c.getItemAssistant().deleteItem2(softClay, 1);
			c.getItemAssistant().addItem(id, 1);
			c.getActionSender().sendMessage(
					"You make the soft clay into a "
							+ ItemAssistant.getItemName(id) + ".");
			c.getPlayerAssistant().addSkillXP(xp, c.playerCrafting);
			c.doAmount--;
		}
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (c.getItemAssistant().playerHasItem(softClay)
						&& c.playerLevel[12] >= level && !(c.doAmount <= 0)
						&& c.isPotCrafting == true) {
					c.startAnimation(unFire);
					c.getItemAssistant().deleteItem2(softClay, 1);
					c.getItemAssistant().addItem(id, 1);
					c.getActionSender().sendMessage(
							"You make the soft clay into a "
									+ ItemAssistant.getItemName(id) + ".");
					c.getPlayerAssistant().addSkillXP(xp, c.playerCrafting);
					c.doAmount--;
				}

				if (c.playerLevel[12] < level) {
					container.stop();
					c.getActionSender().sendMessage(
							"You need a crafting level of " + level
									+ " to make this.");
				}

				if (!c.getItemAssistant().playerHasItem(softClay)) {
					container.stop();
					c.getActionSender().sendMessage(
							"You need soft clay to do this.");
				}

				if (c.isPotCrafting == false) {
					container.stop();
				}

				if (c.doAmount <= 0) {
					container.stop();
				}

			}

			@Override
			public void stop() {
				c.isPotCrafting = false;
				c.startAnimation(65535);
			}
		}, 3);
	}

	public static void makeFire(final Client c, final int startId,
			final int finishId, final int level, final double xp, int amount) {
		c.getPlayerAssistant().closeAllWindows();
		c.doAmount = amount;
		c.isPotCrafting = true;
		if (c.getItemAssistant().playerHasItem(startId)
				&& c.playerLevel[12] >= level && c.isPotCrafting == true) {
			c.getItemAssistant().deleteItem2(startId, 1);
			c.getItemAssistant().addItem(finishId, 1);
			c.startAnimation(Fire);
			c.getActionSender().sendSound(469, 100, 0);
			c.getActionSender().sendMessage(
					"You put a " + ItemAssistant.getItemName(startId)
							+ " into the oven.");
			c.getActionSender().sendMessage(
					"You retrieve the " + ItemAssistant.getItemName(finishId)
							+ " from the oven.");
			c.getPlayerAssistant().addSkillXP(xp, c.playerCrafting);
			c.doAmount--;
		}

		if (c.playerLevel[12] < level) {
			c.getActionSender().sendMessage(
					"You need a crafting level of " + level + " to make this.");
		}

		if (!c.getItemAssistant().playerHasItem(startId)
				&& c.playerLevel[12] >= level) {
			c.getActionSender().sendMessage(
					"You need an " + ItemAssistant.getItemName(startId)
							+ " to do this.");
		}

		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (c.getItemAssistant().playerHasItem(startId)
						&& c.playerLevel[12] >= level
						&& c.isPotCrafting == true && !(c.doAmount <= 0)) {
					c.getItemAssistant().deleteItem2(startId, 1);
					c.getItemAssistant().addItem(finishId, 1);
					c.startAnimation(Fire);
					c.getActionSender().sendSound(469, 100, 0);
					c.getActionSender().sendMessage(
							"You put a " + ItemAssistant.getItemName(startId)
									+ " into the oven.");
					c.getActionSender().sendMessage(
							"You retrieve the "
									+ ItemAssistant.getItemName(finishId)
									+ " from the oven.");
					c.getPlayerAssistant().addSkillXP(xp, c.playerCrafting);
					c.doAmount--;
				}

				if (c.isPotCrafting == false
						|| !c.getItemAssistant().playerHasItem(startId)
						|| c.playerLevel[12] < level) {
					container.stop();
				}

				if (c.doAmount <= 0) {
					container.stop();
				}

			}

			@Override
			public void stop() {
				c.isPotCrafting = false;
				c.startAnimation(65535);
			}
		}, 5);
	}

}
