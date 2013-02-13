/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/ClixImporter.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.clix2010.clixDBClass.BiTrackContentImpressions;
import de.lemo.dms.connectors.clix2010.clixDBClass.ChatProtocol;
import de.lemo.dms.connectors.clix2010.clixDBClass.Chatroom;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponent;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponentType;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComposing;
import de.lemo.dms.connectors.clix2010.clixDBClass.ExerciseGroup;
import de.lemo.dms.connectors.clix2010.clixDBClass.ExercisePersonalised;
import de.lemo.dms.connectors.clix2010.clixDBClass.ForumEntry;
import de.lemo.dms.connectors.clix2010.clixDBClass.ForumEntryState;
import de.lemo.dms.connectors.clix2010.clixDBClass.Person;
import de.lemo.dms.connectors.clix2010.clixDBClass.PersonComponentAssignment;
import de.lemo.dms.connectors.clix2010.clixDBClass.PlatformGroup;
import de.lemo.dms.connectors.clix2010.clixDBClass.PlatformGroupSpecification;
import de.lemo.dms.connectors.clix2010.clixDBClass.Portfolio;
import de.lemo.dms.connectors.clix2010.clixDBClass.PortfolioLog;
import de.lemo.dms.connectors.clix2010.clixDBClass.ScormSessionTimes;
import de.lemo.dms.connectors.clix2010.clixDBClass.T2Task;
import de.lemo.dms.connectors.clix2010.clixDBClass.TAnswerPosition;
import de.lemo.dms.connectors.clix2010.clixDBClass.TGroupFullSpecification;
import de.lemo.dms.connectors.clix2010.clixDBClass.TQtiContent;
import de.lemo.dms.connectors.clix2010.clixDBClass.TQtiEvalAssessment;
import de.lemo.dms.connectors.clix2010.clixDBClass.TTestSpecification;
import de.lemo.dms.connectors.clix2010.clixDBClass.TeamExerciseComposingExt;
import de.lemo.dms.connectors.clix2010.clixDBClass.TeamExerciseGroup;
import de.lemo.dms.connectors.clix2010.clixDBClass.TeamExerciseGroupMember;
import de.lemo.dms.connectors.clix2010.clixDBClass.WikiEntry;
import de.lemo.dms.connectors.clix2010.clixHelper.TimeConverter;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.AssignmentMining;
import de.lemo.dms.db.miningDBclass.ChatLogMining;
import de.lemo.dms.db.miningDBclass.ChatMining;
import de.lemo.dms.db.miningDBclass.ConfigMining;
import de.lemo.dms.db.miningDBclass.CourseAssignmentMining;
import de.lemo.dms.db.miningDBclass.CourseForumMining;
import de.lemo.dms.db.miningDBclass.CourseGroupMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseQuizMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.CourseScormMining;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.CourseWikiMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.ForumMining;
import de.lemo.dms.db.miningDBclass.GroupMining;
import de.lemo.dms.db.miningDBclass.GroupUserMining;
import de.lemo.dms.db.miningDBclass.LevelAssociationMining;
import de.lemo.dms.db.miningDBclass.LevelCourseMining;
import de.lemo.dms.db.miningDBclass.LevelMining;
import de.lemo.dms.db.miningDBclass.PlatformMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuestionMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.QuizMining;
import de.lemo.dms.db.miningDBclass.QuizQuestionMining;
import de.lemo.dms.db.miningDBclass.QuizUserMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.RoleMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.ScormMining;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.WikiMining;

// TODO: Auto-generated Javadoc
/**
 * Class for data-retrieval from Clix platforms.
 * 
 * @author s.schwarzrock
 */
public class ClixImporter {

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

	// List, holding the entries of the BI_TRACKCONTENT_IMPRESSIONS table of the source database
	/** The bi track content impressions. */
	private List<BiTrackContentImpressions> biTrackContentImpressions;
	// List, holding the entries of the CHATPROTOCOL table of the source database
	/** The chat protocol. */
	private List<ChatProtocol> chatProtocol;
	// List, holding the entries of the EXERCISE_GROUP table of the source database
	/** The exercise group. */
	private List<ExerciseGroup> exerciseGroup;
	// List, holding the entries of the E_COMPONENT table of the source database
	/** The e component. */
	// private List<EComponent> eComponent;
	/** The e component. */
	private Map<Long, EComponent> eComponentMap;
	// List, holding the entries of the E_COMPONENTTYPE table of the source database
	/** The e component type. */
	private List<EComponentType> eComponentType;
	// List, holding the entries of the E_COMPOSING table of the source database
	/** The e composing. */
	private List<EComposing> eComposing;
	// List, holding the entries of the EXERCISE_PERSONALISED table of the source database
	/** The exercise personalised. */
	private List<ExercisePersonalised> exercisePersonalised;
	// List, holding the entries of the FORUM_ENTRY table of the source database
	/** The forum entry. */
	private List<ForumEntry> forumEntry;
	// List, holding the entries of the FORUM_ENTRY_STATE table of the source database
	/** The forum entry state. */
	private List<ForumEntryState> forumEntryState;
	// List, holding the entries of the PERSON table of the source database
	/** The person. */
	private List<Person> person;
	// List, holding the entries of the PERSON_COMPONENT_ASSIGNMENT table of the source database
	/** The person component assignment. */
	private List<PersonComponentAssignment> personComponentAssignment;
	// List, holding the entries of the PLATFORMGROUP table of the source database
	/** The platform group. */
	private List<PlatformGroup> platformGroup;
	// List, holding the entries of the PLATFORMGROUPSPECIFICATION table of the source database
	/** The platform group specification. */
	private List<PlatformGroupSpecification> platformGroupSpecification;
	// List, holding the entries of the PORTFOLIO table of the source database
	/** The portfolio. */
	private List<Portfolio> portfolio;
	// List, holding the entries of the PORTFOLIO_LOG table of the source database
	/** The portfolio log. */
	private List<PortfolioLog> portfolioLog;
	// List, holding the entries of the SCORM_SESSION_TIMES table of the source database
	/** The scorm session times. */
	private List<ScormSessionTimes> scormSessionTimes;
	// List, holding the entries of the T2_TASK table of the source database
	/** The t2 task. */
	private List<T2Task> t2Task;
	// List, holding the entries of the T_ANSWERPOSITION table of the source database
	/** The t answer position. */
	private List<TAnswerPosition> tAnswerPosition;
	// List, holding the entries of the TEAM_EXERCISE_COMPOSING_EXT table of the source database
	/** The team exercise composing ext. */
	private List<TeamExerciseComposingExt> teamExerciseComposingExt;
	// List, holding the entries of the TEAM_EXERCISE_GOUP table of the source database
	/** The team exercise group. */
	private List<TeamExerciseGroup> teamExerciseGroup;
	// List, holding the entries of the TEAM_EXERCISE_GROUP_MEMBER table of the source database
	/** The team exercise group member. */
	private List<TeamExerciseGroupMember> teamExerciseGroupMember;
	// List, holding the entries of the T_GROUPFULLSPECIFICATION table of the source database
	/** The t group full specification. */
	private List<TGroupFullSpecification> tGroupFullSpecification;
	// List, holding the entries of the T_QTI_CONTENT table of the source database
	/** The t qti content. */
	private List<TQtiContent> tQtiContent;
	// List, holding the entries of the T_QTI_EVAL_ASSESSMENT table of the source database
	/** The t qti eval assessment. */
	private List<TQtiEvalAssessment> tQtiEvalAssessment;
	// List, holding the entries of the T_TESTSPECIFICATION table of the source database
	/** The t test specification. */
	private List<TTestSpecification> tTestSpecification;
	// List, holding the entries of the WIKI_ENTRY table of the source database
	/** The wiki entry. */
	private List<WikiEntry> wikiEntry;
	// List, holding the entries of the CHATROOM table of the source database
	/** The chatroom. */
	private List<Chatroom> chatroom;

	/** The e composing map. */
	private final Map<Long, EComposing> eComposingMap = new HashMap<Long, EComposing>();

	// Map, holding the CourseMining objects, found in the current data extraction process
	/** The course_mining. */
	private Map<Long, CourseMining> courseMining;

	/** The platform_mining. */
	private Map<Long, PlatformMining> platformMining;
	// Map, holding the QuizMining objects, found in the current data extraction process
	/** The quiz_mining. */
	private Map<Long, QuizMining> quizMining;
	// Map, holding the AssignmentMining objects, found in the current data extraction process
	/** The assignment_mining. */
	private Map<Long, AssignmentMining> assignmentMining;
	// Map, holding the ScormMining objects, found in the current data extraction process
	/** The scorm_mining. */
	private Map<Long, ScormMining> scormMining;
	// Map, holding the ForumMining objects, found in the current data extraction process
	/** The forum_mining. */
	private Map<Long, ForumMining> forumMining;
	// Map, holding the ResourceMining objects, found in the current data extraction process
	/** The resource_mining. */
	private Map<Long, ResourceMining> resourceMining;
	// Map, holding the UserMining objects, found in the current data extraction process
	/** The user_mining. */
	private Map<Long, UserMining> userMining;
	// Map, holding the WikiMining objects, found in the current data extraction process
	/** The wiki_mining. */
	private Map<Long, WikiMining> wikiMining;
	// Map, holding the GroupMining objects, found in the current data extraction process
	/** The group_mining. */
	private Map<Long, GroupMining> groupMining;
	// Map, holding the QuestionMining objects, found in the current data extraction process
	/** The question_mining. */
	private Map<Long, QuestionMining> questionMining;
	// Map, holding the RoleMining objects, found in the current data extraction process
	/** The role_mining. */
	private Map<Long, RoleMining> roleMining;
	// Map, holding the ChatMining objects, found in the current data extraction process
	/** The chat_mining. */
	private Map<Long, ChatMining> chatMining;
	/** The level_mining. */
	private Map<Long, LevelMining> oldLevelMining;
	// Map, holding the QuizQuestionMining objects, found in the current data extraction process
	/** The quiz_question_mining. */
	private Map<Long, QuizQuestionMining> quizQuestionMining;
	// Map, holding the CourseQuizMining objects, found in the current data extraction process
	/** The course_quiz_mining. */
	private Map<Long, CourseQuizMining> courseQuizMining;
	/** The course_user_mining. */
	private Map<Long, CourseUserMining> courseUserMining;
	// Map, holding the CourseAssignmentMining objects, found in the current data extraction process
	/** The course_assignment_mining. */
	private Map<Long, CourseAssignmentMining> courseAssignmentMining;
	// Map, holding the CourseScormMining objects, found in the current data extraction process
	/** The course_scorm_mining. */
	private Map<Long, CourseScormMining> courseScormMining;
	// Map, holding the CourseUserMining objects, found in the current data extraction process
	// private Map<Long, CourseUserMining> course_user_mining;
	// Map, holding the CourseForumMining objects, found in the current data extraction process
	/** The course_forum_mining. */
	private Map<Long, CourseForumMining> courseForumMining;
	// Map, holding the CourseGroupMining objects, found in the current data extraction process
	/** The course_group_mining. */
	private Map<Long, CourseGroupMining> courseGroupMining;
	// Map, holding the CourseResourceMining objects, found in the current data extraction process
	/** The course_resource_mining. */
	private Map<Long, CourseResourceMining> courseResourceMining;
	// Map, holding the CourseWikiMining objects, found in the current data extraction process
	/** The course_wiki_mining. */
	private Map<Long, CourseWikiMining> courseWikiMining;
	// Map, holding the GroupUserMining objects, found in the current data extraction process
	/** The group_user_mining. */
	private Map<Long, GroupUserMining> groupUserMining;
	// Map, holding the QuizUserMining objects, found in the current data extraction process
	/** The quiz_user_mining. */
	private Map<Long, QuizUserMining> quizUserMining;

	// Maps of mining-objects that have been found in a previous extraction process

	// Map, holding the CourseMining objects, found in a previous data extraction process
	/** The old_course_mining. */
	private Map<Long, CourseMining> oldCourseMining;

	// Map, holding the QuizMining objects, found in a previous data extraction process
	/** The old_quiz_mining. */
	private Map<Long, QuizMining> oldQuizMining;
	// Map, holding the AssignmentMining objects, found in a previous data extraction process
	/** The old_assignment_mining. */
	private Map<Long, AssignmentMining> oldAssignmentMining;
	// Map, holding the ScormMining objects, found in a previous data extraction process
	/** The old_scorm_mining. */
	private Map<Long, ScormMining> oldScormMining;
	// Map, holding the ForumMining objects, found in a previous data extraction process
	/** The old_forum_mining. */
	private Map<Long, ForumMining> oldForumMining;
	// Map, holding the ResourceMining objects, found in a previous data extraction process
	/** The old_resource_mining. */
	private Map<Long, ResourceMining> oldResourceMining;
	// Map, holding the UserMining objects, found in a previous data extraction process
	/** The old_user_mining. */
	private Map<Long, UserMining> oldUserMining;
	// Map, holding the WikiMining objects, found in a previous data extraction process
	/** The old_wiki_mining. */
	private Map<Long, WikiMining> oldWikiMining;
	// Map, holding the GroupMining objects, found in a previous data extraction process
	/** The old_group_mining. */
	private Map<Long, GroupMining> oldGroupMining;
	// Map, holding the QuestionMining objects, found in a previous data extraction process
	/** The old_question_mining. */
	private Map<Long, QuestionMining> oldQuestionMining;
	// Map, holding the RoleMining objects, found in a previous data extraction process
	/** The old_role_mining. */
	private Map<Long, RoleMining> oldRoleMining;
	// Map, holding the ChatMining objects, found in a previous data extraction process
	/** The old_chat_mining. */
	private HashMap<Long, ChatMining> oldChatMining;
	
	private final Logger logger = Logger.getLogger(this.getClass());

	private final IConnector connector;

	public ClixImporter(final IConnector connector) {
		this.connector = connector;
	}

	/**
	 * Performs a extraction process for an entire Clix2010 database.
	 * 
	 * @param platformName
	 *            the platform name
	 * @return the clix data
	 */
	public void getClixData(final DBConfigObject dbConfig)
	{
		final Clock c = new Clock();
		final Long starttime = System.currentTimeMillis() / 1000;

		this.platformMining = new HashMap<Long, PlatformMining>();
		// Do Import

		this.initialize();

		this.logger.info("\n" + c.getAndReset() + " (initializing)" + "\n");

		this.loadData(dbConfig);
		this.logger.info("\n" + c.getAndReset() + " (loading data)" + "\n");
		this.saveData();
		this.logger.info("\n" + c.getAndReset() + " (saving data)" + "\n");

		// Create and save config-object
		final Long endtime = System.currentTimeMillis() / 1000;
		final ConfigMining config = new ConfigMining();
		config.setLastModifiedLong(System.currentTimeMillis());
		config.setElapsedTime((endtime) - (starttime));
		config.setDatabaseModel("1.2");
		config.setPlatform(this.connector.getPlatformId());

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session miningSession = dbHandler.getMiningSession();
		dbHandler.saveToDB(miningSession, config);
		dbHandler.closeSession(miningSession);
	}

	/**
	 * Performs a data-extraction for a Clix2010 database for all objects that are newer than the given time stamp.
	 * 
	 * @param platformName
	 *            the platform name
	 * @param startTime
	 *            the start time
	 */
	public void updateClixData(final DBConfigObject dbConfig, Long startTime)
	{
		final Long currentSysTime = System.currentTimeMillis() / 1000;
		Long upperLimit = 0L;

		this.platformMining = new HashMap<Long, PlatformMining>();

		while (startTime <= currentSysTime)
		{
			// increase upper limit successively
			upperLimit = startTime + 604800L;

			this.initialize();

			// Do Update
			this.loadData(dbConfig, startTime, upperLimit);

			this.saveData();

			startTime = upperLimit;
		}

		final Long endtime = System.currentTimeMillis() / 1000;
		final ConfigMining config = new ConfigMining();
		config.setLastModifiedLong(System.currentTimeMillis());
		config.setElapsedTime((endtime) - (currentSysTime));
		config.setDatabaseModel("1.2");
		config.setPlatform(this.connector.getPlatformId());

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		dbHandler.saveToDB(session, config);
		dbHandler.closeSession(session);
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

				// generateLevelMining();
				// updates.add(level_mining.values());

				this.logger.info("\nAssociationObjects: \n");

				// updates.add(level_association_mining.values());

				// updates.add(level_course_mining.values());

				this.courseUserMining = this.generateCourseUserMining();
				updates.add(this.courseUserMining.values());
				
				this.quizQuestionMining = this.generateQuizQuestionMining();
				updates.add(this.quizQuestionMining.values());

				this.courseQuizMining = this.generateCourseQuizMining();
				updates.add(this.courseQuizMining.values());

				//
				this.courseAssignmentMining = this.generateCourseAssignmentMining();
				updates.add(this.courseAssignmentMining.values());

				// level_association_mining = generateLevelAssociationMining();
				// updates.add(level_association_mining.values());

				// level_course_mining = generateLevelCourseMining();
				// updates.add(level_course_mining.values());

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

			this.logger.info("\nLogObjects: \n");

			updates.add(this.generateAssignmentLogMining().values());

			updates.add(this.courseAssignmentMining.values());

			updates.add(this.generateCourseLogMining().values());

			updates.add(this.generateForumLogMining().values());

			updates.add(this.generateQuizLogMining().values());

			updates.add(this.generateQuestionLogMining().values());

			updates.add(this.generateScormLogMining().values());

			updates.add(this.generateResourceLogMining().values());

			updates.add(this.generateWikiLogMining().values());

			updates.add(this.generateChatLogMining().values());

			Long objects = 0L;

			for (final Collection<?> collection : updates)
			{
				objects += collection.size();
			}

			if (objects > 0)
			{

				final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
				final Session session = dbHandler.getMiningSession();
				this.logger.info("Writing to DB");
				dbHandler.saveCollectionToDB(session, updates);
			} else {
				this.logger.info("No new objects found.");
			}

			this.clearSourceData();

		} catch (final Exception e) {
			e.printStackTrace();
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
		// eComponent.clear();
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
		this.t2Task.clear();
		this.tAnswerPosition.clear();
		this.teamExerciseComposingExt.clear();
		this.teamExerciseGroup.clear();
		this.teamExerciseGroupMember.clear();
		this.tGroupFullSpecification.clear();
		this.tQtiContent.clear();
		this.tQtiEvalAssessment.clear();
		this.tTestSpecification.clear();
		this.wikiEntry.clear();
		this.chatroom.clear();

	}

	/**
	 * Looks, if there are already values in the Mining database and loads them, if necessary.
	 * 
	 * @param platformName
	 *            the platform name
	 */
	@SuppressWarnings("unchecked")
	private void initialize()
	{
		try {
			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

			// accessing DB by creating a session and a transaction using HibernateUtil
			final Session session = dbHandler.getMiningSession();
			session.clear();

			ArrayList<?> l;

			Query logCount = session.createQuery("select max(log.id) from ResourceLogMining log where log.platform="
					+ this.connector.getPlatformId());
			this.resourceLogMax = ((ArrayList<Long>) logCount.list()).get(0);
			if (this.resourceLogMax == null) {
				this.resourceLogMax = 0L;
			}

			logCount = session.createQuery("select max(log.id) from ChatLogMining log where log.platform="
					+ this.connector.getPlatformId());
			this.chatLogMax = ((ArrayList<Long>) logCount.list()).get(0);
			if (this.chatLogMax == null) {
				this.chatLogMax = 0L;
			}

			logCount = session.createQuery("select max(log.id) from AssignmentLogMining log where log.platform="
					+ this.connector.getPlatformId());
			this.assignmentLogMax = ((ArrayList<Long>) logCount.list()).get(0);
			if (this.assignmentLogMax == null) {
				this.assignmentLogMax = 0L;
			}

			logCount = session.createQuery("select max(log.id) from CourseLogMining log where log.platform="
					+ this.connector.getPlatformId());
			this.courseLogMax = ((ArrayList<Long>) logCount.list()).get(0);
			if (this.courseLogMax == null) {
				this.courseLogMax = 0L;
			}

			logCount = session.createQuery("select max(log.id) from ForumLogMining log where log.platform="
					+ this.connector.getPlatformId());
			this.forumLogMax = ((ArrayList<Long>) logCount.list()).get(0);
			if (this.forumLogMax == null) {
				this.forumLogMax = 0L;
			}

			logCount = session.createQuery("select max(log.id) from QuestionLogMining log where log.platform="
					+ this.connector.getPlatformId());
			this.questionLogMax = ((ArrayList<Long>) logCount.list()).get(0);
			if (this.questionLogMax == null) {
				this.questionLogMax = 0L;
			}

			logCount = session.createQuery("select max(log.id) from QuizLogMining log where log.platform="
					+ this.connector.getPlatformId());
			this.quizLogMax = ((ArrayList<Long>) logCount.list()).get(0);
			if (this.quizLogMax == null) {
				this.quizLogMax = 0L;
			}

			logCount = session.createQuery("select max(log.id) from ScormLogMining log where log.platform="
					+ this.connector.getPlatformId());
			this.scormLogMax = ((ArrayList<Long>) logCount.list()).get(0);
			if (this.scormLogMax == null) {
				this.scormLogMax = 0L;
			}

			logCount = session.createQuery("select max(log.id) from WikiLogMining log where log.platform="
					+ this.connector.getPlatformId());
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

		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Loads all necessary tables from the Clix2010 database.
	 */
	@SuppressWarnings("unchecked")
	private void loadData(final DBConfigObject dbConfig)
	{
		try {

			// accessing DB by creating a session and a transaction using HibernateUtil
			final Session session = ClixHibernateUtil.getSessionFactory(dbConfig).openSession();
			session.clear();

			this.logger.info("Starting data extraction.");

			final Query pers = session.createQuery("from Person x order by x.id asc");
			this.person = pers.list();
			this.logger.info("Person tables: " + this.person.size());

			final Query chatro = session.createQuery("from Chatroom x order by x.id asc");
			this.chatroom = chatro.list();
			this.logger.info("Chatroom tables: " + this.chatroom.size());

			final Query eCompo = session.createQuery("from EComposing x order by x.id asc");
			this.eComposing = eCompo.list();
			this.logger.info("EComposing tables: " + this.eComposing.size());
			for (int i = 0; i < this.eComposing.size(); i++)
			{
				this.eComposingMap.put(this.eComposing.get(i).getComponent(), this.eComposing.get(i));
			}

			final Query portf = session.createQuery("from Portfolio x order by x.id asc");
			this.portfolio = portf.list();
			this.logger.info("Portfolio tables: " + this.portfolio.size());

			final Query biTrack = session.createQuery("from BiTrackContentImpressions x order by x.id asc");
			this.biTrackContentImpressions = biTrack.list();
			this.logger.info("biTrackContentImpressions tables: " + this.biTrackContentImpressions.size());

			final Query chatProt = session.createQuery("from ChatProtocol x order by x.id asc");
			this.chatProtocol = chatProt.list();
			this.logger.info("ChatProtocol tables: " + this.chatProtocol.size());

			final Query perComAss = session.createQuery("from PersonComponentAssignment x order by x.id asc");
			this.personComponentAssignment = perComAss.list();
			this.logger.info("PersonComponentAssignment tables: " + this.personComponentAssignment.size());

			final Query eCompTy = session.createQuery("from EComponentType x order by x.id asc");
			this.eComponentType = eCompTy.list();
			this.logger.info("EComponentType tables: " + this.eComponentType.size());

			final Query eComp = session.createQuery("from EComponent x order by x.id asc");
			final List<EComponent> tmp = eComp.list();
			this.eComponentMap = new HashMap<Long, EComponent>();
			for (final EComponent c : tmp)
			{
				this.eComponentMap.put(c.getId(), c);
			}
			tmp.clear();

			this.logger.info("EComponent tables: " + this.eComponentMap.values().size());

			final Query exPer = session.createQuery("from ExercisePersonalised x order by x.id asc");
			this.exercisePersonalised = exPer.list();
			this.logger.info("ExercisePersonalised tables: " + this.exercisePersonalised.size());

			final Query exGro = session.createQuery("from ExerciseGroup x order by x.id asc");
			this.exerciseGroup = exGro.list();
			this.logger.info("ExerciseGroup tables: " + this.exerciseGroup.size());

			final Query foEnt = session.createQuery("from ForumEntry x order by x.id asc");
			this.forumEntry = foEnt.list();
			this.logger.info("ForumEntry tables: " + this.forumEntry.size());

			final Query foEntS = session.createQuery("from ForumEntryState x order by x.id asc");
			this.forumEntryState = foEntS.list();
			this.logger.info("ForumEntryState tables: " + this.forumEntryState.size());

			final Query plGrSp = session.createQuery("from PlatformGroupSpecification x order by x.id asc");
			this.platformGroupSpecification = plGrSp.list();
			this.logger.info("PlatformGroupSpecification tables: " + this.platformGroupSpecification.size());

			final Query plGr = session.createQuery("from PlatformGroup x order by x.id asc");
			this.platformGroup = plGr.list();
			this.logger.info("PlatformGroup tables: " + this.platformGroup.size());

			final Query porLo = session.createQuery("from PortfolioLog x order by x.id asc");
			this.portfolioLog = porLo.list();
			this.logger.info("PortfolioLog tables: " + this.portfolioLog.size());

			final Query scoSes = session.createQuery("from ScormSessionTimes x order by x.id asc");
			this.scormSessionTimes = scoSes.list();
			this.logger.info("ScormSession tables: " + this.scormSessionTimes.size());

			final Query t2Ta = session.createQuery("from T2Task x order by x.id asc");
			this.t2Task = t2Ta.list();
			this.logger.info("T2Task tables: " + this.t2Task.size());

			final Query tAnPos = session.createQuery("from TAnswerPosition x order by x.id asc");
			this.tAnswerPosition = tAnPos.list();
			this.logger.info("TAnswerPosition tables: " + this.tAnswerPosition.size());

			final Query tECEx = session.createQuery("from TeamExerciseComposingExt x order by x.id asc");
			this.teamExerciseComposingExt = tECEx.list();
			this.logger.info("TeamExerciseComposingExt tables: " + this.teamExerciseComposingExt.size());

			final Query tEG = session.createQuery("from TeamExerciseGroup x order by x.id asc");
			this.teamExerciseGroup = tEG.list();
			this.logger.info("TeamExerciseGroup tables: " + this.teamExerciseGroup.size());

			final Query tEGMem = session.createQuery("from TeamExerciseGroupMember x order by x.id asc");
			this.teamExerciseGroupMember = tEGMem.list();
			this.logger.info("TeamExerciseGroupMember tables: " + this.teamExerciseGroupMember.size());

			final Query tGFSpec = session.createQuery("from TGroupFullSpecification x order by x.id asc");
			this.tGroupFullSpecification = tGFSpec.list();
			this.logger.info("TGroupFullSpecification tables: " + this.tGroupFullSpecification.size());

			final Query tQC = session.createQuery("from TQtiContent x order by x.id asc");
			this.tQtiContent = tQC.list();
			this.logger.info("TQtiContent tables: " + this.tQtiContent.size());

			final Query tQEA = session.createQuery("from TQtiEvalAssessment x order by x.id asc");
			this.tQtiEvalAssessment = tQEA.list();
			this.logger.info("TQtiEvalAssessment tables: " + this.tQtiEvalAssessment.size());

			final Query tTS = session.createQuery("from TTestSpecification x order by x.id asc");
			this.tTestSpecification = tTS.list();
			this.logger.info("TTestSpecification tables: " + this.tTestSpecification.size());

			final Query wE = session.createQuery("from WikiEntry x order by x.id asc");
			this.wikiEntry = wE.list();
			this.logger.info("WikiEntry tables: " + this.wikiEntry.size());

			// writeToFile();
			// loadFromFile();

		} catch (final Exception e)
		{
			e.printStackTrace();
		}

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
	private void loadData(final DBConfigObject dbConfig, final Long start, final Long end)
	{
		try {
			// accessing DB by creating a session and a transaction using HibernateUtil
			final Session session = ClixHibernateUtil.getSessionFactory(dbConfig).openSession();
			session.clear();

			this.logger.info("Starting data extraction.");

			// Read the tables that don't refer to log-entries once
			if (this.userMining == null)
			{
				final Query chatro = session.createQuery("from Chatroom x order by x.id asc");
				this.chatroom = chatro.list();
				this.logger.info("Chatroom tables: " + this.chatroom.size());

				final Query eCompo = session.createQuery("from EComposing x order by x.id asc");
				this.eComposing = eCompo.list();
				this.logger.info("EComposing tables: " + this.eComposing.size());
				for (int i = 0; i < this.eComposing.size(); i++)
				{
					this.eComposingMap.put(this.eComposing.get(i).getComponent(), this.eComposing.get(i));
				}

				final Query portf = session.createQuery("from Portfolio x order by x.id asc");
				this.portfolio = portf.list();
				this.logger.info("Portfolio tables: " + this.portfolio.size());

				final Query perComAss = session.createQuery("from PersonComponentAssignment x order by x.id asc");
				this.personComponentAssignment = perComAss.list();
				this.logger.info("PersonComponentAssignment tables: " + this.personComponentAssignment.size());

				final Query eCompTy = session.createQuery("from EComponentType x order by x.id asc");
				this.eComponentType = eCompTy.list();
				this.logger.info("EComponentType tables: " + this.eComponentType.size());

				final Query eComp = session.createQuery("from EComponent x order by x.id asc");
				final List<EComponent> tmp = eComp.list();
				for (final EComponent c : tmp) {
					this.eComponentMap.put(c.getId(), c);
				}
				tmp.clear();
				this.logger.info("EComponent tables: " + this.eComponentMap.values().size());

				final Query pers = session.createQuery("from Person x order by x.id asc");
				this.person = pers.list();
				this.logger.info("Person tables: " + this.person.size());

				final Query plGrSp = session.createQuery("from PlatformGroupSpecification x order by x.id asc");
				this.platformGroupSpecification = plGrSp.list();
				this.logger.info("PlatformGroupSpecification tables: " + this.platformGroupSpecification.size());

				final Query plGr = session.createQuery("from PlatformGroup x order by x.id asc");
				this.platformGroup = plGr.list();
				this.logger.info("PlatformGroup tables: " + this.platformGroup.size());

				final Query porLo = session.createQuery("from PortfolioLog x order by x.id asc");
				this.portfolioLog = porLo.list();
				this.logger.info("PortfolioLog tables: " + this.portfolioLog.size());

				final Query t2Ta = session.createQuery("from T2Task x order by x.id asc");
				this.t2Task = t2Ta.list();
				this.logger.info("T2Task tables: " + this.t2Task.size());

				final Query tECEx = session.createQuery("from TeamExerciseComposingExt x order by x.id asc");
				this.teamExerciseComposingExt = tECEx.list();
				this.logger.info("TeamExerciseComposingExt tables: " + this.teamExerciseComposingExt.size());

				final Query tEG = session.createQuery("from TeamExerciseGroup x order by x.id asc");
				this.teamExerciseGroup = tEG.list();
				this.logger.info("TeamExerciseGroup tables: " + this.teamExerciseGroup.size());

				final Query tEGMem = session.createQuery("from TeamExerciseGroupMember x order by x.id asc");
				this.teamExerciseGroupMember = tEGMem.list();
				this.logger.info("TeamExerciseGroupMember tables: " + this.teamExerciseGroupMember.size());

				final Query tGFSpec = session.createQuery("from TGroupFullSpecification x order by x.id asc");
				this.tGroupFullSpecification = tGFSpec.list();
				this.logger.info("TGroupFullSpecification tables: " + this.tGroupFullSpecification.size());

				final Query tQC = session.createQuery("from TQtiContent x order by x.id asc");
				this.tQtiContent = tQC.list();
				this.logger.info("TQtiContent tables: " + this.tQtiContent.size());

				final Query tTS = session.createQuery("from TTestSpecification x order by x.id asc");
				this.tTestSpecification = tTS.list();
				this.logger.info("TTestSpecification tables: " + this.tTestSpecification.size());

			}

			// Read log-data successively, using the time stamps

			// The Clix database uses date representation of the type varchar, so the unix-timestamp has to be converted
			// to a string
			String startStr = TimeConverter.getStringRepresentation(start);
			String endStr = TimeConverter.getStringRepresentation(end);

			final Query chatProt = session
					.createQuery("from ChatProtocol x where x.lastUpdated>=:start and x.lastUpdated<=:end order by x.id asc");
			chatProt.setParameter("start", startStr);
			chatProt.setParameter("end", endStr);
			this.chatProtocol = chatProt.list();
			this.logger.info("ChatProtocol tables: " + this.chatProtocol.size());

			final Query foEnt = session
					.createQuery("from ForumEntry x where x.lastUpdated>=:start and x.lastUpdated<=:end order by x.id asc");
			foEnt.setParameter("start", startStr);
			foEnt.setParameter("end", endStr);
			this.forumEntry = foEnt.list();
			this.logger.info("ForumEntry tables: " + this.forumEntry.size());

			final Query foEntS = session
					.createQuery("from ForumEntryState x where x.lastUpdated>=:start and x.lastUpdated<=:end order by x.id asc");
			foEntS.setParameter("start", startStr);
			foEntS.setParameter("end", endStr);
			this.forumEntryState = foEntS.list();
			this.logger.info("ForumEntryState tables: " + this.forumEntryState.size());

			final Query tAnPos = session.createQuery("from TAnswerPosition x order by x.id asc");
			this.tAnswerPosition = tAnPos.list();
			this.logger.info("TAnswerPosition tables: " + this.tAnswerPosition.size());

			final Query tQEA = session
					.createQuery("from TQtiEvalAssessment x where x.lastInvocation>=:start and x.lastInvocation<=:end order by x.id asc");
			tQEA.setParameter("start", startStr);
			tQEA.setParameter("end", endStr);
			this.tQtiEvalAssessment = tQEA.list();
			this.logger.info("TQtiEvalAssessment tables: " + this.tQtiEvalAssessment.size());

			final Query scoSes = session
					.createQuery("from ScormSessionTimes x where x.lastUpdated>=:start and x.lastUpdated<=:end order by x.id asc");
			scoSes.setParameter("start", startStr);
			scoSes.setParameter("end", endStr);
			this.scormSessionTimes = scoSes.list();
			this.logger.info("ScormSession tables: " + this.scormSessionTimes.size());

			final Query wE = session
					.createQuery("from WikiEntry x where x.lastUpdated>=:start and x.lastUpdated<=:end order by x.id asc");
			wE.setParameter("start", startStr);
			wE.setParameter("end", endStr);
			this.wikiEntry = wE.list();
			this.logger.info("WikiEntry tables: " + this.wikiEntry.size());

			final Query exPer = session
					.createQuery("from ExercisePersonalised x  where x.uploadDate>=:start and x.uploadDate<=:end order by x.id asc");
			exPer.setParameter("start", startStr);
			exPer.setParameter("end", endStr);
			this.exercisePersonalised = exPer.list();
			this.logger.info("ExercisePersonalised tables: " + this.exercisePersonalised.size());

			final Query exGro = session.createQuery("from ExerciseGroup x order by x.id asc");
			this.exerciseGroup = exGro.list();
			this.logger.info("ExerciseGroup tables: " + this.exerciseGroup.size());

			// The date-strings have to be modified, because the date format of the table BiTrackContentImpressions is
			// different
			startStr = startStr.substring(0, startStr.indexOf(" "));
			endStr = endStr.substring(0, endStr.indexOf(" "));

			final Query biTrack = session
					.createQuery("from BiTrackContentImpressions x where x.dayOfAccess>=:start and x.dayOfAccess<=:end order by x.id asc");
			biTrack.setParameter("start", startStr);
			biTrack.setParameter("end", endStr);
			this.biTrackContentImpressions = biTrack.list();
			this.logger.info("biTrackContentImpressions tables: " + this.biTrackContentImpressions.size());

			// writeToFile();
			// loadFromFile();

		} catch (final Exception e)
		{
			e.printStackTrace();
		}

	}

	// Generators for "" objects

	/**
	 * Generates ChatMining-objects from the given data.
	 * 
	 * @return HashMap with ChatMining-objects
	 */
	private Map<Long, ChatMining> generateChatMining() {
		final HashMap<Long, ChatMining> chats = new HashMap<Long, ChatMining>();
		try {
			for (final Chatroom loadedItem : this.chatroom)
			{
				final ChatMining item = new ChatMining();
				item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				item.setTitle(loadedItem.getTitle());
				item.setChatTime(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(this.connector.getPlatformId());
				chats.put(item.getId(), item);
			}

			this.logger.info("Generated " + chats.size() + " ChatMining");
		} catch (final Exception e)
		{
			e.printStackTrace();
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
				if ((loadedItem.getCharacteristicId() == 1L) || (loadedItem.getCharacteristicId() == 11L)) {
					eCTypes.put(loadedItem.getComponent(), loadedItem);
				}
			}

			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				final ResourceMining item = new ResourceMining();
				if (eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
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
			e.printStackTrace();
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
				if ((loadedItem.getCharacteristicId() == 4L) || (loadedItem.getCharacteristicId() == 5L)
						|| (loadedItem.getCharacteristicId() == 3L)) {
					eCTypes.put(loadedItem.getComponent(), loadedItem);
				}
			}
			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				final CourseMining item = new CourseMining();
				if (eCTypes.get(loadedItem.getType()) != null)
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
			e.printStackTrace();
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

				if (loadedItem.getGender() == 1) {
					item.setGender(false);
				} else {
					item.setGender(true);
				}
				item.setLogin(Encoder.createMD5(loadedItem.getLogin()));

				users.put(item.getId(), item);
			}
			this.logger.info("Generated " + users.size() + " UserMining.");
		} catch (final Exception e)
		{
			e.printStackTrace();
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
				if (loadedItem.getCharacteristicId() == 8L) {
					eCTypes.put(loadedItem.getComponent(), loadedItem);
				}
			}

			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				final AssignmentMining item = new AssignmentMining();
				if (eCTypes.get(loadedItem.getType()) != null)
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
			e.printStackTrace();
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
			for (final EComponentType loadedItem : this.eComponentType)
			{
				if (loadedItem.getCharacteristicId() == 14L) {
					eCTypes.put(loadedItem.getComponent(), loadedItem);
				}
			}
			for (final EComposing loadedItem : this.eComposing)
			{
				eCompo.put(loadedItem.getComposing(), loadedItem);
			}
			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				final QuizMining item = new QuizMining();
				if (eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setTimeOpen(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(this.connector.getPlatformId());
					if (eCompo.get(loadedItem.getId()) != null) {
						item.setTimeClose(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getEndDate()));
					}
					quizzes.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + quizzes.size() + " QuizMinings.");
		} catch (final Exception e)
		{
			e.printStackTrace();
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
					eCTypes.put(loadedItem.getComponent(), loadedItem);
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
			e.printStackTrace();
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
		final HashMap<Long, RoleMining> roles = new HashMap<Long, RoleMining>();
		try {
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
			this.logger.info("Generated " + roles.size() + " RoleMinings.");
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return roles;
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
			e.printStackTrace();
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
					eCTypes.put(loadedItem.getComponent(), loadedItem);
				}
			}
			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				final WikiMining item = new WikiMining();
				if ((eCTypes.get(loadedItem.getType()) != null)
						&& eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("wiki"))
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
			e.printStackTrace();
		}
		return wikis;
	}

	/**
	 * Generates DepartmentMining-objects from the given data.
	 * 
	 * @return HashMap with DepartmentMining-objects
	 */
	/*
	 * private Map<Long, DepartmentMining> generateDepartmentMining()
	 * {
	 * HashMap<Long, DepartmentMining> departments = new HashMap<Long, DepartmentMining>();
	 * try{
	 * 
	 * this.logger.info("Generated " + departments.size() + " DepartmentMinings.");
	 * }catch(Exception e)
	 * {
	 * e.printStackTrace();
	 * }
	 * return departments;
	 * }
	 */

	/**
	 * Generates DegreeMining-objects from the given data
	 * 
	 * @return HashMap with DegreeMining-objects
	 */
	/*
	 * <<<<<<< HEAD
	 * public HashMap<Long, DegreeMining> generateDegreeMining()
	 * =======
	 * private Map<Long, DegreeMining> generateDegreeMining()
	 * >>>>>>> refs/remotes/origin/feature_currentWorkBranch1210_sebs
	 * {
	 * HashMap<Long, DegreeMining> degrees = new HashMap<Long, DegreeMining>();
	 * try{
	 * 
	 * this.logger.info("Generated " + degrees.size() + " DegreeMinings.");
	 * }catch(Exception e)
	 * {
	 * e.printStackTrace();
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
			final HashMap<Long, T2Task> t2t = new HashMap<Long, T2Task>();
			for (final T2Task loadedItem : this.t2Task) {
				t2t.put(loadedItem.getTopicId(), loadedItem);
			}

			for (final TQtiContent loadedItem : this.tQtiContent)
			{
				final QuestionMining item = new QuestionMining();

				item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				item.setTitle(loadedItem.getName());
				if (t2t.get(loadedItem.getId()) != null)
				{
					item.setText(t2t.get(loadedItem.getId()).getQuestionText());
					item.setType(t2t.get(loadedItem.getId()).getTaskType() + "");
				}
				item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreated()));
				item.setPlatform(this.connector.getPlatformId());

				questions.put(item.getId(), item);
			}
			this.logger.info("Generated " + questions.size() + " QuestionMinings.");

		} catch (final Exception e)
		{
			e.printStackTrace();
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
				if ((loadedItem.getCharacteristicId() == 10L) || (loadedItem.getCharacteristicId() == 1L)) {
					eCTypes.put(loadedItem.getComponent(), loadedItem);
				}
			}
			for (final EComposing loadedItem : this.eComposing)
			{
				eCompo.put(loadedItem.getComposing(), loadedItem);
			}
			for (final EComponent loadedItem : this.eComponentMap.values())
			{
				final ScormMining item = new ScormMining();
				if ((eCTypes.get(loadedItem.getType()) != null)
						&& eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("scorm"))
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
			e.printStackTrace();
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
			for (final TTestSpecification loadedItem : this.tTestSpecification)
			{
				final QuizQuestionMining item = new QuizQuestionMining();
				item.setQuestion(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getTest()),
						this.questionMining, this.oldQuestionMining);
				item.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getTask()), this.quizMining,
						this.oldQuizMining);
				// Id for QuizQuestion entry is a combination of the question-id and the quiz-id
				item.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId().hashCode()));
				item.setPlatform(this.connector.getPlatformId());

				if ((item.getQuestion() != null) && (item.getQuiz() != null)) {
					quizQuestions.put(item.getId(), item);
				}
			}

			this.logger.info("Generated " + quizQuestions.size() + " QuizQuestionMinings.");
		} catch (final Exception e)
		{
			e.printStackTrace();
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
				if(loadedItem.getComponent() >= 0)
					id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());
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
			e.printStackTrace();
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
				if(loadedItem.getComponent() >= 0)
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
			e.printStackTrace();
		}
		return courseAssignments;
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
				if(loadedItem.getComponent() >= 0)
					id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());
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
			e.printStackTrace();
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
				if(loadedItem.getComponent() >= 0)
					id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());
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
			e.printStackTrace();
		}
		return courseQuizzes;
	}

	/**
	 * Generates QuizUserMining-objects from the given data.
	 * 
	 * @return HashMap with QuizUserMining-objects
	 */
	private HashMap<Long, QuizUserMining> generateQuizUserMining()
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
			e.printStackTrace();
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
				if(loadedItem.getComponent() >= 0)
					id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent());
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
			e.printStackTrace();
		}
		return courseWikis;
	}

	/**
	 * Generates DegreeCourseMining-objects from the given data.
	 * 
	 * @return HashMap with DegreeCourseMining-objects
	 */
	/*
	 * private Map<Long, DegreeCourseMining> generateDegreeCourseMining()
	 * {
	 * HashMap<Long, DegreeCourseMining> degreeCourses = new HashMap<Long, DegreeCourseMining>();
	 * 
	 * try{
	 * this.logger.info("Generated " + degreeCourses.size() + " DegreeCourseMinings.");
	 * 
	 * }catch(Exception e)
	 * {
	 * e.printStackTrace();
	 * }
	 * return degreeCourses;
	 * }
	 */

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
			
			for(TGroupFullSpecification loadedItem : this.tGroupFullSpecification)
			{
				GroupUserMining item = new GroupUserMining();
				item.setGroup(Long.valueOf(connector.getPrefix() + "" + loadedItem.getGroup()), groupMining, oldGroupMining);
				item.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getPerson()), userMining, oldUserMining);
				if(item.getGroup() != null && item.getUser() != null)
				{
					long id = Long.valueOf(connector.getPrefix() + "" + loadedItem.getGroup() + "" + loadedItem.getPerson());
					item.setId(id);
					if(groupUsers.get(id) == null)
						groupUsers.put(id, item);
				}
				
				if ((item.getUser() != null) && (item.getGroup() != null)) {
					groupUsers.put(item.getId(), item);
				}
			}

			this.logger.info("Generated " + groupUsers.size() + " GroupUserMinings.");

		} catch (final Exception e)
		{
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return courseGroups;
	}
	
	private Map<Long, CourseUserMining> generateCourseUserMining()
	{
		final HashMap<Long, CourseUserMining> courseUser = new HashMap<Long, CourseUserMining>();
		try{
			
			RoleMining teacher = null;
			RoleMining student = null;
			for(RoleMining r : roleMining.values())
			{
				if(r.getType() == 1)
					teacher = r;
				else if(r.getType() == 2)
					student = r;
				if(teacher != null && student != null)
					break;
					
			}
			
			/** Students **/
			for(Portfolio loadedItem : this.portfolio)
			{
				if(loadedItem.getCourse() == 0)
				{
					CourseUserMining item = new CourseUserMining();
					
					item.setId(Long.valueOf(connector.getPrefix() + "0" + loadedItem.getId()));
					item.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent()), courseMining, oldCourseMining);
					item.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getPerson()), userMining, oldUserMining);
					item.setRole(student.getId(), roleMining, oldRoleMining);
					item.setPlatform(connector.getPlatformId());
					item.setEnrolend(TimeConverter.getTimestamp(loadedItem.getEndDate()));
					item.setEnrolstart(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					
					if(item.getCourse() != null && item.getUser() != null)
						courseUser.put(item.getId(), item);
				}
			}
			/** Tutors **/
			for(PersonComponentAssignment loadedItem : this.personComponentAssignment)
			{
				CourseUserMining item = new CourseUserMining();
				//TODO ids can be duplicated
				item.setId(Long.valueOf(connector.getPrefix() + "1" + loadedItem.getLongId()));
				item.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getComponent()), courseMining, oldCourseMining);
				item.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getPerson()), userMining, oldUserMining);
				item.setRole(teacher.getId(), roleMining, oldRoleMining);
				item.setPlatform(connector.getPlatformId());
				
				if(item.getCourse() != null && item.getUser() != null)
					courseUser.put(item.getId(), item);
			}
			this.logger.info("Generated " + courseUser.size() + " CourseUserMinings.");
		} catch (final Exception e)
		{
			e.printStackTrace();
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
				if(loadedItem.getComponent() >= 0)
					id = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent());
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
			e.printStackTrace();
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
				item.setSubject(loadedItem.getTitle());
				item.setMessage(loadedItem.getContent());
				item.setAction("Post");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
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
					item.setSubject(forumLogs.get(loadedItem.getEntry()).getSubject());
				}
				item.setMessage("");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));

				if (ecMap.get(loadedItem.getForum()) != null) {
					item.setCourse(ecMap.get(loadedItem.getForum()).getComposing(), this.courseMining,
							this.oldCourseMining);
				}
				
/*				if(item.getCourse() != null)
				{
					CourseForumMining cfm = new CourseForumMining();
					cfm.setForum(item.getForum().getId(), this.forumMining, this.oldForumMining);
					cfm.setCourse(item.getCourse().getId(), this.courseMining, this.oldCourseMining);
				}
*/
				if ((item.getUser() != null) && (item.getForum() != null)) {
					forumLogs.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + forumLogs.size() + " ForumLogMinings.");

		} catch (final Exception e)
		{
			e.printStackTrace();
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
				item.setPlatform(this.connector.getPlatformId());
				item.setDuration(0L);

				if ((item.getUser() != null) && (item.getWiki() != null)) {
					wikiLogs.put(item.getId(), item);
				}

			}
			this.logger.info("Generated " + wikiLogs.size() + " WikiLogMinings.");

		} catch (final Exception e)
		{
			e.printStackTrace();
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
			e.printStackTrace();
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
			for (final TAnswerPosition loadedItem : this.tAnswerPosition)
			{
				final QuestionLogMining item = new QuestionLogMining();

				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getPerson()), this.userMining,
						this.oldUserMining);
				item.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getTest()), this.quizMining,
						this.oldQuizMining);
				item.setQuestion(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getQuestion()),
						this.questionMining, this.oldQuestionMining);
				item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getTest()), this.courseMining,
						this.oldCourseMining);
				item.setId(questionLogs.size() + this.questionLogMax + 1);
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getEvaluated()));
				item.setPlatform(this.connector.getPlatformId());
				item.setDuration(0L);

				if ((item.getQuestion() != null) && (item.getQuiz() != null) && (item.getUser() != null)
						&& (item.getCourse() != null)) {
					questionLogs.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + questionLogs.size() + " QuestionLogMinings.");

		} catch (final Exception e)
		{
			e.printStackTrace();
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
			for (final TQtiEvalAssessment loadedItem : this.tQtiEvalAssessment)
			{
				final QuizLogMining item = new QuizLogMining();

				item.setId(quizLogs.size() + this.quizLogMax + 1);
				item.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getComponent()),
						this.courseMining, this.oldCourseMining);
				item.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCandidate()),
						this.userMining, this.oldUserMining);
				item.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getAssessment()),
						this.quizMining, this.oldQuizMining);
				item.setGrade(Double.valueOf(loadedItem.getEvaluatedScore()));
				item.setPlatform(this.connector.getPlatformId());
				item.setDuration(0L);
				if (loadedItem.getEvalCount() == 0L) {
					item.setAction("Try");
				} else {
					item.setAction("Submit");
				}
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastInvocation()));

				if ((item.getCourse() != null) && (item.getQuiz() != null) && (item.getUser() != null)) {
					quizLogs.put(item.getId(), item);
				}

			}
			this.logger.info("Generated " + quizLogs.size() + " QuizLogMinings.");

		} catch (final Exception e)
		{
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
				item.setPlatform(this.connector.getPlatformId());

				if ((item.getResource() != null) && (item.getCourse() != null) && (item.getUser() != null)) {
					resourceLogs.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + resourceLogs.size() + " ResourceLogMinings.");

		} catch (final Exception e)
		{
			e.printStackTrace();
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
				item.setMessage(loadedItem.getChatSource());
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(this.connector.getPlatformId());
				item.setDuration(0L);
				//item.setCourse(item.getChat().getCourse());

				if ((item.getChat() != null) && (item.getUser() != null)) {
					chatLogs.put(item.getId(), item);
				}
			}
			this.logger.info("Generated " + chatLogs.size() + " ChatLogMinings.");

		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return chatLogs;
	}
}
