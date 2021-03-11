package com.rs2.net.packets.impl;

import com.rs2.game.content.random.PartyRoom;
import com.rs2.game.content.skills.crafting.JewelryMaking;
import com.rs2.game.items.Weight;
import com.rs2.game.items.impl.RareProtection;
import com.rs2.game.players.Player;
import com.rs2.net.packets.PacketType;

/**
 * Remove Item
 **/
public class RemoveItem implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int interfaceId = player.getInStream().readUnsignedWordA();
		int removeSlot = player.getInStream().readUnsignedWordA();
		int removeId = player.getInStream().readUnsignedWordA();
		if (!RareProtection.removeItem(player, removeId)) {
			return;
		}

		player.endCurrentTask();

		Weight.updateWeight(player);

		switch (interfaceId) {

		case 4233:
		case 4239:
		case 4245:
			JewelryMaking.mouldItem(player, removeId, 1);
			break;

		case 7423:
			if (player.inTrade) {
				player.getTrading().declineTrade(true);
				return;
			}
			player.getItemAssistant().bankItem(removeId, removeSlot, 1);
			player.getItemAssistant().resetItems(7423);
			break;

		case 1688:
			player.getItemAssistant().removeItem(removeId, removeSlot);
			break;

		case 5064:
			if (player.inPartyRoom) {
				PartyRoom.depositItem(player, removeId, 1);
			} else {
				player.getItemAssistant().bankItem(removeId, removeSlot, 1);
			}
			break;

		case 5382:
			player.getItemAssistant().fromBank(removeId, removeSlot, 1);
			break;

		case 3900:
			player.getShopAssistant().buyFromShopPrice(removeId);
			break;

		case 3823:
			player.getShopAssistant().sellToShopPrice(removeId, removeSlot);
			break;

		case 3322:
			if (player.duelStatus <= 0) {
				player.getTrading().tradeItem(removeId, removeSlot, 1);
			} else {
				player.getDueling().stakeItem(removeId, removeSlot, 1);
			}
			break;

		case 3415:
			if (player.duelStatus <= 0) {
				player.getTrading().fromTrade(removeId, removeSlot, 1);
			}
			break;

		case 6669:
			player.getDueling().fromDuel(removeId, removeSlot, 1);
			break;

		case 1119:
		case 1120:
		case 1121:
		case 1122:
		case 1123:
			player.getSmithing().readInput(player, player.playerLevel[player.playerSmithing], removeId, 1);
			break;

		}
		Weight.updateWeight(player);
	}

}
