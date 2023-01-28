package com.rs2.net.packets.impl;

import com.rs2.event.impl.MagicOnItemEvent;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

/**
 * Magic on items
 **/

public class MagicOnItems implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		int slot = packet.readSignedWord();
		int itemId = packet.readSignedWordA();
		packet.readSignedWord();
		int spellId = packet.readSignedWordA();
		player.endCurrentTask();
		if(!player.getItemAssistant().playerHasItem(itemId, 1, slot)) {
			return;
		}
		player.usingMagic = true;
		player.getPlayerAssistant().magicOnItems(slot, itemId, spellId);
		player.post(new MagicOnItemEvent(itemId, slot, spellId));
		player.usingMagic = false;

	}

}
