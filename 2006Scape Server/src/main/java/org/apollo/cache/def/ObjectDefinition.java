package org.apollo.cache.def;

import com.google.common.base.Preconditions;

/**
 * Represents a type of GameObject.
 *
 * @author Major
 */
public final class ObjectDefinition {

	/**
	 * The array of game object definitions.
	 */
	private static ObjectDefinition[] definitions;

	/**
	 * Gets the total number of object definitions.
	 *
	 * @return The count.
	 */
	public static int count() {
		return definitions.length;
	}

	/**
	 * Gets the array of object definitions.
	 *
	 * @return The definitions.
	 */
	public static ObjectDefinition[] getDefinitions() {
		return definitions;
	}

	/**
	 * Initialises the object definitions.
	 *
	 * @param definitions The decoded definitions.
	 * @throws RuntimeException If there is an id mismatch.
	 */
	public static void init(ObjectDefinition[] definitions) {
		ObjectDefinition.definitions = definitions;
		for (int id = 0; id < definitions.length; id++) {
			ObjectDefinition def = definitions[id];
			if (def.getId() != id) {
				throw new RuntimeException("Item definition id mismatch.");
			}
		}
	}

	/**
	 * Gets the object definition for the specified id.
	 *
	 * @param id The id of the object.
	 * @return The definition.
	 * @throws IndexOutOfBoundsException If the id is out of bounds.
	 */
	public static ObjectDefinition lookup(int id) {
		Preconditions.checkElementIndex(id, definitions.length, "Id out of bounds.");
		return definitions[id];
	}

	/**
	 * The object's description.
	 */
	private String description;

	/**
	 * The object's id.
	 */
	private final int id;

	/**
	 * Denotes whether this object is impenetrable or not.
	 */
	private boolean impenetrable = true;

	/**
	 * Denotes whether this object has actions associated with it or not.
	 */
	private boolean interactive;

	/**
	 * Denotes whether or not this object obstructs the ground.
	 */
	private boolean obstructive;

	/**
	 * This object's length.
	 */
	private int length = 1;

	/**
	 * The object's menu actions.
	 */
	private String[] menuActions;

	/**
	 * The object's name.
	 */
	private String name;

	/**
	 * Denotes whether the object can be walked over or not.
	 */
	private boolean solid = true;

	/**
	 * This object's width.
	 */
	private int width = 1;

	/**
	 * Creates a new object definition.
	 *
	 * @param id The id of the object.
	 */
	public ObjectDefinition(int id) {
		this.id = id;
	}

	/**
	 * Gets the description of this object.
	 *
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the id of this object.
	 *
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the length of this object.
	 *
	 * @return The length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Gets the menu actions of this object.
	 *
	 * @return The menu actions.
	 */
	public String[] getMenuActions() {
		return menuActions;
	}

	/**
	 * Gets the name of this object.
	 *
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the with of this object.
	 *
	 * @return The width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Indicates the impenetrability of this object.
	 *
	 * @return {@code true} if this object is impenetrable, otherwise {@code false}.
	 */
	public boolean isImpenetrable() {
		return impenetrable;
	}

	/**
	 * Indicates the interactivity of this object.
	 *
	 * @return {@code true} if the object is interactive, otherwise {@code false}.
	 */
	public boolean isInteractive() {
		return interactive;
	}

	/**
	 * Indicates whether or not this object obstructs the ground.
	 *
	 * @return {@code true} if the object obstructs the ground otherwise {@code false}.
	 */
	public boolean isObstructive() {
		return obstructive;
	}

	/**
	 * Indicates the solidity of this object.
	 *
	 * @return {@code true} if this object is solid, otherwise {@code false}.
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * Sets the description of this object.
	 *
	 * @param description The description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the impenetrability of this object.
	 *
	 * @param impenetrable The impenetrability.
	 */
	public void setImpenetrable(boolean impenetrable) {
		this.impenetrable = impenetrable;
	}

	/**
	 * Sets the interactivity of this object.
	 *
	 * @param interactive The interactivity.
	 */
	public void setInteractive(boolean interactive) {
		this.interactive = interactive;
	}

	/**
	 * Sets the length of this object.
	 *
	 * @param length The length.
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Sets the menu actions of this object.
	 *
	 * @param menuActions The menu actions.
	 */
	public void setMenuActions(String[] menuActions) {
		this.menuActions = menuActions;
	}

	/**
	 * Sets the name of this object.
	 *
	 * @param name The name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the solidity of this object.
	 *
	 * @param solid The solidity.
	 */
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	/**
	 * Sets the width of this object.
	 *
	 * @param width The width.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets whether or not this object is obstructive to the ground.
	 *
	 * @param obstructive Whether or not this object obstructs the ground.
	 */
	public void setObstructive(boolean obstructive) {
		this.obstructive = obstructive;
	}

}