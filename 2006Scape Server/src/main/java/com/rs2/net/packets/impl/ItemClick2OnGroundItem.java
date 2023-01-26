package com.rs2.net.packets.impl;

import com.rs2.game.content.skills.firemaking.Firemaking;
import com.rs2.game.content.skills.firemaking.LogData;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

public class ItemClick2OnGroundItem implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		final int itemX = packet.readSignedWordBigEndian();
		final int itemY = packet.readSignedWordBigEndianA();
		final int itemId = packet.readUnsignedWordA();
		System.out.println("ItemClick2OnGroundItem - " + player.playerName + " - " + itemId + " - " + itemX + " - " + itemY);
		// Reset position for the telekinetic guardian statue
		if (itemId == 6888) {
			player.getMageTrainingArena().telekinetic.resetStatue(itemX, itemY);
			return;
		}
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
