package de.lemo.dms.processing;

import java.util.ArrayList;

import de.lemo.dms.processing.questions.QFrequentPathsBIDE;
import de.lemo.dms.processing.questions.QLearningObjectUsage;

public class Test {
	
	public static void main(String[] args)
	{
		QFrequentPathsBIDE bide = new QFrequentPathsBIDE();
		QLearningObjectUsage analysis = new QLearningObjectUsage();
		ArrayList<Long> courseIds = new ArrayList<Long>();
		courseIds.add(1L);
		ArrayList<Long> userIds = new ArrayList<Long>();
		ArrayList<String> types = new ArrayList<String>();
		Double minSup = 0.5;
		boolean sessionWise = false;
		boolean considerLogouts = false;
		Long startTime = 0L;
		Long endTime =1400000000L;
		
		
		//analysis.compute(courseIds, userIds, types, startTime, endTime);
		bide.compute(courseIds, userIds, minSup, sessionWise, startTime, endTime);
	}

}
