package com.rs2.event.impl;

import com.rs2.event.Event;
import com.rs2.game.npcs.Npc;

public final class NpcSecondClickEvent implements Event {
	
	private final int npc;
	
	public NpcSecondClickEvent(int npc) {
		this.npc = npc;
	}

	public int getNpc() {
		return npc;
	}	

}
