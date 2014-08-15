/**
 * File ./src/main/java/de/lemo/dms/db/IDBHandler.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

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
