package com.rs2.net;

import java.math.BigInteger;
import java.net.InetSocketAddress;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoFuture;
import org.apache.mina.common.IoFutureListener;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

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

public class RS2LoginProtocolDecoder extends CumulativeProtocolDecoder {
	
	private static final BigInteger RSA_MODULUS = new BigInteger("91553247461173033466542043374346300088148707506479543786501537350363031301992107112953015516557748875487935404852620239974482067336878286174236183516364787082711186740254168914127361643305190640280157664988536979163450791820893999053469529344247707567448479470137716627440246788713008490213212272520901741443");
	private static final BigInteger RSA_EXPONENT = new BigInteger("33280025241734061313051117678670856264399753710527826596057587687835856000539511539311834363046145710983857746766009612538140077973762171163294453513440619295457626227183742315140865830778841533445402605660729039310637444146319289077374748018792349647460850308384280105990607337322160553135806205784213241305");


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
	public boolean doDecode(IoSession session, ByteBuffer in,
			ProtocolDecoderOutput out) {
		synchronized (session) {
			Object loginStageObj = session.getAttribute("LOGIN_STAGE");
			int loginStage = 0;
			if (loginStageObj != null) {
				loginStage = (Integer) loginStageObj;
			}
			// Logger.log("recv login packet, stage: "+loginStage);
			switch (loginStage) {
			case 0:
				if (2 <= in.remaining()) {
					int protocol = in.get() & 0xff;
					@SuppressWarnings("unused")
					int nameHash = in.get() & 0xff;
					if (protocol == 14) {
						long serverSessionKey = ((long) (java.lang.Math
								.random() * 99999999D) << 32)
								+ (long) (java.lang.Math.random() * 99999999D);
						StaticPacketBuilder s1Response = new StaticPacketBuilder();
						s1Response
								.setBare(true)
								.addBytes(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 })
								.addByte((byte) 0).addLong(serverSessionKey);
						session.setAttribute("SERVER_SESSION_KEY",
								serverSessionKey);
						session.write(s1Response.toPacket());
						session.setAttribute("LOGIN_STAGE", 1);
					}
					return true;
				} else {
					in.rewind();
					return false;
				}
			case 1:
				@SuppressWarnings("unused")
				int loginType = -1,
				loginPacketSize = -1,
				loginEncryptPacketSize = -1;
				if (2 <= in.remaining()) {
					loginType = in.get() & 0xff; // should be 16 or 18
					loginPacketSize = in.get() & 0xff;
					loginEncryptPacketSize = loginPacketSize - (36 + 1 + 1 + 2);
					if (loginPacketSize <= 0 || loginEncryptPacketSize <= 0) {
						System.out.println("Zero or negative login size.");
						session.close();
						return false;
					}
				} else {
					in.rewind();
					return false;
				}
				if (loginPacketSize <= in.remaining()) {
					int magic = in.get() & 0xff;
					int version = in.getUnsignedShort();
					if (magic != 255) {
						// System.out.println("Wrong magic id.");
						session.close();
						return false;
					}
					if (version != 1) {
						// Dont Add Anything
					}
					@SuppressWarnings("unused")
					int lowMem = in.get() & 0xff;
					for (int i = 0; i < 9; i++) {
						in.getInt();
					}
					loginEncryptPacketSize--;
					if(loginEncryptPacketSize != (in.get() & 0xff)) {
						System.out.println("Encrypted size mismatch.");
						session.close();
						return false;
					}
                    byte[] encryptionBytes = new byte[loginEncryptPacketSize];
                    in.get(encryptionBytes);
                    ByteBuffer rsaBuffer = ByteBuffer.wrap(new BigInteger(encryptionBytes).modPow(RSA_EXPONENT, RSA_MODULUS).toByteArray());
					if((rsaBuffer.get() & 0xff) != 10) {
						System.out.println("Encrypted id != 10.");
						session.close();
						return false;
					}
					long clientSessionKey = rsaBuffer.getLong();
					long serverSessionKey = rsaBuffer.getLong();
					int uid = rsaBuffer.getInt();
					if(uid != 314268572) {
						session.close();
						return false;
					}
					String name = readRS2String(rsaBuffer);
					String pass = readRS2String(rsaBuffer);
					int sessionKey[] = new int[4];
					sessionKey[0] = (int) (clientSessionKey >> 32);
					sessionKey[1] = (int) clientSessionKey;
					sessionKey[2] = (int) (serverSessionKey >> 32);
					sessionKey[3] = (int) serverSessionKey;
					ISAACRandomGen inC = new ISAACRandomGen(sessionKey);
					for (int i = 0; i < 4; i++)
						sessionKey[i] += 50;
					ISAACRandomGen outC = new ISAACRandomGen(sessionKey);
					load(session, uid, name, pass, inC, outC, version);
					session.getFilterChain().remove("protocolFilter");
					session.getFilterChain().addLast("protocolFilter", new ProtocolCodecFilter(new GameCodecFactory(inC)));
					return true;
				} else {
					in.rewind();
					return false;
				}
			}
		}
		return false;
	}


	private synchronized void load(final IoSession session, final int uid,
			String name, String pass, final ISAACRandomGen inC,
			ISAACRandomGen outC, int version) {
		session.setAttribute("opcode", -1);
		session.setAttribute("size", -1);
		int returnCode = 2;

		name = name.trim();
		name = name.toLowerCase();
	//	pass = pass.toLowerCase();

		String hostName = ((InetSocketAddress) session.getRemoteAddress())
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

		Client cl = new Client(session, -1);
		cl.playerName = name;
		cl.playerName2 = cl.playerName;
		cl.playerPass = pass;

		cl.setInStreamDecryption(inC);
		cl.setOutStreamDecryption(outC);
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

		if (PlayerHandler.playerCount >= GameConstants.MAX_PLAYERS) {
			returnCode = 7;
		}

		if (GameEngine.updateServer) {
			returnCode = 14;
		}

		if (returnCode == 2) {
			int load = PlayerSave.loadGame(cl, cl.playerName, cl.playerPass);
			if (load == 0) {
				cl.addStarter = true;
			}
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

		cl.packetType = -1;
		cl.packetSize = 0;

		StaticPacketBuilder bldr = new StaticPacketBuilder();
		bldr.setBare(true);
		bldr.addByte((byte) returnCode);
		if (returnCode == 2) {
			cl.saveCharacter = true;
			if (cl.playerRights == 3) {
				bldr.addByte((byte) 2);
			} else {
				bldr.addByte((byte) cl.playerRights);
			}
		} else {
			bldr.addByte((byte) 0);
		}
		bldr.addByte((byte) 0);
		cl.isActive = true;
		Packet pkt = bldr.toPacket();
		session.setAttachment(cl);
		session.write(pkt).addListener(new IoFutureListener() {

			@Override
			public void operationComplete(IoFuture arg0) {
				session.getFilterChain().remove("protocolFilter");
				session.getFilterChain().addFirst("protocolFilter",
						new ProtocolCodecFilter(new GameCodecFactory(inC)));
			}
		});
	}

	private synchronized String readRS2String(ByteBuffer in) {
		StringBuilder sb = new StringBuilder();
		byte b;
		while ((b = in.get()) != 10) {
			sb.append((char) b);
		}
		return sb.toString();
	}

	/**
	 * Releases the buffer used by the given session.
	 * 
	 * @param session
	 *            The session for which to release the buffer
	 * @throws Exception
	 *             if failed to dispose all resources
	 */
	@Override
	public void dispose(IoSession session) throws Exception {
		super.dispose(session);
	}

}
