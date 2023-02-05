package com.rs2.game.items;

import org.apollo.cache.def.ItemDefinition;

public class GameItem {
	
        public int id, amount;
        public boolean stackable = false;
 
        public GameItem(int id, int amount) {
        	if (ItemDefinition.lookup(id).isStackable()) {
        		stackable = true;
        	}
        	this.id = id;
        	this.amount = amount;
        }
        
}