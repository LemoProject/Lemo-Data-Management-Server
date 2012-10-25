package de.lemo.dms.db.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**
 * Implementation of the IDBHandler interface for Hibernate.
 * 
 * 
 * @author s.schwarzrock
 * 
 */
public class HibernateDBHandler implements IDBHandler {

    private Logger logger = Logger.getLogger(getClass());

    private static SessionFactory miningSessionFactory =
            de.lemo.dms.db.hibernate.HibernateUtil.getSessionFactoryMining();

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
            for(Collection<?> collection:data)
            {
                for(Object obj:collection) 
                {
                	
                	if(!className.equals("") && !className.equals(obj.getClass().getName()))
                    {
                         System.out.println("Wrote " + classOb + " objects of class " + className);
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
            }
            System.out.println("Wrote " + classOb + " objects of class " + className + " to database.");
            session.getTransaction().commit();
            session.clear();
        } catch (HibernateException e)
        {
            e.printStackTrace();
        }
        closeSession(session);
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
