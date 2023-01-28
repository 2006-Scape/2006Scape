package org.apollo.net.update;

import io.netty.channel.Channel;

/**
 * A {@link ChannelRequest} with a {@link Comparable} request type.
 *
 * @author Major
 *
 * @param <T> The type of request.
 */
public final class ComparableChannelRequest<T extends Comparable<T>> extends ChannelRequest<T> implements Comparable<ComparableChannelRequest<T>> {

	/**
	 * Creates the ComparableChannelRequest.
	 *
	 * @param channel The {@link Channel} making the request.
	 * @param request The request.
	 */
	public ComparableChannelRequest(Channel channel, T request) {
		super(channel, request);
	}

	@Override
	public int compareTo(ComparableChannelRequest<T> o) {
		return request.compareTo(o.request);
	}

}