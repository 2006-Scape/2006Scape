package com.rs2.tick;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * A class which schedules the execution of {@link Tick}s.
 * 
 * @author Graham
 */
public final class Scheduler {

	/**
	 * A list of active ticks.
	 */
	private final List<Tick> ticks = new ArrayList<Tick>();

	/**
	 * A queue of ticks that still need to be added.
	 */
	private final Queue<Tick> newTicks = new ArrayDeque<Tick>();

	/**
	 * Schedules the specified tick.
	 * 
	 * @param tick
	 *            The tick to schedule.
	 */
	public void schedule(final Tick tick) {
		synchronized (newTicks) {
			newTicks.add(tick);
		}
	}

	/**
	 * This method is called every cycle and executes, adds and removes
	 * {@link Tick}s.
	 */
	public void process() {
		synchronized (newTicks) {
			Tick tick;
			while ((tick = newTicks.poll()) != null)
				ticks.add(tick);
		}

		for (Iterator<Tick> it = ticks.iterator(); it.hasNext();) {
			Tick tick = it.next();
			try {
				if (!tick.tick())
					it.remove();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
}