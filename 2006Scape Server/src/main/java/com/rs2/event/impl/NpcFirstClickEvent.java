package com.rs2.event.impl;

import com.rs2.event.Event;

public final class NpcFirstClickEvent implements Event {
	private final int npc;
	
	public NpcFirstClickEvent(int npc) {
		this.npc = npc;
	}

	public int getNpc() {
		return npc;
	}	

}
