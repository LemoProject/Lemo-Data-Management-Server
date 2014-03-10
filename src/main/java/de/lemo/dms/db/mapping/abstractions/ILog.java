package de.lemo.dms.db.mapping.abstractions;

import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.User;

public interface ILog extends Comparable<ILog>  {

	long getId();
	
	String getTitle();

	long getTimestamp();

	User getUser();

	Course getCourse();
	
	ILearningObject getLearningObject();
	
	Long getLearningObjectId();

	void setId(long id);
	
}
