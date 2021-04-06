package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.world.GameObject;

public final class DoorEvent implements Event {
	
	private GameObject door;
	
	public DoorEvent(GameObject door) {
		this.door = door;
	}

	public GameObject getDoor() {
		return door;
	}

}
