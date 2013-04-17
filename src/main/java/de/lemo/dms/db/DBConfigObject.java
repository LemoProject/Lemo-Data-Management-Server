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
