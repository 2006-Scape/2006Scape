package com.rs2.game.content.minigames.magetrainingarena;

import com.rs2.game.players.Player;

public class Alchemy {
    /* ITEMS */
    // 6893 - Leather boots
    // 6894 - Adamant Kiteshield
    // 6895 - Adamant Med Helm
    // 6896 - Emerald
    // 6897 - Rune Sword
    int[] items = {6893, 6894, 6895, 6896, 6897, -1, -1, -1};

    /* OBJECTS */
    // 10734 - Coin Collector
    // 10783 - 1st Cupboard (+2 for the next 7 cupboards)

    /* INTERFACES */
    // 15892 - Alchemy training arena interface

	private final Player player;

	public Alchemy(Player c) {
		this.player = c;
	}


	public void alchItem(int itemID, int spellID) {
	}
}
