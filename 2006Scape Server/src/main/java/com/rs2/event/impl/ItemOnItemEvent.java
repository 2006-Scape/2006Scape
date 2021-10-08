package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.game.items.Item;

public final class ItemOnItemEvent implements Event {
	
	private final int used;
	
	private final int with;
	
	public ItemOnItemEvent(int used, int with) {
		this.used = used;
		this.with = with;
	}

	public int getUsed() {
		return used;
	}

	public int getUsedWith() {
		return with;
	}	

}
