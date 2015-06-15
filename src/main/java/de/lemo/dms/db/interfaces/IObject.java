package de.lemo.dms.db.interfaces;

import java.util.Set;

import de.lemo.dms.db.mapping.LearningActivity;
import de.lemo.dms.db.mapping.LearningObject;
import de.lemo.dms.db.mapping.LearningObjectExt;
import de.lemo.dms.db.mapping.ObjectContext;

public interface IObject {
	
	public long getId();
	public String getType();
	public Set<LearningActivity> getLearningActivities();
	public Set<ObjectContext> getObjectContexts();
	public LearningObject getParent();
	public Set<LearningObjectExt> getLearningObjectExtensions();
	public String getName();

}
