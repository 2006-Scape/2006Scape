package redone.game.content.skills.agility;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;

public class BarbarianAgility {

	private final Client c;

	public BarbarianAgility(Client c) {
		this.c = c;
	}

	public static final int BARBARIAN_ROPE_SWING_OBJECT = 2282,
			BARBARIAN_LOG_OBJECT = 2294, BARBARIAN_NET_OBJECT = 2284,
			BARBARIAN_LEDGE_OBJECT = 2302, BARBARIAN_LADDER_OBJECT = 3205,
			BARBARIAN_WALL_OBJECT = 1948; // barbarian course objects

	public boolean barbarianCourse(int objectId) {
		switch (objectId) {
		case BARBARIAN_ROPE_SWING_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(2551, 3554)) {
				c.getAgility().walk(0, -1,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				c.getAgility().resetAgilityProgress();
				c.getAgility().agilityProgress[0] = true;
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(2551, 3549, 0);
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			return true;

		case BARBARIAN_LOG_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(2551, 3546)) {
				c.getAgility().walk(-10, 0, c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[0] == true) {
					c.getAgility().agilityProgress[1] = true;
				}
			} else if (c.absX < 2551 && c.absX > 2541) {
				c.getPlayerAssistant().movePlayer(2541, 3546, 0);
			}
			return true;

		case BARBARIAN_NET_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.absX == 2539 && c.absY > 3544 && c.absY < 3547) { 
			c.getAgility().climbUp(c.getX() - 1, c.getY(), 1);
			c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId),
					c.playerAgility);
			if (c.getAgility().agilityProgress[1] == true) {
				c.getAgility().agilityProgress[2] = true;
				}
			} else {
				c.getActionSender().sendMessage("You can't climb the net from here!");
				return false;
			}
			return true;

		case BARBARIAN_LEDGE_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(2536, 3547)) {
				c.getAgility().walk(-4, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[2] == true) {
					c.getAgility().agilityProgress[3] = true;
				}
			} else if (c.absX < 2536 && c.absX > 2532) {
				c.getPlayerAssistant().movePlayer(2532, 3547, 1);
			}
			return true;

		case BARBARIAN_LADDER_OBJECT:
			if (c.playerLevel[c.playerAgility] < 35 && c.objectX != 2776
					&& c.objectY != 3121) {
				c.getActionSender().sendMessage(
						"You need 35 agility to use this ladder.");
				return false;
			} else if (c.objectX == 2776 && c.objectY == 3121) {
				return true;
			}
			c.getAgility().climbDown(c.getX(), c.getY(), 0);
			c.getActionSender().sendMessage("You climb down.");
			if (c.getAgility().agilityProgress[3] == true) {
				c.getAgility().agilityProgress[4] = true;
			}
			return true;

		case BARBARIAN_WALL_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(2541, 3553)) {
				c.getAgility().walk(2, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[4] == true) {
					c.getAgility().agilityProgress[5] = true;
				}
				if (c.getAgility().agilityProgress[5] == true) {
					c.getAgility().lapBonus = 1700 / 30;
					c.getAgility().lapFinished();
					c.getAgility().resetAgilityProgress();
				}
				return true;
			} else if (c.absX == 2540 && c.absY == 3553) {
				c.getAgility().walk(3, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
			} else if (c.absX == 2542 && c.absY == 3553) {
				c.getAgility().walk(1, 0,
						c.getAgility().getAnimation(objectId), -1);
			} else if (c.absX == 2535 && c.absY == 3553 || c.absX == 2538
					&& c.absY == 3553) {
				c.getAgility().walk(2, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				return true;
			}
		}
		return false;
	}
}
