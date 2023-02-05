package com.rs2.game.items;

//only the name id and bonus is used.
public class ItemList {

	public int itemId;
	public String itemName;
	public String itemDescription;
	public double ShopValue;
	public double LowAlch;
	public double HighAlch;
	public int[] Bonuses = new int[100];

	public ItemList(int _itemId) {
		itemId = _itemId;
	}
}
