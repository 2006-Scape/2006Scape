package redone.game.npcs.impl;

import redone.game.npcs.Npc;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;

public class Pets {

	private final int[][] catArray = { { 3505, 7583 }, { 3506, 7584 },
			{ 766, 1560 }, { 3507, 7585 }, { 765, 1559 }, { 764, 1558 },
			{ 763, 1557 }, { 762, 1556 }, { 761, 1555 }, { 768, 1561 },
			{ 769, 1562 }, { 770, 1563 }, { 771, 1564 }, { 772, 1565 },
			{ 773, 1566 } };

	public static final int[] CAT_ITEMS = { 1555, 1556, 1557, 1558, 1559, 1560,
			1561, 1562, 1563, 1564, 1565, 7585, 7584 };

	public void pickUp(Client c, int Type) {
		for (Npc i : NpcHandler.npcs) {
			if (i == null) {
				continue;
			}
		}
		for (Npc i : NpcHandler.npcs) {
			if (i != null) {
				if (i.npcType == Type) {
					if (i.spawnedBy == c.playerId && i.spawnedBy > 0) {
						i.absX = 0;
						i.absY = 0;
						i = null;
						break;
					}
				}
			}
		}
	}

	public void pickUpClean(Client c, int id) {
		for (int[] element : catArray) {
			if (element[0] == id) {
				c.getItemAssistant().addItem(element[1], 1);
			}
		}
		for (Npc i : NpcHandler.npcs) {
			if (i == null) {
				continue;
			}
			if (i.npcType == id) {
				i.absX = 0;
				i.absY = 0;
			}
		}
		c.hasNpc = false;
	}

	public static int summonItemId(int itemId) {
		if (itemId == 1555) {
			return 761;
		} else if (itemId == 1556) {
			return 762;
		} else if (itemId == 1557) {
			return 763;
		} else if (itemId == 1558) {
			return 764;
		} else if (itemId == 1559) {
			return 765;
		} else if (itemId == 1560) {
			return 766;
		} else if (itemId == 1561) {
			return 768;
		} else if (itemId == 1562) {
			return 769;
		} else if (itemId == 1563) {
			return 770;
		} else if (itemId == 1564) {
			return 771;
		} else if (itemId == 1565) {
			return 772;
		} else if (itemId == 1566) {
			return 773;
		} else if (itemId == 7585) {
			return 3507;
		} else if (itemId == 7584) {
			return 3506;
		} else if (itemId == 7583) {
			return 3505;
		}
		return 0;
	}
}
