package com.rs2.net.packets.impl;

import com.rs2.game.players.Player;
import com.rs2.net.GamePacket;
import com.rs2.net.packets.PacketType;

public class Report implements PacketType {

	@Override
	public void processPacket(Player player, GamePacket packet) {
		try {
			ReportHandler.handleReport(player, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
