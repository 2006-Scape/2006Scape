package org.apollo.game.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.io.IOException;
import java.util.Optional;

import org.apollo.ServerContext;
import org.apollo.game.io.player.PlayerLoaderResponse;
import org.apollo.game.model.World.RegistrationStatus;
import org.apollo.game.model.entity.Player;
import org.apollo.game.service.GameService;
import org.apollo.game.service.LoginService;
import org.apollo.net.codec.game.GameMessageDecoder;
import org.apollo.net.codec.game.GameMessageEncoder;
import org.apollo.net.codec.game.GamePacketDecoder;
import org.apollo.net.codec.game.GamePacketEncoder;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.net.codec.login.LoginRequest;
import org.apollo.net.codec.login.LoginResponse;
import org.apollo.net.release.Release;
import org.apollo.util.security.IsaacRandomPair;

/**
 * A login session.
 *
 * @author Graham
 */
public final class LoginSession extends Session {

	/**
	 * The ServerContext.
	 */
	private final ServerContext context;

	/**
	 * The LoginRequest for this LoginSession.
	 */
	private LoginRequest request;

	/**
	 * Creates a login session for the specified channel.
	 *
	 * @param channel The channel.
	 * @param context The server context.
	 */
	public LoginSession(Channel channel, ServerContext context) {
		super(channel);
		this.context = context;
	}

	@Override
	public void destroy() {

	}

	/**
	 * Handles a response from the login service.
	 *
	 * @param request The request this response corresponds to.
	 * @param response The response.
	 */
	public void handlePlayerLoaderResponse(LoginRequest request, PlayerLoaderResponse response) {
		this.request = request;
		GameService service = context.getGameService();
		Optional<Player> optional = response.getPlayer();

		if (optional.isPresent()) {
			service.registerPlayer(optional.get(), this);
		} else {
			sendLoginFailure(response.getStatus());
		}
	}

	@Override
	public void messageReceived(Object message) throws Exception {
		if (message.getClass() == LoginRequest.class) {
			handleLoginRequest((LoginRequest) message);
		}
	}

	/**
	 * Sends a failed {@link LoginResponse} to the client.
	 *
	 * @param status The failure status.
	 */
	public void sendLoginFailure(int status) {
		boolean flagged = false;
		LoginResponse response = new LoginResponse(status, 0, flagged);
		channel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * Sends a succesfull {@link LoginResponse} to the client.
	 *
	 * @param player The {@link Player} that successfully logged in.
	 */
	public void sendLoginSuccess(Player player) {
		IsaacRandomPair randomPair = request.getRandomPair();
		boolean flagged = false;

		GameSession session = new GameSession(channel, context, player, request.isReconnecting());
		channel.attr(ApolloHandler.SESSION_KEY).set(session);
		player.setSession(session);

		int rights = player.getPrivilegeLevel().toInteger();
		channel.writeAndFlush(new LoginResponse(LoginConstants.STATUS_OK, rights, flagged));

		Release release = context.getRelease();

		channel.pipeline().addFirst("messageEncoder", new GameMessageEncoder(release));
		channel.pipeline().addBefore("messageEncoder", "gameEncoder", new GamePacketEncoder(randomPair.getEncodingRandom()));

		channel.pipeline().addBefore("handler", "gameDecoder",
				new GamePacketDecoder(randomPair.getDecodingRandom(), context.getRelease()));
		channel.pipeline().addAfter("gameDecoder", "messageDecoder", new GameMessageDecoder(release));

		channel.pipeline().remove("loginDecoder");
		channel.pipeline().remove("loginEncoder");
	}

	/**
	 * Handles a login request.
	 *
	 * @param request The login request.
	 * @throws IOException If some I/O exception occurs.
	 */
	private void handleLoginRequest(LoginRequest request) throws IOException {
		LoginService service = context.getLoginService();
		service.submitLoadRequest(this, request);
	}
}