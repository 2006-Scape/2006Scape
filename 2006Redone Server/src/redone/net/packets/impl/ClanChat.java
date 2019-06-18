package redone.net.packets.impl;

import redone.Server;
import redone.game.players.Client;
import redone.net.packets.PacketType;
import redone.util.Misc;

/**
 * Chat
 **/
public class ClanChat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String textSent = Misc.longToPlayerName2(c.getInStream().readQWord());
		textSent = textSent.replaceAll("_", " ");
		// c.sendMessage(textSent);
		Server.clanChat.handleClanChat(c, textSent);
	}
}
