package com.rs2.net.packets.impl;

import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

/**
 * @author Andrew (Mr Extremez)
 */

public class IdleLogout implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		if (player.underAttackBy > 0 || player.underAttackBy2 > 0 || player.isBot) {
			return;
		}
		player.logout();
	}
}
