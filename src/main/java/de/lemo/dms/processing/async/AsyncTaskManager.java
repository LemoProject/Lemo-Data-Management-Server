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
 * Threadpool to Manage that every user only can start one thread with the
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class AsyncTaskManager {

	// TODO arbitrary value, make configurable
	private static final int MAX_THREADS = 10;

	// TODO use enum singleton pattern
	private static AsyncTaskManager INSTANCE = null;

	private TaskTimeoutThread resultTimeoutThread;
	// TODO use own thread factory to set low priority!
	private ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

	private Map<String, AsyncAnalysis> tasks = Collections.synchronizedMap(new HashMap<String, AsyncAnalysis>());

	private AsyncTaskManager() {
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
	protected static AsyncTaskManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AsyncTaskManager();
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
	protected synchronized AsyncAnalysis startTask(String taskId, Class<AsyncAnalysis> analysisType) {

		// check if any task by this user is already running and delete any pending results
		// boolean interrupted = false;
		AsyncAnalysis pendingTask = tasks.remove(taskId);
		if (pendingTask != null) {
			System.out.println("cancelling pending task " + taskId);
			pendingTask.cancel();
			tasks.remove(taskId);
		}

		// create a new task and let it run at some point later
		AsyncAnalysis task = createTask(taskId, analysisType);
		synchronized (executor) {
			Future<?> future = executor.submit(task);
			task.setFuture(future);
			tasks.put(taskId, task);
		}

		return task;
	}

	private AsyncAnalysis createTask(String taskId, Class<AsyncAnalysis> analysisType) {
		AsyncAnalysis task = null;
		try {
			task = analysisType.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task.setIaskID(taskId);
		return task;
	}

	private void startResultTimeoutThread(long computationTimeout, long resultTimeout) {
		resultTimeoutThread = new TaskTimeoutThread(tasks, computationTimeout, resultTimeout);
		resultTimeoutThread.setPriority(Thread.NORM_PRIORITY);
		resultTimeoutThread.start();
	}

	public AsyncAnalysis getTask(String key) {
		return tasks.get(key);
	}
}
