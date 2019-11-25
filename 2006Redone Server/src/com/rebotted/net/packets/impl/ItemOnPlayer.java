package com.rebotted.net.packets.impl;

import com.rebotted.game.items.impl.RareProtection;
import com.rebotted.game.players.Client;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.net.packets.PacketType;
import com.rebotted.util.Misc;

/**
 * @author JaydenD12/Jaydennn
 */

public class ItemOnPlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int playerId = c.inStream.readUnsignedWord();
		int itemId = c.playerItems[c.inStream.readSignedWordBigEndian()] - 1;
		c.endCurrentTask();
		switch (itemId) {

		case 962:
			Client o = (Client) PlayerHandler.players[playerId];
			if (!RareProtection.CRACKERS && c.playerRights < 3) {
				int delete = c.getItemAssistant().getItemCount(962);
				c.getItemAssistant().deleteItem(962, delete);
				c.getPacketSender().sendMessage("You can't do that!");
				return;
			}
			c.gfx0(176);
			c.startAnimation(451);
			c.getPacketSender().sendMessage(
					"You pull the Christmas Cracker...");
			o.getPacketSender().sendMessage(
					"You pull the Christmas Cracker...");
			c.getItemAssistant().deleteItem(962, 1);
			if (Misc.random(3) == 1) {
				o.forcedText = "Yay I got the Cracker!";
				o.forcedChatUpdateRequired = true;
				o.getItemAssistant().addItem(1038 + Misc.random(5) * 2, 1);
			} else {
				c.forcedText = "Yay I got the Cracker!";
				c.forcedChatUpdateRequired = true;
				c.getItemAssistant().addItem(1038 + Misc.random(5) * 2, 1);
			}
			c.turnPlayerTo(o.absX, o.absY);
			break;
		default:
			c.getPacketSender().sendMessage("Nothing interesting happens.");
			break;
		}

	}
}
