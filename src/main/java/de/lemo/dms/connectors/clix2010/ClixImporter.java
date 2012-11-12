package de.lemo.dms.connectors.clix2010;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.connectors.clix2010.clixDBClass.BiTrackContentImpressions;
import de.lemo.dms.connectors.clix2010.clixDBClass.BiTrackContentImpressionsPK;
import de.lemo.dms.connectors.clix2010.clixDBClass.ChatProtocol;
import de.lemo.dms.connectors.clix2010.clixDBClass.Chatroom;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponent;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponentType;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponentTypePK;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComposing;
import de.lemo.dms.connectors.clix2010.clixDBClass.ExerciseGroup;
import de.lemo.dms.connectors.clix2010.clixDBClass.ExercisePersonalised;
import de.lemo.dms.connectors.clix2010.clixDBClass.ExercisePersonalisedPK;
import de.lemo.dms.connectors.clix2010.clixDBClass.ForumEntry;
import de.lemo.dms.connectors.clix2010.clixDBClass.ForumEntryState;
import de.lemo.dms.connectors.clix2010.clixDBClass.ForumEntryStatePK;
import de.lemo.dms.connectors.clix2010.clixDBClass.Person;
import de.lemo.dms.connectors.clix2010.clixDBClass.PersonComponentAssignment;
import de.lemo.dms.connectors.clix2010.clixDBClass.PersonComponentAssignmentPK;
import de.lemo.dms.connectors.clix2010.clixDBClass.PlatformGroup;
import de.lemo.dms.connectors.clix2010.clixDBClass.PlatformGroupSpecification;
import de.lemo.dms.connectors.clix2010.clixDBClass.PlatformGroupSpecificationPK;
import de.lemo.dms.connectors.clix2010.clixDBClass.Portfolio;
import de.lemo.dms.connectors.clix2010.clixDBClass.PortfolioLog;
import de.lemo.dms.connectors.clix2010.clixDBClass.ScormSessionTimes;
import de.lemo.dms.connectors.clix2010.clixDBClass.ScormSessionTimesPK;
import de.lemo.dms.connectors.clix2010.clixDBClass.T2Task;
import de.lemo.dms.connectors.clix2010.clixDBClass.TAnswerPosition;
import de.lemo.dms.connectors.clix2010.clixDBClass.TAnswerPositionPK;
import de.lemo.dms.connectors.clix2010.clixDBClass.TGroupFullSpecification;
import de.lemo.dms.connectors.clix2010.clixDBClass.TQtiContent;
import de.lemo.dms.connectors.clix2010.clixDBClass.TQtiEvalAssessment;
import de.lemo.dms.connectors.clix2010.clixDBClass.TTestSpecification;
import de.lemo.dms.connectors.clix2010.clixDBClass.TTestSpecificationPK;
import de.lemo.dms.connectors.clix2010.clixDBClass.TeamExerciseComposingExt;
import de.lemo.dms.connectors.clix2010.clixDBClass.TeamExerciseGroup;
import de.lemo.dms.connectors.clix2010.clixDBClass.TeamExerciseGroupMember;
import de.lemo.dms.connectors.clix2010.clixDBClass.WikiEntry;
import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;
import de.lemo.dms.connectors.clix2010.clixHelper.TimeConverter;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
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
import de.lemo.dms.db.miningDBclass.DegreeCourseMining;
import de.lemo.dms.db.miningDBclass.DegreeMining;
import de.lemo.dms.db.miningDBclass.DepartmentDegreeMining;
import de.lemo.dms.db.miningDBclass.DepartmentMining;
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

public class ClixImporter {
	
	private static Long resourceLogMax = 0L;
	private static Long chatLogMax = 0L;
	private static Long assignmentLogMax = 0L;
	private static Long courseLogMax = 0L;
	private static Long forumLogMax = 0L;
	private static Long questionLogMax = 0L;
	private static Long quizLogMax = 0L;
	private static Long scormLogMax = 0L;
	private static Long wikiLogMax = 0L;
	
	//List, holding the entries of the BI_TRACKCONTENT_IMPRESSIONS table of the source database
	private static List<BiTrackContentImpressions> biTrackContentImpressions;
	//List, holding the entries of the CHATPROTOCOL table of the source database
	private static List<ChatProtocol> chatProtocol;
	//List, holding the entries of the EXERCISE_GROUP table of the source database
	private static List<ExerciseGroup> exerciseGroup;
	//List, holding the entries of the E_COMPONENT table of the source database
	private static List<EComponent> eComponent;
	//List, holding the entries of the E_COMPONENTTYPE table of the source database
	private static List<EComponentType> eComponentType;
	//List, holding the entries of the E_COMPOSING table of the source database
	private static List<EComposing> eComposing;
	//List, holding the entries of the EXERCISE_PERSONALISED table of the source database
	private static List<ExercisePersonalised> exercisePersonalised;
	//List, holding the entries of the FORUM_ENTRY table of the source database
	private static List<ForumEntry> forumEntry;
	//List, holding the entries of the FORUM_ENTRY_STATE table of the source database
	private static List<ForumEntryState> forumEntryState;
	//List, holding the entries of the PERSON table of the source database
	private static List<Person> person;
	//List, holding the entries of the PERSON_COMPONENT_ASSIGNMENT table of the source database
	private static List<PersonComponentAssignment> personComponentAssignment;
	//List, holding the entries of the PLATFORMGROUP table of the source database
	private static List<PlatformGroup> platformGroup;
	//List, holding the entries of the PLATFORMGROUPSPECIFICATION table of the source database
	private static List<PlatformGroupSpecification> platformGroupSpecification;
	//List, holding the entries of the PORTFOLIO table of the source database
	private static List<Portfolio> portfolio;
	//List, holding the entries of the PORTFOLIO_LOG table of the source database
	private static List<PortfolioLog> portfolioLog;
	//List, holding the entries of the SCORM_SESSION_TIMES table of the source database
	private static List<ScormSessionTimes> scormSessionTimes;
	//List, holding the entries of the T2_TASK table of the source database
	private static List<T2Task> t2Task;
	//List, holding the entries of the T_ANSWERPOSITION table of the source database
	private static List<TAnswerPosition> tAnswerPosition;
	//List, holding the entries of the TEAM_EXERCISE_COMPOSING_EXT table of the source database
	private static List<TeamExerciseComposingExt> teamExerciseComposingExt;
	//List, holding the entries of the TEAM_EXERCISE_GOUP table of the source database
	private static List<TeamExerciseGroup> teamExerciseGroup;
	//List, holding the entries of the TEAM_EXERCISE_GROUP_MEMBER table of the source database
	private static List<TeamExerciseGroupMember> teamExerciseGroupMember;
	//List, holding the entries of the T_GROUPFULLSPECIFICATION table of the source database
	private static List<TGroupFullSpecification> tGroupFullSpecification;
	//List, holding the entries of the T_QTI_CONTENT table of the source database
	private static List<TQtiContent> tQtiContent;
	//List, holding the entries of the T_QTI_EVAL_ASSESSMENT table of the source database
	private static List<TQtiEvalAssessment> tQtiEvalAssessment;
	//List, holding the entries of the T_TESTSPECIFICATION table of the source database
	private static List<TTestSpecification> tTestSpecification;
	//List, holding the entries of the WIKI_ENTRY table of the source database
	private static List<WikiEntry> wikiEntry;
	//List, holding the entries of the CHATROOM table of the source database
	private static List<Chatroom> chatroom;
	private static HashMap<Long, EComposing> eComposingMap = new HashMap<Long, EComposing>();
	
	//Maps of new mining-objects, found on the Clix-platform
	

	
	//Map, holding the CourseMining objects, found in the current data extraction process
	private	static HashMap<Long, CourseMining> course_mining;
	
	private static HashMap<Long, PlatformMining> platform_mining;
	//Map, holding the QuizMining objects, found in the current data extraction process
	private	static HashMap<Long, QuizMining> quiz_mining;
	//Map, holding the AssignmentMining objects, found in the current data extraction process
	private static HashMap<Long, AssignmentMining> assignment_mining;
	//Map, holding the ScormMining objects, found in the current data extraction process
	private	static HashMap<Long, ScormMining> scorm_mining;
	//Map, holding the ForumMining objects, found in the current data extraction process
	private	static HashMap<Long, ForumMining> forum_mining;
	//Map, holding the ResourceMining objects, found in the current data extraction process
	private	static HashMap<Long, ResourceMining> resource_mining;
	//Map, holding the UserMining objects, found in the current data extraction process
	private static HashMap<Long, UserMining> user_mining;
	//Map, holding the WikiMining objects, found in the current data extraction process
	private static HashMap<Long, WikiMining> wiki_mining;
	//Map, holding the GroupMining objects, found in the current data extraction process
	private static HashMap<Long, GroupMining> group_mining;
	//Map, holding the QuestionMining objects, found in the current data extraction process
	private static HashMap<Long, QuestionMining> question_mining;
	//Map, holding the RoleMining objects, found in the current data extraction process
	private static HashMap<Long, RoleMining> role_mining;
	//Map, holding the ChatMining objects, found in the current data extraction process
	private static HashMap<Long, ChatMining> chat_mining;
	//Map, holding the QuizQuestionMining objects, found in the current data extraction process
	private static HashMap<Long, QuizQuestionMining> quiz_question_mining;
	//Map, holding the CourseQuizMining objects, found in the current data extraction process
	private static HashMap<Long, CourseQuizMining> course_quiz_mining;
	//Map, holding the CourseAssignmentMining objects, found in the current data extraction process
	private static HashMap<Long, CourseAssignmentMining> course_assignment_mining;
	//Map, holding the CourseScormMining objects, found in the current data extraction process
	private static HashMap<Long, CourseScormMining> course_scorm_mining;
	//Map, holding the CourseUserMining objects, found in the current data extraction process
	private static HashMap<Long, CourseUserMining> course_user_mining;
	//Map, holding the CourseForumMining objects, found in the current data extraction process
	private static HashMap<Long, CourseForumMining> course_forum_mining;
	//Map, holding the CourseGroupMining objects, found in the current data extraction process
	private static HashMap<Long, CourseGroupMining> course_group_mining;
	//Map, holding the CourseResourceMining objects, found in the current data extraction process
	private static HashMap<Long, CourseResourceMining> course_resource_mining;
	//Map, holding the CourseWikiMining objects, found in the current data extraction process
	private static HashMap<Long, CourseWikiMining> course_wiki_mining;
	//Map, holding the GroupUserMining objects, found in the current data extraction process
	private static HashMap<Long, GroupUserMining> group_user_mining;
	//Map, holding the QuizUserMining objects, found in the current data extraction process
	private static HashMap<Long, QuizUserMining> quiz_user_mining;
		
	//Maps of mining-objects that have been found in a previous extraction process
	
	//Map, holding the CourseMining objects, found in a previous data extraction process
	private static HashMap<Long, CourseMining> old_course_mining;
	
	private static HashMap<Long, PlatformMining> old_platform_mining;
	//Map, holding the QuizMining objects, found in a previous data extraction process
	private static HashMap<Long, QuizMining> old_quiz_mining;
	//Map, holding the AssignmentMining objects, found in a previous data extraction process
	private static HashMap<Long, AssignmentMining> old_assignment_mining;
	//Map, holding the ScormMining objects, found in a previous data extraction process
	private static HashMap<Long, ScormMining> old_scorm_mining;
	//Map, holding the ForumMining objects, found in a previous data extraction process
	private static HashMap<Long, ForumMining> old_forum_mining;
	//Map, holding the ResourceMining objects, found in a previous data extraction process
	private static HashMap<Long, ResourceMining> old_resource_mining;
	//Map, holding the UserMining objects, found in a previous data extraction process
	private static HashMap<Long, UserMining> old_user_mining;
	//Map, holding the WikiMining objects, found in a previous data extraction process
	private static HashMap<Long, WikiMining> old_wiki_mining;
	//Map, holding the GroupMining objects, found in a previous data extraction process
	private static HashMap<Long, GroupMining> old_group_mining;
	//Map, holding the QuestionMining objects, found in a previous data extraction process
	private static HashMap<Long, QuestionMining> old_question_mining;
	//Map, holding the RoleMining objects, found in a previous data extraction process
	private static HashMap<Long, RoleMining> old_role_mining;
	//Map, holding the DepartmentMining objects, found in a previous data extraction process
	private static HashMap<Long, DepartmentMining> old_department_mining;
	//Map, holding the DegreeMining objects, found in a previous data extraction process
	private static HashMap<Long, DegreeMining> old_degree_mining;
	//Map, holding the ChatMining objects, found in a previous data extraction process
	private static HashMap<Long, ChatMining> old_chat_mining;
	
	private static Long largestId;
	
	private static PlatformMining platform;
	
	
	/**
	 * Performs a extraction process for an entire Clix2010 database.
	 */
	public static void getClixData(String platformName)
	{
		Clock c = new Clock();
		Long starttime = System.currentTimeMillis()/1000;
		
		largestId = 0L;				
		platform_mining = new HashMap<Long, PlatformMining>();
		
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
	    config.setElapsed_time((endtime) - (starttime));	
	    config.setLargestId(largestId);
	    config.setPlatform(platform.getId());
	    
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();
        dbHandler.saveToDB(session, config);
        dbHandler.closeSession(session);
	}
	
	
	/**
	 * Performs a data-extraction for a Clix2010 database for all objects that are newer than the given time stamp. 
	 */
	public static void updateClixData(String platformName, Long startTime)
	{
		Long currentSysTime = System.currentTimeMillis()/1000;
		Long largestId = -1L;		

		Long upperLimit = 0L;
		
		platform_mining = new HashMap<Long, PlatformMining>();
		
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
	    config.setElapsed_time((endtime) - (currentSysTime));	
	    config.setLargestId(largestId);
	    config.setPlatform(platform.getId());
	    
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();
        dbHandler.saveToDB(session, config);
        dbHandler.closeSession(session);
	}
	

	/**
	 * Generates and saves all objects
	 */
	private static void saveData()
	{
		try{
			List<Collection<?>> updates = new ArrayList<Collection<?>>();
			
			if(user_mining == null)
			{
				updates.add(platform_mining.values());
				
				course_mining = generateCourseMining();
				updates.add(course_mining.values());
				
				quiz_mining = generateQuizMining();
				updates.add(quiz_mining.values());
				
				assignment_mining = generateAssignmentMining();
				updates.add(assignment_mining.values());
				
				scorm_mining = generateScormMining();
				updates.add(scorm_mining.values());
		
				forum_mining = generateForumMining();
				updates.add(forum_mining.values());
				
				resource_mining = generateResourceMining();
				updates.add(resource_mining.values());
				
				wiki_mining = generateWikiMining();
				updates.add(wiki_mining.values());
								
				user_mining = generateUserMining();
				updates.add(user_mining.values());
								
				group_mining = generateGroupMining();
				updates.add(group_mining.values());
				
				question_mining = generateQuestionMining();
				updates.add(question_mining.values());
				
				role_mining = generateRoleMining();
				updates.add(role_mining.values());
								
				chat_mining = generateChatMining();
				updates.add(chat_mining.values());
								
				System.out.println("\nAssociationObjects: \n");
				
				quiz_question_mining = generateQuizQuestionMining();
				updates.add(quiz_question_mining.values());
				
				course_quiz_mining = generateCourseQuizMining();
				updates.add(course_quiz_mining.values());
				
				course_assignment_mining = generateCourseAssignmentMining();
				updates.add(course_assignment_mining.values());
	
				course_scorm_mining = generateCourseScormMining();
				updates.add(course_scorm_mining.values());
	
				course_forum_mining = generateCourseForumMining();
				updates.add(course_forum_mining.values());
				
				course_group_mining = generateCourseGroupMining();
				updates.add(course_group_mining.values());
				
				course_wiki_mining = generateCourseWikiMining();
				updates.add(course_wiki_mining.values());
					
				course_resource_mining = generateCourseResourceMining();
				updates.add(course_resource_mining.values());
				
				group_user_mining = generateGroupUserMining();
				updates.add(group_user_mining.values());
				
				quiz_user_mining = generateQuizUserMining();
				updates.add(quiz_user_mining.values());
				
			}
			
			System.out.println("\nLogObjects: \n");
			
			updates.add(generateAssignmentLogMining().values());
		
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
	
	public static void clearSourceData()
	{
		biTrackContentImpressions.clear();
		chatProtocol.clear();
		exerciseGroup.clear();
		eComponent.clear();
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
	 * Looks, if there are already values in the Mining database and loads them, if necessary 
	 */
	@SuppressWarnings("unchecked")
	private static void initialize(String platformName)
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
		        old_platform_mining = new HashMap<Long, PlatformMining>();
		        for(int i = 0; i < l.size() ; i++)
		        	old_platform_mining.put(Long.valueOf(((PlatformMining)l.get(i)).getId()), (PlatformMining)l.get(i));  
		        System.out.println("Read " + old_platform_mining.size() +" old PlatformMinings."); 
		        
		        for(PlatformMining p : old_platform_mining.values())
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
					platform_mining.put(platform.getId(), platform);
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
	        old_course_mining = new HashMap<Long, CourseMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_course_mining.put(Long.valueOf(((CourseMining)l.get(i)).getId()), (CourseMining)l.get(i));  
	        System.out.println("Read " + old_course_mining.size() +" old CourseMinings."); 

	        
	        Query old_quiz = session.createQuery("from QuizMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<QuizMining>) old_quiz.list();
	        old_quiz_mining = new HashMap<Long, QuizMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_quiz_mining.put(Long.valueOf(((QuizMining)l.get(i)).getId()), (QuizMining)l.get(i));  
	        System.out.println("Read " + old_quiz_mining.size() +" old QuizMinings."); 
	
	        Query old_assignment = session.createQuery("from AssignmentMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<AssignmentMining>) old_assignment.list();
	        old_assignment_mining = new HashMap<Long, AssignmentMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_assignment_mining.put(Long.valueOf(((AssignmentMining)l.get(i)).getId()), (AssignmentMining)l.get(i));  
	        System.out.println("Read " + old_assignment_mining.size() +" old AssignmentMinings."); 
			
	        Query old_scorm = session.createQuery("from ScormMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<ScormMining>) old_scorm.list();
	        old_scorm_mining = new HashMap<Long, ScormMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_scorm_mining.put(Long.valueOf(((ScormMining)l.get(i)).getId()), (ScormMining)l.get(i));  
	        System.out.println("Read " + old_scorm_mining.size() +" old ScormMinings."); 
	        
	        Query old_forum = session.createQuery("from ForumMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<ForumMining>) old_forum.list();
	        old_forum_mining = new HashMap<Long, ForumMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_forum_mining.put(Long.valueOf(((ForumMining)l.get(i)).getId()), (ForumMining)l.get(i));  
	        System.out.println("Read " + old_forum_mining.size() +" old ForumMinings."); 
			
	        Query old_resource = session.createQuery("from ResourceMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<ResourceMining>) old_resource.list();
	        old_resource_mining = new HashMap<Long, ResourceMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_resource_mining.put(Long.valueOf(((ResourceMining)l.get(i)).getId()), (ResourceMining)l.get(i));  
	        System.out.println("Read " + old_resource_mining.size() +" old ForumMinings."); 
			
	        Query old_user = session.createQuery("from UserMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<UserMining>) old_user.list();
	        old_user_mining = new HashMap<Long, UserMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_user_mining.put(Long.valueOf(((UserMining)l.get(i)).getId()), (UserMining)l.get(i));  
	        System.out.println("Read " + old_user_mining.size() +" old UserMinings."); 
			
	        Query old_wiki = session.createQuery("from WikiMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<WikiMining>) old_wiki.list();
	        old_wiki_mining = new HashMap<Long, WikiMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_wiki_mining.put(Long.valueOf(((WikiMining)l.get(i)).getId()), (WikiMining)l.get(i));  
	        System.out.println("Read " + old_wiki_mining.size() +" old WikiMinings."); 
	
	        Query old_group = session.createQuery("from GroupMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<GroupMining>) old_group.list();
	        old_group_mining = new HashMap<Long, GroupMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_group_mining.put(Long.valueOf(((GroupMining)l.get(i)).getId()), (GroupMining)l.get(i));  
	        System.out.println("Read " + old_group_mining.size() +" old GroupMinings."); 
			
	        Query old_question = session.createQuery("from QuestionMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<QuestionMining>) old_question.list();
	        old_question_mining = new HashMap<Long, QuestionMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_question_mining.put(Long.valueOf(((QuestionMining)l.get(i)).getId()), (QuestionMining)l.get(i));  
	        System.out.println("Read " + old_question_mining.size() +" old QuestionMinings."); 
	        
	        Query old_role = session.createQuery("from RoleMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<RoleMining>) old_role.list();
	        old_role_mining = new HashMap<Long, RoleMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_role_mining.put(Long.valueOf(((RoleMining)l.get(i)).getId()), (RoleMining)l.get(i));  
	        System.out.println("Read " + old_role_mining.size() +" old RoleMinings."); 
	        
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
			
	        Query old_chat = session.createQuery("from ChatMining x where x.platform=" + platform.getId() +" order by x.id asc");
	        l = (ArrayList<ChatMining>) old_chat.list();
	        old_chat_mining = new HashMap<Long, ChatMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_chat_mining.put(Long.valueOf(((ChatMining)l.get(i)).getId()), (ChatMining)l.get(i));  
	        System.out.println("Read " + old_chat_mining.size() +" old ChatMinings."); 
	        

	        
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Loads all necessary tables from the Clix2010 database
	 */
	@SuppressWarnings("unchecked")
	private static void loadData()
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
	        eComponent = eComp.list();	     

	        System.out.println("EComponent tables: " + eComponent.size());    
	        
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
	 * Loads all tables needed for the data-extraction from the Clix database
	 * 
	 * 
	 * @param start
	 * @param end
	 */
	@SuppressWarnings("unchecked")
	private static void loadData(Long start, Long end)
	{
		try{
			//accessing DB by creating a session and a transaction using HibernateUtil
	        Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
	        session.clear();
	        
	        System.out.println("Starting data extraction.");  

	        //Read the tables that don't refer to log-entries once
	        if (user_mining == null)
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
		        eComponent = eComp.list();	     
		        System.out.println("EComponent tables: " + eComponent.size());    
		        
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
	
	//Generators for "static" objects
	
	/**
	 * Generates ChatMining-objects from the given data
	 * 
	 * @return	HashMap with ChatMining-objects
	 */
	public static HashMap<Long, ChatMining> generateChatMining() {
		HashMap<Long, ChatMining> chats = new HashMap<Long, ChatMining>();
		try{
			for(Chatroom loadedItem : chatroom)
			{
				ChatMining item = new ChatMining();
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setTitle(loadedItem.getTitle());
				item.setChattime(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
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
	 * Generates ResourceMining-objects from the given data
	 * 
	 * @return	HashMap with ResourceMining-objects
	 */
	public static HashMap<Long, ResourceMining> generateResourceMining()
	{
		HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
		HashMap<Long, ResourceMining> resources = new HashMap<Long, ResourceMining>();
		try{
			for(EComponentType loadedItem : eComponentType)
			{
				if(loadedItem.getCharacteristicId() == 1L || loadedItem.getCharacteristicId() == 11L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);
			}
			
			for(EComponent loadedItem : eComponent)
			{
				ResourceMining item = new ResourceMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setPlatform(platform.getId());
					
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
	 * Generates CourseMining-objects from the given data
	 * 
	 * @return	HashMap with CourseMining-objects
	 */
	public static HashMap<Long, CourseMining> generateCourseMining()
	{
		HashMap<Long, CourseMining> courses = new HashMap<Long, CourseMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(EComponentType loadedItem : eComponentType)
			{
				if(loadedItem.getCharacteristicId() == 4L || loadedItem.getCharacteristicId() == 5L || loadedItem.getCharacteristicId() == 3L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);
			}
			for(EComponent loadedItem : eComponent)
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
	 * Generates UserMining-objects from the given data
	 * 
	 * @return	HashMap with UserMining-objects
	 */
	public static HashMap<Long, UserMining> generateUserMining()
	{
		HashMap<Long, UserMining> users = new HashMap<Long, UserMining>();
		try{
			for(Person loadedItem : person)
			{
				UserMining item = new UserMining();
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setPlatform(platform.getId());
				item.setLastlogin(TimeConverter.getTimestamp(loadedItem.getLastLoginTime()));
				item.setFirstaccess(TimeConverter.getTimestamp(loadedItem.getFirstLoginTime()));
				
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
	 * Generates AssignmentMining-objects from the given data
	 * 
	 * @return	HashMap with AssignmentMining-objects
	 */
	public static HashMap<Long, AssignmentMining> generateAssignmentMining()
	{
		HashMap<Long, AssignmentMining> assignments = new HashMap<Long, AssignmentMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(EComponentType loadedItem:  eComponentType)
				if(loadedItem.getCharacteristicId() == 8L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);

			for(EComponent loadedItem : eComponent)
			{
				AssignmentMining item = new AssignmentMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					if(loadedItem.getStartDate() != null)
						item.setTimeopen(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
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
	 * Generates QuizMining-objects from the given data
	 * 
	 * @return	HashMap with QuizMining-objects
	 */
	public static HashMap<Long, QuizMining> generateQuizMining()
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
			for(EComponent loadedItem : eComponent)
			{
				QuizMining item = new QuizMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setTimeopen(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(platform.getId());
					if(eCompo.get(loadedItem.getId()) != null)
						item.setTimeclose(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getEndDate()));
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
	 * Generates ForumMining-objects from the given data
	 * 
	 * @return	HashMap with ForumMining-objects
	 */
	public static HashMap<Long, ForumMining> generateForumMining()
	{
		
		HashMap<Long, ForumMining> forums = new HashMap<Long, ForumMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(EComponentType loadedItem : eComponentType)
			{
				if(loadedItem.getCharacteristicId() == 2L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);
			}
			for(EComponent loadedItem : eComponent)
			{
				ForumMining item = new ForumMining();
				if(eCTypes.get(loadedItem.getType()) != null && eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("forum"))
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setSummary(loadedItem.getDescription());
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
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
	 * Generates RoleMining-objects from the given data
	 * 
	 * @return	HashMap with RoleMining-objects
	 */
	public static HashMap<Long, RoleMining> generateRoleMining()
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
	 * Generates GroupMining-objects from the given data
	 * 
	 * @return	HashMap with GroupMining-objects
	 */
	public static HashMap<Long, GroupMining> generateGroupMining()
	{
		HashMap<Long, GroupMining> groups = new HashMap<Long, GroupMining>();
		
		try{
			for(PlatformGroup loadedItem : platformGroup)
			{
				GroupMining item = new GroupMining();
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreated()));
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
	 * Generates WikiMining-objects from the given data
	 * 
	 * @return	HashMap with WikiMining-objects
	 */
	public static HashMap<Long, WikiMining> generateWikiMining()
	{
		HashMap<Long, WikiMining> wikis = new HashMap<Long, WikiMining>();
		
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(EComponentType loadedItem : eComponentType)
			{
				if(loadedItem.getCharacteristicId() == 2L)
					eCTypes.put(loadedItem.getComponent(), loadedItem);
			}
			for(EComponent loadedItem : eComponent)
			{
				WikiMining item = new WikiMining();
				if(eCTypes.get(loadedItem.getType()) != null && eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("wiki"))
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setSummary(loadedItem.getDescription());
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
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
	 * Generates DepartmentMining-objects from the given data
	 * 
	 * @return	HashMap with DepartmentMining-objects
	 */
	public static HashMap<Long, DepartmentMining> generateDepartmentMining()
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

	/**
	 * Generates DegreeMining-objects from the given data
	 * 
	 * @return	HashMap with DegreeMining-objects
	 */
	public static HashMap<Long, DegreeMining> generateDegreeMining()
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

	/**
	 * Generates QuestionMining-objects from the given data
	 * 
	 * @return	HashMap with QuestionMining-objects
	 */
	public static HashMap<Long, QuestionMining> generateQuestionMining()
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
	 * Generates ScormMining-objects from the given data
	 * 
	 * @return	HashMap with ScormMining-objects
	 */
	public static HashMap<Long, ScormMining> generateScormMining()
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
			for(EComponent loadedItem : eComponent)
			{
				ScormMining item = new ScormMining();
				if(eCTypes.get(loadedItem.getType()) != null && eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("scorm"))
				{
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setTitle(loadedItem.getName());
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setPlatform(platform.getId());
					
					if(eCompo.get(loadedItem.getId()) != null)
					{
						item.setTimeopen(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getStartDate()));
						item.setTimeclose(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getEndDate()));
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
	 * Generates QuizQuestionMining-objects from the given data
	 * 
	 * @return	HashMap with QuizQuestionMining-objects
	 */
	public static HashMap<Long, QuizQuestionMining> generateQuizQuestionMining()
	{
		HashMap<Long, QuizQuestionMining> quizQuestions = new HashMap<Long, QuizQuestionMining>();
		
		try{
			for(TTestSpecification loadedItem : tTestSpecification)
			{
				QuizQuestionMining item = new QuizQuestionMining();
				item.setQuestion(Long.valueOf(platform.getPrefix() + "" + loadedItem.getTest()), question_mining, old_question_mining);
				item.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getTask()), quiz_mining, old_quiz_mining);
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
	 * Generates CourseScormMining-objects from the given data
	 * 
	 * @return	HashMap with CourseScormMining-objects
	 */
	public static HashMap<Long, CourseScormMining> generateCourseScormMining()
	{
		HashMap<Long, CourseScormMining> courseScorms = new HashMap<Long, CourseScormMining>();
		
		try{
			for(EComposing loadedItem : eComposing)
			{
				if(scorm_mining.get(loadedItem.getComponent()) != null || old_scorm_mining.get(loadedItem.getComponent()) != null)
				{
					CourseScormMining item = new CourseScormMining();
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), course_mining, old_course_mining);
					item.setScorm(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), scorm_mining, old_scorm_mining);
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
	 * Generates CourseAssignmentMining-objects from the given data
	 * 
	 * @return	HashMap with CourseAssignmentMining-objects
	 */
	public static HashMap<Long, CourseAssignmentMining> generateCourseAssignmentMining()
	{
		HashMap<Long, CourseAssignmentMining> courseAssignments = new HashMap<Long, CourseAssignmentMining>();
		try{
			for(EComposing loadedItem : eComposing)
			{
				if(assignment_mining.get(loadedItem.getComponent()) != null || old_assignment_mining.get(loadedItem.getComponent()) != null)
				{
					CourseAssignmentMining item = new CourseAssignmentMining();
					
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), course_mining, old_course_mining);
					item.setAssignment(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), assignment_mining, old_assignment_mining);
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
	 * Generates CourseResourceMining-objects from the given data
	 * 
	 * @return	HashMap with CourseResourceMining-objects
	 */
	public static HashMap<Long, CourseResourceMining> generateCourseResourceMining()
	{
		HashMap<Long, CourseResourceMining> courseResources = new HashMap<Long, CourseResourceMining>();
		
		try{
			for(EComposing loadedItem : eComposing)
			{
				if(resource_mining.get(loadedItem.getComponent()) != null || old_resource_mining.get(loadedItem.getComponent()) != null)
				{
					CourseResourceMining item = new CourseResourceMining();
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), course_mining, old_course_mining);
					item.setResource(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), resource_mining, old_resource_mining);
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
	 * Generates CourseQuizMining-objects from the given data
	 * 
	 * @return	HashMap with CourseQuizMining-objects
	 */
	public static HashMap<Long, CourseQuizMining> generateCourseQuizMining()
	{
		HashMap<Long, CourseQuizMining> courseQuizzes = new HashMap<Long, CourseQuizMining>();
		
		try{
			for(EComposing loadedItem : eComposing)
			{
				if(quiz_mining.get(loadedItem.getComponent()) != null || old_quiz_mining.get(loadedItem.getComponent()) != null)
				{
					CourseQuizMining item = new CourseQuizMining();
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), course_mining, old_course_mining);
					item.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), quiz_mining, old_quiz_mining);
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
	 * Generates QuizUserMining-objects from the given data
	 * 
	 * @return	HashMap with QuizUserMining-objects
	 */
	public static  HashMap<Long, QuizUserMining> generateQuizUserMining()
	{
		HashMap<Long, QuizUserMining> quizUsers = new HashMap<Long, QuizUserMining>();
		try{
			for(TQtiEvalAssessment loadedItem :  tQtiEvalAssessment)
			{
				QuizUserMining item = new QuizUserMining();
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), course_mining, old_course_mining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCandidate()), user_mining, old_user_mining);
				item.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getAssessment()), quiz_mining, old_quiz_mining);
				item.setFinalgrade(loadedItem.getEvaluatedScore());
				item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastInvocation()));
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
	 * Generates CourseUserMining-objects from the given data
	 * 
	 * @return	HashMap with CourseUserMining-objects
	 */
	public static HashMap<Long, CourseUserMining> generateCourseUserMining()
	{
		HashMap<Long, CourseUserMining> courseUsers = new HashMap<Long, CourseUserMining>();
		try{
			//Find out teachers first
			for(PersonComponentAssignment loadedItem : personComponentAssignment)
			{
				CourseUserMining item = new CourseUserMining();
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), course_mining, old_course_mining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), user_mining, old_user_mining);
				item.setRole(Long.valueOf(platform.getPrefix() + "" + 2), role_mining, old_role_mining);
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
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), course_mining, old_course_mining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), user_mining, old_user_mining);
				item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				item.setRole(Long.valueOf(platform.getPrefix() + "" + 1), role_mining, old_role_mining);
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
	 * Generates CourseWikiMining-objects from the given data
	 * 
	 * @return	HashMap with CourseWikiMining-objects
	 */
	public static HashMap<Long, CourseWikiMining> generateCourseWikiMining()
	{
		HashMap<Long, CourseWikiMining> courseWikis = new HashMap<Long, CourseWikiMining>();
		try{
			
			for(EComposing loadedItem : eComposing)
			{
				if(wiki_mining.get(loadedItem.getComponent()) != null || old_wiki_mining.get(loadedItem.getComponent()) != null)
				{
					CourseWikiMining item = new CourseWikiMining();
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), course_mining, old_course_mining);
					item.setWiki(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), wiki_mining, old_wiki_mining);
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
	 * Generates DegreeCourseMining-objects from the given data
	 * 
	 * @return	HashMap with DegreeCourseMining-objects
	 */
	public static HashMap<Long, DegreeCourseMining> generateDegreeCourseMining()
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
	
	/**
	 * Generates DepartmentDegreeMining-objects from the given data
	 * 
	 * @return	HashMap with DepartmentDegreeMining-objects
	 */
	public static HashMap<Long, DepartmentDegreeMining> generateDepartmentDegreeMining()
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
	
	/**
	 * Generates GroupUserMining-objects from the given data
	 * 
	 * @return	HashMap with GroupUserMining-objects
	 */
	public static HashMap<Long, GroupUserMining> generateGroupUserMining()
	{
		HashMap<Long, GroupUserMining> groupUsers = new HashMap<Long, GroupUserMining>();
		
		try{
			
			for(PlatformGroupSpecification loadedItem : platformGroupSpecification)
			{
				GroupUserMining item = new GroupUserMining();
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), user_mining, old_user_mining);
				item.setGroup(Long.valueOf(platform.getPrefix() + "" + loadedItem.getGroup()), group_mining, old_group_mining);
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
	 * Generates CourseGroupMining-objects from the given data
	 * 
	 * @return	HashMap with CourseGroupMining-objects
	 */
	public static HashMap<Long, CourseGroupMining> generateCourseGroupMining()
	{
		HashMap<Long, CourseGroupMining> courseGroups = new HashMap<Long, CourseGroupMining>();
		try{
			for(TeamExerciseGroup loadedItem : teamExerciseGroup)
			{
				CourseGroupMining item = new CourseGroupMining();
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), course_mining, old_course_mining);
				item.setGroup(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()), group_mining, old_group_mining);
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
	 * Generates CourseForumMining-objects from the given data
	 * 
	 * @return	HashMap with CourseForumMining-objects
	 */
	public static HashMap<Long, CourseForumMining> generateCourseForumMining()
	{
		HashMap<Long, CourseForumMining> courseForum = new HashMap<Long, CourseForumMining>();
		try{
			
			for(EComposing loadedItem : eComposing)
			{
				if(forum_mining.get(loadedItem.getComponent()) != null || old_forum_mining.get(loadedItem.getComponent()) != null)
				{
					CourseForumMining item = new CourseForumMining();
					item.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComposing()), course_mining, old_course_mining);
					item.setForum(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), forum_mining, old_forum_mining);
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
	 * Generates ForumLogMining-objects from the given data
	 * 
	 * @return	HashMap with ForumLogMining-objects
	 */
	public static HashMap<Long, ForumLogMining> generateForumLogMining()
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
				
				item.setForum(Long.valueOf(platform.getPrefix() + "" + loadedItem.getForum()), forum_mining, old_forum_mining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getLastUpdater()), user_mining, old_user_mining);
				item.setSubject(loadedItem.getTitle());
				item.setMessage(loadedItem.getContent());
				item.setAction("Post");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(platform.getId());
				
				if(ecMap.get(loadedItem.getForum()) != null)
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + ecMap.get(loadedItem.getForum()).getComposing()), course_mining, old_course_mining);
				
				if(item.getUser() != null && item.getForum() != null )//a forum doesn't have to be in a course, so no check for FK "course"
					forumLogs.put(item.getId(), item);
			}
			
			for(ForumEntryState loadedItem : forumEntryState)
			{
				ForumLogMining item = new ForumLogMining();
				item.setId(forumLogs.size() + forumLogMax + 1);
				item.setForum(loadedItem.getForum(), forum_mining, old_forum_mining);
				item.setUser(loadedItem.getUser(), user_mining, old_user_mining);
				item.setAction("View");
				if(forumLogs.get(loadedItem.getEntry()) != null)
						item.setSubject(forumLogs.get(loadedItem.getEntry()).getSubject());
				item.setMessage("");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));		
				
				if(ecMap.get(loadedItem.getForum()) != null)
					item.setCourse(ecMap.get(loadedItem.getForum()).getComposing(), course_mining, old_course_mining);
				
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
	 * Generates WikiLogMining-objects from the given data
	 * 
	 * @return	HashMap with WikiLogMining-objects
	 */
	public static HashMap<Long, WikiLogMining> generateWikiLogMining()
	{
		HashMap<Long, WikiLogMining> wikiLogs = new HashMap<Long, WikiLogMining>();
		try{
			for(WikiEntry loadedItem : wikiEntry)
			{
				WikiLogMining item = new WikiLogMining();
				item.setId(wikiLogs.size() + wikiLogMax + 1);
				
				item.setWiki(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), wiki_mining, old_wiki_mining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCreator()), user_mining, old_user_mining);
				if(item.getWiki() != null && eComposingMap.get(item.getWiki().getId()) != null)
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + eComposingMap.get(item.getWiki().getId()).getComposing()), course_mining, old_course_mining);
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(platform.getId());
				
				
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
	 * Generates CourseLogMining-objects from the given data
	 * 
	 * @return	HashMap with CourseLogMining-objects
	 */
	public static HashMap<Long, CourseLogMining> generateCourseLogMining()
	{
		HashMap<Long, CourseLogMining> courseLogs = new HashMap<Long, CourseLogMining>();
		try{
			for(PortfolioLog loadedItem : portfolioLog)
			{
				CourseLogMining item = new CourseLogMining();
				item.setId(courseLogs.size() + courseLogMax + 1);
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), course_mining, old_course_mining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), user_mining, old_user_mining);
				item.setAction(loadedItem.getTypeOfModification()+"");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(platform.getId());
				
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
	 * Generates QuestionLogMining-objects from the given data
	 * 
	 * @return	HashMap with QuestionLogMining-objects
	 */
	public static HashMap<Long, QuestionLogMining> generateQuestionLogMining()
	{
		HashMap<Long, QuestionLogMining> questionLogs = new HashMap<Long, QuestionLogMining>();
		
		try{
			for(TAnswerPosition loadedItem : tAnswerPosition)
			{
				QuestionLogMining item = new QuestionLogMining();
				
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), user_mining, old_user_mining);
				item.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getTest()), quiz_mining, old_quiz_mining);
				item.setQuestion(Long.valueOf(platform.getPrefix() + "" + loadedItem.getQuestion()), question_mining, old_question_mining);
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getTest()), course_mining, old_course_mining);
				item.setId(questionLogs.size() + questionLogMax + 1);
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getEvaluated()));
				item.setPlatform(platform.getId());
				
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
	 * Generates QuizLogMining-objects from the given data
	 * 
	 * @return	HashMap with QuizLogMining-objects
	 */
	public static HashMap<Long, QuizLogMining> generateQuizLogMining()
	{
		HashMap<Long, QuizLogMining> quizLogs = new HashMap<Long, QuizLogMining>();
		try{
			for(TQtiEvalAssessment loadedItem : tQtiEvalAssessment)
			{
				QuizLogMining item = new QuizLogMining();
				
				item.setId(quizLogs.size() + quizLogMax + 1);
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), course_mining, old_course_mining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCandidate()), user_mining, old_user_mining);
				item.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getAssessment()), quiz_mining, old_quiz_mining);
				item.setGrade(Double.valueOf(loadedItem.getEvaluatedScore()));
				item.setPlatform(platform.getId());
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
	 * Generates AssignmentLogMining-objects from the given data
	 * 
	 * @return	HashMap with AssignmentLogMining-objects
	 */
	public static HashMap<Long, AssignmentLogMining> generateAssignmentLogMining()
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
				
				item.setAssignment(Long.valueOf(platform.getPrefix() + "" + loadedItem.getExercise()), assignment_mining, old_assignment_mining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUser()), user_mining, old_user_mining);
				item.setGrade(loadedItem.getPoints());
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getUploadDate()));
				item.setPlatform(platform.getId());
				//Get the course_id via the exercisegroup_id
				if(eg.get(loadedItem.getCommunity()) != null)
					item.setCourse(Long.valueOf(platform.getPrefix() + "" + eg.get(loadedItem.getCommunity())), course_mining, old_course_mining);
				
				item.setId(assignmentLogs.size() + assignmentLogMax + 1);
				
				if(item.getCourse() != null && item.getAssignment() != null && item.getUser() != null)
					assignmentLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + assignmentLogs.size() + " AssignmentLogMinings.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return assignmentLogs;
	}
	
	/**
	 * Generates ScormLogMining-objects from the given data
	 * 
	 * @return	HashMap with ScormLogMining-objects
	 */
	public static HashMap<Long, ScormLogMining> generateScormLogMining()
	{
		HashMap<Long, ScormLogMining> scormLogs = new HashMap<Long, ScormLogMining>();
		HashMap<Long, Long> eComp = new HashMap<Long, Long>();
		for(EComposing item : eComposing)
				eComp.put(item.getComponent(), item.getComposing());
		
		try {
			for(ScormSessionTimes loadedItem : scormSessionTimes)
			{
				ScormLogMining item = new ScormLogMining();
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), user_mining, old_user_mining);
				try{
					if(loadedItem.getScore() != null && !loadedItem.getScore().equals("null"))
						item.setGrade(Double.valueOf(loadedItem.getScore().toString()));
					
					item.setScorm(Long.valueOf(platform.getPrefix() + "" + loadedItem.getComponent()), scorm_mining, old_scorm_mining);
					item.setAction(loadedItem.getStatus());
					item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setId(scormLogs.size() + scormLogMax + 1);
					item.setPlatform(platform.getId());
					
					if(eComp.get(loadedItem.getComponent()) != null)
						item.setCourse(Long.valueOf(platform.getPrefix() + "" + eComp.get(loadedItem.getComponent())), course_mining, old_course_mining);
					
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
	 * Generates ResourceLogMining-objects from the given data
	 * 
	 * @return	HashMap with ResourceLogMining-objects
	 */
	public static HashMap<Long, ResourceLogMining> generateResourceLogMining()
	{
		HashMap<Long, ResourceLogMining> resourceLogs = new HashMap<Long, ResourceLogMining>();
		try{
			for(BiTrackContentImpressions loadedItem : biTrackContentImpressions)
			{
				ResourceLogMining item = new ResourceLogMining();
				item.setResource(Long.valueOf(platform.getPrefix() + "" + loadedItem.getContent()), resource_mining, old_resource_mining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUser()), user_mining, old_user_mining);
				item.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getContainer()), course_mining, old_course_mining);
				item.setAction("View");
				item.setDuration(1L);
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
	 * Generates ChatLogMining-objects from the given data
	 * 
	 * @return	HashMap with ChatLogMining-objects
	 */
	public static HashMap<Long, ChatLogMining> generateChatLogMining()
	{
		HashMap<Long, ChatLogMining> chatLogs = new HashMap<Long, ChatLogMining>();
		try{
			for(ChatProtocol loadedItem : chatProtocol)
			{
				ChatLogMining item = new ChatLogMining();
				
				item.setId(chatLogs.size() + chatLogMax + 1);
				item.setChat(Long.valueOf(platform.getPrefix() + "" + loadedItem.getChatroom()), chat_mining, old_chat_mining);
				item.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getPerson()), user_mining, old_user_mining);
				item.setMessage(loadedItem.getChatSource());
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setPlatform(platform.getId());
				
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
	

	private static void loadFromFile()
	{
		try
	    {	    
			chatroom = new ArrayList<Chatroom>();	        
	        eComposing = new ArrayList<EComposing>();
	        biTrackContentImpressions = new ArrayList<BiTrackContentImpressions>();
	        chatProtocol = new ArrayList<ChatProtocol>();
	        eComponentType = new ArrayList<EComponentType>();
	        eComponent= new ArrayList<EComponent>();	     
	        exercisePersonalised= new ArrayList<ExercisePersonalised>();
	        forumEntry = new ArrayList<ForumEntry>();
	        forumEntryState = new ArrayList<ForumEntryState>();
	        person = new ArrayList<Person>();
	        personComponentAssignment = new ArrayList<PersonComponentAssignment>();
	        platformGroupSpecification = new ArrayList<PlatformGroupSpecification>();
	        platformGroup = new ArrayList<PlatformGroup>();
	        portfolio = new ArrayList<Portfolio>();
	        portfolioLog = new ArrayList<PortfolioLog>();
	        scormSessionTimes = new ArrayList<ScormSessionTimes>();
	        t2Task = new ArrayList<T2Task>();
	        tAnswerPosition = new ArrayList<TAnswerPosition>();
	        teamExerciseComposingExt = new ArrayList<TeamExerciseComposingExt>();
	        teamExerciseGroup = new ArrayList<TeamExerciseGroup>();
	        teamExerciseGroupMember = new ArrayList<TeamExerciseGroupMember>();
	        tGroupFullSpecification = new ArrayList<TGroupFullSpecification>();
	        tQtiContent = new ArrayList<TQtiContent>();
	        tQtiEvalAssessment = new ArrayList<TQtiEvalAssessment>();
	        tTestSpecification = new ArrayList<TTestSpecification>();
	        wikiEntry = new ArrayList<WikiEntry>();
	        exerciseGroup = new ArrayList<ExerciseGroup>();
	    	System.out.println("Reading txt DB...");
	    	BufferedReader input =  new BufferedReader(new FileReader("c://users//s.schwarzrock//desktop//clixDB.txt"));
	    	String line = null;
    		while (( line = input.readLine()) != null)
	    	{
    			String[] pre = new String[20];
    			ArrayList<String> nPre = new ArrayList<String>();
    			int pos = 0;
    			while(line.indexOf("?$") > -1)
    				{
    					
    					pre[pos] = line.substring(0, line.indexOf("?$"));
    					nPre.add(line.substring(0, line.indexOf("?$")));
    					line = line.substring(line.indexOf("?$") +2);
    					pos++;
    				}
    				nPre.add(line);
    				pre[pos] = line;
    			
    			if(nPre.get(0).equals("BiTrackContentImpressions") && nPre.size() > 6)
    			{
    				BiTrackContentImpressions item = new BiTrackContentImpressions();
    				String[] sa = pre;
    				BiTrackContentImpressionsPK id = new BiTrackContentImpressionsPK();
    				if(!sa[1].equals("null"))
    					item.setContainer(Long.parseLong(sa[1]));
    				if(!sa[3].equals("null"))
    					item.setCharacteristic(Long.parseLong(sa[3]));
    				if(!sa[4].equals("null"))
    					item.setContent(Long.parseLong(sa[4]));
    				item.setDayOfAccess(sa[2]);
    				if(!sa[5].equals("null"))
    					item.setTotalImpressions(Long.parseLong(sa[5]));
    				if(!sa[6].equals("null"))
    					item.setUser(Long.parseLong(sa[6]));
    				id.setCharacteristic(item.getCharacteristic());
    				id.setContainer(item.getContainer());
    				id.setContent(item.getContent());
    				id.setDayOfAccess(item.getDayOfAccess());
    				id.setUser(item.getUser());
    				item.setId(id);
    				
    				biTrackContentImpressions.add(item);
    			}
    			else if(nPre.get(0).equals("ChatProtocol") && nPre.size() > 5)
    			{
    				ChatProtocol item = new ChatProtocol();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[4].equals("null"))
    					item.setChatroom(Long.parseLong(sa[4]));
    				item.setChatSource(sa[2]);
    				item.setLastUpdated(sa[3]);
    				if(!sa[5].equals("null"))
    					item.setPerson(Long.valueOf(sa[5]));
    				
    				chatProtocol.add(item);
    			}
    			else if(nPre.get(0).equals("Chatroom") && nPre.size() > 3)
    			{
    				Chatroom item = new Chatroom();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				item.setLastUpdated(sa[2]);
    				item.setTitle(sa[3]);
    				
    				chatroom.add(item);
    			}
    			else if(nPre.get(0).equals("EComponent") && nPre.size() > 7)
    			{
    				EComponent item = new EComponent();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				item.setCreationDate(sa[2]);
    				item.setDescription(sa[3]);
    				item.setLastUpdated(sa[4]);
    				item.setName(sa[5]);
    				item.setStartDate(sa[6]);
    				if(!sa[7].equals("null"))
    					item.setType(Long.valueOf(sa[7]));
    				
    				eComponent.add(item);
    			}
    			else if(nPre.get(0).equals("EComponentType") && nPre.size() > 5)
    			{
    				EComponentType item = new EComponentType();
    				String[] sa = pre;
    				if(!sa[2].equals("null"))
    					item.setCharacteristic(Long.valueOf(sa[2]));
    				if(!sa[3].equals("null"))
    					item.setCharacteristicId(Long.valueOf(sa[3]));
    				if(!sa[4].equals("null"))
    					item.setComponent(Long.valueOf(sa[4]));
    				if(!sa[5].equals("null"))
    					item.setLanguage(Long.valueOf(sa[5]));
    				item.setUploadDir(sa[1]);
    				
    				EComponentTypePK id = new EComponentTypePK();
    				id.setComponent(item.getComponent());
    				id.setLanguage(item.getLanguage());
    				item.setId(id);
    				
    				eComponentType.add(item);
    			}
    			else if(nPre.get(0).equals("EComposing") && nPre.size() > 6)
    			{
    				EComposing item = new EComposing();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[4].equals("null"))
    					item.setComponent(Long.valueOf(sa[4]));
    				if(!sa[5].equals("null"))
    					item.setComposing(Long.valueOf(sa[5]));
    				if(!sa[6].equals("null"))
    					item.setComposingType(Long.valueOf(sa[6]));
    				item.setEndDate(sa[2]);
    				item.setStartDate(sa[3]);
    				
    				eComposing.add(item);
    			}
    			else if(nPre.get(0).equals("ExerciseGroup") && nPre.size() > 2)
    			{
    				ExerciseGroup item = new ExerciseGroup();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setAssociatedCourse(Long.valueOf(sa[2]));
    				
    				exerciseGroup.add(item);
    			}
    			
    			else if(nPre.get(0).equals("ExercisePersonalised") && nPre.size() > 6)
    			{
    				ExercisePersonalised item = new ExercisePersonalised();
    				String[] sa = pre;
    				if(!sa[2].equals("null"))
    					item.setCommunity(Long.valueOf(sa[2]));
    				if(!sa[3].equals("null"))
    					item.setExercise(Long.valueOf(sa[3]));
    				if(!sa[4].equals("null"))
    					item.setExerciseSheet(Long.valueOf(sa[4]));
    				if(!sa[5].equals("null"))
    					item.setPoints(Long.valueOf(sa[5]));
    				
    				item.setUploadDate(sa[1]);
    				if(!sa[6].equals("null"))
    					item.setUser(Long.valueOf(sa[6]));
    				
    				ExercisePersonalisedPK id = new ExercisePersonalisedPK();
    				id.setCommunity(item.getCommunity());
    				id.setExercise(item.getExercise());
    				id.setExerciseSheet(item.getExerciseSheet());
    				id.setUser(item.getUser());
    				item.setId(id);
    				
    				exercisePersonalised.add(item);
    			}
    			else if(nPre.get(0).equals("ForumEntry") && nPre.size() > 6)
    			{
    				ForumEntry item = new ForumEntry();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				item.setContent(sa[2]);
    				if(!sa[3].equals("null"))
    					item.setForum(Long.valueOf(sa[3]));
    				item.setLastUpdated(sa[4]);
    				if(!sa[6].equals("null"))
    					item.setLastUpdater(Long.valueOf(sa[6]));
    				item.setTitle(sa[5]);
    				
    				forumEntry.add(item);
    			}
    			else if(nPre.get(0).equals("ForumEntryState") && nPre.size() > 4)
    			{
    				ForumEntryState item = new ForumEntryState();
    				String[] sa = pre;
    				if(!sa[2].equals("null"))
    					item.setEntry(Long.valueOf(sa[2]));
    				if(!sa[3].equals("null"))
    					item.setForum(Long.valueOf(sa[3]));
    				item.setLastUpdated(sa[1]);
    				if(!sa[4].equals("null"))
    					item.setUser(Long.valueOf(sa[4]));
    				
    				ForumEntryStatePK id = new ForumEntryStatePK();
    				id.setEntry(item.getEntry());
    				id.setUser(item.getUser());
    				item.setId(id);
    				
    				forumEntryState.add(item);
    			}
    			else if(nPre.get(0).equals("Person") && nPre.size() > 5)
    			{
    				Person item = new Person();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setFirstLoginTime(sa[2]);
    				if(!sa[3].equals("null"))
    					item.setLastLoginTime(sa[3]);
    				item.setLogin(sa[4]);
    				if(!sa[5].equals("null"))
    					item.setGender(Long.valueOf(sa[5]));
    				
    				person.add(item);
    			}
    			else if(nPre.get(0).equals("PersonComponentAssignment") && nPre.size() > 3)
    			{
    				PersonComponentAssignment item = new PersonComponentAssignment();
    				String[] sa = pre;
    				PersonComponentAssignmentPK id = new PersonComponentAssignmentPK();
    				if(!sa[1].equals("null"))
    					item.setComponent(Long.parseLong(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setPerson(Long.parseLong(sa[2]));
    				if(!sa[3].equals("null"))
    					item.setContext(Long.parseLong(sa[3]));
    				
    				id.setPerson(item.getPerson());
    				id.setComponent(item.getComponent());
    				id.setContext(item.getContext());
    				item.setId(id);
    				
    				personComponentAssignment.add(item);
    			}
    			else if(nPre.get(0).equals("PlatformGroup") && nPre.size() > 4)
    			{
    				PlatformGroup item = new PlatformGroup();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				item.setCreated(sa[2]);
    				
    				item.setLastUpdated(sa[3]);
    				if(!sa[4].equals("null"))
    					item.setTypeId(Long.valueOf(sa[4]));
    				
    				platformGroup.add(item);
    			}
    			else if(nPre.get(0).equals("PlatformGroupSpecification") && nPre.size() > 2)
    			{
    				PlatformGroupSpecification item = new PlatformGroupSpecification();
    				String[] sa = pre;
    				if(!sa[1].equals("null"))
    					item.setGroup(Long.valueOf(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setPerson(Long.valueOf(sa[2]));
    				
    				PlatformGroupSpecificationPK id = new PlatformGroupSpecificationPK();
    				id.setGroup(item.getGroup());
    				id.setPerson(item.getPerson());
    				item.setId(id);
    				
    				platformGroupSpecification.add(item);
    			}
    			else if(nPre.get(0).equals("Portfolio") && nPre.size() > 3)
    			{
    				Portfolio item = new Portfolio();
    				String[] sa = pre;
    				if(!sa[1].equals("null"))
    					item.setId(Long.parseLong(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setPerson(Long.parseLong(sa[2]));
    				if(!sa[3].equals("null"))
    					item.setComponent(Long.parseLong(sa[3]));
    				
    				
    				
    				portfolio.add(item);
    			}
    			else if(nPre.get(0).equals("PortfolioLog") && nPre.size() > 6)
    			{
    				PortfolioLog item = new PortfolioLog();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[3].equals("null"))
    					item.setComponent(Long.valueOf(sa[3]));
    				item.setLastUpdated(sa[2]);
    				if(!sa[4].equals("null"))
    					item.setLastUpdater(Long.valueOf(sa[4]));
    				if(!sa[5].equals("null"))
    					item.setPerson(Long.valueOf(sa[5]));
    				if(!sa[6].equals("null"))
    					item.setTypeOfModification(Long.valueOf(sa[6]));
    				
    				portfolioLog.add(item);
    			}
    			else if(nPre.get(0).equals("ScormSessionTimes") && nPre.size() > 5)
    			{
    				ScormSessionTimes item = new ScormSessionTimes();
    				String[] sa = pre;
    				if(!sa[4].equals("null"))
    					item.setComponent(Long.valueOf(sa[4]));
    				item.setLastUpdated(sa[1]);
    				if(!sa[5].equals("null"))
    					item.setPerson(Long.valueOf(sa[5]));
    				item.setScore(sa[2]);
    				item.setStatus(sa[3]);
    				
    				ScormSessionTimesPK id = new ScormSessionTimesPK();
    				id.setComponent(item.getComponent());
    				id.setPerson(item.getPerson());
    				item.setId(id);
    				
    				scormSessionTimes.add(item);
    			}
    			else if(nPre.get(0).equals("T2Task") && nPre.size() > 5)
    			{
    				T2Task item = new T2Task();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[3].equals("null"))
    					item.setInputType(Long.valueOf(sa[3]));
    				item.setQuestionText(sa[2]);
    				if(!sa[4].equals("null"))
    					item.setTaskType(Long.valueOf(sa[4]));
    				if(!sa[5].equals("null"))
    					item.setTopicId(Long.valueOf(sa[5]));
    				
    				t2Task.add(item);
    			}
    			else if(nPre.get(0).equals("TAnswerPosition") && nPre.size() > 5)
    			{
     				TAnswerPosition item = new TAnswerPosition();
    				String[] sa = pre;
    				item.setEvaluated(sa[1]);
    				if(!sa[2].equals("null"))
    					item.setPerson(Long.valueOf(sa[2]));
    				if(!sa[3].equals("null"))
    					item.setQuestion(Long.valueOf(sa[3]));
    				if(!sa[4].equals("null"))
    					item.setTask(Long.valueOf(sa[4]));
    				if(!sa[5].equals("null"))
    					item.setTest(Long.valueOf(sa[5]));
    				
    				TAnswerPositionPK id = new TAnswerPositionPK();
    				id.setPerson(item.getPerson());
    				id.setQuestion(item.getQuestion());
    				id.setTask(item.getTask());
    				id.setTest(item.getTest());
    				
    				item.setId(id);
    				
    				tAnswerPosition.add(item);
    			}
    			else if(nPre.get(0).equals("TeamExerciseComposingExt") && nPre.size() > 3)
    			{
    				TeamExerciseComposingExt item = new TeamExerciseComposingExt();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[3].equals("null"))
    					item.seteComposingId(Long.valueOf(sa[3]));
    				item.setSubmissionDeadline(sa[2]);
    				
    				teamExerciseComposingExt.add(item);
    			}
    			else if(nPre.get(0).equals("TeamExerciseGroup") && nPre.size() > 2)
    			{
       				TeamExerciseGroup item = new TeamExerciseGroup();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setComponent(Long.valueOf(sa[2]));
    				
    				teamExerciseGroup.add(item);
    			}
    			else if(nPre.get(0).equals("TeamExerciseGroupMember") && nPre.size() > 3)
    			{
    				TeamExerciseGroupMember item = new TeamExerciseGroupMember();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setExerciseGroup(Long.valueOf(sa[2]));
    				if(!sa[3].equals("null"))
    					item.setPortfolio(Long.valueOf(sa[3]));
    				
    				teamExerciseGroupMember.add(item);
    			}
    			else if(nPre.get(0).equals("TGroupFullSpecification") && nPre.size() > 2)
    			{
    				TGroupFullSpecification item = new TGroupFullSpecification();
    				String[] sa = pre;
    				if(!sa[1].equals("null"))
    					item.setGroup(Long.valueOf(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setPerson(Long.valueOf(sa[2]));
    				
    				tGroupFullSpecification.add(item);
    			}
    			else if(nPre.get(0).equals("TQtiContent") && nPre.size() > 4)
    			{
    				TQtiContent item = new TQtiContent();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				item.setCreated(sa[2]);
    				item.setLastUpdated(sa[3]);
    				item.setName(sa[4]);
    				
    				tQtiContent.add(item);
    			}
    			else if(nPre.get(0).equals("TQtiEvalAssessment") && nPre.size() > 7)
    			{
      				TQtiEvalAssessment item = new TQtiEvalAssessment();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[3].equals("null"))
    					item.setAssessment(Long.valueOf(sa[3]));
    				if(!sa[4].equals("null"))
    					item.setCandidate(Long.valueOf(sa[4]));
    				if(!sa[5].equals("null"))
    					item.setComponent(Long.valueOf(sa[5]));
    				if(!sa[6].equals("null"))
    					item.setEvalCount(Long.valueOf(sa[6]));
    				if(!sa[7].equals("null"))
    					item.setEvaluatedScore(Long.valueOf(sa[7]));
    				item.setLastInvocation(sa[2]);
    				
    				tQtiEvalAssessment.add(item);
    			}
    			else if(nPre.get(0).equals("TTestSpecification") && nPre.size() > 2)
    			{
     				TTestSpecification item = new TTestSpecification();
    				String[] sa = pre;
    				if(!sa[1].equals("null"))
    					item.setTask(Long.valueOf(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setTest(Long.valueOf(sa[2]));
    				
    				TTestSpecificationPK id = new TTestSpecificationPK();
    				id.setTask(item.getTask());
    				id.setTest(item.getTest());
    				
    				item.setId(id);
    				
    				tTestSpecification.add(item);
    			}
    			else if(nPre.get(0).equals("WikiEntry") && nPre.size() > 8)
    			{
      				WikiEntry item = new WikiEntry();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[5].equals("null"))
    					item.setComponent(Long.valueOf(sa[5]));
    				item.setCreated(sa[2]);
    				if(!sa[6].equals("null"))
    					item.setCreator(Long.valueOf(sa[6]));
    				if(!sa[7].equals("null"))
    					item.setLastProcessor(Long.valueOf(sa[7]));
    				item.setLastUpdated(sa[3]);
    				if(!sa[8].equals("null"))
    					item.setPublisher(Long.valueOf(sa[8]));
    				item.setPublishingDate(sa[4]);
    				
    				wikiEntry.add(item);
    			}
	    	}
    		
	        for(int i = 0; i < eComposing.size(); i++)
	        {
	        	eComposingMap.put(eComposing.get(i).getComponent(), eComposing.get(i));
	        }
	        input.close();
	        System.out.println("Loaded "+biTrackContentImpressions.size()+" biTrackContentImpressions");
	        System.out.println("Loaded "+chatProtocol.size()+" chatProtocols");
	        System.out.println("Loaded "+chatroom.size()+" chatroom");
	        System.out.println("Loaded "+eComponent.size()+" eComponent");
	        System.out.println("Loaded "+eComponentType.size()+" eComponentType");
	        System.out.println("Loaded "+eComposing.size()+" eComposing");
	        System.out.println("Loaded "+exercisePersonalised.size()+" exercisePersonalised");
	        System.out.println("Loaded "+exerciseGroup.size()+" exerciseGroup");	        
	        System.out.println("Loaded "+forumEntry.size()+" forumEntry");
	        System.out.println("Loaded "+forumEntryState.size()+" forumEntryState");
	        System.out.println("Loaded "+person.size()+" person");
	        System.out.println("Loaded "+platformGroup.size()+" platformGroup");
	        System.out.println("Loaded "+platformGroupSpecification.size()+" platformGroupSpecification");
	        System.out.println("Loaded "+portfolioLog.size()+" portfolioLog");
	        System.out.println("Loaded "+scormSessionTimes.size()+" scormSessionTimes");
	        System.out.println("Loaded "+t2Task.size()+" t2Task");
	        System.out.println("Loaded "+tAnswerPosition.size()+" tAnswerPosition");
	        System.out.println("Loaded "+teamExerciseComposingExt.size()+" teamExerciseComposingExt");
	        System.out.println("Loaded "+teamExerciseGroup.size()+" teamExerciseGroup");
	        System.out.println("Loaded "+teamExerciseGroupMember.size()+" teamExerciseGroupMember");
	        System.out.println("Loaded "+tGroupFullSpecification.size()+" tGroupFullSpecification");
	        System.out.println("Loaded "+tQtiContent.size()+" tQtiContent");
	        System.out.println("Loaded "+tQtiEvalAssessment.size()+" tQtiEvalAssessment");
	        System.out.println("Loaded "+tTestSpecification.size()+" tTestSpecification");
	        System.out.println("Loaded "+wikiEntry.size()+" wikiEntry");
	    		
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	private static void writeToFile()
	{
		ArrayList<IClixMappingClass> al = new ArrayList<IClixMappingClass>();
        al.addAll(chatroom);	        
        al.addAll(eComposing);
        al.addAll(biTrackContentImpressions);
        al.addAll(chatProtocol);
        al.addAll(eComponentType);
        al.addAll(eComponent);	     
        al.addAll(exerciseGroup);
        al.addAll(exercisePersonalised);
        al.addAll(forumEntry);
        al.addAll(forumEntryState);
        al.addAll(person);
        al.addAll(personComponentAssignment);
        al.addAll(platformGroupSpecification);
        al.addAll(platformGroup);
        al.addAll(portfolio);
        al.addAll(portfolioLog);
        al.addAll(scormSessionTimes);
        al.addAll(t2Task);
        al.addAll(tAnswerPosition);
        al.addAll(teamExerciseComposingExt);
        al.addAll(teamExerciseGroup);
        al.addAll(teamExerciseGroupMember);
        al.addAll(tGroupFullSpecification);
        al.addAll(tQtiContent);
        al.addAll(tQtiEvalAssessment);
        al.addAll(tTestSpecification);
        al.addAll(wikiEntry);	        
		try
	    {	    
	    	FileWriter out = new FileWriter("c://users//s.schwarzrock//desktop//clixDB.txt");
	    	PrintWriter pout = new PrintWriter(out);
    		for(int i = 0; i < al.size(); i++)
    		{
	    			pout.println(al.get(i).getString());
    		}

	    	pout.close();
	    	System.out.println("Done...");
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}

	

}
