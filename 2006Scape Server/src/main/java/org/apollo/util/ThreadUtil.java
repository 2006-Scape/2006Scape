package org.apollo.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A static utility class which provides ease of use functionality for {@link Thread}s
 *
 * @author Ryley
 * @author Major
 */
public final class ThreadUtil {

	/**
	 * Returns the amount of available processors available to the Java virtual machine.
	 */
	public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

	/**
	 * A {@link Logger} used to debug messages to the console.
	 */
	private static final Logger logger = Logger.getLogger(ThreadUtil.class.getSimpleName());

	/**
	 * The default {@link UncaughtExceptionHandler} which raises an error from the logger with the exception and name
	 * of
	 * the specified thread the exception occurred in.
	 */
	private static final UncaughtExceptionHandler DEFAULT_EXCEPTION_HANDLER =
		(thread, exception) -> logger.log(Level.SEVERE, "Exception in thread " + thread.getName(), exception);

	/**
	 * Builds a {@link ThreadFactory} using the specified {@code String} name-format, normal thread priority and the
	 * default {@link UncaughtExceptionHandler}, which simply logs exception messages.
	 *
	 * @param name The name-format used when creating threads. Must not be {@code null}.
	 * @return The {@link ThreadFactory}. Will never be {@code null}.
	 */
	public static ThreadFactory create(String name) {
		return create(name, Thread.NORM_PRIORITY, DEFAULT_EXCEPTION_HANDLER);
	}

	/**
	 * Builds a {@link ThreadFactory} using the specified {@code String} name-format, priority and the
	 * {@link #DEFAULT_EXCEPTION_HANDLER}.
	 *
	 * @param name The name-format used when creating threads. Must not be {@code null}.
	 * @param priority The priority used when creating threads. Must be {@code 1 <= priority <= 10}.
	 * @return The {@link ThreadFactory}. Will never be {@code null}.
	 */
	public static ThreadFactory create(String name, int priority) {
		return create(name, priority, DEFAULT_EXCEPTION_HANDLER);
	}

	/**
	 * Creates a {@link ThreadFactory} using the specified {@code String} name-format, priority and
	 * {@link UncaughtExceptionHandler}.
	 *
	 * @param name The name-format used when creating threads. Must not be {@code null}.
	 * @param priority The priority used when creating threads. Must be {@code 1 <= priority <= 10}.
	 * @param handler The {@link UncaughtExceptionHandler} used when creating threads. Must not be {@code null}.
	 * @return The {@link ThreadFactory}. Will never be {@code null}.
	 */
	public static ThreadFactory create(String name, int priority, UncaughtExceptionHandler handler) {
		Objects.requireNonNull(name, "ThreadFactory name must not be null.");
		Objects.requireNonNull(handler, "UncaughtExceptionHandler must not be null.");

		ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
		builder.setNameFormat(name);
		builder.setPriority(priority);
		builder.setUncaughtExceptionHandler(handler);

		return builder.build();
	}

	/**
	 * Sole private constructor to prevent instantiation.
	 */
	private ThreadUtil() {

	}

}