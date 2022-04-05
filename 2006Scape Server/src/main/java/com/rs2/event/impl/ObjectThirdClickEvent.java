package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.world.GameObject;

public final class ObjectThirdClickEvent implements Event {

	private final int gameObject;
	
	public ObjectThirdClickEvent(int gameObject) {
		this.gameObject = gameObject;
	}

	public int getGameObject() {
		return gameObject;
	}
	
}
