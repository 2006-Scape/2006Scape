package com.rs2.net;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Logger;

import org.apollo.cache.FileSystemConstants;
import org.apollo.net.NetworkConstants;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.net.codec.login.LoginDecoderState;
import org.apollo.util.BufferUtil;
import org.apollo.util.StatefulFrameDecoder;
import org.apollo.util.security.IsaacRandom;
import org.apollo.util.security.IsaacRandomPair;
import org.apollo.util.security.PlayerCredentials;

import com.google.common.net.InetAddresses;
import com.rs2.Connection;
import com.rs2.GameConstants;
import com.rs2.GameEngine;
import com.rs2.game.players.Client;
import com.rs2.game.players.PlayerHandler;
import com.rs2.game.players.PlayerSave;
import com.rs2.util.HostBlacklist;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * A {@link StatefulFrameDecoder} which decodes the login request frames.
 *
 * @author Graham
 */
public final class RS2LoginProtocolDecoder extends StatefulFrameDecoder<LoginDecoderState> {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(RS2LoginProtocolDecoder.class.getName());

	/**
	 * The secure random number generator.
	 */
	private static final SecureRandom RANDOM = new SecureRandom();

	/**
	 * The login packet length.
	 */
	private int loginLength;

	/**
	 * The reconnecting flag.
	 */
	private boolean reconnecting;

	/**
	 * The server-side session key.
	 */
	private long serverSeed;

	/**
	 * The username hash.
	 */
	private int usernameHash;

	/**
	 * Creates the login decoder with the default initial state.
	 */
	public RS2LoginProtocolDecoder() {
		super(LoginDecoderState.LOGIN_HANDSHAKE);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out, LoginDecoderState state) {
		switch (state) {
			case LOGIN_HANDSHAKE:
				decodeHandshake(ctx, in, out);
				break;
			case LOGIN_HEADER:
				decodeHeader(ctx, in, out);
				break;
			case LOGIN_PAYLOAD:
				decodePayload(ctx, in, out);
				break;
			default:
				throw new IllegalStateException("Invalid login decoder state: " + state);
		}
	}

	/**
	 * Decodes in the handshake state.
	 *
	 * @param ctx The channel handler context.
	 * @param buffer The buffer.
	 * @param out The {@link List} of objects to pass forward through the pipeline.
	 */
	private void decodeHandshake(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
		if (buffer.readableBytes() >= 2) {
			int protocol = buffer.readUnsignedByte();
			if (protocol != 14) {
				System.out.println("Invalid login request: " + protocol);
				ctx.channel().close();
				return;
			}
			
			usernameHash = buffer.readUnsignedByte();
			serverSeed = RANDOM.nextLong();

			ByteBuf response = ctx.alloc().buffer(17);
			response.writeByte(LoginConstants.STATUS_EXCHANGE_DATA);
			response.writeLong(0);
			response.writeLong(serverSeed);
			ctx.channel().write(response);

			setState(LoginDecoderState.LOGIN_HEADER);
		}
	}

	/**
	 * Decodes in the header state.
	 *
	 * @param ctx The channel handler context.
	 * @param buffer The buffer.
	 * @param out The {@link List} of objects to pass forward through the pipeline.
	 */
	private void decodeHeader(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
		if (buffer.readableBytes() >= 2) {
			int type = buffer.readUnsignedByte();

			if (type != LoginConstants.TYPE_STANDARD && type != LoginConstants.TYPE_RECONNECTION) {
				logger.fine("Failed to decode login header.");
				writeResponseCode(ctx, LoginConstants.STATUS_LOGIN_SERVER_REJECTED_SESSION);
				return;
			}

			reconnecting = type == LoginConstants.TYPE_RECONNECTION;
			loginLength = buffer.readUnsignedByte();

			setState(LoginDecoderState.LOGIN_PAYLOAD);
		}
	}

	/**
	 * Decodes in the payload state.
	 *
	 * @param ctx The channel handler context.
	 * @param buffer The buffer.
	 * @param out The {@link List} of objects to pass forward through the pipeline.
	 */
	private void decodePayload(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
		if (buffer.readableBytes() >= loginLength) {
			ByteBuf payload = buffer.readBytes(loginLength);
			int version = 255 - payload.readUnsignedByte();

			int release = payload.readUnsignedShort();

			int memoryStatus = payload.readUnsignedByte();
			if (memoryStatus != 0 && memoryStatus != 1) {
				logger.fine("Login memoryStatus (" + memoryStatus + ") not in expected range of [0, 1].");
				writeResponseCode(ctx, LoginConstants.STATUS_LOGIN_SERVER_REJECTED_SESSION);
				return;
			}

			boolean lowMemory = memoryStatus == 1;

			int[] crcs = new int[FileSystemConstants.ARCHIVE_COUNT];
			for (int index = 0; index < 9; index++) {
				crcs[index] = payload.readInt();
			}

			int length = payload.readUnsignedByte();
			if (length != loginLength - 41) {
				logger.fine("Login packet unexpected length (" + length + ")");
				writeResponseCode(ctx, LoginConstants.STATUS_LOGIN_SERVER_REJECTED_SESSION);
				return;
			}

			//ByteBuf secure = payload.readBytes(length);
			byte[] val = new byte[length];
			payload.readBytes(val);

			BigInteger value = new BigInteger(val);// new BigInteger(secure.array());
			value = value.modPow(NetworkConstants.RSA_EXPONENT, NetworkConstants.RSA_MODULUS);
			ByteBuf secure = Unpooled.wrappedBuffer(value.toByteArray());

			int id = secure.readUnsignedByte();
			if (id != 10) {
				logger.fine("Unable to read id from secure payload.");
				writeResponseCode(ctx, LoginConstants.STATUS_LOGIN_SERVER_REJECTED_SESSION);
				return;
			}

			long clientSeed = secure.readLong();
			long reportedSeed = secure.readLong();
			if (reportedSeed != serverSeed) {
				logger.fine("Reported seed differed from server seed.");
				writeResponseCode(ctx, LoginConstants.STATUS_LOGIN_SERVER_REJECTED_SESSION);
				return;
			}

			int uid = secure.readInt();
			System.out.println();
			String username = BufferUtil.readString(secure);
			String password = BufferUtil.readString(secure);
			System.out.println(username);
			System.out.println(password);

			InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
			String hostAddress = InetAddresses.toAddrString(socketAddress.getAddress());

//			if (password.length() < 6 || password.length() > 20 || username.isEmpty() || username.length() > 12) {
//				logger.fine("Username ('" + username + "') or password did not pass validation.");
//				writeResponseCode(ctx, LoginConstants.STATUS_INVALID_CREDENTIALS);
//				return;
//			}

			int[] seed = new int[4];
			seed[0] = (int) (clientSeed >> 32);
			seed[1] = (int) clientSeed;
			seed[2] = (int) (serverSeed >> 32);
			seed[3] = (int) serverSeed;

			IsaacRandom decodingRandom = new IsaacRandom(seed);
			for (int index = 0; index < seed.length; index++) {
				seed[index] += 50;
			}

			IsaacRandom encodingRandom = new IsaacRandom(seed);

			PlayerCredentials credentials = new PlayerCredentials(username, password, usernameHash, uid, hostAddress);
			IsaacRandomPair randomPair = new IsaacRandomPair(encodingRandom, decodingRandom);

			ctx.channel().pipeline().replace("decoder", "decoder", new RS2ProtocolDecoder(decodingRandom));
			ctx.channel().pipeline().addLast("encoder", new RS2ProtocolEncoder());
			out.add(load(ctx, credentials, randomPair));
		}
	}

	/**
	 * Writes a response code to the client and closes the current channel.
	 *
	 * @param ctx The context of the channel handler.
	 * @param response The response code to write.
	 */
	private void writeResponseCode(ChannelHandlerContext ctx, int response) {
		ByteBuf buffer = ctx.alloc().buffer(Byte.BYTES);
		buffer.writeByte(response);
		ctx.writeAndFlush(buffer).addListener(ChannelFutureListener.CLOSE);
	}

	

	public static Client load(ChannelHandlerContext ctx,
			PlayerCredentials credentials, IsaacRandomPair randomPair) {
		Channel channel = ctx.channel();
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
        
		Client cl = new Client(channel, -1);
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
			ByteBuf response = ctx.alloc().buffer(3);
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
			ByteBuf buffer = ctx.alloc().buffer(1);
			buffer.writeByte(returnCode);
			ctx.writeAndFlush(buffer).addListener(ChannelFutureListener.CLOSE);
			return null;
		}
		cl.isActive = true;//TODO?
		synchronized (PlayerHandler.lock) {// TODO nuke this?
			cl.getPacketSender().loginPlayer();
			cl.initialized = true;
		}
		return cl;
	}

//	private static final BigInteger RSA_MODULUS = new BigInteger("91553247461173033466542043374346300088148707506479543786501537350363031301992107112953015516557748875487935404852620239974482067336878286174236183516364787082711186740254168914127361643305190640280157664988536979163450791820893999053469529344247707567448479470137716627440246788713008490213212272520901741443");
//	private static final BigInteger RSA_EXPONENT = new BigInteger("33280025241734061313051117678670856264399753710527826596057587687835856000539511539311834363046145710983857746766009612538140077973762171163294453513440619295457626227183742315140865830778841533445402605660729039310637444146319289077374748018792349647460850308384280105990607337322160553135806205784213241305");
//
//	private int loginStage = 0;
//
//	/**
//	 * Parses the data in the provided byte buffer and writes it to
//	 * <code>out</code> as a <code>Packet</code>.
//	 * 
//	 * @param session
//	 *            The IoSession the data was read from
//	 * @param in
//	 *            The buffer
//	 * @param out
//	 *            The decoder output stream to which to write the
//	 *            <code>Packet</code>
//	 * @return Whether enough data was available to create a packet
//	 */
//	@Override
//	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//		Channel channel = ctx.channel();
//		if (!channel.isActive()) {
//			return;
//		}
//		// Logger.log("recv login packet, stage: "+loginStage);
//		switch (loginStage) {
//		case 0:
//			if (in.readableBytes() < 2) {
//				return;
//			}
//			int protocol = in.readUnsignedByte();
//			if (protocol != 14) {
//				System.out.println("Invalid login request: " + protocol);
//				channel.close();
//				return;
//			}
//			@SuppressWarnings("unused")
//			int nameHash = in.readUnsignedByte();
//			ByteBuf response = ctx.alloc().buffer(17);
//			response.writeByte(LoginConstants.STATUS_EXCHANGE_DATA);
//			response.writeLong(0);
//			response.writeLong(new SecureRandom().nextLong());
//			channel.write(response);
//			loginStage = 1;
//			return;
//		case 1:
//
//			if (in.readableBytes() < 2) {
//				return;
//			}
//
//			int loginType = in.readByte(); // should be 16 or 18
//			if (loginType != 16 && loginType != 18) {
//				System.out.println("Invalid login type: " + loginType);
//				// channel.close();
//				// return null;
//			}
//			// System.out.println("Login type = "+loginType);
//			int loginPacketSize = in.readByte() & 0xff;
//			if (in.readableBytes() < loginPacketSize) {
//				return;
//			}
//
//			int magic = in.readByte() & 0xff;
//			int version = in.readShort();
//			if (magic != 255) {
//				// System.out.println("Wrong magic id.");
//				channel.close();
//				return;
//			}
//			if (version != 1) {
//				// Dont Add Anything
//			}
//
//			@SuppressWarnings("unused")
//			int lowMem =in.readByte();// lowmem
//			for (int i = 0; i < 9; i++)
//				in.readInt();// skip
//
//			int securePayloadLength = in.readUnsignedByte();
//
//			ByteBuf secure = in.readBytes(securePayloadLength);
//
//			BigInteger value = new BigInteger(secure.array());//woops
//			value = value.modPow(RSA_EXPONENT, RSA_MODULUS);
//			secure = Unpooled.wrappedBuffer(value.toByteArray());
//
//			int rsaOpcode = secure.readByte() & 0xff;
//
//			if (rsaOpcode != 10) {
//				System.out.println("Unable to decode RSA block properly!");
//				channel.close();
//				return;
//			}
//
//			final long clientSessionKey = secure.readLong();
//			final long serverSessionKey = secure.readLong();
//			final int[] isaacSeed = { (int) (clientSessionKey >> 32), (int) clientSessionKey, (int) (serverSessionKey >> 32),
//					(int) serverSessionKey };
//			final IsaacRandom decodingRandom = new IsaacRandom(isaacSeed);
//			for (int i = 0; i < isaacSeed.length; i++)
//				isaacSeed[i] += 50;
//			final IsaacRandom encodingRandom = new IsaacRandom(isaacSeed);
//			final int uid = secure.readInt();
//			if(uid != 314268572) {
//				channel.close();
//				return;
//			}
//			final String username = BufferUtil.readString(secure);
//			final String password = BufferUtil.readString(secure);
//
//			channel.pipeline().replace("decoder", "decoder", new RS2ProtocolDecoder(decodingRandom));
//			out.add(load(ctx, uid, username, password, decodingRandom, encodingRandom, version));
//		}
//		return;
//	}
}
