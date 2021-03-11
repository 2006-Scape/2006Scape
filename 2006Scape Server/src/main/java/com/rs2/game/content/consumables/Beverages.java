package com.rs2.game.content.consumables;

import com.rs2.event.*;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;

public class Beverages {

	private static final int BEER_GLASS = 1919;

	public static enum beverageData {
		BEER(1917, BEER_GLASS, 829, true, false, false, false), 
		BEER1(7740, BEER_GLASS, 829, true, false, false, false),
				GROG(1915, BEER_GLASS, 829, true, false, false, false), 
				BANDITS_BREW(4627, BEER_GLASS, 829, true, false, false, false), 
				DRAGON_BITTER(1911, BEER_GLASS, 829, true, false, false, false), 
				CIDER(7752,BEER_GLASS, 829, true, false, false, false), 
				MATURE_CIDER(5765, BEER_GLASS, 829, true, false, false, false), 
				MOONLIGHT_MEAD(7750, BEER_GLASS, 829, true, false, false, false), 
				DWARVEN_STOUT(1913, BEER_GLASS, 829, true, false, false, false), 
				GREENMANS_ALE(1909, BEER_GLASS, 829, true, false, false, false), 
				CHEFS_DELIGHT(7754, BEER_GLASS, 829, true, false, false, false), 
				ASGARNIAN_ALE(1905, BEER_GLASS, 829, true, false, false, false), 
				WIZARDS_MIND_BOMB(1907, BEER_GLASS, 829, true, false, false, false);

		private int bevId, replacement, bevAnim;
		private boolean effect1, effect2, effect3, effect4;

		private beverageData(final int bevId, final int replacement,
				final int bevAnim, final boolean effect1,
				final boolean effect2, final boolean effect3,
				final boolean effect4) {
			this.bevId = bevId;
			this.replacement = replacement;
			this.bevAnim = bevAnim;
			this.effect1 = effect1;
			this.effect2 = effect2;
			this.effect3 = effect3;
			this.effect4 = effect4;
		}

		public int getBev() {
			return bevId;
		}

		public int getRep() {
			return replacement;
		}

		public int getAnim() {
			return bevAnim;
		}

		public boolean getEffect1() {
			return effect1;
		}

		public boolean getEffect2() {
			return effect2;
		}

		public boolean getEffect3() {
			return effect3;
		}

		public boolean getEffect4() {
			return effect4;
		}

		private final String getName() {
			return Misc.optimizeText(toString().toLowerCase().replaceAll("_", " "));
		}
	}

	public static boolean isBeverage(Player c, int beverageId) {
		boolean isBeverage = false;
		for (final beverageData b : beverageData.values()) {
			if (beverageId == b.getBev()) {
				isBeverage = true;
			}
		}
		return isBeverage;
	}

	private static int drunkTimer = 0;

	private static void resetDrunk(Player c) {
		c.playerStandIndex = 0x328;
		c.updateRequired = true;
		c.appearanceUpdateRequired = true;
		drunkTimer = -1;
	}

	private static void getBevEffect(final Player c, int bevEffectId) {
		switch (bevEffectId) {
		case 1:
			c.playerStandIndex = 3040;
			c.getPacketSender().sendMessage("You start to feel dizzy.");
			   CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
		            @Override
		            public void execute(CycleEventContainer container) {
					if (c.disconnected) {
						container.stop();
						return;
					}

					if (drunkTimer > 0) {
						drunkTimer--;
					}
					if (drunkTimer == 0) {
						container.stop();
					}
				}

				public void stop() {
					resetDrunk(c);
				}
			}, 1);
			break;
		default:
			break;
		}
		c.updateRequired = true;
		c.appearanceUpdateRequired = true;
	}
	
	public static void foodEffect(Player c, int id) {
		switch (id) {
		case 1917:
		case 7740://beer
			double beerEffectStrength = c.getLevelForXP(c.playerXP[2]) * .04 + c.getLevelForXP(c.playerXP[2]);
			double beerEffectAttack = c.getLevelForXP(c.playerXP[0]) * .07;
			if (c.playerLevel[2] < beerEffectStrength) {
				c.playerLevel[2] = (int) beerEffectStrength;
			}
			if (c.playerLevel[0] > 0) {
				c.playerLevel[0] -= beerEffectAttack;
			}
			if (c.playerLevel[0] <= 0) {
				c.playerLevel[0] = 1;
			}
			c.getPlayerAssistant().refreshSkill(0);
			c.getPlayerAssistant().refreshSkill(2);
			break;
		case 1913: //dwarven stout
			c.playerLevel[c.playerMining] = c.getPlayerAssistant().getLevelForXP(c.playerXP[c.playerMining]) + 1;
			c.playerLevel[c.playerSmithing] = c.getPlayerAssistant().getLevelForXP(c.playerXP[c.playerSmithing]) + 1;
			c.getPlayerAssistant().refreshSkill(c.playerMining);
			c.getPlayerAssistant().refreshSkill(c.playerSmithing);
		break;
		case 1907://wizard's mind bomb
			if (c.playerLevel[c.playerMagic] < 50) {
				c.playerLevel[c.playerMagic] = c.getPlayerAssistant().getLevelForXP(c.playerXP[c.playerMagic]) + 2;
			} else {
				c.playerLevel[c.playerMagic] = c.getPlayerAssistant().getLevelForXP(c.playerXP[c.playerMagic]) + 3;
			}
			c.playerLevel[c.playerStrength] = c.getPlayerAssistant().getLevelForXP(c.playerXP[c.playerStrength]) - 3;
			c.playerLevel[c.playerDefence] = c.getPlayerAssistant().getLevelForXP(c.playerXP[c.playerDefence]) - 3;
			c.playerLevel[c.playerAttack] = c.getPlayerAssistant().getLevelForXP(c.playerXP[c.playerAttack]) - 4;
			c.getPlayerAssistant().refreshSkill(c.playerDefence);
			c.getPlayerAssistant().refreshSkill(c.playerMagic);
			c.getPlayerAssistant().refreshSkill(c.playerAttack);
			c.getPlayerAssistant().refreshSkill(c.playerStrength);
		break;
		case 1915://grog
			c.playerLevel[2] = c.getPlayerAssistant().getLevelForXP(c.playerXP[2]) + 3;
			if (c.playerLevel[0] > 0) {
				c.playerLevel[0] = c.getPlayerAssistant().getLevelForXP(c.playerXP[0]) - 2;
			}
			if (c.playerLevel[0] <= 0) {
				c.playerLevel[0] = 1;
			}
			c.getPlayerAssistant().refreshSkill(0);
			c.getPlayerAssistant().refreshSkill(2);
			break;
		}
	}

	public static void drinkBeverage(final Player player, final int beverageId, int slotId) {
		for (final beverageData b : beverageData.values()) {
			if (beverageId == b.getBev()) {
				if (System.currentTimeMillis() - player.potDelay >= 1500) {
					if (player.getItemAssistant().playerHasItem(beverageId)) {
						if (b.getEffect1()) {
							drunkTimer = 30;
							getBevEffect(player, 1);
						}
						if (b.getEffect2()) {
							drunkTimer = 45;
							getBevEffect(player, 2);
						}
						if (b.getEffect3()) {
							drunkTimer = 60;
							getBevEffect(player, 3);
						}
						if (b.getEffect4()) {
							drunkTimer = 75;
							getBevEffect(player, 4);
						}
						foodEffect(player, beverageId);
						player.potDelay = System.currentTimeMillis();
						player.foodDelay = player.potDelay;
						player.getCombatAssistant().resetPlayerAttack();
						player.attackTimer++;
						player.startAnimation(b.getAnim());
						player.getItemAssistant().resetItems(3214);
						player.potDelay = System.currentTimeMillis();
						player.getItemAssistant().deleteItem(beverageId, slotId, 1);
						player.getItemAssistant().addItem(b.getRep(), 1);
						player.forcedChat("Cheers mate!");
						player.getPacketSender().sendMessage("You drink the " + b.getName() + ".");
					}
				}
			}
		}
	}
}