package org.apollo.game.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rs2.game.players.Player;
import com.rs2.net.Packet;

/**
 * A game session.
 *
 * @author Graham
 */
public final class GameSession extends Session {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(GameSession.class.getName());

	/**
	 * The queue of pending {@link Message}s.
	 */
	private final BlockingQueue<Packet> messages = new ArrayBlockingQueue<>(25);

	/**
	 * The player.
	 */
	private Player player;

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * If the player was reconnecting.
	 */
	private final boolean reconnecting;

	/**
	 * Creates a login session for the specified channel.
	 *
	 * @param channel The channel.
	 * @param context The server context.
	 * @param player The player.
	 * @param reconnecting If the player was reconnecting.
	 */
	public GameSession(Channel channel, Player player, boolean reconnecting) {
		super(channel);
		this.player = player;
		this.reconnecting = reconnecting;
	}

	@Override
	public void destroy() {
		player.disconnected = true;
	}

//	/**
//	 * Encodes and dispatches the specified message.
//	 *
//	 * @param message The message.
//	 */
//	public void dispatchMessage(Packet message) {
//		Channel channel = getChannel();
//		if (channel.isActive() && channel.isOpen()) {
//			ChannelFuture future = channel.writeAndFlush(message);
//	//		if (message.getClass() == LogoutMessage.class) {//TODO write packet 109.
//	//			future.addListener(ChannelFutureListener.CLOSE);
//	//		}
//		}
//	}
//
//	/**
//	 * Handles pending messages for this session.
//	 *
//	 * @param chainSet The {@link MessageHandlerChainSet}
//	 */
//	public void handlePendingMessages(MessageHandlerChainSet chainSet) {
//		while (!messages.isEmpty()) {
//			Message message = messages.poll();
//
//			try {
//				chainSet.notify(player, message);
//			} catch (Exception reason) {
//				logger.log(Level.SEVERE, "Uncaught exception thrown while handling message: " + message, reason);
//			}
//		}
//	}
//
//	/**
//	 * Handles a player saver response.
//	 *
//	 * @param success A flag indicating if the save was successful.
//	 */
//	public void handlePlayerSaverResponse(boolean success) {
//		context.getGameService().finalizePlayerUnregistration(player);
//	}

	/**
	 * Determines if this player is reconnecting.
	 *
	 * @return {@code true} if reconnecting, {@code false} otherwise.
	 */
	public boolean isReconnecting() {
		return reconnecting;
	}

	@Override
	public void messageReceived(Object message) {
//		System.out.println(message.getClass());
//		if (messages.size() >= 25) {
//			logger.warning("Too many messages in queue for game session, dropping...");
//		} else {
//			messages.add((Message) message);
//		}
		player.queueMessage((Packet) message);
	}

	public SocketAddress getRemoteAddress() {
		return channel.remoteAddress();
	}

	public void close() {
		channel.close();
	}

	public boolean isActive() {
		return channel.isActive();
	}

	public void write(Packet packet) {
		System.out.println("Writing packet xd");
		channel.write(packet);
	}

}