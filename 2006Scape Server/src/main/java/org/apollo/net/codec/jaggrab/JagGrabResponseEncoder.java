package org.apollo.net.codec.jaggrab;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * A {@link MessageToMessageEncoder} for the JAGGRAB protocol.
 *
 * @author Graham
 */
public final class JagGrabResponseEncoder extends MessageToMessageEncoder<JagGrabResponse> {

	@Override
	protected void encode(ChannelHandlerContext ctx, JagGrabResponse response, List<Object> out) {
		out.add(response.getFileData());
	}

}