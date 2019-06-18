package redone.game.content.skills.fletching;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

/**
 * @author Tom
 */

public class Stringing {

	public static int STRING_SOUND = 2606;

	public enum Data {

		SHORT_BOW(50, 1777, 841, 5, 5), LONG_BOW(48, 1777, 839, 10, 10), OAK_SHORT_BOW(
				54, 1777, 843, 20, 16.5), OAK_LONG_BOW(56, 1777, 845, 25, 25), COMPOSITE_BOW(
				4825, 1777, 4827, 30, 45), WILLOW_SHORT_BOW(60, 1777, 849, 35,
				33.3), WILLOW_LONG_BOW(58, 1777, 847, 40, 41.5), MAPLE_SHORT_BOW(
				64, 1777, 853, 50, 50), MAPLE_LONG_BOW(62, 1777, 851, 55, 58.3), YEW_SHORT_BOW(
				68, 1777, 857, 65, 68.5), YEW_LONG_BOW(66, 1777, 855, 70, 75), MAGIC_SHORT_BOW(
				72, 1777, 861, 80, 83.3), MAGIC_LONG_BOW(70, 1777, 859, 85,
				91.5);

		public int item1, item2, product, level;
		public double xp;

		public static Data forId(int itemId, int usedWith) {
			for (Data loadData : Data.values()) {
				if (loadData.item1 == itemId && loadData.item2 == usedWith
						|| loadData.item2 == itemId
						&& loadData.item1 == usedWith) {
					return loadData;
				}
			}
			return null;
		}

		private Data(int item1, int item2, int product, int level, double xp) {
			this.item1 = item1;
			this.item2 = item2;
			this.product = product;
			this.level = level;
			this.xp = xp;
		}

		public int getItem1() {
			return item1;
		}

		public int getItem2() {
			return item2;
		}

		public int getProduct() {
			return product;
		}

		public int getLevel() {
			return level;
		}

		public double getXp() {
			return xp;
		}
	}

	public static boolean StringBow(final Client c, int itemUsed, int usedWith) {
		final Data loadData = Data.forId(itemUsed, usedWith);
		if (loadData == null) {
			return false;
		}
		if (c.playerLevel[9] < loadData.getLevel()) {
			c.getDialogueHandler().sendStatement(
					"You need a fletching level of " + loadData.getLevel()
							+ " to do this");
			return false;
		}
		if (!c.getItemAssistant().playerHasItem(loadData.getItem1())
				|| !c.getItemAssistant().playerHasItem(loadData.getItem2())) {
			c.getDialogueHandler().sendStatement(
					"You need a "
							+ ItemAssistant.getItemName(loadData.getItem1())
							+ " and a "
							+ ItemAssistant.getItemName(loadData.getItem2())
							+ " to make this.");
			return false;
		}
		c.playerIsFletching = true;
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (!c.getItemAssistant().playerHasItem(loadData.getItem1())
						|| !c.getItemAssistant().playerHasItem(
								loadData.getItem2())
						|| c.playerIsFletching == false) {
					container.stop();
					return;
				}
				c.getItemAssistant().deleteItem2(loadData.getItem1(), 1);
				c.getItemAssistant().deleteItem2(loadData.getItem2(), 1);
				c.getActionSender().sendMessage(
						"You add a string to the bow.");
				c.getItemAssistant().addItem(loadData.getProduct(), 1);
				c.getPlayerAssistant().addSkillXP(loadData.getXp(), 9);
			}

			@Override
			public void stop() {
				c.playerIsFletching = false;
				return;
			}
		}, 3);
		return true;
	}

}
