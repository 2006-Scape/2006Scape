package com.rs2.game.content.skills.cooking;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.StaticItemList;
import com.rs2.game.content.StaticObjectList;
import com.rs2.game.content.music.sound.SoundList;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

public class CookingTutorialIsland extends SkillHandler {

    public static void cookThisFood(Player p, int i, int object) {
        switch (i) {
            case StaticItemList.RAW_SHRIMPS:
                cookFish(p, i, 30, 1, StaticItemList.BURNT_FISH, StaticItemList.SHRIMPS, object);
                break;
            default:
                p.getPacketSender().sendMessage("Nothing interesting happens.");
                break;
        }
    }

    private static void cookFish(Player c, int itemID, int xpRecieved,
                                 int levelRequired, int burntFish, int cookedFish, int object) {
        if (!COOKING) {
            c.getPacketSender().sendMessage(
                    "Cooking is currently disabled.");
            return;
        }
        if (!hasRequiredLevel(c, 7, levelRequired, "cooking", "cook this")) {
            return;
        }
        int chance = c.playerLevel[Constants.COOKING];
        if (c.playerEquipment[c.playerHands] == StaticItemList.COOKING_GAUNTLETS) {
            chance = c.playerLevel[Constants.COOKING] + 8;
        }
        if (chance <= 0) {
            chance = Misc.random(5);
        }
        c.playerSkillProp[Constants.COOKING][0] = itemID;
        c.playerSkillProp[Constants.COOKING][1] = xpRecieved;
        c.playerSkillProp[Constants.COOKING][2] = levelRequired;
        c.playerSkillProp[Constants.COOKING][3] = burntFish;
        c.playerSkillProp[Constants.COOKING][4] = cookedFish;
        c.playerSkillProp[Constants.COOKING][5] = object;
        c.playerSkillProp[Constants.COOKING][6] = chance;
        c.stopPlayerSkill = false;
        int item = c.getItemAssistant().getItemAmount(c.playerSkillProp[Constants.COOKING][0]);
        if (item == 1) {
            c.doAmount = 1;
            cookTutFish(c);
            return;
        }
        viewCookInterface(c, itemID);
    }

    public static void getAmount(Player player, int amount) {
        int item = player.getItemAssistant().getItemAmount(player.playerSkillProp[Constants.COOKING][0]);
        if (amount > item) {
            amount = item;
        }
        player.doAmount = amount;
        cookTutFish(player);
    }

    public static void resetCooking(Player c) {
        c.playerSkilling[Constants.COOKING] = false;
        c.stopPlayerSkill = false;
        for (int i = 0; i < 6; i++) {
            c.playerSkillProp[Constants.COOKING][i] = -1;
        }
    }

    private static void viewCookInterface(Player c, int item) {
        c.getPacketSender().sendChatInterface(1743);
        c.getPacketSender().sendFrame246(13716, 190, item);
        c.getPacketSender().sendString(
                "" + DeprecatedItems.getItemName(item) + "", 13717);
    }

    private static void cookTutFish(final Player c) {
        if (c.playerSkilling[Constants.COOKING]) {
            return;
        }
        if (c.tutorialProgress == 6) {
            c.playerSkilling[Constants.COOKING] = true;
            c.stopPlayerSkill = true;
            c.getPacketSender().closeAllWindows();
            if (c.playerSkillProp[Constants.COOKING][5] > 0) {
                // c.startAnimation(c.playerSkillProp[Constants.COOKING][5] == StaticObjectList.FIRE ? 897 :
                // 896);
                c.startAnimation(c.playerSkillProp[Constants.COOKING][5] == StaticObjectList.FIRE ? 897
                        : c.playerSkillProp[Constants.COOKING][5] == StaticObjectList.STOVE_12269 ? 897 : 896);
                if (Constants.SOUND) {
                    c.getPacketSender().sendSound(SoundList.COOK_ITEM, 100,
                            0);
                }

            }
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

                @Override
                public void execute(CycleEventContainer container) {
                    c.getItemAssistant().deleteItem(
                            c.playerSkillProp[Constants.COOKING][0],
                            c.getItemAssistant().getItemSlot(
                                    c.playerSkillProp[Constants.COOKING][0]), 1);
                    if (c.cookStage1 == 1) {
                        c.getPacketSender().chatbox(6180);
                        c.getDialogueHandler()
                                .chatboxText(
                                        "You have just burned your first shrimp. This is normal. As you",
                                        "get more experience in Cooking, you will burn stuff less often.",
                                        "Let's try cooking without burning it this time. First catch some",
                                        "more shrimp then use them on a fire.",
                                        "Burning your shrimp.");
                        c.getPacketSender().chatbox(6179);
                        c.cookStage1 = 0;
                        c.getItemAssistant()
                                .addItem(c.playerSkillProp[Constants.COOKING][3], 1);
                    } else {
                        c.getPacketSender().chatbox(6180);
                        c.getDialogueHandler()
                                .chatboxText(
                                        "If you'd like a recap on anything you've learnt so far, speak to",
                                        "the Survival Expert. You can now move on to the next",
                                        "instructor. Click on the gate shown and follow the path.",
                                        "Remember, you can move the camera with the arrow keys.",
                                        "Well done, you've just cooked your first RuneScape meal");
                        c.getPacketSender().chatbox(6179);
                        c.getPacketSender().createArrow(3089, 3092,
                                c.getH(), 2);
                        c.getPlayerAssistant().addSkillXP(
                                c.playerSkillProp[Constants.COOKING][1], 7);
                        c.getItemAssistant()
                                .addItem(c.playerSkillProp[Constants.COOKING][4], 1);
                        c.tutorialProgress = 7;
                    }
                    deleteTime(c);
                    if (!c.getItemAssistant().playerHasItem(
                            c.playerSkillProp[Constants.COOKING][0], 1)
                            || c.doAmount <= 0) {
                        container.stop();
                    }
                    if (!c.stopPlayerSkill) {
                        container.stop();
                    }
                }

                @Override
                public void stop() {
                    resetCooking(c);
                }
            }, 4);
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

                @Override
                public void execute(CycleEventContainer container) {
                    if (c.playerSkillProp[Constants.COOKING][5] > 0) {
                        // c.getPacketSender().sendSound(357, 100, 1); //
                        // cook sound
                        c.startAnimation(c.playerSkillProp[Constants.COOKING][5] == StaticObjectList.FIRE ? 897
                                : 896);
                    }
                    if (!c.stopPlayerSkill) {
                        container.stop();
                    }
                }

                @Override
                public void stop() {

                }
            }, 4);
            return;
        }
    }
}
