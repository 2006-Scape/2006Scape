package org.apollo.net.update;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

import org.apollo.cache.IndexedFileSystem;
import org.apollo.net.update.resource.CombinedResourceProvider;
import org.apollo.net.update.resource.HypertextResourceProvider;
import org.apollo.net.update.resource.ResourceProvider;
import org.apollo.net.update.resource.VirtualResourceProvider;

import com.google.common.base.Charsets;

/**
 * A worker which services HTTP requests.
 *
 * @author Graham
 */
public final class HttpRequestWorker extends RequestWorker<HttpRequest, ResourceProvider> {

	/**
	 * The default character set.
	 */
	private static final Charset CHARACTER_SET = Charsets.ISO_8859_1;

	/**
	 * The value of the server header.
	 */
	private static final String SERVER_IDENTIFIER = "JAGeX/3.1";

	/**
	 * The directory with web files.
	 */
	private static final Path WWW_DIRECTORY = Paths.get("data/www");

	/**
	 * Creates the HTTP request worker.
	 *
	 * @param dispatcher The dispatcher.
	 * @param fs The file system.
	 */
	public HttpRequestWorker(UpdateDispatcher dispatcher, IndexedFileSystem fs) {
		super(dispatcher, new CombinedResourceProvider(new VirtualResourceProvider(fs), new HypertextResourceProvider(WWW_DIRECTORY)));
	}

	/**
	 * Creates an error page.
	 *
	 * @param status The HTTP status.
	 * @param description The error description.
	 * @return The error page as a buffer.
	 */
	private static ByteBuf createErrorPage(HttpResponseStatus status, String description) {
		String title = status.code() + " " + status.reasonPhrase();
		StringBuilder builder = new StringBuilder("<!DOCTYPE html><html><head><title>");

		builder.append(title);
		builder.append("</title></head><body><h1>");
		builder.append(title);
		builder.append("</h1><p>");
		builder.append(description);
		builder.append("</p><hr /><address>");
		builder.append(SERVER_IDENTIFIER);
		builder.append(" Server</address></body></html>");

		return Unpooled.copiedBuffer(builder.toString(), Charset.defaultCharset());
	}

	/**
	 * Gets the MIME type of a file by its name.
	 *
	 * @param name The file name.
	 * @return The MIME type.
	 */
	private static String getMimeType(String name) {
		if (name.endsWith("/")) {
			name = name.concat("index.html");
		}

		if (name.endsWith(".htm") || name.endsWith(".html")) {
			return "text/html";
		} else if (name.endsWith(".css")) {
			return "text/css";
		} else if (name.endsWith(".js")) {
			return "text/javascript";
		} else if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (name.endsWith(".gif")) {
			return "image/gif";
		} else if (name.endsWith(".png")) {
			return "image/png";
		} else if (name.endsWith(".txt")) {
			return "text/plain";
		}

		return "application/octet-stream";
	}

	@Override
	protected ChannelRequest<HttpRequest> nextRequest(UpdateDispatcher dispatcher) throws InterruptedException {
		return dispatcher.nextHttpRequest();
	}

	@Override
	protected void service(ResourceProvider provider, Channel channel, HttpRequest request) throws IOException {
		String path = request.getUri();
		Optional<ByteBuffer> buf = provider.get(path);

		HttpResponseStatus status = HttpResponseStatus.OK;
		String mime = getMimeType(request.getUri());

		if (!buf.isPresent()) {
			status = HttpResponseStatus.NOT_FOUND;
			mime = "text/html";
		}

		ByteBuf wrapped = buf.isPresent() ? Unpooled.wrappedBuffer(buf.get()) : createErrorPage(status, "The page you requested could not be found.");

		HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), status);

		response.headers().set("Date", new Date());
		response.headers().set("Server", SERVER_IDENTIFIER);
		response.headers().set("Content-type", mime + ", charset=" + CHARACTER_SET.name());
		response.headers().set("Cache-control", "no-cache");
		response.headers().set("Pragma", "no-cache");
		response.headers().set("Expires", new Date(0));
		response.headers().set("Connection", "close");
		response.headers().set("Content-length", wrapped.readableBytes());

		channel.write(response);
		channel.writeAndFlush(wrapped).addListener(ChannelFutureListener.CLOSE);
	}

}
