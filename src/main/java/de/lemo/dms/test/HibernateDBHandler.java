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
 * 
 * @author s.schwarzrock
 * 
 */
public class HibernateDBHandler implements IDBHandler {

    private Logger logger = Logger.getLogger(getClass());
    private SessionFactory miningSessionFactory = MiningHibernateUtil.getSessionFactory();

    public Session getMiningSession() {
        return miningSessionFactory.openSession();
    }

    @Override
    /**
     * Saves a list of generic objects to the database.
     * 
     */
    public void saveCollectionToDB(Session session, List<Collection<?>> data) {

        List<Object> objects = new ArrayList<Object>();
        session.clear();
        try {

            for(Iterator<Collection<?>> iter = data.iterator(); iter.hasNext();)
            {
                Collection<?> l = iter.next();
                for(Iterator<?> iter2 = l.iterator(); iter2.hasNext();) {
                    Object o = iter2.next();

                    /*
                     * if(isIn.contains((IMappingClass)o)) System.out.println("double" + o.getClass()); else {
                     */
                    // isIn.add((IMappingClass)o);
                    objects.add(o);
                    // }

                }
            }
            Transaction tx = session.beginTransaction();
            int classOb = 0;
            String className = "";
            for(int i = 0; i < objects.size(); i++)
            {

                if(!className.equals("") && !className.equals(objects.get(i).getClass().getName()))
                {
                    System.out.println("Wrote " + classOb + " objects of class " + className);
                    classOb = 0;
                }
                className = objects.get(i).getClass().getName();

                classOb++;
                // mining_session.saveOrUpdate(objects.get(i));
                session.save(objects.get(i));

                if(i % 50 == 0) {
                    // flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }
            System.out.println("Wrote " + classOb + " objects of class " + className + " to database.");
            tx.commit();
            session.clear();

        } catch (HibernateException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    /**
     * Save a single object to the database.
     */
    public void saveToDB(Session session, Object data) {
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(data);
        tx.commit();
        session.clear();

    }

    @Override
    /**
     * Closes the database connection.
     */
    public void closeSession(Session session) {
        try {
            session.close();
        } catch (HibernateException he)
        {
            logger.info(he.getMessage());
        }
    }

    @Override
    /**
     * Performs a Hibernate query.
     */
    public List<?> performQuery(Session session, EQueryType queryType, String query) {
        List<?> l = null;
        try {
            if(queryType == EQueryType.SQL)
                l = session.createSQLQuery(query).list();
            else if(queryType == EQueryType.HQL)
                l = session.createQuery(query).list();
        } catch (HibernateException he)
        {
            System.out.println("Exception: " + he.getMessage());
        }
        return l;
    }
}
