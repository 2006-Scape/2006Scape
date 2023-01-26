package org.apollo.net.codec.game;

/**
 * Represents the order of bytes in a {@link DataType} when {@link DataType#getBytes()} {@code  > 1}.
 *
 * @author Graham
 */
public enum DataOrder {

	/**
	 * Most significant byte to least significant byte.
	 */
	BIG,

	/**
	 * Also known as the V2 order.
	 */
	INVERSED_MIDDLE,

	/**
	 * Least significant byte to most significant byte.
	 */
	LITTLE,

	/**
	 * Also known as the V1 order.
	 */
	MIDDLE;

}