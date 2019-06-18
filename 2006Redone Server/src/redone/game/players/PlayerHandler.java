package redone.game.players;

import java.net.InetSocketAddress;

import redone.Constants;
import redone.Server;
import redone.game.content.minigames.castlewars.CastleWars;
import redone.game.npcs.Npc;
import redone.game.npcs.NpcHandler;
import redone.util.Misc;
import redone.util.Stream;

public class PlayerHandler {

	public static Player players[] = new Player[Constants.MAX_PLAYERS];
	public static int playerCount = 0;
	public static String playersCurrentlyOn[] = new String[Constants.MAX_PLAYERS];
	public static boolean updateAnnounced;
	public static boolean updateRunning;
	public static int updateSeconds;
	public static long updateStartTime;
	private boolean kickAllPlayers = false;

	static {
		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			players[i] = null;
		}
	}

	public boolean newPlayerClient(Client client1) {
		int slot = -1;
		for (int i = 1; i < Constants.MAX_PLAYERS; i++) {
			if (players[i] == null || players[i].disconnected) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			return false;
		}
		client1.handler = this;
		client1.playerId = slot;
		players[slot] = client1;
		players[slot].isActive = true;
		players[slot].connectedFrom = ((InetSocketAddress) client1.getSession()
				.getRemoteAddress()).getAddress().getHostAddress();
		if (Constants.SERVER_DEBUG) {
			Misc.println("Player Slot " + slot + " slot 0 " + players[0]
					+ " Player Hit " + players[slot]);
		}
		return true;
	}

	public static int getPlayerCount() {
		return playerCount;
	}

	public void updatePlayerNames() {
		playerCount = 0;
		for (int i = 0; i < Constants.MAX_PLAYERS; i++) {
			if (players[i] != null) {
				playersCurrentlyOn[i] = players[i].playerName;
				playerCount++;
			} else {
				playersCurrentlyOn[i] = "";
			}
		}
	}

	public static int getPlayerID(String playerName) {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (playersCurrentlyOn[i] != null) {
				if (playersCurrentlyOn[i].equalsIgnoreCase(playerName)) {
					return i;
				}
			}
		}
		return -1;
	}

	public static boolean isPlayerOn(String playerName) {
		// synchronized (PlayerHandler.players) {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (playersCurrentlyOn[i] != null) {
				if (playersCurrentlyOn[i].equalsIgnoreCase(playerName)) {
					return true;
				}
			}
		}
		return false;
	}

	public void process() {
		// synchronized (PlayerHandler.players) {
		updatePlayerNames();
		if (kickAllPlayers) {
			for (int i = 0; i < PlayerHandler.players.length; i++) {
				if (players[i] != null) {
					players[i].disconnected = true;
				}
			}
		}
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (players[i] == null || !players[i].isActive) {
				continue;
			}
			try {

				Client t = (Client) PlayerHandler.players[i];
				if (players[i].disconnected) {
					if (players[i].playerEquipment[t.playerCape] == 4042
							|| players[i].playerEquipment[t.playerCape] == 4041) {
						CastleWars.deleteGameItems(t);
					}
					PlayerSave.saveGame(t);
				}

				if (players[i].disconnected
						&& (System.currentTimeMillis() - players[i].logoutDelay > 10000
								|| players[i].properLogout || kickAllPlayers)) {
					if (players[i].inTrade) {
						Client o = (Client) PlayerHandler.players[players[i].tradeWith];
						if (o != null) {
							o.getTrading().declineTrade();
						}
					}
					if(Server.trawler.players.contains(this)) {
						Server.trawler.players.remove(this);
				    }
					players[i].lastX = players[i].absX;
					players[i].lastY = players[i].absY;
					players[i].lastH = players[i].heightLevel;
					if (players[i].hasNpc == true) {
						t.getSummon().pickUpClean(t, players[i].summonId);
					}
					if (players[i].duelStatus == 5) {
						Client o = (Client) PlayerHandler.players[players[i].duelingWith];
						if (o != null) {
							o.getDueling().duelVictory();
						}
					} else if (players[i].duelStatus <= 4
							&& players[i].duelStatus >= 1) {
						Client o = (Client) PlayerHandler.players[players[i].duelingWith];
						if (o != null) {
							o.getDueling().declineDuel();
						}
					}
					Client o = (Client) PlayerHandler.players[i];
					if (PlayerSave.saveGame(o)) {
						System.out.println("Game saved for player "
								+ players[i].playerName);
					} else {
						System.out.println("Could not save for "
								+ players[i].playerName);
					}
					removePlayer(players[i]);
					players[i] = null;
					continue;
				}

				players[i].preProcessing();
				while (players[i].processQueuedPackets()) {
					;
				}
				players[i].process();
				players[i].postProcessing();
				players[i].getNextPlayerMovement();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (players[i] == null || !players[i].isActive) {
				continue;
			}
			try {
				Client t = (Client) PlayerHandler.players[i];
				if (players[i].disconnected) {
					if (players[i].playerEquipment[t.playerCape] == 4042
							|| players[i].playerEquipment[t.playerCape] == 4041) {
						CastleWars.deleteGameItems(t);
					}
					PlayerSave.saveGame(t);
				}
				if (players[i].disconnected
						&& (System.currentTimeMillis() - players[i].logoutDelay > 10000
								|| players[i].properLogout || kickAllPlayers)) {
					if (players[i].inTrade) {
						Client o = (Client) PlayerHandler.players[players[i].tradeWith];
						if (o != null) {
							o.getTrading().declineTrade();
						}
					}
					if(Server.trawler.players.contains(this)) {
						Server.trawler.players.remove(this);
				    }
					players[i].lastX = players[i].absX;
					players[i].lastY = players[i].absY;
					players[i].lastH = players[i].heightLevel;
					if (players[i].hasNpc == true) {
						t.getSummon().pickUpClean(t, players[i].summonId);
					}
					if (players[i].duelStatus == 5) {
						Client o1 = (Client) PlayerHandler.players[players[i].duelingWith];
						if (o1 != null) {
							o1.getDueling().duelVictory();
						}
					} else if (players[i].duelStatus <= 4
							&& players[i].duelStatus >= 1) {
						Client o1 = (Client) PlayerHandler.players[players[i].duelingWith];
						if (o1 != null) {
							o1.getDueling().declineDuel();
						}
					}

					Client o1 = (Client) PlayerHandler.players[i];
					if (PlayerSave.saveGame(o1)) {
						System.out.println("Game saved for player "
								+ players[i].playerName);
					} else {
						System.out.println("Could not save for "
								+ players[i].playerName);
					}
					removePlayer(players[i]);
					players[i] = null;
				} else {
					if (!players[i].initialized) {
						players[i].initialize();
						players[i].initialized = true;
					} else {
						players[i].update();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (updateRunning && !updateAnnounced) {
			updateAnnounced = true;
			Server.UpdateServer = true;
		}
		if (updateRunning
				&& System.currentTimeMillis() - updateStartTime > updateSeconds * 1000) {
			kickAllPlayers = true;
		}

		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (players[i] == null || !players[i].isActive) {
				continue;
			}
			try {
				players[i].clearUpdateFlags();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void updateNPC(Player plr, Stream str) {
		// synchronized(plr) {
		updateBlock.currentOffset = 0;

		str.createFrameVarSizeWord(65);
		str.initBitAccess();

		str.writeBits(8, plr.npcListSize);
		int size = plr.npcListSize;
		plr.npcListSize = 0;
		for (int i = 0; i < size; i++) {
			if (plr.RebuildNPCList == false
					&& plr.withinDistance(plr.npcList[i]) == true) {
				plr.npcList[i].updateNPCMovement(str);
				plr.npcList[i].appendNPCUpdateBlock(updateBlock);
				plr.npcList[plr.npcListSize++] = plr.npcList[i];
			} else {
				int id = plr.npcList[i].npcId;
				plr.npcInListBitmap[id >> 3] &= ~(1 << (id & 7));
				str.writeBits(1, 1);
				str.writeBits(2, 3);
			}
		}
		for (Npc i : NpcHandler.npcs) {
			if (i != null) {
				int id = i.npcId;
				if (plr.RebuildNPCList == false
						&& (plr.npcInListBitmap[id >> 3] & 1 << (id & 7)) != 0) {
				} else if (plr.withinDistance(i) == false) {
				} else {
					plr.addNewNPC(i, str, updateBlock);
				}
			}
		}

		plr.RebuildNPCList = false;

		if (updateBlock.currentOffset > 0) {
			str.writeBits(14, 16383);
			str.finishBitAccess();
			str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
		} else {
			str.finishBitAccess();
		}
		str.endFrameVarSizeWord();
	}

	private final Stream updateBlock = new Stream(
			new byte[Constants.BUFFER_SIZE]);

	public void updatePlayer(Player plr, Stream str) {
		// synchronized(plr) {
		updateBlock.currentOffset = 0;
		if (updateRunning && !updateAnnounced) {
			str.createFrame(114);
			str.writeWordBigEndian(updateSeconds * 50 / 30);
		}
		plr.updateThisPlayerMovement(str);
		boolean saveChatTextUpdate = plr.isChatTextUpdateRequired();
		plr.setChatTextUpdateRequired(false);
		plr.appendPlayerUpdateBlock(updateBlock);
		plr.setChatTextUpdateRequired(saveChatTextUpdate);
		str.writeBits(8, plr.playerListSize);
		int size = plr.playerListSize;
		if (size > 255) {
			size = 255;
		}
		plr.playerListSize = 0;
		for (int i = 0; i < size; i++) {
			if (!plr.didTeleport && !plr.playerList[i].didTeleport
					&& plr.withinDistance(plr.playerList[i])) {
				plr.playerList[i].updatePlayerMovement(str);
				plr.playerList[i].appendPlayerUpdateBlock(updateBlock);
				plr.playerList[plr.playerListSize++] = plr.playerList[i];
			} else {
				int id = plr.playerList[i].playerId;
				plr.playerInListBitmap[id >> 3] &= ~(1 << (id & 7));
				str.writeBits(1, 1);
				str.writeBits(2, 3);
			}
		}
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (players[i] == null || !players[i].isActive || players[i] == plr) {
				continue;
			}
			int id = players[i].playerId;
			if ((plr.playerInListBitmap[id >> 3] & 1 << (id & 7)) != 0) {
				continue;
			}
			if (!plr.withinDistance(players[i])) {
				continue;
			}
			plr.addNewPlayer(players[i], str, updateBlock);
		}
		if (updateBlock.currentOffset > 0) {
			str.writeBits(11, 2047);
			str.finishBitAccess();
			str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
		} else {
			str.finishBitAccess();
		}

		str.endFrameVarSizeWord();
	}

	private void removePlayer(Player plr) {
		if (plr.privateChat != 2) {
			for (int i = 1; i < PlayerHandler.players.length; i++) {
				if (players[i] == null || players[i].isActive == false) {
					continue;
				}
				Client o = (Client) PlayerHandler.players[i];
				if (o != null) {
					o.getPlayerAssistant().updatePM(plr.playerId, 0);
				}
			}
		}
		plr.destruct();
	}

}
