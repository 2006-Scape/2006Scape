package com.rs2.net.packets.impl;

import com.rs2.Constants;
import com.rs2.game.content.random.PartyRoom;
import com.rs2.game.content.skills.crafting.JewelryMaking;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.world.Boundary;

/**
 * Bank 5 Items
 **/
public class Bank5 implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		int interfaceId = packet.readSignedWordBigEndianA();
		int removeId = packet.readSignedWordBigEndianA();
		int removeSlot = packet.readSignedWordBigEndian();
		player.endCurrentTask();
		switch (interfaceId) {

		case 2274:
			if (Boundary.isIn(player, Boundary.PARTY_ROOM)) {
				PartyRoom.withdrawItem(player, removeSlot, 5);
				return;
			}
			break;
		case 4233:
		case 4239:
		case 4245:
			JewelryMaking.mouldItem(player, removeId, 5);
			break;
		case 3900:
			player.getShopAssistant().buyItem(removeId, removeSlot, 1); //this says 1 in BANKx5 but it's banking 5 for real... strange shit.
			break;

		case 3823:
			if(!player.getItemAssistant().playerHasItem(removeId)) {
				return;
			}
			if (player.inTrade) {
				player.getTrading().declineTrade(true);
			}
			player.getShopAssistant().sellItem(removeId, removeSlot, 1);
			break;

		case 7423:
			if (player.storing) {
				return;
			}
			player.getItemAssistant().bankItem(removeId, removeSlot, 5);
			player.getItemAssistant().resetItems(7423);
			break;

		case 5064:
			if(!player.getItemAssistant().playerHasItem(removeId)) {
				return;
			}
			if (Boundary.isIn(player, Boundary.PARTY_ROOM)) {
				PartyRoom.depositItem(player, removeId, 5);
				break;
			}
			if (player.inTrade) {
				player.getPacketSender().sendMessage(
						"You can't store items while trading!");
				return;
			}
			player.getItemAssistant().bankItem(removeId, removeSlot, 5);
			break;

		case 5382:
			player.getItemAssistant().fromBank(removeId, removeSlot, 5);
			break;

		case 3322:
			if (player.duelStatus <= 0) {
				player.getTrading().tradeItem(removeId, removeSlot, 5);
			} else {
				player.getDueling().stakeItem(removeId, removeSlot, 5);
			}
			break;

		case 3415:
			if (player.duelStatus <= 0) {
				player.getTrading().fromTrade(removeId, removeSlot, 5);
			}
			break;

		case 6669:
			player.getDueling().fromDuel(removeId, removeSlot, 5);
			break;

		case 1119:
		case 1120:
		case 1121:
		case 1122:
		case 1123:
			player.getSmithing().readInput(player, player.playerLevel[Constants.SMITHING], removeId, 5);
			break;

		case 15948: // Mage Training Arena Shop
			player.getMageTrainingArena().buyItem(removeId);
			break;
		}
	}

}
