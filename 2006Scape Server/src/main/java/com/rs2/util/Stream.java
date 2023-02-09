package com.rs2.util;

import com.rs2.Constants;
import org.apollo.util.security.IsaacRandom;

public class Stream {

	public Stream() {
	}

	public Stream(byte abyte0[]) {
		buffer = abyte0;
		currentOffset = 0;
	}

	public void writeByteA(int i) {
		ensureCapacity(1);
		buffer[currentOffset++] = (byte) (i + 128);
	}

	public void writeByteS(int i) {
		ensureCapacity(1);
		buffer[currentOffset++] = (byte) (128 - i);
	}

	public void writeByteC(int i) {
		ensureCapacity(1);
		buffer[currentOffset++] = (byte) -i;
	}
	
	public void writeWordBigEndianA(int i) {
		ensureCapacity(2);
		buffer[currentOffset++] = (byte) (i + 128);
		buffer[currentOffset++] = (byte) (i >> 8);
	}

	public void writeWordA(int i) {
		ensureCapacity(2);
		buffer[currentOffset++] = (byte) (i >> 8);
		buffer[currentOffset++] = (byte) (i + 128);
	}

	public void writeWordBigEndian_dup(int i) {
		ensureCapacity(2);
		buffer[currentOffset++] = (byte) i;
		buffer[currentOffset++] = (byte) (i >> 8);
	}

	public int readDWord_v1() {
		currentOffset += 4;
		return ((buffer[currentOffset - 2] & 0xff) << 24)
				+ ((buffer[currentOffset - 1] & 0xff) << 16)
				+ ((buffer[currentOffset - 4] & 0xff) << 8)
				+ (buffer[currentOffset - 3] & 0xff);
	}

	public int readDWord_v2() {
		currentOffset += 4;
		return ((buffer[currentOffset - 3] & 0xff) << 24)
				+ ((buffer[currentOffset - 4] & 0xff) << 16)
				+ ((buffer[currentOffset - 1] & 0xff) << 8)
				+ (buffer[currentOffset - 2] & 0xff);
	}

	public void writeDWord_v1(int i) {
		ensureCapacity(4);
		buffer[currentOffset++] = (byte) (i >> 8);
		buffer[currentOffset++] = (byte) i;
		buffer[currentOffset++] = (byte) (i >> 24);
		buffer[currentOffset++] = (byte) (i >> 16);
	}

	public void writeDWord_v2(int i) {
		ensureCapacity(4);
		buffer[currentOffset++] = (byte) (i >> 16);
		buffer[currentOffset++] = (byte) (i >> 24);
		buffer[currentOffset++] = (byte) i;
		buffer[currentOffset++] = (byte) (i >> 8);
	}

	public void writeBytes_reverse(byte abyte0[], int i, int j) {
		ensureCapacity(i);
		for (int k = j + i - 1; k >= j; k--) {
			buffer[currentOffset++] = abyte0[k];
		}
	}

	public void writeBytes_reverseA(byte abyte0[], int i, int j) {
		ensureCapacity(i);
		for (int k = j + i - 1; k >= j; k--) {
			buffer[currentOffset++] = (byte) (abyte0[k] + 128);
		}

	}

	public void createFrame(int id) {
		ensureCapacity(1);
		buffer[currentOffset++] = (byte) (id + packetEncryption.nextInt());
	}

	private static final int frameStackSize = 10;
	private int frameStackPtr = -1;
	private final int frameStack[] = new int[frameStackSize];

	public void createFrameVarSize(int id) {
		ensureCapacity(3);
		buffer[currentOffset++] = (byte) (id + packetEncryption.nextInt());
		buffer[currentOffset++] = 0;
		if (frameStackPtr >= frameStackSize - 1) {
			throw new RuntimeException("Stack overflow");
		} else {
			frameStack[++frameStackPtr] = currentOffset;
		}
	}

	public void createFrameVarSizeWord(int id) {
		ensureCapacity(2);
		buffer[currentOffset++] = (byte) (id + packetEncryption.nextInt());
		writeWord(0);
		if (frameStackPtr >= frameStackSize - 1) {
			throw new RuntimeException("Stack overflow");
		} else {
			frameStack[++frameStackPtr] = currentOffset;
		}
	}

	public void endFrameVarSize() {
		if (frameStackPtr < 0) {
			throw new RuntimeException("Stack empty");
		} else {
			writeFrameSize(currentOffset - frameStack[frameStackPtr--]);
		}
	}

	public void endFrameVarSizeWord() {
		if (frameStackPtr < 0) {
			throw new RuntimeException("Stack empty");
		} else {
			writeFrameSizeWord(currentOffset - frameStack[frameStackPtr--]);
		}
	}

	public void writeByte(int i) {
		ensureCapacity(1);
		buffer[currentOffset++] = (byte) i;
	}

	public void writeWord(int i) {
		ensureCapacity(2);
		buffer[currentOffset++] = (byte) (i >> 8);
		buffer[currentOffset++] = (byte) i;
	}

	public void writeWordBigEndian(int i) {
		ensureCapacity(2);
		buffer[currentOffset++] = (byte) i;
		buffer[currentOffset++] = (byte) (i >> 8);
	}

	public void write3Byte(int i) {
		ensureCapacity(3);
		buffer[currentOffset++] = (byte) (i >> 16);
		buffer[currentOffset++] = (byte) (i >> 8);
		buffer[currentOffset++] = (byte) i;
	}

	public void writeDWord(int i) {
		ensureCapacity(4);
		buffer[currentOffset++] = (byte) (i >> 24);
		buffer[currentOffset++] = (byte) (i >> 16);
		buffer[currentOffset++] = (byte) (i >> 8);
		buffer[currentOffset++] = (byte) i;
	}

	public void writeDWordBigEndian(int i) {
		ensureCapacity(4);
		buffer[currentOffset++] = (byte) i;
		buffer[currentOffset++] = (byte) (i >> 8);
		buffer[currentOffset++] = (byte) (i >> 16);
		buffer[currentOffset++] = (byte) (i >> 24);
	}

	public void writeQWord(long l) {
		ensureCapacity(8);
		buffer[currentOffset++] = (byte) (int) (l >> 56);
		buffer[currentOffset++] = (byte) (int) (l >> 48);
		buffer[currentOffset++] = (byte) (int) (l >> 40);
		buffer[currentOffset++] = (byte) (int) (l >> 32);
		buffer[currentOffset++] = (byte) (int) (l >> 24);
		buffer[currentOffset++] = (byte) (int) (l >> 16);
		buffer[currentOffset++] = (byte) (int) (l >> 8);
		buffer[currentOffset++] = (byte) (int) l;
	}

	public void writeString(java.lang.String s) {
		ensureCapacity(s.length());
		System.arraycopy(s.getBytes(), 0, buffer, currentOffset, s.length());
		currentOffset += s.length();
		buffer[currentOffset++] = 10;
	}

	public void writeBytes(byte abyte0[], int i, int j) {
		ensureCapacity(i);
		for (int k = j; k < j + i; k++) {
			buffer[currentOffset++] = abyte0[k];
		}
	}

	public void writeFrameSize(int i) {
		buffer[currentOffset - i - 1] = (byte) i;
	}

	public void writeFrameSizeWord(int i) {
		buffer[currentOffset - i - 2] = (byte) (i >> 8);
		buffer[currentOffset - i - 1] = (byte) i;
	}

	public void initBitAccess() {
		bitPosition = currentOffset * 8;
	}

	public void writeBits(int numBits, int value) {
		ensureCapacity((int) Math.ceil(numBits * 8) * 4);
		int bytePos = bitPosition >> 3;
		int bitOffset = 8 - (bitPosition & 7);
		bitPosition += numBits;

		for (; numBits > bitOffset; bitOffset = 8) {
			buffer[bytePos] &= ~bitMaskOut[bitOffset];
			buffer[bytePos++] |= value >> numBits - bitOffset
					& bitMaskOut[bitOffset];

			numBits -= bitOffset;
		}
		if (numBits == bitOffset) {
			buffer[bytePos] &= ~bitMaskOut[bitOffset];
			buffer[bytePos] |= value & bitMaskOut[bitOffset];
		} else {
			buffer[bytePos] &= ~(bitMaskOut[numBits] << bitOffset - numBits);
			buffer[bytePos] |= (value & bitMaskOut[numBits]) << bitOffset
					- numBits;
		}
	}

	public void finishBitAccess() {
		currentOffset = (bitPosition + 7) / 8;
	}

	public byte buffer[] = null;
	public int currentOffset = 0;
	public int bitPosition = 0;

	public static int bitMaskOut[] = new int[32];
	static {
		for (int i = 0; i < 32; i++) {
			bitMaskOut[i] = (1 << i) - 1;
		}
	}

	public void ensureCapacity(int len) {
		if (currentOffset + len + 1 >= buffer.length) {
			byte[] oldBuffer = buffer;
			int newLength = buffer.length * 2;
			buffer = new byte[newLength];
			System.arraycopy(oldBuffer, 0, buffer, 0, oldBuffer.length);
			ensureCapacity(len);
		}
	}

	public void reset() {
		if (!(currentOffset > Constants.BUFFER_SIZE)) {
			byte[] oldBuffer = buffer;
			buffer = new byte[Constants.BUFFER_SIZE];
			for (int i = 0; i < currentOffset; i++) {
				buffer[i] = oldBuffer[i];
			}
		}
	}

	public IsaacRandom packetEncryption = null;

}
