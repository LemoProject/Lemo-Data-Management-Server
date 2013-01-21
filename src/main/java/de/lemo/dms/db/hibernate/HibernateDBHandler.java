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
 * 
 * @author s.schwarzrock
 * 
 */
public class HibernateDBHandler implements IDBHandler {

    private static final int BATCH_SIZE = 50;

    private Logger logger = Logger.getLogger(getClass());

    public Session getMiningSession() {
        return MiningHibernateUtil.getSessionFactory().openSession();
    }

    /**
     * Saves a list of generic objects to the database.
     * 
     */
    @Override
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
                    session.merge(obj);
                    if(i % BATCH_SIZE == 0)
                    {
                        // flush a batch of inserts and release memory:
                        session.flush();
                        session.clear();
                    }
                }
            logger.debug("Wrote " + classOb + " objects of class " + className + " to database.");
            session.flush();            
            session.getTransaction().commit();
            session.clear();            
            closeSession(session);
        } catch (HibernateException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Save a single object to the database.
     */
    @Override
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
        	session.clear();
            session.close();
        } catch (HibernateException he)
        {
            he.printStackTrace();
        }
    }

    /**
     * Performs a Hibernate query.
     */
    @Override
    public List<?> performQuery(Session session, EQueryType queryType, String query) {
        try {
            switch(queryType) {
            case HQL:
                return session.createQuery(query).list();
            case SQL:
                return session.createSQLQuery(query).list();
            default:
                break;
            }
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return null;
    }

}
