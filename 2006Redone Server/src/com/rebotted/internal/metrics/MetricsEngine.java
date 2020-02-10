package com.rebotted.internal.metrics;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 10/02/2020 DMY - PoC, data needs constant compression for long-term metrics.
 * Psudo garbage collection really. I don't want to see these HashMaps become
 * more encumbersome, switch to POJO ORM?
 * 
 * @author RS-Emulators
 *
 */
public class MetricsEngine {

	private static MetricsEngine me;
	private HashMap<String, Long> counters = new HashMap<String, Long>();
	private HashMap<Long, Long> deltas = new HashMap<Long, Long>();
	private HashMap<Long, String> deltameta = new HashMap<Long, String>();
	private Long deltacalc = 0L;
	private int deltax = 0;
	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	private MetricsEngine() {
		/**
		 * PoC code - A concurrent thread to perform collation on the HashMaps.
		 */
		Runnable cleanup = new Runnable() {
			public void run() {
				Long ctime = System.currentTimeMillis();
				System.out.println("BEFORE deltas length: " + deltas.size()); // debug
				synchronized (deltas) { // probably not required
					deltas.forEach((a, b) -> { // a,b are HashMap<Long, Long>
												// deltas[]
						if (a < (ctime - 1000)) { // debug - 1 seconds
							System.out.println(a + " is too old!"); // There is
																	// a bug
																	// here.
																	// Caps out
																	// at 50
																	// iterations.
						}
					});
				}
				System.out.println("AFTER deltas length: " + deltas.size()); // debug
			}
		};
		scheduler.scheduleWithFixedDelay(cleanup, 30, 30, TimeUnit.SECONDS);
	}

	/**
	 * Trigger a metrics events via unique key.
	 * 
	 * @param key
	 *            A unique name for this metric
	 * @param time
	 *            The current system time.
	 */
	public void count(String key, Long time) {
		if (counters.containsKey(key)) {
			delta(key, time - counters.get(key)); // I cheat by never storing
													// the second timestamp,
													// only the product delta.
			counters.remove(key); // stop
			return;
		}
		counters.put(key, time); // start
	}

	/**
	 * Data integrity forced by private.
	 * 
	 * Data is psuedo normalised into two HashMaps, using the current time
	 * (ctime) as a primary key.
	 */
	private void delta(String key, Long delta) {
		long ctime = System.currentTimeMillis();
		deltas.put(ctime, delta); // 1:1 normalization
		deltameta.put(ctime, key); // 1:1 normalization
	}

	/**
	 * PoC code
	 */
	public Long getMetrics(int seconds) {
		Long ctime = System.currentTimeMillis();
		deltas.forEach((a, b) -> {
			if (a > ctime - (seconds * 1000)) {
				deltacalc += b;
				deltax += 1;
			}
		});
		if (deltacalc == 0 || deltax == 0) {
			return 0L;
		}
		return deltacalc / deltax;
	}

	public Long getMetricsForKey(String key, int seconds) {
		return 0L; // TODO
	}

	/**
	 * Global Construction hook
	 * 
	 * @return A static hook to the MetricsEngine instance.
	 */
	public static MetricsEngine getInstance() {
		if (me == null) { // Singleton 
			me = new MetricsEngine();
		}
		return me;
	}

}
