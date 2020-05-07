package com.rebotted.net.packets.impl;

import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.net.packets.PacketType;

public class FollowPlayer implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int followPlayer = player.getInStream().readUnsignedWordBigEndian();
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
