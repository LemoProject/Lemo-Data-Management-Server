package de.lemo.dms.db;

import java.util.HashMap;
import java.util.Map;

public class ED_Person {
	
	private Long id;
	private String name;
	private Map<Long, String> contextRoles = new HashMap<Long, String>();
	private Map<String, String> extensions = new HashMap<String, String>();
	
	/**
	 * Adds a context-role pair to the Person
	 * 
	 * @param attr	The LearningContext id
	 * @param value The Person's role within the LearningContext
	 */
	public void addContextRole(Long context, String role)
	{
		this.contextRoles.put(context, role);
	}
	
	/**
	 * Adds an extension attribute-value pair to the Person
	 * 
	 * @param attr	The attribute's name
	 * @param value The attribute's value
	 */
	public void addExtension(String attr, String value)
	{
		this.extensions.put(attr, value);
	}
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the contextRoles
	 */
	public Map<Long, String> getContextRoles() {
		return contextRoles;
	}
	/**
	 * @param contextRoles the contextRoles to set
	 */
	public void setContextRoles(Map<Long, String> contextRoles) {
		this.contextRoles = contextRoles;
	}
	/**
	 * @return the extensions
	 */
	public Map<String, String> getExtensions() {
		return extensions;
	}
	/**
	 * @param extensions the extensions to set
	 */
	public void setExtensions(Map<String, String> extensions) {
		this.extensions = extensions;
	}
	

}
