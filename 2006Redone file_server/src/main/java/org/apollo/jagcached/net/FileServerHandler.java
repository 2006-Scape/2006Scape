package org.apollo.jagcached.net;

import java.util.logging.Level;
import java.util.logging.Logger;


import org.apollo.jagcached.FileServer;
import org.apollo.jagcached.dispatch.RequestDispatcher;
import org.apollo.jagcached.net.jaggrab.JagGrabRequest;
import org.apollo.jagcached.net.ondemand.OnDemandRequest;
import org.apollo.jagcached.net.service.ServiceRequest;
import org.apollo.jagcached.net.service.ServiceResponse;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

/**
 * An {@link IdleStateAwareChannelUpstreamHandler} for the {@link FileServer}.
 * @author Graham
 */
public final class FileServerHandler extends IdleStateAwareChannelUpstreamHandler {
	
	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(FileServerHandler.class.getName());
	
	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
		e.getChannel().close();
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Object msg = e.getMessage();		
		if (msg instanceof ServiceRequest) {
			ServiceRequest request = (ServiceRequest) msg;
			if (request.getId() != ServiceRequest.SERVICE_ONDEMAND) {
				e.getChannel().close();
			} else {
				e.getChannel().write(new ServiceResponse());
			}
		} else if (msg instanceof OnDemandRequest) {
			RequestDispatcher.dispatch(e.getChannel(), (OnDemandRequest) msg);
		} else if (msg instanceof JagGrabRequest) {
			RequestDispatcher.dispatch(e.getChannel(), (JagGrabRequest) msg);
		} else if (msg instanceof HttpRequest) {
			RequestDispatcher.dispatch(e.getChannel(), (HttpRequest) msg);
		} else {
			throw new Exception("unknown message type");
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.log(Level.SEVERE, "Exception occured, closing channel...", e.getCause());
		e.getChannel().close();
	}

}
