package de.lemo.dms.dp.umed;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.felix.ipojo.annotations.Requires;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dp.DataProvider;
import de.lemo.dp.ED_Context;
import de.lemo.dp.ED_Person;
import de.lemo.dp.umed.adapters.EDI_Person;
import de.lemo.dp.umed.entities.PersonContext;
import de.lemo.dp.umed.interfaces.IContext;
import de.lemo.dp.umed.interfaces.IPerson;

public class DataProviderUmed implements DataProvider{
	
	@Requires
	private EntityManagerFactory emf;
	private Set<ED_Context> contexts = null; 
	private static DataProviderUmed instance;
	
	private DataProviderUmed()
	{
		
	}
	
	public DataProviderUmed getInstance()
	{
		if(instance == null)
		{
			instance = new DataProviderUmed();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<ED_Context> getCourses() {
		
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Criteria criteria = session.createCriteria(IContext.class, "context");
		
		if(criteria.list().size() > 0)
			this.contexts = new HashSet<ED_Context>(criteria.list());
		else
		{
			session.clear();
			session.close();
			return new HashSet<ED_Context>();
		}
		session.clear();
		session.close();
		return this.contexts;
	}

	@Override
	public Set<ED_Context> getCourses(ED_Person person) {
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Set<ED_Context> contexts = new HashSet<ED_Context>();
		Criteria criteria = session.createCriteria(IPerson.class, "person");
		criteria.add(Restrictions.eq("person.name", person.getName()));
		if(criteria.list().size() == 0)
			return contexts;
		else
		{
			for(PersonContext pc : ((IPerson)criteria.list().get(0)).getPersonContexts())
			{
				contexts.add((ED_Context) pc.getLearningContext());
			}
			
		}		
		return contexts;
		
	}

	@Override
	public ED_Person getPerson(String login) {
		ED_Person person = null;
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		Criteria criteria = session.createCriteria(IPerson.class, "person");
		criteria.add(Restrictions.eq("person.name", login));
		
		if(criteria.list().size() == 0)
		{
			session.clear();
			session.close();
			return new EDI_Person();
		}
		else
		{		
			person = (ED_Person)criteria.list().get(0);
			session.clear();
			session.close();
		}		
		return person;
	}

}
