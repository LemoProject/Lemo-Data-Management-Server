package de.lemo.dms.db;

import java.util.HashMap;

/**
 * This class has been created to give users the possibility to save and submit parameters for the database connection.
 */
public class DBConfigObject {

    private HashMap<String, String> properties;

    public DBConfigObject() {
        properties = new HashMap<String, String>();
    }

    public DBConfigObject(HashMap<String, String> properties) {
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
    public void setProperty(String property, String value)
    {
        this.properties.put(property, value);
    }

    /**
     * Returns a copy of all properties as key-value-pairs in a HashMap.
     * 
     * @return A HashMap holding the properties.
     */
    public HashMap<String, String> getProperties()
    {
        return new HashMap<String, String>(properties);
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
    public String getPropertyValue(String property)
    {
        return this.properties.get(property);
    }

}
