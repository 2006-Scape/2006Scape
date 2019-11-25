package com.rebotted.net.packets.impl;

import com.rebotted.GameConstants;
import com.rebotted.GameEngine;
import com.rebotted.game.content.minigames.castlewars.CastleWars;
import com.rebotted.game.content.music.sound.SoundList;
import com.rebotted.game.content.skills.SkillHandler;
import com.rebotted.game.content.skills.firemaking.Firemaking;
import com.rebotted.game.content.skills.firemaking.LogData;
import com.rebotted.game.items.impl.RareProtection;
import com.rebotted.game.npcs.impl.Pets;
import com.rebotted.game.players.Client;
import com.rebotted.net.packets.PacketType;

/**
 * Drop Item
 **/
public class DropItem implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		int itemId = player.getInStream().readUnsignedWordA();
		player.getInStream().readUnsignedByte();
		player.getInStream().readUnsignedByte();
		int slot = player.getInStream().readUnsignedWordA();
		if (!player.getItemAssistant().playerHasItem(itemId) || !RareProtection.doOtherDupe(player, itemId) || System.currentTimeMillis() - player.alchDelay < 1800 || player.stopPlayerPacket || System.currentTimeMillis() - player.buryDelay < 1800 || !CastleWars.deleteCastleWarsItems(player, itemId)) {
			return;
		}
		for (LogData logData : LogData.values()) {
			if (itemId == logData.getLogId()) {
				Firemaking.pickedUpFiremakingLog = false;
			}
		}
		for (LogData logData : LogData.values()) {
			if (itemId == logData.getLogId()) {
				if (GameEngine.objectManager.objectExists(player.absX, player.absY)) {
					player.getPacketSender().sendMessage(
							"You cannot drop a log here.");
					return;
				}
			}
		}
		if (player.duelingArena()) {
			player.getPacketSender().sendMessage(
					"You can't drop items inside the arena!");
			return;
		}
		if (player.inTrade) {
			player.getPacketSender().sendMessage(
					"You can't drop items while trading!");
			return;
		}
		if (player.hasNpc == true) {
			player.getPacketSender().sendMessage(
					"You already have a pet dropped.");
			return;
		}
		SkillHandler.resetSkills(player);
		if (player.tutorialProgress < 36 && GameConstants.TUTORIAL_ISLAND) {
			player.getPacketSender().sendMessage(
					"You can't drop items on tutorial island!");
			return;
		}

		player.endCurrentTask();

		switch (itemId) {
		case 1560:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Kitten.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Kitten.");
			}
			break;
		case 1559:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Kitten.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Kitten.");
			}
			break;
		case 1558:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Kitten.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Kitten.");
			}
			break;
		case 1557:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Kitten.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Kitten.");
			}
			break;
		case 1556:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Kitten.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Kitten.");
			}
			break;
		case 1555:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Kitten.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Kitten.");
			}
			break;
		case 1561:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Cat.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Cat.");
			}
			break;
		case 1562:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Cat.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Cat.");
			}
			break;
		case 1563:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Cat.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Cat.");
			}
			break;
		case 1564:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Cat.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Cat.");
			}
			break;
		case 1565:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Cat.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Cat.");
			}
			break;
		case 7583:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage(
						"You drop your Hell Kitten.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Hell Kitten.");
			}
			break;
		case 1566:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage("You drop your Cat.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Cat.");
			}
			break;
		case 7585:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage(
						"You drop your Hell Kitten.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Hell Kitten.");
			}
			break;
		case 4045:
			if (CastleWars.isInCw(player)) {
				int explosiveHit = 15;
				player.startAnimation(827);
				player.getItemAssistant().deleteItem(itemId, slot, player.playerItemsN[slot]);
				player.handleHitMask(explosiveHit);
				player.dealDamage(explosiveHit);
				player.getPlayerAssistant().refreshSkill(3);
				player.forcedText = "Ow! That really hurt!";
				player.forcedChatUpdateRequired = true;
				player.updateRequired = true;
			} else {
				player.getItemAssistant().deleteItem(4045, player.getItemAssistant().getItemAmount(4045));
				player.getItemAssistant().deleteItem(4046, player.getItemAssistant().getItemAmount(4046));
				player.getPacketSender().sendMessage("You can't do that! Your not in castle wars!");
			}
			break;
		case 7584:
			if (!player.hasNpc) {
				GameEngine.npcHandler.spawnNpc3(player, Pets.summonItemId(itemId),
						player.absX, player.absY - 1, player.heightLevel, 0, 120, 25, 200,
						200, false, false, true);
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				player.hasNpc = true;
				player.getPlayerAssistant().followPlayer();
				player.getPacketSender().sendMessage(
						"You drop your Hell Kitten.");
			} else {
				player.getPacketSender().sendMessage(
						"You already dropped your Hell Kitten.");
			}
			break;
		}

		boolean droppable = true;
		/*for (int i : Constants.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				droppable = false;
				break;
			}
		}*/

		for (int p : Pets.CAT_ITEMS) {
			if (p == itemId) {
				if (player.hasNpc == true) {
					droppable = false;
					break;
				}
			}
		}

		if (player.playerItemsN[slot] != 0 && itemId != -1
				&& player.playerItems[slot] == itemId + 1) {
			if (droppable) {
				for (int i = 0; i < GameConstants.DESTROYABLE_ITEMS.length; i++) { 
					if (itemId == GameConstants.DESTROYABLE_ITEMS[i]) {
						player.droppedItem = itemId;
						player.getItemAssistant().destroyInterface(itemId);
						return;
					}
				}
				if (player.underAttackBy > 0) {
					if (player.getShopAssistant().getItemShopValue(itemId) > 1000) {
						player.getPacketSender()
								.sendMessage(
										"You may not drop items worth more than 1000 while in combat.");
						return;
					}
				}
				GameEngine.itemHandler.createGroundItem(player, itemId, player.getX(),
						player.getY(), player.playerItemsN[slot], player.getId());
				player.getItemAssistant().deleteItem(itemId, slot,
						player.playerItemsN[slot]);
				if (GameConstants.SOUND) {
					player.getPacketSender().sendSound(SoundList.ITEM_DROP, 100,
							0);
				}
			} else {
				player.getPacketSender().sendMessage(
						"This items cannot be dropped.");
			}
		}
	}
}
