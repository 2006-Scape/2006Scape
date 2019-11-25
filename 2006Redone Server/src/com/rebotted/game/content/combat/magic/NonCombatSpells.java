package com.rebotted.game.content.combat.magic;

import com.rebotted.game.players.Client;

public class NonCombatSpells {

	public static void teleportObelisk(Client c, int x, int y, int height) {
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.getPacketSender().sendMessage(
					"You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && !c.isTeleporting) {
			c.stopMovement();
			c.getPacketSender().closeAllWindows();
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceNpc(0);
			c.getPlayerAssistant().spellTeleport(x, y, height);
		}
	}

}
