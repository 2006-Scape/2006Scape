package com.rebotted.game.npcs.drops;

import com.rebotted.util.Misc;

public class ItemDrop {
    public int item_id, chance;
    public int[] amounts;

    ItemDrop (int item_id, int[]amounts, int chance) {
        this.item_id = item_id;
        this.amounts = amounts;
        this.chance = chance;
    }

    ItemDrop (int item_id, int amount, int chance) {
        this.item_id = item_id;
        this.amounts = new int[]{amount, amount};
        this.chance = chance;
    }

    public int getChance(){
        return this.chance;
    }

    public int getItemID(){
        return this.item_id;
    }

    public int getAmount(){
        return Misc.random(this.amounts[0], this.amounts[1]);
    }
}
