package com.rs2.net;

/**
 * A mutable sequence of bytes used to construct the immutable
 * <code>Packet</code> objects. By default, methods use big endian byte
 * ordering.
 */
public class StaticPacketBuilder implements PacketBuilder {

	/**
	 * Default capacity
	 */
	private static final int DEFAULT_SIZE = 32;
	/**
	 * The payload buffer
	 */
	private byte[] payload;
	/**
	 * Current number of bytes used in the buffer
	 */
	private int curLength;
	/**
	 * ID of the packet
	 */
	private int id;
	/**
	 * Current index into the buffer by bits
	 */
	private Packet.Size size = Packet.Size.Fixed;
	/**
	 * Whether this packet does not use the standard packet header
	 */
	private boolean bare = false;

	/**
	 * Constructs a packet builder with no data and an initial capacity of
	 * <code>DEFAULT_SIZE</code>.
	 * 
	 * @see DEFAULT_SIZE
	 */
	public StaticPacketBuilder() {
		this(DEFAULT_SIZE);
	}

	public byte[] getPayload() {
		return payload;
	}

	/**
	 * Constructs a packet builder with no data and an initial capacity of
	 * <code>capacity</code>.
	 * 
	 * @param capacity
	 *            The initial capacity of the buffer
	 */
	public StaticPacketBuilder(int capacity) {
		payload = new byte[capacity];
	}

	/**
	 * Ensures that the buffer is at least <code>minimumBytes</code> bytes.
	 * 
	 * @param minimumCapacity
	 *            The size needed
	 */
	private void ensureCapacity(int minimumCapacity) {
		if (minimumCapacity >= payload.length) {
			expandCapacity(minimumCapacity);
		}
	}

	/**
	 * Expands the buffer to the specified size.
	 * 
	 * @param minimumCapacity
	 *            The minimum capacity to which to expand
	 * @see java.lang.AbstractStringBuilder#expandCapacity(int)
	 */
	private void expandCapacity(int minimumCapacity) {
		int newCapacity = (payload.length + 1) * 2;
		if (newCapacity < 0) {
			newCapacity = Integer.MAX_VALUE;
		} else if (minimumCapacity > newCapacity) {
			newCapacity = minimumCapacity;
		}
		byte[] newPayload = new byte[newCapacity];
		try {
			while (curLength > payload.length) {
				curLength--;
			}
			System.arraycopy(payload, 0, newPayload, 0, curLength);
		} catch (Exception e) {

		}
		payload = newPayload;
	}

	/**
	 * Sets this packet as bare. A bare packet will contain only the payload
	 * data, rather than having the standard packet header prepended.
	 * 
	 * @param bare
	 *            Whether this packet is to be sent bare
	 */
	public StaticPacketBuilder setBare(boolean bare) {
		this.bare = bare;
		return this;
	}

	/**
	 * Sets the ID for this packet.
	 * 
	 * @param id
	 *            The ID of the packet
	 */
	public StaticPacketBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public StaticPacketBuilder setSize(Packet.Size s) {
		size = s;
		return this;
	}

	/**
	 * Adds the contents of <code>byte</code> array <code>data</code> to the
	 * packet. The size of this packet will grow by the length of the provided
	 * array.
	 * 
	 * @param data
	 *            The bytes to add to this packet
	 * @return A reference to this object
	 */
	public StaticPacketBuilder addBytes(byte[] data) {
		return addBytes(data, 0, data.length);
	}

	/**
	 * Adds the contents of <code>byte</code> array <code>data</code>, starting
	 * at index <code>offset</code>. The size of this packet will grow by
	 * <code>len</code> bytes.
	 * 
	 * @param data
	 *            The bytes to add to this packet
	 * @param offset
	 *            The index of the first byte to append
	 * @param len
	 *            The number of bytes to append
	 * @return A reference to this object
	 */
	public StaticPacketBuilder addBytes(byte[] data, int offset, int len) {
		int newLength = curLength + len;
		ensureCapacity(newLength);
		System.arraycopy(data, offset, payload, curLength, len);
		curLength = newLength;
		return this;
	}

	/**
	 * Adds a <code>byte</code> to the data buffer. The size of this packet will
	 * grow by one byte.
	 * 
	 * @param val
	 *            The <code>byte</code> value to add
	 * @return A reference to this object
	 */
	public StaticPacketBuilder addByte(byte val) {
		return addByte(val, true);
	}

	/**
	 * Adds a <code>byte</code> to the data buffer. The size of this packet will
	 * grow by one byte.
	 * 
	 * @param val
	 *            The <code>byte</code> value to add
	 * @param checkCapacity
	 *            Whether the buffer capacity should be checked
	 * @return A reference to this object
	 */
	private StaticPacketBuilder addByte(byte val, boolean checkCapacity) {
		if (checkCapacity) {
			ensureCapacity(curLength + 1);
		}
		payload[curLength++] = val;
		return this;
	}

	/**
	 * Adds a <code>int</code> to the data stream. The size of this packet will
	 * grow by four bytes.
	 * 
	 * @param val
	 *            The <code>int</code> value to add
	 * @return A reference to this object
	 */
	public StaticPacketBuilder addInt(int val) {
		ensureCapacity(curLength + 4);
		addByte((byte) (val >> 24), false);
		addByte((byte) (val >> 16), false);
		addByte((byte) (val >> 8), false);
		addByte((byte) val, false);
		return this;
	}

	/**
	 * Adds a <code>long</code> to the data stream. The size of this packet will
	 * grow by eight bytes.
	 * 
	 * @param val
	 *            The <code>long</code> value to add
	 * @return A reference to this object
	 */
	public StaticPacketBuilder addLong(long val) {
		addInt((int) (val >> 32));
		addInt((int) (val & -1L));
		return this;
	}

	/**
	 * Returns a <code>Packet</code> object for the data contained in this
	 * builder.
	 * 
	 * @return A <code>Packet</code> object
	 */
	public Packet toPacket() {
		byte[] data = new byte[curLength];
		System.arraycopy(payload, 0, data, 0, curLength);
		return new Packet(id, data, bare, size);
	}

}
