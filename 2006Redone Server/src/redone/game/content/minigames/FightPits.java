package redone.game.content.minigames;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import redone.game.players.Client;
import redone.util.Misc;

/**
 * @author ArrowzFtw
 */
public class FightPits {

	/**
	 * @note States of minigames
	 */
	private static final String PLAYING = "PLAYING";
	private static final String WAITING = "WAITING";
	/**
	 * @note Current fight pits champion
	 */
	private static String pitsChampion = "None";
	/**
	 * @note Countdown for game to start
	 */
	private static int gameStartTimer = 80;
	/**
	 * @note Elapsed Game start time
	 */
	private static int elapsedGameTime = 0;
	private static final int END_GAME_TIME = 400;
	/*
	 * @note Game started or not?
	 */
	private static boolean gameStarted = false;
	/**
	 * @note Stores player and State
	 */
	private static Map<Client, String> playerMap = Collections
			.synchronizedMap(new HashMap<Client, String>());

	/**
	 * @note Where to spawn when pits game starts
	 */
	private static final int MINIGAME_START_POINT_X = 2392;
	private static final int MINIGAME_START_POINT_Y = 5139;
	/**
	 * @note Exit game area
	 */
	private static final int EXIT_GAME_X = 2399;
	private static final int EXIT_GAME_Y = 5169;
	/**
	 * @note Exit waiting room
	 */
	public static final int EXIT_WAITING_X = 2399;
	public static final int EXIT_WAITING_Y = 5177;
	/**
	 * @note Waiting room coordinates
	 */
	private static final int WAITING_ROOM_X = 2399;
	private static final int WAITING_ROOM_Y = 5175;

	/**
	 * @return HashMap Value
	 */
	public static String getState(Client c) {
		return playerMap.get(c);
	}

	private static final int TOKKUL_ID = 6529;

	/**
	 * @note Adds player to waiting room.
	 */
	public static void addPlayer(Client c) {
		playerMap.put(c, WAITING);
		c.getPlayerAssistant().movePlayer(WAITING_ROOM_X, WAITING_ROOM_Y, 0);
	}

	/**
	 * @note Starts the game and moves players to arena
	 */
	private static void enterGame(Client c) {
		playerMap.put(c, PLAYING);
		int teleportToX = MINIGAME_START_POINT_X + Misc.random(12);
		int teleportToY = MINIGAME_START_POINT_Y + Misc.random(12);
		c.getPlayerAssistant().movePlayer(teleportToX, teleportToY, 0);
		c.inPits = true;
		// c.getPA().showOption(3, 0, "Attack", 1);
		// c.getPA().showOption(3, 0, "Null", 1);
	}

	/**
	 * @note Removes player from pits if there in waiting or in game
	 */
	public static void removePlayer(Client c, boolean forceRemove) {
		c.inPits = false;
		if (forceRemove) {
			c.getPlayerAssistant()
					.movePlayer(EXIT_WAITING_X, EXIT_WAITING_Y, 0);
			playerMap.remove(c);
			return;
		}
		String state = playerMap.get(c);
		if (state == null) {
			c.getPlayerAssistant()
					.movePlayer(EXIT_WAITING_X, EXIT_WAITING_Y, 0);
			return;
		}

		if (state.equals(PLAYING)) {
			if (getListCount(PLAYING) - 1 == 0 && !forceRemove) {
				pitsChampion = c.playerName;
				c.headIcon = 21;
				c.updateRequired = true;
				c.getItemAssistant()
						.addItem(TOKKUL_ID, 1500 + Misc.random(500));

			}
			c.getPlayerAssistant().movePlayer(EXIT_GAME_X, EXIT_GAME_Y, 0);
		} else if (state.equals(WAITING)) {
			c.getPlayerAssistant()
					.movePlayer(EXIT_WAITING_X, EXIT_WAITING_Y, 0);
			c.getPlayerAssistant().walkableInterface(-1);
		}
		playerMap.remove(c);

		if (state.equals(PLAYING)) {
			if (!forceRemove) {
				playerMap.put(c, WAITING);
			}
		}
	}

	/**
	 * @return Players playing fight pits
	 */
	public static int getListCount(String state) {
		int count = 0;
		for (String s : playerMap.values()) {
			if (state == s) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @note Updates players
	 */
	private static void update() {
		for (Client c : playerMap.keySet()) {
			String status = playerMap.get(c);
			@SuppressWarnings("unused")
			boolean updated = status == WAITING ? updateWaitingRoom(c)
					: updateGame(c);
		}
	}

	/**
	 * @note Updates waiting room interfaces etc.
	 */
	public static boolean updateWaitingRoom(Client c) {
		c.getPlayerAssistant().sendFrame126(
				"Next Game Begins In : " + gameStartTimer, 2805);
		c.getPlayerAssistant().sendFrame126(
				"Champion: JalYt-Ket-" + pitsChampion, 2806);
		c.getPlayerAssistant().sendConfig(560, 1);
		c.getPlayerAssistant().walkableInterface(2804);
		return true;
	}

	/**
	 * @note Updates players in game interfaces etc.
	 */
	public static boolean updateGame(Client c) {
		c.getPlayerAssistant().sendFrame126(
				"Foes Remaining: " + getListCount(PLAYING), 2805);
		c.getPlayerAssistant().sendFrame126(
				"Champion: JalYt-Ket-" + pitsChampion, 2806);
		c.getPlayerAssistant().sendConfig(560, 1);
		c.getPlayerAssistant().walkableInterface(2804);
		return true;
	}

	/**
	 * @note Handles death and respawn rubbish.
	 */
	public static void handleDeath(Client c) {
		removePlayer(c, true);
	}

	/*
	 * @process 600ms Tick
	 */
	public static void process() {
		update();
		if (!gameStarted) {
			if (gameStartTimer > 0) {
				gameStartTimer--;
			} else if (gameStartTimer == 0) {
				if (getListCount(WAITING) > 4) {
					beginGame();
				}
				gameStartTimer = 80;
			}
		}
		if (gameStarted) {
			elapsedGameTime++;
			if (elapsedGameTime == END_GAME_TIME) {
				endGame();
				elapsedGameTime = 0;
				gameStarted = false;
				gameStartTimer = 80;
			}
		}
	}

	/**
	 * @note Starts game for the players in waiting room
	 */
	private static void beginGame() {
		for (Client c : playerMap.keySet()) {
			enterGame(c);
		}
	}

	/**
	 * @note Ends game and returns player to their normal spot.
	 */
	private static void endGame() {
		for (Client c : playerMap.keySet()) {
			removePlayer(c, true);
		}
	}
}
