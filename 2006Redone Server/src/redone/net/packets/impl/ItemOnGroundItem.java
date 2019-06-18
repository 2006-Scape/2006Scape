package redone.net.packets.impl;

import redone.Server;
import redone.game.content.skills.firemaking.Firemaking;
import redone.game.players.Client;
import redone.net.packets.PacketType;
import redone.util.Misc;

public class ItemOnGroundItem implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		player.getInStream().readSignedWordBigEndian();
		int itemUsed = player.getInStream().readSignedWordA();
		int groundItem = player.getInStream().readUnsignedWord();
		int gItemY = player.getInStream().readSignedWordA();
		int itemUsedSlot = player.getInStream().readSignedWordBigEndianA();
		int gItemX = player.getInStream().readUnsignedWord();
		if (!player.getItemAssistant().playerHasItem(itemUsed, 1, itemUsedSlot)) {
			return;
		}
		if (!Server.itemHandler.itemExists(groundItem, gItemX, gItemY)) {
			return;
		}

		switch (itemUsed) {
		case 590:
		case 7331:
		case 7330:
		case 7329:
			Firemaking.attemptFire(player, itemUsed, groundItem, gItemX, gItemY,
					true);
			break;

		default:
			if (player.playerRights == 3) {
				Misc.println("ItemUsed " + itemUsed + " on Ground Item "
						+ groundItem);
			}
			break;
		}
	}

}
