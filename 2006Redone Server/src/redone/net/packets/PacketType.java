package redone.net.packets;

import redone.game.players.Client;

public interface PacketType {

	public void processPacket(Client c, int packetType, int packetSize);
}
