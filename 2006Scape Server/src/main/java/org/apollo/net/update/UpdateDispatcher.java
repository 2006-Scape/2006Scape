package org.apollo.net.update;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.apollo.net.codec.jaggrab.JagGrabRequest;
import org.apollo.net.codec.update.OnDemandRequest;

/**
 * Dispatches update requests to worker threads.
 *
 * @author Graham
 */
public final class UpdateDispatcher {

	/**
	 * The maximum size of a queue before requests are rejected.
	 */
	private static final int MAXIMUM_QUEUE_SIZE = 1024;

	/**
	 * A queue for pending 'on-demand' requests.
	 */
	private final BlockingQueue<ComparableChannelRequest<OnDemandRequest>> demand = new PriorityBlockingQueue<>();

	/**
	 * A queue for pending HTTP requests.
	 */
	private final BlockingQueue<ChannelRequest<HttpRequest>> http = new LinkedBlockingQueue<>();

	/**
	 * A queue for pending JAGGRAB requests.
	 */
	private final BlockingQueue<ChannelRequest<JagGrabRequest>> jaggrab = new LinkedBlockingQueue<>();

	/**
	 * Dispatches a HTTP request.
	 *
	 * @param channel The channel.
	 * @param request The request.
	 */
	public void dispatch(Channel channel, HttpRequest request) {
		if (http.size() >= MAXIMUM_QUEUE_SIZE) {
			channel.close();
		}
		http.add(new ChannelRequest<>(channel, request));
	}

	/**
	 * Dispatches a JAGGRAB request.
	 *
	 * @param channel The channel.
	 * @param request The request.
	 */
	public void dispatch(Channel channel, JagGrabRequest request) {
		if (jaggrab.size() >= MAXIMUM_QUEUE_SIZE) {
			channel.close();
		}
		jaggrab.add(new ChannelRequest<>(channel, request));
	}

	/**
	 * Dispatches an 'on-demand' request.
	 *
	 * @param channel The channel.
	 * @param request The request.
	 */
	public void dispatch(Channel channel, OnDemandRequest request) {
		if (demand.size() >= MAXIMUM_QUEUE_SIZE) {
			channel.close();
		}
		demand.add(new ComparableChannelRequest<>(channel, request));
	}

	/**
	 * Gets the next HTTP request from the queue, blocking if none are available.
	 *
	 * @return The HTTP request.
	 * @throws InterruptedException If the thread is interrupted.
	 */
	ChannelRequest<HttpRequest> nextHttpRequest() throws InterruptedException {
		return http.take();
	}

	/**
	 * Gets the next JAGGRAB request from the queue, blocking if none are available.
	 *
	 * @return The JAGGRAB request.
	 * @throws InterruptedException If the thread is interrupted.
	 */
	ChannelRequest<JagGrabRequest> nextJagGrabRequest() throws InterruptedException {
		return jaggrab.take();
	}

	/**
	 * Gets the next 'on-demand' request from the queue, blocking if none are available.
	 *
	 * @return The 'on-demand' request.
	 * @throws InterruptedException If the thread is interrupted.
	 */
	ChannelRequest<OnDemandRequest> nextOnDemandRequest() throws InterruptedException {
		return demand.take();
	}

}