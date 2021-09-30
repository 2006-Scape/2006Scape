package com.rs2.game.content.minigames.magetrainingarena;

import java.util.Random;

import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.world.Boundary;

public class Alchemy {

	private final Player player;

	public Alchemy(Player c) {
		this.player = c;
	}

    public void searchCupboard(int objectID) {
        int index = (objectID - firstCupboard) / 2;
        int item = Alchemy.items[(index + offset) % Alchemy.items.length];
        player.getItemAssistant().addItem(item, 1);
    }

	public void alchItem(int itemID, int spellID) {
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
    public static int offset = 0;
    public static int valueOffset = 0;
    public static int firstCupboard = 10783;
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
        if (++ticks < 71) {
            return;
        }
        ticks = 0;
        offset = random.nextInt(items.length);
        valueOffset = random.nextInt(values.length);
		for (int i = 0; i < NpcHandler.MAX_NPCS; i ++) {
			if (NpcHandler.npcs[i] != null && NpcHandler.npcs[i].npcType == 3099) {
                NpcHandler.npcs[i].forceChat("Items are changing!");
            }
		}
        for (Player p : PlayerHandler.players) {
            if (p == null) {
                continue;
            }
            updateInterface(p);
        }
	}

    public static void updateInterface(Player player) {
        if (!Boundary.isIn(player, Boundary.MAGE_TRAINING_ARENA_ALCHEMY)) {
            return;
        }
        int startInterface = 15902;
        for (int i = 0; i < values.length; i++) {
            player.getPacketSender().sendString("" + values[(i + valueOffset) % values.length], startInterface + i);
        }
        player.getPacketSender().sendString("" + player.alchemyPoints, 15896);
        player.getPacketSender().walkableInterface(15892);
    }
}
