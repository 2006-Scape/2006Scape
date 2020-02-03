package com.rebotted.game.objects.impl;

import com.rebotted.game.players.Player;

public class UseOther {


	public static void useUp(final Player player, final int objectId) {
		player.stopPlayerPacket = true;
		player.startAnimation(828);
		player.getPacketSender().closeAllWindows();
		player.teleportToX = player.absX;
		player.teleportToY = player.absY - 6400;
		player.getPacketSender().sendMessage("You climb up.");
		player.stopPlayerPacket = false;
	}

	public static void useDown(final Player player, final int objectId) {
		if (player.objectX == 2647 && player.objectY == 3657 || player.objectX == 2650 && player.objectY == 3661) {
			player.getPacketSender().sendMessage("This trapdoor is currently disabled.");
			return;
		}
		player.stopMovement();
		player.startAnimation(827);
		player.getPacketSender().closeAllWindows();
		player.teleportToX = player.absX;
		player.teleportToY = player.absY + 6400;
		player.getPacketSender().sendMessage("You climb down.");
	}
}
