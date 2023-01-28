package org.apollo.util;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * A utility class containing helper methods for various {@link Collection} objects.
 *
 * @author Ryley
 */
public final class CollectionUtil {

	/**
	 * Polls every element within the specified {@link Queue} and performs the specified {@link Consumer} event for
	 * each element.
	 *
	 * @param queue The {@link Queue} to poll elements from. Must not be {@code null}.
	 * @param consumer The {@link Consumer} to execute for each polled element. Must not be {@code null}.
	 */
	public static <T> void pollAll(Queue<T> queue, Consumer<T> consumer) {
		Preconditions.checkNotNull(queue, "Queue may not be null");
		Preconditions.checkNotNull(consumer, "Consumer may not be null");

		T element;
		while ((element = queue.poll()) != null) {
			consumer.accept(element);
		}
	}

	/**
	 * Suppresses the default public constructor to discourage normal instantiation outside of this class.
	 */
	private CollectionUtil() {

	}

}