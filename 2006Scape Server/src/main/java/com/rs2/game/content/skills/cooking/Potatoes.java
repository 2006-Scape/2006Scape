package com.rs2.game.content.skills.cooking;

import com.rs2.Constants;
import com.rs2.game.content.StaticItemList;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.players.Player;

import java.util.HashMap;
import java.util.Map;

public class Potatoes extends SkillHandler {

    Player c;

    public Potatoes(Player player) {
        this.c = player;
    }

    public enum PotatoMaking {
        // first item, new item, xp, level required
        // BUTTERED(6701, 6703, 6697, 95, 39),
        CHILLI(StaticItemList.CHILLI_CON_CARNE, StaticItemList.CHILLI_POTATO, 165, 41),
        CHEESE(StaticItemList.CHEESE, StaticItemList.POTATO_WITH_CHEESE, 199, 47),
        EGG(StaticItemList.EGG_AND_TOMATO, StaticItemList.EGG_POTATO, 195, 51),
        MUSHROOM(StaticItemList.MUSHROOM__ONION, StaticItemList.MUSHROOM_POTATO, 27, 64),
        TUNA(StaticItemList.TUNA_AND_CORN, StaticItemList.TUNA_POTATO, 309, 68);

        /**
         * Seperate integers for the id's.
         */
        private int newPotatoID, ingredient, XP, levelReq;

        /**
         * @param ingredient
         * @param newPotatoID
         * @param XP
         * @param levelReq
         */
        private PotatoMaking(int ingredient, int newPotatoID, int XP,
                             int levelReq) {
            // this.potatoID = potatoID;
            this.ingredient = ingredient;
            this.newPotatoID = newPotatoID;
            this.levelReq = levelReq;
            this.XP = XP;
        }

        public int getNewPotatoID() {
            return newPotatoID;
        }

        public int getIngredient() {
            return ingredient;
        }

        public int getReq() {
            return levelReq;
        }

        public int getXP() {
            return XP;
        }

        private static final Map<Integer, PotatoMaking> potato = new HashMap<Integer, PotatoMaking>();

        public static PotatoMaking forId(int id) {
            return potato.get(id);
        }

        static {
            for (PotatoMaking p : PotatoMaking.values()) {
                potato.put(p.getIngredient(), p);
            }
        }
    }

    /**
     * Id used with one or the other
     *
     * @param id1
     * @param id2
     */
    public void handlePotato(int id1, int id2) {
        makePotato(id1 == StaticItemList.POTATO_WITH_BUTTER ? id2 : id1);
    }

    /**
     * Creating the actual item and replacing the id's
     *
     * @param id
     * @return
     */
    public boolean makePotato(int id) {
        PotatoMaking potato = PotatoMaking.forId(id);
        if (potato == null) {
            return false;
        }
        if (!COOKING) {
            c.getPacketSender().sendMessage("This skill is currently disabled.");
            return false;
        }
        if (c.getItemAssistant().playerHasItem(potato.getIngredient(), 1)) {
            if (c.playerLevel[Constants.COOKING] >= potato.getReq()) {
                c.getItemAssistant().deleteItem(potato.getIngredient(), 1);
                c.getItemAssistant().deleteItem(StaticItemList.POTATO_WITH_BUTTER, 1);
                c.getPacketSender().sendMessage("You put the topping on.");
                c.getItemAssistant().addItem(potato.getNewPotatoID(), 1);
                c.getPlayerAssistant().addSkillXP(potato.getXP(), Constants.COOKING);
                RandomEventHandler.addRandom(c);
            } else {
                c.getPacketSender().sendMessage("You need a cooking level of " + potato.getReq() + " to make this potato.");
            }
        }
        return false;
    }

}
