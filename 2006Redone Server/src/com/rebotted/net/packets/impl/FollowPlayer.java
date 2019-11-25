package com.rebotted.net.packets.impl;

import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.net.packets.PacketType;

public class FollowPlayer implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		int followPlayer = c.getInStream().readUnsignedWordBigEndian();
		if (PlayerHandler.players[followPlayer] == null) {
			return;
		}
		c.playerIndex = 0;
		c.npcIndex = 0;
		c.mageFollow = false;
		c.usingBow = false;
		c.usingRangeWeapon = false;
		c.followDistance = 1;
		c.followId = followPlayer;
		c.endCurrentTask();
	}
}
