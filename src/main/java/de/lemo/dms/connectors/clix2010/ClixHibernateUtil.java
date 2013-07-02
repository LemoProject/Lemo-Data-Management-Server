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
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/EComponent.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/LearningLog.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ChatProtocol.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/EComponentType.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/EComposing.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ExercisePersonalised.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntry.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntryState.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/Person.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroup.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroupSpecification.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/Portfolio.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PortfolioLog.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TeamExerciseGroup.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TGroupFullSpecification.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiContent.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestItemD.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestPlayerResp.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestPlayer.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiContentStructure.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiContentComposing.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiEvalAssessment.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/WikiEntry.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ExerciseGroup.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PersonComponentAssignment.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ScormSessionTimes.hbm.xml");
		cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/BiTrackContentImpressions.hbm.xml");

		return cfg.buildSessionFactory();
	}

}
