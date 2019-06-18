package org.apollo.jagcached.net.jaggrab;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * A {@link OneToOneEncoder} for the JAGGRAB protocol.
 * @author Graham
 */
public final class JagGrabResponseEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel c, Object msg) throws Exception {
		if (msg instanceof JagGrabResponse) {
			JagGrabResponse resp = (JagGrabResponse) msg;
			return resp.getFileData();
		}
		return msg;
	}

}
