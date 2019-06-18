package redone.game.content.minigames.castlewars;

import java.util.HashMap;
import java.util.Iterator;

import redone.game.items.ItemAssistant;
import redone.game.players.Client;
import redone.game.players.Player;
import redone.util.Misc;

/**
 * @Author Sanity for base
 * @Author Satan666
 * @Author Andrew fixing it up
 */

public class CastleWars {

	/*
	 * Game timers.
	 */
	private static final int GAME_TIMER = 200; // 1500 * 600 = 900000ms = 15
												// minutes
	private static final int GAME_START_TIMER = 30;
	/*
	 * Hashmap for the waitingroom players
	 */
	private static HashMap<Client, Integer> waitingRoom = new HashMap<Client, Integer>();
	/*
	 * hashmap for the gameRoom players
	 */
	private static HashMap<Client, Integer> gameRoom = new HashMap<Client, Integer>();
	/*
	 * The coordinates for the waitingRoom both sara/zammy
	 */
	private static final int[][] WAIT_ROOM = { { 2377, 9485 }, // sara
			{ 2421, 9524 } // zammy
	};
	/*
	 * The coordinates for the gameRoom both sara/zammy
	 */
	private static final int[][] GAME_ROOM = { { 2426, 3076 }, // sara
			{ 2372, 3131 } // zammy
	};
	private static final int[][] FLAG_STANDS = { { 2429, 3074 }, // sara
																	// {X-Coord,
																	// Y-Coord)
			{ 2370, 3133 } // zammy
	};
	/*
	 * Scores for saradomin and zamorak!
	 */
	private static int[] scores = { 0, 0 };
	/*
	 * Booleans to check if a team's flag is safe
	 */
	private static int zammyFlag = 0;
	private static int saraFlag = 0;
	/*
	 * Zamorak and saradomin banner/capes item ID's
	 */
	public static final int SARA_BANNER = 4037;
	public static final int ZAMMY_BANNER = 4039;
	public static final int SARA_CAPE = 4041;
	public static final int ZAMMY_CAPE = 4042;
	public static final int SARA_HOOD = 4513;
	public static final int ZAMMY_HOOD = 4515;
	
	public static boolean deleteCastleWarsItems(Client c, int itemId) {
		int[] items = { 4049, 4045, 4053, 4042, 4041, 4037, 4039, 4043 };
		for (int item : items) {
			int amount = c.getItemAssistant().getItemCount(item);
			if (itemId == item && !isInCw(c)) {
				c.getItemAssistant().deleteItem(item, amount);
				c.getActionSender().sendMessage("You shouldn't have " + ItemAssistant.getItemName(itemId) + " outside of castlewars!");
				return false;
			}
		}
		return true;
	}


	private static final int[][] COLLAPSE_ROCKS = { // collapsing rocks coords
	{ 2399, 2402, 9511, 9514 }, // north X Y coords sara 0
			{ 2390, 2393, 9500, 9503 }, // east X Y coords sara 1
			{ 2400, 2403, 9493, 9496 }, // south X Y coords zammy 2
			{ 2408, 2411, 9502, 9505 } // west X Y coords zammy 3
	};

	/*
     *
     */
	private static int properTimer = 0;
	private static int timeRemaining = -1;
	private static int gameStartTimer = GAME_START_TIMER;
	private static boolean gameStarted = false;

	public static void resetPlayer(Client c) {
		c.getPlayerAssistant().movePlayer(2440 + Misc.random(3), 3089 - Misc.random(3), 0);
		deleteGameItems(c);
		c.getActionSender().sendMessage("Cheating will not be tolerated.");
	}

	public static void collapseCave(int cave) {
		Iterator<Client> iterator = gameRoom.keySet().iterator();
		while (iterator.hasNext()) {
			Client teamPlayer = iterator.next();
			if (teamPlayer.absX > COLLAPSE_ROCKS[cave][0]
					&& teamPlayer.absX < COLLAPSE_ROCKS[cave][1]
					&& teamPlayer.absY > COLLAPSE_ROCKS[cave][2]
					&& teamPlayer.absY < COLLAPSE_ROCKS[cave][3]) {
				int dmg = teamPlayer.playerLevel[3];
				teamPlayer.handleHitMask(dmg);
				teamPlayer.dealDamage(99);// 99 damage?
			}
		}
	}

	/**
	 * Method we use to add someone to the waitinroom in a different method,
	 * this will filter out some error messages
	 * 
	 * @param player
	 *            the player that wants to join
	 * @param team
	 *            the team!
	 */
	public static void addToWaitRoom(Client player, int team) {
		if (player == null) {
			return;
		} else if (gameStarted == true) {
			player.getActionSender()
					.sendMessage(
							"There's already a Castle Wars going. Please wait a few minutes before trying again.");
			return;
		} else if (player.playerEquipment[player.playerHat] > 0 || player.playerEquipment[player.playerCape] > 0 || player.getItemAssistant().playerHasItem(player.playerCape) || player.getItemAssistant().playerHasItem(player.playerHat)) {
			player.getActionSender().sendMessage("You may not bring capes or helmets in castle wars.");
			return;
		}
		toWaitingRoom(player, team);
	}

	/**
	 * Method we use to transfer to player from the outside to the waitingroom
	 * (:
	 * 
	 * @param player
	 *            the player that wants to join
	 * @param team
	 *            team he wants to be in - team = 1 (saradomin), team = 2
	 *            (zamorak), team = 3 (random)
	 */
	public static void toWaitingRoom(Client player, int team) {
		int[] food = { 391, 385, 379, 333, 329, 373, 361, 7946, 397, 1891, 365,
				339, 1942, 6701, 6705, 7056, 7054, 7058, 7060, 315, 347, 325,
				1897, 2289, 2293, 2297, 2301, 2309, 2323, 2325, 2327, 351,
				6703, 1963, 6883, 2108, 2118, 2116, 15272, 1961, 1959, 1989,
				1935, 1915, 1978, 712, 1907, 2120, 2114, 1973, 1993, 355, 
				3144, 1917, 1909, 2955, 1969, 6701, 1893, 1895, 1965, 1899,
				1901, 2291, 2295, 2297, 2299, 2301, 2303, 1883, 6883, 2108, 2118,
				2116, 7928, 7929, 7930, 7931, 7932, 7933, 10476, 2130, 2003, 2011,
				4049};
		for (int element : food) {
			if (player.getItemAssistant().playerHasItem(element)) {
				player.getActionSender().sendMessage("You may not bring your own consumables inside of Castle Wars.");
				return;
			}
		}
		if (team == 1) {
			if (getSaraPlayers() > getZammyPlayers() && getSaraPlayers() > 0) {
				player.getActionSender().sendMessage(
						"The saradomin team is full, try again later!");
				return;
			}
			if (getZammyPlayers() >= getSaraPlayers() || getSaraPlayers() == 0) {
				player.getActionSender().sendMessage(
						"You have been added to the Saradomin team.");
				player.getActionSender().sendMessage(
						"Next Game Begins In: "
								+ (gameStartTimer * 3 + timeRemaining * 3)
								+ " seconds.");
				addCapes(player, SARA_CAPE);
				waitingRoom.put(player, team);
				player.getPlayerAssistant().movePlayer(
						WAIT_ROOM[team - 1][0] + Misc.random(5),
						WAIT_ROOM[team - 1][1] + Misc.random(5), 0);
			}
		} else if (team == 2) {
			if (getZammyPlayers() > getSaraPlayers() && getZammyPlayers() > 0) {
				player.getActionSender().sendMessage(
						"The zamorak team is full, try again later!");
				return;
			}
			if (getZammyPlayers() <= getSaraPlayers() || getZammyPlayers() == 0) {
				player.getActionSender()
						.sendMessage(
								"Random team: You have been added to the Zamorak team.");
				player.getActionSender().sendMessage(
						"Next Game Begins In: "
								+ (gameStartTimer * 3 + timeRemaining * 3)
								+ " seconds.");
				addCapes(player, ZAMMY_CAPE);
				waitingRoom.put(player, team);
				player.getPlayerAssistant().movePlayer(
						WAIT_ROOM[team - 1][0] + Misc.random(5),
						WAIT_ROOM[team - 1][1] + Misc.random(5), 0);
			}
		} else if (team == 3) {
			toWaitingRoom(player, getZammyPlayers() > getSaraPlayers() ? 1 : 2);
			return;
		}
	}

	/**
	 * Method to add score to scoring team
	 * 
	 * @param player
	 *            the player who scored
	 * @param banner
	 *            banner id!
	 */
	public static void returnFlag(Client player, int wearItem) {
		if (player == null) {
			return;
		}
		if (wearItem != SARA_BANNER && wearItem != ZAMMY_BANNER) {
			return;
		}
		int team = gameRoom.get(player);
		int objectId = -1;
		int objectTeam = -1;
		switch (team) {
		case 1:
			if (wearItem == SARA_BANNER) {
				setSaraFlag(0);
				objectId = 4902;
				objectTeam = 0;
				player.getActionSender().sendMessage(
						"Returned the sara flag!");
			} else {
				objectId = 4903;
				objectTeam = 1;
				setZammyFlag(0);
				scores[0]++; // upping the score of a team; team 0 = sara,
								// team 1 = zammy
				player.getActionSender().sendMessage(
						"The team of Saradomin scores 1 point!");
			}
			break;
		case 2:
			if (wearItem == ZAMMY_BANNER) {
				setZammyFlag(0);
				objectId = 4903;
				objectTeam = 1;
				player.getActionSender().sendMessage(
						"Returned the zammy flag!");
			} else {
				objectId = 4902;
				objectTeam = 0;
				setSaraFlag(0);
				scores[1]++; // upping the score of a team; team 0 = sara,
								// team 1 = zammy
				player.getActionSender().sendMessage(
						"The team of Zamorak scores 1 point!");
				zammyFlag = 0;
			}
			break;
		}
		changeFlagObject(objectId, objectTeam);
		player.getActionSender().createPlayerHints(10, -1);
		player.playerEquipment[player.playerWeapon] = -1;
		player.playerEquipmentN[player.playerWeapon] = 0;
		player.getItemAssistant().updateSlot(3);
		player.appearanceUpdateRequired = true;
		player.updateRequired = true;
		player.getItemAssistant().resetItems(3214);
	}

	/**
	 * Method that will capture a flag when being taken by the enemy team!
	 * 
	 * @param player
	 *            the player who returned the flag
	 * @param team
	 */
	public static void captureFlag(Client player) {
		if (player.playerEquipment[player.playerWeapon] > 0) {
			player.getActionSender()
					.sendMessage(
							"Please remove your weapon before attempting to get the flag again!");
			return;
		}
		int team = gameRoom.get(player);
		if (team == 2 && saraFlag == 0) { // sara flag
			setSaraFlag(1);
			addFlag(player, SARA_BANNER);
			createHintIcon(player, 1);
			changeFlagObject(4377, 0);
		}
		if (team == 1 && zammyFlag == 0) {
			setZammyFlag(1);
			addFlag(player, ZAMMY_BANNER);
			createHintIcon(player, 2);
			changeFlagObject(4378, 1);
		}
	}

	/**
	 * Method that will add the flag to a player's weapon slot
	 * 
	 * @param player
	 *            the player who's getting the flag
	 * @param flagId
	 *            the banner id.
	 */
	public static void addFlag(Client player, int flagId) {
		player.playerEquipment[player.playerWeapon] = flagId;
		player.playerEquipmentN[player.playerWeapon] = 1;
		player.getItemAssistant().updateSlot(player.playerWeapon);
		player.appearanceUpdateRequired = true;
		player.updateRequired = true;
	}

	/**
	 * Method we use to handle the flag dropping
	 * 
	 * @param player
	 *            the player who dropped the flag/died
	 * @param flagId
	 *            the flag item ID
	 */
	public static void dropFlag(Client player, int flagId) {
		int object = -1;
		switch (flagId) {
		case SARA_BANNER: // sara
			setSaraFlag(2);
			object = 4900;
			createFlagHintIcon(player.getX(), player.getY());
			break;
		case ZAMMY_BANNER: // zammy
			setZammyFlag(2);
			object = 4901;
			createFlagHintIcon(player.getX(), player.getY());
			break;
		}

		player.playerEquipment[player.playerWeapon] = -1;
		player.playerEquipmentN[player.playerWeapon] = 0;
		player.getItemAssistant().updateSlot(player.playerWeapon);
		player.appearanceUpdateRequired = true;
		player.updateRequired = true;
		for (Client teamPlayer : gameRoom.keySet()) {
			teamPlayer.getActionSender().object(object, player.getX(),
					player.getY(), 0, 10);
		}
	}

	/**
	 * Method we use to pickup the flag when it was dropped/lost
	 * 
	 * @param Player
	 *            the player who's picking it up
	 * @param objectId
	 *            the flag object id.
	 */
	public static void pickupFlag(Client player) {
		switch (player.objectId) {
		case 4900: // sara
			if (player.playerEquipment[player.playerWeapon] > 0) {
				player.getActionSender()
						.sendMessage(
								"Please remove your weapon before attempting to get the flag again!");
				return;
			}
			if (saraFlag != 2) {
				return;
			}
			setSaraFlag(1);
			addFlag(player, 4037);
			break;
		case 4901: // zammy
			if (player.playerEquipment[player.playerWeapon] > 0) {
				player.getActionSender()
						.sendMessage(
								"Please remove your weapon before attempting to get the flag again!");
				return;
			}
			if (zammyFlag != 2) {
				return;
			}
			setZammyFlag(1);
			addFlag(player, 4039);
			break;
		}
		createHintIcon(player, gameRoom.get(player) == 1 ? 2 : 1);
		Iterator<Client> iterator = gameRoom.keySet().iterator();
		while (iterator.hasNext()) {
			Client teamPlayer = iterator.next();
			teamPlayer.getActionSender().createObjectHints(player.objectX,
					player.objectY, 170, -1);
			teamPlayer.getActionSender().object(-1, player.objectX,
					player.objectY, 0, 10);
		}
		return;
	}

	/**
	 * Hint icons appear to your team when a enemy steals flag
	 * 
	 * @param player
	 *            the player who took the flag
	 * @param t
	 *            team of the opponent team. (:
	 */
	public static void createHintIcon(Client player, int t) {
		Iterator<Client> iterator = gameRoom.keySet().iterator();
		while (iterator.hasNext()) {
			Client teamPlayer = iterator.next();
			// System.out.println(teamPlayer.playerName + " => Team => " +
			// gameRoom.get(teamPlayer));
			// System.out.println("Desired team = " + t);
			teamPlayer.getActionSender().createPlayerHints(10, -1);
			if (gameRoom.get(teamPlayer) == t) {
				// System.out.println("Created hint icons for playername " +
				// teamPlayer.playerName + " and team number: " + t);
				teamPlayer.getActionSender().createPlayerHints(10,
						player.playerId);
				teamPlayer.getPlayerAssistant().requestUpdates();
			}
		}
	}

	/**
	 * Hint icons appear to your team when a enemy steals flag
	 * 
	 * @param player
	 *            the player who took the flag
	 * @param t
	 *            team of the opponent team. (:
	 */
	public static void createFlagHintIcon(int x, int y) {
		Iterator<Client> iterator = gameRoom.keySet().iterator();
		while (iterator.hasNext()) {
			Client teamPlayer = iterator.next();
			teamPlayer.getActionSender().createObjectHints(x, y, 170, 2);
		}
	}

	/**
	 * This method is used to get the teamNumber of a certain player
	 * 
	 * @param player
	 * @return
	 */
	public static int getTeamNumber(Client player) {
		if (player == null) {
			return -1;
		}
		if (gameRoom.containsKey(player)) {
			return gameRoom.get(player);
		}
		return -1;
	}

	/**
	 * The leaving method will be used on click object or log out
	 * 
	 * @param player
	 *            player who wants to leave
	 */
	public static void leaveWaitingRoom(Client player) {
		if (player == null) {
			System.out.println("player is null");
			return;
		}
		if (waitingRoom.containsKey(player)) {
			waitingRoom.remove(player);
			player.getActionSender().createPlayerHints(10, -1);
			player.getActionSender().sendMessage("You left your team!");
			deleteGameItems(player);
			player.getPlayerAssistant().movePlayer(2439 + Misc.random(4),
					3085 + Misc.random(5), 0);
			return;
		}
		player.getPlayerAssistant().movePlayer(2439 + Misc.random(4),
				3085 + Misc.random(5), 0);
		// System.out.println("Waiting room map does not contain " +
		// player.playerName);
	}

	public static void process() {
		if (properTimer > 0) {
			properTimer--;
			return;
		} else {
			properTimer = 4;
		}
		if (gameStartTimer > 0) {
			gameStartTimer--;
			updatePlayers();
		} else if (gameStartTimer == 0) {
			startGame();
		}
		if (timeRemaining > 0) {
			timeRemaining--;
			updateInGamePlayers();
		} else if (timeRemaining == 0) {
			endGame();
		}
	}

	/**
	 * Method we use to update the player's interface in the waiting room
	 */
	public static void updatePlayers() {
		Iterator<Client> iterator = waitingRoom.keySet().iterator();
		while (iterator.hasNext()) {
			Client player = iterator.next();
			if (player != null) {
				player.getPlayerAssistant().sendFrame126(
						"Next Game Begins In: "
								+ (gameStartTimer * 3 + timeRemaining * 3)
								+ " seconds.", 6570);
				player.getPlayerAssistant().sendFrame126(
						"Zamorak Players: " + getZammyPlayers() + ".", 6572);
				player.getPlayerAssistant().sendFrame126(
						"Saradomin Players: " + getSaraPlayers() + ".", 6664);
				player.getPlayerAssistant().walkableInterface(6673);
			}
		}
	}

	/**
	 * Method we use the update the player's interface in the game room
	 */
	public static void updateInGamePlayers() {
		if (getSaraPlayers() > 0 && getZammyPlayers() > 0) {
			Iterator<Client> iterator = gameRoom.keySet().iterator();
			while (iterator.hasNext()) {
				Client player = iterator.next();
				int config;
				if (player == null) {
					continue;
				}
				player.getPlayerAssistant().walkableInterface(11146);
				player.getPlayerAssistant().sendFrame126(
						"Zamorak = " + scores[1], 11147);
				player.getPlayerAssistant().sendFrame126(
						scores[0] + " = Saradomin", 11148);
				player.getPlayerAssistant().sendFrame126(
						timeRemaining * 3 + " secs", 11155);
				config = 2097152 * saraFlag;
				player.getPlayerAssistant().sendFrame87(378, config);
				config = 2097152 * zammyFlag; // flags 0 = safe 1 = taken 2
												// = dropped
				player.getPlayerAssistant().sendFrame87(377, config);
			}
		}
	}

	/*
	 * Method that will start the game when there's enough players.
	 */
	public static void startGame() {
		if (getSaraPlayers() < 1 || getZammyPlayers() < 1) {
			gameStartTimer = GAME_START_TIMER;
			return;
		}
		gameStartTimer = -1;
		System.out.println("Starting Castle Wars game.");
		gameStarted = true;
		timeRemaining = GAME_TIMER / 2;
		Iterator<Client> iterator = waitingRoom.keySet().iterator();
		while (iterator.hasNext()) {
			Client player = iterator.next();
			int team = waitingRoom.get(player);
			if (player == null) {
				continue;
			}
			player.getPlayerAssistant().walkableInterface(-1);
			player.getPlayerAssistant().movePlayer(
					GAME_ROOM[team - 1][0] + Misc.random(3),
					GAME_ROOM[team - 1][1] - Misc.random(3), 1);
			player.getPlayerAssistant().movePlayer(
					GAME_ROOM[team - 1][0] + Misc.random(3),
					GAME_ROOM[team - 1][1] - Misc.random(3), 1);
			gameRoom.put(player, team);
		}
		waitingRoom.clear();
	}

	/*
	 * Method we use to end an ongoing cw game.
	 */
	public static void endGame() {
		Iterator<Client> iterator = gameRoom.keySet().iterator();
		while (iterator.hasNext()) {
			Client player = iterator.next();
			int team = gameRoom.get(player);
			if (player == null) {
				continue;
			}
			player.cwGames++;
			player.getPlayerAssistant().movePlayer(2440 + Misc.random(3),
					3089 - Misc.random(3), 0);
			player.getActionSender().sendMessage(
					"Castle Wars: The Castle Wars game has ended!");
			player.getActionSender().sendMessage(
					"Castle Wars: Kills: " + player.cwKills + " Deaths: "
							+ player.cwDeaths + " Games Played: "
							+ player.cwGames + ".");
			player.getActionSender().createPlayerHints(10, -1);
			deleteGameItems(player);
			player.isDead = false;
			for (int i = 0; i < 25; i++) {
				player.playerLevel[i] = player
						.getLevelForXP(player.playerXP[i]);
				player.getPlayerAssistant().refreshSkill(i);
			}
			if (scores[0] == scores[1]) {
				player.getItemAssistant().addItem(4067, 1);
				player.getActionSender().sendMessage(
						"Tie game! You earn 1 ticket!");
			} else if (team == 1) {
				if (scores[0] > scores[1]) {
					player.getItemAssistant().addItem(4067, 2);
					player.getActionSender()
							.sendMessage(
									"You won the game. You received 2 Castle Wars Tickets!");
				} else if (scores[0] < scores[1]) {
					player.getItemAssistant().addItem(4067, 0);
					player.getActionSender().sendMessage(
							"You lost the game. You received no tickets!");
				}
			} else if (team == 2) {
				if (scores[1] > scores[0]) {
					player.getItemAssistant().addItem(4067, 2);
					player.getActionSender()
							.sendMessage(
									"You won the game. You received 2 Castle Wars Tickets!");
				} else if (scores[1] < scores[0]) {
					player.getItemAssistant().addItem(4067, 0);
					player.getActionSender().sendMessage(
							"You lost the game. You received no tickets!");
					;
				}
			}
		}
		resetGame();
	}

	/**
	 * reset the game variables
	 */
	public static void resetGame() {
		changeFlagObject(4902, 0);
		changeFlagObject(4903, 1);
		setSaraFlag(0);
		setZammyFlag(0);
		timeRemaining = -1;
		System.out.println("Ending Castle Wars game.");
		gameStartTimer = GAME_START_TIMER;
		gameStarted = false;
		gameRoom.clear();
	}

	/**
	 * Method we use to remove a player from the game
	 * 
	 * @param player
	 *            the player we want to be removed
	 */
	public static void removePlayerFromCw(Client player) {
		if (player == null) {
			System.out.println("Error removing player from castle wars [REASON = null].");
			return;
		}
		if (gameRoom.containsKey(player)) {
			/*
			 * Logging/leaving with flag
			 */
			if (player.getItemAssistant().playerHasEquipped(SARA_BANNER)) {
				player.getItemAssistant().removeItem(player.playerEquipment[3],
						3);
				setSaraFlag(0); // safe flag
			} else if (player.getItemAssistant()
					.playerHasEquipped(ZAMMY_BANNER)) {
				player.getItemAssistant().removeItem(player.playerEquipment[3],
						3);
				setZammyFlag(0); // safe flag
			}
			deleteGameItems(player);
			player.getPlayerAssistant().movePlayer(2440, 3089, 0);
			player.getActionSender().sendMessage(
					"The Casle Wars game has ended for you!");
			player.getActionSender().sendMessage(
					"Kills: " + player.cwKills + " Deaths: " + player.cwDeaths
							+ ".");
			player.getActionSender().createPlayerHints(10, -1);
			gameRoom.remove(player);
		}
		if (getZammyPlayers() <= 0 || getSaraPlayers() <= 0) {
			endGame();
		}
	}

	/**
	 * Will add a cape to a player's equip
	 * 
	 * @param player
	 *            the player
	 * @param capeId
	 *            the capeId
	 */
	public static void addCapes(Client player, int capeId) {
		player.playerEquipment[player.playerCape] = capeId;
		player.playerEquipmentN[player.playerCape] = 1;
		player.getItemAssistant().updateSlot(player.playerCape);
		player.appearanceUpdateRequired = true;
		player.updateRequired = true;
	}

	/**
	 * This method will delete all items received in game. Easy to add items to
	 * the array. (:
	 * 
	 * @param player
	 *            the player who want the game items deleted from.
	 */

	public static void deleteGameItems(Client player) {
		switch (player.playerEquipment[3]) {
		case 4037:
		case 4039:
			player.playerEquipment[3] = -1;
			player.playerEquipmentN[3] = 0;
			player.getItemAssistant().updateSlot(player.playerWeapon);
			player.appearanceUpdateRequired = true;
			player.updateRequired = true;
			System.out.println(player.playerName + " removed weapon:"
					+ player.playerEquipment[3]);
			break;
		}
		switch (player.playerEquipment[1]) {
		case 4042:
		case 4041:
			player.playerEquipment[1] = -1;
			player.playerEquipmentN[1] = 0;
			player.getItemAssistant().updateSlot(player.playerCape);
			player.appearanceUpdateRequired = true;
			player.updateRequired = true;
			System.out.println(player.playerName + " removed cape: "
					+ player.playerEquipment[1]);
			break;
		}
		int[] items = { 4049, 1265, 4045, 4053, 4042, 4041, 4037, 4039, 4043 };
		//4049, 4045, 4053, 4042, 4041, 4037, 4039
		for (int item : items) {
			if (player.getItemAssistant().playerHasItem(item)) {
				player.getItemAssistant().deleteItem2(item,
						player.getItemAssistant().getItemAmount(item));
			}
		}
	}

	/**
	 * Methode we use to get the zamorak players
	 * 
	 * @return the amount of players in the zamorakian team!
	 */
	public static int getZammyPlayers() {
		int players = 0;
		Iterator<Integer> iterator = !waitingRoom.isEmpty() ? waitingRoom
				.values().iterator() : gameRoom.values().iterator();
		while (iterator.hasNext()) {
			if (iterator.next() == 2) {
				players++;
			}
		}
		return players;
	}

	/**
	 * Method we use to get the saradomin players!
	 * 
	 * @return the amount of players in the saradomin team!
	 */
	public static int getSaraPlayers() {
		int players = 0;
		Iterator<Integer> iterator = !waitingRoom.isEmpty() ? waitingRoom
				.values().iterator() : gameRoom.values().iterator();
		while (iterator.hasNext()) {
			if (iterator.next() == 1) {
				players++;
			}
		}
		return players;
	}

	/**
	 * Method we use for checking if the player is in the gameRoom
	 * 
	 * @param player
	 *            player who will be checking
	 * @return
	 */
	public static boolean isInCw(Player player) {
		return gameRoom.containsKey(player);
	}

	/**
	 * Method we use for checking if the player is in the waitingRoom
	 * 
	 * @param player
	 *            player who will be checking
	 * @return
	 */
	public static boolean isInCwWait(Player player) {
		return waitingRoom.containsKey(player);
	}

	/**
	 * Method to make sara flag change status 0 = safe, 1 = taken, 2 = dropped
	 * 
	 * @param status
	 */
	public static void setSaraFlag(int status) {
		saraFlag = status;
	}

	/**
	 * Method to make zammy flag change status 0 = safe, 1 = taken, 2 = dropped
	 * 
	 * @param status
	 */
	public static void setZammyFlag(int status) {
		zammyFlag = status;
	}

	/**
	 * Method we use for the changing the object of the flag stands when
	 * capturing/returning flag
	 * 
	 * @param objectId
	 *            the object
	 * @param team
	 *            the team of the player
	 */
	public static void changeFlagObject(int objectId, int team) {
		Iterator<Client> iterator = gameRoom.keySet().iterator();
		while (iterator.hasNext()) {
			Client teamPlayer = iterator.next();
			teamPlayer.getActionSender().object(objectId,
					FLAG_STANDS[team][0], FLAG_STANDS[team][1], 0, 10);
		}
	}
}
