package redone.net.packets.impl;

import redone.game.content.skills.SkillHandler;
import redone.game.content.skills.cooking.Cooking;
import redone.game.content.skills.core.Fishing;
import redone.game.content.skills.smithing.Smelting;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.net.packets.PacketType;

/**
 * Walking packet
 **/
public class Walking implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		player.getDueling().checkDuelWalk();
		if (player.canChangeAppearance) { //|| c.performingAction) {
			return;
		}
		if (player.getCannon().settingUp) {
			return;
		}
		if (player.isTeleporting == true) {
			player.isTeleporting = false;
		}
		if (player.playerSkilling[10]) {// fishing
			Fishing.resetFishing(player);
		}
		if (player.playerIsCooking) {// cooking
			Cooking.resetCooking(player);
		}
		if (player.playerSkilling[13]) {// smelting
			Smelting.resetSmelting(player);
		}
		if (player.playerStun) {
			return;
		}
		if (player.stopPlayer == true) {
			return;
		}
		if (player.isBotting == true) {
			player.getActionSender().sendMessage("Please type ::amibotting if you would like to move again");
			return;
		}
		if (player.isFiremaking == true) {
			player.isFiremaking = false;
		}
		if (player.stopPlayerPacket == true) {
			return;
		}
		if (player.inTrade) {
			player.inTrade = false;
		}
		if (player.tutorialProgress > 35 && !player.isSmithing) {
			player.getPlayerAssistant().closeAllWindows();
		} else if (player.tutorialProgress < 36 && player.isSmithing) {
			player.getPlayerAssistant().closeAllWindows();
			player.isSmithing = false;
		}
		SkillHandler.resetSkills(player);
		if (player.closeTutorialInterface == false && player.tutorialProgress == 36) {
			player.getDialogueHandler().sendDialogues(3116, player.npcType);
		}
		if (player.gliderOpen == true) {
			player.gliderOpen = false;
		}
		if (player.isBanking == true) {
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
		if (packetType == 248 || packetType == 164) {
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
					player.getActionSender().sendMessage(
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
						&& packetType != 98) {
					player.playerIndex = 0;
					return;
				}
			}
			if (packetType != 98) {
				player.getActionSender().sendMessage(
						"A magical force stops you from moving.");
				player.playerIndex = 0;
			}
			return;
		}

		if (System.currentTimeMillis() - player.lastSpear < 4000) {
			player.getActionSender().sendMessage("You have been stunned.");
			player.playerIndex = 0;
			return;
		}

		if (packetType == 98) {
			player.mageAllowed = true;
		}

		if (player.WildernessWarning == false && player.wildLevel > 0) {
			player.resetWalkingQueue();
			player.WildernessWarning = true;
			player.getPlayerAssistant().sendFrame126("WARNING!", 6940);
			player.getPlayerAssistant().showInterface(1908);
		}

		  if(player.openDuel) {
	            Client o = (Client) PlayerHandler.players[player.duelingWith];
	            if(o != null)
	                o.getDueling().declineDuel();
	            player.getDueling().declineDuel();
	        }
	        if((player.duelStatus >= 1 && player.duelStatus <= 4) || player.duelStatus == 6) {
	            if(player.duelStatus == 6) {
	                player.getDueling().claimStakedItems();        
	            }
	            return;
	        }

		if (player.respawnTimer > 3) {
			return;
		}
		if (packetType == 248) {
			packetSize -= 14;
		}
		player.newWalkCmdSteps = (packetSize - 5) / 2;
		if (++player.newWalkCmdSteps > player.walkingQueueSize) {
			player.newWalkCmdSteps = 0;
			return;
		}

		player.getNewWalkCmdX()[0] = player.getNewWalkCmdY()[0] = 0;

		int firstStepX = player.getInStream().readSignedWordBigEndianA()
				- player.getMapRegionX() * 8;
		for (int i = 1; i < player.newWalkCmdSteps; i++) {
			player.getNewWalkCmdX()[i] = player.getInStream().readSignedByte();
			player.getNewWalkCmdY()[i] = player.getInStream().readSignedByte();
		}

		int firstStepY = player.getInStream().readSignedWordBigEndian() - player.getMapRegionY() * 8;
		player.setNewWalkCmdIsRunning(player.getInStream().readSignedByteC() == 1 && player.playerEnergy > 0);
		for (int i1 = 0; i1 < player.newWalkCmdSteps; i1++) {
			player.getNewWalkCmdX()[i1] += firstStepX;
			player.getNewWalkCmdY()[i1] += firstStepY;
		}
	}

}
