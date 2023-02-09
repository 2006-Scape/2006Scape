package com.rs2.game.content.skills.thieving;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

import java.util.Arrays;

public class SearchForTraps {
    public enum chestsWithTraps {

        ARDOUGNE_NATURE_RUNE_CHEST(2568, 28, 25, new int[][]{
            {561, 1},
            {995, 3},
        }, false),
        ARDOUGNE_10_COIN_CHEST(2566, 13, 25, new int[][]{
                {995, 10}
        }, false),
        ARDOUGNE_50_COIN_CHEST(2567, 43, 125, new int[][]{
                {995, 50}
        }, false),
        ARDOUGNE_CASTLE_CHEST(2570, 72, 500, new int[][]{
                {995, 1000},
                {383, 1},
                {1623, 1},
                {449, 1}
        }, true);
        private final int chestId;
        private final int levelReq;
        private final double xp;
        private final int[][] reward;
        private final boolean randomizeRewards;
        private long resetTime;

        private chestsWithTraps(final int chestId, final int levelReq, final double xp, int[][] reward, boolean randomizeRewards) {
            this.chestId = chestId;
            this.levelReq = levelReq;
            this.xp = xp;
            this.reward = reward;
            this.randomizeRewards = randomizeRewards;
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
    }

    public static void searchForTraps(final Player client, final int chestId) {
        if (ThieveOther.thievingEnabled(client)) {
            for (final chestsWithTraps chest : chestsWithTraps.values()) {
                if (chest.getChestId() == chestId) {
                    if (client.playerLevel[Constants.THIEVING] < chest.getLevelReq()) {
                        client.getDialogueHandler().sendStatement("You must have a thieving level of " + chest.getLevelReq() + " to steal from this chest.");
                        return;
                    }

                    if (System.currentTimeMillis() < chest.resetTime) {
                        long timeFirstSearchForTraps = chest.resetTime - (Constants.CYCLE_TIME * getChestResetTime(chest.getChestId()));
                        if (client.hasSearchedForTraps() || System.currentTimeMillis() - timeFirstSearchForTraps >= Constants.CYCLE_TIME) {
                            client.getPacketSender().sendMessage("This chest has already been looted");
                            return;
                        }
                    }

                    client.getPacketSender().sendMessage("You search the chest for traps");
                    client.startAnimation(536);
                    RandomEventHandler.addRandom(client);
                    int resetTime = getChestResetTime(chest.getChestId());
                    client.getPacketSender().sendMessage("You find a trap on the chest...");
                    client.getPacketSender().sendMessage("You disable the trap");
                    client.getPlayerAssistant().addSkillXP((int) chest.getXp(), Constants.THIEVING);
                    chest.resetTime = System.currentTimeMillis() + (resetTime * Constants.CYCLE_TIME);
                    client.getPacketSender().sendMessage("You open the chest");
                    client.startAnimation(536);

                    if (chest.getReward() != null && !chest.randomizeRewards) {
                        Arrays.stream(chest.reward).forEach(reward -> {
                            client.getItemAssistant().addOrDropItem(reward[0], reward[1]);
                        });
                        client.getPacketSender().sendMessage("You find treasure inside!");
                    } else if (chest.getReward() != null && chest.randomizeRewards) {
                        int randomizedReward = randomizeReward(chest.reward);
                        int[] reward = chest.reward[randomizedReward];
                        client.getItemAssistant().addOrDropItem(reward[0], reward[1]);
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
        int resetTime = 0;
        switch (i) {
            case 2566:
                resetTime = 7;
                break;
            case 2567:
                resetTime = 70;
                break;
            case 2568:
                resetTime = 10;
                break;
            case 2570:
                resetTime = 240;
                break;
        }
        return resetTime;
    }

    private static int randomizeReward(int[][] items) {
        return Misc.random(items.length);
    }
}
