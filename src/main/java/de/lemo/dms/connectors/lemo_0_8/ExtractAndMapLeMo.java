/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/ExtractAndMapMoodle.java
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
 * File ./main/java/de/lemo/dms/connectors/lemo_0_8/ExtractAndMapMoodle.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import de.lemo.dms.connectors.lemo_0_8.mapping.AssignmentLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.AssignmentLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ChatLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ChatLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ConfigLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseAssignmentLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseChatLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseForumLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseGroupLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseQuizLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseResourceLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseScormLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseUserLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.CourseWikiLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ForumLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ForumLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.GroupLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.GroupUserLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.IDMappingLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.LevelAssociationLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.LevelCourseLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.LevelLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.PlatformLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuestionLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuestionLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuizLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuizLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuizQuestionLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.QuizUserLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ResourceLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ResourceLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.RoleLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ScormLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.ScormLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.UserLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.WikiLogLMS;
import de.lemo.dms.connectors.lemo_0_8.mapping.WikiLMS;
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
public class ExtractAndMapLeMo extends ExtractAndMap {
	// Versionsnummer in Namen einfügen

	// Object-classes
	private List<AssignmentLMS> assignmentLms = new ArrayList<AssignmentLMS>();
	private List<ChatLMS> chatLms = new ArrayList<ChatLMS>();
	private List<CourseLMS> courseLms = new ArrayList<CourseLMS>();
	private List<ForumLMS> forumLms = new ArrayList<ForumLMS>();
	private List<QuizLMS> quizLms = new ArrayList<QuizLMS>();
	private List<ResourceLMS> resourceLms = new ArrayList<ResourceLMS>();
	private List<RoleLMS> roleLms = new ArrayList<RoleLMS>();
	private List<ScormLMS> scormLms = new ArrayList<ScormLMS>();
	private List<UserLMS> userLms = new ArrayList<UserLMS>();
	private List<WikiLMS> wikiLms = new ArrayList<WikiLMS>();

	// Association-classes
	
	private List<CourseAssignmentLMS> courseAssignmentLms = new ArrayList<CourseAssignmentLMS>();
	private List<CourseChatLMS> courseChatLms = new ArrayList<CourseChatLMS>();
	private List<CourseForumLMS> courseForumLms = new ArrayList<CourseForumLMS>();
	private List<CourseQuizLMS> courseQuizLms = new ArrayList<CourseQuizLMS>();
	private List<CourseResourceLMS> courseResourceLms = new ArrayList<CourseResourceLMS>();
	private List<CourseScormLMS> courseScormLms = new ArrayList<CourseScormLMS>();
	private List<CourseUserLMS> courseUserLms = new ArrayList<CourseUserLMS>();
	private List<CourseWikiLMS> courseWikiLms = new ArrayList<CourseWikiLMS>();
	private List<QuizUserLMS> quizUserLms = new ArrayList<QuizUserLMS>();

	// Log-classes
	
	private List<AssignmentLogLMS> assignmentLogLms = new ArrayList<AssignmentLogLMS>();
	private List<ChatLogLMS> chatLogLms = new ArrayList<ChatLogLMS>();
	private List<ForumLogLMS> forumLogLms = new ArrayList<ForumLogLMS>();
	private List<ResourceLogLMS> resourceLogLms = new ArrayList<ResourceLogLMS>();
	private List<ScormLogLMS> scormLogLms = new ArrayList<ScormLogLMS>();
	private List<QuizLogLMS> quizLogLms = new ArrayList<QuizLogLMS>();
	private List<WikiLogLMS> wikiLogLms = new ArrayList<WikiLogLMS>();
	
	private final Logger logger = Logger.getLogger(this.getClass());

	public ExtractAndMapLeMo(final IConnector connector) {
		super(connector);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void getLMStables(final DBConfigObject dbConfig, final long readingfromtimestamp, List<Long> courses, List<String> logins) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConfig).openSession();
		session.clear();
		final Transaction tx = session.beginTransaction();
		
		//Read Context
		Criteria criteria = session.createCriteria(CourseLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseLms = criteria.list();
		logger.info("CourseLMS tables: " + this.courseLms.size());
		
		criteria = session.createCriteria(UserLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.userLms = criteria.list();
		logger.info("UserLMS tables: " + this.courseLms.size());
		
		criteria = session.createCriteria(RoleLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.roleLms = criteria.list();
		logger.info("RoleLMS tables: " + this.roleLms.size());
		
		criteria = session.createCriteria(AssignmentLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assignmentLms = criteria.list();
		logger.info("AssignmentLMS tables: " + this.assignmentLms.size());
		
		criteria = session.createCriteria(ChatLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.chatLms = criteria.list();
		logger.info("ChatLMS tables: " + this.chatLms.size());
		
		criteria = session.createCriteria(ForumLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.forumLms = criteria.list();
		logger.info("ForumLMS tables: " + this.forumLms.size());
		
		criteria = session.createCriteria(QuizLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.quizLms = criteria.list();
		logger.info("QuizLMS tables: " + this.quizLms.size());
		
		criteria = session.createCriteria(ResourceLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.resourceLms = criteria.list();
		logger.info("ResourceLMS tables: " + this.resourceLms.size());
		
		criteria = session.createCriteria(ScormLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.scormLms = criteria.list();
		logger.info("ScormLMS tables: " + this.scormLms.size());
		
		criteria = session.createCriteria(WikiLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.wikiLms = criteria.list();
		logger.info("WikiLMS tables: " + this.wikiLms.size());
		
		criteria = session.createCriteria(WikiLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.wikiLms = criteria.list();
		logger.info("WikiLMS tables: " + this.wikiLms.size());
		
		criteria = session.createCriteria(CourseAssignmentLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseAssignmentLms = criteria.list();
		logger.info("CourseAssignmentLMS tables: " + this.courseAssignmentLms.size());
		
		criteria = session.createCriteria(CourseChatLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseChatLms = criteria.list();
		logger.info("CourseChatLMS tables: " + this.courseChatLms.size());
		
		criteria = session.createCriteria(CourseForumLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseForumLms = criteria.list();
		logger.info("CourseForumLMS tables: " + this.courseForumLms.size());
		
		criteria = session.createCriteria(CourseQuizLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseQuizLms = criteria.list();
		logger.info("CourseQuizLMS tables: " + this.courseQuizLms.size());
		
		criteria = session.createCriteria(CourseResourceLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseResourceLms = criteria.list();
		logger.info("CourseResourceLMS tables: " + this.courseResourceLms.size());
		
		criteria = session.createCriteria(CourseScormLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseScormLms = criteria.list();
		logger.info("CourseScormLMS tables: " + this.courseScormLms.size());
		
		criteria = session.createCriteria(CourseWikiLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseWikiLms = criteria.list();
		logger.info("CourseWikiLMS tables: " + this.courseWikiLms.size());
		
		criteria = session.createCriteria(CourseUserLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseUserLms = criteria.list();
		logger.info("CourseUserLMS tables: " + this.courseUserLms.size());

		criteria = session.createCriteria(AssignmentLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assignmentLogLms = criteria.list();
		logger.info("AssignmentLogLMS tables: " + this.assignmentLogLms.size());
		
		criteria = session.createCriteria(ChatLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.chatLogLms = criteria.list();
		logger.info("ChatLogLMS tables: " + this.chatLogLms.size());
		
		criteria = session.createCriteria(ForumLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.forumLogLms = criteria.list();
		logger.info("ForumLogLMS tables: " + this.forumLogLms.size());
		
		criteria = session.createCriteria(QuizLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.quizLogLms = criteria.list();
		logger.info("QuizLogLMS tables: " + this.quizLogLms.size());
		
		criteria = session.createCriteria(ResourceLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.resourceLogLms = criteria.list();
		logger.info("ResourceLogLMS tables: " + this.resourceLogLms.size());
		
		criteria = session.createCriteria(ScormLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.scormLogLms = criteria.list();
		logger.info("ScormLogLMS tables: " + this.scormLogLms.size());
		
		criteria = session.createCriteria(WikiLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.wikiLogLms = criteria.list();
		logger.info("WikiLogLMS tables: " + this.wikiLogLms.size());
		
		criteria = session.createCriteria(QuizUserLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.quizUserLms = criteria.list();
		logger.info("QuizUserLMS tables: " + this.quizUserLms.size());
		

		// hibernate session finish and close
		tx.commit();
		session.close();

	}

	@Override
	@SuppressWarnings("unchecked")
	public void getLMStables(final DBConfigObject dbConf, final long readingfromtimestamp, final long readingtotimestamp, List<Long> courses, List<String> logins) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConf).openSession();
		session.clear();
		final Transaction tx = session.beginTransaction();
		
		//Read Context
		Criteria criteria = session.createCriteria(CourseLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseLms = criteria.list();
		logger.info("CourseLMS tables: " + this.courseLms.size());
		
		criteria = session.createCriteria(UserLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.userLms = criteria.list();
		logger.info("UserLMS tables: " + this.courseLms.size());
		
		criteria = session.createCriteria(RoleLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.roleLms = criteria.list();
		logger.info("RoleLMS tables: " + this.roleLms.size());
		
		criteria = session.createCriteria(AssignmentLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assignmentLms = criteria.list();
		logger.info("AssignmentLMS tables: " + this.assignmentLms.size());
		
		criteria = session.createCriteria(ChatLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.chatLms = criteria.list();
		logger.info("ChatLMS tables: " + this.chatLms.size());
		
		criteria = session.createCriteria(ForumLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.forumLms = criteria.list();
		logger.info("ForumLMS tables: " + this.forumLms.size());
		
		criteria = session.createCriteria(QuizLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.quizLms = criteria.list();
		logger.info("QuizLMS tables: " + this.quizLms.size());
		
		criteria = session.createCriteria(ResourceLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.resourceLms = criteria.list();
		logger.info("ResourceLMS tables: " + this.resourceLms.size());
		
		criteria = session.createCriteria(ScormLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.scormLms = criteria.list();
		logger.info("ScormLMS tables: " + this.scormLms.size());
		
		criteria = session.createCriteria(WikiLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.wikiLms = criteria.list();
		logger.info("WikiLMS tables: " + this.wikiLms.size());
		
		criteria = session.createCriteria(WikiLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.wikiLms = criteria.list();
		logger.info("WikiLMS tables: " + this.wikiLms.size());
		
		criteria = session.createCriteria(CourseAssignmentLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseAssignmentLms = criteria.list();
		logger.info("CourseAssignmentLMS tables: " + this.courseAssignmentLms.size());
		
		criteria = session.createCriteria(CourseChatLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseChatLms = criteria.list();
		logger.info("CourseChatLMS tables: " + this.courseChatLms.size());
		
		criteria = session.createCriteria(CourseForumLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseForumLms = criteria.list();
		logger.info("CourseForumLMS tables: " + this.courseForumLms.size());
		
		criteria = session.createCriteria(CourseQuizLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseQuizLms = criteria.list();
		logger.info("CourseQuizLMS tables: " + this.courseQuizLms.size());
		
		criteria = session.createCriteria(CourseResourceLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseResourceLms = criteria.list();
		logger.info("CourseResourceLMS tables: " + this.courseResourceLms.size());
		
		criteria = session.createCriteria(CourseScormLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseScormLms = criteria.list();
		logger.info("CourseScormLMS tables: " + this.courseScormLms.size());
		
		criteria = session.createCriteria(CourseUserLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseUserLms = criteria.list();
		logger.info("CourseUserLMS tables: " + this.courseUserLms.size());
		
		criteria = session.createCriteria(CourseWikiLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseWikiLms = criteria.list();
		logger.info("CourseWikiLMS tables: " + this.courseWikiLms.size());

		criteria = session.createCriteria(AssignmentLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assignmentLogLms = criteria.list();
		logger.info("AssignmentLogLMS tables: " + this.assignmentLogLms.size());
		
		criteria = session.createCriteria(ChatLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.chatLogLms = criteria.list();
		logger.info("ChatLogLMS tables: " + this.chatLogLms.size());
		
		criteria = session.createCriteria(ForumLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.forumLogLms = criteria.list();
		logger.info("ForumLogLMS tables: " + this.forumLogLms.size());
		
		criteria = session.createCriteria(QuizLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.quizLogLms = criteria.list();
		logger.info("QuizLogLMS tables: " + this.quizLogLms.size());
		
		criteria = session.createCriteria(ResourceLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.resourceLogLms = criteria.list();
		logger.info("ResourceLogLMS tables: " + this.resourceLogLms.size());
		
		criteria = session.createCriteria(ScormLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.scormLogLms = criteria.list();
		logger.info("ScormLogLMS tables: " + this.scormLogLms.size());
		
		criteria = session.createCriteria(WikiLogLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.wikiLogLms = criteria.list();
		logger.info("WikiLogLMS tables: " + this.wikiLogLms.size());
		
		criteria = session.createCriteria(QuizUserLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.quizUserLms = criteria.list();
		logger.info("QuizUserLMS tables: " + this.quizUserLms.size());
		

		// hibernate session finish and close
		tx.commit();
		session.close();
	}

	@Override
	public void clearLMStables() {
		this.assignmentLms.clear();
		this.chatLms.clear();
		this.courseLms.clear();
		this.forumLms.clear();
		this.quizLms.clear();
		this.resourceLms.clear();
		this.scormLms.clear();
		this.wikiLms.clear();		
		this.userLms.clear();
		this.courseAssignmentLms.clear();
		this.courseChatLms.clear();
		this.courseForumLms.clear();
		this.courseQuizLms.clear();
		this.courseResourceLms.clear();
		this.courseScormLms.clear();
		this.courseWikiLms.clear();
		this.roleLms.clear();
		this.quizUserLms.clear();
	}

	// methods for create and fill the mining-table instances

	@Override
	public Map<Long, CourseUser> generateCourseUsers() {

		final HashMap<Long, CourseUser> courseUserMining = new HashMap<Long, CourseUser>();
		
		for(final CourseUserLMS loadedItem : this.courseUserLms)
		{
			CourseUser insert = new CourseUser();
			insert.setId(loadedItem.getId());
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			insert.setUser(loadedItem.getUser(), this.userMining, this.oldUserMining);
			insert.setRole(loadedItem.getRole(), this.roleMining, this.oldRoleMining);
			
			courseUserMining.put(insert.getId(), insert);
		}
		
		return this.courseUserMining;
	}

	@Override
	public Map<Long, Course> generateCourses() {

		final HashMap<Long, Course> courseMining = new HashMap<Long, Course>();
		for (final CourseLMS loadedItem : this.courseLms)
		{
			final Course insert = new Course();

			insert.setId(loadedItem.getId());
			insert.setTitle(loadedItem.getTitle());

			courseMining.put(insert.getId(), insert);
		}
		return courseMining;
	}

	@Override
	public Map<Long, AssessmentLog> generateAssessmentLogs() {

		final HashMap<Long, AssessmentLog> assessmentLogs = new HashMap<Long, AssessmentLog>();
		
		for(QuizLogLMS loadedItem : this.quizLogLms)
		{
			AssessmentLog insert = new AssessmentLog();
			insert.setId(assessmentLogs.size() + 1 + this.assessmentLogMax);
			insert.setAction(loadedItem.getAction());
			insert.setUser(loadedItem.getUser(), this.userMining, this.oldUserMining);
			insert.setLearning(loadedItem.getQuiz(), this.learningObjectMining, this.oldLearningObjectMining);
			insert.setTimestamp(loadedItem.getTimestamp());
			
			assessmentLogs.put(insert.getId(), insert);
		}
		
		for(AssignmentLogLMS loadedItem : this.assignmentLogLms)
		{
			AssessmentLog insert = new AssessmentLog();
			insert.setId(assessmentLogs.size() + 1 + this.assessmentLogMax);
			insert.setAction(loadedItem.getAction());
			insert.setUser(loadedItem.getUser(), this.userMining, this.oldUserMining);
			insert.setLearning(loadedItem.getAssignment(), this.learningObjectMining, this.oldLearningObjectMining);
			insert.setTimestamp(loadedItem.getTimestamp());
			
			assessmentLogs.put(insert.getId(), insert);
		}
		
		for(ScormLogLMS loadedItem : this.scormLogLms)
		{
			AssessmentLog insert = new AssessmentLog();
			insert.setId(assessmentLogs.size() + 1 + this.assessmentLogMax);
			insert.setAction(loadedItem.getAction());
			insert.setUser(loadedItem.getUser(), this.userMining, this.oldUserMining);
			insert.setLearning(loadedItem.getScorm(), this.learningObjectMining, this.oldLearningObjectMining);
			insert.setTimestamp(loadedItem.getTimestamp());
			
			assessmentLogs.put(insert.getId(), insert);
		}
		
		return assessmentLogs;
	}


	/*
	@Override
	public Map<Long, QuizQuestionMining> generateQuizQuestionMining() {

		final HashMap<Long, QuizQuestionMining> quizQuestionMining = new HashMap<Long, QuizQuestionMining>();

		for (final QuizQuestionInstancesLMS loadedItem : this.quizQuestionInstancesLms)
		{
			final QuizQuestionMining insert = new QuizQuestionMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getQuiz()), this.quizMining,
					this.oldQuizMining);
			insert.setQuestion(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getQuestion()),
					this.questionMining, this.oldQuestionMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getQuiz() != null) && (insert.getQuestion() != null))
			{
				quizQuestionMining.put(insert.getQuestion().getId(), insert);
			}
			else
			{
				this.logger.debug("In QuizQuestionMining, quiz not found: " + loadedItem.getQuiz());
			}
		}
		return quizQuestionMining;
	}

	@Override
	public Map<Long, QuestionMining> generateQuestionMining() {

		final HashMap<Long, QuestionMining> questionMining = new HashMap<Long, QuestionMining>();

		for (final QuestionLMS loadedItem : this.questionLms)
		{
			final QuestionMining insert = new QuestionMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setText(loadedItem.getQuestiontext());
			insert.setType(loadedItem.getQtype());
			insert.setTimeCreated(loadedItem.getTimecreated());
			insert.setPlatform(this.connector.getPlatformId());
			insert.setTimeModified(loadedItem.getTimemodified());

			questionMining.put(insert.getId(), insert);
		}
		return questionMining;
	}
	*/

	@Override
	public Map<Long, UserAssessment> generateUserAssessments() {

		final HashMap<Long, UserAssessment> userAssessment = new HashMap<Long, UserAssessment>();
		
		for(QuizUserLMS loadedItem : this.quizUserLms)
		{
			UserAssessment insert = new UserAssessment();
			insert.setId(loadedItem.getId());
			insert.setLearning(Long.valueOf("17" + loadedItem.getQuiz()), learningObjectMining, oldLearningObjectMining);
			insert.setUser(loadedItem.getUser(), userMining, oldUserMining);
			insert.setGrade(loadedItem.getFinalGrade());
			insert.setTimemodified(loadedItem.getTimeModified());
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			
			if(insert.getUser() != null && insert.getCourse() != null && insert.getLearning() != null)
				userAssessment.put(insert.getId(), insert);
		}
		
		return userAssessment;
	}

	@Override
	public Map<Long, Role> generateRoles() {
		// generate role tables
		final HashMap<Long, Role> roleMining = new HashMap<Long, Role>();

		for (final RoleLMS loadedItem : this.roleLms)
		{
			final Role insert = new Role();

			insert.setId(loadedItem.getId());
			insert.setTitle(loadedItem.getName());
			insert.setSortOrder(loadedItem.getSortOrder());
			insert.setType(loadedItem.getType());
			roleMining.put(insert.getId(), insert);
		}
		return roleMining;
	}


	@Override
	public Map<Long, CollaborationLog> generateCollaborativeLogs() {
		final HashMap<Long, CollaborationLog> collaborationLogs = new HashMap<Long, CollaborationLog>();
		
		for(ChatLogLMS loadedItem : this.chatLogLms)
		{
			CollaborationLog insert = new CollaborationLog();
			insert.setId(collaborationLogs.size() + 1 + this.accessLogMax);
			insert.setUser(loadedItem.getUser(), this.userMining, this.oldUserMining);
			insert.setLearning(loadedItem.getChat(), this.learningObjectMining, this.oldLearningObjectMining);
			insert.setTimestamp(loadedItem.getTimestamp());
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			
			collaborationLogs.put(insert.getId(), insert);
		}
		
		for(ForumLogLMS loadedItem : this.forumLogLms)
		{
			CollaborationLog insert = new CollaborationLog();
			insert.setId(collaborationLogs.size() + 1 + this.accessLogMax);
			insert.setUser(loadedItem.getUser(), this.userMining, this.oldUserMining);
			insert.setLearning(loadedItem.getForum(), this.learningObjectMining, this.oldLearningObjectMining);
			insert.setTimestamp(loadedItem.getTimestamp());
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			
			collaborationLogs.put(insert.getId(), insert);
		}
		
		for(WikiLogLMS loadedItem : this.wikiLogLms)
		{
			CollaborationLog insert = new CollaborationLog();
			insert.setId(collaborationLogs.size() + 1 + this.accessLogMax);
			insert.setUser(loadedItem.getUser(), this.userMining, this.oldUserMining);
			insert.setLearning(loadedItem.getWiki(), this.learningObjectMining, this.oldLearningObjectMining);
			insert.setTimestamp(loadedItem.getTimestamp());
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			
			collaborationLogs.put(insert.getId(), insert);
		}
		
		return collaborationLogs;
	}
	
	public Map<String, LearningType> generateLearningTypes(){
		return this.learningTypeMining;
	}
	
	private void addLearningAttribute(LearningObj learningObject, String name, String value)
	{
		Attribute attribute = new Attribute();
		if(!this.attributeMining.containsKey(name))
		{
			
			attribute.setName(name);
			attribute.setId(this.attributeIdMax + 1);
			this.learningAttributeIdMax++;
			this.attributeMining.put(name, attribute);
		}
		else
		{
			attribute = this.attributeMining.get(name);
		}
		LearningAttribute la = new LearningAttribute();
		la.setId(this.learningAttributeIdMax + 1);
		this.learningAttributeIdMax++;
		la.setLearning(learningObject);
		la.setValue(value);
		la.setAttribute(attribute);
		
		this.learningAttributeMining.put(la.getId(), la);
		
	}

	@Override
	public Map<Long, LearningObj> generateLearningObjs() {
		
		Map<Long, LearningObj> learningObjs = new HashMap<Long, LearningObj>();
		
		for (final ResourceLMS loadedItem : this.resourceLms)
		{
			final LearningObj insert = new LearningObj();

			if(!this.learningTypeMining.containsKey(loadedItem.getType()) && !this.oldLearningTypeMining.containsKey(loadedItem.getType()))
			{
				LearningType type = new LearningType();
				type.setType(loadedItem.getType());
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}
			if(loadedItem.getType().toUpperCase().equals("RESOURCE"))
				insert.setId(Long.valueOf("11" + loadedItem.getId()));
			else if(loadedItem.getType().toUpperCase().equals("PAGE"))
				insert.setId(Long.valueOf("13" + loadedItem.getId()));
			else if(loadedItem.getType().toUpperCase().equals("URL"))
				insert.setId(Long.valueOf("12" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setType(loadedItem.getType(), this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Access");

			// Get time of creation

			learningObjs.put(insert.getId(), insert);
		}
		
		final HashMap<Long, LearningObj> amTmp = new HashMap<Long, LearningObj>();

		for (final AssignmentLMS loadedItem : this.assignmentLms)
		{
			final LearningObj insert = new LearningObj();

			if(!this.learningTypeMining.containsKey("Assign") && !this.oldLearningTypeMining.containsKey("Assign"))
			{
				LearningType type = new LearningType();
				type.setType("Assign");
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}

			insert.setId(Long.valueOf("17" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setType("Assign", this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Assessment");
			
			addLearningAttribute(insert, "MaxGrade", ((double)loadedItem.getMaxGrade())+"");

			amTmp.put(insert.getId(), insert);
		}

		learningObjs.putAll(amTmp);
		
		for (final QuizLMS loadedItem : this.quizLms)
		{

			final LearningObj insert = new LearningObj();
			
			if(!this.learningTypeMining.containsKey(loadedItem.getType()) && !this.oldLearningTypeMining.containsKey(loadedItem.getType()))
			{
				LearningType type = new LearningType();
				type.setType(loadedItem.getType());
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}

			insert.setId(Long.valueOf("18" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			//insert.setMaxGrade(loadedItem.getSumgrade());
			insert.setType("Quiz", this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Assessment");
			
			addLearningAttribute(insert, "MaxGrade", ((double)loadedItem.getMaxGrade())+"");

			learningObjs.put(insert.getId(), insert);
		}
		
		for (final ScormLMS loadedItem : this.scormLms)
		{
			final LearningObj insert = new LearningObj();
			
			if(!this.learningTypeMining.containsKey(loadedItem.getType()) && !this.oldLearningTypeMining.containsKey(loadedItem.getType()))
			{
				LearningType type = new LearningType();
				type.setType(loadedItem.getType());
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}

			insert.setId(Long.valueOf("19" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			//insert.setMaxGrade(loadedItem.getMaxgrade());
			insert.setType(loadedItem.getType(), this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Assessment");
			
			addLearningAttribute(insert, "MaxGrade", ((double)loadedItem.getMaxGrade())+"");

			learningObjs.put(insert.getId(), insert);
		}
		
		for (final ChatLMS loadedItem : this.chatLms)
		{
			final LearningObj insert = new LearningObj();

			if(!this.learningTypeMining.containsKey("Chat") && !this.oldLearningTypeMining.containsKey("Chat"))
			{
				LearningType type = new LearningType();
				type.setType("Chat");
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}
			
			insert.setId(Long.valueOf("14" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setType("Chat", this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Collaboration");
			
			learningObjs.put(insert.getId(), insert);
			
		}
		
		for (final ForumLMS loadedItem : this.forumLms)
		{
			final LearningObj insert = new LearningObj();

			if(!this.learningTypeMining.containsKey("Forum") && !this.oldLearningTypeMining.containsKey("Forum"))
			{
				LearningType type = new LearningType();
				type.setType("Forum");
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}
			
			insert.setId(Long.valueOf("15" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setType("Forum", this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Collaboration");

			learningObjs.put(insert.getId(), insert);
		}
		
		for (final WikiLMS loadedItem : this.wikiLms)
		{
			final LearningObj insert = new LearningObj();

			if(!this.learningTypeMining.containsKey("Wiki") && !this.oldLearningTypeMining.containsKey("Wiki"))
			{
				LearningType type = new LearningType();
				type.setType("Wiki");
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}
			
			insert.setId(Long.valueOf("16" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setType("Wiki", this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Collaboration");
			
			learningObjs.put(insert.getId(), insert);
		}
		
		
		
		return learningObjs;
	}

	@Override
	public Map<Long, User> generateUsers() {
		final HashMap<Long, User> userMining = new HashMap<Long, User>();

		for (final UserLMS loadedItem : this.userLms)
		{

			final User insert = new User();

			insert.setId(loadedItem.getId());
			insert.setLogin(loadedItem.getLogin());

			userMining.put(insert.getId(), insert);
		}
		return userMining;
	}

	@Override
	public Map<Long, CourseAttribute> generateCourseAttributes() {
		return this.courseAttributeMining;
	}

	@Override
	public Map<Long, UserAttribute> generateUserAttributes() {
		return this.userAttributeMining;
	}

	@Override
	public Map<Long, LearningAttribute> generateLearningAttributes() {
		return this.learningAttributeMining;
	}

	@Override
	public Map<Long, CourseLearning> generateCourseLearnings() {
		
		Map<Long, CourseLearning> courseLearnings = new HashMap<Long, CourseLearning>();
		
		for(final CourseAssignmentLMS loadedItem : this.courseAssignmentLms)
		{
			CourseLearning insert = new CourseLearning();
			insert.setId(1);
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			insert.setLearning(loadedItem.getAssignment(), this.learningObjectMining, this.oldLearningObjectMining);
			
			courseLearnings.put(insert.getId(), insert);
		}
		for(final CourseChatLMS loadedItem : this.courseChatLms)
		{
			CourseLearning insert = new CourseLearning();
			insert.setId(1);
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			insert.setLearning(loadedItem.getChat(), this.learningObjectMining, this.oldLearningObjectMining);
			
			courseLearnings.put(insert.getId(), insert);
		}
		for(final CourseForumLMS loadedItem : this.courseForumLms)
		{
			CourseLearning insert = new CourseLearning();
			insert.setId(1);
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			insert.setLearning(loadedItem.getForum(), this.learningObjectMining, this.oldLearningObjectMining);
			
			courseLearnings.put(insert.getId(), insert);
		}
		for(final CourseQuizLMS loadedItem : this.courseQuizLms)
		{
			CourseLearning insert = new CourseLearning();
			insert.setId(1);
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			insert.setLearning(loadedItem.getQuiz(), this.learningObjectMining, this.oldLearningObjectMining);
			
			courseLearnings.put(insert.getId(), insert);
		}
		for(final CourseResourceLMS loadedItem : this.courseResourceLms)
		{
			CourseLearning insert = new CourseLearning();
			insert.setId(1);
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			insert.setLearning(loadedItem.getResource(), this.learningObjectMining, this.oldLearningObjectMining);
			
			courseLearnings.put(insert.getId(), insert);
		}
		for(final CourseScormLMS loadedItem : this.courseScormLms)
		{
			CourseLearning insert = new CourseLearning();
			insert.setId(1);
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			insert.setLearning(loadedItem.getScorm(), this.learningObjectMining, this.oldLearningObjectMining);
			
			courseLearnings.put(insert.getId(), insert);
		}
		for(final CourseWikiLMS loadedItem : this.courseWikiLms)
		{
			CourseLearning insert = new CourseLearning();
			insert.setId(1);
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			insert.setLearning(loadedItem.getWiki(), this.learningObjectMining, this.oldLearningObjectMining);
			
			courseLearnings.put(insert.getId(), insert);
		}
		
		return courseLearnings;
	}

	@Override
	public Map<Long, AccessLog> generateAccessLogs() {
		final HashMap<Long, AccessLog> accessLogMining = new HashMap<Long, AccessLog>();
		// A HashMap of list of timestamps. Every key represents one user, the according value is a list of his/her
		// requests times.
		
		for(ResourceLogLMS loadedItem : this.resourceLogLms)
		{
			AccessLog insert = new AccessLog();
			insert.setId(accessLogMining.size() + 1 + this.accessLogMax);
			insert.setUser(loadedItem.getUser(), this.userMining, this.oldUserMining);
			insert.setLearning(loadedItem.getResource(), this.learningObjectMining, this.oldLearningObjectMining);
			insert.setTimestamp(loadedItem.getTimestamp());
			insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
			
			accessLogMining.put(insert.getId(), insert);
		}
		


		
		return accessLogMining;
	}

	@Override
	public Map<String, Attribute> generateAttributes() {
		return this.attributeMining;
	}

}
