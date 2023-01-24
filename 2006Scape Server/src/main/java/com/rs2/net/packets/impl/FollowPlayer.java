package com.rs2.net.packets.impl;

import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

public class FollowPlayer implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		int followPlayer = packet.readUnsignedWordBigEndian();
		if (PlayerHandler.players[followPlayer] == null) {
			return;
		}
		player.playerIndex = 0;
		player.npcIndex = 0;
		player.mageFollow = false;
		player.usingBow = false;
		player.usingRangeWeapon = false;
		player.followDistance = 1;
		player.followId = followPlayer;
		player.endCurrentTask();
	}
}
