package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.game.items.Item;
import com.rs2.game.npcs.Npc;

public final class ItemOnNpcEvent implements Event {
	
	private final int item;
	
	private final int npc;

	private final int clicked;
	
	public ItemOnNpcEvent(int item, int npc, int clicked) {
		this.item = item;
		this.npc = npc;
		this.clicked = clicked;
	}

	public int getItem() {
		return item;
	}

	public int getNpc() {
		return npc;
	}

	public int getNpcClicked() {
		return clicked;
	}

}
