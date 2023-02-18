package com.rs2.game.content.consumables;

import com.rs2.Constants;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

import static com.rs2.game.content.StaticItemList.KEBAB;

/**
 * @author darkside1222
 */
public class Kebabs {
    float chances = 0.0f;

    /**
     * Chances(Percents)
     */
    public static float chances(String effect) {
        float chances = 0.0f;// PERCENT
        if (effect.equalsIgnoreCase("Effect1")) {// Nothing
            chances = 8.7f;
        } else if (effect.equalsIgnoreCase("Effect2")) {// normal heal
            chances = 61.2f;
        } else if (effect.equalsIgnoreCase("Effect3")) {// better heal
            chances = 21.1f;
        } else if (effect.equalsIgnoreCase("Effect4")) {// SUPER heal
            chances = 3.6f;
        } else if (effect.equalsIgnoreCase("Effect5")) {// Damages you.
            chances = 6.3f - 0.9f;
            ;
        } else {
            chances = Float.parseFloat(effect);
        }
        return chances; // Equals 100%
    }

    /**
     * Different effects(Healing,lowering,damaging)
     */
    public static void effects(Player c) {
        float eff1 = chances("effect1");
        float eff2 = chances("effect2");
        float eff3 = chances("effect3");
        float eff4 = chances("effect4");
        float eff5 = chances("effect5");

        if (Misc.random(100.0f) <= eff1) { // 8.71%
            c.getPacketSender().sendMessage(
                    "That kebab didn't seem to do a lot.");

        } else if (Misc.random(100.0f) <= eff2) { // 61.24% heals 10% of HP
            c.getPacketSender()
                    .sendMessage("It restores some life points.");
            if (c.playerLevel[Constants.HITPOINTS] < c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS])) {
                c.playerLevel[Constants.HITPOINTS] += c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS]) * 0.10;
                if (c.playerLevel[Constants.HITPOINTS] > c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS])) {
                    c.playerLevel[Constants.HITPOINTS] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS]);
                }

            }

        } else if (Misc.random(100.0f) <= eff3) { // 21.12% + 10-20 HP
            c.getPacketSender().sendMessage(
                    "That was a good kebab. You feel a lot better. ");
            if (c.playerLevel[Constants.HITPOINTS] < c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS])) {
                c.playerLevel[Constants.HITPOINTS] += Misc.random(20);
                if (c.playerLevel[Constants.HITPOINTS] > c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS])) {
                    c.playerLevel[Constants.HITPOINTS] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS]);
                }
            }

        } else if (Misc.random(100.0f) <= eff4) {// 3.65% + attk,str,def +
            // 2-3 + heal 0-300
            c.getPacketSender()
                    .sendMessage(
                            "Wow, that was an amazing kebab! You feel really invigorated.");
            c.playerLevel[Constants.DEFENCE] += 2 + Misc.random(1); // def
            c.playerLevel[Constants.STRENGTH] += 2 + Misc.random(1); // str
            c.playerLevel[Constants.ATTACK] += 2 + Misc.random(1); // atk
            c.getPlayerAssistant().refreshSkill(Constants.DEFENCE);
            c.getPlayerAssistant().refreshSkill(Constants.STRENGTH);
            c.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
            if (c.playerLevel[Constants.HITPOINTS] < c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS])) {
                c.playerLevel[Constants.HITPOINTS] += Misc.random(30);
                if (c.playerLevel[Constants.HITPOINTS] > c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS])) {
                    c.playerLevel[Constants.HITPOINTS] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.HITPOINTS]);
                }
            }

        } else if (Misc.random(100.0f) <= eff5) {// 6.3%. lower STAT
            c.getPacketSender().sendMessage(
                    "That tasted very dodgy. You feel very ill.");
            c.getPacketSender().sendMessage(
                    "Eating the kebab has done damage to some of your stats.");
            for (int j = 0; j < 2; j++) {
                c.playerLevel[j] -= 2; // Fix this l0l
                c.getPlayerAssistant().refreshSkill(j);
            }
        }

    }

    /**
     * Eatting the kebab
     */
    public static void eat(Player player, int slot) {
        if (System.currentTimeMillis() - player.foodDelay >= 1500
                && player.playerLevel[Constants.HITPOINTS] > 0) {
            if (player.playerLevel[Constants.HITPOINTS] == player.getPlayerAssistant().getLevelForXP(player.playerXP[Constants.HITPOINTS])) { // If
                // full
                // health,
                // does
                // nothing
                // but
                // eat.
                player.getCombatAssistant().resetPlayerAttack();
                player.getPacketSender().sendMessage("You eat the kebab.");
                player.attackTimer += 2;
                player.startAnimation(829);
                player.getItemAssistant().deleteItem(KEBAB, slot, 1);
                player.getPacketSender().sendSound(317, 100, 0);
                player.foodDelay = System.currentTimeMillis();
                player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
                return;
            }
            player.getCombatAssistant().resetPlayerAttack();
            player.getPacketSender().sendMessage("You eat the kebab.");
            effects(player);
            player.attackTimer += 2;
            player.startAnimation(829);
            player.getItemAssistant().deleteItem(KEBAB, slot, 1);
            player.getPacketSender().sendSound(317, 100, 0);
            player.foodDelay = System.currentTimeMillis();
            player.getPlayerAssistant().refreshSkill(Constants.HITPOINTS);
        }
    }
}
