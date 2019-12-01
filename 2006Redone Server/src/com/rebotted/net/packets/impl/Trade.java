package com.rebotted.net.packets.impl;

import com.rebotted.GameConstants;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

/**
 * Trading
 */

public class Trade implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int tradeId = c.getInStream().readSignedWordBigEndian();
		c.getPlayerAssistant().resetFollow();
		c.endCurrentTask();
		if (c.disconnected) {
			c.tradeStatus = 0;
		}
		if (c.duelingArena()) {
			c.getPacketSender().sendMessage("You can't trade inside the arena!");
			return;
		}
	
		if (c.playerRights == 2 && !GameConstants.ADMIN_CAN_TRADE) {
			c.getPacketSender().sendMessage("Trading as an admin has been disabled.");
			return;
		}

	    if(tradeId < 1) {
            return;
	    }
	    if (tradeId != c.playerId) {
            c.getTrading().requestTrade(tradeId);
		}
	}

}
