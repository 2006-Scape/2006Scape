package com.rs2.util;

import com.rs2.game.npcs.drops.ItemDrop;

/**
 * @author SandroC
 */
public class NpcDrop {
    private final int     id;
    private final ItemDrop[] items;

    public NpcDrop(int id, ItemDrop[] items) {
        this.id = id;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public ItemDrop[] getItems() {
        return items;
    }
}
