package com.rs2.net.packets.impl;

import org.apollo.cache.def.ItemDefinition;

import com.rs2.game.content.random.PartyRoom;
import com.rs2.game.items.GameItem;
import com.rs2.game.items.ItemData;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.world.Boundary;

/**
 * Bank All Items
 **/
public class BankAll implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		int removeSlot = packet.readUnsignedWordA();
		int interfaceId = packet.readUnsignedWord();
		int removeId = packet.readUnsignedWordA();
		player.endCurrentTask();
		switch (interfaceId) {
		case 2274:
			if (Boundary.isIn(player, Boundary.PARTY_ROOM)) {
				PartyRoom.withdrawItem(player, removeSlot, player.partyN[removeSlot]);
				return;
			}
			break;
		// buy x
		case 3900:
			player.outStream.createFrame(27);
			player.xRemoveSlot = removeSlot;
			player.xRemoveId = removeId;
			player.xInterfaceId = interfaceId;
			break;

		// sell x
		case 3823:
			if(!player.getItemAssistant().playerHasItem(removeId)) {
				return;
			}
			player.outStream.createFrame(27);
			player.xRemoveSlot = removeSlot;
			player.xRemoveId = removeId;
			player.xInterfaceId = interfaceId;
			break;

		case 7423:
			if (player.storing) {

				return;
			}
			player.getItemAssistant().bankItem(player.playerItems[removeSlot],
					removeSlot, player.playerItemsN[removeSlot]);
			player.getItemAssistant().resetItems(7423);
			break;

		case 5064:
			if(!player.getItemAssistant().playerHasItem(removeId)) {
				return;
			}
			if (Boundary.isIn(player, Boundary.PARTY_ROOM)) {
				PartyRoom.depositItem(player, removeId, player.getItemAssistant().itemAmount(player.playerItems[removeSlot]));
				break;
			}
			if (player.inTrade) {
				player.getPacketSender().sendMessage("You can't store items while trading!");
				return;
			}
			if (ItemDefinition.lookup(removeId).isStackable()) {
				player.getItemAssistant().bankItem(player.playerItems[removeSlot], removeSlot, player.playerItemsN[removeSlot]);
			} else {
				player.getItemAssistant().bankItem(player.playerItems[removeSlot], removeSlot, player.getItemAssistant().itemAmount(player.playerItems[removeSlot]));
			}
			break;

		case 5382:
			if (player.getItemAssistant().playerHasItem(removeId)) {
				for (int i = 0; i <= 27; i++) {
					if (removeId == player.playerItems[i] - 1)
					{
						if ((player.playerItemsN[i] + player.bankItemsN[removeSlot] + 1) < -1) {
							player.getPacketSender().sendMessage("Can't withdraw more of that item!");
							break;
						} else {
							player.getItemAssistant().fromBank(player.bankItems[removeSlot], removeSlot,
									player.bankItemsN[removeSlot]);
						}
					}
				}
			}
			else
			{
				player.getItemAssistant().fromBank(player.bankItems[removeSlot], removeSlot,
						player.bankItemsN[removeSlot]);
			}
			break;

		case 3322:
			if (player.duelStatus <= 0) {
				if (ItemDefinition.lookup(removeId).isStackable()) {
					player.getTrading().tradeItem(removeId, removeSlot,
							player.playerItemsN[removeSlot]);
				} else {
					player.getTrading().tradeItem(removeId, removeSlot, 28);
				}
			} else {
				if (ItemDefinition.lookup(removeId).isStackable() || ItemDefinition.lookup(removeId).isNote()) {
					player.getDueling().stakeItem(removeId, removeSlot,
							player.playerItemsN[removeSlot]);
				} else {
					player.getDueling().stakeItem(removeId, removeSlot, 28);
				}
			}
			break;

		case 3415:
			if (player.duelStatus <= 0) {
				if (ItemDefinition.lookup(removeId).isStackable()) {
					for (GameItem item : player.getTrading().offeredItems) {
						if (item.id == removeId) {
							player.getTrading()
									.fromTrade(
											removeId,
											removeSlot,
											player.getTrading().offeredItems
													.get(removeSlot).amount);
						}
					}
				} else {
					for (GameItem item : player.getTrading().offeredItems) {
						if (item.id == removeId) {
							player.getTrading().fromTrade(removeId, removeSlot, 28);
						}
					}
				}
			}
			break;

		case 6669:
			if (ItemDefinition.lookup(removeId).isStackable() || ItemDefinition.lookup(removeId).isNote()) {
				for (GameItem item : player.getDueling().stakedItems) {
					if (item.id == removeId) {
						player.getDueling()
								.fromDuel(
										removeId,
										removeSlot,
										player.getDueling().stakedItems
												.get(removeSlot).amount);
					}
				}

			} else {
				player.getDueling().fromDuel(removeId, removeSlot, 28);
			}
			break;

		}
	}

}
