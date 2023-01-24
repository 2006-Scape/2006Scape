package com.rs2.net;

import org.apache.mina.common.IoSession;

/**
 * Immutable packet object.
 * 
 * @author Graham
 */
public final class Packet {

	public static enum Size {
		Fixed, VariableByte, VariableShort
	};

	/**
	 * The associated IO session
	 */
	private final IoSession session;
	/**
	 * The ID of the packet
	 */
	private final int pID;
	/**
	 * The length of the payload
	 */
	private final int pLength;
	/**
	 * The payload
	 */
	public /*private final*/ byte[] pData;
	/**
	 * The current index into the payload buffer for reading
	 */
	private int caret = 0;
	/**
	 * Whether this packet is without the standard packet header
	 */
	private final boolean bare;
	private Size size = Size.Fixed;

	public Packet(IoSession session, int pID, byte[] pData, boolean bare, Size s) {
		this.session = session;
		this.pID = pID;
		this.pData = pData;
		pLength = pData.length;
		this.bare = bare;
		size = s;
	}

	/**
	 * Creates a new packet with the specified parameters.
	 * 
	 * @param session
	 *            The session to associate with the packet
	 * @param pID
	 *            The ID of the packet
	 * @param pData
	 *            The payload of the packet
	 * @param bare
	 *            Whether this packet is bare, which means that it does not
	 *            include the standard packet header
	 */
	public Packet(IoSession session, int pID, byte[] pData, boolean bare) {
		this(session, pID, pData, bare, Size.Fixed);
	}

	/**
	 * Creates a new packet with the specified parameters. The packet is
	 * considered not to be a bare packet.
	 * 
	 * @param session
	 *            The session to associate with the packet
	 * @param pID
	 *            The ID of the packet
	 * @param pData
	 *            The payload the packet
	 */
	public Packet(IoSession session, int pID, byte[] pData) {
		this(session, pID, pData, false);
	}

	/**
	 * Returns the IO session associated with the packet, if any.
	 * 
	 * @return The <code>IoSession</code> object, or <code>null</code> if none.
	 */
	public IoSession getSession() {
		return session;
	}

	/**
	 * Checks if this packet is considered to be a bare packet, which means that
	 * it does not include the standard packet header (ID and length values).
	 * 
	 * @return Whether this packet is a bare packet
	 */
	public boolean isBare() {
		return bare;
	}

	public Size getSize() {
		return size;
	}

	/**
	 * Returns the packet ID.
	 * 
	 * @return The packet ID
	 */
	public int getId() {
		return pID;
	}

	/**
	 * Returns the length of the payload of this packet.
	 * 
	 * @return The length of the packet's payload
	 */
	public int getLength() {
		return pLength;
	}

	/**
	 * Returns the entire payload data of this packet.
	 * 
	 * @return The payload <code>byte</code> array
	 */
	public byte[] getData() {
		return pData;
	}

	/**
	 * Returns the remaining payload data of this packet.
	 * 
	 * @return The payload <code>byte</code> array
	 */
	public byte[] getRemainingData() {
		byte[] data = new byte[pLength - caret];
		for (int i = 0; i < data.length; i++) {
			data[i] = pData[i + caret];
		}
		caret += data.length;
		return data;

	}

	public int remaining() {
		return pData.length - caret;
	}

	/**
	 * Returns this packet in string form.
	 * 
	 * @return A <code>String</code> representing this packet
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[id=" + pID + ",len=" + pLength + ",data=0x");
		for (int x = 0; x < pLength; x++) {
			sb.append(byteToHex(pData[x], true));
		}
		sb.append("]");
		return sb.toString();
	}

	private static String byteToHex(byte b, boolean forceLeadingZero) {
		StringBuilder out = new StringBuilder();
		int ub = b & 0xff;
		if (ub / 16 > 0 || forceLeadingZero) {
			out.append(hex[ub / 16]);
		}
		out.append(hex[ub % 16]);
		return out.toString();
	}

	private static final char[] hex = "0123456789ABCDEF".toCharArray();

	
	/* TODO Stream methods below */
	
	public long readQWord2() {
		final long l = readDWord() & 0xffffffffL;
		final long l1 = readDWord() & 0xffffffffL;
		return (l << 32) + l1;
	}

	public byte readSignedByteA() {
		return (byte) (pData[caret++] - 128);
	}

	public byte readSignedByteC() {
		return (byte) -pData[caret++];
	}

	public byte readSignedByteS() {
		return (byte) (128 - pData[caret++]);
	}

	public int readUnsignedByteA() {
		return pData[caret++] - 128 & 0xff;
	}

	public int readUnsignedByteC() {
		return -pData[caret++] & 0xff;
	}

	public int readUnsignedByteS() {
		return 128 - pData[caret++] & 0xff;
	}

	public void writeByteA(int i) {
		ensureCapacity(1);
		pData[caret++] = (byte) (i + 128);
	}

	public void writeByteS(int i) {
		ensureCapacity(1);
		pData[caret++] = (byte) (128 - i);
	}

	public void writeByteC(int i) {
		ensureCapacity(1);
		pData[caret++] = (byte) -i;
	}

	public int readSignedWordBigEndian() {
		caret += 2;
		int i = ((pData[caret - 1] & 0xff) << 8) + (pData[caret - 2] & 0xff);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readSignedWordA() {
		caret += 2;
		int i = ((pData[caret - 2] & 0xff) << 8) + (pData[caret - 1] - 128 & 0xff);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readSignedWordBigEndianA() {
		caret += 2;
		int i = ((pData[caret - 1] & 0xff) << 8) + (pData[caret - 2] - 128 & 0xff);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readUnsignedWordBigEndian() {
		caret += 2;
		return ((pData[caret - 1] & 0xff) << 8) + (pData[caret - 2] & 0xff);
	}

	public int readUnsignedWordA() {
		caret += 2;
		return ((pData[caret - 2] & 0xff) << 8) + (pData[caret - 1] - 128 & 0xff);
	}

	public int readUnsignedWordBigEndianA() {
		caret += 2;
		return ((pData[caret - 1] & 0xff) << 8) + (pData[caret - 2] - 128 & 0xff);
	}

	public void writeWordBigEndianA(int i) {
		ensureCapacity(2);
		pData[caret++] = (byte) (i + 128);
		pData[caret++] = (byte) (i >> 8);
	}

	public void writeWordA(int i) {
		ensureCapacity(2);
		pData[caret++] = (byte) (i >> 8);
		pData[caret++] = (byte) (i + 128);
	}

	public void writeWordBigEndian_dup(int i) {
		ensureCapacity(2);
		pData[caret++] = (byte) i;
		pData[caret++] = (byte) (i >> 8);
	}

	public int readDWord_v1() {
		caret += 4;
		return ((pData[caret - 2] & 0xff) << 24)
				+ ((pData[caret - 1] & 0xff) << 16)
				+ ((pData[caret - 4] & 0xff) << 8)
				+ (pData[caret - 3] & 0xff);
	}

	public int readDWord_v2() {
		caret += 4;
		return ((pData[caret - 3] & 0xff) << 24)
				+ ((pData[caret - 4] & 0xff) << 16)
				+ ((pData[caret - 1] & 0xff) << 8)
				+ (pData[caret - 2] & 0xff);
	}

	public void writeDWord_v1(int i) {
		ensureCapacity(4);
		pData[caret++] = (byte) (i >> 8);
		pData[caret++] = (byte) i;
		pData[caret++] = (byte) (i >> 24);
		pData[caret++] = (byte) (i >> 16);
	}

	public void writeDWord_v2(int i) {
		ensureCapacity(4);
		pData[caret++] = (byte) (i >> 16);
		pData[caret++] = (byte) (i >> 24);
		pData[caret++] = (byte) i;
		pData[caret++] = (byte) (i >> 8);
	}

	public void readBytes_reverse(byte abyte0[], int i, int j) {
		for (int k = j + i - 1; k >= j; k--) {
			abyte0[k] = pData[caret++];
		}

	}

	public void writeBytes_reverse(byte abyte0[], int i, int j) {
		ensureCapacity(i);
		for (int k = j + i - 1; k >= j; k--) {
			pData[caret++] = abyte0[k];
		}
	}

	public void readBytes_reverseA(byte abyte0[], int i, int j) {
		ensureCapacity(i);
		for (int k = j + i - 1; k >= j; k--) {
			abyte0[k] = (byte) (pData[caret++] - 128);
		}

	}

	public int readUnsignedByte() {
		return pData[caret++] & 0xff;
	}

	public byte readSignedByte() {
		return pData[caret++];
	}

	public int readUnsignedWord() {
		caret += 2;
		return ((pData[caret - 2] & 0xff) << 8) + (pData[caret - 1] & 0xff);
	}

	public int readSignedWord() {
		caret += 2;
		int i = ((pData[caret - 2] & 0xff) << 8) + (pData[caret - 1] & 0xff);
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}

	public int readDWord() {
		caret += 4;
		return ((pData[caret - 4] & 0xff) << 24)
				+ ((pData[caret - 3] & 0xff) << 16)
				+ ((pData[caret - 2] & 0xff) << 8)
				+ (pData[caret - 1] & 0xff);
	}

	public long readQWord() {
		long l = readDWord() & 0xffffffffL;
		long l1 = readDWord() & 0xffffffffL;
		return (l << 32) + l1;
	}

	public java.lang.String readString() {
		int i = caret;
		while (pData[caret++] != 10) {
			;
		}
		return new String(pData, i, caret - i - 1);
	}

	public void readBytes(byte abyte0[], int i, int j) {
		for (int k = j; k < j + i; k++) {
			abyte0[k] = pData[caret++];
		}

	}

	public void ensureCapacity(int len) {
		if (caret + len + 1 >= pData.length) {
			byte[] oldBuffer = pData;
			int newLength = pData.length * 2;
			pData = new byte[newLength];
			System.arraycopy(oldBuffer, 0, pData, 0, oldBuffer.length);
			ensureCapacity(len);
		}
	}
}
