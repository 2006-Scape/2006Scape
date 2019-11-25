package com.rebotted.net.packets.impl;

import com.rebotted.game.players.Client;
import com.rebotted.net.packets.PacketType;

/**
 * @author Andrew (Mr Extremez)
 */

public class IdleLogout implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		if (player.underAttackBy > 0 || player.underAttackBy2 > 0 || player.isBot) {
			return;
		}
		player.logout();
	}
}
