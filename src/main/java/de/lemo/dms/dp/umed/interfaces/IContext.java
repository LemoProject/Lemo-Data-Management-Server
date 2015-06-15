package de.lemo.dms.dp.umed.interfaces;

import java.util.Set;

import de.lemo.dms.dp.umed.entities.LearningActivity;
import de.lemo.dms.dp.umed.entities.ObjectContext;
import de.lemo.dms.dp.umed.entities.PersonContext;

public interface IContext {
	
	public long getId();
	public IContext getParent();
	public String getName();
	public Set<ObjectContext> getObjectContexts();
	public Set<LearningActivity> getLearningActivities();
	public Set<PersonContext> getPersonContexts();

}
