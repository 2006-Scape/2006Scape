package com.rs2.net.packets.impl;

import com.rs2.GameConstants;
import com.rs2.GameEngine;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.items.ItemData;
import com.rs2.game.players.Player;
import com.rs2.net.packets.PacketType;

/**
 * Magic on floor items
 **/

public class MagicOnFloorItems implements PacketType {

	@Override
	public void processPacket(final Player player, int packetType, int packetSize) {
		final int itemY = player.getInStream().readSignedWordBigEndian();
		int itemId = player.getInStream().readUnsignedWord();
		final int itemX = player.getInStream().readSignedWordBigEndian();
		player.getInStream().readUnsignedWordA();

		if (!GameEngine.itemHandler.itemExists(itemId, itemX, itemY)) {
			player.stopMovement();
			return;
		}
		player.usingMagic = true;
		player.endCurrentTask();
		if (!player.getCombatAssistant().checkMagicReqs(51)) {
			player.stopMovement();
			return;
		}

		if (player.getItemAssistant().freeSlots(itemId, 1) <= 0) {
			player.getPacketSender().sendMessage("You don't have enough space in your inventory.");
			player.stopMovement();
			return;
		}

		if (itemId == 6888 && player.goodDistance(player.getX(), player.getY(), itemX, itemY, 20)) {
			System.out.println("test");
			int offY = (player.getX() - itemX) * -1;
			int offX = (player.getY() - itemY) * -1;
			player.teleGrabX = itemX;
			player.teleGrabY = itemY;
			player.teleGrabItem = itemId;
			player.turnPlayerTo(itemX, itemY);
			player.teleGrabDelay = System.currentTimeMillis();
			player.startAnimation(MagicData.MAGIC_SPELLS[51][2]);
			player.gfx100(MagicData.MAGIC_SPELLS[51][3]);
			player.getPlayerAssistant().createPlayersStillGfx(144, itemX, itemY, 0,
					72);
			player.getPlayerAssistant().createPlayersProjectile(player.getX(), player.getY(),
					offX, offY, 50, 70, MagicData.MAGIC_SPELLS[51][4], 50, 10,
					0, 50);
			player.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[51][7], 6);
			player.getPlayerAssistant().refreshSkill(GameConstants.MAGIC);
			player.stopMovement();
			return;
		}
		
		if (player.goodDistance(player.getX(), player.getY(), itemX, itemY, 12)) {
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
			player.getPlayerAssistant().createPlayersStillGfx(144, itemX, itemY,
					0, 72);
			player.getPlayerAssistant().createPlayersProjectile(player.getX(),
					player.getY(), offX, offY, 50, 70,
					MagicData.MAGIC_SPELLS[51][4], 50, 10, 0, 50);
			player.getPlayerAssistant().addSkillXP(
					MagicData.MAGIC_SPELLS[51][7], 6);
			player.getPlayerAssistant().refreshSkill(GameConstants.MAGIC);
			player.stopMovement();
			   CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
					if (!player.walkingToItem) {
						stop();
					}
					if (System.currentTimeMillis() - player.teleGrabDelay > 1550
							&& player.usingMagic) {
						if (GameEngine.itemHandler.itemExists(player.teleGrabItem,
								player.teleGrabX, player.teleGrabY)
								&& player.goodDistance(player.getX(), player.getY(),
										itemX, itemY, 12)) {
							GameEngine.itemHandler.removeGroundItem(player,
									player.teleGrabItem, player.teleGrabX,
									player.teleGrabY, true);
							player.usingMagic = false;
							container.stop();
						}
					}
				}

				@Override
				public void stop() {
					player.walkingToItem = false;
				}
			}, 1);
		}
	}

}
