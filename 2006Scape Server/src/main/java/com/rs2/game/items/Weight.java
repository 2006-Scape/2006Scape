package com.rs2.game.items;

import com.rs2.game.players.Player;

/**
 * @author somedude, credits to Galkon for item weights
 */
public class Weight {

    /**
     * Calculates the weight when doing actions
     *
     * @param c
     * @param item
     * @param action
     *            - deleteItem, addItem.
     */
    public static void calcWeight(Player c, int item, String action) {
    	double weight = ItemDefinitions.getWeight(item);
        if (action.equalsIgnoreCase("deleteItem")) {
            if (weight > 99.20) {
                c.weight -= weight / 100;
                if (c.weight < 0) {
                    c.weight = 0.0;
                }
                c.getPacketSender().writeWeight((int) c.weight);
                return;
            }
            c.weight -= weight / 10;
            if (c.weight < 0) {
                c.weight = 0.0;
            }
            c.getPacketSender().writeWeight((int) c.weight);
        } else if (action.equalsIgnoreCase("addItem")) {
            if (weight > 99.20) {
                c.weight += weight / 100;
                c.getPacketSender().writeWeight((int) c.weight);
                return;
            }
            c.weight += weight / 10;
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
            // Equipped items
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
