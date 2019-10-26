package redone.game.content;

import redone.game.players.Client;

/**
 *
 * Holds Ironman configs and assisting methods
 * @author Fritz (TheRedArmy - http://www.rune-server.ee/members/theredarmy/)
 * @Dark98
 *
 */
public class IronMan {

    /**
     * Mode ids and names
     */
    public final static int IRONMAN_MODE = 1;
    public final static int HC_IRONMAN_MODE = 2;
    public final static int ULTIMATE_IRONMAN = 3;
    public final static String[] MODE_NAMES = { "IronMan", "Hardcore IronMan", "Ultimate IronMan" };

    /**
     * Gets the player's current game type
     * @param player - Player whose game type is being fetched
     * @return - Player's game type
     */
    public static int getMode(Client player) {
        return player.ironMan;
    }

    /**
     * Sets the players game type
     * @param player - Player whose game type is being set
     * @param mode - Game type player is being set to
     */
    public static void setMode(Client player, int mode) {
        player.ironMan = (mode);
    }

    /**
     * Gets the player's game type name
     * @param player - Player whose game type name is being fetched
     * @return - Player's game type name
     */
    public static String getModeName(Client player) {
        return MODE_NAMES[getMode(player) - 1];
    }

    /**
     * Exchanges items for notes and notes for items when items are used on a bank booth
     * @param player - Player using item on bank booth
     * @param item - Item being used on the bank booth
     * @param objectID - Object item is being used on
     */
    /*public static void exchangeNotes(Client player, Item item, int objectID) {
        if (objectID != 26972 || !player.getItems().playerHasItem(item, 1))
            return;
        int amount = player.getItems().getItemAmount(item);
        int freeSlots = player.getItems().freeSlots();
        if (item.isNote()) {
            amount = amount > freeSlots && amount != freeSlots + 1 ? freeSlots : amount;
            player.getItems().deleteItem(item, amount);
            player.getItems().addItem(ItemContainer.getItem(item.getId() - 1), amount);
            player.sendMessage("You exchange your notes for items");
        } else if (ItemContainer.getItem(item.getId() + 1).isNote()) {
            player.getItems().deleteItem(item, amount);
            player.getItems().addItem(ItemContainer.getItem(item.getId() + 1), amount);
            player.sendMessage("You exchange your items for notes");
        } else
            player.sendMessage("This cannot be noted");
    }*/
}