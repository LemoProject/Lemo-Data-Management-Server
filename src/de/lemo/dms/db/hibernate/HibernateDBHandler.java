package de.lemo.dms.db.hibernate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;

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

	static Session mining_session;
	private DBConfigObject currentConfig;
	private static Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();
	
	@Override
	/**
	 * Saves a list of generic objects to the database.
	 * 
	 */
	public void saveToDB(List<Collection<?>> data) {
		 
		try{
			Transaction tx = mining_session.beginTransaction();
			Long i = 0L;
			boolean isNewTable = true;
			//Iterate through all object-lists
		    for ( Iterator<Collection<?>> iter = data.iterator(); iter.hasNext();) 
		    {
		    	//Iterate through all objects of a mapping-class
		    	Collection<?> l = iter.next();
		    	isNewTable = true;
		    	for ( Iterator<?> iter2 = l.iterator(); iter2.hasNext();) {
	
		    		Object o = iter2.next();
		    		
		    		if(isNewTable)
		    		{
		    			System.out.println("Writing "+l.size()+" elements of "+o.getClass().getName()+" to the database.");
		    			isNewTable = false;
		    		}
		    		
		    		
		    		mining_session.saveOrUpdate(o);
		    		i++;
		    		
		    	    if ( i % 60 == 0 ) {
		    	        //flush a batch of inserts and release memory:
		    	    	mining_session.flush();
		    	    	mining_session.clear();
		    	    }    	    
		    	}
		    }
	    	tx.commit();
		    mining_session.clear();
		    
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	/**
	 * Save a single object to the database.
	 */
	public void saveToDB(Object data) {
		Transaction tx = mining_session.beginTransaction();
		mining_session.saveOrUpdate(data);
		tx.commit();
		mining_session.clear();
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * Opens a connection to the database.
	 * 
	 */
	public void getConnection(DBConfigObject dbConf) {
		try
		{			
			this.currentConfig = dbConf;
			mining_session = de.lemo.dms.db.hibernate.HibernateUtil.getSessionFactoryMining(dbConf).openSession();		
		}catch(HibernateException he)
		{
			System.out.println(he.getMessage());
		}
	}

	@Override
	/**
	 * Closes the database connection.
	 */
	public void closeConnection() {
		try{
			mining_session.close();
		}catch(HibernateException he)
		{logger.info(he.getMessage());}
	}

	@Override
	/**
	 * Performs a Hibernate query.
	 */
	public List<?> performQuery(String query) {
		List<?> l = null;
		try {
			
			l = mining_session.createQuery(query).list();
			
		}catch(HibernateException he)
		{	
			System.out.println(he.getMessage());
		}	
		return l;
	}

}
