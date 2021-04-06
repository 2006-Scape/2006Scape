package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.game.items.Item;

public final class ItemOnItemEvent implements Event {
	
	private final Item used;
	
	private final Item with;	
	
	public ItemOnItemEvent(Item used, Item with) {		
		this.used = used;
		this.with = with;
	}

	public Item getUsed() {
		return used;
	}

	public Item getUsedWith() {
		return with;
	}	

}
