package com.rs2.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.apollo.util.StatefulFrameDecoder;
import org.apollo.util.security.IsaacRandom;

import com.google.common.base.Preconditions;
import com.rs2.net.Packet.Type;

public class RS2ProtocolDecoder extends StatefulFrameDecoder<GameDecoderState> {
	
	private int opcode = -1;
	private int length = -1;
	private final IsaacRandom random;
	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
			0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
			0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
			8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
			6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
			0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
			0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
			0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
			1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
			0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
			0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
			0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
			0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
			0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
			0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
			2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
			4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
			0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
			1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
			0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
			0, 0, 6, 6, 0, 0, 0 // 250
	};

	/**
	 * To make sure only the CodecFactory can initialise us.
	 */
	public RS2ProtocolDecoder(IsaacRandom isaac) {
		super(GameDecoderState.GAME_OPCODE);
		this.random = isaac;
	}
	
//	/**
//	 * Decodes a message.
//	 * 
//	 * @param session
//	 * @param in
//	 * @param out
//	 * @return
//	 */
//	@Override
//	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//		/*
//		 * If the opcode is not present.
//		 */
//		if (opcode == -1) {
//			/*
//			 * Check if it can be read.
//			 */
//			if (in.readableBytes() >= 1) {
//				/*
//				 * Read and decrypt the opcode.
//				 */
//				opcode = in.readByte() & 0xFF;
//				opcode = (opcode - random.nextInt()) & 0xFF;
//				/*
//				 * Find the packet size.
//				 */
//				length = PACKET_SIZES[opcode];
//			} else {
//				/*
//				 * We need to wait for more data.
//				 */
//				return;
//			}
//		}
//		
//		/*
//		 * If the packet is variable-length.
//		 */
//		if (length == -1) {
//			/*
//			 * Check if the size can be read.
//			 */
//			if (in.readableBytes() >= 1) {
//				/*
//				 * Read the packet size and cache it.
//				 */
//				length = in.readByte() & 0xFF;
//			} else {
//				/*
//				 * We need to wait for more data.
//				 */
//				return;
//			}
//		}
//		
//		/*
//		 * If the packet payload (data) can be read.
//		 */
//		if (in.readableBytes() >= length) {
//			/*
//			 * Read it.
//			 */
//			ByteBuf payload = in.readBytes(length);
//			/*
//			 * Produce and write the packet object.
//			 */
//			out.add(new Packet(opcode, Type.FIXED, payload));
//			opcode = -1;
//			length = -1;
//		}
//		
//		/*
//		 * We need to wait for more data.
//		 */
//		return;
//	}

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

			int s = PACKET_SIZES[opcode];

			switch (s) {
				default:
					length = s;
					if (length == 0) {
						setState(GameDecoderState.GAME_OPCODE);
						out.add(new Packet(opcode, Type.FIXED, Unpooled.EMPTY_BUFFER));
					} else {
						setState(GameDecoderState.GAME_PAYLOAD);
					}
					break;
				case -1:
					setState(GameDecoderState.GAME_LENGTH);
					break;
//				default:
//					throw new IllegalStateException("Illegal packet type: " + type + ".");
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
			out.add(new Packet(opcode, Type.FIXED, payload));
		}
	}
}
