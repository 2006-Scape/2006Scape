package org.apollo.net.release;

import java.util.HashMap;
import java.util.Map;

import org.apollo.net.message.Message;
import org.apollo.net.meta.PacketMetaData;
import org.apollo.net.meta.PacketMetaDataGroup;

import com.google.common.base.Preconditions;

/**
 * A {@link Release} is a distinct client version, e.g. {@code 317}.
 *
 * @author Graham
 */
public abstract class Release {

	/**
	 * The array of message decoders.
	 */
	private final MessageDecoder<?>[] decoders = new MessageDecoder<?>[256];

	/**
	 * The map of message classes to message encoders.
	 */
	private final Map<Class<? extends Message>, MessageEncoder<?>> encoders = new HashMap<>();

	/**
	 * The incoming packet meta data.
	 */
	private final PacketMetaDataGroup incomingPacketMetaData;

	/**
	 * The release number, e.g. {@code 317}.
	 */
	private final int releaseNumber;

	/**
	 * Creates the release.
	 *
	 * @param releaseNumber The release number.
	 * @param incomingPacketMetaData The incoming packet meta data.
	 */
	public Release(int releaseNumber, PacketMetaDataGroup incomingPacketMetaData) {
		this.releaseNumber = releaseNumber;
		this.incomingPacketMetaData = incomingPacketMetaData;
	}

	/**
	 * Gets meta data for the specified incoming packet.
	 *
	 * @param opcode The opcode of the incoming packet.
	 * @return The {@link PacketMetaData} object.
	 */
	public final PacketMetaData getIncomingPacketMetaData(int opcode) {
		return incomingPacketMetaData.getMetaData(opcode);
	}

	/**
	 * Gets the {@link MessageDecoder} for the specified opcode.
	 *
	 * @param opcode The opcode.
	 * @return The message decoder.
	 * @throws IndexOutOfBoundsException If the opcode is less than 0, or greater than 255.
	 */
	public final MessageDecoder<?> getMessageDecoder(int opcode) {
		Preconditions.checkElementIndex(opcode, decoders.length, "Opcode out of bounds.");
		return decoders[opcode];
	}

	/**
	 * Gets the {@link MessageEncoder} for the specified message type.
	 *
	 * @param type The type of message.
	 * @return The message encoder.
	 */
	@SuppressWarnings("unchecked")
	public <M extends Message> MessageEncoder<M> getMessageEncoder(Class<M> type) {
		return (MessageEncoder<M>) encoders.get(type);
	}

	/**
	 * Gets the release number.
	 *
	 * @return The release number.
	 */
	public final int getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * Registers a {@link MessageEncoder} for the specified message type.
	 *
	 * @param type The message type.
	 * @param encoder The message encoder.
	 */
	public final <M extends Message> void register(Class<M> type, MessageEncoder<M> encoder) {
		encoders.put(type, encoder);
	}

	/**
	 * Registers a {@link MessageDecoder} for the specified opcode.
	 *
	 * @param opcode The opcode, between 0 and 255 inclusive.
	 * @param decoder The message decoder.
	 * @throws IndexOutOfBoundsException If the opcode is less than 0, or greater than 255.
	 */
	public final <M extends Message> void register(int opcode, MessageDecoder<M> decoder) {
		Preconditions.checkElementIndex(opcode, decoders.length, "Opcode out of bounds.");
		decoders[opcode] = decoder;
	}

	@Override
	public final String toString() {
		return Release.class.getSimpleName() + " " + releaseNumber;
	}

}