package redone.net.packets.impl;

import redone.game.content.random.PartyRoom;
import redone.game.items.GameItem;
import redone.game.items.Item;
import redone.game.players.Client;
import redone.net.packets.PacketType;

/**
 * Bank All Items
 **/
public class BankAll implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		int removeSlot = player.getInStream().readUnsignedWordA();
		int interfaceId = player.getInStream().readUnsignedWord();
		int removeId = player.getInStream().readUnsignedWordA();
		switch (interfaceId) {
		case 3900:
			player.getShopAssistant().buyItem(removeId, removeSlot, 10);
			break;

		case 3823:
			if(!player.getItemAssistant().playerHasItem(removeId)) {
				return;
			}
			player.getShopAssistant().sellItem(removeId, removeSlot, 10);
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
				PartyRoom.depositItem(player, removeId, player.getItemAssistant()
						.itemAmount(player.playerItems[removeSlot]));
				break;
			}
			if (player.inTrade) {
				player.getActionSender().sendMessage(
						"You can't store items while trading!");
				return;
			}
			if (Item.itemStackable[removeId]) {
				player.getItemAssistant().bankItem(player.playerItems[removeSlot],
						removeSlot, player.playerItemsN[removeSlot]);
			} else {
				player.getItemAssistant().bankItem(
						player.playerItems[removeSlot],
						removeSlot,
						player.getItemAssistant().itemAmount(
								player.playerItems[removeSlot]));
			}
			break;

		case 5382:
			player.getItemAssistant().fromBank(player.bankItems[removeSlot], removeSlot,
					player.bankItemsN[removeSlot]);
			break;

		case 3322:
			if (player.duelStatus <= 0) {
				if (Item.itemStackable[removeId]) {
					player.getTrading().tradeItem(removeId, removeSlot,
							player.playerItemsN[removeSlot]);
				} else {
					player.getTrading().tradeItem(removeId, removeSlot, 28);
				}
			} else {
				if (Item.itemStackable[removeId] || Item.itemIsNote[removeId]) {
					player.getDueling().stakeItem(removeId, removeSlot,
							player.playerItemsN[removeSlot]);
				} else {
					player.getDueling().stakeItem(removeId, removeSlot, 28);
				}
			}
			break;

		case 3415:
			if (player.duelStatus <= 0) {
				if (Item.itemStackable[removeId]) {
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
			if (Item.itemStackable[removeId] || Item.itemIsNote[removeId]) {
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
