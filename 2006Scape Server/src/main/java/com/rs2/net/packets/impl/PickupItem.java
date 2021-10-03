package com.rs2.net.packets.impl;

import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.minigames.castlewars.CastleWars;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.content.skills.firemaking.LogData;
import com.rs2.game.items.ItemAssistant;
import com.rs2.game.items.impl.RareProtection;
import com.rs2.game.players.Player;
import com.rs2.net.packets.PacketType;
import com.rs2.util.GameLogger;
import com.rs2.world.GlobalDropsHandler;

/**
 * Pickup Item
 **/
public class PickupItem implements PacketType {


	@Override
	public void processPacket(final Player player, int packetType, int packetSize) {
		player.pItemY = player.getInStream().readSignedWordBigEndian();
		player.pItemId = player.getInStream().readUnsignedWord();
		player.pItemX = player.getInStream().readSignedWordBigEndian();
		// Cannot pickup the telekinetic guardian statue
		if (player.pItemId == 6888) {
			// TODO: Move camera to observe the current maze
			return;
		}
		if (player.getItemAssistant().freeSlots() < 1)
		{
			if (!(player.getItemAssistant().playerHasItem(player.pItemId) && player.getItemAssistant().isStackable(player.pItemId)))
			{
				player.getPacketSender().sendMessage("Not enough space in your inventory.");
				return;
			}
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
		String itemName = ItemAssistant.getItemName(player.pItemId).toLowerCase();
		if (player.getPlayerAssistant().isPlayer()) {
			GameLogger.writeLog(player.playerName, "pickupitem", player.playerName + " picked up " + itemName + " itemX: " + player.pItemX + ", itemY: " + player.pItemY + "");
		}
		if (!CastleWars.deleteCastleWarsItems(player, player.pItemId)) {
			return;
		}
		if (!RareProtection.removeItemOtherActions(player, player.pItemId)) {
			return;
		}
		if (player.pItemY > 9817 && player.pItemY < 9825 && player.pItemX > 3186 && player.pItemX < 3197 || player.pItemX > 3107 && player.pItemX < 3113 && player.pItemY > 3155 && player.pItemY < 3159 && player.heightLevel == 2) {
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
					if ((player.getX() == player.pItemX && player.getY() == player.pItemY
							|| player.getX() - 1 == player.pItemX && player.getY() == player.pItemY
							|| player.getY() - 1 == player.pItemY && player.getX() == player.pItemX
							|| player.getX() + 1 == player.pItemX && player.getY() == player.pItemY
							|| player.getY() + 1 == player.pItemY && player.getX() == player.pItemX
						||player.getX() == player.pItemX && player.getY() == player.pItemY) && player.walkingToItem) {
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
