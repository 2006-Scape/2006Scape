package org.apollo.jagcached.net.jaggrab;

/**
 * Represents the request for a single file using the JAGGRAB protocol.
 * @author Graham
 */
public final class JagGrabRequest {
	
	/**
	 * The path to the file.
	 */
	private final String filePath;

	/**
	 * Creates the request.
	 * @param filePath The file path.
	 */
	public JagGrabRequest(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Gets the file path.
	 * @return The file path.
	 */
	public String getFilePath() {
		return filePath;
	}

}
