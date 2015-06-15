package de.lemo.dms.db.interfaces;

import java.util.Set;

import de.lemo.dms.db.mapping.LearningActivity;
import de.lemo.dms.db.mapping.ObjectContext;
import de.lemo.dms.db.mapping.PersonContext;

public interface IContext {
	
	public long getId();
	public IContext getParent();
	public String getName();
	public Set<ObjectContext> getObjectContexts();
	public Set<LearningActivity> getLearningActivities();
	public Set<PersonContext> getPersonContexts();

}
