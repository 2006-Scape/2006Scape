package com.rebotted.tick;

/**
 * Represents a periodic task that can be scheduled with a {@link Scheduler}.
 * 
 * @author Graham
 */
public abstract class Tick {

	/**
	 * The number of cycles between consecutive executions of this task.
	 */
	private final int delay;

	/**
	 * The current 'count down' value. When this reaches zero the task will be
	 * executed.
	 */
	private int countdown;

	/**
	 * A flag which indicates if this task is still running.
	 */
	private boolean running = true;

	/**
	 * Creates a new task with the specified delay.
	 * 
	 * @param delay
	 *            The number of cycles between consecutive executions of this
	 *            task.
	 * @throws IllegalArgumentException
	 *             if the {@code delay} is not positive.
	 */
	public Tick(int delay) {
		checkDelay(delay);
		this.delay = delay;
		this.countdown = delay;
	}

	/**
	 * Checks if the task is running.
	 * 
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Checks if the task is stopped.
	 * 
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isStopped() {
		return !running;
	}

	/**
	 * This method should be called by the scheduling class every cycle. It
	 * updates the {@link #countdown} and calls the {@link #execute()} method if
	 * necessary.
	 * 
	 * @return A flag indicating if the task is running.
	 */
	public boolean tick() {
		if (running && --countdown == 0) {
			execute();
			countdown = delay;
		}
		return running;
	}

	public int getCountdown() {
		return countdown;
	}

	/**
	 * Performs this task's action.
	 */
	protected abstract void execute();

	/**
	 * Changes the delay of this task.
	 * 
	 * @param delay
	 *            The number of cycles between consecutive executions of this
	 *            task.
	 * @throws IllegalArgumentException
	 *             if the {@code delay} is not positive.
	 */
	public void setDelay(int delay) {
		checkDelay(delay);
		delay = 0;
	}

	/**
	 * Stops this task.
	 * 
	 * @throws IllegalStateException
	 *             if the task has already been stopped.
	 */
	public void stop() {
		if (!checkStopped())
			return;
		running = false;
		onStop();
	}

	/**
	 * Stops this task. Does not run the onStop method.
	 * 
	 * @throws IllegalStateException
	 *             if the task has already been stopped.
	 */
	public void forceStop() {
		checkStopped();
		running = false;
	}

	/**
	 * Override this method for code which should be run when the task stops.
	 */
	public void onStop() {

	}

	/**
	 * Checks if the delay is negative and throws an exception if so.
	 * 
	 * @param delay
	 *            The delay.
	 * @throws IllegalArgumentException
	 *             if the delay is not positive.
	 */
	private void checkDelay(int delay) {
		if (delay < 0)
			throw new IllegalArgumentException("Delay must be positive.");
	}

	/**
	 * Checks if this task has been stopped and throws an exception if so.
	 * 
	 * @throws IllegalStateException
	 *             if the task has been stopped.
	 */
	private boolean checkStopped() {
		return running;
	}

}