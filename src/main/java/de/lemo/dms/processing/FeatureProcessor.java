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
import de.lemo.dms.processing.features.PostRating;
import de.lemo.dms.processing.resulttype.UserInstance;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/* Processes and persists Features.
 * 
 */

public class FeatureProcessor{

	//Generates all features for all users in the given course.
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
			insertAllFeaturesFromLog(userInstance,user,courseId,session);
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
			User user, Long courseId,Session session) {
		Criteria criteria = session.createCriteria(CollaborationLog.class);
		criteria.add(Restrictions.eq("course.id", courseId));
		criteria.add(Restrictions.eq("user", user));
		List<CollaborationLog> logsFromUser = criteria.list();
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
			else if(learningAttribute.getAttribute().getName().equals("PostRating")){	
				int max = userInstance.getPostRatingMax();
				int min = userInstance.getPostRatingMin();
				int current = Integer.valueOf(learningAttribute.getValue());
				userInstance.setPostRatingSum(userInstance.getPostRatingSum()+current);
				if(current > max){
					userInstance.setPostRatingMax(current);
				}
				if(current < min){
					userInstance.setPostRatingMin(current);
				}
			}
		}		
	}
}