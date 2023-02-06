package org.apollo.cache.def;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Represents a type of Item.
 *
 * @author Graham
 */
public final class ItemDefinition {

	/**
	 * The item definitions.
	 */
	private static ItemDefinition[] definitions;

	/**
	 * A map of item ids to noted ids.
	 */
	private static final BiMap<Integer, Integer> notes = HashBiMap.create();

	/**
	 * A map of noted ids to item ids.
	 */
	private static final BiMap<Integer, Integer> notesInverse = notes.inverse();

	/**
	 * Gets the total number of item definitions.
	 *
	 * @return The count.
	 */
	public static int count() {
		return definitions.length;
	}

	/**
	 * Gets the array of item definitions.
	 *
	 * @return The definitions.
	 */
	public static ItemDefinition[] getDefinitions() {
		return definitions;
	}

	/**
	 * Initialises the class with the specified set of definitions.
	 *
	 * @param definitions The definitions.
	 * @throws RuntimeException If there is an id mismatch.
	 */
	public static void init(ItemDefinition[] definitions) {
		ItemDefinition.definitions = definitions;
		for (int id = 0; id < definitions.length; id++) {
			ItemDefinition def = definitions[id];
			if (def.getId() != id) {
				throw new RuntimeException("Item definition id mismatch.");
			}
			if (def.isNote()) {
				def.toNote();
				notes.put(def.getNoteInfoId(), def.getId());
			}
		}
	}

	/**
	 * Converts an item id to a noted id.
	 *
	 * @param id The item id.
	 * @return The noted id.
	 */
	public static int itemToNote(int id) {
		Integer entry = notes.get(id);
		if (entry == null) {
			return id;
		}
		return entry;
	}

	private static ItemDefinition NULL_DEF = new ItemDefinition(-1);
	static {
		NULL_DEF.setName("");
		NULL_DEF.setDescription("");
	}
	
	/**
	 * Gets the item definition for the specified id.
	 *
	 * @param id The id.
	 * @return The definition.
	 * @throws IndexOutOfBoundsException If the id is out of bounds.
	 */
	public static ItemDefinition lookup(int id) {
//		Preconditions.checkElementIndex(id, definitions.length, "Id out of bounds.");
		if(id < 0 || id > definitions.length) {
			return NULL_DEF;
		}
		return definitions[id];
	}

	/**
	 * Converts a noted id to the normal item id.
	 *
	 * @param id The note id.
	 * @return The item id.
	 */
	public static int noteToItem(int id) {
		Integer entry = notesInverse.get(id);
		if (entry == null) {
			return id;
		}
		return entry;
	}

	/**
	 * The description of the item.
	 */
	private String description;

	/**
	 * The ground actions array.
	 */
	private final String[] groundActions = new String[5];

	/**
	 * The item's id.
	 */
	private final int id;

	/**
	 * The inventory actions array.
	 */
	private final String[] inventoryActions = new String[5];

	/**
	 * A flag indicating if this item is members only.
	 */
	private boolean members = false;

	/**
	 * The name of the item.
	 */
	private String name;

	/**
	 * The id of the item to copy note graphics from.
	 */
	private int noteGraphicId = -1;

	/**
	 * The id of the item to copy note info from.
	 */
	private int noteInfoId = -1;

	/**
	 * A flag indicating if this item is stackable.
	 */
	private boolean stackable = false;

	/**
	 * This item's team.
	 */
	private int team;

	/**
	 * The item's floor value.
	 */
	private int value = 1;

	/**
	 * Creates an item definition with the default values.
	 *
	 * @param id The item's id.
	 */
	public ItemDefinition(int id) {
		this.id = id;
	}

	/**
	 * Gets the description of this item.
	 *
	 * @return The item's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets a ground action.
	 *
	 * @param id The id.
	 * @return The action.
	 * @throws IndexOutOfBoundsException If the id is out of bounds.
	 */
	public String getGroundAction(int id) {
		Preconditions.checkElementIndex(id, groundActions.length, "Ground action id is out of bounds.");
		return groundActions[id];
	}

	/**
	 * Gets this item's id.
	 *
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets an inventory action.
	 *
	 * @param id The id.
	 * @return The action.
	 * @throws IndexOutOfBoundsException If the id is out of bounds.
	 */
	public String getInventoryAction(int id) {
		Preconditions.checkElementIndex(id, inventoryActions.length, "Inventory action id is out of bounds.");
		return inventoryActions[id];
	}

	/**
	 * Gets this item's name.
	 *
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets this item's note graphic id.
	 *
	 * @return The note graphic id.
	 */
	public int getNoteGraphicId() {
		return noteGraphicId;
	}

	/**
	 * Gets this item's note info id.
	 *
	 * @return The note info id.
	 */
	public int getNoteInfoId() {
		return noteInfoId;
	}

	/**
	 * Gets this item's team.
	 *
	 * @return The team.
	 */
	public int getTeam() {
		return team;
	}

	/**
	 * Gets this item's value.
	 *
	 * @return The value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Checks if this item is members only.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isMembersOnly() {
		return members;
	}

	/**
	 * Checks if this item is a note.
	 *
	 * @return {@code true} if so, {@code false} otherwise.
	 */
	public boolean isNote() {
		return noteGraphicId != -1 && noteInfoId != -1;
	}

	/**
	 * Checks if the item specified by this definition is stackable.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isStackable() {
		return stackable;
	}

	/**
	 * Sets this item's description.
	 *
	 * @param description The description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets a ground action.
	 *
	 * @param id The id.
	 * @param action The action.
	 * @throws IndexOutOfBoundsException If the id is out of bounds.
	 */
	public void setGroundAction(int id, String action) {
		Preconditions.checkElementIndex(id, groundActions.length, "Ground action id is out of bounds.");
		groundActions[id] = action;
	}

	/**
	 * Sets an inventory action.
	 *
	 * @param id The id.
	 * @param action The action.
	 * @throws IndexOutOfBoundsException If the id is out of bounds.
	 */
	public void setInventoryAction(int id, String action) {
		Preconditions.checkElementIndex(id, inventoryActions.length, "Inventory action id is out of bounds.");
		inventoryActions[id] = action;
	}

	/**
	 * Sets this item's members only flag.
	 *
	 * @param members The flag.
	 */
	public void setMembersOnly(boolean members) {
		this.members = members;
	}

	/**
	 * Sets this item's name.
	 *
	 * @param name The name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets this item's note graphic id.
	 *
	 * @param noteGraphicId The note graphic id.
	 */
	public void setNoteGraphicId(int noteGraphicId) {
		this.noteGraphicId = noteGraphicId;
	}

	/**
	 * Sets this item's note info id.
	 *
	 * @param noteInfoId The note info id.
	 */
	public void setNoteInfoId(int noteInfoId) {
		this.noteInfoId = noteInfoId;
	}

	/**
	 * Sets this item's stackable flag.
	 *
	 * @param stackable The stackable flag.
	 */
	public void setStackable(boolean stackable) {
		this.stackable = stackable;
	}

	/**
	 * Sets this item's team.
	 *
	 * @param team The team.
	 */
	public void setTeam(int team) {
		this.team = team;
	}

	/**
	 * sets this item's value.
	 *
	 * @param value The value.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Converts this item to a note, if possible.
	 *
	 * @throws IllegalStateException If {@link ItemDefinition#isNote()} returns {@code false}.
	 */
	public void toNote() {
		if (isNote()) {
			if (description != null && description.startsWith("Swap this note at any bank for ")) {
				return; // already converted.
			}

			ItemDefinition infoDef = lookup(noteInfoId);
			name = infoDef.name;
			members = infoDef.members;

			String prefix = "a";
			char firstChar = name == null ? 'n' : name.charAt(0);

			if (firstChar == 'A' || firstChar == 'E' || firstChar == 'I' || firstChar == 'O' || firstChar == 'U') {
				prefix = "an";
			}

			description = "Swap this note at any bank for " + prefix + " " + name + ".";
			value = infoDef.value;
			stackable = true;
		} else {
			throw new IllegalStateException("Item cannot be noted.");
		}
	}

}
