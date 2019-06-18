package redone.net.packets.impl;

import redone.game.items.impl.HandleEmpty;
import redone.game.players.Client;
import redone.net.packets.PacketType;

/**
 * Item Click 2 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30 Proper Streams
 */

public class ItemClick2 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();

		if (!c.getItemAssistant().playerHasItem(itemId, 1)) {
			return;
		}
		
		if (HandleEmpty.canEmpty(c, itemId)) {
			HandleEmpty.handleEmptyItem(c, itemId, HandleEmpty.filledToEmpty(c, itemId));
			return;
		}

		switch (itemId) {
		}

	}

}
