package org.apollo.game.session;

import com.rs2.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apollo.net.codec.login.LoginRequest;
import org.apollo.util.security.IsaacRandomPair;
import org.apollo.util.security.PlayerCredentials;

import com.rs2.Connection;
import com.rs2.GameEngine;
import com.rs2.game.players.Client;
import com.rs2.game.players.PlayerHandler;
import com.rs2.game.players.PlayerSave;
import com.rs2.net.RS2ProtocolDecoder;
import com.rs2.util.HostBlacklist;

/**
 * Temporary quick and tear integration with apollo netcode. This needs redone when the Apollo Service system is added.
 * @author Advocatus
 *
 */
public final class LoginSession extends Session {

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

	public static void load(Channel channel,
			PlayerCredentials credentials, IsaacRandomPair randomPair, boolean reconnecting) {
		
		int returnCode = 2;
		
		String username = credentials.getUsername().trim();
		String password = credentials.getPassword().trim();
		username = username.toLowerCase();
		//	pass = pass.toLowerCase();

		String hostName = ((InetSocketAddress) channel.remoteAddress())
				.getAddress().getHostName();

//		if (uid != 314268572) {
//			channel.close();
//			return;
//		}
//		if (version != 1) {
//			returnCode = 31;
//		}
		
		if (HostBlacklist.isBlocked(hostName)) {
			returnCode = 11;
		}
		
		if (!username.matches("[A-Za-z0-9 ]+")) {
			returnCode = 4;
		}
		if (username.length() > 12) {
			returnCode = 8;
		}
		
        if (password.length() == 0) {
            returnCode = 4;
        }
		GameSession session = new GameSession(channel, null, reconnecting);
		Client cl = new Client(session, -1);
		session.setPlayer(cl);
		cl.playerName = username;
		cl.playerName2 = cl.playerName;
		cl.playerPass = password;
		cl.outStream.packetEncryption = randomPair.getEncodingRandom();
		cl.saveCharacter = false;
		char first = username.charAt(0);
		cl.properName = Character.toUpperCase(first)
				+ username.substring(1, username.length());		
		if (Connection.isNamedBanned(cl.playerName)) {
			returnCode = 4;
		}
		
		if (PlayerHandler.isPlayerOn(username)) {
			returnCode = 5;
		}
		
		if (PlayerHandler.getPlayerCount() >= Constants.MAX_PLAYERS) {
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
			ByteBuf response = channel.alloc().buffer(3);
			response.writeByte((byte) 2);
			if (cl.playerRights == 3) {
				response.writeByte((byte) 2);
			} else {
				response.writeByte((byte) cl.playerRights);
			}
			response.writeByte((byte) 0);
			channel.write(response);
		} else {
			System.out.println("returncode:" + returnCode);
			ByteBuf buffer = channel.alloc().buffer(1);
			buffer.writeByte(returnCode);
			channel.writeAndFlush(buffer).addListener(ChannelFutureListener.CLOSE);
			return;
		}
		cl.isActive = true;
		channel.attr(ApolloHandler.SESSION_KEY).set(session);
		
		channel.pipeline().addBefore("handler", "gameDecoder", new RS2ProtocolDecoder(randomPair.getDecodingRandom()));
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
		load(channel, request.getCredentials(), request.getRandomPair(), request.isReconnecting());
	}
}