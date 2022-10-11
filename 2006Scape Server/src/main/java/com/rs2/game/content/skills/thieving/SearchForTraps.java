package com.rs2.game.content.skills.thieving;

import com.rs2.GameConstants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.players.Player;

import java.util.Arrays;

public class SearchForTraps {
    private static boolean isSearchingForTraps = false;

    public static enum chestsWithTraps {

        ARDOUGNE_NATURE_RUNE_CHEST(2568, 1, 125, new int[][]{ // change levelReq to 28
            {561, 1},
            {995, 3},
        }, 8000, 8),
        ARDOUGNE_10_COIN_CHEST(2566, 1, 125, new int[][]{
                {995, 10}
        }, 8000, 8),
        ARDOUGNE_EMPTY_CHEST(2567, 1, 125, null, 8000, 8);

        private final int chestId;
        private final int levelReq;
        private final double xp;
        private final int[][] reward;
        private long resetTime;
        private final int hardness;

        private chestsWithTraps(final int chestId, final int levelReq, final double xp, int[][] reward, long resetTime, int hardness) {
            this.chestId = chestId;
            this.levelReq = levelReq;
            this.xp = xp;
            this.reward = reward;
            this.hardness = hardness;
            this.resetTime = System.currentTimeMillis();
        }

        public int getChestId() {
            return chestId;
        }

        public int getLevelReq() {
            return levelReq;
        }

        public double getXp() {
            return xp;
        }

        public int[][] getReward() {
            return reward;
        }

        public int getHardness() {
            return hardness;
        }
    }

    private static boolean isChestWithTrap(int providedObjectId) {
        for (final chestsWithTraps chests : chestsWithTraps.values()) {
            if (providedObjectId == chests.getChestId()) {
                return true;
            }
        }
        return false;
    }

    public static void searchForTraps(final Player client, final int chestId) {
        if (canSearchForTraps(client) && ThieveOther.thievingEnabled(client)) {
            for (final chestsWithTraps chest : chestsWithTraps.values()) {
                if (chest.getChestId() == chestId) {
                    if (client.playerLevel[GameConstants.THIEVING] < chest.getLevelReq()) {
                        client.getDialogueHandler().sendStatement("You must have a thieving level of " + chest.getLevelReq() + " to steal from this chest.");
                        return;
                    }

                    if (System.currentTimeMillis() < chest.resetTime) {
                        long timeFirstSearchForTraps = chest.resetTime - (GameConstants.CYCLE_TIME * getChestResetTime(chest.getChestId()));
                        if (client.hasSearchedForTraps() || System.currentTimeMillis() - timeFirstSearchForTraps >= GameConstants.CYCLE_TIME) {
                            client.getPacketSender().sendMessage("This chest has already been looted");
                            return;
                        }
                    }

                    client.getPacketSender().sendMessage("You search the chest for traps");
                    client.startAnimation(536); // open chest animation?
                    RandomEventHandler.addRandom(client);
                    int resetTime = getChestResetTime(chest.getChestId());
                    client.getPacketSender().sendMessage("You find a trap on the chest...");
                    client.getPacketSender().sendMessage("You disable the trap");
                    client.getPlayerAssistant().addSkillXP((int) chest.getXp(), GameConstants.THIEVING);
                    chest.resetTime = System.currentTimeMillis() + (resetTime * GameConstants.CYCLE_TIME);
                    client.getPacketSender().sendMessage("You open the chest");
                    client.startAnimation(536);

                    if (chest.getReward() != null) {
                        Arrays.stream(chest.reward).forEach(reward -> {
                            client.getItemAssistant().addOrDropItem(reward[0], reward[1]);
                        });
                        client.getPacketSender().sendMessage("You find treasure inside!");
                    } else {
                        client.getPacketSender().sendMessage("The chest is empty");
                    }
                    client.setHasSearchedForTraps(true);
                    CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {
                            client.setHasSearchedForTraps(false);
                            container.stop();
                        }
                        @Override
                        public void stop() {
                        }
                    }, resetTime);
                }
            }
        }
    }

    private static int getChestResetTime(int i) {
        switch (i) {
            case 2566:
                return 50;// ardougne nat rune chest
        }
        return 5;
    }


    private static boolean canSearchForTraps(Player client) {
        if (System.currentTimeMillis() - client.lastSearchedForTraps < 8000 || System.currentTimeMillis() - client.logoutDelay < 4000) {
            return false;
        }
        return true;
    }



}
