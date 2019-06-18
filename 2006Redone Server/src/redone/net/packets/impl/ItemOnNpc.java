package redone.net.packets.impl;

import redone.game.content.skills.SkillHandler;
import redone.game.items.UseItem;
import redone.game.npcs.NpcHandler;
import redone.game.players.Client;
import redone.net.packets.PacketType;

public class ItemOnNpc implements PacketType {

	@Override
	public void processPacket(final Client player, int packetType, int packetSize) {
		final int itemId = player.getInStream().readSignedWordA();
		final int i = player.getInStream().readSignedWordA();
		final int slot = player.getInStream().readSignedWordBigEndian();
		final int npcId = NpcHandler.npcs[i].npcType;
		SkillHandler.resetItemOnNpc(player);
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
			NpcHandler.npcs[i].requestTransformTime(player, 1735, 893, 43, 42, 50);
		break;
		}
		if (player.getItemAssistant().playerHasItem(995, 1) && npcId == 736) {
			player.getItemAssistant().deleteItem2(995, 1);
			player.getDialogueHandler().sendNpcChat1("Thanks!", player.npcType, "Emily");
		}
		if (player.getItemAssistant().playerHasItem(1927, 1) && player.gertCat == 2 && npcId == 2997) {
			player.getDialogueHandler().sendDialogues(319, npcId);
			player.getItemAssistant().deleteItem2(1927, 1);
			player.getItemAssistant().addItem(1925, 1);
			player.gertCat = 3;
		} else if (player.getItemAssistant().playerHasItem(1552, 1) && player.gertCat == 3 && npcId == 2997) {
			player.getDialogueHandler().sendDialogues(323, npcId);
			player.getItemAssistant().deleteItem2(1552, 1);
			player.gertCat = 4;
		} else if (player.getItemAssistant().playerHasItem(1554, 1) && player.gertCat == 5 && npcId == 2997) {
			player.getItemAssistant().deleteItem2(1554, 1);
			player.getDialogueHandler().sendDialogues(326, npcId);
		}
		UseItem.ItemonNpc(player, itemId, slot, i);
	}
}
