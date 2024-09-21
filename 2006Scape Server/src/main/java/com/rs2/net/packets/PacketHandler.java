package com.rs2.net.packets;

import com.rs2.Constants;
import com.rs2.event.impl.ObjectFifthClickEvent;
import com.rs2.game.dialogues.Dialogue;
import com.rs2.game.players.Player;
import com.rs2.net.Packet;
import com.rs2.net.packets.impl.AttackPlayer;
import com.rs2.net.packets.impl.Bank10;
import com.rs2.net.packets.impl.Bank5;
import com.rs2.net.packets.impl.BankAll;
import com.rs2.net.packets.impl.BankX1;
import com.rs2.net.packets.impl.InterfaceX;
import com.rs2.net.packets.impl.ChallengePlayer;
import com.rs2.net.packets.impl.ChangeAppearance;
import com.rs2.net.packets.impl.ChangeRegions;
import com.rs2.net.packets.impl.Chat;
import com.rs2.net.packets.impl.ClickItem;
import com.rs2.net.packets.impl.ClickNPC;
import com.rs2.net.packets.impl.ClickObject;
import com.rs2.net.packets.impl.ClickTab;
import com.rs2.net.packets.impl.ClickingButtons;
import com.rs2.net.packets.impl.ClickingInGame;
import com.rs2.net.packets.impl.ClickingStuff;
import com.rs2.net.packets.impl.Commands;
import com.rs2.net.packets.impl.DropItem;
import com.rs2.net.packets.impl.FollowPlayer;
import com.rs2.net.packets.impl.IdleLogout;
import com.rs2.net.packets.impl.ItemClick2;
import com.rs2.net.packets.impl.ItemClick2OnGroundItem;
import com.rs2.net.packets.impl.ItemClick3;
import com.rs2.net.packets.impl.ItemOnGroundItem;
import com.rs2.net.packets.impl.ItemOnItem;
import com.rs2.net.packets.impl.ItemOnNpc;
import com.rs2.net.packets.impl.ItemOnObject;
import com.rs2.net.packets.impl.ItemOnPlayer;
import com.rs2.net.packets.impl.MagicOnFloorItems;
import com.rs2.net.packets.impl.MagicOnItems;
import com.rs2.net.packets.impl.MagicOnObject;
import com.rs2.net.packets.impl.MoveItems;
import com.rs2.net.packets.impl.PickupItem;
import com.rs2.net.packets.impl.PrivateMessaging;
import com.rs2.net.packets.impl.RemoveItem;
import com.rs2.net.packets.impl.Report;
import com.rs2.net.packets.impl.SilentPacket;
import com.rs2.net.packets.impl.Trade;
import com.rs2.net.packets.impl.Walking;
import com.rs2.net.packets.impl.WearItem;

public class PacketHandler {

	private static PacketType packetId[] = new PacketType[256];

	static {

		SilentPacket u = new SilentPacket();
		packetId[3] = u;
		packetId[202] = u;
		packetId[77] = u;
		packetId[86] = u;
		packetId[78] = u;
		packetId[36] = u;
		packetId[226] = u;
		packetId[246] = u;
		packetId[148] = u;
		packetId[183] = u;
		packetId[230] = u;
		packetId[136] = u;
		packetId[189] = u;
		packetId[152] = u;
		packetId[200] = u;
		packetId[85] = u;
		packetId[165] = u;
		packetId[238] = u;
		packetId[150] = u;
		packetId[120] = new ClickTab();
		packetId[14] = new ItemOnPlayer();
		packetId[40] = new Dialogue();
		ClickObject co = new ClickObject();
		packetId[132] = co;
		packetId[252] = co;
		packetId[70] = co;
		packetId[234] = co;
		packetId[57] = new ItemOnNpc();
		ClickNPC cn = new ClickNPC();
		packetId[72] = cn;
		packetId[131] = cn;
		packetId[155] = cn;
		packetId[17] = cn;
		packetId[21] = cn;
		packetId[218] = new Report();
		packetId[16] = new ItemClick2();
		packetId[75] = new ItemClick3();
		packetId[122] = new ClickItem();
		packetId[241] = new ClickingInGame();
		packetId[4] = new Chat();
		packetId[236] = new PickupItem();
		packetId[87] = new DropItem();
		packetId[185] = new ClickingButtons();
		packetId[130] = new ClickingStuff();
		packetId[103] = new Commands();
		packetId[214] = new MoveItems();
		packetId[237] = new MagicOnItems();
		packetId[181] = new MagicOnFloorItems();
		packetId[202] = new IdleLogout();
		AttackPlayer ap = new AttackPlayer();
		packetId[73] = ap;
		packetId[249] = ap;
		packetId[128] = new ChallengePlayer();
		packetId[139] = new Trade();
		packetId[39] = new FollowPlayer();
		packetId[41] = new WearItem();
		packetId[145] = new RemoveItem();
		packetId[117] = new Bank5();
		packetId[43] = new Bank10();
		packetId[129] = new BankAll();
		packetId[101] = new ChangeAppearance();
		packetId[35] = new MagicOnObject();
		PrivateMessaging pm = new PrivateMessaging();
		packetId[188] = pm;
		packetId[126] = pm;
		packetId[215] = pm;
		packetId[59] = pm;
		packetId[95] = pm;
		packetId[133] = pm;
		packetId[135] = new BankX1();
		packetId[208] = new InterfaceX();
		Walking w = new Walking();
		packetId[98] = w;
		packetId[164] = w;
		packetId[248] = w;
		packetId[53] = new ItemOnItem();
		packetId[192] = new ItemOnObject();
		packetId[25] = new ItemOnGroundItem();
		ChangeRegions cr = new ChangeRegions();
		packetId[121] = cr;
		packetId[210] = cr;
		packetId[253] = new ItemClick2OnGroundItem();
		packetId[228] = new ClickObject();
		// packetId[ContinueDialoguePacketHandler.CONTINUE] = new
		// ContinueDialoguePacketHandler();
	}

	/*public static void processPacket(Client c, int packetType, int packetSize) {
		if (packetType == -1) {
			return;
		}
		PacketType p = packetId[packetType];
		if (p != null) {
			try {
				// System.out.println("packet: " + packetType);
				p.processPacket(c, packetType, packetSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Unhandled packet type: " + packetType
					+ " - size: " + packetSize);
		}
	}*/
	
	public static void processPacket(Player player, Packet packet) {
		int packetType = packet.getOpcode();
		int packetSize = packet.getLength();
        PacketType p = packetId[packetType];
        if(p != null && packetType > 0 && packetType < 257) {
            if (Constants.sendServerPackets && player.playerRights == 3) {
                player.getPacketSender().sendMessage("PacketType: " + packetType + ". PacketSize: " + packetSize + ".");
            }
            try {
                p.processPacket(player, packet);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            player.disconnected = true;
            System.out.println(player.playerName + " is sending invalid PacketType: " + packetType + ". PacketSize: " + packetSize);
        }
    }

}
