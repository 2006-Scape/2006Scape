package org.apollo.net.update.resource;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * A {@link ResourceProvider} which provides additional hypertext resources.
 *
 * @author Graham
 */
public final class HypertextResourceProvider implements ResourceProvider {

	/**
	 * The base {@link Path} from which documents are served.
	 */
	private final Path base;

	/**
	 * Creates a new hypertext resource provider with the specified base directory.
	 *
	 * @param base The base directory.
	 */
	public HypertextResourceProvider(Path base) {
		this.base = base;
	}

	@Override
	public boolean accept(String path) throws IOException {
		Path file = base.resolve(path);

		URI target = file.toUri().normalize();
		if (!target.toASCIIString().startsWith(base.toUri().normalize().toASCIIString())) {
			return false;
		}

		if (Files.isDirectory(file)) {
			file = file.resolve("index.html");
		}

		return Files.exists(file);
	}

	@Override
	public Optional<ByteBuffer> get(String path) throws IOException {
		Path root = base.resolve(path);

		if (Files.isDirectory(root)) {
			root = root.resolve("index.html");
		}

		if (!Files.exists(root)) {
			return Optional.empty();
		}

		try (FileChannel channel = FileChannel.open(root)) {
			ByteBuffer buf = channel.map(MapMode.READ_ONLY, 0, Files.size(root));
			return Optional.of(buf);
		}
	}

}