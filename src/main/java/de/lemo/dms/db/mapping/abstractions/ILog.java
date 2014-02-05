package de.lemo.dms.db.mapping.abstractions;

import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.User;

public interface ILog extends Comparable<ILog>  {

	long getId();

	long getTimestamp();

	User getUser();

	Course getCourse();

	void setId(long id);
	
}
