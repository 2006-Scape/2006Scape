package org.apollo.net.codec.game;

/**
 * Represents the different simple data types.
 *
 * @author Graham
 */
public enum DataType {

	/**
	 * A byte.
	 */
	BYTE(1),

	/**
	 * A short.
	 */
	SHORT(2),

	/**
	 * A 'tri byte' - a group of three bytes.
	 */
	TRI_BYTE(3),

	/**
	 * An integer.
	 */
	INT(4),

	/**
	 * A long.
	 */
	LONG(8);

	/**
	 * The number of bytes this type occupies.
	 */
	private final int bytes;

	/**
	 * Creates a data type.
	 *
	 * @param bytes The number of bytes it occupies.
	 */
	private DataType(int bytes) {
		this.bytes = bytes;
	}

	/**
	 * Gets the number of bytes the data type occupies.
	 *
	 * @return The number of bytes.
	 */
	public int getBytes() {
		return bytes;
	}

}