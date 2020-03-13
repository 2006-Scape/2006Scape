package com.rebotted.game.items;

import com.rebotted.GameConstants;
import com.rebotted.GameEngine;
import com.rebotted.game.players.Player;

/**
 * 
 * @author ArrowzFtw
 * @note itemId+1 is the playerItems
 * @note playerItems-1 = normalItemId
 */
public class Inventory {

	Player player;

	public Inventory(Player player) {
		this.player = player;
	}

	public void removeItem(Item i) {
		player.getItemAssistant().deleteItem(i.getId(), i.getCount());
	}

	public void addItemToSlot(Item i, int slot) {
		if (get(slot) != i.getId() + 1) {
			player.playerItems[slot] = i.getId() + 1;
			player.playerItemsN[slot] = i.getCount();
		} else {
			player.playerItemsN[slot] += i.getCount();
		}
		update();
	}

	public int get(int slot) {
		return player.playerItems[slot];
	}

	public void update() {
		player.getItemAssistant().resetItems(3214);
	}

	public boolean contains(int id) {
		return player.getItemAssistant().playerHasItem(id);
	}

	public boolean contains(Item item) {
		return player.getItemAssistant().playerHasItem(item.getId(), item.getCount());
	}

	public boolean contains(int id, int amount) {
		return player.getItemAssistant().playerHasItem(id, amount);
	}

	public Inventory getItemContainer() {
		return this;
	}

	public void addItem(Item item) {
		player.getItemAssistant().addItem(item.getId(), item.getCount());
	}

	public int getItemAmount(int id) {
		return player.getItemAssistant().getItemAmount(id);
	}

	public void replace(int item, int newItem) {
		player.getItemAssistant().deleteItem(item, 1);
		player.getItemAssistant().addItem(newItem, 1);
	}

	public int getCount(int i) {
		return player.getItemAssistant().getItemAmount(i);
	}

	public void set(int slot, Item item) {
		player.playerItems[slot] = item.getId() + 1;
		player.playerItemsN[slot] = item.getCount();
		update();
	}

	public int freeSlots() {
		return player.getItemAssistant().freeSlots();
	}

	public void add(int id) {
		player.getItemAssistant().addItem(id, 1);
	}

	public boolean add(int id, int amount) {
		if (player.getItemAssistant().isStackable(id)) {
			int allAmount = getStackedGroundAmount(id, amount, getCount(id));
			if (allAmount > 0) {
				GameEngine.itemHandler.createGroundItem(player, id, player.getX(), player
						.getY(), allAmount, player
						.getId());
				player.getItemAssistant().deleteItem(id, Integer.MAX_VALUE);
				player.getItemAssistant().addItem(id, Integer.MAX_VALUE);
				return true;
			}
		}
		return player.getItemAssistant().addItem(id, amount);
	}

	public int getStackedGroundAmount(int itemId, int addItemAmount,
			int itemAmount) {
		long x = 0;
		x += itemAmount;
		x += addItemAmount;
		if (x > 2147483647) {
			x -= 2147483647;
			return (int) x;
		}
		return -1;
	}

	public boolean canAddItem(Item item) {
		return item.getCount() <= freeSlots();
	}

	public void addItem(Item item, boolean drop) {
		if (!drop) {
			addItem(item); 
		} else {
			GameEngine.itemHandler.createGroundItem(player, item.getId(), player.getX(), player.getY(), item.getCount(), player.playerId);
		}
	}
	
	public boolean ownsItem(String itemName) {
		for (int i = 0; i < GameConstants.ITEM_LIMIT; i++) {
			if (GameEngine.itemHandler.itemList[i] != null) {
				if (GameEngine.itemHandler.itemList[i].itemName
						.equalsIgnoreCase(itemName)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean playerHasItem(int item) {
		return player.getItemAssistant().playerHasItem(item);
	}

	public void removeItemSlot(Item item, int slot) {
		player.getItemAssistant().deleteItem(item.getId(), slot, item.getCount());
	}

}
