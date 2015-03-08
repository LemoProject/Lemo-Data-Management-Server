package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningAttribute;
import de.lemo.dms.db.mapping.UserAssessment;
import de.lemo.dms.processing.features.ContentImageCount;
import de.lemo.dms.processing.features.ContentLinkCount;
import de.lemo.dms.processing.features.ContentWordCount;
import de.lemo.dms.processing.features.PostRating;

/*
 * Preprocesses features for faster processing later.
 */

public class FeaturePreProcessor {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public void processAssessments(){
		List<Collection<?>> persistCollections = new ArrayList<Collection<?>>();
		List<UserAssessment> courseUser = createAssessmentsFromLearningAttributes();
		persistCollections.add(courseUser);
		persistFeatures(persistCollections);
		logger.info("End processing of Assessments!");
	}
	
	//Processes features from learning objects
	public void processContentFeatures(){
		List<Collection<?>> persistCollections = new ArrayList<Collection<?>>();
		List<LearningAttribute> contentImageCount = new ContentImageCount().getLearningAttributes();
		persistCollections.add(contentImageCount);
		persistFeatures(persistCollections);
		persistCollections = new ArrayList<Collection<?>>();
		List<LearningAttribute> contentLinkCount = new ContentLinkCount().getLearningAttributes();
		persistCollections.add(contentLinkCount);
		persistFeatures(persistCollections);
		persistCollections = new ArrayList<Collection<?>>();
		List<LearningAttribute> contentWordCount = new ContentWordCount().getLearningAttributes();
		persistCollections.add(contentWordCount);
		persistFeatures(persistCollections);
		logger.info("End feature processing!");		
	}
	
	public void processLogFeatures(){
		List<Collection<?>> persistCollections = new ArrayList<Collection<?>>();
		PostRating postRating = new PostRating();
		postRating.process();
		List<LearningAttribute> learningAttributes = postRating.getLearningAttributes();
		persistCollections.add(learningAttributes);
		persistFeatures(persistCollections);
		logger.info("End processing of Logs!");		
	}	

	//This is the main method for extracting user features for classification from the learning attributes.
	public List<UserAssessment> createAssessmentsFromLearningAttributes(){
		List<UserAssessment> userAssessments = new ArrayList<UserAssessment>();
		Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		long nextId = getNextUnusedIdInEntity(UserAssessment.class);	
		Criteria criteria = session.createCriteria(Course.class);
		List<Course> courses = criteria.list();
		for(Course course : courses){
			criteria = session.createCriteria(CourseUser.class,"courseUser");
			criteria.add(Restrictions.eq("courseUser.course", course));
			List<CourseUser> courseUsers = criteria.list();
			for(CourseUser courseUser : courseUsers){
				List<String> contentAttributeNames = new ArrayList<String>();
				Map<String,UserAssessment> assessments = new HashMap<String, UserAssessment>();				
				contentAttributeNames.add("Content_Linkcount");
				contentAttributeNames.add("Content_Imagecount");
				contentAttributeNames.add("Post_Up_Votes");
				contentAttributeNames.add("Post_Down_Votes");
				contentAttributeNames.add("Content_Wordcount");
				contentAttributeNames.add("PostRatingSum");
				contentAttributeNames.add("PostRatingMin");
				contentAttributeNames.add("PostRatingMax");
				for(String contentAttributeName : contentAttributeNames){
					UserAssessment userAssessment = new UserAssessment();
					userAssessment.setCourse(course);
					userAssessment.setUser(courseUser.getUser());
					userAssessment.setId(nextId);
					userAssessment.setFeedback(contentAttributeName);
					userAssessment.setGrade(0D);
					assessments.put(contentAttributeName, userAssessment);
					nextId++;					
				}				
				criteria = session.createCriteria(CollaborationLog.class, "collaborationLog");
				criteria.add(Restrictions.eq("collaborationLog.course", course));
				criteria.add(Restrictions.eq("collaborationLog.user", courseUser.getUser()));
				List<CollaborationLog> collaborationLogs = criteria.list();
				if(!collaborationLogs.isEmpty()){
					for(CollaborationLog collaborationLog : collaborationLogs){
						criteria = session.createCriteria(LearningAttribute.class, "learningAttribute");
						criteria.add(Restrictions.eq("learningAttribute.learning", collaborationLog.getLearning()));
						List<LearningAttribute> learningAttributes = criteria.list();
						for(LearningAttribute learningAttribute : learningAttributes){
							if(learningAttribute.getAttribute().getName().equals("Content_Linkcount")){								
								assessments.get("Content_Linkcount")
									.setGrade(assessments.get("Content_Linkcount").getGrade()+
												Integer.valueOf(learningAttribute.getValue()));
							}	
							else if(learningAttribute.getAttribute().getName().equals("Post_Up_Votes")){								
								assessments.get("Post_Up_Votes")
									.setGrade(assessments.get("Post_Up_Votes").getGrade()+
												Integer.valueOf(learningAttribute.getValue()));
							}
							else if(learningAttribute.getAttribute().getName().equals("Post_Down_Votes")){								
								assessments.get("Post_Down_Votes")
									.setGrade(assessments.get("Post_Down_Votes").getGrade()+
												Integer.valueOf(learningAttribute.getValue()));
							}
							else if(learningAttribute.getAttribute().getName().equals("Content_Wordcount")){								
								assessments.get("Content_Wordcount")
									.setGrade(assessments.get("Content_Wordcount").getGrade()+
												Integer.valueOf(learningAttribute.getValue()));
							}
							else if(learningAttribute.getAttribute().getName().equals("Content_Imagecount")){								
								assessments.get("Content_Imagecount")
									.setGrade(assessments.get("Content_Imagecount").getGrade()+
												Integer.valueOf(learningAttribute.getValue()));
							}
							else if(learningAttribute.getAttribute().getName().equals("PostRating")){								
								double max = assessments.get("PostRatingMax").getGrade();
								double min = assessments.get("PostRatingMin").getGrade();
								double current = Double.valueOf(learningAttribute.getValue());
								assessments.get("PostRatingSum")
									.setGrade(assessments.get("PostRatingSum").getGrade()+
												current);
								if(current > max){
									assessments.get("PostRatingMax")
										.setGrade(current);
								}
								if(current < min){
									assessments.get("PostRatingMin")
										.setGrade(current);
								}
							}
						}
					}
				}
				userAssessments.addAll(assessments.values());
			}
			logger.info(course.getTitle()+" #UsersAssessments cumulative: "+userAssessments.size());
		}
		session.close();
		return userAssessments;
	}
	
	private long getNextUnusedIdInEntity(Class<?> entityClass) {
		long nextId=0;
		Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		Criteria criteria = session.createCriteria(entityClass);
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		UserAssessment userAssessmentMaxId = (UserAssessment) criteria.uniqueResult();
		if(userAssessmentMaxId!=null){
			nextId = userAssessmentMaxId.getId()+1;
		}
		session.close();
		return nextId;
	}

	//Either saves or updates the given instances.
	private void persistFeatures(List<Collection<?>> persistCollections) {
		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();	
		Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();		
		dbHandler.saveCollectionToDB(session, persistCollections);
		session.close();
	}
}
