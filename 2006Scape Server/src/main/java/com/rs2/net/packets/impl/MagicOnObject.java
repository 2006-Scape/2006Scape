package com.rs2.net.packets.impl;

import com.rs2.game.content.skills.crafting.OrbCharging;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

public class MagicOnObject implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		int x = packet.readSignedWordBigEndian();
		int magicId = packet.readUnsignedWord();
		int y = packet.readUnsignedWordA();
		int objectId = packet.readSignedWordBigEndian();
		
		player.turnPlayerTo(x, y);
		switch (objectId) {
		case 2153:
		case 2152:
		case 2151:
		case 2150:
			OrbCharging.chargeOrbs(player, magicId, objectId);
			break;
		}
	}

}
