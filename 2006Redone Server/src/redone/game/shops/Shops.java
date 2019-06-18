package redone.game.shops;

import java.util.HashMap;

import redone.game.content.randomevents.RandomEventHandler;
import redone.game.players.Client;

/**
 * Shops
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class Shops {

	public enum Shop {
		SHOP1(588, 2), SHOP2(550, 3), SHOP3(575, 4), SHOP4(2356, 5), SHOP5(
				3796, 6), SHOP6(1860, 7), SHOP7(559, 9), SHOP8(562, 10), SHOP9(
				581, 11), SHOP10(548, 12), SHOP11(554, 13), SHOP12(601, 14), SHOP13(
				1301, 15), SHOP14(1039, 16), SHOP15(2353, 17), SHOP16(3166, 18), SHOP17(
				2161, 19), SHOP18(2162, 20), SHOP19(600, 21), SHOP20(603, 22), SHOP21(
				593, 23), SHOP22(545, 24), SHOP23(585, 25), SHOP24(2305, 26), SHOP25(
				2307, 27), SHOP26(2304, 28), SHOP27(2306, 29), SHOP28(517, 30), SHOP29(
				558, 31), SHOP30(576, 32), SHOP31(1369, 33), SHOP32(1038, 35), SHOP33(
				1433, 36), SHOP34(584, 37), SHOP35(540, 38), SHOP36(2157, 39), SHOP37(
				538, 40), SHOP38(1303, 41), SHOP39(578, 42), SHOP40(587, 43), SHOP41(
				1398, 44), SHOP42(556, 45), SHOP43(1865, 46), SHOP44(543, 47), SHOP45(
				2198, 48), SHOP46(580, 49), SHOP47(1862, 50), SHOP48(583, 51), SHOP49(
				553, 52), SHOP50(461, 53), SHOP51(903, 54), SHOP551(2258, 55), SHOP52(
				1435, 56), SHOP53(3800, 57), SHOP54(2623, 58), SHOP55(594, 59), SHOP56(
				579, 60), SHOP57(2160, 61), SHOP58(2191, 61), SHOP59(589, 62), SHOP60(
				549, 63), SHOP61(542, 64), SHOP62(3038, 65), SHOP63(544, 66), SHOP64(
				541, 67), SHOP65(1434, 68), SHOP66(577, 69), SHOP67(539, 70), SHOP68(
				1980, 71), SHOP69(546, 72), SHOP70(382, 73), SHOP71(3541, 74), SHOP72(
				520, 75), SHOP73(1436, 76), SHOP74(590, 77), SHOP75(971, 78), SHOP76(
				1917, 79), SHOP77(1040, 80), SHOP78(563, 81), SHOP79(522, 82), SHOP80(
				524, 83), SHOP81(526, 84), SHOP82(2154, 85), SHOP83(1334, 86), SHOP84(
				2552, 87), SHOP85(528, 88), SHOP86(1254, 89), SHOP87(2086, 90), SHOP88(
				3824, 91), SHOP89(1866, 92), SHOP90(1699, 93), SHOP91(1282, 94), SHOP92(
				530, 95), SHOP93(516, 96), SHOP94(560, 97), SHOP95(471, 98), //SHOP96(1208, 99), 
				SHOP97(532, 100), SHOP98(3797, 101), SHOP99(534, 102), SHOP100(
				836, 103), SHOP101(551, 104), SHOP102(586, 105), SHOP103(564,
				106), SHOP104(747, 107), SHOP105(573, 108), SHOP106(1316, 108), SHOP107(
				547, 108), SHOP114(1787, 110), SHOP116(1526, 112), SHOP115(568,
				113), SHOP118(1083, 114), SHOP119(735, 115), SHOP120(793, 116), SHOP121(
				794, 116), SHOP122(1079, 117), SHOP123(682, 119), SHOP124(683,
				120), SHOP125(692, 121), SHOP126(1658, 122), SHOP127(461, 123),
		// SHOP128(537, 124),
		// SHOP129(536, 125),
		SHOP130(904, 126), SHOP131(2152, 127), SHOP132(2153, 128), SHOP133(
				2151, 129), SHOP134(2158, 130), SHOP135(2156, 131), SHOP136(
				2159, 132), SHOP137(851, 133), SHOP138(602, 134), SHOP139(596,
				135), SHOP140(597, 136), SHOP141(1784, 137), SHOP142(2620, 138), SHOP143(
				2622, 139), SHOP144(552, 88), SHOP145(1778, 140), SHOP146(1782,
				141), SHOP147(849, 142);

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

	public static void dialogueShop(Client c, int npcClick) {
		final Shop shops = Shop.forId(npcClick);
		if (shops == null)
			return;
		if (npcClick == shops.getNpc()) {
			c.getDialogueHandler().sendDialogues(1322, shops.getNpc());
		}
	}

	public static void openShop(Client c, int npcClickId) {
		final Shop shops = Shop.forId(npcClickId);
		if (shops == null)
			return;
		if (npcClickId == shops.getNpc()) {
			c.getShopAssistant().openShop(shops.getShop());
			RandomEventHandler.addRandom(c);
		}
	}
	
}
