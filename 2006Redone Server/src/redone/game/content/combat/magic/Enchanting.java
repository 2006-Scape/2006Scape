package redone.game.content.combat.magic;

import java.util.HashMap;
import java.util.Map;

import redone.game.players.Client;

public class Enchanting {

	private final Client c;

	public Enchanting(Client c) {
		this.c = c;
	}

	public enum Enchant {

		SAPPHIRERING(1637, 2550, 7, 18, 719, 114, 1), SAPPHIREAMULET(1694,
				1727, 7, 18, 719, 114, 1), SAPPHIRENECKLACE(1656, 3853, 7, 18,
				719, 114, 1),

		EMERALDRING(1639, 2552, 27, 37, 719, 114, 2), EMERALDAMULET(1696, 1729,
				27, 37, 719, 114, 2), EMERALDNECKLACE(1658, 5521, 27, 37, 719,
				114, 2),

		RUBYRING(1641, 2568, 47, 59, 720, 115, 3), RUBYAMULET(1698, 1725, 47,
				59, 720, 115, 3),
		// RUBYNECKLACE(1660, 11194, 47, 59, 720, 115, 3),

		DIAMONDRING(1643, 2570, 57, 67, 720, 115, 4), DIAMONDAMULET(1700, 1731,
				57, 67, 720, 115, 4),
		// DIAMONDNECKLACE(1662, 11090, 57, 67, 720, 115, 4),

		DRAGONSTONERING(1645, 2572, 68, 78, 721, 116, 5), 
		DRAGONSTONEAMULET(1702, 1704, 68, 78, 721, 116, 5),
		// DRAGONSTONENECKLACE(1664, 11105, 68, 78, 721, 116, 5),

		ONYXRING(6575, 6583, 87, 97, 721, 452, 6), ONYXAMULET(6581, 6585, 87,
				97, 721, 452, 6);

		// ONYXNECKLACE(6577, 11128, 87, 97, 721, 452, 6)

		int unenchanted, enchanted, levelReq, xpGiven, anim, gfx,
				reqEnchantmentLevel;

		private Enchant(int unenchanted, int enchanted, int levelReq,
				int xpGiven, int anim, int gfx, int reqEnchantmentLevel) {
			this.unenchanted = unenchanted;
			this.enchanted = enchanted;
			this.levelReq = levelReq;
			this.xpGiven = xpGiven;
			this.anim = anim;
			this.gfx = gfx;
			this.reqEnchantmentLevel = reqEnchantmentLevel;
		}

		public int getUnenchanted() {
			return unenchanted;
		}

		public int getEnchanted() {
			return enchanted;
		}

		public int getLevelReq() {
			return levelReq;
		}

		public int getXp() {
			return xpGiven;
		}

		public int getAnim() {
			return anim;
		}

		public int getGFX() {
			return gfx;
		}

		public int getELevel() {
			return reqEnchantmentLevel;
		}

		private static final Map<Integer, Enchant> enc = new HashMap<Integer, Enchant>();

		public static Enchant forId(int itemID) {
			return enc.get(itemID);
		}

		static {
			for (Enchant en : Enchant.values()) {
				enc.put(en.getUnenchanted(), en);
			}
		}
	}

	private enum EnchantSpell {

		SAPPHIRE(1155, 555, 1, 564, 1, -1, 0), EMERALD(1165, 556, 3, 564, 1,
				-1, 0), RUBY(1176, 554, 5, 564, 1, -1, 0), DIAMOND(1180, 557,
				10, 564, 1, -1, 0), DRAGONSTONE(1187, 555, 15, 557, 15, 564, 1), ONYX(
				6003, 557, 20, 554, 20, 564, 1);

		int spell, reqRune1, reqAmtRune1, reqRune2, reqAmtRune2, reqRune3,
				reqAmtRune3;

		private EnchantSpell(int spell, int reqRune1, int reqAmtRune1,
				int reqRune2, int reqAmtRune2, int reqRune3, int reqAmtRune3) {
			this.spell = spell;
			this.reqRune1 = reqRune1;
			this.reqAmtRune1 = reqAmtRune1;
			this.reqRune2 = reqRune2;
			this.reqAmtRune2 = reqAmtRune2;
			this.reqRune3 = reqRune3;
			this.reqAmtRune3 = reqAmtRune3;
		}

		public int getSpell() {
			return spell;
		}

		public int getReq1() {
			return reqRune1;
		}

		public int getReqAmt1() {
			return reqAmtRune1;
		}

		public int getReq2() {
			return reqRune2;
		}

		public int getReqAmt2() {
			return reqAmtRune2;
		}

		public int getReq3() {
			return reqRune3;
		}

		public int getReqAmt3() {
			return reqAmtRune3;
		}

		public static final Map<Integer, EnchantSpell> ens = new HashMap<Integer, EnchantSpell>();

		public static EnchantSpell forId(int id) {
			return ens.get(id);
		}

		static {
			for (EnchantSpell en : EnchantSpell.values()) {
				ens.put(en.getSpell(), en);
			}
		}

	}

	private boolean hasRunes(int spellID) {
		EnchantSpell ens = EnchantSpell.forId(spellID);
		if (ens.getReq3() == 0) {
			return c.getItemAssistant().playerHasItem(ens.getReq1(),
					ens.getReqAmt1())
					&& c.getItemAssistant().playerHasItem(ens.getReq2(),
							ens.getReqAmt2())
					&& c.getItemAssistant().playerHasItem(ens.getReq3(),
							ens.getReqAmt3());
		} else {
			return c.getItemAssistant().playerHasItem(ens.getReq1(),
					ens.getReqAmt1())
					&& c.getItemAssistant().playerHasItem(ens.getReq2(),
							ens.getReqAmt2());
		}
	}

	private int getEnchantmentLevel(int spellID) {
		switch (spellID) {
		case 1155: // Lvl-1 enchant sapphire
			return 1;
		case 1165: // Lvl-2 enchant emerald
			return 2;
		case 1176: // Lvl-3 enchant ruby
			return 3;
		case 1180: // Lvl-4 enchant diamond
			return 4;
		case 1187: // Lvl-5 enchant dragonstone
			return 5;
		case 6003: // Lvl-6 enchant onyx
			return 6;
		}
		return 0;
	}

	public void enchantItem(int itemID, int spellID) {
		Enchant enc = Enchant.forId(itemID);
		EnchantSpell ens = EnchantSpell.forId(spellID);
		if (enc == null || ens == null) {

			return;
		}
		if (c.playerLevel[c.playerMagic] >= enc.getLevelReq()) {
			if (c.getItemAssistant().playerHasItem(enc.getUnenchanted(), 1)) {
				if (hasRunes(spellID)) {
					if (getEnchantmentLevel(spellID) == enc.getELevel()) {
						c.getItemAssistant().deleteItem(enc.getUnenchanted(), 1);
						c.getItemAssistant().addItem(enc.getEnchanted(), 1);
						c.getPlayerAssistant().addSkillXP(enc.getXp(),
								c.playerMagic);
						c.getItemAssistant()
								.deleteItem(
										ens.getReq1(),
										c.getItemAssistant().getItemSlot(
												ens.getReq1()),
										ens.getReqAmt1());
						c.getItemAssistant()
								.deleteItem(
										ens.getReq2(),
										c.getItemAssistant().getItemSlot(
												ens.getReq2()),
										ens.getReqAmt2());
						c.startAnimation(enc.getAnim());
						c.gfx100(enc.getGFX());
						if (ens.getReq3() != -1) {
							c.getItemAssistant().deleteItem(
									ens.getReq3(),
									c.getItemAssistant().getItemSlot(
											ens.getReq3()), ens.getReqAmt3());
						}
						c.getPlayerAssistant().sendFrame106(6);
					} else {
						c.getActionSender().sendMessage(
								"You can only enchant this jewelry using a level-"
										+ enc.getELevel()
										+ " enchantment spell!");
					}
				} else {
					c.getActionSender().sendMessage(
							"You do not have enough runes to cast this spell.");
				}
			}
		} else {
			c.getActionSender().sendMessage(
					"You need a magic level of at least " + enc.getLevelReq()
							+ " to cast this spell.");
		}
	}
}
