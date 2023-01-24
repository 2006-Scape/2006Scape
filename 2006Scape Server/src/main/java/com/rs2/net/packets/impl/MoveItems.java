package com.rs2.net.packets.impl;

import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

/**
 * Move Items
 **/
public class MoveItems implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		int interfaceId = packet.readSignedWordBigEndianA();
		boolean insertMode = packet.readSignedByteC() == 1;
		int from = packet.readSignedWordBigEndianA();
		int to = packet.readSignedWordBigEndian();
		if (player.inTrade) {
			player.getTrading().declineTrade();
			return;
		}
		if (player.tradeStatus == 1) {
			player.getTrading().declineTrade();
			return;
		}
		if (player.duelStatus == 1) {
			player.getDueling().declineDuel();
			return;
		}
		player.getItemAssistant().moveItems(from, to, interfaceId, insertMode);
		player.endCurrentTask();
	}
}
