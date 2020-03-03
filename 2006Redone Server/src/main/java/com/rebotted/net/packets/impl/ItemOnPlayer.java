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
	public void processPacket(Player c, int packetType, int packetSize) {
		int playerId = c.inStream.readUnsignedWord();
		int itemId = c.playerItems[c.inStream.readSignedWordBigEndian()] - 1;
		c.endCurrentTask();
		switch (itemId) {

		case 962:
			Player o = (Player) PlayerHandler.players[playerId];
			if (RareProtection.CRACKERS) {
				int delete = c.getItemAssistant().getItemAmount(962);
				c.getItemAssistant().deleteItem(962, delete);
				c.getPacketSender().sendMessage("You can't do that!");
				return;
			}
			c.turnPlayerTo(o.absX, o.absY);
			o.turnPlayerTo(c.absX, c.absY);
			o.gfx0(176);
			c.gfx0(176);
			c.startAnimation(451);
			o.startAnimation(451);
			c.getPacketSender().sendMessage(
						"You pull the Christmas Cracker...");
			o.getPacketSender().sendMessage(
					c.playerName.toUpperCase() + " need your help... You pull the Christmas Cracker...");
			c.getItemAssistant().deleteItem(962, 1);
			if (Misc.random(3) == 1) {
				o.forcedText = "Yay! I got the Cracker!";
				o.forcedChatUpdateRequired = true;
				o.getItemAssistant().addItem(1038 + Misc.random(5) * 2, 1);
			} else {
				c.forcedText = "Yay! I got the Cracker!";
				c.forcedChatUpdateRequired = true;
				c.getItemAssistant().addItem(1038 + Misc.random(5) * 2, 1);
			}
			break;
		default:
			c.getPacketSender().sendMessage("Nothing interesting happens.");
			break;
		}
	}
}
