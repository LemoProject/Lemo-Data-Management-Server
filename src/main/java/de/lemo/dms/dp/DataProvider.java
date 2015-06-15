package de.lemo.dms.dp;

import java.util.Set;

public interface DataProvider {
	
	public Set<ED_Context> getCourses();
	
	public Set<ED_Context> getCourses(ED_Person person);
	
	public ED_Person getPerson(String login);
	
}
