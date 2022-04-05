package org.apollo.jagcached.resource;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;

/**
 * A {@link ResourceProvider} which provides additional hypertext resources.
 * @author Graham Edgecombe
 */
public final class HypertextResourceProvider extends ResourceProvider {
	
	/**
	 * The base directory from which documents are served.
	 */
	private final File base;
	
	/**
	 * Creates a new hypertext resource provider with the specified base
	 * directory.
	 * @param base The base directory.
	 */
	public HypertextResourceProvider(File base) {
		this.base = base;
	}

	@Override
	public boolean accept(String path) throws IOException {
		File f = new File(base, path);
		URI target = f.toURI().normalize();
		if (target.toASCIIString().startsWith(base.toURI().normalize().toASCIIString())) {
			if (f.isDirectory()) {
				f = new File(f, "index.html");
			}
			return f.exists();
		}
		return false;
	}

	@Override
	public ByteBuffer get(String path) throws IOException {
		File f = new File(base, path);
		if (f.isDirectory()) {
			f = new File(f, "index.html");
		}
		if (!f.exists()) {
			return null;
		}
		
		RandomAccessFile raf = new RandomAccessFile(f, "r");
		ByteBuffer buf;
		try {
			buf = raf.getChannel().map(MapMode.READ_ONLY, 0, raf.length());
		} finally {
			raf.close();
		}
		
		return buf;
	}

}
