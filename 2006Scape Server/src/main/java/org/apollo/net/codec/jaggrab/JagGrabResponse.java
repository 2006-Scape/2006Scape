package org.apollo.net.codec.jaggrab;

import io.netty.buffer.ByteBuf;

/**
 * Represents a single JAGGRAB response.
 *
 * @author Graham
 */
public final class JagGrabResponse {

	/**
	 * The file data.
	 */
	private final ByteBuf fileData;

	/**
	 * Creates the response.
	 *
	 * @param fileData The file data.
	 */
	public JagGrabResponse(ByteBuf fileData) {
		this.fileData = fileData;
	}

	/**
	 * Gets the file data.
	 *
	 * @return The file data.
	 */
	public ByteBuf getFileData() {
		return fileData;
	}

}