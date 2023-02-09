package com.rs2.game.objects.impl;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.quests.QuestAssistant;
import com.rs2.game.items.impl.LightSources;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

/**
 * Climbing handles stairs, ladders
 * @author Andrew (Mr Extremez)
 */

public class Climbing {

	private static final int CLIMB_UP = 828, CLIMB_DOWN = CLIMB_UP;

	public static void handleClimbing(final Player client) {
		if (System.currentTimeMillis() - client.climbDelay < 1200) {
			return;
		}
		client.stopPlayer = true;
		CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				processClimbing(client);
				client.getPlayerAssistant().requestUpdates();
				container.stop();
			}

			@Override
			public void stop() {
				client.climbDelay = System.currentTimeMillis();
				client.stopPlayer = false;
			}
		}, 1);
	}

	public static void processClimbing(Player client) {
		int chapionsGuildRequiredQP = Math.min(32, QuestAssistant.MAXIMUM_QUESTPOINTS);
		switch (client.objectId) {
			case 9584:
				if (client.objectX == 2932 && client.objectY == 3282) {
					client.getPlayerAssistant().movePlayer(2933, 3282, 0);
					client.resetWalkingQueue();
				}
				break;

			case 272:
				client.getPlayerAssistant().movePlayer(client.absX, client.absY, 1);
				client.resetWalkingQueue();
				break;


			case 273:
				client.getPlayerAssistant().movePlayer(client.absX, client.absY, 0);
				client.resetWalkingQueue();
				break;

			case 245:
				if (client.objectY == 3224) {
					client.getPlayerAssistant().movePlayer(client.absX, client.absY+2, 2);
					client.resetWalkingQueue();
				} else if (client.objectY == 3139 || client.objectX == 2835 || client.objectX == 2963) {
					client.getPlayerAssistant().movePlayer(client.absX+2, client.absY, 2);
					client.resetWalkingQueue();
				} else {
					client.getPlayerAssistant().movePlayer(client.absX-2, client.absY, 2);
					client.resetWalkingQueue();
				}
				break;
			case 246:
				if (client.objectY == 3224) {
					client.getPlayerAssistant().movePlayer(client.absX, client.absY-2, 1);
					client.resetWalkingQueue();
				} else if (client.objectY == 3139 || client.objectX == 2835 || client.objectX == 2963) {
					client.getPlayerAssistant().movePlayer(client.absX-2, client.absY, 1);
					client.resetWalkingQueue();
				} else {
					client.getPlayerAssistant().movePlayer(client.absX+2, client.absY, 1);
					client.resetWalkingQueue();
				}
				break;

			case 11888:
				if (client.absX == 2908 && client.absY == 3336) {
					climbUp(client);
					client.resetWalkingQueue();
				}
				break;

			case 4568:
				if (client.objectX == 2506 && client.objectY == 3640) {
					climbUp(client);
					client.resetWalkingQueue();
				}
				break;

			case 4569:
				if (client.objectX == 2506 && client.objectY == 3640) {
					handleLadder(client);
					client.resetWalkingQueue();
				}
				break;

			case 4570:
				if (client.objectX == 2506 && client.objectY == 3641) {
					climbDown(client);
					client.resetWalkingQueue();
				}
				break;

			case 11889:
				if (client.absX == 2908 && client.absY == 3336) {
					handleLadder(client);
					client.resetWalkingQueue();
				}
				break;

			case 11890:
				if (client.absX == 2908 && client.absY == 3336) {
					climbDown(client);
					client.resetWalkingQueue();
				}
				break;

			case 9582:
				if (client.objectX == 2931 && client.objectY == 3282) {
					client.getPlayerAssistant().movePlayer(2933, 3282, 1);
					client.resetWalkingQueue();
				}
				break;
			case 1722:
				if (client.objectX == 2590 && client.objectY == 3089 && client.heightLevel == 0 && client.absY == 3088) {
					client.getPlayerAssistant().movePlayer(client.absX, 3092, 1);
				} else if (client.objectX == 2590 && client.objectY == 3089 && client.heightLevel == 0 && client.absY != 3088) {
					return;
				}
				if (client.objectX == 3175 && client.objectY == 3420 && client.heightLevel == 0 && client.absX == 3177 && client.absX > 3418 && client.absX < 3425) {
					return;
				} else if (client.absY == 3423) {
					client.getPlayerAssistant().movePlayer(client.absX, 3419, 1);
					client.resetWalkingQueue();
				}
				if (client.absX == 3098) {
					client.getPlayerAssistant().movePlayer(3102, 3266, 1);
					client.resetWalkingQueue();
				} else if (client.absY == 3445) {
					client.getPlayerAssistant().movePlayer(3260, 3449, 1);
					client.resetWalkingQueue();
				} else if (client.absY == 3358 && client.questPoints >= chapionsGuildRequiredQP) {
					client.getPlayerAssistant().movePlayer(client.absX, 3354, 1);
					client.resetWalkingQueue();
				} else if (client.absY == 3358 && client.questPoints < chapionsGuildRequiredQP) {
					client.getPacketSender().sendMessage("You need " + chapionsGuildRequiredQP + " quest points to use these stairs.");
				} else if (client.absX == 3180) {
					client.getPlayerAssistant().movePlayer(3176, client.absY, 1);
					client.resetWalkingQueue();
				} else if (client.absX == 3159) {
					client.getPlayerAssistant().movePlayer(3155, 3435, 1);
					client.resetWalkingQueue();
				} else if (client.absX == 2661) {
					client.getPlayerAssistant().movePlayer(2665, client.absY, 1);
					client.resetWalkingQueue();
				} else if (client.absY == 3083) {
					client.getPlayerAssistant().movePlayer(client.absX, 3087, 2);
					client.resetWalkingQueue();
				} else if (client.absY == 3298) {
					client.getPlayerAssistant().movePlayer(client.absX, 3294, 1);
					client.resetWalkingQueue();
				} else if (client.absY == 3472) {
					client.getPlayerAssistant().movePlayer(client.absX, 3476, 1);
					client.resetWalkingQueue();
				}
				break;

			case 1723:
				if (client.absX == 3102) {
					client.getPlayerAssistant().movePlayer(3098, 3266, 0);
					client.resetWalkingQueue();
				} else if (client.absY == 3354 && client.questPoints >= chapionsGuildRequiredQP) {
					client.getPlayerAssistant().movePlayer(client.absX, 3358, 0);
					client.resetWalkingQueue();
				} else if (client.absY == 3358 && client.questPoints < chapionsGuildRequiredQP) {
					client.getPacketSender().sendMessage("You need " + chapionsGuildRequiredQP + " quest points to use these stairs");
				} else if (client.absY == 3449) {
					client.getPlayerAssistant().movePlayer(3259, 3445, 0);
					client.resetWalkingQueue();
				} else if (client.absX == 3155) {
					client.getPlayerAssistant().movePlayer(3159, 3435, 0);
					client.resetWalkingQueue();
				} else if (client.absX == 2665) {
					client.getPlayerAssistant().movePlayer(2661, client.absY, 0);
					client.resetWalkingQueue();
				} else if (client.absY == 3092) {
					client.getPlayerAssistant().movePlayer(client.absX, 3088, 0);
					client.resetWalkingQueue();
				} else if (client.absY == 3087) {
					client.getPlayerAssistant().movePlayer(client.absX, 3083, 1);
					client.resetWalkingQueue();
				} else if (client.absY == 3419) {
					client.getPlayerAssistant().movePlayer(client.absX, 3423, 0);
					client.resetWalkingQueue();
				} else if (client.absX == 3176) {
					client.getPlayerAssistant().movePlayer(3180, client.absY, 0);
					client.resetWalkingQueue();
				} else if (client.absY == 3321) {
					client.getPlayerAssistant().movePlayer(client.absX, 3325, 0);
					client.resetWalkingQueue();
				} else if (client.absY == 3294) {
					client.getPlayerAssistant().movePlayer(client.absX, 3298, 0);
					client.resetWalkingQueue();
				} else if (client.absY == 3476) {
					client.getPlayerAssistant().movePlayer(client.absX, 3472, 0);
					client.resetWalkingQueue();
				}
				break;

			case 1733:
				if (client.objectX == 2569 && client.objectY == 3122) {
					client.getPlayerAssistant().movePlayer(2569, 9525, 0);
					client.resetWalkingQueue();
				} else if (client.absX == 3186) {
					client.getPlayerAssistant().movePlayer(3190, 9834, 0);
					client.resetWalkingQueue();
				} else if (client.objectX == 2603 && client.objectY == 3078) {
					client.getPlayerAssistant().feature("using this staircase");
					client.resetWalkingQueue();
				} else if (client.objectX == 3058 && client.objectY == 3376) {
				    client.resetWalkingQueue();
				} else if (client.absX != 3186) {
					client.getPlayerAssistant().movePlayer(client.absX,
							client.absY + 6393, 0);
					client.resetWalkingQueue();
				}
				break;

			case 1734:
				if (client.objectX == 2569 && client.objectY == 9522) {
					client.getPlayerAssistant().movePlayer(2569, 3121, 0);
					client.resetWalkingQueue();
				} else if (client.absX == 3190) {
					client.getPlayerAssistant().movePlayer(3186, 3434, 0);
					client.resetWalkingQueue();
				} else if (client.objectX == 3059 && client.objectY == 9776) {
					client.getPlayerAssistant().movePlayer(3061,
							client.absY - 6400, 0);
					client.resetWalkingQueue();
				} else if (client.absX != 3190) {
					client.getPlayerAssistant().movePlayer(client.absX,
							client.absY - 6396, 0);
					client.resetWalkingQueue();
				}
				break;

			case 1737:
				if (client.absY == 3294) {
					client.getPlayerAssistant().movePlayer(2661, 3291, 1);
					client.resetWalkingQueue();
				} else if (client.absY == 3302) {
					client.getPlayerAssistant().movePlayer(2648, 3301, 1);
					client.resetWalkingQueue();
				} else if (client.absY == 3293) {
					client.getPlayerAssistant().movePlayer(2649, 3296, 1);
					client.resetWalkingQueue();
				}
				break;

			case 1736:
				if (client.absY == 3291) {
					client.getPlayerAssistant().movePlayer(2662, 3294, 0);
					client.resetWalkingQueue();
				} else if (client.absY == 3301) {
					client.getPlayerAssistant().movePlayer(2645, 3302, 0);
					client.resetWalkingQueue();
				} else if (client.absX == 2649) {
					client.getPlayerAssistant().movePlayer(2648, 3293, 0);
					client.resetWalkingQueue();
				}
				break;

			case 1742:
				if (client.objectX == 2445 && client.objectY == 3434) {
					client.getPlayerAssistant().movePlayer(2445, 3433, 1);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else if (client.objectX == 2444 && client.objectY == 3414) {
					client.getPlayerAssistant().movePlayer(2445, 3416, 1);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else if (client.objectX == 2455 && client.objectY == 3417) {
					client.getPlayerAssistant().movePlayer(2457, 3417, 1);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else if (client.objectX == 2461 && client.objectY == 3416) {
					client.getPlayerAssistant().movePlayer(2460, 3417, 1);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else if (client.objectX == 2440 && client.objectY == 3404) {
					client.getPlayerAssistant().movePlayer(2440, 3403, 1);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				}
				break;

			case 1744:
				if (client.objectX == 2445 && client.objectY == 3434) {
					client.getPlayerAssistant().movePlayer(2445, 3433, 0);
					client.startAnimation(CLIMB_DOWN);
					client.resetWalkingQueue();
				} else if (client.objectX == 2444 || client.objectX == 2445
						&& client.objectY == 3415) {
					client.getPlayerAssistant().movePlayer(2444, 3413, 0);
					client.startAnimation(CLIMB_DOWN);
					client.resetWalkingQueue();
				} else if (client.objectX == 2456 && client.objectY == 3417) {
					client.getPlayerAssistant().movePlayer(2457, 3417, 0);
					client.startAnimation(CLIMB_DOWN);
					client.resetWalkingQueue();
				} else if (client.objectX == 2461 && client.objectY == 3417) {
					client.getPlayerAssistant().movePlayer(2460, 3417, 0);
					client.startAnimation(CLIMB_DOWN);
					client.resetWalkingQueue();
				} else if (client.objectX == 2440 && client.objectY == 3404) {
					client.getPlayerAssistant().movePlayer(2440, 3403, 0);
					client.startAnimation(CLIMB_DOWN);
					client.resetWalkingQueue();
				}
				break;
			case 7257:
				client.getPlayerAssistant().movePlayer(3044, 4973, 1);
				client.startAnimation(827);
				client.getPacketSender().sendMessage("You climb down.");
				client.resetWalkingQueue();
				break;
			case 6279:
				if (client.getItemAssistant().playerHasItem(954, 1)) {
					client.getPlayerAssistant().movePlayer(3206, 9379, 0);
					client.startAnimation(827);
					client.getPacketSender().sendMessage("You climb down.");
					client.resetWalkingQueue();
				} else {
					client.getPacketSender().sendMessage(
							"You need a rope to enter.");
					return;
				}
				break;

			case 6436:
				UseOther.useUp(client, client.objectId);
				client.resetWalkingQueue();
				break;

			case 6434:
			case 5167:
				UseOther.useDown(client, client.objectId);
				client.resetWalkingQueue();
				break;
			case 1767:
				if (client.objectX == 3069 && client.objectY == 3856) {
					UseOther.useDown(client, client.objectId);
					client.resetWalkingQueue();
				}
				break;
			case 6439:
				client.getPlayerAssistant().movePlayer(3309, 2963, 0);
				client.getPacketSender().sendMessage("You climb up.");
				client.resetWalkingQueue();
				break;

			case 2408:
				if (client.playerLevel[Constants.PRAYER] > 0) {
					client.playerLevel[Constants.PRAYER] = 0;
				}
				client.getPlayerAssistant().refreshSkill(Constants.PRAYER);
				client.getPacketSender().sendMessage(
						"Your prayer is drained as you enter the dungeon.");
				client.getPlayerAssistant().movePlayer(2823, 9771, 0);
				client.startAnimation(827);
				client.getPacketSender().sendMessage("You climb down.");
				client.resetWalkingQueue();
				break;
				case 2147:
				client.getPlayerAssistant().movePlayer(3104, 9576, 0);
				client.startAnimation(827);
				client.getPacketSender().sendMessage("You climb down.");
					client.resetWalkingQueue();
				break;
			case 2148:
				client.getPlayerAssistant().movePlayer(3103, 3162, 0);
				client.startAnimation(828);
				client.getPacketSender().sendMessage("You climb up.");
				client.resetWalkingQueue();
				break;
			case 4383:
				client.getPlayerAssistant().movePlayer(2515, 10007, 0);
				client.startAnimation(827);
				client.getPacketSender().sendMessage("You climb down.");
				client.resetWalkingQueue();
				break;
			case 5131:
				client.getPlayerAssistant().movePlayer(3549, 9865, 0);
				client.getPacketSender().sendMessage("You climb down.");
				client.resetWalkingQueue();
				break;
			case 5130:
				client.getPlayerAssistant().movePlayer(3543, 3463, 0);
				client.getPacketSender().sendMessage("You climb up.");
				client.resetWalkingQueue();
				break;
			case 4413:
				client.getPlayerAssistant().movePlayer(2510, 3644, 0);
				client.startAnimation(828);
				client.getPacketSender().sendMessage("You climb up.");
				client.resetWalkingQueue();
				break;
			case 3432:
				client.getPlayerAssistant().movePlayer(3440, 9887, 0);
				client.resetWalkingQueue();
				break;
			case 1738:
				if (client.objectX == 2728 && client.objectY == 3460
						&& client.heightLevel == 0) {
					client.getPlayerAssistant().movePlayer(2729, 3462, 1);
					client.resetWalkingQueue();
				} else if (client.objectX == 2746 && client.objectY == 3460
						&& client.heightLevel == 0) {
					client.getPlayerAssistant().movePlayer(2745, 3461, 1);
					client.resetWalkingQueue();
				} else if (client.objectX == 2648 && client.objectY == 3310) {
					Climbing.climbUp(client);
					client.resetWalkingQueue();
				} else if (client.objectX == 2673 && client.objectY == 3300) {
					client.getPlayerAssistant().movePlayer(2675, 3300, 1);
					client.resetWalkingQueue();
				} else if (client.objectX == 3204 && client.objectY == 3207) {
					client.getPlayerAssistant().movePlayer(3205, 3209, 1);
					client.resetWalkingQueue();
				} else if (client.objectX == 3204 && client.objectY == 3229) {
					client.getPlayerAssistant().movePlayer(3205, 3228, 1);
					client.resetWalkingQueue();
				} else if (client.objectX == 3258 && client.objectY == 3487) {
					client.getPlayerAssistant().movePlayer(3257, 3487, 1);
					client.resetWalkingQueue();
				} else if (client.objectX == 3144 && client.objectY == 3447 && client.playerLevel[Constants.COOKING] > 31 && client.playerEquipment[client.playerHat] == 1949) {
					client.getPlayerAssistant().movePlayer(3143, 3448, 1);
					client.resetWalkingQueue();
				} else if (client.objectX == 3010 && client.objectY == 3515) {
					client.getPlayerAssistant().movePlayer(3012, 3515, 1);
					client.resetWalkingQueue();
				} else if (client.objectX == 2895 && client.objectY == 3513) {
					client.getPlayerAssistant().movePlayer(2897, 3513, 1);
					client.resetWalkingQueue();
				} else if (client.objectX == 3501 && client.objectY == 3475) {
					client.getPlayerAssistant().movePlayer(3500, 3476, 1);
					client.resetWalkingQueue();
				}
				break;
			case 3443:
				client.getPlayerAssistant().movePlayer(3423, 3485, 0);
				break;
			case 1754:
				if (client.objectX == 3116 && client.objectY == 3452) {
					client.getPlayerAssistant().movePlayer(3222, 3218, 0);
					client.startAnimation(CLIMB_DOWN);
					client.resetWalkingQueue();
				}
					break;
			case 1755:
				if (client.objectX == 3116 && client.objectY == 9852) {
					client.getPlayerAssistant().movePlayer(3116, 3451, 0);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else if (client.objectX == 3097 && client.objectY == 9867) {
					client.getPlayerAssistant().movePlayer(3096, 3468, 0);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else if (client.objectX == 3237 && client.objectY == 9858) {
					client.getPlayerAssistant().movePlayer(3238, 3458, 0);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else if (client.objectX == 3088 && client.objectY == 9971) {
					UseOther.useUp(client, client.objectId);
					client.resetWalkingQueue();
					// client.startAnimation(CLIMB_UP);
				} else if (client.objectX == 3209 && client.objectY == 9616) {
					// OtherObjects.useUp(client, client.objectId);
					client.getPlayerAssistant().movePlayer(3209, 3215, 0);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
					client.getPacketSender().sendMessage("You climb up.");
				} else if (client.objectX == 3019 && client.objectY == 9740) {// noord
					client.getPlayerAssistant().movePlayer(3019, 3341, 0);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else if (client.objectX == 3020 && client.objectY == 9739) {// oost
					client.getPlayerAssistant().movePlayer(3021, 3339, 0);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else if (client.objectX == 3018 && client.objectY == 9739) {// wst
					client.getPlayerAssistant().movePlayer(3017, 3339, 0);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else if (client.objectX == 3019 && client.objectY == 9738) {// zuid
					client.getPlayerAssistant().movePlayer(3019, 3337, 0);
					client.startAnimation(CLIMB_UP);
					client.resetWalkingQueue();
				} else {
					UseOther.useUp(client, client.objectId);
				}
				break;

			case 2405:
				UseOther.useUp(client, client.objectId);
				client.resetWalkingQueue();
				break;

			case 98:
				if (client.inWild()) {
					return;
				}
				if (!LightSources.playerHasLightSource(client)) {
					client.getPlayerAssistant().movePlayer(2641, 9740, 0);
					client.resetWalkingQueue();
					return;
				} else if (LightSources.playerHasLightSource(client)) {
					client.getPlayerAssistant().movePlayer(2641, 9764, 0);
					client.resetWalkingQueue();
					return;
				}
				break;

			case 96:
				if (client.inWild()) {
					return;
				}
				LightSources.brightness3(client);
				client.getPlayerAssistant().movePlayer(2649, 9804, 0);
				client.resetWalkingQueue();
				break;

			case 2711:
				if (client.absY == 3325) {
					client.getPlayerAssistant().movePlayer(client.absX, 3321, 1);
					client.resetWalkingQueue();
				}
				break;

			case 4755:
				if (client.absY == 2797) {
					client.getPlayerAssistant().movePlayer(client.absX, 2793, 0);
					client.resetWalkingQueue();
				}
				break;

			case 4756:
				if (client.absY == 2793) {
					client.getPlayerAssistant().movePlayer(client.absX, 2797, 1);
					client.resetWalkingQueue();
				}
				break;

			case 4879:
				client.getPlayerAssistant().movePlayer(2807, 9200, 0);
				client.getPacketSender().sendMessage(
						"You go down the trapdoor.");
				client.startAnimation(827);
				client.getPacketSender().closeAllWindows();
				client.resetWalkingQueue();
				break;

			case 5492:
				if (client.getItemAssistant().playerHasItem(1523, 1)
						&& Misc.random(4) < 3) {
					client.getPlayerAssistant().movePlayer(3149, 9652, 0);
					client.getPacketSender().sendMessage(
							"You go down the trapdoor.");
					client.startAnimation(827);
					client.getPlayerAssistant().addSkillXP(.5,
							Constants.THIEVING);
					client.getPacketSender().closeAllWindows();
				} else if (!client.getItemAssistant().playerHasItem(1523, 1)
						&& Misc.random(5) < 2) {
					client.getPlayerAssistant().movePlayer(3149, 9652, 0);
					client.getPacketSender().sendMessage(
							"You go down the trapdoor.");
					client.startAnimation(827);
					client.getPlayerAssistant().addSkillXP(.5,
							Constants.THIEVING);
					client.getPacketSender().closeAllWindows();
					client.resetWalkingQueue();
				} else if (client.getItemAssistant().playerHasItem(1523, 1)
						&& Misc.random(4) > 3) {
					client.getPacketSender().sendMessage(
							"You fail to pick the lock.");
					client.getPacketSender()
							.sendMessage(
									"Your thieving has been drained, your fingers feel numb.");
					client.playerLevel[Constants.THIEVING] = client.getPlayerAssistant()
							.getLevelForXP(client.playerXP[Constants.THIEVING]) - 1;
					client.getPlayerAssistant().refreshSkill(Constants.THIEVING);
					client.getItemAssistant().deleteItem(1523, 1);
				} else if (!client.getItemAssistant().playerHasItem(1523, 1)
						&& Misc.random(5) > 2) {
					client.getPacketSender().sendMessage(
							"You fail to pick the lock.");
					client.getPacketSender()
							.sendMessage(
									"Your thieving has been drained, your fingers feel numb.");
					client.playerLevel[Constants.THIEVING] = client.getPlayerAssistant()
							.getLevelForXP(client.playerXP[Constants.THIEVING]) - 1;
					client.getPlayerAssistant().refreshSkill(Constants.THIEVING);
				}
				break;

			case 6278:
				if (client.objectX == 2637 && client.objectY == 3408) {
					UseOther.useDown(client, 6278);
				}
				break;

			case 11724:
				if (client.absX == 2971) {
					client.getPlayerAssistant().movePlayer(2968, 3348, 1);
					client.resetWalkingQueue();
				}
				break;

			case 11725:
				if (client.absY == 3348) {
					client.getPlayerAssistant().movePlayer(2971, 3347, 0);
					client.resetWalkingQueue();
				}
				break;

			case 1725:
				if (client.absY == 3376) {
					client.getPlayerAssistant().movePlayer(client.absX, 3380, 1);
					client.resetWalkingQueue();
				} else if (client.absX == 3285 || client.absX == 3286 && client.heightLevel == 0) {
					client.getPlayerAssistant().movePlayer(client.absX, 3492, 1);
					client.resetWalkingQueue();
				} else if (client.absY == 3509) {
					client.getPlayerAssistant().movePlayer(2751, 3513, 1);
					client.resetWalkingQueue();
				} else if (client.absX == 3226) {
					client.getPlayerAssistant().movePlayer(3230, 3394, 1);
					client.resetWalkingQueue();
				}
				break;

			case 5096:
				client.getPlayerAssistant().movePlayer(2649, 9591, 0);
				client.resetWalkingQueue();
				break;

			case 1726:
				if (client.absY == 3380 ) {
					client.getPlayerAssistant().movePlayer(client.absX, 3376, 0);
					client.resetWalkingQueue();
				} else if (client.absY == 3513) {
					client.getPlayerAssistant().movePlayer(2751, 3509, 0);
					client.resetWalkingQueue();
				} else if (client.absX == 3230) {
					client.getPlayerAssistant().movePlayer(3226, 3394, 0);
					client.resetWalkingQueue();
				} else if (client.absX == 3285 || client.absX == 3286
						&& client.heightLevel == 1) {
					client.getPlayerAssistant().movePlayer(client.absX, 3496, 0);
					client.resetWalkingQueue();
				}
				break;

			case 11727:
				if (client.absY == 3350 || client.absY == 3351
						|| client.absY == 3340 || client.absY == 3341
						|| client.absY == 3342) {
					climbUp(client);
					client.resetWalkingQueue();
				}
				break;

			case 11728:
				if (client.absY == 3350 || client.absY == 3351
						|| client.absY == 3340 || client.absY == 3341
						|| client.absY == 3342) {
					climbDown(client);
					client.resetWalkingQueue();
				}
				break;

			case 11729:
				if (client.objectX == 2954 && client.objectY == 3338) {
					client.getPlayerAssistant().movePlayer(2956, 3338, 1);
					client.resetWalkingQueue();
				} else if (client.objectX == 2960 && client.objectY == 3338) {
					client.getPlayerAssistant().movePlayer(2959, 3339, 2);
					client.resetWalkingQueue();
				} else if (client.objectX == 2957 && client.objectY == 3338) {
					client.getPlayerAssistant().movePlayer(2959, 3338, 3);
					client.resetWalkingQueue();
				}
				break;

			case 11731:
				if (client.objectX == 2955 && client.objectY == 3338) {
					client.getPlayerAssistant().movePlayer(2956, 3338, 0);
				} else if (client.objectX == 2960 && client.objectY == 3339) {
					client.getPlayerAssistant().movePlayer(2959, 3338, 1);
				} else if (client.objectX == 2958 && client.objectY == 3338) {
					client.getPlayerAssistant().movePlayer(2957, 3337, 2);
				}
				break;

			case 11732:
				if (client.objectX == 3034 && client.objectY == 3363) {
					client.getPlayerAssistant().movePlayer(3036, 3363, 1);
				} else if (client.objectX == 3048 && client.objectY == 3352) {
					client.getPlayerAssistant().movePlayer(3049, 3354, 1);
				}
				break;

			case 11733:
				if (client.objectX == 3035 && client.objectY == 3363) {
					client.getPlayerAssistant().movePlayer(3036, 3362, 0);
				} else if (client.objectX == 3049 && client.objectY == 3353) {
					client.getPlayerAssistant().movePlayer(3049, 3354, 0);
				}
				break;

			case 11734:
				if (client.absY == 3336) {
					client.getPlayerAssistant().movePlayer(2984, 3340, 2);
				} else if (client.absY == 3380) {
					client.getPlayerAssistant().movePlayer(client.absX, 3384, 1);
				}
				break;

			case 11735:
				if (client.absY == 3340) {
					client.getPlayerAssistant().movePlayer(2984, 3336, 1);
				} else if (client.absY == 3384) {
					client.getPlayerAssistant().movePlayer(client.absX, 3380, 0);
				}
				break;

			case 11736:
				if (client.absY == 3368) {
					client.getPlayerAssistant().movePlayer(client.absX, 3372, 1);
				} else if (client.absY == 3362) {
					client.getPlayerAssistant().movePlayer(client.absX, 3366, 1);
				}
				break;

			case 11737:
				if (client.absY == 3366) {
					client.getPlayerAssistant().movePlayer(client.absX, 3362, 0);
				} else {
					client.getPlayerAssistant().movePlayer(client.absX, 3368, 0);
				}
				break;

			case 12266:
				if (client.objectX == 3077 && client.objectY == 3493) {
					client.getPlayerAssistant().movePlayer(3077, 9893, 0);
					client.startAnimation(827);
					client.resetWalkingQueue();
					client.getPacketSender().sendMessage("You climb down.");
				}
				break;

			case 12265:
				if (client.objectX == 3076 && client.objectY == 9893) {
					client.getPlayerAssistant().movePlayer(3078, 3493, 0);
					client.startAnimation(828);
					client.resetWalkingQueue();
					client.getPacketSender().sendMessage("You climb up.");
				}
				break;
		}
	}

	public static void climbUp(Player player) {
		if (System.currentTimeMillis() - player.climbDelay < 1200) {
			return;
		}

		switch (player.heightLevel) {
			case -1:
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 0);
				player.climbDelay = System.currentTimeMillis();
				player.getPacketSender().sendMessage("You climb up.");
				player.startAnimation(CLIMB_UP);
				player.resetWalkingQueue();
				player.getPacketSender().closeAllWindows();
				break;
			case 0:
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 1);
				player.climbDelay = System.currentTimeMillis();
				player.getPacketSender().sendMessage("You climb up.");
				player.startAnimation(CLIMB_UP);
				player.resetWalkingQueue();
				player.getPacketSender().closeAllWindows();
				break;
			case 1:
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 2);
				player.climbDelay = System.currentTimeMillis();
				player.getPacketSender().sendMessage("You climb up.");
				player.startAnimation(CLIMB_UP);
				player.resetWalkingQueue();
				player.getPacketSender().closeAllWindows();
				break;
			case 2:
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 3);
				player.climbDelay = System.currentTimeMillis();
				player.getPacketSender().sendMessage("You climb up.");
				player.startAnimation(CLIMB_UP);
				player.resetWalkingQueue();
				player.getPacketSender().closeAllWindows();
				break;
			default:
				if (player.heightLevel > 3) {
					climbDown(player);
					player.resetWalkingQueue();
				}
				player.getPacketSender().sendMessage("This object is currently not supported.");
				System.out.println("Bug detected with climbing up object " + player.objectId + " objectX " + player.objectX + " objectY " + player.objectY + ".");
				break;
		}
	}

	public static void climbDown(Player player) {
		if (System.currentTimeMillis() - player.climbDelay < 1200) {
			return;
		}
		if (player.heightLevel > 3) {
			player.getPlayerAssistant().movePlayer(player.absX, player.absY, 3);
			player.climbDelay = System.currentTimeMillis();
			player.getPacketSender().sendMessage("You climb down.");
			player.startAnimation(CLIMB_DOWN);
			player.resetWalkingQueue();
			player.getPacketSender().closeAllWindows();
		}
		switch (player.heightLevel) {
			case 1:
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 0);
				player.climbDelay = System.currentTimeMillis();
				player.getPacketSender().sendMessage("You climb down.");
				player.startAnimation(CLIMB_DOWN);
				player.resetWalkingQueue();
				player.getPacketSender().closeAllWindows();
				break;
			case 2:
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 1);
				player.climbDelay = System.currentTimeMillis();
				player.getPacketSender().sendMessage("You climb down.");
				player.startAnimation(CLIMB_DOWN);
				player.resetWalkingQueue();
				player.getPacketSender().closeAllWindows();
				break;
			case 3:
				player.getPlayerAssistant().movePlayer(player.absX, player.absY, 2);
				player.climbDelay = System.currentTimeMillis();
				player.getPacketSender().sendMessage("You climb down.");
				player.startAnimation(CLIMB_DOWN);
				player.resetWalkingQueue();
				player.getPacketSender().closeAllWindows();
				break;
			default:
				if (player.heightLevel < 0) {
					climbUp(player);
				}
				player.getPacketSender().sendMessage("This object is currently not supported.");
				System.out.println("Bug detected with climbing down object " + player.objectId + " objectX " + player.objectX + " objectY " + player.objectY + ".");
				break;
		}
	}

	public static void handleLadder(Player player) {
		player.getDialogueHandler().sendOption("Climb Up.", "Climb Down.");
		player.dialogueAction = 147;
	}

	private static void close(Player client, int actionButtonId) {
		if (actionButtonId == 9157) {
			client.getPacketSender().sendMessage("You climb up.");
			client.startAnimation(CLIMB_UP);
			client.resetWalkingQueue();
		} else if (actionButtonId == 9158) {
			client.getPacketSender().sendMessage("You climb down.");
			client.startAnimation(827);
			client.resetWalkingQueue();
		}
		client.getPacketSender().closeAllWindows();
		client.nextChat = 0;
	}

	public static void handleLadderButtons(Player client, int actionButtonId) {
		if (client.dialogueAction != 147) {
			return;
		}
		switch (actionButtonId) {
			case 9157:
				if (client.heightLevel == 1) {
					client.getPlayerAssistant().movePlayer(client.absX, client.absY, 2);
					close(client, actionButtonId);
				} else if (client.heightLevel == 2) {
					client.getPlayerAssistant().movePlayer(client.absX, client.absY, 3);
					close(client, actionButtonId);
				}
				break;
			case 9158:
				if (client.heightLevel == 2) {
					client.getPlayerAssistant().movePlayer(client.absX, client.absY, 1);
					close(client, actionButtonId);
				} else if (client.heightLevel == 1) {
					client.getPlayerAssistant().movePlayer(client.absX, client.absY, 0);
					close(client, actionButtonId);
				}
				break;
		}
	}
}