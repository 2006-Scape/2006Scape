package com.rebotted.net.packets.impl;

import com.rebotted.game.players.Player;
import com.rebotted.net.packets.PacketType;

/**
 * Bank X Items
 **/
public class BankX1 implements PacketType {

	public static final int PART1 = 135;
	public static final int PART2 = 208;
	public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		c.endCurrentTask();
		if (packetType == 135) {
			c.xRemoveSlot = c.getInStream().readSignedWordBigEndian();
			c.xInterfaceId = c.getInStream().readUnsignedWordA();
			c.xRemoveId = c.getInStream().readSignedWordBigEndian();
		}
		else {
			if (c.xInterfaceId == 7423) {
				c.getItemAssistant().bankItem(c.xRemoveId, c.xRemoveSlot, Xamount);// Depo 1
				c.getItemAssistant().resetItems(7423);
			}
		}
		if (packetType == PART1) {
			synchronized (c) {
				c.getOutStream().createFrame(27);
			}
		}

	}
}
