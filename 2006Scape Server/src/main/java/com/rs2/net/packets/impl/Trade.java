package com.rs2.net.packets.impl;

import com.rs2.Constants;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

/**
 * Trading
 */

public class Trade implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		int tradeId = packet.readSignedWordBigEndian();
		player.getPlayerAssistant().resetFollow();
		player.endCurrentTask();
		if (player.disconnected) {
			player.tradeStatus = 0;
		}
		if (player.duelingArena()) {
			player.getPacketSender().sendMessage("You can't trade inside the arena!");
			return;
		}
	
		if (player.playerRights == 2 && !Constants.ADMIN_CAN_TRADE) {
			player.getPacketSender().sendMessage("Trading as an admin has been disabled.");
			return;
		}

	    if(tradeId < 1) {
            return;
	    }
	    if (tradeId != player.playerId) {
            player.getTrading().requestTrade(tradeId);
		}
	}

}
