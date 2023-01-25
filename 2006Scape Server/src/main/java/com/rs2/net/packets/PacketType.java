package com.rs2.net.packets;

import com.rs2.game.players.Player;
import com.rs2.net.GamePacket;

public interface PacketType {

	public void processPacket(Player player, GamePacket packet);
}
