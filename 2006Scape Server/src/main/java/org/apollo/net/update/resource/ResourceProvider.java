package org.apollo.net.update.resource;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;

/**
 * A class which provides resources.
 *
 * @author Graham
 */
public interface ResourceProvider {

	/**
	 * Checks that this provider can fulfil a request to the specified resource.
	 *
	 * @param path The path to the resource, e.g. {@code /crc}.
	 * @return {@code true} if the provider can fulfil a request to the resource, {@code false} otherwise.
	 * @throws IOException If an I/O error occurs.
	 */
	public boolean accept(String path) throws IOException;

	/**
	 * Gets the resource data, as a {@link ByteBuffer}, wrapped in an {@link Optional}.
	 *
	 * @param path The path to the resource.
	 * @return A {@code ByteBuffer} representation of a resource if it exists otherwise {@link Optional#empty()} is
	 *         returned.
	 * @throws IOException If some I/O exception occurs.
	 */
	public Optional<ByteBuffer> get(String path) throws IOException;

}