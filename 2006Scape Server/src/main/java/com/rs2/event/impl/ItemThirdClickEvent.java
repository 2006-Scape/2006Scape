package com.rs2.event.impl;

import com.rs2.event.Event;

public final class ItemThirdClickEvent implements Event {
	
	private final int id;
	
	public ItemThirdClickEvent(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}