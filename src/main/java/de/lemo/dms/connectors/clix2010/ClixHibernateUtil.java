/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/ClixHibernateUtil.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/ClixHibernateUtil.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010;

import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.google.common.collect.Maps;

import de.lemo.dms.connectors.clix2010.mapping.*;
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public final class ClixHibernateUtil {

	private static Map<DBConfigObject, SessionFactory> sessionFactories = Maps.newHashMap();

	private ClixHibernateUtil() {
	}

	public static SessionFactory getSessionFactory(final DBConfigObject dbconfig) {
		SessionFactory sessionFactory = ClixHibernateUtil.sessionFactories.get(dbconfig);
		if (sessionFactory == null) {
			sessionFactory = ClixHibernateUtil.createSessionFactory(dbconfig);
			ClixHibernateUtil.sessionFactories.put(dbconfig, sessionFactory);
		}
		return sessionFactory;
	}

	public static void closeSessionFactory(final DBConfigObject dbconfig) {
		final SessionFactory sessionFactory = ClixHibernateUtil.sessionFactories.remove(dbconfig);
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
		cfg.addAnnotatedClass(EComponent.class);
		cfg.addAnnotatedClass(LearningLog.class);
		cfg.addAnnotatedClass(ChatProtocol.class);
		cfg.addAnnotatedClass(EComponentType.class);
		cfg.addAnnotatedClass(EComposing.class);
		cfg.addAnnotatedClass(ExercisePersonalised.class);
		cfg.addAnnotatedClass(ForumEntry.class);
		cfg.addAnnotatedClass(ForumEntryState.class);
		cfg.addAnnotatedClass(Person.class);
		cfg.addAnnotatedClass(PlatformGroup.class);
		cfg.addAnnotatedClass(PlatformGroupSpecification.class);
		cfg.addAnnotatedClass(Portfolio.class);
		cfg.addAnnotatedClass(PortfolioLog.class);
		cfg.addAnnotatedClass(TeamExerciseGroup.class);
		cfg.addAnnotatedClass(TGroupFullSpecification.class);
		cfg.addAnnotatedClass(TQtiContent.class);
		cfg.addAnnotatedClass(TQtiTestItemD.class);
		cfg.addAnnotatedClass(TQtiTestPlayerResp.class);
		cfg.addAnnotatedClass(TQtiTestPlayer.class);
		cfg.addAnnotatedClass(TQtiContentStructure.class);
		cfg.addAnnotatedClass(TQtiContentComposing.class);
		cfg.addAnnotatedClass(TQtiEvalAssessment.class);
		cfg.addAnnotatedClass(WikiEntry.class);
		cfg.addAnnotatedClass(ExerciseGroup.class);
		cfg.addAnnotatedClass(PersonComponentAssignment.class);
		cfg.addAnnotatedClass(ScormSessionTimes.class);
		cfg.addAnnotatedClass(BiTrackContentImpressions.class);

		return cfg.buildSessionFactory();
	}

}
