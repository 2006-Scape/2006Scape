package org.apollo.jagcached;

import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rs2.Constants;
import org.apollo.game.session.ApolloHandler;
import org.apollo.net.HttpChannelInitializer;
import org.apollo.net.JagGrabChannelInitializer;
import org.apollo.net.ServiceChannelInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * The core class of the file server.
 * @author Graham
 */
public final class FileServer {
	
	/**
	 * The {@link ServerBootstrap} for the HTTP listener.
	 */
	private ServerBootstrap httpBootstrap;

	/**
	 * The {@link ServerBootstrap} for the JAGGRAB listener.
	 */
	private ServerBootstrap jaggrabBootstrap;

	/**
	 * The event loop group.
	 */
	private final EventLoopGroup loopGroup = new NioEventLoopGroup();

	/**
	 * The {@link ServerBootstrap} for the service listener.
	 */
	private final ServerBootstrap serviceBootstrap = new ServerBootstrap();
	
	
	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(FileServer.class.getName());

	
	/**
	 * The request worker pool.
	 */
	private RequestWorkerPool pool;


	/**
	 * Starts the file server.
	 * @throws Exception if an error occurs.
	 */
	public SocketAddress service = new InetSocketAddress((Constants.WORLD == 1) ? 43594 : 43596 + Constants.WORLD);

	public void start() throws Exception {
		if (!new File(Constants.FILE_SYSTEM_DIR).exists())
		{
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			System.out.println("************************************");
			System.out.println("************************************");
			System.out.println("************************************");
			System.out.println("WARNING: I could not find the data/cache folder. You are LIKELY running this in the wrong directory!");
			System.out.println("In IntelliJ, fix it by clicking \"GameEngine\" > Edit Configurations at the top of your screen");
			System.out.println("Then changing the \"Working Directory\" to be in \"2006Scape/2006Scape Server\", instead of just \"2006Scape\"");
			System.out.println("************************************");
			System.out.println("************************************");
			System.out.println("************************************");
			System.exit(1);
		}

		if(Constants.FILE_SERVER) {
			httpBootstrap = new ServerBootstrap();
			jaggrabBootstrap = new ServerBootstrap();
			pool = new RequestWorkerPool();
			logger.info("Starting workers...");
			pool.start();
		}
		logger.info("Starting services...");
		
		init();
		SocketAddress http = new InetSocketAddress(Constants.HTTP_PORT);
		SocketAddress jaggrab = new InetSocketAddress(Constants.JAGGRAB_PORT);

		bind(service, http, jaggrab);
		
		logger.info("Ready for connections.");
	}

	/**
	 * Initialises the server.
	 *
	 * @throws Exception If an error occurs.
	 */
	public void init() throws Exception {
		
		serviceBootstrap.group(loopGroup);
		if(Constants.FILE_SERVER) {
			httpBootstrap.group(loopGroup);
			jaggrabBootstrap.group(loopGroup);		
		}
		ApolloHandler handler = new ApolloHandler();

		ChannelInitializer<SocketChannel> service = new ServiceChannelInitializer(handler);
		serviceBootstrap.channel(NioServerSocketChannel.class);
		serviceBootstrap.childHandler(service);

		if(!Constants.FILE_SERVER)
			return;
		ChannelInitializer<SocketChannel> http = new HttpChannelInitializer(handler);
		httpBootstrap.channel(NioServerSocketChannel.class);
		httpBootstrap.childHandler(http);

		ChannelInitializer<SocketChannel> jaggrab = new JagGrabChannelInitializer(handler);
		jaggrabBootstrap.channel(NioServerSocketChannel.class);
		jaggrabBootstrap.childHandler(jaggrab);
	}
	
	/**
	 * Binds the server to the specified address.
	 *
	 * @param service The service address to bind to.
	 * @param http The HTTP address to bind to.
	 * @param jaggrab The JAGGRAB address to bind to.
	 * @throws BindException If the ServerBootstrap fails to bind to the SocketAddress.
	 */
	public void bind(SocketAddress service, SocketAddress http, SocketAddress jaggrab) throws IOException {
		logger.fine("Binding service listener to address: " + service + "...");
		bind(serviceBootstrap, service);
		if (Constants.FILE_SERVER) {
			try {
				logger.fine("Binding HTTP listener to address: " + http + "...");
				bind(httpBootstrap, http);
			} catch (IOException cause) {
				logger.log(Level.WARNING, "Unable to bind to HTTP - JAGGRAB will be used as a fallback.", cause);
			}
	
			logger.fine("Binding JAGGRAB listener to address: " + jaggrab + "...");
			bind(jaggrabBootstrap, jaggrab);
		}
		logger.info("Ready for connections.");
	}
	
	/**
	 * Attempts to bind the specified ServerBootstrap to the specified SocketAddress.
	 *
	 * @param bootstrap The ServerBootstrap.
	 * @param address The SocketAddress.
	 * @throws IOException If the ServerBootstrap fails to bind to the SocketAddress.
	 */
	private void bind(ServerBootstrap bootstrap, SocketAddress address) throws IOException {
		try {
			bootstrap.bind(address).sync();
		} catch (Exception cause) {
			throw new IOException("Failed to bind to " + address, cause);
		}
	}
	
}
