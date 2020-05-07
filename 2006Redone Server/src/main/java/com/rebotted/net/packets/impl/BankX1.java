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
	public void processPacket(Player player, int packetType, int packetSize) {
		player.endCurrentTask();
		if (packetType == 135) {
			player.xRemoveSlot = player.getInStream().readSignedWordBigEndian();
			player.xInterfaceId = player.getInStream().readUnsignedWordA();
			player.xRemoveId = player.getInStream().readSignedWordBigEndian();
		} else {
			if (player.xInterfaceId == 7423) {
				player.getItemAssistant().bankItem(player.xRemoveId, player.xRemoveSlot, Xamount);// Depo 1
				player.getItemAssistant().resetItems(7423);
			}
		}
		if (packetType == PART1) {
			synchronized (player) {
				player.getOutStream().createFrame(27);
			}
		}

	}
}
