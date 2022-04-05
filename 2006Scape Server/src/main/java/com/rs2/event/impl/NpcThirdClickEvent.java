package com.rs2.event.impl;


import com.rs2.event.Event;
import com.rs2.game.npcs.Npc;

public final class NpcThirdClickEvent implements Event {
	
	private final int npc;
	
	public NpcThirdClickEvent(int npc) {
		this.npc = npc;
	}

	public int getNpc() {
		return npc;
	}	

}
