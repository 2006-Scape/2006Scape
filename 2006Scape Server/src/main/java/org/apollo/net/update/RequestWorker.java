package org.apollo.net.update;

import io.netty.channel.Channel;

import java.io.IOException;

/**
 * The base class for request workers.
 *
 * @author Graham
 * @param <T> The type of request.
 * @param <P> The type of provider.
 */
public abstract class RequestWorker<T, P> implements Runnable {

	/**
	 * The update dispatcher.
	 */
	private final UpdateDispatcher dispatcher;

	/**
	 * The resource provider.
	 */
	private final P provider;

	/**
	 * A flag indicating if the worker should be running.
	 */
	private boolean running = true;

	/**
	 * Creates the request worker with the specified file system.
	 *
	 * @param dispatcher The update dispatcher.
	 * @param provider The resource provider.
	 */
	public RequestWorker(UpdateDispatcher dispatcher, P provider) {
		this.provider = provider;
		this.dispatcher = dispatcher;
	}

	/**
	 * Gets the next request.
	 *
	 * @param dispatcher The dispatcher.
	 * @return The next request.
	 * @throws InterruptedException If the thread is interrupted.
	 */
	protected abstract ChannelRequest<T> nextRequest(UpdateDispatcher dispatcher) throws InterruptedException;

	@Override
	public final void run() {
		while (true) {
			synchronized (this) {
				if (!running) {
					break;
				}
			}

			ChannelRequest<T> request;
			try {
				request = nextRequest(dispatcher);
			} catch (InterruptedException e) {
				continue;
			}

			Channel channel = request.getChannel();

			try {
				service(provider, channel, request.getRequest());
			} catch (IOException e) {
				e.printStackTrace();
				channel.close();
			}
		}
	}

	/**
	 * Services a request.
	 *
	 * @param provider The resource provider.
	 * @param channel The channel.
	 * @param request The request to service.
	 * @throws IOException If an I/O error occurs.
	 */
	protected abstract void service(P provider, Channel channel, T request) throws IOException;

	/**
	 * Stops this worker. The worker's thread may need to be interrupted.
	 */
	public final void stop() {
		synchronized (this) {
			running = false;
		}
	}

}