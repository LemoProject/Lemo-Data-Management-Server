package de.lemo.dms.processing.features;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.Attribute;
import de.lemo.dms.db.mapping.LearningAttribute;


/* Fetches all content fields from the database and exposes them to the processContent method.
 * To create a new ContentProcessor this method should be implemented and an attribute name should be provided.
 * If an attribute with the given name can be found it is used, else an new one is created.
 */
public abstract class ContentProcessor {

	private Session session;
	private List<LearningAttribute> learningAttributes;
	private String attributeName;

	public ContentProcessor(){
		attributeName="";
		process();
	}
	
	public ContentProcessor(String name){
		attributeName=name;
		process();
	}

	private void process(){
		session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		learningAttributes = new ArrayList<LearningAttribute>();

		queryAllPosts();
		addLearningAttribute();
		addIds();
		processContent(learningAttributes);
		
		session.close();
	}

	protected abstract void processContent(List<LearningAttribute> contentAttributes);
	
	private void addIds() {
		Criteria criteria = session.createCriteria(LearningAttribute.class, "attribute");
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		LearningAttribute maxAttribute = (LearningAttribute) criteria.uniqueResult();
		Long nextId = maxAttribute.getId()+1;
		for(LearningAttribute learningAttribute : learningAttributes){
			learningAttribute.setId(nextId);
			nextId++;
		}		
	}

	private void addLearningAttribute(){
		Attribute attribute = getAttribute();
		if(attribute==null){
			attribute=createAttribute();
		}
		for(LearningAttribute learningAttribute : learningAttributes){
			learningAttribute.setAttribute(attribute);
		}		
	}

	

	private Attribute createAttribute() {
		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();	
		Attribute attribute = new Attribute();		
		Criteria criteria = session.createCriteria(Attribute.class);
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		Attribute maxAttribute = (Attribute) criteria.uniqueResult();
		Long nextId = maxAttribute.getId()+1;
		attribute.setId(nextId);
		attribute.setName(attributeName);
		dbHandler.saveToDB(session, attribute);
		return attribute;
	}
	
	@SuppressWarnings("unchecked")
	private Attribute getAttribute() {
		Criteria criteria = session.createCriteria(Attribute.class, "attribute");
		criteria.add(Restrictions.eq("attribute.name", attributeName));
		List<Attribute> attributes = criteria.list();
		return attributes.isEmpty()? null : attributes.get(0);
	}

	@SuppressWarnings("unchecked")
	private void queryAllPosts(){
		Attribute posts = new Attribute();
		posts.setId(6L);
		Criteria criteria = session.createCriteria(LearningAttribute.class, "la");
		criteria.add(Restrictions.eq("la.attribute", posts));
		learningAttributes = criteria.list();
	}
	
	public List<LearningAttribute> getLearningAttributes(){
		return learningAttributes;
	}
}
