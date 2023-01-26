package org.apollo.net.meta;

import com.google.common.base.Preconditions;

/**
 * A class containing a group of {@link PacketMetaData} objects.
 *
 * @author Graham
 */
public final class PacketMetaDataGroup {

	/**
	 * Creates a packet meta data group from the packet length array.
	 *
	 * @param lengths The packet lengths.
	 * @return The packet meta data group.
	 * @throws IllegalArgumentException If the array length is not 256, or if there is an element in the array with a
	 *             value below -3.
	 */
	public static PacketMetaDataGroup createFromArray(int[] lengths) {
		Preconditions.checkArgument(lengths.length == 256, "Array length must be 256.");
		PacketMetaDataGroup group = new PacketMetaDataGroup();

		for (int index = 0; index < lengths.length; index++) {
			int length = lengths[index];
			Preconditions.checkArgument(length >= -2, "No packet length can have a value less than -3.");
			PacketMetaData metaData = null;
			if (length == -2) {
				metaData = PacketMetaData.createVariableShort();
			} else if (length == -1) {
				metaData = PacketMetaData.createVariableByte();
			} else {
				metaData = PacketMetaData.createFixed(length);
			}
			group.packets[index] = metaData;
		}
		return group;
	}

	/**
	 * The array of packet meta data objects.
	 */
	private final PacketMetaData[] packets = new PacketMetaData[256];

	/**
	 * This constructor should not be called directly. Use the {@link #createFromArray} method instead.
	 */
	private PacketMetaDataGroup() {

	}

	/**
	 * Gets the meta data for the specified packet.
	 *
	 * @param opcode The opcode of the packet.
	 * @return The {@link PacketMetaData}, or {@code null} if the packet does not exist.
	 * @throws IllegalArgumentException If the opcode is less than 0, or greater than 255.
	 */
	public PacketMetaData getMetaData(int opcode) {
		Preconditions.checkElementIndex(opcode, packets.length, "Opcode out of bounds.");
		return packets[opcode];
	}

}