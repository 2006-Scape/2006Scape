package com.rs2.world.clip;

public class ByteStream {

	private final byte[] buffer;
	private int offset;

	public ByteStream(byte[] buffer) {
		this.buffer = buffer;
		offset = 0;
	}

	public void skip(int length) {
		offset += length;
	}

	public int getUByte() {
		return buffer[offset++] & 0xff;
	}

	public int getUShort() {
		return (getUByte() << 8) + getUByte();
	}

	public int getUSmart() {
		int i = buffer[offset] & 0xff;
		if (i < 128) {
			return getUByte();
		} else {
			return getUShort() - 32768;
		}
	}
}
