package de.lemo.dms.db.mapping.abstractions;

import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.User;

public interface ILog extends Comparable<ILog>  {

	long getId();

	Long getTimestamp();

	User getUser();

	Course getCourse();
	
	ILearningObject getLearning();

	void setId(long id);
	
	long getPrefix();
	
	String getType();
	
	Long getLearningId();
	
}
