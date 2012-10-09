package de.lemo.dms.service;

import java.util.ArrayList;
import java.util.List;

import de.lemo.dms.processing.questions.QActivityResourceTypeResolution;
import de.lemo.dms.processing.resulttype.ResultListRRITypes;

public class Test {

	
	public static void main(String[] args)
	{
		QActivityResourceTypeResolution qart = new QActivityResourceTypeResolution();
		List<Long> courses = new ArrayList<Long>();
		Long startTime = 0L;
		Long endTime = 1500000000L;
		List<String> resourceTypes = new ArrayList<String>();
		courses.add(2200L);
		ResultListRRITypes r = qart.compute(courses, startTime, endTime, 200L, resourceTypes);
		startTime++;

			
	}
}
