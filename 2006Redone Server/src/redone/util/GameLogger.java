package redone.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * Author: Nightleaf <colton.thompson@live.com>
 */

public class GameLogger {

	public static String getTime() {
		Calendar cal = new GregorianCalendar();
		String day, month, hour, minute;
		int YEAR = cal.get(Calendar.YEAR);
		int MONTH = cal.get(Calendar.MONTH) + 1;
		int DAY = cal.get(Calendar.DAY_OF_MONTH);
		int HOUR = cal.get(Calendar.HOUR_OF_DAY);
		int MIN = cal.get(Calendar.MINUTE);
		day = DAY < 10 ? "0" + DAY : "" + DAY;
		month = MONTH < 10 ? "0" + MONTH : "" + MONTH;
		hour = HOUR < 10 ? "0" + HOUR : "" + HOUR;
		minute = MIN < 10 ? "0" + MIN : "" + MIN;
		return "[" + YEAR + "/" + month + "/" + day + "] " + hour + ":" + minute + " ";
	}

	/**
	 * Formats a currency string just like RS.
	 * 
	 * @param cash
	 * @return
	 */
	public static String formatCurrency(int cash) {
		String s = String.valueOf(cash);
		for (int k = s.length() - 3; k > 0; k -= 3) {
			s = s.substring(0, k).replace(",", ".") + "," + s.substring(k);
		}
		if (s.length() > 8) {

			s = s.substring(0, s.length() - 8).replace(",", ".") + " million (" + s + ")";
		} else if (s.length() > 4) {
			s = s.substring(0, s.length() - 4) + "K (" + s + ")";
		}
		return " " + s;
	}

	/**
	 * Writes to a log file.
	 * 
	 * @param log
	 *            the log file.
	 * @param data
	 *            the data we are writing to the log.
	 */
	public static void writeLog(String player, String logType, String data) {
		File log = null;
		if (logType.equalsIgnoreCase("alchemy")) {
			log = new File("./Data/logs/alchlogs/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("shopselling")) {
			log = new File("./Data/logs/shopselling/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("shopbuying")) {
			log = new File("./Data/logs/shopbuying/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("dropitem")) {
			log = new File("./Data/logs/dropitem/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("clickitem")) {
			log = new File("./Data/logs/clickitem/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("pickupitem")) {
			log = new File("./Data/logs/pickupitem/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("commands")) {
			log = new File("./Data/logs/commands/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("pmsent")) {
			log = new File("./Data/logs/privatemessages/pmsent/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("pmrecieved")) {
			log = new File("./Data/logs/privatemessages/pmrecieved/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("tradesgave")) {
			log = new File("./Data/logs/trades/gave/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("tradesrecieved")) {
			log = new File("./Data/logs/trades/recieved/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("pkingkilled")) {
			log = new File("./Data/logs/pking/killed/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("pkingkiller")) {
			log = new File("./Data/logs/pking/killer/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("duelingkilled")) {
			log = new File("./Data/logs/dueling/killed/" + player + ".txt");
		} else if (logType.equalsIgnoreCase("duelingkiller")) {
			log = new File("./Data/logs/dueling/killer/" + player + ".txt");
		} else {
			log = new File("./Data/logs/" + player + ".txt");
		}

		if (!log.exists()) {
			try {
				log.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter logFile = new FileWriter(log, true);
			BufferedWriter bf = new BufferedWriter(logFile);
			bf.write(getTime() + "" + data + "");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
