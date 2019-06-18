package redone.game.content.combat.melee;

import redone.game.players.Client;

/**
 * Handles adding and removing hosts to the players array list.
 */
public class PlayerKilling {

	/**
	 * Adds the host of the killed player.
	 * 
	 * @param client
	 *            Player that saves the host.
	 * @param host
	 *            Host address of the killed player.
	 * @return True if the host is added to the players array.
	 */

	public static boolean addHostToList(Client client, String host) {
		if (client != null) {
			return client.lastKilledPlayers.add(host);
		}
		return false;
	}

	/**
	 * Checks if the host is already on the players array.
	 * 
	 * @param client
	 *            Player that is adding the killed players host.
	 * @param host
	 *            Host address of the killed player.
	 * @return True if the host is on the players array.
	 */

	public static boolean hostOnList(Client client, String host) {
		if (client != null) {
			if (client.lastKilledPlayers.lastIndexOf(host) >= KILL_WAIT_MAX) {
				removeHostFromList(client, host);
				return false;
			}
			return client.lastKilledPlayers.contains(host);
		}
		return false;
	}

	/**
	 * Removes the host from the players array.
	 * 
	 * @param client
	 *            Player that is removing the host.
	 * @param host
	 *            Host that is being removed.
	 * @return True if host is successfully removed.
	 */

	public static boolean removeHostFromList(Client client, String host) {
		if (client != null) {
			return client.lastKilledPlayers.remove(host);
		}
		return false;
	}

	/*
	 * Amount of kills you have to wait before the host is deleted.
	 */

	public static final int KILL_WAIT_MAX = 3;

}
