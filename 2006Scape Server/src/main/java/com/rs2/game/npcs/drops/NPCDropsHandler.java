package com.rs2.game.npcs.drops;

import com.google.gson.Gson;
import com.rs2.util.Misc;
import com.rs2.util.NpcDrop;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Npc Drops Handler
 *
 * @author Andrew (Mr Extremez), SandroC
 */

public class NPCDropsHandler {
    private static NpcDrop[] npcDrops;

    /*public static int // found on http://runescape.wikia.com/wiki/Drop rate
            ALWAYS    = 0,
            COINSRATE = 3,
            COMMON    = 32,
            UNCOMMON  = 64,
            RARE      = 256,
            VERY_RARE = 512;*/

    public static void loadItemDropData() {
        try {
            npcDrops = new Gson().fromJson(new FileReader("./data/cfg/npcdrops.json"), NpcDrop[].class);
        } catch (FileNotFoundException fileex) {
            System.out.println("npcdrops.json: file not found.");
        }
    }

    /**
     * Handles the npc drops for the npc id.
     *
     * @param npcId id of the npc
     *
     * @return Items dropped by that npc
     */
    public static ItemDrop[] getNpcDrops(String npc, int npcId) {
        for (NpcDrop npcDrop : npcDrops) {
            if (npcDrop.getId() == npcId) {
                return npcDrop.getItems();
            }
        }

        return new ItemDrop[]{
                new ItemDrop(526, 1, 0),
                new ItemDrop(995, new int[]{ 1, 10 }, 3),
                new ItemDrop(2677, 1, 512) };
    }

    /**
     * Misc.random in shorter form
     *
     * @param max max number
     *
     * @return random number from 0->max
     */
    public static int r(int max) {
        return Misc.random(max);
    }

}
