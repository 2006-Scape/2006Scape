package redone.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles all of our cycle based events
 * 
 * @author Stuart <RogueX>
 * @author Null++
 */
public class CycleEventHandler {

	/**
	 * The instance of this class
	 */
	private static CycleEventHandler instance;

	/**
	 * Returns the instance of this class
	 * 
	 * @return
	 */
	public static CycleEventHandler getSingleton() {
		if (instance == null) {
			instance = new CycleEventHandler();
		}
		return instance;
	}

	/**
	 * Holds all of our events currently being ran
	 */
	private final List<CycleEventContainer> events;

	/**
	 * Creates a new instance of this class
	 */
	public CycleEventHandler() {
		events = new ArrayList<CycleEventContainer>();
	}

	/**
	 * Add an event to the list
	 * 
	 * @param id
	 * @param owner
	 * @param event
	 * @param cycles
	 */
	public void addEvent(int id, Object owner, CycleEvent event, int cycles) {
		events.add(new CycleEventContainer(id, owner, event, cycles));
	}

	/**
	 * Add an event to the list
	 * 
	 * @param owner
	 * @param event
	 * @param cycles
	 */
	public void addEvent(Object owner, CycleEvent event, int cycles) {
		events.add(new CycleEventContainer(-1, owner, event, cycles));
	}

	/**
	 * Execute and remove events
	 */
	public void process() {
		List<CycleEventContainer> eventsCopy = new ArrayList<CycleEventContainer>(
				events);
		List<CycleEventContainer> remove = new ArrayList<CycleEventContainer>();
		for (CycleEventContainer c : eventsCopy) {
			if (c != null) {
				if (c.needsExecution() && c.isRunning()) {
					c.execute();
					if (!c.isRunning()) {
						remove.add(c);

					}
				}
			}
		}
		for (CycleEventContainer c : remove) {
			events.remove(c);
		}
	}

	/**
	 * Returns the amount of events currently running
	 * 
	 * @return amount
	 */
	public int getEventsCount() {
		return events.size();
	}

	/**
	 * Stops all events for a specific owner and id
	 * 
	 * @param owner
	 */
	public void stopEvents(Object owner) {
		for (CycleEventContainer c : events) {
			if (c.getOwner() == owner) {
				c.stop();
			}
		}
	}

	/**
	 * Stops all events for a specific owner and id
	 * 
	 * @param owner
	 * @param id
	 */
	public void stopEvents(Object owner, int id) {
		for (CycleEventContainer c : events) {
			if (c.getOwner() == owner && id == c.getID()) {
				c.stop();
			}
		}
	}

	/**
	 * Stops all events for a specific owner and id
	 * 
	 * @param id
	 */
	public void stopEvents(int id) {
		for (CycleEventContainer c : events) {
			if (id == c.getID()) {
				c.stop();
			}
		}
	}

}
