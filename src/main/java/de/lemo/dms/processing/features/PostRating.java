package de.lemo.dms.processing.features;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.Attribute;
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.LearningAttribute;
import de.lemo.dms.db.mapping.LearningObj;
public class PostRating {

	private List<LearningAttribute> learningAttributes;
	private Session session;
	private String attributeName;
	private long nextId;
	
	
	public PostRating(){
		attributeName = "PostRating";
		learningAttributes = new ArrayList<LearningAttribute>();
	}
	
	public void process(){
		session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		queryNextLearningAttributeId();
		queryAllLogs();
		session.close();
	}
	
	private void queryNextLearningAttributeId() {
		Criteria criteria = session.createCriteria(LearningAttribute.class, "attribute");
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		LearningAttribute maxAttribute = (LearningAttribute) criteria.uniqueResult();
		nextId = maxAttribute.getId()+1;		
	}

	private void queryAllLogs(){
		Criteria criteria = session.createCriteria(CollaborationLog.class)
	            .add(Restrictions.eq("action", "vote"))
				.add(Restrictions.eq("text", "down"));
	    ProjectionList projectionList = Projections.projectionList();
	    projectionList.add(Projections.groupProperty("learning"));
	    projectionList.add(Projections.rowCount());
	    criteria.setProjection(projectionList);
	    List<Object[]> downVotes = criteria.list();
	    for (Object[] obj : downVotes) {
	    	addVotesToLearningObj((LearningObj)obj[0],-(Long)obj[1]);
	    }
	    
		criteria = session.createCriteria(CollaborationLog.class)
	            .add(Restrictions.eq("action", "vote"))
				.add(Restrictions.eq("text", "up"));
	    projectionList = Projections.projectionList();
	    projectionList.add(Projections.groupProperty("learning"));
	    projectionList.add(Projections.rowCount());
	    criteria.setProjection(projectionList);
	    List<Object[]> upVotes = criteria.list();
	    for (Object[] obj : upVotes) {
	    	addVotesToLearningObj((LearningObj)obj[0],(Long)obj[1]);
	    }
	}
	
	//Checks if there is already a learning attribute corresponding to the learning object 
	// or creates a new one.
	private void addVotesToLearningObj(LearningObj learning, long l) {
		LearningAttribute learningAttribute = null;
		for(LearningAttribute attribute : learningAttributes){
			if(attribute.getLearning().equals(learning)){
				learningAttribute = attribute;
			}
		}
		if(learningAttribute!=null){
			Long postRating = Long.valueOf(learningAttribute.getValue());
			learningAttribute.setValue(String.valueOf((postRating+l)));			
		} else {
			learningAttributes.add(createLearningAttribute(learning,l));
		}
	}

	private LearningAttribute createLearningAttribute(LearningObj learning, long l) {
		LearningAttribute learningAttribute = new LearningAttribute();
		learningAttribute.setLearning(learning);
		learningAttribute.setAttribute(queryAttribute());
		learningAttribute.setValue(String.valueOf(l));
		learningAttribute.setId(nextId++);
		return learningAttribute;
	}
	
	@SuppressWarnings("unchecked")
	private Attribute queryAttribute() {
		Criteria criteria = session.createCriteria(Attribute.class, "attribute");
		criteria.add(Restrictions.eq("attribute.name", attributeName));
		List<Attribute> attributes = criteria.list();
		return attributes.isEmpty()? createAttribute() : attributes.get(0);
	}
	
	//Creates a new attribute entry in the database with the current attribute name.
	// Return value is the new attribute.
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
	
	public List<LearningAttribute> getLearningAttributes(){
		return learningAttributes;
	}
	
}
