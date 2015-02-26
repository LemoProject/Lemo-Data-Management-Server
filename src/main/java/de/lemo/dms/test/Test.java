/**
 * File ./src/main/java/de/lemo/dms/test/Test.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
 * File ./main/java/de/lemo/dms/test/Test.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.UserAttribute;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.questions.QCourseActivity;
import de.lemo.dms.processing.questions.QFrequentPathsBIDE;
import de.lemo.dms.processing.questions.QFrequentPathsApriori;
import de.lemo.dms.processing.questions.QPerformanceHistogram;
import de.lemo.dms.processing.questions.QPerformanceUserTest;
import de.lemo.dms.processing.questions.async.AFrequentPathsBIDE;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;
import de.lemo.dms.processing.resulttype.ResultListHashMapObject;
import de.lemo.dms.service.ServiceCourseDetails;
import de.lemo.dms.service.ServiceCourseTitleSearch;
import de.lemo.dms.service.ServiceLearningTypes;

/**
 * sollte gelöscht werden
 * @author Sebastian Schwarzrock
 *
 */
public class Test {


	public static void gen()
	{
		final ContentGenerator conGen = new ContentGenerator();
		ServerConfiguration.getInstance().loadConfig("/lemo");
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		dbHandler.saveCollectionToDB(session, conGen.generateMiningDB(5, 100, 1325372400L, 200));
	}
/*	
	public static void write()
	{
		final TestDataCreatorChemgapedia ch = new TestDataCreatorChemgapedia();

		ch.getDataFromDB();
		ch.writeDataSource("c://users//s.schwarzrock//desktop//chemgaLog.log",
				"c://users//s.schwarzrock//desktop//VluGen");
	}

	public static void writeMoodle()
	{
		final TestDataCreatorMoodle mod = new TestDataCreatorMoodle();
		mod.getDataFromDB();
		mod.writeSourceDB();
	}

	/**
	 * main method for test case
	 * TODO move to unit test
	 * @param args
	 */
	
	public static void writeStats()
	{
		ServerConfiguration.getInstance().loadConfig("/lemo");
		BufferedWriter writer = null;
	        try {
	            //create a temporary file
	            File logFile = new File("log.txt");

	            // This will output the full path where the file will be written to...
	            System.out.println(logFile.getCanonicalPath());

	            writer = new BufferedWriter(new FileWriter(logFile));
	    		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
	    		final Session session = dbHandler.getMiningSession();
	    		Criteria criteria = session.createCriteria(UserAttribute.class, "obj");
	    		List<UserAttribute> ua = criteria.list();
	    		Map<Long, String> progMap = new HashMap<Long, String>();
	    		for(UserAttribute u : ua)
	    		{
	    			if(u.getAttribute().getId() == 5)
	    			{		progMap.put(u.getUser().getId(), u.getValue());
	    			System.out.println(u.getValue());}
	    		}
	    		
	    		
	    		
	    		criteria = session.createCriteria(ILog.class);
	    		writer.write("Time,User,UserProgress,Object,Type");
	    		writer.write(System.getProperty("line.separator"));
	    		int i = 0;
	    		List<ILog> list = criteria.list();
	    		Collections.sort(list);
	    		for(ILog log : list)
	    		{
	    			i++;
	    			java.util.Date time=new java.util.Date((long)log.getTimestamp()*1000);
	    			
	    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
	    			sdf.setTimeZone(TimeZone.getTimeZone("GMT+1")); // give a timezone reference for formating (see comment at the bottom
	    			String formattedDate = sdf.format(time);
	    			
	    			writer.write(formattedDate + "," + log.getUser().getId()+","+progMap.get(log.getUser().getId())+",\"" + log.getLearning().getTitle()+"\"," + log.getLearning().getLOType());
	    			writer.write(System.getProperty("line.separator"));
	    		//	if( i == 1000)
	    			//	break;
	    		}
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                // Close the writer regardless of what happens...
	                writer.close();
	            } catch (Exception e) {
	            }
	        }
	}
	
	public static void test()
	{
		ServerConfiguration.getInstance().loadConfig("/lemo");
		ServiceCourseTitleSearch scd = new ServiceCourseTitleSearch();
		scd.getCoursesByText("e", 1000l, 0l);
		QCourseActivity cca = new QCourseActivity();
		List<Long> courses = new ArrayList<Long>();
		courses.add(1L);
		courses.add(2L);
		List<String> types = new ArrayList<String>();
		List<Long> users = new ArrayList<Long>();
		Double minSup = 0.7d;
		Long startTime = 0L;
		Long endTime = 1500000000L;
		Long minLength = null;
		Long maxLength = null;
		//ResultListCourseObject co = scd.getCoursesByText("e");
		ResultListHashMapObject ilo = cca.compute(courses, users, startTime, endTime, 100L, new ArrayList<String>(), new ArrayList<Long>(), new ArrayList<Long>());
		
		
			QPerformanceHistogram qput = new QPerformanceHistogram();
			

			List<Long> gender = new ArrayList<Long>();
			List<Long> learningObjects = new ArrayList<Long>();
			
			qput.compute(courses, users, new ArrayList<Long>(), 100L, startTime, endTime, gender);

			
		
	}
	

	public static void main(final String[] args)
	{
		ServerConfiguration.getInstance().loadConfig("/lemo");
		Test.writeStats();
	}

}
