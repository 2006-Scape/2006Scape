package com.rs2.game.content.skills.crafting;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.players.Player;

import static com.rs2.game.content.StaticItemList.*;

/**
 * Battle Staff making
 *
 * @author Andrew (Mr Extremez)
 */
public class BattleStaffs {

    public static enum battleStaffs {

        AIR(AIR_ORB, 66, 138, AIR_BATTLESTAFF),
        FIRE(FIRE_ORB, 62, 125, FIRE_BATTLESTAFF),
        EARTH(EARTH_ORB, 58, 113, EARTH_BATTLESTAFF),
        WATER(WATER_ORB, 54, 100, WATER_BATTLESTAFF);

        private int orb, levelReq, xpRecieved, battlestaff;

        private battleStaffs(final int orb, final int levelReq, final int xpRecieved, final int battlestaff) {
            this.orb = orb;
            this.levelReq = levelReq;
            this.xpRecieved = xpRecieved;
            this.battlestaff = battlestaff;
        }

        public int getOrb() {
            return orb;
        }

        public int getLevelReq() {
            return levelReq;
        }

        public int getXP() {
            return xpRecieved;
        }

        public int getBattlestaff() {
            return battlestaff;
        }
    }

    public static void makeBattleStaff(Player player, int itemUsed, int usedWith) {
        for (final battleStaffs b : battleStaffs.values()) {
            if ((itemUsed == b.getOrb() && usedWith == BATTLESTAFF) || (itemUsed == BATTLESTAFF && usedWith == b.getOrb())) {
                if (player.getItemAssistant().playerHasItem(BATTLESTAFF) && player.getItemAssistant().playerHasItem(b.getOrb())) {
                    if (player.isCrafting == true) {
                        return;
                    }
                    if (player.playerLevel[Constants.CRAFTING] < b.getLevelReq()) {
                        player.getPacketSender().sendMessage("You need level " + b.getLevelReq() + " crafting to do that.");
                        return;
                    }
                    player.getPacketSender().closeAllWindows();
                    player.isCrafting = true;
                    player.skillAmount = player.getItemAssistant().getItemAmount(b.getOrb());
                    CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
                        @Override
                        public void execute(CycleEventContainer container) {
                            if (player.isCrafting == true && player != null) {
                                if (!player.getItemAssistant().playerHasItem(BATTLESTAFF, 1)) {
                                    player.getPacketSender().sendMessage("You have run out of battlestaffs.");
                                    container.stop();
                                    return;
                                }
                                if (!player.getItemAssistant().playerHasItem(b.getOrb(), 1)) {
                                    player.getPacketSender().sendMessage("You have run out of orbs.");
                                    container.stop();
                                    return;
                                }
                                if (player.skillAmount == 0) {
                                    container.stop();
                                    return;
                                }
                                player.getItemAssistant().deleteItem(BATTLESTAFF, 1);
                                player.getItemAssistant().deleteItem(b.getOrb(), 1);
                                player.getItemAssistant().addItem(b.getBattlestaff(), 1);
                                player.getPlayerAssistant().addSkillXP((int) b.getXP(), 6);
                                player.skillAmount--;
                                if (!player.getItemAssistant().playerHasItem(BATTLESTAFF) || !player.getItemAssistant().playerHasItem(b.getOrb())) {
                                    container.stop();
                                    return;
                                }
                            } else {
                                container.stop();
                            }
                        }

                        @Override
                        public void stop() {
                            player.isCrafting = false;
                        }
                    }, 2);
                }
            }
        }
    }


}
