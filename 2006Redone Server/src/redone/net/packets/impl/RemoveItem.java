package redone.net.packets.impl;

import redone.game.content.random.PartyRoom;
import redone.game.content.skills.crafting.JewelryMaking;
import redone.game.items.impl.RareProtection;
import redone.game.players.Client;
import redone.net.packets.PacketType;

/**
 * Remove Item
 **/
public class RemoveItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int interfaceId = c.getInStream().readUnsignedWordA();
		int removeSlot = c.getInStream().readUnsignedWordA();
		int removeId = c.getInStream().readUnsignedWordA();
		if (removeId == 88) {
			c.weight += 4.5;
			c.getActionSender().writeWeight((int) c.weight);
		}
		if (!RareProtection.removeItem(c, removeId)) {
			return;
		}
		switch (interfaceId) {

		case 4233:
		case 4239:
		case 4245:
			JewelryMaking.mouldItem(c, removeId, 1);
			break;

		case 7423:
			if (c.inTrade) {
				c.getTrading().declineTrade(true);
				return;
			}
			c.getItemAssistant().bankItem(removeId, removeSlot, 1);
			c.getItemAssistant().resetItems(7423);
			break;

		case 1688:
			c.getItemAssistant().removeItem(removeId, removeSlot);
			break;

		case 5064:
			if (c.inPartyRoom) {
				PartyRoom.depositItem(c, removeId, 1);
			} else {
				c.getItemAssistant().bankItem(removeId, removeSlot, 1);
			}
			break;

		case 5382:
			c.getItemAssistant().fromBank(removeId, removeSlot, 1);
			break;

		case 3900:
			c.getShopAssistant().buyFromShopPrice(removeId, removeSlot);
			break;

		case 3823:
			c.getShopAssistant().sellToShopPrice(removeId, removeSlot);
			break;

		case 3322:
			if (c.duelStatus <= 0) {
				c.getTrading().tradeItem(removeId, removeSlot, 1);
			} else {
				c.getDueling().stakeItem(removeId, removeSlot, 1);
			}
			break;

		case 3415:
			if (c.duelStatus <= 0) {
				c.getTrading().fromTrade(removeId, removeSlot, 1);
			}
			break;

		case 6669:
			c.getDueling().fromDuel(removeId, removeSlot, 1);
			break;

		case 1119:
		case 1120:
		case 1121:
		case 1122:
		case 1123:
			c.getSmithing().readInput(c.playerLevel[c.playerSmithing],
					Integer.toString(removeId), c, 1);
			break;

		}
	}

}
