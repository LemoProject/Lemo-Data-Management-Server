/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/ExtractAndMapMoodle.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/ExtractAndMapMoodle.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7;


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
import de.lemo.dms.connectors.moodle_2_7.mapping.AssignGradesLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.AssignLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ChatLogLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ChatLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ContextLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.CourseCategoriesLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.CourseLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.CourseModulesLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.EnrolLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ForumLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ForumDiscussionsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ForumPostsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.GradeGradesLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.GradeItemsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.GroupsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.GroupsMembersLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.LogstoreStandardLogLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ModulesLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.PageLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.QuizLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.QuizAttemptsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.QuizGradesLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ResourceLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.UrlLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.RoleLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.RoleAssignmentsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ScormLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.UserEnrolmentsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.UserLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.WikiLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.WikiPagesLMS;
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
public class ExtractAndMapMoodle extends ExtractAndMap {
	// Versionsnummer in Namen einfügen

	// LMS tables instances lists
	/** The log_lms. */
	private List<LogstoreStandardLogLMS> logstoreLms;
	private List<AssignGradesLMS> assignGradesLms;
	private List<ResourceLMS> resourceLms;
	private List<UrlLMS> urlLms;
	private List<PageLMS> pageLms;
	private List<CourseLMS> courseLms;
	private List<ForumLMS> forumLms;
	private List<WikiLMS> wikiLms;
	private List<WikiPagesLMS> wikiPagesLms;
	private List<UserLMS> userLms;
	private List<QuizLMS> quizLms;
	private List<GroupsLMS> groupLms;
	private List<GroupsMembersLMS> groupMembersLms;
	private List<ForumPostsLMS> forumPostsLms;
	private List<RoleLMS> roleLms;
	private List<ContextLMS> contextLms;
	private List<RoleAssignmentsLMS> roleAssignmentsLms;
	private List<QuizGradesLMS> quizGradesLms;
	private List<ForumDiscussionsLMS> forumDiscussionsLms;
	private List<ScormLMS> scormLms;
	private List<GradeGradesLMS> gradeGradesLms;
	private List<GradeItemsLMS> gradeItemsLms;
	private List<ChatLMS> chatLms;
	private List<ChatLogLMS> chatLogLms;
	private List<CourseCategoriesLMS> courseCategoriesLms;
	private List<AssignLMS> assignLms;
	private List<EnrolLMS> enrolLms;
	private List<UserEnrolmentsLMS> userEnrolmentsLms;
	private List<ModulesLMS> modulesLms;
	private List<CourseModulesLMS> courseModulesLms;
	private List<QuizAttemptsLMS> quizAttemptsLms;

	private Map<Long, Long> chatCourse = new HashMap<Long, Long>();
	
	private final Logger logger = Logger.getLogger(this.getClass());

	private final IConnector connector;

	public ExtractAndMapMoodle(final IConnector connector) {
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
		
		boolean hasCR = false;
		if(courses != null && courses.size() > 0)
			hasCR = true; 
		else
			courses = new ArrayList<Long>();
		
		boolean empty = false;
		
		//Read Context
		Criteria criteria = session.createCriteria(ContextLMS.class, "obj");
		List<Long> contextLevels = new ArrayList<Long>();
		contextLevels.add(40L);
		contextLevels.add(50L);
		
		criteria.add(Restrictions.in("obj.contextlevel", contextLevels));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.contextLms = criteria.list();
		logger.info("ContextLMS tables: " + this.contextLms.size());
		
		if(logins != null && !logins.isEmpty())
		{
			List<String> archetypes = new ArrayList<String>();
			List<Long> roleIds = new ArrayList<Long>();
			List<String> userIds = new ArrayList<String>();
			
			archetypes.add("manager");
			archetypes.add("coursecreator");
			archetypes.add("teacher");
			archetypes.add("editingteacher");
			
			criteria = session.createCriteria(RoleLMS.class, "obj");
			criteria.add(Restrictions.in("obj.archetype", archetypes));
			for(RoleLMS role : (List<RoleLMS>)criteria.list())
				roleIds.add(role.getId());
			
			criteria = session.createCriteria(UserLMS.class, "obj");
			criteria.add(Restrictions.in("obj.username", logins));
			for(UserLMS user : (List<UserLMS>)criteria.list())
				userIds.add(user.getId()+"");
			
			criteria = session.createCriteria(RoleAssignmentsLMS.class, "obj");
			criteria.add(Restrictions.in("obj.userid", userIds));
			criteria.add(Restrictions.in("obj.roleid", roleIds));
			for(ContextLMS c : this.contextLms)
			{
				for(RoleAssignmentsLMS ra : (List<RoleAssignmentsLMS>)criteria.list())
				{
					if(c.getContextlevel() == 50 && c.getId() == ra.getContextid())
					{
						courses.add(c.getInstanceid());
						hasCR = true;
					}
				}
			}			
		}
		
		// reading the LMS Database, create tables as lists of instances of the DB-table classes
		criteria = session.createCriteria(AssignLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.assignLms = criteria.list();
		logger.info("AssignLMS tables: " + this.assignLms.size());
		
		//Read RoleAssignments
		criteria = session.createCriteria(RoleAssignmentsLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
			for(ContextLMS c : this.contextLms)
			{
				if(c.getContextlevel() == 50 && courses.contains(c.getInstanceid()))
					ids.add(c.getId());				
			}
			if(!(empty = ids.isEmpty()))
				criteria.add(Restrictions.in("obj.contextid", ids));
		}
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.roleAssignmentsLms = criteria.list();
		else
			this.roleAssignmentsLms = new ArrayList<RoleAssignmentsLMS>();
		logger.info("RoleAssignmentsLMS tables: " + this.roleAssignmentsLms.size());
		
		
		//Read Assign
		criteria = session.createCriteria(AssignGradesLMS.class, "obj");
		if(hasCR)
		{
			List<Long> tmp = new ArrayList<Long>();
			for(AssignLMS assign : assignLms)
			{
				tmp.add(assign.getId());
			}
			if(!(empty = tmp.isEmpty()))
				criteria.add(Restrictions.in("obj.assignment", tmp));
		}
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.assignGradesLms = criteria.list();
		else
			this.assignGradesLms = new ArrayList<AssignGradesLMS>();
		logger.info("AssignGradesLMS tables: " + this.assignGradesLms.size());

		//Read Enrol
		criteria = session.createCriteria(EnrolLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.courseid", courses));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.enrolLms = criteria.list();
		logger.info("EnrolLMS tables: " + this.enrolLms.size());

		//Read Modules
		criteria = session.createCriteria(ModulesLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.modulesLms = criteria.list();
		logger.info("ModulesLMS tables: " + this.modulesLms.size());

		//Read UserEnrolments
		criteria = session.createCriteria(UserEnrolmentsLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(EnrolLMS e : this.enrolLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.enrolid", ids));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.userEnrolmentsLms = criteria.list();
		else
			this.userEnrolmentsLms = new ArrayList<UserEnrolmentsLMS>();
		logger.info("UserEnrolmentsLMS tables: " + this.userEnrolmentsLms.size());

		//Read CourseModules
		criteria = session.createCriteria(CourseModulesLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseModulesLms = criteria.list();
		logger.info("CourseModulesLMS tables: " + this.courseModulesLms.size());

		//Read Log
		criteria = session.createCriteria(LogstoreStandardLogLMS.class, "obj");
		if(hasCR){
			criteria.add(Restrictions.in("obj.course", courses));
		}
		criteria.add(Restrictions.gt("obj.timecreated", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.logstoreLms = criteria.list();
		logger.info("LogLMS tables: " + this.logstoreLms.size());
		
		
		//Read Resource
		criteria = session.createCriteria(ResourceLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.resourceLms = criteria.list();
		logger.info("ResourceLMS tables: " + this.resourceLms.size());
		
		//Read Urls
		criteria = session.createCriteria(UrlLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.urlLms = criteria.list();
		logger.info("UrlLMS tables: " + this.urlLms.size());
		
		//Read Pages
		criteria = session.createCriteria(PageLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.pageLms = criteria.list();
		logger.info("PageLMS tables: " + this.pageLms.size());

		
		//Read Quiz
		criteria = session.createCriteria(QuizLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.quizLms = criteria.list();
		logger.info("QuizLMS tables: " + this.quizLms.size());
		
		//Read QuizAttempts
		criteria = session.createCriteria(QuizAttemptsLMS.class, "obj");
		if(hasCR)
			if(hasCR)
			{
				ArrayList<Long> ids = new ArrayList<Long>();
	 			for(QuizLMS e : this.quizLms)
	 				ids.add(e.getId());
	 			if(!(empty = ids.isEmpty()))
	 				criteria.add(Restrictions.in("obj.quiz", ids));
			}
		
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.quizAttemptsLms = criteria.list();
		else
			this.quizAttemptsLms = new ArrayList<QuizAttemptsLMS>();
		logger.info("QuizAttemptsLMS tables: " + this.quizAttemptsLms.size());

		
		//Read Chats
		criteria = session.createCriteria(ChatLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.chatLms = criteria.list();
		logger.info("ChatLMS tables: " + this.chatLms.size());
		

		//Read ChatLog
		criteria = session.createCriteria(ChatLogLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(ChatLMS e : this.chatLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.chat", ids));
		}
		criteria.add(Restrictions.gt("obj.timestamp", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.chatLogLms = criteria.list();
		else
			this.chatLogLms = new ArrayList<ChatLogLMS>();
		logger.info("ChatLogLMS tables: " + this.chatLogLms.size());

		
		criteria = session.createCriteria(CourseCategoriesLMS.class, "obj");
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseCategoriesLms = criteria.list();
		logger.info("CourseCategoriesLMS tables: " + this.courseCategoriesLms.size());

		criteria = session.createCriteria(CourseLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.id", courses));
		
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseLms = criteria.list();
		logger.info("CourseLMS tables: " + this.courseLms.size());

		final Query forumPosts;
		if(!hasCR)
		{
			forumPosts= session
				.createQuery("from ForumPostsLMS x where x.modified>=:readingtimestamp order by x.id asc");
			forumPosts.setParameter("readingtimestamp", readingfromtimestamp);
			this.forumPostsLms = forumPosts.list();
		}
		else
		{
			String courseClause ="(";
			for(int i = 0; i < courses.size(); i++)
			{
				courseClause += courses.get(i);
				if(i < courses.size() - 1)
					courseClause += ",";
				else
					courseClause += ")";
			}
			forumPosts = session.createSQLQuery("SELECT posts.id,posts.userid,posts.created,posts.modified,posts.subject,posts.message,posts.discussion from mdl_forum_posts as posts JOIN mdl_logstore_standard_log as logs ON posts.userid = logs.userid Where logs.courseid in "+ courseClause +" and (posts.created = logs.timecreated or posts.modified = logs.timecreated) AND posts.modified>=:readingtimestamp");
			forumPosts.setParameter("readingtimestamp", readingfromtimestamp);
			List<Object[]> tmpl = forumPosts.list();
			this.forumPostsLms = new ArrayList<ForumPostsLMS>();
			for(Object[] obj : tmpl)
			{
				ForumPostsLMS p = new ForumPostsLMS();
				if(obj[0].getClass().equals(BigInteger.class))
				{
					p.setId(((BigInteger) obj[0]).longValue());
				}
				else
				{
					p.setId(((Integer) obj[0]).longValue());
				}
				if(obj[0].getClass().equals(BigInteger.class))
				{
					p.setUserid(((BigInteger) obj[1]).longValue());
				}
				else
				{
					p.setUserid(((Integer) obj[1]).longValue());
				}
				if(obj[0].getClass().equals(BigInteger.class))
				{
					p.setCreated(((BigInteger) obj[2]).longValue());
				}
				else
				{
					p.setCreated(((Integer) obj[2]).longValue());
				}
				if(obj[0].getClass().equals(BigInteger.class))
				{
					p.setModified(((BigInteger) obj[3]).longValue());
				}
				else
				{
					p.setModified(((Integer) obj[3]).longValue());
				}
				p.setSubject((String) obj[4]);
				p.setMessage((String) obj[5]);
				if(obj[0].getClass().equals(BigInteger.class))
				{
					p.setDiscussion(((BigInteger) obj[6]).longValue());
				}
				else
				{
					p.setDiscussion(((Integer) obj[6]).longValue());
				}
				this.forumPostsLms.add(p);
			
			}
		}
		logger.info("ForumPostsLMS tables: " + this.forumPostsLms.size());

		criteria = session.createCriteria(ForumLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.forumLms = criteria.list();
		logger.info("ForumLMS tables: " + this.forumLms.size());

		criteria = session.createCriteria(GroupsLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.courseid", courses));
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.groupLms = criteria.list();
		logger.info("GroupsLMS tables: " + this.groupLms.size());

		criteria = session.createCriteria(WikiLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.wikiLms = criteria.list();
		logger.info("WikiLMS tables: " + this.wikiLms.size());
		
		criteria = session.createCriteria(WikiPagesLMS.class, "obj");
		if(hasCR && !this.wikiLms.isEmpty())
		{
			Set<Long> wikiids = new HashSet<Long>();
			for(WikiLMS wiki : this.wikiLms)
			{
				wikiids.add(wiki.getId());
			}
			criteria.add(Restrictions.in("obj.subwikiid", wikiids));
		}
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.wikiPagesLms = criteria.list();
		logger.info("WikiPagesLMS tables: " + this.wikiPagesLms.size());

		
		criteria = session.createCriteria(GroupsMembersLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(GroupsLMS e : this.groupLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.groupid", ids));
		}
		//criteria.add(Restrictions.gt("obj.timeadded", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.groupMembersLms = criteria.list();
		else
			this.groupMembersLms = new ArrayList<GroupsMembersLMS>();
		logger.info("GroupsMembersLMS tables: " + this.groupMembersLms.size());
		
/*		criteria = session.createCriteria(QuestionStatesLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(QuizQuestionInstancesLMS e : this.quizQuestionInstancesLms)
 				ids.add(e.getQuestion());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.question", ids));
		}
		criteria.add(Restrictions.gt("obj.timestamp", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.questionStatesLms = criteria.list();
		else
			this.questionStatesLms = new ArrayList<QuestionStatesLMS>();
		logger.info("QuestionStatesLMS tables: " + this.questionStatesLms.size());
		*/

/*
		criteria = session.createCriteria(QuestionLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(QuizQuestionInstancesLMS e : this.quizQuestionInstancesLms)
 				ids.add(e.getQuestion());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.id", ids));
		}
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.questionLms = criteria.list();
		else
			this.questionLms = new ArrayList<QuestionLMS>();
		logger.info("QuestionLMS tables: " + this.questionLms.size());
		
*/

		criteria = session.createCriteria(UserLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(RoleAssignmentsLMS e : this.roleAssignmentsLms)
 				ids.add(Long.valueOf(e.getUserid()));
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.id", ids));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.userLms = criteria.list();
		else
			this.userLms = new ArrayList<UserLMS>();
		logger.info("UserLMS tables: " + this.userLms.size());

		criteria = session.createCriteria(RoleLMS.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		this.roleLms = criteria.list();
		logger.info("RoleLMS tables: " + this.roleLms.size());

		criteria = session.createCriteria(QuizGradesLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(QuizLMS e : this.quizLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.quiz", ids));
		}
		
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.quizGradesLms = criteria.list();
		else
			this.quizGradesLms = new ArrayList<QuizGradesLMS>();
		logger.info("QuizGradesLMS tables: " + this.quizGradesLms.size());

		criteria = session.createCriteria(ForumDiscussionsLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(ForumLMS e : this.forumLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.forum", ids));
		}
		
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.forumDiscussionsLms = criteria.list();
		else
			this.forumDiscussionsLms = new ArrayList<ForumDiscussionsLMS>();
		logger.info("ForumDiscussionsLMS tables: " + this.forumDiscussionsLms.size());

		criteria = session.createCriteria(ScormLMS.class, "obj");
		if(hasCR)
		{
			criteria.add(Restrictions.in("obj.course", courses));
		}
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.scormLms = criteria.list();
		logger.info("ScormLMS tables: " + this.scormLms.size());

		criteria = session.createCriteria(GradeItemsLMS.class, "obj");
		if(hasCR)
		{
			criteria.add(Restrictions.in("obj.courseid", courses));
		}
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.gradeItemsLms = criteria.list();
		logger.info("GradeItemsLMS tables: " + this.gradeItemsLms.size());
		
		criteria = session.createCriteria(GradeGradesLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(GradeItemsLMS e : this.gradeItemsLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.itemid", ids));
		}
		
		//criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.gradeGradesLms = criteria.list();
		else
			this.gradeGradesLms = new ArrayList<GradeGradesLMS>();
		logger.info("GradeGradesLMS tables: " + this.gradeGradesLms.size());

		// hibernate session finish and close
		tx.commit();
		session.close();

	}

	@Override
	@SuppressWarnings("unchecked")
	public void getLMStables(final DBConfigObject dbConf, final long readingfromtimestamp, final long readingtotimestamp, List<Long> courses, List<String> logins) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConf).openSession();
		// Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19",
		session.clear();
		final Transaction tx = session.beginTransaction();

		// reading the LMS Database, create tables as lists of instances of the DB-table classes
		Criteria criteria; 
		boolean hasCR = false;
		if(courses != null && courses.size() > 0)
			hasCR = true; 
		
		boolean empty = false;
		
		if (this.userLms == null) {
			
			//Read Context
			criteria = session.createCriteria(ContextLMS.class, "obj");
			List<Long> contextLevels = new ArrayList<Long>();
			contextLevels.add(40L);
			contextLevels.add(50L);
			
			criteria.add(Restrictions.in("obj.contextlevel", contextLevels));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.contextLms = criteria.list();
			logger.info("ContextLMS tables: " + this.contextLms.size());
			
			if(logins != null && !logins.isEmpty())
			{
				List<String> archetypes = new ArrayList<String>();
				List<Long> roleIds = new ArrayList<Long>();
				List<String> userIds = new ArrayList<String>();
				
				archetypes.add("manager");
				archetypes.add("coursecreator");
				archetypes.add("teacher");
				archetypes.add("editingteacher");
				
				criteria = session.createCriteria(RoleLMS.class, "obj");
				criteria.add(Restrictions.in("obj.archetype", archetypes));
				for(RoleLMS role : (List<RoleLMS>)criteria.list())
					roleIds.add(role.getId());
				
				criteria = session.createCriteria(UserLMS.class, "obj");
				criteria.add(Restrictions.in("obj.username", logins));
				for(UserLMS user : (List<UserLMS>)criteria.list())
					userIds.add(user.getId()+"");
				
				criteria = session.createCriteria(RoleAssignmentsLMS.class, "obj");
				criteria.add(Restrictions.in("obj.userid", userIds));
				criteria.add(Restrictions.in("obj.roleid", roleIds));
				for(ContextLMS c : this.contextLms)
				{
					for(RoleAssignmentsLMS ra : (List<RoleAssignmentsLMS>)criteria.list())
					{
						if(c.getContextlevel() == 50 && c.getId() == ra.getContextid())
						{
							courses.add(c.getInstanceid());
							hasCR = true;
						}
					}
				}
				
				
			}

			criteria = session.createCriteria(AssignLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.assignLms = criteria.list();
			logger.info("AssignLMS tables: " + this.assignLms.size());

			criteria = session.createCriteria(EnrolLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.courseid", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.enrolLms = criteria.list();
			logger.info("EnrolLMS tables: " + this.enrolLms.size());

			criteria = session.createCriteria(ModulesLMS.class, "obj");
			criteria.addOrder(Property.forName("obj.id").asc());
			this.modulesLms = criteria.list();
			logger.info("ModulesLMS tables: " + this.modulesLms.size());

			criteria = session.createCriteria(UserEnrolmentsLMS.class, "obj");
			if(hasCR)
			{
				ArrayList<Long> ids = new ArrayList<Long>();
	 			for(EnrolLMS e : this.enrolLms)
	 				ids.add(e.getId());
	 			if(!(empty = ids.isEmpty()))
	 				criteria.add(Restrictions.in("obj.enrolid", ids));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.userEnrolmentsLms = criteria.list();
			else
				this.userEnrolmentsLms = new ArrayList<UserEnrolmentsLMS>();
			logger.info("UserEnrolmentsLMS tables: " + this.userEnrolmentsLms.size());

			criteria = session.createCriteria(CourseModulesLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.courseModulesLms = criteria.list();
			logger.info("CourseModulesLMS tables: " + this.courseModulesLms.size());

			criteria = session.createCriteria(ResourceLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.resourceLms = criteria.list();
			logger.info("ResourceLMS tables: " + this.resourceLms.size());
			
			
			//Read Urls
			criteria = session.createCriteria(UrlLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			
			criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.urlLms = criteria.list();
			logger.info("UrlLMS tables: " + this.urlLms.size());
			
			//Read Pages
			criteria = session.createCriteria(PageLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			
			criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.pageLms = criteria.list();
			logger.info("UrlLMS tables: " + this.pageLms.size());

			criteria = session.createCriteria(CourseLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.id", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.courseLms = criteria.list();
			logger.info("CourseLMS tables: " + this.courseLms.size());

			criteria = session.createCriteria(ChatLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.chatLms = criteria.list();
			logger.info("ChatLMS tables: " + this.chatLms.size());

			criteria = session.createCriteria(CourseCategoriesLMS.class, "obj");
			criteria.addOrder(Property.forName("obj.id").asc());
			this.courseCategoriesLms = criteria.list();
			logger.info("CourseCategoriesLMS tables: " + this.courseCategoriesLms.size());

			criteria = session.createCriteria(ForumLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.forumLms = criteria.list();
			logger.info("ForumLMS tables: " + this.forumLms.size());

			criteria = session.createCriteria(GroupsLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.courseid", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.groupLms = criteria.list();
			logger.info("GroupsLMS tables: " + this.groupLms.size());

			criteria = session.createCriteria(QuizLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.quizLms = criteria.list();
			logger.info("QuizLMS tables: " + this.quizLms.size());

			criteria = session.createCriteria(WikiLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.wikiLms = criteria.list();
			logger.info("WikiLMS tables: " + this.wikiLms.size());

			/*
			criteria = session.createCriteria(QuizQuestionInstancesLMS.class, "obj");
			if(hasCR)
			{
				ArrayList<Long> ids = new ArrayList<Long>();
	 			for(QuizLMS e : this.quizLms)
	 				ids.add(e.getId());
	 			if(!(empty = ids.isEmpty()))
	 				criteria.add(Restrictions.in("obj.quiz", ids));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.quizQuestionInstancesLms = criteria.list();
			else
				this.quizQuestionInstancesLms = new ArrayList<QuizQuestionInstancesLMS>();
			logger.info("QuizQuestionInstancesLMS tables: " + this.quizQuestionInstancesLms.size());
*/
/*
			criteria = session.createCriteria(QuestionLMS.class, "obj");
			if(hasCR)
			{
				ArrayList<Long> ids = new ArrayList<Long>();
	 			for(QuizQuestionInstancesLMS e : this.quizQuestionInstancesLms)
	 				ids.add(e.getQuestion());
	 			if(!(empty = ids.isEmpty()))
	 				criteria.add(Restrictions.in("obj.id", ids));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.questionLms = criteria.list();
			else
				this.questionLms = new ArrayList<QuestionLMS>();
			logger.info("QuestionLMS tables: " + this.questionLms.size());
*/
			
			criteria = session.createCriteria(RoleLMS.class, "obj");
			criteria.addOrder(Property.forName("obj.id").asc());
			this.roleLms = criteria.list();
			logger.info("RoleLMS tables: " + this.roleLms.size());

			session.clear();

			/*
			criteria = session.createCriteria(AssignmentLMS.class, "obj");
			if(hasCR)
			{
				criteria.add(Restrictions.in("obj.course", courses));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			this.assignmentLms = criteria.list();
			logger.info("AssignmentLMS tables: " + this.assignmentLms.size());
			*/
			criteria = session.createCriteria(ScormLMS.class, "obj");
			if(hasCR)
			{
				criteria.add(Restrictions.in("obj.course", courses));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			this.scormLms = criteria.list();
			logger.info("ScormLMS tables: " + this.scormLms.size());
			


			criteria = session.createCriteria(GradeItemsLMS.class, "obj");
			if(hasCR)
			{
				criteria.add(Restrictions.in("obj.courseid", courses));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			this.gradeItemsLms = criteria.list();
			logger.info("GradeItemsLMS tables: " + this.gradeItemsLms.size());
			
			criteria = session.createCriteria(RoleAssignmentsLMS.class, "obj");
			if(hasCR)
			{
				ArrayList<Long> ids = new ArrayList<Long>();
				for(ContextLMS c : this.contextLms)
				{
					if(c.getContextlevel() == 50 && courses.contains(c.getInstanceid()))
						ids.add(c.getId());					
				}
				if(!(empty = ids.isEmpty()))
					criteria.add(Restrictions.in("obj.contextid", ids));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.roleAssignmentsLms = criteria.list();	
			else
				this.roleAssignmentsLms = new ArrayList<RoleAssignmentsLMS>();
			logger.info("RoleAssignmentsLMS tables: " + this.roleAssignmentsLms.size());

			criteria = session.createCriteria(UserLMS.class, "obj");
			if(hasCR)
			{
				ArrayList<Long> ids = new ArrayList<Long>();
	 			for(RoleAssignmentsLMS e : this.roleAssignmentsLms)
	 				ids.add(Long.valueOf(e.getUserid()));
	 			if(!(empty = ids.isEmpty()))
	 				criteria.add(Restrictions.in("obj.id", ids));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.userLms = criteria.list();
			else
				this.userLms = new ArrayList<UserLMS>();
			logger.info("UserLMS tables: " + this.userLms.size());
		}

		criteria = session.createCriteria(QuizAttemptsLMS.class, "obj");
		if(hasCR)
			if(hasCR)
			{
				ArrayList<Long> ids = new ArrayList<Long>();
	 			for(QuizLMS e : this.quizLms)
	 				ids.add(e.getId());
	 			if(!(empty = ids.isEmpty()))
	 				criteria.add(Restrictions.in("obj.quiz", ids));
			}
		criteria.add(Restrictions.lt("obj.timemodified", readingtotimestamp));
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.quizAttemptsLms = criteria.list();
		else
			this.quizAttemptsLms = new ArrayList<QuizAttemptsLMS>();
		logger.info("QuizAttemptsLMS tables: " + this.quizAttemptsLms.size());

		criteria = session.createCriteria(LogstoreStandardLogLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		criteria.add(Restrictions.lt("obj.time", readingtotimestamp));
		criteria.add(Restrictions.gt("obj.time", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.logstoreLms = criteria.list();
		logger.info("LogLMS tables: " + this.logstoreLms.size());

		criteria = session.createCriteria(ChatLogLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(ChatLMS e : this.chatLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.chat", ids));
		}
		criteria.add(Restrictions.lt("obj.timestamp", readingtotimestamp));
		criteria.add(Restrictions.gt("obj.timestamp", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.chatLogLms = criteria.list();
		else
			this.chatLogLms = new ArrayList<ChatLogLMS>();
		logger.info("ChatLogLMS tables: " + this.chatLogLms.size());

		final Query forumPosts;
		if(!hasCR)
		{
			forumPosts= session
				.createQuery("from ForumPostsLMS x where x.created>=:readingtimestamp and x.created<=:ceiling order by x.id asc");
			forumPosts.setParameter("readingtimestamp", readingfromtimestamp);
			forumPosts.setParameter("ceiling", readingtotimestamp);
			this.forumPostsLms = forumPosts.list();
		}
		else
		{
			String courseClause ="(";
			for(int i = 0; i < courses.size(); i++)
			{
				courseClause += courses.get(i);
				if(i < courses.size() - 1)
					courseClause += ",";
				else
					courseClause += ")";
			}
			forumPosts = session.createSQLQuery("SELECT posts.id,posts.userid,posts.created,posts.modified,posts.subject,posts.message,posts.discussion from forum_posts as posts JOIN logstore_standard_log as logs ON posts.userid = logs.userid Where logs.courseid in "+ courseClause +" and (posts.created = logs.timecreated or posts.modified = logs.timecreated) AND posts.created>=:readingtimestamp and posts.created<=:ceiling");
			forumPosts.setParameter("readingtimestamp", readingfromtimestamp);
			forumPosts.setParameter("ceiling", readingtotimestamp);
			List<Object[]> tmpl = forumPosts.list();			
			if(this.forumPostsLms == null)
				this.forumPostsLms = new ArrayList<ForumPostsLMS>();
			for(Object[] obj : tmpl)
			{
				ForumPostsLMS p = new ForumPostsLMS();
				if(obj[0].getClass().equals(BigInteger.class))
				{
					p.setId(((BigInteger) obj[0]).longValue());
				}
				else
				{
					p.setId(((Integer) obj[0]).longValue());
				}
				if(obj[0].getClass().equals(BigInteger.class))
				{
					p.setUserid(((BigInteger) obj[1]).longValue());
				}
				else
				{
					p.setUserid(((Integer) obj[1]).longValue());
				}
				if(obj[0].getClass().equals(BigInteger.class))
				{
					p.setCreated(((BigInteger) obj[2]).longValue());
				}
				else
				{
					p.setCreated(((Integer) obj[2]).longValue());
				}
				if(obj[0].getClass().equals(BigInteger.class))
				{
					p.setModified(((BigInteger) obj[3]).longValue());
				}
				else
				{
					p.setModified(((Integer) obj[3]).longValue());
				}
				p.setSubject((String) obj[4]);
				p.setMessage((String) obj[5]);
				if(obj[0].getClass().equals(BigInteger.class))
				{
					p.setDiscussion(((BigInteger) obj[6]).longValue());
				}
				else
				{
					p.setDiscussion(((Integer) obj[6]).longValue());
				}
				this.forumPostsLms.add(p);
			
			}
		}
		logger.info("ForumPostsLMS tables: " + this.forumPostsLms.size());

		final Query forumPostsModified;
		if(!hasCR)
		{
			forumPostsModified= session
				.createQuery("from ForumPostsLMS x where x.modified>=:readingtimestamp and x.modified<=:ceiling order by x.id asc");
			this.forumPostsLms.addAll(forumPostsModified.list());
		}
		else
		{
			String courseClause ="(";
			for(int i = 0; i < courses.size(); i++)
			{
				courseClause += courses.get(i);
				if(i < courses.size() - 1)
					courseClause += ",";
				else
					courseClause += ")";
			}
			forumPostsModified = session.createSQLQuery("SELECT posts.id,posts.userid,posts.created,posts.modified,posts.subject,posts.message from mdl_forum_posts as posts JOIN mdl_log as logs ON posts.userid = logs.userid Where logs.course in "+ courseClause +" and (posts.created = logs.time or posts.modified = logs.time) AND posts.modified>=:readingtimestamp and posts.modified<=:ceiling");
			forumPostsModified.setParameter("readingtimestamp", readingfromtimestamp);
			forumPostsModified.setParameter("ceiling", readingtotimestamp);
			List<Object[]> tmpl = forumPostsModified.list();
			if(this.forumPostsLms == null)
				this.forumPostsLms = new ArrayList<ForumPostsLMS>();
			for(Object[] obj : tmpl)
			{
				ForumPostsLMS p = new ForumPostsLMS();
				p.setId(((Integer) obj[0]).longValue());
				p.setUserid(((Integer) obj[1]).longValue());
				p.setCreated(((Integer) obj[2]).longValue());
				p.setModified(((Integer) obj[3]).longValue());
				p.setSubject((String) obj[4]);
				p.setMessage((String) obj[5]);
				
				this.forumPostsLms.add(p);
			
			}
		}
		logger.info("ForumPostsModifiedLMS tables: " + this.forumPostsLms.size());

		session.clear();
		
		criteria = session.createCriteria(AssignGradesLMS.class, "obj");
		if(hasCR)
		{
			List<Long> tmp = new ArrayList<Long>();
			for(AssignLMS assign : assignLms)
			{
				tmp.add(assign.getId());
			}
			
			if(!(empty = tmp.isEmpty()))
				criteria.add(Restrictions.in("obj.assignment", tmp));
		}		
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.add(Restrictions.lt("obj.timemodified", readingtotimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.assignGradesLms = criteria.list();
		else
			this.assignGradesLms = new ArrayList<AssignGradesLMS>();
		logger.info("AssignGradesLMS tables: " + this.assignGradesLms.size());

		criteria = session.createCriteria(GroupsMembersLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(GroupsLMS e : this.groupLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.groupid", ids));
		}
		criteria.add(Restrictions.lt("obj.timeadded", readingtotimestamp));
		criteria.add(Restrictions.gt("obj.timeadded", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.groupMembersLms = criteria.list();
		else
			this.groupMembersLms = new ArrayList<GroupsMembersLMS>();
		logger.info("GroupsMembersLMS tables: " + this.groupMembersLms.size());

		/*
		criteria = session.createCriteria(QuestionStatesLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(QuizQuestionInstancesLMS e : this.quizQuestionInstancesLms)
 				ids.add(e.getQuestion());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.question", ids));
		}
		criteria.add(Restrictions.lt("obj.timestamp", readingtotimestamp));
		criteria.add(Restrictions.gt("obj.timestamp", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.questionStatesLms = criteria.list();
		else
			this.questionStatesLms = new ArrayList<QuestionStatesLMS>();
		logger.info("QuestionStatesLMS tables: " + this.questionStatesLms.size());
*/


		/*
		criteria = session.createCriteria(AssignmentSubmissionsLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(AssignmentLMS e : this.assignmentLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.id", ids));
		}
		criteria.add(Restrictions.lt("obj.timecreated", readingtotimestamp));
		criteria.add(Restrictions.gt("obj.timecreated", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.assignmentSubmissionLms = criteria.list();
		else
			this.assignmentSubmissionLms = new ArrayList<AssignmentSubmissionsLMS>();
		logger.info("AssignmentSubmissionsLMS tables: " + this.userLms.size());
		 */
		
		criteria = session.createCriteria(QuizGradesLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(QuizLMS e : this.quizLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.quiz", ids));
		}
		criteria.add(Restrictions.lt("obj.timemodified", readingtotimestamp));
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.quizGradesLms = criteria.list();
		else
			this.quizGradesLms = new ArrayList<QuizGradesLMS>();
		logger.info("QuizGradesLMS tables: " + this.quizGradesLms.size());

		criteria = session.createCriteria(ForumDiscussionsLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(ForumLMS e : this.forumLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.forum", ids));
		}
		criteria.add(Restrictions.lt("obj.timemodified", readingtotimestamp));
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.forumDiscussionsLms = criteria.list();
		else
			this.forumDiscussionsLms = new ArrayList<ForumDiscussionsLMS>();
		logger.info("ForumDiscussionsLMS tables: " + this.forumDiscussionsLms.size());

		criteria = session.createCriteria(GradeGradesLMS.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> ids = new ArrayList<Long>();
 			for(GradeItemsLMS e : this.gradeItemsLms)
 				ids.add(e.getId());
 			if(!(empty = ids.isEmpty()))
 				criteria.add(Restrictions.in("obj.itemid", ids));
		}
		criteria.add(Restrictions.lt("obj.timemodified", readingtotimestamp));
		criteria.add(Restrictions.gt("obj.timemodified", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.gradeGradesLms = criteria.list();
		else
			this.gradeGradesLms = new ArrayList<GradeGradesLMS>();
		logger.info("GradeGradesLMS tables: " + this.gradeGradesLms.size());

		session.clear();

		// hibernate session finish and close
		tx.commit();
		session.close();

	}

	@Override
	public void clearLMStables() {
		this.logstoreLms.clear();
		this.resourceLms.clear();
		this.courseLms.clear();
		this.forumLms.clear();
		this.wikiLms.clear();
		this.userLms.clear();
		this.quizLms.clear();
		this.gradeGradesLms.clear();
		this.groupLms.clear();
		this.groupMembersLms.clear();
		this.forumPostsLms.clear();
		this.roleLms.clear();
		this.roleAssignmentsLms.clear();
	}

	// methods for create and fill the mining-table instances

	@Override
	public Map<Long, CourseUser> generateCourseUsers() {

		final HashMap<Long, CourseUser> courseUserMining = new HashMap<Long, CourseUser>();

		for (final ContextLMS loadedItem : this.contextLms)
		{
			if (loadedItem.getContextlevel() == 50) {
				for (final RoleAssignmentsLMS loadedItem2 : this.roleAssignmentsLms)
				{
					if (loadedItem2.getContextid() == loadedItem.getId()) {
						final CourseUser insert = new CourseUser();

						insert.setId(loadedItem2.getId());
						insert.setRole(loadedItem2.getRoleid(),
								this.roleMining, this.oldRoleMining);
						insert.setUser(Long.valueOf(loadedItem2.getUserid()),
								this.userMining, this.oldUserMining);
						insert.setCourse(loadedItem.getInstanceid(),
								this.courseMining, this.oldCourseMining);
						if ((insert.getUser() != null) && (insert.getCourse() != null) && (insert.getRole() != null)) {
							courseUserMining.put(insert.getId(), insert);
						}
					}
				}
			}
		}
		return courseUserMining;
	}

	@Override
	public Map<Long, Course> generateCourses() {

		final HashMap<Long, Course> courseMining = new HashMap<Long, Course>();
		for (final CourseLMS loadedItem : this.courseLms)
		{
			final Course insert = new Course();

			insert.setId(loadedItem.getId());
			insert.setTitle(loadedItem.getFullname());

			courseMining.put(insert.getId(), insert);
		}
		return courseMining;
	}

	/*
	@Override
	public Map<Long, QuestionLogMining> generateQuestionLogMining()
	{

		final HashMap<Long, QuestionLogMining> questionLogMiningtmp = new HashMap<Long, QuestionLogMining>();
		final HashMap<Long, QuestionLogMining> questionLogMining = new HashMap<Long, QuestionLogMining>();
		final HashMap<String, Long> timestampIdMap = new HashMap<String, Long>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final QuestionStatesLMS loadedItem : this.questionStatesLms) {

			final QuestionLogMining insert = new QuestionLogMining();

			insert.setId(questionLogMiningtmp.size() + 1 + this.questionLogMax);
			insert.setQuestion(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getQuestion()),
					this.questionMining, this.oldQuestionMining);
			insert.setPenalty(loadedItem.getPenalty());
			insert.setAnswers(loadedItem.getAnswer());
			insert.setTimestamp(loadedItem.getTimestamp());
			if(insert.getTimestamp() > maxLog)
			{
				maxLog = insert.getTimestamp();
			}
			
			insert.setPlatform(this.connector.getPlatformId());

			// Set Grades
			if ((loadedItem.getEvent() == 3) || (loadedItem.getEvent() == 6) || (loadedItem.getEvent() == 9)) {
				insert.setRawGrade(loadedItem.getRawGrade());
				insert.setFinalGrade(loadedItem.getGrade());
			}

			switch (loadedItem.getEvent())
			{
				case 0:
					insert.setAction("OPEN");
					break;
				case 1:
					insert.setAction("NAVIGATE");
					break;
				case 2:
					insert.setAction("SAVE");
					break;
				case 3:
					insert.setAction("GRADE");
					break;
				case 4:
					insert.setAction("DUPLICATE");
					break;
				case 5:
					insert.setAction("VALIDATE");
					break;
				case 6:
					insert.setAction("CLOSEANDGRADE");
					break;
				case 7:
					insert.setAction("SUBMIT");
					break;
				case 8:
					insert.setAction("CLOSE");
					break;
				case 9:
					insert.setAction("MANUALGRADE");
					break;
				default:
					insert.setAction("UNKNOWN");
			}

			// Set quiz type
			if ((insert.getQuestion() != null) && (this.quizQuestionMining.get(insert.getQuestion().getId()) != null))
			{
				insert.setQuiz(this.quizQuestionMining.get(insert.getQuestion().getId()).getQuiz());
				if (this.courseQuizMining.get(insert.getQuiz().getId()) != null) {
					insert.setCourse(this.courseQuizMining.get(insert.getQuiz().getId()).getCourse());
				}
			}
			else if ((insert.getQuestion() != null)
					&& (this.oldQuizQuestionMining.get(insert.getQuestion().getId()) != null)
					&& (this.oldCourseQuizMining.get(insert.getQuiz().getId()) != null))
			{
				insert.setQuiz(this.oldQuizQuestionMining.get(insert.getQuestion().getId()).getQuiz());
				if (this.oldCourseQuizMining.get(insert.getQuiz().getId()) != null) {
					insert.setCourse(this.courseQuizMining.get(insert.getQuiz().getId()).getCourse());
				}
			}

			// Set Type
			for (final QuestionLMS loadedItem2 : this.questionLms)
			{
				if (loadedItem2.getId() == (loadedItem.getQuestion())) {
					insert.setType(loadedItem2.getQtype());
					break;
				}
			}
			if ((insert.getType() == null) && (this.oldQuestionMining.get(loadedItem.getQuestion()) != null)) {
				insert.setType(this.oldQuestionMining.get(loadedItem.getQuestion()).getType());
			}
			if (insert.getType() == null) {
				this.logger.debug("In QuestionLogMining, type not found for questionStates: " + loadedItem.getId()
						+ " and question: " + loadedItem.getQuestion() + " question list size: "
						+ this.questionLms.size());
			}

			if ((insert.getQuestion() != null) && (insert.getQuiz() != null) && (insert.getCourse() != null))
			{

				timestampIdMap.put(insert.getTimestamp() + " " + insert.getQuiz().getId(), insert.getId());
				questionLogMiningtmp.put(insert.getId(), insert);
			}
		}

		// Set Course and
		for (final LogLMS loadedItem : this.logLms)
		{
			final long uid1 = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid1) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid1, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid1);
				if (loadedItem.getAction().equals("login")) {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid1, times);
			}

			if (loadedItem.getModule().equals("quiz")
					&& (timestampIdMap.get(loadedItem.getTime() + " " + this.connector.getPrefix() + ""
							+ loadedItem.getInfo()) != null)) {
				{
					final QuestionLogMining qlm = questionLogMiningtmp.get(timestampIdMap.get(loadedItem.getTime()
							+ " " + this.connector.getPrefix() + "" + loadedItem.getInfo()));
					qlm.setUser(uid1, this.userMining, this.oldUserMining);
					questionLogMining.put(qlm.getId(), qlm);
				}
			}
		}

		for (final QuestionLogMining r : questionLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}

		return questionLogMining;
	}
	*/

	
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
			insert.setText(TextHelper.replaceString(loadedItem.getMessage()));
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
					insert.setText("Subject: " + discussions.get(Long.valueOf(loadedItem.getObjectid())).getName());
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
					insert.setText(TextHelper.replaceString(p.getMessage()));
				}	
				if(insert.getText() == null)
					for (final ForumPostsLMS loadedItem2 : this.forumPostsLms)
					{
						if ((loadedItem2.getUserid() == loadedItem.getUser())
								&& ((loadedItem2.getCreated() == loadedItem.getTimecreated()) || (loadedItem2.getModified() == loadedItem
										.getTimecreated()))) {
							insert.setText(TextHelper.replaceString(loadedItem2.getMessage()));
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

			if(!this.learningTypeMining.containsKey("Resource") && !this.oldLearningTypeMining.containsKey("Resource"))
			{
				LearningType type = new LearningType();
				type.setType("Resource");
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}
			
			insert.setId(Long.valueOf("11" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setType("Resource", this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Access");

			// Get time of creation

			learningObjs.put(insert.getId(), insert);
		}
		
		for (final UrlLMS loadedItem : this.urlLms)
		{
			final LearningObj insert = new LearningObj();

			if(!this.learningTypeMining.containsKey("URL") && !this.oldLearningTypeMining.containsKey("URL"))
			{
				LearningType type = new LearningType();
				type.setType("URL");
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}
			
			insert.setId(Long.valueOf("12" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setType("URL", this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Access");

			learningObjs.put(insert.getId(), insert);
		}
		
		for (final PageLMS loadedItem : this.pageLms)
		{
			final LearningObj insert = new LearningObj();

			if(!this.learningTypeMining.containsKey("Page") && !this.oldLearningTypeMining.containsKey("Page"))
			{
				LearningType type = new LearningType();
				type.setType("Page");
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}
			
			insert.setId(Long.valueOf("13" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setType("Page", this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Access");

			learningObjs.put(insert.getId(), insert);
		}
		
		final HashMap<Long, LearningObj> amTmp = new HashMap<Long, LearningObj>();
		
		

		for (final AssignLMS loadedItem : this.assignLms)
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
			insert.setTitle(loadedItem.getName());
			insert.setType("Assign", this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Assessment");
			
			for (final GradeItemsLMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getIteminstance() != null) && (loadedItem2.getItemmodule() != null))
				{
					this.logger.debug("Iteminstance " + loadedItem2.getIteminstance() + " AssignId"
							+ loadedItem.getId());
					if ((loadedItem.getId() == loadedItem2.getIteminstance().longValue())
							&& loadedItem2.getItemmodule().equals("assign")) {
						//insert.setMaxGrade(loadedItem2.getGrademax());
						break;
					}
				}
				else {
					this.logger.debug("Iteminstance or Itemmodule not found for AssignId" + loadedItem.getId()
							+ " and type quiz and Iteminstance " + loadedItem2.getIteminstance() + " Itemmodule:"
							+ loadedItem2.getItemmodule());
				}
			}

			amTmp.put(insert.getId(), insert);
		}

		learningObjs.putAll(amTmp);
		
		for (final QuizLMS loadedItem : this.quizLms)
		{

			final LearningObj insert = new LearningObj();
			
			if(!this.learningTypeMining.containsKey("Quiz") && !this.oldLearningTypeMining.containsKey("Quiz"))
			{
				LearningType type = new LearningType();
				type.setType("Quiz");
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}

			insert.setId(Long.valueOf("18" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			//insert.setMaxGrade(loadedItem.getSumgrade());
			insert.setType("Quiz", this.learningTypeMining, this.oldLearningTypeMining);
			insert.setInteractionType("Assessment");

			learningObjs.put(insert.getId(), insert);
		}
		
		for (final ScormLMS loadedItem : this.scormLms)
		{
			final LearningObj insert = new LearningObj();
			
			if(!this.learningTypeMining.containsKey("Scorm") && !this.oldLearningTypeMining.containsKey("Scorm"))
			{
				LearningType type = new LearningType();
				type.setType("Scorm");
				type.setId(this.learningObjectTypeMax + 1 + this.learningTypeMining.size());
				this.learningTypeMining.put(type.getType(), type);
			}

			insert.setId(Long.valueOf("19" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			//insert.setMaxGrade(loadedItem.getMaxgrade());
			insert.setType("Scorm", this.learningTypeMining, this.oldLearningTypeMining);
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
			this.chatCourse.put(insert.getId(), loadedItem.getCourse());
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
			insert.setTitle(loadedItem.getName());
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
			insert.setTitle(loadedItem.getName());
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
