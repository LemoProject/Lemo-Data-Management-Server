/**
 * File BideTask.java
 * Date 22.04.2013
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.processing.async;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * File ThreadPool.java Date 22.04.2013 Project Lemo Learning Analytics Copyright TODO (INSERT COPYRIGHT)
 */

/**
 * Threadpool to Manage that every user only can start one thread with the
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class BideThreadPool {

	private static final int MAX_THREADS = 10;

	// TODO use enum singleton pattern
	private static BideThreadPool INSTANCE = null;

	private ThreadPoolTimeoutThread resultTimeoutThread;
	// TODO use own thread factory to set low priority!
	private ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

	private Map<String, BideTask> tasks = Collections.synchronizedMap(new HashMap<String, BideTask>());

	private BideThreadPool() {
		// TODO load timeout from config
		long computationTimeout = 5000;
		long resultTimeout = 30000;
		startResultTimeoutThread(computationTimeout, resultTimeout);
	}

	/**
	 * implements singleton pattern
	 * 
	 * @return Instance of the singleton
	 */
	protected static BideThreadPool getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BideThreadPool();
		}
		return INSTANCE;
	}

	/**
	 * Starts a new asynchronous task. The result may be polled by calling XXX with the task's ID. If any task with he
	 * same
	 * ID is queued, running or done, it will be canceled and any results will be removed.
	 * 
	 * @param taskId
	 *            id of the user who started the thread
	 * @return true if an old thread was already running and got stopped
	 */
	protected synchronized BideTask startTask(String taskId) {

		// check if any task by this user is already running and delete any pending results
		// boolean interrupted = false;
		BideTask pendingTask = tasks.remove(taskId);
		if (pendingTask != null) {
			System.out.println("cancelling pending task " + taskId);
			pendingTask.cancel();
			tasks.remove(taskId);
		}

		// create a new task and let it run at some point later
		BideTask bideTask = new BideTask(taskId);
		synchronized (executor) {
			Future<?> future = executor.submit(bideTask);
			bideTask.setFuture(future);
			tasks.put(taskId, bideTask);
		}
		return bideTask;
	}

	private void startResultTimeoutThread(long computationTimeout, long resultTimeout) {
		resultTimeoutThread = new ThreadPoolTimeoutThread(tasks, computationTimeout, resultTimeout);
		resultTimeoutThread.setPriority(Thread.NORM_PRIORITY);
		resultTimeoutThread.start();
	}

	public BideTask getTask(String key) {
		return tasks.get(key);
	}
}
