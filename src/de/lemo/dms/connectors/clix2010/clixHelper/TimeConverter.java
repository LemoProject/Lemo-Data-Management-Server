package de.lemo.dms.connectors.clix2010.clixHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {

	public static Long getTimestamp(String date)
	{
		Long t = -1L;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date d = sdf.parse(date);
			t = d.getTime() / 1000;
		}catch (Exception e)
		{
			return t;
		}
		return t;
	}
}
