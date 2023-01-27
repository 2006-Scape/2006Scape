package org.apollo.game.session;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.net.SocketAddress;

import com.rs2.game.players.Player;
import com.rs2.net.Packet;

/**
 * Temporary quick and tear integration with apollo netcode. This needs redone when the the packets are fully redone.
 * @author Advocatus
 *
 */
public final class GameSession extends Session {

	private Player player;

	public void setPlayer(Player player) {
		this.player = player;
	}

	private final boolean reconnecting;

	public GameSession(Channel channel, Player player, boolean reconnecting) {
		super(channel);
		this.player = player;
		this.reconnecting = reconnecting;
	}

	@Override
	public void destroy() {
		player.disconnected = true;
	}

	public boolean isReconnecting() {
		return reconnecting;
	}

	@Override
	public void messageReceived(Object message) {
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

//	public void write(Packet packet) {
//		channel.write(packet);
//	}

	public void write(ByteBuf buf) {
		channel.writeAndFlush(buf);
	}

}