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

    private static Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();

    private static SessionFactory miningSessionFactory =
            de.lemo.dms.db.hibernate.HibernateUtil.getSessionFactoryMining(
                    ServerConfigurationHardCoded.getInstance().getMiningDBConfig());

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
        try {

            for(Iterator<Collection<?>> iter = data.iterator(); iter.hasNext();)
            {
                Collection<?> l = iter.next();
                HashSet<IMappingClass> isIn = new HashSet<IMappingClass>();
                for(Iterator<?> iter2 = l.iterator(); iter2.hasNext();) {
                    Object o = iter2.next();

                    if(isIn.contains((IMappingClass) o))
                        logger.info("double " + o.getClass());
                    else
                    {
                        isIn.add((IMappingClass) o);
                        objects.add(o);
                    }

                }
            }
            Transaction tx = session.beginTransaction();
            int classOb = 0;
            String className = "";
            for(int i = 0; i < objects.size(); i++)
            {

                if(!className.equals("") && !className.equals(objects.get(i).getClass().getName()))
                {
                    logger.info("Wrote " + classOb + " objects of class " + className);
                    classOb = 0;
                }
                className = objects.get(i).getClass().getName();

                classOb++;
                // session.saveOrUpdate(objects.get(i));
                session.saveOrUpdate(objects.get(i));

                if(i % 50 == 0) {
                    // flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }
            logger.info("Wrote " + classOb + " objects of class " + className + " to database.");
            tx.commit();
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
