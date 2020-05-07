package com.rebotted.net.packets.impl;

import com.rebotted.game.items.impl.RareProtection;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.net.packets.PacketType;
import com.rebotted.util.Misc;

/**
 * @author JaydenD12/Jaydennn
 */

public class ItemOnPlayer implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int playerId = player.inStream.readUnsignedWord();
		int itemId = player.playerItems[player.inStream.readSignedWordBigEndian()] - 1;
		player.endCurrentTask();
		switch (itemId) {

		case 962:
			Player o = (Player) PlayerHandler.players[playerId];
			if (RareProtection.CRACKERS) {
				int delete = player.getItemAssistant().getItemAmount(962);
				player.getItemAssistant().deleteItem(962, delete);
				player.getPacketSender().sendMessage("You can't do that!");
				return;
			}
			player.turnPlayerTo(o.absX, o.absY);
			o.turnPlayerTo(player.absX, player.absY);
			o.gfx0(176);
			player.gfx0(176);
			player.startAnimation(451);
			o.startAnimation(451);
			player.getPacketSender().sendMessage(
						"You pull the Christmas Cracker...");
			o.getPacketSender().sendMessage(
					player.playerName.toUpperCase() + " need your help... You pull the Christmas Cracker...");
			player.getItemAssistant().deleteItem(962, 1);
			if (Misc.random(3) == 1) {
				o.forcedText = "Yay! I got the Cracker!";
				o.forcedChatUpdateRequired = true;
				o.getItemAssistant().addItem(1038 + Misc.random(5) * 2, 1);
			} else {
				player.forcedText = "Yay! I got the Cracker!";
				player.forcedChatUpdateRequired = true;
				player.getItemAssistant().addItem(1038 + Misc.random(5) * 2, 1);
			}
			break;
		default:
			player.getPacketSender().sendMessage("Nothing interesting happens.");
			break;
		}
	}
}
