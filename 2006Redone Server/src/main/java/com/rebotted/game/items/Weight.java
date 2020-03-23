package com.rebotted.game.items;

import com.rebotted.game.players.Player;

/**
 * @author somedude, credits to Galkon for item weights
 */
public class Weight extends ItemDefinition {

    /**
     * Calculates the weight when doing actions
     *
     * @param c
     * @param item
     * @param action
     *            - deleteitem, additem, equip, unequip.
     */
    public static void calcWeight(Player c, int item, String action) {
        if (action.equalsIgnoreCase("deleteitem")) {
            if (getWeight(item) > 99.20) {
                c.weight -= getWeight(item) / 100;
                if (c.weight < 0) {
                    c.weight = 0.0;
                }
                c.getPacketSender().writeWeight((int) c.weight);
                return;
            }
            c.weight -= getWeight(item) / 10;
            if (c.weight < 0) {
                c.weight = 0.0;
            }
            c.getPacketSender().writeWeight((int) c.weight);
        } else if (action.equalsIgnoreCase("additem")) {
            if (getWeight(item) > 99.20) {
                c.weight += getWeight(item) / 100;
                c.getPacketSender().writeWeight((int) c.weight);
                return;
            }
            c.weight += getWeight(item) / 10;
            c.getPacketSender().writeWeight((int) c.weight);
        }
    }

    /**
     * Updates the weight for inventory and equipment.
     *
     * @param player
     */
    public static void updateWeight(Player player) {
        if (player != null) {
            player.weight = 0;
            // Inventory items
            for (int playerItem : player.playerItems) {
                if (playerItem > -1) {// inventory
                    calcWeight(player, playerItem, "addItem");
                }
            }
            // Equiped items
            for (int element : player.playerEquipment) {
                if (element > -1) {// equipment
                    if (element == 88) {
                    	player.weight -= 4.5;
                    } else {
                    	calcWeight(player, element, "addItem");
                    }
                }
            }
        }
        player.getPacketSender().writeWeight((int) player.weight);
    }
}
