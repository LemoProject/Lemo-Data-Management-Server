/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/ClixImporter.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/ClixImporter.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.CriteriaHelper;
import de.lemo.dms.connectors.TextHelper;
import de.lemo.dms.connectors.clix2010.mapping.BiTrackContentImpressions;
import de.lemo.dms.connectors.clix2010.mapping.ChatProtocol;
import de.lemo.dms.connectors.clix2010.mapping.EComponent;
import de.lemo.dms.connectors.clix2010.mapping.EComponentType;
import de.lemo.dms.connectors.clix2010.mapping.EComposing;
import de.lemo.dms.connectors.clix2010.mapping.ExerciseGroup;
import de.lemo.dms.connectors.clix2010.mapping.ExercisePersonalised;
import de.lemo.dms.connectors.clix2010.mapping.ForumEntry;
import de.lemo.dms.connectors.clix2010.mapping.ForumEntryState;
import de.lemo.dms.connectors.clix2010.mapping.LearningLog;
import de.lemo.dms.connectors.clix2010.mapping.Person;
import de.lemo.dms.connectors.clix2010.mapping.PersonComponentAssignment;
import de.lemo.dms.connectors.clix2010.mapping.PlatformGroup;
import de.lemo.dms.connectors.clix2010.mapping.PlatformGroupSpecification;
import de.lemo.dms.connectors.clix2010.mapping.Portfolio;
import de.lemo.dms.connectors.clix2010.mapping.PortfolioLog;
import de.lemo.dms.connectors.clix2010.mapping.ScormSessionTimes;
import de.lemo.dms.connectors.clix2010.mapping.TGroupFullSpecification;
import de.lemo.dms.connectors.clix2010.mapping.TQtiContent;
import de.lemo.dms.connectors.clix2010.mapping.TQtiContentComposing;
import de.lemo.dms.connectors.clix2010.mapping.TQtiContentStructure;
import de.lemo.dms.connectors.clix2010.mapping.TQtiEvalAssessment;
import de.lemo.dms.connectors.clix2010.mapping.TQtiTestItemD;
import de.lemo.dms.connectors.clix2010.mapping.TQtiTestPlayer;
import de.lemo.dms.connectors.clix2010.mapping.TQtiTestPlayerResp;
import de.lemo.dms.connectors.clix2010.mapping.TeamExerciseGroup;
import de.lemo.dms.connectors.clix2010.mapping.WikiEntry;
import de.lemo.dms.connectors.clix2010.clixHelper.TimeConverter;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.AssignmentLogMining;
import de.lemo.dms.db.mapping.AssignmentMining;
import de.lemo.dms.db.mapping.ChatLogMining;
import de.lemo.dms.db.mapping.ChatMining;
import de.lemo.dms.db.mapping.ConfigMining;
import de.lemo.dms.db.mapping.CourseAssignmentMining;
import de.lemo.dms.db.mapping.CourseChatMining;
import de.lemo.dms.db.mapping.CourseForumMining;
import de.lemo.dms.db.mapping.CourseGroupMining;
import de.lemo.dms.db.mapping.CourseLogMining;
import de.lemo.dms.db.mapping.CourseMining;
import de.lemo.dms.db.mapping.CourseQuizMining;
import de.lemo.dms.db.mapping.CourseResourceMining;
import de.lemo.dms.db.mapping.CourseScormMining;
import de.lemo.dms.db.mapping.CourseUserMining;
import de.lemo.dms.db.mapping.CourseWikiMining;
import de.lemo.dms.db.mapping.ForumLogMining;
import de.lemo.dms.db.mapping.ForumMining;
import de.lemo.dms.db.mapping.GroupMining;
import de.lemo.dms.db.mapping.GroupUserMining;
import de.lemo.dms.db.mapping.LevelMining;
import de.lemo.dms.db.mapping.PlatformMining;
import de.lemo.dms.db.mapping.QuestionLogMining;
import de.lemo.dms.db.mapping.QuestionMining;
import de.lemo.dms.db.mapping.QuizLogMining;
import de.lemo.dms.db.mapping.QuizMining;
import de.lemo.dms.db.mapping.QuizQuestionMining;
import de.lemo.dms.db.mapping.QuizUserMining;
import de.lemo.dms.db.mapping.ResourceLogMining;
import de.lemo.dms.db.mapping.ResourceMining;
import de.lemo.dms.db.mapping.RoleMining;
import de.lemo.dms.db.mapping.ScormLogMining;
import de.lemo.dms.db.mapping.ScormMining;
import de.lemo.dms.db.mapping.UserMining;
import de.lemo.dms.db.mapping.WikiLogMining;
import de.lemo.dms.db.mapping.WikiMining;
import de.lemo.dms.db.mapping.abstractions.ILogMining;

/**
 * Class for data-retrieval from Clix platforms.
 * 
 * @author s.schwarzrock
 */
public class ClixImporter {
	
	private static final int MAGIC_THOU = 1000;
	
	private Long maxLog = 0L;

	/** The resource log max. */
	private Long resourceLogMax = 0L;

	/** The chat log max. */
	private Long chatLogMax = 0L;

	/** The assignment log max. */
	private Long assignmentLogMax = 0L;

	/** The course log max. */
	private Long courseLogMax = 0L;

	/** The forum log max. */
	private Long forumLogMax = 0L;

	/** The question log max. */
	private Long questionLogMax = 0L;

	/** The quiz log max. */
	private Long quizLogMax = 0L;

	/** The scorm log max. */
	private Long scormLogMax = 0L;

	/** The wiki log max. */
	private Long wikiLogMax = 0L;

	/** The bi track content impressions. */
	private List<BiTrackContentImpressions> biTrackContentImpressions;
	/** The chat protocol. */
	private List<ChatProtocol> chatProtocol;
	/** The chat protocol. */
	private List<LearningLog> learningLog;
	/** The exercise group. */
	private List<ExerciseGroup> exerciseGroup;
	/** The TQtiContentComposing. */
	private List<TQtiContentComposing> tQtiContentComposing;
	/** The e component. */
	private Map<Long, EComponent> eComponentMap;
	/** The e component type. */
	private List<EComponentType> eComponentType;
	/** The e component type. */
	private List<TQtiTestPlayer> tQtiTestPlayer;
	/** The e composing. */
	private List<EComposing> eComposing;
	/** The exercise personalised. */
	private List<ExercisePersonalised> exercisePersonalised;
	/** The tQti TestPlayer Responses. */
	private List<TQtiTestPlayerResp> tQtiTestPlayerResp;
	/** The forum entry. */
	private List<ForumEntry> forumEntry;
	/** The forum entry state. */
	private List<ForumEntryState> forumEntryState;
	/** The person. */
	private List<Person> person;
	/** The person component assignment. */
	private List<PersonComponentAssignment> personComponentAssignment;
	/** The platform group. */
	private List<PlatformGroup> platformGroup;
	/** The platform group specification. */
	private List<PlatformGroupSpecification> platformGroupSpecification;
	/** The portfolio. */
	private List<Portfolio> portfolio;
	/** The portfolio log. */
	private List<PortfolioLog> portfolioLog;
	/** The scorm session times. */
	private List<ScormSessionTimes> scormSessionTimes;
	/** The team exercise group. */
	private List<TeamExerciseGroup> teamExerciseGroup;
	/** The t group full specification. */
	private List<TGroupFullSpecification> tGroupFullSpecification;
	/** The t qti content. */
	private List<TQtiContent> tQtiContent;
	/** The t qti content structure. */
	private List<TQtiContentStructure> tQtiContentStructure;
	/** The t qti test item d structure. */
	private List<TQtiTestItemD> tQtiTestItemD;
	/** The t qti eval assessment. */
	private List<TQtiEvalAssessment> tQtiEvalAssessment;
	/** The wiki entry. */
	private List<WikiEntry> wikiEntry;

	/** The e composing map. */
	private Map<Long, EComposing> eComposingMap;

	/** The course_mining. */
	private Map<Long, CourseMining> courseMining;

	/** The platform_mining. */
	private Map<Long, PlatformMining> platformMining;
	/** The quiz_mining. */
	private Map<Long, QuizMining> quizMining;
	/** The assignment_mining. */
	private Map<Long, AssignmentMining> assignmentMining;
	/** The scorm_mining. */
	private Map<Long, ScormMining> scormMining;
	/** The forum_mining. */
	private Map<Long, ForumMining> forumMining;
	/** The resource_mining. */
	private Map<Long, ResourceMining> resourceMining;
	/** The user_mining. */
	private Map<Long, UserMining> userMining;
	/** The wiki_mining. */
	private Map<Long, WikiMining> wikiMining;
	/** The group_mining. */
	private Map<Long, GroupMining> groupMining;
	/** The question_mining. */
	private Map<Long, QuestionMining> questionMining;
	/** The role_mining. */
	private Map<Long, RoleMining> roleMining;
	/** The chat_mining. */
	private Map<Long, ChatMining> chatMining;
	/** The level_mining. */
	private Map<Long, LevelMining> oldLevelMining;
	/** The quiz_question_mining. */
	private Map<Long, QuizQuestionMining> quizQuestionMining;
	/** The course_quiz_mining. */
	private Map<Long, CourseQuizMining> courseQuizMining;
	/** The course_chat_mining. */
	private Map<Long, CourseChatMining> courseChatMining;
	/** The course_user_mining. */
	private Map<Long, CourseUserMining> courseUserMining;
	/** The course_assignment_mining. */
	private Map<Long, CourseAssignmentMining> courseAssignmentMining;
	/** The course_scorm_mining. */
	private Map<Long, CourseScormMining> courseScormMining;
	/** The course_forum_mining. */
	private Map<Long, CourseForumMining> courseForumMining;
	/** The course_group_mining. */
	private Map<Long, CourseGroupMining> courseGroupMining;
	/** The course_resource_mining. */
	private Map<Long, CourseResourceMining> courseResourceMining;
	/** The course_wiki_mining. */
	private Map<Long, CourseWikiMining> courseWikiMining;
	/** The group_user_mining. */
	private Map<Long, GroupUserMining> groupUserMining;
	/** The quiz_user_mining. */
	private Map<Long, QuizUserMining> quizUserMining;

	// Maps of mining-objects that have been found in a previous extraction process

	/** The old_course_mining. */
	private Map<Long, CourseMining> oldCourseMining;
	/** The old_quiz_mining. */
	private Map<Long, QuizMining> oldQuizMining;
	/** The old_assignment_mining. */
	private Map<Long, AssignmentMining> oldAssignmentMining;
	/** The old_scorm_mining. */
	private Map<Long, ScormMining> oldScormMining;
	/** The old_forum_mining. */
	private Map<Long, ForumMining> oldForumMining;
	/** The old_resource_mining. */
	private Map<Long, ResourceMining> oldResourceMining;
	/** The old_user_mining. */
	private Map<Long, UserMining> oldUserMining;
	/** The old_wiki_mining. */
	private Map<Long, WikiMining> oldWikiMining;
	/** The old_group_mining. */
	private Map<Long, GroupMining> oldGroupMining;
	/** The old_question_mining. */
	private Map<Long, QuestionMining> oldQuestionMining;
	/** The old_role_mining. */
	private Map<Long, RoleMining> oldRoleMining;
	/** The old_chat_mining. */
	private Map<Long, ChatMining> oldChatMining;

	private final Logger logger = Logger.getLogger(this.getClass());

	private final IConnector connector;
	
	/** Database-handler **/
	private IDBHandler dbHandler;

	public ClixImporter(final IConnector connector) {
		this.connector = connector;
	}

	/**
	 * Performs a extraction process for an entire Clix2010 database.
	 * 
	 * @param dbConfig config-object for database connection
	 * @param courses List of course-ids for course-data that shall be imported. 
	 * 				  If empty a all courses are imported.
	 */
	public void getClixData(final DBConfigObject dbConfig, List<Long> courses, List<String> logins)
	{
		final Clock c = new Clock();
		final Long starttime = System.currentTimeMillis() / MAGIC_THOU;
		this.dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

		this.platformMining = new HashMap<Long, PlatformMining>();
		// Do Import

		long startTime = this.initialize();

		this.logger.info("\n" + c.getAndReset() + " (initializing)" + "\n");

		this.loadData(dbConfig, courses, logins, startTime);
		this.logger.info(c.getAndReset() + " (loading data)" );
		this.saveData();
		this.logger.info(c.getAndReset() + " (saving data)" );

		// Create and save config-object
		final Long endtime = System.currentTimeMillis() / MAGIC_THOU;
		final ConfigMining config = new ConfigMining();
		config.setLastModifiedLong(System.currentTimeMillis());
		config.setElapsedTime((endtime) - (starttime));
		config.setDatabaseModel("1.3");
		config.setPlatform(this.connector.getPlatformId());
		//To stop rounding errors
		config.setLatestTimestamp(this.maxLog);

		final Session miningSession = this.dbHandler.getMiningSession();
		this.dbHandler.saveToDB(miningSession, config);
		dbHandler.closeSession(miningSession);
	}

	/**
	 * Performs a data-extraction for a Clix2010 database for all objects that 
	 * are newer than the given time stamp. Performs import in 7 day intervalls.
	 * 
	 * @param dbConfig 	Config-object for database connection
	 * @param startTime	The start time
	 * @param courses 	List of course-ids for course-data that shall be imported. 
	 * 				  	If empty a all courses are imported.

	 */
	public void updateClixData(final DBConfigObject dbConfig, Long startTime, List<Long> courses, List<String> logins)
	{
		final Long currentSysTime = System.currentTimeMillis() / MAGIC_THOU;
		Long upperLimit = 0L;
		this.dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

		this.platformMining = new HashMap<Long, PlatformMining>();

		while (startTime <= currentSysTime)
		{
			// increase upper limit successively
			upperLimit = startTime + 604800L;

			this.initialize();

			// Do Update
			this.loadData(dbConfig, startTime, upperLimit, courses, logins);

			this.saveData();

			startTime = upperLimit;
		}

		final Long endtime = System.currentTimeMillis() / MAGIC_THOU;
		final ConfigMining config = new ConfigMining();
		config.setLastModifiedLong(System.currentTimeMillis());
		config.setElapsedTime((endtime) - (currentSysTime));
		config.setDatabaseModel("1.3");
		config.setPlatform(this.connector.getPlatformId());
		config.setLatestTimestamp(this.maxLog);
		final Session session = this.dbHandler.getMiningSession();
		this.dbHandler.saveToDB(session, config);
		this.dbHandler.closeSession(session);
	}

	/**
	 * Generates and saves all objects.
	 */
	private void saveData()
	{
		try {
			final List<Collection<?>> updates = new ArrayList<Collection<?>>();

			if (this.userMining == null)
			{
				updates.add(this.platformMining.values());

				this.courseMining = this.generateCourseMining();
				updates.add(this.courseMining.values());

				this.quizMining = this.generateQuizMining();
				updates.add(this.quizMining.values());

				this.assignmentMining = this.generateAssignmentMining();
				updates.add(this.assignmentMining.values());

				this.scormMining = this.generateScormMining();
				updates.add(this.scormMining.values());

				this.forumMining = this.generateForumMining();
				updates.add(this.forumMining.values());

				this.resourceMining = this.generateResourceMining();
				updates.add(this.resourceMining.values());

				this.wikiMining = this.generateWikiMining();
				updates.add(this.wikiMining.values());

				this.userMining = this.generateUserMining();
				updates.add(this.userMining.values());

				this.groupMining = this.generateGroupMining();
				updates.add(this.groupMining.values());

				this.questionMining = this.generateQuestionMining();
				updates.add(this.questionMining.values());

				this.roleMining = this.generateRoleMining();
				updates.add(this.roleMining.values());

				this.chatMining = this.generateChatMining();
				updates.add(this.chatMining.values());

				this.logger.info(">>AssociationObjects<<");

				this.courseUserMining = this.generateCourseUserMining();
				updates.add(this.courseUserMining.values());

				this.quizQuestionMining = this.generateQuizQuestionMining();
				updates.add(this.quizQuestionMining.values());

				this.courseQuizMining = this.generateCourseQuizMining();
				updates.add(this.courseQuizMining.values());
				
				this.courseChatMining = generateCourseChatMining();
				updates.add(this.courseChatMining.values());

				//
				this.courseAssignmentMining = this.generateCourseAssignmentMining();
				updates.add(this.courseAssignmentMining.values());

				this.courseScormMining = this.generateCourseScormMining();
				updates.add(this.courseScormMining.values());

				this.courseForumMining = this.generateCourseForumMining();
				updates.add(this.courseForumMining.values());

				this.courseGroupMining = this.generateCourseGroupMining();
				updates.add(this.courseGroupMining.values());

				this.courseWikiMining = this.generateCourseWikiMining();
				updates.add(this.courseWikiMining.values());

				this.courseResourceMining = this.generateCourseResourceMining();
				updates.add(this.courseResourceMining.values());

				this.groupUserMining = this.generateGroupUserMining();
				updates.add(this.groupUserMining.values());

				this.quizUserMining = this.generateQuizUserMining();
				updates.add(this.quizUserMining.values());
				


			}

			this.logger.info(">>LogObjects<<");
			
			updates.add(this.courseAssignmentMining.values());
			
			updates.add(this.generateAssignmentLogMining().values());

			updates.add(this.generateCourseLogMining().values());
			
			updates.add(this.generateForumLogMining().values());
			
			updates.add(this.generateQuizLogMining().values());
			
			updates.add(this.generateQuestionLogMining().values());
			
			updates.add(this.generateScormLogMining().values());
			
			updates.add(this.generateResourceLogMining().values());
			
			updates.add(this.generateWikiLogMining().values());

			updates.add(generateILogMining());
			
			updates.add(this.generateChatLogMining().values());

			Long objects = 0L;

			for (final Collection<?> collection : updates)
			{
				objects += collection.size();
			}

			if (objects > 0)
			{

				final Session session = this.dbHandler.getMiningSession();
				this.logger.info("Writing to DB");
				dbHandler.saveCollectionToDB(session, updates);
			} else {
				this.logger.info("No new objects found.");
			}

			updates.clear();
			this.clearSourceData();
			
			
		} catch (final Exception e) {
			logger.error(this + e.getMessage());
		}
	}

	/**
	 * Clears all data-tables.
	 */
	public void clearSourceData()
	{
		this.biTrackContentImpressions.clear();
		this.chatProtocol.clear();
		this.exerciseGroup.clear();
		this.eComponentMap.clear();
		this.eComponentType.clear();
		this.eComposing.clear();
		this.exercisePersonalised.clear();
		this.forumEntry.clear();
		this.forumEntryState.clear();
		this.person.clear();
		this.personComponentAssignment.clear();
		this.platformGroup.clear();
		this.platformGroupSpecification.clear();
		this.portfolio.clear();
		this.portfolioLog.clear();
		this.scormSessionTimes.clear();
		this.teamExerciseGroup.clear();
		this.tGroupFullSpecification.clear();
		this.tQtiContent.clear();
		this.tQtiEvalAssessment.clear();
		this.wikiEntry.clear();

	}
	

	/**
	 * Looks, if there are already values in the Mining database and loads them, if necessary.
	 * 
	 */
	@SuppressWarnings("unchecked")
	private Long initialize()
	{
		Long readingtimestamp;
		
		try {

			// accessing DB by creating a session and a transaction using HibernateUtil
			final Session session = this.dbHandler.getMiningSession();

			ArrayList<?> l;
			
			readingtimestamp = (Long) session.createQuery("Select max(latestTimestamp) from ConfigMining where platform=" + this.connector.getPlatformId()).uniqueResult();
			
			
			if(readingtimestamp == null)
			{
				readingtimestamp = 0L;
				this.oldAssignmentMining = new HashMap<Long, AssignmentMining>();
				this.oldChatMining = new HashMap<Long, ChatMining>();
				this.oldCourseMining = new HashMap<Long, CourseMining>();
				this.oldForumMining = new HashMap<Long, ForumMining>();
				this.oldGroupMining = new HashMap<Long, GroupMining>();
				this.oldLevelMining = new HashMap<Long, LevelMining>();
				this.oldQuestionMining = new HashMap<Long, QuestionMining>();
				this.oldQuizMining = new HashMap<Long, QuizMining>();
				this.oldResourceMining = new HashMap<Long, ResourceMining>();
				this.oldRoleMining = new HashMap<Long, RoleMining>();
				this.oldScormMining = new HashMap<Long, ScormMining>();
				this.oldUserMining = new HashMap<Long, UserMining>();
				this.oldWikiMining = new HashMap<Long, WikiMining>();
				
				this.resourceLogMax = 0L;
				this.chatLogMax = 0L;
				this.assignmentLogMax = 0L;
				this.courseLogMax = 0L;
				this.forumLogMax = 0L;
				this.questionLogMax = 0L;
				this.quizLogMax = 0L;
				this.scormLogMax = 0L;
				this.wikiLogMax = 0L;
			}
			else
			{
				Query logCount = session.createQuery("select max(log.id) from ResourceLogMining log");
				this.resourceLogMax = ((ArrayList<Long>) logCount.list()).get(0);
				if (this.resourceLogMax == null) {
					this.resourceLogMax = 0L;
				}
	
				logCount = session.createQuery("select max(log.id) from ChatLogMining log");
				this.chatLogMax = ((ArrayList<Long>) logCount.list()).get(0);
				if (this.chatLogMax == null) {
					this.chatLogMax = 0L;
				}
	
				logCount = session.createQuery("select max(log.id) from AssignmentLogMining log");
				this.assignmentLogMax = ((ArrayList<Long>) logCount.list()).get(0);
				if (this.assignmentLogMax == null) {
					this.assignmentLogMax = 0L;
				}
	
				logCount = session.createQuery("select max(log.id) from CourseLogMining log");
				this.courseLogMax = ((ArrayList<Long>) logCount.list()).get(0);
				if (this.courseLogMax == null) {
					this.courseLogMax = 0L;
				}
	
				logCount = session.createQuery("select max(log.id) from ForumLogMining log");
				this.forumLogMax = ((ArrayList<Long>) logCount.list()).get(0);
				if (this.forumLogMax == null) {
					this.forumLogMax = 0L;
				}
	
				logCount = session.createQuery("select max(log.id) from QuestionLogMining log");
				this.questionLogMax = ((ArrayList<Long>) logCount.list()).get(0);
				if (this.questionLogMax == null) {
					this.questionLogMax = 0L;
				}
	
				logCount = session.createQuery("select max(log.id) from QuizLogMining log");
				this.quizLogMax = ((ArrayList<Long>) logCount.list()).get(0);
				if (this.quizLogMax == null) {
					this.quizLogMax = 0L;
				}
	
				logCount = session.createQuery("select max(log.id) from ScormLogMining log");
				this.scormLogMax = ((ArrayList<Long>) logCount.list()).get(0);
				if (this.scormLogMax == null) {
					this.scormLogMax = 0L;
				}
	
				logCount = session.createQuery("select max(log.id) from WikiLogMining log");
				this.wikiLogMax = ((ArrayList<Long>) logCount.list()).get(0);
				if (this.wikiLogMax == null) {
					this.wikiLogMax = 0L;
				}
	
				final Query oldCourse = session.createQuery("from CourseMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<CourseMining>) oldCourse.list();
				this.oldCourseMining = new HashMap<Long, CourseMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldCourseMining.put(Long.valueOf(((CourseMining) l.get(i)).getId()), (CourseMining) l.get(i));
				}
				this.logger.info("Read " + this.oldCourseMining.size() + " old CourseMinings.");
	
				final Query oldQuiz = session.createQuery("from QuizMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<QuizMining>) oldQuiz.list();
				this.oldQuizMining = new HashMap<Long, QuizMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldQuizMining.put(Long.valueOf(((QuizMining) l.get(i)).getId()), (QuizMining) l.get(i));
				}
				this.logger.info("Read " + this.oldQuizMining.size() + " old QuizMinings.");
	
				final Query oldAssignment = session.createQuery("from AssignmentMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<AssignmentMining>) oldAssignment.list();
				this.oldAssignmentMining = new HashMap<Long, AssignmentMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldAssignmentMining.put(Long.valueOf(((AssignmentMining) l.get(i)).getId()),
							(AssignmentMining) l.get(i));
				}
				this.logger.info("Read " + this.oldAssignmentMining.size() + " old AssignmentMinings.");
	
				final Query oldScorm = session.createQuery("from ScormMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<ScormMining>) oldScorm.list();
				this.oldScormMining = new HashMap<Long, ScormMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldScormMining.put(Long.valueOf(((ScormMining) l.get(i)).getId()), (ScormMining) l.get(i));
				}
				this.logger.info("Read " + this.oldScormMining.size() + " old ScormMinings.");
	
				final Query oldForum = session.createQuery("from ForumMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<ForumMining>) oldForum.list();
				this.oldForumMining = new HashMap<Long, ForumMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldForumMining.put(Long.valueOf(((ForumMining) l.get(i)).getId()), (ForumMining) l.get(i));
				}
				this.logger.info("Read " + this.oldForumMining.size() + " old ForumMinings.");
	
				final Query oldResource = session.createQuery("from ResourceMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<ResourceMining>) oldResource.list();
				this.oldResourceMining = new HashMap<Long, ResourceMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldResourceMining
							.put(Long.valueOf(((ResourceMining) l.get(i)).getId()), (ResourceMining) l.get(i));
				}
				this.logger.info("Read " + this.oldResourceMining.size() + " old ForumMinings.");
	
				final Query oldUser = session.createQuery("from UserMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<UserMining>) oldUser.list();
				this.oldUserMining = new HashMap<Long, UserMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldUserMining.put(Long.valueOf(((UserMining) l.get(i)).getId()), (UserMining) l.get(i));
				}
				this.logger.info("Read " + this.oldUserMining.size() + " old UserMinings.");
	
				final Query oldWiki = session.createQuery("from WikiMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<WikiMining>) oldWiki.list();
				this.oldWikiMining = new HashMap<Long, WikiMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldWikiMining.put(Long.valueOf(((WikiMining) l.get(i)).getId()), (WikiMining) l.get(i));
				}
				this.logger.info("Read " + this.oldWikiMining.size() + " old WikiMinings.");
	
				final Query oldGroup = session.createQuery("from GroupMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<GroupMining>) oldGroup.list();
				this.oldGroupMining = new HashMap<Long, GroupMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldGroupMining.put(Long.valueOf(((GroupMining) l.get(i)).getId()), (GroupMining) l.get(i));
				}
				this.logger.info("Read " + this.oldGroupMining.size() + " old GroupMinings.");
	
				final Query oldQuestion = session.createQuery("from QuestionMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<QuestionMining>) oldQuestion.list();
				this.oldQuestionMining = new HashMap<Long, QuestionMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldQuestionMining
							.put(Long.valueOf(((QuestionMining) l.get(i)).getId()), (QuestionMining) l.get(i));
				}
				this.logger.info("Read " + this.oldQuestionMining.size() + " old QuestionMinings.");
	
				final Query oldRole = session.createQuery("from RoleMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<RoleMining>) oldRole.list();
				this.oldRoleMining = new HashMap<Long, RoleMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldRoleMining.put(Long.valueOf(((RoleMining) l.get(i)).getId()), (RoleMining) l.get(i));
				}
				this.logger.info("Read " + this.oldRoleMining.size() + " old RoleMinings.");
	
				final Query oldLevel = session.createQuery("from LevelMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<LevelMining>) oldLevel.list();
				this.oldLevelMining = new HashMap<Long, LevelMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldLevelMining.put(Long.valueOf(((LevelMining) l.get(i)).getId()), (LevelMining) l.get(i));
				}
				this.logger.info("Read " + this.oldLevelMining.size() + " old LevelMinings.");
	
				final Query oldChat = session.createQuery("from ChatMining x where x.platform="
						+ this.connector.getPlatformId() + " order by x.id asc");
				l = (ArrayList<ChatMining>) oldChat.list();
				this.oldChatMining = new HashMap<Long, ChatMining>();
				for (int i = 0; i < l.size(); i++) {
					this.oldChatMining.put(Long.valueOf(((ChatMining) l.get(i)).getId()), (ChatMining) l.get(i));
				}
				this.logger.info("Read " + this.oldChatMining.size() + " old ChatMinings.");
			}
			
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
			return null;
		}
		
		return readingtimestamp;
	}

	/**
	 * Loads all necessary tables from the Clix2010 database.
	 */
	@SuppressWarnings("unchecked")
	private void loadData(final DBConfigObject dbConfig, List<Long> courses, List<String> logins, long startTime)
	{

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = ClixHibernateUtil.getSessionFactory(dbConfig).openSession();
		
		boolean hasCR = false;
		if(courses != null && courses.size() > 0)
			hasCR = true; 
		boolean empty = false;
		
		if(logins != null && !logins.isEmpty())
		{
			List<Long> newCourses = new ArrayList<Long>();
			Criteria criteria = session.createCriteria(Person.class, "obj");
			criteria.add(CriteriaHelper.in("obj.login", logins));
			List<Long> usId = new ArrayList<Long>();
			
			for(Person p : (List<Person>) criteria.list())
				usId.add(p.getId());
			
			if(!usId.isEmpty())
			{
				criteria = session.createCriteria(PersonComponentAssignment.class, "obj");
				criteria.add(CriteriaHelper.in("obj.person", usId));
				for(PersonComponentAssignment pca : (List<PersonComponentAssignment>) criteria.list())
					newCourses.add(pca.getComponent());
				
				courses.addAll(newCourses);
				hasCR = true;
				logger.info(newCourses.toString());
				
			}
			
		}
		
		

		this.logger.info("Starting data extraction.");
		
		// The Clix database uses date representation of the type varchar, so the unix-timestamp has to be converted
		// to a string
		String startStr = TimeConverter.getStringRepresentation(startTime);
		

		
		//Get QTiTestPlayerResp tables
		this.tQtiTestPlayerResp = new ArrayList<TQtiTestPlayerResp>();
		Criteria criteria = session.createCriteria(TQtiTestPlayerResp.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.container", courses));
		}
		criteria.add(Restrictions.gt("obj.evaluationDate", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		
		this.tQtiTestPlayerResp = criteria.list();
		logger.info("TQtiTestPlayerResp tables: " + this.tQtiTestPlayerResp.size());
		
		//Get QTiTestPlayer tables
		this.tQtiTestPlayer = new ArrayList<TQtiTestPlayer>();
		criteria = session.createCriteria(TQtiTestPlayer.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.container", courses));
		}
		criteria.add(Restrictions.gt("obj.created", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.tQtiTestPlayer = criteria.list();
		logger.info("TQtiTestPlayer tables: " + this.tQtiTestPlayer.size());
		
		//Get EComposing tables
		this.eComposing = new ArrayList<EComposing>();
		criteria = session.createCriteria(EComposing.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.composing", courses));
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.eComposing = criteria.list();
		this.eComposingMap = new HashMap<Long, EComposing>();
		for (int i = 0; i < this.eComposing.size(); i++)
		{
			this.eComposingMap.put(this.eComposing.get(i).getComponent(), this.eComposing.get(i));
		}
		logger.info("EComposing tables: " + this.eComposing.size());
		
		//Get ExerciseGroup tables
		this.exerciseGroup = new ArrayList<ExerciseGroup>();
		criteria = session.createCriteria(ExerciseGroup.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.associatedCourse", courses));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.exerciseGroup = criteria.list();
		this.logger.info("ExerciseGroup tables: " + this.exerciseGroup.size());
		
		//Get ExercisePersonalised tables
		this.exercisePersonalised = new ArrayList<ExercisePersonalised>();
		criteria = session.createCriteria(ExercisePersonalised.class, "obj");
		if(hasCR)
		{
			Set<Long> ids = new HashSet<Long>();
			for(ExerciseGroup eg : this.exerciseGroup)
				ids.add(eg.getId());
			if(!(empty = ids.isEmpty()))
				criteria.add(CriteriaHelper.in("obj.community", ids));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.exercisePersonalised = criteria.list();
		else
			this.exercisePersonalised = new ArrayList<ExercisePersonalised>();
		
		this.logger.info("ExercisePersonalised tables: " + this.exercisePersonalised.size());

		Set<Long> tmpComp = new HashSet<Long>();
		
		
		
		//Get EComponent tables
		criteria = session.createCriteria(EComponent.class, "obj");			
		if(hasCR)
		{
			tmpComp.addAll(this.eComposingMap.keySet());
			tmpComp.addAll(courses);
			for(ExercisePersonalised eP : this.exercisePersonalised)
				tmpComp.add(eP.getExercise());
			if(!(empty = tmpComp.isEmpty()))
			{
				criteria.add(CriteriaHelper.in("obj.id", new ArrayList<Long>(tmpComp)));
				//criteria.add(CriteriaHelper.in("obj.id", tmpComp));
			}
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		final List<EComponent> tmp;
		if(!(hasCR && empty))
			tmp = criteria.list();
		else
			tmp = new ArrayList<EComponent>();			
		this.eComponentMap = new HashMap<Long, EComponent>();
		for (final EComponent c : tmp)
		{
			this.eComponentMap.put(c.getId(), c);
		}
		tmp.clear();
		this.logger.info("EComponent tables: " + this.eComponentMap.values().size());
		
		//Get TQtiContentStructure tables
		
		criteria = session.createCriteria(TQtiContentStructure.class, "obj");
		if(hasCR)
		{
			ArrayList<Long> newKeys = new ArrayList<Long>(this.eComposingMap.keySet());
			HashSet<Long> allKeys = new HashSet<Long>();
			while(!newKeys.isEmpty())
			{
				
				criteria = session.createCriteria(TQtiContentStructure.class, "obj");
				criteria.add(CriteriaHelper.in("obj.container", newKeys));
				List<TQtiContentStructure> t = criteria.list();
				newKeys.clear();
				for(TQtiContentStructure tqs : t )
				{
					newKeys.add(tqs.getContent());
					allKeys.add(tqs.getContainer());
				}
				allKeys.addAll(newKeys);
			}
			criteria = session.createCriteria(TQtiContentStructure.class, "obj");
			if(!(empty = allKeys.isEmpty()))
				criteria.add(CriteriaHelper.in("obj.container", allKeys));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.tQtiContentStructure = criteria.list();
		else
			this.tQtiContentStructure = new ArrayList<TQtiContentStructure>();
		this.logger.info("TQtiContentStructure tables: " + this.tQtiContentStructure.size());
		
		//Get TQtiContentComposing tables
		criteria = session.createCriteria(TQtiContentComposing.class, "obj");
		if(hasCR)
		{
			HashSet<Long> tmp1 = new HashSet<Long>();
			for(TQtiContentStructure tqs : this.tQtiContentStructure)
				tmp1.add(tqs.getContent());
			if(!(empty = tmp1.isEmpty()))
				criteria.add(CriteriaHelper.in("obj.container", tmp1));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.tQtiContentComposing = criteria.list();
		else
			this.tQtiContentComposing = new ArrayList<TQtiContentComposing>();
		logger.info("TQtiContentComposing tables: " + this.tQtiContentComposing.size());
		
		//Get TQtiContent tables
		criteria = session.createCriteria(TQtiContent.class, "obj");
		if(hasCR)
		{
			HashSet<Long> ids = new HashSet<Long>();
			for(TQtiContentStructure tqs : this.tQtiContentStructure)
			{
				ids.add(tqs.getContainer());
				ids.add(tqs.getContent());
			}
			for(TQtiContentComposing tqs : this.tQtiContentComposing)
			{
				ids.add(tqs.getContainer());
				ids.add(tqs.getContent());
			}
			if(!(empty = ids.isEmpty()))
				criteria.add(CriteriaHelper.in("obj.id", ids));
					
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.tQtiContent = criteria.list();
		else
			this.tQtiContent = new ArrayList<TQtiContent>();
		this.logger.info("TQtiContent tables: " + this.tQtiContent.size());
		
		//Get ChatProtocol tables
		criteria = session.createCriteria(ChatProtocol.class, "obj");
		if(hasCR)
		{
			HashSet<Long> tmp1 = new HashSet<Long>(this.eComposingMap.keySet());
			if(!(empty = tmp1.isEmpty()))
				criteria.add(CriteriaHelper.in("obj.chatroom", tmp1));
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.chatProtocol = criteria.list();
		else
			this.chatProtocol = new ArrayList<ChatProtocol>();
		logger.info("ChatProtocol tables: " + this.chatProtocol.size());

		//Get EComponentType tables
		criteria = session.createCriteria(EComponentType.class, "obj");
		if(hasCR)
		{
			Set<Long> ids = new HashSet<Long>();
			
			for(EComponent eg : this.eComponentMap.values())
			{
				ids.add(eg.getType());
			}
			//if(!(empty = ids.isEmpty()))
			//criteria.add(CriteriaHelper.in("obj.id", ids));
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.eComponentType = criteria.list();
		this.logger.info("EComponentType tables: " + this.eComponentType.size());
		
		
		
		//Get ForumEntry tables
		criteria = session.createCriteria(ForumEntry.class, "obj");
		if(hasCR)
		{
			if(!(empty = this.eComposingMap.isEmpty()))
			{
				criteria.add(CriteriaHelper.in("obj.forum", this.eComposingMap.keySet()));
			}
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!empty)
		{
			this.forumEntry = criteria.list();
		}
		else
			this.forumEntry = new ArrayList<ForumEntry>();
		this.logger.info("ForumEntry tables: " + this.forumEntry.size());
		

		
		//Get ForumEntryState tables
		/*criteria = session.createCriteria(ForumEntryState.class, "obj");
		if(hasCR)
		{
			if(!(empty = this.eComposingMap.isEmpty()))
			{
				criteria.add(CriteriaHelper.in("obj.forum", this.eComposingMap.keySet()));
			}
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!hasCR || !empty)
		{
			this.forumEntryState = criteria.list();
		}
		else*/
			this.forumEntryState = new ArrayList<ForumEntryState>();
		this.logger.info("ForumEntryState tables: " + this.forumEntryState.size());
		
		//Get LearningLog tables
		criteria = session.createCriteria(LearningLog.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.course", courses));
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.learningLog = criteria.list();
		this.logger.info("LearningLog tables: " + this.learningLog.size());
		
		//Get Portfolio tables
		criteria = session.createCriteria(Portfolio.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));			
		criteria.addOrder(Property.forName("obj.id").asc());
		this.portfolio = criteria.list();
		this.logger.info("Portfolio tables: " + this.portfolio.size());		
		
		//Get PortfolioLog tables
		criteria = session.createCriteria(PortfolioLog.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.portfolioLog = criteria.list();
		this.logger.info("PortfolioLog tables: " + this.portfolioLog.size());	
		
		//Get PersonComponentAssignment tables
		criteria = session.createCriteria(PersonComponentAssignment.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.personComponentAssignment = criteria.list();
		this.logger.info("PersonComponentAssignment tables: " + this.personComponentAssignment.size());	
		
		//Get Person tables
		criteria = session.createCriteria(Person.class, "obj");
		if(hasCR)
		{
			Set<Long> ids = new HashSet<Long>();
			for(Portfolio eg : this.portfolio)
				ids.add(eg.getPerson());
			for(ExercisePersonalised eP : this.exercisePersonalised)
				ids.add(eP.getUser());
			for(PersonComponentAssignment eP : this.personComponentAssignment)
				ids.add(eP.getPerson());
			if(!(empty = ids.isEmpty()))
				criteria.add(CriteriaHelper.in("obj.id", ids));
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.person = criteria.list();
		else
			this.person = new ArrayList<Person>();
		this.logger.info("Person tables: " + this.person.size());	
		

		
		//Get PlatformGroupSpecification tables
		/*criteria = session.createCriteria(PlatformGroupSpecification.class, "obj");
		if(hasCR)
		{
			Set<Long> ids = new HashSet<Long>();
			for(Person eg : this.person)
				ids.add(eg.getId());
			if(!(empty = ids.isEmpty()))
				criteria.add(CriteriaHelper.in("obj.person", ids));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.platformGroupSpecification = criteria.list();
		else*/
			this.platformGroupSpecification = new ArrayList<PlatformGroupSpecification>();
		this.logger.info("PlatformGroupSpecification tables: " + this.platformGroupSpecification.size());	
		
		//Get PlatformGroup tables
		criteria = session.createCriteria(PlatformGroup.class, "obj");
		if(hasCR)
		{
			Set<Long> ids = new HashSet<Long>();
			for(PlatformGroupSpecification eg : this.platformGroupSpecification)
				ids.add(eg.getGroup());
			if(!(empty = ids.isEmpty()))
				criteria.add(CriteriaHelper.in("obj.id", ids));
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.platformGroup = criteria.list();
		else
			this.platformGroup = new ArrayList<PlatformGroup>();
		this.logger.info("PlatformGroup tables: " + this.platformGroup.size());	
	
		//Get ScormSessionTimes tables
		criteria = session.createCriteria(ScormSessionTimes.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.scormSessionTimes = criteria.list();
		this.logger.info("ScormSessionTimes tables: " + this.scormSessionTimes.size());
		
		//Get TeamExerciseGroup tables
		criteria = session.createCriteria(TeamExerciseGroup.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.teamExerciseGroup = criteria.list();
		this.logger.info("TeamExerciseGroup tables: " + this.teamExerciseGroup.size());
		
		//Get TQtiTestItemD tables
		criteria = session.createCriteria(TQtiTestItemD.class, "obj");
		if(hasCR)
		{
			HashSet<Long> ids = new HashSet<Long>();
			for(TQtiContent tc : this.tQtiContent)
			{
				ids.add(tc.getId());
			}
			if(!(empty = ids.isEmpty()))
			{
				criteria.add(CriteriaHelper.in("obj.content", ids));
			}
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.tQtiTestItemD = criteria.list();
		else
			this.tQtiTestItemD = new ArrayList<TQtiTestItemD>();
		this.logger.info("TQtiTestItemD tables: " + this.tQtiTestItemD.size());	
		
		//Get TGroupFullSpecification tables
		criteria = session.createCriteria(TGroupFullSpecification.class, "obj");
		if(hasCR)
		{				
			
			Set<Long> ids = new HashSet<Long>();
			for(TeamExerciseGroup eg : this.teamExerciseGroup)
				ids.add(eg.getId());
			if(!(empty = ids.isEmpty()))
				criteria.add(CriteriaHelper.in("obj.group", ids));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.tGroupFullSpecification = criteria.list();
		else
			this.tGroupFullSpecification = new ArrayList<TGroupFullSpecification>();
		this.logger.info("TGroupFullSpecification tables: " + this.tGroupFullSpecification.size());
		

		
		//Get TQtiEvalAssessment tables
		criteria = session.createCriteria(TQtiEvalAssessment.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.add(Restrictions.gt("obj.lastInvocation", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.tQtiEvalAssessment = criteria.list();
		this.logger.info("TQtiEvalAssessment tables: " + this.tQtiEvalAssessment.size());
		
		//Get WikiEntry tables
		criteria = session.createCriteria(WikiEntry.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.add(Restrictions.gt("obj.lastUpdated", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.wikiEntry = criteria.list();
		this.logger.info("WikiEntry tables: " + this.wikiEntry.size());
		
		startStr = startStr.substring(0, startStr.indexOf(' '));
		
		//Get BiTrackContentImpressions tables
		criteria = session.createCriteria(BiTrackContentImpressions.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.container", courses));
		}
		criteria.add(Restrictions.gt("obj.dayOfAccess", startStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.biTrackContentImpressions = criteria.list();
		logger.info("BiTrackContentImpressions tables: " + this.biTrackContentImpressions.size());
		

		session.clear();
		session.close();
	}

	/**
	 * Loads all tables needed for the data-extraction from the Clix database.
	 * 
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 */
	@SuppressWarnings("unchecked")
	private void loadData(final DBConfigObject dbConfig, final Long start, final Long end, List<Long> courses, List<String> logins)
	{
		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = ClixHibernateUtil.getSessionFactory(dbConfig).openSession();
		
		boolean hasCR = false;
		if(courses != null && courses.size() > 0)
			hasCR = true; 
		
		boolean empty = true;
		
		if(logins != null && !logins.isEmpty())
		{
			List<Long> newCourses = new ArrayList<Long>();
			Criteria criteria = session.createCriteria(Person.class, "obj");
			criteria.add(CriteriaHelper.in("obj.login", logins));
			List<Long> usId = new ArrayList<Long>();
			
			for(Person p : (List<Person>) criteria.list())
				usId.add(p.getId());
			
			if(!usId.isEmpty())
			{
				criteria = session.createCriteria(PersonComponentAssignment.class, "obj");
				criteria.add(CriteriaHelper.in("obj.person", usId));
				for(PersonComponentAssignment pca : (List<PersonComponentAssignment>) criteria.list())
					newCourses.add(pca.getComponent());
				
				courses.addAll(newCourses);
				hasCR = true;
				
			}
			
		}

		this.logger.info("Starting data extraction.");

		Criteria criteria;
		
		// The Clix database uses date representation of the type varchar, so the unix-timestamp has to be converted
		// to a string
		String startStr = TimeConverter.getStringRepresentation(start);
		String endStr = TimeConverter.getStringRepresentation(end);
		
		// Read the tables that don't refer to log-entries once
		if (this.userMining == null)
		{

			
			//Get EComposing tables
			criteria = session.createCriteria(EComposing.class, "obj");
			if(hasCR)
			{
				criteria.add(CriteriaHelper.in("obj.composing", courses));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			this.eComposing = criteria.list();
			this.eComposingMap = new HashMap<Long, EComposing>();
			for (int i = 0; i < this.eComposing.size(); i++)
			{
				this.eComposingMap.put(this.eComposing.get(i).getComponent(), this.eComposing.get(i));
			}
			logger.info("EComposing tables: " + this.eComposing.size());
			
			criteria = session.createCriteria(Portfolio.class, "obj");
			if(hasCR)
			{
				criteria.add(CriteriaHelper.in("obj.component", courses));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			this.portfolio = criteria.list();
			this.logger.info("Portfolio tables: " + this.portfolio.size());
			
			//Get PersonComponentAssignment tables
			criteria = session.createCriteria(PersonComponentAssignment.class, "obj");
			if(hasCR)
			{
				criteria.add(CriteriaHelper.in("obj.component", courses));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			this.personComponentAssignment = criteria.list();
			this.logger.info("PersonComponentAssignment tables: " + this.personComponentAssignment.size());
			
			//Get ExerciseGroup tables
			criteria = session.createCriteria(ExerciseGroup.class, "obj");
			if(hasCR)
			{
				criteria.add(CriteriaHelper.in("obj.associatedCourse", courses));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			this.exerciseGroup = criteria.list();
			this.logger.info("ExerciseGroup tables: " + this.exerciseGroup.size());
			
			//Get ExercisePersonalised tables
			criteria = session.createCriteria(ExercisePersonalised.class, "obj");
			if(hasCR)
			{
				Set<Long> ids = new HashSet<Long>();
				for(ExerciseGroup eg : this.exerciseGroup)
					ids.add(eg.getId());
				if(!(empty = ids.isEmpty()))
					criteria.add(CriteriaHelper.in("obj.community", ids));
			}
			criteria.add(Restrictions.between("obj.uploadDate", startStr, endStr));
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.exercisePersonalised = criteria.list();
			else
				this.exercisePersonalised = new ArrayList<ExercisePersonalised>();
			this.logger.info("ExercisePersonalised tables: " + this.exercisePersonalised.size());		
			
			//Get EComponent tables
			Set<Long> tmpComp = new HashSet<Long>();
			criteria = session.createCriteria(EComponent.class, "obj");
			if(hasCR)
			{
				tmpComp.addAll(this.eComposingMap.keySet());
				tmpComp.addAll(courses);
				for(ExercisePersonalised eP : this.exercisePersonalised)
					tmpComp.add(eP.getExercise());
				if(!(empty = tmpComp.isEmpty()))
					criteria.add(CriteriaHelper.in("obj.id", tmpComp));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			final List<EComponent> tmp;
			if(!(hasCR && empty))
				tmp = criteria.list();
			else
				tmp = new ArrayList<EComponent>();	
			this.eComponentMap = new HashMap<Long, EComponent>();
			for (final EComponent c : tmp) {
				this.eComponentMap.put(c.getId(), c);
			}
			tmp.clear();
			this.logger.info("EComponent tables: " + this.eComponentMap.values().size());

			//Get EComponentType tables
			criteria = session.createCriteria(EComponentType.class, "obj");
			if(hasCR)
			{
				Set<Long> ids = new HashSet<Long>();
				
				for(EComponent eg : this.eComponentMap.values())
				{
					ids.add(eg.getType());
				}
				//if(!(empty = ids.isEmpty()))
				//criteria.add(CriteriaHelper.in("obj.id", ids));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			this.eComponentType = criteria.list();
			this.logger.info("EComponentType tables: " + this.eComponentType.size());
			

			
			//Get TQtiContentStructure tables
			criteria = session.createCriteria(TQtiContentStructure.class, "obj");
			if(hasCR)
			{
				ArrayList<Long> newKeys = new ArrayList<Long>(this.eComposingMap.keySet());
				HashSet<Long> allKeys = new HashSet<Long>();
				while(!newKeys.isEmpty())
				{
					
					criteria = session.createCriteria(TQtiContentStructure.class, "obj");
					criteria.add(CriteriaHelper.in("obj.container", newKeys));
					List<TQtiContentStructure> t = criteria.list();
					newKeys.clear();
					for(TQtiContentStructure tqs : t )
					{
						newKeys.add(tqs.getContent());
						allKeys.add(tqs.getContainer());
					}
					allKeys.addAll(newKeys);
				}
				criteria = session.createCriteria(TQtiContentStructure.class, "obj");
				if(!(empty = allKeys.isEmpty()))
					criteria.add(CriteriaHelper.in("obj.container", allKeys));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.tQtiContentStructure = criteria.list();
			else
				this.tQtiContentStructure = new ArrayList<TQtiContentStructure>();
			this.logger.info("TQtiContentStructure tables: " + this.tQtiContentStructure.size());
			
			//Get TQtiContentComposing tables
			criteria = session.createCriteria(TQtiContentComposing.class, "obj");
			if(hasCR)
			{
				HashSet<Long> tmp1 = new HashSet<Long>();
				for(TQtiContentStructure tqs : this.tQtiContentStructure)
					tmp1.add(tqs.getContent());
				if(!(empty = tmp1.isEmpty()))
					criteria.add(CriteriaHelper.in("obj.container", tmp1));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.tQtiContentComposing = criteria.list();
			else
				this.tQtiContentComposing = new ArrayList<TQtiContentComposing>();
			logger.info("TQtiContentComposing tables: " + this.tQtiContentComposing.size());

			//Get Person tables
			criteria = session.createCriteria(Person.class, "obj");
			if(hasCR)
			{
				Set<Long> ids = new HashSet<Long>();
				for(Portfolio eg : this.portfolio)
					ids.add(eg.getPerson());
				for(ExercisePersonalised eP : this.exercisePersonalised)
					ids.add(eP.getUser());
				for(PersonComponentAssignment eP : this.personComponentAssignment)
					ids.add(eP.getPerson());
				if(!(empty = ids.isEmpty()))
					criteria.add(CriteriaHelper.in("obj.id", ids));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.person = criteria.list();
			else
				this.person = new ArrayList<Person>();
			this.logger.info("Person tables: " + this.person.size());
			
			//Get PlatformGroupSpecification tables
			/*criteria = session.createCriteria(PlatformGroupSpecification.class, "obj");
			if(hasCR)
			{
				Set<Long> ids = new HashSet<Long>();
				for(Person eg : this.person)
					ids.add(eg.getId());
				if(!(empty = ids.isEmpty()))
					criteria.add(CriteriaHelper.in("obj.person", ids));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!hasCR || !empty)
				this.platformGroupSpecification = criteria.list();
			else*/
				this.platformGroupSpecification = new ArrayList<PlatformGroupSpecification>();
			this.logger.info("PlatformGroupSpecification tables: " + this.platformGroupSpecification.size());
			
			//Get PlatformGroup tables
			criteria = session.createCriteria(PlatformGroup.class, "obj");
			if(hasCR)
			{
				Set<Long> ids = new HashSet<Long>();
				for(PlatformGroupSpecification eg : this.platformGroupSpecification)
					ids.add(eg.getGroup());
				if(!(empty = ids.isEmpty()))
					criteria.add(CriteriaHelper.in("obj.id", ids));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.platformGroup = criteria.list();
			else
				this.platformGroup = new ArrayList<PlatformGroup>();
			this.logger.info("PlatformGroup tables: " + this.platformGroup.size());
			
			//Get TQtiContent tables
			criteria = session.createCriteria(TQtiContent.class, "obj");
			if(hasCR)
			{
				HashSet<Long> ids = new HashSet<Long>();
				for(TQtiContentStructure tqs : this.tQtiContentStructure)
				{
					ids.add(tqs.getContainer());
					ids.add(tqs.getContent());
				}
				for(TQtiContentComposing tqs : this.tQtiContentComposing)
				{
					ids.add(tqs.getContainer());
					ids.add(tqs.getContent());
				}
				if(!(empty = ids.isEmpty()))
					criteria.add(CriteriaHelper.in("obj.id", ids));
						
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.tQtiContent = criteria.list();
			else
				this.tQtiContent = new ArrayList<TQtiContent>();
			this.logger.info("TQtiContent tables: " + this.tQtiContent.size());
		}
			
			//Get TQtiTestItemD tables
			criteria = session.createCriteria(TQtiTestItemD.class, "obj");
			if(hasCR)
			{
				HashSet<Long> ids = new HashSet<Long>();
				for(TQtiContent tc : this.tQtiContent)
				{
					ids.add(tc.getId());
				}
				if(!(empty = ids.isEmpty()))
				{
					criteria.add(CriteriaHelper.in("obj.content", ids));
				}
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.tQtiTestItemD = criteria.list();
			else
				this.tQtiTestItemD = new ArrayList<TQtiTestItemD>();
			this.logger.info("TQtiTestItemD tables: " + this.tQtiTestItemD.size());	
			
			//Get TeamExerciseGroup tables
			criteria = session.createCriteria(TeamExerciseGroup.class, "obj");
			if(hasCR)
			{
				criteria.add(CriteriaHelper.in("obj.component", courses));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			this.teamExerciseGroup = criteria.list();
			this.logger.info("TeamExerciseGroup tables: " + this.teamExerciseGroup.size());
			
			//Get TGroupFullSpecification tables
			criteria = session.createCriteria(TGroupFullSpecification.class, "obj");
			if(hasCR)
			{				
				
				Set<Long> ids = new HashSet<Long>();
				for(TeamExerciseGroup eg : this.teamExerciseGroup)
					ids.add(eg.getId());
				if(!(empty = ids.isEmpty()))
					criteria.add(CriteriaHelper.in("obj.group", ids));
			}
			criteria.addOrder(Property.forName("obj.id").asc());
			if(!(hasCR && empty))
				this.tGroupFullSpecification = criteria.list();
			else
				this.tGroupFullSpecification = new ArrayList<TGroupFullSpecification>();
			this.logger.info("TGroupFullSpecification tables: " + this.tGroupFullSpecification.size());



		// Read log-data successively, using the time stamps

		//Get QTiTestPlayerResp tables
		criteria = session.createCriteria(TQtiTestPlayerResp.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.container", courses));
		}
		criteria.addOrder(Property.forName("obj.id").asc());
		this.tQtiTestPlayerResp = criteria.list();
		logger.info("TQtiTestPlayerResp tables: " + this.tQtiTestPlayerResp.size());
		
		//Get LearningLog tables
		criteria = session.createCriteria(LearningLog.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.course", courses));
		}
		criteria.add(Restrictions.between("obj.lastUpdated", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.learningLog = criteria.list();
		this.logger.info("LearningLog tables: " + this.learningLog.size());

		//Get Portfolio tables
		criteria = session.createCriteria(Portfolio.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.add(Restrictions.between("obj.lastUpdated", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.portfolio = criteria.list();
		this.logger.info("Portfolio tables: " + this.portfolio.size());

		//Get PortfolioLog tables
		criteria = session.createCriteria(PortfolioLog.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.add(Restrictions.between("obj.lastUpdated", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.portfolioLog = criteria.list();
		this.logger.info("PortfolioLog tables: " + this.portfolioLog.size());

		//Get ChatProtocol tables
		criteria = session.createCriteria(ChatProtocol.class, "obj");
		if(hasCR)
		{
			HashSet<Long> tmp1 = new HashSet<Long>(this.eComposingMap.keySet());
			if(!(empty = tmp1.isEmpty()))
				criteria.add(CriteriaHelper.in("obj.chatroom", tmp1));
		}
		criteria.add(Restrictions.between("obj.lastUpdated", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!(hasCR && empty))
			this.chatProtocol = criteria.list();
		else
			this.chatProtocol = new ArrayList<ChatProtocol>();
		this.logger.info("ChatProtocol tables: " + this.chatProtocol.size());

		//Get ForumEntry tables
		criteria = session.createCriteria(ForumEntry.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.forum", this.eComposingMap.keySet()));
		}
		criteria.add(Restrictions.between("obj.lastUpdated", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.forumEntry = criteria.list();
		this.logger.info("ForumEntry tables: " + this.forumEntry.size());

		//Get ForumEntryState tables
		criteria = session.createCriteria(ForumEntryState.class, "obj");
		if(hasCR)
		{
			if(!this.eComposingMap.isEmpty())
			criteria.add(CriteriaHelper.in("obj.forum", this.eComposingMap.keySet()));
		}
		criteria.add(Restrictions.between("obj.lastUpdated", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		if(!this.eComposingMap.isEmpty())
			this.forumEntryState = criteria.list();
		else
			this.forumEntryState = new ArrayList<ForumEntryState>();
		this.logger.info("ForumEntryState tables: " + this.forumEntryState.size());

		//Get TQtiEvalAssessment tables
		criteria = session.createCriteria(TQtiEvalAssessment.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.add(Restrictions.between("obj.lastInvocation", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.tQtiEvalAssessment = criteria.list();
		this.logger.info("TQtiEvalAssessment tables: " + this.tQtiEvalAssessment.size());
		
		//Get ScormSessionTimes tables
		criteria = session.createCriteria(ScormSessionTimes.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.add(Restrictions.between("obj.lastUpdated", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.scormSessionTimes = criteria.list();
		this.logger.info("ScormSessionTimes tables: " + this.scormSessionTimes.size());
		
		//Get WikiEntry tables
		criteria = session.createCriteria(WikiEntry.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.component", courses));
		}
		criteria.add(Restrictions.between("obj.lastUpdated", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.wikiEntry = criteria.list();
		this.logger.info("WikiEntry tables: " + this.wikiEntry.size());
		

		

		
		//Get QTiTestPlayer tables
		criteria = session.createCriteria(TQtiTestPlayer.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.container", courses));
		}
		criteria.add(Restrictions.between("obj.created", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.tQtiTestPlayer = criteria.list();
		logger.info("TQtiTestPlayer tables: " + this.tQtiTestPlayer.size());

		// The date-strings have to be modified, because the date format of the table BiTrackContentImpressions is
		// different
		startStr = startStr.substring(0, startStr.indexOf(' '));
		endStr = endStr.substring(0, endStr.indexOf(' '));


		//Get BiTrackContentImpressions tables
		criteria = session.createCriteria(BiTrackContentImpressions.class, "obj");
		if(hasCR)
		{
			criteria.add(CriteriaHelper.in("obj.container", courses));
		}
		criteria.add(Restrictions.between("obj.dayOfAccess", startStr, endStr));
		criteria.addOrder(Property.forName("obj.id").asc());
		this.biTrackContentImpressions = criteria.list();
		this.logger.info("BiTrackContentImpressions tables: " + this.biTrackContentImpressions.size());
		

		session.clear();
		session.close();

	}

	// Generators for "" objects

	/**
	 * Generates ChatMining-objects from the given data.
	 * 
	 * @return HashMap with ChatMining-objects
	 */
	private Map<Long, ChatMining> generateChatMining() {
		final HashMap<Long, ChatMining> chats = new HashMap<Long, ChatMining>();
		final HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
		for (final EComponentType loadedItem : this.eComponentType) {
			//if (loadedItem.getCharacteristicId() == 8L) {
				eCTypes.put(loadedItem.getId(), loadedItem);
			//}
		}
		try {
			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				EComponentType ect = eCTypes.get(loadedItem.getType());
				if (ect != null && (ect.getUploadDir().toLowerCase().contains("chat")) )
				{
					final ChatMining item = new ChatMining();
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setDescription(loadedItem.getDescription());
					item.setChatTime(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setPlatform(this.connector.getPlatformId());
					chats.put(item.getId(), item);
				}
			}

			this.logger.info("Generated " + chats.size() + " ChatMining");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return chats;
	}

	/**
	 * Generates ResourceMining-objects from the given data.
	 * 
	 * @return HashMap with ResourceMining-objects
	 */
	private Map<Long, ResourceMining> generateResourceMining()
	{
		final HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
		final HashMap<Long, ResourceMining> resources = new HashMap<Long, ResourceMining>();
		try {
			for (final EComponentType loadedItem : this.eComponentType)
			{
//				if ((loadedItem.getCharacteristicId() == 1L) || (loadedItem.getCharacteristicId() == 11L)) {
					eCTypes.put(loadedItem.getId(), loadedItem);
	//			}
			}

			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				final ResourceMining item = new ResourceMining();
				EComponentType ect = eCTypes.get(loadedItem.getType());
				if (ect != null && (ect.getUploadDir().toLowerCase().contains("reading") || 
						ect.getUploadDir().toLowerCase().contains("wbt") ||
						ect.getUploadDir().toLowerCase().contains("picture") ||
						ect.getUploadDir().toLowerCase().contains("htmlsite") ||
						ect.getUploadDir().toLowerCase().contains("video") ||
						ect.getUploadDir().toLowerCase().contains("linklist")))
						
				{
					Long id = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId());
					item.setId(id);
					item.setTitle(loadedItem.getName());
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setPlatform(this.connector.getPlatformId());

					if (eCTypes.get(loadedItem.getType()).getCharacteristicId() != null) {
						if (eCTypes.get(loadedItem.getType()).getCharacteristicId() == 1L) {
							item.setType("Medium");
						} else {
							item.setType("Folder");
						}
					}

					resources.put(item.getId(), item);
				}

			}
			this.logger.info("Generated " + resources.size() + " ResourceMining.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return resources;
	}

	/**
	 * Generates CourseMining-objects from the given data.
	 * 
	 * @return HashMap with CourseMining-objects
	 */
	private Map<Long, CourseMining> generateCourseMining()
	{
		final HashMap<Long, CourseMining> courses = new HashMap<Long, CourseMining>();
		try {
			final HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for (final EComponentType loadedItem : this.eComponentType)
			{
				//if ((loadedItem.getCharacteristicId() == 4L) || (loadedItem.getCharacteristicId() == 5L)
					//	|| (loadedItem.getCharacteristicId() == 3L)) {
					eCTypes.put(loadedItem.getId(), loadedItem);
				//}
			}
			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				EComponentType ect = eCTypes.get(loadedItem.getType());
				final CourseMining item = new CourseMining();
				if (ect != null && ect.getUploadDir().toLowerCase().contains("course"))
				{
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setStartDate(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(this.connector.getPlatformId());

					courses.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + courses.size() + " CourseMining.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return courses;
	}

	/**
	 * Generates UserMining-objects from the given data.
	 * 
	 * @return HashMap with UserMining-objects
	 */
	private Map<Long, UserMining> generateUserMining()
	{
		final HashMap<Long, UserMining> users = new HashMap<Long, UserMining>();
		try {
			for (final Person loadedItem : this.person)
			{
				final UserMining item = new UserMining();
				item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				item.setPlatform(this.connector.getPlatformId());
				item.setLastLogin(TimeConverter.getTimestamp(loadedItem.getLastLoginTime()));
				item.setFirstAccess(TimeConverter.getTimestamp(loadedItem.getFirstLoginTime()));
				
				if(TimeConverter.getTimestamp(loadedItem.getLastUpdated()) > maxLog)
					maxLog = TimeConverter.getTimestamp(loadedItem.getLastUpdated());

				if (loadedItem.getGender() == 1) {
					item.setGender(2);
				} else if (loadedItem.getGender() == 2) {
					item.setGender(1);
				}
				else
				{
					item.setGender(0);
				}
				item.setLogin(Encoder.createMD5(loadedItem.getLogin()));

				users.put(item.getId(), item);
			}
			this.logger.info("Generated " + users.size() + " UserMining.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return users;
	}

	/**
	 * Generates AssignmentMining-objects from the given data.
	 * 
	 * @return HashMap with AssignmentMining-objects
	 */
	private Map<Long, AssignmentMining> generateAssignmentMining()
	{
		final HashMap<Long, AssignmentMining> assignments = new HashMap<Long, AssignmentMining>();
		try {
			final HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for (final EComponentType loadedItem : this.eComponentType) {
				//if (loadedItem.getCharacteristicId() == 8L) {
					eCTypes.put(loadedItem.getId(), loadedItem);
				//}
			}

			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				EComponentType ect = eCTypes.get(loadedItem.getType());
				final AssignmentMining item = new AssignmentMining();
				if (ect != null && (ect.getUploadDir().toLowerCase().contains("teamlearning") || ect.getCharacteristicId() == 8L) )
				{
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					if (loadedItem.getStartDate() != null) {
						item.setTimeOpen(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					}
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(this.connector.getPlatformId());
					assignments.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + assignments.size() + " AssignmentMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return assignments;
	}

	/**
	 * Generates QuizMining-objects from the given data.
	 * 
	 * @return HashMap with QuizMining-objects
	 */
	private Map<Long, QuizMining> generateQuizMining()
	{
		final HashMap<Long, QuizMining> quizzes = new HashMap<Long, QuizMining>();
		try {
			final HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			final HashMap<Long, EComposing> eCompo = new HashMap<Long, EComposing>();
			final HashMap<Long, TQtiContent> tQtis = new HashMap<Long, TQtiContent>();
			
			for (final TQtiContent loadedItem : this.tQtiContent)
			{
				tQtis.put(loadedItem.getId(), loadedItem);
			}
			
			for (final EComponentType loadedItem : this.eComponentType)
			{
			//	if (loadedItem.getCharacteristicId() == 14L) {
					eCTypes.put(loadedItem.getId(), loadedItem);
		//		}
			}
			for (final EComposing loadedItem : this.eComposing)
			{
				eCompo.put(loadedItem.getComposing(), loadedItem);
			}
			
			
			
			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				final QuizMining item = new QuizMining();
				EComponentType ec = eCTypes.get(loadedItem.getType());
				if (ec != null && ec.getUploadDir().toLowerCase().contains("qti"))
				{
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setTimeOpen(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(this.connector.getPlatformId());
					if(tQtis.containsKey(loadedItem.getId()))
						item.setMaxGrade(tQtis.get(loadedItem.getId()).getScore());
					else
						item.setMaxGrade(0d);
					if (eCompo.get(loadedItem.getId()) != null) {
						item.setTimeClose(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getEndDate()));
					}
					quizzes.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + quizzes.size() + " QuizMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return quizzes;
	}

	/**
	 * Generates ForumMining-objects from the given data.
	 * 
	 * @return HashMap with ForumMining-objects
	 */
	private Map<Long, ForumMining> generateForumMining()
	{

		final HashMap<Long, ForumMining> forums = new HashMap<Long, ForumMining>();
		try {
			final HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for (final EComponentType loadedItem : this.eComponentType)
			{
				if (loadedItem.getCharacteristicId() == 2L) {
					eCTypes.put(loadedItem.getId(), loadedItem);
				}
			}
			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				final ForumMining item = new ForumMining();
				if ((eCTypes.get(loadedItem.getType()) != null)
						&& eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("forum"))
				{
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setSummary(loadedItem.getDescription());
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(this.connector.getPlatformId());
					forums.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + forums.size() + " ForumMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return forums;
	}

	/**
	 * Generates RoleMining-objects from the given data.
	 * 
	 * @return HashMap with RoleMining-objects
	 */
	private Map<Long, RoleMining> generateRoleMining()
	{
		this.roleMining = new HashMap<Long, RoleMining>();
		try {
			/*
			for (final PlatformGroup loadedItem : this.platformGroup)
			{
				final RoleMining item = new RoleMining();
				item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				item.setPlatform(this.connector.getPlatformId());
				switch (Integer.valueOf(loadedItem.getTypeId() + ""))
				{
					case 1: {
						item.setName("Standard");
						item.setShortname("Standard");
						item.setSortOrder(1L);
						item.setType(1);
						break;
					}
					case 2:
						item.setName("Admininstrator");
						item.setShortname("Administrator");
						item.setSortOrder(0L);
						item.setType(0);
						break;
					default:
						item.setName("Portal (extern)");
						item.setShortname("Portal");
						item.setSortOrder(2L);
						item.setType(2);
						break;
				}

				roles.put(item.getId(), item);
			}
			*/

			if(this.oldRoleMining.size() == 0)
			{
				
			
				final RoleMining r = new RoleMining();
				r.setId(0L);
				r.setDescription("Admin");
				r.setName("Admininstrator");
				r.setName("Admininstrator");
				r.setShortname("Administrator");
				r.setSortOrder(0L);
				r.setType(0);
				r.setPlatform(this.connector.getPlatformId());
				
				roleMining.put(r.getId(), r);
				
				final RoleMining r1 = new RoleMining();
				r1.setId(1L);
				r1.setDescription("Teacher");
				r1.setName("Teacher");
				r1.setName("Teacher");
				r1.setShortname("Teacher");
				r1.setSortOrder(1L);
				r1.setType(1);
				r1.setPlatform(this.connector.getPlatformId());
				
				roleMining.put(r1.getId(), r1);
				
				final RoleMining r2 = new RoleMining();
				r2.setId(2L);
				r2.setDescription("Student");
				r2.setName("Student");
				r2.setName("Student");
				r2.setShortname("Student");
				r2.setSortOrder(2L);
				r2.setType(2);
				r2.setPlatform(this.connector.getPlatformId());
				
				roleMining.put(r2.getId(), r2);
			}
			
			
			
			this.logger.info("Generated " + roleMining.size() + " RoleMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return roleMining;
	}

	/**
	 * Generates GroupMining-objects from the given data.
	 * 
	 * @return HashMap with GroupMining-objects
	 */
	private Map<Long, GroupMining> generateGroupMining()
	{
		final HashMap<Long, GroupMining> groups = new HashMap<Long, GroupMining>();

		try {
			for (final PlatformGroup loadedItem : this.platformGroup)
			{
				final GroupMining item = new GroupMining();
				item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreated()));
				item.setPlatform(this.connector.getPlatformId());

				groups.put(item.getId(), item);
			}
			this.logger.info("Generated " + groups.size() + " GroupMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return groups;
	}

	/**
	 * Generates WikiMining-objects from the given data.
	 * 
	 * @return HashMap with WikiMining-objects
	 */
	private Map<Long, WikiMining> generateWikiMining()
	{
		final HashMap<Long, WikiMining> wikis = new HashMap<Long, WikiMining>();

		try {
			final HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for (final EComponentType loadedItem : this.eComponentType)
			{
				if (loadedItem.getCharacteristicId() == 2L) {
					eCTypes.put(loadedItem.getId(), loadedItem);
				}
			}
			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				final WikiMining item = new WikiMining();
				if ((eCTypes.get(loadedItem.getType()) != null)
						&& eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("wiki") )
				{
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setSummary(loadedItem.getDescription());
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(this.connector.getPlatformId());

					wikis.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + wikis.size() + " WikiMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return wikis;
	}

	/**
	 * Generates DepartmentMining-objects from the given data.
	 * 
	 * @return HashMap with DepartmentMining-objects
	 */

	/**
	 * Generates DegreeMining-objects from the given data
	 * 
	 * @return HashMap with DegreeMining-objects
	 */
	/*
	 * logger.error(this + e.getMessage());
	 * }
	 * return degrees;
	 * }
	 */

	/**
	 * Generates QuestionMining-objects from the given data
	 * 
	 * @return HashMap with QuestionMining-objects
	 */
	private Map<Long, QuestionMining> generateQuestionMining()
	{
		final HashMap<Long, QuestionMining> questions = new HashMap<Long, QuestionMining>();

		try {
			/*
			final HashMap<Long, T2Task> t2t = new HashMap<Long, T2Task>();
			for (final T2Task loadedItem : this.t2Task) {
				t2t.put(loadedItem.getTopicId(), loadedItem);
			}*/
			
			final HashMap<Long, TQtiTestItemD> testItems = new HashMap<Long, TQtiTestItemD>();
			for (final TQtiTestItemD loadedItem : this.tQtiTestItemD) {
				testItems.put(loadedItem.getContent(), loadedItem);
			}
			
			HashSet<Long> ids = new HashSet<Long>();
			for (final TQtiContentComposing tqc : this.tQtiContentComposing)
			{
				ids.add(tqc.getContent());
			}

			for (final TQtiContent loadedItem : this.tQtiContent)
			{
				if(ids.contains(loadedItem.getId()))
				{
					final QuestionMining item = new QuestionMining();
	
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					if (testItems.get(loadedItem.getId()) != null)
					{
						item.setText(testItems.get(loadedItem.getId()).getQuestion());
						item.setType(testItems.get(loadedItem.getId()).getQuestionType() + "");
					}
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreated()));
					item.setPlatform(this.connector.getPlatformId());
	
					questions.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + questions.size() + " QuestionMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return questions;
	}

	/**
	 * Generates ScormMining-objects from the given data.
	 * 
	 * @return HashMap with ScormMining-objects
	 */
	private Map<Long, ScormMining> generateScormMining()
	{
		final HashMap<Long, ScormMining> scorms = new HashMap<Long, ScormMining>();

		try {
			final HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			final HashMap<Long, EComposing> eCompo = new HashMap<Long, EComposing>();

			for (final EComponentType loadedItem : this.eComponentType)
			{
				//if ((loadedItem.getCharacteristicId() == 10L) || (loadedItem.getCharacteristicId() == 1L)) {
					eCTypes.put(loadedItem.getId(), loadedItem);
			//	}
			}
			for (final EComposing loadedItem : this.eComposing)
			{
				eCompo.put(loadedItem.getComposing(), loadedItem);
			}
			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				EComponentType ect = eCTypes.get(loadedItem.getType());
				final ScormMining item = new ScormMining();
				if (ect != null && (ect.getUploadDir().toLowerCase().contains("scorm")))
				{
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(this.connector.getPlatformId());

					if (eCompo.get(loadedItem.getId()) != null)
					{
						item.setTimeOpen(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getStartDate()));
						item.setTimeClose(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getEndDate()));
					}
					scorms.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + scorms.size() + " ScormMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return scorms;
	}

	// Generators for relationships
	/**
	 * Generates QuizQuestionMining-objects from the given data.
	 * 
	 * @return HashMap with QuizQuestionMining-objects
	 */
	private Map<Long, QuizQuestionMining> generateQuizQuestionMining()
	{
		final HashMap<Long, QuizQuestionMining> quizQuestions = new HashMap<Long, QuizQuestionMining>();

		try {
			for (final TQtiContentStructure loadedItem : this.tQtiContentStructure)
			{
				for( final TQtiContentComposing loadedItem2 : this.tQtiContentComposing)
				{
					final QuizQuestionMining item = new QuizQuestionMining();
					item.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getContainer()), this.quizMining,
							this.oldQuizMining);
					if(loadedItem2.getContainer().equals(loadedItem.getContent()))
					{
						item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getId().hashCode()));
						item.setQuestion(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getContent()),
								this.questionMining, this.oldQuestionMining);
					}
					item.setPlatform(this.connector.getPlatformId());
					if ((item.getQuestion() != null) && (item.getQuiz() != null)) {
						quizQuestions.put(item.getId(), item);
					}		
				}
			}

			this.logger.info("Generated " + quizQuestions.size() + " QuizQuestionMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return quizQuestions;
	}

	/**
	 * Generates CourseScormMining-objects from the given data.
	 * 
	 * @return HashMap with CourseScormMining-objects
	 */
	private Map<Long, CourseScormMining> generateCourseScormMining()
	{
		final HashMap<Long, CourseScormMining> courseScorms = new HashMap<Long, CourseScormMining>();

		try {
			for (final EComposing loadedItem : this.eComposing)
			{
				long id = 0;
				if (loadedItem.getComponent() >= 0) {
					id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());				
				}
				if ((this.scormMining.get(id) != null)
						|| (this.oldScormMining.get(id) != null))
				{
					final CourseScormMining item = new CourseScormMining();
					item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComposing()),
							this.courseMining, this.oldCourseMining);
					item.setScorm(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
							this.scormMining, this.oldScormMining);
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setPlatform(this.connector.getPlatformId());

					if ((item.getCourse() != null) && (item.getScorm() != null)) {
						courseScorms.put(item.getId(), item);
					}
				}

			}

			this.logger.info("Generated " + courseScorms.size() + " CourseScormMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return courseScorms;
	}

	/**
	 * Generates CourseAssignmentMining-objects from the given data.
	 * 
	 * @return HashMap with CourseAssignmentMining-objects
	 */
	private Map<Long, CourseAssignmentMining> generateCourseAssignmentMining()
	{
		final HashMap<Long, CourseAssignmentMining> courseAssignments = new HashMap<Long, CourseAssignmentMining>();
		try {
			for (final EComposing loadedItem : this.eComposing)
			{
				long id = 0;
				if (loadedItem.getComponent() >= 0) {
					id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());
				}
				id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());
				if ((this.assignmentMining.get(id) != null)
						|| (this.oldAssignmentMining.get(id) != null))
				{
					final CourseAssignmentMining item = new CourseAssignmentMining();

					item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComposing()),
							this.courseMining, this.oldCourseMining);
					item.setAssignment(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
							this.assignmentMining, this.oldAssignmentMining);
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setPlatform(this.connector.getPlatformId());

					if ((item.getCourse() != null) && (item.getAssignment() != null)) {
						courseAssignments.put(item.getId(), item);
					}
				}

			}
			this.logger.info("Generated " + courseAssignments.size() + " CourseAssignmentMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return courseAssignments;
	}
	
	/**
	 * Generates CourseChatMining-objects from the given data.
	 * 
	 * @return HashMap with CourseChatMining-objects
	 */
	private Map<Long, CourseChatMining> generateCourseChatMining()
	{
		final HashMap<Long, CourseChatMining> courseChats = new HashMap<Long, CourseChatMining>();
		try {
			for (final EComposing loadedItem : this.eComposing)
			{
				long id = 0;
				if (loadedItem.getComponent() >= 0) {
					id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());
				}
				id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());
				if ((this.chatMining.get(id) != null)
						|| (this.oldChatMining.get(id) != null))
				{
					final CourseChatMining item = new CourseChatMining();

					item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComposing()),
							this.courseMining, this.oldCourseMining);
					item.setChat(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
							this.chatMining, this.oldChatMining);
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setPlatform(this.connector.getPlatformId());

					if ((item.getCourse() != null) && (item.getChat() != null)) {
						courseChats.put(item.getId(), item);
					}
				}

			}
			this.logger.info("Generated " + courseChats.size() + " CourseAssignmentMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return courseChats;
	}

	/**
	 * Generates CourseResourceMining-objects from the given data.
	 * 
	 * @return HashMap with CourseResourceMining-objects
	 */
	private Map<Long, CourseResourceMining> generateCourseResourceMining()
	{
		final HashMap<Long, CourseResourceMining> courseResources = new HashMap<Long, CourseResourceMining>();

		try {
			for (final EComposing loadedItem : this.eComposing)
			{
				long id = 0;
				if (loadedItem.getComponent() >= 0) {
					id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());
				}
				if ((this.resourceMining.get(id) != null)
						|| (this.oldResourceMining.get(id) != null))
				{
					final CourseResourceMining item = new CourseResourceMining();
					item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComposing()),
							this.courseMining, this.oldCourseMining);
					item.setResource(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
							this.resourceMining, this.oldResourceMining);
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setPlatform(this.connector.getPlatformId());

					if ((item.getResource() != null) && (item.getCourse() != null)) {
						courseResources.put(item.getId(), item);
					}
				}

			}
			this.logger.info("Generated " + courseResources.size() + " CourseResourceMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return courseResources;
	}

	/**
	 * Generates CourseQuizMining-objects from the given data.
	 * 
	 * @return HashMap with CourseQuizMining-objects
	 */
	private Map<Long, CourseQuizMining> generateCourseQuizMining()
	{
		final HashMap<Long, CourseQuizMining> courseQuizzes = new HashMap<Long, CourseQuizMining>();

		try {
			for (final EComposing loadedItem : this.eComposing)
			{
				long id = 0;
				if (loadedItem.getComponent() >= 0) {
					id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());
				}
				if ((this.quizMining.get(id) != null)
						|| (this.oldQuizMining.get(id) != null))
				{
					final CourseQuizMining item = new CourseQuizMining();
					item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComposing()),
							this.courseMining, this.oldCourseMining);
					item.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
							this.quizMining, this.oldQuizMining);
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setPlatform(this.connector.getPlatformId());

					if ((item.getCourse() != null) && (item.getQuiz() != null)) {
						courseQuizzes.put(item.getId(), item);
					}
				}

			}
			this.logger.info("Generated " + courseQuizzes.size() + " CourseQuizMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return courseQuizzes;
	}

	/**
	 * Generates QuizUserMining-objects from the given data.
	 * 
	 * @return HashMap with QuizUserMining-objects
	 */
	private Map<Long, QuizUserMining> generateQuizUserMining()
	{
		final HashMap<Long, QuizUserMining> quizUsers = new HashMap<Long, QuizUserMining>();
		try {
			for (final TQtiEvalAssessment loadedItem : this.tQtiEvalAssessment)
			{
				final QuizUserMining item = new QuizUserMining();
				item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
						this.courseMining, this.oldCourseMining);
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCandidate()),
						this.userMining, this.oldUserMining);
				item.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getAssessment()),
						this.quizMining, this.oldQuizMining);
				item.setFinalGrade(loadedItem.getEvaluatedScore());
				item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastInvocation()));
				item.setPlatform(this.connector.getPlatformId());

				if ((item.getCourse() != null) && (item.getQuiz() != null) && (item.getUser() != null)) {
					quizUsers.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + quizUsers.size() + " QuizUserMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return quizUsers;
	}

	/**
	 * Generates CourseWikiMining-objects from the given data.
	 * 
	 * @return HashMap with CourseWikiMining-objects
	 */
	private Map<Long, CourseWikiMining> generateCourseWikiMining()
	{
		final HashMap<Long, CourseWikiMining> courseWikis = new HashMap<Long, CourseWikiMining>();
		try {

			for (final EComposing loadedItem : this.eComposing)
			{
				long id = 0;
				if (loadedItem.getComponent() >= 0) {
					id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());				
				}

				if ((this.wikiMining.get(id) != null)
						|| (this.oldWikiMining.get(id) != null))
				{
					final CourseWikiMining item = new CourseWikiMining();
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComposing()),
							this.courseMining, this.oldCourseMining);
					item.setWiki(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
							this.wikiMining, this.oldWikiMining);
					item.setPlatform(this.connector.getPlatformId());

					if ((item.getCourse() != null) && (item.getWiki() != null)) {
						courseWikis.put(item.getId(), item);
					}
				}
			}

			this.logger.info("Generated " + courseWikis.size() + " CourseWikiMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return courseWikis;
	}

	/**
	 * Generates GroupUserMining-objects from the given data
	 * 
	 * @return HashMap with GroupUserMining-objects
	 */
	private Map<Long, GroupUserMining> generateGroupUserMining()
	{
		final HashMap<Long, GroupUserMining> groupUsers = new HashMap<Long, GroupUserMining>();

		try {

			for (final PlatformGroupSpecification loadedItem : this.platformGroupSpecification)
			{
				final GroupUserMining item = new GroupUserMining();
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getPerson()), this.userMining,
						this.oldUserMining);
				item.setGroup(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getGroup()), this.groupMining,
						this.oldGroupMining);
				item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId().hashCode()));
				item.setPlatform(this.connector.getPlatformId());

				if ((item.getUser() != null) && (item.getGroup() != null)) {
					groupUsers.put(item.getId(), item);
				}

			}

			for (TGroupFullSpecification loadedItem : this.tGroupFullSpecification)
			{
				GroupUserMining item = new GroupUserMining();
				item.setGroup(Long.valueOf(connector.getPrefix() + "" + loadedItem.getGroup()), groupMining,
						oldGroupMining);
				item.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getPerson()), userMining,
						oldUserMining);
				if (item.getGroup() != null && item.getUser() != null)
				{
					long id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getGroup() + ""
							+ loadedItem.getPerson());
					item.setId(id);
					if (groupUsers.get(id) == null) {
						groupUsers.put(id, item);
					}
				}

				if ((item.getUser() != null) && (item.getGroup() != null)) {
					groupUsers.put(item.getId(), item);
				}
			}

			this.logger.info("Generated " + groupUsers.size() + " GroupUserMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return groupUsers;
	}

	/**
	 * Generates CourseGroupMining-objects from the given data.
	 * 
	 * @return HashMap with CourseGroupMining-objects
	 */
	private Map<Long, CourseGroupMining> generateCourseGroupMining()
	{
		final HashMap<Long, CourseGroupMining> courseGroups = new HashMap<Long, CourseGroupMining>();
		try {
			for (final TeamExerciseGroup loadedItem : this.teamExerciseGroup)
			{
				final CourseGroupMining item = new CourseGroupMining();
				item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
						this.courseMining, this.oldCourseMining);
				item.setGroup(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.groupMining,
						this.oldGroupMining);
				item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				item.setPlatform(this.connector.getPlatformId());

				if ((item.getCourse() != null) && (item.getGroup() != null)) {
					courseGroups.put(item.getId(), item);
				}

			}
			this.logger.info("Generated " + courseGroups.size() + " CourseGroupMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return courseGroups;
	}

	private Map<Long, CourseUserMining> generateCourseUserMining()
	{
		final HashMap<Long, CourseUserMining> courseUser = new HashMap<Long, CourseUserMining>();
		try {

			RoleMining teacher = null;
			RoleMining student = null;
			for (RoleMining r : roleMining.values())
			{
				if (r.getType() == 1) {
					teacher = r;
				}

				else if (r.getType() == 2) {
					student = r;
				}
				if (teacher != null && student != null) {
					break;
				}
			}
			if(teacher == null)
			{
				for (RoleMining r : oldRoleMining.values())
				{
					if (r.getType() == 1) 
					{
						teacher = r;
					}
					else if (r.getType() == 2) 
					{
						student = r;
					}
					if (teacher != null && student != null) {
						break;
					}
				}
			}

			/** Students **/
			for (Portfolio loadedItem : this.portfolio)
			{
				if (loadedItem.getCourse() == 0)
				{
					CourseUserMining item = new CourseUserMining();

					item.setId(Long.valueOf(connector.getPrefix() + "0" + loadedItem.getId()));
					item.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent()), courseMining,
							oldCourseMining);
					item.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getPerson()), userMining,
							oldUserMining);
					item.setRole(student.getId(), roleMining, oldRoleMining);
					item.setPlatform(connector.getPlatformId());
					item.setEnrolend(TimeConverter.getTimestamp(loadedItem.getEndDate()));
					item.setEnrolstart(TimeConverter.getTimestamp(loadedItem.getStartDate()));

					if (item.getCourse() != null && item.getUser() != null) {
						courseUser.put(item.getId(), item);
					}
				}
			}
			/** Teachers **/
			for (PersonComponentAssignment loadedItem : this.personComponentAssignment)
			{
				CourseUserMining item = new CourseUserMining();
				item.setId(Long.valueOf(connector.getPrefix() + "1" + loadedItem.getLongId()));
				item.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent()), courseMining,
						oldCourseMining);
				item.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getPerson()), userMining,
						oldUserMining);
				item.setRole(teacher.getId(), roleMining, oldRoleMining);
				item.setPlatform(connector.getPlatformId());

				if (item.getCourse() != null && item.getUser() != null) {
					courseUser.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + courseUser.size() + " CourseUserMinings.");
		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
			logger.error(this + e.getMessage());
		}

		return courseUser;
	}

	/**
	 * Generates CourseForumMining-objects from the given data.
	 * 
	 * @return HashMap with CourseForumMining-objects
	 */
	private Map<Long, CourseForumMining> generateCourseForumMining()
	{
		final HashMap<Long, CourseForumMining> courseForum = new HashMap<Long, CourseForumMining>();
		try {

			for (final EComposing loadedItem : this.eComposing)
			{
				long id = 0;
				if (loadedItem.getComponent() >= 0) {
					id = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent());
				}
				if ((this.forumMining.get(id) != null)
						|| (this.oldForumMining.get(id) != null))
				{
					final CourseForumMining item = new CourseForumMining();
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComposing()),
							this.courseMining, this.oldCourseMining);
					item.setForum(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
							this.forumMining, this.oldForumMining);
					item.setPlatform(this.connector.getPlatformId());

					if ((item.getCourse() != null) && (item.getForum() != null))
					{
						courseForum.put(item.getId(), item);
					}
				}
			}

			this.logger.info("Generated " + courseForum.size() + " CourseForumMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}

		return courseForum;
	}

	// Generators for logs

	/**
	 * Generates ForumLogMining-objects from the given data.
	 * 
	 * @return HashMap with ForumLogMining-objects
	 */
	private Map<Long, ForumLogMining> generateForumLogMining()
	{
		final HashMap<Long, ForumLogMining> forumLogs = new HashMap<Long, ForumLogMining>();

		try {
			final HashMap<Long, EComposing> ecMap = new HashMap<Long, EComposing>();
			for (final EComposing item : this.eComposing) {
				ecMap.put(item.getComponent(), item);
			}

			for (final ForumEntry loadedItem : this.forumEntry)
			{
				final ForumLogMining item = new ForumLogMining();
				item.setId(forumLogs.size() + this.forumLogMax + 1);

				item.setForum(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getForum()), this.forumMining,
						this.oldForumMining);
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getLastUpdater()),
						this.userMining, this.oldUserMining);
				String sub = "";
				if(loadedItem.getTitle() != null)
					sub = loadedItem.getTitle();
				while(sub.startsWith("AW: "))
				{
					sub = sub.substring(4);
				}
				item.setSubject(sub);
				item.setMessage(TextHelper.replaceString(loadedItem.getContent()));
				item.setAction("Post");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				if(item.getTimestamp() > maxLog)
				{
					maxLog = item.getTimestamp();
				}
				
				item.setPlatform(this.connector.getPlatformId());
				item.setDuration(0L);

				if (ecMap.get(loadedItem.getForum()) != null) {
					item.setCourse(
							Long.valueOf(this.connector.getPrefix() + ""
									+ ecMap.get(loadedItem.getForum()).getComposing()), this.courseMining,
							this.oldCourseMining);
				}

				if ((item.getUser() != null) && (item.getForum() != null)) {
					forumLogs.put(item.getId(), item);
				}
			}

			for (final ForumEntryState loadedItem : this.forumEntryState)
			{
				final ForumLogMining item = new ForumLogMining();
				item.setId(forumLogs.size() + this.forumLogMax + 1);
				item.setForum(loadedItem.getForum(), this.forumMining, this.oldForumMining);
				item.setUser(loadedItem.getUser(), this.userMining, this.oldUserMining);
				item.setAction("View");
				if (forumLogs.get(loadedItem.getEntry()) != null) {
					String sub = forumLogs.get(loadedItem.getEntry()).getSubject();
					while(sub.startsWith("AW:"))
					{
						sub = sub.substring(3);
					}
					item.setSubject(sub);
				}
				item.setMessage("");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));

				if (ecMap.get(loadedItem.getForum()) != null) {
					item.setCourse(ecMap.get(loadedItem.getForum()).getComposing(), this.courseMining,
							this.oldCourseMining);
				}
				if ((item.getUser() != null) && (item.getForum() != null)) {
					forumLogs.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + forumLogs.size() + " ForumLogMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return forumLogs;
	}

	/**
	 * Generates WikiLogMining-objects from the given data.
	 * 
	 * @return HashMap with WikiLogMining-objects
	 */
	private Map<Long, WikiLogMining> generateWikiLogMining()
	{
		final HashMap<Long, WikiLogMining> wikiLogs = new HashMap<Long, WikiLogMining>();
		try {
			for (final WikiEntry loadedItem : this.wikiEntry)
			{
				final WikiLogMining item = new WikiLogMining();
				item.setId(wikiLogs.size() + this.wikiLogMax + 1);

				item.setWiki(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
						this.wikiMining, this.oldWikiMining);
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCreator()), this.userMining,
						this.oldUserMining);
				if ((item.getWiki() != null) && (this.eComposingMap.get(item.getWiki().getId()) != null)) {
					item.setCourse(
							Long.valueOf(this.connector.getPrefix() + ""
									+ this.eComposingMap.get(item.getWiki().getId()).getComposing()),
							this.courseMining, this.oldCourseMining);
				}
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				if(item.getTimestamp() > maxLog)
				{
					maxLog = item.getTimestamp();
				}
				
				item.setPlatform(this.connector.getPlatformId());
				item.setDuration(0L);

				if ((item.getUser() != null) && (item.getWiki() != null)) {
					wikiLogs.put(item.getId(), item);
				}

			}
			this.logger.info("Generated " + wikiLogs.size() + " WikiLogMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}

		return wikiLogs;
	}

	/**
	 * Generates CourseLogMining-objects from the given data.
	 * 
	 * @return HashMap with CourseLogMining-objects
	 */
	private Map<Long, CourseLogMining> generateCourseLogMining()
	{
		final HashMap<Long, CourseLogMining> courseLogs = new HashMap<Long, CourseLogMining>();
		try {
			for (final PortfolioLog loadedItem : this.portfolioLog)
			{
				final CourseLogMining item = new CourseLogMining();
				item.setId(courseLogs.size() + this.courseLogMax + 1);
				item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
						this.courseMining, this.oldCourseMining);
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getPerson()), this.userMining,
						this.oldUserMining);
				item.setAction(loadedItem.getTypeOfModification() + "");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				if(item.getTimestamp() > maxLog)
				{
					maxLog = item.getTimestamp();
				}
				
				item.setPlatform(this.connector.getPlatformId());
				item.setDuration(0L);

				if ((item.getCourse() != null) && (item.getUser() != null))
				{
					courseLogs.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + courseLogs.size() + " CourseLogMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return courseLogs;
	}

	/**
	 * Generates QuestionLogMining-objects from the given data.
	 * 
	 * @return HashMap with QuestionLogMining-objects
	 */
	private Map<Long, QuestionLogMining> generateQuestionLogMining()
	{
		final HashMap<Long, QuestionLogMining> questionLogs = new HashMap<Long, QuestionLogMining>();

		try {
			for (final TQtiTestPlayerResp loadedItem : this.tQtiTestPlayerResp)
			{
				final QuestionLogMining item = new QuestionLogMining();

				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCandidate()), this.userMining,
						this.oldUserMining);
				item.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getContent()), this.quizMining,
						this.oldQuizMining);
				item.setQuestion(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getTestItem()),
						this.questionMining, this.oldQuestionMining);
				item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getContainer()), this.courseMining,
						this.oldCourseMining);
				
				item.setFinalGrade(loadedItem.getEvaluatedScore());
				item.setRawGrade(loadedItem.getEvaluatedScore());
				item.setPenalty(0d);
				item.setType("QTI");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getEvaluationDate()));
				if(item.getTimestamp() > maxLog)
				{
					maxLog = item.getTimestamp();
				}
				
				item.setPlatform(this.connector.getPlatformId());
				item.setDuration(0L);
				item.setAnswers(loadedItem.getText());
				item.setId(questionLogs.size() + this.questionLogMax + 1);	
				if(loadedItem.getProcessStatus() == 0L)
					item.setAction("view");
				if(loadedItem.getProcessStatus() == 1L)
					item.setAction("close");
				
				if ((item.getQuestion() != null) && (item.getQuiz() != null) && (item.getUser() != null)
						&& (item.getCourse() != null)) {
					questionLogs.put(item.getId(), item);
					
				}
			}
			this.logger.info("Generated " + questionLogs.size() + " QuestionLogMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return questionLogs;
	}

	/**
	 * Generates QuizLogMining-objects from the given data.
	 * 
	 * @return HashMap with QuizLogMining-objects
	 */
	private Map<Long, QuizLogMining> generateQuizLogMining()
	{
		final HashMap<Long, QuizLogMining> quizLogs = new HashMap<Long, QuizLogMining>();
		try {
			for (final TQtiTestPlayer loadedItem : this.tQtiTestPlayer)
			{
				final QuizLogMining item = new QuizLogMining();

				item.setId(quizLogs.size() + this.quizLogMax + 1);
				item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getContainer()),
						this.courseMining, this.oldCourseMining);
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCandidate()),
						this.userMining, this.oldUserMining);
				item.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getContent()),
						this.quizMining, this.oldQuizMining);
				item.setGrade(loadedItem.getEvaluatedScore());
				item.setPlatform(this.connector.getPlatformId());
				item.setDuration(0L);
				int status = loadedItem.getRuntimeStatus().intValue();
				if(loadedItem.getRuntimeStatus() == null)
					status = -1;
				switch(status)
				{
					case 0: 
						item.setAction("attempt");
						break;
					case 1: 
						item.setAction("saved");
						break;
					case 2:
						item.setAction("commited");
						break;
					case 3:
						item.setAction("graded");
						break;
					default: 
						item.setAction("unknown");
						break;					
				}
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getCreated()));
				if(item.getTimestamp() > maxLog)
				{
					maxLog = item.getTimestamp();
				}

				if ((item.getCourse() != null) && (item.getQuiz() != null) && (item.getUser() != null)) {
					quizLogs.put(item.getId(), item);
				}

			}
			this.logger.info("Generated " + quizLogs.size() + " QuizLogMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return quizLogs;
	}

	/**
	 * Generates AssignmentLogMining-objects from the given data.
	 * 
	 * @return HashMap with AssignmentLogMining-objects
	 */
	private Map<Long, AssignmentLogMining> generateAssignmentLogMining()
	{
		final HashMap<Long, AssignmentLogMining> assignmentLogs = new HashMap<Long, AssignmentLogMining>();
		try {
			// This HashMap is helpful for finding the course_id that is associated with the AssignmentLog
			final HashMap<Long, Long> eg = new HashMap<Long, Long>();
			for (final ExerciseGroup loadedItem : this.exerciseGroup)
			{
				if (loadedItem.getAssociatedCourse() != 0L) {
					eg.put(loadedItem.getId(), loadedItem.getAssociatedCourse());
				}
			}
			for (final ExercisePersonalised loadedItem : this.exercisePersonalised)
			{
				final AssignmentLogMining item = new AssignmentLogMining();

				item.setAssignment(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getExercise()),
						this.assignmentMining, this.oldAssignmentMining);
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUser()), this.userMining,
						this.oldUserMining);
				item.setGrade(Double.valueOf(loadedItem.getPoints()));
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getUploadDate()));
				if(item.getTimestamp() > maxLog)
				{
					maxLog = item.getTimestamp();
				}
				
				item.setPlatform(this.connector.getPlatformId());
				// Get the course_id via the exercisegroup_id
				if (eg.get(loadedItem.getCommunity()) != null) {
					item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + eg.get(loadedItem.getCommunity())),
							this.courseMining, this.oldCourseMining);
				}

				item.setId(assignmentLogs.size() + this.assignmentLogMax + 1);
				item.setDuration(0L);

				if ((item.getCourse() != null) && (item.getAssignment() != null) && (item.getUser() != null))
				{
					assignmentLogs.put(item.getId(), item);
					if (this.courseAssignmentMining.get(item.getAssignment().getId()) == null)
					{
						final CourseAssignmentMining cam = new CourseAssignmentMining();
						cam.setAssignment(item.getAssignment());
						cam.setCourse(item.getCourse());
						cam.setPlatform(this.connector.getPlatformId());
						cam.setId(Long.valueOf(this.connector.getPrefix() + "" + (this.courseAssignmentMining.size())));

						this.courseAssignmentMining.put(cam.getAssignment().getId(), cam);
					}
				}
			}
			this.logger.info("Generated " + assignmentLogs.size() + " AssignmentLogMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return assignmentLogs;
	}

	/**
	 * Generates ScormLogMining-objects from the given data.
	 * 
	 * @return HashMap with ScormLogMining-objects
	 */
	private Map<Long, ScormLogMining> generateScormLogMining()
	{
		final HashMap<Long, ScormLogMining> scormLogs = new HashMap<Long, ScormLogMining>();
		final HashMap<Long, Long> eComp = new HashMap<Long, Long>();
		for (final EComposing item : this.eComposing) {
			eComp.put(item.getComponent(), item.getComposing());
		}

		try {
			for (final ScormSessionTimes loadedItem : this.scormSessionTimes)
			{
				final ScormLogMining item = new ScormLogMining();
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getPerson()), this.userMining,
						this.oldUserMining);
				try {
					if ((loadedItem.getScore() != null) && !loadedItem.getScore().equals("null")) {
						item.setGrade(Double.valueOf(loadedItem.getScore().toString()));
					}

					item.setScorm(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
							this.scormMining, this.oldScormMining);
					item.setAction(loadedItem.getStatus());
					item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					if(item.getTimestamp() > maxLog)
					{
						maxLog = item.getTimestamp();
					}
					
					item.setId(scormLogs.size() + this.scormLogMax + 1);
					item.setPlatform(this.connector.getPlatformId());

					item.setDuration(0L);

					if (eComp.get(loadedItem.getComponent()) != null) {
						item.setCourse(
								Long.valueOf(this.connector.getPrefix() + "" + eComp.get(loadedItem.getComponent())),
								this.courseMining, this.oldCourseMining);
					}

					if ((item.getUser() != null) && (item.getScorm() != null)) {
						scormLogs.put(item.getId(), item);
					}

				} catch (final NullPointerException e)
				{
					this.logger.info("Couldn't parse grade: " + loadedItem.getScore());
				}

			}
			this.logger.info("Generated " + scormLogs.size() + " ScormLogMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return scormLogs;
	}

	/**
	 * Generates ResourceLogMining-objects from the given data.
	 * 
	 * @return HashMap with ResourceLogMining-objects
	 */
	private Map<Long, ResourceLogMining> generateResourceLogMining()
	{
		final HashMap<Long, ResourceLogMining> resourceLogs = new HashMap<Long, ResourceLogMining>();
		try {
			for (final BiTrackContentImpressions loadedItem : this.biTrackContentImpressions)
			{
				final ResourceLogMining item = new ResourceLogMining();
				item.setResource(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getContent()),
						this.resourceMining, this.oldResourceMining);
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUser()), this.userMining,
						this.oldUserMining);
				item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getContainer()),
						this.courseMining, this.oldCourseMining);
				item.setAction("View");
				item.setDuration(0L);
				item.setId(resourceLogs.size() + this.resourceLogMax + 1);
				// Time stamp has different format (2009-07-31)
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getDayOfAccess() + " 00:00:00.000"));
				if(item.getTimestamp() > maxLog)
				{
					maxLog = item.getTimestamp();
				}
				
				item.setPlatform(this.connector.getPlatformId());

				if ((item.getResource() != null) && (item.getCourse() != null) && (item.getUser() != null)) {
					resourceLogs.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + resourceLogs.size() + " ResourceLogMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return resourceLogs;
	}

	/**
	 * Generates ChatLogMining-objects from the given data.
	 * 
	 * @return HashMap with ChatLogMining-objects
	 */
	private Map<Long, ChatLogMining> generateChatLogMining()
	{
		final HashMap<Long, ChatLogMining> chatLogs = new HashMap<Long, ChatLogMining>();
		try {
			for (final ChatProtocol loadedItem : this.chatProtocol)
			{
				final ChatLogMining item = new ChatLogMining();

				item.setId(chatLogs.size() + this.chatLogMax + 1);
				item.setChat(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getChatroom()), this.chatMining,
						this.oldChatMining);
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getPerson()), this.userMining,
						this.oldUserMining);
				item.setMessage(TextHelper.replaceString(loadedItem.getChatSource()));
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				if(item.getTimestamp() > maxLog)
				{
					maxLog = item.getTimestamp();
				}
				
				item.setPlatform(this.connector.getPlatformId());
				item.setDuration(0L);
				EComposing l = this.eComposingMap.get(loadedItem.getChatroom());
				if(l != null)
					item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + l.getComposing()), courseMining, oldCourseMining);

				if ((item.getChat() != null) && (item.getUser() != null)) {
					chatLogs.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + chatLogs.size() + " ChatLogMinings.");

		} catch (final Exception e)
		{
			logger.error(this + e.getMessage());
		}
		return chatLogs;
	}
	
	
	/**
	 * Generates ILogMining-objects from the given data.
	 * 
	 * @return HashMap with ILogMining-objects
	 */
	private ArrayList<ILogMining> generateILogMining()
	{
		final ArrayList<ILogMining> iLogs = new ArrayList<ILogMining>();
		final ArrayList<AssignmentLogMining> assignmentLogs = new ArrayList<AssignmentLogMining>();
		final ArrayList<ScormLogMining> scormLogs = new ArrayList<ScormLogMining>();
		final ArrayList<WikiLogMining> wikiLogs = new ArrayList<WikiLogMining>();
		final ArrayList<ResourceLogMining> resourceLogs = new ArrayList<ResourceLogMining>();
		try {
			
			Long lastATime = null;
			Long lastAUser = null;
			Long lastRTime = null;
			Long lastRUser = null;
			Long lastSTime = null;
			Long lastSUser = null;
			Long lastWTime = null;
			Long lastWUser = null;
			
			
			
			
			for(LearningLog log : this.learningLog)
			{
				if(this.assignmentMining.get(Long.valueOf(connector.getPrefix() + "" + log.getComponent())) != null)
				{
					if(!(TimeConverter.getTimestamp(log.getLastUpdated()).equals(lastATime) && log.getPerson().equals(lastAUser)))
					{
						AssignmentLogMining item = new AssignmentLogMining();
						item.setId(assignmentLogs.size() + this.assignmentLogMax + 1);
						item.setCourse(Long.valueOf(connector.getPrefix() + "" + log.getCourse()), courseMining, oldCourseMining);
						item.setAssignment(Long.valueOf(connector.getPrefix() + "" + log.getComponent()), assignmentMining, oldAssignmentMining);
						item.setUser(Long.valueOf(connector.getPrefix() + "" + log.getPerson()), userMining, oldUserMining);
						item.setDuration(0L);
						item.setAction(log.getTypeOfModification() + "");
						item.setGrade(log.getEvaluatedScore());
						item.setPlatform(connector.getPlatformId());
						item.setTimestamp(TimeConverter.getTimestamp(log.getLastUpdated()));
						if(item.getTimestamp() > maxLog)
						{
							maxLog = item.getTimestamp();
						}
						
						if(item.getCourse() != null && item.getUser() != null && item.getAssignment() != null)
							assignmentLogs.add(item);
						
						//lastATime = item.getTimestamp();
						//lastAUser = log.getPerson();
					}
					
				}
				else if(this.resourceMining.get(Long.valueOf(connector.getPrefix() + "" + log.getComponent())) != null)
				{
					if(!(TimeConverter.getTimestamp(log.getLastUpdated()).equals(lastRTime) && log.getPerson().equals(lastRUser)))
					{
						ResourceLogMining item = new ResourceLogMining();
						item.setId(resourceLogs.size() + this.resourceLogMax + 1);
						item.setCourse(Long.valueOf(connector.getPrefix() + "" + log.getCourse()), courseMining, oldCourseMining);
						item.setResource(Long.valueOf(connector.getPrefix() + "" + log.getComponent()), resourceMining, oldResourceMining);
						item.setUser(Long.valueOf(connector.getPrefix() + "" + log.getPerson()), userMining, oldUserMining);
						item.setDuration(0L);
						item.setAction(log.getTypeOfModification() + "");
						item.setPlatform(connector.getPlatformId());
						item.setTimestamp(TimeConverter.getTimestamp(log.getLastUpdated()));
						if(item.getTimestamp() > maxLog)
						{
							maxLog = item.getTimestamp();
						}
						
						if(item.getCourse() != null && item.getUser() != null && item.getResource() != null)
							resourceLogs.add(item);
					}
				}
				else if(this.wikiMining.get(Long.valueOf(connector.getPrefix() + "" + log.getComponent())) != null)
				{
					if(!(TimeConverter.getTimestamp(log.getLastUpdated()).equals(lastWTime) && log.getPerson().equals(lastWUser)))
					{
						WikiLogMining item = new WikiLogMining();
						item.setId(wikiLogs.size() + this.wikiLogMax + 1);
						item.setCourse(Long.valueOf(connector.getPrefix() + "" + log.getCourse()), courseMining, oldCourseMining);
						item.setWiki(Long.valueOf(connector.getPrefix() + "" + log.getComponent()), wikiMining, oldWikiMining);
						item.setUser(Long.valueOf(connector.getPrefix() + "" + log.getPerson()), userMining, oldUserMining);
						item.setDuration(0L);
						item.setAction(log.getTypeOfModification() + "");
						item.setPlatform(connector.getPlatformId());
						item.setTimestamp(TimeConverter.getTimestamp(log.getLastUpdated()));
						if(item.getTimestamp() > maxLog)
						{
							maxLog = item.getTimestamp();
						}
						
						if(item.getCourse() != null && item.getUser() != null && item.getWiki() != null)
							wikiLogs.add(item);
					}
				}
				else if(this.scormMining.get(Long.valueOf(connector.getPrefix() + "" + log.getComponent())) != null)
				{
					if(!(TimeConverter.getTimestamp(log.getLastUpdated()).equals(lastSTime) && log.getPerson().equals(lastSUser)))
					{
						ScormLogMining item = new ScormLogMining();
						item.setId(scormLogs.size() + this.scormLogMax + 1);
						item.setCourse(Long.valueOf(connector.getPrefix() + "" + log.getCourse()), courseMining, oldCourseMining);
						item.setScorm(Long.valueOf(connector.getPrefix() + "" + log.getComponent()), scormMining, oldScormMining);
						item.setUser(Long.valueOf(connector.getPrefix() + "" + log.getPerson()), userMining, oldUserMining);
						item.setDuration(0L);
						item.setAction(log.getTypeOfModification() + "");
						item.setPlatform(connector.getPlatformId());
						item.setTimestamp(TimeConverter.getTimestamp(log.getLastUpdated()));
						if(item.getTimestamp() > maxLog)
						{
							maxLog = item.getTimestamp();
						}
						
						item.setGrade(log.getEvaluatedScore());
						
						if(item.getCourse() != null && item.getUser() != null && item.getScorm() != null)
							scormLogs.add(item);
					}
				}
			}
			
		}catch (Exception e)
		{
			logger.error(this + e.getMessage());
		}
		Collections.sort(assignmentLogs);
		this.logger.info("Generated " + assignmentLogs.size() + " AssignmentLogMinings.");
		Collections.sort(resourceLogs);
		this.logger.info("Generated " + resourceLogs.size() + " ResourceLogMinings.");
		Collections.sort(wikiLogs);
		this.logger.info("Generated " + wikiLogs.size() + " WikiLogMinings.");
		Collections.sort(scormLogs);
		this.logger.info("Generated " + scormLogs.size() + " ScormLogMinings.");
		
		
		iLogs.addAll(assignmentLogs);
		iLogs.addAll(resourceLogs);
		iLogs.addAll(wikiLogs);
		iLogs.addAll(scormLogs);
		
		return iLogs;		
	}
}
