package com.rs2.game.shops;

import java.util.HashMap;

import com.rs2.game.content.randomevents.RandomEventHandler;
import com.rs2.game.players.Player;

/**
 * Shops
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class Shops {

	public enum Shop {
		 SHOP1(588, 2), SHOP2(550, 3), SHOP3(575, 4), SHOP4(2356, 5),
		 SHOP5(3796, 6), SHOP6(1860, 7), SHOP7(559, 9), SHOP8(562, 10),
		 SHOP9(581, 11), SHOP10(548, 12), SHOP11(554, 13), SHOP12(601, 14),
		 SHOP13(1301, 15), SHOP14(1039, 16), SHOP15(2353, 17), SHOP16(3166, 18),
		 SHOP17(2161, 19), SHOP18(2162, 20), SHOP19(600, 21), SHOP20(603, 22),
		 SHOP21(593, 23), SHOP22(545, 24), SHOP23(585, 25), SHOP24(2305, 26),
		 SHOP25(2307, 27), SHOP26(2304, 28), SHOP27(2306, 29), SHOP28(517, 30),
		 SHOP29(558, 31), SHOP30(576, 32), SHOP31(1369, 33), SHOP32(1038, 35),
		 SHOP33(1433, 36), SHOP34(584, 37), SHOP35(540, 38), SHOP36(2157, 39),
		 SHOP37(538, 40), SHOP38(1303, 41), SHOP39(578, 42), SHOP40(587, 43),
		 SHOP41(1398, 44), SHOP42(556, 45), SHOP43(1865, 46), SHOP44(543, 47),
		 SHOP45(2198, 48), SHOP46(580, 49), SHOP47(1862, 50), SHOP48(583, 51),
		 SHOP49(553, 52), SHOP50(461, 53), SHOP51(903, 54), SHOP551(2258, 55),
		 SHOP52(1435, 56), SHOP53(3800, 57), SHOP54(2623, 58), SHOP55(594, 59),
		 SHOP56(579, 60), SHOP57(2160, 61), SHOP58(2191, 61), SHOP59(589, 62),
		 SHOP60(549, 63), SHOP61(542, 64), SHOP62(3038, 65), SHOP63(544, 66),
		 SHOP64(541, 67), SHOP65(1434, 68), SHOP66(577, 69), SHOP67(539, 70),
		 SHOP68(1980, 71), SHOP69(546, 72), SHOP70(382, 73), SHOP71(3541, 74),
		 SHOP72(520, 75), SHOP73(521,75), SHOP74(1436, 76), SHOP75(590, 77),
		 SHOP76(971, 78), SHOP77(1917, 79), SHOP78(1040, 80), SHOP79(563, 81),
		 SHOP80(522, 82), SHOP81(523, 82), SHOP82(524, 83), SHOP83(525, 83),
		 SHOP84(526, 84), SHOP85(527, 84), SHOP86(2154, 85), SHOP87(1334, 86),
		 SHOP88(2352, 87), SHOP89(528, 88), SHOP90(529, 88), SHOP91(1254, 89),
		 SHOP92(2086, 90), SHOP93(3824, 91), SHOP94(1866, 92), SHOP95(1699, 93),
		 SHOP96(1282, 94), SHOP97(530, 95), SHOP98(531, 95), SHOP99(516, 96),
		 SHOP100(560, 97), SHOP101(471, 98), SHOP102(1208, 99), SHOP103(532, 100),
		 SHOP104(533,100), SHOP105(606, 101), SHOP106(3797, 101), SHOP107(534, 102),
		 SHOP108(535, 102), SHOP109(836, 103), SHOP110(551, 104), SHOP111(552, 104),
		 SHOP112(586, 105), SHOP113(564, 106), SHOP114(747, 107), SHOP115(573, 108),
		 SHOP116(1316, 108), SHOP117(1787, 110), SHOP118(1526, 112), SHOP119(568, 113),
		 SHOP120(1972, 226),
		 SHOP121(1083, 114), SHOP122(735, 115), SHOP123(793, 116), SHOP124(794, 116),
		 SHOP125(1079, 117), SHOP126(682, 119), SHOP127(683, 120), SHOP128(692, 121),
		 SHOP129(1658, 122), SHOP130(461, 123), SHOP131(904, 126), SHOP132(2152, 127),
		 SHOP133(2153, 128), SHOP134(2151, 129), SHOP135(2158, 130), SHOP136(2156, 131),
		 SHOP137(2159, 132), SHOP138(851, 133), SHOP139(602, 134), SHOP140(596, 135),
		 SHOP141(597, 136), SHOP142(1784, 137), SHOP143(2620, 138), SHOP144(2622, 139),
		 SHOP145(1778, 140), SHOP146(1782, 141), SHOP147(849, 142), SHOP148(970, 145),
		 SHOP149(555, 220), SHOP150(1688, 284);


		private final int npcId, shopId;

		public static HashMap<Integer, Shop> npc = new HashMap<Integer, Shop>();

		public static Shop forId(int id) {
			return npc.get(id);
		}

		static {
			for (Shop f : Shop.values())
				npc.put(f.getNpc(), f);
		}

		private Shop(int npcId, int shopId) {
			this.npcId = npcId;
			this.shopId = shopId;
		}

		public int getNpc() {
			return npcId;
		}

		public int getShop() {
			return shopId;
		}

	}

	public static void dialogueShop(Player c, int npcClick) {
		final Shop shops = Shop.forId(npcClick);
		if (shops == null)
			return;
		if (npcClick == shops.getNpc()) {
			c.getDialogueHandler().sendDialogues(1322, shops.getNpc());
		}
	}

	public static void openShop(Player c, int npcClickId) {
		final Shop shops = Shop.forId(npcClickId);
		if (shops == null)
			return;
		if (npcClickId == shops.getNpc()) {
			c.getShopAssistant().openShop(shops.getShop());
			RandomEventHandler.addRandom(c);
		}
	}
	
}
