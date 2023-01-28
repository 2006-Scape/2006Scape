package org.apollo.net.codec.handshake;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.logging.Logger;

import org.apollo.net.codec.login.LoginDecoder;
import org.apollo.net.codec.login.LoginEncoder;
import org.apollo.net.codec.update.UpdateDecoder;
import org.apollo.net.codec.update.UpdateEncoder;

/**
 * A {@link ByteToMessageDecoder} which decodes the handshake and makes changes to the pipeline as appropriate for the
 * selected service.
 *
 * @author Graham
 */
public final class HandshakeDecoder extends ByteToMessageDecoder {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(HandshakeDecoder.class.getName());

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
		if (!buffer.isReadable()) {
			return;
		}

		int id = buffer.readUnsignedByte();

		switch (id) {
			case HandshakeConstants.SERVICE_GAME:
				ctx.pipeline().addFirst("loginEncoder", new LoginEncoder());
				ctx.pipeline().addAfter("handshakeDecoder", "loginDecoder", new LoginDecoder());
				break;

			case HandshakeConstants.SERVICE_UPDATE:
				ctx.pipeline().addFirst("updateEncoder", new UpdateEncoder());
				ctx.pipeline().addBefore("handler", "updateDecoder", new UpdateDecoder());

				ByteBuf buf = ctx.alloc().buffer(8).writeLong(0);
				ctx.channel().writeAndFlush(buf);
				break;

			default:
				ByteBuf data = buffer.readBytes(buffer.readableBytes());
				logger.info(String.format("Unexpected handshake request received: %d data: %s", id, data.toString()));
				return;
		}

		ctx.pipeline().remove(this);
		out.add(new HandshakeMessage(id));
	}

}