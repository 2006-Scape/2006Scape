package com.rs2.net.packets.impl;

import com.rs2.game.content.random.PartyRoom;
import com.rs2.game.content.skills.cooking.Cooking;
import com.rs2.game.content.skills.smithing.Smelting;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.world.Boundary;

public class InterfaceX implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		player.endCurrentTask();
		int Xamount = packet.readDWord();
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
			case 2274:
				if (Boundary.isIn(player, Boundary.PARTY_ROOM)) {
					PartyRoom.withdrawItem(player, player.xRemoveSlot, Xamount);
					return;
				}
				break;
			case 5064:
				if (player.inTrade) {
					player.getPacketSender().sendMessage("You can't store items while trading!");
					return;
				}
				if (Boundary.isIn(player, Boundary.PARTY_ROOM)) {
					PartyRoom.depositItem(player, player.xRemoveId, Xamount);
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
