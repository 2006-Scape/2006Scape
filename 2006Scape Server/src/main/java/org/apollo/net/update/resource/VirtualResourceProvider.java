package org.apollo.net.update.resource;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apollo.cache.IndexedFileSystem;

/**
 * A {@link ResourceProvider} which maps virtual resources (such as {@code /media}) to files in an
 * {@link IndexedFileSystem}.
 *
 * @author Graham
 */
public final class VirtualResourceProvider implements ResourceProvider {

	/**
	 * A {@link List} of valid prefixes.
	 */
	private static final List<String> VALID_PREFIXES = Arrays.asList("/crc", "/title", "/config", "/interface", "/media", "/versionlist", "/textures", "/wordenc", "/sounds");

	/**
	 * The file system.
	 */
	private final IndexedFileSystem fs;

	/**
	 * Creates a new virtual resource provider with the specified file system.
	 *
	 * @param fs The file system.
	 */
	public VirtualResourceProvider(IndexedFileSystem fs) {
		this.fs = fs;
	}

	@Override
	public boolean accept(String path) throws IOException {
		Objects.requireNonNull(path);

		return VALID_PREFIXES.stream().anyMatch(path::startsWith);
	}

	@Override
	public Optional<ByteBuffer> get(String path) throws IOException {
		if (path.startsWith("/crc")) {
			return Optional.of(fs.getCrcTable());
		} else if (path.startsWith("/title")) {
			return Optional.of(fs.getFile(0, 1));
		} else if (path.startsWith("/config")) {
			return Optional.of(fs.getFile(0, 2));
		} else if (path.startsWith("/interface")) {
			return Optional.of(fs.getFile(0, 3));
		} else if (path.startsWith("/media")) {
			return Optional.of(fs.getFile(0, 4));
		} else if (path.startsWith("/versionlist")) {
			return Optional.of(fs.getFile(0, 5));
		} else if (path.startsWith("/textures")) {
			return Optional.of(fs.getFile(0, 6));
		} else if (path.startsWith("/wordenc")) {
			return Optional.of(fs.getFile(0, 7));
		} else if (path.startsWith("/sounds")) {
			return Optional.of(fs.getFile(0, 8));
		}

		return Optional.empty();
	}

}