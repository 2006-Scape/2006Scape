package com.rebotted.net.packets.impl;

import com.rebotted.game.items.UseItem;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

public class ItemOnItem implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int usedWithSlot = player.getInStream().readUnsignedWord();
		int itemUsedSlot = player.getInStream().readUnsignedWordA();
		int useWith = player.playerItems[usedWithSlot] - 1;
		int itemUsed = player.playerItems[itemUsedSlot] - 1;
		if (!player.getItemAssistant().playerHasItem(useWith, 1, usedWithSlot)|| !player.getItemAssistant().playerHasItem(itemUsed, 1, itemUsedSlot)) {
			return;
		}
		player.endCurrentTask();
		UseItem.itemOnItem(player, itemUsed, useWith);
	}

}
