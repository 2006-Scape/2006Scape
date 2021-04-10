package com.rs2.event.impl;


import com.rs2.event.Event;
import com.rs2.game.items.Item;

public final class ItemFirstClickEvent implements Event {
	
	private final int item;
	
	public ItemFirstClickEvent(int item) {
		this.item = item;
	}

	public int getItem() {
		return item;
	}

}
