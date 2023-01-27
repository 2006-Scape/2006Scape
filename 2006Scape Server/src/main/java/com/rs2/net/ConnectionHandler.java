package com.rs2.net;

import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Old class that was used with netty 4 impl. This is no longer needed but will be a reference for re-adding the HostList stuff into the ApolloHandler.
 * @author Advocatus
 *
 */
public class ConnectionHandler extends ChannelInboundHandlerAdapter {
	
////	public static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("session");
//
//	private Session session = null;
//	
////	@Override
////	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
////		if (session == null) {
////			session = new Session(ctx.getChannel());
////			if (!HostList.getHostList().add(session)) {
////				ctx.getChannel().close();
////			} else {
////				session.setInList(true);
////			}
////		}
////	}
//	
//	
//	@Override
//	public void channelInactive(ChannelHandlerContext ctx) {
//		if (session != null) {
//	//		HostList.getHostList().remove(session);
//			Client client = session.getClient();
//			if (client != null) {
//				client.disconnected = true;
//			}
//			session = null;
//		}
//		Channel channel = ctx.channel();
//		channel.close();
//	}
//
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
//		if (!e.getMessage().contains("An existing connection was forcibly closed by the remote host")) {
//			e.printStackTrace();
//		//	logger.log(Level.WARNING, "Exception occured for channel: " + ctx.channel() + ", closing...", e);
//		}
//		ctx.channel().close();
//	}
//
//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
////		System.out.println(message.getClass());
//		if (session == null) {
//			session = new Session(ctx.channel());
////			if (!HostList.getHostList().add(session)) {
////				ctx.channel().close();
////			} else {
////				session.setInList(true);
////			}
//		}
//		if (message instanceof Client) {
//			session.setClient((Client) message);
//		} else if (message instanceof Packet) {
//			if (session.getClient() != null) {
//				session.getClient().queueMessage((Packet) message);
//			}
//		}		
//	}
//	
//	@Override
//	public void channelReadComplete(ChannelHandlerContext ctx) {
//		ctx.flush();
//	}
}
