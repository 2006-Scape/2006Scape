package com.rs2.net;

public class HostList {
//
//	private final Map<InetAddress, Long> clients;
//	
//	private HostList() {
//		clients = Collections.synchronizedMap(new HashMap<InetAddress, Long>());
//	}
//	
//	private static HostList list = new HostList();
//
//	public static HostList getHostList() {
//		return list;
//	}
//
//	private final Map<String, Integer> connections = new HashMap<String, Integer>();
//
//	public synchronized boolean add(Session session) {
//		if(!isConnectionOk(session)) {
//			return false;
//		}
//		String addr = ((InetSocketAddress) session.getChannel().remoteAddress()).getAddress().getHostAddress();
//		Integer amt = connections.get(addr);
//		if (amt == null) {
//			amt = 1;
//		} else {
//			amt += 1;
//		}
//		if (amt > Constants.IPS_ALLOWED || Connection.isIpBanned(addr)) {
//			return false;
//		} else {
//			connections.put(addr, amt);
//			return true;
//		}
//	}
//
//	public synchronized void remove(Session session) {
//		if (!session.isInList()) {
//			return;
//		}
//		String addr = ((InetSocketAddress) session.getChannel().remoteAddress())
//				.getAddress().getHostAddress();
//		Integer amt = connections.get(addr);
//		if (amt == null) {
//			return;
//		}
//		amt -= 1;
//		if (amt <= 0) {
//			connections.remove(addr);
//		} else {
//			connections.put(addr, amt);
//		}
//	}
//
//	private InetAddress getAddress(Session io) {
//		return ((InetSocketAddress) io.getChannel().remoteAddress()).getAddress();
//	}
//
//	/**
//	 * Method responsible for deciding if a connection is OK to continue
//	 * 
//	 * @param session
//	 *            The new session that will be verified
//	 * @return True if the session meets the criteria, otherwise false
//	 */
//	private boolean isConnectionOk(Session session) {
//		InetAddress addr = getAddress(session);
//		long now = System.currentTimeMillis();
//		if (clients.containsKey(addr)) {
//			long lastConnTime = clients.get(addr);
//
//			if (now - lastConnTime < Constants.CONNECTION_DELAY) {
//				System.out.println("["+addr+"] Session dropped (delay="+(now-lastConnTime)+"ms)");
//				return false;
//			} 
//		} 
//		clients.put(addr, now);
//		return true;
//	}
}
