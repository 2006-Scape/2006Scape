package com.rs2.game.content.skills.core;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.StaticItemList;
import com.rs2.game.content.StaticObjectList;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.objects.Object;
import com.rs2.game.players.Player;
import com.rs2.game.players.PlayerHandler;
import com.rs2.util.Misc;
import com.rs2.world.clip.Region;

public class Mining {

    /**
     * Andrew (Mr Extremez)
     */
    private static final int[] RANDOM_GEMS = {StaticItemList.UNCUT_DIAMOND, StaticItemList.UNCUT_RUBY, StaticItemList.UNCUT_EMERALD, StaticItemList.UNCUT_SAPPHIRE};
    private static final int[] GLORIES = {StaticItemList.AMULET_OF_GLORY1, StaticItemList.AMULET_OF_GLORY2, StaticItemList.AMULET_OF_GLORY3, StaticItemList.AMULET_OF_GLORY4};

    public boolean giveGem(Player player) {
        int chance = 256;
        for (int i = 0; i < GLORIES.length; i++) {
            if (player.playerEquipment[player.playerAmulet] == GLORIES[i]) {
                chance = 86;
            }
        }
        return Misc.random(chance) == 1;
    }

    public void obtainGem(Player player) {
        int reward = RANDOM_GEMS[(int) (RANDOM_GEMS.length * Math.random())];
        player.getItemAssistant().addItem(reward, 1);
        player.getPacketSender().sendMessage("You found an " + DeprecatedItems.getItemName(reward) + ".");
    }

    public final int[][] Pick_Settings = {
            {StaticItemList.BRONZE_PICKAXE, 1, 1, 625}, //Bronze
            {StaticItemList.IRON_PICKAXE, 1, 2, 626}, //Iron
            {StaticItemList.STEEL_PICKAXE, 6, 3, 627}, //Steel
            {StaticItemList.MITHRIL_PICKAXE, 21, 4, 629}, //Mithril
            {StaticItemList.ADAMANT_PICKAXE, 31, 5, 628}, //Addy
            {StaticItemList.RUNE_PICKAXE, 41, 6, 624}, //Rune
    };

    public static enum gems {
        OPAL(StaticItemList.UNCUT_OPAL, 60),
        JADE(StaticItemList.UNCUT_JADE, 30),
        RED_TOPAZ(StaticItemList.UNCUT_RED_TOPAZ, 15),
        SAPHIRE(StaticItemList.UNCUT_SAPPHIRE, 9),
        EMERALD(StaticItemList.UNCUT_EMERALD, 5),
        RUBY(StaticItemList.UNCUT_RUBY, 5),
        DIAMOND(StaticItemList.UNCUT_DIAMOND, 4);

        public final int itemID;
        public final int chance;

        gems(int itemID, int chance) {
            this.itemID = itemID;
            this.chance = chance;
        }


        public static int getRandom() {
            final int maxChance = 128;
            int random = (int) Math.floor(Math.random() * maxChance);
            int index = 0;
            for (gems gem : gems.values()) {
                index += gem.chance;
                if (index >= random)
                    return gem.itemID;
            }
            return gems.OPAL.itemID;
        }
    }

    //Rock ID's, Level Req, XP, mineTimer, respawnTimer, Ore ID's
    public static enum rockData {
        ESSENCE(new int[]{StaticObjectList.RUNE_ESSENCE}, 1, 5, 2, 0, new int[]{StaticItemList.RUNE_ESSENCE, StaticItemList.PURE_ESSENCE}),
        CLAY(new int[]{StaticObjectList.ROCKS_2108, StaticObjectList.ROCKS_2109, StaticObjectList.ROCKS_11189, StaticObjectList.ROCKS_11190, StaticObjectList.ROCKS_11191, StaticObjectList.ROCKS_9713, StaticObjectList.ROCKS_9711, StaticObjectList.ROCKS_14905, StaticObjectList.ROCKS_14904}, 1, 5, 1, 2, new int[]{StaticItemList.CLAY}),
        COPPER(new int[]{StaticObjectList.ROCKS_3042, StaticObjectList.ROCKS_2091, StaticObjectList.ROCKS_2090, StaticObjectList.ROCKS_9708, StaticObjectList.ROCKS_9709, StaticObjectList.ROCKS_9710, StaticObjectList.ROCKS_11960, StaticObjectList.ROCKS_14906, StaticObjectList.ROCKS_14907}, 1, 18, 1, 4, new int[]{StaticItemList.COPPER_ORE}),
        TIN(new int[]{StaticObjectList.ROCKS_2094, StaticObjectList.ROCKS_2095, StaticObjectList.ROCKS_3043, StaticObjectList.ROCKS_9716, StaticObjectList.ROCKS_9714, StaticObjectList.ROCKS_11958, StaticObjectList.ROCKS_11957, StaticObjectList.ROCKS_11959, StaticObjectList.ROCKS_11933, StaticObjectList.ROCKS_11934, StaticObjectList.ROCKS_11935, StaticObjectList.ROCKS_14903, StaticObjectList.ROCKS_14902}, 1, 18, 1, 4, new int[]{StaticItemList.TIN_ORE}),
        BLURITE(new int[]{StaticObjectList.ROCKS_10583, StaticObjectList.ROCKS_10584, StaticObjectList.ROCKS_2110}, 10, 20, 1, 42, new int[]{StaticItemList.BLURITE_ORE}),
        IRON(new int[]{StaticObjectList.ROCKS, StaticObjectList.ROCKS_2093, StaticObjectList.ROCKS_2092, StaticObjectList.ROCKS_9717, StaticObjectList.ROCKS_9718, StaticObjectList.ROCKS_9719, StaticObjectList.ROCKS_11962, StaticObjectList.ROCKS_11956, StaticObjectList.ROCKS_11954, StaticObjectList.ROCKS_14856, StaticObjectList.ROCKS_14857, StaticObjectList.ROCKS_14858, StaticObjectList.ROCKS_14914, StaticObjectList.ROCKS_14913}, 15, 35, 2, 9, new int[]{StaticItemList.IRON_ORE}),
        SILVER(new int[]{StaticObjectList.ROCKS_2101, StaticObjectList.ROCKS_11186, StaticObjectList.ROCKS_11187, StaticObjectList.ROCKS_11188, StaticObjectList.ROCKS_2100}, 20, 40, 3, 100, new int[]{StaticItemList.SILVER_ORE}),
        COAL(new int[]{StaticObjectList.ROCKS_2096, StaticObjectList.ROCKS_2097, StaticObjectList.ROCKS_11963, StaticObjectList.ROCKS_11964, StaticObjectList.ROCKS_14850, StaticObjectList.ROCKS_14851, StaticObjectList.ROCKS_14852, StaticObjectList.ROCKS_11930, StaticObjectList.ROCKS_11931, StaticObjectList.ROCKS_11932}, 30, 50, 4, 50, new int[]{StaticItemList.COAL}),
        GOLD(new int[]{StaticObjectList.ROCKS_2099, StaticObjectList.ROCKS_2098, StaticObjectList.ROCKS_11183, StaticObjectList.ROCKS_11184, StaticObjectList.ROCKS_11185, StaticObjectList.ROCKS_9720, StaticObjectList.ROCKS_9722}, 40, 65, 6, 100, new int[]{StaticItemList.GOLD_ORE}),
        MITHRIL(new int[]{StaticObjectList.ROCKS_2103, StaticObjectList.ROCKS_2102, StaticObjectList.ROCKS_14853, StaticObjectList.ROCKS_14854, StaticObjectList.ROCKS_14855}, 55, 80, 8, 200, new int[]{StaticItemList.MITHRIL_ORE}),
        ADAMANT(new int[]{StaticObjectList.ROCKS_2104, StaticObjectList.ROCKS_2105, StaticObjectList.ROCKS_14862, StaticObjectList.ROCKS_14863, StaticObjectList.ROCKS_14864}, 70, 95, 10, 400, new int[]{StaticItemList.ADAMANTITE_ORE}),
        RUNE(new int[]{StaticObjectList.ROCKS_14859, StaticObjectList.ROCKS_14860, StaticObjectList.ROCKS_2106, StaticObjectList.ROCKS_2107}, 85, 125, 20, 1200, new int[]{StaticItemList.RUNITE_ORE}),
        GRANITE(new int[]{StaticObjectList.ROCKS_10947}, 45, 75, 10, 8, new int[]{StaticItemList.GRANITE_500G, StaticItemList.GRANITE_2KG, StaticItemList.GRANITE_5KG}),
        SANDSTONE(new int[]{StaticObjectList.ROCKS_10946}, 35, 60, 5, 8, new int[]{StaticItemList.SANDSTONE_1KG, StaticItemList.SANDSTONE_2KG, StaticItemList.SANDSTONE_5KG, StaticItemList.SANDSTONE_10KG}),
        GEM(new int[]{StaticObjectList.ROCKS_2111}, 40, 65, 6, 175, new int[]{});

        private final int levelReq, mineTimer, respawnTimer, xp;
        private final int[] oreIds;
        private final int[] objectId;

        private rockData(final int[] objectId, final int levelReq, final int xp, final int mineTimer, final int respawnTimer, final int... oreIds) {
            this.objectId = objectId;
            this.levelReq = levelReq;
            this.xp = xp;
            this.mineTimer = mineTimer;
            this.respawnTimer = respawnTimer;
            this.oreIds = oreIds;
        }

        public int getObject(final int object) {
            for (int element : objectId) {
                if (object == element) {
                    return element;
                }
            }
            return -1;
        }

        public static rockData getRock(final int object) {
            for (final rockData rock : rockData.values()) {
                if (object == rock.getObject(object)) {
                    return rock;
                }
            }
            return null;
        }

        public int getRequiredLevel() {
            return levelReq;
        }

        public int getXp() {
            return xp;
        }

        public int getTimer() {
            return mineTimer;
        }

        public int getRespawnTimer() {
            return respawnTimer;
        }

        public int[] getOreIds() {
            return oreIds;
        }

        public int getOre(int playerLevel) {
            if (this == rockData.ESSENCE)
                return playerLevel < 30 ? oreIds[0] : oreIds[1];
            if (this == rockData.GEM)
                return gems.getRandom();

            // return a random ore from the possibilities
            return oreIds[(int) Math.floor(Math.random() * oreIds.length)];
        }
    }

    public void repeatAnimation(final Player c) {
        CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
                if (c.isMining) {
                    c.startAnimation(Pick_Settings[c.miningAxe][3]);
                    c.getPacketSender().sendSound(432, 100, 0);
                } else {
                    container.stop();
                }
            }

            @Override
            public void stop() {
                c.startAnimation(65535);
                c.isMining = false;
            }
        }, 3);
    }

    public void startMining(final Player player, final int objectID, final int objectX, final int objectY, final int type) {
        CycleEventHandler.getSingleton().stopEvents(player, "miningEvent".hashCode());
        if (player.isMining || player.miningRock)
            return;
        int miningLevel = player.playerLevel[Constants.MINING];
        rockData rock = rockData.getRock(objectID);
        player.miningAxe = -1;
        player.turnPlayerTo(objectX, objectY);
        // check if the player has required level for this rock
        if (rock.getRequiredLevel() > miningLevel) {
            player.getPacketSender().sendMessage("You need a Mining level of " + rock.getRequiredLevel() + " to mine this rock.");
            return;
        }
        // check id the player has a pickaxe they can use on them
        for (int i = 0; i < Pick_Settings.length; i++) {
            if (player.getItemAssistant().playerHasItem(Pick_Settings[i][0]) || player.playerEquipment[player.playerWeapon] == Pick_Settings[i][0]) {
                if (Pick_Settings[i][1] <= miningLevel) {
                    player.miningAxe = i;
                }
            }
        }
        if (player.miningAxe == -1) {
            player.getPacketSender().sendMessage("You need a pickaxe to mine this rock.");
            return;
        }
        if (player.getItemAssistant().freeSlots() < 1) {
            player.getPacketSender().sendMessage("You do not have enough inventory slots to do that.");
            return;
        }

        player.startAnimation(Pick_Settings[player.miningAxe][3]);
        player.getPacketSender().sendSound(432, 100, 0);
        player.isMining = true;
        repeatAnimation(player);
        player.rockX = objectX;
        player.rockY = objectY;
        player.miningRock = true;

        // Tutorial only stuff
        if (player.tutorialProgress == 17 || player.tutorialProgress == 18) {
            player.getPacketSender().chatbox(6180);
            player.getDialogueHandler().chatboxText("", "Your character is now attempting to mine the rock.", "This should only take a few seconds.", "", "Please wait");
            player.getPacketSender().chatbox(6179);
        } else {
            player.getPacketSender().sendMessage("You swing your pick at the rock.");
        }

        CycleEventHandler.getSingleton().addEvent("miningEvent".hashCode(), player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
                int oreID = rock.getOre(miningLevel);
                if (!player.isMining) {
                    container.stop();
                    player.startAnimation(65535);
                    return;
                }
                if (player.isMining) {
                    if (!giveGem(player)) {
                        player.getItemAssistant().addItem(oreID, 1);
                        player.getPlayerAssistant().addSkillXP(rock.getXp(), Constants.MINING);
                        player.getPacketSender().sendMessage("You manage to mine some " + DeprecatedItems.getItemName(oreID).toLowerCase() + ".");
                    } else {
                        obtainGem(player);
                    }
                }
                if (player.tutorialProgress == 17) {
                    if (rock != rockData.TIN) {
                        player.getDialogueHandler().sendStatement("You should mine tin first.");
                        resetMining(player);
                        return;
                    }
                    if (player.getItemAssistant().playerHasItem(StaticItemList.TIN_ORE)) {
                        player.getPacketSender().createArrow(3086, 9501, player.getH(), 2);
                        player.getDialogueHandler().chatboxText("Now you have some tin ore you must need some copper ore, then", "you'll have all you need to create a bronze bar. As you did before", "right click on the copper rock and select 'mine'.", "", "Mining");
                        player.tutorialProgress = 18;
                    }
                } else if (player.tutorialProgress == 18) {
                    if (rock != rockData.COPPER) {
                        player.getDialogueHandler().sendStatement("You have already mined this type of ore, now try the other.");
                        resetMining(player);
                        return;
                    }
                    if (player.getItemAssistant().playerHasItem(StaticItemList.COPPER_ORE)) {
                        player.getPacketSender().createArrow(3078, 9495, 0, 2);
                        player.getDialogueHandler().sendDialogues(3061, -1);
                    }
                }
                if (player.getItemAssistant().freeSlots() < 1) {
                    player.getPacketSender().sendMessage("You have ran out of inventory slots.");
                    container.stop();
                }
                mineRock(rock.getRespawnTimer(), objectX, objectY, type, objectID);
                container.stop();
                if (rock == rockData.ESSENCE) {
                    startMining(player, objectID, objectX, objectY, type);
                }
            }

            @Override
            public void stop() {
                player.getPacketSender().closeAllWindows();
                player.startAnimation(65535);
                player.isMining = false;
                player.rockX = 0;
                player.rockY = 0;
                player.miningRock = false;
                return;
            }
        }, getTimer(rock, player.miningAxe, miningLevel));
    }

    public static void resetMining(Player player) {
        player.getPacketSender().closeAllWindows();
        player.startAnimation(65535);
        player.isMining = false;
        player.rockX = 0;
        player.rockY = 0;
        player.miningRock = false;
    }

    public int getTimer(rockData rock, int pick, int level) {
        double timer = (int) ((rock.getRequiredLevel() * 2) + 20 + Misc.random(20)) - ((Pick_Settings[pick][2] * (Pick_Settings[pick][2] * 0.75)) + level);
        if (timer < 2.0) {
            return 2;
        } else {
            return (int) timer;
        }
    }

    public void mineRock(int respawnTime, int x, int y, int type, int i) {
        if (i != StaticObjectList.RUNE_ESSENCE) {
            new Object(StaticObjectList.ROCKS_452, x, y, 0, type, 10, i, respawnTime);
            Region.addObject(StaticObjectList.ROCKS_452, x, y, 0, 10, type, false);

            for (int t = 0; t < PlayerHandler.players.length; t++) {
                if (PlayerHandler.players[t] != null) {
                    if (PlayerHandler.players[t].rockX == x && PlayerHandler.players[t].rockY == y) {
                        PlayerHandler.players[t].isMining = false;
                        PlayerHandler.players[t].startAnimation(65535);
                        PlayerHandler.players[t].rockX = 0;
                        PlayerHandler.players[t].rockY = 0;
                    }
                }
            }
        }
    }

    public static void prospectRock(final Player player, final String itemName) {
        if (player.tutorialProgress == 15 || player.tutorialProgress == 16) {
            player.getPacketSender().closeAllWindows();
            player.getPacketSender().chatbox(6180);
            player.getDialogueHandler()
                    .chatboxText(
                            "Please wait.",
                            "Your character is now attempting to prospect the rock. This should",
                            "only take a few seconds.", "", "");
            player.getPacketSender().chatbox(6179);
            CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
                @Override
                public void execute(CycleEventContainer container) {
                    if (player.tutorialProgress == 15) {
                        player.getPacketSender().sendMessage("This rock contains " + itemName.toLowerCase() + ".");
                        player.getPacketSender().chatbox(6180);
                        player.getDialogueHandler()
                                .chatboxText(
                                        "",
                                        "So now you know there's tin in the grey rocks. Try prospecting",
                                        "the brown ones next.", "",
                                        "It's tin");
                        player.getPacketSender().createArrow(3086, 9501,
                                player.getH(), 2);
                        player.getPacketSender().chatbox(6179);
                        player.tutorialProgress = 16;
                        container.stop();
                        return;
                    } else if (player.tutorialProgress == 16) {
                        player.getPacketSender().sendMessage("This rock contains " + itemName.toLowerCase() + ".");
                        player.getPacketSender().chatbox(6180);
                        player.getDialogueHandler()
                                .chatboxText(
                                        "Talk to the Mining Instructor to find out about these types of",
                                        "ore and how you can mine them. He'll even give you the",
                                        "required tools.", "",
                                        "It's copper");
                        player.getPacketSender().createArrow(1, 5);
                        player.getPacketSender().chatbox(6179);
                        container.stop();
                        return;
                    }
                    player.getPacketSender().sendMessage("This rock contains " + itemName.toLowerCase() + ".");
                    container.stop();
                }

                @Override
                public void stop() {

                }

            }, 3);
            return;
        }
        player.getPacketSender().sendMessage("You examine the rock for ores...");
        CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
                player.getPacketSender().sendMessage("This rock contains " + itemName + ".");
                container.stop();
            }

            @Override
            public void stop() {

            }
        }, 3);
    }

    public static void prospectNothing(final Player c) {
        c.getPacketSender().sendMessage("You examine the rock for ores...");
        CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
                c.getPacketSender().sendMessage("There is no ore left in this rock.");
                container.stop();
            }

            @Override
            public void stop() {

            }
        }, 2);
    }

    public static boolean rockExists(int rockID) {
        for (final rockData rock : rockData.values()) {
            if (rockID == rock.getObject(rockID)) {
                return true;
            }
        }
        return false;
    }

}
