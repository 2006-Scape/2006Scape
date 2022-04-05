package org.apollo.jagcached.fs;

/**
 * An {@link Index} points to a file in the {@code main_file_cache.dat} file.
 * @author Graham
 */
public final class Index {

	/**
	 * Decodes a buffer into an index.
	 * @param buffer The buffer.
	 * @return The decoded {@link Index}.
	 * @throws IllegalArgumentException if the buffer length is invalid.
	 */
	public static Index decode(byte[] buffer) {
		if (buffer.length != FileSystemConstants.INDEX_SIZE) {
			throw new IllegalArgumentException("Incorrect buffer length.");
		}
		
		int size = ((buffer[0] & 0xFF) << 16) | ((buffer[1] & 0xFF) << 8) | (buffer[2] & 0xFF);
		int block = ((buffer[3] & 0xFF) << 16) | ((buffer[4] & 0xFF) << 8) | (buffer[5] & 0xFF);
		
		return new Index(size, block);
	}
	
	/**
	 * The size of the file.
	 */
	private final int size;
	
	/**
	 * The first block of the file.
	 */
	private final int block;
	
	/**
	 * Creates the index.
	 * @param size The size of the file.
	 * @param block The first block of the file.
	 */
	public Index(int size, int block) {
		this.size = size;
		this.block = block;
	}
	
	/**
	 * Gets the size of the file.
	 * @return The size of the file.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Gets the first block of the file.
	 * @return The first block of the file.
	 */
	public int getBlock() {
		return block;
	}

}
