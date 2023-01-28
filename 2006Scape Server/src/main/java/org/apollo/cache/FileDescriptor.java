package org.apollo.cache;

import java.io.File;

/**
 * A class which points to a file in the cache.
 *
 * @author Graham
 */
public final class FileDescriptor {

	/**
	 * The file id.
	 */
	private final int file;

	/**
	 * The file type.
	 */
	private final int type;

	/**
	 * Creates the file descriptor.
	 *
	 * @param type The file type.
	 * @param file The file id.
	 */
	public FileDescriptor(int type, int file) {
		this.type = type;
		this.file = file;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FileDescriptor) {
			FileDescriptor other = (FileDescriptor) obj;
			return type == other.type && file == other.file;
		}

		return false;
	}

	/**
	 * Gets the file id.
	 *
	 * @return The file id.
	 */
	public int getFile() {
		return file;
	}

	/**
	 * Gets the file type.
	 *
	 * @return The file type.
	 */
	public int getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return file * FileSystemConstants.ARCHIVE_COUNT + type;
	}

}