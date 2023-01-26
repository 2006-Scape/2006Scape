package org.apollo.net.codec.game;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apollo.net.meta.PacketType;
import org.apollo.util.security.IsaacRandom;

/**
 * A {@link MessageToByteEncoder} which encodes in-game packets.
 *
 * @author Graham
 */
public final class GamePacketEncoder extends MessageToByteEncoder<GamePacket> {

	/**
	 * The random number generator.
	 */
	private final IsaacRandom random;

	/**
	 * Creates the {@link GamePacketEncoder}.
	 *
	 * @param random The random number generator.
	 */
	public GamePacketEncoder(IsaacRandom random) {
		this.random = random;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, GamePacket packet, ByteBuf out) throws Exception {
		PacketType type = packet.getType();
		int payloadLength = packet.getLength();
		
		if (type == PacketType.VARIABLE_BYTE && payloadLength >= 256) {
			throw new Exception("Payload too long for variable byte packet.");
		} else if (type == PacketType.VARIABLE_SHORT && payloadLength >= 65_536) {
			throw new Exception("Payload too long for variable short packet.");
		}

		out.writeByte(packet.getOpcode() + random.nextInt() & 0xFF);
		if (type == PacketType.VARIABLE_BYTE) {
			out.writeByte(payloadLength);
		} else if (type == PacketType.VARIABLE_SHORT) {
			out.writeShort(payloadLength);
		}
		out.writeBytes(packet.content());
	}

}