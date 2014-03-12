/**
 * File ./src/main/java/de/lemo/dms/test/Test.java
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
 * File ./main/java/de/lemo/dms/test/Test.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.processing.questions.QCourseActivity;

/**
 * sollte gelöscht werden
 * @author Sebastian Schwarzrock
 *
 */
public class Test {

/*
	public static void gen()
	{
		final ContentGenerator conGen = new ContentGenerator();
		ServerConfiguration.getInstance().loadConfig("/lemo");
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		dbHandler.saveCollectionToDB(session, conGen.generateMiningDB(5, 2, 2, 251, 1325372400L, 500, 1));
	}
	
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
	
	public static void test()
	{
		QCourseActivity qca = new QCourseActivity();
		List<Long> courses = new ArrayList<Long>();
		courses.add(112L);
		List<Long> users = new ArrayList<Long>();
		Long startTime = 0L;
		Long endTime = 1563990913L;
		Long resolution = 28L;
		List<String> resourceTypes = new ArrayList<String>();
		List<Long> gender = new ArrayList<Long>();
		
		
		qca.compute(courses, users, startTime, endTime, resolution, resourceTypes, gender);
	}

	public static void main(final String[] args)
	{
		ServerConfiguration.getInstance().loadConfig("/lemo");
		Test.test();
	}

}
