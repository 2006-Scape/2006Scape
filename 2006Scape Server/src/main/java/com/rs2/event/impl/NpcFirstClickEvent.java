package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.game.npcs.Npc;

public final class NpcFirstClickEvent implements Event {
	
	private final Npc npc;
	
	public NpcFirstClickEvent(Npc npc) {
		this.npc = npc;
	}

	public Npc getNpc() {
		return npc;
	}	

}
