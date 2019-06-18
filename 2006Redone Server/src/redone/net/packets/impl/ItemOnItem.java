package redone.net.packets.impl;

import redone.game.items.UseItem;
import redone.game.players.Client;
import redone.net.packets.PacketType;

public class ItemOnItem implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		int usedWithSlot = player.getInStream().readUnsignedWord();
		int itemUsedSlot = player.getInStream().readUnsignedWordA();
		int useWith = player.playerItems[usedWithSlot] - 1;
		int itemUsed = player.playerItems[itemUsedSlot] - 1;
		if (!player.getItemAssistant().playerHasItem(useWith, 1, usedWithSlot)|| !player.getItemAssistant().playerHasItem(itemUsed, 1, itemUsedSlot)) {
			return;
		}
		UseItem.ItemonItem(player, itemUsed, useWith);
	}

}
