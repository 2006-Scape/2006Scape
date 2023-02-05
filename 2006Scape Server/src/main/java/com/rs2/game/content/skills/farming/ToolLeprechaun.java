/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs2.game.content.skills.farming;

/**
 *
 * @author ArrowzFtw
 */

import java.util.HashMap;
import java.util.Map;

import org.apollo.cache.def.ItemDefinition;

import com.rs2.game.dialogues.ChatEmotes;
import com.rs2.game.items.Item;
import com.rs2.game.items.ItemData;
import com.rs2.game.players.Player;

/**
 * Created by IntelliJ IDEA. User: vayken Date: 23/02/12 Time: 12:12 To change
 * this template use File | Settings | File Templates.
 */

public class ToolLeprechaun {

	private Player player;

	public ToolLeprechaun(Player player) {
		this.player = player;
	}

	public int[] tools = { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 };// new
																					// int[18];

	/* setting up the store item array and the player item array */

	public Item[] storeItems = { new Item(5341), new Item(5343), new Item(952),
			new Item(5329), new Item(5331), new Item(5325) };
	public Item[] storeItems2 = { new Item(1925), new Item(6032),
			new Item(6034) };
	public Item[] storeItemsClient = { new Item(5341), new Item(5343),
			new Item(952), new Item(5329), new Item(5331), new Item(5325) };
	public Item[] storeItems2Client = { new Item(1925), new Item(6032),
			new Item(6034) };

	/* setting up the main constant field */

	public static final int[] NOTABLE_ITEMS = { 225, 239, 247, 249, 251, 253,
			255, 257, 259, 261, 263, 265, 267, 269, 3000, 2481, 592, 1965,
			1967, 6004, 5980, 5976, 1955, 1963, 2108, 5972, 2114, 754, 2126,
			248, 1951, 240, 2367, 1942, 1957, 1965, 1982, 5986, 5504, 5982,
			6006, 5994, 5996, 5931, 5998, 6000, 6002, 6016, 6055 };

	public static final int LEPRECHAUN_INTERFACE = 15614;
	public static final int LEPRECHAUN_INTERFACE_CONTAINER = 15682;//
	public static final int LEPRECHAUN_INTERFACE_CONTAINER2 = 15683;//
	public static final int PLAYER_INTERFACE = 15593;
	public static final int PLAYER_INTERFACE_CONTAINER = 15594;//
	public static final int PLAYER_INTERFACE_CONTAINER2 = 15595;//
	public static final int TOOL_CONFIGS = 615;

	/* this enum store every tool data for the interfaces loading */

	public enum ToolStoreData {

		RAKE(0, 5341, 1, 1, 15596, 15597, "Rake"), SEED_DIBBER(1, 5343, 2, 1,
				15598, 15599, "Dibber"), SPADE(2, 952, 4, 1, 15600, 15601,
				"Spade"), SECATEURS(3, 5329, 8, 1, 15602, 15603, "Secateurs"), MAGIC_SECATEURS(
				4, 7409, 8, 1, 15602, 15603, "Secateurs"), WATERING_CAN_0(5,
				5331, 16, 1, 15604, 15605, "Watering Can"), WATERING_CAN_1(6,
				5333, 32, 1, 15604, 15605, "Watering Can"), WATERING_CAN_2(7,
				5334, 48, 1, 15604, 15605, "Watering Can"), WATERING_CAN_3(8,
				5335, 64, 1, 15604, 15605, "Watering Can"), WATERING_CAN_4(9,
				5336, 80, 1, 15604, 15605, "Watering Can"), WATERING_CAN_5(10,
				5337, 96, 1, 15604, 15605, "Watering Can"), WATERING_CAN_6(11,
				5338, 112, 1, 15604, 15605, "Watering Can"), WATERING_CAN_7(12,
				5339, 128, 1, 15604, 15605, "Watering Can"), WATERING_CAN_8(13,
				5340, 144, 1, 15604, 15605, "Watering Can"), GARDENING_TROWEL(
				14, 5325, 256, 1, 15606, 15607, "Trowel"), EMPTY_BUCKETS(15,
				1925, 512, 31, 15608, 15609, "Buckets"), COMPOST(16, 6032,
				16384, 255, 15610, 15611, "Compost"), SUPER_COMPOST(17, 6034,
				4194304, 255, 15612, 15613, "Super Compost");

		private int toolIndex;
		private int toolId;
		private int toolConfig;
		private int toolMaxQuantity;
		private int toolFrameId;
		private int toolCountFrameId;
		private String toolName;

		private static Map<Integer, ToolStoreData> tools = new HashMap<Integer, ToolStoreData>();
		private static Map<Integer, ToolStoreData> indexes = new HashMap<Integer, ToolStoreData>();

		public static ToolStoreData forId(int toolId) {
			return tools.get(toolId);
		}

		public static ToolStoreData forIndex(int index) {
			return indexes.get(index);
		}

		static {
			for (ToolStoreData data : ToolStoreData.values()) {
				tools.put(data.toolId, data);
				indexes.put(data.toolIndex, data);
			}
		}

		ToolStoreData(int toolIndex, int toolId, int toolConfig,
				int toolMaxQuantity, int toolFrameId, int toolCountFrameId,
				String toolName) {
			this.toolIndex = toolIndex;
			this.toolId = toolId;
			this.toolConfig = toolConfig;
			this.toolMaxQuantity = toolMaxQuantity;
			this.toolFrameId = toolFrameId;
			this.toolCountFrameId = toolCountFrameId;
			this.toolName = toolName;
		}

		public int getToolIndex() {
			return toolIndex;
		}

		public int getToolId() {
			return toolId;
		}

		public int getToolConfig() {
			return toolConfig;
		}

		public int getToolMaxQuantity() {
			return toolMaxQuantity;
		}

		public int getToolFrameId() {
			return toolFrameId;
		}

		public int getToolCountFrameId() {
			return toolCountFrameId;
		}

		public String getToolName() {
			return toolName;
		}
	}

	/* loading the interfaces */

	public void loadInterfaces() {
		player.getPacketSender().showInterface(LEPRECHAUN_INTERFACE);
		player.getPacketSender().sendUpdateItems(LEPRECHAUN_INTERFACE_CONTAINER, storeItems);
		player.getPacketSender().sendUpdateItems(LEPRECHAUN_INTERFACE_CONTAINER2,
				storeItems2);
		player.getPacketSender().setSidebarInterface(3, PLAYER_INTERFACE);
		player.getPacketSender().sendUpdateItems(PLAYER_INTERFACE_CONTAINER,
				storeItemsClient);
		player.getPacketSender().sendUpdateItems(PLAYER_INTERFACE_CONTAINER2,
				storeItems2Client);
		updateStore();
	}

	/* handling watering can things */

	public void handleAdditionalTools() {
		int item;
		int i = 5340;
		while (!player.getItemAssistant().playerHasItem(i)
				&& i >= 5330) {
			i--;
		}
		item = i;
		if (item == 5330) {
			return;
		}
		storeItemsClient[4] = new Item(item);

		if (player.getItemAssistant().playerHasItem(7409)) {
			storeItemsClient[3] = new Item(7409);
		} else {
			storeItemsClient[3] = new Item(5329);
		}

	}

	public void checkWateringCanQuantity() {
		int counter = 0;
		int counter2 = 0;
		for (int i = 5; i <= 13; i++) {
			ToolStoreData toolStoreData = ToolStoreData.forIndex(i);
			if (player.getItemAssistant().playerHasItem(toolStoreData.getToolId())) {
				counter2++;
			}
			if (tools[i] == 1) {
				counter++;
			}
		}
		if (counter == 0) {
			storeItems[4] = new Item(5331);
		}
		if (counter2 == 0) {
			storeItemsClient[4] = new Item(5331);
		}

	}

	public boolean hasWateringCanInStore() {
		int counter = 0;
		for (int i = 5; i <= 13; i++) {
			if (tools[i] == 1) {
				counter++;
			}
		}
		if (counter == 0) {
			return false;
		}
		return true;

	}

	/* updating the store state and player state */

	public void updateStore() {
		int configValue = 0;
		for (int i = 0; i < tools.length; i++) {
			ToolStoreData toolStoreData = ToolStoreData.forIndex(i);
			if (toolStoreData == null) {
				return;
			}
			configValue += toolStoreData.getToolConfig() * tools[i];
			updateClientInterface(toolStoreData, player.getItemAssistant()
					.getItemAmount(toolStoreData.getToolId()), i);
			if (toolStoreData.getToolId() != 5332
					&& toolStoreData.getToolId() >= 5331
					&& toolStoreData.getToolId() <= 5340 && tools[i] == 1) {
				storeItems[4] = new Item(toolStoreData.getToolId());
			}
			if (toolStoreData.getToolId() == 7409 && tools[i] == 1) {
				storeItems[3] = new Item(7409);
			}

		}
		handleAdditionalTools();
		checkWateringCanQuantity();
		player.getPacketSender().sendConfig(TOOL_CONFIGS, configValue);
		player.getPacketSender().sendUpdateItems(LEPRECHAUN_INTERFACE_CONTAINER,
				storeItems);
		player.getPacketSender().sendUpdateItems(PLAYER_INTERFACE_CONTAINER,
				storeItemsClient);

	}

	public void updateClientInterface(ToolStoreData toolStoreData, int count,
			int index) {
		player.tempBoolean = false;
		if (count > 0) {
			if (index >= 5 && index <= 13) {
				player.tempBoolean = true;
			}
			player.getPacketSender().sendString("@gre@" + toolStoreData.getToolName(),
					toolStoreData.getToolFrameId());
			player.getPacketSender().sendString("@gre@" + count,
					toolStoreData.getToolCountFrameId());
		} else {
			// watering can doses
			if (index >= 5 && index <= 13 && player.tempBoolean) {
				return;
			}
			// secateurs
			if ((index == 3 || index == 4)
					&& (player.getItemAssistant().playerHasItem(7409) || player
							.getItemAssistant().playerHasItem(5329))) {
				return;
			}

			player.getPacketSender().sendString("" + toolStoreData.getToolName(),
					toolStoreData.getToolFrameId());
			player.getPacketSender().sendString("" + count,
					toolStoreData.getToolCountFrameId());

		}
	}

	/* store any item with id and amount provided */

	public void storeItems(int itemId, int amount) {
		ToolStoreData toolStoreData = ToolStoreData.forId(itemId);
		if (toolStoreData == null) {
			return;
		}

		int storeAmount = tools[toolStoreData.getToolIndex()];
		int finalAmount = amount;
		if (!player.getItemAssistant().playerHasItem(itemId))
			return;
		if (toolStoreData.getToolMaxQuantity() == storeAmount
				|| (itemId == 7409 || itemId == 5329)
				&& (tools[3] == 1 || tools[4] == 1) || hasWateringCanInStore()
				&& toolStoreData.getToolId() != 5332 && itemId >= 5340
				&& itemId <= 5331) {
			player.getPacketSender().sendMessage("You can't store any more of those.");
			return;
		}
		if (player.getItemAssistant().getItemAmount(itemId) <= 0) {
			player.getPacketSender().sendMessage("You aren't carrying any of those.");
			return;
		}
		if (player.getItemAssistant().getItemAmount(itemId) < amount) {
			finalAmount = player.getItemAssistant().getItemAmount(itemId);
		}

		player.getItemAssistant().deleteItem(itemId, finalAmount);
		tools[toolStoreData.getToolIndex()] += finalAmount;
		updateStore();

	}

	/* withdraw any item with item id and amount provided */

	public void withdrawItems(int itemId, int amount) {
		ToolStoreData toolStoreData = ToolStoreData.forId(itemId);
		if (toolStoreData == null) {
			return;
		}

		if (player.getItemAssistant().freeSlots()  <= 0) {
			player.getPacketSender().sendMessage("Not enough space in your inventory.");
			return;
		}
		if (tools[toolStoreData.getToolIndex()] <= 0) {
			player.getPacketSender().sendMessage(
					"You haven't got any of those stored in here.");
			return;
		}
		int finalAmount;
		if (amount > tools[toolStoreData.getToolIndex()]) {
			finalAmount = tools[toolStoreData.getToolIndex()];
		} else {
			finalAmount = amount;
		}
		if (finalAmount > player.getItemAssistant().freeSlots() ) {
			finalAmount = player.getItemAssistant().freeSlots() ;
		}

		tools[toolStoreData.getToolIndex()] -= finalAmount;
		player.getItemAssistant().addItem(itemId, finalAmount);
		updateStore();
	}

	/* note any item with the item id provided */

	public boolean noteItem(int itemId) {

		if (ItemDefinition.lookup(itemId).isNote()) {
			player.getDialogueHandler().sendNpcChat(3021, ChatEmotes.DEFAULT, "That is a banknote!");
			return true;
		}
		for (int item : NOTABLE_ITEMS) {
			if (itemId == item) {
				int count = player.getItemAssistant().getItemAmount(itemId);
				player.getItemAssistant().deleteItem(itemId, count);
				player.getItemAssistant().addItem(itemId + 1, count);
				player.getDialogueHandler().sendStatement(
						"The tool leprechaun notes those items for you.");

				return true;
			}
		}
		player.getDialogueHandler().sendNpcChat(3021, ChatEmotes.DEFAULT, "Nay, I've got no banknotes to exchange for that item.");
		return true;
	}

}
