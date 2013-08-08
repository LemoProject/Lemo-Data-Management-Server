/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_1_9/MoodleNumericHibernateUtil.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_1_9/MoodleNumericHibernateUtil.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_1_9;

import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.google.common.collect.Maps;

import de.lemo.dms.connectors.moodle_1_9.mapping.AssignmentLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.AssignmentSubmissionsLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.ChatLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.ChatLogLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.ContextLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.CourseCategoriesLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.CourseLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.CourseModulesLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.ForumDiscussionsLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.ForumLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.ForumPostsLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.GradeGradesLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.GradeItemsLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.GroupsLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.GroupsMembersLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.LogLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.QuestionLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.QuestionStatesLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.QuizGradesLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.QuizLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.QuizQuestionInstancesLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.ResourceLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.RoleAssignmentsLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.RoleLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.ScormLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.UserLMS;
import de.lemo.dms.connectors.moodle_1_9.mapping.WikiLMS;
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public final class HibernateUtil {

	private static Map<DBConfigObject, SessionFactory> sessionFactories = Maps.newHashMap();

	private HibernateUtil() {
	}

	public static SessionFactory getSessionFactory(final DBConfigObject dbconfig) {
		SessionFactory sessionFactory = HibernateUtil.sessionFactories.get(dbconfig);
		if (sessionFactory == null) {
			sessionFactory = HibernateUtil.createSessionFactory(dbconfig);
			HibernateUtil.sessionFactories.put(dbconfig, sessionFactory);
		}
		return sessionFactory;
	}

	public static void closeSessionFactory(final DBConfigObject dbconfig) {
		final SessionFactory sessionFactory = HibernateUtil.sessionFactories.remove(dbconfig);
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
		
		cfg.addAnnotatedClass(CourseLMS.class);
		cfg.addAnnotatedClass(CourseModulesLMS.class);
		cfg.addAnnotatedClass(ForumDiscussionsLMS.class);
		cfg.addAnnotatedClass(ForumPostsLMS.class);
		cfg.addAnnotatedClass(ForumLMS.class);
		cfg.addAnnotatedClass(GradeGradesLMS.class);
		cfg.addAnnotatedClass(GroupsMembersLMS.class);
		cfg.addAnnotatedClass(GroupsLMS.class);
		cfg.addAnnotatedClass(LogLMS.class);
		cfg.addAnnotatedClass(QuestionStatesLMS.class);
		cfg.addAnnotatedClass(QuestionLMS.class);
		cfg.addAnnotatedClass(QuizGradesLMS.class);
		cfg.addAnnotatedClass(QuizLMS.class);
		cfg.addAnnotatedClass(QuizQuestionInstancesLMS.class);
		cfg.addAnnotatedClass(WikiLMS.class);
		cfg.addAnnotatedClass(ResourceLMS.class);
		cfg.addAnnotatedClass(UserLMS.class);
		cfg.addAnnotatedClass(RoleLMS.class);
		cfg.addAnnotatedClass(ContextLMS.class);
		cfg.addAnnotatedClass(RoleAssignmentsLMS.class);
		cfg.addAnnotatedClass(AssignmentLMS.class);
		cfg.addAnnotatedClass(AssignmentSubmissionsLMS.class);
		cfg.addAnnotatedClass(ScormLMS.class);
		cfg.addAnnotatedClass(GradeItemsLMS.class);
		cfg.addAnnotatedClass(ChatLMS.class);
		cfg.addAnnotatedClass(ChatLogLMS.class);
		cfg.addAnnotatedClass(CourseCategoriesLMS.class);

		return cfg.buildSessionFactory();
	}

}
