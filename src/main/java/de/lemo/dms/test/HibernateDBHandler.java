/**
 * File ./src/main/java/de/lemo/dms/test/HibernateDBHandler.java
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
 * File ./main/java/de/lemo/dms/test/HibernateDBHandler.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.hibernate.MiningHibernateUtil;

/**
 * Implementation of the IDBHandler interface for Hibernate.
 * 
 * @author s.schwarzrock
 */
public class HibernateDBHandler implements IDBHandler {

	private final Logger logger = Logger.getLogger(this.getClass());
	private final SessionFactory miningSessionFactory = MiningHibernateUtil.getSessionFactory();

	@Override
	public Session getMiningSession() {
		return this.miningSessionFactory.openSession();
	}

	@Override
	/**
	 * Saves a list of generic objects to the database.
	 * 
	 */
	public void saveCollectionToDB(final Session session, final List<Collection<?>> data) {

		final List<Object> objects = new ArrayList<Object>();
		session.clear();
		try {

			for (final Iterator<Collection<?>> iter = data.iterator(); iter.hasNext();)
			{
				final Collection<?> l = iter.next();
				for (final Iterator<?> iter2 = l.iterator(); iter2.hasNext();) {
					final Object o = iter2.next();

					objects.add(o);
				}
			}
			final Transaction tx = session.beginTransaction();
			int classOb = 0;
			String className = "";
			for (int i = 0; i < objects.size(); i++)
			{

				if (!className.equals("") && !className.equals(objects.get(i).getClass().getName()))
				{
					logger.info("Wrote " + classOb + " objects of class " + className);
					classOb = 0;
				}
				className = objects.get(i).getClass().getName();

				classOb++;
				session.save(objects.get(i));

				if ((i % 50) == 0) {
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
			}
			logger.info("Wrote " + classOb + " objects of class " + className + " to database.");
			tx.commit();
			session.clear();

		} catch (final HibernateException e)
		{
			logger.error(e.getMessage());
		}

	}

	@Override
	/**
	 * Save a single object to the database.
	 */
	public void saveToDB(final Session session, final Object data) {
		final Transaction tx = session.beginTransaction();
		session.saveOrUpdate(data);
		tx.commit();
		session.clear();

	}

	@Override
	/**
	 * Closes the database connection.
	 */
	public void closeSession(final Session session) {
		try {
			session.close();
		} catch (final HibernateException he)
		{
			this.logger.error(he.getMessage());
		}
	}

	@Override
	/**
	 * Performs a Hibernate query.
	 */
	public List<?> performQuery(final Session session, final EQueryType queryType, final String query) {
		List<?> l = null;
		try {
			if (queryType == EQueryType.SQL) {
				l = session.createSQLQuery(query).list();
			} else if (queryType == EQueryType.HQL) {
				l = session.createQuery(query).list();
			}
		} catch (final HibernateException he)
		{
			logger.error(he.getMessage());
		}
		return l;
	}
}
