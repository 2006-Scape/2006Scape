package com.rs2.game.dialogues;

import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

/**
 * Dialogue
 **/

public class Dialogue implements PacketType {

	@Override
	public void processPacket(Player c, Packet packet) {
		if (c.nextChat > 0) {
			c.getDialogueHandler().sendDialogues(c.nextChat, c.talkingNpc);
		} else {
			c.getDialogueHandler().sendDialogues(0, -1);
		}
	}
}
