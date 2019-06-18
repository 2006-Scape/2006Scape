package redone.world;

import redone.Connection;
import redone.game.items.Item;
import redone.game.players.Client;
import redone.game.players.PlayerHandler;

/**
 * @author Sanity
 */

public class ClanChatHandler {

	public ClanChatHandler() {

	}

	public Clan[] clans = new Clan[100];

	public void handleClanChat(Client player, String name) {
		for (int j = 0; j < clans.length; j++) {
			if (clans[j] != null) {
				if (clans[j].name.equalsIgnoreCase(name)) {
					addToClan(player.playerId, j);
					return;
				}
			}
		}
		makeClan(player, name);
	}

	public void makeClan(Client player, String name) {
		if (openClan() >= 0) {
			if (validName(name)) {
				player.clanId = openClan();
				clans[player.clanId] = new Clan(player, name);
				addToClan(player.playerId, player.clanId);
			} else {
				player.getActionSender().sendMessage("A clan with this name already exists.");
			}
		} else {
			player.getActionSender().sendMessage("Your clan chat request could not be completed.");
		}
	}

	public void updateClanChat(int clanId) {
		for (int j = 0; j < clans[clanId].members.length; j++) {
			if (clans[clanId].members[j] <= 0)
				continue;
			if (PlayerHandler.players[clans[clanId].members[j]] != null) {
				Client c = (Client) PlayerHandler.players[clans[clanId].members[j]];
				c.getPlayerAssistant().sendFrame126("Talking in: " + clans[clanId].name,
						18139);
				c.getPlayerAssistant().sendFrame126("Owner: " + clans[clanId].owner, 18140);
				int slotToFill = 18144;
				for (int i = 0; i < clans[clanId].members.length; i++) {
					if (clans[clanId].members[i] > 0) {
						if (PlayerHandler.players[clans[clanId].members[i]] != null) {
							c.getPlayerAssistant().sendFrame126(PlayerHandler.players[clans[clanId].members[i]].playerName, slotToFill);
							slotToFill++;
						}
					}
				}
				for (int k = slotToFill; k < 18244; k++)
					c.getPlayerAssistant().sendFrame126("", k);
			}
		}
	}

	public int openClan() {
		for (int j = 0; j < clans.length; j++) {
			if (clans[j] == null || clans[j].owner == "")
				return j;
		}
		return -1;
	}

	public boolean validName(String name) {
		for (int j = 0; j < clans.length; j++) {
			if (clans[j] != null) {
				if (clans[j].name.equalsIgnoreCase(name))
					return false;
			}
		}
		return true;
	}

	public void addToClan(int playerId, int clanId) {
		if (clans[clanId] != null) {
			for (int j = 0; j < clans[clanId].members.length; j++) {
				if (clans[clanId].members[j] <= 0) {
					clans[clanId].members[j] = playerId;
					PlayerHandler.players[playerId].clanId = clanId;
					// c.getActionSender().sendMessage("You have joined the clan chat: " +
					// clans[clanId].name);
					messageToClan(
							PlayerHandler.players[playerId].playerName
									+ " has joined the channel.", clanId);
					updateClanChat(clanId);
					return;
				}
			}
		}
	}

	public void leaveClan(int playerId, int clanId) {
		if (clanId < 0) {
			Client c = (Client) PlayerHandler.players[playerId];
			c.getActionSender().sendMessage("You are not in a clan.");
			return;
		}
		if (clans[clanId] != null) {
			if (PlayerHandler.players[playerId].playerName
					.equalsIgnoreCase(clans[clanId].owner)) {
				messageToClan("The clan has been deleted by the owner.", clanId);
				destructClan(PlayerHandler.players[playerId].clanId);
				return;
			}
			for (int j = 0; j < clans[clanId].members.length; j++) {
				if (clans[clanId].members[j] == playerId) {
					clans[clanId].members[j] = -1;
				}
			}
			if (PlayerHandler.players[playerId] != null) {
				Client c = (Client) PlayerHandler.players[playerId];
				PlayerHandler.players[playerId].clanId = -1;
				c.getActionSender().sendMessage("You have left the clan.");
				c.getPlayerAssistant().clearClanChat();
			}
			updateClanChat(clanId);
		} else {
			Client c = (Client) PlayerHandler.players[playerId];
			PlayerHandler.players[playerId].clanId = -1;
			c.getActionSender().sendMessage("You are not in a clan.");
		}
	}

	public void destructClan(int clanId) {
		if (clanId < 0)
			return;
		for (int j = 0; j < clans[clanId].members.length; j++) {
			if (clanId < 0)
				continue;
			if (clans[clanId].members[j] <= 0)
				continue;
			if (PlayerHandler.players[clans[clanId].members[j]] != null) {
				Client c = (Client) PlayerHandler.players[clans[clanId].members[j]];
				c.clanId = -1;
				c.getPlayerAssistant().clearClanChat();
			}
		}
		clans[clanId].members = new int[50];
		clans[clanId].owner = "";
		clans[clanId].name = "";
	}

	public void messageToClan(String message, int clanId) {
		if (clanId < 0)
			return;
		for (int j = 0; j < clans[clanId].members.length; j++) {
			if (clans[clanId].members[j] < 0)
				continue;
			if (PlayerHandler.players[clans[clanId].members[j]] != null) {
				Client c = (Client) PlayerHandler.players[clans[clanId].members[j]];
				c.getActionSender().sendMessage("@red@" + message);
			}
		}
	}

	public void playerMessageToClan(Client c, int playerId, String message, int clanId) {
		if (Connection.isMuted(c)) {
			return;
		}
		if (clanId < 0)
			return;
		for (int j = 0; j < clans[clanId].members.length; j++) {
			if (clans[clanId].members[j] <= 0)
				continue;
			if (PlayerHandler.players[clans[clanId].members[j]] != null) {
				c = (Client) PlayerHandler.players[clans[clanId].members[j]];
				c.getActionSender().sendClan(PlayerHandler.players[playerId].playerName,
						message, clans[clanId].name,
						PlayerHandler.players[playerId].playerRights);
			}
		}
	}

	public void sendLootShareMessage(int clanId, String message) {
		if (clanId >= 0) {
			for (int j = 0; j < clans[clanId].members.length; j++) {
				if (clans[clanId].members[j] <= 0)
					continue;
				if (PlayerHandler.players[clans[clanId].members[j]] != null) {
					Client c = (Client) PlayerHandler.players[clans[clanId].members[j]];
					c.getActionSender().sendClan("Lootshare", message, clans[clanId].name, 2);
				}
			}
		}
	}

	public void handleLootShare(Client c, int item, int amount) {
		sendLootShareMessage(c.clanId, c.playerName + " has received " + amount
				+ "x " + Item.getItemName(item) + ".");
	}

}