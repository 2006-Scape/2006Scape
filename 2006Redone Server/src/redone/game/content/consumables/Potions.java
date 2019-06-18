package redone.game.content.consumables;

import redone.event.CycleEvent;
import redone.event.CycleEventContainer;
import redone.event.CycleEventHandler;
import redone.game.items.Item;
import redone.game.items.ItemAssistant;
import redone.game.players.Client;

public class Potions {

	private final Client c;

	public Potions(Client c) {
		this.c = c;
	}

	public void handlePotion(int itemId, int slot) {
		if (c.duelRule[5]) {
			c.getActionSender().sendMessage(
					"You may not drink potions in this duel.");
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0) {
			return;
		}
		if (System.currentTimeMillis() - c.potDelay >= 1200) {
			c.potDelay = System.currentTimeMillis();
			c.foodDelay = System.currentTimeMillis();
			c.getCombatAssistant().resetPlayerAttack();
			c.attackTimer++;
			c.getActionSender().sendMessage(
					"You drink some of your " + Item.getItemName(itemId) + ".");
			c.startAnimation(829);
			final String item = Item.getItemName(itemId);
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
					c.getActionSender().sendMessage(m1);
					container.stop();
				}
				@Override
					public void stop() {
						
					}
			}, 1);
			switch (itemId) {
			case 3040:
				drinkStatPotion(itemId, 3042, slot, 6, false);
				break;
			case 3042:
				drinkStatPotion(itemId, 3044, slot, 6, false);
				break;
			case 3044:
				drinkStatPotion(itemId, 3046, slot, 6, false);
				break;
			case 3046:
				drinkStatPotion(itemId, 229, slot, 6, false);
				break;
			case 2450:
				doTheBrewzam(itemId, 189, slot);
				break;
			case 189:
				doTheBrewzam(itemId, 191, slot);
				break;
			case 191:
				doTheBrewzam(itemId, 193, slot);
				break;
			case 193:
				doTheBrewzam(itemId, 229, slot);
				break;
			case 6685: // brews
				doTheBrew(itemId, 6687, slot);
				break;
			case 6687:
				doTheBrew(itemId, 6689, slot);
				break;
			case 6689:
				doTheBrew(itemId, 6691, slot);
				break;
			case 6691:
				doTheBrew(itemId, 229, slot);
				break;
			case 2436:
				drinkStatPotion(itemId, 145, slot, 0, true); // sup attack
				break;
			case 145:
				drinkStatPotion(itemId, 147, slot, 0, true);
				break;
			case 147:
				drinkStatPotion(itemId, 149, slot, 0, true);
				break;
			case 149:
				drinkStatPotion(itemId, 229, slot, 0, true);
				break;
			case 2440:
				drinkStatPotion(itemId, 157, slot, 2, true); // sup str
				break;
			case 157:
				drinkStatPotion(itemId, 159, slot, 2, true);
				break;
			case 159:
				drinkStatPotion(itemId, 161, slot, 2, true);
				break;
			case 161:
				drinkStatPotion(itemId, 229, slot, 2, true);
				break;
			case 2444:
				drinkStatPotion(itemId, 169, slot, 4, false); // range pot
				break;
			case 169:
				drinkStatPotion(itemId, 171, slot, 4, false);
				break;
			case 171:
				drinkStatPotion(itemId, 173, slot, 4, false);
				break;
			case 173:
				drinkStatPotion(itemId, 229, slot, 4, false);
				break;
			case 2432:
				drinkStatPotion(itemId, 133, slot, 1, false); // def pot
				break;
			case 133:
				drinkStatPotion(itemId, 135, slot, 1, false);
				break;
			case 135:
				drinkStatPotion(itemId, 137, slot, 1, false);
				break;
			case 137:
				drinkStatPotion(itemId, 229, slot, 1, false);
				break;
			case 113:
				drinkStatPotion(itemId, 115, slot, 2, false); // str pot
				break;
			case 115:
				drinkStatPotion(itemId, 117, slot, 2, false);
				break;
			case 117:
				drinkStatPotion(itemId, 119, slot, 2, false);
				break;
			case 119:
				drinkStatPotion(itemId, 229, slot, 2, false);
				break;
			case 2428:
				drinkStatPotion(itemId, 121, slot, 0, false); // attack
																// pot
				break;
			case 121:
				drinkStatPotion(itemId, 123, slot, 0, false);
				break;
			case 123:
				drinkStatPotion(itemId, 125, slot, 0, false);
				break;
			case 125:
				drinkStatPotion(itemId, 229, slot, 0, false);
				break;
			case 2442:
				drinkStatPotion(itemId, 163, slot, 1, true); // super def
																// pot
				break;
			case 163:
				drinkStatPotion(itemId, 165, slot, 1, true);
				break;
			case 165:
				drinkStatPotion(itemId, 167, slot, 1, true);
				break;
			case 167:
				drinkStatPotion(itemId, 229, slot, 1, true);
				break;
			case 3024:
				drinkPrayerPot(itemId, 3026, slot, true); // sup restore
				break;
			case 3026:
				drinkPrayerPot(itemId, 3028, slot, true);
				break;
			case 3028:
				drinkPrayerPot(itemId, 3030, slot, true);
				break;
			case 3030:
				drinkPrayerPot(itemId, 229, slot, true);
				break;
			case 10925:
				drinkPrayerPot(itemId, 10927, slot, true); // sanfew
															// serums
				curePoison(300000);
				break;
			case 10927:
				drinkPrayerPot(itemId, 10929, slot, true);
				curePoison(300000);
				break;
			case 10929:
				drinkPrayerPot(itemId, 10931, slot, true);
				curePoison(300000);
				break;
			case 10931:
				drinkPrayerPot(itemId, 229, slot, true);
				curePoison(300000);
				break;
			case 2434:
				drinkPrayerPot(itemId, 139, slot, false); // pray pot
				break;
			case 139:
				drinkPrayerPot(itemId, 141, slot, false);
				break;
			case 141:
				drinkPrayerPot(itemId, 143, slot, false);
				break;
			case 143:
				drinkPrayerPot(itemId, 229, slot, false);
				break;
			case 2446:
				drinkAntiPoison(itemId, 175, slot, 30000); // anti poisons
				break;
			case 175:
				drinkAntiPoison(itemId, 177, slot, 30000);
				break;
			case 177:
				drinkAntiPoison(itemId, 179, slot, 30000);
				break;
			case 179:
				drinkAntiPoison(itemId, 229, slot, 30000);
				break;
			case 2448:
				drinkAntiPoison(itemId, 181, slot, 300000); // anti
															// poisons
				break;
			case 181:
				drinkAntiPoison(itemId, 183, slot, 300000);
				break;
			case 183:
				drinkAntiPoison(itemId, 185, slot, 300000);
				break;
			case 185:
				drinkAntiPoison(itemId, 229, slot, 300000);
				break;
			/** Energy Potions **/
			case 3008:
				energyPotion(itemId, 3010, slot);
				break;
			case 3010:
				energyPotion(itemId, 3012, slot);
				break;
			case 3012:
				energyPotion(itemId, 3014, slot);
				break;
			case 3014:
				energyPotion(itemId, 229, slot);
				break;
			/** Super Energy Potions **/
			case 3016:
				energyPotion(itemId, 3018, slot);
				break;
			case 3018:
				energyPotion(itemId, 3020, slot);
				break;
			case 3020:
				energyPotion(itemId, 3022, slot);
				break;
			case 3022:
				energyPotion(itemId, 229, slot);
				break;
			case 2452:
				antifirePot(itemId, 2454, slot);
				break;
			case 2454:
				antifirePot(itemId, 2456, slot);
				break;
			case 2456:
				antifirePot(itemId, 2458, slot);
				break;
			case 2458:
				antifirePot(itemId, 229, slot);
				break;
			}
		}
	}

	private void energyPotion(int itemId, int replaceItem, int slot) {
		c.playerItems[slot] = replaceItem + 1;
		c.getItemAssistant().resetItems(3214);
		if (itemId >= 3008 && itemId <= 3014) {
			c.playerEnergy += 20;
		} else {
			c.playerEnergy += 40;
		}
		if (c.playerEnergy > 100) {
			c.playerEnergy = 100;
		}
		c.getPlayerAssistant().sendFrame126(c.playerEnergy + "%", 149);
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
		c.playerLevel[5] += c.getLevelForXP(c.playerXP[5]) * .33;
		if (rest) {
			c.playerLevel[5] += 1;
		}
		if (c.playerLevel[5] > c.getLevelForXP(c.playerXP[5])) {
			c.playerLevel[5] = c.getLevelForXP(c.playerXP[5]);
		}
		c.getPlayerAssistant().refreshSkill(5);
		if (rest) {
			restoreStats();
		}
	}

	public void restoreStats() {
		for (int j = 0; j <= 6; j++) {
			if (j == 5 || j == 3) {
				continue;
			}
			if (c.playerLevel[j] < c.getLevelForXP(c.playerXP[j])) {
				c.playerLevel[j] += c.getLevelForXP(c.playerXP[j]) * .33;
				if (c.playerLevel[j] > c.getLevelForXP(c.playerXP[j])) {
					c.playerLevel[j] = c.getLevelForXP(c.playerXP[j]);
				}
				c.getPlayerAssistant().refreshSkill(j);
				c.getActionSender().setSkillLevel(j, c.playerLevel[j],
						c.playerXP[j]);
			}
		}
	}

	public void doTheBrewzam(int itemId, int replaceItem, int slot) {
		// c.startAnimation(829);
		c.playerItems[slot] = replaceItem + 1;
		c.getItemAssistant().resetItems(3214);
		int[] toDecrease = { 1, 3 };
		for (int tD : toDecrease) {
			c.playerLevel[tD] -= getBrewStat(tD, .10);
			if (c.playerLevel[tD] < 0) {
				c.playerLevel[tD] = 1;
			}
			c.getPlayerAssistant().refreshSkill(tD);
			c.getActionSender().setSkillLevel(tD, c.playerLevel[tD],
					c.playerXP[tD]);
		}
		c.playerLevel[0] += getBrewStat(0, .20);
		if (c.playerLevel[0] > c.getLevelForXP(c.playerXP[0]) * 1.2 + 1) {
			c.playerLevel[0] = (int) (c.getLevelForXP(c.playerXP[0]) * 1.2);
		}
		c.playerLevel[2] += getBrewStat(2, .12);
		if (c.playerLevel[2] > c.getLevelForXP(c.playerXP[2]) * 1.2 + 1) {
			c.playerLevel[2] = (int) (c.getLevelForXP(c.playerXP[2]) * 1.2);
		}
		c.playerLevel[5] += getBrewStat(5, .10);
		if (c.playerLevel[5] > c.getLevelForXP(c.playerXP[5]) * 1.2 + 1) {
			c.playerLevel[5] = (int) (c.getLevelForXP(c.playerXP[5]) * 1.2);
		}
		c.getPlayerAssistant().refreshSkill(0);
		c.getPlayerAssistant().refreshSkill(2);
		c.getPlayerAssistant().refreshSkill(5);
		c.hitUpdateRequired = true;
		c.hitDiff = 9;
	}

	public void doTheBrew(int itemId, int replaceItem, int slot) {
		if (c.duelRule[6]) {
			c.getActionSender()
					.sendMessage("You may not eat in this duel.");
			return;
		}
		// c.startAnimation(829);
		c.playerItems[slot] = replaceItem + 1;
		c.getItemAssistant().resetItems(3214);
		int[] toDecrease = { 0, 2, 4, 6 };
		for (int tD : toDecrease) {
			c.playerLevel[tD] -= getBrewStat(tD, .10);
			if (c.playerLevel[tD] < 0) {
				c.playerLevel[tD] = 1;
			}
			c.getPlayerAssistant().refreshSkill(tD);
			c.getActionSender().setSkillLevel(tD, c.playerLevel[tD],
					c.playerXP[tD]);
		}
		c.playerLevel[1] += getBrewStat(1, .20);
		if (c.playerLevel[1] > c.getLevelForXP(c.playerXP[1]) * 1.2 + 1) {
			c.playerLevel[1] = (int) (c.getLevelForXP(c.playerXP[1]) * 1.2);
		}
		c.getPlayerAssistant().refreshSkill(1);

		c.playerLevel[3] += getBrewStat(3, .15);
		if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]) * 1.17 + 1) {
			c.playerLevel[3] = (int) (c.getLevelForXP(c.playerXP[3]) * 1.17);
		}
		c.getPlayerAssistant().refreshSkill(3);
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
		c.getActionSender().sendMessage(
				"Your immunity against dragon fire has been increased.");
		c.getItemAssistant().resetItems(3214);

	}

	public int getBrewStat(int skill, double amount) {
		return (int) (c.getLevelForXP(c.playerXP[skill]) * amount);
	}

	public int getBoostedStat(int skill, boolean sup) {
		int increaseBy;
		if (sup) {
			increaseBy = (int) (c.getLevelForXP(c.playerXP[skill]) * .20);
		} else {
			increaseBy = (int) (c.getLevelForXP(c.playerXP[skill]) * .13) + 1;
		}
		if (c.playerLevel[skill] + increaseBy > c
				.getLevelForXP(c.playerXP[skill]) + increaseBy + 1) {
			return c.getLevelForXP(c.playerXP[skill]) + increaseBy
					- c.playerLevel[skill];
		}
		return increaseBy;
	}

	public boolean isPotion(int itemId) {
		String name = ItemAssistant.getItemName(itemId);
		return name.contains("(4)") || name.contains("(3)")
				|| name.contains("(2)") || name.contains("(1)");
	}

	public boolean potionNames(int itemId) {
		String name = ItemAssistant.getItemName(itemId);
		return name.endsWith("potion(4)") || name.endsWith("potion(3)")
				|| name.endsWith("potion(2)") || name.endsWith("potion(1)")
				|| name.contains("saradomin brew")
				|| name.contains("zamorak brew");
	}
}
