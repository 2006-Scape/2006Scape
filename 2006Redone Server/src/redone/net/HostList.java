package redone.net;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.common.IoSession;

import redone.Connection;
import redone.Constants;

public class HostList {

	private static HostList list = new HostList();

	public static HostList getHostList() {
		return list;
	}

	private final Map<String, Integer> connections = new HashMap<String, Integer>();

	public synchronized boolean add(IoSession session) {
		String addr = ((InetSocketAddress) session.getRemoteAddress())
				.getAddress().getHostAddress();
		Integer amt = connections.get(addr);
		if (amt == null) {
			amt = 1;
		} else {
			amt += 1;
		}
		if (amt > Constants.IPS_ALLOWED || Connection.isIpBanned(addr)) {
			return false;
		} else {
			connections.put(addr, amt);
			return true;
		}
	}

	public synchronized void remove(IoSession session) {
		if (session.getAttribute("inList") != Boolean.TRUE) {
			return;
		}
		String addr = ((InetSocketAddress) session.getRemoteAddress())
				.getAddress().getHostAddress();
		Integer amt = connections.get(addr);
		if (amt == null) {
			return;
		}
		amt -= 1;
		if (amt <= 0) {
			connections.remove(addr);
		} else {
			connections.put(addr, amt);
		}
	}

}
