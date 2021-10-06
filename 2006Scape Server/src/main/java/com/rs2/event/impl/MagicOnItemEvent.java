package com.rs2.event.impl;

import com.rs2.event.Event;

public final class MagicOnItemEvent implements Event {
	
	private final int itemId;
	
	private final int slot;

	
	private final int spellId;
	
	public MagicOnItemEvent(int itemId, int slot, int spellId) {
		this.itemId = itemId;
		this.slot = slot;
		this.spellId = spellId;
	}

	public int getItemId() {
		return itemId;
	}

	public int getSlot() {
		return slot;
	}

	public int getSpellId() {
		return spellId;
	}

}
