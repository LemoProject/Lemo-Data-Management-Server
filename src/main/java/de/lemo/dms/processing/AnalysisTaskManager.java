/**
 * File ./src/main/java/de/lemo/dms/processing/AnalysisTaskManager.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File BideTask.java
 * Date 22.04.2013
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.processing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.service.ServiceTaskManager;

/**
 * Thread pool to ensure that a user can start any long running analysis only once. Instead of a result, the client gets
 * an URL that may be polled until the processing has finished.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class AnalysisTaskManager {

	private final Logger logger = Logger.getLogger(getClass());

	// TODO arbitrary value, make configurable
	private static final int MAX_THREADS = 10;

	// TODO use enum singleton pattern
	private static AnalysisTaskManager INSTANCE = null;

	private TaskTimeoutThread resultTimeoutThread;
	// TODO use own thread factory to set low priority!
	private ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

	private Map<String, AnalysisTask> tasks = Collections.synchronizedMap(new HashMap<String, AnalysisTask>());

	private AnalysisTaskManager() {
		long computationTimeout = ServerConfiguration.getInstance().getPathAnalysisTimeout();
		long resultTimeout = computationTimeout;
		startResultTimeoutThread(computationTimeout, resultTimeout);
	}

	/**
	 * implements singleton pattern
	 * 
	 * @return Instance of the singleton
	 */
	public static synchronized AnalysisTaskManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AnalysisTaskManager();
		}
		return INSTANCE;
	}

	/**
	 * Starts a new asynchronous task. The result may be polled from the {@link ServiceTaskManager} with the task's ID.
	 * If any task with the same ID is queued, running or done, it will be canceled and any results will be removed.
	 * 
	 * @param taskId
	 *            id to identify the task
	 * @param task
	 *            the task to add
	 */
	public synchronized void addTask(AnalysisTask task) {
		String taskId = task.getTaskId();
		
		// check if any task by this user is already running and delete any pending results
		AnalysisTask pendingTask = tasks.remove(taskId);
		if (pendingTask != null) {
			logger.debug("cancelled pending task: " + taskId);
			pendingTask.cancel();
		}

		// create a new task and let it run at some point later
		synchronized (executor) {
			Future<?> future = executor.submit(task);
			task.setFuture(future);
			tasks.put(taskId, task);
			logger.debug("submitted task " + taskId);
		}
	}

	public AnalysisTask getTask(String key) {
		return tasks.get(key);
	}

	private void startResultTimeoutThread(long computationTimeout, long resultTimeout) {
		resultTimeoutThread = new TaskTimeoutThread(tasks, computationTimeout, resultTimeout);
		resultTimeoutThread.setPriority(Thread.NORM_PRIORITY);
		resultTimeoutThread.start();
	}

}
