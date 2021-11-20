package org.apollo.jagcached.resource;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * A resource provider composed of multiple resource providers.
 * @author Graham Edgecombe
 */
public final class CombinedResourceProvider extends ResourceProvider {
	
	/**
	 * An array of resource providers.
	 */
	private final ResourceProvider[] providers;
	
	/**
	 * Creates the combined resource providers.
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
	public ByteBuffer get(String path) throws IOException {
		for (ResourceProvider provider : providers) {
			if (provider.accept(path)) {
				return provider.get(path);
			}
		}
		return null;
	}

}
