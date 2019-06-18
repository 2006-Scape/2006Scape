package org.apollo.jagcached.dispatch;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;


import org.apollo.jagcached.net.jaggrab.JagGrabRequest;
import org.apollo.jagcached.net.ondemand.OnDemandRequest;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;

/**
 * A class which dispatches requests to worker threads.
 * @author Graham
 */
public final class RequestDispatcher {
	
	/**
	 * The maximum size of a queue before requests are rejected.
	 */
	private static final int MAXIMUM_QUEUE_SIZE = 1024;
	
	/**
	 * A queue for pending 'on-demand' requests.
	 */
	private static final BlockingQueue<ChannelRequest<OnDemandRequest>> onDemandQueue = new PriorityBlockingQueue<ChannelRequest<OnDemandRequest>>();

	/**
	 * A queue for pending JAGGRAB requests.
	 */
	private static final BlockingQueue<ChannelRequest<JagGrabRequest>> jagGrabQueue = new LinkedBlockingQueue<ChannelRequest<JagGrabRequest>>();
	
	/**
	 * A queue for pending HTTP requests.
	 */
	private static final BlockingQueue<ChannelRequest<HttpRequest>> httpQueue = new LinkedBlockingQueue<ChannelRequest<HttpRequest>>();
	
	/**
	 * Gets the next 'on-demand' request from the queue, blocking if none are
	 * available.
	 * @return The 'on-demand' request.
	 * @throws InterruptedException if the thread is interrupted.
	 */
	static ChannelRequest<OnDemandRequest> nextOnDemandRequest() throws InterruptedException {
		return onDemandQueue.take();
	}
	
	/**
	 * Gets the next JAGGRAB request from the queue, blocking if none are
	 * available.
	 * @return The JAGGRAB request.
	 * @throws InterruptedException if the thread is interrupted.
	 */
	static ChannelRequest<JagGrabRequest> nextJagGrabRequest() throws InterruptedException {
		return jagGrabQueue.take();
	}
	
	/**
	 * Gets the next HTTP request from the queue, blocking if none are
	 * available.
	 * @return The HTTP request.
	 * @throws InterruptedException if the thread is interrupted.
	 */
	static ChannelRequest<HttpRequest> nextHttpRequest() throws InterruptedException {
		return httpQueue.take();
	}
	
	/**
	 * Dispatches an 'on-demand' request.
	 * @param channel The channel.
	 * @param request The request.
	 */
	public static void dispatch(Channel channel, OnDemandRequest request) {
		if (onDemandQueue.size() >= MAXIMUM_QUEUE_SIZE) {
			channel.close();
		}
		onDemandQueue.add(new ChannelRequest<OnDemandRequest>(channel, request));
	}

	/**
	 * Dispatches a JAGGRAB request.
	 * @param channel The channel.
	 * @param request The request.
	 */
	public static void dispatch(Channel channel, JagGrabRequest request) {
		if (jagGrabQueue.size() >= MAXIMUM_QUEUE_SIZE) {
			channel.close();
		}
		jagGrabQueue.add(new ChannelRequest<JagGrabRequest>(channel, request));
	}

	/**
	 * Dispatches a HTTP request.
	 * @param channel The channel.
	 * @param request The request.
	 */
	public static void dispatch(Channel channel, HttpRequest request) {
		if (httpQueue.size() >= MAXIMUM_QUEUE_SIZE) {
			channel.close();
		}
		httpQueue.add(new ChannelRequest<HttpRequest>(channel, request));
	}
	
	/**
	 * Default private constructor to prevent instantiation.
	 */
	private RequestDispatcher() {
		
	}

}
