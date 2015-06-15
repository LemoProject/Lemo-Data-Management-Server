package de.lemo.dms.dp.umed.interfaces;

import java.util.Set;

import de.lemo.dms.dp.umed.entities.LearningActivity;
import de.lemo.dms.dp.umed.entities.PersonContext;

public interface IPerson {

	public long getId();
	public String getName();
	public Set<PersonContext> getPersonContexts();
	public Set<LearningActivity> getLearningActivities();
}
