package de.lemo.dms.db.hibernate;

import java.util.Map.Entry;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class MiningHibernateUtil {

	private static SessionFactory sessionFactory;

	public static void initSessionFactory(DBConfigObject dbConfig) {
		sessionFactory = createSessionFactory(dbConfig);
	}

	/**
	 * a getter for the SessionFactory of the Mining DB
	 * 
	 * @return a session Factory for Mining DB sessions
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			throw new IllegalStateException("No mining config set.");
		}
		return sessionFactory;
	}

	private static SessionFactory createSessionFactory(DBConfigObject dbConfig) {
		Configuration config = new Configuration();

		// add properties from files
		for (Entry<String, String> entry : dbConfig.getProperties().entrySet()) {
			config.setProperty(entry.getKey(), entry.getValue());
		}

		// Meta
		config.addResource("de/lemo/dms/db/miningDBclass/ConfigMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/IDMappingMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/PlatformMining.hbm.xml");

		// Object-classes
		config.addResource("de/lemo/dms/db/miningDBclass/AssignmentMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/ChatMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/CourseMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/ForumMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/GroupMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/QuestionMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/QuizMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/ResourceMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/RoleMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/ScormMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/UserMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/WikiMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/LevelMining.hbm.xml");

		// Association-classes
		config.addResource("de/lemo/dms/db/miningDBclass/CourseAssignmentMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/CourseForumMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/CourseGroupMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/CourseQuizMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/CourseResourceMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/CourseScormMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/CourseUserMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/CourseWikiMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/GroupUserMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/QuizQuestionMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/QuizUserMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/LevelCourseMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/LevelAssociationMining.hbm.xml");

		// Log-classes
		config.addResource("de/lemo/dms/db/miningDBclass/AssignmentLogMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/ChatLogMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/CourseLogMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/ForumLogMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/ResourceLogMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/ScormLogMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/QuestionLogMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/QuizLogMining.hbm.xml");
		config.addResource("de/lemo/dms/db/miningDBclass/WikiLogMining.hbm.xml");

		return config.buildSessionFactory();
	}

}