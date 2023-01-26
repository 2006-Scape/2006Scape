package org.apollo.net.codec.game;

/**
 * An enumeration with the different states the {@link GamePacketDecoder} can be in.
 *
 * @author Graham
 */
public enum GameDecoderState {

	/**
	 * The game length state waits for the packet length. Once it has been received, it sets the state to the payload
	 * state.
	 */
	GAME_LENGTH,

	/**
	 * The game opcode state waits for an encrypted opcode. It decrypts it, and will either set the next state to the
	 * length (if the packet is variably- sized) or the payload (if it is not variably-sized) state.
	 */
	GAME_OPCODE,

	/**
	 * The payload state will wait for the whole packet to be received. Then, it will pass a {@link GamePacket} object
	 * to Netty and reset the state back to the game opcode state, ready for the next packet.
	 */
	GAME_PAYLOAD;

}