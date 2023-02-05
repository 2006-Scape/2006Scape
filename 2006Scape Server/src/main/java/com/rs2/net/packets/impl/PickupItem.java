package com.rs2.net.packets.impl;

import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.minigames.castlewars.CastleWars;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.content.skills.firemaking.LogData;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.items.impl.RareProtection;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.util.GameLogger;
import com.rs2.world.Boundary;
import com.rs2.world.GlobalDropsHandler;
import com.rs2.world.clip.PathFinder;

/**
 * Pickup Item
 **/
public class PickupItem implements PacketType {


	@Override
	public void processPacket(final Player player, Packet packet) {
		player.pItemY = packet.readSignedWordBigEndian();
		player.pItemId = packet.readUnsignedWord();
		player.pItemX = packet.readSignedWordBigEndian();
		// Cannot pickup the telekinetic guardian statue, should show overview of current maze
		if (player.pItemId == 6888) {
			player.getMageTrainingArena().telekinetic.observeStatue(player.pItemX, player.pItemY);
			return;
		}
		// Disabled for now, doesn't detect open doors etc
		// if (!PathFinder.getPathFinder().accessible(player.getX(), player.getY(), player.heightLevel, player.pItemX, player.pItemY)) {
		// 	player.getPacketSender().sendMessage("You can't reach this item.");
		// 	return;
		// }
		if (player.getItemAssistant().freeSlots(player.pItemId, 1) <= 0) {
			player.getPacketSender().sendMessage("Not enough space in your inventory.");
			return;
		}
		if (Math.abs(player.getX() - player.pItemX) > 25 || Math.abs(player.getY() - player.pItemY) > 25) {
			player.resetWalkingQueue();
			return;
		}
		player.getCombatAssistant().resetPlayerAttack();
		player.endCurrentTask();
		if (player.stopPlayerPacket) {
			return;
		}
		String itemName = DeprecatedItems.getItemName(player.pItemId).toLowerCase();
		if (player.getPlayerAssistant().isPlayer()) {
			GameLogger.writeLog(player.playerName, "pickupitem", player.playerName + " picked up " + itemName + " itemX: " + player.pItemX + ", itemY: " + player.pItemY + "");
		}
		if (!CastleWars.deleteCastleWarsItems(player, player.pItemId)) {
			return;
		}
		if (!RareProtection.removeItemOtherActions(player, player.pItemId)) {
			return;
		}
		if (Boundary.isIn(player.pItemX, player.pItemY, player.heightLevel, Boundary.VARROCK_BANK_BASEMENT)
			|| Boundary.isIn(player.pItemX, player.pItemY, player.heightLevel, Boundary.MAGE_TOWER_CAGE)) {
			player.getPacketSender().sendMessage("You can't pick up these items!");
			return;
		}
		for (LogData logData : LogData.values()) {
			if (player.isFiremaking && player.pItemId == logData.getLogId()) {
				player.getPacketSender().sendMessage("You can't do that!");
				player.stopFiremaking = true;
				return;
			}
		}
		for (LogData logData : LogData.values()) {
			if (player.pItemId == logData.getLogId()) {
				player.pickedUpFiremakingLog = true;
			}
		}
		SkillHandler.resetSkills(player);
		player.getCombatAssistant().resetPlayerAttack();
		player.walkingToItem = true;
		player.soundDone = false;
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (!player.walkingToItem) {
					container.stop();
				}
				if (player.goodDistance(player.getX(), player.getY(), player.pItemX, player.pItemY, 1) && player.walkingToItem) {
					container.stop();
				}
			}

			@Override
			public void stop() {
				player.walkingToItem = false;
				GameEngine.itemHandler.removeGroundItem(player, player.pItemId, player.pItemX, player.pItemY, true);
				GlobalDropsHandler.pickup(player, player.pItemId, player.pItemX, player.pItemY);
				if (!player.soundDone)
				{
					player.soundDone = true;
					player.getPacketSender().sendSound(SoundList.ITEM_PICKUP, 100, 0);
				}
			}
		}, 1);
	}
}
