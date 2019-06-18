package redone.game.content.combat.prayer;

public class PrayerData {

	public int prayerId = -1;
	public static double prayerPoint = 1.0;
	public long stopPrayerDelay, prayerDelay;
	public boolean usingPrayer;

	public final int[] PRAYER_DRAIN_RATE = { 500, 500, 500, 500, 500, 500, 500,
			500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
			500, 500, 500, 500, 500, 500 };

	public final int[] PRAYER_LEVEL_REQUIRED = { 1, 4, 7, 8, 9, 10, 13, 16, 19,
			22, 25, 26, 27, 28, 31, 34, 37, 40, 43, 44, 45, 46, 49, 52, 60, 70 };

	public final int[] PRAYER = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };

	public final String[] PRAYER_NAME = { "Thick Skin", "Burst of Strength",
			"Clarity of Thought", "Sharp Eye", "Mystic Will", "Rock Skin",
			"Superhuman Strength", "Improved Reflexes", "Rapid Restore",
			"Rapid Heal", "Protect Item", "Hawk Eye", "Mystic Lore",
			"Steel Skin", "Ultimate Strength", "Incredible Reflexes",
			"Protect from Magic", "Protect from Missiles",
			"Protect from Melee", "Eagle Eye", "Mystic Might", "Retribution",
			"Redemption", "Smite", "Chivalry", "Piety" };

	public final int[] PRAYER_GLOW = { 83, 84, 85, 601, 602, 86, 87, 88, 89,
			90, 91, 603, 604, 92, 93, 94, 95, 96, 97, 605, 606, 98, 99, 100,
			607, 608 };

	public final int[] PRAYER_HEAD_ICONS = { -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, 2, 1, 0, -1, -1, 3, 5, 4, -1, -1 };

	public boolean[] prayerActive = { false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false };

	/**
	 * How fast the prayer is drained
	 */

	public static final double[] prayerData = { 1, // Thick Skin.
			1, // Burst of Strength.
			1, // Clarity of Thought.
			1, // Sharp Eye.
			1, // Mystic Will.
			2, // Rock Skin.
			2, // SuperHuman Strength.
			2, // Improved Reflexes.
			0.4, // Rapid restore.
			0.6, // Rapid Heal.
			0.6, // Protect Items.
			1.5, // Hawk eye.
			2, // Mystic Lore.
			4, // Steel Skin.
			4, // Ultimate Strength.
			4, // Incredible Reflexes.
			4, // Protect from Magic.
			4, // Protect from Missiles.
			4, // Protect from Melee.
			4, // Eagle Eye.
			4, // Mystic Might.
			1, // Retribution.
			2, // Redemption.
			6, // Smite.
			8, // Chivalry.
			8, // Piety.
	};
}
