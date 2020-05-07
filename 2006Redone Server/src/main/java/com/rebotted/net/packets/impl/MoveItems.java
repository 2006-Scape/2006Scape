package com.rebotted.net.packets.impl;

import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

/**
 * Move Items
 **/
public class MoveItems implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int interfaceId = player.getInStream().readSignedWordBigEndianA();
		boolean insertMode = player.getInStream().readSignedByteC() == 1;
		int from = player.getInStream().readSignedWordBigEndianA();
		int to = player.getInStream().readSignedWordBigEndian();
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
