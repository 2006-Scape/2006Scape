package com.rs2.event.impl;

import com.rs2.event.Event;

public final class ItemSecondClickEvent implements Event {


	private final int id;

	private final int slot;

	public ItemSecondClickEvent(int id, int slot) {
		this.id = id;
		this.slot = slot;
	}

	public int getId() {
		return id;
	}

	public int getSlot() {
		return slot;
	}

}
