package com.rs2.game.items.impl;

import com.rs2.Constants;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;

import static com.rs2.game.content.StaticItemList.*;

import org.apollo.cache.def.ItemDefinition;

/**
 * Dye.java
 *
 * @author Andrew (Mr Extremez)
 */

public enum Dye {

    RED_CAPE(RED_DYE, CAPE),
    BLUE_CAPE(BLUE_DYE, CAPE_1021),
    GREEN_CAPE(GREEN_DYE, CAPE_1027),
    PINK_CAPE(PINK_DYE, CAPE_6959),
    ORANGE_CAPE(ORANGE_DYE, CAPE_1031),
    YELLOW_CAPE(YELLOW_DYE, CAPE_1023),
    PURPLE_CAPE(PURPLE_DYE, CAPE_1029);

    int reward, itemUsed;

    private Dye(int itemUsed, int reward) {
        this.itemUsed = itemUsed;
        this.reward = reward;
    }

    private int getItemUsed() {
        return itemUsed;
    }

    private int getReward() {
        return reward;
    }

    //blue+yellow =green
    //red+blue = purple

    public static final int[][] MAIL_DATA = {
            {ORANGE_DYE, GOBLIN_MAIL, ORANGE_GOBLIN_MAIL},
            {ORANGE_DYE, BLUE_GOBLIN_MAIL, ORANGE_GOBLIN_MAIL},
            {BLUE_DYE, GOBLIN_MAIL, BLUE_GOBLIN_MAIL},
            {BLUE_DYE, ORANGE_GOBLIN_MAIL, BLUE_GOBLIN_MAIL},
            {BLUE_DYE, YELLOW_DYE, GREEN_DYE},
            {RED_DYE, BLUE_DYE, PURPLE_DYE}
    };

    public static boolean blockDye(Player player, Dye dye, int itemUsed, int useWith) {
        if (itemUsed == dye.getItemUsed() && DeprecatedItems.getItemName(useWith).equalsIgnoreCase("Cape") && ItemDefinition.lookup(useWith).isNote()) {
            player.getPacketSender().sendMessage("You can't dye a noted cape.");
            return true;
        } else if (itemUsed == dye.getItemUsed() && DeprecatedItems.getItemName(useWith).equalsIgnoreCase("Cape") && useWith == dye.getReward() && !ItemDefinition.lookup(useWith).isNote()) {
            player.getPacketSender().sendMessage("That cape is already that color.");
            return true;
        } else if (itemUsed == dye.getItemUsed() && !DeprecatedItems.getItemName(useWith).equalsIgnoreCase("Cape")) {
            return true;
        }
        return false;
    }

    public static void dyeItem(Player player, int itemUsed, int useWith) {
        for (Dye cape : Dye.values()) {
            if (blockDye(player, cape, itemUsed, useWith)) {
                return;
            }
            if (itemUsed == cape.getItemUsed() && DeprecatedItems.getItemName(useWith).equalsIgnoreCase("Cape") && !ItemDefinition.lookup(useWith).isNote() && useWith != cape.getReward()) {
                player.getItemAssistant().deleteItem(itemUsed, 1);
                player.getItemAssistant().deleteItem(useWith, 1);
                player.getItemAssistant().addItem(cape.getReward(), 1);
                player.getPlayerAssistant().addSkillXP(2.5, Constants.CRAFTING);
            }
        }
    }

}