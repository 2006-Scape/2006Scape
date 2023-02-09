package com.rs2.game.content.skills.cooking;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.StaticItemList;
import com.rs2.game.content.StaticObjectList;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.items.ItemConstants;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

import java.security.SecureRandom;

public class Cooking extends SkillHandler {

    private static SecureRandom cookingRandom = new SecureRandom(); // The random factor

    private static enum CookingItems {
        //raw, cooked, burnt, levelreq, exp, stopburn, stopburn w/gloves, name
        SHRIMP(StaticItemList.RAW_SHRIMPS, StaticItemList.SHRIMPS, StaticItemList.BURNT_SHRIMP, 1, 30, 34, 30, "shrimp"),
        SARDINE(StaticItemList.RAW_SARDINE, StaticItemList.SARDINE, StaticItemList.BURNT_FISH_369, 1, 40, 38, 38, "sardine"),
        HERRING(StaticItemList.RAW_HERRING, StaticItemList.HERRING, StaticItemList.BURNT_FISH_357, 5, 50, 41, 41, "herring"),
        TROUT(StaticItemList.RAW_TROUT, StaticItemList.TROUT, StaticItemList.BURNT_FISH_343, 15, 70, 50, 50, "trout"),
        TUNA(StaticItemList.RAW_TUNA, StaticItemList.TUNA, StaticItemList.BURNT_FISH_367, 30, 100, 64, 63, "tuna"),
        ANCHOVIES(StaticItemList.RAW_ANCHOVIES, StaticItemList.ANCHOVIES, StaticItemList.BURNT_FISH, 5, 45, 34, 34, "anchovies"),
        RAW_BEEF(StaticItemList.RAW_BEEF, StaticItemList.COOKED_MEAT, StaticItemList.BURNT_MEAT, 1, 30, 33, 33, "raw beef"),
        RAW_RAT(StaticItemList.RAW_RAT_MEAT, StaticItemList.COOKED_MEAT, StaticItemList.BURNT_MEAT, 1, 30, 33, 33, "raw rat meat"),
        BURNT_MEAT(StaticItemList.COOKED_MEAT, StaticItemList.BURNT_MEAT, StaticItemList.BURNT_MEAT, 1, 1, 100, 100, "cooked meat"),
        RAW_CHICKEN(StaticItemList.RAW_CHICKEN, StaticItemList.COOKED_CHICKEN, StaticItemList.BURNT_CHICKEN, 1, 30, 33, 33, "raw chicken"),
        RAW_BEAR_MEAT(StaticItemList.RAW_BEAR_MEAT, StaticItemList.COOKED_MEAT, StaticItemList.BURNT_MEAT, 1, 30, 33, 33, "raw bear meat"),
        MACKEREL(StaticItemList.RAW_MACKEREL, StaticItemList.MACKEREL, StaticItemList.BURNT_FISH_357, 10, 60, 45, 45, "mackerel"),
        SALMON(StaticItemList.RAW_SALMON, StaticItemList.SALMON, StaticItemList.BURNT_FISH_343, 25, 90, 58, 55, "salmon"),
        UNCOOKED_BERRY_PIE(StaticItemList.UNCOOKED_BERRY_PIE, StaticItemList.REDBERRY_PIE, StaticItemList.BURNT_PIE, 10, 78, 50, 50, "uncooked pie"),
        PIKE(StaticItemList.RAW_PIKE, StaticItemList.PIKE, StaticItemList.BURNT_FISH_343, 20, 80, 59, 59, "pike"),
        KARAMBWAN(StaticItemList.RAW_KARAMBWAN, StaticItemList.COOKED_KARAMBWAN, StaticItemList.COOKED_KARAMBWAN_3146, 1, 80, 20, 20, "karambwan"),
        LOBSTER(StaticItemList.RAW_LOBSTER, StaticItemList.LOBSTER, StaticItemList.BURNT_LOBSTER, 40, 120, 74, 68, "lobster"),
        SWORDFISH(StaticItemList.RAW_SWORDFISH, StaticItemList.SWORDFISH, StaticItemList.BURNT_SWORDFISH, 50, 140, 86, 81, "swordfish"),
        MONKFISH(StaticItemList.RAW_MONKFISH, StaticItemList.MONKFISH, StaticItemList.BURNT_MONKFISH, 62, 150, 92, 90, "monkfish"),
        SHARK(StaticItemList.RAW_SHARK, StaticItemList.SHARK, StaticItemList.BURNT_SHARK, 76, 210, 100, 94, "shark"),
        SEA_TURTLE(StaticItemList.RAW_SEA_TURTLE, StaticItemList.SEA_TURTLE, StaticItemList.BURNT_SEA_TURTLE, 82, 211, 100, 100, "sea turtle"),
        MANTA_RAY(StaticItemList.RAW_MANTA_RAY, StaticItemList.MANTA_RAY, StaticItemList.BURNT_MANTA_RAY, 91, 216, 100, 100, "manta ray"),
        SEAWEED(StaticItemList.SEAWEED, StaticItemList.SODA_ASH, StaticItemList.SODA_ASH, 1, 1, 1, 1, "sea weed"),
        CURRY(StaticItemList.UNCOOKED_CURRY, StaticItemList.CURRY, StaticItemList.BURNT_CURRY, 60, 280, 74, 74, "curry");

        int rawItem, cookedItem, burntItem, levelReq, xp, stopBurn, stopBurnGloves;
        String name;

        private CookingItems(int rawItem, int cookedItem, int burntItem, int levelReq, int xp, int stopBurn, int stopBurnGloves, String name) {
            this.rawItem = rawItem;
            this.cookedItem = cookedItem;
            this.burntItem = burntItem;
            this.levelReq = levelReq;
            this.xp = xp;
            this.stopBurn = stopBurn;
            this.name = name;
        }

        private int getRawItem() {
            return rawItem;
        }

        private int getCookedItem() {
            return cookedItem;
        }

        private int getBurntItem() {
            return burntItem;
        }

        private int getLevelReq() {
            return levelReq;
        }

        private int getXp() {
            return xp;
        }

        private int getStopBurn() {
            return stopBurn;
        }

        private int getStopBurnGloves() {
            return stopBurnGloves;
        }

        private String getName() {
            return name;
        }
    }

    public static CookingItems forId(int itemId) {
        for (CookingItems item : CookingItems.values()) {
            if (itemId == item.getRawItem()) {
                return item;
            }
        }
        return null;
    }

    public static void makeBreadOptions(Player c, int item) {
        if (c.getItemAssistant().playerHasItem(StaticItemList.BUCKET_OF_WATER) && c.getItemAssistant().playerHasItem(StaticItemList.POT_OF_FLOUR) && item == c.breadID) {
            c.getItemAssistant().deleteItem(StaticItemList.BUCKET_OF_WATER, 1);
            c.getItemAssistant().deleteItem(StaticItemList.POT_OF_FLOUR, 1);
            c.getItemAssistant().addItem(StaticItemList.BUCKET, 1);
            c.getItemAssistant().addItem(StaticItemList.POT, 1);
            c.getItemAssistant().addItem(item, 1);
            c.getPacketSender().sendMessage("You mix the water and flour to make some " + DeprecatedItems.getItemName(item) + ".");
        }
        c.getPacketSender().closeAllWindows();
    }

    public static void pastryCreation(Player c, int itemID1, int itemID2, int giveItem, String message) {
        if (c.getItemAssistant().playerHasItem(itemID1) && c.getItemAssistant().playerHasItem(itemID2)) {
            c.getItemAssistant().deleteItem(itemID1, 1);
            c.getItemAssistant().deleteItem(itemID2, 1);
            c.getItemAssistant().addItem(giveItem, 1);
            if (message.equalsIgnoreCase("")) {
                c.getPacketSender().sendMessage("You mix the two ingredients and get an " + DeprecatedItems.getItemName(giveItem) + ".");
            } else {
                c.getPacketSender().sendMessage(message);
            }
        }
    }

    public static void cookingAddon(Player c, int itemID1, int itemID2, int giveItem, int requiredLevel, int expGained) {
        if (c.playerLevel[Constants.COOKING] >= requiredLevel) {
            if (c.getItemAssistant().playerHasItem(itemID1) && c.getItemAssistant().playerHasItem(itemID2)) {
                c.getItemAssistant().deleteItem(itemID1, 1);
                c.getItemAssistant().deleteItem(itemID2, 1);
                c.getItemAssistant().addItem(giveItem, 1);
                c.getPlayerAssistant().addSkillXP(expGained, 7);
                c.getPacketSender().sendMessage("You create a " + DeprecatedItems.getItemName(giveItem) + ".");
            }
        } else {
            c.getPacketSender().sendMessage("You don't have the required level to make an " + DeprecatedItems.getItemName(giveItem));
        }
    }

    public static void setCooking(Player player, boolean isCooking) {
        player.playerIsCooking = isCooking;
        player.stopPlayerSkill = isCooking;
    }

    private static void viewCookInterface(Player c, int item) {
        c.getPacketSender().sendChatInterface(1743);
        c.getPacketSender().sendFrame246(13716, view190 ? 190 : 170, item);
        c.getPacketSender().sendString(getLine(c) + "" + DeprecatedItems.getItemName(item) + "", 13717);
    }

    public static boolean startCooking(Player c, int itemId, int objectId) {
        CookingItems item = forId(itemId);
        if (item != null) {
            if (c.playerLevel[Constants.COOKING] < item.getLevelReq()) {
                c.getPacketSender().closeAllWindows();
                c.getDialogueHandler().sendStatement("You need a Cooking level of " + item.getLevelReq() + " to cook this.");
                c.nextChat = 0;
                return false;
            }
            if (c.playerIsCooking) {
                c.getPacketSender().closeAllWindows();
                return false;
            }
            if (!COOKING) {
                c.getPacketSender().sendMessage("This skill is currently disabled.");
                return false;
            }
            // save the id of the item and object for the cooking interface.
            c.cookingItem = itemId;
            c.cookingObject = objectId;
            viewCookInterface(c, item.getRawItem());
            return true;
        }
        return false;
    }

    private static boolean getSuccess(Player c, int burnBonus, int levelReq, int stopBurn) {
        if (c.playerLevel[Constants.COOKING] >= stopBurn) {
            return true;
        }
        double burn_chance = 55.0 - burnBonus;
        double cook_level = c.playerLevel[Constants.COOKING];
        double lev_needed = levelReq;
        double burn_stop = stopBurn;
        double multi_a = burn_stop - lev_needed;
        double burn_dec = burn_chance / multi_a;
        double multi_b = cook_level - lev_needed;
        burn_chance -= multi_b * burn_dec;
        double randNum = cookingRandom.nextDouble() * 100.0;
        return burn_chance <= randNum;
    }

    public static void cookItem(final Player player, final int itemId, final int amount, final int objectId) {
        CycleEventHandler.getSingleton().stopEvents(player, "cookingEvent".hashCode());
        final CookingItems item = forId(itemId);
        if (item != null) {
            setCooking(player, true);
            RandomEventHandler.addRandom(player);
            player.getPacketSender().closeAllWindows();
            player.doAmount = amount;
            if (player.doAmount > player.getItemAssistant().getItemAmount(itemId)) {
                player.doAmount = player.getItemAssistant().getItemAmount(itemId);
            }
            if (objectId > 0) {
                player.startAnimation(objectId == StaticObjectList.FIRE ? 897 : 896);
            }
            CycleEventHandler.getSingleton().addEvent("cookingEvent".hashCode(), player, new CycleEvent() {
                @Override
                public void execute(CycleEventContainer container) {
                    if (!player.playerIsCooking) {
                        setCooking(player, false);
                        container.stop();
                        return;
                    }
                    if (!player.getItemAssistant().playerHasItem(item.getRawItem(), 1)) {
                        player.getPacketSender().sendMessage("You have run out of " + item.getName() + " to cook.");
                        setCooking(player, false);
                        container.stop();
                        return;
                    }
                    boolean burn;
                    if (player.playerEquipment[ItemConstants.HANDS] == StaticItemList.COOKING_GAUNTLETS) {
                        burn = !getSuccess(player, 3, item.getLevelReq(), item.getStopBurnGloves());
                    } else {
                        burn = !getSuccess(player, 3, item.getLevelReq(), item.getStopBurn());
                    }
                    player.getItemAssistant().deleteItem(item.getRawItem(),
                            player.getItemAssistant().getItemSlot(itemId), 1);
                    if (!burn) {
                        player.getPacketSender().sendMessage("You successfully cook the " + item.getName().toLowerCase() + ".");
                        if (Constants.SOUND) {
                            player.getPacketSender().sendSound(SoundList.COOK_ITEM, 100, 0);
                        }
                        player.getPlayerAssistant().addSkillXP(item.getXp(), Constants.COOKING);
                        player.getItemAssistant().addItem(item.getCookedItem(), 1);
                    } else {
                        player.getPacketSender().sendMessage(
                                "Oops! You accidentally burnt the "
                                        + item.getName().toLowerCase() + "!");
                        player.getItemAssistant().addItem(item.getBurntItem(), 1);
                    }
                    player.doAmount--;
                    if (player.disconnected) {
                        container.stop();
                        return;
                    }
                    if (objectId < 0) {
                        container.stop();
                        return;
                    }
                    if (player.playerIsCooking && !Misc.goodDistance(player.objectX, player.objectY, player.absX, player.absY, 2)) {
                        container.stop();
                        return;
                    }
                    if (player.doAmount > 0) {
                        if (objectId > 0) {
                            player.startAnimation(objectId == StaticObjectList.FIRE ? 897 : 896);
                        }
                    } else if (player.doAmount == 0) {
                        setCooking(player, false);
                        container.stop();
                    }
                }

                @Override
                public void stop() {

                }
            }, 4);
        }
    }
}
