package com.rs2.game.items;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.decoder.ItemDefinitionDecoder;
import org.apollo.jagcached.Constants;

import com.rs2.GameConstants;
import com.rs2.GameEngine;

public class Deprecated {
	
	//TODO remove later after done testing defs
	public static void main(String[] args) throws FileNotFoundException {
		IndexedFileSystem fs = new IndexedFileSystem(Paths.get(Constants.FILE_SYSTEM_DIR), true);
		new ItemDefinitionDecoder(fs).run();
	//	System.out.println(org.apollo.cache.def.ItemDefinition.lookup(4157).getValue());
	}

	//TODO this is super scuffed and used for Potion Mixing. Redo.
	public static int getItemId(String itemName) {
		for (int i = 0; i < GameConstants.ITEM_LIMIT; i++) {
			if (GameEngine.itemHandler.itemList[i] != null) {
				if (GameEngine.itemHandler.itemList[i].itemName
						.equalsIgnoreCase(itemName)) {
					return GameEngine.itemHandler.itemList[i].itemId;
				}
			}
		}
		return -1;
	}
}
