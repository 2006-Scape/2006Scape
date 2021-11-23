package org.apollo.jagcached.net;


import org.apollo.jagcached.net.ondemand.OnDemandRequestDecoder;
import org.apollo.jagcached.net.ondemand.OnDemandResponseEncoder;
import org.apollo.jagcached.net.service.ServiceRequestDecoder;
import org.apollo.jagcached.net.service.ServiceResponseEncoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.Timer;

/**
 * A {@link ChannelPipelineFactory} for the 'on-demand' protocol.
 * @author Graham
 */
public final class OnDemandPipelineFactory implements ChannelPipelineFactory {

	/**
	 * The file server event handler.
	 */
	private final FileServerHandler handler;
	
	/**
	 * The timer used for idle checking.
	 */
	private final Timer timer;
	
	/**
	 * Creates an 'on-demand' pipeline factory.
	 * @param handler The file server event handler.
	 * @param timer The timer used for idle checking.
	 */
	public OnDemandPipelineFactory(FileServerHandler handler, Timer timer) {
		this.handler = handler;
		this.timer = timer;
	}
	
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		
		// decoders
		pipeline.addLast("serviceDecoder", new ServiceRequestDecoder());
		pipeline.addLast("decoder", new OnDemandRequestDecoder());
		
		// encoders
		pipeline.addLast("serviceEncoder", new ServiceResponseEncoder());
		pipeline.addLast("encoder", new OnDemandResponseEncoder());
		
		// handler
		pipeline.addLast("timeout", new IdleStateHandler(timer, NetworkConstants.IDLE_TIME, 0, 0));
		pipeline.addLast("handler", handler);
		
		return pipeline;
	}

}
