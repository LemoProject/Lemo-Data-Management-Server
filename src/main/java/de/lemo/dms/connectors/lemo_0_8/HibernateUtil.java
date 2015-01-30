/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/HibernateUtil.java
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
 * File ./main/java/de/lemo/dms/connectors/lemo_0_8/HibernateUtil.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8;

import java.util.HashMap;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.common.collect.Maps;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.connectors.lemo_0_8.mapping.AssignmentLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.AssignmentLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ChatLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ChatLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ConfigLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseAssignmentLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseChatLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseForumLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseGroupLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseQuizLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseResourceLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseScormLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseUserLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseWikiLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ForumLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ForumLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.GroupLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.GroupUserLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.IDMappingLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.LevelAssociationLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.LevelCourseLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.LevelLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.PlatformLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuestionLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuestionLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuizLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuizLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuizQuestionLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuizUserLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ResourceLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ResourceLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.RoleLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ScormLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ScormLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.UserLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.WikiLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.WikiLMS;

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

		// Meta
		cfg.addAnnotatedClass(ConfigLMS.class);
		cfg.addAnnotatedClass(IDMappingLMS.class);
		cfg.addAnnotatedClass(PlatformLMS.class);

		// Object-classes
		cfg.addAnnotatedClass(AssignmentLMS.class);
		cfg.addAnnotatedClass(ChatLMS.class);
		cfg.addAnnotatedClass(CourseLMS.class);
		cfg.addAnnotatedClass(ForumLMS.class);
		cfg.addAnnotatedClass(GroupLMS.class);
		cfg.addAnnotatedClass(QuestionLMS.class);
		cfg.addAnnotatedClass(QuizLMS.class);
		cfg.addAnnotatedClass(ResourceLMS.class);
		cfg.addAnnotatedClass(RoleLMS.class);
		cfg.addAnnotatedClass(ScormLMS.class);
		cfg.addAnnotatedClass(UserLMS.class);
		cfg.addAnnotatedClass(WikiLMS.class);
		cfg.addAnnotatedClass(LevelLMS.class);

		// Association-classes
		
		cfg.addAnnotatedClass(CourseAssignmentLMS.class);
		cfg.addAnnotatedClass(CourseChatLMS.class);
		cfg.addAnnotatedClass(CourseForumLMS.class);
		cfg.addAnnotatedClass(CourseGroupLMS.class);
		cfg.addAnnotatedClass(CourseQuizLMS.class);
		cfg.addAnnotatedClass(CourseResourceLMS.class);
		cfg.addAnnotatedClass(CourseScormLMS.class);
		cfg.addAnnotatedClass(CourseUserLMS.class);
		cfg.addAnnotatedClass(CourseWikiLMS.class);
		cfg.addAnnotatedClass(GroupUserLMS.class);
		cfg.addAnnotatedClass(QuizQuestionLMS.class);
		cfg.addAnnotatedClass(QuizUserLMS.class);
		cfg.addAnnotatedClass(LevelCourseLMS.class);
		cfg.addAnnotatedClass(LevelAssociationLMS.class);

		// Log-classes
		
		cfg.addAnnotatedClass(AssignmentLogLMS.class);
		cfg.addAnnotatedClass(ChatLogLMS.class);
		cfg.addAnnotatedClass(CourseLogLMS.class);
		cfg.addAnnotatedClass(ForumLogLMS.class);
		cfg.addAnnotatedClass(ResourceLogLMS.class);
		cfg.addAnnotatedClass(ScormLogLMS.class);
		cfg.addAnnotatedClass(QuestionLogLMS.class);
		cfg.addAnnotatedClass(QuizLogLMS.class);
		cfg.addAnnotatedClass(WikiLogLMS.class);

		return cfg.buildSessionFactory();
	}

}
