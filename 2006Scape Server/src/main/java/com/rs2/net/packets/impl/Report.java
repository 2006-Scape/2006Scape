package com.rs2.net.packets.impl;

import com.rs2.game.players.Player;
import com.rs2.net.packets.PacketType;

public class Report implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		try {
			ReportHandler.handleReport(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
