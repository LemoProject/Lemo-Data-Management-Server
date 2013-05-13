/**
 * File ThreadPoolTimeoutThread.java
 * Date 24.04.2013
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.processing.async;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Thread to remove old results. Check the task results creation date and removes any results that exceeded their life
 * time.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class TaskTimeoutThread extends Thread {

	// check every 10 seconds
	private static final int CHECK_INTERVALL = 10000;
	private static final String NAME = "BideResultTimeout";

	private Map<String, AsyncAnalysis> tasks;
	private long maxProcessingTime;
	private long maxResultLifeTime;

	public TaskTimeoutThread(Map<String, AsyncAnalysis> tasks, long maxProcessingTime, long maxResultLifeTime) {
		super(NAME);
		this.tasks = tasks;
		this.maxProcessingTime = maxProcessingTime;
		this.maxResultLifeTime = maxResultLifeTime;
	}

	public void run() {
		try {
			while (true) {
				// sleep some arbitrary time
				Thread.sleep(CHECK_INTERVALL);

				System.out.println("Checking timeouts");

				synchronized (tasks) {
					for (Iterator<Entry<String, AsyncAnalysis>> iterator = tasks.entrySet().iterator(); iterator.hasNext();) {
						Entry<String, AsyncAnalysis> entry = iterator.next();
						AsyncAnalysis task = entry.getValue();

						if (task.isRunning()) {
							// check if the computation time reached the limit
							long computationTime = new Date().getTime() - task.getStartTime();
							System.out.println(task + " | computation time " + computationTime + "/"
									+ maxProcessingTime);
							if (computationTime > maxProcessingTime) {
								// TODO log
								System.out.println(task + " | " + "computation timeout limit reached, removing task "
										+ task.getTaskID());

								// cancel the task but don't remove it,
								// so the user may knows that it got canceled by a timeout
								task.cancel();
							}
						} else if (task.isDone()) {
							// check if the result life time reached the limit
							long resultLifeTime = new Date().getTime() - task.getEndTime();
							System.out.println(task + " | result time " + resultLifeTime + "/" + maxResultLifeTime);
							if (task.isDone() && resultLifeTime > maxResultLifeTime) {
								// TODO log
								System.out.println(task + " | " + "result lifetime limit reached, removing result "
										+ task.getTaskID());
								iterator.remove();
							}
						}

					}
				}
			}
		} catch (InterruptedException e) {
			// this thread should never be interrupted, it should run forever
			// TODO log
			e.printStackTrace();
		}

	}
}
