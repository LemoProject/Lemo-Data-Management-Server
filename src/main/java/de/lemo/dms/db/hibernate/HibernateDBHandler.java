/**
 * File ./src/main/java/de/lemo/dms/db/hibernate/HibernateDBHandler.java
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
 * File ./main/java/de/lemo/dms/db/hibernate/HibernateDBHandler.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.hibernate;

import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;

/**
 * Implementation of the IDBHandler interface for Hibernate.
 * 
 * @author s.schwarzrock
 */
public class HibernateDBHandler implements IDBHandler {

	private static final int BATCH_SIZE = 50;

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Session getMiningSession() {
		return MiningHibernateUtil.getSessionFactory().openSession();
	}

	/**
	 * Saves a list of generic objects to the database.
	 */
	@Override
	public void saveCollectionToDB(final Session session, final List<Collection<?>> data) {

		try {
			int classOb = 0;
			String className = "";
			Transaction tx = session.beginTransaction();
			int i = 0;
			for (int j = 0; j < data.size(); j++) {
				for (final Object obj : data.get(j))
				{

					if (!className.equals("") && !className.equals(obj.getClass().getName()))
					{
						this.logger.info("Wrote " + classOb + " objects of class " + className);
						classOb = 0;
					}
					className = obj.getClass().getName();
					i++;
					classOb++;
					session.saveOrUpdate(obj);
					if ((i % HibernateDBHandler.BATCH_SIZE) == 0)
					{
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}
				}
			}
			this.logger.info("Wrote " + classOb + " objects of class " + className + " to database.");
			session.flush();
			tx.commit();
			session.clear();
			//this.closeSession(session);
		} catch (final HibernateException e)
		{
			logger.error(e.getMessage());
		}
	}

	/**
	 * Save a single object to the database.
	 */
	@Override
	public void saveToDB(final Session session, final Object data) {
		final Transaction tx = session.beginTransaction();
		session.saveOrUpdate(data);
		tx.commit();
		session.clear();
	}

	/**
	 * Closes the database connection.
	 */
	@Override
	public void closeSession(final Session session) {
		try {
			session.clear();
			session.close();
		} catch (final HibernateException he)
		{
			logger.error(he.getMessage());
		}
	}

	/**
	 * Performs a Hibernate query.
	 */
	@Override
	public List<?> performQuery(final Session session, final EQueryType queryType, final String query) {
		try {
			switch (queryType) {
				case HQL:
					return session.createQuery(query).list();
				case SQL:
					return session.createSQLQuery(query).list();
				default:
					break;
			}
		} catch (final HibernateException he) {
			logger.error(he.getMessage());
		}
		return null;
	}

}
