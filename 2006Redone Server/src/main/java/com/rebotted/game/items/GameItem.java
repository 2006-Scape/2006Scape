package com.rebotted.game.items;

public class GameItem {
	
        public int id, amount;
        public boolean stackable = false;
 
        public GameItem(int id, int amount) {
        	if (ItemData.itemStackable[id]) {
        		stackable = true;
        	}
        	this.id = id;
        	this.amount = amount;
        }
        
}