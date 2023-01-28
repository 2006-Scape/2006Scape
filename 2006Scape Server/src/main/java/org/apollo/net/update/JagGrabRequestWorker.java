package org.apollo.net.update;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.net.codec.jaggrab.JagGrabRequest;
import org.apollo.net.codec.jaggrab.JagGrabResponse;
import org.apollo.net.update.resource.ResourceProvider;
import org.apollo.net.update.resource.VirtualResourceProvider;

/**
 * A worker which services JAGGRAB requests.
 *
 * @author Graham
 */
public final class JagGrabRequestWorker extends RequestWorker<JagGrabRequest, ResourceProvider> {

	/**
	 * Creates the JAGGRAB request worker.
	 *
	 * @param dispatcher The dispatcher.
	 * @param fs The file system.
	 */
	public JagGrabRequestWorker(UpdateDispatcher dispatcher, IndexedFileSystem fs) {
		super(dispatcher, new VirtualResourceProvider(fs));
	}

	@Override
	protected ChannelRequest<JagGrabRequest> nextRequest(UpdateDispatcher dispatcher) throws InterruptedException {
		return dispatcher.nextJagGrabRequest();
	}

	@Override
	protected void service(ResourceProvider provider, Channel channel, JagGrabRequest request) throws IOException {
		Optional<ByteBuffer> buffer = provider.get(request.getFilePath());

		if (buffer.isPresent()) {
			ByteBuf wrapped = Unpooled.wrappedBuffer(buffer.get());
			channel.writeAndFlush(new JagGrabResponse(wrapped)).addListener(ChannelFutureListener.CLOSE);
		} else {
			channel.close();
		}
	}

}