package redone.game.content.consumables;

import redone.game.players.Client;
import redone.util.Misc;

/**
 * @author darkside1222
 */
public class Kebabs {

	public static int Kebab = 1971;
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
	public static void effects(Client c) {
		float eff1 = chances("effect1");
		float eff2 = chances("effect2");
		float eff3 = chances("effect3");
		float eff4 = chances("effect4");
		float eff5 = chances("effect5");

		if (Misc.random(100.0f) <= eff1) { // 8.71%
			c.getActionSender().sendMessage(
					"That kebab didn't seem to do a lot.");

		} else if (Misc.random(100.0f) <= eff2) { // 61.24% heals 10% of HP
			c.getActionSender()
					.sendMessage("It restores some life points.");
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += c.getLevelForXP(c.playerXP[3]) * 0.10;
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3])) {
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
				}

			}

		} else if (Misc.random(100.0f) <= eff3) { // 21.12% + 10-20 HP
			c.getActionSender().sendMessage(
					"That was a good kebab. You feel a lot better. ");
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += Misc.random(20);
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3])) {
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
				}
			}

		} else if (Misc.random(100.0f) <= eff4) {// 3.65% + attk,str,def +
													// 2-3 + heal 0-300
			c.getActionSender()
					.sendMessage(
							"Wow, that was an amazing kebab! You feel really invigorated.");
			c.playerLevel[1] += 2 + Misc.random(1); // def
			c.playerLevel[2] += 2 + Misc.random(1); // str
			c.playerLevel[0] += 2 + Misc.random(1); // atk
			c.getPlayerAssistant().refreshSkill(1);
			c.getPlayerAssistant().refreshSkill(2);
			c.getPlayerAssistant().refreshSkill(3);
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += Misc.random(30);
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3])) {
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
				}
			}

		} else if (Misc.random(100.0f) <= eff5) {// 6.3%. lower STAT
			c.getActionSender().sendMessage(
					"That tasted very dodgy. You feel very ill.");
			c.getActionSender().sendMessage(
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
	public static void eat(Client c, int slot) {
		if (System.currentTimeMillis() - c.foodDelay >= 1500
				&& c.playerLevel[3] > 0) {
			if (c.playerLevel[3] == c.getLevelForXP(c.playerXP[3])) { // If
																		// full
																		// health,
																		// does
																		// nothing
																		// but
																		// eat.
				c.getCombatAssistant().resetPlayerAttack();
				c.getActionSender().sendMessage("You eat the kebab.");
				c.attackTimer += 2;
				c.startAnimation(829);
				c.getItemAssistant().deleteItem(Kebab, slot, 1);
				c.getActionSender().sendSound(317, 100, 0);
				c.foodDelay = System.currentTimeMillis();
				c.getPlayerAssistant().refreshSkill(3);
				return;
			}
			c.getCombatAssistant().resetPlayerAttack();
			c.getActionSender().sendMessage("You eat the kebab.");
			effects(c);
			c.attackTimer += 2;
			c.startAnimation(829);
			c.getItemAssistant().deleteItem(Kebab, slot, 1);
			c.getActionSender().sendSound(317, 100, 0);
			c.foodDelay = System.currentTimeMillis();
			c.getPlayerAssistant().refreshSkill(3);
		}
	}
}
