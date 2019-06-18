package redone.world;

import redone.game.npcs.Npc;
import redone.game.npcs.NpcSize;
import redone.game.players.Client;

public class TileControl {

	public static Tile generate(int x, int y, int z) {
		return new Tile(x, y, z);
	}

	public static Tile[] getTiles(Client client) {

		int size = 1, tileCount = 0;

		Tile[] tiles = new Tile[size * size];

		if (tiles.length == 1)
			tiles[0] = generate(client.absX, client.absY, client.heightLevel);
		else {
			for (int x = 0; x < size; x++)
				for (int y = 0; y < size; y++)
					tiles[tileCount++] = generate(client.absX + x, client.absY + y, client.heightLevel);
		}
		return tiles;
	}

	public static Tile[] getTiles(Npc npc) {

		int size = 1, tileCount = 0;

		size = NpcSize.getNPCSize(npc.npcType);
		Tile[] tiles = new Tile[size * size];

		if (tiles.length == 1)
			tiles[0] = generate(npc.absX, npc.absY, npc.heightLevel);
		else {
			for (int x = 0; x < size; x++)
				for (int y = 0; y < size; y++)
					tiles[tileCount++] = generate(npc.absX + x, npc.absY + y, npc.heightLevel);
		}
		return tiles;
	}

	public static int calculateDistance(Client client, Client following) {

		Tile[] tiles = getTiles(client);

		int[] location = currentLocation(client);
		int[] pointer = new int[tiles.length];

		int lowestCount = 20, count = 0;

		for (Tile newTiles : tiles) {
			if (newTiles.getTile() == location)
				pointer[count++] = 0;
			else
				pointer[count++] = calculateDistance(newTiles, following);
		}
		for (int i = 0; i < pointer.length; i++)
			if (pointer[i] < lowestCount)
				lowestCount = pointer[i];

		return lowestCount;
	}

	public static int calculateDistance(Npc npc, Client following) {

		Tile[] tiles = getTiles(npc);

		int[] location = currentLocation(npc);
		int[] pointer = new int[tiles.length];

		int lowestCount = 20, count = 0;

		for (Tile newTiles : tiles) {
			if (newTiles.getTile() == location)
				pointer[count++] = 0;
			else
				pointer[count++] = calculateDistance(newTiles, following);
		}
		for (int i = 0; i < pointer.length; i++)
			if (pointer[i] < lowestCount)
				lowestCount = pointer[i];

		return lowestCount;
	}

	public static int calculateDistance(Client client, Npc npc) {

		Tile[] tiles = getTiles(client);

		int[] location = currentLocation(client);
		int[] pointer = new int[tiles.length];

		int lowestCount = 20, count = 0;

		for (Tile newTiles : tiles) {
			if (newTiles.getTile() == location)
				pointer[count++] = 0;
			else
				pointer[count++] = calculateDistance(newTiles, npc);
		}
		for (int i = 0; i < pointer.length; i++)
			if (pointer[i] < lowestCount)
				lowestCount = pointer[i];

		return lowestCount;
	}

	public static int calculateDistance(Tile location, Client other) {
		int X = Math.abs(location.getTile()[0] - other.absX);
		int Y = Math.abs(location.getTile()[1] - other.absY);
		return X > Y ? X : Y;
	}

	public static int calculateDistance(Tile location, Npc other) {
		int X = Math.abs(location.getTile()[0] - other.absX);
		int Y = Math.abs(location.getTile()[1] - other.absY);
		return X > Y ? X : Y;
	}

	public static int calculateDistance(int[] location, int[] other) {
		int X = Math.abs(location[0] - other[0]);
		int Y = Math.abs(location[1] - other[1]);
		return X > Y ? X : Y;
	}

	public static int[] currentLocation(Client client) {
		int[] currentLocation = new int[3];
		if (client != null) {
			currentLocation[0] = client.absX;
			currentLocation[1] = client.absY;
			currentLocation[2] = client.heightLevel;
		}
		return currentLocation;
	}

	public static int[] currentLocation(Npc npc) {
		int[] currentLocation = new int[3];
		if (npc != null) {
			currentLocation[0] = npc.absX;
			currentLocation[1] = npc.absY;
			currentLocation[2] = npc.heightLevel;
		}
		return currentLocation;
	}

	public static int[] currentLocation(Tile tileLocation) {

		int[] currentLocation = new int[3];

		if (tileLocation != null) {
			currentLocation[0] = tileLocation.getTile()[0];
			currentLocation[1] = tileLocation.getTile()[1];
			currentLocation[2] = tileLocation.getTile()[2];
		}
		return currentLocation;
	}
}