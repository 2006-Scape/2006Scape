package redone.event;

/**
 * Represents a periodic task that can be scheduled with a {@link TaskScheduler}
 * .
 * 
 * @author Graham
 */
public abstract class Task {

	/**
	 * The number of cycles between consecutive executions of this task.
	 */
	private final int delay;

	/**
	 * A flag which indicates if this task should be executed once immediately.
	 */
	private final boolean immediate;

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
	 * Creates a new task with a delay of 1 cycle.
	 */
	public Task() {
		this(1);
	}

	/**
	 * Creates a new task with a delay of 1 cycle and immediate flag.
	 * 
	 * @param immediate
	 *            A flag that indicates if for the first execution there should
	 *            be no delay.
	 */
	public Task(boolean immediate) {
		this(1, immediate);
	}

	/**
	 * Creates a new task with the specified delay.
	 * 
	 * @param delay
	 *            The number of cycles between consecutive executions of this
	 *            task.
	 * @throws IllegalArgumentException
	 *             if the {@code delay} is not positive.
	 */
	public Task(int delay) {
		this(delay, false);
	}

	/**
	 * Creates a new task with the specified delay and immediate flag.
	 * 
	 * @param delay
	 *            The number of cycles between consecutive executions of this
	 *            task.
	 * @param immediate
	 *            A flag which indicates if for the first execution there should
	 *            be no delay.
	 * @throws IllegalArgumentException
	 *             if the {@code delay} is not positive.
	 */
	public Task(int delay, boolean immediate) {
		checkDelay(delay);
		this.delay = delay;
		countdown = delay;
		this.immediate = immediate;
	}

	/**
	 * Checks if this task is an immediate task.
	 * 
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isImmediate() {
		return immediate;
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
		checkStopped();
		running = false;
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
		if (delay <= 0) {
			throw new IllegalArgumentException("Delay must be positive.");
		}
	}

	/**
	 * Checks if this task has been stopped and throws an exception if so.
	 * 
	 * @throws IllegalStateException
	 *             if the task has been stopped.
	 */
	private void checkStopped() {
		if (!running) {
			throw new IllegalStateException();
		}
	}

}
