/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/ExtractAndMapMoodle.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/ExtractAndMapMoodle.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_7;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import de.lemo.dms.connectors.moodle_2_7.mapping.ChatLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ContextLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.CourseLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.CourseModulesLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.EnrolLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ForumLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ForumDiscussionsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ForumPostsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.GradeGradesLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.GradeItemsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.LogstoreStandardLogLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ModulesLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.PageLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.QuizLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ResourceLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.UrlLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.RoleLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.RoleAssignmentsLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.ScormLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.UserLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.WikiLMS;
import de.lemo.dms.connectors.moodle_2_7.mapping.WikiPagesLMS;
import de.lemo.dms.db.CourseObject;
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
	private List<ForumPostsLMS> forumPostsLms;
	private List<RoleLMS> roleLms;
	private List<ContextLMS> contextLms;
	private List<RoleAssignmentsLMS> roleAssignmentsLms;
	private List<ForumDiscussionsLMS> forumDiscussionsLms;
	private List<ScormLMS> scormLms;
	private List<GradeGradesLMS> gradeGradesLms;
	private List<GradeItemsLMS> gradeItemsLms;
	//private List<ChatLMS> chatLms;
	//private List<ChatLogLMS> chatLogLms;
	private List<AssignLMS> assignLms;
	private List<EnrolLMS> enrolLms;
	private List<ModulesLMS> modulesLms;
	private List<CourseModulesLMS> courseModulesLms;
	final Map<LearningContext, CourseObject> courseDetails = new HashMap<LearningContext, CourseObject>();

	//private Map<Long, Long> chatCourse = new HashMap<Long, Long>();
	
	private final Logger logger = Logger.getLogger(this.getClass());

	private final IConnector connector;
	
	List<Long> coursesInt = null;
	private DBConfigObject dbConfigInt = null;
	private List<String> objecttables = new ArrayList<String>();
	private Map<Long, LearningObjectExt> learningAttributes = new HashMap<Long, LearningObjectExt>();
	private Map<Long, LearningContextExt> courseAttributes = new HashMap<Long, LearningContextExt>();
	private Map<Long, PersonExt> userAttributes = new HashMap<Long, PersonExt>();
	
	private int logMaxRes = 500000;

	public ExtractAndMapMoodle(final IConnector connector) {
		super(connector);
		this.connector = connector;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void getLMStables(final DBConfigObject dbConfig, final long readingfromtimestamp, List<Long> courses, List<String> logins) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConfig).openSession();
		
		
		boolean hasCR = false;
		if(courses != null && courses.size() > 0)
			hasCR = true; 
		else
			courses = new ArrayList<Long>();
		
		boolean empty = false;
		
		coursesInt = courses;
		
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

		//Read CourseModules
		criteria = session.createCriteria(CourseModulesLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.courseModulesLms = criteria.list();
		logger.info("CourseModulesLMS tables: " + this.courseModulesLms.size());

		//Read Log
		/*
		criteria = session.createCriteria(LogstoreStandardLogLMS.class, "obj");
		if(hasCR){
			criteria.add(Restrictions.in("obj.course", courses));
		}
		criteria.add(Restrictions.in("obj.objecttable", this.objecttables));
		criteria.setMaxResults(this.logMaxRes);
		criteria.addOrder(Property.forName("obj.id").asc());
		criteria.add(Restrictions.gt("obj.timecreated", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.timecreated").asc());
		this.logstoreLms = criteria.list();
		if(this.logstoreLms.size() > 0)
			this.eventLimit = this.logstoreLms.get(this.logstoreLms.size()-1).getId();
		logger.info("LogLMS tables: " + this.logstoreLms.size());
		*/
		
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
		
		//Read Chats
		criteria = session.createCriteria(ChatLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		
		/*
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
		 */
		
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
		this.dbConfigInt = dbConfig;
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

			/*
			criteria = session.createCriteria(ChatLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.chatLms = criteria.list();
			logger.info("ChatLMS tables: " + this.chatLms.size());
*/

			criteria = session.createCriteria(ForumLMS.class, "obj");
			if(hasCR)
				criteria.add(Restrictions.in("obj.course", courses));
			criteria.addOrder(Property.forName("obj.id").asc());
			this.forumLms = criteria.list();
			logger.info("ForumLMS tables: " + this.forumLms.size());

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

		criteria = session.createCriteria(LogstoreStandardLogLMS.class, "obj");
		if(hasCR)
			criteria.add(Restrictions.in("obj.course", courses));
		criteria.add(Restrictions.lt("obj.time", readingtotimestamp));
		criteria.add(Restrictions.gt("obj.time", readingfromtimestamp));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.logstoreLms = criteria.list();
		logger.info("LogLMS tables: " + this.logstoreLms.size());

		/*
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
*/
		
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
		this.forumPostsLms.clear();
		this.roleLms.clear();
		this.roleAssignmentsLms.clear();
	}

	// methods for create and fill the mining-table instances

	@Override
	public Map<Long, PersonContext> generatePersonContexts() {

		final HashMap<Long, PersonContext> courseUserMining = new HashMap<Long, PersonContext>();
		
		
		final HashMap<Long, String> roleMining = new HashMap<Long, String>();

		for (final RoleLMS loadedItem : this.roleLms)
		{
			if(loadedItem.getArchetype().equals("manager") || loadedItem.getArchetype().equals("coursecreator")) {
				roleMining.put(loadedItem.getId(), "administrator");
			}
			else if(loadedItem.getArchetype().equals("teacher") || loadedItem.getArchetype().equals("editingteacher")) {
				roleMining.put(loadedItem.getId(), "teacher");
			}
			else {
				roleMining.put(loadedItem.getId(), "student");
			}
		}

		for (final ContextLMS loadedItem : this.contextLms)
		{
			if (loadedItem.getContextlevel() == 50) {
				for (final RoleAssignmentsLMS loadedItem2 : this.roleAssignmentsLms)
				{
					if (loadedItem2.getContextid() == loadedItem.getId()) {
						final PersonContext insert = new PersonContext();

						insert.setId(loadedItem2.getId());
						insert.setRole(roleMining.get(loadedItem2.getRoleid()));
						insert.setPerson(Long.valueOf(loadedItem2.getUserid()),
								this.personMining, this.oldPersonMining);
						insert.setLearningContext(loadedItem.getInstanceid(),
								this.learningContextMining, this.oldLearningContextMining);
						if ((insert.getPerson() != null) && (insert.getLearningContext() != null) && (insert.getRole() != null)) {
							courseUserMining.put(insert.getId(), insert);
						}
					}
				}
			}
		}
		return courseUserMining;
	}

	@Override
	public Map<Long, LearningContext> generateLearningContexts() {

		final HashMap<Long, LearningContext> courseMining = new HashMap<Long, LearningContext>();
		for (final CourseLMS loadedItem : this.courseLms)
		{
			final LearningContext insert = new LearningContext();

			insert.setId(loadedItem.getId());
			insert.setName(loadedItem.getFullname());

			courseMining.put(insert.getId(), insert);
			addCourseAttribute(insert, "CourseDescription", loadedItem.getSummary());
		}
		return courseMining;
	}

	@Override
	public Map<Long, LearningObject> generateLearningObjects() {
		
		Map<Long, LearningObject> learningObjs = new HashMap<Long, LearningObject>();
		
		for (final ResourceLMS loadedItem : this.resourceLms)
		{
			final LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("11" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			insert.setType("Resource");

			// Get time of creation

			learningObjs.put(insert.getId(), insert);
		}
		
		for (final UrlLMS loadedItem : this.urlLms)
		{
			final LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("12" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			insert.setType("URL");

			learningObjs.put(insert.getId(), insert);
		}
		
		for (final PageLMS loadedItem : this.pageLms)
		{
			final LearningObject insert = new LearningObject();

			insert.setId(Long.valueOf("13" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			insert.setType("Page");

			learningObjs.put(insert.getId(), insert);
		}
		
		final HashMap<Long, LearningObject> amTmp = new HashMap<Long, LearningObject>();
		
		

		for (final AssignLMS loadedItem : this.assignLms)
		{
			final LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("17" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			insert.setType("Assign");
			
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
			addLearningAttribute(insert, "MaxGrade", ((double)loadedItem.getGrade())+"");
			amTmp.put(insert.getId(), insert);
		}

		learningObjs.putAll(amTmp);
		
		for (final QuizLMS loadedItem : this.quizLms)
		{

			final LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("18" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			//insert.setMaxGrade(loadedItem.getSumgrade());
			insert.setType("Quiz");
			addLearningAttribute(insert, "MaxGrade", ((double)loadedItem.getGrade())+"");

			learningObjs.put(insert.getId(), insert);
		}
		
		for (final ScormLMS loadedItem : this.scormLms)
		{
			final LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("19" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			//insert.setMaxGrade(loadedItem.getMaxgrade());
			insert.setType("Scorm");
			
			addLearningAttribute(insert, "MaxGrade", ((double)loadedItem.getMaxgrade())+"");

			addLearningAttribute(insert, "MaxGrade", loadedItem.getMaxgrade()+"");
			learningObjs.put(insert.getId(), insert);
		}
		
		/*
		for (final ChatLMS loadedItem : this.chatLms)
		{
			final LearningObj insert = new LearningObj();
			insert.setId(Long.valueOf("14" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			this.chatCourse.put(insert.getId(), loadedItem.getCourse());
			insert.setType("Chat");
			
			learningObjs.put(insert.getId(), insert);
			
		}
		*/
		
		for (final ForumLMS loadedItem : this.forumLms)
		{
			final LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("15" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			insert.setType("Forum");

			learningObjs.put(insert.getId(), insert);
		}
		
		for (final WikiLMS loadedItem : this.wikiLms)
		{
			final LearningObject insert = new LearningObject();
			insert.setId(Long.valueOf("16" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			insert.setType("Wiki");
			
			learningObjs.put(insert.getId(), insert);
		}
		
		
		
		return learningObjs;
	}

	@Override
	public Map<Long, Person> generatePersons() {
		final HashMap<Long, Person> userMining = new HashMap<Long, Person>();

		for (final UserLMS loadedItem : this.userLms)
		{

			final Person insert = new Person();

			insert.setId(loadedItem.getId());
			insert.setName(Encoder.createMD5(loadedItem.getUsername()));

			userMining.put(insert.getId(), insert);
		}
		return userMining;
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
		loe.setAttribute(attribute);
		loe.setValue(value);
		this.personExtMining.put(loe.getId(), loe);
	}
	
	private void addLearningAttribute(LearningObject learning, String attribute, String value)
	{
		for(LearningObjectExt loe : this.learningObjectExt.values())
		{
			if(loe.getLearningObject() == learning && loe.getAttr().equals(attribute))
			{
				loe.setValue(value);
				return;
			}
		}
		LearningObjectExt loe = new LearningObjectExt();
		loe.setId(this.learningAttributeIdMax + 1);
		this.learningAttributeIdMax++;
		loe.setLearningObject(learning);
		loe.setAttr(attribute);
		loe.setValue(value);
		this.learningObjectExt.put(loe.getId(), loe);
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
		return this.personExtMining;
	}

	@Override
	public Map<Long, LearningObjectExt> generateLearningObjectExts() {
		return this.learningObjectExt;
	}

	@Override
	public Map<Long, ObjectContext> generateObjectContexts() {
		
		Map<Long, ObjectContext> courseLearnings = new HashMap<Long, ObjectContext>();
		
		for (final AssignLMS loadedItem : this.assignLms)
		{
			final ObjectContext insert = new ObjectContext();

			insert.setId(Long.valueOf("17" + loadedItem.getId()));
			insert.setLearningContext(loadedItem.getCourse(), this.learningContextMining,
					this.oldLearningContextMining);
			if (insert.getLearningContext() == null) {
				this.logger.debug("course not found for course-assignment: " + loadedItem.getId() + " and course: "
						+ loadedItem.getCourse());
			}
			insert.setLearningObject(Long.valueOf("17" + loadedItem.getId()),
					this.learningObjectMining, this.oldLearningObjectMining);
			if ((insert.getLearningContext() != null) && (insert.getLearningObject() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for (final ScormLMS loadedItem : this.scormLms)
		{
			final ObjectContext insert = new ObjectContext();

			insert.setId(Long.valueOf("19" + loadedItem.getId()));
			insert.setLearningContext(loadedItem.getCourse(), this.learningContextMining,
					this.oldLearningContextMining);
			insert.setLearningObject(Long.valueOf("19" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getLearningContext() != null) && (insert.getLearningObject() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}

		}
		
		for (final QuizLMS loadedItem : this.quizLms)
		{
			final ObjectContext insert = new ObjectContext();

			insert.setId(Long.valueOf("18" + loadedItem.getId()));
			insert.setLearningContext(loadedItem.getCourse(), this.learningContextMining,
					this.oldLearningContextMining);
			insert.setLearningObject(Long.valueOf("18" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getLearningContext() != null) && (insert.getLearningObject() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		/*
		for (final Entry<Long, Long> loadedItem : this.chatCourse.entrySet())
		{
			final CourseLearning insert = new CourseLearning();

			insert.setId(loadedItem.getKey());
			insert.setCourse(loadedItem.getValue(), this.learningContextMining, this.oldLearningContextMining);	
			insert.setLearning(loadedItem.getKey(),
					this.learningObjectMining, this.learningObjectMining);
			if ((insert.getCourse() != null) && (insert.getLearning() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}*/
		
		for (final ForumLMS loadedItem : this.forumLms)
		{
			final ObjectContext insert = new ObjectContext();

			insert.setId(Long.valueOf("15" + loadedItem.getId()));
			insert.setLearningContext(loadedItem.getCourse(), this.learningContextMining,
					this.oldLearningContextMining);
			insert.setLearningObject(Long.valueOf("15" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getLearningContext() != null) && (insert.getLearningObject() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for (final WikiLMS loadedItem : this.wikiLms)
		{
			final ObjectContext insert = new ObjectContext();

			insert.setId(Long.valueOf("16" + loadedItem.getId()));
			insert.setLearningContext(loadedItem.getCourse(), this.learningContextMining,
					this.oldLearningContextMining);
			insert.setLearningObject(Long.valueOf("16" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getLearningContext() != null) && (insert.getLearningObject() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}

		for (final ResourceLMS loadedItem : this.resourceLms)
		{
			final ObjectContext insert = new ObjectContext();

			insert.setId(Long.valueOf("11" + loadedItem.getId()));
			insert.setLearningContext(loadedItem.getCourse(), this.learningContextMining,
					this.oldLearningContextMining);
			insert.setLearningObject(Long.valueOf("11" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getLearningContext() != null) && (insert.getLearningObject() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for (final UrlLMS loadedItem : this.urlLms)
		{
			final ObjectContext insert = new ObjectContext();

			insert.setId(Long.valueOf("12" + loadedItem.getId()));
			insert.setLearningContext(loadedItem.getCourse(), this.learningContextMining,
					this.oldLearningContextMining);
			insert.setLearningObject(Long.valueOf("12" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getLearningContext() != null) && (insert.getLearningObject() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		for (final PageLMS loadedItem : this.pageLms)
		{
			final ObjectContext insert = new ObjectContext();

			insert.setId(Long.valueOf("13" + loadedItem.getId()));
			insert.setLearningContext(loadedItem.getCourse(), this.learningContextMining,
					this.oldLearningContextMining);
			insert.setLearningObject(Long.valueOf("13" + loadedItem.getId()), this.learningObjectMining,
					this.oldLearningObjectMining);
			if ((insert.getLearningContext() != null) && (insert.getLearningObject() != null)) {
				courseLearnings.put(insert.getId(), insert);
			}
		}
		
		return courseLearnings;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, LearningActivity> generateLearningActivities() {
		final HashMap<Long, LearningActivity> accessLogMining = new HashMap<Long, LearningActivity>();
		// A HashMap of list of timestamps. Every key represents one user, the according value is a list of his/her
		// requests times.
		
		final HashMap<Long, ForumDiscussionsLMS> discussions = new HashMap<Long, ForumDiscussionsLMS>();
		final HashMap<Long, ForumPostsLMS> posts = new HashMap<Long, ForumPostsLMS>();
		final HashMap<Long, WikiPagesLMS> wikipages = new HashMap<Long, WikiPagesLMS>();
		final HashMap<Long, CourseModulesLMS> couMod = new HashMap<Long, CourseModulesLMS>();
		
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
		
		this.objecttables.clear();
		this.objecttables.add("resource");
		this.objecttables.add("url");
		this.objecttables.add("page");
		this.objecttables.add("forum");
		this.objecttables.add("forum_discussions");
		this.objecttables.add("forum_subscriptions");
		this.objecttables.add("forum_posts");
		this.objecttables.add("wiki");
		this.objecttables.add("wiki_pages");
		this.objecttables.add("assign");
		this.objecttables.add("scorm");
		this.objecttables.add("quiz");
		
		long eventLimit = 0;
		
		Session moodleSession = HibernateUtil.getSessionFactory(dbConfigInt).openSession();
		Criteria criteria = moodleSession.createCriteria(LogstoreStandardLogLMS.class, "obj");
		if(this.coursesInt != null && !this.coursesInt.isEmpty())
			criteria.add(Restrictions.in("obj.course", coursesInt));
		criteria.add(Restrictions.in("obj.objecttable", this.objecttables));
		criteria.add(Restrictions.gt("obj.id", eventLimit));
		criteria.setMaxResults(logMaxRes);
		criteria.addOrder(Property.forName("obj.id").asc());
		this.logstoreLms = criteria.list();
		if(this.logstoreLms.size() > 0)
			eventLimit = this.logstoreLms.get(this.logstoreLms.size()-1).getId();
		moodleSession.clear();
		moodleSession.close();
		
		while(!this.logstoreLms.isEmpty())
		{
			for (final LogstoreStandardLogLMS loadedItem : this.logstoreLms)
			{
				final LearningActivity insert = new LearningActivity();
				insert.setId(1 + this.accessLogMax);
				this.accessLogMax++;
				insert.setTime(loadedItem.getTimecreated());
				insert.setAction(loadedItem.getAction());
				insert.setPerson(loadedItem.getUser(), this.personMining,
						this.oldPersonMining);
				insert.setLearningContext(loadedItem.getCourse(),
						this.learningContextMining, this.oldLearningContextMining);			
				
				insert.setAction(loadedItem.getAction());
				
				if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("resource")) {
	
					insert.setLearningObject(Long.valueOf("11" + loadedItem.getObjectid()),
							this.learningObjectMining, this.oldLearningObjectMining);
				}
				
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("url")) {
					insert.setLearningObject(Long.valueOf("12" + loadedItem.getObjectid()),
							this.learningObjectMining, this.oldLearningObjectMining);
	
				}
				
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("page")) {
					
					insert.setLearningObject(Long.valueOf("13" + loadedItem.getObjectid()),
							this.learningObjectMining, this.oldLearningObjectMining);
	
				}
				
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("forum")) {
					
					insert.setLearningObject(Long.valueOf("15" + loadedItem.getObjectid()) , this.learningObjectMining, this.oldLearningObjectMining);
				}
				
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("forum_discussions")) {
	
					if (discussions.containsKey(Long.valueOf(loadedItem.getObjectid())))
					{
						Long f = discussions.get(Long.valueOf(loadedItem.getObjectid())).getForum();
						insert.setLearningObject(Long.valueOf("15" + f),
								this.learningObjectMining, this.oldLearningObjectMining);
						insert.setInfo(TextHelper.replaceString("Subject: " + discussions.get(Long.valueOf(loadedItem.getObjectid())).getName(),255));
						//insert.setReference(insert.getLearningObject().getId(), accessLogMining);
					}
				}
				
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("forum_posts")) {
					
					ForumPostsLMS p = posts.get(Long.valueOf(loadedItem.getObjectid()));
					if(p != null && discussions.containsKey(p.getDiscussion()))
					{
						insert.setLearningObject(Long.valueOf("15" + discussions.get(p.getDiscussion()).getForum()),
								this.learningObjectMining, this.oldLearningObjectMining );
						insert.setInfo(TextHelper.replaceString(p.getMessage(),255));
					}	
					if(insert.getInfo() == null)
						for (final ForumPostsLMS loadedItem2 : this.forumPostsLms)
						{
							if ((loadedItem2.getUserid() == loadedItem.getUser())
									&& ((loadedItem2.getCreated() == loadedItem.getTimecreated()) || (loadedItem2.getModified() == loadedItem
											.getTimecreated()))) {
								insert.setInfo(TextHelper.replaceString(loadedItem2.getMessage(),255));
								break;
							}
						}
				}
				
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("forum_subscriptions")) {
					insert.setLearningObject(Long.valueOf("15" + loadedItem.getObjectid()),
								this.learningObjectMining, this.oldLearningObjectMining );
	
				}			
				
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("wiki"))
				{
					insert.setLearningObject(Long.valueOf("16" + loadedItem.getObjectid()), this.learningObjectMining, this.oldLearningObjectMining);
				}
				
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("wiki_pages"))
				{
					if(wikipages.get(loadedItem.getObjectid()) != null)
					{
						insert.setInfo(wikipages.get(loadedItem.getObjectid()).getTitle());
						insert.setLearningObject(Long.valueOf("16" + wikipages.get(loadedItem.getObjectid()).getSubwikiid()), this.learningObjectMining, this.oldLearningObjectMining);
					}
				}
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("assign")){
					insert.setLearningObject(Long.valueOf("17" + loadedItem.getObjectid()), this.learningObjectMining, this.oldLearningObjectMining);
				}
				
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("scorm")) {
					insert.setLearningObject(Long.valueOf("19" + loadedItem.getObjectid()), this.learningObjectMining, this.oldLearningObjectMining);
				}
				
				
				else if (loadedItem.getObjecttable() != null && loadedItem.getObjecttable().equals("quiz")){
					insert.setLearningObject(Long.valueOf("18" + loadedItem.getObjectid()), this.learningObjectMining, this.oldLearningObjectMining);
				}
				
				if(insert.getLearningContext() != null && !courseDetails.containsKey(insert.getLearningContext()))
				{
					courseDetails.put(insert.getLearningContext(), new de.lemo.dms.db.CourseObject());
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
				if(insert.getLearningContext() != null &&  insert.getTime() < courseDetails.get(insert.getLearningContext()).getFirstRequest())
				{
					courseDetails.get(insert.getLearningContext()).setFirstRequest(insert.getTime());
				}
				
				if ((insert.getLearningContext() != null) && (insert.getLearningObject() != null) && (insert.getPerson() != null)) 
				{
					accessLogMining.put(insert.getId(), insert);
				}
	
			}
			
			
			this.logstoreLms.clear();
			
			final Session miningSession = this.dbHandler.getMiningSession();
			List<Collection<?>> logs = new ArrayList<Collection<?>>();
			logs.add(accessLogMining.values());
			this.dbHandler.saveCollectionToDB(miningSession, logs);
			accessLogMining.clear();
			miningSession.clear();
			miningSession.close();
			
			moodleSession = HibernateUtil.getSessionFactory(dbConfigInt).openSession();
			criteria = moodleSession.createCriteria(LogstoreStandardLogLMS.class, "obj");
			if(this.coursesInt != null && !this.coursesInt.isEmpty())
				criteria.add(Restrictions.in("obj.course", coursesInt));
			criteria.add(Restrictions.in("obj.objecttable", this.objecttables));
			criteria.add(Restrictions.gt("obj.id", eventLimit));
			criteria.setMaxResults(logMaxRes);
			criteria.addOrder(Property.forName("obj.id").asc());
			this.logstoreLms = criteria.list();
			if(this.logstoreLms.size() > 0)
				eventLimit = this.logstoreLms.get(this.logstoreLms.size()-1).getId();
			moodleSession.clear();
			moodleSession.close();
			
		}
		
		for(AssignGradesLMS loadedItem : this.assignGradesLms)
		{
			LearningActivity insert = new LearningActivity();
			insert.setId(1 + this.accessLogMax);
			this.accessLogMax++;
			insert.setAction("Submit");
			insert.setTime(loadedItem.getTimemodified());
			insert.setLearningObject(Long.valueOf("17" + loadedItem.getAssignment()), learningObjectMining, oldLearningObjectMining);
			insert.setPerson(loadedItem.getUser(), personMining, oldPersonMining);
			if(loadedItem.getGrade() != null)
			{
				insert.setInfo(loadedItem.getGrade() +"");
			}
			
			
			for(AssignLMS loadedItem2 : this.assignLms)
			{
				if(loadedItem2.getId() == loadedItem.getAssignment())
				{
					insert.setLearningContext(loadedItem2.getCourse(), this.learningContextMining, this.oldLearningContextMining);
					break;
				}
			}
			
			if(insert.getPerson() != null && insert.getLearningContext() != null && insert.getLearningObject() != null)
			{
				accessLogMining.put(insert.getId(), insert);
			}
		}
		
		for (final GradeGradesLMS loadedItem : this.gradeGradesLms)
		{
			final LearningActivity insert = new LearningActivity();

			insert.setId(1 + this.accessLogMax);
			this.accessLogMax++;
			if (loadedItem.getTimemodified() != null) {
				insert.setTime(loadedItem.getTimemodified());
			}
			if (loadedItem.getFinalgrade() != null) {
				insert.setInfo(loadedItem.getFinalgrade() +"");
			}

			insert.setPerson(Long.valueOf(loadedItem.getUserid()), this.personMining,
					this.oldPersonMining);

			for (final GradeItemsLMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getId() == loadedItem.getItemid()) && (loadedItem2.getIteminstance() != null)) {
					insert.setLearningContext(loadedItem2.getCourseid(),
							this.learningContextMining, this.oldLearningContextMining);
					insert.setLearningObject(Long.valueOf("18" + loadedItem2.getIteminstance()),
							this.learningObjectMining, this.oldLearningObjectMining);
					if ((insert.getLearningObject() != null) && (insert.getPerson() != null)) {
						if(insert.getInfo() != null)
							insert.setAction("Submit");
						accessLogMining.put(insert.getId(), insert);
					}
				}
			}
		}
		
		final Session miningSession = this.dbHandler.getMiningSession();
		List<Collection<?>> logs = new ArrayList<Collection<?>>();
		logs.add(accessLogMining.values());
		this.dbHandler.saveCollectionToDB(miningSession, logs);
		accessLogMining.clear();
		miningSession.clear();
		miningSession.close();
	
		
		/*
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
				insert.setCourse(this.chatCourse.get(insert.getLearning()), this.learningContextMining, this.oldLearningContextMining);
			}
			insert.setPerson(Long.valueOf(loadedItem.getUser()), this.personMining,
					this.oldPersonMining);
			
			if(!courseDetails.containsKey(insert.getCourse()) && insert.getCourse() != null){
				courseDetails.put(insert.getCourse(), new CourseObject());
				courseDetails.get(insert.getCourse()).setFirstRequest(insert.getTimestamp());
			}
			if(insert.getCourse() != null)
				courseDetails.get(insert.getCourse()).setLastRequest(insert.getTimestamp());

			if ((insert.getLearning() != null) && (insert.getUser() != null) && (insert.getCourse() != null)) {
				collaborationLogs.put(insert.getId(), insert);
			}

		}*/
	
		
		
		
		
		return accessLogMining;
	}
}
