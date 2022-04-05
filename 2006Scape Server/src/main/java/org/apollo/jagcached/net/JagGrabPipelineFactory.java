package org.apollo.jagcached.net;

import java.nio.charset.Charset;


import org.apollo.jagcached.net.jaggrab.JagGrabRequestDecoder;
import org.apollo.jagcached.net.jaggrab.JagGrabResponseEncoder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.Timer;

/**
 * A {@link ChannelPipelineFactory} for the JAGGRAB protocol.
 * @author Graham
 */
public final class JagGrabPipelineFactory implements ChannelPipelineFactory {

	/**
	 * The maximum length of a request, in bytes.
	 */
	private static final int MAX_REQUEST_LENGTH = 8192;
	
	/**
	 * The character set used in the request.
	 */
	private static final Charset JAGGRAB_CHARSET = Charset.forName("US-ASCII");
	
	/**
	 * A buffer with two line feed (LF) characters in it.
	 */
	private static final ChannelBuffer DOUBLE_LINE_FEED_DELIMITER = ChannelBuffers.buffer(2);
	
	/**
	 * Populates the double line feed buffer.
	 */
	static {
		DOUBLE_LINE_FEED_DELIMITER.writeByte(10);
		DOUBLE_LINE_FEED_DELIMITER.writeByte(10);
	}
	
	/**
	 * The file server event handler.
	 */
	private final FileServerHandler handler;
	
	/**
	 * The timer used for idle checking.
	 */
	private final Timer timer;
	
	/**
	 * Creates a {@code JAGGRAB} pipeline factory.
	 * @param handler The file server event handler.
	 * @param timer The timer used for idle checking.
	 */
	public JagGrabPipelineFactory(FileServerHandler handler, Timer timer) {
		this.handler = handler;
		this.timer = timer;
	}
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		
		// decoders
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(MAX_REQUEST_LENGTH, DOUBLE_LINE_FEED_DELIMITER));
		pipeline.addLast("string-decoder", new StringDecoder(JAGGRAB_CHARSET));
		pipeline.addLast("jaggrab-decoder", new JagGrabRequestDecoder());
		
		// encoders
		pipeline.addLast("jaggrab-encoder", new JagGrabResponseEncoder());
		
		// handler
		pipeline.addLast("timeout", new IdleStateHandler(timer, NetworkConstants.IDLE_TIME, 0, 0));
		pipeline.addLast("handler", handler);
		
		return pipeline;
	}

}
