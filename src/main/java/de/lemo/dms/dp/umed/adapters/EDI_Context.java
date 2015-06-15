package de.lemo.dms.dp.umed.adapters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.lemo.dms.dp.ED_Activity;
import de.lemo.dms.dp.ED_Context;
import de.lemo.dms.dp.ED_Object;
import de.lemo.dms.dp.ED_Person;
import de.lemo.dms.dp.umed.entities.LearningActivity;
import de.lemo.dms.dp.umed.entities.LearningContext;
import de.lemo.dms.dp.umed.entities.ObjectContext;
import de.lemo.dms.dp.umed.entities.PersonContext;
import de.lemo.dms.dp.umed.interfaces.IContext;

public class EDI_Context extends LearningContext implements ED_Context, IContext {

	@Override
	public String getName() {
		return this.getName();
	}

	@Override
	public ED_Context getParentContext() {
		return (EDI_Context)this.getParent();
	}

	@Override
	public List<ED_Context> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ED_Object> getObjects(String type) {
		Set<ED_Object> objects = new HashSet<ED_Object>();
		for(ObjectContext lo : this.getObjectContexts())
		{
			if(lo.getLearningObject().getType().equals(type))
				objects.add((EDI_Object) lo.getLearningObject());
		}
		return objects;
	}

	@Override
	public List<ED_Activity> getActivities(String action) {
		List<ED_Activity> activities = new ArrayList<ED_Activity>();
		for(LearningActivity la : this.getLearningActivities())
		{
			if(la.getAction().equals(action) )
				activities.add((EDI_Activity)la);
		}
		return activities;
	}

	@Override
	public List<ED_Activity> getActivities(String action, Date begin,
			Date end) {
		List<ED_Activity> activities = new ArrayList<ED_Activity>();
		for(LearningActivity la : this.getLearningActivities())
		{
			if(la.getAction().equals(action) && la.getTime() > (begin.getTime() / 1000) && la.getTime() < (end.getTime() / 1000))
				activities.add((EDI_Activity)la);
		}
		return activities;
	}

	@Override
	public Set<ED_Person> getPersons(String role) {
		Set<ED_Person> persons = new HashSet<ED_Person>();
		for(PersonContext pc : this.getPersonContexts())
		{
			if(pc.getRole().equals(role))
				persons.add((EDI_Person)pc.getPerson());
		}
		return persons;
	}

	@Override
	public Set<ED_Person> getStudents() {
		Set<ED_Person> students = new HashSet<ED_Person>();
		for(PersonContext pc : this.getPersonContexts())
		{
			if(pc.getRole().equals("student"))
				students.add((EDI_Person)pc.getPerson());
		}
		return students;
	}

	@Override
	public Set<ED_Person> getInstructors() {
		Set<ED_Person> instructors = new HashSet<ED_Person>();
		for(PersonContext pc : this.getPersonContexts())
		{
			if(pc.getRole().equals("teacher"))
				instructors.add((EDI_Person)pc.getPerson());
		}
		return instructors;
	}

}
