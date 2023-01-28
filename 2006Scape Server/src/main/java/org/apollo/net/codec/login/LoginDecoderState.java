package org.apollo.net.codec.login;

/**
 * An enumeration with the different states the {@link LoginDecoder} can be in.
 *
 * @author Graham
 */
public enum LoginDecoderState {

	/**
	 * The login handshake state will wait for the username hash to be received. Once it is, a server session key will
	 * be sent to the client and the state will be set to the login header state.
	 */
	LOGIN_HANDSHAKE,

	/**
	 * The login header state will wait for the login type and payload length to be received. These are saved, and then
	 * the state will be set to the login payload state.
	 */
	LOGIN_HEADER,

	/**
	 * The login payload state will wait for all login information (such as client release number, username and
	 * password).
	 */
	LOGIN_PAYLOAD;

}