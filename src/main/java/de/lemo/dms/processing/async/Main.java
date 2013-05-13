/**
 * File BideTask.java
 * Date 22.04.2013
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.processing.async;

import java.util.concurrent.ExecutionException;

/**
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class Main {

	private final static int USERCOUNT = 20;
	private static final BideThreadPool pool = BideThreadPool.getInstance();

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		// user id for interaction
		for (int i = 0; i < USERCOUNT; i++) {
			pool.startTask(Integer.toString(i));
		}
		for (int i = 0; i < USERCOUNT; i++) {
			// should stop any previous tasks by this user
			pool.startTask(Integer.toString(i));
		}

		Thread.sleep(20000);

		// print results if available
		for (int i = 0; i < USERCOUNT; i++) {
			BideTask task = pool.getTask(Integer.toString(i));
			if (task != null) {
				System.out.print(task + " ");
				if (task.isDone() && !task.isCancelled()) {
					try {
						System.out.print("" + task.getResult());
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				} else if (task.isCancelled()) {
					System.out.print("no result, got cancelled by timeout");
				}
				System.out.println();
			}
		}

	}
}
