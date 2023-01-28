package org.apollo.net.codec.update;

import io.netty.buffer.ByteBuf;

import org.apollo.cache.FileDescriptor;

/**
 * Represents a single 'on-demand' response.
 *
 * @author Graham
 */
public final class OnDemandResponse {

	/**
	 * The chunk data.
	 */
	private final ByteBuf chunkData;

	/**
	 * The chunk id.
	 */
	private final int chunkId;

	/**
	 * The file descriptor.
	 */
	private final FileDescriptor fileDescriptor;

	/**
	 * The file size.
	 */
	private final int fileSize;

	/**
	 * Creates the 'on-demand' response.
	 *
	 * @param fileDescriptor The file descriptor.
	 * @param fileSize The file size.
	 * @param chunkId The chunk id.
	 * @param chunkData The chunk data.
	 */
	public OnDemandResponse(FileDescriptor fileDescriptor, int fileSize, int chunkId, ByteBuf chunkData) {
		this.fileDescriptor = fileDescriptor;
		this.fileSize = fileSize;
		this.chunkId = chunkId;
		this.chunkData = chunkData;
	}

	/**
	 * Gets the chunk data.
	 *
	 * @return The chunk data.
	 */
	public ByteBuf getChunkData() {
		return chunkData;
	}

	/**
	 * Gets the chunk id.
	 *
	 * @return The chunk id.
	 */
	public int getChunkId() {
		return chunkId;
	}

	/**
	 * Gets the file descriptor.
	 *
	 * @return The file descriptor.
	 */
	public FileDescriptor getFileDescriptor() {
		return fileDescriptor;
	}

	/**
	 * Gets the file size.
	 *
	 * @return The file size.
	 */
	public int getFileSize() {
		return fileSize;
	}

}