package com.rs2.event.impl;


import com.rs2.event.Event;
import com.rs2.world.GameObject;

public final class ObjectSecondClickEvent implements Event {

	private final GameObject gameObject;
	
	public ObjectSecondClickEvent(GameObject gameObject) {		
		this.gameObject = gameObject;
	}

	public GameObject getGameObject() {
		return gameObject;
	}
	
}