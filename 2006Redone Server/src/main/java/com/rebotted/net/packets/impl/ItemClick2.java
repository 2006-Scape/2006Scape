package com.rebotted.net.packets.impl;

import com.rebotted.game.items.impl.HandleEmpty;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

/**
 * Item Click 2 Or Alternative Item Option 1
 * @author Ryan / Lmctruck30 Proper Streams
 */

public class ItemClick2 implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int itemId = player.getInStream().readSignedWordA();

		if (!player.getItemAssistant().playerHasItem(itemId, 1)) {
			return;
		}

		player.endCurrentTask();

		if (HandleEmpty.canEmpty(player, itemId)) {
			HandleEmpty.handleEmptyItem(player, itemId, HandleEmpty.filledToEmpty(player, itemId));
			return;
		}

		switch (itemId) {
		case 4079:
			player.startAnimation(1459);
			break;
		}

	}

}
