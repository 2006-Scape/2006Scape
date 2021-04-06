package com.rs2.event.impl;


import com.rs2.event.Event;
import com.rs2.game.items.Item;

public final class ItemFirstClickEvent implements Event {
	
	private final Item item;
	
	private final int widgetId;
	
	public ItemFirstClickEvent(Item item, int widgetId) {
		this.item = item;
		this.widgetId = widgetId;
	}

	public Item getItem() {
		return item;
	}
	
	public int getWidgetId() {
		return widgetId;
	}	

}
