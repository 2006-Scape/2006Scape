package org.apollo.jagcached.resource;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * A class which provides resources.
 * @author Graham Edgecombe
 */
public abstract class ResourceProvider {

	/**
	 * Checks that this provider can fulfil a request to the specified
	 * resource.
	 * @param path The path to the resource, e.g. {@code /crc}.
	 * @return {@code true} if the provider can fulfil a request to the
	 * resource, {@code false} otherwise.
	 * @throws IOException if an I/O error occurs.
	 */
	public abstract boolean accept(String path) throws IOException;
	
	/**
	 * Gets a resource by its path.
	 * @param path The path.
	 * @return The resource, or {@code null} if it doesn't exist.
	 * @throws IOException if an I/O error occurs.
	 */
	public abstract ByteBuffer get(String path) throws IOException;

}
