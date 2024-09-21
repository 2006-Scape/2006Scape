package com.rs2.net.packets.impl;

import com.rs2.event.impl.ItemThirdClickEvent;
import com.rs2.game.content.skills.runecrafting.Runecrafting;
import com.rs2.game.items.impl.HandleEmpty;
import com.rs2.game.items.impl.Teles;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.util.Misc;

/**
 * Item Click 3 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30 Proper Streams
 */

public class ItemClick3 implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		int itemId11 = packet.readSignedWordBigEndianA();
		int itemId1 = packet.readSignedWordA();
		int itemId = packet.readSignedWordA();
		if (!player.getItemAssistant().playerHasItem(itemId, 1)) {
			return;
		}
		player.post(new ItemThirdClickEvent(itemId));
		if (HandleEmpty.canEmpty(player, itemId)) {
			HandleEmpty.handleEmptyItem(player, itemId, HandleEmpty.filledToEmpty(player, itemId));
			return;
		}
		if (player.duelStatus > 0 && player.duelStatus < 5 || player.tradeStatus == 1) {
			return;
		}

		player.endCurrentTask();

		switch (itemId) {
		case 1438:// Air Talisman
			Runecrafting.locate(player, 2985, 3292);
			break;
		case 1440:// Earth Talisman
			Runecrafting.locate(player, 3306, 3474);
			break;
		case 1442:// Fire Talisman
			Runecrafting.locate(player, 3313, 3255);
			break;
		case 1444:// Water Talisman
			Runecrafting.locate(player, 3185, 3165);
			break;
		case 1446:// Body Talisman
			Runecrafting.locate(player, 3053, 3445);
			break;
		case 1448:// Mind Talisman
			Runecrafting.locate(player, 2982, 3514);
			break;
			
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
			player.getItemAssistant().deleteItem(1933, 1);
			player.getItemAssistant().addItem(1931, 1);
		break;
		
		case 1921:
			player.getItemAssistant().deleteItem(1921, 1);
			player.getItemAssistant().addItem(1923, 1);
		break;

		default:

			if (player.playerRights == 3) {
				System.out.println(player.playerName + " - Item3rdOption: " + itemId
						+ " : " + itemId11 + " : " + itemId1);
			}
			break;
		}

	}

}
