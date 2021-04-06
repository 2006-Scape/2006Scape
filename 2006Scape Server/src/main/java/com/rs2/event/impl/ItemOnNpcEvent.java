package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.game.items.Item;
import com.rs2.game.npcs.Npc;

public final class ItemOnNpcEvent implements Event {
	
	private final Item item;
	
	private final Npc npc;
	
	public ItemOnNpcEvent(Item item, Npc npc) {
		this.item = item;
		this.npc = npc;
	}

	public Item getItem() {
		return item;
	}

	public Npc getNpc() {
		return npc;
	}

}
