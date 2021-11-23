package org.apollo.jagcached.net.service;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * A {@link FrameDecoder} which decodes {@link ServiceRequest} messages.
 * @author Graham
 */
public final class ServiceRequestDecoder extends FrameDecoder {
	
	/**
	 * Creates the decoder, enabling the 'unfold' mechanism.
	 */
	public ServiceRequestDecoder() {
		super(true);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel c, ChannelBuffer buf) throws Exception {
		if (buf.readable()) {
			ServiceRequest request = new ServiceRequest(buf.readUnsignedByte());
						
			ChannelPipeline pipeline = ctx.getPipeline();
			pipeline.remove(this);
			
			if (buf.readable()) {
				return new Object[] { request, buf.readBytes(buf.readableBytes()) };
			} else {
				return request;
			}
		}
		return null;
	}

}
