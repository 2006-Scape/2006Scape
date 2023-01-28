package org.apollo.net.codec.jaggrab;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * A {@link MessageToMessageDecoder} for the JAGGRAB protocol.
 *
 * @author Graham
 */
public final class JagGrabRequestDecoder extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext ctx, String request, List<Object> out) {
		if (request.startsWith("JAGGRAB /")) {
			String filePath = request.substring(8).trim();
			out.add(new JagGrabRequest(filePath));
		} else {
			throw new IllegalArgumentException("Corrupted request line.");
		}
	}

}