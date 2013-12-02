/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_3/HibernateUtil.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/HibernateUtil.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3;

import java.util.HashMap;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.common.collect.Maps;

import de.lemo.dms.connectors.moodle_2_3.mapping.AssignGradesLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.ChatLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.ChatLogLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.ContextLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.CourseCategoriesLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.CourseLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.CourseModulesLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.AssignLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.EnrolLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.ForumDiscussionsLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.ForumLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.ForumPostsLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.GradeGradesLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.GradeItemsLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.GroupsLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.GroupsMembersLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.LogLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.ModulesLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.PageLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.QuestionLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.QuestionStatesLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.QuizAttemptsLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.QuizGradesLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.QuizLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.QuizQuestionInstancesLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.ResourceLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.UrlLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.RoleAssignmentsLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.RoleLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.ScormLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.UserEnrolmentsLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.UserLMS;
import de.lemo.dms.connectors.moodle_2_3.mapping.WikiLMS;
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public final class HibernateUtil {

	private HibernateUtil() {
	}

	private static HashMap<DBConfigObject, SessionFactory> sessionFactories = Maps.newHashMap();

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

	private static SessionFactory createSessionFactory(final DBConfigObject dbconfig) {
		final Configuration cfg = new Configuration();

		// add properties from files
		for (final Entry<String, String> entry : dbconfig.getProperties().entrySet()) {
			cfg.setProperty(entry.getKey(), entry.getValue());
		}

		// Add mapping classes

		cfg.addAnnotatedClass(AssignLMS.class);

		cfg.addAnnotatedClass(EnrolLMS.class);
		cfg.addAnnotatedClass(UserEnrolmentsLMS.class);
		cfg.addAnnotatedClass(ModulesLMS.class);
		cfg.addAnnotatedClass(CourseModulesLMS.class);

		cfg.addAnnotatedClass(CourseLMS.class);
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
		cfg.addAnnotatedClass(QuizAttemptsLMS.class);
		cfg.addAnnotatedClass(QuizQuestionInstancesLMS.class);
		cfg.addAnnotatedClass(WikiLMS.class);
		cfg.addAnnotatedClass(ResourceLMS.class);
		cfg.addAnnotatedClass(PageLMS.class);
		cfg.addAnnotatedClass(UrlLMS.class);
		cfg.addAnnotatedClass(UserLMS.class);
		cfg.addAnnotatedClass(RoleLMS.class);
		cfg.addAnnotatedClass(ContextLMS.class);
		cfg.addAnnotatedClass(RoleAssignmentsLMS.class);
		cfg.addAnnotatedClass(AssignGradesLMS.class);
		cfg.addAnnotatedClass(ScormLMS.class);
		cfg.addAnnotatedClass(GradeItemsLMS.class);
		cfg.addAnnotatedClass(ChatLMS.class);
		cfg.addAnnotatedClass(ChatLogLMS.class);
		cfg.addAnnotatedClass(CourseCategoriesLMS.class);

		return cfg.buildSessionFactory();
	}

}
