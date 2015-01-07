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

	// LMS tables instances lists
	private List<ConfigLMS> configLms = new ArrayList<ConfigLMS>();
	private List<IDMappingLMS> idMapLms = new ArrayList<IDMappingLMS>();
	private List<PlatformLMS> platformLms = new ArrayList<PlatformLMS>();

	// Object-classes
	private List<AssignmentLMS> assignmentLms = new ArrayList<AssignmentLMS>();
	private List<ChatLMS> chatLms = new ArrayList<ChatLMS>();
	private List<CourseLMS> courseLms = new ArrayList<CourseLMS>();
	private List<ForumLMS> forumLms = new ArrayList<ForumLMS>();
	private List<GroupLMS> groupLms = new ArrayList<GroupLMS>();
	private List<QuestionLMS> questionLms = new ArrayList<QuestionLMS>();
	private List<QuizLMS> quizLms = new ArrayList<QuizLMS>();
	private List<ResourceLMS> resourceLms = new ArrayList<ResourceLMS>();
	private List<RoleLMS> roleLms = new ArrayList<RoleLMS>();
	private List<ScormLMS> scormLms = new ArrayList<ScormLMS>();
	private List<UserLMS> userLms = new ArrayList<UserLMS>();
	private List<WikiLMS> wikiLms = new ArrayList<WikiLMS>();
	private List<LevelLMS> levelLms = new ArrayList<LevelLMS>();

	// Association-classes
	
	private List<CourseAssignmentLMS> courseAssignmentLms = new ArrayList<CourseAssignmentLMS>();
	private List<CourseChatLMS> courseChatLms = new ArrayList<CourseChatLMS>();
	private List<CourseForumLMS> courseForumLms = new ArrayList<CourseForumLMS>();
	private List<CourseGroupLMS> courseGroupLms = new ArrayList<CourseGroupLMS>();
	private List<CourseQuizLMS> courseQuizLms = new ArrayList<CourseQuizLMS>();
	private List<CourseResourceLMS> courseResourceLms = new ArrayList<CourseResourceLMS>();
	private List<CourseScormLMS> courseScormLms = new ArrayList<CourseScormLMS>();
	private List<CourseUserLMS> courseUserLms = new ArrayList<CourseUserLMS>();
	private List<CourseWikiLMS> courseWikiLms = new ArrayList<CourseWikiLMS>();
	private List<GroupUserLMS> groupUserLms = new ArrayList<GroupUserLMS>();
	private List<QuizQuestionLMS> quizQuestionLms = new ArrayList<QuizQuestionLMS>();
	private List<QuizUserLMS> quizUserLms = new ArrayList<QuizUserLMS>();
	private List<LevelCourseLMS> levelCourseLms = new ArrayList<LevelCourseLMS>();
	private List<LevelAssociationLMS> levelAssociationLms = new ArrayList<LevelAssociationLMS>();

	// Log-classes
	
	private List<AssignmentLogLMS> assignmentLogLms = new ArrayList<AssignmentLogLMS>();
	private List<ChatLogLMS> chatLogLms = new ArrayList<ChatLogLMS>();
	private List<CourseLogLMS> courseLogLms = new ArrayList<CourseLogLMS>();
	private List<ForumLogLMS> forumLogLms = new ArrayList<ForumLogLMS>();
	private List<ResourceLogLMS> resourceLogLms = new ArrayList<ResourceLogLMS>();
	private List<ScormLogLMS> scormLogLms = new ArrayList<ScormLogLMS>();
	private List<QuestionLogLMS> questionLogLms = new ArrayList<QuestionLogLMS>();
	private List<QuizLogLMS> quizLogLms = new ArrayList<QuizLogLMS>();
	private List<WikiLogLMS> wikiLogLms = new ArrayList<WikiLogLMS>();

	private Map<Long, Long> chatCourse = new HashMap<Long, Long>();
	
	private final Logger logger = Logger.getLogger(this.getClass());

	private final IConnector connector;

	public ExtractAndMapLeMo(final IConnector connector) {
		super(connector);
		this.connector = connector;
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
		final HashMap<Long, ArrayList<AssignGradesLMS>> assignGrades = new HashMap<Long, ArrayList<AssignGradesLMS>>();
		final HashMap<Long, CourseModulesLMS> courseModules = new HashMap<Long, CourseModulesLMS>();
		
		long moduleid = 0;
		for (final ModulesLMS loadedItem : this.modulesLms)
		{
			if (loadedItem.getName().equals("assign"))
			{
				moduleid = loadedItem.getId();
				break;
			}
		}
		
		for(CourseModulesLMS cm : this.courseModulesLms)
		{
			if(cm.getModule() == moduleid)
				courseModules.put(cm.getId(), cm);
		}
		
		for (final AssignGradesLMS assignGrade : this.assignGradesLms)
		{
			if (assignGrades.get(assignGrade.getAssignment()) == null)
			{
				final ArrayList<AssignGradesLMS> a = new ArrayList<AssignGradesLMS>();
				a.add(assignGrade);
				assignGrades.put(assignGrade.getAssignment(), a);
			} else {
				assignGrades.get(assignGrade.getAssignment()).add(assignGrade);
			}

		}

		for (final LogstoreStandardLogLMS loadedItem : this.logstoreLms) {

			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("assign"))
			{
				AssessmentLog insert = new AssessmentLog();
				insert.setId(assessmentLogs.size() + 1 + this.assessmentLogMax);
				insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
				insert.setUser(loadedItem.getUser(), this.userMining, this.oldUserMining);
				insert.setTimestamp(loadedItem.getTimecreated());
				insert.setAction(loadedItem.getAction());
				insert.setLearning(Long.valueOf("17" + loadedItem.getObjectid()), this.learningObjectMining, this.oldLearningObjectMining);
				/*if(courseModules.containsKey(loadedItem.getCmid())	)
				{
					insert.setLearning(Long.valueOf("17" + courseModules.get(loadedItem.getCmid()).getInstance()), learningObjectMining, oldLearningObjectMining);
				}		*/		
			}
			
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("scorm")) {
				final AssessmentLog insert = new AssessmentLog();

				insert.setId(assessmentLogs.size() + 1 + this.assessmentLogMax);

				insert.setCourse(loadedItem.getCourse(),
						this.courseMining, this.oldCourseMining);
				
				insert.setUser(loadedItem.getUser(), this.userMining,
						this.oldUserMining);
				insert.setAction(loadedItem.getAction());

				insert.setTimestamp(loadedItem.getTimecreated());
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}
				insert.setLearning(Long.valueOf("19" + loadedItem.getObjectid()), this.learningObjectMining, this.oldLearningObjectMining);
			/*	if (loadedItem.getInfo().matches("[0-9]+")) {
					insert.setLearning(Long.valueOf("19" + loadedItem.getInfo()),
							this.learningObjectMining, this.oldLearningObjectMining);
				}*/
			}
			
			
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("quiz"))
			{
				final AssessmentLog insert = new AssessmentLog();

				insert.setId(assessmentLogs.size() + 1 + this.assessmentLogMax);
				insert.setCourse(loadedItem.getCourse(),
						this.courseMining, this.oldCourseMining);
				insert.setAction(loadedItem.getAction());
				insert.setUser(loadedItem.getUser(), this.userMining,
						this.oldUserMining);

				insert.setLearning(Long.valueOf("18" + loadedItem.getObjectid()), this.learningObjectMining, this.oldLearningObjectMining);
				insert.setTimestamp(loadedItem.getTimecreated());
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}
				
				if ((insert.getCourse() != null) && (insert.getLearning() != null) && (insert.getUser() != null)) {
					assessmentLogs.put(insert.getId(), insert);
				}

			}
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

		final HashMap<Long, UserAssessment> taskUserMining = new HashMap<Long, UserAssessment>();
		
		for(AssignGradesLMS loadedItem : this.assignGradesLms)
		{
			UserAssessment insert = new UserAssessment();
			insert.setId(loadedItem.getId());
			insert.setLearning(Long.valueOf("17" + loadedItem.getAssignment()), learningObjectMining, oldLearningObjectMining);
			insert.setUser(loadedItem.getUser(), userMining, oldUserMining);
			if(loadedItem.getGrade() != null)
			{
				insert.setGrade(loadedItem.getGrade());
			}
			insert.setTimemodified(loadedItem.getTimemodified());
			
			for(AssignLMS loadedItem2 : this.assignLms)
			{
				if(loadedItem2.getId() == loadedItem.getAssignment())
				{
					insert.setCourse(loadedItem2.getCourse(), courseMining, oldCourseMining);
					break;
				}
			}
			
			if(insert.getUser() != null && insert.getCourse() != null && insert.getLearning() != null)
				taskUserMining.put(insert.getId(), insert);
		}
		
		for (final GradeGradesLMS loadedItem : this.gradeGradesLms)
		{
			final UserAssessment insert = new UserAssessment();

			insert.setId(loadedItem.getId());
			if (loadedItem.getFinalgrade() != null) {
				insert.setGrade(loadedItem.getFinalgrade());
			}
			if (loadedItem.getTimemodified() != null) {
				insert.setTimemodified(loadedItem.getTimemodified());
			}
			insert.setUser(Long.valueOf(loadedItem.getUserid()), this.userMining,
					this.oldUserMining);

			for (final GradeItemsLMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getId() == loadedItem.getItemid()) && (loadedItem2.getIteminstance() != null)) {
					insert.setCourse(loadedItem2.getCourseid(),
							this.courseMining, this.oldCourseMining);
					insert.setLearning(Long.valueOf("18" + loadedItem2.getIteminstance()),
							this.learningObjectMining, this.oldLearningObjectMining);
					if ((insert.getLearning() != null) && (insert.getUser() != null)) {
						taskUserMining.put(insert.getId(), insert);
					}
				}
			}
		}
		return taskUserMining;
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
			insert.setSortOrder(loadedItem.getSortorder());
			if(loadedItem.getArchetype().equals("manager") || loadedItem.getArchetype().equals("coursecreator")) {
				insert.setType(0);
			}
			else if(loadedItem.getArchetype().equals("teacher") || loadedItem.getArchetype().equals("editingteacher")) {
				insert.setType(1);
			}
			else {
				insert.setType(2);
			}
			roleMining.put(insert.getId(), insert);
		}
		return roleMining;
	}


	@Override
	public Map<Long, CollaborationLog> generateCollaborativeLogs() {
		final HashMap<Long, CollaborationLog> collaborationLogs = new HashMap<Long, CollaborationLog>();
		final HashMap<Long, CourseModulesLMS> couMod = new HashMap<Long, CourseModulesLMS>();

		for (final ChatLogLMS loadedItem : this.chatLogLms)
		{
			final CollaborationLog insert = new CollaborationLog();
			
			insert.setId(collaborationLogs.size() + 1 + this.collaborationLogMax);
			insert.setLearning(Long.valueOf("14" + loadedItem.getChat()), this.learningObjectMining,
					this.oldLearningObjectMining);
			insert.setText(TextHelper.replaceString(loadedItem.getMessage(), 255));
			insert.setTimestamp(loadedItem.getTimestamp());
			if(insert.getTimestamp() > maxLog)
			{
				maxLog = insert.getTimestamp();
			}
			if (this.chatCourse.get(insert.getLearning()) != null) {
				insert.setCourse(this.chatCourse.get(insert.getLearning()), this.courseMining, this.oldCourseMining);
			}
			insert.setUser(Long.valueOf(loadedItem.getUser()), this.userMining,
					this.oldUserMining);

			if ((insert.getLearning() != null) && (insert.getUser() != null) && (insert.getCourse() != null)) {
				collaborationLogs.put(insert.getId(), insert);
			}

		}
		
		final HashMap<Long, ForumDiscussionsLMS> discussions = new HashMap<Long, ForumDiscussionsLMS>();
		final HashMap<Long, ForumPostsLMS> posts = new HashMap<Long, ForumPostsLMS>();
		final HashMap<Long, WikiPagesLMS> wikipages = new HashMap<Long, WikiPagesLMS>();
		
		for(WikiPagesLMS wp : this.wikiPagesLms)
		{
			wikipages.put(wp.getId(), wp);
		}
		
		for(ForumDiscussionsLMS dis : this.forumDiscussionsLms)
		{
			discussions.put(dis.getId(), dis);
		}
		
		for(ForumPostsLMS post : this.forumPostsLms)
		{
			posts.put(post.getId(), post);
		}
		
		for (final CourseModulesLMS cm : this.courseModulesLms)
		{
			couMod.put(cm.getId(), cm);
		}
		
		for (final LogstoreStandardLogLMS loadedItem : this.logstoreLms) {
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("forum")) {

				final CollaborationLog insert = new CollaborationLog();

				insert.setId(collaborationLogs.size() + 1 + this.collaborationLogMax);
				insert.setUser(loadedItem.getUser(), this.userMining,
						this.oldUserMining);
				insert.setAction(loadedItem.getAction() + " forum");
				insert.setTimestamp(loadedItem.getTimecreated());
				insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
				insert.setLearning(Long.valueOf("15" + loadedItem.getObjectid()) , this.learningObjectMining, this.oldLearningObjectMining);
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}
				
				if ((insert.getCourse() != null) && (insert.getLearning() != null) && (insert.getUser() != null)) {
					collaborationLogs.put(insert.getId(), insert);
				}
			}
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("forum_discussions")) {

				final CollaborationLog insert = new CollaborationLog();

				insert.setId(collaborationLogs.size() + 1 + this.collaborationLogMax);
				insert.setUser(loadedItem.getUser(), this.userMining,
						this.oldUserMining);
				insert.setAction(loadedItem.getAction() + " discussion");
				insert.setTimestamp(loadedItem.getTimecreated());
				insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}
				if (discussions.containsKey(Long.valueOf(loadedItem.getObjectid())))
				{
					Long f = discussions.get(Long.valueOf(loadedItem.getObjectid())).getForum();
					insert.setLearning(Long.valueOf("15" + f),
							this.learningObjectMining, this.oldLearningObjectMining);
					insert.setText(TextHelper.replaceString("Subject: " + discussions.get(Long.valueOf(loadedItem.getObjectid())).getName(),255));
					insert.setReferrer(insert.getLearning().getId(), collaborationLogs);
				}
			
				if ((insert.getCourse() != null) && (insert.getLearning() != null) && (insert.getUser() != null)) {
					collaborationLogs.put(insert.getId(), insert);
				}
			}
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("forum_posts")) {
				final CollaborationLog insert = new CollaborationLog();

				insert.setId(collaborationLogs.size() + 1 + this.collaborationLogMax);
				insert.setUser(loadedItem.getUser(), this.userMining,
						this.oldUserMining);
				insert.setAction(loadedItem.getAction() + " post");
				insert.setTimestamp(loadedItem.getTimecreated());
				insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}
				ForumPostsLMS p = posts.get(Long.valueOf(loadedItem.getObjectid()));
				if(p != null && discussions.containsKey(p.getDiscussion()))
				{
					insert.setLearning(Long.valueOf("15" + discussions.get(p.getDiscussion()).getForum()),
							this.learningObjectMining, this.oldLearningObjectMining );
					insert.setText(TextHelper.replaceString(p.getMessage(),255));
				}	
				if(insert.getText() == null)
					for (final ForumPostsLMS loadedItem2 : this.forumPostsLms)
					{
						if ((loadedItem2.getUserid() == loadedItem.getUser())
								&& ((loadedItem2.getCreated() == loadedItem.getTimecreated()) || (loadedItem2.getModified() == loadedItem
										.getTimecreated()))) {
							insert.setText(TextHelper.replaceString(loadedItem2.getMessage(),255));
							break;
						}
					}
				
				if ((insert.getCourse() != null) && (insert.getLearning() != null) && (insert.getUser() != null)) {
					collaborationLogs.put(insert.getId(), insert);
				}
			}
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("forum_subscriptions")) {
				final CollaborationLog insert = new CollaborationLog();

				insert.setId(collaborationLogs.size() + 1 + this.collaborationLogMax);
				insert.setUser(loadedItem.getUser(), this.userMining,
						this.oldUserMining);
				insert.setAction(loadedItem.getAction() + " subscription");
				insert.setTimestamp(loadedItem.getTimecreated());
				insert.setCourse(loadedItem.getCourse(), this.courseMining, this.oldCourseMining);
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}
				insert.setLearning(Long.valueOf("15" + loadedItem.getObjectid()),
							this.learningObjectMining, this.oldLearningObjectMining );
				
				if ((insert.getCourse() != null) && (insert.getLearning() != null) && (insert.getUser() != null)) {
					collaborationLogs.put(insert.getId(), insert);
				}
			}			
			
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("wiki"))
			{
				final CollaborationLog insert = new CollaborationLog();

				insert.setId(collaborationLogs.size() + 1 + this.collaborationLogMax);
				insert.setLearning(Long.valueOf("16" + loadedItem.getObjectid()), this.learningObjectMining, this.oldLearningObjectMining);
				
				insert.setUser(loadedItem.getUser(), this.userMining,
						this.oldUserMining);
				insert.setCourse(loadedItem.getCourse(),
						this.courseMining, this.oldCourseMining);
				insert.setTimestamp(loadedItem.getTimecreated());
				insert.setAction(loadedItem.getAction() + " wiki");
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}

				if ((insert.getUser() != null) && (insert.getCourse() != null) && (insert.getLearning() != null)) {
					collaborationLogs.put(insert.getId(), insert);
				}
			}
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("wiki_pages"))
			{
				final CollaborationLog insert = new CollaborationLog();

				insert.setId(collaborationLogs.size() + 1 + this.collaborationLogMax);
				if(wikipages.get(loadedItem.getObjectid()) != null)
				{
					insert.setText(wikipages.get(loadedItem.getObjectid()).getTitle());
					insert.setLearning(Long.valueOf("16" + wikipages.get(loadedItem.getObjectid()).getSubwikiid()), this.learningObjectMining, this.oldLearningObjectMining);
				}
				insert.setUser(loadedItem.getUser(), this.userMining,
						this.oldUserMining);
				insert.setCourse(loadedItem.getCourse(),
						this.courseMining, this.oldCourseMining);
				insert.setTimestamp(loadedItem.getTimecreated());
				insert.setAction(loadedItem.getAction() + " wiki page");
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}
				

				if ((insert.getUser() != null) && (insert.getCourse() != null) && (insert.getLearning() != null)) {
					collaborationLogs.put(insert.getId(), insert);
				}
			}
		}
		
		return collaborationLogs;
	}
	
	public Map<String, LearningType> generateLearningTypes(){
		return this.learningTypeMining;
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

			if(!this.learningTypeMining.containsKey(loadedItem.getType()) && !this.oldLearningTypeMining.containsKey(loadedItem.getType()))
			{
				LearningType type = new LearningType();
				type.setType(loadedItem.getType());
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}

			insert.setId(Long.valueOf("17" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setType(loadedItem.getType(), this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Assessment");

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
			insert.setLogin(Encoder.createMD5(loadedItem.getUsername()));

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
		
		for (final AssignLMS loadedItem : this.assignLms)
		{
			final CourseLearning insert = new CourseLearning();

			insert.setId(Long.valueOf("17" + loadedItem.getId()));
			insert.setCourse(loadedItem.getCourse(), this.courseMining,
					this.oldCourseMining);
			if (insert.getCourse() == null) {
				this.logger.debug("course not found for course-assignment: " + loadedItem.getId() + " and course: "
						+ loadedItem.getCourse());
			}
			insert.setLearning(Long.valueOf("17" + loadedItem.getId()),
					this.learningObjectMining, this.oldLearningObjectMining);
			if ((insert.getCourse() != null) && (insert.getLearning() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for (final ScormLMS loadedItem : this.scormLms)
		{
			final CourseLearning insert = new CourseLearning();

			insert.setId(Long.valueOf("19" + loadedItem.getId()));
			insert.setCourse(loadedItem.getCourse(), this.courseMining,
					this.oldCourseMining);
			insert.setLearning(Long.valueOf("19" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getCourse() != null) && (insert.getLearning() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}

		}
		
		for (final QuizLMS loadedItem : this.quizLms)
		{
			final CourseLearning insert = new CourseLearning();

			insert.setId(Long.valueOf("18" + loadedItem.getId()));
			insert.setCourse(loadedItem.getCourse(), this.courseMining,
					this.oldCourseMining);
			insert.setLearning(Long.valueOf("18" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getCourse() != null) && (insert.getLearning() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for (final Entry<Long, Long> loadedItem : this.chatCourse.entrySet())
		{
			final CourseLearning insert = new CourseLearning();

			insert.setId(loadedItem.getKey());
			insert.setCourse(loadedItem.getValue(), this.courseMining, this.oldCourseMining);	
			insert.setLearning(loadedItem.getKey(),
					this.learningObjectMining, this.learningObjectMining);
			if ((insert.getCourse() != null) && (insert.getLearning() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for (final ForumLMS loadedItem : this.forumLms)
		{
			final CourseLearning insert = new CourseLearning();

			insert.setId(Long.valueOf("15" + loadedItem.getId()));
			insert.setCourse(loadedItem.getCourse(), this.courseMining,
					this.oldCourseMining);
			insert.setLearning(Long.valueOf("15" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getCourse() != null) && (insert.getLearning() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for (final WikiLMS loadedItem : this.wikiLms)
		{
			final CourseLearning insert = new CourseLearning();

			insert.setId(Long.valueOf("16" + loadedItem.getId()));
			insert.setCourse(loadedItem.getCourse(), this.courseMining,
					this.oldCourseMining);
			insert.setLearning(Long.valueOf("16" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getCourse() != null) && (insert.getLearning() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}

		for (final ResourceLMS loadedItem : this.resourceLms)
		{
			final CourseLearning insert = new CourseLearning();

			insert.setId(Long.valueOf("11" + loadedItem.getId()));
			insert.setCourse(loadedItem.getCourse(), this.courseMining,
					this.oldCourseMining);
			insert.setLearning(Long.valueOf("11" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getCourse() != null) && (insert.getLearning() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for (final UrlLMS loadedItem : this.urlLms)
		{
			final CourseLearning insert = new CourseLearning();

			insert.setId(Long.valueOf("12" + loadedItem.getId()));
			insert.setCourse(loadedItem.getCourse(), this.courseMining,
					this.oldCourseMining);
			insert.setLearning(Long.valueOf("12" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getCourse() != null) && (insert.getLearning() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for (final PageLMS loadedItem : this.pageLms)
		{
			final CourseLearning insert = new CourseLearning();

			insert.setId(Long.valueOf("13" + loadedItem.getId()));
			insert.setCourse(loadedItem.getCourse(), this.courseMining,
					this.oldCourseMining);
			insert.setLearning(Long.valueOf("13" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getCourse() != null) && (insert.getLearning() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		return courseLearnings;
	}

	@Override
	public Map<Long, AccessLog> generateAccessLogs() {
		final HashMap<Long, AccessLog> viewLogMining = new HashMap<Long, AccessLog>();
		// A HashMap of list of timestamps. Every key represents one user, the according value is a list of his/her
		// requests times.

		for (final LogstoreStandardLogLMS loadedItem : this.logstoreLms)
		{
			final AccessLog insert = new AccessLog();

			insert.setId(viewLogMining.size() + 1 + this.accessLogMax);

			insert.setUser(loadedItem.getUser(), this.userMining,
					this.oldUserMining);
			insert.setCourse(loadedItem.getCourse(),
					this.courseMining, this.oldCourseMining);
			
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("resource")) {

				/*if (loadedItem.getInfo().matches("[0-9]+")) {
					insert.setLearning(Long.valueOf("11" + loadedItem.getInfo()),
							this.learningObjectMining, this.oldLearningObjectMining);
				}*/
				insert.setLearning(Long.valueOf("11" + loadedItem.getObjectid()),
						this.learningObjectMining, this.oldLearningObjectMining);
				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTimecreated());
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}
				
				if ((insert.getCourse() != null) && (insert.getLearning() != null) && (insert.getUser() != null)) {
					viewLogMining.put(insert.getId(), insert);
				}

			}
			
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("url")) {
				


				/*if (loadedItem.getInfo().matches("[0-9]+")) {
					insert.setLearning(Long.valueOf("12" + loadedItem.getInfo()),
							this.learningObjectMining, this.oldLearningObjectMining);
				}*/
				insert.setLearning(Long.valueOf("12" + loadedItem.getObjectid()),
						this.learningObjectMining, this.oldLearningObjectMining);
				insert.setTimestamp(loadedItem.getTimecreated());
				insert.setAction(loadedItem.getAction());
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}
				/*
				if ((insert.getLearning() == null) && !(loadedItem.getAction().equals("view all"))) {
					this.logger.debug("In ResourceLogMining, resource not found for log: " + loadedItem.getId()
							+ " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo()
							+ " and action: " + loadedItem.getAction());
				}*/
				if ((insert.getCourse() != null) && (insert.getLearning() != null) && (insert.getUser() != null)) {
					viewLogMining.put(insert.getId(), insert);
				}

			}
			
			if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("page")) {
				
				/*if (loadedItem.getInfo().matches("[0-9]+")) {
					insert.setLearning(Long.valueOf("13" + loadedItem.getInfo()),
							this.learningObjectMining, this.oldLearningObjectMining);
				}*/
				insert.setLearning(Long.valueOf("13" + loadedItem.getObjectid()),
						this.learningObjectMining, this.oldLearningObjectMining);
				insert.setTimestamp(loadedItem.getTimecreated());
				insert.setAction(loadedItem.getAction());
				if(insert.getTimestamp() > maxLog)
				{
					maxLog = insert.getTimestamp();
				}
				/*
				if ((insert.getLearning() == null) && !(loadedItem.getAction().equals("view all"))) {
					this.logger.debug("In ResourceLogMining, resource not found for log: " + loadedItem.getId()
							+ " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo()
							+ " and action: " + loadedItem.getAction());
				}*/
				if ((insert.getCourse() != null) && (insert.getLearning() != null) && (insert.getUser() != null)) {
					viewLogMining.put(insert.getId(), insert);
				}

			}
		}
		return viewLogMining;
	}

	@Override
	public Map<String, Attribute> generateAttributes() {
		return this.attributeMining;
	}

}
