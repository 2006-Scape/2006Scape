package com.rs2.net;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.timeout.ReadTimeoutException;

import com.rs2.game.players.Client;

public class ConnectionHandler extends SimpleChannelHandler {
	
	private Session session = null;
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		// TODO Auto-generated method stub 
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		if (e.getMessage() instanceof Client) {
			session.setClient((Client) e.getMessage());
		} else if (e.getMessage() instanceof Packet) {
			if (session.getClient() != null) {
				Packet p = (Packet) e.getMessage();
				int packetType = p.getOpcode();
				int packetSize = p.getLength();
				byte[] buffer = p.getPayload().array();
				session.getClient().queueMessage(new GamePacket(packetType, buffer, true));
			}
		}
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		if (session == null) {
			session = new Session(ctx.getChannel());
			if (!HostList.getHostList().add(session)) {
				ctx.getChannel().close();
			} else {
				session.setInList(true);
			}
		}
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		if (session != null) {
			HostList.getHostList().remove(session);
			Client client = session.getClient();
			if (client != null) {
				client.disconnected = true;
			}
			session = null;
		}
	}

}
