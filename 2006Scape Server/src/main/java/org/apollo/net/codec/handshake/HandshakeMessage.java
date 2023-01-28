package org.apollo.net.codec.handshake;

/**
 * A handshake message in the service handshake protocol.
 *
 * @author Graham
 */
public final class HandshakeMessage {

	/**
	 * The service id.
	 */
	private final int serviceId;

	/**
	 * Creates the handshake message.
	 *
	 * @param serviceId The service id.
	 */
	public HandshakeMessage(int serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * Gets the service id.
	 *
	 * @return The service id.
	 */
	public int getServiceId() {
		return serviceId;
	}

}