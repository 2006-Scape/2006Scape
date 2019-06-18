package redone.game.content.skills.agility;

import redone.Constants;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;
import redone.util.Misc;

public class Agility {

	/**
	 * @author Aintaro
	 */

	Client c;

	public Agility(Client c) {
		this.c = c;
	}

	public boolean[] agilityProgress = new boolean[6];

	public void resetAgilityProgress() {
		for (int i = 0; i < 6; i++) {
			agilityProgress[i] = false;
		}
		lapBonus = 0;
	}

	public static final int LOG_EMOTE = 762, PIPES_EMOTE = 844,
			CLIMB_UP_EMOTE = 828, CLIMB_DOWN_EMOTE = 827,
			CLIMB_UP_MONKEY_EMOTE = 3487, WALL_EMOTE = 840;
	public int steppingStone, steppingStoneTimer = 0, agilityTimer = -1,
			moveHeight = -1, tropicalTreeUpdate = -1, zipLine = -1;
	private int moveX, moveY, moveH;

	/**
	 * sets a specific emote to walk to point x
	 */

	private void walkToEmote(int id) {
		c.isRunning2 = false;
		c.playerWalkIndex = id;
		c.getPlayerAssistant().requestUpdates(); // this was needed to make the
													// agility work
	}

	/**
	 * resets the player animation
	 */

	private void stopEmote() {
		c.getCombatAssistant().getPlayerAnimIndex();
		c.getPlayerAction().setAction(false);
		c.getPlayerAction().canWalk(true);
		c.getPlayerAssistant().requestUpdates(); // this was needed to make the
													// agility work
		c.isRunning2 = true;
	}

	private static void setAnimationBack(Client c) {
		c.isRunning2 = true;
		c.getPlayerAssistant().sendConfig(173, 1);
		c.playerWalkIndex = 0x333;
		c.getPlayerAssistant().requestUpdates();
	}

	/**
	 * walk to point x with s a specific animation
	 */

	public void walk(int EndX, int EndY, int Emote, int endingAnimation) {
		c.getPlayerAction().setAction(true);
		c.getPlayerAction().canWalk(false);
		walkToEmote(Emote);
		c.getPlayerAssistant().walkTo2(EndX, EndY);
		destinationReached(EndX, EndY, endingAnimation);
	}

	public static void brimhavenSkippingStone(final Client c) {
		if (c.stopPlayerPacket) {
			return;
		}
		if (c.playerLevel[c.playerAgility] < 12) {
			c.getDialogueHandler().sendStatement("You need 12 agility to use these stepping stones");
			c.nextChat = 0;
			return;
		}
		c.stopPlayerPacket = true;
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				c.startAnimation(769);
				if (c.absX <= 2997) {
					container.stop();
				}
			}

			@Override
			public void stop() {
				// c.getPlayerAssistant().addSkillXP(100, c.playerAgility);
			}
		}, 1);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (c.absX >= 2648) {
					c.teleportToX = c.absX - 2;
					c.teleportToY = c.absY - 5;
					if (c.absX <= 2997) {
						container.stop();
					}
				} else if (c.absX <= 2648) {
					c.teleportToX = c.absX + 2;
					c.teleportToY = c.absY + 5;
					if (c.absX >= 2645) {
						container.stop();
					}
				}

			}

			@Override
			public void stop() {
				// c.getPlayerAssistant().addSkillXP(300, c.playerAgility);
				setAnimationBack(c);
				c.stopPlayerPacket = false;
			}
		}, 3);
	}

	/**
	 * when a player reaches he's point the stopEmote() method gets called this
	 * method calculates when the player reached he's point
	 */

	public void destinationReached(int x2, int y2, final int endingEmote) {
		if (x2 >= 0 && y2 >= 0 && x2 != y2) {
			  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (c.disconnected) {
						container.stop();
						return;
					}
					if (moveHeight >= 0) {
						c.getPlayerAssistant().movePlayer(c.getX(), c.getY(), moveHeight);
						moveHeight = -1;
					}
					stopEmote();
					c.startAnimation(endingEmote);
					container.stop();
				}
				@Override
					public void stop() {
						
					}
			}, x2 + y2);
		} else if (x2 == y2) {
			  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (c.disconnected) {
						container.stop();
						return;
					}
					if (moveHeight >= 0) {
						c.getPlayerAssistant().movePlayer(c.getX(), c.getY(), moveHeight);
						moveHeight = -1;
					}
					stopEmote();
					c.startAnimation(endingEmote);
					container.stop();
				}
					@Override
					public void stop() {
						
					}
			}, x2);
		} else if (x2 < 0) {
			  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (c.disconnected) {
						container.stop();
						return;
					}
					if (moveHeight >= 0) {
						c.getPlayerAssistant().movePlayer(c.getX(), c.getY(), moveHeight);
						moveHeight = -1;

					}
					stopEmote();
					c.startAnimation(endingEmote);
					container.stop();
				}
				@Override
					public void stop() {
						
					}
			}, -x2 + y2);
		} else if (y2 < 0) {
			  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (c.disconnected) {
						container.stop();
						return;
					}
					if (moveHeight >= 0) {
						c.getPlayerAssistant().movePlayer(c.getX(), c.getY(), moveHeight);
						moveHeight = -1;

					}
					stopEmote();
					c.startAnimation(endingEmote);
					container.stop();
				}
				@Override
					public void stop() {
						
					}
			}, x2 - y2);
		}
	}

	/**
	 * @param objectId
	 *            : the objectId to know how much exp a player receives
	 */

	public double getXp(int objectId) {
		switch (objectId) {
		case GnomeAgility.TREE_OBJECT:
		case GnomeAgility.TREE_BRANCH_OBJECT:
			return 5;
		case GnomeAgility.LOG_OBJECT:
		case GnomeAgility.PIPES1_OBJECT:
		case GnomeAgility.PIPES2_OBJECT:
		case GnomeAgility.NET2_OBJECT:
		case GnomeAgility.NET1_OBJECT:
		case GnomeAgility.ROPE_OBJECT:
			return 7.5;
		case PyramidAgility.PYRAMID_WALL_OBJECT:
		case BarbarianAgility.BARBARIAN_NET_OBJECT:
			return 8;
		case WildernessAgility.WILDERNESS_PIPE_OBJECT:
			return 12;
		case WildernessAgility.WILDERNESS_SWING_ROPE_OBJECT:
		case WildernessAgility.WILDERNESS_STEPPING_STONE_OBJECT:
		case WildernessAgility.WILDERNESS_LOG_BALANCE_OBJECT:
			return 20;
		case WildernessAgility.WILDERNESS_ROCKS_OBJECT:
			return 0;
		case BarbarianAgility.BARBARIAN_LOG_OBJECT:
		case BarbarianAgility.BARBARIAN_WALL_OBJECT:
			return 14;
		case WerewolfAgility.WEREWOLF_PIPES_OBJECT:
			return 15;
		case WerewolfAgility.WEREWOLF_SKULL_OBJECT:
			return 25;
		case WerewolfAgility.WEREWOLF_HURDLE_OBJECT1:
		case WerewolfAgility.WEREWOLF_HURDLE_OBJECT2:
		case WerewolfAgility.WEREWOLF_HURDLE_OBJECT3:
			return 20;
		case BarbarianAgility.BARBARIAN_ROPE_SWING_OBJECT:
		case BarbarianAgility.BARBARIAN_LEDGE_OBJECT:
			return 22;
		case WerewolfAgility.WEREWOLF_STEPPING_STONE_OBJECT:
			return 50;
		case PyramidAgility.PYRAMID_GAP_OBJECT:
		case PyramidAgility.PYRAMID_GAP_2:
		case PyramidAgility.PYRAMID_GAP_3:
		case PyramidAgility.PYRAMID_GAP_4:
		case PyramidAgility.PYRAMID_GAP_5:
			return 52;
		case PyramidAgility.PYRAMID_PLANK_OBJECT:
			return 56;
		case WerewolfAgility.WEREWOLF_SLING_OBJECT:
			return 190;
		}
		return -1;
	}

	/**
	 * @param objectId
	 *            : the objectId to fit with the right agility level required
	 */

	private int getLevelRequired(int objectId) {
		switch (objectId) {
		case PyramidAgility.PYRAMID_WALL_OBJECT:
		case PyramidAgility.PYRAMID_STAIRCE_OBJECT:
		case PyramidAgility.PYRAMID_PLANK_OBJECT:
		case PyramidAgility.PYRAMID_GAP_OBJECT:
		case PyramidAgility.PYRAMID_GAP_2:
		case PyramidAgility.PYRAMID_GAP_3:
		case PyramidAgility.PYRAMID_GAP_4:
		case PyramidAgility.PYRAMID_GAP_5:
		case PyramidAgility.LEDGE:
		case PyramidAgility.LEDGE_2:
		case PyramidAgility.LEDGE_3:
		case PyramidAgility.PYRAMID_ROCKS:
			return 30;
		case BarbarianAgility.BARBARIAN_ROPE_SWING_OBJECT:
		case BarbarianAgility.BARBARIAN_LOG_OBJECT:
		case BarbarianAgility.BARBARIAN_NET_OBJECT:
		case BarbarianAgility.BARBARIAN_LEDGE_OBJECT:
		case BarbarianAgility.BARBARIAN_WALL_OBJECT:
			return 35;

		case ApeAtollAgility.APE_ATOLL_STEPPING_STONES_OBJECT:
		case ApeAtollAgility.APE_ATOLL_TROPICAL_TREE_OBJECT:
		case ApeAtollAgility.APE_ATOLL_MONKEYBARS_OBJECT:
		case ApeAtollAgility.APE_ATOLL_SKULL_SLOPE_OBJECT:
		case ApeAtollAgility.APE_ATOLL_SWINGROPE_OBJECT:
		case ApeAtollAgility.APE_ATOLL_BIG_TROPICAL_TREE_OBJECT:
			return 50;

		case WildernessAgility.WILDERNESS_PIPE_OBJECT:
		case WildernessAgility.WILDERNESS_SWING_ROPE_OBJECT:
		case WildernessAgility.WILDERNESS_STEPPING_STONE_OBJECT:
		case WildernessAgility.WILDERNESS_ROCKS_OBJECT:
		case WildernessAgility.WILDERNESS_LOG_BALANCE_OBJECT:
			return 52;

		case WerewolfAgility.WEREWOLF_STEPPING_STONE_OBJECT:
		case WerewolfAgility.WEREWOLF_HURDLE_OBJECT1:
		case WerewolfAgility.WEREWOLF_HURDLE_OBJECT2:
		case WerewolfAgility.WEREWOLF_HURDLE_OBJECT3:
		case WerewolfAgility.WEREWOLF_PIPES_OBJECT:
		case WerewolfAgility.WEREWOLF_SKULL_OBJECT:
		case WerewolfAgility.WEREWOLF_SLING_OBJECT:
			return 60;
		}
		return -1;
	}

	/**
	 * @param objectId
	 *            : the objectId to fit with the right animation played
	 */

	public int getAnimation(int objectId) {
		switch (objectId) {
		case GnomeAgility.LOG_OBJECT:
		case WildernessAgility.WILDERNESS_LOG_BALANCE_OBJECT:
		case BarbarianAgility.BARBARIAN_LOG_OBJECT:
		case GnomeAgility.ROPE_OBJECT:
		case 2332:
			return LOG_EMOTE;
		case 154:
		case 4084:
		case 9330:
		case 9228:
		case 5100:
		case WildernessAgility.WILDERNESS_PIPE_OBJECT:
		case WerewolfAgility.WEREWOLF_PIPES_OBJECT:
			return PIPES_EMOTE;
		case WildernessAgility.WILDERNESS_SWING_ROPE_OBJECT:
		case BarbarianAgility.BARBARIAN_ROPE_SWING_OBJECT:
		case WerewolfAgility.WEREWOLF_STEPPING_STONE_OBJECT:
			return 3067;
		case WildernessAgility.WILDERNESS_STEPPING_STONE_OBJECT:
			return 1604; // 2588
		case WildernessAgility.WILDERNESS_ROCKS_OBJECT:
		case WerewolfAgility.WEREWOLF_SKULL_OBJECT:
		case PyramidAgility.PYRAMID_ROCKS:
			return 1148;
		case BarbarianAgility.BARBARIAN_LEDGE_OBJECT:
		case PyramidAgility.LEDGE:
		case PyramidAgility.LEDGE_2:
		case PyramidAgility.LEDGE_3:
			return 756;
		case BarbarianAgility.BARBARIAN_WALL_OBJECT:
		case PyramidAgility.PYRAMID_WALL_OBJECT:
			return WALL_EMOTE;
		case ApeAtollAgility.APE_ATOLL_STEPPING_STONES_OBJECT:
			return 3480;
		case ApeAtollAgility.APE_ATOLL_MONKEYBARS_OBJECT:
			return 3483;
		case ApeAtollAgility.APE_ATOLL_SKULL_SLOPE_OBJECT:
			return 3485;
		case ApeAtollAgility.APE_ATOLL_BIG_TROPICAL_TREE_OBJECT:
			return 3494;
		case ApeAtollAgility.APE_ATOLL_SWINGROPE_OBJECT:
			return 3482;
		case WerewolfAgility.WEREWOLF_SLING_OBJECT:
			return 744;
		case WerewolfAgility.WEREWOLF_HURDLE_OBJECT1:
		case WerewolfAgility.WEREWOLF_HURDLE_OBJECT2:
		case WerewolfAgility.WEREWOLF_HURDLE_OBJECT3:
			return 2750;
		}
		return -1;
	}

	/**
	 * method used for the tropicalTree at ape atoll the problem was that the
	 * heightlevel was not correct while the heightlevel was not correct we
	 * could not change heightlevels and use the walkToEmote directly so we need
	 * to add a little timer to make it work
	 */

	public void climbUpTropicalTree(final int moveX, final int moveY, final int moveH) {
		c.startAnimation(CLIMB_UP_MONKEY_EMOTE);
		c.getPlayerAction().setAction(true);
		c.getPlayerAction().canWalk(false);
		  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				if (c.disconnected) {
					container.stop();
					return;
				}
				c.getPlayerAction().setAction(false);
				c.getPlayerAction().canWalk(true);
				c.getPlayerAssistant().movePlayer(moveX, moveY, moveH);
				container.stop();
			}
			@Override
				public void stop() {
					
				}
		}, 2);
	}

	/**
	 * climbUp a ladder or anything. small delay before getting teleported to
	 * destination
	 */

	public void climbUp(final int moveX, final int moveY, final int moveH) {
		c.startAnimation(CLIMB_UP_EMOTE);
		c.getPlayerAction().setAction(true);
		c.getPlayerAction().canWalk(false);
		  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				if (c.disconnected) {
					stop();
					return;
				}
				c.getPlayerAction().setAction(false);
				c.getPlayerAction().canWalk(true);
				c.getPlayerAssistant().movePlayer(moveX, moveY, moveH);
				container.stop();
			}
			@Override
				public void stop() {
					
				}
		}, 1);
	}

	/**
	 * climbDown a ladder or anything. small delay before getting teleported to
	 * destination
	 */

	public void climbDown(final int moveX, final int moveY, final int moveH) {
		c.startAnimation(CLIMB_DOWN_EMOTE);
		c.getPlayerAction().setAction(true);
		c.getPlayerAction().canWalk(false);
		  CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
	            @Override
	            public void execute(CycleEventContainer container) {
				if (c.disconnected) {
					stop();
					return;
				}
				c.getPlayerAction().setAction(false);
				c.getPlayerAction().canWalk(true);
				c.getPlayerAssistant().movePlayer(moveX, moveY, moveH);
				container.stop();
			}
			@Override
				public void stop() {
					
				}
		}, 1);
	}

	/**
	 * a specific position the player has to stand on before the action is set
	 * to true
	 */

	public boolean hotSpot(int hotX, int hotY) {
		if (c.getX() == hotX && c.getY() == hotY) {
			return true;
		}
		return false;
	}

	int lapBonus = 0;

	public void lapFinished() {
		if (agilityProgress[5]) {
			c.getPlayerAssistant().addSkillXP(lapBonus, c.playerAgility);
			c.getActionSender().sendMessage("You received some bonus experience for completing the track!");
			resetAgilityProgress();
		}
	}

	/**
	 * 600 ms process for some agility actions
	 */

	public void agilityProcess() {
		// tropicaltreeupdate timer for the object in ape atoll course
		if (tropicalTreeUpdate > 0) {
			tropicalTreeUpdate--;
		}
		if (tropicalTreeUpdate == 0) {
			walk(13,
					13,
					getAnimation(ApeAtollAgility.APE_ATOLL_BIG_TROPICAL_TREE_OBJECT),
					-1);
			tropicalTreeUpdate = -1;
		}
		// zipline timer for the object in werewolve course
		if (zipLine > 0) {
			zipLine--;
		}
		if (zipLine == 0) {
			walk(0, -39, getAnimation(WerewolfAgility.WEREWOLF_SLING_OBJECT),
					743);
			zipLine = -1;
		}

		if (steppingStone > 0 && steppingStoneTimer == 0) {
			walk(-1,
					0,
					getAnimation(WildernessAgility.WILDERNESS_STEPPING_STONE_OBJECT),
					-1);
			steppingStone--;
			steppingStoneTimer = 2;
		}

		if (steppingStoneTimer > 0) {
			steppingStoneTimer--;
		}

		if (hotSpot(3363, 2851)) {
			moveX = 3368;
			moveY = 2851;
			moveH = 1;
			walk(1, 0, 2753, -1);
			c.getPlayerAssistant().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}

		if (hotSpot(3372, 2832)) {
			moveX = 3367;
			moveY = 2832;
			moveH = 1;
			walk(-1, 0, 2753, -1);
			c.getPlayerAssistant().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}

		if (hotSpot(3364, 2832)) {
			moveX = 3359;
			moveY = 2832;
			moveH = 1;
			walk(-1, 0, 2753, -1);
			c.getPlayerAssistant().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}

		if (hotSpot(3357, 2836)) {
			moveX = 3357;
			moveY = 2841;
			moveH = 2;
			walk(0, 1, 2753, -1);
			c.getPlayerAssistant().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}

		if (hotSpot(3357, 2846)) {
			moveX = 3357;
			moveY = 2849;
			moveH = 2;
			walk(0, 1, 2753, -1);
			c.getPlayerAssistant().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}

		if (hotSpot(3359, 2849)) {
			moveX = 3366;
			moveY = 2849;
			moveH = 2;
			walk(1, 0, 2753, -1);
			c.getPlayerAssistant().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}

		if (hotSpot(3372, 2841)) {
			moveX = 3372;
			moveY = 2836;
			moveH = 2;
			walk(0, -1, 2753, -1);
			c.getPlayerAssistant().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}

		if (hotSpot(3366, 2834)) {
			moveX = 3363;
			moveY = 2834;
			moveH = 2;
			walk(-1, 0, 2753, -1);
			c.getPlayerAssistant().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}

		if (hotSpot(3359, 2842)) {
			moveX = 3359;
			moveY = 2847;
			moveH = 3;
			walk(0, 1, 2753, -1);
			c.getPlayerAssistant().addSkillXP(14, c.playerAgility);
			agilityTimer = 2;
		}

		if (hotSpot(3370, 2843)) {
			moveX = 3370;
			moveY = 2840;
			moveH = 3;
			walk(0, -1, 2753, -1);
			c.getPlayerAssistant().addSkillXP(14, c.playerAgility);
		}

		if (agilityTimer > 0) {
			agilityTimer--;
		}

		if (agilityTimer == 0) {
			c.getPlayerAssistant().movePlayer(moveX, moveY, moveH);
			moveX = -1;
			moveY = -1;
			moveH = 0;
			agilityTimer = -1;
			System.out.println("Bam");
		}

	}

	public boolean checkLevel(int objectId) {
		if (getLevelRequired(objectId) > c.playerLevel[c.playerAgility]) {
			c.getActionSender().sendMessage(
					"You need atleast " + getLevelRequired(objectId)
							+ " agility to do this.");
			return true;
		}
		return false;
	}

	static int changeObjectTimer = 10;
	static int rndChance;
	static int newObjectX, newObjectY;

	public static void Brimhavenprocess() {
		if (changeObjectTimer > 0) {
			changeObjectTimer--;
		}
		if (changeObjectTimer == 0) {
			rndChance = Misc.random(3);
			if (rndChance == 0) {
				newObjectX = 2794;
				newObjectY = 9579;
			} else if (rndChance == 1) {
				newObjectX = 2783;
				newObjectY = 9579;
			} else if (rndChance == 2) {
				newObjectX = 2783;
				newObjectY = 9568;
			} else if (rndChance == 3) {
				newObjectX = 2794;
				newObjectY = 9568;
			}
			for (Player player : PlayerHandler.players) {
				if (player != null) {
					Client c = (Client) player;
					c.getActionSender().createObjectHints(newObjectX,
							newObjectY, 130, 2);
					System.out.println("Updated");
				}
			}
			changeObjectTimer = 10;
		}
	}

	public static double getAgilityRunRestore(Client c) {
		return 2260 - c.playerLevel[Constants.AGILITY] * 10;
	}
}
