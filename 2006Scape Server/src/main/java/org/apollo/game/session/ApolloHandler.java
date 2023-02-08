package org.apollo.game.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.net.codec.handshake.HandshakeConstants;
import org.apollo.net.codec.handshake.HandshakeMessage;
import org.apollo.net.codec.jaggrab.JagGrabRequest;

import com.rs2.Constants;

/**
 * An implementation of {@link ChannelInboundHandlerAdapter} which handles incoming upstream events from Netty.
 *
 * @author Graham
 */
@Sharable
public final class ApolloHandler extends ChannelInboundHandlerAdapter {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(ApolloHandler.class.getName());

	/**
	 * The {@link Session} {@link AttributeKey}.
	 */
	public static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("session");

	/**
	 * Creates the Apollo event handler.
	 *
	 * @param context The server context.
	 */
	public ApolloHandler() {
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		Channel channel = ctx.channel();
		Session session = channel.attr(ApolloHandler.SESSION_KEY).getAndRemove();
		if (session != null) {
			session.destroy();
		}
		logger.fine("Channel disconnected: " + channel);
		channel.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
		if (!e.getMessage().contains("An existing connection was forcibly closed by the remote host")) {
			logger.log(Level.WARNING, "Exception occured for channel: " + ctx.channel() + ", closing...", e);
		}
		ctx.channel().close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
		Channel channel = ctx.channel();
		Attribute<Session> attribute = channel.attr(ApolloHandler.SESSION_KEY);
		Session session = attribute.get();

		if (message instanceof HttpRequest || message instanceof JagGrabRequest) {
			session = new UpdateSession(channel);
		}

		if (session != null) {
			session.messageReceived(message);
			return;
		}

		// TODO: Perhaps let HandshakeMessage implement Message to remove this explicit check
		if (message instanceof HandshakeMessage) {
			HandshakeMessage handshakeMessage = (HandshakeMessage) message;

			switch (handshakeMessage.getServiceId()) {
			case HandshakeConstants.SERVICE_GAME:
					attribute.set(new LoginSession(channel));
					break;

				case HandshakeConstants.SERVICE_UPDATE:
					if(!Constants.FILE_SERVER) {
						ctx.close();
						return;
					}
					attribute.set(new UpdateSession(channel));
					break;
			}
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

}