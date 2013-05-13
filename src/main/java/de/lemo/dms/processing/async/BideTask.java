/**
 * File BideTask.java
 * Date 22.04.2013
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.processing.async;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class BideTask implements Callable<Object> {

	private long startTime;
	private long endTime;
	private String taskID;
	private Future<?> future;

	public BideTask(String taskId) {
		this.taskID = taskId;
	}

	@Override
	public synchronized Object call() throws InterruptedException {

		startTime = new Date().getTime();

		System.out.println("start thread " + this.taskID);

		// simulate computation
		while (true) {
			if (Thread.interrupted()) {
				System.out.println("task " + taskID + " interupted!");
				cancel();
				return null;
			}

			Thread.sleep(500);

			if (Math.random() > 0.9) {
				break;
			}
		}

		System.out.println("thread " + this.taskID + " is finished");

		endTime = new Date().getTime();
		return "[this is the result of task " + taskID + "]";

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
			} catch (InterruptedException e) {
				// should never happen at this point, as the task is done
				e.printStackTrace();
				return null;
			}
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
