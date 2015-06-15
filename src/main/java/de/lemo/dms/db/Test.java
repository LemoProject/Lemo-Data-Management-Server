package de.lemo.dms.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import de.lemo.dms.db.interfaces.IContext;
import de.lemo.dms.dp.umed.adapters.EDI_Context;

public class Test {
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	
	
	{
		HashSet<EDI_Context> contexts = new HashSet<EDI_Context>();
		ServerConfiguration.getInstance().loadConfig("/lemo");
		Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();

		try{
		Criteria criteria = session.createCriteria(IContext.class, "context");
		criteria.add(Restrictions.isNull("context.parent"));
			
		if(criteria.list().size() > 0)
		{
			List<EDI_Context> bla = criteria.list();
			contexts = new HashSet<EDI_Context>( bla);
		}
		session.clear();
		session.close();
		for(EDI_Context c : contexts)
		{
			System.out.println(c.getName());
		}
		}catch(Exception e)
		{System.out.println(e.getMessage());}
		
	}
			
}
