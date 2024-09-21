package com.rs2.game.items;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rs2.util.Misc;

/**
 * Loads the bonuses and weights from a json file.
 * 
 * @author Advocatus
 *
 */
public class ItemDefinitions {

	private static Map<Integer, Definition> defintions = new HashMap<>();

	private static final int[] EMPTY_BONUSES = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	/**
	 * Gets the array of bonuses associated with this item id.
	 * 
	 * @param id The item id.
	 * @return The bonuses or an empty array.
	 */
	public static int[] getBonus(int id) {
		Definition def = defintions.get(id);
		return def != null && def.bonuses != null ? def.bonuses : EMPTY_BONUSES;
	}

	/**
	 * Gets the weight of the item.
	 * 
	 * @param id The item id.
	 * @return The weight or 0.0 by default.
	 */
	public static double getWeight(int id) {
		Definition def = defintions.get(id);
		return def != null ? def.weight : 0.0;
	}

	public static void load() {
		Gson gson = new Gson();

		try {
			Type collectionType = new TypeToken<ItemData[]>() {
			}.getType();
			ItemData[] data = gson.fromJson(new FileReader("./data/cfg/ItemDefinitions.json"), collectionType);

			for (ItemData item : data) {
				defintions.put(item.id, new Definition(item));
			}
		} catch (FileNotFoundException fileex) {
			System.out.println("items.json: file not found.");
		}
	}

	private static class Definition {
		private double weight;
		private int[] bonuses;

		private Definition(ItemData item) {
			this.weight = item.weight;
			this.bonuses = item.bonuses != null ? item.bonuses.getBonuses() : null;
		}
	}

	private static class ItemData {
		private int id;
		private double weight = 0.0;
		private Bonuses bonuses;
	}

	private static class Bonuses {
		int attackStab;
		int attackSlash;
		int attackCrush;
		int attackMagic;
		int attackRange;
		int defenceStab;
		int defenceSlash;
		int defenceCrush;
		int defenceMagic;
		int defenceRange;
		int strengthBonus;
		int prayerBonus;

		public int[] getBonuses() {
			int[] bonuses = new int[12];

			bonuses[0] = this.attackStab;
			bonuses[1] = this.attackSlash;
			bonuses[2] = this.attackCrush;
			bonuses[3] = this.attackMagic;
			bonuses[4] = this.attackRange;
			bonuses[5] = this.defenceStab;
			bonuses[6] = this.defenceSlash;
			bonuses[7] = this.defenceCrush;
			bonuses[8] = this.defenceMagic;
			bonuses[9] = this.defenceRange;
			bonuses[10] = this.strengthBonus;
			bonuses[11] = this.prayerBonus;

			return bonuses;
		}
	}
}
