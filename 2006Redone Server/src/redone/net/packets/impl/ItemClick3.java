package redone.net.packets.impl;

import redone.game.items.impl.HandleEmpty;
import redone.game.items.impl.Teles;
import redone.game.players.Client;
import redone.net.packets.PacketType;
import redone.util.Misc;

/**
 * Item Click 3 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30 Proper Streams
 */

public class ItemClick3 implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		int itemId11 = player.getInStream().readSignedWordBigEndianA();
		int itemId1 = player.getInStream().readSignedWordA();
		int itemId = player.getInStream().readSignedWordA();
		if (!player.getItemAssistant().playerHasItem(itemId, 1)) {
			return;
		}
		if (HandleEmpty.canEmpty(player, itemId)) {
			HandleEmpty.handleEmptyItem(player, itemId, HandleEmpty.filledToEmpty(player, itemId));
			return;
		}
		if (player.duelStatus > 0 && player.duelStatus < 5 || player.tradeStatus == 1) {
			return;
		}
		switch (itemId) {

		case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566:
			player.itemUsing = itemId;
			Teles.useROD(player);
			break;

		case 1712:
		case 1710:
		case 1708:
		case 1706:
			player.itemUsing = itemId;
			Teles.useAOG(player);
			break;

		case 3853:
		case 3855:
		case 3857:
		case 3859:
		case 3861:
		case 3863:
		case 3865:
		case 3867:
			player.itemUsing = itemId;
			Teles.useGN(player);
			break;
			
		case 1933:
			player.getItemAssistant().deleteItem2(1933, 1);
			player.getItemAssistant().addItem(1931, 1);
		break;
		
		case 1921:
			player.getItemAssistant().deleteItem2(1921, 1);
			player.getItemAssistant().addItem(1923, 1);
		break;

		default:

			if (player.playerRights == 3) {
				Misc.println(player.playerName + " - Item3rdOption: " + itemId
						+ " : " + itemId11 + " : " + itemId1);
			}
			break;
		}

	}

}
