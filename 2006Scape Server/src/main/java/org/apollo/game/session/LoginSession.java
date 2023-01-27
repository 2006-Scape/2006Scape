package org.apollo.game.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Optional;

import org.apollo.net.codec.game.GameMessageDecoder;
import org.apollo.net.codec.game.GameMessageEncoder;
import org.apollo.net.codec.game.GamePacketDecoder;
import org.apollo.net.codec.game.GamePacketEncoder;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.net.codec.login.LoginRequest;
import org.apollo.net.codec.login.LoginResponse;
import org.apollo.net.release.Release;
import org.apollo.util.security.IsaacRandom;
import org.apollo.util.security.IsaacRandomPair;

import com.rs2.Connection;
import com.rs2.GameConstants;
import com.rs2.GameEngine;
import com.rs2.game.players.Client;
import com.rs2.game.players.PlayerHandler;
import com.rs2.game.players.PlayerSave;
import com.rs2.net.PacketBuilder;
import com.rs2.util.HostBlacklist;

/**
 * A login session.
 *
 * @author Graham
 */
public final class LoginSession extends Session {

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
	public LoginSession(Channel channel) {
		super(channel);
	}

	@Override
	public void destroy() {

	}

	@Override
	public void messageReceived(Object message) throws Exception {
		if (message.getClass() == LoginRequest.class) {
			handleLoginRequest((LoginRequest) message);
		}
	}

	public static Client load(Channel channel,
			String name, String pass, final IsaacRandom inC,
			IsaacRandom outC) {
		int returnCode = 2;
		
		name = name.trim();
		name = name.toLowerCase();
		//	pass = pass.toLowerCase();

		String hostName = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostName();

		//if (version != 1) {
			//returnCode = 31;
		//d}
		
		if (HostBlacklist.isBlocked(hostName)) {
			returnCode = 11;
		}
		
		if (!name.matches("[A-Za-z0-9 ]+")) {
			returnCode = 4;
		}
		if (name.length() > 12) {
			returnCode = 8;
		}
		
        if (pass.length() == 0) {
            returnCode = 4;
        }
        
		Client cl = new Client(channel, -1);
		cl.playerName = name;
		cl.playerName2 = cl.playerName;
		cl.playerPass = pass;
		cl.outStream.packetEncryption = outC;
		cl.saveCharacter = false;
		char first = name.charAt(0);
		cl.properName = Character.toUpperCase(first)
				+ name.substring(1, name.length());		
		if (Connection.isNamedBanned(cl.playerName)) {
			returnCode = 4;
		}
		
		if (PlayerHandler.isPlayerOn(name)) {
			returnCode = 5;
		}
		
		if (PlayerHandler.getPlayerCount() >= GameConstants.MAX_PLAYERS) {
			returnCode = 7;
		}
		
		if (GameEngine.updateServer) {
			returnCode = 14;
		}
		
		if (returnCode == 2) {
			int load = PlayerSave.loadGame(cl, cl.playerName, cl.playerPass);
			if (load == 0)
				cl.addStarter = true;
			if (load == 3) {
				returnCode = 3;
				cl.saveFile = false;
			} else {
				for (int i = 0; i < cl.playerEquipment.length; i++) {
					if (cl.playerEquipment[i] == 0) {
						cl.playerEquipment[i] = -1;
						cl.playerEquipmentN[i] = 0;
					}
				}
				if (!GameEngine.playerHandler.newPlayerClient(cl)) {
					returnCode = 7;
					cl.saveFile = false;
				} else {
					cl.saveFile = true;
				}
			}
		}
		if (returnCode == 2) {
			cl.saveCharacter = true;
			final PacketBuilder bldr = new PacketBuilder();
			bldr.put((byte) 2);
			if (cl.playerRights == 3) {
				bldr.put((byte) 2);
			} else {
				bldr.put((byte) cl.playerRights);
			}
			bldr.put((byte) 0);
			channel.write(bldr.toPacket());
		} else {
			System.out.println("returncode:" + returnCode);
			sendReturnCode(channel, returnCode);
			return null;
		}
		cl.isActive = true;//TODO?
		synchronized (PlayerHandler.lock) {// TODO nuke this?
			cl.getPacketSender().loginPlayer();
			cl.initialized = true;
		}
		return cl;
	}

	public static void sendReturnCode(final Channel channel, final int code) {
		channel.write(new PacketBuilder().put((byte) code).toPacket()).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(final ChannelFuture arg0) throws Exception {
				arg0.channel().close();
			}
		});
	}
	
//	/**
//	 * Handles a response from the login service.
//	 *
//	 * @param request The request this response corresponds to.
//	 * @param response The response.
//	 */
//	public void handlePlayerLoaderResponse(LoginRequest request, PlayerLoaderResponse response) {
//		this.request = request;
//		GameService service = context.getGameService();
//		Optional<Player> optional = response.getPlayer();
//
//		if (optional.isPresent()) {
//			service.registerPlayer(optional.get(), this);
//		} else {
//			sendLoginFailure(response.getStatus());
//		}
//	}
//	
//	/**
//	 * Sends a failed {@link LoginResponse} to the client.
//	 *
//	 * @param status The failure status.
//	 */
//	public void sendLoginFailure(int status) {
//		boolean flagged = false;
//		LoginResponse response = new LoginResponse(status, 0, flagged);
//		channel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//	}

	/**
	 * Sends a succesfull {@link LoginResponse} to the client.
	 *
	 * @param player The {@link Player} that successfully logged in.
	 */
	public void sendLoginSuccess(Player player) {
		IsaacRandomPair randomPair = request.getRandomPair();
		boolean flagged = false;

		GameSession session = new GameSession(channel, player, request.isReconnecting());
		channel.attr(ApolloHandler.SESSION_KEY).set(session);
		player.setSession(session);

		int rights = player.getPrivilegeLevel().toInteger();
		channel.writeAndFlush(new LoginResponse(LoginConstants.STATUS_OK, rights, flagged));

		channel.pipeline().addFirst("messageEncoder", new GameMessageEncoder(release));
		channel.pipeline().addBefore("messageEncoder", "gameEncoder", new GamePacketEncoder(randomPair.getEncodingRandom()));

		channel.pipeline().addBefore("handler", "gameDecoder", new GamePacketDecoder(randomPair.getDecodingRandom()));
		channel.pipeline().addAfter("gameDecoder", "messageDecoder", new GameMessageDecoder());

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
		load(channel, request.getCredentials().getUsername(), request.getCredentials().getPassword(), request.getRandomPair().getDecodingRandom(), request.getRandomPair().getEncodingRandom());
	}
}