package redone.net.packets.impl;

import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.minigames.castlewars.CastleWarObjects;
import redone.game.content.minigames.castlewars.CastleWars;
import redone.game.content.skills.core.Mining;
import redone.game.content.skills.core.Woodcutting;
import redone.game.content.skills.thieving.Stalls;
import redone.game.globalworldobjects.Doors;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.net.packets.PacketType;

public class ClickObject implements PacketType {

	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252,
			THIRD_CLICK = 70, FOURTH_CLICK = 234;

	@Override
	public void processPacket(final Client player, int packetType,
			int packetSize) {
		player.clickObjectType = player.objectX = player.objectId = player.objectY = 0;
		player.objectYOffset = player.objectXOffset = 0;
		player.getPlayerAssistant().resetFollow();
		player.getCombatAssistant().resetPlayerAttack();
		player.getPlayerAssistant().requestUpdates();
		switch (packetType) {

		case FIRST_CLICK:
			player.objectX = player.getInStream().readSignedWordBigEndianA();
			player.objectId = player.getInStream().readUnsignedWord();
			player.objectY = player.getInStream().readUnsignedWordA();
			player.objectDistance = 1;
			player.turnPlayerTo(player.objectX, player.objectY);
			if (player.playerRights == 3) {
				player.getActionSender().sendMessage("ObjectId: " + player.objectId + " ObjectX: " + player.objectX + " ObjectY: " + player.objectY + " Objectclick = 1, Xoff: " + (player.getX() - player.objectX) + " Yoff: " + (player.getY() - player.objectY));
			}
			if(player.goodDistance(player.getX(), player.getY(), player.objectX, player.objectY, 1)) {
				if (Doors.getSingleton().handleDoor(player.objectId, player.objectX, player.objectY, player.heightLevel)) {
				}
			}
			
			/*if (client.performingAction) {
				return;
			}*/

			if (Stalls.isObject(player.objectId)) {
				Stalls.attemptStall(player, player.objectId, player.objectX, player.objectX);
				return;
			}

			if (player.teleTimer > 0) {
				player.getActionSender().sendMessage(
						"You cannot use objects while teleporting.");
				return;
			}
			if (Math.abs(player.getX() - player.objectX) > 25 || Math.abs(player.getY() - player.objectY) > 25) {
				player.resetWalkingQueue();
				break;
			}
			if (Mining.rockExists(player, player.objectId)) {
				player.objectDistance = 5;
			}
			if (Woodcutting.playerTrees(player, player.objectId)) {
				player.objectDistance = 3;
			}
			switch (player.objectId) {
			case 1276:
				Woodcutting.startWoodcutting(player, 0, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1278:
				Woodcutting.startWoodcutting(player, 1, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1286:
				Woodcutting.startWoodcutting(player, 2, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1281:
				Woodcutting.startWoodcutting(player, 3, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1308:
				Woodcutting.startWoodcutting(player, 4, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 5552:
				Woodcutting.startWoodcutting(player, 5, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1307:
				Woodcutting.startWoodcutting(player, 6, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1309:
				Woodcutting.startWoodcutting(player, 7, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1306:
				Woodcutting.startWoodcutting(player, 8, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 5551:
				Woodcutting.startWoodcutting(player, 9, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 5553:
				Woodcutting.startWoodcutting(player, 10, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 3033:
				Woodcutting.startWoodcutting(player, 11, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 3037:
				Woodcutting.startWoodcutting(player, 12, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1282:
				Woodcutting.startWoodcutting(player, 13, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1383:
				Woodcutting.startWoodcutting(player, 14, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 2023:
				Woodcutting.startWoodcutting(player, 15, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1319:
				Woodcutting.startWoodcutting(player, 16, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1318:
				Woodcutting.startWoodcutting(player, 17, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1315:
				Woodcutting.startWoodcutting(player, 18, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1316:
				Woodcutting.startWoodcutting(player, 19, player.objectX, player.objectY, player.clickObjectType);
				break;
			case 1332:
				Woodcutting.startWoodcutting(player, 20, player.objectX, player.objectY, player.clickObjectType);
				break;
				
			case 1292:
				if (player.spiritTree == false && player.clickedTree == true) {
					player.getActionSender().sendMessage("You have already spawned a tree spirit.");
					return;
				}
				if (player.spiritTree == false && player.clickedTree == false) {
					player.getActionSender().sendMessage("You attempt to chop the tree, and a tree spirit appears.");
					NpcHandler.spawnNpc(player, 655, player.getX(), player.getY(), 0, 0, 225, 20, 80, 80, true, false);
					player.clickedTree = true;
				} else if (player.spiritTree == true) {
					Woodcutting.startWoodcutting(player, 21, player.objectX, player.objectY, player.clickObjectType);
				}
				break;

			case 1294:
			case 1293:
			case 1317:
				player.getPlayerAssistant().spiritTree();
				break;
				
		      case 2164:
              case 2165:
                  Server.trawler.fixNet(player);
                  break;

			case 4462:
			case 4460:
			case 4461:
			case 4463:
			case 4464:
			case 4459:
				if (!CastleWars.isInCw(player)) {
					player.getActionSender().sendMessage("You have to be in castle wars to use these objects.");
					CastleWars.resetPlayer(player);
					return;
				}
				CastleWarObjects.handleObject(player, player.objectId, player.objectX, player.objectY);
			break;
			
			case 2513:
				player.getRangersGuild().fireAtTarget();
			break;
			
			case 12163:
			case 12164:
			case 12165:
			case 12166:
				player.objectDistance = 4;
			break;
			
	        case 8930:
                player.fade(1975, 4409, 3);
            break;
            case 8929:
                player.fade(2442, 10147, 0);
            break;

			case 12982:
				if (player.absY == 3278) {
					player.objectYOffset = 2;
				}
				break;

			case 300:
				if (player.objectX == 3093 && player.objectY == 3509) {
					player.objectDistance = 2;
				}
				break;
			case 2479:
			case 2482:
			case 10596:
			case 10595:
			case 1725:
			case 2483:
			case 4568:
			case 2145:
				player.objectDistance = 3;
				break;

			case 11993:
			case 245:
			case 246:
			case 273:
			case 272:
			case 8959:
				player.objectDistance = 1;
				break;

			case 492:
			case 5100:
			case 12127:
			case 5083:
			case 4551:
			case 4558:
			case 2634:
			case 1742:
			case 2484:
			case 8966:
			case 993:
			case 2230:
			case 2265:
			case 4569:
			case 5098:
			case 5096:
			case 5094:
			case 3828:
				player.objectDistance = 2;
				break;
				

			case 2617:
				if (player.objectX == 3077 && player.objectY == 9768) {
					player.objectDistance = 3;
				}
				break;

			case 2781:
				if (player.objectX == 3272 && player.objectY == 3185) {
					player.objectDistance = 3;
				}
				break;

			case 2216:
				if (player.absX == 2880) {
					player.objectXOffset = 3;
					player.objectYOffset = 1;
				}
				break;

			case 3760:
				if (player.objectX == 2892 && player.objectY == 10072) {
					player.objectYOffset = 2;
				}
				break;

			case 96:
				if (player.objectX == 2638 && player.objectY == 9763
						|| player.objectY == 9740) {
					player.objectXOffset = 3;
					player.objectYOffset = 1;
				}
				break;

			case 2323:
				if (player.objectX == 2703 && player.objectY == 3205) {
					player.objectXOffset = 2;
				}
				break;

			case 2322:
				if (player.objectX == 2705 && player.objectY == 3209) {
					player.objectXOffset = 4;
				}
				break;

			case 190:
				player.objectDistance = 3;
				break;

			case 154:
				player.objectDistance = 2;
				break;

			case 10885:
				player.objectYOffset = -3;
				player.objectXOffset = 1;
				break;

			case 10859:
				if (player.objectX == 3356 && player.objectY == 2847) {
					player.objectXOffset = 1;
					player.objectYOffset = -1;
				} else if (player.objectX == 3364 && player.objectY == 2833) {
					player.objectXOffset = 2;
					player.objectYOffset = 1;
				} else {
					player.objectYOffset = 2;
				}
				break;

			case 10860:
				if (player.objectX == 3372 && player.objectY == 2839) {
					player.objectYOffset = 2;
				}
				break;

			case 10886:
				player.objectXOffset = 2;
				player.objectYOffset = 1;
				break;

			case 10862:
				if (player.objectX == 3362 && player.objectY == 2849) {
					player.objectXOffset = -3;
				}
				break;

			case 6552:
				if (player.absX == 3234) {
					player.objectXOffset = 2;
					player.objectYOffset = 1;
				}
				break;

			case 2711:
				if (player.objectX == 2631 && player.objectY == 3322) {
					player.objectXOffset = 1;
					player.objectYOffset = 3;
				}
				break;

			case 3044:
				if (player.objectX == 3078 && player.objectY == 9495) {
					player.objectDistance = 3;
				}
				break;

			case 1737:
				if (player.objectX == 2662 && player.objectY == 3291) {
					player.objectYOffset = 3;
				}
				break;

			case 11737:
				player.objectYOffset = 1;
				break;

			case 11724:
				if (player.absX == 2971) {
					player.objectXOffset = 3;
				}
				break;

			case 11729:
				if (player.absY == 3340) {
					player.objectYOffset = 2;
				}
				break;

			case 11732:
				if (player.objectX == 3034 && player.objectY == 3363) {
					player.objectXOffset = 2;
				} else if (player.objectX == 3048 && player.objectY == 3352) {
					player.objectYOffset = 2;
				}
				break;

			case 11735:
				if (player.absY == 3340 || player.absY == 3384) {
					player.objectYOffset = 2;
				}
				break;

			case 11725:
				if (player.absY == 3348) {
					player.objectYOffset = 1;
				}
				break;

			case 5097:
				if (player.objectX == 2635 && player.objectY == 9514) {
					player.objectXOffset = 2;
					player.objectYOffset = 3;
				}
				break;

			case 1726:
				if (player.absX == 3285 && player.absY == 3492) {
					player.objectDistance = 1;
				} else {
					player.objectXOffset = 2;
				}
				break;

			case 1568:
				if (player.objectX == 2399 && player.objectY == 3099) {
					player.getActionSender()
							.object(9472, 2399, 3099, 0, 10);
				}
				if (player.objectX == 2400 && player.objectY == 3108) {
					player.getActionSender()
							.object(9472, 2400, 3108, 2, 10);
				}
				break;

			case 4437:
				if (player.getItemAssistant().playerHasItem(1265, 1)) {
					player.getActionSender().sendMessage(
							"You start to break up the rocks...");
					player.startAnimation(625);
					   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							container.stop();
							player.startAnimation(65535);
						}

						@Override
						public void stop() {
							player.getActionSender().object(-1,
									player.objectX, player.objectY, 0, 10);
							player.getActionSender().object(4438,
									player.objectX, player.objectY, 0, 10);
							player.getActionSender().sendMessage(
									"You break up the rocks.");
						}
					}, 3);
				}
				break;

			case 4438:
				if (player.getItemAssistant().playerHasItem(1265, 1)) {
					player.getActionSender().sendMessage(
							"You start to break up the rocks...");
					player.startAnimation(625);
					   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							stop();
							player.startAnimation(65535);
						}

						@Override
						public void stop() {
							player.getActionSender().object(-1,
									player.objectX, player.objectY, 0, 10);
							player.getActionSender().sendMessage(
									"You break up the rocks.");
						}
					}, 3);
				}
				break;

			case 4448:
				if (player.getItemAssistant().playerHasItem(1265, 1)) {
					player.getActionSender().sendMessage(
							"You start to mine the wall...");
					player.startAnimation(625);
					   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							stop();
							player.startAnimation(65535);
							player.getActionSender().sendMessage(
									"You collapse the cave wall.");
						}

						@Override
						public void stop() {
							if ((player.objectX == 2390 || player.objectX == 2393)
									&& (player.objectY == 9503 || player.objectY == 9500)) { // east
								// cave
								// side
								player.getActionSender().object(-1, 2391,
										9501, 0, 10);
								player.getActionSender().object(4437, 2391,
										9501, 0, 10);
								CastleWars.collapseCave(1);
							}
							if ((player.objectX == 2399 || player.objectX == 2402)
									&& (player.objectY == 9511 || player.objectY == 9514)) { // north
								// cave
								// side
								player.getActionSender().object(-1, 2400,
										9512, 1, 10);
								player.getActionSender().object(4437, 2400,
										9512, 1, 10);
								CastleWars.collapseCave(0);
							}
							if ((player.objectX == 2408 || player.objectX == 2411)
									&& (player.objectY == 9502 || player.objectY == 9505)) { // west
								// cave
								// side
								player.getActionSender().object(-1, 2409,
										9503, 0, 10);
								player.getActionSender().object(4437, 2409,
										9503, 0, 10);
								CastleWars.collapseCave(3);
							}
							if ((player.objectX == 2400 || player.objectX == 2403)
									&& (player.objectY == 9496 || player.objectY == 9493)) { // south
								// cave
								// side
								player.getActionSender().object(-1, 2401,
										9494, 1, 10);
								player.getActionSender().object(4437, 2401,
										9494, 1, 10);
								CastleWars.collapseCave(2);
							}
						}
					}, 3);
				}
				break;

			case 1733:
				if (player.objectX == 3058 && player.objectY == 3376) {
					player.getPlayerAssistant().movePlayer(3058, 9776, 0);
				} else if (player.objectX == 2603 && player.objectY == 3078) {
					player.objectXOffset = 3;
					player.objectYOffset = 1;
				}
				break;

			case 55:
				if (player.objectX == 3061 && player.objectY == 3374) {
					player.getPlayerAssistant().movePlayer(3058, 9776, 0);
				}
				break;

			case 9472:
				if (player.objectX == 2399 && player.objectY == 3099) {
					player.startAnimation(828);
					player.stopMovement();
					player.resetWalkingQueue();
					player.getPlayerAssistant().requestUpdates();
					player.getPlayerAssistant().removeAllWindows();
					   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							container.stop();
							player.startAnimation(65535);
							player.getPlayerAssistant().movePlayer(2400, 9507,
									0);
						}

							@Override
							public void stop() {
								
							}
					}, 1);
				}
				if (player.objectX == 2400 && player.objectY == 3108) {
					player.startAnimation(828);
					player.stopMovement();
					player.resetWalkingQueue();
					player.getPlayerAssistant().requestUpdates();
					player.getPlayerAssistant().removeAllWindows();
					   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							stop();
							player.startAnimation(65535);
							player.getPlayerAssistant().movePlayer(2399, 9500, 0);
						}

						@Override
							public void stop() {
								
							}
					}, 1);
				}
				break;

			case 4407:
				player.objectXOffset = 2;
				break;

			case 4387:
				CastleWars.addToWaitRoom(player, 1); // saradomin
				break;

			case 4388:
				CastleWars.addToWaitRoom(player, 2); // zamorak
				break;

			case 4408:
				CastleWars.addToWaitRoom(player, 3); // guthix
				break;

			case 4389: // sara
			case 4390: // zammy waiting room portal
				CastleWars.leaveWaitingRoom(player);
				break;

			/*
			 * werewolf agility course stepping stone
			 */
			case 5138:
			case 5136:
			case 5141:
			case 5133:
			case 5152:
				player.objectDistance = 2;
				break;
			/*
			 * agility pyramid gap
			 */
			case 10863:
			case 10857:
				player.objectDistance = 4;
				break;
			/*
			 * rope swing in barbarian agility arena
			 */
			case 2282:
				player.objectDistance = 4;
				break;

			case 2294:
				player.objectDistance = 4;
				break;

			case 9295:
				if (player.absX == 3155) {
					player.objectDistance = 2;
				}
				break;

			case 2491:
				if (player.objectX == 2926 && player.objectY == 4817) {
					player.objectXOffset = 1;
					player.objectYOffset = -3;
				}
				if (player.objectX == 2927 && player.objectY == 4814) {
					player.objectXOffset = -1;
					player.objectYOffset = 3;
				}
				if (player.objectX == 2893 && player.objectY == 4812) {
					player.objectXOffset = 2;
					player.objectYOffset = 5;
				}
				if (player.objectX == 2925 && player.objectY == 4848) {
					player.objectXOffset = 2;
					player.objectYOffset = 5;
				}
				if (player.objectX == 2891 && player.objectY == 4847) {
					player.objectXOffset = 2;
					player.objectYOffset = -1;
				}
				break;

			case 2609:
				player.objectYOffset = 2;
				break;

			case 4755:
				player.objectYOffset = 1;
				break;

			case 7056:
				player.objectXOffset = 2;
				break;

			case 26983:
			case 26982:
			case 24355:
			case 24354:
				player.objectYOffset = 1;
				player.objectDistance = 0;
				break;

			case 1722:
				if (player.absX == 3159) {
					player.objectXOffset = 3;
				} else if (player.objectX == 3175 && player.objectY == 3420) {
					player.objectXOffset = 1;
					player.objectYOffset = 3;
				} else if (player.objectX == 3177 && player.objectY == 3401) {
					player.objectXOffset = 3;
					player.objectYOffset = 1;
				} else if (player.absY == 3298) {
					player.objectYOffset = 3;
				}
				break;

			case 11666:
				player.objectXOffset = -1;
				player.objectYOffset = -1;
				break;

			case 1723:
				if (player.objectX == 3100 && player.objectY == 3266
						|| player.objectX == 2663 && player.objectY == 3321) {
					player.objectXOffset = 2;
					player.objectYOffset = 1;
				} else if (player.objectX == 3259 && player.objectY == 3447
						|| player.objectX == 2590 && player.objectY == 3090
						|| player.objectX == 3212 && player.objectY == 3474) {
					player.objectYOffset = 2;
				} else if (player.objectX == 2590 && player.objectY == 3085) {
					player.objectXOffset = 1;
					player.objectYOffset = 2;
				}
				break;

			case 1738:
				if (player.objectX == 3204 && player.objectY == 3207 || player.objectX == 2648 && player.objectY == 3310) {
					player.objectXOffset = 1;
					player.objectYOffset = 2;
				} else if (player.objectX == 3204 && player.objectY == 3229) {
					player.objectXOffset = 2;
				} else if (player.objectX == 2839 && player.objectY == 3537 || player.objectX == 2673 && player.objectY == 3300 || player.objectX == 2728 && player.objectY == 3460) {
					player.objectXOffset = 2;
					player.objectYOffset = 1;
				} else if (player.absX == 2746) {
					player.objectXOffset = 2;
				} else if (player.objectX == 3010 && player.objectY == 3515 || player.objectX == 2648 && player.objectY == 3310) {
					player.objectDistance = 2;
				} else if (player.objectX == 2895 && player.objectY == 3513 || player.objectX == 3144 && player.objectY == 3447) {
					player.objectDistance = 3;
				}
				break;

			case 1739:
				if (player.objectX == 3204 && player.objectY == 3207) {
					player.objectDistance = 3;
				} else if (player.objectX == 3204 && player.objectY == 3229) {
					player.objectXOffset = 2;
				} else if (player.objectX == 3204 && player.objectY == 3207) {
					player.objectXOffset = 1;
					player.objectYOffset = 2;
				}
				break;

			case 1740:
				if (player.objectX == 3205 && player.objectY == 3208) {
					player.objectXOffset = 1;
					player.objectYOffset = 2;
				} else if (player.objectX == 3144 && player.objectY == 3448) {
					player.objectDistance = 1;
				}
				break;

			case 12536:
				player.objectXOffset = 2;
				player.objectYOffset = 1;
				break;

			case 12537:
				player.objectXOffset = 1;
				player.objectYOffset = 2;
				break;

			case 12538:
				player.objectYOffset = 1;
				break;

			case 2287:
				if (player.absX == 2552 && player.absY == 3561) {
					player.objectYOffset = 2;
				} else if (player.absX == 2552 && player.absY == 3558) {
					player.objectYOffset = -1;
				}
				break;

			case 1734:
				if (player.objectX == 2569 && player.objectY == 9522) {
					player.objectXOffset = 1;
					player.objectYOffset = 3;
				} else if (player.objectX == 3187 && player.objectY == 9833) {
					player.objectXOffset = 3;
					player.objectYOffset = 1;
				}
				break;
				
			case 4493:
			case 4494:
			case 4495:
			case 4496:
				player.objectDistance = 5;
				break;

			case 6522:
			case 10229:
				player.objectDistance = 2;
				break;
				
			case 4417:
				if (player.objectX == 2425 && player.objectY == 3074) {
					player.objectYOffset = 2;
				}
				break;

			case 4420:
				if (player.getX() >= 2383 && player.getX() <= 2385) {
					player.objectYOffset = 1;
				} else {
					player.objectYOffset = -2;
				}
				// fall through
				break;

			case 2878:
			case 2879:
				player.objectDistance = 3;
				break;

			case 2558:
				player.objectDistance = 0;
				if (player.absX > player.objectX && player.objectX == 3044) {
					player.objectXOffset = 1;
				}
				if (player.absY > player.objectY) {
					player.objectYOffset = 1;
				}
				if (player.absX < player.objectX && player.objectX == 3038) {
					player.objectXOffset = -1;
				}
				break;

			case 9356:
				player.objectDistance = 2;
				break;

			case 1815:
			case 1816:
			case 5959:
			case 5960:
				player.objectDistance = 0;
				break;

			case 9293:
				player.objectDistance = 2;
				break;

			case 4418:
				if (player.objectX == 2374 && player.objectY == 3131) {
					player.objectYOffset = -2;
				} else if (player.objectX == 2369 && player.objectY == 3126) {
					player.objectXOffset = 2;
				} else if (player.objectX == 2380 && player.objectY == 3127) {
					player.objectYOffset = 2;
				} else if (player.objectX == 2369 && player.objectY == 3126) {
					player.objectXOffset = 2;
				} else if (player.objectX == 2374 && player.objectY == 3131) {
					player.objectYOffset = -2;
				}
				break;

			case 9706:
				player.objectDistance = 0;
				player.objectXOffset = 1;
				break;

			case 9707:
				player.objectDistance = 0;
				player.objectYOffset = -1;
				break;

			case 4419:
				if (player.getX() >= 2417 && player.getX() <= 2418) {
					player.objectYOffset = 3;
				} else {
					player.objectYOffset = -1;
					player.objectXOffset = -3;
					player.objectDistance = 3;
				}
				break;

			case 6707:
				player.objectYOffset = 3;
				break;

			case 6823:
				player.objectDistance = 2;
				player.objectYOffset = 1;
				break;

			case 6706:
				player.objectXOffset = 2;
				break;

			case 6772:
				player.objectDistance = 2;
				player.objectYOffset = 1;
				break;

			case 6705:
				player.objectYOffset = -1;
				break;

			case 6822:
				player.objectDistance = 2;
				player.objectYOffset = 1;
				break;

			case 6704:
				player.objectYOffset = -1;
				break;

			case 6773:
				player.objectDistance = 2;
				player.objectXOffset = 1;
				player.objectYOffset = 1;
				break;

			case 6703:
				player.objectXOffset = -1;
				break;

			case 6771:
				player.objectDistance = 2;
				player.objectXOffset = 1;
				player.objectYOffset = 1;
				break;

			case 6702:
				player.objectXOffset = -1;
				break;

			case 6821:
				player.objectDistance = 2;
				player.objectXOffset = 1;
				player.objectYOffset = 1;
				break;

			/*
			 * case 1276: case 1278: case 1306: case 1307: case 1308: case 1309:
			 * case 1281: case 1319: case 1332: case 1318: case 1330:
			 * client.objectDistance = 3; break;
			 */

			default:
				player.objectDistance = 1;
				player.objectXOffset = 0;
				player.objectYOffset = 0;
				break;
			}
			if (player.goodDistance(player.objectX + player.objectXOffset,
					player.objectY + player.objectYOffset, player.getX(),
					player.getY(), player.objectDistance)) {
				player.getObjects().firstClickObject(player.objectId,
						player.objectX, player.objectY);
			} else {
				player.clickObjectType = 1;
				   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (player.clickObjectType == 1
								&& player.goodDistance(player.objectX
										+ player.objectXOffset, player.objectY
										+ player.objectYOffset, player.getX(),
										player.getY(), player.objectDistance)) {
							player.getObjects().firstClickObject(
									player.objectId, player.objectX,
									player.objectY);
							container.stop();
						}
						if (player.clickObjectType > 1
								|| player.clickObjectType == 0) {
							container.stop();
						}
					}

					@Override
					public void stop() {
						player.clickObjectType = 0;
					}
				}, 1);
			}
			break;

		case SECOND_CLICK:
			player.objectId = player.getInStream().readUnsignedWordBigEndianA();
			player.objectY = player.getInStream().readSignedWordBigEndian();
			player.objectX = player.getInStream().readUnsignedWordA();
			player.objectDistance = 1;
			if (player.playerRights == 3) {
				player.getActionSender().sendMessage("ObjectId: " + player.objectId + " ObjectX: " + player.objectX + " ObjectY: " + player.objectY + " Objectclick = 2, Xoff: " + (player.getX() - player.objectX) + " Yoff: " + (player.getY() - player.objectY));
			}
			if (Stalls.isObject(player.objectId)) {
				Stalls.attemptStall(player, player.objectId, player.objectX, player.objectX);
				return;
			}
			switch (player.objectId) {
			case 6162:
			case 6163:
			case 6164:
			case 6165:
			case 6166:
			case 9390:
				player.objectDistance = 2;
				break;
				
			case 12537:
				player.objectDistance = 2;
				break;

			case 2781:
				if (player.objectX == 3272 && player.objectY == 3185) {
					player.objectDistance = 3;
				}
				break;

			case 1739:
				if (player.objectX == 3204 && player.objectY == 3207) {
					player.objectDistance = 3;
				} else if (player.objectX == 3204 && player.objectY == 3229) {
					player.objectXOffset = 2;
				} else if (player.objectX == 3204 && player.objectY == 3207) {
					player.objectXOffset = 1;
					player.objectYOffset = 2;
				}
				break;

			default:
				player.objectDistance = 1;
				player.objectXOffset = 0;
				player.objectYOffset = 0;
				break;
			}
			if (player.goodDistance(player.objectX + player.objectXOffset, player.objectY + player.objectYOffset, player.getX(), player.getY(), player.objectDistance)) {
				player.getObjects().secondClickObject(player.objectId, player.objectX, player.objectY);
			} else {
				player.clickObjectType = 2;
				   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (player.clickObjectType == 2 && player.goodDistance(player.objectX + player.objectXOffset, player.objectY + player.objectYOffset, player.getX(), player.getY(), player.objectDistance)) {
							player.getObjects().secondClickObject(player.objectId, player.objectX, player.objectY);
							container.stop();
						}
						if (player.clickObjectType != 2) {
							container.stop();
						}
					}
					@Override
					public void stop() {
						player.clickObjectType = 0;
					}
				}, 1);
			}
			break;

		case THIRD_CLICK: // 'F'
			player.objectX = player.getInStream().readSignedWordBigEndian();
			player.objectY = player.getInStream().readUnsignedWord();
			player.objectId = player.getInStream().readUnsignedWordBigEndianA();
			if (player.playerRights == 3) {
				player.getActionSender().sendMessage("ObjectId: " + player.objectId + " ObjectX: " + player.objectX + " ObjectY: " + player.objectY + " Objectclick = 3, Xoff: " + (player.getX() - player.objectX) + " Yoff: " + (player.getY() - player.objectY));
			}
			switch (player.objectId) {
			case 12537:
				player.objectDistance = 2;
				break;
			case 1739:
				if (player.objectX == 3204 && player.objectY == 3207) {
					player.objectDistance = 3;
				} else if (player.objectX == 3204 && player.objectY == 3229) {
					player.objectXOffset = 2;
				} else if (player.objectX == 3204 && player.objectY == 3207) {
					player.objectXOffset = 1;
					player.objectYOffset = 2;
				}
				break;
			default:
				player.objectDistance = 1;
				break;
			}
			player.objectXOffset = 0;
			player.objectYOffset = 0;
			if (player.goodDistance(player.objectX + player.objectXOffset, player.objectY + player.objectYOffset, player.getX(), player.getY(), player.objectDistance)) {
				player.getObjects().thirdClickObject(player.objectId, player.objectX, player.objectY);
			} else {
				player.clickObjectType = 3;
				   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (player.clickObjectType == 3 && player.goodDistance(player.objectX + player.objectXOffset, player.objectY + player.objectYOffset, player.getX(), player.getY(), player.objectDistance)) {
							player.getObjects().thirdClickObject(player.objectId, player.objectX, player.objectY);
							container.stop();
						}
						if (player.clickObjectType < 3) {
							container.stop();
						}
					}

					@Override
					public void stop() {
						player.clickObjectType = 0;
					}
				}, 1);
			}
			break;
			

		case FOURTH_CLICK:
			player.objectX = player.getInStream().readSignedWordBigEndianA();
			player.objectId = player.getInStream().readUnsignedWordA();
			player.objectY = player.getInStream().readUnsignedWordBigEndianA();
			if (player.playerRights == 3) {
				player.getActionSender().sendMessage("ObjectId: " + player.objectId + " ObjectX: " + player.objectX + " ObjectY: " + player.objectY + " Objectclick = 4, Xoff: " + (player.getX() - player.objectX) + " Yoff: " + (player.getY() - player.objectY));
			}
			switch (player.objectId) {
			default:
				player.objectDistance = 1;
				break;
			}
			player.objectXOffset = 0;
			player.objectYOffset = 0;
			if (player.goodDistance(player.objectX + player.objectXOffset, player.objectY + player.objectYOffset, player.getX(), player.getY(), player.objectDistance)) {
				player.getObjects().fourthClickObject(player.objectId, player.objectX, player.objectY);
			} else {
				player.clickObjectType = 4;
				   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (player.clickObjectType == 4 && player.goodDistance(player.objectX + player.objectXOffset, player.objectY + player.objectYOffset, player.getX(), player.getY(), player.objectDistance)) {
							player.getObjects().fourthClickObject(player.objectId, player.objectX, player.objectY);
							container.stop();
						}
						if (player.clickObjectType < 4) {
							container.stop();
						}
					}

					@Override
					public void stop() {
						player.clickObjectType = 0;
					}
				}, 1);
			}
			break;
		}
	}
}
