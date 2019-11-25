package com.rebotted.net.packets.impl;

import com.rebotted.game.players.Client;
import com.rebotted.net.packets.PacketType;

public class Report implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		try {
			ReportHandler.handleReport(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
