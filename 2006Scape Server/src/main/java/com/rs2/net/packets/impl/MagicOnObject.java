package com.rs2.net.packets.impl;

import com.rs2.game.content.skills.crafting.OrbCharging;
import com.rs2.game.players.Player;
import com.rs2.net.packets.PacketType;

public class MagicOnObject implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int x = player.getInStream().readSignedWordBigEndian();
		int magicId = player.getInStream().readUnsignedWord();
		int y = player.getInStream().readUnsignedWordA();
		int objectId = player.getInStream().readSignedWordBigEndian();
		
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
