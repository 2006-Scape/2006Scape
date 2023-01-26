package org.apollo.net.codec.game;

import io.netty.buffer.ByteBuf;

import org.apollo.util.BufferUtil;

import com.google.common.base.Preconditions;

/**
 * A utility class for reading {@link GamePacket}s.
 *
 * @author Graham
 */
public final class GamePacketReader {

	/**
	 * The current bit index.
	 */
	private int bitIndex;

	/**
	 * The buffer.
	 */
	private final ByteBuf buffer;

	/**
	 * The current mode.
	 */
	private AccessMode mode = AccessMode.BYTE_ACCESS;

	/**
	 * Creates the reader.
	 *
	 * @param packet The packet.
	 */
	public GamePacketReader(GamePacket packet) {
		buffer = packet.content();
	}

	/**
	 * Checks that this reader is in the bit access mode.
	 *
	 * @throws IllegalStateException If the reader is not in bit access mode.
	 */
	private void checkBitAccess() {
		Preconditions.checkState(mode == AccessMode.BIT_ACCESS,
				"For bit-based calls to work, the mode must be bit access.");
	}

	/**
	 * Checks that this reader is in the byte access mode.
	 *
	 * @throws IllegalStateException If the reader is not in byte access mode.
	 */
	private void checkByteAccess() {
		Preconditions.checkState(mode == AccessMode.BYTE_ACCESS,
				"For byte-based calls to work, the mode must be byte access.");
	}

	/**
	 * Reads a standard data type from the buffer with the specified order and transformation.
	 *
	 * @param type The data type.
	 * @param order The data order.
	 * @param transformation The data transformation.
	 * @return The value.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 * @throws IllegalArgumentException If the combination is invalid.
	 */
	private long get(DataType type, DataOrder order, DataTransformation transformation) {
		checkByteAccess();
		long longValue = 0;
		int length = type.getBytes();
		if (order == DataOrder.BIG) {
			for (int i = length - 1; i >= 0; i--) {
				if (i == 0 && transformation != DataTransformation.NONE) {
					if (transformation == DataTransformation.ADD) {
						longValue |= buffer.readByte() - 128 & 0xFFL;
					} else if (transformation == DataTransformation.NEGATE) {
						longValue |= -buffer.readByte() & 0xFFL;
					} else if (transformation == DataTransformation.SUBTRACT) {
						longValue |= 128 - buffer.readByte() & 0xFFL;
					} else {
						throw new IllegalArgumentException("Unknown transformation.");
					}
				} else {
					longValue |= (buffer.readByte() & 0xFFL) << i * 8;
				}
			}
		} else if (order == DataOrder.LITTLE) {
			for (int i = 0; i < length; i++) {
				if (i == 0 && transformation != DataTransformation.NONE) {
					if (transformation == DataTransformation.ADD) {
						longValue |= buffer.readByte() - 128 & 0xFFL;
					} else if (transformation == DataTransformation.NEGATE) {
						longValue |= -buffer.readByte() & 0xFFL;
					} else if (transformation == DataTransformation.SUBTRACT) {
						longValue |= 128 - buffer.readByte() & 0xFFL;
					} else {
						throw new IllegalArgumentException("Unknown transformation.");
					}
				} else {
					longValue |= (buffer.readByte() & 0xFFL) << i * 8;
				}
			}
		} else if (order == DataOrder.MIDDLE) {
			if (transformation != DataTransformation.NONE) {
				throw new IllegalArgumentException("Middle endian cannot be transformed.");
			}
			if (type != DataType.INT) {
				throw new IllegalArgumentException("Middle endian can only be used with an integer.");
			}
			longValue |= (buffer.readByte() & 0xFF) << 8;
			longValue |= buffer.readByte() & 0xFF;
			longValue |= (buffer.readByte() & 0xFF) << 24;
			longValue |= (buffer.readByte() & 0xFF) << 16;
		} else if (order == DataOrder.INVERSED_MIDDLE) {
			if (transformation != DataTransformation.NONE) {
				throw new IllegalArgumentException("Inversed middle endian cannot be transformed.");
			}
			if (type != DataType.INT) {
				throw new IllegalArgumentException("Inversed middle endian can only be used with an integer.");
			}
			longValue |= (buffer.readByte() & 0xFF) << 16;
			longValue |= (buffer.readByte() & 0xFF) << 24;
			longValue |= buffer.readByte() & 0xFF;
			longValue |= (buffer.readByte() & 0xFF) << 8;
		} else {
			throw new IllegalArgumentException("Unknown order.");
		}
		return longValue;
	}

	/**
	 * Gets a bit from the buffer.
	 *
	 * @return The value.
	 * @throws IllegalStateException If the reader is not in bit access mode.
	 */
	public int getBit() {
		return getBits(1);
	}

	/**
	 * Gets the specified amount of bits from the buffer.
	 *
	 * @param amount The amount of bits.
	 * @return The value.
	 * @throws IllegalStateException If the reader is not in bit access mode.
	 * @throws IllegalArgumentException If the number of bits is not between 1 and 31 inclusive.
	 */
	public int getBits(int amount) {
		Preconditions.checkArgument(amount >= 0 && amount <= 32, "Number of bits must be between 1 and 32 inclusive.");
		checkBitAccess();

		int bytePos = bitIndex >> 3;
		int bitOffset = 8 - (bitIndex & 7);
		int value = 0;
		bitIndex += amount;

		for (; amount > bitOffset; bitOffset = 8) {
			value += (buffer.getByte(bytePos++) & DataConstants.BIT_MASK[bitOffset]) << amount - bitOffset;
			amount -= bitOffset;
		}
		if (amount == bitOffset) {
			value += buffer.getByte(bytePos) & DataConstants.BIT_MASK[bitOffset];
		} else {
			value += buffer.getByte(bytePos) >> bitOffset - amount & DataConstants.BIT_MASK[amount];
		}
		return value;
	}

	/**
	 * Gets bytes.
	 *
	 * @param bytes The target byte array.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 */
	public void getBytes(byte[] bytes) {
		checkByteAccess();
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = buffer.readByte();
		}
	}

	/**
	 * Gets bytes with the specified transformation.
	 *
	 * @param transformation The transformation.
	 * @param bytes The target byte array.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 */
	public void getBytes(DataTransformation transformation, byte[] bytes) {
		if (transformation == DataTransformation.NONE) {
			getBytesReverse(bytes);
		} else {
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = (byte) getSigned(DataType.BYTE, transformation);
			}
		}
	}

	/**
	 * Gets bytes in reverse.
	 *
	 * @param bytes The target byte array.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 */
	public void getBytesReverse(byte[] bytes) {
		checkByteAccess();
		for (int i = bytes.length - 1; i >= 0; i--) {
			bytes[i] = buffer.readByte();
		}
	}

	/**
	 * Gets bytes in reverse with the specified transformation.
	 *
	 * @param transformation The transformation.
	 * @param bytes The target byte array.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 */
	public void getBytesReverse(DataTransformation transformation, byte[] bytes) {
		if (transformation == DataTransformation.NONE) {
			getBytesReverse(bytes);
		} else {
			for (int i = bytes.length - 1; i >= 0; i--) {
				bytes[i] = (byte) getSigned(DataType.BYTE, transformation);
			}
		}
	}

	/**
	 * Gets the length of this reader.
	 *
	 * @return The length of this reader.
	 */
	public int getLength() {
		checkByteAccess();
		return buffer.writableBytes();
	}

	/**
	 * Gets a signed data type from the buffer.
	 *
	 * @param type The data type.
	 * @return The value.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 */
	public long getSigned(DataType type) {
		return getSigned(type, DataOrder.BIG, DataTransformation.NONE);
	}

	/**
	 * Gets a signed data type from the buffer with the specified order.
	 *
	 * @param type The data type.
	 * @param order The byte order.
	 * @return The value.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 * @throws IllegalArgumentException If the combination is invalid.
	 */
	public long getSigned(DataType type, DataOrder order) {
		return getSigned(type, order, DataTransformation.NONE);
	}

	/**
	 * Gets a signed data type from the buffer with the specified order and transformation.
	 *
	 * @param type The data type.
	 * @param order The byte order.
	 * @param transformation The data transformation.
	 * @return The value.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 * @throws IllegalArgumentException If the combination is invalid.
	 */
	public long getSigned(DataType type, DataOrder order, DataTransformation transformation) {
		long longValue = get(type, order, transformation);
		if (type != DataType.LONG) {
			int max = (int) (Math.pow(2, type.getBytes() * 8 - 1) - 1);
			if (longValue > max) {
				longValue -= (max + 1) * 2;
			}
		}
		return longValue;
	}

	/**
	 * Gets a signed data type from the buffer with the specified transformation.
	 *
	 * @param type The data type.
	 * @param transformation The data transformation.
	 * @return The value.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 * @throws IllegalArgumentException If the combination is invalid.
	 */
	public long getSigned(DataType type, DataTransformation transformation) {
		return getSigned(type, DataOrder.BIG, transformation);
	}

	/**
	 * Gets a signed smart from the buffer.
	 *
	 * @return The smart.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 */
	public int getSignedSmart() {
		checkByteAccess();
		int peek = buffer.getByte(buffer.readerIndex());
		if (peek < 128) {
			return buffer.readByte() - 64;
		}
		return buffer.readShort() - 49152;
	}

	/**
	 * Gets a string from the buffer.
	 *
	 * @return The string.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 */
	public String getString() {
		checkByteAccess();
		return BufferUtil.readString(buffer);
	}

	/**
	 * Gets an unsigned data type from the buffer.
	 *
	 * @param type The data type.
	 * @return The value.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 */
	public long getUnsigned(DataType type) {
		return getUnsigned(type, DataOrder.BIG, DataTransformation.NONE);
	}

	/**
	 * Gets an unsigned data type from the buffer with the specified order.
	 *
	 * @param type The data type.
	 * @param order The byte order.
	 * @return The value.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 * @throws IllegalArgumentException If the combination is invalid.
	 */
	public long getUnsigned(DataType type, DataOrder order) {
		return getUnsigned(type, order, DataTransformation.NONE);
	}

	/**
	 * Gets an unsigned data type from the buffer with the specified order and transformation.
	 *
	 * @param type The data type.
	 * @param order The byte order.
	 * @param transformation The data transformation.
	 * @return The value.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 * @throws IllegalArgumentException If the combination is invalid.
	 */
	public long getUnsigned(DataType type, DataOrder order, DataTransformation transformation) {
		long longValue = get(type, order, transformation);
		Preconditions.checkArgument(type != DataType.LONG, "Longs must be read as a signed type.");
		return longValue & 0xFFFFFFFFFFFFFFFFL;
	}

	/**
	 * Gets an unsigned data type from the buffer with the specified transformation.
	 *
	 * @param type The data type.
	 * @param transformation The data transformation.
	 * @return The value.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 * @throws IllegalArgumentException If the combination is invalid.
	 */
	public long getUnsigned(DataType type, DataTransformation transformation) {
		return getUnsigned(type, DataOrder.BIG, transformation);
	}

	/**
	 * Gets an unsigned smart from the buffer.
	 *
	 * @return The smart.
	 * @throws IllegalStateException If this reader is not in byte access mode.
	 */
	public int getUnsignedSmart() {
		checkByteAccess();
		int peek = buffer.getByte(buffer.readerIndex());
		if (peek < 128) {
			return buffer.readByte();
		}
		return buffer.readShort() - 32768;
	}

	/**
	 * Switches this builder's mode to the bit access mode.
	 *
	 * @throws IllegalStateException If the builder is already in bit access mode.
	 */
	public void switchToBitAccess() {
		Preconditions.checkState(mode != AccessMode.BIT_ACCESS, "Already in bit access mode.");
		mode = AccessMode.BIT_ACCESS;
		bitIndex = buffer.readerIndex() * 8;
	}

	/**
	 * Switches this builder's mode to the byte access mode.
	 *
	 * @throws IllegalStateException If the builder is already in byte access mode.
	 */
	public void switchToByteAccess() {
		Preconditions.checkState(mode != AccessMode.BYTE_ACCESS, "Already in byte access mode.");
		mode = AccessMode.BYTE_ACCESS;
		buffer.readerIndex((bitIndex + 7) / 8);
	}

}