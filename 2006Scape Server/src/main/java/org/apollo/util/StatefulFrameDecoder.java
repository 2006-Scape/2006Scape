package org.apollo.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.Objects;

/**
 * A stateful implementation of a {@link ByteToMessageDecoder} which may be extended and used by other classes. The
 * current state is tracked by this class and is a user-specified enumeration.
 *
 * The state may be changed by calling the {@link StatefulFrameDecoder#setState} method.
 *
 * The current state is supplied as a parameter in the {@link StatefulFrameDecoder#decode} and
 * {@link StatefulFrameDecoder#decodeLast} methods.
 *
 * This class is not thread safe: it is recommended that the state is only set in the decode methods overridden.
 *
 * @author Graham
 * @param <T> The state enumeration.
 */
public abstract class StatefulFrameDecoder<T extends Enum<T>> extends ByteToMessageDecoder {

	/**
	 * The current state.
	 */
	private T state;

	/**
	 * Creates the stateful frame decoder with the specified initial state.
	 *
	 * @param state The initial state.
	 * @throws NullPointerException If the state is {@code null}.
	 */
	public StatefulFrameDecoder(T state) {
		setState(state);
	}

	/**
	 * Sets a new state.
	 *
	 * @param state The new state.
	 * @throws NullPointerException If the state is {@code null}.
	 */
	public final void setState(T state) {
		this.state = Objects.requireNonNull(state, "State cannot be null.");
	}

	@Override
	protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		decode(ctx, in, out, state);
	}

	/**
	 * Decodes the received packets into a frame.
	 *
	 * @param ctx The current context of this handler.
	 * @param in The cumulative buffer, which may contain zero or more bytes.
	 * @param out The {@link List} of objects to pass forward through the pipeline.
	 * @param state The current state. The state may be changed by calling {@link #setState}.
	 * @throws Exception If there is an exception when decoding a frame.
	 */
	protected abstract void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out, T state) throws Exception;

}