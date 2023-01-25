package com.rs2.net;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.rs2.util.ISAACRandomGen;

public class RS2ProtocolDecoder extends CumulativeProtocolDecoder {

	private final ISAACRandomGen isaac;
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
	protected RS2ProtocolDecoder(ISAACRandomGen isaac) {
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
	protected boolean doDecode(IoSession session, ByteBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		synchronized (session) {
			/*
			 * Fetch the ISAAC cipher for this session.
			 */
			// ISAACRandomGen inCipher = ((Player)
			// session.getAttribute("player")).getInStreamDecryption();

			/*
			 * Fetch any cached opcodes and sizes, reset to -1 if not present.
			 */
			int opcode = (Integer) session.getAttribute("opcode");
			int size = (Integer) session.getAttribute("size");

			/*
			 * If the opcode is not present.
			 */
			if (opcode == -1) {
				/*
				 * Check if it can be read.
				 */
				if (in.remaining() >= 1) {
					/*
					 * Read and decrypt the opcode.
					 */
					opcode = in.get() & 0xFF;
					opcode = opcode - isaac.getNextKey() & 0xFF;

					/*
					 * Find the packet size.
					 */
					size = RS2ProtocolDecoder.PACKET_SIZES[opcode];

					/*
					 * Set the cached opcode and size.
					 */
					session.setAttribute("opcode", opcode);
					session.setAttribute("size", size);
				} else {
					/*
					 * We need to wait for more data.
					 */
					return false;
				}
			}

			/*
			 * If the packet is variable-length.
			 */
			if (size == -1) {
				/*
				 * Check if the size can be read.
				 */
				if (in.remaining() >= 1) {
					/*
					 * Read the packet size and cache it.
					 */
					size = in.get() & 0xFF;
					session.setAttribute("size", size);
				} else {
					/*
					 * We need to wait for more data.
					 */
					return false;
				}
			}

			/*
			 * If the packet payload (data) can be read.
			 */
			if (in.remaining() >= size) {
				/*
				 * Read it.
				 */
				byte[] data = new byte[size];
				in.get(data);
				ByteBuffer payload = ByteBuffer.allocate(data.length);
				payload.put(data);
				payload.flip();

				/*
				 * Produce and write the packet object.
				 */
				out.write(new GamePacket(opcode, data));

				/*
				 * Reset the cached opcode and sizes.
				 */
				session.setAttribute("opcode", -1);
				session.setAttribute("size", -1);

				/*
				 * Indicate we are ready to read another packet.
				 */
				return true;
			}

			/*
			 * We need to wait for more data.
			 */
			return false;
		}
	}

	@Override
	/**
	 * Releases resources used by this decoder.
	 * @param session
	 */
	public void dispose(IoSession session) throws Exception {
		super.dispose(session);
	}

}
