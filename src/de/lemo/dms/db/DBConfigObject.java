package de.lemo.dms.db;

import java.util.HashMap;

/**
 * The Class DBConfigObject.
 */
public class DBConfigObject {
	
	/**
	 * The Enum Connectortype.
	 */
	public enum ConnectorType{
		
		/** The Moodle19. */
		Moodle19;
	}	
	
	private ConnectorType connector = ConnectorType.Moodle19;
	
	private HashMap<String, String> properties = new HashMap<String, String>();
	
	public void addProperty(String property, String value)
	{
		this.properties.put(property, value);
	}
	
	public HashMap<String, String> getProperties()
	{
		return this.properties;
	}
	
	public void clearProperties()
	{
		this.properties.clear();
	}
		
	public String getPropertyValue(String property)
	{
		return this.properties.get(property);
	}	
	
	public ConnectorType getConnectorType()
	{
		return this.connector;
	}
	
	public void setConnectorType(ConnectorType connector)
	{
		this.connector = connector;
	}

}
