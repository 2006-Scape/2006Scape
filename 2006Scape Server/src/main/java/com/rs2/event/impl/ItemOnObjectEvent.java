package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.game.items.Item;
import com.rs2.world.GameObject;

public final class ItemOnObjectEvent implements Event {
	
	private final int item;
	
	private final int gameObject;
	
	public ItemOnObjectEvent(int item, int gameObject) {
		this.item = item;
		this.gameObject = gameObject;
	}

	public int getItem() {
		return item;
	}

	public int getGameObject() {
		return gameObject;
	}

}
