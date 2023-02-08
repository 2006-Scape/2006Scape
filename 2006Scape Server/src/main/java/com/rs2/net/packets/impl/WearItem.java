package com.rs2.net.packets.impl;

import com.rs2.Constants;
import com.rs2.game.items.Weight;
import com.rs2.game.items.impl.RareProtection;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;
import com.rs2.util.Misc;

/**
 * Wear Item
 **/
public class WearItem implements PacketType {

	@Override
	public void processPacket(Player player, Packet packet) {
		player.wearId = packet.readUnsignedWord();
		player.wearSlot = packet.readUnsignedWordA();
		player.interfaceId = packet.readUnsignedWordA();
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

		if(player.wearId == 6583 || player.wearId == 7927) {
			for (int i = 0; i < Constants.SIDEBARS.length; i++) {
				player.getPacketSender().setSidebarInterface(i, 6014);
			}
			switch(player.wearId) {
				case 6583:
					player.getPacketSender().sendMessage("As you put on the ring you turn into an rock!");
					player.npcId2 = 2626;
					break;
				case 7927:
					player.getPacketSender().sendMessage("As you put on the ring you turn into an egg!");
					player.npcId2 = 3689 + Misc.random(5);
					break;
			}
			player.isNpc = true;
			player.updateRequired = true;
			player.setAppearanceUpdateRequired(true);
		}

		player.getPlayerAssistant().handleTiara();
		player.getItemAssistant().wearItem(player.wearId, player.wearSlot);
		Weight.updateWeight(player);
	}

}
