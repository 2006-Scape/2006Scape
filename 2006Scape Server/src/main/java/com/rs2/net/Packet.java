package com.rs2.net;

import io.netty.buffer.ByteBuf;

/**
 * Represents a single packet.
 * 
 * @author Graham Edgecombe
 * 
 */
public class Packet {

	/**
	 * The type of packet.
	 * 
	 * @author Graham Edgecombe
	 * 
	 */
	public enum Type {

		/**
		 * A fixed size packet where the size never changes.
		 */
		FIXED,

		/**
		 * A variable packet where the size is described by a byte.
		 */
		VARIABLE,

		/**
		 * A variable packet where the size is described by a word.
		 */
		VARIABLE_SHORT;

	}

	/**
	 * The opcode.
	 */
	private final int opcode;

	/**
	 * The type.
	 */
	private final Type type;

	/**
	 * The payload.
	 */
	private final ByteBuf payload;

	/**
	 * Creates a packet.
	 * 
	 * @param opcode  The opcode.
	 * @param type    The type.
	 * @param payload The payload.
	 */
	public Packet(final int opcode, final Type type, final ByteBuf payload) {
		this.opcode = opcode;
		this.type = type;
		this.payload = payload;
	}

	/**
	 * Checks if this packet is raw. A raw packet does not have the usual headers
	 * such as opcode or size.
	 * 
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean isRaw() {
		return opcode == -1;
	}

	/**
	 * Gets the opcode.
	 * 
	 * @return The opcode.
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Gets the type.
	 * 
	 * @return The type.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Gets the payload.
	 * 
	 * @return The payload.
	 */
	public ByteBuf getPayload() {
		return payload;
	}

	/**
	 * Gets the length.
	 * 
	 * @return The length.
	 */
	public int getLength() {
		return payload.capacity();
	}

	/**
	 * Reads a single byte.
	 * 
	 * @return A single byte.
	 */
	public byte get() {
		return payload.readByte();
	}

	/**
	 * Reads several bytes.
	 * 
	 * @param b The target array.
	 */
	public void get(final byte[] b) {
		payload.readBytes(b);
	}

	/**
	 * Reads a byte.
	 * 
	 * @return A single byte.
	 */
	public byte getByte() {
		return get();
	}

	/**
	 * Reads an unsigned byte.
	 * 
	 * @return An unsigned byte.
	 */
	public int getUnsignedByte() {
		return payload.readByte() & 0xff;
	}

	/**
	 * Reads a short.
	 * 
	 * @return A short.
	 */
	public short getShort() {
		return payload.readShort();
	}

	/**
	 * Reads an unsigned short.
	 * 
	 * @return An unsigned short.
	 */
	public int getUnsignedShort() {
		int value = 0;
		value |= (get() & 0xff) << 8;
		value |= (get() & 0xff);
		return value;
	}

	public int getUnsignedShortA() {
		int value = 0;
		value |= (get() & 0xff) << 8;
		value |= ((get() - 128) & 0xff);
		return value;
	}

	/**
	 * Reads an integer.
	 * 
	 * @return An integer.
	 */
	public int getInt() {
		return payload.readInt();
	}

	/**
	 * Reads a long.
	 * 
	 * @return A long.
	 */
	public long getLong() {
		return payload.readLong();
	}

	/**
	 * Reads a type C byte.
	 * 
	 * @return A type C byte.
	 */
	public byte getByteC() {
		return (byte) (-get());
	}

	/**
	 * Gets a type S byte.
	 * 
	 * @return A type S byte.
	 */
	public byte getByteS() {
		return (byte) (128 - get());
	}

	/**
	 * Reads a little-endian type A short.
	 * 
	 * @return A little-endian type A short.
	 */
	public short getLEShortA() {
		int i = (get() - 128 & 0xFF) | ((get() & 0xFF) << 8);
		if (i > 32767)
			i -= 0x10000;
		return (short) i;
	}

	/**
	 * Reads a little-endian short.
	 * 
	 * @return A little-endian short.
	 */
	public short getLEShort() {
		int i = (get() & 0xFF) | ((get() & 0xFF) << 8);
		if (i > 32767)
			i -= 0x10000;
		return (short) i;
	}

	/**
	 * Reads a V1 integer.
	 * 
	 * @return A V1 integer.
	 */
	public int getInt1() {
		final byte b1 = get();
		final byte b2 = get();
		final byte b3 = get();
		final byte b4 = get();
		return ((b3 << 24) & 0xFF) | ((b4 << 16) & 0xFF) | ((b1 << 8) & 0xFF) | (b2 & 0xFF);
	}

	/**
	 * Reads a V2 integer.
	 * 
	 * @return A V2 integer.
	 */
	public int getInt2() {
		final int b1 = get() & 0xFF;
		final int b2 = get() & 0xFF;
		final int b3 = get() & 0xFF;
		final int b4 = get() & 0xFF;
		return ((b2 << 24) & 0xFF) | ((b1 << 16) & 0xFF) | ((b4 << 8) & 0xFF) | (b3 & 0xFF);
	}

	/**
	 * Gets a 3-byte integer.
	 * 
	 * @return The 3-byte integer.
	 */
	public int getTriByte() {
		return ((get() << 16) & 0xFF) | ((get() << 8) & 0xFF) | (get() & 0xFF);
	}

	/**
	 * Reads a type A byte.
	 * 
	 * @return A type A byte.
	 */
	public byte getByteA() {
		return (byte) (get() - 128);
	}

	/**
	 * Reads a RuneScape string.
	 * 
	 * @return The string.
	 */
	public String getRS2String() {
		byte temp;
		StringBuilder b = new StringBuilder();
		while ((temp = payload.readByte()) != 10) {
			b.append((char) temp);
		}
		return b.toString();
	}

	/**
	 * Reads a type A short.
	 * 
	 * @return A type A short.
	 */
	public short getShortA() {
		int i = ((get() & 0xFF) << 8) | (get() - 128 & 0xFF);
		if (i > 32767)
			i -= 0x10000;
		return (short) i;
	}

	/**
	 * Reads a series of bytes in reverse.
	 * 
	 * @param is     The target byte array.
	 * @param offset The offset.
	 * @param length The length.
	 */
	public void getReverse(final byte[] is, final int offset, final int length) {
		for (int i = (offset + length - 1); i >= offset; i--)
			is[i] = get();
	}

	/**
	 * Reads a series of type A bytes in reverse.
	 * 
	 * @param is     The target byte array.
	 * @param offset The offset.
	 * @param length The length.
	 */
	public void getReverseA(final byte[] is, final int offset, final int length) {
		for (int i = (offset + length - 1); i >= offset; i--)
			is[i] = getByteA();
	}

	/**
	 * Reads a series of bytes.
	 * 
	 * @param is     The target byte array.
	 * @param offset The offset.
	 * @param length The length.
	 */
	public void get(final byte[] is, final int offset, final int length) {
		for (int i = 0; i < length; i++)
			is[offset + i] = get();
	}

	/**
	 * Gets a smart.
	 * 
	 * @return The smart.
	 */
	public int getSmart() {
		final int peek = payload.getByte(payload.readerIndex());
		if (peek < 128)
			return (get() & 0xFF);
		else
			return (getShort() & 0xFFFF) - 32768;
	}

	/**
	 * Gets a signed smart.
	 * 
	 * @return The signed smart.
	 */
	public int getSignedSmart() {
		final int peek = payload.getByte(payload.readerIndex());
		if (peek < 128)
			return ((get() & 0xFF) - 64);
		else
			return ((getShort() & 0xFFFF) - 49152);
	}

	/* Legacy methods here */

	public int readUnsignedByte() {
		return get() & 0xff;
	}

	public byte readSignedByte() {
		return get();
	}

	public byte readSignedByteC() {
		return (byte) -get();
	}

	public int readUnsignedByteS() {
		return 128 - get() & 0xff;
	}

	public int readHex() {
		return ((get() & 0xFF) * 1000) + (get() & 0xFF);
	}

	public void readBytes(byte abyte0[], int length, int offset) {
		for (int i = 0; i < length; i++)
			abyte0[offset + i] = get();
	}

	public void readBytes_reverseA(byte abyte0[], int length, int offset) {
		for (int i = (offset + length - 1); i >= offset; i--)
			abyte0[i] = getByteA();
	}

	public String readString() {
		byte temp;
		StringBuilder b = new StringBuilder();
		while ((temp = payload.readByte()) != 10) {
			b.append((char) temp);
		}
		return b.toString();
	}

	public int readDWord() {
		return ((readUnsignedByte()) << 24) + ((readUnsignedByte()) << 16) + ((readUnsignedByte()) << 8) + (readUnsignedByte());
	}

	public long readQWord() {
		long l = readDWord() & 0xffffffffL;
		long l1 = readDWord() & 0xffffffffL;
		return (l << 32) + l1;
	}

	public long readQWord2() {
		final long l = readDWord() & 0xffffffffL;
		final long l1 = readDWord() & 0xffffffffL;
		return (l << 32) + l1;
	}

	public int readSignedWordA() {
		int i = ((readUnsignedByte()) << 8) + (get() - 128 & 0xff);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readUnsignedWordA() {
		return ((readUnsignedByte()) << 8) + (get() - 128 & 0xff);
	}

	public int readUnsignedWord() {
		return ((readUnsignedByte()) << 8) + (readUnsignedByte());
	}

	public int readSignedWord() {
		int i = ((readUnsignedByte()) << 8) + (readUnsignedByte());
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readSignedWordBigEndian() {
		int i = (readUnsignedByte()) + ((readUnsignedByte()) << 8);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readSignedWordBigEndianA() {
		int i = (get() - 128 & 0xff) + ((readUnsignedByte()) << 8);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readUnsignedWordBigEndian() {
		return (readUnsignedByte()) + ((readUnsignedByte()) << 8);
	}

	public int readUnsignedWordBigEndianA() {
		return (get() - 128 & 0xff) + ((readUnsignedByte()) << 8);
	}
}
