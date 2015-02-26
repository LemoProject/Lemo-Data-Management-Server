package de.lemo.dms.processing.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	private ArrayList<LearningAttribute> learningAttributes;
	private Session session;
	private String attributeName;
	
	
	public PostRating(){
		attributeName = "PostRating";
	}
	
	private void process(){
		session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();

		
		queryAllLogs();
		System.out.println(getAttribute().getName());
		session.close();
	}
	
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
	
	@SuppressWarnings("unchecked")
	private Attribute getAttribute() {
		Criteria criteria = session.createCriteria(Attribute.class, "attribute");
		criteria.add(Restrictions.eq("attribute.name", attributeName));
		List<Attribute> attributes = criteria.list();
		return attributes.isEmpty()? null : attributes.get(0);
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

	private void addVotesToLearningObj(LearningObj learning, long l) {
		System.out.println(learning.getTitle()+": "+l);
		Criteria criteria = session.createCriteria(LearningAttribute.class)
				.add(Restrictions.eq("learning", learning))
				.add(Restrictions.eq("attribute", getAttribute()));
		List<LearningAttribute> learningAttributes = criteria.list();
		if(learningAttributes.isEmpty()){
			createLearningAttribute(learning,l);
		} else{
			Long postRating = Long.valueOf(learningAttributes.get(0).getValue());
			learningAttributes.get(0).setValue(String.valueOf((postRating+l)));
		}
	}

	private void createLearningAttribute(LearningObj learning, long l) {
		LearningAttribute learningAttribute = new LearningAttribute();
		learningAttribute.setLearning(learning);
		learningAttribute.setAttribute(getAttribute());
		learningAttribute.setValue(String.valueOf(l));
		Criteria criteria = session.createCriteria(LearningAttribute.class, "attribute");
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		LearningAttribute maxAttribute = (LearningAttribute) criteria.uniqueResult();
		Long nextId = maxAttribute.getId()+1;
		learningAttribute.setId(nextId);		
	}

	protected void processLogs(){
		process();
	}
	
	
}
