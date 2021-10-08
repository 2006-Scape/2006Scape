package com.rs2.event;

import com.rs2.game.players.Player;

import java.util.function.Predicate;

/**
 * Represents a single subscriber for some {@link Event}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 *
 * @param <E> The type of event to the subscriber.
 */
@FunctionalInterface
public interface EventSubscriber<E extends Event> extends Predicate<E> {

	/**
	 * A handler method which executes event specific logic if and only if
	 * {@link #test(Event)} returns {@code true}.
	 *
	 * @param context The context of the event.
	 * @param player The player to subscribe the event for.
	 * @param event The event to subscribe.
	 */
	void subscribe(EventContext context, Player player, E event);

	@Override
	default boolean test(E event) {
		return true;
	}

}