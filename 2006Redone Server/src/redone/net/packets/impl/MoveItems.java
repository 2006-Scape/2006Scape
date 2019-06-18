package redone.net.packets.impl;

import redone.game.players.Client;
import redone.net.packets.PacketType;

/**
 * Move Items
 **/
public class MoveItems implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int interfaceId = c.getInStream().readSignedWordBigEndianA();
		boolean insertMode = c.getInStream().readSignedByteC() == 1;
		int from = c.getInStream().readSignedWordBigEndianA();
		int to = c.getInStream().readSignedWordBigEndian();
		// c.sendMessage("junk: " + somejunk);
		if (c.inTrade) {
			c.getTrading().declineTrade();
			return;
		}
		if (c.tradeStatus == 1) {
			c.getTrading().declineTrade();
			return;
		}
		if (c.duelStatus == 1) {
			c.getDueling().declineDuel();
			return;
		}
		c.getItemAssistant().moveItems(from, to, interfaceId, insertMode);
	}
}
