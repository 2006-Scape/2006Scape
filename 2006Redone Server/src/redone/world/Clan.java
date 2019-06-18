package redone.world;

import redone.game.players.Client;

/**
 * @author Sanity
 */

public class Clan {

	public Clan(Client player, String name) {
		this.owner = player.playerName;
		this.name = name;
	}

	public int[] members = new int[50];
	public String name;
	public String owner;
	public boolean lootshare;
}