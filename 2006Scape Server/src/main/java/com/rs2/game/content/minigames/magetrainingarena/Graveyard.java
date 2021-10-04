package com.rs2.game.content.minigames.magetrainingarena;

import java.util.Random;

import com.rs2.GameConstants;
import com.rs2.game.content.combat.magic.MagicData;
import com.rs2.game.content.combat.magic.MagicRequirements;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.world.Boundary;

public class Graveyard {

	private Player player;

	public Graveyard(Player c) {
		this.player = c;
	}

    public void searchCupboard(int objectID) {
    }

	public void alchItem(int itemID, int spellID) {
    }

    public void depositFood() {
    }

    public void clearItems() {
        for (int item: items) {
            player.getItemAssistant().deleteItem(item, Integer.MAX_VALUE);
        }
        player.getItemAssistant().deleteItem(995, Integer.MAX_VALUE);
    }

    /* ITEMS */
    // 6893 - Leather boots
    // 6894 - Adamant Kiteshield
    // 6895 - Adamant Med Helm
    // 6896 - Emerald
    // 6897 - Rune Sword
    public static int[] items = {6893, 6894, 6895, 6896, 6897, -1, -1, -1};
    public static int[] values = {30, 15, 8, 5, 1};

    public static int ticks = 0;
    private static Random random = new Random();

    /* OBJECTS */
    // 10734 - Coin Collector
    // 10783 - 1st Cupboard (+2 for the next 7 cupboards)

    /* INTERFACES */
    // 15892 - Alchemy training arena interface

	public static void process() {
        for (Player p : PlayerHandler.players) {
            if (p == null) {
                continue;
            }
            updateInterface(p);
        }
        // Every 71 ticks, randomize the order and such
        if (++ticks % 71 == 0) {
            for (Player p : PlayerHandler.players) {
                if (p == null) {
                    continue;
                }
                updateInterface(p);
            }
        }
	}

    public static void updateInterface(Player player) {
        if (!Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA_ALCHEMY)) {
            return;
        }
    }
}
