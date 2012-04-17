package de.lemo.dms.core;

/**
 * Small helper class for performance surveillance.
 * 
 * @author s.schwarzrock
 *
 */
public class Clock {
	
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
		start = System.currentTimeMillis();
	}
	
	/**
	 * Gets the timespan since last reset or constructor call.
	 * 
	 * @return the timespan since last reset or constructor call.
	 */
	public String get()
	{
		long g = System.currentTimeMillis();
		return (((g - start)/1000)/60)  +" m "+(((g - start)/1000) % 60) + " s " + (((g - start)%1000)) + "ms";
	}
	
	/**
	 * Gets the timespan since last reset or constructor call and resets the clock.
	 * 
	 * @return the timespan since last reset or constructor call
	 */
	public String getAndReset()
	{		
		String g = this.get();
		this.reset();
		return g;
	}
}
