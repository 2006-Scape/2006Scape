package com.rebotted.net.packets.impl;

import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.net.packets.PacketType;
import com.rebotted.util.Misc;

/**
 * Clicking stuff (interfaces)
 **/
public class ClickingStuff implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		if (player.playerIsBusy()) {
			player.playerIsBusy = false;
		}
		if (player.isBanking)
	         player.isBanking = false;
		if(player.isShopping)
	        player.isShopping = false;
		if (player.inTrade) {
			Player opponent = (Player) PlayerHandler.players[player.tradeWith];
			if (!player.acceptedTrade || !opponent.inTrade || opponent == null) {
				opponent = (Player) PlayerHandler.players[player.tradeWith];
				opponent.tradeAccepted = false;
				player.tradeAccepted = false;
				opponent.tradeStatus = 0;
				player.tradeStatus = 0;
				player.tradeConfirmed = false;
				player.tradeConfirmed2 = false;
				player.getPacketSender().sendMessage("@red@Trade has been declined.");
				opponent.getPacketSender().sendMessage("@red@Other player has declined the trade.");
				Misc.println("trade reset");
				player.getTrading().declineTrade();
				opponent.getTrading().declineTrade();
				}
			}

		if(player.openDuel && player.duelStatus >= 1 && player.duelStatus <= 4) {
		Player o = PlayerHandler.players[player.duelingWith];
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
