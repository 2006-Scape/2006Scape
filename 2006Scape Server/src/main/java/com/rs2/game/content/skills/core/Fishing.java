package com.rs2.game.content.skills.core;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.content.randomevents.RiverTroll;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

import static com.rs2.game.content.StaticItemList.*;
import static com.rs2.game.content.StaticNpcList.*;

public class Fishing extends SkillHandler {

    public static void randomEvents(Player client) {
        if (Misc.random(350) == 5) {
            RiverTroll.spawnRiverTroll(client);
        }
        if (RiverTroll.hasRiverTroll == false) {
            RandomEventHandler.addRandom(client);
        }
    }

    /**
     * Resets the Event according to the ID. id = 10 (According to skill.)
     */
    protected static int eventId = 10;

    public static int[][] data = {
            // dataid, levelreq, item needed, bait, item recieved, exp,
            // animation,
            // secondfish, levelreq, secondexp
            {1, 1, SMALL_FISHING_NET, -1, RAW_SHRIMPS, 10, 621, RAW_ANCHOVIES, 15, 40}, // SHRIMP + ANCHOVIES
            {2, 5, FISHING_ROD, FISHING_BAIT, RAW_SARDINE, 20, 622, RAW_HERRING, 10, 30}, // SARDINE + HERRING
            {3, 16, BIG_FISHING_NET, -1, RAW_MACKEREL, 20, 620, -1, -1, -1}, // MACKEREL
            {4, 20, FLY_FISHING_ROD, FEATHER, RAW_TROUT, 50, 622, RAW_SALMON, 30, 70}, // TROUT + SALMON
            {5, 23, BIG_FISHING_NET, -1, RAW_COD, 45, 619, RAW_BASS, 46, 100}, // BASS + COD
            {6, 25, FISHING_ROD, FISHING_BAIT, RAW_PIKE, 60, 622, -1, -1, -1}, // PIKE
            {7, 35, HARPOON, -1, RAW_TUNA, 80, 618, RAW_SWORDFISH, 50, 100}, // TUNA + SWORDFISH
            {8, 40, LOBSTER_POT, -1, RAW_LOBSTER, 90, 619, -1, -1, -1}, // LOBSTER
            {9, 62, SMALL_FISHING_NET, -1, RAW_MONKFISH, 120, 621, -1, -1, -1}, // MONKFISH
            {10, 76, HARPOON, -1, RAW_SHARK, 110, 618, -1, -1, -1} // SHARK
    };

    private static String[][] messages = {
            {"You cast out your net."}, // SHRIMP + ANCHOVIES
            {"You cast out your line."}, // SARDINE + HERRING
            {"You start harpooning fish."}, // TUNA + SWORDIE
            {"You attempt to catch a lobster."}, // LOBSTER
    };

    private static void attemptdata(final Player c, final int npcId) {
        if (!FISHING) {
            c.getPacketSender().sendMessage(c.disabled());
            return;
        }
        if (c.playerSkillProp[Constants.FISHING][4] > 0) {
            c.playerSkilling[Constants.FISHING] = false;
            return;
        }
        if (!noInventorySpace(c, "fishing")) {
            return;
        }
        resetFishing(c);
        for (int i = 0; i < data.length; i++) {
            if (npcId == data[i][0]) {
                if (c.playerLevel[Constants.FISHING] < data[i][1]) {
                    c.getDialogueHandler().sendStatement(
                            "You need a fishing level of at least "
                                    + data[i][1]
                                    + " in order to fish at this spot.");
                    return;
                }
                if (!hasFishingEquipment(c, data[i][2])) {
                    return;
                }
                if (data[i][3] > 0) {
                    if (!c.getItemAssistant().playerHasItem(data[i][3])) {
                        c.getDialogueHandler().sendStatement(
                                "You need more "
                                        + DeprecatedItems.getItemName(data[i][3])
                                        .toLowerCase().toLowerCase()
                                        + " in order to fish at this spot.");
                        return;
                    }
                }
                c.playerSkillProp[Constants.FISHING][0] = data[i][6]; // ANIM
                c.playerSkillProp[Constants.FISHING][1] = data[i][4]; // FISH
                c.playerSkillProp[Constants.FISHING][2] = data[i][5]; // XP
                c.playerSkillProp[Constants.FISHING][3] = data[i][3]; // BAIT
                c.playerSkillProp[Constants.FISHING][4] = data[i][2]; // EQUIP
                c.playerSkillProp[Constants.FISHING][5] = data[i][7]; // sFish
                c.playerSkillProp[Constants.FISHING][6] = data[i][8]; // sLvl
                c.playerSkillProp[Constants.FISHING][7] = data[i][4]; // FISH
                c.playerSkillProp[Constants.FISHING][8] = data[i][9]; // sXP
                c.playerSkillProp[Constants.FISHING][9] = Misc.random(1) == 0 ? 7 : 5;
                c.playerSkillProp[Constants.FISHING][10] = data[i][0]; // INDEX

                if (c.playerSkilling[Constants.FISHING]) {
                    return;
                }
                c.playerSkilling[Constants.FISHING] = true;
                if (c.tutorialProgress == 6) { // if tutorial prog = 6
                    c.startAnimation(c.playerSkillProp[Constants.FISHING][0]);
                    c.stopPlayerSkill = true;
                    c.getPacketSender().drawHeadicon(0, 0, 0, 0); // deletes
                    // headicon
                    c.getPacketSender().chatbox(6180);
                    c.getDialogueHandler()
                            .chatboxText(
                                    "This should only take a few seconds.",
                                    "As you gain Fishing experience you'll find that there are many",
                                    "types of fish and many ways to catch them.",
                                    "", "Please wait");
                    c.getPacketSender().chatbox(6179);
                    CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {

                            if (c.playerSkillProp[Constants.FISHING][5] > 0) {
                                if (c.playerLevel[Constants.FISHING] >= c.playerSkillProp[Constants.FISHING][6]) {
                                    c.playerSkillProp[Constants.FISHING][1] = c.playerSkillProp[Constants.FISHING][Misc
                                            .random(1) == 0 ? 7 : 5];
                                }
                            }

                            if (!c.stopPlayerSkill) {
                                container.stop();
                            }
                            if (!c.playerSkilling[Constants.FISHING]) {
                                container.stop();
                            }

                            if (c.playerSkillProp[Constants.FISHING][1] > 0) {
                                c.startAnimation(c.playerSkillProp[Constants.FISHING][0]);
                            }

                        }

                        @Override
                        public void stop() {
                            resetFishing(c);
                        }
                    }, 5);
                    CycleEventHandler.getSingleton().addEvent(c,
                            new CycleEvent() {

                                @Override
                                public void execute(
                                        CycleEventContainer container) {
                                    if (c.playerSkillProp[Constants.FISHING][5] > 0) {
                                        if (c.playerLevel[Constants.FISHING] >= c.playerSkillProp[Constants.FISHING][6]) {
                                            c.playerSkillProp[Constants.FISHING][1] = c.playerSkillProp[Constants.FISHING][Misc
                                                    .random(1) == 0 ? 7 : 5];
                                        }
                                    }
                                    if (c.playerSkillProp[Constants.FISHING][2] > 0) {
                                        c.getPlayerAssistant().addSkillXP(
                                                c.playerSkillProp[Constants.FISHING][2],
                                                Constants.FISHING);
                                    }
                                    if (c.playerSkillProp[Constants.FISHING][1] > 0) {
                                        c.getItemAssistant().addItem(c.playerSkillProp[Constants.FISHING][1], 1);
                                        c.startAnimation(c.playerSkillProp[Constants.FISHING][0]);
                                        c.getDialogueHandler().sendDialogues(3019, -1);
                                        container.stop();
                                    }
                                    if (!noInventorySpace(c, "fishing")) {
                                        container.stop();
                                    }
                                    if (!c.stopPlayerSkill) {
                                        container.stop();
                                    }
                                    if (!c.playerSkilling[Constants.FISHING]) {
                                        container.stop();
                                    }
                                }

                                @Override
                                public void stop() {
                                    resetFishing(c);
                                }
                            }, getTimer(c, npcId) + 5 + playerFishingLevel(c));
                    return;
                }

                // end of tutorial island fishing

                c.getPacketSender().sendMessage("" + messages(c));
                // c.getPA().sendSound(379, 100, 1); // fishing
                c.startAnimation(c.playerSkillProp[Constants.FISHING][0]);
                c.stopPlayerSkill = true;
                CycleEventHandler.getSingleton().addEvent(eventId, c,
                        new CycleEvent() {

                            @Override
                            public void execute(CycleEventContainer container) {
                                if (c.playerSkillProp[Constants.FISHING][3] > 0) {

                                    if (!c.getItemAssistant().playerHasItem(
                                            c.playerSkillProp[Constants.FISHING][3])) {
                                        c.getPacketSender()
                                                .sendMessage(
                                                        "You don't have any "
                                                                + DeprecatedItems
                                                                .getItemName(c.playerSkillProp[Constants.FISHING][3])
                                                                + " left!");
                                        c.getPacketSender()
                                                .sendMessage(
                                                        "You need "
                                                                + DeprecatedItems
                                                                .getItemName(c.playerSkillProp[Constants.FISHING][3])
                                                                + " to fish here.");
                                        resetFishing(c);
                                        container.stop();
                                    }
                                }
                                if (c.playerSkillProp[Constants.FISHING][5] > 0) {
                                    if (c.playerLevel[Constants.FISHING] >= c.playerSkillProp[Constants.FISHING][6]) {
                                        c.playerSkillProp[Constants.FISHING][1] = c.playerSkillProp[Constants.FISHING][Misc
                                                .random(1) == 0 ? 7 : 5];
                                    }
                                }
                                if (!hasFishingEquipment(c,
                                        c.playerSkillProp[Constants.FISHING][4])) {
                                    resetFishing(c);
                                    container.stop();
                                }
                                if (!noInventorySpace(c, "fishing")) {
                                    resetFishing(c);
                                    container.stop();
                                }
                                if (!c.stopPlayerSkill) {
                                    container.stop();
                                }
                                if (!c.playerSkilling[Constants.FISHING]) {
                                    resetFishing(c);
                                    container.stop();
                                }
                                if (c.playerSkillProp[Constants.FISHING][1] > 0) {
                                    c.startAnimation(c.playerSkillProp[Constants.FISHING][0]);
                                    // c.getPA().sendSound(379, 100, 1); //
                                    // fishing
                                }

                            }

                            @Override
                            public void stop() {
                            }
                        }, 5);
                CycleEventHandler.getSingleton().addEvent(eventId, c,
                        new CycleEvent() {

                            @Override
                            public void execute(CycleEventContainer container) {
                                if (c.playerSkillProp[Constants.FISHING][5] > 0) {
                                    if (c.playerLevel[Constants.FISHING] >= c.playerSkillProp[Constants.FISHING][6]) {
                                        c.playerSkillProp[Constants.FISHING][1] = c.playerSkillProp[Constants.FISHING][Misc
                                                .random(1) == 0 ? 7 : 5];
                                    }
                                }
                                if (c.playerSkillProp[Constants.FISHING][1] > 0) {
                                    c.getPacketSender()
                                            .sendMessage(
                                                    "You catch "
                                                            + (c.playerSkillProp[Constants.FISHING][1] == RAW_ANCHOVIES
                                                            || c.playerSkillProp[Constants.FISHING][1] == RAW_SHRIMPS
                                                            || c.playerSkillProp[Constants.FISHING][1] == RAW_MONKFISH ? "some "
                                                            : "a ")
                                                            + DeprecatedItems
                                                            .getItemName(
                                                                    c.playerSkillProp[Constants.FISHING][1])
                                                            .toLowerCase()
                                                            .replace(
                                                                    "raw ",
                                                                    "")
                                                            + ".");
                                }
                                if (c.playerSkillProp[Constants.FISHING][1] > 0 && c.randomEventsEnabled) {
                                    randomEvents(c);
                                }
                                if (c.playerSkillProp[Constants.FISHING][1] > 0) {
                                    c.getItemAssistant().deleteItem(c.playerSkillProp[Constants.FISHING][3], c.getItemAssistant().getItemSlot(c.playerSkillProp[Constants.FISHING][3]), 1);
                                    c.getItemAssistant().addItem(c.playerSkillProp[Constants.FISHING][1], 1);
                                    c.startAnimation(c.playerSkillProp[Constants.FISHING][0]);
                                }
                                if (c.playerSkillProp[Constants.FISHING][5] > 0
                                        && c.playerLevel[Constants.FISHING] >= c.playerSkillProp[Constants.FISHING][6]) {
                                    c.getPlayerAssistant().addSkillXP(
                                            c.playerSkillProp[Constants.FISHING][8],
                                            Constants.FISHING);
                                } else if (c.playerSkillProp[Constants.FISHING][7] > 0) {
                                    c.getPlayerAssistant().addSkillXP(
                                            c.playerSkillProp[Constants.FISHING][2],
                                            Constants.FISHING);
                                }
                                if (c.playerSkillProp[Constants.FISHING][3] > 0) {
                                    if (!c.getItemAssistant().playerHasItem(
                                            c.playerSkillProp[Constants.FISHING][3])) {
                                        c.getDialogueHandler()
                                                .sendStatement(
                                                        "You have run out of "
                                                                + DeprecatedItems
                                                                .getItemName(
                                                                        c.playerSkillProp[Constants.FISHING][3])
                                                                .toLowerCase()
                                                                .toLowerCase()
                                                                + ".");
                                        // c.getPacketDispatcher().sendMessage("You don't have any "+
                                        // ItemAssistant.getItemName(c.playerSkillProp[Constants.FISHING][3])
                                        // +" left!");
                                        // c.getPacketDispatcher().sendMessage("You need "+
                                        // ItemAssistant.getItemName(c.playerSkillProp[Constants.FISHING][3])
                                        // +" to fish here.");
                                        container.stop();
                                    }
                                }
                                if (!hasFishingEquipment(c,
                                        c.playerSkillProp[Constants.FISHING][4])) {
                                    resetFishing(c);
                                    container.stop();
                                }
                                if (!noInventorySpace(c, "fishing")) {
                                    resetFishing(c);
                                    container.stop();
                                }
                                if (!c.stopPlayerSkill) {
                                    container.stop();
                                }
                                if (!c.playerSkilling[Constants.FISHING]) {
                                    resetFishing(c);
                                    container.stop();
                                }
                            }

                            @Override
                            public void stop() {
                            }
                        }, getTimer(c, npcId) + 5 + playerFishingLevel(c));
            }
        }
    }

    public static boolean hasFishingEquipment(Player c, int equipment) {
        if (!c.getItemAssistant().playerHasItem(equipment)) {
            if (equipment == HARPOON) {
                if (!c.getItemAssistant().playerHasItem(HARPOON)) {
                    c.getPacketSender().sendMessage(
                            "You need a "
                                    + DeprecatedItems.getItemName(equipment)
                                    .toLowerCase() + " to fish here.");
                    resetFishing(c);
                    return false;
                }
            } else {
                resetFishing(c);
                c.getPacketSender().sendMessage(
                        "You need a " + DeprecatedItems.getItemName(equipment)
                                + " to fish here.");
                return false;
            }
        }
        return true;
    }

    public static void resetFishing(Player player) {
        player.startAnimation(65535);
        player.stopPlayerSkill = false;
        player.playerSkilling[Constants.FISHING] = false;
        player.fishingWhirlPool = false;
        stopEvents(player, eventId);
        for (int i = 0; i < 11; i++) {
            player.playerSkillProp[Constants.FISHING][i] = -1;
        }
    }

    public static String messages(Player c) {
        if (c.playerSkillProp[Constants.FISHING][10] == 1 || c.playerSkillProp[Constants.FISHING][10] == 9) {
            // etc
            return messages[0][0];
        }

        if (c.playerSkillProp[Constants.FISHING][10] == 2 || c.playerSkillProp[Constants.FISHING][10] == 3
                || c.playerSkillProp[Constants.FISHING][10] == 4
                || c.playerSkillProp[Constants.FISHING][10] == 5
                || c.playerSkillProp[Constants.FISHING][10] == 6) {
            return messages[1][0];
        }

        if (c.playerSkillProp[Constants.FISHING][10] == 7 || c.playerSkillProp[Constants.FISHING][10] == 10) {
            return messages[2][0];
        }

        if (c.playerSkillProp[Constants.FISHING][10] == 8) {
            return messages[3][0];
        }

        return null;
    }

    private static int playerFishingLevel(Player c) {
        return 10 - (int) Math.floor(c.playerLevel[Constants.FISHING] / 10);
    }

    private final static int getTimer(Player c, int npcId) {
        switch (npcId) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 4;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 5;
            case 8:
                return 5;
            case 9:
                return 8;
            case 10:
                return 12;
            case 11:
                return 13;
            case 12:
                return 13;
            default:
                return -1;
        }
    }

    public static void fishingNPC(Player c, int i, int l) {
        switch (i) {
            case 1:
                switch (l) {
                    // NET + BAIT
                    case FISHING_SPOT_316:
                    case FISHING_SPOT_319:
                    case FISHING_SPOT_323:
                    case FISHING_SPOT_325:
                    case FISHING_SPOT_326:
                    case FISHING_SPOT_327:
                    case FISHING_SPOT_329:
                    case FISHING_SPOT_330:
                    case FISHING_SPOT_333:
                    case FISHING_SPOT_404:
                        Fishing.attemptdata(c, 1);
                        break;
                    // NET + HARPOON
                    case FISHING_SPOT_313:
                    case FISHING_SPOT_334:
                        Fishing.attemptdata(c, 3);
                        break;
                    // NET + HARPOON
                    case FISHING_SPOT_322:
                        Fishing.attemptdata(c, 5);
                        break;
                    // LURE
                    case FISHING_SPOT_309:
                    case FISHING_SPOT_310:
                    case FISHING_SPOT_311:
                    case FISHING_SPOT_314:
                    case FISHING_SPOT_315:
                    case FISHING_SPOT_317:
                    case FISHING_SPOT_318:
                    case FISHING_SPOT_328:
                    case FISHING_SPOT_331:
                    case FISHING_SPOT_403:
                        Fishing.attemptdata(c, 4);
                        break;
                    case FISHING_SPOT_1191:
                        Fishing.attemptdata(c, 9);
                        break;
                    // CAGE + HARPOON
                    case FISHING_SPOT_312:
                    case FISHING_SPOT_321:
                    case FISHING_SPOT_324:
                    case FISHING_SPOT_405:
                        Fishing.attemptdata(c, 8);
                        break;
                }
                break;
            case 2:
                switch (l) {
                    // BAIT + NET
                    case FISHING_SPOT_316:
                    case FISHING_SPOT_326:
                    case FISHING_SPOT_327:
                    case FISHING_SPOT_330:
                    case FISHING_SPOT_332:
                    case FISHING_SPOT_404:
                        Fishing.attemptdata(c, 2);
                        break;
                    case FISHING_SPOT_319:
                    case FISHING_SPOT_323:
                    case FISHING_SPOT_325: // BAIT + NET
                        Fishing.attemptdata(c, 9);
                        break;
                    // BAIT + LURE
                    case FISHING_SPOT_309:
                    case FISHING_SPOT_310:
                    case FISHING_SPOT_311:
                    case FISHING_SPOT_314:
                    case FISHING_SPOT_315:
                    case FISHING_SPOT_317:
                    case FISHING_SPOT_318:
                    case FISHING_SPOT_328:
                    case FISHING_SPOT_329:
                    case FISHING_SPOT_331:
                    case FISHING_SPOT_403:
                        Fishing.attemptdata(c, 6);
                        break;
                    // SWORDIES+TUNA-CAGE+HARPOON
                    case FISHING_SPOT_312:
                    case FISHING_SPOT_321:
                    case FISHING_SPOT_324:
                    case FISHING_SPOT_405:
                    case FISHING_SPOT_1191:
                        Fishing.attemptdata(c, 7);
                        break;
                    // NET+HARPOON
                    case FISHING_SPOT_313:
                    case FISHING_SPOT_322:
                    case FISHING_SPOT_334:
                        Fishing.attemptdata(c, 10);
                        break;
                }
                break;
        }
    }

    public static boolean fishingNPC(Player c, int npc) {
        for (int i = MASTER_FISHER; i < NULL_335; i++) {
            if (npc == i) {
                return true;
            }
            if (npc == FISHING_SPOT_1191) {
                return true;
            }
        }
        return false;
    }
}