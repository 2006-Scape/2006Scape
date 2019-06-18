package redone.net;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.mina.common.IoFilter;
import org.apache.mina.common.IoFilterAdapter;
import org.apache.mina.common.IoSession;

/**
 * A {@link IoFilter} which blocks connections from connecting at a rate faster
 * than the specified interval.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 */
public class ConnectionThrottleFilter extends IoFilterAdapter {

	private long allowedInterval;
	private final Map<InetAddress, Long> clients;
	private final Map<InetAddress, Integer> counts;
	private final Set<InetAddress> connectedAddresses;

	/**
	 * Constructor that takes in a specified wait time.
	 * 
	 * @param allowedInterval
	 *            The number of milliseconds a client is allowed to wait before
	 *            making another successful connection
	 */
	public ConnectionThrottleFilter(long allowedInterval) {
		this.allowedInterval = allowedInterval;
		clients = Collections.synchronizedMap(new HashMap<InetAddress, Long>());
		counts = Collections
				.synchronizedMap(new HashMap<InetAddress, Integer>());
		connectedAddresses = new HashSet<InetAddress>();
	}

	/**
	 * Sets the interval between connections from a client. This value is
	 * measured in milliseconds.
	 * 
	 * @param allowedInterval
	 *            The number of milliseconds a client is allowed to wait before
	 *            making another successful connection
	 */
	public void setAllowedInterval(long allowedInterval) {
		this.allowedInterval = allowedInterval;
	}

	public void delayClient(IoSession session, int delay) {
		long d = System.currentTimeMillis() - delay;
		clients.put(getAddress(session), d);
	}

	private InetAddress getAddress(IoSession io) {
		return ((InetSocketAddress) io.getRemoteAddress()).getAddress();
	}

	/**
	 * Method responsible for deciding if a connection is OK to continue
	 * 
	 * @param session
	 *            The new session that will be verified
	 * @return True if the session meets the criteria, otherwise false
	 */
	public boolean isConnectionOk(IoSession session) {
		InetAddress addr = getAddress(session);
		long now = System.currentTimeMillis();
		if (clients.containsKey(addr)) {
			long lastConnTime = clients.get(addr);

			if (now - lastConnTime < allowedInterval) {
				int c = 0;
				if (!counts.containsKey(addr)) {
					counts.put(addr, 0);
				} else {
					c = counts.get(addr) + 1;
				}
				if (c >= 350) {

					c = 0;
				}
				counts.put(addr, c);
				// Logger.err("["+host+"] Session dropped (delay="+(now-lastConnTime)+"ms)");
				return false;
			} else {
				clients.put(addr, now);
				return true;
			}
		} else {
			clients.put(addr, now);
			return true;
		}
	}

	public void closedSession(IoSession io) {
		connectedAddresses.remove(getAddress(io));
	}

	public void acceptedLogin(IoSession io) {
		connectedAddresses.add(getAddress(io));
	}

	public boolean isConnected(IoSession io) {
		return connectedAddresses.contains(getAddress(io));
	}

	public int[] getSizes() {
		return new int[] { clients.size(), counts.size(),
				connectedAddresses.size() };
	}

	public void connectionOk(IoSession io) {
		counts.remove(getAddress(io));
	}

	@Override
	public void sessionCreated(NextFilter nextFilter, IoSession session)
			throws Exception {
		if (!isConnectionOk(session)) {
			session.close();
			return;
		}
		nextFilter.sessionCreated(session);
	}
}
