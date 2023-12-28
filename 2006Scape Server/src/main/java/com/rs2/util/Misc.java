package com.rs2.util;

import com.google.gson.Gson;
import com.rs2.game.players.Player;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.*;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    /**
     * Formats digits for integers.
     *
     * @param amount
     * @return
     */
    public static String format(final int num) {
        String string = Integer.toString(num);
        return string;
    }

    /**
     * Formats a number into a string with commas.
     */
    public static String formatValue(int value) {
        return new DecimalFormat("#, ###").format(value);
    }

    /** Formats digits for longs. */
    public static String format(final long num) {
        String string = Long.toString(num);
        return string;
    }
	
	    /** Formats a price for longs.
     * @param amount*/
    public static String formatPrice(final int amount) {
        if (amount >= 0 && amount < 1_000) {
            return "" + amount;
        }
        if (amount >= 1_000 && amount < 1_000_000) {
            return (amount / 1_000) + "K";
        }
        if (amount >= 1_000_000 && amount < 1_000_000_000) {
            return (amount / 1_000_000) + "M";
        }
        if (amount >= 1_000_000_000 && amount < Integer.MAX_VALUE) {
            return (amount / 1_000_000_000) + "B";
        }
        return "<col=fc2a2a>Lots!";
    }
	
	/**
     * @return 5.5 for example.
     */
    public static double roundDoubleToNearestOneDecimalPlace(double number) {
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(number));
    }

    /**
     * Shorted to 5.66 for example.
     */
    public static double roundDoubleToNearestTwoDecimalPlaces(double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(number));
    }

    public static double getDoubleRoundedUp(double doubleNumber) {
        return Math.ceil(doubleNumber);
    }

    public static double getDoubleRoundedDown(double doubleNumber) {
        return (double) ((int) doubleNumber);
    }

    public static String formatRunescapeStyle(long num) {
        boolean negative = false;
        if (num < 0) {
            num = -num;
            negative = true;
        }
        int length = String.valueOf(num).length();
        String number = Long.toString(num);
        String numberString = number;
        String end = "";
        if (length == 4) {
            numberString = number.substring(0, 1) + "k";
            //6400
            double doubleVersion = 0.0;
            doubleVersion = num / 1000.0;
            if (doubleVersion != getDoubleRoundedUp(doubleVersion)) {
                if (num - (1000 * getDoubleRoundedDown(doubleVersion)) > 100) {
                    numberString = number.substring(0, 1) + "." + number.substring(1, 2) + "k";
                }
            }
        } else if (length == 5) {
            numberString = number.substring(0, 2) + "k";
        } else if (length == 6) {
            numberString = number.substring(0, 3) + "k";
        } else if (length == 7) {
            String sub = number.substring(1, 2);
            if (sub.equals("0")) {
                numberString = number.substring(0, 1) + "m";
            } else {
                numberString = number.substring(0, 1) + "." + number.substring(1, 2) + "m";
            }
        } else if (length == 8) {
            end = "." + number.substring(2, 3);
            if (end.equals(".0")) {
                end = "";
            }
            numberString = number.substring(0, 2) + end + "m";
        } else if (length == 9) {
            end = "." + number.substring(3, 4);
            if (end.equals(".0")) {
                end = "";
            }
            numberString = number.substring(0, 3) + end + "m";
        } else if (length == 10) {
            numberString = number.substring(0, 4) + "m";
        } else if (length == 11) {
            numberString = number.substring(0, 2) + "." + number.substring(2, 5) + "b";
        } else if (length == 12) {
            numberString = number.substring(0, 3) + "." + number.substring(3, 6) + "b";
        } else if (length == 13) {
            numberString = number.substring(0, 4) + "." + number.substring(4, 7) + "b";
        }
        if (negative) {
            numberString = "-" + numberString;
        }
        return numberString;
    }

    public static String formatText(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                s = String.format("%s%s", Character.toUpperCase(s.charAt(0)),
                    s.substring(1));
            }
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                if (i + 1 < s.length()) {
                    s = String.format("%s%s%s", s.subSequence(0, i + 1),
                        Character.toUpperCase(s.charAt(i + 1)),
                        s.substring(i + 2));
                }
            }
        }
        return s.replace("_", " ");
    }

    public static String formatTextUnderscore(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                s = String.format("%s%s", Character.toUpperCase(s.charAt(0)),
                    s.substring(1));
            }
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                if (i + 1 < s.length()) {
                    s = String.format("%s%s%s", s.subSequence(0, i + 1),
                        Character.toUpperCase(s.charAt(i + 1)),
                        s.substring(i + 2));
                }
            }
        }
        return s.replace(" ", "_");
    }

    public static String optimizeTextNew(String text) {
        for (int index = 0; index < text.length(); index++) {
            if (index == 0) {
                text = String.format("%s%s", Character.toUpperCase(text.charAt(0)), text.substring(1));
            }
            if (!Character.isLetterOrDigit(text.charAt(index))) {
                if (index + 1 < text.length()) {
                    text = String.format("%s%s%s", text.subSequence(0, index + 1), Character.toUpperCase(text.charAt(index + 1)), text.substring(index + 2));
                }
            }
        }
        return text;
    }

    public static String getTotalAmount(int j) {
        if (j >= 10000 && j < 1000000) {
            return j / 1000 + "K";
        } else if (j >= 1000000 && j <= Integer.MAX_VALUE) {
            return j / 1000000 + "M";
        } else {
            return "" + j;
        }
    }

    public static String insertCommasToNumber(String number) {
        return number.length() < 4 ? number : insertCommasToNumber(number
            .substring(0, number.length() - 3))
            + ","
            + number.substring(number.length() - 3, number.length());
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
	
	/**
     * Format number into for example: 357,555
     */
    public static String formatNumber(long number) {
        // Do not use return NumberFormat.getIntegerInstance().format(number);. It is 9 times slower.
        String string = Long.toString(number);
        if (number < 1000) {
            return string;
        }
        if (number >= 1000 && number < 10000) {
            return string.substring(0, 1) + "," + string.substring(1);
        }
        if (number < 100000) {
            return string.substring(0, 2) + "," + string.substring(2);
        }
        if (number < 1000000) {
            return string.substring(0, 3) + "," + string.substring(3);
        }
        if (number < 10000000) {
            return string.substring(0, 1) + "," + string.substring(1, 4) + "," + string.substring(4, 7);
        }
        if (number < 100000000) {
            return string.substring(0, 2) + "," + string.substring(2, 5) + "," + string.substring(5, 8);
        }
        if (number < 1000000000) {
            return string.substring(0, 3) + "," + string.substring(3, 6) + "," + string.substring(6, 9);
        }
        if (number <= Integer.MAX_VALUE) {
            return string.substring(0, 1) + "," + string.substring(1, 4) + "," + string.substring(4, 7) + "," + string.substring(7, 10);
        }
        return string;
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
	
	 public static int toCyclesOrDefault(long time, int def, TimeUnit unit) {
        if (time > Integer.MAX_VALUE) {
            time = def;
        }
        return (int) TimeUnit.MILLISECONDS.convert(time, unit) / 600;
    }

    public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> sortEntries(Map<K, V> map) {
        List<Map.Entry<K, V>> sortedEntries = new ArrayList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(sortedEntries, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        return sortedEntries;
    }

    public static Map<String, Long> sortByComparator(Map<String, Long> unsortMap, final boolean ascending)
    {
        List<Map.Entry<String, Long>> list = new LinkedList<Map.Entry<String, Long>>(unsortMap.entrySet());
        // Sorting the list based on values
        list.sort(new Comparator<Map.Entry<String, Long>>() {
            public int compare(Map.Entry<String, Long> o1,
                               Map.Entry<String, Long> o2) {
                if (ascending) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
        // Maintaining insertion order with the help of LinkedList
        Map<String, Long> sortedMap = new LinkedHashMap<String, Long>();
        for (Map.Entry<String, Long> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static <T> List<T> jsonArrayToList(Path path, Class<T[]> clazz) {
        try {
            T[] collection = new Gson().fromJson(Files.newBufferedReader(path), clazz);
            return new ArrayList<T>(Arrays.asList(collection));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static String kOrMil(int amount) {
        if (amount < 100_000)
            return String.valueOf(amount);
        if (amount < 10_000_000)
            return amount / 1_000 + "K";
        else
            return amount / 1_000_000 + "M";
    }

    public static int[] convertRollRangeStringToIntArray(String input)
    {
        String[] strArray = input.split("-");
        int[] intArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }
	
	/**
     * Random instance, used to generate pseudo-random primitive types.
     */
    public static final Random RANDOM = new Random();
	
    /**
     * Finds out if a certain event should happen, and if it should, return
     * true;
     *
     * @param chance
     *            The chance of the event happening
     * @return If the event should happen
     */
    public static boolean percentageChance(int chance) {
        return RANDOM.nextInt(100) < chance;
    }

    public static String getFractionFromPercentage(float percentage) {
        return "1 / " + (int) Math.round((1 / percentage) * 100); //Was ceil is now round
    }

    public static int getDenominatorFromPercentage(float percentage) {
        return (int) Math.round((1 / percentage) * 100); //Was ceil is now round
    }
	
	public static String getCurrentServerTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        int hour = zonedDateTime.getHour();
        String hourPrefix = hour < 10 ? "0" + hour + "" : "" + hour + "";
        int minute = zonedDateTime.getMinute();
        String minutePrefix = minute < 10 ? "0" + minute + "" : "" + minute + "";
        return "" + hourPrefix + ":" + minutePrefix + "";
    }
    public static String getCurrentServerDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, h:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }
	
	/**
     * Gets the formatted time played.
     *
     * @return The time played formatted as a string.
     */
    public static String getFormattedPlayTime(Player player, long creationTime) {
        long different = System.currentTimeMillis() - creationTime;


        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

     /*   long days = (long) elapsedJoinDate / 86400; // 86,400
        long daysRemainder = (long) elapsedJoinDate - (days * 86400);
        long hours = (long) daysRemainder / 3600; // 3,600
        long hoursRemainder = (long) daysRemainder - (hours * 3600);
        long minutes = (long) hoursRemainder / 60; // 60
        long seconds = (long) hoursRemainder - (minutes * 60); // remainder*/

        return elapsedDays + "d " + elapsedHours + "h " + elapsedMinutes + "m " + elapsedSeconds + "s";

    }
	
    public static String getTimePlayed(long totalPlayTime) {
        final int sec = (int) (totalPlayTime / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
        return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
    }

    public static String getHoursPlayed(long totalPlayTime) {
        final int sec = (int) (totalPlayTime / 1000), h = sec / 3600;
        return (h < 10 ? "0" + h : h) + "h";
    }

    public static String asMinutesLeft(int ticksLeft) {
        long ms = ticksLeft * 600;
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(ms);
        String str = "";

        if (minutes > 0) {
            if (minutes > 1) {
                str = minutes + " mins";
            } else {
                str = "One min";
            }
        }

        return str;
    }

    public static String asSeconds(int ticksLeft) {
        long ms = ticksLeft * 600;
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(ms);
        String str = "";


        if (seconds > 1) {
            str = seconds + " seconds";
        } else {
            str = "One second";
        }

        return str;
    }

    public static int getMinutesPassed(long t) {
        int seconds = (int) ((t / 1000) % 60);
        int minutes = (int) (((t - seconds) / 1000) / 60);
        return minutes;
    }

    /**
     * Useful method for getting the stack trace where some code
     * was executed without throwing an exception.
     * This method basically makes it so that we don't have to throw new RuntimeException
     * in order to get a stack trace.
     * @return the stack trace
     */
    public static String getStackTrace() {
        final StringWriter writer = new StringWriter();
        new Exception("Stack trace").printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
	
	public static float getPercentageFromDecimal(float decimal) {
        return decimal * 100;
    }

    public static float getPercentageFromDenominator(float denominator) {
        return (1 / denominator) * 100;
    }

    public static float getDecimalFromFraction(int numerator, int denominator) {
        return (float) numerator / denominator;
    }

    public static float getDecimalFromDenominator(int denominator) {
        return (float) 1 / denominator;
    }

    /** Gets the date of server. */
    public static String getDate() {
        return new SimpleDateFormat("EE MMM dd yyyy").format(new Date());
    }

    /** Gets the date of server. */
    public static String getSimpleDate() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }
	
	public static String pluralOrNot(String word, int count) {
        String value = "";
        if (count == 0 || count > 1) {
            value = word + "s";
        } else {
            value = word;
        }
        return value;
    }

    public static float ticksToSeconds(int tick) {
        int ticksInMillis = tick * 600;
        float tickToSecs = ticksInMillis / 1000f;
        return tickToSecs;
    }

    public static float ticksToMinutes(int tick) {
        int ticksInMillis = tick * 600;
        float tickToMin = ticksInMillis / 60000f;
        return tickToMin;
    }

    public static float ticksToHours(int tick) {
        int ticksInMillis = tick * 600;
        float tickToHour = ticksInMillis / 3_600_000f;
        return tickToHour;
    }

    public static String convertLongToDateTime(long time) {
        Date date = new Date(time);
        //Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Format format = new SimpleDateFormat("dd MMM yyyy h:mm:ss a");
        return format.format(date);
    }

    public static String convertLongToTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("h:mm:ss a");
        return format.format(date);
    }

    public static String convertLongToDate(long time) {
        Date date = new Date(time);
        //Format format = new SimpleDateFormat("yyyy-MM-dd");
        Format format = new SimpleDateFormat("dd MMM yyyy");
        return format.format(date);
    }

    public static String convertLongToDuration(long time) {
        if (time < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(time);
        time -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append((days > 1 || days == 0) ? " days " : " day ");
        sb.append(hours);
        sb.append((hours > 1 || hours == 0) ? " hours " : " hour ");
        sb.append(minutes);
        sb.append((minutes > 1 || minutes == 0) ? " minutes " : " minute ");
        sb.append(seconds);
        sb.append((seconds > 1 || seconds == 0) ? " seconds" : " second");

        return (sb.toString());
    }

    public static String convertLongToShortDuration(long time, boolean useSeconds) {
        if (time < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }
        long days = TimeUnit.MILLISECONDS.toDays(time);
        time -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);


        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append("d ");
        sb.append(hours);
        sb.append("h ");
        sb.append(minutes);
        sb.append("m ");
        if (useSeconds) {
            sb.append(seconds);
            sb.append("s ");
        }


        return (sb.toString());
    }

    public static String convertSecondsToShortDuration(long time, boolean useSeconds) {
        if (time < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = (int)TimeUnit.SECONDS.toDays(time);
        long hours = TimeUnit.SECONDS.toHours(time) - (days *24);
        long minutes = TimeUnit.SECONDS.toMinutes(time) - (TimeUnit.SECONDS.toHours(time)* 60);
        long seconds = TimeUnit.SECONDS.toSeconds(time) - (TimeUnit.SECONDS.toMinutes(time) *60);


        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append("d ");
        sb.append(hours);
        sb.append("h ");
        sb.append(minutes);
        sb.append("m ");
        if (useSeconds) {
            sb.append(seconds);
            sb.append("s ");
        }

        return (sb.toString());
    }

    public static String convertSecondsToDuration(long time, boolean showDaysAndHours) {
        if (time < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = (int)TimeUnit.SECONDS.toDays(time);
        long hours = TimeUnit.SECONDS.toHours(time) - (days *24);
        long minutes = TimeUnit.SECONDS.toMinutes(time) - (TimeUnit.SECONDS.toHours(time)* 60);
        long seconds = TimeUnit.SECONDS.toSeconds(time) - (TimeUnit.SECONDS.toMinutes(time) *60);

        StringBuilder sb = new StringBuilder(64);
        if (showDaysAndHours) {
            sb.append(days);
            sb.append((days > 1 || days == 0) ? " days " : " day ");
            sb.append(hours);
            sb.append((hours > 1 || hours == 0) ? " hours " : " hour ");
            sb.append(minutes);
            sb.append((minutes > 1 || minutes == 0) ? " minutes " : " minute ");
            sb.append(seconds);
            sb.append((seconds > 1 || seconds == 0) ? " seconds" : " second");
        } else {
            sb.append(minutes);
            sb.append((minutes > 1 || minutes == 0) ? " minutes " : " minute ");
            sb.append(seconds);
            sb.append((seconds > 1 || seconds == 0) ? " seconds" : " second");
        }

        return (sb.toString());
    }
	
	public static int getMinutesElapsed(int minute, int hour, int day, int year) {
        Calendar i = Calendar.getInstance();

        if (i.get(1) == year) {
            if (i.get(6) == day) {
                if (hour == i.get(11)) {
                    return i.get(12) - minute;
                }
                return (i.get(11) - hour) * 60 + (59 - i.get(12));
            }

            int ela = (i.get(6) - day) * 24 * 60 * 60;
            return ela > 2147483647 ? 2147483647 : ela;
        }

        int ela = getElapsed(day, year) * 24 * 60 * 60;

        return ela > 2147483647 ? 2147483647 : ela;
    }

    public static int getDayOfYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int days = 0;
        int[] daysOfTheMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
            daysOfTheMonth[1] = 29;
        }
        days += c.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < daysOfTheMonth.length; i++) {
            if (i < month) {
                days += daysOfTheMonth[i];
            }
        }
        return days;
    }

    public static int getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public static int getElapsed(int day, int year) {
        if (year < 2013) {
            return 0;
        }

        int elapsed = 0;
        int currentYear = Misc.getYear();
        int currentDay = Misc.getDayOfYear();

        if (currentYear == year) {
            elapsed = currentDay - day;
        } else {
            elapsed = currentDay;

            for (int i = 1; i < 5; i++) {
                if (currentYear - i == year) {
                    elapsed += 365 - day;
                    break;
                } else {
                    elapsed += 365;
                }
            }
        }

        return elapsed;
    }

    public static boolean isWeekend() {
        int day = Calendar.getInstance().get(7);
        return (day == 1) || (day == 6) || (day == 7);
    }

    public static int getTicks(int seconds) {
        return (int) (seconds / 0.6);
    }

    public static int getSeconds(int ticks) {
        return (int) (ticks * 0.6);
    }
	public static String getServerUptime(long serverStartTime) {
		long currentTime = System.currentTimeMillis();
		long uptimeMillis = currentTime - serverStartTime;
	
		long seconds = (uptimeMillis / 1000) % 60;
		long minutes = (uptimeMillis / (1000 * 60)) % 60;
		long hours = (uptimeMillis / (1000 * 60 * 60)) % 24;
		long days = uptimeMillis / (1000 * 60 * 60 * 24);
	
		return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
	}
	
	public static double roundOneDecimal(double number_to_format) {
        DecimalFormat decimal_format = new DecimalFormat("#.#");
        return Double.parseDouble(decimal_format.format(number_to_format).replace(",", "."));
    }
    
    public static double round(double number_to_format) {
        DecimalFormat decimal_format = new DecimalFormat("#.##");
        return Double.parseDouble(decimal_format.format(number_to_format).replace(",", "."));
    }

    public static boolean rollDie(int dieSides, int chance) {
        return random(dieSides) < chance;
    }

    public static String capitalizeJustFirst(String str) {
        str = str.toLowerCase();
        if (str.length() > 1) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
        } else {
            return str.toUpperCase();
        }
        return str;
    }
	
}
