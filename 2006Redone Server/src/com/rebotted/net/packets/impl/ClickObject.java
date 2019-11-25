package com.rebotted.net.packets.impl;

import java.util.function.Consumer;
import com.rebotted.GameEngine;
import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.content.minigames.castlewars.CastleWarObjects;
import com.rebotted.game.content.minigames.castlewars.CastleWars;
import com.rebotted.game.content.skills.core.Woodcutting;
import com.rebotted.game.content.skills.thieving.Stalls;
import com.rebotted.game.globalworldobjects.Doors;
import com.rebotted.game.npcs.NpcHandler;
import com.rebotted.game.objects.Objects;
import com.rebotted.game.players.Client;
import com.rebotted.net.packets.PacketType;
import com.rebotted.world.clip.Region;

public class ClickObject implements PacketType {

	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252,
			THIRD_CLICK = 70, FOURTH_CLICK = 234;

	public void onObjectReached(Client player, Consumer<Client> consumer) {
		if (System.currentTimeMillis() - player.clickDelay < 300)
			return;
		player.clickDelay = System.currentTimeMillis();

		final int objectX = player.objectX;
		final int objectY = player.objectY;
		final int objectId = player.objectId;

		Objects object = Region.getObject(objectId, objectX, objectY, player.heightLevel);
		if (object == null) {
			// Since most content is coded poorly we will have to assume the object is valid
			// but we won't know the face direction, so some objects will behave incorrectly
			// if the object size is not equal on both axes and the face
			// is not set to zero. The proper fix would be when an object is removed or added
			// they are added into the region configuration properly so we can retrieve
			// the object at runtime. This will suffice for now.
			object = new Objects(objectId, objectX, objectY, player.heightLevel, 0, 10, 0);
		}

		int[] size = object.getObjectSize();

		CycleEvent objectWalkToEvent = new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (objectX != player.objectX || objectY != player.objectY || objectId != player.objectId) {
					container.stop();
					return;
				}

				int x = player.absX;
				int y = player.absY;
				int xMin = objectX - 1;
				int xMax = xMin + size[0] + 1;
				int yMin = objectY - 1;
				int yMax = yMin + size[1] + 1;

				if (x >= xMin && y >= yMin && x <= xMax && y <= yMax) {
					consumer.accept(player);
					container.stop();
				}
			}

			@Override
			public void stop() {}
		};

		player.startCurrentTask(1, objectWalkToEvent);
		objectWalkToEvent.execute(player.getCurrentTask()); // cheap hax for instant event execution, since we don't support it
	}

	@Override
	public void processPacket(final Client player, int packetType, int packetSize) {
		player.clickObjectType = player.objectX = player.objectId = player.objectY = 0;
		player.getPlayerAssistant().resetFollow();
		player.getCombatAssistant().resetPlayerAttack();
		player.getPlayerAssistant().requestUpdates();
		player.endCurrentTask();
		switch (packetType) {

			case FIRST_CLICK:
				player.objectX = player.getInStream().readSignedWordBigEndianA();
				player.objectId = player.getInStream().readUnsignedWord();
				player.objectY = player.getInStream().readUnsignedWordA();
				onObjectReached(player, (p) -> completeObjectClick(p, 1));
				break;

			case SECOND_CLICK:
				player.objectId = player.getInStream().readUnsignedWordBigEndianA();
				player.objectY = player.getInStream().readSignedWordBigEndian();
				player.objectX = player.getInStream().readUnsignedWordA();
				onObjectReached(player, (p) -> completeObjectClick(p, 2));
				break;

			case THIRD_CLICK: // 'F'
				player.objectX = player.getInStream().readSignedWordBigEndian();
				player.objectY = player.getInStream().readUnsignedWord();
				player.objectId = player.getInStream().readUnsignedWordBigEndianA();
				onObjectReached(player, (p) -> completeObjectClick(p, 3));
				break;


			case FOURTH_CLICK:
				player.objectX = player.getInStream().readSignedWordBigEndianA();
				player.objectId = player.getInStream().readUnsignedWordA();
				player.objectY = player.getInStream().readUnsignedWordBigEndianA();
				onObjectReached(player, (p) -> completeObjectClick(p, 4));
				break;
		}
	}

	public void completeObjectClick(final Client player, int objectOption) {
		player.turnPlayerTo(player.objectX, player.objectY);

		switch (objectOption) {
		case 1:
			if (player.playerRights == 3 || player.debugMode) {
				player.getPacketSender().sendMessage("ObjectId: " + player.objectId + " ObjectX: " + player.objectX + " ObjectY: " + player.objectY + " Objectclick = 1, Xoff: " + (player.getX() - player.objectX) + " Yoff: " + (player.getY() - player.objectY));
			}

			//todo: check if it's a door before fire handle
			Doors.getSingleton().handleDoor(player.objectId, player.objectX, player.objectY, player.heightLevel, player);

			/*if (client.performingAction) {
				return;
			}*/

			if (Stalls.isObject(player.objectId)) {
				Stalls.attemptStall(player, player.objectId, player.objectX, player.objectX);
				return;
			}

			if (player.teleTimer > 0) {
				player.getPacketSender().sendMessage(
						"You cannot use objects while teleporting.");
				return;
			}
			if (Math.abs(player.getX() - player.objectX) > 25 || Math.abs(player.getY() - player.objectY) > 25) {
				player.resetWalkingQueue();
				break;
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
					player.getPacketSender().sendMessage("You have already spawned a tree spirit.");
					return;
				}
				if (player.spiritTree == false && player.clickedTree == false) {
					player.getPacketSender().sendMessage("You attempt to chop the tree, and a tree spirit appears.");
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
                  GameEngine.trawler.fixNet(player);
                  break;

			case 4462:
			case 4460:
			case 4461:
			case 4463:
			case 4464:
			case 4459:
				if (!CastleWars.isInCw(player)) {
					player.getPacketSender().sendMessage("You have to be in castle wars to use these objects.");
					CastleWars.resetPlayer(player);
					return;
				}
				CastleWarObjects.handleObject(player, player.objectId, player.objectX, player.objectY);
			break;

			case 2513:
				player.getRangersGuild().fireAtTarget();
			break;

	        case 8930:
                player.fade(1975, 4409, 3);
            break;
            case 8929:
                player.fade(2442, 10147, 0);
            break;

			case 1568:
				if (player.objectX == 2399 && player.objectY == 3099) {
					player.getPacketSender()
							.object(9472, 2399, 3099, 0, 10);
				}
				if (player.objectX == 2400 && player.objectY == 3108) {
					player.getPacketSender()
							.object(9472, 2400, 3108, 2, 10);
				}
				break;

			case 4437:
				if (player.getItemAssistant().playerHasItem(1265, 1)) {
					player.getPacketSender().sendMessage(
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
							player.getPacketSender().object(-1,
									player.objectX, player.objectY, 0, 10);
							player.getPacketSender().object(4438,
									player.objectX, player.objectY, 0, 10);
							player.getPacketSender().sendMessage(
									"You break up the rocks.");
						}
					}, 3);
				}
				break;

			case 4438:
				if (player.getItemAssistant().playerHasItem(1265, 1)) {
					player.getPacketSender().sendMessage(
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
							player.getPacketSender().object(-1,
									player.objectX, player.objectY, 0, 10);
							player.getPacketSender().sendMessage(
									"You break up the rocks.");
						}
					}, 3);
				}
				break;

			case 4448:
				if (player.getItemAssistant().playerHasItem(1265, 1)) {
					player.getPacketSender().sendMessage(
							"You start to mine the wall...");
					player.startAnimation(625);
					   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							stop();
							player.startAnimation(65535);
							player.getPacketSender().sendMessage(
									"You collapse the cave wall.");
						}

						@Override
						public void stop() {
							if ((player.objectX == 2390 || player.objectX == 2393)
									&& (player.objectY == 9503 || player.objectY == 9500)) { // east
								// cave
								// side
								player.getPacketSender().object(-1, 2391,
										9501, 0, 10);
								player.getPacketSender().object(4437, 2391,
										9501, 0, 10);
								CastleWars.collapseCave(1);
							}
							if ((player.objectX == 2399 || player.objectX == 2402)
									&& (player.objectY == 9511 || player.objectY == 9514)) { // north
								// cave
								// side
								player.getPacketSender().object(-1, 2400,
										9512, 1, 10);
								player.getPacketSender().object(4437, 2400,
										9512, 1, 10);
								CastleWars.collapseCave(0);
							}
							if ((player.objectX == 2408 || player.objectX == 2411)
									&& (player.objectY == 9502 || player.objectY == 9505)) { // west
								// cave
								// side
								player.getPacketSender().object(-1, 2409,
										9503, 0, 10);
								player.getPacketSender().object(4437, 2409,
										9503, 0, 10);
								CastleWars.collapseCave(3);
							}
							if ((player.objectX == 2400 || player.objectX == 2403)
									&& (player.objectY == 9496 || player.objectY == 9493)) { // south
								// cave
								// side
								player.getPacketSender().object(-1, 2401,
										9494, 1, 10);
								player.getPacketSender().object(4437, 2401,
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
					player.getPacketSender().closeAllWindows();
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
					player.getPacketSender().closeAllWindows();
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
			}
			player.getObjects().firstClickObject(player.objectId, player.objectX, player.objectY);
			break;

		case 2:
			if (player.playerRights == 3) {
				player.getPacketSender().sendMessage("ObjectId: " + player.objectId + " ObjectX: " + player.objectX + " ObjectY: " + player.objectY + " Objectclick = 2, Xoff: " + (player.getX() - player.objectX) + " Yoff: " + (player.getY() - player.objectY));
			}
			if (Stalls.isObject(player.objectId)) {
				Stalls.attemptStall(player, player.objectId, player.objectX, player.objectX);
				return;
			}
			player.getObjects().secondClickObject(player.objectId, player.objectX, player.objectY);
			break;

		case 3: // 'F'
			if (player.playerRights == 3) {
				player.getPacketSender().sendMessage("ObjectId: " + player.objectId + " ObjectX: " + player.objectX + " ObjectY: " + player.objectY + " Objectclick = 3, Xoff: " + (player.getX() - player.objectX) + " Yoff: " + (player.getY() - player.objectY));
			}

			player.getObjects().thirdClickObject(player.objectId, player.objectX, player.objectY);
			break;
			

		case 4:
			if (player.playerRights == 3) {
				player.getPacketSender().sendMessage("ObjectId: " + player.objectId + " ObjectX: " + player.objectX + " ObjectY: " + player.objectY + " Objectclick = 4, Xoff: " + (player.getX() - player.objectX) + " Yoff: " + (player.getY() - player.objectY));
			}

			player.getObjects().fourthClickObject(player.objectId, player.objectX, player.objectY);
			break;
		}
	}



}
