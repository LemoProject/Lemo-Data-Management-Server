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
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.CollaborationObj;
import de.lemo.dms.db.mapping.CollaborationType;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseCollaboration;
import de.lemo.dms.db.mapping.CourseLearning;
import de.lemo.dms.db.mapping.CourseAssessment;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningObj;
import de.lemo.dms.db.mapping.LearningType;
import de.lemo.dms.db.mapping.Assessment;
import de.lemo.dms.db.mapping.AssessmentType;
import de.lemo.dms.db.mapping.UserAssessment;
import de.lemo.dms.db.mapping.AccessLog;
import de.lemo.dms.db.mapping.Role;
import de.lemo.dms.db.mapping.AssessmentLog;
import de.lemo.dms.db.mapping.User;

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
	
	private HashMap<String, CollaborationType> collTypes = new HashMap<String, CollaborationType>();
	private HashMap<String, LearningType> learnTypes = new HashMap<String, LearningType>();
	private HashMap<String, AssessmentType> assessTypes = new HashMap<String, AssessmentType>();
	
	private HashMap<String, CourseLearning> courseLearn = new HashMap<String, CourseLearning>();
	
	private final Logger logger = Logger.getLogger(this.getClass());

	private final IConnector connector;

	public ExtractAndMapMooc(final IConnector connector) {
		super(connector);
		this.connector = connector;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void getLMStables(final DBConfigObject dbConfig, final long readingfromtimestamp, List<Long> courses, List<String> logins) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConfig).openSession();
		session.clear();
		
		//Read Context
		Criteria criteria = session.createCriteria(Courses.class, "obj");
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.coursesMooc = criteria.list();
		
		criteria = session.createCriteria(AssessmentAnswers.class, "obj");
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentAnswersMooc = criteria.list();
		
		criteria = session.createCriteria(AssessmentSessions.class, "obj");
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.timemodified").asc());
		this.assessmentAnswersMooc = criteria.list();
		
		criteria = session.createCriteria(AssessmentQuestions.class, "obj");
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentQuestionsMooc = criteria.list();
		
		criteria = session.createCriteria(Assessments.class, "obj");
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentMooc = criteria.list();
		
		criteria = session.createCriteria(Events.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.eventsMooc = criteria.list();
		
		criteria = session.createCriteria(Comments.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.commentsMooc = criteria.list();
		
		criteria = session.createCriteria(Memberships.class, "obj");
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.membershipsMooc = criteria.list();
		
		criteria = session.createCriteria(Progress.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.progressMooc = criteria.list();
		
		criteria = session.createCriteria(Questions.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.questionsMooc = criteria.list();
		
		criteria = session.createCriteria(Answers.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.answersMooc = criteria.list();
		
		criteria = session.createCriteria(Segments.class, "obj");
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.segmentsMooc = criteria.list();
		
		criteria = session.createCriteria(UnitResources.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.unitResourcesMooc = criteria.list();
		
		criteria = session.createCriteria(Users.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.usersMooc = criteria.list();
		
		criteria = session.createCriteria(Videos.class, "obj");
		criteria.add(Restrictions.gt("obj.timeModified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.videosMooc = criteria.list();
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
		progressMooc.clear();
		questionsMooc.clear();
		segmentsMooc.clear();
		unitResourcesMooc.clear();
		usersMooc.clear();
		videosMooc.clear();
		
	}

	@Override
	public Map<Long, CourseUser> generateCourseUserMining() {
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
			
			courseUsers.put(insert.getId(), insert);
		}
			
		return courseUsers;
	}  

	@Override
	public Map<Long, CourseAssessment> generateCourseAssessmentMining() {
		final HashMap<Long, CourseAssessment> courseAssessments = new HashMap<Long, CourseAssessment>();
		return courseAssessments;
	}

	@Override
	public Map<Long, CourseCollaboration> generateCourseCollaborativeObjectMining() {
		final HashMap<Long, CourseCollaboration> courseCollaborations = new HashMap<Long, CourseCollaboration>();
		return courseCollaborations;
	}

	@Override
	public Map<Long, Course> generateCourseMining() {
		final HashMap<Long, Course> courses = new HashMap<Long, Course>();
		
		for(Courses loadedItem : this.coursesMooc)
		{
			Course insert = new Course();
			insert.setId(  loadedItem.getId());
			insert.setPlatform(this.connector.getPlatformId(), this.platformMining, this.oldPlatformMining);
			insert.setTitle(loadedItem.getTitle());
			insert.setTimeModified(loadedItem.getTimemodified());
			
			courses.put(insert.getId(), insert);
		}
		return courses;
	}

	@Override
	public Map<Long, CourseLearning> generateCourseLearningObjectMining() {
		final HashMap<Long, CourseLearning> courseLearnings = new HashMap<Long, CourseLearning>();
		
		for(Segments loadedItem : this.segmentsMooc)
		{
			CourseLearning insert = new CourseLearning();
			insert.setId(Long.valueOf("1" + loadedItem.getId()));
			insert.setCourse(loadedItem.getCourseId(), this.courseMining, this.oldCourseMining);
			insert.setLearningObject(Long.valueOf("1" + loadedItem.getId()), this.learningObjectMining, this.oldLearningObjectMining);
			
			if(insert.getLearningObject() != null && insert.getCourse() != null)
				courseLearnings.put(insert.getId(), insert);
		}
		
		
		return courseLearnings;
	}

	@Override
	public Map<Long, UserAssessment> generateAssessmentUserMining() {
		final HashMap<Long, UserAssessment> assessmentUsers = new HashMap<Long, UserAssessment>();
		
		
		for(AssessmentSessions loadedItem : this.assessmentSessionsMooc)
		{
			UserAssessment insert = new UserAssessment();
			
			CourseUser cu = this.courseUserMining.get(loadedItem.getMembershipId());
			if(cu == null)
			{
				cu = this.oldCourseUserMining.get(loadedItem.getMembershipId());
			}
			if(cu != null)
			{
				insert.setCourse(cu.getCourse().getId(), this.courseMining, this.oldCourseMining);				
				insert.setUser(cu.getUser().getId(), this.userMining, this.oldUserMining);
				insert.setAssessment(loadedItem.getAssessmentId(), this.assessmentMining, this.oldAssessmentMining);
				insert.setGrade(loadedItem.getScore());
				insert.setTimemodified(loadedItem.getTimeModified());
				
				assessmentUsers.put(insert.getId(), insert);
			}
		}
		
		return assessmentUsers;
	}

	@Override
	public Map<Long, AccessLog> generateLearningLogMining() {
		
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
			if(loadedItem.getCourseId() != 0)
			{
				insert.setCourse(loadedItem.getId(), this.courseMining, this.oldCourseMining);
			}
			else
			{
				insert.setCourse(this.courseLearningObjectMining.get(Long.valueOf("1" + loadedItem.getSegmentId())).getCourse().getId(), this.courseMining, this.oldCourseMining);
			}
			
			if(unitResources.get(loadedItem.getUnitResourceId()) != null && unitResources.get(loadedItem.getUnitResourceId()).getAttachableType().equals("Video"))
			{
				UnitResources uR = unitResources.get(loadedItem.getUnitResourceId());
				insert.setLearningObject(uR.getAttachableId(), this.learningObjectMining, this.oldLearningObjectMining);			
			}
			insert.setTimestamp(loadedItem.getTimestamp());
			
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
			
		}
		return learningLogs;
	}

	@Override
	public Map<Long, CollaborationLog> generateCollaborativeLogMining() {
		final List<CollaborationLog> loglist = new ArrayList<CollaborationLog>();
		final HashMap<Long, CollaborationLog> collaborationLogs = new HashMap<Long, CollaborationLog>();
		
		final Map<Long, CollaborationLog> questionLog = new HashMap<Long, CollaborationLog>();
		final Map<Long, CollaborationLog> answersLog = new HashMap<Long, CollaborationLog>();
		
		
		for(Questions loadedItem : this.questionsMooc)
		{
			CollaborationLog insert = new CollaborationLog();
			insert.setCollaborativeObject(loadedItem.getSegmentId(), this.collaborativeObjectMining, this.oldCollaborativeObjectMining);
			insert.setUser(loadedItem.getUserId(), this.userMining, this.oldUserMining);
			insert.setCourse(loadedItem.getCourseId(), this.courseMining, this.oldCourseMining);
			insert.setAction("Question");
			insert.setText(loadedItem.getContent());
			insert.setTimestamp(loadedItem.getTimeCreated());
			
			if(insert.getCollaborativeObject() != null && insert.getUser() != null && insert.getCourse() != null)
			{
				questionLog.put(loadedItem.getId(),insert);
				loglist.add(insert);
			}
		}
		
		for(Answers loadedItem : this.answersMooc)
		{
			CollaborationLog insert = new CollaborationLog();
			insert.setParent(questionLog.get(loadedItem.getQuestionId()));
			
			if(insert.getParent() != null)
			{
				insert.setId(loadedItem.getId());
				insert.setCollaborativeObject(questionLog.get(loadedItem.getQuestionId()).getCollaborativeObject().getId(), this.collaborativeObjectMining, this.collaborativeObjectMining);
				insert.setUser(loadedItem.getUserId(), this.userMining, this.oldUserMining);
				insert.setCourse(insert.getParent().getCourse().getId(), this.courseMining, this.oldCourseMining);
				insert.setAction("Answer");
				insert.setText(loadedItem.getContent());
				insert.setTimestamp(loadedItem.getTimeCreated());
				
				if(insert.getCollaborativeObject() != null && insert.getUser() != null && insert.getCourse() != null)
				{
					answersLog.put(loadedItem.getId(),insert);
					loglist.add(insert);
				}
			}
		}
		for(Comments loadedItem : this.commentsMooc)
		{
			CollaborationLog insert = new CollaborationLog();
			if(loadedItem.getCommentableType().equals("Answer"))
			{
				insert.setParent(answersLog.get(loadedItem.getCommentableId()));
				if(insert.getParent() != null)
				{
					insert.setCollaborativeObject(answersLog.get(loadedItem.getCommentableId()).getCollaborativeObject().getId(), this.collaborativeObjectMining, this.collaborativeObjectMining);
				}
			}
			else if(loadedItem.getCommentableType().equals("Question"))
			{
				insert.setParent(questionLog.get(loadedItem.getCommentableId()));
				if(insert.getParent() != null)
				{
					insert.setCollaborativeObject(questionLog.get(loadedItem.getCommentableId()).getCollaborativeObject().getId(), this.collaborativeObjectMining, this.collaborativeObjectMining);
				}
			}
			
			if(insert.getParent() != null)
			{
				insert.setId(loadedItem.getId());
				
				insert.setUser(loadedItem.getUserId(), this.userMining, this.oldUserMining);
				insert.setCourse(insert.getParent().getCourse().getId(), this.courseMining, this.oldCourseMining);
				insert.setAction("Comment");
				insert.setText(loadedItem.getContent());
				insert.setTimestamp(loadedItem.getTimeCreated());
				
				if(insert.getCollaborativeObject() != null && insert.getUser() != null && insert.getCourse() != null)
				{
					loglist.add(insert);
				}
			}
		}
		
		Collections.sort(loglist);
		for(int i = 0; i < loglist.size(); i++)
		{
			loglist.get(i).setId(this.collaborationLogMax + i + 1);
			this.collaborationLogMax++;
			collaborationLogs.put(loglist.get(i).getId(), loglist.get(i));			
		}
		
		return collaborationLogs;
	}

	@Override
	public Map<Long, LearningObj> generateLearningObjectMining() {
		final HashMap<Long, LearningObj> learningObjs = new HashMap<Long, LearningObj>();
		
		for(Segments loadedItem : this.segmentsMooc)
		{
			LearningObj insert = new LearningObj();
			insert.setId(Long.valueOf("1" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());

			if(loadedItem.getType().equals("LessonUnit") || loadedItem.getType().equals("Chapter"))
			{
				LearningType type = new LearningType();
				if(this.learnTypes.get(loadedItem.getType()) == null)
				{
					type.setId(this.learningObjectTypeMax + 1);
					type.setType(loadedItem.getType());
					this.learnTypes.put(type.getType(), type);
				}
				else
				{
					type = this.learnTypes.get(loadedItem.getType());
				}
				
				insert.setType(type);
			}
			
			if(loadedItem.getParent() != null)
			{
				insert.setParent(insert.getParent().getId(), learningObjectMining, oldLearningObjectMining);
			}
			
			if(insert.getType() != null)
			{
				learningObjs.put(insert.getId(), insert);
			}
		}
		
		for(Videos loadedItem : this.videosMooc)
		{
			LearningObj insert = new LearningObj();
			insert.setId(Long.valueOf("2" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setUrl(loadedItem.getUrl());

			LearningType type = new LearningType();
			if(this.learnTypes.get("Video") == null)
			{
				type.setId(this.learningObjectTypeMax + 1);
				type.setType("Video");
				this.learnTypes.put(type.getType(), type);
			}
			else
			{
				type = this.learnTypes.get("Video");
			}
				
				insert.setType(type);
				
			if(insert.getType() != null)
			{
				learningObjs.put(insert.getId(), insert);
			}
		}
		
		
		return learningObjs;
	}

	@Override
	public Map<Long, CollaborationObj> generateCollaborativeObjectMining() {
		final HashMap<Long, CollaborationObj> collaborationObjs = new HashMap<Long, CollaborationObj>();
		
		CollaborationType type;
		if(this.collaborativeObjectTypeMining.get("Unit Forum") == null)
		{
			type = new CollaborationType();
			type.setId(1);
			type.setType("Unit Forum");
		}
		else
		{
			type = this.collaborativeObjectTypeMining.get("unit Forum");
		}
		
		for(Segments loadedItem : this.segmentsMooc)
		{
			if(loadedItem.getType().equals("LessonUnit"))
			{
				CollaborationObj insert = new CollaborationObj();
				insert.setId(loadedItem.getId());
				insert.setTitle("Forum "  + loadedItem.getTitle());
				insert.setType(type);
			}
		}
		
		return collaborationObjs;
	}

	@Override
	public Map<Long, User> generateUserMining() {
		final HashMap<Long, User> users = new HashMap<Long, User>();
		
		for(Users loadedItem : this.usersMooc)
		{
			User insert = new User();
			insert.setId(  loadedItem.getId());
			insert.setGender(0);
			insert.setLogin(loadedItem.getId() + "mooc");
			
			users.put(insert.getId(), insert);
		}
		
		return users;
	}

	@Override
	public Map<Long, Assessment> generateAssessmentMining() {
		final HashMap<Long, Assessment> assessments = new HashMap<Long, Assessment>();
		for(Assessments loadedItem : this.assessmentMooc)
		{
			Assessment insert = new Assessment();
			insert.setId(loadedItem.getId());
			insert.setTitle(loadedItem.getTitle());
			
			AssessmentType type = new AssessmentType();
			if(this.assessTypes.get(loadedItem.getType()) == null)
			{
				type.setId(this.assessmentTypeMax + 1);
				type.setType(loadedItem.getType());
				this.assessTypes.put(type.getType(), type);
			}
			else
			{
				type = this.assessTypes.get(loadedItem.getType());
			}
			insert.setType(type);
			
			assessments.put(insert.getId(), insert);
		}
		
		
		return assessments;
	}

	@Override
	public Map<Long, Role> generateRoleMining() {
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
	public Map<Long, AssessmentLog> generateAssessmentLogMining() {
		final HashMap<Long, AssessmentLog> assessmentLogs = new HashMap<Long, AssessmentLog>();
		
		for(AssessmentSessions loadedItem : this.assessmentSessionsMooc)
		{
			AssessmentLog insert = new AssessmentLog();
			insert.setId(loadedItem.getId());
			insert.setDuration(loadedItem.getDuration());
			insert.setAction(loadedItem.getState());
			
			CourseUser cu = this.courseUserMining.get(loadedItem.getMembershipId());
			if(cu == null)
			{
				cu = this.oldCourseUserMining.get(loadedItem.getMembershipId());
			}
			if(cu != null)
			{
				insert.setCourse(cu.getCourse().getId(), this.courseMining, this.oldCourseMining);				
				insert.setUser(cu.getUser().getId(), this.userMining, this.oldUserMining);
				
				assessmentLogs.put(insert.getId(), insert);
			}
		}
		return assessmentLogs;
	}

	@Override
	public Map<String, LearningType> generateLearningObjectTypeMining() {
		return this.learnTypes;
	}

	@Override
	public Map<String, CollaborationType> generateCollaborativeObjectTypeMining() {
		return this.collTypes;
	}

	@Override
	public Map<String, AssessmentType> generateAssessmentTypeMining() {
		return this.assessTypes;
	}

}
