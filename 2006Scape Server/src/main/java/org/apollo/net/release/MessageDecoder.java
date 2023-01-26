package org.apollo.net.release;

import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.message.Message;

/**
 * A {@link MessageDecoder} decodes a {@link GamePacket} into a {@link Message} object which can be processed by the
 * server.
 *
 * @author Graham
 * @param <M> The type of message.
 */
public abstract class MessageDecoder<M extends Message> {

	/**
	 * Decodes the specified packet into a message.
	 *
	 * @param packet The packet.
	 * @return The message.
	 */
	public abstract M decode(GamePacket packet);

}