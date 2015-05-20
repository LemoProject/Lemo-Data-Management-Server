package de.lemo.dms.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.mapping.LearningContext;
import de.lemo.dms.db.mapping.LearningContextExt;
import de.lemo.dms.db.mapping.ObjectContext;
import de.lemo.dms.db.mapping.Person;
import de.lemo.dms.db.mapping.PersonContext;
import de.lemo.dms.db.mapping.PersonExt;

public class Test {
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		ServerConfiguration.getInstance().loadConfig("/lemo");
		Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();

		
		Map<Long, ED_LearningContext> edContexts = new HashMap<Long, ED_LearningContext>();
		
		List<Long> ids = new ArrayList<Long>();
		Long id = 4122L;
		String s = "teacher";
		
		Map<Long, ED_Person> persons = new HashMap<Long, ED_Person>();
		
		
		Criteria criteria = session.createCriteria(PersonContext.class, "personContext");
		criteria.add(Restrictions.eq("personContext.learningContext.id", id));
		if(s != null)
			criteria.add(Restrictions.like("personContext.role", s));
		
		for(PersonContext pc : (List<PersonContext>) criteria.list())
		{
			ED_Person edPerson = new ED_Person();
			edPerson.setId(pc.getPerson().getId());
			edPerson.addContextRole(pc.getLearningContext().getId(), pc.getRole());
			ids.add(edPerson.getId());
			persons.put(edPerson.getId(), edPerson);
		}
		
		criteria = session.createCriteria(Person.class, "person");
		criteria.add(Restrictions.in("person.id", ids));
		
		for(Person p : (List<Person>) criteria.list())
		{
			if(persons.get(p.getId()) != null)
			{
				persons.get(p.getId()).setName(p.getName());
			}
		}
		
		criteria = session.createCriteria(PersonExt.class, "personExt");
		criteria.add(Restrictions.in("personExt.person.id", ids));
		
		for(PersonExt pExt : (List<PersonExt>) criteria.list())
		{
			if(persons.get(pExt.getPerson().getId()) != null)
			{
				persons.get(pExt.getPerson().getId()).addExtension(pExt.getAttr(), pExt.getValue());
			}
		}
		
		System.out.println();
	}
	
}
