package com.rs2.net.packets.impl;

import com.rs2.Constants;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.content.skills.cooking.Cooking;
import com.rs2.game.content.skills.core.Fishing;
import com.rs2.game.content.skills.smithing.Smelting;
import com.rs2.game.items.ItemConstants;
import com.rs2.game.items.impl.Greegree;
import com.rs2.game.items.impl.Greegree.MonkeyData;
import com.rs2.game.players.Client;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

/**
 * Walking packet
 **/
public class Walking implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		player.getDueling().checkDuelWalk();
		if (player.playerIsBusy()) {
			player.playerIsBusy = false;
		}	
		if (MonkeyData.isWearingGreegree(player) && !Greegree.canWear(player)) {
			Greegree.resetAnimations(player);
			player.getItemAssistant().removeItem(ItemConstants.WEAPON);
		}
		if (player.canChangeAppearance) { //|| c.performingAction) {
			return;
		}
		if (player.getCannon().settingUp) {
			return;
		}
		if (player.isTeleporting) {
			player.isTeleporting = false;
		}
		if (player.playerSkilling[Constants.FISHING]) {// fishing
			Fishing.resetFishing(player);
		}
		if (player.playerIsCooking) {// cooking
			Cooking.setCooking(player, false);
		}
		if (player.playerSkilling[Constants.SMITHING]) {// smelting
			Smelting.resetSmelting(player);
		}
		if (player.playerStun) {
			return;
		}
		if (player.stopPlayer) {
			return;
		}
		if (player.isFiremaking) {
			player.isFiremaking = false;
		}
		if (player.stopPlayerPacket) {
			return;
		}
		if (player.inTrade) {
			player.inTrade = false;
			player.getTrading().declineTrade(true);
		}
		if (player.tutorialProgress > 35 && !player.isSmithing) {
			player.getPacketSender().closeAllWindows();
		} else if (player.tutorialProgress < 36 && player.isSmithing) {
			player.getPacketSender().closeAllWindows();
			player.isSmithing = false;
		}
		SkillHandler.resetSkills(player);
		if (player.closeTutorialInterface == false && player.tutorialProgress == 36) {
			player.getDialogueHandler().sendDialogues(3116, player.npcType);
		}
		if (player.gliderOpen) {
			player.gliderOpen = false;
		}
		if (player.isBanking) {
			player.isBanking = false;
		}
		if (player.canWalkTutorial == false && player.tutorialProgress < 36) {
			return;
		}
		if (player.followId > 0 || player.followId2 > 0) {
			player.getPlayerAssistant().resetFollow();
		}
		if (player.getPlayerAction().checkWalking() == false) {
			return;
		}
		if (packet.getOpcode() == 248 || packet.getOpcode() == 164) {
			player.faceUpdate(0);
			player.npcIndex = 0;
			player.playerIndex = 0;
			if (player.clickObjectType > 0) {
				player.clickObjectType = 0;
			} else if (player.clickNpcType > 0) {
				player.clickNpcType = 0;
			}
		}

		if (player.duelRule[1] && player.duelStatus == 5) {
			if (PlayerHandler.players[player.duelingWith] != null) {
				if (!player.goodDistance(player.getX(), player.getY(),
						PlayerHandler.players[player.duelingWith].getX(),
						PlayerHandler.players[player.duelingWith].getY(), 1)
						|| player.attackTimer == 0) {
					player.getPacketSender().sendMessage(
							"Walking has been disabled in this duel!");
				}
			}
			player.playerIndex = 0;
			return;
		}


		if (player.freezeTimer > 0) {
			if (PlayerHandler.players[player.playerIndex] != null) {
				if (player.goodDistance(player.getX(), player.getY(),
						PlayerHandler.players[player.playerIndex].getX(),
						PlayerHandler.players[player.playerIndex].getY(), 1)
						&& packet.getOpcode() != 98) {
					player.playerIndex = 0;
					return;
				}
			}
			if (packet.getOpcode() != 98) {
				player.getPacketSender().sendMessage("A magical force stops you from moving.");
				player.playerIndex = 0;
			}
			return;
		}

		if (System.currentTimeMillis() - player.lastSpear < 4000) {
			player.getPacketSender().sendMessage("You have been stunned.");
			player.playerIndex = 0;
			return;
		}

		if (packet.getOpcode() == 98) {
			player.mageAllowed = true;
		}

		if (!player.wildernessWarning && player.wildLevel > 0) {
			player.resetWalkingQueue();
			player.wildernessWarning = true;
			player.getPacketSender().sendString("WARNING!", 6940);
			player.getPacketSender().showInterface(1908);
		}
		if (player.openDuel && player.duelStatus <= 3) {
			Client o = (Client) PlayerHandler.players[player.duelingWith];
			if (o != null) {
				o.getDueling().declineDuel();
			}
			player.getDueling().declineDuel();
		}
		if ((player.duelStatus >= 1 && player.duelStatus <= 4) || player.duelStatus == 6) {
			if (player.duelStatus == 6) {
				player.getDueling().claimStakedItems();
			}
			return;
		}

		if (player.respawnTimer > 3) {
			return;
		}

		if (player.wearId == 6583 || player.wearId == 7927) {
			player.getPlayerAssistant().unMorphPlayer();
			player.getPacketSender().sendMessage("You Can't Move While Being Morphed");
		}

		player.endCurrentTask();
		int packetSize = packet.getLength();
		if (packet.getOpcode() == 248) {
			packetSize -= 14;
		}
//
//		if (player.clickToTele) {
//			player.newWalkCmdSteps = (packetSize - 5) / 2;
//			if (++player.newWalkCmdSteps > player.walkingQueueSize) {
//				player.newWalkCmdSteps = 0;
//				return;
//			}
//			player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;
//			int firstStepX, firstStepY;
//			firstStepX = player.getInStream().readSignedWordBigEndianA();
//			for (int i = 1; i < player.newWalkCmdSteps; i++) {
//				player.getNewWalkCmdX()[i] = player.getInStream().readSignedByte();
//				player.getNewWalkCmdY()[i] = player.getInStream().readSignedByte();
//			}
//			firstStepY = player.getInStream().readSignedWordBigEndian();
//			player.setNewWalkCmdIsRunning(player.getInStream().readSignedByteC() == 1 && player.playerEnergy > 0);
//			for (int i1 = 0; i1 < player.newWalkCmdSteps; i1++) {
//				player.getPlayerAssistant().movePlayer(player.getNewWalkCmdX()[i1] + firstStepX, player.getNewWalkCmdY()[i1] + firstStepY, player.heightLevel);
//				player.getNewWalkCmdX()[i1] += firstStepX;
//				player.getNewWalkCmdY()[i1] += firstStepY;
//			}
//		}
//		else {
//			player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;
//			int steps = (packetSize - 5) / 2;
//
//			int[][] path = new int[steps][2];
//			int firstStepX = player.getInStream().readSignedWordBigEndianA();
//			for (int i = 0; i < steps; i++) {
//				path[i][0] = player.getInStream().readSignedByte();
//				path[i][1] = player.getInStream().readSignedByte();
//			}
//			int firstStepY = player.getInStream().readSignedWordBigEndian();
//			int x = firstStepX;
//			int y = firstStepY;
//			player.setNewWalkCmdIsRunning(player.isRunning2 && player.playerEnergy > 0);
//			for (int i = 0; i < steps; i++) {
//				path[i][0] += firstStepX;
//				path[i][1] += firstStepY;
//				x = path[i][0];
//				y = path[i][1];
//			}
//			//System.out.println("Player has requested to walk to: "+x+","+y);
//			player.getPlayerAssistant().playerWalk(x, y);
//		}
//	}
//}
		player.newWalkCmdSteps = (packetSize - 5) / 2;
		if (++player.newWalkCmdSteps > player.walkingQueueSize) {
			player.newWalkCmdSteps = 0;
			return;
		}

		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;

		int firstStepX, firstStepY;
		int realX = 0;
		int realY = 0;

		if (player.clickToTele) {
			firstStepX = packet.readSignedWordBigEndianA();
		} else {
			realX = packet.readSignedWordBigEndianA();
			firstStepX = realX - player.getMapRegionX() * 8;
		}
		for (int i = 1; i < player.newWalkCmdSteps; i++) {
			player.getNewWalkCmdX()[i] = packet.readSignedByte();
			player.getNewWalkCmdY()[i] = packet.readSignedByte();
		}

		if (player.clickToTele) {
			firstStepY = packet.readSignedWordBigEndian();
		} else {
			realY = packet.readSignedWordBigEndian();
			firstStepY = realY - player.getMapRegionY() * 8;
		}

		if (!player.clickToTele) {
			if (player.distanceToPoint(realX, realY) > 30) {
				return;
			}
		}

		player.setNewWalkCmdIsRunning(packet.readSignedByteC() == 1 && player.playerEnergy > 0);
		for (int i1 = 0; i1 < player.newWalkCmdSteps; i1++) {
			if (player.clickToTele)
				player.getPlayerAssistant().movePlayer(player.getNewWalkCmdX()[i1] + firstStepX, player.getNewWalkCmdY()[i1] + firstStepY, player.heightLevel);
			player.getNewWalkCmdX()[i1] += firstStepX;
			player.getNewWalkCmdY()[i1] += firstStepY;
		}
	}

}
