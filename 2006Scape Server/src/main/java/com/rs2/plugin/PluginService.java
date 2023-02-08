package com.rs2.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rs2.Constants;
import com.rs2.event.EventSubscriber;
import com.rs2.game.players.Player;
import com.rs2.util.LoggerUtils;

/**
 * The service that services plugins.
 * 
 * @author Vult-R
 */
public final class PluginService {

	/**
	 * The single logger for this class.
	 */
	private static final Logger logger = LoggerUtils.getLogger(PluginService.class);

	/**
	 * The list of subscribers registered to the server.
	 */
	private static final List<EventSubscriber<?>> subscribers = new ArrayList<>();

	/**
	 * Loads the plugins.
	 */
	public void load() {
		try {
			Collection<EventSubscriber<?>> plugins = findPlugins();

			plugins.stream().forEach(it -> register(it));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "A problem was encountered while trying to load plugins.", e);
		}
		logger.info("Loaded: " + subscribers.size() + " plugins.");
	}
	
	/**
	 * Finds plugins in a given directory.
	 *
	 */
	private Collection<EventSubscriber<?>> findPlugins() throws IOException {
		return findPlugins(new File("./plugins"));
	}
	
	/**
	 * Finds plugins in a specified directory.
	 *
	 * @param dir
	 * 		The directory to check for plugins.
	 * 
	 * @return The collection of plugin data.
	 */
	private Collection<EventSubscriber<?>> findPlugins(File dir) {
		Collection<EventSubscriber<?>> plugins = new ArrayList<>();
		for (File file : Objects.requireNonNull(dir.listFiles())) {
			String base = file.getPath();

			base = base.replace("\\", ".");

			base = base.replace("/", ".");

			base = base.replace("..plugins.", "");

			base = base.replace(".kt", "");

			base = base.replace(".java", "");

			if (Constants.SERVER_DEBUG) {
				System.out.println(base);
			}

			if (!file.isDirectory()) {
				try {
					Class<?> clazz = Class.forName(base);

					if (EventSubscriber.class.isAssignableFrom(clazz) && !Modifier.isInterface(clazz.getModifiers()) && !Modifier.isAbstract(clazz.getModifiers())) {
						final EventSubscriber<?> sub = (EventSubscriber<?>) clazz.newInstance();

						plugins.add(sub);
					}
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}

			} else {
				plugins.addAll(findPlugins(file));
			}

		}
		return Collections.unmodifiableCollection(plugins);
	}
	
	/**
	 * Assigns a plugin to a subscriber
	 */
	private void register(EventSubscriber<?> subscriber) {

		Player.provideSubscriber(subscriber);

		subscribers.add(subscriber);

	}

	/**
	 * Reloads plugins.
	 */
	public void reload() {
		throw new UnsupportedOperationException("This is currently not supported.");
	}

	/**
	 * Gets the list of subscriber registered to the server.
	 */
	public List<EventSubscriber<?>> getSubscribers() {
		return subscribers;
	}

}
