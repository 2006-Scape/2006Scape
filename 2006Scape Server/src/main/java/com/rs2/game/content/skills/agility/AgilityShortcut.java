package com.rs2.game.content.skills.agility;

import com.rs2.Constants;
import com.rs2.game.players.Player;

/**
 * Agility Shortcuts
 * @author Andrew (Mr Extremez)
 */

public class AgilityShortcut {

	private static final int WALK = 1, MOVE = 2, AGILITY = 3, DOWN = 4, UP = 5;

	private static void handleAgility(Player player, int x, int y, int levelReq, int anim, int walk, String message) {
		if (player.playerLevel[Constants.AGILITY] < levelReq) {
			player.getPacketSender().sendMessage("You need " + levelReq + " agility to use this shortcut.");
			return;
		}
		switch (walk) {
		case WALK:
			player.getPlayerAssistant().walkTo(x, y);
			break;
		case MOVE:
			player.getPlayerAssistant().movePlayer(x, y, player.heightLevel);
			break;
		case AGILITY:
			player.getAgility().walk(x, y, anim, -1);
			break;
		case DOWN:
			player.getPlayerAssistant().movePlayer(x, y, player.heightLevel - 1);
			break;
		case UP:
			player.getPlayerAssistant().movePlayer(x, y, player.heightLevel + 1);
			break;
		}
		if (anim != 0 && anim != -1) {
			player.startAnimation(anim);
		}
		player.getPacketSender().sendMessage(message);
	}

	public static void processAgilityShortcut(Player player) {
		switch (player.objectId) {
		case 993:
		if (player.absY == 3435) {
			handleAgility(player, 2761, 3438, 1, 3067, MOVE, "You jump over the stile.");
		} else if (player.absY == 3438) {
			handleAgility(player, 2761, 3435, 1, 3067, MOVE, "You jump over the stile.");
		}
		break;
		case 9326:
			if (player.absX == 2770) {
				handleAgility(player, 2768, 10002, 81, 3067, MOVE, "You jump over the strange floor.");
			} else if (player.absX == 2768) {
				handleAgility(player, 2770, 10002, 81, 3067, MOVE, "You jump over the strange floor.");
			} else if (player.absX == 2773) {
				handleAgility(player, 2775, 10003, 81, 3067, MOVE, "You jump over the strange floor.");
			} else if (player.absX == 2775) {
				handleAgility(player, 2773, 10003, 81, 3067, MOVE, "You jump over the strange floor.");
			}
		break;
		case 9321:
		if (player.absX == 2735) {
			handleAgility(player, -5, 0, 62, 2240, WALK, "You squeeze through the crevice.");
		} else if (player.absX == 2730) {
			handleAgility(player, 5, 0, 62, 2240, WALK, "You squeeze through the crevice.");
		}
		break;
		case 12127:
			if (player.absY == 4403) {
				handleAgility(player, 0, -2, 66, 2240, WALK,
						"You squeeze past the jutted wall.");
			} else if (player.absY == 4401) {
				handleAgility(player, 0, 2, 66, 2240, WALK,
						"You squeeze past the jutted wall.");
			} else if (player.absY == 4404) {
				handleAgility(player, 0, -2, 46, 2240, WALK,
						"You squeeze past the jutted wall.");
			} else if (player.absY == 4402) {
				handleAgility(player, 0, 2, 46, 2240, WALK,
						"You squeeze past the jutted wall.");
			}
			break;
		case 3933:
			if (player.absY == 3232) {
				handleAgility(player, 0, 7, 85, 762, WALK,
						"You pass through the agility shortcut.");
			} else if (player.absY == 3239) {
				handleAgility(player, 0, -7, 85, 762, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 4615:
		case 4616:
			if (player.absX == 2595) {
				handleAgility(player, 2599, player.absY, 1, 3067, MOVE,
						"You pass through the agility shortcut.");
			} else if (player.absX == 2599) {
				handleAgility(player, 2595, player.absY, 1, 3067, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 11844:
			if (player.absX == 2936) {
				handleAgility(player, -2, 0, 5, -1, WALK,
						"You pass through the agility shortcut.");
			} else if (player.absX == 2934) {
				handleAgility(player, 2, 0, 5, -1, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 5090:
			if (player.absX == 2687) {// 2682, 9506
				handleAgility(player, -5, 0, 5, 762, WALK,
						"You walk across the log balance.");
			}
			break;
		case 5088:
			if (player.absX == 2682) {// 2867, 9506
				handleAgility(player, 5, 0, 5, 762, WALK,
						"You walk across the log balance.");
			}
			break;
		case 14922:
			if (player.objectX == 2344 && player.objectY == 3651) {
				handleAgility(player, 2344, 3655, 1, 762, MOVE,
						"You crawl through the hole.");
			} else if (player.objectX == 2344 && player.objectY == 3654) {
				handleAgility(player, 2344, 3650, 1, 762, MOVE,
						"You crawl through the hole.");
			}
			break;
		case 9330:
			if (player.objectX == 2601 && player.objectY == 3336) {
				handleAgility(player, -4, 0, 33, player.getAgility()
						.getAnimation(Agility.PIPES_EMOTE), AGILITY,
						"You pass through the agility shortcut.");
			}
		case 5100:
			if (player.absY == 9566) {
				handleAgility(player, 2655, 9573, 17, 762, MOVE,
						"You pass through the agility shortcut.");
			} else if (player.absY == 9573) {
				handleAgility(player, 2655, 9573, 17, 762, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 9328:
			if (player.objectX == 2599 && player.objectY == 3336) {
				handleAgility(player, 4, 0, 33, player.getAgility()
						.getAnimation(Agility.PIPES_EMOTE), AGILITY,
						"You pass through the agility shortcut.");
			}
			break;

		case 9293:
			if (player.absX < player.objectX) {
				handleAgility(player, 2892, 9799, 70, player.getAgility()
						.getAnimation(Agility.PIPES_EMOTE), MOVE,
						"You pass through the agility shortcut.");
			} else {
				handleAgility(player, 2886, 9799, 70, player.getAgility()
						.getAnimation(Agility.PIPES_EMOTE), MOVE,
						"You pass through the agility shortcut.");
			}
			break;

		case 9294:
			if (player.absX < player.objectX) {
				player.getPlayerAssistant().movePlayer(player.objectX + 1,
						player.absY, 0);
				handleAgility(player, 2880, 9713, 80, 3067, MOVE,
						"You jump over the strange wall.");
			} else if (player.absX > player.objectX) {
				handleAgility(player, 2878, 9713, 80, 3067, MOVE,
						"You jump over the strange wall.");
			}
			break;

		case 9302:
			if (player.absY == 3112) {
				handleAgility(player, 2575, 3107, 16, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;

		case 9301:
			if (player.absY == 3107) {
				handleAgility(player, 2575, 3112, 16, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 9307:
			if (player.absY == 9888) {
				handleAgility(player, 3670, 9888, 58, Agility.CLIMB_UP_EMOTE, UP,
						"You jump up the weathered wall.");
			}
			break;
		case 9308:
			if (player.absY == 9888) {
				handleAgility(player, 3671, 9888, 58, Agility.CLIMB_DOWN_EMOTE, DOWN,
						"You jump down the weathered wall.");
			}
			break;
		case 9309:
			if (player.absY == 3309) {
				handleAgility(player, 2948, 3313, 26, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 9310:
			if (player.absY == 3313) {
				handleAgility(player, 2948, 3309, 26, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 2322:
			if (player.absX == 2709) {
				handleAgility(player, 2704, 3209, 10, 3067, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 2323:
			if (player.absX == 2705) {
				handleAgility(player, 2709, 3205, 10, 3067, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 2332:
			if (player.absX == 2906) {
				handleAgility(player, 4, 0, 1, 762, WALK,
						"You pass through the agility shortcut.");
			} else if (player.absX == 2910) {
				handleAgility(player, -4, 0, 1, 762, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 3067:
			if (player.absX == 2639) {
				handleAgility(player, -1, 0, 1, 3067, WALK,
						"You pass through the agility shortcut.");
			} else if (player.absX == 2638) {
				handleAgility(player, -1, 0, 1, 3067, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 2618:
			if (player.absY == 3492) {
				handleAgility(player, 0, +2, 1, 3067, WALK,
						"You jump over the broken fence.");
			} else if (player.absY == 3494) {
				handleAgility(player, 0, -2, 1, 3067, WALK,
						"You jump over the broken fence.");
			}
			break;
		case 5110:
			Agility.brimhavenSkippingStone(player);
			break;
		case 5111:
			Agility.brimhavenSkippingStone(player);
			break;
		case 2296:
			if (player.absX == 2603) {
				handleAgility(player, -5, 0, 1, -1, WALK,
						"You pass through the agility shortcut.");
			} else if (player.absX == 2598) {
				handleAgility(player, 5, 0, 1, -1, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		}
	}
}
