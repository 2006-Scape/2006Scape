package com.rs2.net.packets.impl;

import com.rs2.Constants;
import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.world.GlobalDropsHandler;
import com.rs2.world.clip.PathFinder;

/**
 * Magic on floor items
 **/

public class MagicOnFloorItems implements PacketType {

	@Override
	public void processPacket(final Player player, Packet packet) {
		final int itemY = packet.readSignedWordBigEndian();
		int itemId = packet.readUnsignedWord();
		final int itemX = packet.readSignedWordBigEndian();
		packet.readUnsignedWordA();
		player.stopMovement();

		if (!GameEngine.itemHandler.itemExists(itemId, itemX, itemY)) {
			player.getPacketSender().sendMessage("This item no longer exist.");
			return;
		}
		if (System.currentTimeMillis() - player.teleGrabDelay <= 1550) {
			return;
		}
		if (player.getItemAssistant().freeSlots(itemId, 1) <= 0) {
			player.getPacketSender().sendMessage("You don't have enough space in your inventory.");
			return;
		}
		if (!player.goodDistance(player.getX(), player.getY(), itemX, itemY, 12)) {
			return;
		}
		if (!PathFinder.isProjectilePathClear(player.getX(), player.getY(), player.heightLevel, itemX, itemY)) {
			player.getPacketSender().sendMessage("You can't see this item.");
			return;
		}

		player.endCurrentTask();
		player.usingMagic = true;
		if (!player.getCombatAssistant().checkMagicReqs(51)) {
			return;
		}

		if (itemId == 6888 && player.goodDistance(player.getX(), player.getY(), itemX, itemY, 20)) {
			player.getMageTrainingArena().telekinetic.moveStatue(itemX, itemY);
			return;
		}
		
		player.walkingToItem = true;
		int offY = (player.getX() - itemX) * -1;
		int offX = (player.getY() - itemY) * -1;
		player.teleGrabX = itemX;
		player.teleGrabY = itemY;
		player.teleGrabItem = itemId;
		player.turnPlayerTo(itemX, itemY);
		player.teleGrabDelay = System.currentTimeMillis();
		player.startAnimation(MagicData.MAGIC_SPELLS[51][2]);
		player.gfx100(MagicData.MAGIC_SPELLS[51][3]);
		player.getPlayerAssistant().createPlayersStillGfx(144, itemX, itemY, 0, 72);
		player.getPlayerAssistant().createPlayersProjectile(player.getX(),
				player.getY(), offX, offY, 50, 70,
				MagicData.MAGIC_SPELLS[51][4], 50, 10, 0, 50);
		player.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[51][7], 6);
		player.getPlayerAssistant().refreshSkill(Constants.MAGIC);
		player.stopMovement();
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (!player.walkingToItem) {
					container.stop();
				} else if (System.currentTimeMillis() - player.teleGrabDelay > 1550) {
					if (GameEngine.itemHandler.itemExists(player.teleGrabItem, player.teleGrabX, player.teleGrabY)) {
						GameEngine.itemHandler.removeGroundItem(player, player.teleGrabItem, player.teleGrabX, player.teleGrabY, true);
						GlobalDropsHandler.pickup(player, player.teleGrabItem, player.teleGrabX, player.teleGrabY);
					}
					stop();
				}
			}

			@Override
			public void stop() {
				player.usingMagic = false;
				player.walkingToItem = false;
			}
		}, 1);
	}
}
