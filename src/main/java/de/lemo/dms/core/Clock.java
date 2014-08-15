/**
 * File ./src/main/java/de/lemo/dms/core/Clock.java
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
 * File ./main/java/de/lemo/dms/core/Clock.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core;

/**
 * Small helper class for performance surveillance.
 * 
 * @author s.schwarzrock
 */
public class Clock {

	private static final int SECONDS = 60;
	private static final int DIVIDOR = 1000;
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
