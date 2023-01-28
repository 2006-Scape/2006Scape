package com.rs2.net.packets.impl;

import com.rs2.event.impl.ItemOnNpcEvent;
import com.rs2.game.content.skills.SkillHandler;
import com.rs2.game.items.UseItem;
import com.rs2.game.npcs.NpcHandler;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.PacketType;

public class ItemOnNpc implements PacketType {

	@Override
	public void processPacket(final Player player, Packet packet) {
		final int itemId = packet.readSignedWordA();
		final int i = packet.readSignedWordA();
		final int slot = packet.readSignedWordBigEndian();
		final int npcId = NpcHandler.npcs[i].npcType;
		SkillHandler.resetItemOnNpc(player);
		player.endCurrentTask();
		if (player.playerRights == 3) {
			player.getPacketSender().sendMessage("Item id: " + itemId + " slot: " + slot + " i: " + i);
		}
		if (player.getItemAssistant().freeSlots() < 1) {
			player.getPacketSender().sendMessage("Your inventory is full.");
			return;
		}
		if (player == null || player.disconnected || !player.getItemAssistant().playerHasItem(itemId, 1, slot) || NpcHandler.npcs[i] == null || NpcHandler.npcs[i].isDead || player.isDead || player.isTeleporting) {
			return;
		}
		player.faceNpc(i);
		player.post(new ItemOnNpcEvent(itemId,npcId, i));
		switch(npcId) {
		case 3021:
			if (player.getFarmingTools().noteItem(itemId)) {
				return;
			}
			break;
			case 43:
				NpcHandler.npcs[i].shearSheep(player, 1735, 1737, 893, 43, 42, 50);
				break;
		}
		if (player.getItemAssistant().playerHasItem(995, 1) && npcId == 736) {
			player.getItemAssistant().deleteItem(995, 1);
			player.getDialogueHandler().sendNpcChat1("Thanks!", player.npcType, "Emily");
		}
		if (player.getItemAssistant().playerHasItem(1927, 1) && player.gertCat == 2 && npcId == 2997) {
			player.getDialogueHandler().sendDialogues(319, npcId);
			player.getItemAssistant().deleteItem(1927, 1);
			player.getItemAssistant().addItem(1925, 1);
			player.gertCat = 3;
		} else if (player.getItemAssistant().playerHasItem(1552, 1) && player.gertCat == 3 && npcId == 2997) {
			player.getDialogueHandler().sendDialogues(323, npcId);
			player.getItemAssistant().deleteItem(1552, 1);
			player.gertCat = 4;
		} else if (player.getItemAssistant().playerHasItem(1554, 1) && player.gertCat == 5 && npcId == 2997) {
			player.getItemAssistant().deleteItem(1554, 1);
			player.getDialogueHandler().sendDialogues(326, npcId);
		}
		UseItem.itemOnNpc(player, itemId, slot, i);
	}
}
