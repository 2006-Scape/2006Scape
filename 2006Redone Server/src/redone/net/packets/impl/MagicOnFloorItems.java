package redone.net.packets.impl;

import redone.Server;
import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.content.combat.magic.MagicData;
import redone.game.items.Item;
import redone.game.players.Client;
import redone.net.packets.PacketType;

/**
 * Magic on floor items
 **/
public class MagicOnFloorItems implements PacketType {

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		final int itemY = c.getInStream().readSignedWordBigEndian();
		int itemId = c.getInStream().readUnsignedWord();
		final int itemX = c.getInStream().readSignedWordBigEndian();
		c.getInStream().readUnsignedWordA();

		if (!Server.itemHandler.itemExists(itemId, itemX, itemY)) {
			c.stopMovement();
			return;
		}
		c.usingMagic = true;
		if (!c.getCombatAssistant().checkMagicReqs(51)) {
			c.stopMovement();
			return;
		}

		if ((c.getItemAssistant().freeSlots() >= 1 || c.getItemAssistant()
				.playerHasItem(itemId, 1))
				&& Item.itemStackable[itemId]
				|| c.getItemAssistant().freeSlots() > 0
				&& !Item.itemStackable[itemId]) {
			if (c.goodDistance(c.getX(), c.getY(), itemX, itemY, 12)) {
				c.walkingToItem = true;
				int offY = (c.getX() - itemX) * -1;
				int offX = (c.getY() - itemY) * -1;
				c.teleGrabX = itemX;
				c.teleGrabY = itemY;
				c.teleGrabItem = itemId;
				c.turnPlayerTo(itemX, itemY);
				c.teleGrabDelay = System.currentTimeMillis();
				c.startAnimation(MagicData.MAGIC_SPELLS[51][2]);
				c.gfx100(MagicData.MAGIC_SPELLS[51][3]);
				c.getPlayerAssistant().createPlayersStillGfx(144, itemX, itemY,
						0, 72);
				c.getPlayerAssistant().createPlayersProjectile(c.getX(),
						c.getY(), offX, offY, 50, 70,
						MagicData.MAGIC_SPELLS[51][4], 50, 10, 0, 50);
				c.getPlayerAssistant().addSkillXP(
						MagicData.MAGIC_SPELLS[51][7], 6);
				c.getPlayerAssistant().refreshSkill(6);
				c.stopMovement();
				   CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			            @Override
			            public void execute(CycleEventContainer container) {
						if (!c.walkingToItem) {
							stop();
						}
						if (System.currentTimeMillis() - c.teleGrabDelay > 1550
								&& c.usingMagic) {
							if (Server.itemHandler.itemExists(c.teleGrabItem,
									c.teleGrabX, c.teleGrabY)
									&& c.goodDistance(c.getX(), c.getY(),
											itemX, itemY, 12)) {
								Server.itemHandler.removeGroundItem(c,
										c.teleGrabItem, c.teleGrabX,
										c.teleGrabY, true);
								c.usingMagic = false;
								container.stop();
							}
						}
					}

					@Override
					public void stop() {
						c.walkingToItem = false;
					}
				}, 1);
			}
		} else {
			c.getActionSender().sendMessage(
					"You don't have enough space in your inventory.");
			c.stopMovement();
		}

		if (c.goodDistance(c.getX(), c.getY(), itemX, itemY, 12)) {
			int offY = (c.getX() - itemX) * -1;
			int offX = (c.getY() - itemY) * -1;
			c.teleGrabX = itemX;
			c.teleGrabY = itemY;
			c.teleGrabItem = itemId;
			c.turnPlayerTo(itemX, itemY);
			c.teleGrabDelay = System.currentTimeMillis();
			c.startAnimation(MagicData.MAGIC_SPELLS[51][2]);
			c.gfx100(MagicData.MAGIC_SPELLS[51][3]);
			c.getPlayerAssistant().createPlayersStillGfx(144, itemX, itemY, 0,
					72);
			c.getPlayerAssistant().createPlayersProjectile(c.getX(), c.getY(),
					offX, offY, 50, 70, MagicData.MAGIC_SPELLS[51][4], 50, 10,
					0, 50);
			c.getPlayerAssistant().addSkillXP(MagicData.MAGIC_SPELLS[51][7], 6);
			c.getPlayerAssistant().refreshSkill(6);
			c.stopMovement();
		}
	}

}
