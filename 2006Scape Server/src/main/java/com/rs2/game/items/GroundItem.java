package com.rs2.game.items;

public class GroundItem {

	public int itemId, itemX, itemY, itemH, itemAmount, itemController, hideTicks, removeTicks;
	
	public String ownerName;

	public GroundItem(int id, int x, int y, int h, int amount, int controller,
			int hideTicks, String name) {
		itemId = id;
		itemX = x;
		itemY = y;
		itemH = h;
		itemAmount = amount;
		itemController = controller;
		this.hideTicks = hideTicks;
		ownerName = name;
	}

	public int getItemId() {
		return itemId;
	}

	public int getItemX() {
		return itemX;
	}

	public int getItemY() {
		return itemY;
	}

	public int getItemH() {
		return itemH;
	}

	public int getItemAmount() {
		return itemAmount;
	}

	public int getItemController() {
		return itemController;
	}

	public String getName() {
		return ownerName;
	}

}
