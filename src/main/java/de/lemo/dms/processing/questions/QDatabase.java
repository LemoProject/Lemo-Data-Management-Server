package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningAttribute;
import de.lemo.dms.db.mapping.UserAssessment;
import de.lemo.dms.processing.FeatureFilter;
import de.lemo.dms.processing.FeatureProcessor;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.classification.Classifier;
import de.lemo.dms.processing.resulttype.ResultListUserInstance;
import de.lemo.dms.processing.resulttype.UserInstance;

@Path("queryDatabase")
public class QDatabase extends Question {
	
	Session session;
	private Long testCourseId;
	private Long startTime;
	private Long endTime;
	private Long trainCourseId;
	@POST
	public ResultListUserInstance compute(
			@FormParam(MetaParam.COURSE_IDS) final Long testCourseId,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam("targetCourseId") final Long trainCourseId) {

		this.testCourseId = testCourseId;
		this.trainCourseId = trainCourseId;
		this.startTime = startTime;
		this.endTime = endTime;

		this.startTime = 0L;
		this.endTime = Long.MAX_VALUE;
	//	validateTimestamps(startTime, endTime);
		System.out.println("Test Course: "+testCourseId+" Train Course: "+trainCourseId);
		ResultListUserInstance result;
		
		//result = classifyFromLearningObjects(testCourseId,trainCourseId);
		//result = classifyFromLogs();
		result = createDummyResult();
		return result;
	}

	private ResultListUserInstance createDummyResult() {
		List<UserInstance> userInstances = new ArrayList<UserInstance>();
		UserInstance userInstance = new UserInstance();
		userInstance.setAnswerCount(4);
		userInstance.setClassId(0);
		userInstance.setCommentCount(9);
		userInstance.setDownVotes(0);
		userInstance.setWordCount(1999);
		userInstance.setLinkCount(8);
		userInstance.setForumUsed(true);
		userInstances.add(userInstance);
		return new ResultListUserInstance(userInstances);
	}

	private ResultListUserInstance classifyFromLogs() {
		List<UserInstance> trainInstances = generateUserInstancesFromFeatures(trainCourseId);
		List<UserInstance> testInstances = generateUserInstancesFromFeatures(testCourseId);
		Classifier naiveBayes = new Classifier();
		ResultListUserInstance result = naiveBayes.trainAndTestUserInstances(trainInstances,testInstances);
		return result;
	}

	//Gets all users as instances and initializes them from learning attributes.
	public List<UserInstance> queryAllUserInstances(Long courseId) {
		List<UserInstance> studentInstances= new ArrayList<UserInstance>();
		
		session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		Criteria criteria = session.createCriteria(CourseUser.class);
		criteria.add(Restrictions.eq("course.id", courseId));
		List<CourseUser> courseUsers = criteria.list();
		session.close();	
		
		for(CourseUser courseUser : courseUsers){
			studentInstances.add(new UserInstance(courseUser).queryUserAssessments());			
		}	
		return studentInstances;
	}
	
	public List<UserInstance> generateUserInstancesFromFeatures(Long courseId){	
		List<UserInstance> studentInstances = new FeatureProcessor(courseId,startTime,endTime).generateFeaturesForCourseUsers();
		studentInstances = new FeatureFilter().calculateClassValue(studentInstances);
		return studentInstances;
	}
}
