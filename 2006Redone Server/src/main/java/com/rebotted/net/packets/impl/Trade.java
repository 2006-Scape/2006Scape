package com.rebotted.net.packets.impl;

import com.rebotted.GameConstants;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

/**
 * Trading
 */

public class Trade implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int tradeId = player.getInStream().readSignedWordBigEndian();
		player.getPlayerAssistant().resetFollow();
		player.endCurrentTask();
		if (player.disconnected) {
			player.tradeStatus = 0;
		}
		if (player.duelingArena()) {
			player.getPacketSender().sendMessage("You can't trade inside the arena!");
			return;
		}
	
		if (player.playerRights == 2 && !GameConstants.ADMIN_CAN_TRADE) {
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
