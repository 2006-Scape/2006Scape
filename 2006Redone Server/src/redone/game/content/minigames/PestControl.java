package redone.game.content.minigames;

import java.util.HashMap;

import redone.Server;
import redone.game.content.combat.prayer.PrayerDrain;
import redone.game.npcs.Npc;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.util.Misc;

/**
 * @author Harlan Credits to Sanity
 */

public class PestControl {

	/**
	 * /** how long before were put into the game from lobby
	 */
	private static final int WAIT_TIMER = 60;
	/**
	 * How many players we need to start a game
	 */
	private final static int PLAYERS_REQUIRED = 1;
	/**
	 * Hashmap for the players in lobby
	 */
	public static HashMap<Client, Integer> waitingBoat = new HashMap<Client, Integer>();
	private static HashMap<Client, Integer> gamePlayers = new HashMap<Client, Integer>();

	private static int gameTimer = -1;
	private static int waitTimer = 60;
	public static boolean gameStarted = false;
	private int properTimer = 0;
	public static int KNIGHTS_HEALTH = -1;

	/**
	 * Array used for storing the portals health
	 */
	public static int[] portalHealth = { 200, 200, 200, 200 };
	public static int[] portals = { 3777, 3778, 3779, 3780 };
	/**
	 * array used for storing the npcs used in the minigame
	 * 
	 * @order npcId, xSpawn, ySpawn, health
	 */
	
	public int shifter = 3732 + Misc.random(9);
	public int brawler = 3772 + Misc.random(4);
	public int defiler = 3762 + Misc.random(9);
	public int ravager = 3742 + Misc.random(4);
	public int torcher = 3752 + Misc.random(7);
	public int splater = 3727 + Misc.random(4);

	private final int[][] pcNPCData = { { 3777, 2628, 2591 }, // portal
			{ 3778, 2680, 2588 }, // portal
			{ 3779, 2669, 2570 }, // portal
			{ 3780, 2645, 2569 }, // portal
			{ 3782, 2656, 2592 }, // knight
	};
	
	private final int[][] voidMonsterData = {
			{ shifter, 2660 + Misc.random(4), 2592 + Misc.random(4) },
			{ brawler, 2663 + Misc.random(4), 2575 + Misc.random(4) },
			{ defiler, 2656 + Misc.random(4), 2572 + Misc.random(4) },
			{ ravager, 2664 + Misc.random(4), 2574 + Misc.random(4) },
			{ torcher, 2656 + Misc.random(4), 2595 + Misc.random(4) },
			{ ravager, 2634 + Misc.random(4), 2596 + Misc.random(4) },
			{ brawler, 2638 + Misc.random(4), 2588 + Misc.random(4) },
			{ shifter, 2637 + Misc.random(4), 2598 + Misc.random(4) },
			{ ravager, 2677 + Misc.random(4), 2579 + Misc.random(4) },
			{ defiler, 2673 + Misc.random(4), 2584 + Misc.random(4) },
			{ defiler, 2673 + Misc.random(4), 2584 + Misc.random(4) },
			{ defiler, 2675 + Misc.random(4), 2591 + Misc.random(4) },
			{ splater, 2644 + Misc.random(4), 2575 + Misc.random(4) },
			{ splater, 2633 + Misc.random(4), 2595 + Misc.random(4) }};

	public void process() {
		try {
			if (properTimer > 0) {
				properTimer--;
				return;
			} else {
				properTimer = 1;
			}
			waitBoat();
			if (waitTimer > 0) {
				waitTimer--;
			} else if (waitTimer == 0) {
				startGame();
			}
			if (KNIGHTS_HEALTH == 0) {
				endGame(false);
			}
			if (gameStarted && playersInGame() < 1) {
				endGame(false);
			}
			if (gameTimer > 0 && gameStarted) {
				gameTimer--;
				setGameInterface();
				if (allPortalsDead() || allPortalsDead3()) {
					endGame(true);
				}
			} else if (gameTimer <= 0 && gameStarted) {
				endGame(false);
			}
		} catch (RuntimeException e) {
			System.out.println("Failed to set process");
			e.printStackTrace();
		}
	}

	/**
	 * Method we use for removing a player from the pc game
	 * 
	 * @param player
	 *            The Player.
	 */
	public static void removePlayerGame(Client player) {
		if (gamePlayers.containsKey(player)) {
			player.getPlayerAssistant().movePlayer(2657, 2639, 0);
			gamePlayers.remove(player);
		}
	}

	private static void waitBoat() {
		for (final Client c : waitingBoat.keySet()) {
			if (c != null) {
				if (gameStarted && isInPcBoat(c)) {
					c.getActionSender().sendMessage("Next Departure: " + (waitTimer + gameTimer) / 60 + " minutes");
				} else {
					c.getActionSender().sendMessage("Next Departure: " + waitTimer + " seconds.");
				}
			}
		}
	}

	public static void setGameInterface() {
		for (final Client player : gamePlayers.keySet()) {
			if (player != null) {
				if (gameTimer > 60) {
					player.getActionSender().sendMessage("Time remaining: " + gameTimer / 60 + " minutes");
				} else if (gameTimer < 60) {
					player.getActionSender().sendMessage("Time remaining: " + gameTimer + " seconds");
				}
				player.getActionSender().sendMessage("The knights current health is " + KNIGHTS_HEALTH + ".");
				player.getActionSender().sendMessage("Your current pc damage is " + player.pcDamage + ".");
			}
		}
	}

	/*
	 * private void setBoatInterface() { try { for (Client c :
	 * waitingBoat.keySet()) { if (c != null) { try { if (gameStarted) {
	 * c.getPlayerAssistant().sendString("Next Departure: " + (waitTimer +
	 * gameTimer)/60 + " minutes", 21120); } else {
	 * c.getPlayerAssistant().sendString("Next Departure: " + waitTimer + "",
	 * 21120); } c.getPlayerAssistant().sendString("Players Ready: " +
	 * playersInBoat() + "", 21121); c.getPlayerAssistant().sendString("(Need "
	 * + PLAYERS_REQUIRED + " to 25 players)", 21122);
	 * c.getPlayerAssistant().sendString("Points: " + c.pcPoints + "", 21123);
	 * switch (waitTimer) { case 60: c.getPacketDispatcher
	 * ().sendMessage("Next game will start in: 60 seconds."); break; case 30:
	 * c.
	 * getPacketDispatcher().sendMessage("Next game will start in: 30 seconds."
	 * ); break; } } catch (RuntimeException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } } } } catch (RuntimeException e) {
	 * System.out.println("Failed to set interfaces"); e.printStackTrace(); } }
	 */

	/*
	 * private void setGameInterface() { for (Client player :
	 * gamePlayers.keySet()) { if (player != null) { for (int i = 0; i <
	 * portalHealth.length; i++) { if (portalHealth[i] > 0) {
	 * player.getPlayerAssistant().sendString("" + portalHealth[i] + "", 21111 +
	 * i); } else player.getPlayerAssistant().sendString("Dead", 21111 + i); }
	 * player.getPlayerAssistant().sendString("" + KNIGHTS_HEALTH, 21115);
	 * player.getPlayerAssistant().sendString("" + player.pcDamage, 21116); if
	 * (gameTimer > 60) {
	 * player.getPlayerAssistant().sendString("Time remaining: " +
	 * (gameTimer/60) + " minutes", 21117); } else {
	 * player.getPlayerAssistant().sendString("Time remaining: " + gameTimer +
	 * " seconds", 21117); } } } }
	 */

	/***
	 * Moving players to arena if there's enough players
	 */
	private void startGame() {
		if (playersInBoat() < PLAYERS_REQUIRED) {
			waitTimer = WAIT_TIMER;
			return;
		}
		for (int i = 0; i < portalHealth.length; i++) {
			portalHealth[i] = 200;
		}
		gameStarted = true;
		gameTimer = 400;
		KNIGHTS_HEALTH = 250;
		waitTimer = -1;
		spawnNPC();
		setGameInterface();
		for (Client player : waitingBoat.keySet()) {
			int team = waitingBoat.get(player);
			if (player == null) {
				continue;
			}
			player.getPlayerAssistant().movePlayer(2656 + Misc.random3(3),
					2614 - Misc.random3(4), 0);
			player.getDialogueHandler().sendDialogues(599, 3790);
			player.getActionSender().sendMessage(
					"The Pest Control Game has begun!");
			gamePlayers.put(player, team);
		}

		waitingBoat.clear();
	}

	/**
	 * Checks how many players are in the waiting lobby
	 * 
	 * @return players waiting
	 */
	private static int playersInBoat() {
		int players = 0;
		for (Client player : waitingBoat.keySet()) {
			if (player != null) {
				players++;
			}
			if (player == null) {
				players--;
			}
		}
		return players;
	}

	/**
	 * Checks how many players are in the game
	 * 
	 * @return players in the game
	 */
	private int playersInGame() {
		int players = 0;
		for (Client player : gamePlayers.keySet()) {
			if (player != null) {
				players++;
			}
			if (player == null) {
				players--;
			}
		}
		return players;
	}

	/**
	 * Ends the game
	 * 
	 * @param won
	 *            Did you win?
	 */
	private void endGame(boolean won) {
		for (Client player : gamePlayers.keySet()) {
			if (player == null) {
				continue;
			}
			player.getPlayerAssistant().movePlayer(2657, 2639, 0);
			if (won && player.pcDamage > 50) {
				player.getDialogueHandler().sendDialogues(598, 3790);
				int POINT_REWARD = 4;
				player.getActionSender().sendMessage(
						"You have won the pest control game and have been awarded "
								+ POINT_REWARD + " Pest Control points.");
				player.pcPoints += POINT_REWARD;
				player.getItemAssistant().addItem(995, player.combatLevel * 10);
			} else if (won) {
				player.getDialogueHandler().sendDialogues(596, 3790);
				int POINT_REWARD2 = 2;
				player.pcPoints += POINT_REWARD2;
				player.getActionSender().sendMessage(
						"The void knights notice your lack of zeal. You only gain "
								+ POINT_REWARD2 + " points.");
			} else {
				player.getDialogueHandler().sendDialogues(597, 3790);
				player.getActionSender()
						.sendMessage(
								"You failed to kill all the portals in 3 minutes and have not been awarded points.");
			}
		}
		cleanUpPlayer();
		cleanUp();
	}

	/**
	 * Resets the game variables and map
	 */
	private void cleanUp() {
		gameTimer = -1;
		KNIGHTS_HEALTH = -1;
		waitTimer = WAIT_TIMER;
		gameStarted = false;
		gamePlayers.clear();
		/*
		 * Removes the npcs from the game if any left over for whatever reason
		 */
		for (int[] aPcNPCData : pcNPCData) {
			for (int j = 0; j < NpcHandler.npcs.length; j++) {
				if (NpcHandler.npcs[j] != null) {
					if (NpcHandler.npcs[j].npcType == aPcNPCData[0]) {
						NpcHandler.npcs[j] = null;
					}
				}
			}
		}
		for (int[] aPcNPCData : voidMonsterData) {
			for (int j = 0; j < NpcHandler.npcs.length; j++) {
				if (NpcHandler.npcs[j] != null) {
					if (NpcHandler.npcs[j].npcType == aPcNPCData[0]) {
						NpcHandler.npcs[j] = null;
					}
				}
			}
		}
	}

	/**
	 * Cleans the player of any damage, loss they may of received
	 */
	private void cleanUpPlayer() {
		for (Client player : gamePlayers.keySet()) {
			player.poisonDamage = 0;
			PrayerDrain.resetPrayers(player);
			for (int i = 0; i < 24; i++) {
				player.playerLevel[i] = player.getPlayerAssistant()
						.getLevelForXP(player.playerXP[i]);
				player.getPlayerAssistant().refreshSkill(i);
			}
			player.specAmount = 10;
			player.pcDamage = 0;
			player.getItemAssistant().addSpecialBar(
					player.playerEquipment[player.playerWeapon]);
		}
	}

	/**
	 * Checks if the portals are dead
	 * 
	 * @return players dead
	 */
	private static boolean allPortalsDead() {
		int count = 0;
		for (int aPortalHealth : portalHealth) {
			if (aPortalHealth <= 0) {
				count++;
				// System.out.println("Portal Health++" + count);
			}
		}
		return count >= 4;
	}

	public boolean allPortalsDead3() {
		int count = 0;
		for (Npc npc : NpcHandler.npcs) {
			if (npc != null) {
				if (npc.npcType > 3777 && npc.npcType < 3780) {
					if (npc.needRespawn) {
						count++;
					}
				}
			}
		}
		return count >= 4;
	}

	/**
	 * Moves a player out of the waiting boat
	 * 
	 * @param c
	 *            Client c
	 */
	public static void leaveWaitingBoat(Client c) {
		if (waitingBoat.containsKey(c)) {
			waitingBoat.remove(c);
			c.getPlayerAssistant().movePlayer(2657, 2639, 0);
		}
	}

	/**
	 * Moves a player into the hash and into the lobby
	 * 
	 * @param player
	 *            The player
	 */
	public static void addToWaitRoom(Client player) {
		if (player != null && player.combatLevel > 39) {
			waitingBoat.put(player, 1);
			player.getActionSender().sendMessage(
					"You have joined the Pest Control boat.");
			player.getActionSender().sendMessage(
					"You currently have " + player.pcPoints
							+ " Pest Control Points.");
			player.getActionSender().sendMessage(
					"There are currently " + playersInBoat()
							+ " players ready in the boat.");
			player.getActionSender().sendMessage(
					"Players needed: " + PLAYERS_REQUIRED + " to 25 players.");
			player.getPlayerAssistant().movePlayer(2661, 2639, 0);
			waitBoat();
		} else if (player.combatLevel < 40) {
			player.getActionSender().sendMessage("You need 40 combat to play pest control.");
		}
	}

	/**
	 * Checks if a player is in the game
	 * 
	 * @param player
	 *            The player
	 * @return return
	 */
	public static boolean isInGame(Client player) {
		return gamePlayers.containsKey(player);
	}

	/**
	 * Checks if a player is in the pc boat (lobby)
	 * 
	 * @param player
	 *            The player
	 * @return return
	 */
	public static boolean isInPcBoat(Client player) {
		return waitingBoat.containsKey(player);
	}

	public static boolean npcIsPCMonster(int npcType) {
		return npcType >= 3727 && npcType <= 3776;
	}

	private void spawnNPC() {
		//npcid, npcx, npcy, heightlevel, walking type, hp, att, def
		for (int[] aPcNPCData : pcNPCData) {
			Server.npcHandler.spawnNpc2(aPcNPCData[0], aPcNPCData[1], aPcNPCData[2], 0, 0, 200, 0, 0, playersInGame() * 200);
		}
		for (int[] voidMonsters : voidMonsterData) {
			//Server.npcHandler.spawnNpc2(voidMonsters[0], voidMonsters[1], voidMonsters[2], 0, 1, voidMonsters[NpcHandler.getNpcListHP(voidMonsters[0])], NpcHandler.getNpcListCombat(voidMonsters[0])/10, NpcHandler.getNpcListCombat(voidMonsters[0]), playersInGame() * 200);
			Server.npcHandler.spawnNpc2(voidMonsters[0], voidMonsters[1], voidMonsters[2], 0, 1, 500, 20, 200, 25);
		}
	}
}
