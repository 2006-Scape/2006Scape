package redone.net.packets.impl;

import redone.Connection;
import redone.game.players.Client;
import redone.game.players.antimacro.AntiSpam;
import redone.net.packets.PacketType;
import redone.util.Misc;

/**
 * Chat
 **/
public class Chat implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		player.setChatTextEffects(player.getInStream().readUnsignedByteS());
		player.setChatTextColor(player.getInStream().readUnsignedByteS());
		player.setChatTextSize((byte) (player.packetSize - 2));
		player.inStream.readBytes_reverseA(player.getChatText(), player.getChatTextSize(), 0);
		ReportHandler.addText(player.playerName, player.getChatText(), packetSize - 2);
		String word = Misc.textUnpack(player.getChatText(), player.packetSize - 2).toLowerCase();
		if (AntiSpam.blockedWords(player, word, true) && !Connection.isMuted(player)) {
			player.setChatTextUpdateRequired(true);
		}
		System.out.println(word);
		if (Connection.isMuted(player)) {
			player.getActionSender().sendMessage("You are muted and can't speak.");
			player.setChatTextUpdateRequired(false);
			return;
		}
	}
}
