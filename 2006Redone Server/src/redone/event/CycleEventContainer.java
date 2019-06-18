package redone.event;

/**
 * The wrapper for our event
 * 
 * @author Stuart <RogueX>
 * @author Null++
 */

public class CycleEventContainer {

	/**
	 * Event owner
	 */
	private final Object owner;

	/**
	 * Is the event running or not
	 */
	private boolean isRunning;

	/**
	 * The amount of cycles per event execution
	 */
	private int tick;

	/**
	 * The actual event
	 */
	private final CycleEvent event;

	/**
	 * The current amount of cycles passed
	 */
	private int cyclesPassed;

	/**
	 * The event ID
	 */
	private final int eventID;

	/**
	 * Sets the event containers details
	 * 
	 * @param owner
	 *            , the owner of the event
	 * @param event
	 *            , the actual event to run
	 * @param tick
	 *            , the cycles between execution of the event
	 */
	public CycleEventContainer(int id, Object owner, CycleEvent event, int tick) {
		eventID = id;
		this.owner = owner;
		this.event = event;
		isRunning = true;
		cyclesPassed = 0;
		this.tick = tick;
	}

	/**
	 * Execute the contents of the event
	 */
	public void execute() {
		event.execute(this);
	}

	/**
	 * Stop the event from running
	 */
	public void stop() {
		isRunning = false;
		event.stop();
	}

	/**
	 * Does the event need to be ran?
	 * 
	 * @return true yes false no
	 */
	public boolean needsExecution() {
		if (!isRunning()) {
			return false;
		}
		if (++cyclesPassed >= tick) {
			cyclesPassed = 0;
			return true;
		}
		return false;
	}

	/**
	 * Returns the owner of the event
	 * 
	 * @return
	 */
	public Object getOwner() {
		return owner;
	}

	/**
	 * Is the event running?
	 * 
	 * @return true yes false no
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Returns the event id
	 * 
	 * @return id
	 */
	public int getID() {
		return eventID;
	}

	/**
	 * Returns the current cycle/tick.
	 * 
	 * @return
	 */
	public int getTick() {
		return tick;
	}

	/**
	 * Set the amount of cycles between the execution
	 * 
	 * @param tick
	 */
	public void setTick(int tick) {
		this.tick = tick;
	}

}
