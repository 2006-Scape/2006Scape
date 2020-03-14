package com.rebotted.game.items;

import com.rebotted.GameEngine;

/**
 * Represents a single item.
 * 
 * @author Graham Edgecombe
 * 
 */
public class Item {

	/**
	 * The id.
	 */
	private int id;

	/**
	 * The number of items.
	 */
	private int count;

	/**
	 * Creates a single item.
	 * 
	 * @param id
	 *            The id.
	 */
	public Item(int id) {
		this(id, 1);
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Creates a stacked item.
	 * 
	 * @param id
	 *            The id.
	 * @param count
	 *            The number of items.
	 * @throws IllegalArgumentException
	 *             if count is negative.
	 */
	public Item(int id, int count) {
		if (count < 0) {
			throw new IllegalArgumentException("Count cannot be negative.");
		}
		this.id = id;
		this.count = count;
	}

	/**
	 * Creates a stacked item.
	 * 
	 * @param id
	 *            The id.
	 * @param count
	 *            The number of items.
	 * @param timer
	 *            The timer assigned.
	 * @throws IllegalArgumentException
	 *             if count is negative.
	 */
	public Item(int id, int count, int timer) {
		if (count < 0) {
			throw new IllegalArgumentException("Count cannot be negative.");
		}
		this.id = id;
		this.count = count;
	}

	/**
	 * Gets the item id.
	 * 
	 * @return The item id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the count.
	 * 
	 * @return The count.
	 */
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return Item.class.getName() + " [id=" + id + ", count=" + count + "]";
	}

	public boolean equals(Item item) {
		return item.getId() == id && count == item.getCount();
	}

	public ItemList getDefinition() {
		return GameEngine.itemHandler.itemList[id];
	}
}