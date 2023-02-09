package com.rs2.game.content.consumables;

import com.rs2.Constants;
import com.rs2.event.*;
import com.rs2.game.players.Player;
import com.rs2.util.Misc;
import static com.rs2.game.content.StaticItemList.*;
public class Beverages {

	public static enum beverageData {
		BEER_(BEER, BEER_GLASS, 829, true, false, false, false),
		BEER1_(BEER_7740, BEER_GLASS, 829, true, false, false, false),
		GROG_(GROG, BEER_GLASS, 829, true, false, false, false),
		BANDITS_BREW_(BANDITS_BREW, BEER_GLASS, 829, true, false, false, false),
		DRAGON_BITTER_(DRAGON_BITTER, BEER_GLASS, 829, true, false, false, false),
		CIDER_(CIDER_7752, BEER_GLASS, 829, true, false, false, false),
		MATURE_CIDER_(MATURE_CIDER, BEER_GLASS, 829, true, false, false, false),
		MOONLIGHT_MEAD_(MOONLIGHT_MEAD_7750, BEER_GLASS, 829, true, false, false, false),
		DWARVEN_STOUT_(DWARVEN_STOUT, BEER_GLASS, 829, true, false, false, false),
		GREENMANS_ALE_(GREENMANS_ALE, BEER_GLASS, 829, true, false, false, false),
		CHEFS_DELIGHT_(CHEFS_DELIGHT_7754, BEER_GLASS, 829, true, false, false, false),
		ASGARNIAN_ALE_(ASGARNIAN_ALE, BEER_GLASS, 829, true, false, false, false),
		WIZARDS_MIND_BOMB_(WIZARDS_MIND_BOMB, BEER_GLASS, 829, true, false, false, false);

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
			double beerEffectStrength = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.STRENGTH]) * .04 + c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.STRENGTH]);
			double beerEffectAttack = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.ATTACK]) * .07;
			if (c.playerLevel[Constants.STRENGTH] < beerEffectStrength) {
				c.playerLevel[Constants.STRENGTH] = (int) beerEffectStrength;
			}
			if (c.playerLevel[Constants.ATTACK] > 0) {
				c.playerLevel[Constants.ATTACK] -= beerEffectAttack;
			}
			if (c.playerLevel[Constants.ATTACK] <= 0) {
				c.playerLevel[Constants.ATTACK] = 1;
			}
			c.getPlayerAssistant().refreshSkill(0);
			c.getPlayerAssistant().refreshSkill(Constants.STRENGTH);
			break;
		case 1913: //dwarven stout
			c.playerLevel[Constants.MINING] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.MINING]) + 1;
			c.playerLevel[Constants.SMITHING] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.SMITHING]) + 1;
			c.getPlayerAssistant().refreshSkill(Constants.MINING);
			c.getPlayerAssistant().refreshSkill(Constants.SMITHING);
		break;
		case 1907://wizard's mind bomb
			if (c.playerLevel[Constants.MAGIC] < 50) {
				c.playerLevel[Constants.MAGIC] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.MAGIC]) + 2;
			} else {
				c.playerLevel[Constants.MAGIC] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.MAGIC]) + 3;
			}
			c.playerLevel[Constants.STRENGTH] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.STRENGTH]) - 3;
			c.playerLevel[Constants.DEFENCE] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.DEFENCE]) - 3;
			c.playerLevel[Constants.ATTACK] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.ATTACK]) - 4;
			c.getPlayerAssistant().refreshSkill(Constants.DEFENCE);
			c.getPlayerAssistant().refreshSkill(Constants.MAGIC);
			c.getPlayerAssistant().refreshSkill(Constants.ATTACK);
			c.getPlayerAssistant().refreshSkill(Constants.STRENGTH);
		break;
		case 1915://grog
			c.playerLevel[Constants.STRENGTH] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.STRENGTH]) + 3;
			if (c.playerLevel[Constants.ATTACK] > 0) {
				c.playerLevel[Constants.ATTACK] = c.getPlayerAssistant().getLevelForXP(c.playerXP[Constants.ATTACK]) - 2;
			}
			if (c.playerLevel[Constants.ATTACK] <= 0) {
				c.playerLevel[Constants.ATTACK] = 1;
			}
			c.getPlayerAssistant().refreshSkill(0);
			c.getPlayerAssistant().refreshSkill(Constants.STRENGTH);
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