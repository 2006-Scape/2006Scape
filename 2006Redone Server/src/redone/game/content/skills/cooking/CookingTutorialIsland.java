package redone.game.content.skills.cooking;

import redone.Constants;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.music.sound.SoundList;
import redone.game.content.skills.SkillHandler;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;
import redone.util.Misc;

public class CookingTutorialIsland extends SkillHandler {

	public static void cookThisFood(Client p, int i, int object) {
		switch (i) {
		case 317:
			cookFish(p, i, 30, 1, 323, 315, object);
			break;
		default:
			p.getActionSender().sendMessage("Nothing interesting happens.");
			break;
		}
	}

	private static void cookFish(Client c, int itemID, int xpRecieved,
			int levelRequired, int burntFish, int cookedFish, int object) {
		if (!COOKING) {
			c.getActionSender().sendMessage(
					"Cooking is currently disabled.");
			return;
		}
		if (!hasRequiredLevel(c, 7, levelRequired, "cooking", "cook this")) {
			return;
		}
		int chance = c.playerLevel[7];
		if (c.playerEquipment[c.playerHands] == 775) {
			chance = c.playerLevel[7] + 8;
		}
		if (chance <= 0) {
			chance = Misc.random(5);
		}
		c.playerSkillProp[7][0] = itemID;
		c.playerSkillProp[7][1] = xpRecieved;
		c.playerSkillProp[7][2] = levelRequired;
		c.playerSkillProp[7][3] = burntFish;
		c.playerSkillProp[7][4] = cookedFish;
		c.playerSkillProp[7][5] = object;
		c.playerSkillProp[7][6] = chance;
		c.stopPlayerSkill = false;
		int item = c.getItemAssistant().getItemAmount(c.playerSkillProp[7][0]);
		if (item == 1) {
			c.doAmount = 1;
			cookTutFish(c);
			return;
		}
		viewCookInterface(c, itemID);
	}

	public static void getAmount(Client c, int amount) {
		int item = c.getItemAssistant().getItemAmount(c.playerSkillProp[7][0]);
		if (amount > item) {
			amount = item;
		}
		c.doAmount = amount;
		cookTutFish(c);
	}

	public static void resetCooking(Client c) {
		c.playerSkilling[7] = false;
		c.stopPlayerSkill = false;
		for (int i = 0; i < 6; i++) {
			c.playerSkillProp[7][i] = -1;
		}
	}

	private static void viewCookInterface(Client c, int item) {
		c.getPlayerAssistant().sendChatInterface(1743);
		c.getPlayerAssistant().sendFrame246(13716, 190, item);
		c.getPlayerAssistant().sendFrame126(
				"" + ItemAssistant.getItemName(item) + "", 13717);
	}

	private static void cookTutFish(final Client c) {
		if (c.playerSkilling[7]) {
			return;
		}
		if (c.tutorialProgress == 6) {
			c.playerSkilling[7] = true;
			c.stopPlayerSkill = true;
			c.getPlayerAssistant().removeAllWindows();
			if (c.playerSkillProp[7][5] > 0) {
				// c.startAnimation(c.playerSkillProp[7][5] == 2732 ? 897 :
				// 896);
				c.startAnimation(c.playerSkillProp[7][5] == 2732 ? 897
						: c.playerSkillProp[7][5] == 12269 ? 897 : 896);
				if (Constants.SOUND) {
					c.getActionSender().sendSound(SoundList.COOK_ITEM, 100,
							0);
				}

			}
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					c.getItemAssistant().deleteItem(
							c.playerSkillProp[7][0],
							c.getItemAssistant().getItemSlot(
									c.playerSkillProp[7][0]), 1);
					if (c.Cookstage1 == 1) {
						c.getActionSender().chatbox(6180);
						c.getDialogueHandler()
								.chatboxText(
										c,
										"You have just burned your first shrimp. This is normal. As you",
										"get more experience in Cooking, you will burn stuff less often.",
										"Let's try cooking without burning it this time. First catch some",
										"more shrimp then use them on a fire.",
										"Burning your shrimp.");
						c.getActionSender().chatbox(6179);
						c.Cookstage1 = 0;
						c.getItemAssistant()
								.addItem(c.playerSkillProp[7][3], 1);
					} else {
						c.getActionSender().chatbox(6180);
						c.getDialogueHandler()
								.chatboxText(
										c,
										"If you'd like a recap on anything you've learnt so far, speak to",
										"the Survival Expert. You can now move on to the next",
										"instructor. Click on the gate shown and follow the path.",
										"Remember, you can move the camera with the arrow keys.",
										"Well done, you've just cooked your first RuneScape meal");
						c.getActionSender().chatbox(6179);
						c.getActionSender().createArrow(3089, 3092,
								c.getH(), 2);
						c.getPlayerAssistant().addSkillXP(
								c.playerSkillProp[7][1], 7);
						c.getItemAssistant()
								.addItem(c.playerSkillProp[7][4], 1);
						c.tutorialProgress = 7;
					}
					deleteTime(c);
					if (!c.getItemAssistant().playerHasItem(
							c.playerSkillProp[7][0], 1)
							|| c.doAmount <= 0) {
						container.stop();
					}
					if (!c.stopPlayerSkill) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					resetCooking(c);
				}
			}, 4);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

				@Override
				public void execute(CycleEventContainer container) {
					if (c.playerSkillProp[7][5] > 0) {
						// c.getPlayerAssistant().sendSound(357, 100, 1); //
						// cook sound
						c.startAnimation(c.playerSkillProp[7][5] == 2732 ? 897
								: 896);
					}
					if (!c.stopPlayerSkill) {
						container.stop();
					}
				}

				@Override
				public void stop() {

				}
			}, 4);
			return;
		}
	}
}
