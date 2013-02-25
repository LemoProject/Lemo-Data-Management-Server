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
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class HibernateUtil {

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

		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/AssignLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/EnrolLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/UserEnrolmentsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ModulesLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/CourseModulesLMS.hbm.xml");

		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/CourseLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ForumDiscussionsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ForumPostsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ForumLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/GradeGradesLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/GroupsMembersLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/GroupsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/LogLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/QuestionStatesLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/QuestionLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/QuizGradesLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/QuizLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/QuizAttemptsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/QuizQuestionInstancesLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/WikiLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ResourceLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/UserLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/RoleLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ContextLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/RoleAssignmentsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/AssignmentLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/AssignmentSubmissionLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ScormLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/GradeItemsLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ChatLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ChatLogLMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/CourseCategoriesLMS.hbm.xml");

		return cfg.buildSessionFactory();
	}

}
