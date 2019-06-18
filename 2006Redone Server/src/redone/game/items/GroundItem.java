package redone.game.items;

public class GroundItem {

	public int itemId, itemX, itemY, itemAmount, itemController, hideTicks,
			removeTicks;
	public String ownerName;

	public GroundItem(int id, int x, int y, int h, int amount, int controller,
			int hideTicks, String name) {
		itemId = id;
		itemX = x;
		itemY = y;
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
