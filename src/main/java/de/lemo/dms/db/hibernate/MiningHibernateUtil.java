/**
 * File ./src/main/java/de/lemo/dms/db/hibernate/MiningHibernateUtil.java
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
 * File ./main/java/de/lemo/dms/db/hibernate/MiningHibernateUtil.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.hibernate;

import java.util.Map.Entry;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public final class MiningHibernateUtil {

	private static SessionFactory sessionFactory;

	private MiningHibernateUtil() {
	}

	public static void initSessionFactory(final DBConfigObject dbConfig) {
		MiningHibernateUtil.sessionFactory = MiningHibernateUtil.createSessionFactory(dbConfig);
	}

	/**
	 * a getter for the SessionFactory of the Mining DB
	 * 
	 * @return a session Factory for Mining DB sessions
	 */
	public static SessionFactory getSessionFactory() {
		if (MiningHibernateUtil.sessionFactory == null) {
			throw new IllegalStateException("No mining config set.");
		}
		return MiningHibernateUtil.sessionFactory;
	}

	private static SessionFactory createSessionFactory(final DBConfigObject dbConfig) {
		final Configuration config = new Configuration();

		// add properties from files
		for (final Entry<String, String> entry : dbConfig.getProperties().entrySet()) {
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
		config.addResource("de/lemo/dms/db/miningDBclass/CourseChatMining.hbm.xml");
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