package com.rebotted.game.content.minigames.castlewars;

import com.rebotted.game.players.Player;

public class CastleWarObjects {

	public static void handleObject(Player player, int id, int x, int y) {
		switch (id) {
		case 4469:
			if (CastleWars.getTeamNumber(player) == 2) {
				player.getPacketSender().sendMessage(
						"You are not allowed in the other teams spawn point.");
				break;
			}
			if (x == 2426) {
				if (player.getY() == 3080) {
					player.getPlayerAssistant()
							.movePlayer(2426, 3081, player.heightLevel);
				} else if (player.getY() == 3081) {
					player.getPlayerAssistant()
							.movePlayer(2426, 3080, player.heightLevel);
				}
			} else if (x == 2422) {
				if (player.getX() == 2422) {
					player.getPlayerAssistant()
							.movePlayer(2423, 3076, player.heightLevel);
				} else if (player.getX() == 2423) {
					player.getPlayerAssistant()
							.movePlayer(2422, 3076, player.heightLevel);
				}
			}
			break;
		case 4470:
			if (CastleWars.getTeamNumber(player) == 1) {
				player.getPacketSender().sendMessage(
						"You are not allowed in the other teams spawn point.");
				break;
			}
			if (x == 2373 && y == 3126) {
				if (player.getY() == 3126) {
					player.getPlayerAssistant().movePlayer(2373, 3127, 1);
				} else if (player.getY() == 3127) {
					player.getPlayerAssistant().movePlayer(2373, 3126, 1);
				}
			} else if (x == 2377 && y == 3131) {
				if (player.getX() == 2376) {
					player.getPlayerAssistant().movePlayer(2377, 3131, 1);
				} else if (player.getX() == 2377) {
					player.getPlayerAssistant().movePlayer(2376, 3131, 1);
				}
			}
			break;
		case 4417:
			if (x == 2428 && y == 3081 && player.heightLevel == 1) {
				player.getPlayerAssistant().movePlayer(2430, 3080, 2);
			}
			if (x == 2425 && y == 3074 && player.heightLevel == 2) {
				player.getPlayerAssistant().movePlayer(2426, 3074, 3);
			}
			if (x == 2419 && y == 3078 && player.heightLevel == 0) {
				player.getPlayerAssistant().movePlayer(2420, 3080, 1);
			}
			break;
		case 4415:
			if (x == 2419 && y == 3080 && player.heightLevel == 1) {
				player.getPlayerAssistant().movePlayer(2419, 3077, 0);
			}
			if (x == 2430 && y == 3081 && player.heightLevel == 2) {
				player.getPlayerAssistant().movePlayer(2427, 3081, 1);
			}
			if (x == 2425 && y == 3074 && player.heightLevel == 3) {
				player.getPlayerAssistant().movePlayer(2425, 3077, 2);
			}
			if (x == 2374 && y == 3133 && player.heightLevel == 3) {
				player.getPlayerAssistant().movePlayer(2374, 3130, 2);
			}
			if (x == 2369 && y == 3126 && player.heightLevel == 2) {
				player.getPlayerAssistant().movePlayer(2372, 3126, 1);
			}
			if (x == 2380 && y == 3127 && player.heightLevel == 1) {
				player.getPlayerAssistant().movePlayer(2380, 3130, 0);
			}
			break;
		case 4411: // castle wars jumping stones
			if (x == player.getX() && y == player.getY()) {
				player.getPacketSender().sendMessage("You are standing on the rock you clicked.");
			} else if (x > player.getX() && y == player.getY()) {
				player.getPlayerAssistant().walkTo(1, 0);
			} else if (x < player.getX() && y == player.getY()) {
				player.getPlayerAssistant().walkTo(-1, 0);
			} else if (y > player.getY() && x == player.getX()) {
				player.getPlayerAssistant().walkTo(0, 1);
			} else if (y < player.getY() && x == player.getX()) {
				player.getPlayerAssistant().walkTo(0, -1);
			} else {
				player.getPacketSender().sendMessage("Can't reach that.");
			}
			break;
		case 4419:
			if (x == 2417 && y == 3074 && player.heightLevel == 0) {
				if (player.getX() == 2416) {
					player.getPlayerAssistant().movePlayer(2417, 3077, 0);
				} else {
					player.getPlayerAssistant().movePlayer(2416, 3074, 0);
				}
			}
			break;

		case 4911:
			if (x == 2421 && y == 3073 && player.heightLevel == 1) {
				player.getPlayerAssistant().movePlayer(2421, 3074, 0);
			}
			if (x == 2378 && y == 3134 && player.heightLevel == 1) {
				player.getPlayerAssistant().movePlayer(2378, 3133, 0);
			}
			break;
		case 1747:
			if (x == 2421 && y == 3073 && player.heightLevel == 0) {
				player.getPlayerAssistant().movePlayer(2421, 3074, 1);
			}
			if (x == 2378 && y == 3134 && player.heightLevel == 0) {
				player.getPlayerAssistant().movePlayer(2378, 3133, 1);
			}
			break;
		case 4912:
			if (x == 2430 && y == 3082 && player.heightLevel == 0) {
				player.getPlayerAssistant().movePlayer(player.getX(), player.getY() + 6400, 0);
			}
			if (x == 2369 && y == 3125 && player.heightLevel == 0) {
				player.getPlayerAssistant().movePlayer(player.getX(), player.getY() + 6400, 0);
			}
			break;
		case 1757:
			if (x == 2430 && y == 9482) {
				player.getPlayerAssistant().movePlayer(2430, 3081, 0);
			} else if (player.absX == 2533) {
				player.getPlayerAssistant().movePlayer(2532, 3155, 0);
			} else {
				player.getPlayerAssistant().movePlayer(2369, 3126, 0);
			}
			break;

		case 4418:
			if (x == 2380 && y == 3127 && player.heightLevel == 0) {
				player.getPlayerAssistant().movePlayer(2379, 3127, 1);
			}
			if (x == 2369 && y == 3126 && player.heightLevel == 1) {
				player.getPlayerAssistant().movePlayer(2369, 3127, 2);
			}
			if (x == 2374 && y == 3131 && player.heightLevel == 2) {
				player.getPlayerAssistant().movePlayer(2373, 3133, 3);
			}
			break;
		case 4420:
			if (x == 2382 && y == 3131 && player.heightLevel == 0) {
				if (player.getX() >= 2383 && player.getX() <= 2385) {
					player.getPlayerAssistant().movePlayer(2382, 3130, 0);
				} else {
					player.getPlayerAssistant().movePlayer(2383, 3133, 0);
				}
			}
			break;
		case 4437:
			if (x == 2400 && y == 9512) {
				player.getPlayerAssistant().movePlayer(2400, 9514, 0);
			} else if (x == 2391 && y == 9501) {
				player.getPlayerAssistant().movePlayer(2393, 9502, 0);
			} else if (x == 2409 && y == 9503) {
				player.getPlayerAssistant().movePlayer(2411, 9503, 0);
			} else if (x == 2401 && y == 9494) {
				player.getPlayerAssistant().movePlayer(2401, 9493, 0);
			}
			break;
		case 1568:
			if (x == 2399 && y == 3099) {
				player.getPlayerAssistant().movePlayer(2399, 9500, 0);
			} else {
				player.getPlayerAssistant().movePlayer(2400, 9507, 0);
			}
		case 6281:
			player.getPlayerAssistant().movePlayer(2370, 3132, 2);
			break;
		case 4472:
			player.getPlayerAssistant().movePlayer(2370, 3132, 1);
			break;
		case 6280:
			player.getPlayerAssistant().movePlayer(2429, 3075, 2);
			break;
		case 4471:
			player.getPlayerAssistant().movePlayer(2429, 3075, 1);
			break;
		case 4406:
			CastleWars.removePlayerFromCw(player);
			break;
		case 4407:
			CastleWars.removePlayerFromCw(player);
			break;
		case 4458:
		if (System.currentTimeMillis() - player.miscTimer > 1200) {
			player.startAnimation(881);
			player.getItemAssistant().addItem(4049, 1);
			player.getPacketSender().sendMessage("You get some bandages");
			player.miscTimer = System.currentTimeMillis();
		}
		break;
		case 4902: // sara flag
		case 4377:
			switch (CastleWars.getTeamNumber(player)) {
			case 1:
				CastleWars.returnFlag(player, player.playerEquipment[player.playerWeapon]);
				break;
			case 2:
				CastleWars.captureFlag(player);
				break;
			}
			break;
		case 4903: // zammy flag
		case 4378:
			switch (CastleWars.getTeamNumber(player)) {
			case 1:
				CastleWars.captureFlag(player);
				break;
			case 2:
				CastleWars.returnFlag(player, player.playerEquipment[player.playerWeapon]);
				break;
			}
			break;
		case 4461: // barricades
			if (System.currentTimeMillis() - player.miscTimer > 1200) {
				player.getPacketSender().sendMessage("You get a barricade!");
				player.getItemAssistant().addItem(4053, 1);
				player.miscTimer = System.currentTimeMillis();
			}
			break;
		case 4463: // explosive potion!
			if (System.currentTimeMillis() - player.miscTimer > 1200) {
				player.getPacketSender().sendMessage(
						"You get an explosive potion!");
				player.getItemAssistant().addItem(4045, 1);
				player.miscTimer = System.currentTimeMillis();
			}
			break;
		case 4464: // pickaxe table
			if (System.currentTimeMillis() - player.miscTimer > 1200) {
				player.getPacketSender().sendMessage(
						"You get a bronzen pickaxe for mining.");
				player.getItemAssistant().addItem(1265, 1);
				player.miscTimer = System.currentTimeMillis();
			}
			break;
		case 4459: // tinderbox table
			if (System.currentTimeMillis() - player.miscTimer > 1200) {
				player.getPacketSender().sendMessage("You take a tinderbox!");
				player.getItemAssistant().addItem(590, 1);
				player.miscTimer = System.currentTimeMillis();
			}
			break;
		case 4462:
			if (System.currentTimeMillis() - player.miscTimer > 1200) {
				player.getPacketSender().sendMessage("You take some rope!");
				player.getItemAssistant().addItem(954, 1);
				player.miscTimer = System.currentTimeMillis();
			}
			break;
		case 4460:
			if (System.currentTimeMillis() - player.miscTimer > 1200) {
				player.getPacketSender().sendMessage("You take a rock!");
				player.getItemAssistant().addItem(4043, 1);
				player.miscTimer = System.currentTimeMillis();
			}
			break;
		case 4900:
		case 4901:
			CastleWars.pickupFlag(player);
		default:
			break;

		}
	}
}
