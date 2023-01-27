//package com.rs2.net;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToByteEncoder;
//
//public class RS2ProtocolEncoder extends MessageToByteEncoder<Packet> {
//
//	/**
//	 * Only CodecFactory can create us.
//	 */
//	public RS2ProtocolEncoder() {
//	}
//
//	@Override
//	/**
//	 * Encodes a message.
//	 * @param session
//	 * @param message
//	 * @param out
//	 */
//	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
//		out.writeBytes(msg.getPayload());//return ((Packet) object).getPayload(); Do we need to go bldr->packet?
//	}
//
//}
