package org.apollo.jagcached;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rs2.Constants;
import org.apollo.cache.IndexedFileSystem;
import org.apollo.net.update.HttpRequestWorker;
import org.apollo.net.update.JagGrabRequestWorker;
import org.apollo.net.update.OnDemandRequestWorker;
import org.apollo.net.update.RequestWorker;
import org.apollo.net.update.UpdateDispatcher;

/**
 * A class which manages the pool of request workers.
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class RequestWorkerPool {
	
	/**
	 * The number of threads per request type.
	 */
	private static final int THREADS_PER_REQUEST_TYPE = Runtime.getRuntime().availableProcessors();
	
	/**
	 * The number of request types.
	 */
	private static final int REQUEST_TYPES = 3;
	
	/**
	 * The executor service.
	 */
	private final ExecutorService service;
	
	/**
	 * A list of request workers.
	 */
	private final List<RequestWorker<?, ?>> workers = new ArrayList<RequestWorker<?, ?>>();
	
	/**
	 * Gets the update dispatcher.
	 *
	 * @return The update dispatcher.
	 */
	public static UpdateDispatcher getDispatcher() {
		return dispatcher;
	}
	
	/**
	 * The request worker pool.
	 */
	public RequestWorkerPool() {
		int totalThreads = REQUEST_TYPES * THREADS_PER_REQUEST_TYPE;
		service = Executors.newFixedThreadPool(totalThreads);
	}
	private static final UpdateDispatcher dispatcher = new UpdateDispatcher();

	/**
	 * Starts the threads in the pool.
	 * @throws Exception if the file system cannot be created.
	 */
	public void start() throws Exception {
		Path base = Paths.get(Constants.FILE_SYSTEM_DIR);
		for (int i = 0; i < THREADS_PER_REQUEST_TYPE; i++) {
			workers.add(new JagGrabRequestWorker(dispatcher, new IndexedFileSystem(base, true)));
			workers.add(new OnDemandRequestWorker(dispatcher, new IndexedFileSystem(base, true)));
			workers.add(new HttpRequestWorker(dispatcher, new IndexedFileSystem(base, true)));
		}
		
		for (RequestWorker<?, ?> worker : workers) {
			service.submit(worker);
		}
	}
	
	/**
	 * Stops the threads in the pool.
	 */
	public void stop() {
		for (RequestWorker<?, ?> worker : workers) {
			worker.stop();
		}
		
		service.shutdownNow();
	}

}
