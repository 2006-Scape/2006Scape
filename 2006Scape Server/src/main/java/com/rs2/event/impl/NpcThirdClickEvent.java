package com.rs2.event.impl;


import com.rs2.event.Event;
import com.rs2.game.npcs.Npc;

public final class NpcThirdClickEvent implements Event {
	
	private final Npc npc;
	
	public NpcThirdClickEvent(Npc npc) {
		this.npc = npc;
	}

	public Npc getNpc() {
		return npc;
	}	

}
