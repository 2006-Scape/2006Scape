package com.rebotted.util;

/**
 * @author SandroC
 */
public class ShopData {
    private final int    id;
    private final String name;
    private final int    sellModifier;
    private final int    buyModifier;
    private final ShopItems[] items;

    public ShopData(int id, String name, int sellModifier, int buyModifier, ShopItems[] items) {
        this.id = id;
        this.name = name;
        this.sellModifier = sellModifier;
        this.buyModifier = buyModifier;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSellModifier() {
        return sellModifier;
    }

    public int getBuyModifier() {
        return buyModifier;
    }

    public ShopItems[] getItems() {
        return items;
    }

    public static class ShopItems {
        int itemId;
        int itemAmount;

        public int getItemId() {
            return itemId;
        }

        public int getItemAmount() {
            return itemAmount;
        }
    }
}
