package redone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import redone.game.players.Client;

/**
 * Connection Check Class
 * 
 * @author Ryan / Lmctruck30
 */

public class Connection {

	public static ArrayList<String> bannedIps = new ArrayList<String>();
	public static ArrayList<String> bannedNames = new ArrayList<String>();
	public static ArrayList<String> mutedIps = new ArrayList<String>();
	public static ArrayList<String> mutedNames = new ArrayList<String>();
	public static ArrayList<String> loginLimitExceeded = new ArrayList<String>();

	/**
	 * Adds the banned usernames and ips from the text file to the ban list
	 **/
	public static void initialize() {
		banUsers();
		banIps();
		muteUsers();
		muteIps();
	}

	/**
	 * Adding Name To List
	 */
	public static void addIpToLoginList(String IP) {
		loginLimitExceeded.add(IP);
	}

	/**
	 * Remove Ip From List
	 */
	public static void removeIpFromLoginList(String IP) {
		loginLimitExceeded.remove(IP);
	}

	/**
	 * Clear Name List
	 */
	public static void clearLoginList() {
		loginLimitExceeded.clear();
	}

	public static boolean checkLoginList(String IP) {
		loginLimitExceeded.add(IP);
		int num = 0;
		for (String ips : loginLimitExceeded) {
			if (IP.equals(ips)) {
				num++;
			}
		}
		if (num > 5) {
			return true;
		}
		return false;
	}

	public static void unMuteUser(String name) {
		mutedNames.remove(name);
		deleteFromFile("./Data/bans/UsersMuted.txt", name);
	}

	public static void unIPMuteUser(String name) {
		mutedIps.remove(name);
		deleteFromFile("./Data/bans/IpsMuted.txt", name);
	}

	/**
	 * Adding Ban IP
	 **/
	public static void addIpToBanList(String IP) {
		bannedIps.add(IP);
	}

	public static void addIpToMuteList(String IP) {
		mutedIps.add(IP);
		addIpToMuteFile(IP);
	}

	/**
	 * Removing Ban IP
	 **/
	public static void removeIpFromBanList(String IP) {
		bannedIps.remove(IP);
	}

	/**
	 * Contains Ban IP
	 **/
	public static boolean isIpBanned(String IP) {
		if (bannedIps.contains(IP)) {
			return true;
		}
		return false;
	}

	/**
	 * Adding banned username
	 **/
	public static void addNameToBanList(String name) {
		bannedNames.add(name.toLowerCase());
	}

	public static void addNameToMuteList(String name) {
		mutedNames.add(name.toLowerCase());
		addUserToFile(name);
	}

	/**
	 * Removing banned username
	 **/
	public static void removeNameFromBanList(String name) {
		bannedNames.remove(name.toLowerCase());
		deleteFromFile("./Data/bans/UsersBanned.txt", name);
	}

	public static void removeNameFromMuteList(String name) {
		bannedNames.remove(name.toLowerCase());
		deleteFromFile("./Data/bans/UsersMuted.txt", name);
	}

	public static void deleteFromFile(String file, String name) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(file));
			ArrayList<String> contents = new ArrayList<String>();
			while (true) {
				String line = r.readLine();
				if (line == null) {
					break;
				} else {
					line = line.trim();
				}
				if (!line.equalsIgnoreCase(name)) {
					contents.add(line);
				}
			}
			r.close();
			BufferedWriter w = new BufferedWriter(new FileWriter(file));
			for (String line : contents) {
				w.write(line, 0, line.length());
				w.newLine();
			}
			w.flush();
			w.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Contains banned username
	 **/
	public static boolean isNamedBanned(String name) {
		if (bannedNames.contains(name.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Reads all usernames from text file then adds them all to the ban list
	 **/
	public static void banUsers() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"./Data/bans/UsersBanned.txt"));
			String data = null;
			try {
				while ((data = in.readLine()) != null) {
					addNameToBanList(data);
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void muteUsers() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"./Data/bans/UsersMuted.txt"));
			String data = null;
			try {
				while ((data = in.readLine()) != null) {
					mutedNames.add(data);
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads all the Ips from text file then adds them all to ban list
	 **/
	public static void banIps() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"./Data/bans/IpsBanned.txt"));
			String data = null;
			try {
				while ((data = in.readLine()) != null) {
					addIpToBanList(data);
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void muteIps() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"./Data/bans/IpsMuted.txt"));
			String data = null;
			try {
				while ((data = in.readLine()) != null) {
					mutedIps.add(data);
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the username into the text file - when using the ::ban playername
	 * command
	 **/
	public static void addNameToFile(String Name) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"./Data/bans/UsersBanned.txt", true));
			try {
				out.newLine();
				out.write(Name);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addUserToFile(String Name) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"./Data/bans/UsersMuted.txt", true));
			try {
				out.newLine();
				out.write(Name);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the IP into the text file - use ::ipban username
	 **/
	public static void addIpToFile(String Name) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"./Data/bans/IpsBanned.txt", true));
			try {
				out.newLine();
				out.write(Name);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addIpToMuteFile(String Name) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"./Data/bans/IpsMuted.txt", true));
			try {
				out.newLine();
				out.write(Name);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isMuted(Client c) {
		return mutedNames.contains(c.playerName.toLowerCase())
				|| mutedIps.contains(c.connectedFrom);
	}

}
