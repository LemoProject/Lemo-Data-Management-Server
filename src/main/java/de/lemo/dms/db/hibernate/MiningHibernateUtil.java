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
import de.lemo.dms.db.mapping.*;

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
		config.addAnnotatedClass(ConfigMining.class);
		config.addAnnotatedClass(IDMappingMining.class);
		config.addAnnotatedClass(PlatformMining.class);

		// Object-classes
		config.addAnnotatedClass(AssignmentMining.class);
		config.addAnnotatedClass(ChatMining.class);
		config.addAnnotatedClass(CourseMining.class);
		config.addAnnotatedClass(ForumMining.class);
		config.addAnnotatedClass(GroupMining.class);
		config.addAnnotatedClass(QuestionMining.class);
		config.addAnnotatedClass(QuizMining.class);
		config.addAnnotatedClass(ResourceMining.class);
		config.addAnnotatedClass(RoleMining.class);
		config.addAnnotatedClass(ScormMining.class);
		config.addAnnotatedClass(UserMining.class);
		config.addAnnotatedClass(WikiMining.class);
		config.addAnnotatedClass(LevelMining.class);

		// Association-classes
		
		config.addAnnotatedClass(CourseAssignmentMining.class);
		config.addAnnotatedClass(CourseChatMining.class);
		config.addAnnotatedClass(CourseForumMining.class);
		config.addAnnotatedClass(CourseGroupMining.class);
		config.addAnnotatedClass(CourseQuizMining.class);
		config.addAnnotatedClass(CourseResourceMining.class);
		config.addAnnotatedClass(CourseScormMining.class);
		config.addAnnotatedClass(CourseUserMining.class);
		config.addAnnotatedClass(CourseWikiMining.class);
		config.addAnnotatedClass(GroupUserMining.class);
		config.addAnnotatedClass(QuizQuestionMining.class);
		config.addAnnotatedClass(QuizUserMining.class);
		config.addAnnotatedClass(AssignmentUserMining.class);
		config.addAnnotatedClass(ScormUserMining.class);
		config.addAnnotatedClass(LevelCourseMining.class);
		config.addAnnotatedClass(LevelAssociationMining.class);

		// Log-classes
		
		config.addAnnotatedClass(AssignmentLogMining.class);
		config.addAnnotatedClass(ChatLogMining.class);
		config.addAnnotatedClass(CourseLogMining.class);
		config.addAnnotatedClass(ForumLogMining.class);
		config.addAnnotatedClass(ResourceLogMining.class);
		config.addAnnotatedClass(ScormLogMining.class);
		config.addAnnotatedClass(QuestionLogMining.class);
		config.addAnnotatedClass(QuizLogMining.class);
		config.addAnnotatedClass(WikiLogMining.class);

		return config.buildSessionFactory();
	}

}