package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.game.items.Item;
import com.rs2.world.GameObject;

public final class ItemOnObjectEvent implements Event {
	
	private final Item item;
	
	private final GameObject gameObject;
	
	public ItemOnObjectEvent(Item item, GameObject gameObject) {
		this.item = item;
		this.gameObject = gameObject;
	}

	public Item getItem() {
		return item;
	}

	public GameObject getGameObject() {
		return gameObject;
	}

}
