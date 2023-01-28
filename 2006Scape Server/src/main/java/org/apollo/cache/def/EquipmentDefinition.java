package org.apollo.cache.def;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

/**
 * Represents a type of Item that may be equipped.
 *
 * @author Graham
 */
public final class EquipmentDefinition {

	/**
	 * The attack id.
	 */
	private static final int ATTACK = 0;

	/**
	 * The defence id.
	 */
	private static final int DEFENCE = 1;

	/**
	 * The strength id.
	 */
	private static final int STRENGTH = 2;

	/**
	 * The hitpoints id.
	 */
	private static final int HITPOINTS = 3;

	/**
	 * The ranged id.
	 */
	private static final int RANGED = 4;

	/**
	 * The prayer id.
	 */
	private static final int PRAYER = 5;

	/**
	 * The magic id.
	 */
	private static final int MAGIC = 6;

	/**
	 * The equipment definitions.
	 */
	private static final Map<Integer, EquipmentDefinition> definitions = new HashMap<>();

	/**
	 * Gets the total number of equipment definitions.
	 *
	 * @return The count.
	 */
	public static int count() {
		return definitions.size();
	}

	/**
	 * Initialises the equipment definitions.
	 *
	 * @param definitions The definitions.
	 * @throws RuntimeException If there is an id mismatch.
	 */
	public static void init(EquipmentDefinition[] definitions) {
		for (int id = 0; id < definitions.length; id++) {
			EquipmentDefinition def = definitions[id];
			if (def != null) {
				if (def.getId() != id) {
					throw new RuntimeException("Equipment definition id mismatch.");
				}
				EquipmentDefinition.definitions.put(def.getId(), def);
			}
		}
	}

	/**
	 * Gets an equipment definition by its id.
	 *
	 * @param id The id.
	 * @return {@code null} if the item is not equipment, the definition otherwise.
	 * @throws IndexOutOfBoundsException If the id is out of bounds.
	 */
	public static EquipmentDefinition lookup(int id) {
		Preconditions.checkElementIndex(id, ItemDefinition.count(), "Id out of bounds.");
		return definitions.get(id);
	}

	/**
	 * The item id.
	 */
	private final int id;

	/**
	 * The array of skill requirement levels.
	 */
	private final int[] levels = { 1, 1, 1, 1, 1, 1, 1 };

	/**
	 * The slot this equipment goes into.
	 */
	private int slot;

	/**
	 * Various flags.
	 */
	private boolean twoHanded, fullBody, fullHat, fullMask;

	/**
	 * Creates a new equipment definition.
	 *
	 * @param id The id.
	 */
	public EquipmentDefinition(int id) {
		this.id = id;
	}

	/**
	 * Gets the minimum attack level required to equip this item.
	 *
	 * @return The level.
	 */
	public int getAttackLevel() {
		return levels[ATTACK];
	}

	/**
	 * Gets the minimum defence level required to equip this item.
	 *
	 * @return The level.
	 */
	public int getDefenceLevel() {
		return levels[DEFENCE];
	}

	/**
	 * Gets the minimum hitpoints level required to equip this item.
	 *
	 * @return The level.
	 */
	public int getHitpointsLevel() {
		return levels[HITPOINTS];
	}

	/**
	 * Gets the minimum magic level required to equip this item.
	 *
	 * @return The level.
	 */
	public int getMagicLevel() {
		return levels[MAGIC];
	}

	/**
	 * Gets the minimum prayer level required to equip this item.
	 *
	 * @return The level.
	 */
	public int getPrayerLevel() {
		return levels[PRAYER];
	}

	/**
	 * Gets the minimum ranged level required to equip this item.
	 *
	 * @return The level.
	 */
	public int getRangedLevel() {
		return levels[RANGED];
	}

	/**
	 * Gets the minimum strength level required to equip this item.
	 *
	 * @return The level.
	 */
	public int getStrengthLevel() {
		return levels[STRENGTH];
	}

	/**
	 * Gets the id.
	 *
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the minimum level required to equip this item for a specific skill.
	 *
	 * @param skill The skill id.
	 * @return The level.
	 */
	public int getLevel(int skill) {
		Preconditions.checkArgument(skill >= ATTACK && skill <= MAGIC, "Skill id out of bounds.");
		return levels[skill];
	}

	/**
	 * Gets the target slot.
	 *
	 * @return The target slot.
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * Checks if this equipment is a full body.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isFullBody() {
		return fullBody;
	}

	/**
	 * Checks if this equipment is a full hat.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isFullHat() {
		return fullHat;
	}

	/**
	 * Checks if this equipment is a full mask.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isFullMask() {
		return fullMask;
	}

	/**
	 * Checks if this equipment is two-handed.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isTwoHanded() {
		return twoHanded;
	}

	/**
	 * Sets the flags.
	 *
	 * @param twoHanded The two handed flag.
	 * @param fullBody The full body flag.
	 * @param fullHat The full hat flag.
	 * @param fullMask The full mask flag.
	 */
	public void setFlags(boolean twoHanded, boolean fullBody, boolean fullHat, boolean fullMask) {
		this.twoHanded = twoHanded;
		this.fullBody = fullBody;
		this.fullHat = fullHat;
		this.fullMask = fullMask;
	}

	/**
	 * Sets the required levels.
	 *
	 * @param attack The required attack level.
	 * @param strength The required strength level.
	 * @param defence The required defence level.
	 * @param ranged The required ranged level.
	 * @param prayer The required prayer level.
	 * @param magic The required magic level.
	 */
	public void setLevels(int attack, int strength, int defence, int ranged, int prayer, int magic) {
		setLevels(attack, strength, defence, 1, ranged, prayer, magic);
	}

	/**
	 * Sets the required levels.
	 *
	 * @param attack The required attack level.
	 * @param strength The required strength level.
	 * @param defence The required defence level.
	 * @param hitpoints The required hitpoints level.
	 * @param ranged The required ranged level.
	 * @param prayer The required prayer level.
	 * @param magic The required magic level.
	 */
	public void setLevels(int attack, int strength, int defence, int hitpoints, int ranged, int prayer, int magic) {
		levels[ATTACK] = attack;
		levels[STRENGTH] = strength;
		levels[DEFENCE] = defence;
		levels[HITPOINTS] = hitpoints;
		levels[RANGED] = ranged;
		levels[PRAYER] = prayer;
		levels[MAGIC] = magic;
	}

	/**
	 * Sets the target slot.
	 *
	 * @param slot The target slot.
	 */
	public void setSlot(int slot) {
		this.slot = slot;
	}

}