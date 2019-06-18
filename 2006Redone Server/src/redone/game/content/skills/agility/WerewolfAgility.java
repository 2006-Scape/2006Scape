package redone.game.content.skills.agility;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;

public class WerewolfAgility {

	private final Client c;

	public WerewolfAgility(Client c) {
		this.c = c;
	}

	public static final int WEREWOLF_STEPPING_STONE_OBJECT = 5138,
			WEREWOLF_HURDLE_OBJECT1 = 5133, WEREWOLF_HURDLE_OBJECT2 = 5134,
			WEREWOLF_HURDLE_OBJECT3 = 5135, WEREWOLF_PIPES_OBJECT = 5152,
			WEREWOLF_SKULL_OBJECT = 5136, WEREWOLF_SLING_OBJECT = 5141; // werewolf
																		// course
																		// objects

	public boolean werewolfCourse(int objectId) {
		switch (objectId) {
		case WEREWOLF_STEPPING_STONE_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(3538, 9873)) {
				c.getAgility().walk(0, 2,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				c.getAgility().resetAgilityProgress();
				c.getAgility().agilityProgress[0] = true;
			} else if (c.getAgility().hotSpot(3538, 9875)) {
				c.getAgility().walk(0, 2,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				c.getAgility().resetAgilityProgress();
				c.getAgility().agilityProgress[0] = true;
			} else if (c.getAgility().hotSpot(3538, 9876)) {
				c.getAgility().walk(1, 2,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				c.getAgility().resetAgilityProgress();
				c.getAgility().agilityProgress[0] = true;
			} else if (c.getAgility().hotSpot(3538, 9877)) {
				c.getAgility().walk(2, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				c.getAgility().resetAgilityProgress();
				c.getAgility().agilityProgress[0] = true;
			} else if (c.getAgility().hotSpot(3540, 9877)) {
				c.getAgility().walk(0, 2,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				c.getAgility().resetAgilityProgress();
				c.getAgility().agilityProgress[0] = true;
			} else if (c.getAgility().hotSpot(3540, 9879)) {
				c.getAgility().walk(0, 2,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				c.getAgility().resetAgilityProgress();
				c.getAgility().agilityProgress[0] = true;
			}
			return true;

		case WEREWOLF_HURDLE_OBJECT1:
		case WEREWOLF_HURDLE_OBJECT2:
		case WEREWOLF_HURDLE_OBJECT3:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getY() == 9892 || c.getY() == 9895 || c.getY() == 9898) {
				c.getAgility().walk(0, 2,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[0] == true) {
					c.getAgility().agilityProgress[1] = true;
				}
				 CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(c.getX(), c.getY() + 1, 0);
						c.getActionSender().sendMessage("You managed to jump over the hurdle.");
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			return true;

		case WEREWOLF_PIPES_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(3538, 9904)) {
				c.getAgility().walk(0, 6,
						c.getAgility().getAnimation(objectId), 748);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[1] == true) {
					c.getAgility().agilityProgress[2] = true;
				}
			} else if (c.getAgility().hotSpot(3541, 9904)) {
				c.getAgility().walk(0, 6,
						c.getAgility().getAnimation(objectId), 748);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[1] == true) {
					c.getAgility().agilityProgress[2] = true;
				}
			} else if (c.getAgility().hotSpot(3544, 9904)) {
				c.getAgility().walk(0, 6,
						c.getAgility().getAnimation(objectId), 748);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[1] == true) {
					c.getAgility().agilityProgress[2] = true;
				}
			}
			return true;

		case WEREWOLF_SKULL_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getX() == 3533) {
				c.getAgility().walk(-3, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[2] == true) {
					c.getAgility().agilityProgress[3] = true;
				}
			}
			return true;

		case 5139:
			if (c.getAgility().hotSpot(3528, 9910)) {
				c.getAgility().walk(
						0,
						-39,
						c.getAgility().getAnimation(
								WerewolfAgility.WEREWOLF_SLING_OBJECT), 743);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[3] == true) {
					c.getAgility().agilityProgress[4] = true;
				}
				c.getAgility().agilityProgress[5] = true;
				c.getAgility().lapBonus = 2350 / 30;
				c.getAgility().lapFinished();
			}
			return true;

		case WEREWOLF_SLING_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getX() == 3530) {
				c.getPlayerAction().setAction(true);
				c.getPlayerAction().canWalk(false);
				c.getPlayerAssistant().movePlayer(3528, 9910, 0);
				c.isRunning2 = true;
				// c.getAgility().zipLine = 0;
				// c.getAgility().moveHeight = 0;
			}
			return true;
		}
		return false;
	}

}
