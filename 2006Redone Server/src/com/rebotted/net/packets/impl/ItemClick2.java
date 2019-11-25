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
	public void processPacket(Player c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();

		if (!c.getItemAssistant().playerHasItem(itemId, 1)) {
			return;
		}

		c.endCurrentTask();

		if (HandleEmpty.canEmpty(c, itemId)) {
			HandleEmpty.handleEmptyItem(c, itemId, HandleEmpty.filledToEmpty(c, itemId));
			return;
		}

		switch (itemId) {
		}

	}

}
