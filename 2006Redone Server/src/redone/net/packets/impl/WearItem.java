package redone.net.packets.impl;

import redone.game.items.impl.RareProtection;
import redone.game.players.Client;
import redone.net.packets.PacketType;

/**
 * Wear Item
 **/
public class WearItem implements PacketType {

	/* (non-Javadoc)
	 * @see server.net.packets.PacketType#processPacket(server.game.players.Client, int, int)
	 */
	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		player.wearId = player.getInStream().readUnsignedWord();
		player.wearSlot = player.getInStream().readUnsignedWordA();
		player.interfaceId = player.getInStream().readUnsignedWordA();
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

		if (player.wearId == 88) {
			player.weight -= 4.5;
			player.getActionSender().writeWeight((int) player.weight);
		}

		if (player.wearSlot == player.playerHat) {
			player.getActionSender().setConfig(491, 0);
		}

		player.getPlayerAssistant().handleTiara();
		player.getItemAssistant().wearItem(player.wearId, player.wearSlot);
	}

}
