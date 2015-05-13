/**
 * File ./src/main/java/de/lemo/dms/connectors/iversity/ExtractAndMapMoodle.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import de.lemo.dms.connectors.mooc.mapping.Answers;
import de.lemo.dms.connectors.mooc.mapping.Questions;
import de.lemo.dms.connectors.mooc.mapping.Segments;
import de.lemo.dms.connectors.mooc.mapping.UnitResources;
import de.lemo.dms.connectors.mooc.mapping.Users;
import de.lemo.dms.connectors.mooc.mapping.Videos;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.mapping.LearningActivity;
import de.lemo.dms.db.mapping.LearningContext;
import de.lemo.dms.db.mapping.LearningContextExt;
import de.lemo.dms.db.mapping.LearningObject;
import de.lemo.dms.db.mapping.LearningObjectExt;
import de.lemo.dms.db.mapping.ObjectContext;
import de.lemo.dms.db.mapping.Person;
import de.lemo.dms.db.mapping.PersonContext;
import de.lemo.dms.db.mapping.PersonExt;
import de.lemo.dms.processing.resulttype.CourseObject;

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
	private List<Answers> answersMooc;
	private List<Questions> questionsMooc;
	private List<Segments> segmentsMooc;
	private List<UnitResources> unitResourcesMooc;
	private List<Users> usersMooc;
	private List<Videos> videosMooc;
	
	private Map<Long, LearningObjectExt> learningAttributes = new HashMap<Long, LearningObjectExt>();
	private Map<Long, LearningContextExt> courseAttributes = new HashMap<Long, LearningContextExt>();
	private Map<Long, PersonExt> userAttributes = new HashMap<Long, PersonExt>();

	final Map<LearningContext, CourseObject> courseDetails = new HashMap<LearningContext, CourseObject>();
	
	
	
		
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final Map<Long, Long> segmentsToCourses = new HashMap<Long, Long>();
	
	private long eventLimit = 0;
	private DBConfigObject dbConfigInt = null;
	List<Long> coursesInt = null;

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
		
		this.dbConfigInt = dbConfig;
		this.coursesInt = courses;
		
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
		criteria.setMaxResults(1000000);
		criteria.addOrder(Property.forName("obj.id").asc());
		this.eventsMooc = criteria.list();
		if(this.eventsMooc.size() > 0)
			this.eventLimit = this.eventsMooc.get(this.eventsMooc.size()-1).getId();
		logger.info("EventLimit: " + this.eventLimit);
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
		session.clear();
		session.close();
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
	public Map<Long, PersonContext> generatePersonContexts() {
		final HashMap<Long, PersonContext> courseUsers = new HashMap<Long, PersonContext>();
		
		for(Memberships loadedItem : this.membershipsMooc)
		{
			PersonContext insert = new PersonContext();
			insert.setId( loadedItem.getId());
			insert.setLearningContext(  loadedItem.getCourseId(), this.learningContextMining, this.oldLearningContextMining);
			insert.setPerson(loadedItem.getUserId(), this.personMining, this.oldPersonMining);
			String role = "student";
			if(loadedItem.getRole().equals("instructor") || loadedItem.getRole().equals("instructor_assistant"))
				role = "teacher";
			else if(loadedItem.getRole().equals("administrator"))
				role = "administrator";
			insert.setRole(role);
			
			if(insert.getLearningContext() != null && insert.getPerson() != null)
				courseUsers.put(insert.getId(), insert);
		}
		
		return courseUsers;
	}  

	@Override
	public Map<Long, LearningContext> generateLearningContexts() {
		final HashMap<Long, LearningContext> courses = new HashMap<Long, LearningContext>();
		
		for(Courses loadedItem : this.coursesMooc)
		{
			LearningContext insert = new LearningContext();
			insert.setId(  loadedItem.getId());
			insert.setName(loadedItem.getTitle());
			addCourseAttribute(insert, "Course Description", loadedItem.getDescription());
			courses.put(insert.getId(), insert);
		}
		return courses;
	}
	
	private void addCourseAttribute(LearningContext course, String attribute, String value)
	{
		LearningContextExt loe = new LearningContextExt();
		loe.setId(this.learningAttributeIdMax + 1);
		this.learningAttributeIdMax++;
		loe.setLearningContext(course);
		loe.setAttr(attribute);
		loe.setValue(value);
		this.courseAttributes.put(loe.getId(), loe);
	}
	
	private void addUserAttribute(Person user, String attribute, String value)
	{
		PersonExt loe = new PersonExt();
		loe.setId(this.learningAttributeIdMax + 1);
		this.learningAttributeIdMax++;
		loe.setPerson(user);
		loe.setAttr(attribute);
		loe.setValue(value);
		this.userAttributes.put(loe.getId(), loe);
	}
	
	private void addLearningAttribute(LearningObject learning, String attribute, String value)
	{
		LearningObjectExt loe = new LearningObjectExt();
		loe.setId(this.learningAttributeIdMax + 1);
		this.learningAttributeIdMax++;
		loe.setLearningObject(learning);
		loe.setAttr(attribute);
		loe.setValue(value);
		this.learningAttributes.put(loe.getId(), loe);
	}

	@Override
	public Map<Long, ObjectContext> generateObjectContexts() {
		final HashMap<Long, ObjectContext> courseLearnings = new HashMap<Long, ObjectContext>();
		
		Map<Long, Long> unitCourses = new HashMap<Long, Long>();
		
		
		for(Segments loadedItem : this.segmentsMooc)
		{
			ObjectContext insert = new ObjectContext();
			insert.setId(Long.valueOf("10" + loadedItem.getId()));
			insert.setLearningContext(loadedItem.getCourseId(), this.learningContextMining, this.oldLearningContextMining);
			insert.setLearningObject(Long.valueOf("10" + loadedItem.getId()), this.learningObjectMining, this.oldLearningObjectMining);
			
			unitCourses.put(loadedItem.getId(), loadedItem.getCourseId());
			
			if(insert.getLearningObject() != null && insert.getLearningContext() != null)
				courseLearnings.put(insert.getId(), insert);
		}		
		
		for(Segments loadedItem : this.segmentsMooc)
		{
			if(loadedItem.getType().equals("LessonUnit"))
			{
				ObjectContext insert = new ObjectContext();
				insert.setId(Long.valueOf("13" + loadedItem.getId()));
				insert.setLearningContext(loadedItem.getCourseId(), this.learningContextMining, this.oldLearningContextMining);
				insert.setLearningObject(Long.valueOf("13" + loadedItem.getId()), this.learningObjectMining, this.oldLearningObjectMining);
				
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for(UnitResources loadedItem : this.unitResourcesMooc)
		{
			if(loadedItem.getAttachableType().equals("Video"))
			{
				ObjectContext insert = new ObjectContext();
				insert.setId(Long.valueOf("12" + loadedItem.getId()));
				if(unitCourses.get(loadedItem.getUnitId()) != null)
				{
					insert.setLearningContext(unitCourses.get(loadedItem.getUnitId()), this.learningContextMining, this.oldLearningContextMining);
				}
				insert.setLearningObject(Long.valueOf("12" + loadedItem.getAttachableId()), this.learningObjectMining, this.oldLearningObjectMining);
				
				if(insert.getLearningContext() != null && insert.getLearningObject() != null)
				{
					courseLearnings.put(insert.getId(), insert);
				}
			}
			
			if(loadedItem.getAttachableType().equals("Assessment"))
			{
				ObjectContext insert = new ObjectContext();
				insert.setId(Long.valueOf("11" + loadedItem.getId()));
				if(unitCourses.get(loadedItem.getUnitId()) != null)
				{
					insert.setLearningContext(unitCourses.get(loadedItem.getUnitId()), this.learningContextMining, this.oldLearningContextMining);
				}
				insert.setLearningObject(Long.valueOf("11" + loadedItem.getAttachableId()), this.learningObjectMining, this.oldLearningObjectMining);
				
				if(insert.getLearningContext() != null && insert.getLearningObject() != null)
				{
					courseLearnings.put(insert.getId(), insert);
				}
			}
			if(loadedItem.getAttachableType().equals("Question"))
			{
				ObjectContext insert = new ObjectContext();
				insert.setId(Long.valueOf("14" + loadedItem.getId()));
				if(unitCourses.get(loadedItem.getUnitId()) != null)
				{
					insert.setLearningContext(unitCourses.get(loadedItem.getUnitId()), this.learningContextMining, this.oldLearningContextMining);
				}
				insert.setLearningObject(Long.valueOf("14" + loadedItem.getAttachableId()), this.learningObjectMining, this.oldLearningObjectMining);
				
				if(insert.getLearningContext() != null && insert.getLearningObject() != null)
				{
					courseLearnings.put(insert.getId(), insert);
				}
			}			
		}
		
		return courseLearnings;
	}

	@Override
	public Map<Long, LearningActivity> generateLearningActivities() {
		
		final HashMap<Long, LearningActivity> learningLogs = new HashMap<Long, LearningActivity>();
		final Map<Long, UnitResources> unitResources = new HashMap<Long, UnitResources>();
		for(UnitResources u : this.unitResourcesMooc)
		{
			unitResources.put(u.getId(), u);
		}		
		
		while(this.eventsMooc.size() > 0)
		{
			for(Events loadedItem : this.eventsMooc)
			{
				LearningActivity insert = new LearningActivity();
				insert.setId(this.accessLogMax + 1);
				this.accessLogMax++;
				insert.setPerson(loadedItem.getUserId(), this.personMining, this.oldPersonMining);
				if(loadedItem.getCourseId() != null)
				{
					insert.setLearningContext(loadedItem.getId(), this.learningContextMining, this.oldLearningContextMining);
				}
				else if (this.segmentsToCourses.get(loadedItem.getSegmentId()) != null)
				{
					insert.setLearningContext(this.segmentsToCourses.get(loadedItem.getSegmentId()), this.learningContextMining, this.oldLearningContextMining);
				}
				
				if(unitResources.get(loadedItem.getUnitResourceId()) != null && unitResources.get(loadedItem.getUnitResourceId()).getAttachableType().equals("Video"))
				{
					UnitResources uR = unitResources.get(loadedItem.getUnitResourceId());
					insert.setLearningObject(Long.valueOf("12" + uR.getAttachableId()), this.learningObjectMining, this.oldLearningObjectMining);			
				}
				insert.setTime(loadedItem.getTimestamp().getTime()/1000);
				
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
				
				if(insert.getLearningContext() != null && !courseDetails.containsKey(insert.getLearningContext()))
				{
					courseDetails.put(insert.getLearningContext(), new CourseObject());
					courseDetails.get(insert.getLearningContext()).setId(insert.getLearningContext().getId());
					courseDetails.get(insert.getLearningContext()).setFirstRequest(insert.getTime());
					courseDetails.get(insert.getLearningContext()).setLastRequest(insert.getTime());
					
				}
				if(insert.getTime() > maxLog)
				{
					maxLog = insert.getTime();
				}
				if(insert.getLearningContext() != null && insert.getTime() > courseDetails.get(insert.getLearningContext()).getLastRequest())
				{
					courseDetails.get(insert.getLearningContext()).setLastRequest(insert.getTime());
				}
				if(insert.getLearningContext() != null && insert.getTime() < courseDetails.get(insert.getLearningContext()).getFirstRequest())
				{
					courseDetails.get(insert.getLearningContext()).setFirstRequest(insert.getTime());
				}
				
				if ((insert.getLearningContext() != null) && (insert.getLearningObject() != null) && (insert.getPerson() != null)) 
				{
					learningLogs.put(insert.getId(), insert);
					if(insert.getTime()== null)
						System.out.println("Time-out Events");
				}
				
			}
			
			this.eventsMooc.clear();
			
			final Session miningSession = this.dbHandler.getMiningSession();
			List<Collection<?>> logs = new ArrayList<Collection<?>>();
			logs.add(learningLogs.values());

			this.dbHandler.saveCollectionToDB(miningSession, logs);
			learningLogs.clear();
			miningSession.clear();
			miningSession.close();
			
			
			final Session moocSession = HibernateUtil.getSessionFactory(dbConfigInt).openSession();
			Criteria criteria = moocSession.createCriteria(Events.class, "obj");
			List<Long> segments = new ArrayList<Long>();
			for(Segments s : this.segmentsMooc)
			{
				segments.add(s.getId());
			}
			if(!segments.isEmpty())
			{
				criteria.add(Restrictions.in("obj.segmentId", segments));
				criteria.add(Restrictions.gt("obj.id", this.eventLimit));
			}
			criteria.setMaxResults(1000000);
			criteria.addOrder(Property.forName("obj.id").asc());
			this.eventsMooc = criteria.list();
			if(this.eventsMooc.size() > 0)
				this.eventLimit = this.eventsMooc.get(this.eventsMooc.size()-1).getId();
			moocSession.clear();
			moocSession.close();
			
		}
		///
		///
		///Collabs//////////////////////////
		learningLogs.clear();
		
		final List<LearningActivity> loglist = new ArrayList<LearningActivity>();
		final HashMap<Long, LearningActivity> collaborationLogs = new HashMap<Long, LearningActivity>();
		
		final Map<Long, LearningActivity> questionLog = new HashMap<Long, LearningActivity>();
		final Map<Long, LearningActivity> answersLog = new HashMap<Long, LearningActivity>();
		
		
		for(Questions loadedItem : this.questionsMooc)
		{
			LearningActivity insert = new LearningActivity();
			insert.setLearningObject(Long.valueOf("14" + loadedItem.getSegmentId()), this.learningObjectMining, this.oldLearningObjectMining);
			insert.setTime(loadedItem.getTimeCreated().getTime()/1000);
			insert.setPerson(loadedItem.getUserId(), this.personMining, this.oldPersonMining);
			insert.setLearningContext(loadedItem.getCourseId(), this.learningContextMining, this.oldLearningContextMining);
			insert.setAction("Question");				
			String com = loadedItem.getContent();
			if(com != null && com.length() > 255)
				com = com.substring(0, 254);
			insert.setInfo(com);
			
			if(loadedItem.getSegmentId() != null)
			{
				insert.setLearningObject(Long.valueOf("13" + loadedItem.getSegmentId()), learningObjectMining, oldLearningObjectMining);
			}
			
			if(insert.getLearningContext() != null && !courseDetails.containsKey(insert.getLearningContext()))
			{
				courseDetails.put(insert.getLearningContext(), new CourseObject());
				courseDetails.get(insert.getLearningContext()).setId(insert.getLearningContext().getId());
				courseDetails.get(insert.getLearningContext()).setFirstRequest(insert.getTime());
				courseDetails.get(insert.getLearningContext()).setLastRequest(insert.getTime());
				
			}
			if(insert.getTime() > maxLog)
			{
				maxLog = insert.getTime();
			}
			if(insert.getLearningContext() != null && insert.getTime() > courseDetails.get(insert.getLearningContext()).getLastRequest())
			{
				courseDetails.get(insert.getLearningContext()).setLastRequest(insert.getTime());
			}
			if(insert.getLearningContext() != null && insert.getTime() < courseDetails.get(insert.getLearningContext()).getFirstRequest())
			{
				courseDetails.get(insert.getLearningContext()).setFirstRequest(insert.getTime());
			}
			
			if(insert.getLearningObject() != null && insert.getPerson() != null && insert.getLearningContext() != null)
			{
				questionLog.put(loadedItem.getId(), insert);
				loglist.add(insert);
			}
		}
		
		for(Answers loadedItem : this.answersMooc)
		{
			LearningActivity insert = new LearningActivity();
			insert.setReference(questionLog.get(loadedItem.getQuestionId()));
			
			if(insert.getReference() != null)
			{
				insert.setId(loadedItem.getId());
				insert.setLearningObject(questionLog.get(loadedItem.getQuestionId()).getLearningObject().getId(), this.learningObjectMining, this.learningObjectMining);
				insert.setPerson(loadedItem.getUserId(), this.personMining, this.oldPersonMining);
				insert.setLearningContext(insert.getReference().getLearningContext().getId(), this.learningContextMining, this.oldLearningContextMining);
				insert.setAction("Answer");				String com = loadedItem.getContent();
				if(com != null && com.length() > 255)
					com = com.substring(0, 254);
				insert.setInfo(com);
				insert.setTime(loadedItem.getTimeCreated().getTime() / 1000);
				insert.setReference(questionLog.get(loadedItem.getQuestionId()));
				if(!courseDetails.containsKey(insert.getLearningContext()))
				{
					courseDetails.put(insert.getLearningContext(), new CourseObject());
					courseDetails.get(insert.getLearningContext()).setFirstRequest(insert.getTime());
				}
				courseDetails.get(insert.getLearningContext()).setLastRequest(insert.getTime());
				
				if(insert.getLearningObject() != null && insert.getPerson() != null && insert.getLearningContext() != null && insert.getReference() != null)
				{
					answersLog.put(loadedItem.getId() , insert);
					loglist.add(insert);
				}
			}
		}
		for(Comments loadedItem : this.commentsMooc)
		{
			LearningActivity insert = new LearningActivity();
			insert.setTime(loadedItem.getTimeCreated().getTime()/1000);
			if(loadedItem.getCommentableType().equals("Answer"))
			{
				insert.setReference(answersLog.get(loadedItem.getCommentableId()));
				if(insert.getReference() != null)
				{
					insert.setLearningObject(answersLog.get(loadedItem.getCommentableId()).getLearningObject().getId(), this.learningObjectMining, this.learningObjectMining);
				}
			}
			else if(loadedItem.getCommentableType().equals("Question"))
			{
				insert.setReference(questionLog.get(loadedItem.getCommentableId()));
				if(insert.getReference() != null)
				{
					insert.setLearningObject(questionLog.get(loadedItem.getCommentableId()).getLearningObject().getId(), this.learningObjectMining, this.learningObjectMining);
				}
			}
			
			if(insert.getReference() != null)
			{
				insert.setId(loadedItem.getId());
				
				insert.setPerson(loadedItem.getUserId(), this.personMining, this.oldPersonMining);
				insert.setLearningContext(insert.getReference().getLearningContext().getId(), this.learningContextMining, this.oldLearningContextMining);
				insert.setAction("Comment");
				String com = loadedItem.getContent();
				if(com != null && com.length() > 255)
					com = com.substring(0, 254);
				insert.setInfo(com);				
				if(!courseDetails.containsKey(insert.getLearningContext()))
				{
					courseDetails.put(insert.getLearningContext(), new CourseObject());
					courseDetails.get(insert.getLearningContext()).setFirstRequest(insert.getTime());
				}
				courseDetails.get(insert.getLearningContext()).setLastRequest(insert.getTime());
				
				if(insert.getLearningObject() != null && insert.getPerson() != null && insert.getLearningContext() != null && insert.getReference() != null)
				{
					loglist.add(insert);
				}
			}
		}
		
		Collections.sort(loglist);
		for(int i = 0; i < loglist.size(); i++)
		{
			LearningActivity l = loglist.get(i);
			l.setId(this.accessLogMax + 1);
			this.accessLogMax++;
			learningLogs.put(l.getId(), l);			
		}
		
		Session miningSession = this.dbHandler.getMiningSession();
		List<Collection<?>> logs = new ArrayList<Collection<?>>();
		logs.add(learningLogs.values());
		this.dbHandler.saveCollectionToDB(miningSession, logs);
		learningLogs.clear();
		miningSession.clear();
		miningSession.close();
		
		
		final Set<Long> maxGradeKnown = new HashSet<Long>();		
		for(AssessmentSessions loadedItem : this.assessmentSessionsMooc)
		{
			LearningActivity insert = new LearningActivity();			
			PersonContext cu = this.personContextMining.get(loadedItem.getMembershipId());
			if(cu == null)
			{
				cu = this.oldPersonContextMining.get(loadedItem.getMembershipId());
			}
			if(cu != null && cu.getPerson() != null)
			{
				if(loadedItem.getScore() != null)
				{
					insert.setId(this.accessLogMax + 1);
					this.accessLogMax ++;
					insert.setLearningObject(Long.valueOf("11" + loadedItem.getAssessmentId()), this.learningObjectMining, this.oldLearningObjectMining);
					insert.setLearningContext(cu.getLearningContext().getId(), this.learningContextMining, this.oldLearningContextMining);				
					insert.setPerson(cu.getPerson().getId(), this.personMining, this.oldPersonMining);
					insert.setLearningObject(Long.valueOf("11" + loadedItem.getAssessmentId()), this.learningObjectMining, this.learningObjectMining);
					insert.setInfo(loadedItem.getScore() + "");
					insert.setTime(loadedItem.getTimeModified().getTime()/1000);
					insert.setAction("Submit");
					
					if(!maxGradeKnown.contains(Long.valueOf("11" + loadedItem.getAssessmentId())) && insert.getLearningObject() != null)
					{
						addLearningAttribute(insert.getLearningObject(), "MaxGrade", loadedItem.getMaxScore()+"");
						maxGradeKnown.add(Long.valueOf("11" + loadedItem.getAssessmentId()));
					}
					if(insert.getClass() != null && insert.getPerson() != null && insert.getLearningObject() != null && insert.getLearningContext() != null)
						learningLogs.put(insert.getId(), insert);
				}
			}
		}	
		
		miningSession = this.dbHandler.getMiningSession();
		logs = new ArrayList<Collection<?>>();
		logs.add(learningLogs.values());

		this.dbHandler.saveCollectionToDB(miningSession, logs);
		learningLogs.clear();
		miningSession.clear();
		miningSession.close();
		
		return learningLogs;
	}

	
	@Override
	public Map<Long, LearningObject> generateLearningObjects() {
		final HashMap<Long, LearningObject> learningObjs = new HashMap<Long, LearningObject>();
		
		for(LearningContext loadedItem : this.learningContextMining.values())
		{
			LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("15" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			insert.setType("Course Exam");
			
			if(loadedItem.getName() != null)
			{
				learningObjs.put(insert.getId(), insert);
			}
		}
		

		
		
		for(Segments loadedItem : this.segmentsMooc)
		{
			this.segmentsToCourses.put(loadedItem.getId(),loadedItem.getCourseId());
			
			if(loadedItem.getType().equals("LessonUnit"))
			{
				LearningObject insert = new LearningObject();
				insert.setId(Long.valueOf("13" + loadedItem.getId()));
				insert.setName("Forum "  + loadedItem.getTitle());
				insert.setType("UnitForum");
				
				
				
				if(loadedItem.getTitle() != null)
				{
					learningObjs.put(insert.getId(), insert);
				}
			}
		}
		
		for(Questions loadedItem : this.questionsMooc)
		{
			LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("14" + loadedItem.getId()));
			insert.setName(loadedItem.getTitle());
			insert.setType("Thread");
			
			
			insert.setParent(Long.valueOf("13" + loadedItem.getSegmentId()), learningObjs, this.oldLearningObjectMining);
			
			if(loadedItem.getTitle() != null)
			{
				learningObjs.put(insert.getId(), insert);
			}
		}
		
		for(Assessments loadedItem : this.assessmentMooc)
		{
			LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("11" + loadedItem.getId()));
			insert.setName(loadedItem.getTitle());
			insert.setType(loadedItem.getType());
			
			
			if(loadedItem.getTitle() != null)
			{
				learningObjs.put(insert.getId(), insert);
			}
		}
		
		for(Segments loadedItem : this.segmentsMooc)
		{
			LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("10" + loadedItem.getId()));
			insert.setName(loadedItem.getTitle());	

			if(loadedItem.getType().equals("LessonUnit") || loadedItem.getType().equals("Chapter"))
			{
				insert.setType(loadedItem.getType());
			}
			
			if(loadedItem.getParent() != null)
			{
				insert.setParent(Long.valueOf("10" + loadedItem.getParent()), learningObjs, oldLearningObjectMining);
			}
			
			if(insert.getType() != null)
			{
				learningObjs.put(insert.getId(), insert);
			}
		}
		
		for(Videos loadedItem : this.videosMooc)
		{
			LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("12" + loadedItem.getId()));
			insert.setName(loadedItem.getTitle());
			insert.setType("Video");
				
			if(insert.getType() != null && loadedItem.getTitle() != null)
			{
				learningObjs.put(insert.getId(), insert);
			}
		}	
		
		/*
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
		*/
		return learningObjs;
	}

	
	@Override
	public Map<Long, Person> generatePersons() {
		final HashMap<Long, Person> users = new HashMap<Long, Person>();
		
		//Test
/*		Map<Long, Long> uid = new HashMap<Long, Long>();
		for(Memberships m : this.membershipsMooc)
			if(m.getProgress() >= 80)
				uid.put(m.getUserId(), m.getProgress());
*/		
		
		for(Users loadedItem : this.usersMooc)
		{
			Person insert = new Person();
			insert.setId(  loadedItem.getId());
			insert.setName(loadedItem.getId() + "mooc");
			
			//
//			if(uid.containsKey(loadedItem.getId()))
//			{
			
			if(loadedItem.getGender() != null && loadedItem.getGender().equals("male"))
				addUserAttribute(insert, "User Gender", "1");
			if(loadedItem.getGender() != null && loadedItem.getGender().equals("female"))
				addUserAttribute(insert, "User Gender", "0");
			addUserAttribute(insert, "User Timezone", loadedItem.getTimezone());
//				addUserAttribute(insert, "UserProgress", uid.get(loadedItem.getId())+"");
			users.put(insert.getId(), insert);
//			}
		}
		
		this.usersMooc.clear();
		
		return users;
	}



	@Override
	public Map<Long, LearningContextExt> generateLearningContextExts() {
		
		
		for(LearningContextExt lce : this.courseAttributes.values())
		{
			lce.setLearningContext(lce.getLearningContext().getId(), this.learningContextMining, this.oldLearningContextMining);
		}
		
		for(LearningContextExt ca : this.oldLearningContextExtMining.values())
		{
			if(ca.getAttr().equals("CourseLastRequest") && this.courseDetails.get(ca.getLearningContext()) != null && this.courseDetails.get(ca.getLearningContext()).getLastRequest() > Long.valueOf(ca.getValue()))
			{
				ca.setValue(this.courseDetails.get(ca.getLearningContext()).getLastRequest().toString());
				this.courseAttributes.put(ca.getId(), ca);
			}
			if(ca.getAttr().equals("CourseFirstRequest") && this.courseDetails.get(ca.getLearningContext()) != null && this.courseDetails.get(ca.getLearningContext()).getFirstRequest() < Long.valueOf(ca.getValue()))
			{
				ca.setValue(this.courseDetails.get(ca.getLearningContext()).getFirstRequest().toString());
				this.courseAttributes.put(ca.getId(), ca);
			}
		}
		if(this.oldLearningContextExtMining.isEmpty())
		{

			for(CourseObject co :this.courseDetails.values())
			{
				LearningContextExt ca = new LearningContextExt();
				ca.setId(this.courseAttributeIdMax + 1);
				this.courseAttributeIdMax++;
				ca.setAttr("CourseLastRequest");
				ca.setLearningContext(this.learningContextMining.get(co.getId()));
				ca.setValue(co.getLastRequest().toString());
				this.courseAttributes.put(ca.getId(), ca);
				
				LearningContextExt first= new LearningContextExt();
				first.setId(this.courseAttributeIdMax + 1);
				this.courseAttributeIdMax++;
				first.setAttr("CourseFirstRequest");
				first.setLearningContext(this.learningContextMining.get(co.getId()));
				first.setValue(co.getFirstRequest().toString());
				this.courseAttributes.put(first.getId(), first);
			}
		}
		
		return this.courseAttributes;
	}

	@Override
	public Map<Long, PersonExt> generatePersonExts() {
		
		for(PersonExt lce : this.userAttributes.values())
		{
			lce.setPerson(lce.getPerson().getId(), this.personMining, this.oldPersonMining);
		}
		return this.userAttributes;
	}

	@Override
	public Map<Long, LearningObjectExt> generateLearningObjectExts() {
		
		for(LearningObjectExt lce : this.learningAttributes.values())
		{
			lce.setLearningObject(lce.getLearningObject().getId(), this.learningObjectMining, this.oldLearningObjectMining);
		}
		
		return this.learningAttributes;
	}
}
