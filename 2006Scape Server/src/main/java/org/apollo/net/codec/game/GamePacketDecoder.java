package org.apollo.net.codec.game;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.apollo.net.meta.PacketMetaData;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.Release;
import org.apollo.util.StatefulFrameDecoder;
import org.apollo.util.security.IsaacRandom;

import com.google.common.base.Preconditions;

/**
 * A {@link StatefulFrameDecoder} that decodes {@link GamePacket}s.
 *
 * @author Graham
 */
public final class GamePacketDecoder extends StatefulFrameDecoder<GameDecoderState> {

	/**
	 * The current length.
	 */
	private int length;

	/**
	 * The current opcode.
	 */
	private int opcode;

	/**
	 * The random number generator.
	 */
	private final IsaacRandom random;

	/**
	 * The packet type.
	 */
	private PacketType type;

	/**
	 * Creates the {@link GamePacketDecoder}.
	 *
	 * @param random The random number generator.
	 * @param release The current release.
	 */
	public GamePacketDecoder(IsaacRandom random) {
		super(GameDecoderState.GAME_OPCODE);
		this.random = random;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out, GameDecoderState state) {
		switch (state) {
			case GAME_OPCODE:
				decodeOpcode(in, out);
				break;
			case GAME_LENGTH:
				decodeLength(in);
				break;
			case GAME_PAYLOAD:
				decodePayload(in, out);
				break;
			default:
				throw new IllegalStateException("Invalid game decoder state.");
		}
	}

	/**
	 * Decodes the length state.
	 *
	 * @param buffer The buffer.
	 */
	private void decodeLength(ByteBuf buffer) {
		if (buffer.isReadable()) {
			length = buffer.readUnsignedByte();
			if (length != 0) {
				setState(GameDecoderState.GAME_PAYLOAD);
			}
		}
	}

	/**
	 * Decodes the opcode state.
	 *
	 * @param buffer The buffer.
	 * @param out The {@link List} of objects to be passed along the pipeline.
	 */
	private void decodeOpcode(ByteBuf buffer, List<Object> out) {
		if (buffer.isReadable()) {
			int encryptedOpcode = buffer.readUnsignedByte();
			opcode = encryptedOpcode - random.nextInt() & 0xFF;

			PacketMetaData metaData = Scuffed.SCAPE2006.getMetaData(opcode);
			Preconditions.checkNotNull(metaData, "Illegal opcode: " + opcode + ".");

			type = metaData.getType();
			switch (type) {
				case FIXED:
					length = metaData.getLength();
					if (length == 0) {
						setState(GameDecoderState.GAME_OPCODE);
						out.add(new GamePacket(opcode, type, Unpooled.EMPTY_BUFFER));
					} else {
						setState(GameDecoderState.GAME_PAYLOAD);
					}
					break;
				case VARIABLE_BYTE:
					setState(GameDecoderState.GAME_LENGTH);
					break;
				default:
					throw new IllegalStateException("Illegal packet type: " + type + ".");
			}
		}
	}

	/**
	 * Decodes the payload state.
	 *
	 * @param buffer The buffer.
	 * @param out The {@link List} of objects to be passed along the pipeline.
	 */
	private void decodePayload(ByteBuf buffer, List<Object> out) {
		if (buffer.readableBytes() >= length) {
			ByteBuf payload = buffer.readBytes(length);
			setState(GameDecoderState.GAME_OPCODE);
			out.add(new GamePacket(opcode, type, payload));
		}
	}

}