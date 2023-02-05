package com.rs2.game.items;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.decoder.ItemDefinitionDecoder;
import org.apollo.jagcached.Constants;

import com.rs2.GameConstants;
import com.rs2.GameEngine;

public class Deprecated {
	

		
	//TODO this is super scuffed and used for Potion Mixing. Redo.
	public static int getItemId(String itemName) {
		for (int i = 0; i < GameConstants.ITEM_LIMIT; i++) {
			if (GameEngine.itemHandler.itemList[i] != null) {
				if (GameEngine.itemHandler.itemList[i].itemName.equalsIgnoreCase(itemName)) {
					return GameEngine.itemHandler.itemList[i].itemId;
				}
			}
		}
		return -1;
	}

	
	public static String getItemName(int ItemID) {
		if(org.apollo.cache.def.ItemDefinition.lookup(ItemID) == null)
			return "Unarmed";
		return org.apollo.cache.def.ItemDefinition.lookup(ItemID).getName();
	}
}
