package redone.game.content.skills.agility;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;

public class PyramidAgility {

	private final Client c;

	public PyramidAgility(Client c) {
		this.c = c;
	}

	public static final int PYRAMID_STAIRCE_OBJECT = 10857,
			PYRAMID_WALL_OBJECT = 10865, PYRAMID_PLANK_OBJECT = 10868,
			PYRAMID_GAP_OBJECT = 10863, PYRAMID_GAP_2 = 10885,
			PYRAMID_GAP_3 = 10859, PYRAMID_GAP_4 = 10883,
			PYRAMID_GAP_5 = 10862, LEDGE = 10860, LEDGE_2 = 10886,
			LEDGE_3 = 10888, PYRAMID_ROCKS = 10852; // pyramid
													// course
													// objects

	public boolean pyramidCourse(int objectId) {
		switch (objectId) {
		case PYRAMID_STAIRCE_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(3357, 2832)
					|| c.getAgility().hotSpot(3356, 2832)
					|| c.getAgility().hotSpot(3355, 2830)
					|| c.getAgility().hotSpot(3359, 2834)
					|| c.getAgility().hotSpot(3358, 2834)
					|| c.getAgility().hotSpot(3361, 2836)
					|| c.getAgility().hotSpot(3360, 2836)) {
				if (c.heightLevel == 0) {
					c.getAgility().climbUp(c.getX(), c.getY() + 3, 1);
				} else if (c.heightLevel == 1) {
					c.getAgility().climbUp(c.getX(), c.getY() + 3, 2);
				} else if (c.heightLevel == 2) {
					c.getAgility().climbUp(c.getX(), c.getY() + 3, 3);
				} else {
					c.getAgility().climbUp(c.getX(), c.getY() - 8, 0);
					c.getActionSender()
							.sendMessage(
									"Congratulations you completed the pyramid course!");
				}
			}
			return true;

		case PYRAMID_ROCKS:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.absX == 3349) {
				c.getAgility().walk(-6, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getActionSender().sendMessage(
						"You leave the agility pyramid");
			} else if (c.absX == 3343) {
				c.getAgility()
						.walk(6, 0, c.getAgility().getAnimation(3043), -1);
				c.getActionSender().sendMessage(
						"You enter the agility pyramid");
			}
			return true;

		case LEDGE:
		case LEDGE_2:
		case LEDGE_3:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(3363, 2851)) {
				c.getAgility().walk(5, 0,
						c.getAgility().getAnimation(objectId), -1);
			} else if (c.getAgility().hotSpot(3364, 2832)) {
				c.getAgility().walk(-5, 0,
						c.getAgility().getAnimation(objectId), -1);
			} else if (c.getAgility().hotSpot(3372, 2841)) {
				c.getAgility().walk(0, -5,
						c.getAgility().getAnimation(objectId), -1);
			} else if (c.getAgility().hotSpot(3359, 2842)) {
				c.getAgility().walk(0, 5,
						c.getAgility().getAnimation(objectId), -1);
			}
			return true;

		case PyramidAgility.PYRAMID_WALL_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(3354, 2848)
					|| c.getAgility().hotSpot(3355, 2848)) {
				c.getAgility().walk(0, 2,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
			}
			if (c.getAgility().hotSpot(3371, 2834)
					|| c.getAgility().hotSpot(3371, 2833)) {
				c.getAgility().walk(-2, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
			}
			if (c.getAgility().hotSpot(3359, 2838)
					|| c.getAgility().hotSpot(3358, 2838)) {
				c.getAgility().walk(0, 2,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
			}
			return true;

		case PYRAMID_GAP_OBJECT:
		case PYRAMID_GAP_2:
		case PYRAMID_GAP_3:
		case PYRAMID_GAP_4:
		case PYRAMID_GAP_5:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(3363, 2851)) {
				c.getAgility().walk(1, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(3368, 2851, 1);
						container.stop();
			        }
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			if (c.getAgility().hotSpot(3372, 2832)
					|| c.getAgility().hotSpot(3372, 2831)) {
				c.getAgility().walk(-1, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(3367, c.absY, 1);// was
																			// 2832
						container.stop();
					}
			        @Override
						public void stop() {
							
						}
				}, 1);
			}
			if (c.getAgility().hotSpot(3364, 2832)) {
				c.getAgility().walk(-1, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(3359, 2832, 1);
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			if (c.getAgility().hotSpot(3357, 2836)) {
				c.getAgility().walk(0, 5,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(3357, 2841, 2);
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			if (c.getAgility().hotSpot(3357, 2846)) {
				c.getAgility().walk(0, 3,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(3357, 2849, 2);
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			if (c.getAgility().hotSpot(3359, 2849)
					|| c.getAgility().hotSpot(3359, 2850)) {
				c.getAgility().walk(5, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(3364, c.absY, 2);
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			if (c.getAgility().hotSpot(3366, 2834)) {
				c.getAgility().walk(-1, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(3363, 2834, 2);
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			if (c.getAgility().hotSpot(3359, 2842)) {
				c.getAgility().walk(0, 1,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(3359, 2847, 3);
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			if (c.getAgility().hotSpot(3370, 2843)) {
				c.getAgility().walk(0, -1,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(3370, 2838, 3);
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			if (c.getAgility().hotSpot(3372, 2841)) {
				c.getAgility().walk(0, -1,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
				  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (c.disconnected) {
							container.stop();
							return;
						}
						c.getPlayerAssistant().movePlayer(3372, 2836, 2);
						container.stop();
					}
					@Override
						public void stop() {
							
						}
				}, 1);
			}
			return true;

		case PYRAMID_PLANK_OBJECT:
			if (c.getAgility().checkLevel(objectId)) {
				return false;
			}
			if (c.getAgility().hotSpot(3375, 2845)) {
				c.getAgility().walk(0, -6,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
			}
			if (c.getAgility().hotSpot(3370, 2835)) {
				c.getAgility().walk(-6, 0,
						c.getAgility().getAnimation(objectId), -1);
				c.getPlayerAssistant().addSkillXP(
						c.getAgility().getXp(objectId), c.playerAgility);
			}
			return true;
		}
		return false;
	}

}
