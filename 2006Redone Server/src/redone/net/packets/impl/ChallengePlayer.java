package redone.net.packets.impl;

import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.net.packets.PacketType;

/**
 * Challenge Player
 **/
public class ChallengePlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		switch (packetType) {
		case 128:
			int answerPlayer = c.getInStream().readUnsignedWord();
		    if(PlayerHandler.players[answerPlayer] == null || answerPlayer == c.playerId)
                return;

			if (c.duelingArena() || c.duelStatus == 5) {
				c.getActionSender().sendMessage("You can't challenge inside the arena!");
				return;
			}
			
			if (c.isBotting == true) {
				c.getActionSender().sendMessage("You can't challenge players, until you confirm you are not botting.");
				c.getActionSender().sendMessage("If you need to you can type ::amibotting, to see if your botting.");
				return;
			}
			if (c.inDuelArena()) {
				c.getDueling().requestDuel(answerPlayer);
			}
			break;
		}
	}
}
