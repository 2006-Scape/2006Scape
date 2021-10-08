package com.rs2.event.impl;


import com.rs2.event.Event;
import com.rs2.world.GameObject;

public final class ObjectFifthClickEvent implements Event {

	private final GameObject gameObject;
	
	public ObjectFifthClickEvent(GameObject gameObject) {		
		this.gameObject = gameObject;
	}

	public GameObject getGameObject() {
		return gameObject;
	}
	
}
