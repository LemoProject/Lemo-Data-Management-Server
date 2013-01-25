/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/MoodleNumericHibernateUtil.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodleNumericId;

import java.util.HashMap;
import java.util.Map.Entry;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.google.common.collect.Maps;
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class MoodleNumericHibernateUtil {

	private static HashMap<DBConfigObject, SessionFactory> sessionFactories = Maps.newHashMap();

	public static SessionFactory getSessionFactory(final DBConfigObject dbconfig) {
		SessionFactory sessionFactory = MoodleNumericHibernateUtil.sessionFactories.get(dbconfig);
		if (sessionFactory == null) {
			sessionFactory = MoodleNumericHibernateUtil.createSessionFactory(dbconfig);
			MoodleNumericHibernateUtil.sessionFactories.put(dbconfig, sessionFactory);
		}
		return sessionFactory;
	}

	public static void closeSessionFactory(final DBConfigObject dbconfig) {
		final SessionFactory sessionFactory = MoodleNumericHibernateUtil.sessionFactories.remove(dbconfig);
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
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Course_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Course_Modules_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Forum_discussions_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Forum_posts_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Forum_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Grade_grades_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Groups_members_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Groups_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Log_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Question_states_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Question_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Quiz_grades_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Quiz_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Quiz_question_instances_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Wiki_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Resource_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/User_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Role_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Context_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Role_assignments_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Assignment_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Assignment_submission_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Scorm_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Grade_items_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Chat_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/ChatLog_LMS.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/CourseCategories_LMS.hbm.xml");

		return cfg.buildSessionFactory();
	}

}
