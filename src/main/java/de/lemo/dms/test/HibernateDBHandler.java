/**
 * File ./main/java/de/lemo/dms/test/HibernateDBHandler.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
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

					/*
					 * if(isIn.contains((IMappingClass)o)) System.out.println("double" + o.getClass()); else {
					 */
					// isIn.add((IMappingClass)o);
					objects.add(o);
					// }

				}
			}
			final Transaction tx = session.beginTransaction();
			int classOb = 0;
			String className = "";
			for (int i = 0; i < objects.size(); i++)
			{

				if (!className.equals("") && !className.equals(objects.get(i).getClass().getName()))
				{
					System.out.println("Wrote " + classOb + " objects of class " + className);
					classOb = 0;
				}
				className = objects.get(i).getClass().getName();

				classOb++;
				// mining_session.saveOrUpdate(objects.get(i));
				session.save(objects.get(i));

				if ((i % 50) == 0) {
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
			}
			System.out.println("Wrote " + classOb + " objects of class " + className + " to database.");
			tx.commit();
			session.clear();

		} catch (final HibernateException e)
		{
			e.printStackTrace();
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
			this.logger.info(he.getMessage());
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
			System.out.println("Exception: " + he.getMessage());
		}
		return l;
	}
}
