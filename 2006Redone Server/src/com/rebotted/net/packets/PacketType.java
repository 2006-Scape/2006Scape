package com.rebotted.net.packets;

import com.rebotted.game.players.Client;

public interface PacketType {

	public void processPacket(Client c, int packetType, int packetSize);
}
