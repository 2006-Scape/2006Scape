package redone.game.content.skills.agility;

import redone.game.players.Client;

public class GnomeAgility {

	private final Client c;

	public GnomeAgility(Client c) {
		this.c = c;
	}
	
	private static long clickTimer = 0;

	public static final int LOG_OBJECT = 2295, NET1_OBJECT = 2285,
			TREE_OBJECT = 2313, ROPE_OBJECT = 2312, TREE_BRANCH_OBJECT = 2314,
			NET2_OBJECT = 2286, PIPES1_OBJECT = 154, PIPES2_OBJECT = 4058;// gnome
																			// course
																			// objects

	public boolean gnomeCourse(int objectId) {
		switch (objectId) {
		case LOG_OBJECT:
			if (c.getAgility().hotSpot(2474, 3436)) {
				c.getAgility().walk(0, -7, c.getAgility().getAnimation(objectId), -1);
			} else if (c.absX == 2474 && c.absY > 3429 && c.absY < 3436) {
				c.getPlayerAssistant().movePlayer(2474, 3429, 0);
			}
			c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), c.playerAgility);
			c.getAgility().resetAgilityProgress();
			c.getAgility().agilityProgress[0] = true;
			return true;

		case NET1_OBJECT:
			c.getAgility().climbUp(c.getX(), c.getY() - 2, 1);
			c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), c.playerAgility);
			if (c.getAgility().agilityProgress[0] == true) {
				c.getAgility().agilityProgress[1] = true;
			}
			return true;

		case TREE_OBJECT:
			c.getAgility().climbUp(c.getX(), c.getY() - 3, 2);
			c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), c.playerAgility);
			if (c.getAgility().agilityProgress[1] == true) {
				c.getAgility().agilityProgress[2] = true;
			}
			return true;

		case ROPE_OBJECT:
			if (c.getAgility().hotSpot(2477, 3420)) {
				c.getAgility().walk(6, 0, c.getAgility().getAnimation(objectId), -1);
			} else if (c.absY == 3420 && c.absX > 2477 && c.absX < 2483) {// makes
																			// sure
																			// they
																			// don't
																			// cheat
			c.getPlayerAssistant().movePlayer(2483, 3420, 2);
			}
			c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), c.playerAgility);
			if (c.getAgility().agilityProgress[2] == true) {
				c.getAgility().agilityProgress[3] = true;
			}
			return true;

		case TREE_BRANCH_OBJECT:
			c.getAgility().climbDown(c.getX(), c.getY(), 0);
			c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), c.playerAgility);
			if (c.getAgility().agilityProgress[3] == true) {
				c.getAgility().agilityProgress[4] = true;
			}
			return true;

		case NET2_OBJECT:
			if (System.currentTimeMillis() - clickTimer < 1800) {
				return false;
			}
			if (c.getY() == 3425 && System.currentTimeMillis() - clickTimer > 1800) {
				c.getAgility().climbUp(c.getX(), c.getY() + 2, 0);
				c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), c.playerAgility);
				clickTimer = System.currentTimeMillis();
				if (c.getAgility().agilityProgress[4] == true) {
					c.getAgility().agilityProgress[5] = true;
				}
			}
			return true;

		case PIPES1_OBJECT:
			if (c.getAgility().hotSpot(2484, 3430)) {
				c.getAgility().walk(0, 7, c.getAgility().getAnimation(objectId), 748);
				if (c.getAgility().agilityProgress[5] == true) {
					c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId), c.playerAgility);
					c.getAgility().lapBonus = 1400 / 30;
					c.getAgility().lapFinished();
				} else {
					c.getPlayerAssistant().addSkillXP(
							c.getAgility().getXp(objectId), c.playerAgility);
				}
				c.getAgility().resetAgilityProgress();
			} else if (c.absY > 3430 && c.absY < 3436 && System.currentTimeMillis() - clickTimer > 1800) {
				c.getPlayerAssistant().movePlayer(2484, 3437, 0);
			}
			return true;

		case PIPES2_OBJECT:
			if (c.getAgility().hotSpot(2487, 3430)) {
				c.getAgility().walk(0, 7, c.getAgility().getAnimation(objectId), 748);
				if (c.getAgility().agilityProgress[5] == true) {
					c.getPlayerAssistant().addSkillXP(
							c.getAgility().getXp(objectId), c.playerAgility);
					c.getAgility().lapBonus = 1400 / 30;
					c.getAgility().lapFinished();
				} else {
					c.getPlayerAssistant().addSkillXP(
							c.getAgility().getXp(objectId), c.playerAgility);
				}
				c.getAgility().resetAgilityProgress();
			} else if (c.absY > 3430 && c.absY < 3436) {
				c.getPlayerAssistant().movePlayer(2487, 3437, 0);
			}
			return true;
		}
		return false;
	}
}
