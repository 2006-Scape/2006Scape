package com.rebotted.net.packets.impl;

import com.rebotted.game.content.random.PartyRoom;
import com.rebotted.game.content.skills.cooking.Cooking;
import com.rebotted.game.content.skills.smithing.Smelting;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

public class InterfaceX implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		player.endCurrentTask();
		int Xamount = player.getInStream().readDWord();
		if (Xamount < 0) {
            Xamount = player.getItemAssistant().getItemAmount(player.xRemoveId);
        }
        if (Xamount == 0) {
            Xamount = 1;
        }
        if (!player.isBanking) {
	        if (player.playerIsCooking && player.doAmount > 0) {
				Cooking.cookItem(player, player.cookingItem, Xamount, player.cookingObject);
			}
	        if (player.isSmelting && player.doAmount > 0) {
	        	Smelting.smeltBar(player, player.smeltingItem);
	        }
        }
		switch (player.xInterfaceId) {
			case 5064:
				if (player.inPartyRoom) {
					PartyRoom.depositItem(player, player.xRemoveId, player.getItemAssistant().itemAmount(player.playerItems[player.xRemoveSlot]));
					break;
				}
				if (player.inTrade) {
					player.getPacketSender().sendMessage("You can't store items while trading!");
					return;
				}
				player.getItemAssistant().bankItem(player.playerItems[player.xRemoveSlot], player.xRemoveSlot, Xamount);
				break;

			case 5382:
					player.getItemAssistant().fromBank(player.bankItems[player.xRemoveSlot], player.xRemoveSlot, Xamount);
				break;

			case 7423:
				if (player.storing) {
					return;
				}
				player.getItemAssistant().bankItem(player.playerItems[player.xRemoveSlot], player.xRemoveSlot, Xamount);
				player.getItemAssistant().resetItems(7423);
				break;

			case 3322:
				if (player.duelStatus <= 0) {
					player.getTrading().tradeItem(player.xRemoveId, player.xRemoveSlot, Xamount);
				} else {
					player.getDueling().stakeItem(player.xRemoveId, player.xRemoveSlot, Xamount);
				}
				break;

			case 3415:
				if (player.duelStatus <= 0) {
					player.getTrading().fromTrade(player.xRemoveId, player.xRemoveSlot, Xamount);
				}
				break;

			case 6669:
				player.getDueling().fromDuel(player.xRemoveId, player.xRemoveSlot, Xamount);
				break;

			case 3900:
				player.getShopAssistant().buyItem(player.xRemoveId, player.xRemoveSlot, Xamount);
				break;

			case 3823:
				player.getShopAssistant().sellItem(player.xRemoveId, player.xRemoveSlot, Xamount);
				break;
		}
	}
}
