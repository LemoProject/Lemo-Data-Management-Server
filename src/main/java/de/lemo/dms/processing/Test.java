package de.lemo.dms.processing;

import java.util.ArrayList;

import de.lemo.dms.processing.questions.QFrequentPathsBIDE;

public class Test {
	
	public static void main(String[] args)
	{
		QFrequentPathsBIDE bide = new QFrequentPathsBIDE();
		ArrayList<Long> courseIds = new ArrayList<Long>();
		ArrayList<Long> userIds = new ArrayList<Long>();
		Double minSup = 1.0;
		boolean sessionWise = false;
		Long startTime = 0L;
		Long endTime =1500000000L;
		
		bide.compute(courseIds, userIds, minSup, sessionWise, startTime, endTime);
	}

}
