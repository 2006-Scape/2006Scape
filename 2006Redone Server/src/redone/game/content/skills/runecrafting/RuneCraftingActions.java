package redone.game.content.skills.runecrafting;

import redone.game.players.Client;

/**
 * Handles runecrafting actions
 * 
 * @author Andrew
 */

public class RuneCraftingActions {

	public static void handleRuneCrafting(Client client, int objectId) {
		switch (objectId) {
		case 2465:// air altar portal
			if (client.objectX == 2841 && client.objectY == 4828) {
				client.getPlayerAssistant().startTeleport(2983, 3293, 0, "modern");
			}
			break;

		case 2466:// mind altar portal
			if (client.objectX == 2793 && client.objectY == 4827) {
				client.getPlayerAssistant().startTeleport(2980, 3514, 0, "modern");
			}
			break;

		case 2467:// water altar portal
			if (client.objectX == 2727 && client.objectY == 4832) {
				client.getPlayerAssistant().startTeleport(3184, 3162, 0, "modern");
			}
			break;

		case 2468:// earth rune portal
			if (client.objectX == 2655 && client.objectY == 4829) {
				client.getPlayerAssistant().startTeleport(3308, 3476, 0, "modern");
			}
			break;

		case 2469:// fire rune portal
			if (client.objectX == 2574 && client.objectY == 4850) {
				client.getPlayerAssistant().startTeleport(3311, 3256, 0, "modern");
			}
			break;

		case 2470:// body altar portal
			if (client.objectX == 2523 && client.objectY == 4825) {
				client.getPlayerAssistant().startTeleport(3051, 3444, 0, "modern");
			}
			break;

		case 2471:// cosmic altar portal
			if (client.objectX == 2163 && client.objectY == 4833 || client.objectX == 2142 && client.objectY == 4854 || client.objectX == 2121 && client.objectY == 4833 || client.objectX == 2412 && client.objectY == 4812) {
				client.getPlayerAssistant().startTeleport(2410, 4379, 0, "modern");
			}
			break;

		case 2472:// law altar portal
			if (client.objectX == 2464 && client.objectY == 4817) {
				client.getPlayerAssistant().startTeleport(2857, 3379, 0, "modern");
			}
			break;

		case 2473:// nature portal altar
			if (client.objectX == 2400 && client.objectY == 4834) {
				client.getPlayerAssistant().startTeleport(2866, 3022, 0, "modern");
			}
			break;

		case 2474:
			if (client.objectX == 3233 && client.objectY == 9312) {// desert
																	// treasure
																	// portal
				client.getPlayerAssistant().startTeleport(3233, 2887, 0, "modern");
			} else if (client.objectX == 2282 && client.objectY == 4837) {// chaos
																			// altar
																			// portal
				client.getPlayerAssistant().startTeleport(3062, 3593, 0, "modern");
			}
			break;

		case 2475:// death altar portal
			if (client.objectX == 2208 && client.objectY == 4829) {
				client.getPlayerAssistant().startTeleport(1863, 4639, 0,
						"modern");
			}
			break;

		case 2478:// air altar crafting
			if (client.objectX == 2843 && client.objectY == 4833) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2479:// mind altar crafting
			if (client.objectX == 2785 && client.objectY == 4840) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2480:// water altar crafting
			if (client.objectX == 2715 && client.objectY == 4835) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2481:// earth altar crafting
			if (client.objectX == 2657 && client.objectY == 4840) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2482:// fire altar crafting
			if (client.objectX == 2584 && client.objectY == 4837) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2483:// body altar crafting
			if (client.objectX == 2524 && client.objectY == 4831) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2484:// cosmic altar crafting
			if (client.objectX == 2141 && client.objectY == 4832) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2485:// law altar crafting
			if (client.objectX == 2463 && client.objectY == 4831) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2486:// nature altar crafting
			if (client.objectX == 2399 && client.objectY == 4840) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2487:// chaos altar crafting
			if (client.objectX == 2270 && client.objectY == 4841) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2488:// death altar crafting
			if (client.objectX == 2204 && client.objectY == 4835) {
				client.getRC().craftRunes(objectId);
			}
			break;

		case 2452:// air altar entrance
			if (client.objectX == 2984 && client.objectY == 3291) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;

		case 2453:// mind altar entrance
			if (client.objectX == 2981 && client.objectY == 3513) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;

		case 2454:// water altar entrance
			if (client.objectX == 3184 && client.objectY == 3164) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;

		case 2455:// earth altar entrance
			if (client.objectX == 3305 && client.objectY == 3473) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;

		case 2456:// fire altar entrance
			if (client.objectX == 3312 && client.objectY == 3254) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;

		case 2457:// body altar entrance
			if (client.objectX == 3052 && client.objectY == 3444) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;

		case 2458:// cosmic altar entrance
			if (client.objectX == 2407 && client.objectY == 4376) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;

		case 2459:// law altar entrance
			if (client.objectX == 2857 && client.objectY == 3380) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;

		case 2460:// nature altar entrance
			if (client.objectX == 2868 && client.objectY == 3018) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;

		case 2461:// chaos altar entrance
			if (client.objectX == 3059 && client.objectY == 3590) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;

		case 2462:// death altar entrance
			if (client.objectX == 1859 && client.objectY == 4638) {
				client.getRC().enterAltar(objectId, 0);
			}
			break;
		}
	}

}
