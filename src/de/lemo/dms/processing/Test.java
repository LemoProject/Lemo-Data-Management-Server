package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.List;

import de.lemo.dms.processing.resulttype.ResultList;
import de.lemo.dms.processing.resulttype.RoleObject;

public class Test {

	
	public static void main(String[] args)
	{
		QCourseActivity q = new QCourseActivity();
		QUserInformation qu = new QUserInformation();
		QUserRoles qr = new QUserRoles();
		
		List<Long> c = new ArrayList<Long>();
		c.add(3518L);
		c.add(2691L);
		List<Long> r = new ArrayList<Long>();
		r.add(5L);
		r.add(3L);
		
		//ResultList res = q.compute(null, null, 0L, 1334060056L, 10);
		//qu.getCoursesByUser(3L, 5L, 0L);		
		ResultList ro = qr.getUserRoles();
		for(int i = 0; i < ro.getElements().size(); i++)
		{
			System.out.println(((RoleObject)ro.getElements().get(i)).getName());
		}
	}
}
