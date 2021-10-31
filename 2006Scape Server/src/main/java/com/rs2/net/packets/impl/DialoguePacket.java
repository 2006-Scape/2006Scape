package com.rs2.net.packets.impl;

import com.rs2.game.players.Player;
import com.rs2.net.packets.PacketType;

/**
 * Dialogue Packet
 **/

public class DialoguePacket implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		/*
			TODO: Remove the below dialogue handler code when everything has been converted over
			 to the new system. Expect to see flickering in some Astraeus Dialogues as the old
			 dialogue handler activates before it.
		 */
		if (player.nextChat > 0) {
			player.getDialogueHandler().sendDialogues(player.nextChat, player.talkingNpc);
		} else {
			player.getDialogueHandler().sendDialogues(0, -1);
		}

		// New Dialogue System
		player.getDialogueFactory().execute();
	}
}
