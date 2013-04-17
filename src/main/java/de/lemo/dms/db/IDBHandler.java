/**
 * File ./main/java/de/lemo/dms/db/IDBHandler.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db;

import java.util.Collection;
import java.util.List;
import org.hibernate.Session;

/**
 * Interface for the database connection and database specific operations.
 * 
 * @author s.schwarzrock
 */
public interface IDBHandler {

	/**
	 * Get a session, most likely from a session pool.
	 * 
	 * @return a session
	 */
	Session getMiningSession();

	/**
	 * Saves a collection of lists of mapped objects to the database.
	 * 
	 * @param data
	 *            list containing collections, containing objects
	 */
	void saveCollectionToDB(Session session, List<Collection<?>> data);

	/**
	 * Writes a single object to the database.
	 * 
	 * @param data
	 *            Object
	 */
	void saveToDB(Session session, Object data);

	/**
	 * Perfoms a query on the database.
	 * 
	 * @param query
	 *            that should be performed. It doesn't need to be a SQL-query
	 *            because the type of the query is database-specific.
	 * @return list, containing the results of the query
	 */
	List<?> performQuery(Session session, EQueryType queryType, String query);

	void closeSession(Session session);
}
