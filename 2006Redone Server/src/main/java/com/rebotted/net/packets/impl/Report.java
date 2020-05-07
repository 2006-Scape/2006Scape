package com.rebotted.net.packets.impl;

import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

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
