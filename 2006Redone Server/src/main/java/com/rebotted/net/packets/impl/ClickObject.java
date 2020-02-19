package com.rebotted.net.packets.impl;

import java.util.function.Consumer;
import com.rebotted.GameEngine;
import com.rebotted.event.CycleEvent;
import com.rebotted.event.CycleEventContainer;
import com.rebotted.event.CycleEventHandler;
import com.rebotted.game.content.minigames.castlewars.CastleWarObjects;
import com.rebotted.game.content.minigames.castlewars.CastleWars;
import com.rebotted.game.content.skills.woodcutting.Woodcutting;
import com.rebotted.game.globalworldobjects.Doors;
import com.rebotted.game.npcs.NpcHandler;
import com.rebotted.game.objects.Objects;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;
import com.rebotted.world.clip.Region;

public class ClickObject implements PacketType {

	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252,
			THIRD_CLICK = 70, FOURTH_CLICK = 234;

	public void onObjectReached(Player player, Consumer<Player> consumer) {
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

				if ((x >= xMin && y >= yMin && x <= xMax && y <= yMax) || (player.getRangersGuild().isInTargetArea() && player.objectId == 2513)) {
					consumer.accept(player);
					container.stop();
				}
			}

			@Override
			public void stop() {
				
			}
		};

		player.startCurrentTask(1, objectWalkToEvent);
		objectWalkToEvent.execute(player.getCurrentTask()); // cheap hax for instant event execution, since we don't support it
	}

	@Override
	public void processPacket(final Player player, int packetType, int packetSize) {
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

	public void completeObjectClick(final Player p, int objectOption) {
		p.turnPlayerTo(p.objectX, p.objectY);

		switch (objectOption) {
		case 1:
			if (p.playerRights == 3 || p.debugMode) {
				p.getPacketSender().sendMessage("ObjectId: " + p.objectId + " ObjectX: " + p.objectX + " ObjectY: " + p.objectY + " Objectclick = 1, Xoff: " + (p.getX() - p.objectX) + " Yoff: " + (p.getY() - p.objectY));
			}

			//todo: check if it's a door before fire handle
			Doors.getSingleton().handleDoor(p, p.objectId, p.objectX, p.objectY, p.heightLevel);

			if (p.teleTimer > 0) {
				p.getPacketSender().sendMessage("You cannot use objects while teleporting.");
				return;
			}
			if (Math.abs(p.getX() - p.objectX) > 25 || Math.abs(p.getY() - p.objectY) > 25) {
				p.resetWalkingQueue();
				break;
			}
			if (Woodcutting.playerTrees(p, p.objectId) && p.objectId != 1292) {
				Woodcutting.startWoodcutting(p, p.objectId, p.objectX, p.objectY, p.clickObjectType);
			}
			switch (p.objectId) {
			case 1292:
				if (p.spiritTree == false && p.clickedTree) {
					p.getPacketSender().sendMessage("You have already spawned a tree spirit.");
					return;
				}
				if (p.spiritTree == false && p.clickedTree == false) {
					p.getPacketSender().sendMessage("You attempt to chop the tree, and a tree spirit appears.");
					NpcHandler.spawnNpc(p, 655, p.getX(), p.getY(), 0, 0, 225, 20, 80, 80, true, false);
					p.clickedTree = true;
				} else if (p.spiritTree) {
					Woodcutting.startWoodcutting(p, p.objectId, p.objectX, p.objectY, p.clickObjectType);
				}
				break;

			case 1294:
			case 1293:
			case 1317:
				p.getPlayerAssistant().spiritTree();
				break;

		      case 2164:
              case 2165:
                  GameEngine.trawler.fixNet(p);
                  break;

			case 4462:
			case 4460:
			case 4461:
			case 4463:
			case 4464:
			case 4459:
				if (!CastleWars.isInCw(p)) {
					p.getPacketSender().sendMessage("You have to be in castle wars to use these objects.");
					CastleWars.resetPlayer(p);
					return;
				}
				CastleWarObjects.handleObject(p, p.objectId, p.objectX, p.objectY);
			break;

			case 2513:
				p.getRangersGuild().fireAtTarget();
			break;

	        case 8930:
                p.fade(1975, 4409, 3);
            break;
            case 8929:
                p.fade(2442, 10147, 0);
            break;

			case 1568:
				if (p.objectX == 2399 && p.objectY == 3099) {
					p.getPacketSender()
							.object(9472, 2399, 3099, 0, 10);
				}
				if (p.objectX == 2400 && p.objectY == 3108) {
					p.getPacketSender()
							.object(9472, 2400, 3108, 2, 10);
				}
				break;

			case 4437:
				if (p.getItemAssistant().playerHasItem(1265, 1)) {
					p.getPacketSender().sendMessage(
							"You start to break up the rocks...");
					p.startAnimation(625);
					   CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							container.stop();
							p.startAnimation(65535);
						}

						@Override
						public void stop() {
							p.getPacketSender().object(-1,
									p.objectX, p.objectY, 0, 10);
							p.getPacketSender().object(4438,
									p.objectX, p.objectY, 0, 10);
							p.getPacketSender().sendMessage(
									"You break up the rocks.");
						}
					}, 3);
				}
				break;

			case 4438:
				if (p.getItemAssistant().playerHasItem(1265, 1)) {
					p.getPacketSender().sendMessage(
							"You start to break up the rocks...");
					p.startAnimation(625);
					   CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							stop();
							p.startAnimation(65535);
						}

						@Override
						public void stop() {
							p.getPacketSender().object(-1,
									p.objectX, p.objectY, 0, 10);
							p.getPacketSender().sendMessage(
									"You break up the rocks.");
						}
					}, 3);
				}
				break;

			case 4448:
				if (p.getItemAssistant().playerHasItem(1265, 1)) {
					p.getPacketSender().sendMessage(
							"You start to mine the wall...");
					p.startAnimation(625);
					   CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							stop();
							p.startAnimation(65535);
							p.getPacketSender().sendMessage(
									"You collapse the cave wall.");
						}

						@Override
						public void stop() {
							if ((p.objectX == 2390 || p.objectX == 2393)
									&& (p.objectY == 9503 || p.objectY == 9500)) { // east
								// cave
								// side
								p.getPacketSender().object(-1, 2391,
										9501, 0, 10);
								p.getPacketSender().object(4437, 2391,
										9501, 0, 10);
								CastleWars.collapseCave(1);
							}
							if ((p.objectX == 2399 || p.objectX == 2402)
									&& (p.objectY == 9511 || p.objectY == 9514)) { // north
								// cave
								// side
								p.getPacketSender().object(-1, 2400,
										9512, 1, 10);
								p.getPacketSender().object(4437, 2400,
										9512, 1, 10);
								CastleWars.collapseCave(0);
							}
							if ((p.objectX == 2408 || p.objectX == 2411)
									&& (p.objectY == 9502 || p.objectY == 9505)) { // west
								// cave
								// side
								p.getPacketSender().object(-1, 2409,
										9503, 0, 10);
								p.getPacketSender().object(4437, 2409,
										9503, 0, 10);
								CastleWars.collapseCave(3);
							}
							if ((p.objectX == 2400 || p.objectX == 2403)
									&& (p.objectY == 9496 || p.objectY == 9493)) { // south
								// cave
								// side
								p.getPacketSender().object(-1, 2401,
										9494, 1, 10);
								p.getPacketSender().object(4437, 2401,
										9494, 1, 10);
								CastleWars.collapseCave(2);
							}
						}
					}, 3);
				}
				break;

			case 1733:
				if (p.objectX == 3058 && p.objectY == 3376) {
					p.getPlayerAssistant().movePlayer(3058, 9776, 0);
				} else if (p.objectX == 2603 && p.objectY == 3078) {
				}
				break;

			case 55:
				if (p.objectX == 3061 && p.objectY == 3374) {
					p.getPlayerAssistant().movePlayer(3058, 9776, 0);
				}
				break;

			case 9472:
				if (p.objectX == 2399 && p.objectY == 3099) {
					p.startAnimation(828);
					p.stopMovement();
					p.resetWalkingQueue();
					p.getPlayerAssistant().requestUpdates();
					p.getPacketSender().closeAllWindows();
					   CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							container.stop();
							p.startAnimation(65535);
							p.getPlayerAssistant().movePlayer(2400, 9507,
									0);
						}

							@Override
							public void stop() {

							}
					}, 1);
				}
				if (p.objectX == 2400 && p.objectY == 3108) {
					p.startAnimation(828);
					p.stopMovement();
					p.resetWalkingQueue();
					p.getPlayerAssistant().requestUpdates();
					p.getPacketSender().closeAllWindows();
					   CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
				            @Override
				            public void execute(CycleEventContainer container) {
							stop();
							p.startAnimation(65535);
							p.getPlayerAssistant().movePlayer(2399, 9500, 0);
						}

						@Override
							public void stop() {

							}
					}, 1);
				}
				break;

			case 4387:
				CastleWars.addToWaitRoom(p, 1); // saradomin
				break;

			case 4388:
				CastleWars.addToWaitRoom(p, 2); // zamorak
				break;

			case 4408:
				CastleWars.addToWaitRoom(p, 3); // guthix
				break;

			case 4389: // sara
			case 4390: // zammy waiting room portal
				CastleWars.leaveWaitingRoom(p);
				break;
			}
			p.getObjects().firstClickObject(p.objectId, p.objectX, p.objectY);
			break;

		case 2:
			if (p.playerRights == 3) {
				p.getPacketSender().sendMessage("ObjectId: " + p.objectId + " ObjectX: " + p.objectX + " ObjectY: " + p.objectY + " Objectclick = 2, Xoff: " + (p.getX() - p.objectX) + " Yoff: " + (p.getY() - p.objectY));
			}
			p.getObjects().secondClickObject(p.objectId, p.objectX, p.objectY);
			break;

		case 3: // 'F'
			if (p.playerRights == 3) {
				p.getPacketSender().sendMessage("ObjectId: " + p.objectId + " ObjectX: " + p.objectX + " ObjectY: " + p.objectY + " Objectclick = 3, Xoff: " + (p.getX() - p.objectX) + " Yoff: " + (p.getY() - p.objectY));
			}

			p.getObjects().thirdClickObject(p.objectId, p.objectX, p.objectY);
			break;
			

		case 4:
			if (p.playerRights == 3) {
				p.getPacketSender().sendMessage("ObjectId: " + p.objectId + " ObjectX: " + p.objectX + " ObjectY: " + p.objectY + " Objectclick = 4, Xoff: " + (p.getX() - p.objectX) + " Yoff: " + (p.getY() - p.objectY));
			}
			
			p.getObjects().fourthClickObject(p.objectId, p.objectX, p.objectY);
			break;
		}
	}
}
