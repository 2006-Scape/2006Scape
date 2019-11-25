package com.rebotted.net.packets.impl;

import com.rebotted.game.content.skills.firemaking.Firemaking;
import com.rebotted.game.content.skills.firemaking.LogData;
import com.rebotted.game.players.Client;
import com.rebotted.net.packets.PacketType;

public class ItemClick2OnGroundItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		final int itemX = c.getInStream().readSignedWordBigEndian();
		final int itemY = c.getInStream().readSignedWordBigEndianA();
		final int itemId = c.getInStream().readUnsignedWordA();
		System.out.println("ItemClick2OnGroundItem - " + c.playerName + " - " + itemId + " - " + itemX + " - " + itemY);
		if (c.absX != itemX || c.absY != itemY) {
			c.getActionSender().sendMessage("You can't do that there!");
			return;
		}
		c.endCurrentTask();
		for (LogData l : LogData.values()) {
			if (itemId == l.getLogId()) {
				Firemaking.attemptFire(c, 590, itemId, itemX, itemY, true);
				return;
			}
		}
	}
}
