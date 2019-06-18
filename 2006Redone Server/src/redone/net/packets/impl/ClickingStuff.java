package redone.net.packets.impl;

import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.net.packets.PacketType;
import redone.util.Misc;

/**
 * Clicking stuff (interfaces)
 **/
public class ClickingStuff implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		if (player.playerIsBusy()) {
			player.playerIsBusy = false;
		}
		if (player.isBanking)
	         player.isBanking = false;
		if(player.isShopping)
	        player.isShopping = false;
		if (player.inTrade) {
			if (!player.acceptedTrade) {
				Client opponent = (Client) PlayerHandler.players[player.tradeWith];
				opponent.tradeAccepted = false;
				player.tradeAccepted = false;
				opponent.tradeStatus = 0;
				player.tradeStatus = 0;
				player.tradeConfirmed = false;
				player.tradeConfirmed2 = false;
				player.getActionSender().sendMessage("@red@Trade has been declined.");
				opponent.getActionSender().sendMessage("@red@Other player has declined the trade.");
				Misc.println("trade reset");
				player.getTrading().declineTrade();
			}
		}

		if(player.openDuel && player.duelStatus >= 1 && player.duelStatus <= 4) {
		Client o = (Client) PlayerHandler.players[player.duelingWith];
		if (o != null)
			if (player.duelStatus >= 1 && player.duelStatus <= 4)
				player.getDueling().declineDuel();
				o.getDueling().declineDuel();
			}

		if (player.duelStatus == 6) {
			player.getDueling().claimStakedItems();
		}

	}

}
