package redone.net.packets.impl;

import redone.game.items.impl.RareProtection;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.net.packets.PacketType;
import redone.util.Misc;

/**
 * @author JaydenD12/Jaydennn
 */

public class ItemOnPlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int playerId = c.inStream.readUnsignedWord();
		int itemId = c.playerItems[c.inStream.readSignedWordBigEndian()] - 1;
		switch (itemId) {

		case 962:
			Client o = (Client) PlayerHandler.players[playerId];
			if (!RareProtection.CRACKERS && c.playerRights < 3) {
				int delete = c.getItemAssistant().getItemCount(962);
				c.getItemAssistant().deleteItem2(962, delete);
				c.getActionSender().sendMessage("You can't do that!");
				return;
			}
			c.gfx0(176);
			c.startAnimation(451);
			c.getActionSender().sendMessage(
					"You pull the Christmas Cracker...");
			o.getActionSender().sendMessage(
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
			c.getActionSender().sendMessage("Nothing interesting happens.");
			break;
		}

	}
}
