package redone.game.content.skills.agility;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;

public class ApeAtollAgility {

	private final Client c;

	public ApeAtollAgility(Client c) {
		this.c = c;
	}

	public static final int APE_ATOLL_STEPPING_STONES_OBJECT = 12568,
			APE_ATOLL_TROPICAL_TREE_OBJECT = 12570,
			APE_ATOLL_MONKEYBARS_OBJECT = 12573,
			APE_ATOLL_SKULL_SLOPE_OBJECT = 12576,
			APE_ATOLL_SWINGROPE_OBJECT = 12578,
			APE_ATOLL_BIG_TROPICAL_TREE_OBJECT = 12618;
	private static int NINJA_MONKEY_NPC = 1480;

	public boolean apeAtollCourse(int objectId) {
		switch (objectId) {
		case APE_ATOLL_STEPPING_STONES_OBJECT:
			if (c.npcId2 != NINJA_MONKEY_NPC) {
				c.getActionSender().sendMessage(
						"You can't do that! You aren't a monkey.");
				return false;
			}
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(2755, 2742)) {
				c.getActionSender().sendMessage("You jump the step stone.");
				c.getAgility().walk(-2, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				c.getAgility().resetAgilityProgress();
				c.getAgility().agilityProgress[0] = true;
			}
			return true;

		case APE_ATOLL_TROPICAL_TREE_OBJECT:
			if (c.npcId2 != NINJA_MONKEY_NPC) {
				c.getActionSender().sendMessage(
						"You can't do that! You aren't a monkey.");
				return false;
			}
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(2753, 2742) && c.heightLevel == 0) {
				c.getActionSender().sendMessage(
						"You managed to climb up the Tree.");
				c.getAgility().climbUpTropicalTree(c.getX(), c.getY(), 2);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[0] == true) {
					c.getAgility().agilityProgress[1] = true;
				}
			}
			return true;

		case APE_ATOLL_MONKEYBARS_OBJECT:
			if (c.npcId2 != NINJA_MONKEY_NPC) {
				c.getActionSender().sendMessage(
						"You can't do that! You aren't a monkey.");
				return false;
			}
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(2752, 2741)) {
				c.getActionSender().sendMessage(
						"You swing yourself to the other side");
				c.getAgility().moveHeight = 0;
				c.getAgility().walk(-5, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[1] == true) {
					c.getAgility().agilityProgress[2] = true;
				}
			}
			return true;

		case APE_ATOLL_SKULL_SLOPE_OBJECT:
			if (c.npcId2 != NINJA_MONKEY_NPC) {
				c.getActionSender().sendMessage(
						"You can't do that! You aren't a monkey.");
				return false;
			}
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(2747, 2741)) {
				c.getAgility().walk(-5, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				c.getActionSender().sendMessage("You climb your way up");
				if (c.getAgility().agilityProgress[2] == true) {
					c.getAgility().agilityProgress[3] = true;
				}
			}
			return true;

		case APE_ATOLL_SWINGROPE_OBJECT:
			if (c.npcId2 != NINJA_MONKEY_NPC) {
				c.getActionSender().sendMessage(
						"You can't do that! You aren't a monkey.");
				return false;
			}
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(2751, 2731)) {
				c.getAgility().walk(1, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				if (c.getAgility().agilityProgress[3] == true) {
					c.getAgility().agilityProgress[4] = true;
				}
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(2756, 2731, 0);
						c.getActionSender().sendMessage(
								"You swing yourself to the other side");
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			return true;

		case APE_ATOLL_BIG_TROPICAL_TREE_OBJECT:
			if (c.npcId2 != NINJA_MONKEY_NPC) {
				c.getActionSender().sendMessage(
						"You can't do that! You aren't a monkey.");
				return false;
			}
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			c.getPlayerAction().setAction(true);
			c.getPlayerAction().canWalk(false);
			c.getPlayerAssistant().movePlayer(c.getX(), c.getY() + 1, 1);
			c.getAgility().tropicalTreeUpdate = 2;
			c.getAgility().moveHeight = 0;
			c.getPlayerAssistant().addSkillXP(c.getAgility().getXp(objectId),
					c.playerAgility);
			if (c.getAgility().agilityProgress[4] == true) {
				c.getAgility().agilityProgress[5] = true;
			}
			c.getAgility().lapBonus = 2700;
			c.getAgility().lapFinished();
			c.getAgility().resetAgilityProgress();
			return true;
		}
		return false;
	}

}
