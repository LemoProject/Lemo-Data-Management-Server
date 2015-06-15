package de.lemo.dms.dp;


import java.util.*;

public interface ED_Context {
	
	public String getName();
	
	public ED_Context getParentContext();
	
	public List<ED_Context> getChildren();
	
	public Set<ED_Object> getObjects(String type);
	
	public List<ED_Activity> getActivities(String action);
	
	public List<ED_Activity> getActivities(
			String action, Date begin, Date end);
	
	public Set<ED_Person> getPersons(String role);
	
	public Set<ED_Person> getStudents();
	
	public Set<ED_Person> getInstructors();
	
}
