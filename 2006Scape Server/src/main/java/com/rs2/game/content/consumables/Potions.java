package com.rs2.game.content.consumables;

import com.rs2.Constants;
import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.event.CycleEventHandler;
import com.rs2.game.items.DeprecatedItems;
import com.rs2.game.players.Player;
import org.apollo.cache.def.ItemDefinition;

import static com.rs2.game.content.StaticItemList.*;

public class Potions {

    private final Player c;

    public Potions(Player player) {
        this.c = player;
    }

    public void handlePotion(int itemId, int slot) {
        if (c.duelRule[5]) {
            c.getPacketSender().sendMessage(
                    "You may not drink potions in this duel.");
            return;
        }
        if (c.isDead || c.playerLevel[Constants.HITPOINTS] <= 0) {
            return;
        }
        if (System.currentTimeMillis() - c.potDelay >= 1200) {
            c.potDelay = System.currentTimeMillis();
            c.foodDelay = System.currentTimeMillis();
            c.getCombatAssistant().resetPlayerAttack();
            c.attackTimer++;
            c.getPacketSender().sendMessage(
                    "You drink some of your " + ItemDefinition.lookup(itemId).getName() + ".");
            c.startAnimation(829);
            final String item = ItemDefinition.lookup(itemId).getName();
            String m = "";
            if (item.endsWith("(4)")) {
                m = "You have 3 doses of potion left.";
            } else if (item.endsWith("(3)")) {
                m = "You have 2 doses of potion left.";
            } else if (item.endsWith("(2)")) {
                m = "You have 1 dose of potion left.";
            } else if (item.endsWith("(1)")) {
                m = "You have finished your potion.";
            }
            final String m1 = m;
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
                @Override
                public void execute(CycleEventContainer container) {
                    c.getPacketSender().sendSound(1210, 100, 0);
                    c.getPacketSender().sendMessage(m1);
                    container.stop();
                }

                @Override
                public void stop() {

                }
            }, 1);
            switch (itemId) {
                case MAGIC_POTION4:
                    drinkStatPotion(itemId, MAGIC_POTION3, slot, 6, false);
                    break;
                case MAGIC_POTION3:
                    drinkStatPotion(itemId, MAGIC_POTION2, slot, 6, false);
                    break;
                case MAGIC_POTION2:
                    drinkStatPotion(itemId, MAGIC_POTION1, slot, 6, false);
                    break;
                case MAGIC_POTION1:
                    drinkStatPotion(itemId, VIAL, slot, 6, false);
                    break;
                case ZAMORAK_BREW4:
                    doTheBrewzam(itemId, ZAMORAK_BREW3, slot);
                    break;
                case ZAMORAK_BREW3:
                    doTheBrewzam(itemId, ZAMORAK_BREW2, slot);
                    break;
                case ZAMORAK_BREW2:
                    doTheBrewzam(itemId, ZAMORAK_BREW1, slot);
                    break;
                case ZAMORAK_BREW1:
                    doTheBrewzam(itemId, VIAL, slot);
                    break;
                case SARADOMIN_BREW4:
                    doTheBrew(itemId, SARADOMIN_BREW3, slot);
                    break;
                case SARADOMIN_BREW3:
                    doTheBrew(itemId, SARADOMIN_BREW2, slot);
                    break;
                case SARADOMIN_BREW2:
                    doTheBrew(itemId, SARADOMIN_BREW1, slot);
                    break;
                case SARADOMIN_BREW1:
                    doTheBrew(itemId, VIAL, slot);
                    break;
                case SUPER_ATTACK4:
                    drinkStatPotion(itemId, SUPER_ATTACK3, slot, 0, true);
                    break;
                case SUPER_ATTACK3:
                    drinkStatPotion(itemId, SUPER_ATTACK2, slot, 0, true);
                    break;
                case SUPER_ATTACK2:
                    drinkStatPotion(itemId, SUPER_ATTACK1, slot, 0, true);
                    break;
                case SUPER_ATTACK1:
                    drinkStatPotion(itemId, VIAL, slot, 0, true);
                    break;
                case SUPER_STRENGTH4:
                    drinkStatPotion(itemId, SUPER_STRENGTH3, slot, 2, true);
                    break;
                case SUPER_STRENGTH3:
                    drinkStatPotion(itemId, SUPER_STRENGTH2, slot, 2, true);
                    break;
                case SUPER_STRENGTH2:
                    drinkStatPotion(itemId, SUPER_STRENGTH1, slot, 2, true);
                    break;
                case SUPER_STRENGTH1:
                    drinkStatPotion(itemId, VIAL, slot, 2, true);
                    break;
                case RANGING_POTION4:
                    drinkStatPotion(itemId, RANGING_POTION3, slot, 4, false);
                    break;
                case RANGING_POTION3:
                    drinkStatPotion(itemId, RANGING_POTION2, slot, 4, false);
                    break;
                case RANGING_POTION2:
                    drinkStatPotion(itemId, RANGING_POTION1, slot, 4, false);
                    break;
                case RANGING_POTION1:
                    drinkStatPotion(itemId, VIAL, slot, 4, false);
                    break;
                case DEFENCE_POTION4:
                    drinkStatPotion(itemId, DEFENCE_POTION3, slot, 1, false);
                    break;
                case DEFENCE_POTION3:
                    drinkStatPotion(itemId, DEFENCE_POTION2, slot, 1, false);
                    break;
                case DEFENCE_POTION2:
                    drinkStatPotion(itemId, DEFENCE_POTION1, slot, 1, false);
                    break;
                case DEFENCE_POTION1:
                    drinkStatPotion(itemId, VIAL, slot, 1, false);
                    break;
                case STRENGTH_POTION4:
                    drinkStatPotion(itemId, STRENGTH_POTION3, slot, 2, false);
                    break;
                case STRENGTH_POTION3:
                    drinkStatPotion(itemId, STRENGTH_POTION2, slot, 2, false);
                    break;
                case STRENGTH_POTION2:
                    drinkStatPotion(itemId, STRENGTH_POTION1, slot, 2, false);
                    break;
                case STRENGTH_POTION1:
                    drinkStatPotion(itemId, VIAL, slot, 2, false);
                    break;
                case ATTACK_POTION4:
                    drinkStatPotion(itemId, ATTACK_POTION3, slot, 0, false);
                    break;
                case ATTACK_POTION3:
                    drinkStatPotion(itemId, ATTACK_POTION2, slot, 0, false);
                    break;
                case ATTACK_POTION2:
                    drinkStatPotion(itemId, ATTACK_POTION1, slot, 0, false);
                    break;
                case ATTACK_POTION1:
                    drinkStatPotion(itemId, VIAL, slot, 0, false);
                    break;
                case SUPER_DEFENCE4:
                    drinkStatPotion(itemId, SUPER_DEFENCE3, slot, 1, true);
                    break;
                case SUPER_DEFENCE3:
                    drinkStatPotion(itemId, SUPER_DEFENCE2, slot, 1, true);
                    break;
                case SUPER_DEFENCE2:
                    drinkStatPotion(itemId, SUPER_DEFENCE1, slot, 1, true);
                    break;
                case SUPER_DEFENCE1:
                    drinkStatPotion(itemId, VIAL, slot, 1, true);
                    break;
                case SUPER_RESTORE4:
                    drinkPrayerPot(itemId, SUPER_RESTORE3, slot, true);
                    break;
                case SUPER_RESTORE3:
                    drinkPrayerPot(itemId, SUPER_RESTORE2, slot, true);
                    break;
                case SUPER_RESTORE2:
                    drinkPrayerPot(itemId, SUPER_RESTORE1, slot, true);
                    break;
                case SUPER_RESTORE1:
                    drinkPrayerPot(itemId, VIAL, slot, true);
                    break;
                case PRAYER_POTION4:
                    drinkPrayerPot(itemId, PRAYER_POTION3, slot, false);
                    break;
                case PRAYER_POTION3:
                    drinkPrayerPot(itemId, PRAYER_POTION2, slot, false);
                    break;
                case PRAYER_POTION2:
                    drinkPrayerPot(itemId, PRAYER_POTION1, slot, false);
                    break;
                case PRAYER_POTION1:
                    drinkPrayerPot(itemId, VIAL, slot, false);
                    break;
                case ANTIPOISON4:
                    drinkAntiPoison(itemId, ANTIPOISON3, slot, 30000);
                    break;
                case ANTIPOISON3:
                    drinkAntiPoison(itemId, ANTIPOISON2, slot, 30000);
                    break;
                case ANTIPOISON2:
                    drinkAntiPoison(itemId, ANTIPOISON1, slot, 30000);
                    break;
                case ANTIPOISON1:
                    drinkAntiPoison(itemId, VIAL, slot, 30000);
                    break;
                case SUPERANTIPOISON4:
                    drinkAntiPoison(itemId, SUPERANTIPOISON3, slot, 300000);
                    break;
                case SUPERANTIPOISON3:
                    drinkAntiPoison(itemId, SUPERANTIPOISON2, slot, 300000);
                    break;
                case SUPERANTIPOISON2:
                    drinkAntiPoison(itemId, SUPERANTIPOISON1, slot, 300000);
                    break;
                case SUPERANTIPOISON1:
                    drinkAntiPoison(itemId, VIAL, slot, 300000);
                    break;
                case ENERGY_POTION4:
                    energyPotion(itemId, ENERGY_POTION3, slot);
                    break;
                case ENERGY_POTION3:
                    energyPotion(itemId, ENERGY_POTION2, slot);
                    break;
                case ENERGY_POTION2:
                    energyPotion(itemId, ENERGY_POTION1, slot);
                    break;
                case ENERGY_POTION1:
                    energyPotion(itemId, VIAL, slot);
                    break;
                case SUPER_ENERGY4:
                    energyPotion(itemId, SUPER_ENERGY3, slot);
                    break;
                case SUPER_ENERGY3:
                    energyPotion(itemId, SUPER_ENERGY2, slot);
                    break;
                case SUPER_ENERGY2:
                    energyPotion(itemId, SUPER_ENERGY1, slot);
                    break;
                case SUPER_ENERGY1:
                    energyPotion(itemId, VIAL, slot);
                    break;
                case ANTIFIRE_POTION4:
                    antifirePot(itemId, ANTIFIRE_POTION3, slot);
                    break;
                case ANTIFIRE_POTION3:
                    antifirePot(itemId, ANTIFIRE_POTION2, slot);
                    break;
                case ANTIFIRE_POTION2:
                    antifirePot(itemId, ANTIFIRE_POTION1, slot);
                    break;
                case ANTIFIRE_POTION1:
                    antifirePot(itemId, VIAL, slot);
                    break;
            }
        }
    }

    private void energyPotion(int itemId, int replaceItem, int slot) {
        c.playerItems[slot] = replaceItem + 1;
        c.getItemAssistant().resetItems(3214);
        if (itemId >= ENERGY_POTION4 && itemId <= ENERGY_POTION1) {
            c.playerEnergy += 20;
        } else {
            c.playerEnergy += 40;
        }
        if (c.playerEnergy > 100) {
            c.playerEnergy = 100;
        }
        c.getPacketSender().sendString((int) Math.ceil(c.playerEnergy) + "%", 149);
    }

    public void drinkAntiPoison(int itemId, int replaceItem, int slot,
                                long delay) {
        // c.startAnimation(829);
        c.playerItems[slot] = replaceItem + 1;
        c.getItemAssistant().resetItems(3214);
        curePoison(delay);
    }

    public void curePoison(long delay) {
        c.poisonDamage = 0;
        c.poisonImmune = delay;
        c.lastPoisonSip = System.currentTimeMillis();
    }

    public void drinkStatPotion(int itemId, int replaceItem, int slot,
                                int stat, boolean sup) {
        // c.startAnimation(829);
        c.playerItems[slot] = replaceItem + 1;
        c.getItemAssistant().resetItems(3214);
        enchanceStat(stat, sup);
    }

    public void drinkPrayerPot(int itemId, int replaceItem, int slot,
                               boolean rest) {
        // c.startAnimation(829);
        c.playerItems[slot] = replaceItem + 1;
        c.getItemAssistant().resetItems(3214);
        c.playerLevel[Constants.PRAYER] += c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.PRAYER]) * .33;
        if (rest) {
            c.playerLevel[Constants.PRAYER] += 1;
        }
        if (c.playerLevel[Constants.PRAYER] > c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.PRAYER])) {
            c.playerLevel[Constants.PRAYER] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.PRAYER]);
        }
        c.getPlayerAssistant().refreshSkill(Constants.PRAYER);
        if (rest) {
            restoreStats();
        }
    }

    public void restoreStats() {
        for (int j = 0; j <= 6; j++) {
            if (j == 5 || j == 3) {
                continue;
            }
            if (c.playerLevel[j] < c.getPlayerAssistant().getLevelForXP(c.playerXP[j])) {
                c.playerLevel[j] += c.getPlayerAssistant().getLevelForXP(c.playerXP[j]) * .33;
                if (c.playerLevel[j] > c.getPlayerAssistant().getLevelForXP(c.playerXP[j])) {
                    c.playerLevel[j] = c.getPlayerAssistant().getLevelForXP(c.playerXP[j]);
                }
                c.getPlayerAssistant().refreshSkill(j);
                c.getPacketSender().setSkillLevel(j, c.playerLevel[j],
                        c.playerXP[j]);
            }
        }
    }

    public void doTheBrewzam(int itemId, int replaceItem, int slot) {
        // c.startAnimation(829);
        c.playerItems[slot] = replaceItem + 1;
        c.getItemAssistant().resetItems(3214);
        int[] toDecrease = {1, 3};
        for (int tD : toDecrease) {
            c.playerLevel[tD] -= getBrewStat(tD, .10);
            if (c.playerLevel[tD] < 0) {
                c.playerLevel[tD] = 1;
            }
            c.getPlayerAssistant().refreshSkill(tD);
            c.getPacketSender().setSkillLevel(tD, c.playerLevel[tD],
                    c.playerXP[tD]);
        }
        c.playerLevel[Constants.ATTACK] += getBrewStat(0, .20);
        if (c.playerLevel[Constants.ATTACK] > c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.ATTACK]) * 1.2 + 1) {
            c.playerLevel[Constants.ATTACK] = (int) (c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.ATTACK]) * 1.2);
        }
        c.playerLevel[Constants.STRENGTH] += getBrewStat(2, .12);
        if (c.playerLevel[Constants.STRENGTH] > c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.STRENGTH]) * 1.2 + 1) {
            c.playerLevel[Constants.STRENGTH] = (int) (c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.STRENGTH]) * 1.2);
        }
        c.playerLevel[Constants.PRAYER] += getBrewStat(5, .10);
        if (c.playerLevel[Constants.PRAYER] > c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.PRAYER]) * 1.2 + 1) {
            c.playerLevel[Constants.PRAYER] = (int) (c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.PRAYER]) * 1.2);
        }
        c.getPlayerAssistant().refreshSkill(0);
        c.getPlayerAssistant().refreshSkill(Constants.STRENGTH);
        c.getPlayerAssistant().refreshSkill(Constants.PRAYER);
        c.hitUpdateRequired = true;
        c.hitDiff = 9;
    }

    public void doTheBrew(int itemId, int replaceItem, int slot) {
        if (c.duelRule[6]) {
            c.getPacketSender()
                    .sendMessage("You may not eat in this duel.");
            return;
        }
        // c.startAnimation(829);
        c.playerItems[slot] = replaceItem + 1;
        c.getItemAssistant().resetItems(3214);
        int[] toDecrease = {0, 2, 4, 6};
        for (int tD : toDecrease) {
            c.playerLevel[tD] -= getBrewStat(tD, .10);
            if (c.playerLevel[tD] < 0) {
                c.playerLevel[tD] = 1;
            }
            c.getPlayerAssistant().refreshSkill(tD);
            c.getPacketSender().setSkillLevel(tD, c.playerLevel[tD],
                    c.playerXP[tD]);
        }
        c.playerLevel[Constants.DEFENCE] += getBrewStat(1, .20);
        if (c.playerLevel[Constants.DEFENCE] > c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.DEFENCE]) * 1.2 + 1) {
            c.playerLevel[Constants.DEFENCE] = (int) (c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.DEFENCE]) * 1.2);
        }
        c.getPlayerAssistant().refreshSkill(Constants.DEFENCE);

        c.playerLevel[Constants.HITPOINTS] += getBrewStat(3, .15);
        if (c.playerLevel[Constants.HITPOINTS] > c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS]) * 1.17 + 1) {
            c.playerLevel[Constants.HITPOINTS] = (int) (c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS]) * 1.17);
        }
        c.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
    }

    public void enchanceStat(int skillID, boolean sup) {
        c.playerLevel[skillID] += getBoostedStat(skillID, sup);
        c.getPlayerAssistant().refreshSkill(skillID);
    }

    public void antifirePot(int itemId, int replaceItem, int slot) {
        c.startAnimation(829);
        c.playerItems[slot] = replaceItem + 1;
        c.antiFirePot = true;
        c.antiFirePotion();
        c.getPacketSender().sendMessage(
                "Your immunity against dragon fire has been increased.");
        c.getItemAssistant().resetItems(3214);

    }

    public int getBrewStat(int skill, double amount) {
        return (int) (c.getPlayerAssistant().getLevelForXP(c.playerXP[skill]) * amount);
    }

    public int getBoostedStat(int skill, boolean sup) {
        int increaseBy;
        if (sup) {
            increaseBy = (int) (c.getPlayerAssistant().getLevelForXP(c.playerXP[skill]) * .20);
        } else {
            increaseBy = (int) (c.getPlayerAssistant().getLevelForXP(c.playerXP[skill]) * .13) + 1;
        }
        if (c.playerLevel[skill] + increaseBy > c.getPlayerAssistant()
                .getLevelForXP(c.playerXP[skill]) + increaseBy + 1) {
            return c.getPlayerAssistant().getLevelForXP(c.playerXP[skill]) + increaseBy
                    - c.playerLevel[skill];
        }
        return increaseBy;
    }

    public boolean isPotion(int itemId) {
        String name = DeprecatedItems.getItemName(itemId);
        return name.contains("(4)") || name.contains("(3)")
                || name.contains("(2)") || name.contains("(1)");
    }

    public boolean potionNames(int itemId) {
        String name = DeprecatedItems.getItemName(itemId);
        return name.endsWith("potion(4)") || name.endsWith("potion(3)")
                || name.endsWith("potion(2)") || name.endsWith("potion(1)")
                || name.contains("saradomin brew")
                || name.contains("zamorak brew");
    }
}