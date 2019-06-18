package redone.net.packets.impl;

import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.net.packets.PacketType;

public class FollowPlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int followPlayer = c.getInStream().readUnsignedWordBigEndian();
		if (PlayerHandler.players[followPlayer] == null) {
			return;
		}
		if (c.isBotting == true) {
			c.getActionSender().sendMessage("You can't follow players, until you confirm you are not botting.");
			c.getActionSender().sendMessage("If you need to you can type ::amibotting, to see if your botting.");
			c.stopMovement();
			c.getPlayerAssistant().resetFollow();
			return;
		}
		/*if (c.performingAction) {
			c.stopMovement();
			c.getPlayerAssistant().resetFollow();
			return;
		}*/
		c.playerIndex = 0;
		c.npcIndex = 0;
		c.mageFollow = false;
		c.usingBow = false;
		c.usingRangeWeapon = false;
		c.followDistance = 1;
		c.followId = followPlayer;
	}
}
