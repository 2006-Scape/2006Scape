package redone.game.objects.impl;

import redone.Constants;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.quests.QuestAssistant;
import redone.game.items.impl.LightSources;
import redone.game.players.Client;
import redone.util.Misc;

/**
 * Climbing handles stairs, ladders, trapdoors
 * 
 * @author Andrew
 */

public class Climbing {

	private static final int CLIMB_UP = 828, CLIMB_DOWN = CLIMB_UP;
	
	public static void handleClimbing(final Client client) {
		if (System.currentTimeMillis() - client.climbDelay < 1200) {
			return;
		}
		client.stopPlayer = true;
		CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				client.resetWalkingQueue();
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
	
	public static void processClimbing(Client client) {
		switch (client.objectId) {
		case 9584:
			if (client.objectX == 2932 && client.objectY == 3282) {
				client.getPlayerAssistant().movePlayer(2933, 3282, 0);
			}
			break;
			
		case 272:
			client.getPlayerAssistant().movePlayer(client.absX, client.absY, 1);
			break;
			
				
			case 273:
				client.getPlayerAssistant().movePlayer(client.absX, client.absY, 0);
			break;
			
			case 245:
			if (client.objectY == 3224) {
				client.getPlayerAssistant().movePlayer(client.absX, client.absY+2, 2);
			} else if (client.objectY == 3139 || client.objectX == 2835 || client.objectX == 2963) {
				client.getPlayerAssistant().movePlayer(client.absX+2, client.absY, 2);
			} else {
				client.getPlayerAssistant().movePlayer(client.absX-2, client.absY, 2);
			}
			break;
			case 246:
			if (client.objectY == 3224) {
				client.getPlayerAssistant().movePlayer(client.absX, client.absY-2, 1);
			} else if (client.objectY == 3139 || client.objectX == 2835 || client.objectX == 2963) {
				client.getPlayerAssistant().movePlayer(client.absX-2, client.absY, 1);;
			} else {
				client.getPlayerAssistant().movePlayer(client.absX+2, client.absY, 1);
			}
			break;

		case 11888:
			if (client.absX == 2908 && client.absY == 3336) {
				climbUp(client);
			}
			break;

		case 4568:
			if (client.objectX == 2506 && client.objectY == 3640) {
				climbUp(client);
			}
			break;

		case 4569:
			if (client.objectX == 2506 && client.objectY == 3640) {
				handleLadder(client);
			}
			break;

		case 4570:
			if (client.objectX == 2506 && client.objectY == 3641) {
				climbDown(client);
			}
			break;

		case 11889:
			if (client.absX == 2908 && client.absY == 3336) {
				handleLadder(client);
			}
			break;

		case 11890:
			if (client.absX == 2908 && client.absY == 3336) {
				climbDown(client);
			}
			break;

		case 9582:
			if (client.objectX == 2931 && client.objectY == 3282) {
				client.getPlayerAssistant().movePlayer(2933, 3282, 1);
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
			}
			if (client.absX == 3098) {
				client.getPlayerAssistant().movePlayer(3102, 3266, 1);
			} else if (client.absY == 3445) {
				client.getPlayerAssistant().movePlayer(3260, 3449, 1);
			} else if (client.absY == 3358
					&& client.questPoints >= QuestAssistant.MAXIMUM_QUESTPOINTS) {
				client.getPlayerAssistant().movePlayer(client.absX, 3354, 1);
			} else if (client.absY == 3358
					&& client.questPoints < QuestAssistant.MAXIMUM_QUESTPOINTS) {
				client.getActionSender().sendMessage(
						"You need " + QuestAssistant.MAXIMUM_QUESTPOINTS
								+ " quest points to use these stairs.");
			} else if (client.absX == 3180) {
				client.getPlayerAssistant().movePlayer(3176, client.absY, 1);
			} else if (client.absX == 3159) {
				client.getPlayerAssistant().movePlayer(3155, 3435, 1);
			} else if (client.absX == 2661) {
				client.getPlayerAssistant().movePlayer(2665, client.absY, 1);
			} else if (client.absY == 3083) {
				client.getPlayerAssistant().movePlayer(client.absX, 3087, 2);
			} else if (client.absY == 3298) {
				client.getPlayerAssistant().movePlayer(client.absX, 3294, 1);
			} else if (client.absY == 3472) {
				client.getPlayerAssistant().movePlayer(client.absX, 3476, 1);
			}
			break;

		case 1723:
			if (client.absX == 3102) {
				client.getPlayerAssistant().movePlayer(3098, 3266, 0);
			} else if (client.absY == 3354
					&& client.questPoints >= QuestAssistant.MAXIMUM_QUESTPOINTS) {
				client.getPlayerAssistant().movePlayer(client.absX, 3358, 0);
			} else if (client.absY == 3358
					&& client.questPoints < QuestAssistant.MAXIMUM_QUESTPOINTS) {
				client.getActionSender().sendMessage(
						"You need " + QuestAssistant.MAXIMUM_QUESTPOINTS
								+ " quest points to use these stairs");
			} else if (client.absY == 3449) {
				client.getPlayerAssistant().movePlayer(3259, 3445, 0);
			} else if (client.absX == 3155) {
				client.getPlayerAssistant().movePlayer(3159, 3435, 0);
			} else if (client.absX == 2665) {
				client.getPlayerAssistant().movePlayer(2661, client.absY, 0);
			} else if (client.absY == 3092) {
				client.getPlayerAssistant().movePlayer(client.absX, 3088, 0);
			} else if (client.absY == 3087) {
				client.getPlayerAssistant().movePlayer(client.absX, 3083, 1);
			} else if (client.absY == 3419) {
				client.getPlayerAssistant().movePlayer(client.absX, 3423, 0);
			} else if (client.absX == 3176) {
				client.getPlayerAssistant().movePlayer(3180, client.absY, 0);
			} else if (client.absY == 3321) {
				client.getPlayerAssistant().movePlayer(client.absX, 3325, 0);
			} else if (client.absY == 3294) {
				client.getPlayerAssistant().movePlayer(client.absX, 3298, 0);
			} else if (client.absY == 3476) {
				client.getPlayerAssistant().movePlayer(client.absX, 3472, 0);
			}
			break;

		case 1733:
			if (client.objectX == 2569 && client.objectY == 3122) {
				client.getPlayerAssistant().movePlayer(2569, 9525, 0);
			} else if (client.absX == 3186) {
				client.getPlayerAssistant().movePlayer(3190, 9834, 0);
			} else if (client.objectX == 2603 && client.objectY == 3078) {
				client.getPlayerAssistant().feature("using this staircase");
			} else if (client.absX != 3186) {
				client.getPlayerAssistant().movePlayer(client.absX,
						client.absY + 6393, 0);
			}
			break;

		case 1734:
			if (client.objectX == 2569 && client.objectY == 9522) {
				client.getPlayerAssistant().movePlayer(2569, 3121, 0);
			} else if (client.absX == 3190) {
				client.getPlayerAssistant().movePlayer(3186, 3434, 0);
			} else if (client.objectX == 3059 && client.objectY == 9776) {
				client.getPlayerAssistant().movePlayer(3061,
						client.absY - 6400, 0);
			} else if (client.absX != 3190) {
				client.getPlayerAssistant().movePlayer(client.absX,
						client.absY - 6396, 0);
			}
			break;

		case 1737:
			if (client.absY == 3294) {
				client.getPlayerAssistant().movePlayer(2661, 3291, 1);
			} else if (client.absY == 3302) {
				client.getPlayerAssistant().movePlayer(2648, 3301, 1);
			} else if (client.absY == 3293) {
				client.getPlayerAssistant().movePlayer(2649, 3296, 1);
			}
			break;

		case 1736:
			if (client.absY == 3291) {
				client.getPlayerAssistant().movePlayer(2662, 3294, 0);
			} else if (client.absY == 3301) {
				client.getPlayerAssistant().movePlayer(2645, 3302, 0);
			} else if (client.absX == 2649) {
				client.getPlayerAssistant().movePlayer(2648, 3293, 0);
			}
			break;

		case 1742:
			if (client.objectX == 2445 && client.objectY == 3434) {
				client.getPlayerAssistant().movePlayer(2445, 3433, 1);
				client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 2444 && client.objectY == 3414) {
				client.getPlayerAssistant().movePlayer(2445, 3416, 1);
				client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 2455 && client.objectY == 3417) {
				client.getPlayerAssistant().movePlayer(2457, 3417, 1);
				client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 2461 && client.objectY == 3416) {
				client.getPlayerAssistant().movePlayer(2460, 3417, 1);
				client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 2440 && client.objectY == 3404) {
				client.getPlayerAssistant().movePlayer(2440, 3403, 1);
				client.startAnimation(CLIMB_UP);
			}
			break;

		case 1744:
			if (client.objectX == 2445 && client.objectY == 3434) {
				client.getPlayerAssistant().movePlayer(2445, 3433, 0);
				client.startAnimation(CLIMB_DOWN);
			} else if (client.objectX == 2444 || client.objectX == 2445
					&& client.objectY == 3415) {
				client.getPlayerAssistant().movePlayer(2444, 3413, 0);
				client.startAnimation(CLIMB_DOWN);
			} else if (client.objectX == 2456 && client.objectY == 3417) {
				client.getPlayerAssistant().movePlayer(2457, 3417, 0);
				client.startAnimation(CLIMB_DOWN);
			} else if (client.objectX == 2461 && client.objectY == 3417) {
				client.getPlayerAssistant().movePlayer(2460, 3417, 0);
				client.startAnimation(CLIMB_DOWN);
			} else if (client.objectX == 2440 && client.objectY == 3404) {
				client.getPlayerAssistant().movePlayer(2440, 3403, 0);
				client.startAnimation(CLIMB_DOWN);
			}
			break;
		case 7257:
			client.getPlayerAssistant().movePlayer(3044, 4973, 1);
			client.startAnimation(827);
			client.getActionSender().sendMessage("You climb down.");
			break;
		case 6279:
			if (client.getItemAssistant().playerHasItem(954, 1)) {
				client.getPlayerAssistant().movePlayer(3206, 9379, 0);
				client.startAnimation(827);
				client.getActionSender().sendMessage("You climb down.");
			} else {
				client.getActionSender().sendMessage(
						"You need a rope to enter.");
				return;
			}
			break;

		case 6436:
			UseOther.useUp(client, client.objectId);
			break;

		case 6434:
			UseOther.useDown(client, client.objectId);
			break;
		case 1767:
		if (client.objectX == 3069 && client.objectY == 3856) {
			UseOther.useDown(client, client.objectId);
		}
		break;
		case 6439:
			client.getPlayerAssistant().movePlayer(3309, 2963, 0);
			client.getActionSender().sendMessage("You climb up.");
			break;

		case 2408:
			if (client.playerLevel[5] > 0) {
				client.playerLevel[5] = 0;
			}
			client.getPlayerAssistant().refreshSkill(5);
			client.getActionSender().sendMessage(
					"Your prayer is drained as you enter the dungeon.");
			client.getPlayerAssistant().movePlayer(2823, 9771, 0);
			client.startAnimation(827);
			client.getActionSender().sendMessage("You climb down.");

			break;
		case 5167:
			if (Constants.EXPERIMENTS) {
				UseOther.useDown(client, client.objectId);
			} else {
				client.getActionSender().sendMessage(
						"Experiments are currently disabled.");
			}
			break;
		case 2147:
			client.getPlayerAssistant().movePlayer(3104, 9576, 0);
			client.startAnimation(827);
			client.getActionSender().sendMessage("You climb down.");
			break;
		case 2148:
			client.getPlayerAssistant().movePlayer(3103, 3162, 0);
			client.startAnimation(828);
			client.getActionSender().sendMessage("You climb up.");
			break;
		case 4383:
			client.getPlayerAssistant().movePlayer(2515, 10007, 0);
			client.startAnimation(827);
			client.getActionSender().sendMessage("You climb down.");
			break;
		case 5131:
			client.getPlayerAssistant().movePlayer(3549, 9865, 0);
			client.getActionSender().sendMessage("You climb down.");
			break;
		case 5130:
			client.getPlayerAssistant().movePlayer(3543, 3463, 0);
			client.getActionSender().sendMessage("You climb up.");
			break;
		case 4413:
			client.getPlayerAssistant().movePlayer(2510, 3644, 0);
			client.startAnimation(828);
			client.getActionSender().sendMessage("You climb up.");
			break;
		case 3432:
			client.getPlayerAssistant().movePlayer(3440, 9887, 0);
			break;
		case 1738:
			if (client.objectX == 2728 && client.objectY == 3460
					&& client.heightLevel == 0) {
				client.getPlayerAssistant().movePlayer(2749, 3462, 1);
			} else if (client.objectX == 2746 && client.objectY == 3460
					&& client.heightLevel == 0) {
				client.getPlayerAssistant().movePlayer(2745, 3461, 1);
			} else if (client.objectX == 2648 && client.objectY == 3310) {
				Climbing.climbUp(client);
			} else if (client.objectX == 2673 && client.objectY == 3300) {
				client.getPlayerAssistant().movePlayer(2675, 3300, 1);
			} else if (client.objectX == 3204 && client.objectY == 3207) {
				client.getPlayerAssistant().movePlayer(3205, 3209, 1);
			} else if (client.objectX == 3204 && client.objectY == 3229) {
				client.getPlayerAssistant().movePlayer(3205, 3228, 1);
			} else if (client.objectX == 3258 && client.objectY == 3487) {
				client.getPlayerAssistant().movePlayer(3257, 3487, 1);
			} else if (client.objectX == 3144 && client.objectY == 3447 && client.playerLevel[client.playerCooking] > 31 && client.playerEquipment[client.playerHat] == 1949) {
				client.getPlayerAssistant().movePlayer(3143, 3448, 1);
			} else if (client.objectX == 3010 && client.objectY == 3515) {
				client.getPlayerAssistant().movePlayer(3012, 3515, 1);
			} else if (client.objectX == 2895 && client.objectY == 3513) {
				client.getPlayerAssistant().movePlayer(2897, 3513, 1);
			}
			break;
		case 3443:
			client.getPlayerAssistant().movePlayer(3423, 3485, 0);
			break;
		case 1755:
			if (client.objectX == 3116 && client.objectY == 9852) {
				client.getPlayerAssistant().movePlayer(3116, 3451, 0);
				client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 3097 && client.objectY == 9867) {
				client.getPlayerAssistant().movePlayer(3096, 3468, 0);
				client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 3237 && client.objectY == 9858) {
				client.getPlayerAssistant().movePlayer(3238, 3458, 0);
				client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 3088 && client.objectY == 9971) {
				UseOther.useUp(client, client.objectId);
				// client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 3209 && client.objectY == 9616) {
				// OtherObjects.useUp(client, client.objectId);
				client.getPlayerAssistant().movePlayer(3209, 3215, 0);
				client.startAnimation(CLIMB_UP);
				client.getActionSender().sendMessage("You climb up.");
			} else if (client.objectX == 3019 && client.objectY == 9740) {// noord
				client.getPlayerAssistant().movePlayer(3019, 3341, 0);
				client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 3020 && client.objectY == 9739) {// oost
				client.getPlayerAssistant().movePlayer(3021, 3339, 0);
				client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 3018 && client.objectY == 9739) {// wst
				client.getPlayerAssistant().movePlayer(3017, 3339, 0);
				client.startAnimation(CLIMB_UP);
			} else if (client.objectX == 3019 && client.objectY == 9738) {// zuid
				client.getPlayerAssistant().movePlayer(3019, 3337, 0);
				client.startAnimation(CLIMB_UP);
			} else {
				UseOther.useUp(client, client.objectId);
			}
			break;

		case 2405:
			UseOther.useUp(client, client.objectId);
			break;

		case 98:
			if (client.inWild()) {
				return;
			}
			if (!LightSources.playerHasLightSource(client)) {
				client.getPlayerAssistant().movePlayer(2641, 9740, 0);
				return;
			} else if (LightSources.playerHasLightSource(client)) {
				client.getPlayerAssistant().movePlayer(2641, 9764, 0);
				return;
			}
			break;

		case 96:
			if (client.inWild()) {
				return;
			}
			LightSources.brightness3(client);
			client.getPlayerAssistant().movePlayer(2649, 9804, 0);
			break;

		case 2711:
			if (client.absY == 3325) {
				client.getPlayerAssistant().movePlayer(client.absX, 3321, 1);
			}
			break;

		case 4755:
			if (client.absY == 2797) {
				client.getPlayerAssistant().movePlayer(client.absX, 2793, 0);
			}
			break;

		case 4756:
			if (client.absY == 2793) {
				client.getPlayerAssistant().movePlayer(client.absX, 2797, 1);
			}
			break;

		case 4879:
			client.getPlayerAssistant().movePlayer(2807, 9200, 0);
			client.getActionSender().sendMessage(
					"You go down the trapdoor.");
			client.startAnimation(827);
			client.getPlayerAssistant().removeAllWindows();
			break;

		case 5492:
			if (client.getItemAssistant().playerHasItem(1523, 1)
					&& Misc.random(4) < 3) {
				client.getPlayerAssistant().movePlayer(3149, 9652, 0);
				client.getActionSender().sendMessage(
						"You go down the trapdoor.");
				client.startAnimation(827);
				client.getPlayerAssistant().addSkillXP(.5,
						client.playerThieving);
				client.getPlayerAssistant().removeAllWindows();
			} else if (!client.getItemAssistant().playerHasItem(1523, 1)
					&& Misc.random(5) < 2) {
				client.getPlayerAssistant().movePlayer(3149, 9652, 0);
				client.getActionSender().sendMessage(
						"You go down the trapdoor.");
				client.startAnimation(827);
				client.getPlayerAssistant().addSkillXP(.5,
						client.playerThieving);
				client.getPlayerAssistant().removeAllWindows();
			} else if (client.getItemAssistant().playerHasItem(1523, 1)
					&& Misc.random(4) > 3) {
				client.getActionSender().sendMessage(
						"You fail to pick the lock.");
				client.getActionSender()
						.sendMessage(
								"Your thieving has been drained, your fingers feel numb.");
				client.playerLevel[17] = client.getPlayerAssistant()
						.getLevelForXP(client.playerXP[17]) - 1;
				client.getPlayerAssistant().refreshSkill(17);
				client.getItemAssistant().deleteItem2(1523, 1);
			} else if (!client.getItemAssistant().playerHasItem(1523, 1)
					&& Misc.random(5) > 2) {
				client.getActionSender().sendMessage(
						"You fail to pick the lock.");
				client.getActionSender()
						.sendMessage(
								"Your thieving has been drained, your fingers feel numb.");
				client.playerLevel[17] = client.getPlayerAssistant()
						.getLevelForXP(client.playerXP[17]) - 1;
				client.getPlayerAssistant().refreshSkill(17);
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
			}
			break;

		case 11725:
			if (client.absY == 3348) {
				client.getPlayerAssistant().movePlayer(2971, 3347, 0);
			}
			break;

		case 1725:
			if (client.absX == 3285 || client.absX == 3286
					&& client.heightLevel == 0) {
				client.getPlayerAssistant().movePlayer(client.absX, 3492, 1);
			} else if (client.absY == 3509) {
				client.getPlayerAssistant().movePlayer(2751, 3513, 1);
			} else if (client.absX == 3226) {
				client.getPlayerAssistant().movePlayer(3230, 3394, 1);
			}
			break;
			
		case 5096:
			client.getPlayerAssistant().movePlayer(2649, 9591, 0);
		break;

		case 1726:
			if (client.absY == 3513) {
				client.getPlayerAssistant().movePlayer(2751, 3509, 0);
			} else if (client.absX == 3230) {
				client.getPlayerAssistant().movePlayer(3226, 3394, 0);
			} else if (client.absX == 3285 || client.absX == 3286
					&& client.heightLevel == 1) {
				client.getPlayerAssistant().movePlayer(client.absX, 3496, 0);
			}
			break;

		case 11727:
			if (client.absY == 3350 || client.absY == 3351
					|| client.absY == 3340 || client.absY == 3341
					|| client.absY == 3342) {
				climbUp(client);
			}
			break;

		case 11728:
			if (client.absY == 3350 || client.absY == 3351
					|| client.absY == 3340 || client.absY == 3341
					|| client.absY == 3342) {
				climbDown(client);
			}
			break;

		case 11729:
			if (client.objectX == 2954 && client.objectY == 3338) {
				client.getPlayerAssistant().movePlayer(2956, 3338, 1);
			} else if (client.objectX == 2960 && client.objectY == 3338) {
				client.getPlayerAssistant().movePlayer(2959, 3339, 2);
			} else if (client.objectX == 2957 && client.objectY == 3338) {
				client.getPlayerAssistant().movePlayer(2959, 3338, 3);
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
				client.getActionSender().sendMessage("You climb down.");
			}
			break;

		case 12265:
			if (client.objectX == 3076 && client.objectY == 9893) {
				client.getPlayerAssistant().movePlayer(3078, 3493, 0);
				client.startAnimation(828);
				client.getActionSender().sendMessage("You climb up.");
			}
			break;
		}
	}

	public static void climbUp(Client client) {
		if (System.currentTimeMillis() - client.climbDelay < 1200) {
			return;
		}
		
		switch (client.heightLevel) {
		case -1:
			client.getPlayerAssistant().movePlayer(client.absX, client.absY, 0);
			client.climbDelay = System.currentTimeMillis();
			client.getActionSender().sendMessage("You climb up.");
			client.startAnimation(CLIMB_UP);
			client.getPlayerAssistant().closeAllWindows();
			break;
		case 0:
			client.getPlayerAssistant().movePlayer(client.absX, client.absY, 1);
			client.climbDelay = System.currentTimeMillis();
			client.getActionSender().sendMessage("You climb up.");
			client.startAnimation(CLIMB_UP);
			client.getPlayerAssistant().closeAllWindows();
			break;
		case 1:
			client.getPlayerAssistant().movePlayer(client.absX, client.absY, 2);
			client.climbDelay = System.currentTimeMillis();
			client.getActionSender().sendMessage("You climb up.");
			client.startAnimation(CLIMB_UP);
			client.getPlayerAssistant().closeAllWindows();
			break;
		case 2:
			client.getPlayerAssistant().movePlayer(client.absX, client.absY, 3);
			client.climbDelay = System.currentTimeMillis();
			client.getActionSender().sendMessage("You climb up.");
			client.startAnimation(CLIMB_UP);
			client.getPlayerAssistant().closeAllWindows();
			break;
		default:
			if (client.heightLevel > 3) {
				climbDown(client);
			}
			client.getActionSender().sendMessage("This object is currently not supported.");
			System.out.println("Bug detected with climbing up object " + client.objectId + " objectX " + client.objectX + " objectY " + client.objectY + ".");
			break;
		}
	}

	public static void climbDown(Client client) {
		if (System.currentTimeMillis() - client.climbDelay < 1200) {
			return;
		}
		if (client.heightLevel > 3) {
			client.getPlayerAssistant().movePlayer(client.absX, client.absY, 3);
			client.climbDelay = System.currentTimeMillis();
			client.getActionSender().sendMessage("You climb down.");
			client.startAnimation(CLIMB_DOWN);
			client.getPlayerAssistant().closeAllWindows();
		}
		switch (client.heightLevel) {
		case 1:
			client.getPlayerAssistant().movePlayer(client.absX, client.absY, 0);
			client.climbDelay = System.currentTimeMillis();
			client.getActionSender().sendMessage("You climb down.");
			client.startAnimation(CLIMB_DOWN);
			client.getPlayerAssistant().closeAllWindows();
			break;
		case 2:
			client.getPlayerAssistant().movePlayer(client.absX, client.absY, 1);
			client.climbDelay = System.currentTimeMillis();
			client.getActionSender().sendMessage("You climb down.");
			client.startAnimation(CLIMB_DOWN);
			client.getPlayerAssistant().closeAllWindows();
			break;
		case 3:
			client.getPlayerAssistant().movePlayer(client.absX, client.absY, 2);
			client.climbDelay = System.currentTimeMillis();
			client.getActionSender().sendMessage("You climb down.");
			client.startAnimation(CLIMB_DOWN);
			client.getPlayerAssistant().closeAllWindows();
			break;
		default:
			if (client.heightLevel < 0) {
				climbUp(client);
			}
			client.getActionSender().sendMessage("This object is currently not supported.");
			System.out.println("Bug detected with climbing down object " + client.objectId + " objectX " + client.objectX + " objectY " + client.objectY + ".");
			break;
		}
	}

	public static void handleLadder(Client client) {
		client.getDialogueHandler().sendOption2("Climb Up.", "Climb Down.");
		client.dialogueAction = 147;
	}
	
	private static void close(Client client, int actionButtonId) {
		if (actionButtonId == 9157) {
			client.getActionSender().sendMessage("You climb up.");
			client.startAnimation(CLIMB_UP);
		} else if (actionButtonId == 9158) {
			client.getActionSender().sendMessage("You climb down.");
			client.startAnimation(827);
		}
		client.getPlayerAssistant().closeAllWindows();
		client.nextChat = 0;
	}
	
	public static void handleLadderButtons(Client client, int actionButtonId) {
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