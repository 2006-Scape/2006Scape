package com.rs2.net;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class PipelineFactory extends ChannelInitializer<SocketChannel> {

//	private final ChannelInboundHandlerAdapter handler;
//
//	public PipelineFactory(ChannelInboundHandlerAdapter handler) {
//		this.handler = handler;
//	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		final ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("timeout", new ReadTimeoutHandler(10));
		pipeline.addLast("encoder", new RS2ProtocolEncoder());
		pipeline.addLast("decoder", new RS2LoginProtocolDecoder());
		pipeline.addLast("handler", new ConnectionHandler());//handler
	}

}
