package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.Attribute;
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningAttribute;
import de.lemo.dms.db.mapping.LearningObj;
import de.lemo.dms.db.mapping.User;
import de.lemo.dms.db.mapping.UserAssessment;
import de.lemo.dms.processing.features.ContentImageCount;
import de.lemo.dms.processing.features.ContentLinkCount;
import de.lemo.dms.processing.features.ContentWordCount;
import de.lemo.dms.processing.resulttype.UserInstance;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

public class FeatureProcessor{

	public void processAll(){
		List<Collection<?>> persistCollections = new ArrayList<Collection<?>>();
		List<UserAssessment> courseUser = createAssessmentsFromLearningAttributes();
		persistCollections.add(courseUser);
		persistFeatures(persistCollections);
		System.out.println("End processing!");
	}
	
	//Processes features from learning objects
	public void processFeatures(){
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
		System.out.println("End feature processing!");		
	}

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
						}
					}
				}
				userAssessments.addAll(assessments.values());
			}
			System.out.println(course.getTitle()+" #UsersAssessments cumulative: "+userAssessments.size());
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

	private void persistFeatures(List<Collection<?>> persistCollections) {
		IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();	
		Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();		
		dbHandler.saveCollectionToDB(session, persistCollections);
		session.close();
	}

	public List<UserInstance> generateFeaturesForCourseUsers(Long courseId) {
		List<UserInstance> userInstances = new ArrayList<UserInstance>();
		Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		Criteria criteria = session.createCriteria(CollaborationLog.class);
		criteria.add(Restrictions.eq("course.id", courseId));
		criteria.setProjection(Projections.distinct(Projections.property("user")));
		List<User> users = criteria.list();
		for(User user : users){
			UserInstance userInstance = new UserInstance();
			userInstance.setUserId(user.getId());
			criteria = session.createCriteria(CollaborationLog.class);
			criteria.add(Restrictions.eq("course.id", courseId));
			criteria.add(Restrictions.eq("user", user));
			List<CollaborationLog> logsFromUser = criteria.list();
			insertAllFeaturesFromLog(userInstance,logsFromUser,session);
			insertUserAssessment(userInstance,user,courseId,session);
			userInstances.add(userInstance);
		}
		criteria = session.createCriteria(CourseUser.class);
		criteria.add(Restrictions.eq("course.id", courseId));
		criteria.add(Restrictions.not(Restrictions.in("user", users)));
		List<CourseUser> missingUsers = criteria.list();
		for(CourseUser missingUser : missingUsers){
			UserInstance userInstance = new UserInstance();
			userInstance.setForumUsed(false);
			userInstance.setUserId(missingUser.getUser().getId());
			insertUserAssessment(userInstance,missingUser.getUser(),courseId,session);
			userInstances.add(userInstance);
		}
		session.close();		
		return userInstances;
	}


	//Inserts the class/assessment value into the concrete user instance.
	private void insertUserAssessment(UserInstance userInstance, User user,
			Long courseId, Session session) {		
		Criteria criteria = session.createCriteria(UserAssessment.class);
		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.eq("course.id", courseId));
		criteria.add(Restrictions.eq("feedback", "Progress_Percentage"));
		UserAssessment userAssessment = (UserAssessment) criteria.uniqueResult();
		if(userAssessment==null){
			System.out.println("Keine Assesment gefunden!");
		}else{
			userInstance.setProgressPercentage(userAssessment.getFinalGrade().intValue());
			if(userAssessment.getFinalGrade().intValue()>80){
				userInstance.setClassId(1);
			}
			//assessmentLog = success and SegmentCompletion				
		}		
	}

	private void insertAllFeaturesFromLog(UserInstance userInstance,
			List<CollaborationLog> logsFromUser,Session session) {
		Criteria criteria;
		userInstance.setForumUsed(true);
		for(CollaborationLog logFromUser: logsFromUser){
			if(logFromUser.getAction().equals("vote") && logFromUser.getText().equals("up")){
				userInstance.setUpVotes(userInstance.getUpVotes()+1);
			} else if(logFromUser.getAction().equals("vote") && logFromUser.getText().equals("down")){
				userInstance.setDownVotes(userInstance.getDownVotes()+1);
			} else if(logFromUser.getAction().equals("answered")){
				insertAllFeaturesFromLearningAttribute(userInstance,logFromUser.getLearning(),session);
				userInstance.setPostCount(userInstance.getPostCount()+1);
				userInstance.setAnswerCount(userInstance.getAnswerCount()+1);
			} else if(logFromUser.getAction().equals("commented")){
				insertAllFeaturesFromLearningAttribute(userInstance,logFromUser.getLearning(),session);
				userInstance.setPostCount(userInstance.getPostCount()+1);
				userInstance.setCommentCount(userInstance.getCommentCount()+1);
			}
		}
	}

	private void insertAllFeaturesFromLearningAttribute(
			UserInstance userInstance, LearningObj learning, Session session) {
		Criteria criteria = session.createCriteria(LearningAttribute.class);
		criteria.add(Restrictions.eq("learning", learning));
		List<LearningAttribute> learningAttributes = criteria.list();
		for(LearningAttribute learningAttribute : learningAttributes){
			if(learningAttribute.getAttribute().getName().equals("Content_Linkcount")){								
				userInstance.setLinkCount(userInstance.getLinkCount()+Integer.valueOf(learningAttribute.getValue()));
			}	
			else if(learningAttribute.getAttribute().getName().equals("Post_Up_Votes")){	
				userInstance.setReceivedUpVotes(userInstance.getReceivedUpVotes()+Integer.valueOf(learningAttribute.getValue()));
			}
			else if(learningAttribute.getAttribute().getName().equals("Post_Down_Votes")){
				userInstance.setReceivedDownVotes(userInstance.getReceivedDownVotes()+Integer.valueOf(learningAttribute.getValue()));
			}
			else if(learningAttribute.getAttribute().getName().equals("Content_Wordcount")){								
				userInstance.setWordCount(userInstance.getWordCount()+Integer.valueOf(learningAttribute.getValue()));
			}
			else if(learningAttribute.getAttribute().getName().equals("Content_Imagecount")){								
				userInstance.setImageCount(userInstance.getImageCount()+Integer.valueOf(learningAttribute.getValue()));
			}
		}		
	}
}