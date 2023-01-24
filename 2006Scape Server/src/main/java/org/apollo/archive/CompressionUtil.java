package org.apollo.archive;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

/**
 * A utility class for performing compression/decompression.
 *
 * @author Graham
 */
public final class CompressionUtil {

	/**
	 * Degzips the compressed array and places the results into the decompressed array.
	 *
	 * @param compressed The compressed array.
	 * @param decompressed The decompressed array.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void degzip(byte[] compressed, byte[] decompressed) throws IOException {
		try (DataInputStream is = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(compressed)))) {
			is.readFully(decompressed);
		}
	}

	/**
	 * Degzips <strong>all</strong> of the datain the specified {@link ByteBuffer}.
	 *
	 * @param compressed The compressed buffer.
	 * @return The decompressed array.
	 * @throws IOException If there is an error decompressing the buffer.
	 */
	public static byte[] degzip(ByteBuffer compressed) throws IOException {
		try (InputStream is = new GZIPInputStream(new ByteArrayInputStream(compressed.array()));
		     ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];

			while (true) {
				int read = is.read(buffer, 0, buffer.length);
				if (read == -1) {
					break;
				}

				out.write(buffer, 0, read);
			}

			return out.toByteArray();
		}
	}

	/**
	 * Gzips the specified array.
	 *
	 * @param uncompressed The uncompressed array.
	 * @return The compressed array.
	 * @throws IOException If there is an error compressing the array.
	 */
	public static byte[] gzip(byte[] uncompressed) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		try (DeflaterOutputStream os = new GZIPOutputStream(bout)) {
			os.write(uncompressed);
			os.finish();
			return bout.toByteArray();
		}
	}

	/**
	 * Default private constructor to prevent instantiation.
	 */
	private CompressionUtil() {

	}

}