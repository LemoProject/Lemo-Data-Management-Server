/**
 * File ./src/main/java/de/lemo/dms/connectors/iversity/ExtractAndMapMoodle.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/connectors/iversity/ExtractAndMapMoodle.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.mooc;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.mooc.mapping.AssessmentAnswers;
import de.lemo.dms.connectors.mooc.mapping.AssessmentQuestions;
import de.lemo.dms.connectors.mooc.mapping.AssessmentSessions;
import de.lemo.dms.connectors.mooc.mapping.Assessments;
import de.lemo.dms.connectors.mooc.mapping.Assets;
import de.lemo.dms.connectors.mooc.mapping.Comments;
import de.lemo.dms.connectors.mooc.mapping.Courses;
import de.lemo.dms.connectors.mooc.mapping.Events;
import de.lemo.dms.connectors.mooc.mapping.Memberships;
import de.lemo.dms.connectors.mooc.mapping.Progress;
import de.lemo.dms.connectors.mooc.mapping.Answers;
import de.lemo.dms.connectors.mooc.mapping.Questions;
import de.lemo.dms.connectors.mooc.mapping.Segments;
import de.lemo.dms.connectors.mooc.mapping.UnitResources;
import de.lemo.dms.connectors.mooc.mapping.Users;
import de.lemo.dms.connectors.mooc.mapping.Videos;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.mapping.Attribute;
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseAttribute;
import de.lemo.dms.db.mapping.CourseLearning;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningAttribute;
import de.lemo.dms.db.mapping.LearningObj;
import de.lemo.dms.db.mapping.LearningType;
import de.lemo.dms.db.mapping.UserAssessment;
import de.lemo.dms.db.mapping.AccessLog;
import de.lemo.dms.db.mapping.Role;
import de.lemo.dms.db.mapping.AssessmentLog;
import de.lemo.dms.db.mapping.User;
import de.lemo.dms.db.mapping.UserAttribute;

/**
 * The main class of the extraction process.
 * Implementation of the abstract extract class for the LMS Moodle.
 */
public class ExtractAndMapMooc extends ExtractAndMap {

	// LMS tables instances lists
	/** The log_lms. */
	private List<AssessmentAnswers> assessmentAnswersMooc;
	private List<AssessmentQuestions> assessmentQuestionsMooc;
	private List<AssessmentSessions> assessmentSessionsMooc;
	private List<Assessments> assessmentMooc;
	private List<Assets> assetsMooc;
	private List<Courses> coursesMooc;
	private List<Comments> commentsMooc;
	private List<Events> eventsMooc;
	private List<Memberships> membershipsMooc;
	private List<Progress> progressMooc;
	private List<Answers> answersMooc;
	private List<Questions> questionsMooc;
	private List<Segments> segmentsMooc;
	private List<UnitResources> unitResourcesMooc;
	private List<Users> usersMooc;
	private List<Videos> videosMooc;
	
	private Map<Long, CourseAttribute> courseAttributes = new HashMap<Long, CourseAttribute>();
	private Map<Long, UserAttribute> userAttributes = new HashMap<Long, UserAttribute>();
	private Map<Long, LearningAttribute> learningAttributes = new HashMap<Long, LearningAttribute>();
	
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();
	
	private HashMap<String, LearningType> learnTypes = new HashMap<String, LearningType>();
		
	private final Logger logger = Logger.getLogger(this.getClass());

	//private final IConnector connector;

	public ExtractAndMapMooc(final IConnector connector) {
		super(connector);
		//this.connector = connector;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void getLMStables(final DBConfigObject dbConfig, final long readingfromtimestamp, List<Long> courses, List<String> logins) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConfig).openSession();
		session.clear();
		
		Date date = new Date ();
		date.setTime((long)readingfromtimestamp*1000);
		
		//Read Context
		Criteria criteria = session.createCriteria(Courses.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			criteria.add(Restrictions.in("obj.id", courses));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.coursesMooc = criteria.list();
		logger.info("Loaded " + this.coursesMooc.size() + " Courses entries.");
		
		criteria = session.createCriteria(Segments.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			criteria.add(Restrictions.in("obj.courseId", courses));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.segmentsMooc = criteria.list();
		logger.info("Loaded " + this.segmentsMooc.size() + " Segments entries.");
		
		criteria = session.createCriteria(Events.class, "obj");
		criteria.add(Restrictions.gt("obj.timestamp", date));
		if(!courses.isEmpty())
		{
			List<Long> segments = new ArrayList<Long>();
			for(Segments s : this.segmentsMooc)
			{
				segments.add(s.getId());
			}
			if(!segments.isEmpty())
			{
				criteria.add(Restrictions.in("obj.segmentId", segments));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.eventsMooc = criteria.list();
		logger.info("Loaded " + this.eventsMooc.size() + " Events entries.");
		
		criteria = session.createCriteria(UnitResources.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> segments = new ArrayList<Long>();
			for(Segments s : this.segmentsMooc)
			{
				segments.add(s.getId());
			}
			if(!segments.isEmpty())
			{
				criteria.add(Restrictions.in("obj.unitId", segments));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.unitResourcesMooc = criteria.list();
		logger.info("Loaded " + this.unitResourcesMooc.size() + " UnitResources entries.");
		
		criteria = session.createCriteria(Assessments.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> assessments = new ArrayList<Long>();
			for(UnitResources s : this.unitResourcesMooc)
			{
				if(s.getAttachableType().equals("Assessment"))
				assessments.add(s.getAttachableId());
			}
			if(!assessments.isEmpty())
			{
				criteria.add(Restrictions.in("obj.id", assessments));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentMooc = criteria.list();
		logger.info("Loaded " + this.assessmentMooc.size() + " Assessments entries.");
		
		criteria = session.createCriteria(AssessmentQuestions.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> assessments = new ArrayList<Long>();
			for(Assessments s : this.assessmentMooc)
			{
				assessments.add(s.getId());
			}
			if(!assessments.isEmpty())
			{
				criteria.add(Restrictions.in("obj.assessmentId", assessments));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentQuestionsMooc = criteria.list();
		logger.info("Loaded " + this.assessmentQuestionsMooc.size() + " AssessmentQuestions entries.");
		
		
		criteria = session.createCriteria(AssessmentAnswers.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> assessmentQuestions = new ArrayList<Long>();
			for(AssessmentQuestions s : this.assessmentQuestionsMooc)
			{
				assessmentQuestions.add(s.getId());
			}
			if(!assessmentQuestions.isEmpty())
			{
				criteria.add(Restrictions.in("obj.assessmentQuestionId", assessmentQuestions));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentAnswersMooc = criteria.list();
		logger.info("Loaded " + this.assessmentAnswersMooc.size() + " AssessmentAnswers entries.");
		
		criteria = session.createCriteria(AssessmentSessions.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> assessments = new ArrayList<Long>();
			for(Assessments s : this.assessmentMooc)
			{
				assessments.add(s.getId());
			}
			if(!assessments.isEmpty())
			{
				criteria.add(Restrictions.in("obj.assessmentId", assessments));
			}
		}
		criteria.addOrder(Property.forName("obj.timeModified").asc());
		this.assessmentSessionsMooc = criteria.list();
		logger.info("Loaded " + this.assessmentAnswersMooc.size() + " AssessmentSessions entries.");
		
		criteria = session.createCriteria(Assets.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> assets = new ArrayList<Long>();
			for(UnitResources s : this.unitResourcesMooc)
			{
				if(s.getAttachableType().equals("Asset"))
				assets.add(s.getAttachableId());
			}
			if(!assets.isEmpty())
			{
				criteria.add(Restrictions.in("obj.id", assets));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assetsMooc = criteria.list();
		logger.info("Loaded " + this.assetsMooc.size() + " Assets entries.");
		
		criteria = session.createCriteria(Comments.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.commentsMooc = criteria.list();
		logger.info("Loaded " + this.commentsMooc.size() + " Comments entries.");
		
		criteria = session.createCriteria(Memberships.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			criteria.add(Restrictions.in("obj.courseId", courses));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.membershipsMooc = criteria.list();
		logger.info("Loaded " + this.membershipsMooc.size() + " Memberships entries.");
		
		criteria = session.createCriteria(Users.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> users = new ArrayList<Long>();
			for(Memberships s : this.membershipsMooc)
			{
				users.add(s.getUserId());
			}
			if(!users.isEmpty())
			{
				criteria.add(Restrictions.in("obj.id", users));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.usersMooc = criteria.list();
		logger.info("Loaded " + this.usersMooc.size() + " Users entries.");
		
		/*
		criteria = session.createCriteria(Progress.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> segments = new ArrayList<Long>();
			for(Segments s : this.segmentsMooc)
			{
				segments.add(s.getId());
			}
			if(!segments.isEmpty())
			{
				criteria.add(Restrictions.in("obj.segmentId", segments));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.progressMooc = criteria.list();
		logger.info("Loaded " + this.progressMooc.size() + " Progress entries.");
		*/
		
		criteria = session.createCriteria(Questions.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> segments = new ArrayList<Long>();
			for(Segments s : this.segmentsMooc)
			{
				segments.add(s.getId());
			}
			if(!segments.isEmpty())
			{
				criteria.add(Restrictions.in("obj.segmentId", segments));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.questionsMooc = criteria.list();
		logger.info("Loaded " + this.questionsMooc.size() + " Questions entries.");
		
		criteria = session.createCriteria(Answers.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> questions = new ArrayList<Long>();
			for(Questions s : this.questionsMooc)
			{
				questions.add(s.getId());
			}
			if(!questions.isEmpty())
			{
				criteria.add(Restrictions.in("obj.questionId", questions));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.answersMooc = criteria.list();
		logger.info("Loaded " + this.answersMooc.size() + " Answers entries.");
		
		criteria = session.createCriteria(Videos.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", date));
		if(!courses.isEmpty())
		{
			List<Long> videos = new ArrayList<Long>();
			for(UnitResources s : this.unitResourcesMooc)
			{
				if(s.getAttachableType().equals("Video"))
				videos.add(s.getAttachableId());
			}
			if(!videos.isEmpty())
			{
				criteria.add(Restrictions.in("obj.id", videos));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.videosMooc = criteria.list();
		logger.info("Loaded " + this.videosMooc.size() + " Videos entries.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp,
			long readingtotimestamp, List<Long> courses, List<String> logins) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConf).openSession();
		session.clear();
		
		//Read Context
		Criteria criteria = session.createCriteria(Courses.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.coursesMooc = criteria.list();
		
		criteria = session.createCriteria(AssessmentAnswers.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentAnswersMooc = criteria.list();
		
		criteria = session.createCriteria(AssessmentQuestions.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentQuestionsMooc = criteria.list();
		
		criteria = session.createCriteria(AssessmentSessions.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentSessionsMooc = criteria.list();
		
		criteria = session.createCriteria(Assessments.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentMooc = criteria.list();
		
		criteria = session.createCriteria(Comments.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.commentsMooc = criteria.list();
		
		criteria = session.createCriteria(Events.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.eventsMooc = criteria.list();
		
		criteria = session.createCriteria(Memberships.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.membershipsMooc = criteria.list();
		
		criteria = session.createCriteria(Progress.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.progressMooc = criteria.list();
		
		criteria = session.createCriteria(Questions.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.questionsMooc = criteria.list();
		
		criteria = session.createCriteria(Answers.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.answersMooc = criteria.list();
		
		criteria = session.createCriteria(Segments.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.segmentsMooc = criteria.list();
		
		criteria = session.createCriteria(UnitResources.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.unitResourcesMooc = criteria.list();
		
		criteria = session.createCriteria(Users.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.usersMooc = criteria.list();
		
		criteria = session.createCriteria(Videos.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.videosMooc = criteria.list();
		
	}

	@Override
	public void clearLMStables() {
		
		assessmentAnswersMooc.clear();
		assessmentQuestionsMooc.clear();
		assessmentMooc.clear();
		coursesMooc.clear();
		eventsMooc.clear();
		membershipsMooc.clear();
		//progressMooc.clear();
		questionsMooc.clear();
		segmentsMooc.clear();
		unitResourcesMooc.clear();
		usersMooc.clear();
		videosMooc.clear();
		
	}

	@Override
	public Map<Long, CourseUser> generateCourseUsers() {
		final HashMap<Long, CourseUser> courseUsers = new HashMap<Long, CourseUser>();
		
		for(Memberships loadedItem : this.membershipsMooc)
		{
			CourseUser insert = new CourseUser();
			insert.setId( loadedItem.getId());
			insert.setCourse(  loadedItem.getCourseId(), this.courseMining, this.oldCourseMining);
			insert.setUser(loadedItem.getUserId(), this.userMining, this.oldUserMining);
			Long role = 2L;
			if(loadedItem.getRole().equals("instructor") || loadedItem.getRole().equals("instructor_assistant"))
				role = 1L;
			else if(loadedItem.getRole().equals("administrator"))
				role = 0L;
			insert.setRole(role, this.roleMining, this.oldRoleMining);
			
			if(insert.getCourse() != null && insert.getUser() != null)
				courseUsers.put(insert.getId(), insert);
		}
		return courseUsers;
	}  

	@Override
	public Map<Long, Course> generateCourses() {
		final HashMap<Long, Course> courses = new HashMap<Long, Course>();
		
		for(Courses loadedItem : this.coursesMooc)
		{
			Course insert = new Course();
			insert.setId(  loadedItem.getId());
			insert.setTitle(loadedItem.getTitle());
			addCourseAttribute(insert, "Course Description", loadedItem.getDescription());
			courses.put(insert.getId(), insert);
		}
		return courses;
	}
	
	private void addCourseAttribute(Course course, String attribute, String value)
	{
		if(!this.attributes.containsKey(attribute))
		{
			Attribute description = new Attribute();
			description.setId(this.attributeIdMax + 1);
			this.attributeIdMax++;
			description.setName(attribute);
			this.attributes.put(description.getName(), description);
		}
		CourseAttribute description = new CourseAttribute();
		description.setId(this.courseAttributeIdMax + 1);
		this.courseAttributeIdMax++;
		description.setCourse(course);
		description.setAttribute(this.attributes.get(attribute));
		description.setValue(value);
		
		this.courseAttributes.put(description.getId(), description);
	}
	
	private void addUserAttribute(User user, String attribute, String value)
	{
		if(!this.attributes.containsKey(attribute))
		{
			Attribute description = new Attribute();
			description.setId(this.attributeIdMax + 1);
			this.attributeIdMax++;
			description.setName(attribute);
			this.attributes.put(description.getName(), description);
		}
		UserAttribute description = new UserAttribute();
		description.setId(this.userAttributeIdMax + 1);
		this.userAttributeIdMax++;
		description.setUser(user);
		description.setAttribute(this.attributes.get(attribute));
		description.setValue(value);
		
		this.userAttributes.put(description.getId(), description);
	}
	
	private void addLearningAttribute(LearningObj learning, String attribute, String value)
	{
		if(!this.attributes.containsKey(attribute))
		{
			Attribute description = new Attribute();
			description.setId(this.attributeIdMax + 1);
			this.attributeIdMax++;
			description.setName(attribute);
			this.attributes.put(description.getName(), description);
		}
		LearningAttribute description = new LearningAttribute();
		description.setId(this.learningAttributeIdMax + 1);
		this.learningAttributeIdMax++;
		description.setLearning(learning);
		description.setAttribute(this.attributes.get(attribute));
		description.setValue(value);
		
		this.learningAttributes.put(description.getId(), description);
	}

	@Override
	public Map<Long, CourseLearning> generateCourseLearnings() {
		final HashMap<Long, CourseLearning> courseLearnings = new HashMap<Long, CourseLearning>();
		
		Map<Long, Long> unitCourses = new HashMap<Long, Long>();
		
		for(Segments loadedItem : this.segmentsMooc)
		{
			CourseLearning insert = new CourseLearning();
			insert.setId(Long.valueOf("10" + loadedItem.getId()));
			insert.setCourse(loadedItem.getCourseId(), this.courseMining, this.oldCourseMining);
			insert.setLearning(Long.valueOf("10" + loadedItem.getId()), this.learningObjectMining, this.oldLearningObjectMining);
			
			unitCourses.put(loadedItem.getId(), loadedItem.getCourseId());
			
			if(insert.getLearning() != null && insert.getCourse() != null)
				courseLearnings.put(insert.getId(), insert);
		}		
		
		for(UnitResources loadedItem : this.unitResourcesMooc)
		{
			if(loadedItem.getAttachableType().equals("Video"))
			{
				CourseLearning insert = new CourseLearning();
				insert.setId(Long.valueOf("12" + loadedItem.getId()));
				if(unitCourses.get(loadedItem.getUnitId()) != null)
				{
					insert.setCourse(unitCourses.get(loadedItem.getUnitId()), this.courseMining, this.oldCourseMining);
				}
				insert.setLearning(Long.valueOf("12" + loadedItem.getAttachableId()), this.learningObjectMining, this.oldLearningObjectMining);
				
				if(insert.getCourse() != null && insert.getLearning() != null)
				{
					courseLearnings.put(insert.getId(), insert);
				}
			}
			if(loadedItem.getAttachableType().equals("Assessment"))
			{
				CourseLearning insert = new CourseLearning();
				insert.setId(Long.valueOf("11" + loadedItem.getId()));
				if(unitCourses.get(loadedItem.getUnitId()) != null)
				{
					insert.setCourse(unitCourses.get(loadedItem.getUnitId()), this.courseMining, this.oldCourseMining);
				}
				insert.setLearning(Long.valueOf("11" + loadedItem.getAttachableId()), this.learningObjectMining, this.oldLearningObjectMining);
				
				if(insert.getCourse() != null && insert.getLearning() != null)
				{
					courseLearnings.put(insert.getId(), insert);
				}
			}
			if(loadedItem.getAttachableType().equals("Question"))
			{
				CourseLearning insert = new CourseLearning();
				insert.setId(Long.valueOf("14" + loadedItem.getId()));
				if(unitCourses.get(loadedItem.getUnitId()) != null)
				{
					insert.setCourse(unitCourses.get(loadedItem.getUnitId()), this.courseMining, this.oldCourseMining);
				}
				insert.setLearning(Long.valueOf("14" + loadedItem.getAttachableId()), this.learningObjectMining, this.oldLearningObjectMining);
				
				if(insert.getCourse() != null && insert.getLearning() != null)
				{
					courseLearnings.put(insert.getId(), insert);
				}
			}			
		}
		return courseLearnings;
	}

	@Override
	public Map<Long, UserAssessment> generateUserAssessments() {
		final HashMap<Long, UserAssessment> assessmentUsers = new HashMap<Long, UserAssessment>();
		
		for(AssessmentSessions loadedItem : this.assessmentSessionsMooc)
		{
			UserAssessment insert = new UserAssessment();			
			CourseUser cu = this.courseUserMining.get(loadedItem.getMembershipId());
			if(cu == null)
			{
				cu = this.oldCourseUserMining.get(loadedItem.getMembershipId());
			}
			if(cu != null && cu.getUser() != null)
			{
				if(loadedItem.getScore() != null)
				{
					insert.setId(this.userAssessmentMax + 1);
					this.userAssessmentMax ++;
					insert.setLearning(Long.valueOf("11" + loadedItem.getAssessmentId()), this.learningObjectMining, this.oldLearningObjectMining);
					insert.setCourse(cu.getCourse().getId(), this.courseMining, this.oldCourseMining);				
					insert.setUser(cu.getUser().getId(), this.userMining, this.oldUserMining);
					insert.setLearning(loadedItem.getAssessmentId(), this.learningObjectMining, this.learningObjectMining);
					insert.setGrade(Double.valueOf(loadedItem.getScore()));
					insert.setTimemodified(loadedItem.getTimeModified().getTime()/1000);
					
					assessmentUsers.put(insert.getId(), insert);
				}
			}
		}	
		
		for(Memberships loadedItem : this.membershipsMooc)
		{
			UserAssessment insert = new UserAssessment();
			insert.setId(this.userAssessmentMax + 1);
			this.userAssessmentMax++;
			insert.setLearning(Long.valueOf("15" + loadedItem.getCourseId()), this.learningObjectMining, this.oldLearningObjectMining);
			insert.setCourse(loadedItem.getCourseId(), this.courseMining, this.oldCourseMining);
			insert.setUser(loadedItem.getUserId(), this.userMining, this.oldUserMining);
			insert.setGrade(loadedItem.getNumericGrade());
			insert.setTimemodified(loadedItem.getTimeModified().getTime() / 1000);
			
			assessmentUsers.put(insert.getId(), insert);
		}
		return assessmentUsers;
	}

	@Override
	public Map<Long, AccessLog> generateAccessLogs() {
		
		final HashMap<Long, AccessLog> learningLogs = new HashMap<Long, AccessLog>();
		final Map<Long, UnitResources> unitResources = new HashMap<Long, UnitResources>();
		for(UnitResources u : this.unitResourcesMooc)
		{
			unitResources.put(u.getId(), u);
		}		
		
		for(Events loadedItem : this.eventsMooc)
		{
			AccessLog insert = new AccessLog();
			insert.setId(loadedItem.getId());
			insert.setUser(loadedItem.getUserId(), this.userMining, this.oldUserMining);
			if(loadedItem.getCourseId() != null)
			{
				insert.setCourse(loadedItem.getId(), this.courseMining, this.oldCourseMining);
			}
			else
			{
				insert.setCourse(this.courseLearningMining.get(Long.valueOf("10" + loadedItem.getSegmentId())).getCourse().getId(), this.courseMining, this.oldCourseMining);
			}
			
			if(unitResources.get(loadedItem.getUnitResourceId()) != null && unitResources.get(loadedItem.getUnitResourceId()).getAttachableType().equals("Video"))
			{
				UnitResources uR = unitResources.get(loadedItem.getUnitResourceId());
				insert.setLearning(Long.valueOf("12" + uR.getAttachableId()), this.learningObjectMining, this.oldLearningObjectMining);			
			}
			insert.setTimestamp(loadedItem.getTimestamp().getTime()/1000);
			
			switch(loadedItem.getEvent())
			{
				case 1 : insert.setAction("View");
					break;
				case 2 : insert.setAction("Play");
					break;
				case 3 : insert.setAction("Stop");
					break;
				case 4 : insert.setAction("Pause");
					break;
				default : break;
			}
			
			if(insert.getUser() != null && insert.getCourse() != null && insert.getLearning() != null)
			{
				learningLogs.put(insert.getId(), insert);
			}
			
		}
		return learningLogs;
	}

	@Override
	public Map<Long, CollaborationLog> generateCollaborativeLogs() {
		
		final List<CollaborationLog> loglist = new ArrayList<CollaborationLog>();
		final HashMap<Long, CollaborationLog> collaborationLogs = new HashMap<Long, CollaborationLog>();
		
		final Map<Long, CollaborationLog> questionLog = new HashMap<Long, CollaborationLog>();
		final Map<Long, CollaborationLog> answersLog = new HashMap<Long, CollaborationLog>();
		
		
		for(Questions loadedItem : this.questionsMooc)
		{
			CollaborationLog insert = new CollaborationLog();
			insert.setLearning(Long.valueOf("14" + loadedItem.getSegmentId()), this.learningObjectMining, this.oldLearningObjectMining);
			insert.setUser(loadedItem.getUserId(), this.userMining, this.oldUserMining);
			insert.setCourse(loadedItem.getCourseId(), this.courseMining, this.oldCourseMining);
			insert.setAction("Question");
			insert.setText(loadedItem.getContent());
			insert.setTimestamp(loadedItem.getTimeCreated().getTime()/1000);
			if(loadedItem.getSegmentId() != null)
			{
				insert.setLearning(Long.valueOf("13" + loadedItem.getSegmentId()), learningObjectMining, oldLearningObjectMining);
			}
			
			if(insert.getLearning() != null && insert.getUser() != null && insert.getCourse() != null)
			{
				loglist.add(insert);
			}
		}
		
		for(Answers loadedItem : this.answersMooc)
		{
			CollaborationLog insert = new CollaborationLog();
			insert.setReferrer(loadedItem.getQuestionId(), questionLog);
			
			if(insert.getReferrer() != null)
			{
				insert.setId(loadedItem.getId());
				insert.setLearning(questionLog.get(loadedItem.getQuestionId()).getLearning().getId(), this.learningObjectMining, this.learningObjectMining);
				insert.setUser(loadedItem.getUserId(), this.userMining, this.oldUserMining);
				insert.setCourse(insert.getReferrer().getCourse().getId(), this.courseMining, this.oldCourseMining);
				insert.setAction("Answer");
				insert.setText(loadedItem.getContent());
				insert.setTimestamp(loadedItem.getTimeCreated().getTime() / 1000);
				insert.setReferrer(questionLog.get(loadedItem.getQuestionId()));
				
				if(insert.getLearning() != null && insert.getUser() != null && insert.getCourse() != null)
				{
					answersLog.put(loadedItem.getId() , insert);
					loglist.add(insert);
				}
			}
		}
		for(Comments loadedItem : this.commentsMooc)
		{
			CollaborationLog insert = new CollaborationLog();
			if(loadedItem.getCommentableType().equals("Answer"))
			{
				insert.setReferrer(loadedItem.getCommentableId(), answersLog);
				if(insert.getReferrer() != null)
				{
					insert.setLearning(answersLog.get(loadedItem.getCommentableId()).getLearning().getId(), this.learningObjectMining, this.learningObjectMining);
				}
			}
			else if(loadedItem.getCommentableType().equals("Question"))
			{
				insert.setReferrer(loadedItem.getCommentableId(), questionLog);
				if(insert.getReferrer() != null)
				{
					insert.setLearning(questionLog.get(loadedItem.getCommentableId()).getLearning().getId(), this.learningObjectMining, this.learningObjectMining);
				}
			}
			
			if(insert.getReferrer() != null)
			{
				insert.setId(loadedItem.getId());
				
				insert.setUser(loadedItem.getUserId(), this.userMining, this.oldUserMining);
				insert.setCourse(insert.getReferrer().getCourse().getId(), this.courseMining, this.oldCourseMining);
				insert.setAction("Comment");
				insert.setText(loadedItem.getContent());
				insert.setTimestamp(loadedItem.getTimeCreated().getTime()/1000);
				
				if(insert.getLearning() != null && insert.getUser() != null && insert.getCourse() != null)
				{
					loglist.add(insert);
				}
			}
		}
		
		Collections.sort(loglist);
		for(int i = 0; i < loglist.size(); i++)
		{
			CollaborationLog l = loglist.get(i);
			l.setId(this.collaborationLogMax + i + 1);
			l.setReferrer(null); 
			this.collaborationLogMax++;
			collaborationLogs.put(l.getId(), l);			
		}
		
		return collaborationLogs;
	}

	@Override
	public Map<Long, LearningObj> generateLearningObjs() {
		final HashMap<Long, LearningObj> learningObjs = new HashMap<Long, LearningObj>();
		
		for(Course loadedItem : this.courseMining.values())
		{
			LearningObj insert = new LearningObj();
			insert.setId(Long.valueOf("15" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setInteractionType("Assessment");
			insert.setType(getLearningType("Course Exam"));
			
			learningObjs.put(insert.getId(), insert);
		}
		
		for(Segments loadedItem : this.segmentsMooc)
		{
			if(loadedItem.getType().equals("LessonUnit"))
			{
				LearningObj insert = new LearningObj();
				insert.setId(Long.valueOf("13" + loadedItem.getId()));
				insert.setTitle("Forum "  + loadedItem.getTitle());
				insert.setInteractionType("Collaboration");
				insert.setType(getLearningType("UnitForum"));
				
				learningObjs.put(insert.getId(), insert);
			}
		}
		
		for(Assessments loadedItem : this.assessmentMooc)
		{
			LearningObj insert = new LearningObj();
			insert.setId(Long.valueOf("11" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setInteractionType("Assessment");
			insert.setType(getLearningType(loadedItem.getType()));
			
			learningObjs.put(insert.getId(), insert);
		}
		
		for(Segments loadedItem : this.segmentsMooc)
		{
			LearningObj insert = new LearningObj();
			insert.setId(Long.valueOf("10" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());

			if(loadedItem.getType().equals("LessonUnit") || loadedItem.getType().equals("Chapter"))
			{
				insert.setType(getLearningType(loadedItem.getType()));
			}
			
			if(loadedItem.getParent() != null)
			{
				insert.setParent(loadedItem.getParent(), learningObjs, oldLearningObjectMining);
			}
			
			if(insert.getType() != null)
			{
				learningObjs.put(insert.getId(), insert);
			}
		}
		
		for(Videos loadedItem : this.videosMooc)
		{
			LearningObj insert = new LearningObj();
			insert.setId(Long.valueOf("12" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setInteractionType("Access");
			insert.setType(getLearningType("Video"));
				
			if(insert.getType() != null)
			{
				learningObjs.put(insert.getId(), insert);
			}
		}	
		
		for(Questions loadedItem : this.questionsMooc)
		{
			LearningObj insert = new LearningObj();
			insert.setId(Long.valueOf("14" + loadedItem.getId()));
			insert.setInteractionType("Assessment");
			insert.setType(getLearningType("Question"));
			insert.setTitle(loadedItem.getTitle());
			addLearningAttribute(insert, "Question Content", loadedItem.getContent());
			
			if(insert.getType() != null)
			{
				learningObjs.put(insert.getId(), insert);
			}
		}
		return learningObjs;
	}
	
	private LearningType getLearningType(String typeName)
	{
		LearningType type = new LearningType();
		if(this.learnTypes.get(typeName) == null)
		{
			type.setId(this.learningObjectTypeMax + 1 + this.learnTypes.size());
			type.setType(typeName);
			this.learnTypes.put(type.getType(), type);
		}
		else
		{
			type = this.learnTypes.get(typeName);
		}
		return type;
	}

	
	@Override
	public Map<Long, User> generateUsers() {
		final HashMap<Long, User> users = new HashMap<Long, User>();
		
		for(Users loadedItem : this.usersMooc)
		{
			User insert = new User();
			insert.setId(  loadedItem.getId());
			insert.setLogin(loadedItem.getId() + "mooc");
			addUserAttribute(insert, "User Gender", loadedItem.getGender());
			addUserAttribute(insert, "User Timezone", loadedItem.getTimezone());
			users.put(insert.getId(), insert);
		}
		
		return users;
	}


	@Override
	public Map<Long, Role> generateRoles() {
		final HashMap<Long, Role> roles = new HashMap<Long, Role>();
		
		Role admin = new Role();
		admin.setId(0);
		admin.setSortOrder(0);
		admin.setTitle("Administrator");
		admin.setType(0);
		
		Role teacher = new Role();
		teacher.setId(1);
		teacher.setSortOrder(1);
		teacher.setTitle("Instructor");
		teacher.setType(1);
		
		Role student = new Role();
		student.setId(2);
		student.setSortOrder(2);
		student.setTitle("Student");
		student.setType(2);
		
		roles.put(admin.getId(), admin);
		roles.put(teacher.getId(), teacher);
		roles.put(student.getId(), student);
	
		
		return roles;
	}

	@Override
	public Map<Long, AssessmentLog> generateAssessmentLogs() {
		final HashMap<Long, AssessmentLog> assessmentLogs = new HashMap<Long, AssessmentLog>();
		
		for(AssessmentSessions loadedItem : this.assessmentSessionsMooc)
		{
			AssessmentLog insert = new AssessmentLog();
			insert.setId(this.assessmentLogMax + 1 + assessmentLogs.size());
			insert.setDuration(loadedItem.getDuration());
			insert.setAction(loadedItem.getState());
			insert.setTimestamp(loadedItem.getTimeModified().getTime() / 1000);
			insert.setLearning(Long.valueOf("11" + loadedItem.getAssessmentId()), this.learningObjectMining, this.oldLearningObjectMining);
			
			CourseUser cu = this.courseUserMining.get(loadedItem.getMembershipId());
			if(cu == null)
			{
				cu = this.oldCourseUserMining.get(loadedItem.getMembershipId());
			}
			if(cu != null && insert.getTimestamp() != null)
			{
				insert.setCourse(cu.getCourse().getId(), this.courseMining, this.oldCourseMining);				
				insert.setUser(cu.getUser().getId(), this.userMining, this.oldUserMining);
				
				assessmentLogs.put(insert.getId(), insert);
			}
		}
		

		return assessmentLogs;
	}

	@Override
	public Map<String, LearningType> generateLearningTypes() {
		return this.learnTypes;
	}

	@Override
	public Map<Long, CourseAttribute> generateCourseAttributes() {
		return this.courseAttributes;
	}

	@Override
	public Map<Long, UserAttribute> generateUserAttributes() {
		return this.userAttributes;
	}

	@Override
	public Map<Long, LearningAttribute> generateLearningAttributes() {
		return this.learningAttributes;
	}

	@Override
	public Map<String, Attribute> generateAttributes() {
		return this.attributes;
	}

}
