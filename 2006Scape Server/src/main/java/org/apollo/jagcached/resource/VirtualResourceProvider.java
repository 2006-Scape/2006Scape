package org.apollo.jagcached.resource;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apollo.jagcached.fs.IndexedFileSystem;

/**
 * A {@link ResourceProvider} which maps virtual resources (such as
 * {@code /media}) to files in an {@link IndexedFileSystem}.
 * @author Graham Edgecombe
 */
public final class VirtualResourceProvider extends ResourceProvider {
	
	/**
	 * An array of valid prefixes.
	 */
	private static final String[] VALID_PREFIXES = {
		"crc", "title", "config", "interface", "media", "versionlist",
		"textures", "wordenc", "sounds"
	};
	
	/**
	 * The file system.
	 */
	private final IndexedFileSystem fs;
	
	/**
	 * Creates a new virtual resource provider with the specified file system.
	 * @param fs The file system.
	 */
	public VirtualResourceProvider(IndexedFileSystem fs) {
		this.fs = fs;
	}

	@Override
	public boolean accept(String path) throws IOException {
		for (String prefix : VALID_PREFIXES) {
			if (path.startsWith("/" + prefix)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ByteBuffer get(String path) throws IOException {
		if (path.startsWith("/crc")) {
			return fs.getCrcTable();
		} else if (path.startsWith("/title")) {
			return fs.getFile(0, 1);
		} else if (path.startsWith("/config")) {
			return fs.getFile(0, 2);
		} else if (path.startsWith("/interface")) {
			return fs.getFile(0, 3);
		} else if (path.startsWith("/media")) {
			return fs.getFile(0, 4);
		} else if (path.startsWith("/versionlist")) {
			return fs.getFile(0, 5);
		} else if (path.startsWith("/textures")) {
			return fs.getFile(0, 6);
		} else if (path.startsWith("/wordenc")) {
			return fs.getFile(0, 7);
		} else if (path.startsWith("/sounds")) {
			return fs.getFile(0, 8);
		}
		return null;
	}

}
