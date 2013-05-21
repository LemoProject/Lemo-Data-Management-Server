/**
 * File BideTask.java
 * Date 22.04.2013
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.processing.async;

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
public abstract class AsyncAnalysis implements Callable<Object> {

	private final Logger logger = Logger.getLogger(getClass());

	private long startTime;
	private long endTime;
	private String taskID;
	private Future<?> future;

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
		return future.isDone();
	}

	public boolean isRunning() {
		return startTime > 0 && !future.isDone();
	}

	public boolean isCancelled() {
		return future.isCancelled();
	}

	public void setIaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getTaskID() {
		return taskID;
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
				logger.info(this + " - cancelled", e);
			} catch (InterruptedException e) {
				logger.info(this + " - interuppted", e);
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
		return "Task " + getTaskID() + ", " + state;
	}

}
