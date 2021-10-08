package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.game.items.Item;
import com.rs2.game.players.Player;

public final class ItemOnPlayerEvent implements Event {

	private final Item used;
	
	private final Player usedWith;
	
	public ItemOnPlayerEvent(Item used, Player usedWith) {
		this.used = used;
		this.usedWith = usedWith;
	}

	public Item getUsed() {
		return used;
	}

	public Player getUsedWith() {
		return usedWith;
	}
	
}
