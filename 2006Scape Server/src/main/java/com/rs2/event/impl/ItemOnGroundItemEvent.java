package com.rs2.event.impl;


import com.rs2.event.Event;
import com.rs2.game.items.Item;
import com.rs2.game.players.Position;

public final class ItemOnGroundItemEvent implements Event {
	
	private final Item used;
	
	private final Item groundItem;
	
	private final Position position;
	
	public ItemOnGroundItemEvent(Item used, Item groundItem, Position position) {
		this.used = used;
		this.groundItem = groundItem;
		this.position = position;
	}

	public Item getUsed() {
		return used;
	}

	public Item getGroundItem() {
		return groundItem;
	}

	public Position getPosition() {
		return position;
	}
	
}
