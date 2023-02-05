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
     *            - deleteitem, additem, equip, unequip.
     */
    public static void calcWeight(Player c, int item, String action) {
    	double weight = ItemDefinitions2.getWeight(item);
        if (action.equalsIgnoreCase("deleteitem")) {
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
        } else if (action.equalsIgnoreCase("additem")) {
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
