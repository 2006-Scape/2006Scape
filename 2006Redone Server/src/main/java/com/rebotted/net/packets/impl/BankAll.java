package com.rebotted.net.packets.impl;

import com.rebotted.game.content.random.PartyRoom;
import com.rebotted.game.items.GameItem;
import com.rebotted.game.items.ItemData;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

/**
 * Bank All Items
 **/
public class BankAll implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		int removeSlot = player.getInStream().readUnsignedWordA();
		int interfaceId = player.getInStream().readUnsignedWord();
		int removeId = player.getInStream().readUnsignedWordA();
		player.endCurrentTask();
		switch (interfaceId) {
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
			if (player.inPartyRoom) {
				PartyRoom.depositItem(player, removeId, player.getItemAssistant().itemAmount(player.playerItems[removeSlot]));
				break;
			}
			if (player.inTrade) {
				player.getPacketSender().sendMessage("You can't store items while trading!");
				return;
			}
			if (ItemData.itemStackable[removeId]) {
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
				if (ItemData.itemStackable[removeId]) {
					player.getTrading().tradeItem(removeId, removeSlot,
							player.playerItemsN[removeSlot]);
				} else {
					player.getTrading().tradeItem(removeId, removeSlot, 28);
				}
			} else {
				if (ItemData.itemStackable[removeId] || ItemData.itemIsNote[removeId]) {
					player.getDueling().stakeItem(removeId, removeSlot,
							player.playerItemsN[removeSlot]);
				} else {
					player.getDueling().stakeItem(removeId, removeSlot, 28);
				}
			}
			break;

		case 3415:
			if (player.duelStatus <= 0) {
				if (ItemData.itemStackable[removeId]) {
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
			if (ItemData.itemStackable[removeId] || ItemData.itemIsNote[removeId]) {
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
