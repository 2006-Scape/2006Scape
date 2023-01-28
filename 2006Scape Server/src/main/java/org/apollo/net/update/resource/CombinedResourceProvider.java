package org.apollo.net.update.resource;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;

/**
 * A resource provider composed of multiple resource providers.
 *
 * @author Graham
 */
public final class CombinedResourceProvider implements ResourceProvider {

	/**
	 * An array of resource providers.
	 */
	private final ResourceProvider[] providers;

	/**
	 * Creates the combined resource providers.
	 *
	 * @param providers The providers this provider delegates to.
	 */
	public CombinedResourceProvider(ResourceProvider... providers) {
		this.providers = providers;
	}

	@Override
	public boolean accept(String path) throws IOException {
		return true;
	}

	@Override
	public Optional<ByteBuffer> get(String path) throws IOException {
		for (ResourceProvider provider : providers) {
			if (provider.accept(path)) {
				return provider.get(path);
			}
		}
		return Optional.empty();
	}

}