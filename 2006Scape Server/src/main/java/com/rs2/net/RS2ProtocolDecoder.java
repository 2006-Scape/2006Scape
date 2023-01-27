package com.rs2.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.apollo.util.security.IsaacRandom;

import com.rs2.net.Packet.Type;

public class RS2ProtocolDecoder extends ByteToMessageDecoder {
	
	private int opcode = -1;
	private int size = -1;
	private final IsaacRandom isaac;
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
		this.isaac = isaac;
	}

	/**
	 * Decodes a message.
	 * 
	 * @param session
	 * @param in
	 * @param out
	 * @return
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		/*
		 * If the opcode is not present.
		 */
		if (opcode == -1) {
			/*
			 * Check if it can be read.
			 */
			if (in.readableBytes() >= 1) {
				/*
				 * Read and decrypt the opcode.
				 */
				opcode = in.readByte() & 0xFF;
				opcode = (opcode - isaac.nextInt()) & 0xFF;
				/*
				 * Find the packet size.
				 */
				size = PACKET_SIZES[opcode];
			} else {
				/*
				 * We need to wait for more data.
				 */
				return;
			}
		}
		
		/*
		 * If the packet is variable-length.
		 */
		if (size == -1) {
			/*
			 * Check if the size can be read.
			 */
			if (in.readableBytes() >= 1) {
				/*
				 * Read the packet size and cache it.
				 */
				size = in.readByte() & 0xFF;
			} else {
				/*
				 * We need to wait for more data.
				 */
				return;
			}
		}
		
		/*
		 * If the packet payload (data) can be read.
		 */
		if (in.readableBytes() >= size) {
			/*
			 * Read it.
			 */
			ByteBuf payload = in.readBytes(size);
			/*
			 * Produce and write the packet object.
			 */
			out.add(new Packet(opcode, Type.FIXED, payload));
			opcode = -1;
			size = -1;
		}
		
		/*
		 * We need to wait for more data.
		 */
		return;
	}

}
