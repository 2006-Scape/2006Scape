package com.rs2.game.items;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.decoder.ItemDefinitionDecoder;
import org.apollo.jagcached.Constants;

import com.google.gson.Gson;
import com.rs2.GameConstants;
import com.rs2.GameEngine;
import com.rs2.util.ItemData.Bonuses;
import com.rs2.world.ItemHandler;

public class ItemDumper {

	public static ItemHandler itemHandler = new ItemHandler();

	private static int[] EMPTY = new int[] {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
	};
	public static void main(String[] args) throws FileNotFoundException {
		IndexedFileSystem fs = new IndexedFileSystem(Paths.get(Constants.FILE_SYSTEM_DIR), true);
		new ItemDefinitionDecoder(fs).run();

		ItemDefinition.read();

		for (int i = 0; i < org.apollo.cache.def.ItemDefinition.count(); i++) {
			// org.apollo.cache.def.ItemDefinition def =
			// org.apollo.cache.def.ItemDefinition.lookup(i);
			AdditionalData data = new AdditionalData(i);
			double weight = ItemDefinition.getWeight(i);
			if (weight != 0.0)
				data.weight = weight;
			int[] bonus = getBonus(i);
			Bonuses b = getBonus2(i);
			if (bonus != null)
				data.bonuses = b;
		//	System.out.println(Arrays.toString(bonus));
			if(weight != 0.0 || (bonus != null && !Arrays.equals(bonus, EMPTY)))
				definitions.add(data);
		}

		Gson GSON = new Gson();
//		/* private static final */Path PATH = Paths.get("data", "def", "items");
//		File fileToSave = new File(PATH.normalize().toFile().getAbsoluteFile() + File.separator + fileName + ".json");
//		if (!fileToSave.exists()) {
//			try {
//				if (!fileToSave.createNewFile()) {
//					throw new IllegalStateException("Couldn't create file for item" + fileToSave.getName());
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		try (FileWriter fw = new FileWriter("./dumped.json")) {
			fw.write(GSON.toJson(definitions)); // i.e ItemDefinitions.get(id)
	//		System.out.println("written to file " + mobDrop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static LinkedList<AdditionalData> definitions = new LinkedList<>();
	//private static Map<Integer, AdditionalData> definitions = new HashMap<>();

	public static int[] getBonus(int element) {
		if (element > -1) {
			for (int j = 0; j < GameConstants.ITEM_LIMIT; j++) {
				if (GameEngine.itemHandler.itemList[j] != null) {
					if (GameEngine.itemHandler.itemList[j].itemId == element) {
						return GameEngine.itemHandler.itemList[j].Bonuses;
					}
				}
			}
		}
		return null;
	}
	
	public static Bonuses getBonus2(int element) {
		if (element > -1) {
			for (int j = 0; j < GameConstants.ITEM_LIMIT; j++) {
				if (GameEngine.itemHandler.itemList[j] != null) {
					if (GameEngine.itemHandler.itemList[j].itemId == element) {
						return GameEngine.itemHandler.itemList[j].Bonuses2;
					}
				}
			}
		}
		return null;
	}
}
