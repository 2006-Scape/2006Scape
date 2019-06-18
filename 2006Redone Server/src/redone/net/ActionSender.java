package redone.net;

import java.text.DecimalFormat;

import redone.game.players.Client;
import redone.game.players.Player;
import redone.game.players.PlayerHandler;
import redone.util.Misc;
import redone.world.clip.Region;

public class ActionSender {

	private final Client player;

	public ActionSender(Client client) {
		this.player = client;
	}


	public ActionSender sendClan(String name, String message, String clan, int rights) {
		player.outStream.createFrameVarSizeWord(217);
		player.outStream.writeString(name);
		player.outStream.writeString(message);
		player.outStream.writeString(clan);
		player.outStream.writeWord(rights);
		player.outStream.endFrameVarSize();
		return this;
	}
	
	public ActionSender createPlayersObjectAnim(int X, int Y, int animationID, int tileObjectType, int orientation) {
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
	
	
	public ActionSender setInterfaceOffset(int x, int y, int id) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(70);
			player.getOutStream().writeWord(x);
			player.getOutStream().writeWordBigEndian(y);
			player.getOutStream().writeWordBigEndian(id);
			player.flushOutStream();
		}
		return this;
	}
	
	public ActionSender shakeScreen(int verticleAmount, int verticleSpeed,
			int horizontalAmount, int horizontalSpeed) {
		player.getOutStream().createFrame(35); // Creates frame 35.
		player.getOutStream().writeByte(verticleAmount);
		player.getOutStream().writeByte(verticleSpeed);
		player.getOutStream().writeByte(horizontalAmount);
		player.getOutStream().writeByte(horizontalSpeed);
		return this;
	}

	public ActionSender chatbox(int i1) {
		if (player.getOutStream() != null && player != null) {
			player.outStream.createFrame(218);
			player.outStream.writeWordBigEndianA(i1);
			player.updateRequired = true;
			player.appearanceUpdateRequired = true;
		}
		return this;
	}

	public ActionSender sendMessage(String s) {
		if (player.getOutStream() != null) {
			player.getOutStream().createFrameVarSize(253);
			player.getOutStream().writeString(s);
			player.getOutStream().endFrameVarSize();
		}
		return this;
	}

	public ActionSender setSidebarInterface(int menuId, int form) {
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

	public ActionSender flashSideBarIcon(int i1) {
		// Makes the sidebar Icons flash
		// Usage: i1 = 0 through -12 inorder to work
		player.outStream.createFrame(24);
		player.outStream.writeByteA(i1);
		player.updateRequired = true;
		player.appearanceUpdateRequired = true;
		return this;
	}

	public ActionSender createPlayerHints(int type, int id) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(254);
			player.getOutStream().writeByte(type);
			player.getOutStream().writeWord(id);
			player.getOutStream().write3Byte(0);
			player.flushOutStream();
		}
		return this;
	}

	public ActionSender createObjectHints(int x, int y, int height, int pos) {
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

	public ActionSender createProjectile(int x, int y, int offX, int offY,
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

	public ActionSender createProjectile2(int x, int y, int offX, int offY,
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
	public ActionSender object(int objectId, int objectX, int objectY, int face, int objectType) {
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

	public ActionSender object(int objectId, int objectX, int objectY, int objectH, int face, int objectType) {
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

	public ActionSender tempSong(int songID, int songID2) {
		player.outStream.createFrame(121);
		player.outStream.writeWordBigEndian(songID);
		player.outStream.writeWordBigEndian(songID2);
		player.flushOutStream();
		return this;
	}

	public ActionSender frame174(int sound, int vol, int delay) {
		player.outStream.createFrame(174);
		player.outStream.writeWord(sound);
		player.outStream.writeByte(vol);
		player.outStream.writeWord(delay);
		player.updateRequired = true;
		player.appearanceUpdateRequired = true;
		return this;
	}

	public ActionSender frame174(int id, int type, int delay, int volume) {
		if (player.outStream != null && player != null && id != -1) {
			player.outStream.createFrame(174);
			player.outStream.writeWord(id);
			player.outStream.writeWord(delay);
			player.outStream.writeWord(volume);
			player.flushOutStream();
		}
		return this;
	}

	public ActionSender writeWeight(int weight) {
		player.outStream.createFrame(240);
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		player.outStream.writeWord(Integer.valueOf(twoDForm.format(weight)));
		return this;
	}

	public ActionSender sendConfig(int id, int state) {
		if (player.getOutStream() != null && player != null) {
			if (state < 128) {

			}
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

	public ActionSender multiWay(int i1) {
		player.outStream.createFrame(61);
		player.outStream.writeByte(i1);
		player.updateRequired = true;
		player.setAppearanceUpdateRequired(true);
		return this;
	}

	public ActionSender sendColor(int id, int color) {
		if (player.getOutStream() != null && player != null) {
			player.outStream.createFrame(122);
			player.outStream.writeWordBigEndianA(id);
			player.outStream.writeWordBigEndianA(color);
		}
		return this;
	}

	public ActionSender sendCrashFrame() { // used for crashing cheat
												// clients
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(123);
			player.flushOutStream();
		}
		return this;
	}

	public ActionSender createStillGfx(int id, int x, int y, int height,
			int time) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPlayerAssistant().stillGfx(id, x, y,
									height, time);
						}
					}
				}
			}
		}
		return this;
	}

	public ActionSender object(int objectId, int objectX, int objectY,
			int objectType) {
		if (player.getOutStream() != null && player != null) {
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

	public ActionSender itemOnInterface(int interfaceChild, int zoom, int itemId) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(246);
			player.getOutStream().writeWordBigEndian(interfaceChild);
			player.getOutStream().writeWord(zoom);
			player.getOutStream().writeWord(itemId);
			player.flushOutStream();
		}
		return this;
	}

	public ActionSender setConfig(int id, int state) {
		player.outStream.createFrame(36);
		player.outStream.writeWordBigEndian(id);
		player.outStream.writeByte(state);
		return this;
	}

	public ActionSender sendLink(String s) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(187);
			player.getOutStream().writeString(s);
		}
		return this;
	}

	public ActionSender setSkillLevel(int skillNum, int currentLevel, int XP) {
		if (player.getOutStream() != null && player != null) {
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
	public ActionSender drawHeadicon(int i, int j, int k, int l) {
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

	public ActionSender createArrow(int x, int y, int height, int pos) {
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

	public ActionSender createArrow(int type, int id) {
		if (player != null) {
			player.getOutStream().createFrame(254); // The packet ID
			player.getOutStream().writeByte(type); // 1=NPC, 10=Player
			player.getOutStream().writeWord(id); // NPC/Player ID
			player.getOutStream().write3Byte(0); // Junk
		}
		return this;
	}	

	public ActionSender checkObjectSpawn(int objectId, int objectX, int objectY, int face, int objectType) {
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
	
	public ActionSender createObjectSpawn(int objectId, int objectX, int objectY, int height, int face, int objectType) {
		if (player.heightLevel != height) {
			return this;
		}
		if (player.distanceToPoint(objectX, objectY) < 60) {
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

	/**
	 * Show option, attack, trade, follow etc
	 **/
	public String optionType = "null";

	public ActionSender showOption(int i, int l, String s, int a) {
		// synchronized(c) {
		if (player.getOutStream() != null && player != null) {
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

	public ActionSender sendSong(int id) {
		if (player.getOutStream() != null && player != null && id != -1) {
			player.getOutStream().createFrame(74);
			player.getOutStream().writeWordBigEndian(id);
		}
		return this;
	}

	/**
	 * sendQuickSong(id, delay); - used for things such as level up music
	 */

	public ActionSender sendQuickSong(int id, int songDelay) {
		if (player.getOutStream() != null && player != null) {
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

	public ActionSender sendSound(int id, int type, int delay, int volume) {
		if (player.getOutStream() != null && player != null && id != -1) {
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

	public ActionSender sendSound(int id, int volume, int delay) {
		frame174(id, volume, delay);
		player.updateRequired = true;
		player.appearanceUpdateRequired = true;
		return this;
	}

	public ActionSender sendClearScreen() {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(219);
			player.flushOutStream();
		}
		return this;
	}

	public ActionSender createGroundItem(int itemID, int itemX, int itemY, int itemAmount) {
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
	
	public ActionSender createGroundItem(int itemID, int itemX, int itemY, int itemAmount, int height) {
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

	public ActionSender removeGroundItem(int itemID, int itemX, int itemY, int Amount) {
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
