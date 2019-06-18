package org.apollo.jagcached.fs;

/**
 * Holds file system related constants.
 * @author Graham
 */
public final class FileSystemConstants {
	
	/**
	 * The number of caches.
	 */
	public static final int CACHE_COUNT = 5;
	
	/**
	 * The number of archives in cache 0.
	 */
	public static final int ARCHIVE_COUNT = 9;
	
	/**
	 * The size of an index.
	 */
	public static final int INDEX_SIZE = 6;
	
	/**
	 * The size of a header.
	 */
	public static final int HEADER_SIZE = 8;
	
	/**
	 * The size of a chunk.
	 */
	public static final int CHUNK_SIZE = 512;
	
	/**
	 * The size of a block.
	 */
	public static final int BLOCK_SIZE = HEADER_SIZE + CHUNK_SIZE;

	/**
	 * Default private constructor to prevent instantiation.
	 */
	private FileSystemConstants() {
		
	}

}
