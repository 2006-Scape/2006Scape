package com.rs2.event.impl;


import com.rs2.event.Event;
import com.rs2.world.GameObject;

public final class ObjectFirstClickEvent implements Event {

	private final GameObject gameObject;
	
	public ObjectFirstClickEvent(GameObject gameObject) {		
		this.gameObject = gameObject;
	}

	public GameObject getGameObject() {
		return gameObject;
	}
	
}
