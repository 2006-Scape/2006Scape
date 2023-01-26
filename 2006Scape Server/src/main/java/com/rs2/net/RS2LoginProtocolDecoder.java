package com.rs2.net;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.SecureRandom;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.rs2.Connection;
import com.rs2.GameConstants;
import com.rs2.GameEngine;
import com.rs2.game.players.Client;
import com.rs2.game.players.PlayerHandler;
import com.rs2.game.players.PlayerSave;
import com.rs2.util.HostBlacklist;
import com.rs2.util.ISAACRandomGen;

/**
 * Login protocol decoder.
 * 
 * @author Graham
 * @author Ryan / Lmctruck30 <- login Protocol fixes
 */

public class RS2LoginProtocolDecoder extends FrameDecoder {

	private static final BigInteger RSA_MODULUS = new BigInteger("91553247461173033466542043374346300088148707506479543786501537350363031301992107112953015516557748875487935404852620239974482067336878286174236183516364787082711186740254168914127361643305190640280157664988536979163450791820893999053469529344247707567448479470137716627440246788713008490213212272520901741443");
	private static final BigInteger RSA_EXPONENT = new BigInteger("33280025241734061313051117678670856264399753710527826596057587687835856000539511539311834363046145710983857746766009612538140077973762171163294453513440619295457626227183742315140865830778841533445402605660729039310637444146319289077374748018792349647460850308384280105990607337322160553135806205784213241305");

	private int loginStage = 0;

	/**
	 * Parses the data in the provided byte buffer and writes it to
	 * <code>out</code> as a <code>Packet</code>.
	 * 
	 * @param session
	 *            The IoSession the data was read from
	 * @param in
	 *            The buffer
	 * @param out
	 *            The decoder output stream to which to write the
	 *            <code>Packet</code>
	 * @return Whether enough data was available to create a packet
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (!channel.isConnected()) {
			return null;
		}
		// Logger.log("recv login packet, stage: "+loginStage);
		switch (loginStage) {
		case 0:
			if (buffer.readableBytes() < 2) {
				return null;
			}
			int protocol = buffer.readUnsignedByte();
			if (protocol != 14) {
				System.out.println("Invalid login request: " + protocol);
				channel.close();
				return null;
			}
			@SuppressWarnings("unused")
			int nameHash = buffer.readUnsignedByte();
			channel.write(new PacketBuilder().putLong(0).put((byte) 0).putLong(new SecureRandom().nextLong()).toPacket());
			loginStage = 1;
			return null;
		case 1:

			if (buffer.readableBytes() < 2) {
				return null;
			}

			int loginType = buffer.readByte(); // should be 16 or 18
			if (loginType != 16 && loginType != 18) {
				System.out.println("Invalid login type: " + loginType);
				// channel.close();
				// return null;
			}
			// System.out.println("Login type = "+loginType);
			int loginPacketSize = buffer.readByte() & 0xff;
			if (buffer.readableBytes() < loginPacketSize) {
				return null;
			}

			int magic = buffer.readByte() & 0xff;
			int version = buffer.readShort();
			if (magic != 255) {
				// System.out.println("Wrong magic id.");
				channel.close();
				return null;
			}
			if (version != 1) {
				// Dont Add Anything
			}

			@SuppressWarnings("unused")
			int lowMem =buffer.readByte();// lowmem
			for (int i = 0; i < 9; i++)
				buffer.readInt();// skip

			int securePayloadLength = buffer.readUnsignedByte();

			ChannelBuffer secure = buffer.readBytes(securePayloadLength);

			BigInteger value = new BigInteger(secure.array());
			value = value.modPow(RSA_EXPONENT, RSA_MODULUS);
			secure = ChannelBuffers.wrappedBuffer(value.toByteArray());

			int rsaOpcode = secure.readByte() & 0xff;

			if (rsaOpcode != 10) {
				System.out.println("Unable to decode RSA block properly!");
				channel.close();
				return null;
			}

			final long clientSessionKey = secure.readLong();
			final long serverSessionKey = secure.readLong();
			final int[] isaacSeed = { (int) (clientSessionKey >> 32), (int) clientSessionKey, (int) (serverSessionKey >> 32),
					(int) serverSessionKey };
			final ISAACRandomGen inC = new ISAACRandomGen(isaacSeed);
			for (int i = 0; i < isaacSeed.length; i++)
				isaacSeed[i] += 50;
			final ISAACRandomGen outC = new ISAACRandomGen(isaacSeed);
			final int uid = secure.readInt();
			if(uid != 314268572) {
				channel.close();
				return null;
			}
			final String name = readRS2String(secure);
			final String pass = readRS2String(secure);

			channel.getPipeline().replace("decoder", "decoder", new RS2ProtocolDecoder(inC));
			return load(channel, uid, name, pass, inC, outC, version);
		}
		return null;
	}

	private static Client load(Channel channel, final int uid,
			String name, String pass, final ISAACRandomGen inC,
			ISAACRandomGen outC, int version) {
		int returnCode = 2;
		
		name = name.trim();
		name = name.toLowerCase();
		//	pass = pass.toLowerCase();

		String hostName = ((InetSocketAddress) channel.getRemoteAddress())
				.getAddress().getHostName();

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
				arg0.getChannel().close();
			}
		});
	}

	private static String readRS2String(final ChannelBuffer buf) {
		final StringBuilder bldr = new StringBuilder();
		byte b;
		while (buf.readable() && (b = buf.readByte()) != 10)
			bldr.append((char) b);
		return bldr.toString();
	}
}
