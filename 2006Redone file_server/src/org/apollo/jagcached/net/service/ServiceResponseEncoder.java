package org.apollo.jagcached.net.service;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * A {@link OneToOneEncoder} which encodes {@link ServiceResponse} messages.
 * @author Graham
 */
public final class ServiceResponseEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel c, Object msg) throws Exception {
		if (msg instanceof ServiceResponse) {
			ChannelBuffer buf = ChannelBuffers.buffer(8);
			buf.writeLong(0);
			return buf;
		}
		return msg;
	}

}
