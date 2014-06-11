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


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.TextHelper;
import de.lemo.dms.connectors.mooc.mapping.AssessmentAnswers;
import de.lemo.dms.connectors.mooc.mapping.AssessmentQuestions;
import de.lemo.dms.connectors.mooc.mapping.Assessments;
import de.lemo.dms.connectors.mooc.mapping.Courses;
import de.lemo.dms.connectors.mooc.mapping.Events;
import de.lemo.dms.connectors.mooc.mapping.Memberships;
import de.lemo.dms.connectors.mooc.mapping.Progress;
import de.lemo.dms.connectors.mooc.mapping.Questions;
import de.lemo.dms.connectors.mooc.mapping.Segments;
import de.lemo.dms.connectors.mooc.mapping.UnitResources;
import de.lemo.dms.connectors.mooc.mapping.Users;
import de.lemo.dms.connectors.mooc.mapping.Videos;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.mapping.Assessment;
import de.lemo.dms.db.mapping.CollaborativeObjectLog;
import de.lemo.dms.db.mapping.CollaborativeObject;
import de.lemo.dms.db.mapping.CollaborativeObjectType;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseCollaborativeObject;
import de.lemo.dms.db.mapping.CourseLearningObject;
import de.lemo.dms.db.mapping.CourseTask;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningObject;
import de.lemo.dms.db.mapping.LearningObjectType;
import de.lemo.dms.db.mapping.Task;
import de.lemo.dms.db.mapping.TaskType;
import de.lemo.dms.db.mapping.TaskUser;
import de.lemo.dms.db.mapping.ViewLog;
import de.lemo.dms.db.mapping.Role;
import de.lemo.dms.db.mapping.TaskLog;
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
	private List<Assessment> assessmentMooc;
	private List<Courses> coursesMooc;
	private List<Events> eventsMooc;
	private List<Memberships> membershipsMooc;
	private List<Progress> progressMooc;
	private List<Questions> questionsMooc;
	private List<Segments> segmentsMooc;
	private List<UnitResources> unitResourcesMooc;
	private List<Users> usersMooc;
	private List<Videos> videosMooc;
	

	private Map<Long, Long> chatCourse = new HashMap<Long, Long>();
	protected Map<Long, Assessment> assessmentLog = new HashMap<Long, Assessment>();
	
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
		
		criteria = session.createCriteria(Assessments.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assessmentMooc = criteria.list();
		
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
			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourseId()), this.courseMining, this.oldCourseMining);
			insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserId()), this.userMining, this.oldUserMining);
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
	public Map<Long, CourseTask> generateCourseTaskMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, CourseCollaborativeObject> generateCourseCollaborativeObjectMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, Course> generateCourseMining() {
		final HashMap<Long, Course> courses = new HashMap<Long, Course>();
		
		for(Courses loadedItem : this.coursesMooc)
		{
			Course insert = new Course();
			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setPlatform(this.connector.getPlatformId(), this.platformMining, this.oldPlatformMining);
			insert.setTitle(loadedItem.getTitle());
			insert.setTimeModified(loadedItem.getTimemodified());
			
			courses.put(insert.getId(), insert);
		}
		return courses;
	}

	@Override
	public Map<Long, CourseLearningObject> generateCourseLearningObjectMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, TaskUser> generateTaskUserMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, ViewLog> generateViewLogMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, CollaborativeObjectLog> generateCollaborativeLogMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, LearningObject> generateLearningObjectMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, CollaborativeObject> generateCollaborativeObjectMining() {
		return null;
	}

	@Override
	public Map<Long, User> generateUserMining() {
		final HashMap<Long, User> users = new HashMap<Long, User>();
		
		for(Users loadedItem : this.usersMooc)
		{
			User insert = new User();
			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setGender(0);
			insert.setLogin(loadedItem.getId() + "mooc");
			
			users.put(insert.getId(), insert);
		}
		
		return users;
	}

	@Override
	public Map<Long, Task> generateTaskMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, Role> generateRoleMining() {
		final HashMap<Long, Role> roles = new HashMap<Long, Role>();
		
		Role admin = new Role();
		admin.setId(Long.valueOf(this.connector.getPrefix() + "" + 0));
		admin.setSortOrder(0);
		admin.setTitle("Administrator");
		admin.setType(0);
		
		Role teacher = new Role();
		teacher.setId(Long.valueOf(this.connector.getPrefix() + "" + 1));
		teacher.setSortOrder(1);
		teacher.setTitle("Instructor");
		teacher.setType(1);
		
		Role student = new Role();
		student.setId(Long.valueOf(this.connector.getPrefix() + "" + 2));
		student.setSortOrder(2);
		student.setTitle("Student");
		student.setType(2);
		
		roles.put(admin.getId(), admin);
		roles.put(teacher.getId(), teacher);
		roles.put(student.getId(), student);
	
		
		return roles;
	}

	@Override
	public Map<Long, TaskLog> generateTaskLogMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, Assessment> generateAssessmentMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, LearningObjectType> generateLearningObjectTypeMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, CollaborativeObjectType> generateCollaborativeObjectTypeMining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, TaskType> generateTaskTypeMining() {
		// TODO Auto-generated method stub
		return null;
	}

}
