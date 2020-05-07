package com.rebotted.net.packets.impl;

import com.rebotted.game.content.skills.firemaking.Firemaking;
import com.rebotted.game.content.skills.firemaking.LogData;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

public class ItemClick2OnGroundItem implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		final int itemX = player.getInStream().readSignedWordBigEndian();
		final int itemY = player.getInStream().readSignedWordBigEndianA();
		final int itemId = player.getInStream().readUnsignedWordA();
		System.out.println("ItemClick2OnGroundItem - " + player.playerName + " - " + itemId + " - " + itemX + " - " + itemY);
		if (player.absX != itemX || player.absY != itemY) {
			player.getPacketSender().sendMessage("You can't do that there!");
			return;
		}
		player.endCurrentTask();
		for (LogData l : LogData.values()) {
			if (itemId == l.getLogId()) {
				Firemaking.attemptFire(player, 590, itemId, itemX, itemY, true);
				return;
			}
		}
	}
}
