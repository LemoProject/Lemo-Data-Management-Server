/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle/MoodleHibernateUtil.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle/MoodleHibernateUtil.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle;

import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.google.common.collect.Maps;
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class MoodleHibernateUtil {

	private static Map<DBConfigObject, SessionFactory> sessionFactories = Maps.newHashMap();

	public static SessionFactory getSessionFactory(final DBConfigObject dbconfig) {
		SessionFactory sessionFactory = MoodleHibernateUtil.sessionFactories.get(dbconfig);
		if (sessionFactory == null) {
			sessionFactory = MoodleHibernateUtil.createSessionFactory(dbconfig);
			MoodleHibernateUtil.sessionFactories.put(dbconfig, sessionFactory);
		}
		return sessionFactory;
	}

	public static void closeSessionFactory(final DBConfigObject dbconfig) {
		final SessionFactory sessionFactory = MoodleHibernateUtil.sessionFactories.remove(dbconfig);
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

	private static SessionFactory createSessionFactory(final DBConfigObject dbConfig) {
		final Configuration cfg = new Configuration();

		// add properties from files
		for (final Entry<String, String> entry : dbConfig.getProperties().entrySet()) {
			cfg.setProperty(entry.getKey(), entry.getValue());
		}

		// add mapping classes
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/CourseLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/CourseModulesLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ForumDiscussionsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ForumPostsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ForumLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/GradeGradesLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/GroupsMembersLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/GroupsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/LogLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/QuestionStatesLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/QuestionLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/QuizGradesLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/QuizLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/QuizQuestionInstancesLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/WikiLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ResourceLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/UserLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/RoleLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ContextLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/RoleAssignmentsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/AssignmentLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/AssignmentSubmissionLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ScormLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/GradeItemsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ChatLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ChatLogLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/CourseCategoriesLMS.hbm.xml");

		return cfg.buildSessionFactory();
	}

}
