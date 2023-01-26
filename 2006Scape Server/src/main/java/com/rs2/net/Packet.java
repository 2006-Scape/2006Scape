package com.rs2.net;

import org.jboss.netty.buffer.ChannelBuffer;

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
	private final ChannelBuffer payload;

	/**
	 * Creates a packet.
	 * 
	 * @param opcode
	 *            The opcode.
	 * @param type
	 *            The type.
	 * @param payload
	 *            The payload.
	 */
	public Packet(final int opcode, final Type type, final ChannelBuffer payload) {
		this.opcode = opcode;
		this.type = type;
		this.payload = payload;
	}

	/**
	 * Checks if this packet is raw. A raw packet does not have the usual
	 * headers such as opcode or size.
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
	public ChannelBuffer getPayload() {
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
	 * @param b
	 *            The target array.
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
		StringBuilder bldr = new StringBuilder();
		byte b;
		while (payload.readable() && (b = payload.readByte()) != 10)
			bldr.append((char) b);
		return bldr.toString();
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
	 * @param is
	 *            The target byte array.
	 * @param offset
	 *            The offset.
	 * @param length
	 *            The length.
	 */
	public void getReverse(final byte[] is, final int offset, final int length) {
		for (int i = (offset + length - 1); i >= offset; i--)
			is[i] = get();
	}

	/**
	 * Reads a series of type A bytes in reverse.
	 * 
	 * @param is
	 *            The target byte array.
	 * @param offset
	 *            The offset.
	 * @param length
	 *            The length.
	 */
	public void getReverseA(final byte[] is, final int offset, final int length) {
		for (int i = (offset + length - 1); i >= offset; i--)
			is[i] = getByteA();
	}

	/**
	 * Reads a series of bytes.
	 * 
	 * @param is
	 *            The target byte array.
	 * @param offset
	 *            The offset.
	 * @param length
	 *            The length.
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

	
	
	public int readDWord() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int readHex() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void readBytes(byte[] pmchatText, int pmchatTextSize, int i) {
		// TODO Auto-generated method stub
		
	}

	public void readBytes_reverseA(byte[] chatText, byte chatTextSize, int i) {
		// TODO Auto-generated method stub
		
	}

	public String readString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int readSignedByte() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int readSignedWord() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int readSignedWordA() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int readSignedWordBigEndian() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int readSignedWordBigEndianA() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int readUnsignedByte() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int readUnsignedByteS() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int readSignedByteC() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int readSignedByteS() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int readUnsignedWord() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int readUnsignedWordA() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int readUnsignedWordBigEndian() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int readUnsignedWordBigEndianA() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int readQWord() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int readQWord2() {
		// TODO Auto-generated method stub
		return 0;
	}
//
//	/**
//	 * The ID of the packet
//	 */
//	private final int pID;
//	/**
//	 * The length of the payload
//	 */
//	private final int pLength;
//	/**
//	 * The payload
//	 */
//	public /*private final*/ byte[] pData;
//	/**
//	 * The current index into the payload buffer for reading
//	 */
//	private int caret = 0;
//	/**
//	 * Whether this packet is without the standard packet header
//	 */
//	private final boolean bare;
//
//	/**
//	 * Returns the remaining payload data of this packet.
//	 * 
//	 * @return The payload <code>byte</code> array
//	 */
//	public byte[] getRemainingData() {
//		byte[] data = new byte[pLength - caret];
//		for (int i = 0; i < data.length; i++) {
//			data[i] = pData[i + caret];
//		}
//		caret += data.length;
//		return data;
//
//	}
//
//	public int remaining() {
//		return pData.length - caret;
//	}
//
//	/**
//	 * Returns this packet in string form.
//	 * 
//	 * @return A <code>String</code> representing this packet
//	 */
//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("[id=" + pID + ",len=" + pLength + ",data=0x");
//		for (int x = 0; x < pLength; x++) {
//			sb.append(byteToHex(pData[x], true));
//		}
//		sb.append("]");
//		return sb.toString();
//	}
//
//	private static String byteToHex(byte b, boolean forceLeadingZero) {
//		StringBuilder out = new StringBuilder();
//		int ub = b & 0xff;
//		if (ub / 16 > 0 || forceLeadingZero) {
//			out.append(hex[ub / 16]);
//		}
//		out.append(hex[ub % 16]);
//		return out.toString();
//	}
//
//	private static final char[] hex = "0123456789ABCDEF".toCharArray();
//
//	
//	/* TODO Stream methods below */
//	
//	public long readQWord2() {
//		final long l = readDWord() & 0xffffffffL;
//		final long l1 = readDWord() & 0xffffffffL;
//		return (l << 32) + l1;
//	}
//
//	public byte readSignedByteC() {
//		return (byte) -pData[caret++];
//	}
//
//	public int readUnsignedByteS() {
//		return 128 - pData[caret++] & 0xff;
//	}
//
//	public int readSignedWordBigEndian() {
//		caret += 2;
//		int i = ((pData[caret - 1] & 0xff) << 8) + (pData[caret - 2] & 0xff);
//		if (i > 32767) {
//			i -= 0x10000;
//		}
//		return i;
//	}
//
//	public int readSignedWordA() {
//		caret += 2;
//		int i = ((pData[caret - 2] & 0xff) << 8) + (pData[caret - 1] - 128 & 0xff);
//		if (i > 32767) {
//			i -= 0x10000;
//		}
//		return i;
//	}
//
//	public int readSignedWordBigEndianA() {
//		caret += 2;
//		int i = ((pData[caret - 1] & 0xff) << 8) + (pData[caret - 2] - 128 & 0xff);
//		if (i > 32767) {
//			i -= 0x10000;
//		}
//		return i;
//	}
//
//	public int readUnsignedWordBigEndian() {
//		caret += 2;
//		return ((pData[caret - 1] & 0xff) << 8) + (pData[caret - 2] & 0xff);
//	}
//
//	public int readUnsignedWordA() {
//		caret += 2;
//		return ((pData[caret - 2] & 0xff) << 8) + (pData[caret - 1] - 128 & 0xff);
//	}
//
//	public int readUnsignedWordBigEndianA() {
//		caret += 2;
//		return ((pData[caret - 1] & 0xff) << 8) + (pData[caret - 2] - 128 & 0xff);
//	}
//
//	public void readBytes_reverseA(byte abyte0[], int i, int j) {
//		ensureCapacity(i);
//		for (int k = j + i - 1; k >= j; k--) {
//			abyte0[k] = (byte) (pData[caret++] - 128);
//		}
//
//	}
//
//	public int readUnsignedByte() {
//		return pData[caret++] & 0xff;
//	}
//
//	public byte readSignedByte() {
//		return pData[caret++];
//	}
//
//	public int readUnsignedWord() {
//		caret += 2;
//		return ((pData[caret - 2] & 0xff) << 8) + (pData[caret - 1] & 0xff);
//	}
//
//	public int readSignedWord() {
//		caret += 2;
//		int i = ((pData[caret - 2] & 0xff) << 8) + (pData[caret - 1] & 0xff);
//		if (i > 32767) {
//			i -= 0x10000;
//		}
//		return i;
//	}
//
//	public int readHex() {
//		caret += 2;
//		return ((pData[caret - 2] & 0xFF) * 1000) + (pData[caret - 1] & 0xFF);
//	}
//	
//	public int readDWord() {
//		caret += 4;
//		return ((pData[caret - 4] & 0xff) << 24)
//				+ ((pData[caret - 3] & 0xff) << 16)
//				+ ((pData[caret - 2] & 0xff) << 8)
//				+ (pData[caret - 1] & 0xff);
//	}
//
//	public long readQWord() {
//		long l = readDWord() & 0xffffffffL;
//		long l1 = readDWord() & 0xffffffffL;
//		return (l << 32) + l1;
//	}
//
//	public java.lang.String readString() {
//		int i = caret;
//		while (pData[caret++] != 10) {
//			;
//		}
//		return new String(pData, i, caret - i - 1);
//	}
//
//	public void readBytes(byte abyte0[], int i, int j) {
//		for (int k = j; k < j + i; k++) {
//			abyte0[k] = pData[caret++];
//		}
//
//	}
//
//	private void ensureCapacity(int len) {
//		if (caret + len + 1 >= pData.length) {
//			byte[] oldBuffer = pData;
//			int newLength = pData.length * 2;
//			pData = new byte[newLength];
//			System.arraycopy(oldBuffer, 0, pData, 0, oldBuffer.length);
//			ensureCapacity(len);
//		}
//	}


}
