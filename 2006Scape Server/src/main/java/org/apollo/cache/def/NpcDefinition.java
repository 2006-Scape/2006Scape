package org.apollo.cache.def;

import com.google.common.base.Preconditions;

/**
 * Represents a type of Npc.
 *
 * @author Chris Fletcher
 */
public final class NpcDefinition {

	/**
	 * The npc definitions.
	 */
	private static NpcDefinition[] definitions;

	/**
	 * Gets the total number of npc definitions.
	 *
	 * @return The count.
	 */
	public static int count() {
		return definitions.length;
	}

	/**
	 * Gets the array of npc definitions.
	 *
	 * @return The definitions.
	 */
	public static NpcDefinition[] getDefinitions() {
		return definitions;
	}

	/**
	 * Initialises the class with the specified set of definitions.
	 *
	 * @param definitions The definitions.
	 * @throws IllegalStateException If there is an id mismatch.
	 */
	public static void init(NpcDefinition[] definitions) {
		NpcDefinition.definitions = definitions;
		for (int id = 0; id < definitions.length; id++) {
			NpcDefinition def = definitions[id];
			if (def.getId() != id) {
				throw new IllegalStateException("Npc definition id mismatch.");
			}
		}
	}

	/**
	 * Gets the npc definition for the specified id.
	 *
	 * @param id The id.
	 * @return The definition.
	 * @throws IndexOutOfBoundsException If the id is out of bounds.
	 */
	public static NpcDefinition lookup(int id) {
		Preconditions.checkElementIndex(id, definitions.length, "Id out of bounds.");
		return definitions[id];
	}

	/**
	 * The combat level of the npc.
	 */
	private int combatLevel = -1;

	/**
	 * The description of the npc.
	 */
	private String description;

	/**
	 * The npc id.
	 */
	private final int id;

	/**
	 * An array of interaction options.
	 */
	private final String[] interactions = new String[5];

	/**
	 * The name of the npc.
	 */
	private String name;

	/**
	 * The npc's size, in tiles.
	 */
	private int size = 1;

	/**
	 * The various animation ids.
	 */
	private int standAnim = -1, walkAnim = -1, walkBackAnim = -1, walkLeftAnim = -1, walkRightAnim = -1;

	/**
	 * Creates a new npc definition.
	 *
	 * @param id The npc id.
	 */
	public NpcDefinition(int id) {
		this.id = id;
	}

	/**
	 * Gets the npc's combat level.
	 *
	 * @return The combat level, or -1 if it doesn't have one.
	 */
	public int getCombatLevel() {
		return combatLevel;
	}

	/**
	 * Gets the description of the npc.
	 *
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the npc id.
	 *
	 * @return The npc id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets an interaction option.
	 *
	 * @param slot The slot of the option.
	 * @return The option, or {@code null} if there isn't any at the specified slot.
	 * @throws IndexOutOfBoundsException If the slot is out of bounds.
	 */
	public String getInteraction(int slot) {
		Preconditions.checkElementIndex(slot, interactions.length, "Npc interaction id is out of bounds.");
		return interactions[slot];
	}

	/**
	 * Gets the array of interaction options.
	 *
	 * @return The interaction options.
	 */
	public String[] getInteractions() {
		return interactions;
	}

	/**
	 * Gets the name of the npc.
	 *
	 * @return The name of the npc.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the npc's size, in tiles.
	 *
	 * @return The size.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Gets the id of the npc's standing animation.
	 *
	 * @return The stand animation id, or -1 if it doesn't have one.
	 */
	public int getStandAnimation() {
		return standAnim;
	}

	/**
	 * Gets the walking animation of the npc.
	 *
	 * @return The walking animation.
	 */
	public int getWalkAnimation() {
		return walkAnim;
	}

	/**
	 * Gets the walk-back animation of the npc.
	 *
	 * @return The walk-back animation.
	 */
	public int getWalkBackAnimation() {
		return walkBackAnim;
	}

	/**
	 * Gets the walk-left animation of the npc.
	 *
	 * @return The walk-left animation.
	 */
	public int getWalkLeftAnimation() {
		return walkLeftAnim;
	}

	/**
	 * Gets the walk-right animation of the npc.
	 *
	 * @return The walk-right animation.
	 */
	public int getWalkRightAnimation() {
		return walkRightAnim;
	}

	/**
	 * Checks if the npc has a combat level.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasCombatLevel() {
		return combatLevel != -1;
	}

	/**
	 * Checks if there is an interaction option present.
	 *
	 * @param slot The slot to check.
	 * @return {@code true} if so, {@code false} if not.
	 * @throws IndexOutOfBoundsException If the slot is out of bounds.
	 */
	public boolean hasInteraction(int slot) {
		Preconditions.checkElementIndex(slot, interactions.length, "Npc interaction id is out of bounds.");
		return interactions[slot] != null;
	}

	/**
	 * Checks if the npc has a standing animation id.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasStandAnimation() {
		return standAnim != -1;
	}

	/**
	 * Checks if the npc has a walking animation.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasWalkAnimation() {
		return walkAnim != -1;
	}

	/**
	 * Checks if the npc has a walk-back animation.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasWalkBackAnimation() {
		return walkBackAnim != -1;
	}

	/**
	 * Checks if the npc has a walk-left animation.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasWalkLeftAnimation() {
		return walkLeftAnim != -1;
	}

	/**
	 * Checks if the npc has a walk-right animation.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasWalkRightAnimation() {
		return walkRightAnim != -1;
	}

	/**
	 * Sets the npc's combat level.
	 *
	 * @param combatLevel The combat level.
	 */
	public void setCombatLevel(int combatLevel) {
		this.combatLevel = combatLevel;
	}

	/**
	 * Sets the description of the npc.
	 *
	 * @param description The description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets an interaction option.
	 *
	 * @param slot The slot of the option.
	 * @param interaction The interaction options.
	 * @throws IndexOutOfBoundsException If the slot is out of bounds.
	 */
	public void setInteraction(int slot, String interaction) {
		Preconditions.checkElementIndex(slot, interactions.length, "Npc interaction id is out of bounds.");
		interactions[slot] = interaction;
	}

	/**
	 * Sets the name of the npc.
	 *
	 * @param name The name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the size of the npc, in tiles.
	 *
	 * @param size The size.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Sets the id of the npc's standing animation.
	 *
	 * @param standAnim The stand animation id.
	 */
	public void setStandAnimation(int standAnim) {
		this.standAnim = standAnim;
	}

	/**
	 * Sets the walking animation of the npc.
	 *
	 * @param walkAnim The walking animation.
	 */
	public void setWalkAnimation(int walkAnim) {
		this.walkAnim = walkAnim;
	}

	/**
	 * Sets the various walking animations of the npc.
	 *
	 * @param walkAnim The walking animation.
	 * @param walkBackAnim The walk-back animation.
	 * @param walkLeftAnim The walk-left animation.
	 * @param walkRightAnim The walk-right animation.
	 */
	public void setWalkAnimations(int walkAnim, int walkBackAnim, int walkLeftAnim, int walkRightAnim) {
		this.walkAnim = walkAnim;
		this.walkBackAnim = walkBackAnim;
		this.walkLeftAnim = walkLeftAnim;
		this.walkRightAnim = walkRightAnim;
	}

}