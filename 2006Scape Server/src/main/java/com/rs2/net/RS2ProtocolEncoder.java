package com.rs2.net;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class RS2ProtocolEncoder extends OneToOneEncoder {

	/**
	 * Only CodecFactory can create us.
	 */
	protected RS2ProtocolEncoder() {
	}

	@Override
	/**
	 * Encodes a message.
	 * @param session
	 * @param message
	 * @param out
	 */
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object object) throws Exception {
		return ((Packet) object).getPayload();
	}

}
