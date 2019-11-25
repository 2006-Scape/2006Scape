package com.rebotted.net.packets.impl;

import com.rebotted.game.content.skills.SkillHandler;
import com.rebotted.game.items.UseItem;
import com.rebotted.game.npcs.NpcHandler;
import com.rebotted.game.players.Client;
import com.rebotted.net.packets.PacketType;

public class ItemOnNpc implements PacketType {

	@Override
	public void processPacket(final Client player, int packetType, int packetSize) {
		final int itemId = player.getInStream().readSignedWordA();
		final int i = player.getInStream().readSignedWordA();
		final int slot = player.getInStream().readSignedWordBigEndian();
		final int npcId = NpcHandler.npcs[i].npcType;
		SkillHandler.resetItemOnNpc(player);
		player.endCurrentTask();
		if (player.playerRights == 3) {
			player.getActionSender().sendMessage("Item id: " + itemId + " slot: " + slot + " i: " + i);
		}
		if (player.getItemAssistant().freeSlots() < 1) {
			player.getActionSender().sendMessage("Your inventory is full.");
			return;
		}
		if (player == null || player.disconnected == true || !player.getItemAssistant().playerHasItem(itemId, 1, slot) || NpcHandler.npcs[i] == null || NpcHandler.npcs[i].isDead || player.isDead || player.isTeleporting) {
			return;
		}
		player.faceNpc(i);
		switch(npcId) {
		case 43:
			if (NpcHandler.npcs[i].requestTransformTime(player, 1735, 893, 43, 42, 50, i))
			{
				player.getItemAssistant().addItem(1737, 1);
			}
			else
			{
				player.getActionSender().sendMessage("You need to wait for this sheep's wool to regrow!");
			}
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
