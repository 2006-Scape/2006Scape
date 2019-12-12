package com.rebotted.net.packets.impl;

import com.rebotted.game.items.Weight;
import com.rebotted.game.items.impl.RareProtection;
import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

/**
 * Wear Item
 **/
public class WearItem implements PacketType {

	@Override
	public void processPacket(Player player, int packetType, int packetSize) {
		player.wearId = player.getInStream().readUnsignedWord();
		player.wearSlot = player.getInStream().readUnsignedWordA();
		player.interfaceId = player.getInStream().readUnsignedWordA();
		Weight.updateWeight(player);
		if (!RareProtection.equipItem(player)) {
			return;
		}
		if (player.duelStatus > 0 && player.duelStatus < 5) {
			return;
		}
		if (!player.getItemAssistant().playerHasItem(player.wearId, 1, player.wearSlot)) {
			return;
		}
		if (player.playerIndex > 0 || player.npcIndex > 0) {
			player.getCombatAssistant().resetPlayerAttack();
		}

		player.endCurrentTask();

		if (player.wearId >= 5509 && player.wearId <= 5515) {
			int pouch = -1;
			int a = player.wearId;
			if (a == 5509) {
				pouch = 0;
			}
			if (a == 5510) {
				pouch = 1;
			}
			if (a == 5512) {
				pouch = 2;
			}
			if (a == 5514) {
				pouch = 3;
			}
			player.getPlayerAssistant().emptyPouch(pouch);
			return;
		}
		if (player.wearSlot == player.playerHat) {
			player.getPacketSender().setConfig(491, 0);
		}

		player.getPlayerAssistant().handleTiara();
		player.getItemAssistant().wearItem(player.wearId, player.wearSlot);
		Weight.updateWeight(player);
	}

}
