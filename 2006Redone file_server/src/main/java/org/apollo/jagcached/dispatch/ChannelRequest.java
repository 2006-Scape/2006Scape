package org.apollo.jagcached.dispatch;

import org.jboss.netty.channel.Channel;

/**
 * A specialised request which contains a channel as well as the request object
 * itself.
 * @author Graham
 * @param <T> The type of request.
 */
public final class ChannelRequest<T> implements Comparable<ChannelRequest<T>> {
	
	/**
	 * The channel.
	 */
	private final Channel channel;
	
	/**
	 * The request.
	 */
	private final T request;
	
	/**
	 * Creates a new channel request.
	 * @param channel The channel.
	 * @param request The request.
	 */
	public ChannelRequest(Channel channel, T request) {
		this.channel = channel;
		this.request = request;
	}
	
	/**
	 * Gets the channel.
	 * @return The channel.
	 */
	public Channel getChannel() {
		return channel;
	}
	
	/**
	 * Gets the request.
	 * @return The request.
	 */
	public T getRequest() {
		return request;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(ChannelRequest<T> o) {
		if (request instanceof Comparable<?> && o.request instanceof Comparable<?>) {
			return ((Comparable<T>) request).compareTo(o.request);
		}
		return 0;
	}

}
