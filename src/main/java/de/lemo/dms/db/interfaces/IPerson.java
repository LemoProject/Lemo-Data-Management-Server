package de.lemo.dms.db.interfaces;

import java.util.Set;

import de.lemo.dms.db.mapping.LearningActivity;
import de.lemo.dms.db.mapping.PersonContext;

public interface IPerson {

	public long getId();
	public String getName();
	public Set<PersonContext> getPersonContexts();
	public Set<LearningActivity> getLearningActivities();
}
