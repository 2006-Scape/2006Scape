package org.apollo.jagcached.dispatch;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;

import org.apollo.jagcached.fs.IndexedFileSystem;
import org.apollo.jagcached.resource.CombinedResourceProvider;
import org.apollo.jagcached.resource.HypertextResourceProvider;
import org.apollo.jagcached.resource.ResourceProvider;
import org.apollo.jagcached.resource.VirtualResourceProvider;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

/**
 * A worker which services HTTP requests.
 * @author Graham
 */
public final class HttpRequestWorker extends RequestWorker<HttpRequest, ResourceProvider> {

	/**
	 * The value of the server header.
	 */
	private static final String SERVER_IDENTIFIER = "JAGeX/3.1";
	
	/**
	 * The directory with web files.
	 */
	private static final File WWW_DIRECTORY = new File("./data/www/");

	/**
	 * The default character set.
	 */
	private static final Charset CHARACTER_SET = Charset.forName("ISO-8859-1");

	/**
	 * Creates the HTTP request worker.
	 * @param fs The file system.
	 */
	public HttpRequestWorker(IndexedFileSystem fs) {
		super(new CombinedResourceProvider(new VirtualResourceProvider(fs), new HypertextResourceProvider(WWW_DIRECTORY)));
	}

	@Override
	protected ChannelRequest<HttpRequest> nextRequest() throws InterruptedException {
		return RequestDispatcher.nextHttpRequest();
	}

	@Override
	protected void service(ResourceProvider provider, Channel channel, HttpRequest request) throws IOException {
		String path = request.getUri();
		ByteBuffer buf = provider.get(path);
		
		ChannelBuffer wrappedBuf;
		HttpResponseStatus status = HttpResponseStatus.OK;
		
		String mimeType = getMimeType(request.getUri());
		
		if (buf == null) {
			status = HttpResponseStatus.NOT_FOUND;
			wrappedBuf = createErrorPage(status, "The page you requested could not be found.");
			mimeType = "text/html";
		} else {
			wrappedBuf = ChannelBuffers.wrappedBuffer(buf);
		}
		
		HttpResponse resp = new DefaultHttpResponse(request.getProtocolVersion(), status);
		
		resp.setHeader("Date", new Date());
		resp.setHeader("Server", SERVER_IDENTIFIER);
		resp.setHeader("Content-type", mimeType + ", charset=" + CHARACTER_SET.name());
		resp.setHeader("Cache-control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Expires", new Date(0));
		resp.setHeader("Connection", "close");
		resp.setHeader("Content-length", wrappedBuf.readableBytes());
		resp.setChunked(false);
		resp.setContent(wrappedBuf);
		
		channel.write(resp).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * Gets the MIME type of a file by its name.
	 * @param name The file name.
	 * @return The MIME type.
	 */
	private String getMimeType(String name) {
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
		return "application/octect-stream";
	}

	/**
	 * Creates an error page.
	 * @param status The HTTP status.
	 * @param description The error description.
	 * @return The error page as a buffer.
	 */
	private ChannelBuffer createErrorPage(HttpResponseStatus status, String description) {
		String title = status.getCode() + " " + status.getReasonPhrase();
		
		StringBuilder bldr = new StringBuilder();
		
		bldr.append("<!DOCTYPE html><html><head><title>");
		bldr.append(title);
		bldr.append("</title></head><body><h1>");
		bldr.append(title);
		bldr.append("</h1><p>");
		bldr.append(description);
		bldr.append("</p><hr /><address>");
		bldr.append(SERVER_IDENTIFIER);
		bldr.append(" Server</address></body></html>");
		
		return ChannelBuffers.copiedBuffer(bldr.toString(), Charset.defaultCharset());
	}

}
