package com.rs2.util;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Misc {

	public static String formatPlayerName(String str) {
		str = ucFirst(str);
		str.replace("_", " ");
		return str;
	}

	public static int random(final float range) {
		return (int) (java.lang.Math.random() * (range + 1));
	}

	// return a random number from 0 → range - 1
	public static int randomMinusOne(int range) {
		return (int) Math.random() * range;
	}

	public static double distance(int x1, int y1, int x2, int y2 ) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		return objectX - playerX <= distance && objectX - playerX >= -distance && objectY - playerY <= distance && objectY - playerY >= -distance;
	}

	public static String longToReportPlayerName(long l) {
		int i = 0;
		final char ac[] = new char[12];
		while (l != 0L) {
			final long l1 = l;
			l /= 37L;
			ac[11 - i++] = Misc.playerNameXlateTable[(int) (l1 - l * 37L)];
		}
		return new String(ac, 12 - i, i);
	}

	public static int random3(int range) {
		return (int) (java.lang.Math.random() * range);
	}

	public static int randomNumber(int range) {
		return (int) (Math.random() * range);
	}

	public static String longToPlayerName(long l) {
		int i = 0;
		char ac[] = new char[12];

		while (l != 0L) {
			long l1 = l;

			l /= 37L;
			ac[11 - i++] = xlateTable[(int) (l1 - l * 37L)];
		}
		return new String(ac, 12 - i, i);
	}

	public static final char playerNameXlateTable[] = { '_', 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', '[', ']', '/', '-', ' ' };

	public static String longToPlayerName2(long l) {
		int i = 0;
		char ac[] = new char[99];
		while (l != 0L) {
			long l1 = l;
			l /= 37L;
			ac[11 - i++] = playerNameXlateTable[(int) (l1 - l * 37L)];
		}
		return new String(ac, 12 - i, i);
	}

	public static String format(int num) {
		return NumberFormat.getInstance().format(num);
	}

	public static String ucFirst(String str) {
		str = str.toLowerCase();
		if (str.length() > 1) {
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		} else {
			return str.toUpperCase();
		}
		return str;
	}

	public static void print_debug(String str) {
		System.out.print(str);
	}

	public static void println_debug(String str) {
		System.out.println(str);
	}

	public static void print(String str) {
		System.out.print(str);
	}

	public static void println(String str) {
		System.out.println(str);
	}

	public static String Hex(byte data[]) {
		return Hex(data, 0, data.length);
	}

	public static String Hex(byte data[], int offset, int len) {
		String temp = "";
		for (int cntr = 0; cntr < len; cntr++) {
			int num = data[offset + cntr] & 0xFF;
			String myStr;
			if (num < 16) {
				myStr = "0";
			} else {
				myStr = "";
			}
			temp += myStr + Integer.toHexString(num) + " ";
		}
		return temp.toUpperCase().trim();
	}
	
	public static int random2(int range) {
		return (int) (java.lang.Math.random() * range + 1);
	}

	// return a random number from 0 → range (including range)
	public static int random(int range) {
		return (int) (java.lang.Math.random() * (++range));
	}

	// return a random number between & including the min/max values
	public static int random(int min, int max) {
		++max;
		return (int) Math.floor(Math.random() * (max - min)) + min;
	}

	public static int randomArrayItem(int[] arr) {
		return arr[(int) Math.floor(Math.random() * arr.length)];
	}

	public static int randomArrayListItem(ArrayList<Integer> arr) {
		int index = (int) Math.floor(Math.random() * arr.size());
		return arr.get(index);
	}

	public static long playerNameToInt64(String s) {
		long l = 0L;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			l *= 37L;
			if (c >= 'A' && c <= 'Z') {
				l += 1 + c - 65;
			} else if (c >= 'a' && c <= 'z') {
				l += 1 + c - 97;
			} else if (c >= '0' && c <= '9') {
				l += 27 + c - 48;
			}
		}
		while (l % 37L == 0L && l != 0L) {
			l /= 37L;
		}
		return l;
	}

	private static char decodeBuf[] = new char[4096];

	public static String textUnpack(byte packedData[], int size) {
		int idx = 0, highNibble = -1;
		for (int i = 0; i < size * 2; i++) {
			int val = packedData[i / 2] >> 4 - 4 * (i % 2) & 0xf;
			if (highNibble == -1) {
				if (val < 13) {
					decodeBuf[idx++] = xlateTable[val];
				} else {
					highNibble = val;
				}
			} else {
				decodeBuf[idx++] = xlateTable[(highNibble << 4) + val - 195];
				highNibble = -1;
			}
		}

		return new String(decodeBuf, 0, idx);
	}

	public static String optimizeText(String text) {
		char buf[] = text.toCharArray();
		boolean endMarker = true;
		for (int i = 0; i < buf.length; i++) {
			char c = buf[i];
			if (endMarker && c >= 'a' && c <= 'z') {
				buf[i] -= 0x20;
				endMarker = false;
			}
			if (c == '.' || c == '!' || c == '?') {
				endMarker = true;
			}
		}
		return new String(buf, 0, buf.length);
	}

	public static void textPack(Stream inStream, String text) {
		if (text.length() > 80) {
			text = text.substring(0, 80);
		}
		text = text.toLowerCase();

		int carryOverNibble = -1;
		inStream.currentOffset = 0;
		for (int idx = 0; idx < text.length(); idx++) {
			char c = text.charAt(idx);
			int tableIdx = 0;
			for (int i = 0; i < xlateTable.length; i++) {
				if (c == xlateTable[i]) {
					tableIdx = i;
					break;
				}
			}
			if (tableIdx > 12) {
				tableIdx += 195;
			}
			if (carryOverNibble == -1) {
				if (tableIdx < 13) {
					carryOverNibble = tableIdx;
				} else {
					inStream.buffer[inStream.currentOffset++] = (byte) tableIdx;
				}
			} else if (tableIdx < 13) {
				inStream.buffer[inStream.currentOffset++] = (byte) ((carryOverNibble << 4) + tableIdx);
				carryOverNibble = -1;
			} else {
				inStream.buffer[inStream.currentOffset++] = (byte) ((carryOverNibble << 4) + (tableIdx >> 4));
				carryOverNibble = tableIdx & 0xf;
			}
		}

		if (carryOverNibble != -1) {
			inStream.buffer[inStream.currentOffset++] = (byte) (carryOverNibble << 4);
		}
	}

	public static char xlateTable[] = { ' ', 'e', 't', 'a', 'o', 'i', 'h', 'n',
			's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b',
			'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-',
			'&', '*', '\\', '\'', '@', '#', '+', '=', '\243', '$', '%', '"',
			'[', ']' };

	public static int[] delta(int x1, int y1, int x2, int y2) {
		return new int[] {x2 - x1, y2 - y1};
	}

	public static int directionFromDelta(int x, int y) {
		for (int a = 0; a < directionDeltaX.length; a++) {
			if (directionDeltaX[a] == x && directionDeltaY[a] == y) {
				return xlateDirectionToClient[a];
			}
		}

		throw new IllegalArgumentException(String.format("Cannot find direction %d %d", x, y));
	}

	public static int direction(int srcX, int srcY, int x, int y) {
		double dx = (double) x - srcX, dy = (double) y - srcY;
		double angle = Math.atan(dy / dx);
		angle = Math.toDegrees(angle);
		if (Double.isNaN(angle)) {
			return -1;
		}
		if (Math.signum(dx) < 0) {
			angle += 180.0;
		}
		return (int) (((90 - angle) / 22.5 + 16) % 16);
		/*
		 * int changeX = x - srcX; int changeY = y - srcY; for (int j = 0; j <
		 * directionDeltaX.length; j++) { if (changeX == directionDeltaX[j] &&
		 * changeY == directionDeltaY[j]) return j; } return -1;
		 */
	}

	public static byte directionDeltaX[] = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
	public static byte directionDeltaY[] = new byte[] { 1, 1, 0, -1, -1, -1, 0,	1 };
	public static byte xlateDirectionToClient[] = new byte[] { 1, 2, 4, 7, 6,
			5, 3, 0 };

	public static String capitalize(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				s = String.format("%s%s", Character.toUpperCase(s.charAt(0)),
						s.substring(1));
			}
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				if (i + 1 < s.length()) {
					s = String.format("%s%s%s", s.subSequence(0, i + 1), Character.toUpperCase(s.charAt(i + 1)), s.substring(i + 2));
				}
			}
		}
		return s;
	}
}
