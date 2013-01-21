package de.lemo.dms.connectors.clix2010;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import de.lemo.dms.connectors.Encoder;
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
import de.lemo.dms.core.ServerConfigurationHardCoded;
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
import de.lemo.dms.db.miningDBclass.LevelMining;
import de.lemo.dms.db.miningDBclass.LevelAssociationMining;
import de.lemo.dms.db.miningDBclass.LevelCourseMining;

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
	private  Long chatLogMax = 0L;
	
	/** The assignment log max. */
	private  Long assignmentLogMax = 0L;
	
	/** The course log max. */
	private  Long courseLogMax = 0L;
	
	/** The forum log max. */
	private  Long forumLogMax = 0L;
	
	/** The question log max. */
	private  Long questionLogMax = 0L;
	
	/** The quiz log max. */
	private  Long quizLogMax = 0L;
	
	/** The scorm log max. */
	private  Long scormLogMax = 0L;
	
	/** The wiki log max. */
	private  Long wikiLogMax = 0L;
	
	//List, holding the entries of the BI_TRACKCONTENT_IMPRESSIONS table of the source database
	/** The bi track content impressions. */
	private  List<BiTrackContentImpressions> biTrackContentImpressions;
	//List, holding the entries of the CHATPROTOCOL table of the source database
	/** The chat protocol. */
	private  List<ChatProtocol> chatProtocol;
	//List, holding the entries of the EXERCISE_GROUP table of the source database
	/** The exercise group. */
	private  List<ExerciseGroup> exerciseGroup;
	//List, holding the entries of the E_COMPONENT table of the source database
	/** The e component. */
//	private  List<EComponent> eComponent;
	/** The e component. */
	private  Map<Long, EComponent> eComponentMap;
	//List, holding the entries of the E_COMPONENTTYPE table of the source database
	/** The e component type. */
	private  List<EComponentType> eComponentType;
	//List, holding the entries of the E_COMPOSING table of the source database
	/** The e composing. */
	private  List<EComposing> eComposing;
	//List, holding the entries of the EXERCISE_PERSONALISED table of the source database
	/** The exercise personalised. */
	private  List<ExercisePersonalised> exercisePersonalised;
	//List, holding the entries of the FORUM_ENTRY table of the source database
	/** The forum entry. */
	private  List<ForumEntry> forumEntry;
	//List, holding the entries of the FORUM_ENTRY_STATE table of the source database
	/** The forum entry state. */
	private  List<ForumEntryState> forumEntryState;
	//List, holding the entries of the PERSON table of the source database
	/** The person. */
	private  List<Person> person;
	//List, holding the entries of the PERSON_COMPONENT_ASSIGNMENT table of the source database
	/** The person component assignment. */
	private  List<PersonComponentAssignment> personComponentAssignment;
	//List, holding the entries of the PLATFORMGROUP table of the source database
	/** The platform group. */
	private  List<PlatformGroup> platformGroup;
	//List, holding the entries of the PLATFORMGROUPSPECIFICATION table of the source database
	/** The platform group specification. */
	private  List<PlatformGroupSpecification> platformGroupSpecification;
	//List, holding the entries of the PORTFOLIO table of the source database
	/** The portfolio. */
	private  List<Portfolio> portfolio;
	//List, holding the entries of the PORTFOLIO_LOG table of the source database
	/** The portfolio log. */
	private  List<PortfolioLog> portfolioLog;
	//List, holding the entries of the SCORM_SESSION_TIMES table of the source database
	/** The scorm session times. */
	private  List<ScormSessionTimes> scormSessionTimes;
	//List, holding the entries of the T2_TASK table of the source database
	/** The t2 task. */
	private  List<T2Task> t2Task;
	//List, holding the entries of the T_ANSWERPOSITION table of the source database
	/** The t answer position. */
	private  List<TAnswerPosition> tAnswerPosition;
	//List, holding the entries of the TEAM_EXERCISE_COMPOSING_EXT table of the source database
	/** The team exercise composing ext. */
	private  List<TeamExerciseComposingExt> teamExerciseComposingExt;
	//List, holding the entries of the TEAM_EXERCISE_GOUP table of the source database
	/** The team exercise group. */
	private  List<TeamExerciseGroup> teamExerciseGroup;
	//List, holding the entries of the TEAM_EXERCISE_GROUP_MEMBER table of the source database
	/** The team exercise group member. */
	private  List<TeamExerciseGroupMember> teamExerciseGroupMember;
	//List, holding the entries of the T_GROUPFULLSPECIFICATION table of the source database
	/** The t group full specification. */
	private  List<TGroupFullSpecification> tGroupFullSpecification;
	//List, holding the entries of the T_QTI_CONTENT table of the source database
	/** The t qti content. */
	private  List<TQtiContent> tQtiContent;
	//List, holding the entries of the T_QTI_EVAL_ASSESSMENT table of the source database
	/** The t qti eval assessment. */
	private  List<TQtiEvalAssessment> tQtiEvalAssessment;
	//List, holding the entries of the T_TESTSPECIFICATION table of the source database
	/** The t test specification. */
	private  List<TTestSpecification> tTestSpecification;
	//List, holding the entries of the WIKI_ENTRY table of the source database
	/** The wiki entry. */
	private  List<WikiEntry> wikiEntry;
	//List, holding the entries of the CHATROOM table of the source database
	/** The chatroom. */
	private  List<Chatroom> chatroom;
	
	/** The e composing map. */
	private  Map<Long, EComposing> eComposingMap = new HashMap<Long, EComposing>();
	
	//Maps of new mining-objects, found on the Clix-platform
	

	
	//Map, holding the CourseMining objects, found in the current data extraction process
	/** The course_mining. */
	private	 Map<Long, CourseMining> courseMining;
	
	/** The platform_mining. */
	private  Map<Long, PlatformMining> platformMining;
	//Map, holding the QuizMining objects, found in the current data extraction process
	/** The quiz_mining. */
	private	 Map<Long, QuizMining> quizMining;
	//Map, holding the AssignmentMining objects, found in the current data extraction process
	/** The assignment_mining. */
	private  Map<Long, AssignmentMining> assignmentMining;
	//Map, holding the ScormMining objects, found in the current data extraction process
	/** The scorm_mining. */
	private	 Map<Long, ScormMining> scormMining;
	//Map, holding the ForumMining objects, found in the current data extraction process
	/** The forum_mining. */
	private	 Map<Long, ForumMining> forumMining;
	//Map, holding the ResourceMining objects, found in the current data extraction process
	/** The resource_mining. */
	private	 Map<Long, ResourceMining> resourceMining;
	//Map, holding the UserMining objects, found in the current data extraction process
	/** The user_mining. */
	private  Map<Long, UserMining> userMining;
	//Map, holding the WikiMining objects, found in the current data extraction process
	/** The wiki_mining. */
	private  Map<Long, WikiMining> wikiMining;
	//Map, holding the GroupMining objects, found in the current data extraction process
	/** The group_mining. */
	private  Map<Long, GroupMining> groupMining;
	//Map, holding the QuestionMining objects, found in the current data extraction process
	/** The question_mining. */
	private  Map<Long, QuestionMining> questionMining;
	//Map, holding the RoleMining objects, found in the current data extraction process
	/** The role_mining. */
	private  Map<Long, RoleMining> roleMining;
	//Map, holding the ChatMining objects, found in the current data extraction process
	/** The chat_mining. */
	private  Map<Long, ChatMining> chatMining;
	//Map, holding the LevelMining objects, found in the current data extraction process
	/** The level_mining. */
	private  Map<Long, LevelMining> levelMining;
	
	/** The level_mining. */
	private  Map<Long, LevelMining> oldLevelMining;
	//Map, holding the LevelAssociationMining objects, found in the current data extraction process
	/** The level_association_mining. */
	private  Map<Long, LevelAssociationMining> levelAssociationMining;
	//Map, holding the LevelCourseMining objects, found in the current data extraction process
	/** The level_course_mining. */
	private  Map<Long, LevelCourseMining> levelCourseMining;
	//Map, holding the QuizQuestionMining objects, found in the current data extraction process
	/** The quiz_question_mining. */
	private  Map<Long, QuizQuestionMining> quizQuestionMining;
	//Map, holding the CourseQuizMining objects, found in the current data extraction process
	/** The course_quiz_mining. */
	private  Map<Long, CourseQuizMining> course_quiz_mining;
	//Map, holding the CourseAssignmentMining objects, found in the current data extraction process
	/** The course_assignment_mining. */
	private  Map<Long, CourseAssignmentMining> courseAssignmentMining;
	//Map, holding the CourseScormMining objects, found in the current data extraction process
	/** The course_scorm_mining. */
	private  Map<Long, CourseScormMining> courseScormMining;
	//Map, holding the CourseUserMining objects, found in the current data extraction process
	//private  Map<Long, CourseUserMining> course_user_mining;
	//Map, holding the CourseForumMining objects, found in the current data extraction process
	/** The course_forum_mining. */
	private  Map<Long, CourseForumMining> courseForumMining;
	//Map, holding the CourseGroupMining objects, found in the current data extraction process
	/** The course_group_mining. */
	private  Map<Long, CourseGroupMining> courseGroupMining;
	//Map, holding the CourseResourceMining objects, found in the current data extraction process
	/** The course_resource_mining. */
	private  Map<Long, CourseResourceMining> courseResourceMining;
	//Map, holding the CourseWikiMining objects, found in the current data extraction process
	/** The course_wiki_mining. */
	private  Map<Long, CourseWikiMining> courseWikiMining;
	//Map, holding the GroupUserMining objects, found in the current data extraction process
	/** The group_user_mining. */
	private  Map<Long, GroupUserMining> groupUserMining;
	//Map, holding the QuizUserMining objects, found in the current data extraction process
	/** The quiz_user_mining. */
	private  Map<Long, QuizUserMining> quizUserMining;
		
	//Maps of mining-objects that have been found in a previous extraction process
	
	//Map, holding the CourseMining objects, found in a previous data extraction process
	/** The old_course_mining. */
	private  Map<Long, CourseMining> oldCourseMining;
	
	/** The old_platform_mining. */
	private  Map<Long, PlatformMining> oldPlatformMining;
	//Map, holding the QuizMining objects, found in a previous data extraction process
	/** The old_quiz_mining. */
	private  Map<Long, QuizMining> oldQuizMining;
	//Map, holding the AssignmentMining objects, found in a previous data extraction process
	/** The old_assignment_mining. */
	private  Map<Long, AssignmentMining> oldAssignmentMining;
	//Map, holding the ScormMining objects, found in a previous data extraction process
	/** The old_scorm_mining. */
	private  Map<Long, ScormMining> oldScormMining;
	//Map, holding the ForumMining objects, found in a previous data extraction process
	/** The old_forum_mining. */
	private  Map<Long, ForumMining> oldForumMining;
	//Map, holding the ResourceMining objects, found in a previous data extraction process
	/** The old_resource_mining. */
	private  Map<Long, ResourceMining> oldResourceMining;
	//Map, holding the UserMining objects, found in a previous data extraction process
	/** The old_user_mining. */
	private  Map<Long, UserMining> oldUserMining;
	//Map, holding the WikiMining objects, found in a previous data extraction process
	/** The old_wiki_mining. */
	private  Map<Long, WikiMining> oldWikiMining;
	//Map, holding the GroupMining objects, found in a previous data extraction process
	/** The old_group_mining. */
	private  Map<Long, GroupMining> oldGroupMining;
	//Map, holding the QuestionMining objects, found in a previous data extraction process
	/** The old_question_mining. */
	private  Map<Long, QuestionMining> oldQuestionMining;
	//Map, holding the RoleMining objects, found in a previous data extraction process
	/** The old_role_mining. */
	private  Map<Long, RoleMining> oldRoleMining;
	//Map, holding the ChatMining objects, found in a previous data extraction process
	/** The old_chat_mining. */
	private  Map<Long, ChatMining> oldChatMining;
	
	/** The platform. */
	private  PlatformMining platform;
	
	
	/**
	 * Performs a extraction process for an entire Clix2010 database.
	 *
	 * @param platformName the platform name
	 * @return the clix data
	 */
	public void getClixData(String platformName)
	{
		Clock c = new Clock();
		Long starttime = System.currentTimeMillis()/1000;
		
		platformMining = new HashMap<Long, PlatformMining>();
		
		//Do Import
		
		initialize(platformName);
		
		System.out.println("\n" + c.getAndReset() + " (initializing)" + "\n");
		
		loadData();
		System.out.println("\n" + c.getAndReset() + " (loading data)"+ "\n");
		saveData();
		System.out.println("\n" + c.getAndReset() + " (saving data)"+ "\n");
		
		//Create and save config-object
		Long endtime = System.currentTimeMillis()/1000;
		ConfigMining config = new ConfigMining();
	    config.setLastmodified(System.currentTimeMillis());
	    config.setElapsedTime((endtime) - (starttime));	
	    config.setDatabaseModel("1.2");
	    config.setPlatform(platform.getId());
	    
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();
        dbHandler.saveToDB(session, config);
        dbHandler.closeSession(session);
	}
	
	
	/**
	 * Performs a data-extraction for a Clix2010 database for all objects that are newer than the given time stamp.
	 *
	 * @param platformName the platform name
	 * @param startTime the start time
	 */
	public void updateClixData(String platformName, Long startTime)
	{
		Long currentSysTime = System.currentTimeMillis()/1000;

		Long upperLimit = 0L;
		
		platformMining = new HashMap<Long, PlatformMining>();
		
		while (startTime <= currentSysTime)
		{
			//increase upper limit successively
			upperLimit = startTime + 604800L;
			
			
			initialize(platformName);
			
			//Do Update
			loadData(startTime, upperLimit);
			
			saveData();
			
			startTime = upperLimit;
		}
		
		Long endtime = System.currentTimeMillis()/1000;
		ConfigMining config = new ConfigMining();
	    config.setLastmodified(System.currentTimeMillis());
	    config.setElapsedTime((endtime) - (currentSysTime));	
	    config.setDatabaseModel("1.2");
	    config.setPlatform(platform.getId());
	    
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();
        dbHandler.saveToDB(session, config);
        dbHandler.closeSession(session);
	}
	

	/**
	 * Generates and saves all objects.
	 */
	private void saveData()
	{
		try{
			List<Collection<?>> updates = new ArrayList<Collection<?>>();
			
			if(userMining == null)
			{
				updates.add(platformMining.values());
				
				courseMining = generateCourseMining();
				updates.add(courseMining.values());
				
				quizMining = generateQuizMining();
				updates.add(quizMining.values());
				
				assignmentMining = generateAssignmentMining();
				updates.add(assignmentMining.values());
				
				scormMining = generateScormMining();
				updates.add(scormMining.values());
		
				forumMining = generateForumMining();
				updates.add(forumMining.values());
				
				resourceMining = generateResourceMining();
				updates.add(resourceMining.values());
				
				wikiMining = generateWikiMining();
				updates.add(wikiMining.values());
								
				userMining = generateUserMining();
				updates.add(userMining.values());
								
				groupMining = generateGroupMining();
				updates.add(groupMining.values());
				
				questionMining = generateQuestionMining();
				updates.add(questionMining.values());
				
				roleMining = generateRoleMining();
				updates.add(roleMining.values());
								
				chatMining = generateChatMining();
				updates.add(chatMining.values());
				
				//generateLevelMining();
				//updates.add(level_mining.values());
								
				System.out.println("\nAssociationObjects: \n");
				
				//updates.add(level_association_mining.values());
				
				//updates.add(level_course_mining.values());
				
				quizQuestionMining = generateQuizQuestionMining();
				updates.add(quizQuestionMining.values());
				
				course_quiz_mining = generateCourseQuizMining();
				updates.add(course_quiz_mining.values());
				
				//
				courseAssignmentMining = generateCourseAssignmentMining();
				updates.add(courseAssignmentMining.values());
				
				//level_association_mining = generateLevelAssociationMining();
				//updates.add(level_association_mining.values());
				
				//level_course_mining = generateLevelCourseMining();
				//updates.add(level_course_mining.values());
				
				
	
				courseScormMining = generateCourseScormMining();
				updates.add(courseScormMining.values());
	
				courseForumMining = generateCourseForumMining();
				updates.add(courseForumMining.values());
				
				courseGroupMining = generateCourseGroupMining();
				updates.add(courseGroupMining.values());
				
				courseWikiMining = generateCourseWikiMining();
				updates.add(courseWikiMining.values());
					
				courseResourceMining = generateCourseResourceMining();
				updates.add(courseResourceMining.values());
				
				groupUserMining = generateGroupUserMining();
				updates.add(groupUserMining.values());
				
				quizUserMining = generateQuizUserMining();
				updates.add(quizUserMining.values());
				
			}
			
			System.out.println("\nLogObjects: \n");
			
			updates.add(generateAssignmentLogMining().values());
			
			updates.add(courseAssignmentMining.values());
		
			updates.add(generateCourseLogMining().values());
			
			updates.add(generateForumLogMining().values());
			
			updates.add(generateQuizLogMining().values());
			
			updates.add(generateQuestionLogMining().values());
			
			updates.add(generateScormLogMining().values());
			
			updates.add(generateResourceLogMining().values());

			updates.add(generateWikiLogMining().values());
			
			updates.add(generateChatLogMining().values());
			
			Long objects = 0L;
			
			for(Collection<?> collection:updates)
			{
				objects += collection.size();
			}
			
			if(objects > 0)
			{
				
				IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		        Session session = dbHandler.getMiningSession();
		        System.out.println("Writing to DB");
				dbHandler.saveCollectionToDB(session, updates);
			}
			else
				System.out.println("No new objects found.");
			
	        clearSourceData();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Clears all data-tables.
	 */
	private  void clearSourceData()
	{
		biTrackContentImpressions.clear();
		chatProtocol.clear();
		exerciseGroup.clear();
		//eComponent.clear();
		eComponentMap.clear();
		eComponentType.clear();
		eComposing.clear();
		exercisePersonalised.clear();
		forumEntry.clear();
		forumEntryState.clear();
		person.clear();
		personComponentAssignment.clear();
		platformGroup.clear();
		platformGroupSpecification.clear();
		portfolio.clear();
		portfolioLog.clear();
		scormSessionTimes.clear();
		t2Task.clear();
		tAnswerPosition.clear();
		teamExerciseComposingExt.clear();
		teamExerciseGroup.clear();
		teamExerciseGroupMember.clear();
		tGroupFullSpecification.clear();
		tQtiContent.clear();
		tQtiEvalAssessment.clear();
		tTestSpecification.clear();
		wikiEntry.clear();
		chatroom.clear();
		
	}

	/**
	 * Looks, if there are already values in the Mining database and loads them, if necessary.
	 *
	 * @param platformName the platform name
	 */
	@SuppressWarnings("unchecked")
	private void initialize(String platformName)
	{
		try{
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			
			//accessing DB by creating a session and a transaction using HibernateUtil
	        Session session = dbHandler.getMiningSession();
	        session.clear();	
	        
	        ArrayList<?> l;
	        
	        if(platform == null)
	        {
				Long pid = 0L;
				Long pref = 10L;
		        
		        Query old_platform = session.createQuery("from PlatformMining x order by x.id asc");
		        l = (ArrayList<PlatformMining>) old_platform.list();
		        oldPlatformMining = new HashMap<Long, PlatformMining>();
		        for(int i = 0; i < l.size() ; i++)
		        	oldPlatformMining.put(Long.valueOf(((PlatformMining)l.get(i)).getId()), (PlatformMining)l.get(i));  
		        System.out.println("Read " + oldPlatformMining.size() +" old PlatformMinings."); 
		        
		        for(PlatformMining p : oldPlatformMining.values())
				{
					if( p.getId() > pid)
		        		pid = p.getId();
					if( p.getPrefix() > pref)
		        		pref = p.getPrefix();
					
		        	if(p.getType().equals("Clix_2010") && p.getName().equals(platformName))
		        	{        		
		        		platform = p;
		        	}
				}
				if(platform == null)
				{
					platform = new PlatformMining();
					platform.setId(pid + 1);
					platform.setType("Clix_2010");
					platform.setName(platformName);
					platform.setPrefix(pref + 1);
					platformMining.put(platform.getId(), platform);
				}
	        }
	       
	        
	        Query logCount = session.createQuery("select max(log.id) from ResourceLogMining log where log.platform=" + platform.getId());
	        resourceLogMax = ((ArrayList<Long>) logCount.list()).get(0);
	        if(resourceLogMax == null)
	        	resourceLogMax = 0L;
	        
	        logCount = session.createQuery("select max(log.id) from ChatLogMining log where log.platform=" + platform.getId());
	        chatLogMax = ((ArrayList<Long>) logCount.list()).get(0);
	        if(chatLogMax == null)
	        	chatLogMax = 0L;
	        
	        
	        logCount = session.createQuery("select max(log.id) from AssignmentLogMining log where log.platform=" + platform.getId());
	        assignmentLogMax = ((ArrayList<Long>) logCount.list()).get(0);
	        if(assignmentLogMax == null)
	        	assignmentLogMax = 0L;
	        
	        logCount = session.createQuery("select max(log.id) from CourseLogMining log where log.platform=" + platform.getId());
	        courseLogMax = ((ArrayList<Long>) logCount.list()).get(0);
	        if(courseLogMax == null)
	        	courseLogMax = 0L;
	        
	        logCount = session.createQuery("select max(log.id) from ForumLogMining log where log.platform=" + platform.getId());
	        forumLogMax = ((ArrayList<Long>) logCount.list()).get(0);
	        if(forumLogMax == null)
	        	forumLogMax = 0L;
	        
	        logCount = session.createQuery("select max(log.id) from QuestionLogMining log where log.platform=" + platform.getId());
	        questionLogMax = ((ArrayList<Long>) logCount.list()).get(0);
	        if(questionLogMax == null)
	        	questionLogMax = 0L;
	        
	        logCount = session.createQuery("select max(log.id) from QuizLogMining log where log.platform=" + platform.getId());
	        quizLogMax = ((ArrayList<Long>) logCount.list()).get(0);
	        if(quizLogMax == null)
	        	quizLogMax = 0L;
	        
	        logCount = session.createQuery("select max(log.id) from ScormLogMining log where log.platform=" + platform.getId());
	        scormLogMax = ((ArrayList<Long>) logCount.list()).get(0);
	        if(scormLogMax == null)
	        	scormLogMax = 0L;
	        
	        logCount = session.createQuery("select max(log.id) from WikiLogMining log where log.platform=" + platform.getId());
	        wikiLogMax = ((ArrayList<Long>) logCount.list()).get(0);
	        if(wikiLogMax == null)
	        	wikiLogMax = 0L;
	        
	      
	        
	       
	        
	        Query old_course = session.createQuery("from CourseMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<CourseMining>) old_course.list();
	        oldCourseMining = new HashMap<Long, CourseMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldCourseMining.put(Long.valueOf(((CourseMining)l.get(i)).getId()), (CourseMining)l.get(i));  
	        System.out.println("Read " + oldCourseMining.size() +" old CourseMinings."); 

	        
	        Query old_quiz = session.createQuery("from QuizMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<QuizMining>) old_quiz.list();
	        oldQuizMining = new HashMap<Long, QuizMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldQuizMining.put(Long.valueOf(((QuizMining)l.get(i)).getId()), (QuizMining)l.get(i));  
	        System.out.println("Read " + oldQuizMining.size() +" old QuizMinings."); 
	
	        Query old_assignment = session.createQuery("from AssignmentMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<AssignmentMining>) old_assignment.list();
	        oldAssignmentMining = new HashMap<Long, AssignmentMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldAssignmentMining.put(Long.valueOf(((AssignmentMining)l.get(i)).getId()), (AssignmentMining)l.get(i));  
	        System.out.println("Read " + oldAssignmentMining.size() +" old AssignmentMinings."); 
			
	        Query old_scorm = session.createQuery("from ScormMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<ScormMining>) old_scorm.list();
	        oldScormMining = new HashMap<Long, ScormMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldScormMining.put(Long.valueOf(((ScormMining)l.get(i)).getId()), (ScormMining)l.get(i));  
	        System.out.println("Read " + oldScormMining.size() +" old ScormMinings."); 
	        
	        Query old_forum = session.createQuery("from ForumMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<ForumMining>) old_forum.list();
	        oldForumMining = new HashMap<Long, ForumMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldForumMining.put(Long.valueOf(((ForumMining)l.get(i)).getId()), (ForumMining)l.get(i));  
	        System.out.println("Read " + oldForumMining.size() +" old ForumMinings."); 
			
	        Query old_resource = session.createQuery("from ResourceMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<ResourceMining>) old_resource.list();
	        oldResourceMining = new HashMap<Long, ResourceMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldResourceMining.put(Long.valueOf(((ResourceMining)l.get(i)).getId()), (ResourceMining)l.get(i));  
	        System.out.println("Read " + oldResourceMining.size() +" old ForumMinings."); 
			
	        Query old_user = session.createQuery("from UserMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<UserMining>) old_user.list();
	        oldUserMining = new HashMap<Long, UserMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldUserMining.put(Long.valueOf(((UserMining)l.get(i)).getId()), (UserMining)l.get(i));  
	        System.out.println("Read " + oldUserMining.size() +" old UserMinings."); 
			
	        Query old_wiki = session.createQuery("from WikiMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<WikiMining>) old_wiki.list();
	        oldWikiMining = new HashMap<Long, WikiMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldWikiMining.put(Long.valueOf(((WikiMining)l.get(i)).getId()), (WikiMining)l.get(i));  
	        System.out.println("Read " + oldWikiMining.size() +" old WikiMinings."); 
	
	        Query old_group = session.createQuery("from GroupMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<GroupMining>) old_group.list();
	        oldGroupMining = new HashMap<Long, GroupMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldGroupMining.put(Long.valueOf(((GroupMining)l.get(i)).getId()), (GroupMining)l.get(i));  
	        System.out.println("Read " + oldGroupMining.size() +" old GroupMinings."); 
			
	        Query old_question = session.createQuery("from QuestionMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<QuestionMining>) old_question.list();
	        oldQuestionMining = new HashMap<Long, QuestionMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldQuestionMining.put(Long.valueOf(((QuestionMining)l.get(i)).getId()), (QuestionMining)l.get(i));  
	        System.out.println("Read " + oldQuestionMining.size() +" old QuestionMinings."); 
	        
	        Query old_role = session.createQuery("from RoleMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<RoleMining>) old_role.list();
	        oldRoleMining = new HashMap<Long, RoleMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldRoleMining.put(Long.valueOf(((RoleMining)l.get(i)).getId()), (RoleMining)l.get(i));  
	        System.out.println("Read " + oldRoleMining.size() +" old RoleMinings."); 
	        
	        Query old_level = session.createQuery("from LevelMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<LevelMining>) old_level.list();
	        oldLevelMining = new HashMap<Long, LevelMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldLevelMining.put(Long.valueOf(((LevelMining)l.get(i)).getId()), (LevelMining)l.get(i));  
	        System.out.println("Read " + oldLevelMining.size() +" old LevelMinings."); 
	        
	        /*
	        Query old_department = session.createQuery("from DepartmentMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<DepartmentMining>) old_department.list();
	        old_department_mining = new HashMap<Long, DepartmentMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_department_mining.put(Long.valueOf(((DepartmentMining)l.get(i)).getId()), (DepartmentMining)l.get(i));  
	        System.out.println("Read " + old_department_mining.size() +" old DepartmentMinings."); 
	        
	        Query old_degree = session.createQuery("from DegreeMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<DegreeMining>) old_degree.list();
	        old_degree_mining = new HashMap<Long, DegreeMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_degree_mining.put(Long.valueOf(((DegreeMining)l.get(i)).getId()), (DegreeMining)l.get(i));  
	        System.out.println("Read " + old_degree_mining.size() +" old DegreeMinings."); 
			*/
	        Query old_chat = session.createQuery("from ChatMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<ChatMining>) old_chat.list();
	        oldChatMining = new HashMap<Long, ChatMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	oldChatMining.put(Long.valueOf(((ChatMining)l.get(i)).getId()), (ChatMining)l.get(i));  
	        System.out.println("Read " + oldChatMining.size() +" old ChatMinings."); 
	        

	        
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Loads all necessary tables from the Clix2010 database.
	 */
	@SuppressWarnings("unchecked")
	private  void loadData()
	{
		try{
			
			
			//accessing DB by creating a session and a transaction using HibernateUtil
	        Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
	        //Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
	        session.clear();
     
	        System.out.println("Starting data extraction.");  
	        
	        
	        Query pers = session.createQuery("from Person x order by x.id asc");
	        person = pers.list();	        
	        System.out.println("Person tables: " + person.size()); 

	        Query chatro = session.createQuery("from Chatroom x order by x.id asc");
	        chatroom = chatro.list();	        
	        System.out.println("Chatroom tables: " + chatroom.size());  
	
	        Query eCompo = session.createQuery("from EComposing x order by x.id asc");
	        eComposing = eCompo.list();	        
	        System.out.println("EComposing tables: " + eComposing.size()); 
	        for(int i = 0; i < eComposing.size(); i++)
	        {
	        	eComposingMap.put(eComposing.get(i).getComponent(), eComposing.get(i));
	        }
	        
	        Query portf = session.createQuery("from Portfolio x order by x.id asc");
	        portfolio = portf.list();	        
	        System.out.println("Portfolio tables: " + portfolio.size()); 
	        
	        Query biTrack = session.createQuery("from BiTrackContentImpressions x order by x.id asc");
	        biTrackContentImpressions = biTrack.list();	        
	        System.out.println("biTrackContentImpressions tables: " + biTrackContentImpressions.size());     
	        
	        Query chatProt = session.createQuery("from ChatProtocol x order by x.id asc");
	        chatProtocol = chatProt.list();	        
	        System.out.println("ChatProtocol tables: " + chatProtocol.size());   
	        
	        Query perComAss = session.createQuery("from PersonComponentAssignment x order by x.id asc");
	        personComponentAssignment = perComAss.list();	        
	        System.out.println("PersonComponentAssignment tables: " + personComponentAssignment.size());   
	        
	        Query eCompTy = session.createQuery("from EComponentType x order by x.id asc");
	        eComponentType = eCompTy.list();	        
	        System.out.println("EComponentType tables: " + eComponentType.size());  
	                
	        Query eComp = session.createQuery("from EComponent x order by x.id asc");
	        List<EComponent> tmp = eComp.list();	
	        eComponentMap = new HashMap<Long, EComponent>();
	        for(EComponent c : tmp)
	        {
	        	eComponentMap.put(c.getId(), c);
	        }
	        tmp.clear();

	        System.out.println("EComponent tables: " + eComponentMap.values().size());    
	        
	        Query exPer = session.createQuery("from ExercisePersonalised x order by x.id asc");
	        exercisePersonalised = exPer.list();	        
	        System.out.println("ExercisePersonalised tables: " + exercisePersonalised.size()); 
	        
	       	Query exGro = session.createQuery("from ExerciseGroup x order by x.id asc");
	        exerciseGroup = exGro.list();	        
	        System.out.println("ExerciseGroup tables: " + exerciseGroup.size()); 
	        
	        Query foEnt = session.createQuery("from ForumEntry x order by x.id asc");
	        forumEntry = foEnt.list();	        
	        System.out.println("ForumEntry tables: " + forumEntry.size()); 
	        
	        Query foEntS = session.createQuery("from ForumEntryState x order by x.id asc");
	        forumEntryState = foEntS.list();	        
	        System.out.println("ForumEntryState tables: " + forumEntryState.size()); 

	        
	        Query plGrSp = session.createQuery("from PlatformGroupSpecification x order by x.id asc");
	        platformGroupSpecification = plGrSp.list();	        
	        System.out.println("PlatformGroupSpecification tables: " + platformGroupSpecification.size()); 
	        
	        Query plGr = session.createQuery("from PlatformGroup x order by x.id asc");
	        platformGroup = plGr.list();	        
	        System.out.println("PlatformGroup tables: " + platformGroup.size()); 
	     
	        Query porLo = session.createQuery("from PortfolioLog x order by x.id asc");
	        portfolioLog = porLo.list();	        
	        System.out.println("PortfolioLog tables: " + portfolioLog.size()); 
	        
	        Query scoSes = session.createQuery("from ScormSessionTimes x order by x.id asc");
	        scormSessionTimes = scoSes.list();	        
	        System.out.println("ScormSession tables: " + scormSessionTimes.size()); 
	        
	        Query t2Ta = session.createQuery("from T2Task x order by x.id asc");
	        t2Task = t2Ta.list();	        
	        System.out.println("T2Task tables: " + t2Task.size()); 
	        
	        Query tAnPos = session.createQuery("from TAnswerPosition x order by x.id asc");
	        tAnswerPosition = tAnPos.list();	        
	        System.out.println("TAnswerPosition tables: " + tAnswerPosition.size()); 
	
	        Query tECEx = session.createQuery("from TeamExerciseComposingExt x order by x.id asc");
	        teamExerciseComposingExt = tECEx.list();	        
	        System.out.println("TeamExerciseComposingExt tables: " + teamExerciseComposingExt.size()); 
	
	        Query tEG = session.createQuery("from TeamExerciseGroup x order by x.id asc");
	        teamExerciseGroup = tEG.list();	        
	        System.out.println("TeamExerciseGroup tables: " + teamExerciseGroup.size()); 
	
	        Query tEGMem = session.createQuery("from TeamExerciseGroupMember x order by x.id asc");
	        teamExerciseGroupMember = tEGMem.list();	        
	        System.out.println("TeamExerciseGroupMember tables: " + teamExerciseGroupMember.size()); 
	
	        Query tGFSpec = session.createQuery("from TGroupFullSpecification x order by x.id asc");
	        tGroupFullSpecification = tGFSpec.list();	        
	        System.out.println("TGroupFullSpecification tables: " + tGroupFullSpecification.size()); 
	
	        Query tQC = session.createQuery("from TQtiContent x order by x.id asc");
	        tQtiContent = tQC.list();	        
	        System.out.println("TQtiContent tables: " + tQtiContent.size()); 
	
	        Query tQEA = session.createQuery("from TQtiEvalAssessment x order by x.id asc");
	        tQtiEvalAssessment = tQEA.list();	        
	        System.out.println("TQtiEvalAssessment tables: " + tQtiEvalAssessment.size()); 
	
	        Query tTS = session.createQuery("from TTestSpecification x order by x.id asc");
	        tTestSpecification = tTS.list();	        
	        System.out.println("TTestSpecification tables: " + tTestSpecification.size()); 
	
	        Query wE = session.createQuery("from WikiEntry x order by x.id asc");
	        wikiEntry = wE.list();	        
	        System.out.println("WikiEntry tables: " + wikiEntry.size()); 

//			writeToFile();
//	        loadFromFile();
	        
		}catch(Exception e)
		{
			e.printStackTrace();
		}

    	
	}
	
	/**
	 * Loads all tables needed for the data-extraction from the Clix database.
	 *
	 * @param start the start
	 * @param end the end
	 */
	@SuppressWarnings("unchecked")
	private  void loadData(Long start, Long end)
	{
		try{
			//accessing DB by creating a session and a transaction using HibernateUtil
	        Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
	        session.clear();
	        
	        System.out.println("Starting data extraction.");  

	        //Read the tables that don't refer to log-entries once
	        if (userMining == null)
	        {
		        Query chatro = session.createQuery("from Chatroom x order by x.id asc");
		        chatroom = chatro.list();	        
		        System.out.println("Chatroom tables: " + chatroom.size());  
		        
		        Query eCompo = session.createQuery("from EComposing x order by x.id asc");
		        eComposing = eCompo.list();	        
		        System.out.println("EComposing tables: " + eComposing.size()); 
		        for(int i = 0; i < eComposing.size(); i++)
		        {
		        	eComposingMap.put(eComposing.get(i).getComponent(), eComposing.get(i));
		        }
		        
		        Query portf = session.createQuery("from Portfolio x order by x.id asc");
		        portfolio = portf.list();	        
		        System.out.println("Portfolio tables: " + portfolio.size()); 
		        
		        Query perComAss = session.createQuery("from PersonComponentAssignment x order by x.id asc");
		        personComponentAssignment = perComAss.list();	        
		        System.out.println("PersonComponentAssignment tables: " + personComponentAssignment.size());   
		        
		        Query eCompTy = session.createQuery("from EComponentType x order by x.id asc");
		        eComponentType = eCompTy.list();	        
		        System.out.println("EComponentType tables: " + eComponentType.size());  
		                
		        Query eComp = session.createQuery("from EComponent x order by x.id asc");
		        List<EComponent> tmp = eComp.list();	
		        for(EComponent c : tmp)
		        	eComponentMap.put(c.getId(), c);
		        tmp.clear();    
		        System.out.println("EComponent tables: " + eComponentMap.values().size());    
		        
		        Query pers = session.createQuery("from Person x order by x.id asc");
		        person = pers.list();	        
		        System.out.println("Person tables: " + person.size()); 
		        
		        Query plGrSp = session.createQuery("from PlatformGroupSpecification x order by x.id asc");
		        platformGroupSpecification = plGrSp.list();	        
		        System.out.println("PlatformGroupSpecification tables: " + platformGroupSpecification.size()); 
		        
		        Query plGr = session.createQuery("from PlatformGroup x order by x.id asc");
		        platformGroup = plGr.list();	        
		        System.out.println("PlatformGroup tables: " + platformGroup.size()); 
		     
		        Query porLo = session.createQuery("from PortfolioLog x order by x.id asc");
		        portfolioLog = porLo.list();	        
		        System.out.println("PortfolioLog tables: " + portfolioLog.size()); 
		        
		        Query t2Ta = session.createQuery("from T2Task x order by x.id asc");
		        t2Task = t2Ta.list();	        
		        System.out.println("T2Task tables: " + t2Task.size()); 
		
		        Query tECEx = session.createQuery("from TeamExerciseComposingExt x order by x.id asc");
		        teamExerciseComposingExt = tECEx.list();	        
		        System.out.println("TeamExerciseComposingExt tables: " + teamExerciseComposingExt.size()); 
		
		        Query tEG = session.createQuery("from TeamExerciseGroup x order by x.id asc");
		        teamExerciseGroup = tEG.list();	        
		        System.out.println("TeamExerciseGroup tables: " + teamExerciseGroup.size()); 
		
		        Query tEGMem = session.createQuery("from TeamExerciseGroupMember x order by x.id asc");
		        teamExerciseGroupMember = tEGMem.list();	        
		        System.out.println("TeamExerciseGroupMember tables: " + teamExerciseGroupMember.size()); 
		
		        Query tGFSpec = session.createQuery("from TGroupFullSpecification x order by x.id asc");
		        tGroupFullSpecification = tGFSpec.list();	        
		        System.out.println("TGroupFullSpecification tables: " + tGroupFullSpecification.size()); 
		
		        Query tQC = session.createQuery("from TQtiContent x order by x.id asc");
		        tQtiContent = tQC.list();	        
		        System.out.println("TQtiContent tables: " + tQtiContent.size()); 
	
		        Query tTS = session.createQuery("from TTestSpecification x order by x.id asc");
		        tTestSpecification = tTS.list();	        
		        System.out.println("TTestSpecification tables: " + tTestSpecification.size()); 
	
			}
	        
	        //Read log-data successively, using the time stamps
	        
	        //The Clix database uses date representation of the type varchar, so the unix-timestamp has to be converted to a string
	        String startStr = TimeConverter.getStringRepresentation(start);
	        String endStr = TimeConverter.getStringRepresentation(end);
	        
	        Query chatProt = session.createQuery("from ChatProtocol x where x.lastUpdated>=:start and x.lastUpdated<=:end order by x.id asc");
	    	chatProt.setParameter("start", startStr);
	    	chatProt.setParameter("end", endStr);
	        chatProtocol = chatProt.list();	        
	        System.out.println("ChatProtocol tables: " + chatProtocol.size());   
	        
	        Query foEnt = session.createQuery("from ForumEntry x where x.lastUpdated>=:start and x.lastUpdated<=:end order by x.id asc");
	        foEnt.setParameter("start", startStr);
	        foEnt.setParameter("end", endStr);
	        forumEntry = foEnt.list();	        
	        System.out.println("ForumEntry tables: " + forumEntry.size()); 
	        
	        Query foEntS = session.createQuery("from ForumEntryState x where x.lastUpdated>=:start and x.lastUpdated<=:end order by x.id asc");
	        foEntS.setParameter("start", startStr);
	        foEntS.setParameter("end", endStr);
	        forumEntryState = foEntS.list();	        
	        System.out.println("ForumEntryState tables: " + forumEntryState.size()); 
	        
	        Query tAnPos = session.createQuery("from TAnswerPosition x order by x.id asc");
	        tAnswerPosition = tAnPos.list();	        
	        System.out.println("TAnswerPosition tables: " + tAnswerPosition.size()); 
	    	
	        Query tQEA = session.createQuery("from TQtiEvalAssessment x where x.lastInvocation>=:start and x.lastInvocation<=:end order by x.id asc");
	        tQEA.setParameter("start", startStr);
	        tQEA.setParameter("end", endStr);
	        tQtiEvalAssessment = tQEA.list();	        
	        System.out.println("TQtiEvalAssessment tables: " + tQtiEvalAssessment.size()); 
	        
	        Query scoSes = session.createQuery("from ScormSessionTimes x where x.lastUpdated>=:start and x.lastUpdated<=:end order by x.id asc");
	        scoSes.setParameter("start", startStr);
	        scoSes.setParameter("end", endStr);
	        scormSessionTimes = scoSes.list();	        
	        System.out.println("ScormSession tables: " + scormSessionTimes.size()); 
	    	
	        Query wE = session.createQuery("from WikiEntry x where x.lastUpdated>=:start and x.lastUpdated<=:end order by x.id asc");
	        wE.setParameter("start", startStr);
	        wE.setParameter("end", endStr);
	        wikiEntry = wE.list();	        
	        System.out.println("WikiEntry tables: " + wikiEntry.size()); 
	        
	        Query exPer = session.createQuery("from ExercisePersonalised x  where x.uploadDate>=:start and x.uploadDate<=:end order by x.id asc");
	        exPer.setParameter("start", startStr);
	        exPer.setParameter("end", endStr);
	        exercisePersonalised = exPer.list();	        
	        System.out.println("ExercisePersonalised tables: " + exercisePersonalised.size()); 
	        
	        
	       	Query exGro = session.createQuery("from ExerciseGroup x order by x.id asc");
	        exerciseGroup = exGro.list();	        
	        System.out.println("ExerciseGroup tables: " + exerciseGroup.size()); 
	        
	        //The date-strings have to be modified, because the date format of the table BiTrackContentImpressions is different
	        startStr = startStr.substring(0, startStr.indexOf(" "));
	        endStr = endStr.substring(0, endStr.indexOf(" "));
	   
	        Query biTrack = session.createQuery("from BiTrackContentImpressions x where x.dayOfAccess>=:start and x.dayOfAccess<=:end order by x.id asc");
	        biTrack.setParameter("start", startStr);
	        biTrack.setParameter("end", endStr);
	        biTrackContentImpressions = biTrack.list();	        
	        System.out.println("biTrackContentImpressions tables: " + biTrackContentImpressions.size()); 

			//writeToFile();
			//loadFromFile();
	        
		}catch(Exception e)
		{
			e.printStackTrace();
		}
        
        
	}
	
	//Generators for "" objects
	
	/**
	 * Generates ChatMining-objects from the given data.
	 *
	 * @return HashMap with ChatMining-objects
	 */
	private Map<Long, ChatMining> generateChatMining() {
		HashMap<Long, ChatMining> chats = new HashMap<Long, ChatMining>();
		try{
			for(Chatroom loadedItem : chatroom)
			{
				ChatMining item = new ChatMining();
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setTitle(loadedItem.getTitle());
				item.setChatTime(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(platform.getId());
				
				
				
				chats.put(item.getId(), item);
			}
			
			
			
			System.out.println("Generated " + chats.size() + " ChatMining");
		}catch(Exception e)
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
		HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
		HashMap<Long, ResourceMining> resources = new HashMap<Long, ResourceMining>();
		try{
			for(EComponentType loadedItem : eComponentType)
			{
				if(loadedItem.getCharacteristicId() == 1L || loadedItem.getCharacteristicId() == 11L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);
			}
			
			for(EComponent loadedItem : eComponentMap.values())
			{
				ResourceMining item = new ResourceMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setPlatform(platform.getId());
					
					if(eCTypes.get(loadedItem.getType()).getCharacteristicId() != null)
						if(eCTypes.get(loadedItem.getType()).getCharacteristicId() == 1L)
							item.setType("Medium");
						else
							item.setType("Folder");
					
					resources.put(item.getId(), item);
				}
				
			}
			System.out.println("Generated " + resources.size() + " ResourceMining.");
		}catch(Exception e)
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
		HashMap<Long, CourseMining> courses = new HashMap<Long, CourseMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(EComponentType loadedItem : eComponentType)
			{
				if(loadedItem.getCharacteristicId() == 4L || loadedItem.getCharacteristicId() == 5L || loadedItem.getCharacteristicId() == 3L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);
			}
			for(EComponent loadedItem : eComponentMap.values())
			{
				CourseMining item = new CourseMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setStartdate(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(platform.getId());
					
					courses.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + courses.size() + " CourseMining.");
		}catch(Exception e)
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
		HashMap<Long, UserMining> users = new HashMap<Long, UserMining>();
		try{
			for(Person loadedItem : person)
			{
				UserMining item = new UserMining();
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setPlatform(platform.getId());
				item.setLastlogin(TimeConverter.getTimestamp(loadedItem.getLastLoginTime()));
				item.setFirstAccess(TimeConverter.getTimestamp(loadedItem.getFirstLoginTime()));
				
				if(loadedItem.getGender() == 1)
					item.setGender(false);
				else
					item.setGender(true);
				item.setLogin(Encoder.createMD5(loadedItem.getLogin()));
				
				users.put(item.getId(), item);
			}
			System.out.println("Generated " + users.size() + " UserMining.");
		}catch(Exception e)
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
		HashMap<Long, AssignmentMining> assignments = new HashMap<Long, AssignmentMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(EComponentType loadedItem:  eComponentType)
				if(loadedItem.getCharacteristicId() == 8L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);

			for(EComponent loadedItem : eComponentMap.values())
			{
				AssignmentMining item = new AssignmentMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					if(loadedItem.getStartDate() != null)
						item.setTimeopen(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(platform.getId());
					
					assignments.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + assignments.size() + " AssignmentMinings.");
		}catch(Exception e)
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
		HashMap<Long, QuizMining> quizzes = new HashMap<Long, QuizMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			HashMap<Long, EComposing> eCompo = new HashMap<Long, EComposing>();
			for(EComponentType loadedItem : eComponentType)
			{
				if(loadedItem.getCharacteristicId() == 14L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);
			}
			for(EComposing loadedItem : eComposing)
			{
				eCompo.put(loadedItem.getComposing(), loadedItem);
			}
			for(EComponent loadedItem : eComponentMap.values())
			{
				QuizMining item = new QuizMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setTimeOpen(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(platform.getId());
					if(eCompo.get(loadedItem.getId()) != null)
						item.setTimeClose(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getEndDate()));
					quizzes.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + quizzes.size() + " QuizMinings.");
		}catch(Exception e)
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
		
		HashMap<Long, ForumMining> forums = new HashMap<Long, ForumMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(EComponentType loadedItem : eComponentType)
			{
				if(loadedItem.getCharacteristicId() == 2L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);
			}
			for(EComponent loadedItem : eComponentMap.values())
			{
				ForumMining item = new ForumMining();
				if(eCTypes.get(loadedItem.getType()) != null && eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("forum"))
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setSummary(loadedItem.getDescription());
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(platform.getId());
					forums.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + forums.size() + " ForumMinings.");
		}catch(Exception e)
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
		HashMap<Long, RoleMining> roles = new HashMap<Long, RoleMining>();
		try{
			for(PlatformGroup loadedItem : platformGroup)
			{
				RoleMining item = new RoleMining();
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getTypeId()));
				item.setPlatform(platform.getId());
				switch(Integer.valueOf(item.getId()+""))
				{
				case 1:
				{
					item.setName("Standard");
					item.setShortname("Standard");
					item.setSortorder(1L);
					break;
				}
				case 2:
					item.setName("Admininstrator");
					item.setShortname("Administrator");
					item.setSortorder(0L);
					break;
				default:
					item.setName("Portal (extern)");
					item.setShortname("Portal");
					item.setSortorder(2L);
					break;
				}
				
				roles.put(item.getId(), item);
			}
			System.out.println("Generated " + roles.size() + " RoleMinings.");
		}catch(Exception e)
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
		HashMap<Long, GroupMining> groups = new HashMap<Long, GroupMining>();
		
		try{
			for(PlatformGroup loadedItem : platformGroup)
			{
				GroupMining item = new GroupMining();
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreated()));
				item.setPlatform(platform.getId());
				
				groups.put(item.getId(), item);
			}
			System.out.println("Generated " + groups.size() + " GroupMinings.");
		}catch(Exception e)
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
		HashMap<Long, WikiMining> wikis = new HashMap<Long, WikiMining>();
		
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(EComponentType loadedItem : eComponentType)
			{
				if(loadedItem.getCharacteristicId() == 2L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);
			}
			for(EComponent loadedItem : eComponentMap.values())
			{
				WikiMining item = new WikiMining();
				if(eCTypes.get(loadedItem.getType()) != null && eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("wiki"))
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setSummary(loadedItem.getDescription());
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(platform.getId());
					
					wikis.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + wikis.size() + " WikiMinings.");
		}catch(Exception e)
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
	private Map<Long, DepartmentMining> generateDepartmentMining()
	{
		HashMap<Long, DepartmentMining> departments = new HashMap<Long, DepartmentMining>();
		try{
		
			System.out.println("Generated " + departments.size() + " DepartmentMinings.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return departments;
	}
	*/

	/**
	 * Generates DegreeMining-objects from the given data
	 * 
	 * @return	HashMap with DegreeMining-objects
	 */
	/*
	private Map<Long, DegreeMining> generateDegreeMining()
	{
		HashMap<Long, DegreeMining> degrees = new HashMap<Long, DegreeMining>();
		try{
		
			System.out.println("Generated " + degrees.size() + " DegreeMinings.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return degrees;
	}
	*/

	/**
	 * Generates QuestionMining-objects from the given data
	 * 
	 * @return	HashMap with QuestionMining-objects
	 */
	private Map<Long, QuestionMining> generateQuestionMining()
	{
		HashMap<Long, QuestionMining> questions = new HashMap<Long, QuestionMining>();	
		
		try{
			HashMap<Long, T2Task> t2t = new HashMap<Long, T2Task>();
			for(T2Task loadedItem : t2Task)
				t2t.put(loadedItem.getTopicId(), loadedItem);
			
			for(TQtiContent loadedItem : tQtiContent)
			{
				QuestionMining item = new QuestionMining();
	
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					if(t2t.get(loadedItem.getId()) != null)
					{
						item.setText(t2t.get(loadedItem.getId()).getQuestionText());
						item.setType(t2t.get(loadedItem.getId()).getTaskType() +"");
					}
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreated()));
					item.setPlatform(platform.getId());
	
					questions.put(item.getId(), item);
			}
			System.out.println("Generated " + questions.size() + " QuestionMinings.");
			
		}catch(Exception e)
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
		HashMap<Long, ScormMining> scorms = new HashMap<Long, ScormMining>();	
		
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			HashMap<Long, EComposing> eCompo = new HashMap<Long, EComposing>();
			
			for(EComponentType loadedItem : eComponentType)
			{
				if(loadedItem.getCharacteristicId() == 10L || loadedItem.getCharacteristicId() == 1L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);
			}
			for(EComposing loadedItem : eComposing)
			{
				eCompo.put(loadedItem.getComposing(), loadedItem);
			}
			for(EComponent loadedItem : eComponentMap.values())
			{
				ScormMining item = new ScormMining();
				if(eCTypes.get(loadedItem.getType()) != null && eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("scorm"))
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimeCreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(platform.getId());
					
					if(eCompo.get(loadedItem.getId()) != null)
					{
						item.setTimeOpen(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getStartDate()));
						item.setTimeClose(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getEndDate()));
					}
					scorms.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + scorms.size() + " ScormMinings.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return scorms;
	}
	
	//Generators for relationships
	/**
	 * Generates QuizQuestionMining-objects from the given data.
	 *
	 * @return HashMap with QuizQuestionMining-objects
	 */
	private Map<Long, QuizQuestionMining> generateQuizQuestionMining()
	{
		HashMap<Long, QuizQuestionMining> quizQuestions = new HashMap<Long, QuizQuestionMining>();
		
		try{
			for(TTestSpecification loadedItem : tTestSpecification)
			{
				QuizQuestionMining item = new QuizQuestionMining();
				item.setQuestion(Long.valueOf(platform.getPrefix() + "" + loadedItem.getTest()), questionMining, oldQuestionMining);
				item.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getTask()), quizMining, oldQuizMining);
				//Id for QuizQuestion entry is a combination of the question-id and the quiz-id
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId().hashCode()));
				item.setPlatform(platform.getId());
				
				if(item.getQuestion() != null && item.getQuiz() != null)
					quizQuestions.put(item.getId(), item);
			}
			
			System.out.println("Generated " + quizQuestions.size() + " QuizQuestionMinings.");
		}catch(Exception e)
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
		HashMap<Long, CourseScormMining> courseScorms = new HashMap<Long, CourseScormMining>();
		
		try{
			for(EComposing loadedItem : eComposing)
			{
				if(scormMining.get(loadedItem.getComponent()) != null || oldScormMining.get(loadedItem.getComponent()) != null)
				{
					CourseScormMining item = new CourseScormMining();
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), courseMining, oldCourseMining);
					item.setScorm(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), scormMining, oldScormMining);
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setPlatform(platform.getId());
					
					if(item.getCourse() != null && item.getScorm() != null)
						courseScorms.put(item.getId(), item);
				}
				
			}
			
			System.out.println("Generated " + courseScorms.size() + " CourseScormMinings.");
		}catch(Exception e)
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
		HashMap<Long, CourseAssignmentMining> courseAssignments = new HashMap<Long, CourseAssignmentMining>();
		try{
			for(EComposing loadedItem : eComposing)
			{
				if(assignmentMining.get(loadedItem.getComponent()) != null || oldAssignmentMining.get(loadedItem.getComponent()) != null)
				{
					CourseAssignmentMining item = new CourseAssignmentMining();
					
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), courseMining, oldCourseMining);
					item.setAssignment(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), assignmentMining, oldAssignmentMining);
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setPlatform(platform.getId());
					
					if(item.getCourse() != null && item.getAssignment() != null)
						courseAssignments.put(item.getId(), item);
				}
				
			}
			System.out.println("Generated " + courseAssignments.size() + " CourseAssignmentMinings.");
			
		}catch(Exception e)
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
		HashMap<Long, CourseResourceMining> courseResources = new HashMap<Long, CourseResourceMining>();
		
		try{
			for(EComposing loadedItem : eComposing)
			{
				if(resourceMining.get(loadedItem.getComponent()) != null || oldResourceMining.get(loadedItem.getComponent()) != null)
				{
					CourseResourceMining item = new CourseResourceMining();
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), courseMining, oldCourseMining);
					item.setResource(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), resourceMining, oldResourceMining);
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setPlatform(platform.getId());
					
					if(item.getResource() != null && item.getCourse() != null)
						courseResources.put(item.getId(), item);
				}
				
			}
			System.out.println("Generated " + courseResources.size() + " CourseResourceMinings.");
			
		}catch(Exception e)
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
		HashMap<Long, CourseQuizMining> courseQuizzes = new HashMap<Long, CourseQuizMining>();
		
		try{
			for(EComposing loadedItem : eComposing)
			{
				if(quizMining.get(loadedItem.getComponent()) != null || oldQuizMining.get(loadedItem.getComponent()) != null)
				{
					CourseQuizMining item = new CourseQuizMining();
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), courseMining, oldCourseMining);
					item.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), quizMining, oldQuizMining);
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setPlatform(platform.getId());
					
					if(item.getCourse() != null && item.getQuiz() != null)
						courseQuizzes.put(item.getId(), item);
				}
				
			}
			System.out.println("Generated " + courseQuizzes.size() + " CourseQuizMinings.");
			
		}catch(Exception e)
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
	private  HashMap<Long, QuizUserMining> generateQuizUserMining()
	{
		HashMap<Long, QuizUserMining> quizUsers = new HashMap<Long, QuizUserMining>();
		try{
			for(TQtiEvalAssessment loadedItem :  tQtiEvalAssessment)
			{
				QuizUserMining item = new QuizUserMining();
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), courseMining, oldCourseMining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCandidate()), userMining, oldUserMining);
				item.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getAssessment()), quizMining, oldQuizMining);
				item.setFinalGrade(loadedItem.getEvaluatedScore());
				item.setTimeModified(TimeConverter.getTimestamp(loadedItem.getLastInvocation()));
				item.setPlatform(platform.getId());
				
				if(item.getCourse() != null && item.getQuiz() != null && item.getUser() != null)
					quizUsers.put(item.getId(), item);
			}
			System.out.println("Generated " + quizUsers.size() + " QuizUserMinings.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return quizUsers;
	}
	
	/**
	 * Generates CourseUserMining-objects from the given data.
	 *
	 * @return HashMap with CourseUserMining-objects
	 */
	private Map<Long, CourseUserMining> generateCourseUserMining()
	{
		HashMap<Long, CourseUserMining> courseUsers = new HashMap<Long, CourseUserMining>();
		try{
			//Find out teachers first
			for(PersonComponentAssignment loadedItem : personComponentAssignment)
			{
				CourseUserMining item = new CourseUserMining();
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), courseMining, oldCourseMining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), userMining, oldUserMining);
				item.setRole(Long.valueOf(platform.getPrefix() + "" + 2), roleMining, oldRoleMining);
				item.setEnrolstart(TimeConverter.getTimestamp(loadedItem.getFirstEntered()));
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId().hashCode()));
				item.setPlatform(platform.getId());
				
				if(item.getCourse() != null && item.getUser() != null)
				{
					courseUsers.put(item.getId(), item);
				}
				
			}
			for(Portfolio loadedItem : portfolio)
			{
				CourseUserMining item = new CourseUserMining();
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), courseMining, oldCourseMining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), userMining, oldUserMining);
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setRole(Long.valueOf(platform.getPrefix() + "" + 1), roleMining, oldRoleMining);
				item.setEnrolstart(TimeConverter.getTimestamp(loadedItem.getStartDate()));
				item.setEnrolend(TimeConverter.getTimestamp(loadedItem.getEndDate()));
				
				if(item.getCourse() != null && item.getUser() != null)
				{
					courseUsers.put(item.getId(), item);
				}				
			}
			
			
			System.out.println("Generated " + courseUsers.size() + " CourseUserMinings.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseUsers;
	}
	
	/**
	 * Generates CourseWikiMining-objects from the given data.
	 *
	 * @return HashMap with CourseWikiMining-objects
	 */
	private Map<Long, CourseWikiMining> generateCourseWikiMining()
	{
		HashMap<Long, CourseWikiMining> courseWikis = new HashMap<Long, CourseWikiMining>();
		try{
			
			for(EComposing loadedItem : eComposing)
			{
				if(wikiMining.get(loadedItem.getComponent()) != null || oldWikiMining.get(loadedItem.getComponent()) != null)
				{
					CourseWikiMining item = new CourseWikiMining();
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), courseMining, oldCourseMining);
					item.setWiki(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), wikiMining, oldWikiMining);
					item.setPlatform(platform.getId());
					
					if(item.getCourse() != null && item.getWiki() != null)
						courseWikis.put(item.getId(), item);
				}
			}
		
			System.out.println("Generated " + courseWikis.size() + " CourseWikiMinings.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseWikis;
	}
	
	/**
	 * Generates level mining.
	 *
	 * @return the hash map
	 */
	private Map<Long, LevelAssociationMining> generateLevelAssociationMining()
	{		
		//System.out.println("Generated " + level_association_mining.size() + " LevelAssociationMining.");
		return levelAssociationMining;
	}
	
	/**
	 * Generates level association mining.
	 *
	 * @return the hash map
	 */
	private Map<Long, LevelMining> generateLevelMining()
	{	/*	
		ArrayList<ArrayList<LevelMining>> levels = new ArrayList<ArrayList<LevelMining>>();
		
		ArrayList<LevelMining> tmpCList = new ArrayList<LevelMining>();
		//Create lowest tier levels and level-course-relations
		for(CourseMining course : course_mining.values())
		{
			EComposing tmp = eComposingMap.get(Long.valueOf((course.getId()+"").substring(2)));
			if(tmp != null)
			{
				EComponent component = eComponentMap.get(tmp.getParent());
				if(component != null)
				{
					LevelMining level = new LevelMining();
					level.setTitle(component.getName());
					level.setPlatform(platform.getId());
					level.setId(Long.valueOf(platform.getPrefix() + "" + component.getId()));
					level_mining.put(level.getId(), level);
					tmpCList.add(level);
					
					LevelCourseMining levCou = new LevelCourseMining();
					levCou.setId(Long.valueOf(platform.getPrefix() + "" + tmp.getId()));
					levCou.setCourse(course.getId(), course_mining, old_course_mining);
					levCou.setLevel(level.getId(), level_mining, old_level_mining);	
					
					level_course_mining.put(levCou.getId(), levCou);
				}
			}

		}
		levels.add(tmpCList);
		//Create any levels above lowest tier and according level-associations 
		while(levels.get(levels.size() - 1).size() > 0)
		{
			ArrayList<LevelMining> tmpList = new ArrayList<LevelMining>();
			for(LevelMining lower : levels.get(levels.size()-1))
			{
				EComposing tmp = eComposingMap.get(Long.valueOf((lower.getId()+"").substring(2)));
				if(tmp != null)
				{
					EComponent component = eComponentMap.get(tmp.getParent());
					if(component != null)
					{
						LevelMining level = new LevelMining();
						level.setTitle(component.getName());
						level.setPlatform(platform.getId());
						level.setId(Long.valueOf(platform.getPrefix() + "" + component.getId()));
						level_mining.put(level.getId(), level);
						tmpList.add(level);
						
						LevelAssociationMining levAsc = new LevelAssociationMining();
						levAsc.setId(Long.valueOf(platform.getPrefix() + "" + tmp.getId()));
						levAsc.setLower(lower.getId(), level_mining, old_level_mining);
						levAsc.setUpper(level.getId(), level_mining, old_level_mining);	
						
						level_association_mining.put(levAsc.getId(), levAsc);
					}
				}
			}
			levels.add(tmpList);
		}
		
		int n = 1;
		for(int i = levels.size(); i >= 0; i--)
		{
			for(LevelMining l :levels.get(i))
				l.setDepth(n);
			n++;
		}
					*/
		
		//System.out.println("Generated " + level_mining.size() + " LevelMining.");
		
		return levelMining;
	}
	
	
	/**
	 * Generates level course mining.
	 *
	 * @return the hash map
	 */
	private Map<Long, LevelCourseMining> generateLevelCourseMining()
	{
		//System.out.println("Generated " + level_course_mining.size() + " LevelCourseMining.");
		return levelCourseMining;
	}
	
	/**
	 * Generates DegreeCourseMining-objects from the given data.
	 *
	 * @return HashMap with DegreeCourseMining-objects
	 */
	/*
	private Map<Long, DegreeCourseMining> generateDegreeCourseMining()
	{
		HashMap<Long, DegreeCourseMining> degreeCourses = new HashMap<Long, DegreeCourseMining>();
		
		try{
			System.out.println("Generated " + degreeCourses.size() + " DegreeCourseMinings.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return degreeCourses;
	}
	
	
	private Map<Long, DepartmentDegreeMining> generateDepartmentDegreeMining()
	{
		HashMap<Long, DepartmentDegreeMining> departmentDegrees = new HashMap<Long, DepartmentDegreeMining>();
		try{
			System.out.println("Generated " + departmentDegrees.size() + " DepartmentDegreeMinings.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return departmentDegrees;
	}
	*/
	
	/**
	 * Generates GroupUserMining-objects from the given data
	 * 
	 * @return	HashMap with GroupUserMining-objects
	 */
	private Map<Long, GroupUserMining> generateGroupUserMining()
	{
		HashMap<Long, GroupUserMining> groupUsers = new HashMap<Long, GroupUserMining>();
		
		try{
			
			for(PlatformGroupSpecification loadedItem : platformGroupSpecification)
			{
				GroupUserMining item = new GroupUserMining();
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), userMining, oldUserMining);
				item.setGroup(Long.valueOf(platform.getPrefix() + "" + loadedItem.getGroup()), groupMining, oldGroupMining);
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId().hashCode()));
				item.setPlatform(platform.getId());
				
				if(item.getUser() != null && item.getGroup() != null)
					groupUsers.put(item.getId(), item);
				
			}
			
			
			
			System.out.println("Generated " + groupUsers.size() + " GroupUserMinings.");
			
		}catch(Exception e)
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
		HashMap<Long, CourseGroupMining> courseGroups = new HashMap<Long, CourseGroupMining>();
		try{
			for(TeamExerciseGroup loadedItem : teamExerciseGroup)
			{
				CourseGroupMining item = new CourseGroupMining();
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), courseMining, oldCourseMining);
				item.setGroup(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()), groupMining, oldGroupMining);
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setPlatform(platform.getId());
				
				if(item.getCourse() != null && item.getGroup() != null)
					courseGroups.put(item.getId(), item);
				
			}
			System.out.println("Generated " + courseGroups.size() + " CourseGroupMinings.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseGroups;
	}
	
	/**
	 * Generates CourseForumMining-objects from the given data.
	 *
	 * @return HashMap with CourseForumMining-objects
	 */
	private Map<Long, CourseForumMining> generateCourseForumMining()
	{
		HashMap<Long, CourseForumMining> courseForum = new HashMap<Long, CourseForumMining>();
		try{
			
			for(EComposing loadedItem : eComposing)
			{
				if(forumMining.get(loadedItem.getComponent()) != null || oldForumMining.get(loadedItem.getComponent()) != null)
				{
					CourseForumMining item = new CourseForumMining();
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), courseMining, oldCourseMining);
					item.setForum(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), forumMining, oldForumMining);
					item.setPlatform(platform.getId());
					
					if(item.getCourse() != null && item.getForum() != null)
					{
						courseForum.put(item.getId(), item);
					}
				}
			}
			
			System.out.println("Generated " + courseForum.size() + " CourseForumMinings.");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return courseForum;
	}
	
	//Generators for logs
	
	/**
	 * Generates ForumLogMining-objects from the given data.
	 *
	 * @return HashMap with ForumLogMining-objects
	 */
	private Map<Long, ForumLogMining> generateForumLogMining()
	{
		HashMap<Long, ForumLogMining> forumLogs = new HashMap<Long, ForumLogMining>();
		
		try{
			HashMap<Long, EComposing> ecMap = new HashMap<Long, EComposing>();
			for(EComposing item : eComposing)
				ecMap.put(item.getComponent(), item);

			for(ForumEntry loadedItem : forumEntry)
			{
				ForumLogMining item = new ForumLogMining();
				item.setId(forumLogs.size() + forumLogMax + 1);
				
				item.setForum(Long.valueOf(platform.getPrefix() + "" + loadedItem.getForum()), forumMining, oldForumMining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getLastUpdater()), userMining, oldUserMining);
				item.setSubject(loadedItem.getTitle());
				item.setMessage(loadedItem.getContent());
				item.setAction("Post");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(platform.getId());
				item.setDuration(0L);
				
				if(ecMap.get(loadedItem.getForum()) != null)
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + ecMap.get(loadedItem.getForum()).getComposing()), courseMining, oldCourseMining);
				
				if(item.getUser() != null && item.getForum() != null )//a forum doesn't have to be in a course, so no check for FK "course"
					forumLogs.put(item.getId(), item);
			}
			
			for(ForumEntryState loadedItem : forumEntryState)
			{
				ForumLogMining item = new ForumLogMining();
				item.setId(forumLogs.size() + forumLogMax + 1);
				item.setForum(loadedItem.getForum(), forumMining, oldForumMining);
				item.setUser(loadedItem.getUser(), userMining, oldUserMining);
				item.setAction("View");
				if(forumLogs.get(loadedItem.getEntry()) != null)
						item.setSubject(forumLogs.get(loadedItem.getEntry()).getSubject());
				item.setMessage("");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));		
				
				if(ecMap.get(loadedItem.getForum()) != null)
					item.setCourse(ecMap.get(loadedItem.getForum()).getComposing(), courseMining, oldCourseMining);
				
				if(item.getUser() != null && item.getForum() != null )//a forum doesn't have to be in a course, so no check for FK "course"
					forumLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + forumLogs.size() + " ForumLogMinings.");
			
		}catch(Exception e)
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
		HashMap<Long, WikiLogMining> wikiLogs = new HashMap<Long, WikiLogMining>();
		try{
			for(WikiEntry loadedItem : wikiEntry)
			{
				WikiLogMining item = new WikiLogMining();
				item.setId(wikiLogs.size() + wikiLogMax + 1);
				
				item.setWiki(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), wikiMining, oldWikiMining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCreator()), userMining, oldUserMining);
				if(item.getWiki() != null && eComposingMap.get(item.getWiki().getId()) != null)
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + eComposingMap.get(item.getWiki().getId()).getComposing()), courseMining, oldCourseMining);
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(platform.getId());
				item.setDuration(0L);
				
				
				if(item.getUser() != null && item.getWiki() != null )//a wiki doesn't have to be in a course, so no check for FK "course"
					wikiLogs.put(item.getId(), item);
			
			}
			System.out.println("Generated " + wikiLogs.size() + " WikiLogMinings.");
		
		}catch(Exception e)
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
		HashMap<Long, CourseLogMining> courseLogs = new HashMap<Long, CourseLogMining>();
		try{
			for(PortfolioLog loadedItem : portfolioLog)
			{
				CourseLogMining item = new CourseLogMining();
				item.setId(courseLogs.size() + courseLogMax + 1);
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), courseMining, oldCourseMining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), userMining, oldUserMining);
				item.setAction(loadedItem.getTypeOfModification()+"");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(platform.getId());
				item.setDuration(0L);
				
				if(item.getCourse() != null && item.getUser() != null)
				{
					courseLogs.put(item.getId(), item);
					if(item.getId() == 20438)
						System.out.println();
				}
				if(item.getId() == 22746)
					System.out.println();
			}
			System.out.println("Generated " + courseLogs.size() + " CourseLogMinings.");
			
		}catch(Exception e)
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
		HashMap<Long, QuestionLogMining> questionLogs = new HashMap<Long, QuestionLogMining>();
		
		try{
			for(TAnswerPosition loadedItem : tAnswerPosition)
			{
				QuestionLogMining item = new QuestionLogMining();
				
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), userMining, oldUserMining);
				item.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getTest()), quizMining, oldQuizMining);
				item.setQuestion(Long.valueOf(platform.getPrefix() + "" + loadedItem.getQuestion()), questionMining, oldQuestionMining);
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getTest()), courseMining, oldCourseMining);
				item.setId(questionLogs.size() + questionLogMax + 1);
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getEvaluated()));
				item.setPlatform(platform.getId());
				item.setDuration(0L);
				
				if(item.getQuestion() != null && item.getQuiz() != null && item.getUser() != null && item.getCourse() != null)
					questionLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + questionLogs.size() + " QuestionLogMinings.");
		
		}catch(Exception e)
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
		HashMap<Long, QuizLogMining> quizLogs = new HashMap<Long, QuizLogMining>();
		try{
			for(TQtiEvalAssessment loadedItem : tQtiEvalAssessment)
			{
				QuizLogMining item = new QuizLogMining();
				
				item.setId(quizLogs.size() + quizLogMax + 1);
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), courseMining, oldCourseMining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCandidate()), userMining, oldUserMining);
				item.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getAssessment()), quizMining, oldQuizMining);
				item.setGrade(Double.valueOf(loadedItem.getEvaluatedScore()));
				item.setPlatform(platform.getId());
				item.setDuration(0L);
				if(loadedItem.getEvalCount() == 0L)
					item.setAction("Try");
				else
					item.setAction("Submit");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastInvocation()));
				
				if(item.getCourse() != null && item.getQuiz() != null && item.getUser() != null)
					quizLogs.put(item.getId(), item);
				
			}
			System.out.println("Generated " + quizLogs.size() + " QuizLogMinings.");
		
		}catch(Exception e)
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
		HashMap<Long, AssignmentLogMining> assignmentLogs = new HashMap<Long, AssignmentLogMining>();
		try{
			//This HashMap is helpful for finding the course_id that is associated with the AssignmentLog
			HashMap<Long, Long> eg = new HashMap<Long, Long>();
			for(ExerciseGroup loadedItem:  exerciseGroup)
			{
				if(loadedItem.getAssociatedCourse() != 0L)
					eg.put(loadedItem.getId(), loadedItem.getAssociatedCourse());
			}
			for(ExercisePersonalised loadedItem : exercisePersonalised)
			{
				AssignmentLogMining item = new AssignmentLogMining();
				
				item.setAssignment(Long.valueOf(platform.getPrefix() + "" + loadedItem.getExercise()), assignmentMining, oldAssignmentMining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUser()), userMining, oldUserMining);
				item.setGrade(Double.valueOf(loadedItem.getPoints()));
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getUploadDate()));
				item.setPlatform(platform.getId());
				//Get the course_id via the exercisegroup_id
				if(eg.get(loadedItem.getCommunity()) != null)
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + eg.get(loadedItem.getCommunity())), courseMining, oldCourseMining);
				
				item.setId(assignmentLogs.size() + assignmentLogMax + 1);
				item.setDuration(0L);
				
				if(item.getCourse() != null && item.getAssignment() != null && item.getUser() != null)
				{
					assignmentLogs.put(item.getId(), item);
					if(courseAssignmentMining.get(item.getAssignment().getId()) == null)
					{
						CourseAssignmentMining cam = new CourseAssignmentMining();
						cam.setAssignment(item.getAssignment());
						cam.setCourse(item.getCourse());
						cam.setPlatform(platform.getId());
						cam.setId(Long.valueOf(platform.getPrefix() + "" + (courseAssignmentMining.size())));
						
						courseAssignmentMining.put(cam.getAssignment().getId(), cam);
					}
				}
			}
			System.out.println("Generated " + assignmentLogs.size() + " AssignmentLogMinings.");
		
		}catch(Exception e)
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
		HashMap<Long, ScormLogMining> scormLogs = new HashMap<Long, ScormLogMining>();
		HashMap<Long, Long> eComp = new HashMap<Long, Long>();
		for(EComposing item : eComposing)
				eComp.put(item.getComponent(), item.getComposing());
		
		try {
			for(ScormSessionTimes loadedItem : scormSessionTimes)
			{
				ScormLogMining item = new ScormLogMining();
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), userMining, oldUserMining);
				try{
					if(loadedItem.getScore() != null && !loadedItem.getScore().equals("null"))
						item.setGrade(Double.valueOf(loadedItem.getScore().toString()));
					
					item.setScorm(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), scormMining, oldScormMining);
					item.setAction(loadedItem.getStatus());
					item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setId(scormLogs.size() + scormLogMax + 1);
					item.setPlatform(platform.getId());

					item.setDuration(0L);
					
					if(eComp.get(loadedItem.getComponent()) != null)
						item.setCourse(Long.valueOf(platform.getPrefix() + "" + eComp.get(loadedItem.getComponent())), courseMining, oldCourseMining);
					
					if(item.getUser() != null && item.getScorm() != null) //a scorm doesn't have to be in a course, so no check for FK "course"
						scormLogs.put(item.getId(), item);
					
				}catch(NullPointerException e)
				{
					System.out.println("Couldn't parse grade: "+ loadedItem.getScore());
				}
					
			}
			System.out.println("Generated " + scormLogs.size() + " ScormLogMinings.");
		
		}catch(Exception e)
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
		HashMap<Long, ResourceLogMining> resourceLogs = new HashMap<Long, ResourceLogMining>();
		try{
			for(BiTrackContentImpressions loadedItem : biTrackContentImpressions)
			{
				ResourceLogMining item = new ResourceLogMining();
				item.setResource(Long.valueOf(platform.getPrefix() + "" + loadedItem.getContent()), resourceMining, oldResourceMining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUser()), userMining, oldUserMining);
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getContainer()), courseMining, oldCourseMining);
				item.setAction("View");
				item.setDuration(0L);
				item.setId(resourceLogs.size() + resourceLogMax + 1);
				//Time stamp has different format (2009-07-31)
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getDayOfAccess() + " 00:00:00.000"));
				item.setPlatform(platform.getId());
				
				if(item.getResource() != null && item.getCourse() != null && item.getUser() != null)
					resourceLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + resourceLogs.size() + " ResourceLogMinings.");
		
		}catch(Exception e)
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
		HashMap<Long, ChatLogMining> chatLogs = new HashMap<Long, ChatLogMining>();
		try{
			for(ChatProtocol loadedItem : chatProtocol)
			{
				ChatLogMining item = new ChatLogMining();
				
				item.setId(chatLogs.size() + chatLogMax + 1);
				item.setChat(Long.valueOf(platform.getPrefix() + "" + loadedItem.getChatroom()), chatMining, oldChatMining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), userMining, oldUserMining);
				item.setMessage(loadedItem.getChatSource());
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(platform.getId());
				item.setDuration(0L);
				item.setCourse(item.getChat().getCourse());
				
				if(item.getChat() != null && item.getUser() != null)
					chatLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + chatLogs.size() + " ChatLogMinings.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return chatLogs;
	}
}
