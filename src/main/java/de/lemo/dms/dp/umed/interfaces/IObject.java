package de.lemo.dms.dp.umed.interfaces;

import java.util.Set;

import de.lemo.dms.dp.umed.entities.LearningActivity;
import de.lemo.dms.dp.umed.entities.LearningObject;
import de.lemo.dms.dp.umed.entities.LearningObjectExt;
import de.lemo.dms.dp.umed.entities.ObjectContext;

public interface IObject {
	
	public long getId();
	public String getType();
	public Set<LearningActivity> getLearningActivities();
	public Set<ObjectContext> getObjectContexts();
	public LearningObject getParent();
	public Set<LearningObjectExt> getLearningObjectExtensions();
	public String getName();

}
