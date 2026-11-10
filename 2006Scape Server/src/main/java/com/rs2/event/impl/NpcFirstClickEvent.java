package com.rs2.event.impl;

import com.rs2.event.Event;

public final class NpcFirstClickEvent implements Event {
	private final int npc;
	private final int npcIndex;
	
	public NpcFirstClickEvent(int npc) {
		this(npc, 0);
	}

	public NpcFirstClickEvent(int npc, int npcIndex) {
		this.npc = npc;
		this.npcIndex = npcIndex;
	}

	public int getNpc() {
		return npc;
	}

	public int getNpcIndex() {
		return npcIndex;
	}

}
