package com.rebotted.util;

public class ShopData {
    int id;
    String name;
    int         sellModifier;
    int         buyModifier;
    ShopItems[] items;

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
