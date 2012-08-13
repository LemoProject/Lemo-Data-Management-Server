package de.lemo.dms.db;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

/**
 * Interface for the database connection and database specific operations.
 * 
 * @author s.schwarzrock
 *
 */
public interface IDBHandler {
	
	public Session getSession();

	/**
	 * Saves a collection of lists of mapped objects to the database.
	 * 
	 * @param data list containing collections, containing objects
	 */
	public void saveCollectionToDB(List<Collection<?>> data);
	
	/**
	 * Writes a single object to the database.
	 * 
	 * @param data	Object
	 */
	public void saveToDB(Object data);
	
	/**
	 * Establishes a connection to the mining database.
	 * 
	 * @param dbConf
	 */
	public void getConnection(DBConfigObject dbConf);
	
	/**
	 * 
	 * Closes the connection to the mining database.
	 * 
	 */
	public void closeConnection();
	
	/**
	 * Perfoms a query on the database.
	 * 
	 * @param query	that should be performed. It doesn't need to be a SQL-query 
	 * because the type of the query is database-specific.
	 * @return	list, containing the results of the query 
	 */
	public List<?> performQuery(EQueryType queryType, String query);
	
	/**
	 * 
	 * @return the currrent session
	 */
	public Session getSession(DBConfigObject dbConf);
}
