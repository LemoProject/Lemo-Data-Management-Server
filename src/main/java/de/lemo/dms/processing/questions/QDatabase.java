package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
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
	private final Logger logger = Logger.getLogger(this.getClass());
	
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

	//	validateTimestamps(startTime, endTime);
		System.out.println("Test Course: "+testCourseId+" Train Course: "+trainCourseId);
		Date benchmarkStart = Calendar.getInstance().getTime();
		ResultListUserInstance result;
		
		//result = classifyFromLearningObjects(testCourseId,trainCourseId);
		result = classifyFromLogs();
		Date benchmarkStop = Calendar.getInstance().getTime();
		System.out.println("Time elapsed: " + (benchmarkStop.getTime() - benchmarkStart.getTime()));
		//result = createDummyResult();
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
		userInstance.setProgressPercentage(30);
		userInstance.setForumUsed(true);
		userInstances.add(userInstance);
		userInstance = new UserInstance();
		userInstance.setAnswerCount(2);
		userInstance.setClassId(1);
		userInstance.setCommentCount(1);
		userInstance.setDownVotes(6);
		userInstance.setWordCount(4353);
		userInstance.setLinkCount(2);
		userInstance.setProgressPercentage(90);
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
	
	public List<UserInstance> generateUserInstancesFromFeatures(Long courseId){	
		List<UserInstance> studentInstances = new FeatureProcessor(courseId,startTime,endTime).generateFeaturesForCourseUsers();
		FeatureFilter featureFilter = new FeatureFilter();
		studentInstances = featureFilter.calculateClassValue(studentInstances);
		studentInstances = featureFilter.removeProgressWithoutSegments(studentInstances);
		studentInstances = featureFilter.removeInstructors(studentInstances);
		return studentInstances;
	}
}
