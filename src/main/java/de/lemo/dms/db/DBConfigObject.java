/**
 * File ./src/main/java/de/lemo/dms/db/DBConfigObject.java
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
 * File ./main/java/de/lemo/dms/db/DBConfigObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db;

import java.util.HashMap;
import java.util.Map;

/**
 * This class has been created to give users the possibility to save and submit parameters for the database connection.
 */
public class DBConfigObject {

	private final Map<String, String> properties;

	public DBConfigObject() {
		this.properties = new HashMap<String, String>();
	}

	public DBConfigObject(final Map<String, String> properties) {
		this.properties = properties;
	}

	/**
	 * Adds a key-value-pair to the property list.
	 * 
	 * @param property
	 *            name of the database addressed parameter
	 * @param value
	 *            designated value of the parameter
	 */
	public void setProperty(final String property, final String value)
	{
		this.properties.put(property, value);
	}

	/**
	 * Returns a copy of all properties as key-value-pairs in a HashMap.
	 * 
	 * @return A HashMap holding the properties.
	 */
	public Map<String, String> getProperties()
	{
		return new HashMap<String, String>(this.properties);
	}

	/**
	 * Deletes all properties.
	 */
	public void clearProperties()
	{
		this.properties.clear();
	}

	/**
	 * Returns the value of the given property.
	 * 
	 * @param property
	 * @return the value saved for the property. If the property is unknown, it will return NULL
	 */
	public String getPropertyValue(final String property)
	{
		return this.properties.get(property);
	}

}
