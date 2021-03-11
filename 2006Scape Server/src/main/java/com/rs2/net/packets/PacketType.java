package com.rs2.net.packets;

import com.rs2.game.players.Player;

public interface PacketType {

	public void processPacket(Player player, int packetType, int packetSize);
}
