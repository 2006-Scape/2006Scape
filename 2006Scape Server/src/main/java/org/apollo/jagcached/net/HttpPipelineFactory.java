package org.apollo.jagcached.net;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.Timer;

/**
 * A {@link ChannelPipelineFactory} for the HTTP protocol.
 * @author Graham
 */
public final class HttpPipelineFactory implements ChannelPipelineFactory {

	/**
	 * The maximum length of a request, in bytes.
	 */
	private static final int MAX_REQUEST_LENGTH = 8192;
	
	/**
	 * The file server event handler.
	 */
	private final FileServerHandler handler;
	
	/**
	 * The timer used for idle checking.
	 */
	private final Timer timer;
	
	/**
	 * Creates the HTTP pipeline factory.
	 * @param handler The file server event handler.
	 * @param timer The timer used for idle checking.
	 */
	public HttpPipelineFactory(FileServerHandler handler, Timer timer) {
		this.handler = handler;
		this.timer = timer;
	}
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		
		// decoders
		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("chunker", new HttpChunkAggregator(MAX_REQUEST_LENGTH));
		
		// encoders
		pipeline.addLast("encoder", new HttpResponseEncoder());
		
		// handler
		pipeline.addLast("timeout", new IdleStateHandler(timer, NetworkConstants.IDLE_TIME, 0, 0));
		pipeline.addLast("handler", handler);
		
		return pipeline;
	}

}
