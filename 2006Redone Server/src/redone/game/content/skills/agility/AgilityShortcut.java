package redone.game.content.skills.agility;

import redone.game.players.Client;

/**
 * Agility Shortcuts
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class AgilityShortcut {

	private static final int WALK = 1, MOVE = 2, AGILITY = 3;

	private static void handleAgility(Client client, int x, int y, int levelReq, int anim, int walk, String message) {
		if (client.playerLevel[client.playerAgility] < levelReq) {
			client.getActionSender().sendMessage("You need " + levelReq + " agility to use this shortcut.");
			return;
		}
		switch (walk) {
		case 1:
			client.getPlayerAssistant().walkTo(x, y);
			break;
		case 2:
			client.getPlayerAssistant().movePlayer(x, y, client.heightLevel);
			break;
		case 3:
			client.getAgility().walk(x, y, anim, -1);
			break;
		}
		if (anim != 0 && anim != -1) {
			client.startAnimation(anim);
		}
		client.getActionSender().sendMessage(message);
	}

	public static void processAgilityShortcut(Client client) {
		switch (client.objectId) {
		case 993:
		if (client.absY == 3435) {
			handleAgility(client, 2761, 3438, 1, 3067, MOVE, "You jump over the stile.");
		} else if (client.absY == 3438) {
			handleAgility(client, 2761, 3435, 1, 3067, MOVE, "You jump over the stile.");
		}
		break;
		case 9326:
		if (client.absX == 2773) {
			handleAgility(client, 2, 0, 81, 3067, WALK, "You jump over the strange floor.");
		} else if (client.absX == 2775) {
			handleAgility(client, -2, 0, 81, 3067, WALK, "You jump over the strange floor.");
		}
		break;
		case 9321:
		if (client.absX == 2735) {
			handleAgility(client, -5, 0, 62, 2240, WALK, "You squeeze through the crevice.");
		} else if (client.absX == 2730) {
			handleAgility(client, 5, 0, 62, 2240, WALK, "You squeeze through the crevice.");
		}
		break;
		case 12127:
			if (client.absY == 4403) {
				handleAgility(client, 0, -2, 66, 2240, WALK,
						"You squeeze past the jutted wall.");
			} else if (client.absY == 4401) {
				handleAgility(client, 0, 2, 66, 2240, WALK,
						"You squeeze past the jutted wall.");
			} else if (client.absY == 4404) {
				handleAgility(client, 0, -2, 46, 2240, WALK,
						"You squeeze past the jutted wall.");
			} else if (client.absY == 4402) {
				handleAgility(client, 0, 2, 46, 2240, WALK,
						"You squeeze past the jutted wall.");
			}
			break;
		case 3933:
			if (client.absY == 3232) {
				handleAgility(client, 0, 7, 85, 762, WALK,
						"You pass through the agility shortcut.");
			} else if (client.absY == 3239) {
				handleAgility(client, 0, -7, 85, 762, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 4615:
		case 4616:
			if (client.absX == 2595) {
				handleAgility(client, 2599, client.absY, 1, 3067, MOVE,
						"You pass through the agility shortcut.");
			} else if (client.absX == 2599) {
				handleAgility(client, 2595, client.absY, 1, 3067, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 11844:
			if (client.absX == 2936) {
				handleAgility(client, -2, 0, 5, -1, WALK,
						"You pass through the agility shortcut.");
			} else if (client.absX == 2934) {
				handleAgility(client, 2, 0, 5, -1, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 5090:
			if (client.absX == 2687) {// 2682, 9506
				handleAgility(client, -5, 0, 5, 762, WALK,
						"You walk across the log balance.");
			}
			break;
		case 5088:
			if (client.absX == 2682) {// 2867, 9506
				handleAgility(client, 5, 0, 5, 762, WALK,
						"You walk across the log balance.");
			}
			break;
		case 14922:
			if (client.objectX == 2344 && client.objectY == 3651) {
				handleAgility(client, 2344, 3655, 1, 762, MOVE,
						"You crawl through the hole.");
			} else if (client.objectX == 2344 && client.objectY == 3654) {
				handleAgility(client, 2344, 3650, 1, 762, MOVE,
						"You crawl through the hole.");
			}
			break;
		case 9330:
			if (client.objectX == 2601 && client.objectY == 3336) {
				handleAgility(client, -4, 0, 33, client.getAgility()
						.getAnimation(Agility.PIPES_EMOTE), AGILITY,
						"You pass through the agility shortcut.");
			}
		case 5100:
			if (client.absY == 9566) {
				handleAgility(client, 2655, 9573, 17, 762, MOVE,
						"You pass through the agility shortcut.");
			} else if (client.absY == 9573) {
				handleAgility(client, 2655, 9573, 17, 762, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 9328:
			if (client.objectX == 2599 && client.objectY == 3336) {
				handleAgility(client, 4, 0, 33, client.getAgility()
						.getAnimation(Agility.PIPES_EMOTE), AGILITY,
						"You pass through the agility shortcut.");
			}
			break;

		case 9293:
			if (client.absX < client.objectX) {
				handleAgility(client, 2892, 9799, 70, client.getAgility()
						.getAnimation(Agility.PIPES_EMOTE), MOVE,
						"You pass through the agility shortcut.");
			} else {
				handleAgility(client, 2886, 9799, 70, client.getAgility()
						.getAnimation(Agility.PIPES_EMOTE), MOVE,
						"You pass through the agility shortcut.");
			}
			break;

		case 9294:
			if (client.absX < client.objectX) {
				client.getPlayerAssistant().movePlayer(client.objectX + 1,
						client.absY, 0);
				handleAgility(client, 2880, 9713, 80, 3067, MOVE,
						"You jump over the strange wall.");
			} else if (client.absX > client.objectX) {
				handleAgility(client, 2878, 9713, 80, 3067, MOVE,
						"You jump over the strange wall.");
			}
			break;

		case 9302:
			if (client.absY == 3112) {
				handleAgility(client, 2575, 3107, 16, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;

		case 9301:
			if (client.absY == 3107) {
				handleAgility(client, 2575, 3112, 16, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 9309:
			if (client.absY == 3309) {
				handleAgility(client, 2948, 3313, 26, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 9310:
			if (client.absY == 3313) {
				handleAgility(client, 2948, 3309, 26, 844, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 2322:
			if (client.absX == 2709) {
				handleAgility(client, 2704, 3209, 10, 3067, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 2323:
			if (client.absX == 2705) {
				handleAgility(client, 2709, 3205, 10, 3067, MOVE,
						"You pass through the agility shortcut.");
			}
			break;
		case 2332:
			if (client.absX == 2906) {
				handleAgility(client, 4, 0, 1, 762, WALK,
						"You pass through the agility shortcut.");
			} else if (client.absX == 2910) {
				handleAgility(client, -4, 0, 1, 762, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 3067:
			if (client.absX == 2639) {
				handleAgility(client, -1, 0, 1, 3067, WALK,
						"You pass through the agility shortcut.");
			} else if (client.absX == 2638) {
				handleAgility(client, -1, 0, 1, 3067, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		case 2618:
			if (client.absY == 3492) {
				handleAgility(client, 0, +2, 1, 3067, WALK,
						"You jump over the broken fence.");
			} else if (client.absY == 3494) {
				handleAgility(client, -0, -2, 1, 3067, WALK,
						"You jump over the broken fence.");
			}
			break;
		case 5110:
			Agility.brimhavenSkippingStone(client);
			break;
		case 5111:
			Agility.brimhavenSkippingStone(client);
			break;
		case 2296:
			if (client.absX == 2603) {
				handleAgility(client, -5, 0, 1, -1, WALK,
						"You pass through the agility shortcut.");
			} else if (client.absX == 2598) {
				handleAgility(client, 5, 0, 1, -1, WALK,
						"You pass through the agility shortcut.");
			}
			break;
		}
	}
}
