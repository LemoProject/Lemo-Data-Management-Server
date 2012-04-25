package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.List;

import de.lemo.dms.core.Clock;
import de.lemo.dms.processing.resulttype.ResultList;
import de.lemo.dms.processing.resulttype.RoleObject;

public class Test {

	
	public static void main(String[] args)
	{
		QCourseActivity q = new QCourseActivity();
		QUserInformation qu = new QUserInformation();
		QUserRoles qr = new QUserRoles();
		
		List<Long> c = new ArrayList<Long>();

		List<Long> r = new ArrayList<Long>();

		Clock cl = new Clock();
		ResultList res = q.compute(c, r, 0L, 1500000000L, 300);
		System.err.println(cl.get());
		//qu.getCoursesByUser(3L, 5L, 0L);		
		
	}
}
