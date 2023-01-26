package org.apollo.net.message;

/**
 * A message sent by the client that can be intercepted.
 *
 * @author Graham
 * @author Major
 */
public abstract class Message {

	/**
	 * Indicates whether or not the Message chain has been terminated.
	 */
	private boolean terminated;

	/**
	 * Terminates the Message chain.
	 */
	public final void terminate() {
		terminated = true;
	}

	/**
	 * Returns whether or not the Message chain has been terminated.
	 *
	 * @return {@code true} if the Message chain has been terminated, otherwise {@code false}.
	 */
	public final boolean terminated() {
		return terminated;
	}

}