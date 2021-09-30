package com.rs2.game.content.minigames.magetrainingarena;

import com.rs2.game.players.Player;

public class Enchanting {
    /* ITEMS */
    // 6898 - Green Cylinder
    // 6899 - Yellow Cube
    // 6900 - Blue Icosahedron
    // 6901 - Red Pentamid
    // 6902 - Orb

    /* OBJECTS */
    // 10799 - Yellow Cube Pile
    // 10800 - Green Cylinder Pile
    // 10801 - Blue Icosahedron Pile
    // 10802 - Red Pentamid Pile
    // 10803 - Deposit Hole

    /* INTERFACES */
    // 15917 - Enchantment training arena interface
    // 15930 - Bonus text
    // 15921 - How many enchantment points they have
    // 15922 -> 15925 - Bonus object frame (not sure how to get it to show)
    // 15926 -> 15929 - Bonus object (not sure how to get it to show)

	private final Player player;

	public Enchanting(Player c) {
		this.player = c;
	}


	public void enchantItem(int itemID, int spellID) {
	}
}
