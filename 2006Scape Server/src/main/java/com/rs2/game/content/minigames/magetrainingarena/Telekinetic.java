package com.rs2.game.content.minigames.magetrainingarena;

import java.util.Random;

import com.rs2.GameConstants;
import com.rs2.game.content.combat.magic.CastRequirements;
import com.rs2.game.content.combat.magic.Enchanting.EnchantSpell;
import com.rs2.game.items.GroundItem;
import com.rs2.game.items.ItemAssistant;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.world.Boundary;

public class Telekinetic {

	private Player player;

	public Telekinetic(Player c) {
		this.player = c;
	}

	public void telekineticGrab(int x, int y) {
	}

	public void deposit() {
	}

    public void clearItems() {
    }

    /* ITEMS */
    // 6888 - Guardian Statue

    /* OBJECTS */

    /* INTERFACES */

    public static int ticks = 0;
    private static Random random = new Random();

	public static void process() {
        for (Player p : PlayerHandler.players) {
            if (p == null) {
                continue;
            }
            updateInterface(p);
        }
	}


    public static void updateInterface(Player player) {
        if (!Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA_ENCHANTING)) {
            return;
        }
    }

    public static void spawnStatue(int x, int y, int h) {
        GroundItem statue = new GroundItem(6888, x, y, h, 1, 0, 0, "");
        for (Player p : PlayerHandler.players) {
            if (p == null) {
                continue;
            }
            if (p.distanceToPoint(statue.getItemX(), statue.getItemY()) <= 60) {
                p.getPacketSender().createGroundItem(statue.getItemId(), statue.getItemX(), statue.getItemY(), statue.getItemAmount());
            }
        }
    }
}
