package org.apollo.jagcached.net.service;

/**
 * Represents a service request message.
 * @author Graham
 */
public final class ServiceRequest {
	
	/**
	 * The game service id.
	 */
	public static final int SERVICE_GAME = 14;
	
	/**
	 * The 'on-demand' service id.
	 */
	public static final int SERVICE_ONDEMAND = 15;
	
	/**
	 * The service id.
	 */
	private final int id;

	/**
	 * Creates a service request.
	 * @param id The service id.
	 */
	public ServiceRequest(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the service id.
	 * @return The service id.
	 */
	public int getId() {
		return id;
	}

}
