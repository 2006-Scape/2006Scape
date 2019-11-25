package com.rebotted.net;

import java.text.DecimalFormat;

import com.rebotted.GameConstants;
import com.rebotted.game.content.skills.SkillHandler;
import com.rebotted.game.players.Client;
import com.rebotted.game.players.Player;
import com.rebotted.game.players.PlayerHandler;
import com.rebotted.util.Misc;
import com.rebotted.world.clip.Region;

public class PacketSender {

	private final Client player;

	public PacketSender(Client client) {
		this.player = client;
	}


	public PacketSender sendClan(String name, String message, String clan, int rights) {
		if (player.getOutStream() == null) return this;
		player.outStream.createFrameVarSizeWord(217);
		player.outStream.writeString(name);
		player.outStream.writeString(message);
		player.outStream.writeString(clan);
		player.outStream.writeWord(rights);
		player.outStream.endFrameVarSize();
		return this;
	}
	
	public PacketSender createPlayersObjectAnim(int X, int Y, int animationID, int tileObjectType, int orientation) {
		if (player.getOutStream() == null) return this;
		try{
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC(Y - (player.mapRegionY * 8));
			player.getOutStream().writeByteC(X - (player.mapRegionX * 8));
			int x = 0;
			int y = 0;
			player.getOutStream().createFrame(160);
			player.getOutStream().writeByteS(((x&7) << 4) + (y&7));//tiles away - could just send 0       
			player.getOutStream().writeByteS((tileObjectType<<2) +(orientation&3));
			player.getOutStream().writeWordA(animationID);// animation id
		} catch(Exception e){
			e.printStackTrace();
		}
		return this;
	}
	
	
	public PacketSender setInterfaceOffset(int x, int y, int id) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(70);
			player.getOutStream().writeWord(x);
			player.getOutStream().writeWordBigEndian(y);
			player.getOutStream().writeWordBigEndian(id);
			player.flushOutStream();
		}
		return this;
	}
	
	public PacketSender shakeScreen(int verticleAmount, int verticleSpeed,
			int horizontalAmount, int horizontalSpeed) {
		if (player.getOutStream() == null) return this;
		player.getOutStream().createFrame(35); // Creates frame 35.
		player.getOutStream().writeByte(verticleAmount);
		player.getOutStream().writeByte(verticleSpeed);
		player.getOutStream().writeByte(horizontalAmount);
		player.getOutStream().writeByte(horizontalSpeed);
		return this;
	}

	public PacketSender chatbox(int i1) {
		if (player.getOutStream() != null && player != null) {
			player.outStream.createFrame(218);
			player.outStream.writeWordBigEndianA(i1);
			player.updateRequired = true;
			player.appearanceUpdateRequired = true;
		}
		return this;
	}

	public PacketSender sendMessage(String s) {
		if (player.getOutStream() != null) {
			player.getOutStream().createFrameVarSize(253);
			player.getOutStream().writeString(s);
			player.getOutStream().endFrameVarSize();
		}
		return this;
	}
	
	/**
	 * Reseting animations for everyone
	 **/

	public PacketSender frame1() {
		for (Player player : PlayerHandler.players) {
			if (player != null) {
				Client person = (Client) player;
				if (person != null) {
					if (person.getOutStream() != null && !person.disconnected) {
						if (player
								.distanceToPoint(person.getX(), person.getY()) <= 25) {
							person.getOutStream().createFrame(1);
							person.flushOutStream();
							person.getPlayerAssistant().requestUpdates();
						}
					}
				}
			}
		}
		return this;
	}
	
	public PacketSender setInterfaceWalkable(int ID) {
		player.outStream.createFrame(208);
		player.outStream.writeWordBigEndian_dup(ID);
		player.flushOutStream();
		return this;
	}
	
	public PacketSender sendFrame36(int id, int state) {
		if(player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(36);
			player.getOutStream().writeWordBigEndian(id);
			player.getOutStream().writeByte(state);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendFrame20(int id, int state) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(36);
			player.getOutStream().writeWordBigEndian(id);
			player.getOutStream().writeByte(state);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendFrame126(String s, int id) {
		if(!player.checkPacket126Update(s, id)) {
			return this;
		}
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(126);
			player.getOutStream().writeString(s);
			player.getOutStream().writeWordA(id);
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendFrame107() {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(107);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendPlayerDialogueHead(int Frame) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(185);
			player.getOutStream().writeWordBigEndianA(Frame);
		}
		return this;
	}

	public PacketSender showInterface(int interfaceid) {
		if (player.inTrade || player.inDuel) {
			return this;
		}
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(97);
			player.getOutStream().writeWord(interfaceid);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendFrame248(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(248);
			player.getOutStream().writeWordA(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendFrame246(int MainFrame, int SubFrame, int SubFrame2) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(246);
			player.getOutStream().writeWordBigEndian(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.getOutStream().writeWord(SubFrame2);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendFrame171(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(171);
			player.getOutStream().writeByte(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendDialogueAnimation(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(200);
			player.getOutStream().writeWord(MainFrame);
			player.getOutStream().writeWord(SubFrame);
			player.flushOutStream();
		}
		return this;
	}

	public int mapStatus = 0;

	public PacketSender sendMapState(int state) { // used for disabling map
		if (player.getOutStream() != null && player != null) {
			if (mapStatus != state) {
				mapStatus = state;
				player.getOutStream().createFrame(99);
				player.getOutStream().writeByte(state);
				player.flushOutStream();
			}
		}
		return this;
	}

	public PacketSender sendFrame106(int sideIcon) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(106);
			player.getOutStream().writeByteC(sideIcon);
			player.flushOutStream();
			player.getPlayerAssistant().requestUpdates();
		}
		return this;
	}

	public PacketSender sendFrame70(int i, int o, int id) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(70);
			player.getOutStream().writeWord(i);
			player.getOutStream().writeWordBigEndian(o);
			player.getOutStream().writeWordBigEndian(id);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendNPCDialogueHead(int MainFrame, int SubFrame) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(75);
			player.getOutStream().writeWordBigEndianA(MainFrame);
			player.getOutStream().writeWordBigEndianA(SubFrame);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendChatInterface(int Frame) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(164);
			player.getOutStream().writeWordBigEndian_dup(Frame);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender setPrivateMessaging(int i) { // friends and ignore list status
												// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(221);
			player.getOutStream().writeByte(i);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender setChatOptions(int publicChat, int privateChat, int tradeBlock) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(206);
			player.getOutStream().writeByte(publicChat);
			player.getOutStream().writeByte(privateChat);
			player.getOutStream().writeByte(tradeBlock);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendFrame87(int id, int state) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(87);
			player.getOutStream().writeWordBigEndian_dup(id);
			player.getOutStream().writeDWord_v1(state);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendPM(long name, int rights, byte[] chatmessage,
			int messagesize) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSize(196);
			player.getOutStream().writeQWord(name);
			player.getOutStream().writeDWord(player.lastChatId++);
			player.getOutStream().writeByte(rights);
			player.getOutStream().writeBytes(chatmessage, messagesize, 0);
			player.getOutStream().endFrameVarSize();
			player.flushOutStream();
			Misc.textUnpack(chatmessage, messagesize);
			Misc.longToPlayerName(name);
		}
		return this;
	}

	public PacketSender loadPM(long playerName, int world) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			if (world != 0) {
				world += 9;
			} else if (!GameConstants.WORLD_LIST_FIX) {
				world += 1;
			}
			player.getOutStream().createFrame(50);
			player.getOutStream().writeQWord(playerName);
			player.getOutStream().writeByte(world);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender closeAllWindows() {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(219);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender sendFrame34(int id, int slot, int column, int amount) {
		if (player.getOutStream() != null && player != null) {
			player.outStream.createFrameVarSizeWord(34); // init item to smith
			// screen
			player.outStream.writeWord(column); // Column Across Smith Screen
			player.outStream.writeByte(4); // Total Rows?
			player.outStream.writeDWord(slot); // Row Down The Smith Screen
			player.outStream.writeWord(id + 1); // item
			player.outStream.writeByte(amount); // how many there are?
			player.outStream.endFrameVarSizeWord();
		}
		return this;
	}
	
	public PacketSender sendItemOnInterface(int id, int amount, int child) {
			player.getOutStream().createFrameVarSizeWord(53);
			player.getOutStream().writeWord(child);
			player.getOutStream().writeWord(amount);
			if (amount > 254){
				player.getOutStream().writeByte(255);
				player.getOutStream().writeDWord_v2(amount);
			} else {
				player.getOutStream().writeByte(amount);
			}
			player.getOutStream().writeWordBigEndianA(id); 
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
			return this;
	}
	
	public PacketSender walkableInterface(int id) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(208);
			player.getOutStream().writeWordBigEndian_dup(id);
			player.flushOutStream();
		}
		return this;
	}
	

	public PacketSender openUpBank() {
		if (player.isBanking = false) {
			player.getPacketSender().closeAllWindows();
			return this;
		}
		if (SkillHandler.isSkilling(player)) {
			player.getPacketSender().closeAllWindows();
			player.isBanking = false;
			return this;
		}
		if (player.inWild()) {
			player.getPacketSender().sendMessage("You can't open up a bank in the wilderness!");
			player.getPacketSender().closeAllWindows();
			return this;
		}

		if (!player.inBankArea() && player.playerRights < 3) {
			player.getPacketSender().sendMessage("You can't open a bank unless you're in a bank area!");
			player.getPacketSender().sendMessage("If this is a bug, please report it! Your coords are [" + player.absX + "," + player.absY + "]");
			player.getPacketSender().closeAllWindows();
			return this;
		}

		if (player.absX == 2813 && player.absY == 3443) {
			return this;
		}
		if (player.requestPinDelete) {
			if (player.enterdBankpin) {
				player.requestPinDelete = false;
				player.getPacketSender().sendMessage("[Notice] Your PIN pending deletion has been cancelled.");
			} else if (player.lastLoginDate >= player.pinDeleteDateRequested && player.hasBankpin) {
				player.hasBankpin = false;
				player.requestPinDelete = false;
				player.getPacketSender().sendMessage("[Notice] Your PIN has been deleted. It is recommended "
								+ "to have one.");
			}
		}
		if (!player.enterdBankpin && player.hasBankpin) {
			player.getBankPin().openPin();
			return this;
		}
		if (player.inTrade || player.tradeStatus == 1) {
			Client o = (Client) PlayerHandler.players[player.tradeWith];
			if (o != null) {
				o.getTrading().declineTrade();
			}
		}
		if (player.duelStatus == 1) {
			Client o = (Client) PlayerHandler.players[player.duelingWith];
			if (o != null) {
				o.getDueling().resetDuel();
			}
		}
		if (player.getOutStream() != null && player != null) {
			player.getItemAssistant().resetItems(5064);
			player.getItemAssistant().rearrangeBank();
			player.getItemAssistant().resetBank();
			player.getItemAssistant().resetTempItems();
			player.getOutStream().createFrame(248);
			player.getOutStream().writeWordA(5292);
			player.getOutStream().writeWord(5063);
			player.flushOutStream();
			player.isBanking = true;
		}
		return this;
	}

	/**
	 ** GFX
	 **/
	public PacketSender stillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC(y - player.getMapRegionY() * 8);
			player.getOutStream().writeByteC(x - player.getMapRegionX() * 8);
			player.getOutStream().createFrame(4);
			player.getOutStream().writeByte(0);
			player.getOutStream().writeWord(id);
			player.getOutStream().writeByte(height);
			player.getOutStream().writeWord(time);
			player.flushOutStream();
		}
		return this;
	}


	public PacketSender setSidebarInterface(int menuId, int form) {
		if (player.getOutStream() != null) {
			player.getOutStream().createFrame(71);
			player.getOutStream().writeWord(form);
			player.getOutStream().writeByteA(menuId);
		}
		return this;
	}

	/**
	 * Flashes Sidebar Icon
	 */

	public PacketSender flashSideBarIcon(int i1) {
		// Makes the sidebar Icons flash
		// Usage: i1 = 0 through -12 inorder to work
		if (player.getOutStream() != null) {
			player.outStream.createFrame(24);
			player.outStream.writeByteA(i1);
		}
		player.updateRequired = true;
		player.appearanceUpdateRequired = true;
		return this;
	}

	public PacketSender createPlayerHints(int type, int id) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(254);
			player.getOutStream().writeByte(type);
			player.getOutStream().writeWord(id);
			player.getOutStream().write3Byte(0);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender createObjectHints(int x, int y, int height, int pos) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(254);
			player.getOutStream().writeByte(pos);
			player.getOutStream().writeWord(x);
			player.getOutStream().writeWord(y);
			player.getOutStream().writeByte(height);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender createProjectile(int x, int y, int offX, int offY,
			int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream()
					.writeByteC(y - player.getMapRegionY() * 8 - 2);
			player.getOutStream()
					.writeByteC(x - player.getMapRegionX() * 8 - 3);
			player.getOutStream().createFrame(117);
			player.getOutStream().writeByte(angle);
			player.getOutStream().writeByte(offY);
			player.getOutStream().writeByte(offX);
			player.getOutStream().writeWord(lockon);
			player.getOutStream().writeWord(gfxMoving);
			player.getOutStream().writeByte(startHeight);
			player.getOutStream().writeByte(endHeight);
			player.getOutStream().writeWord(time);
			player.getOutStream().writeWord(speed);
			player.getOutStream().writeByte(16);
			player.getOutStream().writeByte(64);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender createProjectile2(int x, int y, int offX, int offY,
			int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time, int slope) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream()
					.writeByteC(y - player.getMapRegionY() * 8 - 2);
			player.getOutStream()
					.writeByteC(x - player.getMapRegionX() * 8 - 3);
			player.getOutStream().createFrame(117);
			player.getOutStream().writeByte(angle);
			player.getOutStream().writeByte(offY);
			player.getOutStream().writeByte(offX);
			player.getOutStream().writeWord(lockon);
			player.getOutStream().writeWord(gfxMoving);
			player.getOutStream().writeByte(startHeight);
			player.getOutStream().writeByte(endHeight);
			player.getOutStream().writeWord(time);
			player.getOutStream().writeWord(speed);
			player.getOutStream().writeByte(slope);
			player.getOutStream().writeByte(64);
			player.flushOutStream();
		}
		return this;
	}

	/**
	 * Objects, add and remove
	 **/
	public PacketSender object(int objectId, int objectX, int objectY, int face, int objectType) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC(
					objectY - player.getMapRegionY() * 8);
			player.getOutStream().writeByteC(
					objectX - player.getMapRegionX() * 8);
			player.getOutStream().createFrame(101);
			player.getOutStream().writeByteC((objectType << 2) + (face & 3));
			player.getOutStream().writeByte(0);
			if (objectId != -1) { // removing
				player.getOutStream().createFrame(151);
				player.getOutStream().writeByteS(0);
				player.getOutStream().writeWordBigEndian(objectId);
				player.getOutStream()
						.writeByteS((objectType << 2) + (face & 3));
			}
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender object(int objectId, int objectX, int objectY, int objectH, int face, int objectType) {
		if (player.getOutStream() == null) return this;
		if (player.heightLevel != objectH) {
			return this;
		}
		if (Misc.goodDistance(objectX, objectY, player.absX, player.absY, 60)) {
			if (player.getOutStream() != null && player != null) {
				player.getOutStream().createFrame(85);
				player.getOutStream().writeByteC(objectY - player.getMapRegionY() * 8);
				player.getOutStream().writeByteC(objectX - player.getMapRegionX() * 8);
				player.getOutStream().createFrame(101);
				player.getOutStream().writeByteC((objectType << 2) + (face & 3));
				player.getOutStream().writeByte(0);
				if (objectId != -1) { // removing
					player.getOutStream().createFrame(151);
					player.getOutStream().writeByteS(0);
					player.getOutStream().writeWordBigEndian(objectId);
					player.getOutStream().writeByteS((objectType << 2) + (face & 3));
				}
				player.flushOutStream();
			}
		}
		return this;
	}

	public PacketSender tempSong(int songID, int songID2) {
		if (player.getOutStream() == null) return this;
		player.outStream.createFrame(121);
		player.outStream.writeWordBigEndian(songID);
		player.outStream.writeWordBigEndian(songID2);
		player.flushOutStream();
		return this;
	}

	public PacketSender frame174(int sound, int vol, int delay) {
		if (player.getOutStream() == null) return this;
		player.outStream.createFrame(174);
		player.outStream.writeWord(sound);
		player.outStream.writeWord(delay);
		player.outStream.writeByte(vol);
		return this;
	}

	public PacketSender writeWeight(int weight) {
		if (player.getOutStream() == null) return this;
		player.outStream.createFrame(240);
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		player.outStream.writeWord(Integer.valueOf(twoDForm.format(weight)));
		return this;
	}

	public PacketSender sendConfig(int id, int state) {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			if (state < Byte.MIN_VALUE || state > Byte.MAX_VALUE) {
				player.getOutStream().createFrame(87);
				player.getOutStream().writeWordBigEndian_dup(id);
				player.getOutStream().writeDWord_v1(state);
				player.flushOutStream();
			} else {
				player.getOutStream().createFrame(36);
				player.getOutStream().writeWordBigEndian(id);
				player.getOutStream().writeByte(state);
				player.flushOutStream();
			}
		}
		return this;
	}

	public PacketSender multiWay(int i1) {
		if (player.getOutStream() != null) {
			player.outStream.createFrame(61);
			player.outStream.writeByte(i1);
		}
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
		return this;
	}

	public PacketSender sendColor(int id, int color) {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			player.outStream.createFrame(122);
			player.outStream.writeWordBigEndianA(id);
			player.outStream.writeWordBigEndianA(color);
		}
		return this;
	}

	public PacketSender sendCrashFrame() {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			player.getOutStream().createFrame(123);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender createStillGfx(int id, int x, int y, int height, int time) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPacketSender().stillGfx(id, x, y,
									height, time);
						}
					}
				}
			}
		}
		return this;
	}

	public PacketSender object(int objectId, int objectX, int objectY, int objectType) {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC(
					objectY - player.getMapRegionY() * 8);
			player.getOutStream().writeByteC(
					objectX - player.getMapRegionX() * 8);
			player.getOutStream().createFrame(101);
			player.getOutStream().writeByteC((objectType << 2) + (0 & 3));
			player.getOutStream().writeByte(0);
			if (objectId != -1) { // removing
				player.getOutStream().createFrame(151);
				player.getOutStream().writeByteS(0);
				player.getOutStream().writeWordBigEndian(objectId);
				player.getOutStream().writeByteS((objectType << 2) + (0 & 3));
			}
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender itemOnInterface(int interfaceChild, int zoom, int itemId) {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			player.getOutStream().createFrame(246);
			player.getOutStream().writeWordBigEndian(interfaceChild);
			player.getOutStream().writeWord(zoom);
			player.getOutStream().writeWord(itemId);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender setConfig(int id, int state) {
		if (player.getOutStream() == null) return this;
		player.outStream.createFrame(36);
		player.outStream.writeWordBigEndian(id);
		player.outStream.writeByte(state);
		return this;
	}

	public PacketSender sendLink(String s) {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			player.getOutStream().createFrameVarSizeWord(187);
			player.getOutStream().writeString(s);
		}
		return this;
	}

	public PacketSender setSkillLevel(int skillNum, int currentLevel, int XP) {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			player.getOutStream().createFrame(134);
			player.getOutStream().writeByte(skillNum);
			player.getOutStream().writeDWord_v1(XP);
			player.getOutStream().writeByte(currentLevel);
			player.flushOutStream();
		}
		return this;
	}

	/**
	 * Show an arrow icon on the selected player.
	 * 
	 * @Param i - Either 0 or 1; 1 is arrow, 0 is none.
	 * @Param j - The player/Npc that the arrow will be displayed above.
	 * @Param k - Keep this set as 0
	 * @Param l - Keep this set as 0
	 */
	public PacketSender drawHeadicon(int i, int j, int k, int l) {
		if (player.getOutStream() == null) return this;

		// synchronized(c) {
		player.outStream.createFrame(254);
		player.outStream.writeByte(i);

		if (i == 1 || i == 10) {
			player.outStream.writeWord(j);
			player.outStream.writeWord(k);
			player.outStream.writeByte(l);
		} else {
			player.outStream.writeWord(k);
			player.outStream.writeWord(l);
			player.outStream.writeByte(j);
		}
		return this;
	}

	// object

	public PacketSender createArrow(int x, int y, int height, int pos) {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			player.getOutStream().createFrame(254); // The packet ID
			player.getOutStream().writeByte(pos); // Position on Square(2 =
													// middle, 3
													// = west, 4 = east, 5 =
													// south,
													// 6 = north)
			player.getOutStream().writeWord(x); // X-Coord of Object
			player.getOutStream().writeWord(y); // Y-Coord of Object
			player.getOutStream().writeByte(height); // Height off Ground
		}
		return this;
	}

	// npc

	public PacketSender createArrow(int type, int id) {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			player.getOutStream().createFrame(254); // The packet ID
			player.getOutStream().writeByte(type); // 1=NPC, 10=Player
			player.getOutStream().writeWord(id); // NPC/Player ID
			player.getOutStream().write3Byte(0); // Junk
		}
		return this;
	}	

	public PacketSender checkObjectSpawn(int objectId, int objectX, int objectY, int face, int objectType) {
		if (player.distanceToPoint(objectX, objectY) < 60) {
			if (player.getOutStream() != null && player != null) {
				player.getOutStream().createFrame(85);
				player.getOutStream().writeByteC(
						objectY - player.getMapRegionY() * 8);
				player.getOutStream().writeByteC(
						objectX - player.getMapRegionX() * 8);
				player.getOutStream().createFrame(101);
				player.getOutStream()
						.writeByteC((objectType << 2) + (face & 3));
				player.getOutStream().writeByte(0);
				if (objectId != -1) { // removing
					player.getOutStream().createFrame(151);
					player.getOutStream().writeByteS(0);
					player.getOutStream().writeWordBigEndian(objectId);
					player.getOutStream().writeByteS(
							(objectType << 2) + (face & 3));
				}
				player.flushOutStream();
			}
			if (objectId > 0) {
				Region.addObject(objectId, objectX, objectX, player.heightLevel, objectType, face, false);
			}
		}
		return this;
	}
	
	public PacketSender createObjectSpawn(int objectId, int objectX, int objectY, int height, int face, int objectType) {
		if (player.getOutStream() == null) return this;
		if (player.heightLevel != height) {
			return this;
		}
		if (player.distanceToPoint(objectX, objectY) < 60) {
			if (player != null) {
				player.getOutStream().createFrame(85);
				player.getOutStream().writeByteC(objectY - player.getMapRegionY() * 8);
				player.getOutStream().writeByteC(objectX - player.getMapRegionX() * 8);
				player.getOutStream().createFrame(101);
				player.getOutStream().writeByteC((objectType << 2) + (face & 3));
				player.getOutStream().writeByte(0);
				if (objectId != -1) { // removing
					player.getOutStream().createFrame(151);
					player.getOutStream().writeByteS(0);
					player.getOutStream().writeWordBigEndian(objectId);
					player.getOutStream().writeByteS((objectType << 2) + (face & 3));
				}
				player.flushOutStream();
			}
		}
		return this;
	}

	/**
	 * Show option, attack, trade, follow etc
	 **/
	public String optionType = "null";

	public PacketSender showOption(int i, int l, String s, int a) {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			if (!optionType.equalsIgnoreCase(s)) {
				optionType = s;
				player.getOutStream().createFrameVarSize(104);
				player.getOutStream().writeByteC(i);
				player.getOutStream().writeByteA(l);
				player.getOutStream().writeString(s);
				player.getOutStream().endFrameVarSize();
				player.flushOutStream();
			}
		}
		return this;
	}

	/**
	 * sendSong(id);
	 */

	public PacketSender sendSong(int id) {
		if (player.getOutStream() == null) return this;
		if (player != null && id != -1) {
			player.getOutStream().createFrame(74);
			player.getOutStream().writeWordBigEndian(id);
		}
		return this;
	}

	/**
	 * sendQuickSong(id, delay); - used for things such as level up music
	 */

	public PacketSender sendQuickSong(int id, int songDelay) {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			player.getOutStream().createFrame(121);
			player.getOutStream().writeWordBigEndian(id);
			player.getOutStream().writeWordBigEndian(songDelay);
			player.flushOutStream();
		}
		return this;
	}

	/**
	 * sendSound(id, volume, delay);
	 */

	public PacketSender sendSound(int id, int type, int delay, int volume) {
		if (player.getOutStream() == null) return this;
		if (player != null && id != -1) {
			player.getOutStream().createFrame(174);
			player.getOutStream().writeWord(id);
			player.getOutStream().writeByte(type);
			player.getOutStream().writeWord(delay);
			player.getOutStream().writeWord(volume);
			player.flushOutStream();
		}
		return this;
	}

	/**
	 * Send Misc Songs
	 */

	public PacketSender sendSound(int id, int volume, int delay) {
		frame174(id, volume, delay);
		return this;
	}

	public PacketSender sendClearScreen() {
		if (player.getOutStream() == null) return this;
		if (player != null) {
			player.getOutStream().createFrame(219);
			player.flushOutStream();
		}
		return this;
	}

	public PacketSender createGroundItem(int itemID, int itemX, int itemY, int itemAmount) {
		if (player.getOutStream() == null) return this;
		player.getOutStream().createFrame(85);
		player.getOutStream().writeByteC(itemY - 8 * player.mapRegionY);
		player.getOutStream().writeByteC(itemX - 8 * player.mapRegionX);
		player.getOutStream().createFrame(44);
		player.getOutStream().writeWordBigEndianA(itemID);
		player.getOutStream().writeWord(itemAmount);
		player.getOutStream().writeByte(0);
		player.flushOutStream();
		return this;
	}
	
	public PacketSender createGroundItem(int itemID, int itemX, int itemY, int itemAmount, int height) {
		if (player.getOutStream() == null) return this;
		if (player.heightLevel != height) {
			return this;
		}
		player.getOutStream().createFrame(85);
		player.getOutStream().writeByteC(itemY - 8 * player.mapRegionY);
		player.getOutStream().writeByteC(itemX - 8 * player.mapRegionX);
		player.getOutStream().createFrame(44);
		player.getOutStream().writeWordBigEndianA(itemID);
		player.getOutStream().writeWord(itemAmount);
		player.getOutStream().writeByte(0);
		player.flushOutStream();
		return this;
	}


	/**
	 * Pickup Item
	 **/

	public PacketSender removeGroundItem(int itemID, int itemX, int itemY, int Amount) {
		if (player.getOutStream() == null) return this;
		if (player == null) {
			return this;
		}
		player.getOutStream().createFrame(85);
		player.getOutStream().writeByteC(itemY - 8 * player.mapRegionY);
		player.getOutStream().writeByteC(itemX - 8 * player.mapRegionX);
		player.getOutStream().createFrame(156);
		player.getOutStream().writeByteS(0);
		player.getOutStream().writeWord(itemID);
		player.flushOutStream();
		return this;
	}

}
