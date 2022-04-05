package org.apollo.jagcached.net.jaggrab;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Represents a single JAGGRAB reponse.
 * @author Graham
 */
public final class JagGrabResponse {

	/**
	 * The file data.
	 */
	private final ChannelBuffer fileData;
	
	/**
	 * Creates the response.
	 * @param fileData The file data.
	 */
	public JagGrabResponse(ChannelBuffer fileData) {
		this.fileData = fileData;
	}
	
	/**
	 * Gets the file data.
	 * @return The file data.
	 */
	public ChannelBuffer getFileData() {
		return fileData;
	}
	
}
