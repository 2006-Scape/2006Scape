package redone.net.packets.impl;

import redone.game.content.random.PartyRoom;
import redone.game.players.Client;
import redone.net.packets.PacketType;

/**
 * Bank X Items
 **/
public class BankX2 implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		int Xamount = player.getInStream().readDWord();
		if (Xamount < 0) {
            Xamount = player.getItemAssistant().getItemAmount(player.xRemoveId);
        }
        if (Xamount == 0) {
            Xamount = 1;
        }
		switch (player.xInterfaceId) {
		case 5064:
			if(!player.getItemAssistant().playerHasItem(player.playerItems[player.xRemoveSlot], Xamount))
                return;
			if (player.inPartyRoom) {
				PartyRoom.depositItem(player, player.xRemoveId, player.getItemAssistant().itemAmount(player.playerItems[player.xRemoveSlot]));
				break;
			}
			if (player.inTrade) {
				player.getActionSender().sendMessage("You can't store items while trading!");
				return;
			}
			player.getItemAssistant().bankItem(player.playerItems[player.xRemoveSlot], player.xRemoveSlot, Xamount);
			break;

		case 5382:
			if(!player.getItemAssistant().playerHasItem(player.playerItems[player.xRemoveSlot], Xamount))
                return;
			player.getItemAssistant().fromBank(player.bankItems[player.xRemoveSlot], player.xRemoveSlot, Xamount);
			break;

		case 7423:
			if (player.storing) {
				return;
			}
			player.getItemAssistant().bankItem(player.playerItems[player.xRemoveSlot],
					player.xRemoveSlot, Xamount);
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
		}
	}
}
