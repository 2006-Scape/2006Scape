package redone.net.packets.impl;

import redone.Connection;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;
import redone.game.players.antimacro.AntiSpam;
import redone.net.packets.PacketType;
import redone.util.GameLogger;
import redone.util.Misc;

/**
 * Private messaging, friends etc
 **/
public class PrivateMessaging implements PacketType {

	public final int ADD_FRIEND = 188, SEND_PM = 126, REMOVE_FRIEND = 215,
			CHANGE_PM_STATUS = 95, REMOVE_IGNORE = 59, ADD_IGNORE = 133;

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		switch (packetType) {

		case ADD_FRIEND:
			player.friendUpdate = true;
			long friendToAdd = player.getInStream().readQWord();
			boolean canAdd = true;

			for (long friend : player.friends) {
				if (friend != 0 && friend == friendToAdd) {
					canAdd = false;
					player.getActionSender().sendMessage(friendToAdd + " is already on your friends list.");
				}
			}
			if (canAdd) {
				for (int i1 = 0; i1 < player.friends.length; i1++) {
					if (player.friends[i1] == 0) {
						player.friends[i1] = friendToAdd;
						for (int i2 = 1; i2 < PlayerHandler.players.length; i2++) {
							if (PlayerHandler.players[i2] != null && PlayerHandler.players[i2].isActive && Misc.playerNameToInt64(PlayerHandler.players[i2].playerName) == friendToAdd) {
								Client o = (Client) PlayerHandler.players[i2];
								if (o != null) {
									if (PlayerHandler.players[i2].privateChat == 0 || PlayerHandler.players[i2].privateChat == 1 && o.getPlayerAssistant().isInPM(Misc.playerNameToInt64(player.playerName))) {
										player.getPlayerAssistant().loadPM(friendToAdd, 1);
										break;
									}
								}
							}
						}
						break;
					}
				}
			}
			break;

		case SEND_PM:
			long sendMessageToFriendId = player.getInStream().readQWord();
			byte pmchatText[] = new byte[100];
			int pmchatTextSize = (byte) (packetSize - 8);
			player.getInStream().readBytes(pmchatText, pmchatTextSize, 0);
			String word = Misc.textUnpack(pmchatText, player.packetSize - 2).toLowerCase();// used
			if (player.getPlayerAssistant().isPlayer()) {
				GameLogger.writeLog(player.playerName, "pmsent", player.playerName + " said " + Misc.textUnpack(pmchatText, packetSize - 8) + "");
			}
			if (!AntiSpam.blockedWords(player, word, false) || Connection.isMuted(player)) {
				return;
			}
			for (long friend : player.friends) {
				if (friend == sendMessageToFriendId) {
					boolean pmSent = false;

					for (int i2 = 1; i2 < PlayerHandler.players.length; i2++) {
						if (PlayerHandler.players[i2] != null && PlayerHandler.players[i2].isActive && Misc.playerNameToInt64(PlayerHandler.players[i2].playerName) == sendMessageToFriendId) {
							Client o = (Client) PlayerHandler.players[i2];
							if (o != null) {
								if (PlayerHandler.players[i2].privateChat == 0 || PlayerHandler.players[i2].privateChat == 1 && o.getPlayerAssistant().isInPM(Misc.playerNameToInt64(player.playerName))) {
									if (friend == sendMessageToFriendId) {
										o.getPlayerAssistant().sendPM(Misc.playerNameToInt64(player.playerName), player.playerRights, pmchatText, pmchatTextSize);
										if (player.getPlayerAssistant().isPlayer()) {
											GameLogger.writeLog(o.playerName, "pmrecieved", player.playerName + " said to " + o.playerName + " " + Misc.textUnpack(pmchatText, packetSize - 8) + "");
										}
										pmSent = true;
									}
								}
							}
							break;
						}
					}
					if (!pmSent) {
						player.getActionSender().sendMessage("That player is currently offline.");
						break;
					}
				}
			}
			break;

		case REMOVE_FRIEND:
			player.friendUpdate = true;
			long friendToRemove = player.getInStream().readQWord();

			for (int i1 = 0; i1 < player.friends.length; i1++) {
				if (player.friends[i1] == friendToRemove) {
					for (int i2 = 1; i2 < PlayerHandler.players.length; i2++) {
						Client o = (Client) PlayerHandler.players[i2];
						if (o != null) {
							if (player.friends[i1] == Misc
									.playerNameToInt64(PlayerHandler.players[i2].playerName)) {
								o.getPlayerAssistant().updatePM(player.playerId, 0);
								break;
							}
						}
					}
					player.friends[i1] = 0;
					break;
				}
			}
			break;

		case REMOVE_IGNORE:
			player.friendUpdate = true;
			long ignore = player.getInStream().readQWord();

			for (int i = 0; i < player.ignores.length; i++) {
				if (player.ignores[i] == ignore) {
					player.ignores[i] = 0;
					break;
				}
			}
			break;

		case CHANGE_PM_STATUS:
			// int tradeAndCompete = c.getInStream().readUnsignedByte();
			player.privateChat = player.getInStream().readUnsignedByte();
			// int publicChat = c.getInStream().readUnsignedByte();
			for (int i1 = 1; i1 < PlayerHandler.players.length; i1++) {
				if (PlayerHandler.players[i1] != null
						&& PlayerHandler.players[i1].isActive) {
					Client o = (Client) PlayerHandler.players[i1];
					if (o != null) {
						o.getPlayerAssistant().updatePM(player.playerId, 1);
					}
				}
			}
			break;

		case ADD_IGNORE:
			player.friendUpdate = true;
			long ignoreAdd = player.getInStream().readQWord();
			for (int i = 0; i < player.ignores.length; i++) {
				if (player.ignores[i] == 0) {
					player.ignores[i] = ignoreAdd;
					break;
				}
			}
			break;

		}

	}
}
