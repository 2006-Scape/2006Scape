package com.rebotted.net.packets.impl;

import com.rebotted.GameConstants;
import com.rebotted.GameEngine;
import com.rebotted.game.content.minigames.castlewars.CastleWars;
import com.rebotted.game.content.music.sound.SoundList;
import com.rebotted.game.content.skills.SkillHandler;
import com.rebotted.game.content.skills.firemaking.LogData;
import com.rebotted.game.items.ItemConstants;
import com.rebotted.game.items.impl.RareProtection;
import com.rebotted.game.npcs.impl.Pets;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

/**
 * Drop Item
 **/
public class DropItem implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int itemId = player.getInStream().readUnsignedWordA();
		player.getInStream().readUnsignedByte();
		player.getInStream().readUnsignedByte();
		int slot = player.getInStream().readUnsignedWordA();
		if (!player.getItemAssistant().playerHasItem(itemId) || !RareProtection.removeItemOtherActions(player, itemId) || System.currentTimeMillis() - player.alchDelay < 1800 || player.stopPlayerPacket || System.currentTimeMillis() - player.buryDelay < 1800 || !CastleWars.deleteCastleWarsItems(player, itemId)) {
			return;
		}
		for (LogData logData : LogData.values()) {
			if (itemId == logData.getLogId()) {
				player.pickedUpFiremakingLog = false;
			}
		}
		for (LogData logData : LogData.values()) {
			if (itemId == logData.getLogId()) {
				if (GameEngine.objectManager.objectExists(player.absX, player.absY)) {
					player.getPacketSender().sendMessage("You cannot drop a log here.");
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
		if (player.hasNpc) {
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
		if (Pets.isCatItem(itemId)) {
			Pets.dropPet(player, itemId, slot);
		}

		player.endCurrentTask();
		switch (itemId) {
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
				if (player.hasNpc) {
					droppable = false;
					break;
				}
			}
		}
		if (player.playerItemsN[slot] != 0 && itemId != -1 && player.playerItems[slot] == itemId + 1) {
			if (droppable) {
				for (int i = 0; i < ItemConstants.DESTROYABLE_ITEMS.length; i++) { 
					if (itemId == ItemConstants.DESTROYABLE_ITEMS[i]) {
						player.droppedItem = itemId;
						player.getItemAssistant().destroyInterface(itemId);
						return;
					}
				}
				GameEngine.itemHandler.createGroundItem(player, itemId, player.getX(), player.getY(), player.playerItemsN[slot], player.getId());
				player.getItemAssistant().deleteItem(itemId, slot, player.playerItemsN[slot]);
				if (GameConstants.SOUND) {
					player.getPacketSender().sendSound(SoundList.ITEM_DROP, 100, 0);
				}
			} else {
				player.getPacketSender().sendMessage("This item cannot be dropped.");
			}
		}
	}
}
