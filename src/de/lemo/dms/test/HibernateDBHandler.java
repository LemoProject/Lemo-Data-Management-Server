package de.lemo.dms.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * Implementation of the IDBHandler interface for Hibernate.
 * 
 * 
 * @author s.schwarzrock
 *
 */
public class HibernateDBHandler implements IDBHandler{

	static Session session;
	private static Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();
	
	
	public Session getSession()
	{
		return session;
	}
	@Override
	/**
	 * Saves a list of generic objects to the database.
	 * 
	 */
	public void saveCollectionToDB(List<Collection<?>> data) {

		List<Object> objects = new ArrayList<Object>();
		session.clear();
		try{		
			
			
			for ( Iterator<Collection<?>> iter = data.iterator(); iter.hasNext();) 
		    {
				Collection<?> l = iter.next();
				HashSet<IMappingClass> isIn = new HashSet<IMappingClass>();
		    	for ( Iterator<?> iter2 = l.iterator(); iter2.hasNext();) {
		    		Object o = iter2.next();
		    		
		    		/*if(isIn.contains((IMappingClass)o))
		    			System.out.println("double" + o.getClass());
		    		else
		    		{*/
		    			//isIn.add((IMappingClass)o);
		    			objects.add(o);
		    		//}
		    		
		    	}
		    }
			Transaction tx = session.beginTransaction();
			int classOb = 0;
			String className = "";
			for(int i = 0; i < objects.size(); i++)
			{
				
				if(!className.equals("") && !className.equals(objects.get(i).getClass().getName()))
				{
					System.out.println("Wrote " + classOb +" objects of class "+className);
					classOb = 0;
				}
				className = objects.get(i).getClass().getName();
					
				classOb++;
				//mining_session.saveOrUpdate(objects.get(i));
				session.save(objects.get(i));
				
				if ( i % 50 == 0 ) {
	    	        //flush a batch of inserts and release memory:
	    	    	session.flush();
	    	    	session.clear();
	    	    }     
			}
			System.out.println("Wrote " + classOb +" objects of class " + className + " to database.");			
			tx.commit();	    	
		    session.clear();
		    
		}catch(HibernateException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	/**
	 * Save a single object to the database.
	 */
	public void saveToDB(Object data) {
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(data);
		tx.commit();
		session.clear();
		// TODO Auto-generated method stub
		
	}
	
    public Session getSession(DBConfigObject dbConf){
        getConnection(dbConf);
        return session;
    }

	@Override
	/**
	 * Opens a connection to the database.
	 * 
	 */
	public void getConnection(DBConfigObject dbConf) {
		try
		{			
			if(session == null || !session.isOpen())
				session = de.lemo.dms.connectors.moodle.HibernateUtil.getDynamicSourceDBFactoryMoodle(dbConf).openSession();
		}catch(HibernateException he)
		{
			System.out.println("Get connection failed: " + he.getMessage());
		}
	}

	@Override
	/**
	 * Closes the database connection.
	 */
	public void closeConnection() {
		try{
			session.close();
		}catch(HibernateException he)
		{logger.info(he.getMessage());}
	}

	@Override
	/**
	 * Performs a Hibernate query.
	 */
	public List<?> performQuery(EQueryType queryType, String query) {
		List<?> l = null;
		try {
			if(queryType == EQueryType.SQL)
				l = session.createSQLQuery(query).list();
			else if(queryType == EQueryType.HQL)
				l = session.createQuery(query).list();
		}catch(HibernateException he)
		{	
			System.out.println("Exception: "+ he.getMessage());
		}	
		return l;
	}
}
