package redone.game.content.skills.crafting;

import redone.game.content.skills.SkillHandler;

public class CraftingData extends SkillHandler {

	public static enum tanningData {

		SOFT_LEATHER(new int[][] { { 57225, 1 }, { 57217, 5 }, { 57201, 28 } },
				1739, 1741, 1, new int[] { 14777, 14785, 14769 },
				"Soft leather"), HARD_LEATHER(new int[][] { { 57226, 1 },
				{ 57218, 5 }, { 57202, 28 } }, 1739, 1743, 3, new int[] {
				14778, 14786, 14770 }, "Hard leather"), SNAKESKIN(new int[][] {
				{ 57227, 1 }, { 57219, 5 }, { 57203, 28 } }, 6287, 6289, 15,
				new int[] { 14779, 14787, 14771 }, "Snakeskin"), SNAKESKIN2(
				new int[][] { { 57228, 1 }, { 57220, 5 }, { 57204, 28 } },
				6287, 6289, 20, new int[] { 14780, 14788, 14772 }, "Snakeskin"), GREEN_DRAGON_LEATHER(
				new int[][] { { 57229, 1 }, { 57221, 5 }, { 57205, 28 } },
				1753, 1745, 20, new int[] { 14781, 14789, 14773 },
				"Green d'hide"), BLUE_DRAGON_LEATHER(new int[][] {
				{ 57230, 1 }, { 57222, 5 }, { 57206, 28 } }, 1751, 2505, 20,
				new int[] { 14782, 14790, 14774 }, "Blue d'hide"), RED_DRAGON_LEATHER(
				new int[][] { { 57231, 1 }, { 57223, 5 }, { 57207, 28 } },
				1749, 2507, 20, new int[] { 14783, 14791, 14775 }, "Red d'hide"), BLACK_DRAGON_LEATHER(
				new int[][] { { 57232, 1 }, { 57224, 5 }, { 57208, 28 } },
				1747, 2509, 20, new int[] { 14784, 14792, 14776 },
				"Black d'hide");

		private final int[][] buttonId;
		private final int hideId, leatherId, price;
		private final int[] frameId;
		private final String name;

		private tanningData(final int[][] buttonId, final int hideId,
				final int leatherId, final int price, final int[] frameId,
				final String name) {
			this.buttonId = buttonId;
			this.hideId = hideId;
			this.leatherId = leatherId;
			this.price = price;
			this.frameId = frameId;
			this.name = name;
		}

		public int getButtonId(final int button) {
			for (int[] element : buttonId) {
				if (button == element[0]) {
					return element[0];
				}
			}
			return -1;
		}

		public int getAmount(final int button) {
			for (int[] element : buttonId) {
				if (button == element[0]) {
					return element[1];
				}
			}
			return -1;
		}

		public int getHideId() {
			return hideId;
		}

		public int getLeatherId() {
			return leatherId;
		}

		public int getPrice() {
			return price;
		}

		public int getNameFrame() {
			return frameId[0];
		}

		public int getCostFrame() {
			return frameId[1];
		}

		public int getItemFrame() {
			return frameId[2];
		}

		public String getName() {
			return name;
		}
	}

	public static enum leatherDialogueData {

		GREEN_LEATHER(1745, 1065, 1135, 1099), BLUE_LEATHER(2505, 2487, 2499,
				2493), RED_LEATHER(2507, 2489, 2501, 2495), BLACK_LEATHER(2509,
				2491, 2503, 2497);

		private final int leather, vambraces, chaps, body;

		private leatherDialogueData(final int leather, final int vambraces,
				final int chaps, final int body) {
			this.leather = leather;
			this.vambraces = vambraces;
			this.chaps = chaps;
			this.body = body;
		}

		public int getLeather() {
			return leather;
		}

		public int getVamb() {
			return vambraces;
		}

		public int getChaps() {
			return chaps;
		}

		public int getBody() {
			return body;
		}
	}

	public enum Leather {

		LEATHER_GLOVES(1733, 1741, 1059, 1, 1, 13.75), LEATHER_BOOTS(1733,
				1741, 1061, 1, 7, 16.25);

		public int item1, leatherId, product, amountOfLeather, level;
		public double xp;

		public static Leather forId(int itemUsed, int usedWith) {
			for (Leather Data : Leather.values()) {
				if (Data.item1 == itemUsed && Data.leatherId == usedWith
						|| Data.leatherId == itemUsed && Data.item1 == usedWith) {
					return Data;
				}
			}
			return null;
		}

		private Leather(int item1, int leatherId, int product,
				int amountOfLeather, int level, double xp) {
			this.leatherId = leatherId;
			this.product = product;
			this.amountOfLeather = amountOfLeather;
			this.level = level;
			this.xp = xp;
		}

		public int needle() {
			return item1;
		}

		public int getLeather() {
			return leatherId;
		}

		public int getProduct() {
			return product;
		}

		public int amountOfLeather4Product() {
			return amountOfLeather;
		}

		public int getLevel() {
			return level;
		}

		public double getXp() {
			return xp;
		}
	}

	public static enum leatherData {

		LEATHER_BODY(new int[][] { { 33187, 1 }, { 33186, 5 }, { 33185, 10 } },
				1741, 1129, 14, 25, 1), LEATHER_GLOVES(new int[][] {
				{ 33190, 1 }, { 33189, 5 }, { 33188, 10 } }, 1741, 1059, 1,
				13.8, 1), LEATHER_BOOTS(new int[][] { { 33193, 1 },
				{ 33192, 5 }, { 33191, 10 } }, 1741, 1061, 7, 16.25, 1), LEATHER_VAMBRACES(
				new int[][] { { 33196, 1 }, { 33195, 5 }, { 33194, 10 } }, 1741,
				1063, 11, 22, 1), LEATHER_CHAPS(new int[][] { { 33199, 1 },
				{ 33198, 5 }, { 33197, 10 } }, 1741, 1095, 18, 27, 1), LEATHER_COIF(
				new int[][] { { 33202, 1 }, { 33201, 5 }, { 33200, 10 } },
				1741, 1169, 38, 37, 1), LEATHER_COWL(new int[][] {
				{ 33205, 1 }, { 33204, 5 }, { 33203, 10 } }, 1741, 1167, 9,
				18.5, 1), HARD_LEATHER_BODY(new int[][] { { 10239, 1 },
				{ 10238, 5 }, { 6212, 28 } }, 1743, 1131, 28, 35, 1), SNAKESKIN_BODY(
				new int[][] { { 34245, 1 }, { 34244, 5 }, { 34243, 10 },
						{ 34242, 28 } }, 6289, 6322, 53, 55, 15), SNAKESKIN_CHAPS(
				new int[][] { { 34249, 1 }, { 34248, 5 }, { 34247, 10 },
						{ 34246, 28 } }, 6289, 6324, 51, 50, 12), SNAKESKIN_BANDANA(
				new int[][] { { 34253, 1 }, { 34252, 5 }, { 34251, 10 },
						{ 34250, 28 } }, 6289, 6326, 48, 45, 5), SNAKESKIN_BOOTS(
				new int[][] { { 35001, 1 }, { 35000, 5 }, { 34255, 10 },
						{ 34254, 28 } }, 6289, 6328, 45, 30, 6), SNAKESKIN_VAMBRACES(
				new int[][] { { 35005, 1 }, { 35004, 5 }, { 35003, 10 },
						{ 35002, 28 } }, 6289, 6330, 47, 35, 8), GREEN_DHIDE_VAMBRACES(
				new int[][] { { 34185, 1 }, { 34184, 5 }, { 34183, 10 },
						{ 34182, 28 } }, 1745, 1065, 57, 62, 1), GREEN_DHIDE_BODY(
				new int[][] { { 34189, 1 }, { 34188, 5 }, { 34187, 10 },
						{ 34186, 28 } }, 1745, 1135, 63, 186, 3), GREEN_DHIDE_CHAPS(
				new int[][] { { 34193, 1 }, { 34192, 5 }, { 34191, 10 },
						{ 34190, 28 } }, 1745, 1099, 60, 124, 2), BLUE_DHIDE_VAMBRACES(
				new int[][] { { 34185, 1 }, { 34184, 5 }, { 34183, 10 },
						{ 34182, 28 } }, 2505, 2487, 66, 70, 1), BLUE_DHIDE_BODY(
				new int[][] { { 34189, 1 }, { 34188, 5 }, { 34187, 10 },
						{ 34186, 28 } }, 2505, 2499, 71, 210, 3), BLUE_DHIDE_CHAPS(
				new int[][] { { 34193, 1 }, { 34192, 5 }, { 34191, 10 },
						{ 34190, 28 } }, 2505, 2493, 68, 140, 2), RED_DHIDE_VAMBRACES(
				new int[][] { { 34185, 1 }, { 34184, 5 }, { 34183, 10 },
						{ 34182, 28 } }, 2507, 2489, 73, 78, 1), RED_DHIDE_BODY(
				new int[][] { { 34189, 1 }, { 34188, 5 }, { 34187, 10 },
						{ 34186, 28 } }, 2507, 2501, 77, 234, 3), RED_DHIDE_CHAPS(
				new int[][] { { 34193, 1 }, { 34192, 5 }, { 34191, 10 },
						{ 34190, 28 } }, 2507, 2495, 75, 156, 2), BLACK_DHIDE_VAMBRACES(
				new int[][] { { 34185, 1 }, { 34184, 5 }, { 34183, 10 },
						{ 34182, 28 } }, 2509, 2491, 79, 86, 1), BLACK_DHIDE_BODY(
				new int[][] { { 34189, 1 }, { 34188, 5 }, { 34187, 10 },
						{ 34186, 28 } }, 2509, 2503, 84, 258, 3), BLACK_DHIDE_CHAPS(
				new int[][] { { 34193, 1 }, { 34192, 5 }, { 34191, 10 },
						{ 34190, 28 } }, 2509, 2497, 82, 172, 2);

		private final int[][] buttonId;
		private final int leather, product, level, amount;
		private final double xp;

		private leatherData(final int[][] buttonId, final int leather,
				final int product, final int level, final double xp,
				final int amount) {
			this.buttonId = buttonId;
			this.leather = leather;
			this.product = product;
			this.level = level;
			this.xp = xp;
			this.amount = amount;
		}

		public int getButtonId(final int button) {
			for (int[] element : buttonId) {
				if (button == element[0]) {
					return element[0];
				}
			}
			return -1;
		}

		public int getAmount(final int button) {
			for (int[] element : buttonId) {
				if (button == element[0]) {
					return element[1];
				}
			}
			return -1;
		}

		public int getLeather() {
			return leather;
		}

		public int getProduct() {
			return product;
		}

		public int getLevel() {
			return level;
		}

		public double getXP() {
			return xp;
		}

		public int getHideAmount() {
			return amount;
		}
	}

	public static enum cutGemData {

		SAPPHIRE(1623, 1607, 20, 50, 888), EMERALD(1621, 1605, 27, 67, 889), RUBY(
				1619, 1603, 34, 85, 887), DIAMOND(1617, 1601, 43, 107.5, 886), DRAGONSTONE(
				1631, 1615, 55, 137.5, 885), ONYX(6571, 6573, 67, 168, 885), /**
		 * 
		 * Need correct animation ID
		 **/
		OPAL(1625, 1609, 1, 12, 890), JADE(1627, 1611, 13, 20, 891), RED_TOPAZ(
				1629, 1613, 16, 25, 892);

		private final int uncut, cut, level, animation;
		private final double xp;

		private cutGemData(final int uncut, final int cut, final int level,
				final double xp, final int animation) {
			this.uncut = uncut;
			this.cut = cut;
			this.level = level;
			this.xp = xp;
			this.animation = animation;
		}

		public int getUncut() {
			return uncut;
		}

		public int getCut() {
			return cut;
		}

		public int getLevel() {
			return level;
		}

		public double getXP() {
			return xp;
		}

		public int getAnimation() {
			return animation;
		}
	}

	public static enum jewelryData {

		RINGS(new int[][] { { 2357, 1635, 5, 15 }, { 1607, 1637, 20, 40 },
				{ 1605, 1639, 27, 55 }, { 1603, 1641, 34, 70 },
				{ 1601, 1643, 43, 85 }, { 1615, 1645, 55, 100 },
				{ 6573, 6575, 67, 115 } }), NECKLACE(new int[][] {
				{ 2357, 1654, 6, 20 }, { 1607, 1656, 22, 55 },
				{ 1605, 1658, 29, 60 }, { 1603, 1660, 40, 75 },
				{ 1601, 1662, 56, 90 }, { 1615, 1664, 72, 105 },
				{ 6573, 6577, 82, 120 } }), AMULETS(new int[][] {
				{ 2357, 1673, 8, 30 }, { 1607, 1675, 24, 65 },
				{ 1605, 1677, 31, 70 }, { 1603, 1679, 50, 85 },
				{ 1601, 1681, 70, 100 }, { 1615, 1683, 80, 150 },
				{ 6573, 6579, 90, 165 } });

		public int[][] item;

		private jewelryData(final int[][] item) {
			this.item = item;
		}
	}

	public static enum amuletData {
		GOLD(1673, 1692), SAPPHIRE(1675, 1694), EMERALD(1677, 1696), RUBY(1679,
				1698), DIAMOND(1681, 1700), DRAGONSTONE(1683, 1702), ONYX(6579,
				6851);

		private final int amuletId, product;

		private amuletData(final int amuletId, final int product) {
			this.amuletId = amuletId;
			this.product = product;
		}

		public int getAmuletId() {
			return amuletId;
		}

		public int getProduct() {
			return product;
		}
	}
}
