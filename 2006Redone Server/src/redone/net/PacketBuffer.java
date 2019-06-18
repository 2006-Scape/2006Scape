package redone.net;

/**
 * Represents a packet buffer.
 * 
 * @author Ultimate1
 * @author blakeman8192
 */

public class PacketBuffer {

	private int caret;
	private byte[] buffer;

	public PacketBuffer(int capcity) {
		buffer = new byte[capcity];
		caret = 0;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
		caret = 0;
	}

	public PacketBuffer setOpcode(int opcode) {
		return addByte(opcode);
	}

	public PacketBuffer addByte(int i) {
		buffer[caret++] = (byte) i;
		return this;
	}

	public int getByte() {
		return buffer[caret++] & 0xff;
	}

	public PacketBuffer addBoolean(boolean val) {
		return addByte(val == true ? 1 : 0);
	}

	public boolean getBoolean() {
		return getByte() == 1;
	}

	public PacketBuffer addShort(int i) {
		return addByte(i >> 8).addByte(i);
	}

	public int getShort() {
		return getByte() << 8 | getByte();
	}

	public PacketBuffer addInt(int i) {
		return addShort(i >> 16).addShort(i);
	}

	public int getInt() {
		return getShort() << 16 | getShort();
	}

	public PacketBuffer addLong(long i) {
		return addInt((int) (i >> 32)).addInt((int) i);
	}

	public long getLong() {
		return (long) getInt() << 32L | getInt();
	}

	public PacketBuffer addString(String s) {
		for (byte b : s.getBytes()) {
			addByte(b);
		}
		return addByte('\n');
	}

	public String getString() {
		int c;
		StringBuilder builder = new StringBuilder();
		while ((c = getByte()) != '\n') {
			builder.append((char) c);
		}
		return builder.toString();
	}

	public byte[] getBuffer() {
		byte[] newBuffer = new byte[caret + 1];
		newBuffer[0] = (byte) caret;
		System.arraycopy(buffer, 0, newBuffer, 1, caret);
		return newBuffer;
	}

	public int getLength() {
		return buffer.length;
	}

	public void reset() {
		caret = 0;
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = 0;
		}
		buffer = null;
	}
}
