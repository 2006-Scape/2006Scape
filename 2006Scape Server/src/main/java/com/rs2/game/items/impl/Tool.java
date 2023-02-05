package com.rs2.game.items.impl;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.cache.decoder.ItemDefinitionDecoder;
import org.apollo.cache.def.ItemDefinition;
import org.apollo.jagcached.Constants;

import com.rs2.game.content.skills.thieving.Stalls;
import com.rs2.game.content.skills.thieving.Stalls.stallData;

public class Tool {
	//TODO remove later after done testing defs
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		IndexedFileSystem fs = new IndexedFileSystem(Paths.get(Constants.FILE_SYSTEM_DIR), true);
		new ItemDefinitionDecoder(fs).run();
		
	//	System.out.println(ItemDefinition.lookup(4157).getValue());
//		for (int i = 0; i < picks.length; i++) {
//			System.out.println(Arrays.toString(picks[i]));
//		//	System.out.println(ItemDefinition.lookup(picks[i][0]).getName());
//		}
		
		for(Stalls.stallData s : stallData.values()) {
			System.out.println(s.name());
			for (int i = 0; i < s.getStalls().length; i++) { 
		//		System.out.println(s.getStalls()[i][0]);
				System.out.println(Arrays.toString(s.getStalls()[i]));

			}
		}
		
	//	System.out.println(java.util.Arrays.asList(stallData.values()));
	} 
		
		public static int i(String ItemName) {
			return getItemId(ItemName);
		}
		
		public static int getItemId(String itemName) {
			for (ItemDefinition i : ItemDefinition.getDefinitions()) {
				if (i != null) {
					if(i.getName() != null)
					if (i.getName().equalsIgnoreCase(itemName)) {
						return i.getId();
					}
				}
			}
			return -1;
		}
}
