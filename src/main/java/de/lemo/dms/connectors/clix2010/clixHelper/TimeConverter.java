/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixHelper/TimeConverter.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple utility-class for the conversion of Clix-timestamps to posix timestamps
 * 
 * @author s.schwarzrock
 *
 */
public class TimeConverter {
	
	private TimeConverter()
	{
		
	}

	/**
	 * Converts a Clix-timestamp to a posix-timestamp.
	 * 
	 * @param date String-representation of a date within Clix.
	 * 
	 * @return Posix-timestamp of the date.
	 */
	public static Long getTimestamp(final String date)
	{
		Long t = 0L;
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			final Date d = sdf.parse(date);
			t = d.getTime() / 1000;

		} catch (final Exception e)
		{
			return t;
		}
		return t;
	}

	/**
	 * Converts a Posix-timestamp to a Clix-timestamp.
	 * 
	 * @param date Posix-timestamp
	 * @return String-representation of a date within Clix
	 */
	public static String getStringRepresentation(final Long date)
	{
		String s = "";

		s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date * 1000);

		return s;
	}
}
