/**
 * File ./src/main/java/de/lemo/dms/processing/AnalysisTask.java
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

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;

/**
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public abstract class AnalysisTask implements Callable<Object> {

	private final Logger logger = Logger.getLogger(getClass());

	private long startTime;
	private long endTime;
	private String taskId;
	private Future<?> future;

	public AnalysisTask(String taskId) {
		this.taskId = taskId;
	}

	public abstract Object compute();

	@Override
	public synchronized Object call() throws InterruptedException {
		logger.debug(this + " - started.");

		startTime = new Date().getTime();
		Object result = compute();
		endTime = new Date().getTime();

		logger.debug(this + " - finished.");
		return result;
	}

	// TODO add some message why it got cancelled?
	public void cancel() {
		future.cancel(true);
		endTime = new Date().getTime();
	}

	/**
	 * @return true if the task is done
	 */
	public boolean isDone() {
		return future != null && future.isDone();
	}

	public boolean isRunning() {
		return startTime > 0 && future != null && !future.isDone();
	}

	public boolean isCancelled() {
		return future != null && future.isCancelled();
	}

	public String getTaskId() {
		return taskId;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @return the result
	 * @throws ExecutionException
	 */
	public Object getResult() throws ExecutionException {
		if (future.isDone()) {
			try {
				return future.get();
			} catch (CancellationException e) {
				// TODO document exceptions!
				logger.error(this + " - cancelled", e);
			} catch (InterruptedException e) {
				logger.error(this + " - interuppted", e);
			}
			return null;
		} else {
			throw new IllegalStateException("The task is not done yet.");
		}
	}

	public void setFuture(Future<?> future) {
		this.future = future;
	}

	@Override
	public String toString() {
		String state = isRunning() ? "running" : (isCancelled() ? "canceled" : (isDone() ? "done" : "not yet started"));
		return "Task " + getTaskId() + ", " + state;
	}

}
