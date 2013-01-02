package de.lemo.dms.db.hibernate;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;

/**
 * Implementation of the IDBHandler interface for Hibernate.
 * 
 * 
 * @author s.schwarzrock
 * 
 */
public class HibernateDBHandler implements IDBHandler {

    private Logger logger = Logger.getLogger(getClass());
    private SessionFactory miningSessionFactory;

    public HibernateDBHandler(Configuration config) {
        miningSessionFactory = de.lemo.dms.db.hibernate.HibernateUtil.getSessionFactoryMining(config);
    }

    public Session getMiningSession() {
        return miningSessionFactory.openSession();
    }

    @Override
    /**
     * Saves a list of generic objects to the database.
     * 
     */
    public void saveCollectionToDB(Session session, List<Collection<?>> data) {

        try {
            int classOb = 0;
            String className = "";
            session.beginTransaction();
            int i = 0;
            for(int j = 0; j < data.size(); j++)
                for(Object obj : data.get(j))
                {

                    if(!className.equals("") && !className.equals(obj.getClass().getName()))
                    {
                        logger.debug("Wrote " + classOb + " objects of class " + className);
                        classOb = 0;
                    }
                    className = obj.getClass().getName();
                    i++;
                    classOb++;
                    session.saveOrUpdate(obj);
                    if(i % 50 == 0)
                    {
                        // flush a batch of inserts and release memory:
                        session.flush();
                        session.clear();
                    }
                }
            logger.debug("Wrote " + classOb + " objects of class " + className + " to database.");
            session.getTransaction().commit();
            session.close();
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

    /**
     * Closes the database connection.
     */
    public void closeSession(Session session) {
        try {
            session.close();
        } catch (HibernateException he)
        {
            he.printStackTrace();
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
            he.printStackTrace();
        }
        return l;
    }

}