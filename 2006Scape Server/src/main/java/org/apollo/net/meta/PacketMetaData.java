package org.apollo.net.meta;

import com.google.common.base.Preconditions;

/**
 * A class containing meta data for a single type of packet.
 *
 * @author Graham
 */
public final class PacketMetaData {

	/**
	 * Creates packet meta data for a fixed-length packet.
	 *
	 * @param length The length of the packet.
	 * @return The packet meta data.
	 * @throws IllegalArgumentException If {@code length} is less than 0.
	 */
	public static PacketMetaData createFixed(int length) {
		Preconditions.checkArgument(length >= 0, "Packet length cannot be less than 0.");
		return new PacketMetaData(PacketType.FIXED, length);
	}

	/**
	 * Creates a packet meta data object for a variable byte length packet.
	 *
	 * @return The packet meta data object.
	 */
	public static PacketMetaData createVariableByte() {
		return new PacketMetaData(PacketType.VARIABLE_BYTE, 0);
	}

	/**
	 * Creates a packet meta data object for a variable short length packet.
	 *
	 * @return The packet meta data object.
	 */
	public static PacketMetaData createVariableShort() {
		return new PacketMetaData(PacketType.VARIABLE_SHORT, 0);
	}

	/**
	 * The length of this packet.
	 */
	private final int length;

	/**
	 * The type of packet.
	 */
	private final PacketType type;

	/**
	 * Creates the packet meta data object. This should not be called directly. Use the {@link #createFixed},
	 * {@link #createVariableByte}, and {@link #createVariableShort} methods instead!
	 *
	 * @param type The type of packet.
	 * @param length The length of the packet.
	 */
	private PacketMetaData(PacketType type, int length) {
		this.type = type;
		this.length = length;
	}

	/**
	 * Gets the length of this packet.
	 *
	 * @return The length of this packet.
	 * @throws IllegalStateException If the packet is not a fixed-size packet.
	 */
	public int getLength() {
		Preconditions.checkState(type == PacketType.FIXED, "Can only get the length of a fixed length packet.");
		return length;
	}

	/**
	 * Gets the type of packet.
	 *
	 * @return The type of packet.
	 */
	public PacketType getType() {
		return type;
	}

}