/**
 * File ./main/java/de/lemo/dms/core/Clock.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.core;

/**
 * Small helper class for performance surveillance.
 * 
 * @author s.schwarzrock
 */
public class Clock {

	private final static int SECONDS = 60;
	private final static int DIVIDOR = 1000;
	private long start;

	/**
	 * Constructor. Creates and starts a clock.
	 */
	public Clock()
	{
		this.start = System.currentTimeMillis();
	}

	/**
	 * Resets the clock.
	 */
	public void reset()
	{
		this.start = System.currentTimeMillis();
	}

	/**
	 * Gets the timespan since last reset or constructor call.
	 * 
	 * @return the timespan since last reset or constructor call.
	 */
	public String get()
	{
		final long g = System.currentTimeMillis();
		return (((g - this.start) / Clock.DIVIDOR) / Clock.SECONDS) + " m "
				+ (((g - this.start) / Clock.DIVIDOR) % Clock.SECONDS) + " s "
				+ (((g - this.start) % Clock.DIVIDOR)) + "ms";
	}

	/**
	 * Gets the timespan since last reset or constructor call and resets the clock.
	 * 
	 * @return the timespan since last reset or constructor call
	 */
	public String getAndReset()
	{
		final String g = this.get();
		this.reset();
		return g;
	}
}
