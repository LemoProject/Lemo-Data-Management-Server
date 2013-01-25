/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixHelper/TimeConverter.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {

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

	public static String getStringRepresentation(final Long date)
	{
		String s = "";

		s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date * 1000);

		return s;
	}
}
