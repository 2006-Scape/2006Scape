package com.rebotted.net.packets.impl;

import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

/**
 * Magic on items
 **/

public class MagicOnItems implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int slot = player.getInStream().readSignedWord();
		int itemId = player.getInStream().readSignedWordA();
		player.getInStream().readSignedWord();
		int spellId = player.getInStream().readSignedWordA();
		player.endCurrentTask();
		if(!player.getItemAssistant().playerHasItem(itemId, 1, slot)) {
			return;
		}
		player.usingMagic = true;
		player.getPlayerAssistant().magicOnItems(slot, itemId, spellId);
		player.usingMagic = false;

	}

}
