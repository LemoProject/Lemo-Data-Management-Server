package de.lemo.dms.connectors.clix2010;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.lemo.dms.connectors.clix2010.clixDBClass.BiTrackContentImpressions;
import de.lemo.dms.connectors.clix2010.clixDBClass.BiTrackContentImpressionsPK;
import de.lemo.dms.connectors.clix2010.clixDBClass.ChatProtocol;
import de.lemo.dms.connectors.clix2010.clixDBClass.Chatroom;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponentType;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponentTypePK;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComposing;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponent;
import de.lemo.dms.connectors.clix2010.clixDBClass.ExercisePersonalised;
import de.lemo.dms.connectors.clix2010.clixDBClass.ExercisePersonalisedPK;
import de.lemo.dms.connectors.clix2010.clixDBClass.ForumEntry;
import de.lemo.dms.connectors.clix2010.clixDBClass.ForumEntryState;
import de.lemo.dms.connectors.clix2010.clixDBClass.ForumEntryStatePK;
import de.lemo.dms.connectors.clix2010.clixDBClass.Person;
import de.lemo.dms.connectors.clix2010.clixDBClass.PlatformGroup;
import de.lemo.dms.connectors.clix2010.clixDBClass.PlatformGroupSpecification;
import de.lemo.dms.connectors.clix2010.clixDBClass.PlatformGroupSpecificationPK;
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


import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
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
import de.lemo.dms.db.miningDBclass.IDMappingMining;
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
import de.lemo.dms.db.miningDBclass.AssignmentMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.WikiMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

public class ClixImporter {
	
	private static List<BiTrackContentImpressions> biTrackContentImpressions;
	private static List<ChatProtocol> chatProtocol;
	private static List<EComponent> eComponent;
	private static List<EComponentType> eComponentType;
	private static List<EComposing> eComposing;
	private static List<ExercisePersonalised> exercisePersonalised;
	private static List<ForumEntry> forumEntry;
	private static List<ForumEntryState> forumEntryState;
	private static List<Person> person;
	private static List<PlatformGroup> platformGroup;
	private static List<PlatformGroupSpecification> platformGroupSpecification;
	private static List<PortfolioLog> portfolioLog;
	private static List<ScormSessionTimes> scormSessionTimes;
	private static List<T2Task> t2Task;
	private static List<TAnswerPosition> tAnswerPosition;
	private static List<TeamExerciseComposingExt> teamExerciseComposingExt;
	private static List<TeamExerciseGroup> teamExerciseGroup;
	private static List<TeamExerciseGroupMember> teamExerciseGroupMember;
	private static List<TGroupFullSpecification> tGroupFullSpecification;
	private static List<TQtiContent> tQtiContent;
	private static List<TQtiEvalAssessment> tQtiEvalAssessment;
	private static List<TTestSpecification> tTestSpecification;
	private static List<WikiEntry> wikiEntry;
	private static List<Chatroom> chatroom;
	
	
	private static HashMap<Long, EComponent> eComponentMap = new HashMap<Long, EComponent>();
	
	//lists of object tables which are new found in LMS DB
		/** A List of new entries in the course table found in this run of the process. */
		static HashMap<Long, CourseMining> course_mining;
		
		/** A List of new entries in the quiz table found in this run of the process. */
		static HashMap<Long, QuizMining> quiz_mining;
		
		/** A List of new entries in the assignment table found in this run of the process. */
		static HashMap<Long, AssignmentMining> assignment_mining;
		
		/** A List of new entries in the assignment table found in this run of the process. */
		static HashMap<Long, ScormMining> scorm_mining;
		
		/** A List of new entries in the forum table found in this run of the process. */
		static HashMap<Long, ForumMining> forum_mining;
		
		/** A List of new entries in the resource table found in this run of the process. */	
		static HashMap<Long, ResourceMining> resource_mining;
		
		/** A List of new entries in the user table found in this run of the process. */	
		static HashMap<Long, UserMining> user_mining;
		
		/** A List of new entries in the wiki table found in this run of the process. */	
		static HashMap<Long, WikiMining> wiki_mining;
		
		/** A List of new entries in the group table found in this run of the process. */
		static HashMap<Long, GroupMining> group_mining;
		
		/** A List of entries in the new question table found in this run of the process. */
		static HashMap<Long, QuestionMining> question_mining;
		
		/** A List of entries in the new role table found in this run of the process. */
		static HashMap<Long, RoleMining> role_mining;
		
		/** The department_mining. */
		static HashMap<Long, DepartmentMining> department_mining;
		
		/** The degree_mining. */
		static HashMap<Long, DegreeMining> degree_mining;
		
		/** The chat_mining. */
		static HashMap<Long, ChatMining> chat_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, ChatLogMining> chat_log_mining;
		
		/** The quiz_question_mining. */
		static HashMap<Long, QuizQuestionMining> quiz_question_mining;
		
		/** The course_quiz_mining. */
		static HashMap<Long, CourseQuizMining> course_quiz_mining;
		
		/** The course_assignment_mining. */
		static HashMap<Long, CourseAssignmentMining> course_assignment_mining;
		
		/** The course_scorm_mining. */
		static HashMap<Long, CourseScormMining> course_scorm_mining;
		
		/** The course_user_mining. */
		static HashMap<Long, CourseUserMining> course_user_mining;
		
		/** The course_forum_mining. */
		static HashMap<Long, CourseForumMining> course_forum_mining;
		
		/** The course_group_mining. */
		static HashMap<Long, CourseGroupMining> course_group_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, CourseResourceMining> course_resource_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, CourseWikiMining> course_wiki_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, GroupUserMining> group_user_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, QuizUserMining> quiz_user_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, AssignmentLogMining> assignment_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, CourseLogMining> course_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, ForumLogMining> forum_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, QuizLogMining> quiz_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, QuestionLogMining> question_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, ScormLogMining> scorm_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, ResourceLogMining> resource_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, WikiLogMining> wiki_log_mining;
		
		static HashMap<Long, DepartmentDegreeMining> department_degree_mining;
		
		static HashMap<Long, DegreeCourseMining> degree_course_mining;
		
		/** The table that maps user-ids of the source database (string) onto numeric values.*/
		static HashMap<String, IDMappingMining> id_mapping;
		
		/** The table that maps user-ids of the source database (string) onto numeric values.*/
		static HashMap<String, IDMappingMining> old_id_mapping;
		
	//lists of object tables which are already in the mining DB
		/** A List of entries in the course table, needed for linking reasons in the process. */
		static HashMap<Long, CourseMining> old_course_mining;
		
		/** A List of entries in the quiz table, needed for linking reasons in the process. */
		static HashMap<Long, QuizMining> old_quiz_mining;
			
		/** A List of entries in the assignment table, needed for linking reasons in the process. */
		static HashMap<Long, AssignmentMining> old_assignment_mining;
		
		/** A List of entries in the scorm table, needed for linking reasons in the process. */
		static HashMap<Long, ScormMining> old_scorm_mining;
		
		/** A List of entries in the forum table, needed for linking reasons in the process. */
		static HashMap<Long, ForumMining> old_forum_mining;
		
		/** A List of entries in the resource table, needed for linking reasons in the process. */
		static HashMap<Long, ResourceMining> old_resource_mining;
		
		/** A List of entries in the user table, needed for linking reasons in the process. */
		static HashMap<Long, UserMining> old_user_mining;
		
		/** A List of entries in the wiki table, needed for linking reasons in the process. */
		static HashMap<Long, WikiMining> old_wiki_mining;
		
		/** A List of entries in the group table, needed for linking reasons in the process. */
		static HashMap<Long, GroupMining> old_group_mining;
		
		/** A List of entries in the question table, needed for linking reasons in the process. */
		static HashMap<Long, QuestionMining> old_question_mining;
		
		/** A List of entries in the role table, needed for linking reasons in the process. */
		static HashMap<Long, RoleMining> old_role_mining;
		
		/** A List of entries in the quiz_question table, needed for linking reasons in the process. */
		static HashMap<Long, QuizQuestionMining> old_quiz_question_mining;
		
		/** A List of entries in the course_quiz table, needed for linking reasons in the process. */
		static HashMap<Long, CourseQuizMining> old_course_quiz_mining;
		
		/** The old_department_mining. */
		static HashMap<Long, DepartmentMining> old_department_mining;
		
		/** The old_degree_mining. */
		static HashMap<Long, DegreeMining> old_degree_mining;
		
		/** The old_chat_mining. */
		static HashMap<Long, ChatMining> old_chat_mining;
		
		/** The old_chat_log_mining. */
		static HashMap<Long, ChatLogMining> old_chat_log_mining;
		
		/** The course_assignment_mining. */
		static HashMap<Long, CourseAssignmentMining> old_course_assignment_mining;
		
		/** The course_scorm_mining. */
		static HashMap<Long, CourseScormMining> old_course_scorm_mining;
		
		/** The course_user_mining. */
		static HashMap<Long, CourseUserMining> old_course_user_mining;
		
		/** The course_forum_mining. */
		static HashMap<Long, CourseForumMining> old_course_forum_mining;
		
		/** The course_group_mining. */
		static HashMap<Long, CourseGroupMining> old_course_group_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, CourseResourceMining> old_course_resource_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, CourseWikiMining> old_course_wiki_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, GroupUserMining> old_group_user_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, QuizUserMining> old_quiz_user_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, AssignmentLogMining> old_assignment_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, CourseLogMining> old_course_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, ForumLogMining> old_forum_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, QuizLogMining> old_quiz_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, QuestionLogMining> old_question_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, ScormLogMining> old_scorm_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, ResourceLogMining> old_resource_log_mining;
		
		/** The chat_log_mining. */
		static HashMap<Long, WikiLogMining> old_wiki_log_mining;
		
		static HashMap<Long, DepartmentDegreeMining> old_department_degree_mining;
		
		static HashMap<Long, DegreeCourseMining> old_degree_course_mining;
		
	
	
	public static void getClixData()
	{
		Long starttime = System.currentTimeMillis()/1000;
		
		Long largestId = -1L;		

		
		//Do Import
		initialize();
		loadData();
		saveData();
		
		Long endtime = System.currentTimeMillis()/1000;
		ConfigMining config = new ConfigMining();
	    config.setLastmodified(System.currentTimeMillis());
	    config.setElapsed_time((endtime) - (starttime));	
	    config.setLargestId(largestId);
	    config.setPlatform("Clix2010");
	    ServerConfigurationHardCoded.getInstance().getDBHandler().saveToDB(config);
		ServerConfigurationHardCoded.getInstance().getDBHandler().closeConnection();
	}
	
	
	
	public static void updateClixData()
	{
		Long starttime = System.currentTimeMillis()/1000;
		Long largestId = -1L;		

		
		//Do Update
		
		Long endtime = System.currentTimeMillis()/1000;
		ConfigMining config = new ConfigMining();
	    config.setLastmodified(System.currentTimeMillis());
	    config.setElapsed_time((endtime) - (starttime));	
	    config.setLargestId(largestId);
	    config.setPlatform("Clix2010");
	    ServerConfigurationHardCoded.getInstance().getDBHandler().saveToDB(config);
		ServerConfigurationHardCoded.getInstance().getDBHandler().closeConnection();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void saveData()
	{
		try{
			List<Collection<?>> updates = new ArrayList<Collection<?>>();
			
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
	
			user_mining = generateUserMining();
			updates.add(user_mining.values());
			
			wiki_mining = generateWikiMining();
			updates.add(wiki_mining.values());
			
			group_mining = generateGroupMining();
			updates.add(group_mining.values());
			
			question_mining = generateQuestionMining();
			updates.add(question_mining.values());
			
			role_mining = generateRoleMining();
			updates.add(role_mining.values());
			
			department_mining = generateDepartmentMining();
			updates.add(department_mining.values());
			
			degree_mining = generateDegreeMining();
			updates.add(degree_mining.values());
			
			chat_mining = generateChatMining();
			updates.add(chat_mining.values());
			
			/*quiz_question_mining = generateQuizQuestionMining();
			updates.add(quiz_question_mining.values());
			
			course_quiz_mining = generateCourseQuizMining();
			updates.add(course_quiz_mining.values());
			
			course_assignment_mining = generateCourseAssignmentMining();
			updates.add(course_assignment_mining.values());
			
			course_scorm_mining = generateCourseScormMining();
			updates.add(course_scorm_mining.values());
			
			course_user_mining = generateCourseUserMining();
			updates.add(course_user_mining.values());
			
			course_forum_mining = generateCourseForumMining();
			updates.add(course_forum_mining.values());
			
			course_group_mining = generateCourseGroupMining();
			updates.add(course_group_mining.values());
			
			course_resource_mining = generateCourseResourceMining();
			updates.add(course_resource_mining.values());
			*/
			assignment_log_mining = generateAssignmentLogMining();
			updates.add(assignment_log_mining.values());
			/*
			course_log_mining = generateCourseLogMining();
			updates.add(course_log_mining.values());
			
			course_wiki_mining = generateCourseWikiMining();
			updates.add(course_wiki_mining.values());
			*/
			forum_log_mining = generateForumLogMining();
			updates.add(forum_log_mining.values());
			/*
			group_user_mining = generateGroupUserMining();
			updates.add(group_user_mining.values());
			*/
			quiz_log_mining = generateQuizLogMining();
			updates.add(quiz_log_mining.values());
			
			question_log_mining = generateQuestionLogMining();
			updates.add(question_log_mining.values());
			
			scorm_log_mining = generateScormLogMining();
			updates.add(scorm_log_mining.values());
			/*
			quiz_user_mining = generateQuizUserMining();
			updates.add(quiz_user_mining.values());
			*/
			resource_log_mining = generateResourceLogMining();
			updates.add(resource_log_mining.values());
			
			wiki_log_mining = generateWikiLogMining();
			updates.add(wiki_log_mining.values());
			
			chat_log_mining = generateChatLogMining();
			updates.add(chat_log_mining.values());
			/*
			department_degree_mining = generateDepartmentDegreeMining();
			updates.add(department_degree_mining.values());
			
			degree_course_mining = generateDegreeCourseMining();
			updates.add(degree_course_mining.values());
			*/
			ServerConfigurationHardCoded.getInstance().getDBHandler().saveCollectionToDB(updates);
			
	
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static void initialize()
	{
		try{
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			
			dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
			//accessing DB by creating a session and a transaction using HibernateUtil
	        Session session = dbHandler.getSession();
	        //Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
	        session.clear();
	        Transaction tx = session.beginTransaction();
			
			
	        ArrayList<?> l;
	        
	        Query old_course = session.createQuery("from CourseMining x order by x.id asc");
	        l = (ArrayList<CourseMining>) old_course.list();
	        old_course_mining = new HashMap<Long, CourseMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_course_mining.put(Long.valueOf(((CourseMining)l.get(i)).getId()), (CourseMining)l.get(i));  
	        System.out.println("Read " + old_course_mining.size() +" old CourseMinings."); 
	        
	        Query old_quiz = session.createQuery("from QuizMining x order by x.id asc");
	        l = (ArrayList<QuizMining>) old_quiz.list();
	        old_quiz_mining = new HashMap<Long, QuizMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_quiz_mining.put(Long.valueOf(((QuizMining)l.get(i)).getId()), (QuizMining)l.get(i));  
	        System.out.println("Read " + old_quiz_mining.size() +" old QuizMinings."); 
	
	        Query old_assignment = session.createQuery("from AssignmentMining x order by x.id asc");
	        l = (ArrayList<AssignmentMining>) old_assignment.list();
	        old_assignment_mining = new HashMap<Long, AssignmentMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_assignment_mining.put(Long.valueOf(((AssignmentMining)l.get(i)).getId()), (AssignmentMining)l.get(i));  
	        System.out.println("Read " + old_assignment_mining.size() +" old AssignmentMinings."); 
			
	        Query old_scorm = session.createQuery("from ScormMining x order by x.id asc");
	        l = (ArrayList<ScormMining>) old_scorm.list();
	        old_scorm_mining = new HashMap<Long, ScormMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_scorm_mining.put(Long.valueOf(((ScormMining)l.get(i)).getId()), (ScormMining)l.get(i));  
	        System.out.println("Read " + old_scorm_mining.size() +" old ScormMinings."); 
	        
	        Query old_forum = session.createQuery("from ForumMining x order by x.id asc");
	        l = (ArrayList<ForumMining>) old_forum.list();
	        old_forum_mining = new HashMap<Long, ForumMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_forum_mining.put(Long.valueOf(((ForumMining)l.get(i)).getId()), (ForumMining)l.get(i));  
	        System.out.println("Read " + old_forum_mining.size() +" old ForumMinings."); 
			
	        Query old_resource = session.createQuery("from ResourceMining x order by x.id asc");
	        l = (ArrayList<ResourceMining>) old_resource.list();
	        old_resource_mining = new HashMap<Long, ResourceMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_resource_mining.put(Long.valueOf(((ResourceMining)l.get(i)).getId()), (ResourceMining)l.get(i));  
	        System.out.println("Read " + old_resource_mining.size() +" old ForumMinings."); 
			
	        Query old_user = session.createQuery("from UserMining x order by x.id asc");
	        l = (ArrayList<UserMining>) old_user.list();
	        old_user_mining = new HashMap<Long, UserMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_user_mining.put(Long.valueOf(((UserMining)l.get(i)).getId()), (UserMining)l.get(i));  
	        System.out.println("Read " + old_user_mining.size() +" old UserMinings."); 
			
	        Query old_wiki = session.createQuery("from WikiMining x order by x.id asc");
	        l = (ArrayList<WikiMining>) old_wiki.list();
	        old_wiki_mining = new HashMap<Long, WikiMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_wiki_mining.put(Long.valueOf(((WikiMining)l.get(i)).getId()), (WikiMining)l.get(i));  
	        System.out.println("Read " + old_wiki_mining.size() +" old WikiMinings."); 
	
	        Query old_group = session.createQuery("from GroupMining x order by x.id asc");
	        l = (ArrayList<GroupMining>) old_group.list();
	        old_group_mining = new HashMap<Long, GroupMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_group_mining.put(Long.valueOf(((GroupMining)l.get(i)).getId()), (GroupMining)l.get(i));  
	        System.out.println("Read " + old_group_mining.size() +" old GroupMinings."); 
			
	        Query old_question = session.createQuery("from QuestionMining x order by x.id asc");
	        l = (ArrayList<QuestionMining>) old_question.list();
	        old_question_mining = new HashMap<Long, QuestionMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_question_mining.put(Long.valueOf(((QuestionMining)l.get(i)).getId()), (QuestionMining)l.get(i));  
	        System.out.println("Read " + old_question_mining.size() +" old QuestionMinings."); 
	        
	        Query old_role = session.createQuery("from RoleMining x order by x.id asc");
	        l = (ArrayList<RoleMining>) old_role.list();
	        old_role_mining = new HashMap<Long, RoleMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_role_mining.put(Long.valueOf(((RoleMining)l.get(i)).getId()), (RoleMining)l.get(i));  
	        System.out.println("Read " + old_role_mining.size() +" old RoleMinings."); 
	        
	        Query old_department = session.createQuery("from DepartmentMining x order by x.id asc");
	        l = (ArrayList<DepartmentMining>) old_department.list();
	        old_department_mining = new HashMap<Long, DepartmentMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_department_mining.put(Long.valueOf(((DepartmentMining)l.get(i)).getId()), (DepartmentMining)l.get(i));  
	        System.out.println("Read " + old_department_mining.size() +" old DepartmentMinings."); 
	        
	        Query old_degree = session.createQuery("from DegreeMining x order by x.id asc");
	        l = (ArrayList<DegreeMining>) old_degree.list();
	        old_degree_mining = new HashMap<Long, DegreeMining>();
	        for(int i = 0; i < l.size() ; i++)
	        	old_degree_mining.put(Long.valueOf(((DegreeMining)l.get(i)).getId()), (DegreeMining)l.get(i));  
	        System.out.println("Read " + old_degree_mining.size() +" old DegreeMinings."); 
			
	        Query old_chat = session.createQuery("from ChatMining x order by x.id asc");
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



	@SuppressWarnings("unchecked")
	private static void loadData()
	{
		try{
			//accessing DB by creating a session and a transaction using HibernateUtil
	        Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
	        //Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
	        session.clear();
     
	        
	    
	        System.out.println("Starting data extraction.");  
	        
	        Query chatro = session.createQuery("from Chatroom x order by x.id asc");
	        chatroom = chatro.list();	        
	        System.out.println("Chatroom tables: " + chatroom.size());  
	
	        Query eCompo = session.createQuery("from EComposing x order by x.id asc");
	        eComposing = eCompo.list();	        
	        System.out.println("EComposing tables: " + eComposing.size());  
	        
	        Query biTrack = session.createQuery("from BiTrackContentImpressions x order by x.id asc");
	        biTrackContentImpressions = biTrack.list();	        
	        System.out.println("biTrackContentImpressions tables: " + biTrackContentImpressions.size());     
	        
	        Query chatProt = session.createQuery("from ChatProtocol x order by x.id asc");
	        chatProtocol = chatProt.list();	        
	        System.out.println("ChatProtocol tables: " + chatProtocol.size());   
	        

	        
	        Query eCompTy = session.createQuery("from EComponentType x order by x.id asc");
	        eComponentType = eCompTy.list();	        
	        System.out.println("EComponentType tables: " + eComponentType.size());  
	                
	        Query eComp = session.createQuery("from EComponent x order by x.id asc");
	        eComponent = eComp.list();	     
	        for(int i = 0; i < eComponent.size(); i++)
	        {
	        	eComponentMap.put(eComponent.get(i).getId(), eComponent.get(i));
	        }
	        System.out.println("EComponent tables: " + eComponent.size());    
	        
	        Query exPer = session.createQuery("from ExercisePersonalised x order by x.id asc");
	        exercisePersonalised = exPer.list();	        
	        System.out.println("ExercisePersonalised tables: " + exercisePersonalised.size()); 
	        
	        Query foEnt = session.createQuery("from ForumEntry x order by x.id asc");
	        forumEntry = foEnt.list();	        
	        System.out.println("ForumEntry tables: " + forumEntry.size()); 
	        
	        Query foEntS = session.createQuery("from ForumEntryState x order by x.id asc");
	        forumEntryState = foEntS.list();	        
	        System.out.println("ForumEntryState tables: " + forumEntryState.size()); 
	        
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
	        
	        
		}catch(Exception e)
		{
			e.printStackTrace();
		}

    	
	}
	
	private void loadData(Long start, Long end)
	{
		//accessing DB by creating a session and a transaction using HibernateUtil
        Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();

        
        
	}
	
	//Generators for objects
	
	
	
	public static HashMap<Long, ChatMining> generateChatMining() {
		HashMap<Long, ChatMining> chats = new HashMap<Long, ChatMining>();
		try{
			for(Iterator<Chatroom> iter = chatroom.iterator(); iter.hasNext();)
			{
				Chatroom loadedItem = iter.next();
				ChatMining item = new ChatMining();
				item.setId(loadedItem.getId());
				item.setTitle(loadedItem.getTitle());
				item.setChattime(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				
				chats.put(item.getId(), item);
			}
			
			
			
			System.out.println("Generated " + chats.size() + " ChatMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return chats;
	}
	
	public static HashMap<Long, ResourceMining> generateResourceMining()
	{
		HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
		HashMap<Long, ResourceMining> resources = new HashMap<Long, ResourceMining>();
		try{
			for(Iterator<EComponentType> iter1 = eComponentType.iterator(); iter1.hasNext();)
			{
				EComponentType loadedType = iter1.next();
				if(loadedType.getCharacteristicId() == 1L)
					eCTypes.put(iter1.next().getComponent(), loadedType);
			}
			
			for(Iterator<EComponent> iter = eComponent.iterator(); iter.hasNext();)
			{
				EComponent loadedItem = iter.next();
				ResourceMining item = new ResourceMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(loadedItem.getId());
					item.setTitle(loadedItem.getName());
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
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
	
	public static HashMap<Long, CourseMining> generateCourseMining()
	{
		HashMap<Long, CourseMining> courses = new HashMap<Long, CourseMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(Iterator<EComponentType> iter1 = eComponentType.iterator(); iter1.hasNext();)
			{
				EComponentType loadedType = iter1.next();
				if(loadedType.getCharacteristicId() == 4L || loadedType.getCharacteristicId() == 5L || loadedType.getCharacteristicId() == 3L)
					eCTypes.put(iter1.next().getComponent(), loadedType);
			}
			for(Iterator<EComponent> iter = eComponent.iterator(); iter.hasNext();)
			{
				EComponent loadedItem = iter.next();
				CourseMining item = new CourseMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(loadedItem.getId());
					item.setTitle(loadedItem.getName());
					item.setStartdate(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
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
	
	public static HashMap<Long, UserMining> generateUserMining()
	{
		HashMap<Long, UserMining> users = new HashMap<Long, UserMining>();
		try{
			for(Iterator<Person> iter = person.iterator(); iter.hasNext();)
			{
				Person loadedItem = iter.next();
				UserMining item = new UserMining();
				item.setId(loadedItem.getId());
				item.setLastlogin(TimeConverter.getTimestamp(loadedItem.getLastLoginTime()));
				item.setFirstaccess(TimeConverter.getTimestamp(loadedItem.getFirstLoginTime()));
				
				users.put(item.getId(), item);
			}
			System.out.println("Generated " + users.size() + " UserMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return users;
	}
	
	public static HashMap<Long, AssignmentMining> generateAssignmentMining()
	{
		HashMap<Long, AssignmentMining> assignments = new HashMap<Long, AssignmentMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(Iterator<EComponentType> iter1 = eComponentType.iterator(); iter1.hasNext();)
			{
				EComponentType loadedType = iter1.next();
				if(loadedType.getCharacteristicId() == 8L)
					eCTypes.put(iter1.next().getComponent(), loadedType);
			}
			for(Iterator<EComponent> iter = eComponent.iterator(); iter.hasNext();)
			{
				EComponent loadedItem = iter.next();
				AssignmentMining item = new AssignmentMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(loadedItem.getId());
					item.setTitle(loadedItem.getName());
					item.setTimeopen(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					assignments.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + assignments.size() + " AssignmentMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return assignments;
	}
	
	public static HashMap<Long, QuizMining> generateQuizMining()
	{
		HashMap<Long, QuizMining> quizzes = new HashMap<Long, QuizMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			HashMap<Long, EComposing> eCompo = new HashMap<Long, EComposing>();
			for(Iterator<EComponentType> iter1 = eComponentType.iterator(); iter1.hasNext();)
			{
				EComponentType loadedType = iter1.next();
				if(loadedType.getCharacteristicId() == 14L)
					eCTypes.put(iter1.next().getComponent(), loadedType);
			}
			for(Iterator<EComposing> iter2 = eComposing.iterator(); iter2.hasNext();)
			{
				EComposing loadedType = iter2.next();
				eCompo.put(iter2.next().getComposing(), loadedType);
			}
			for(Iterator<EComponent> iter = eComponent.iterator(); iter.hasNext();)
			{
				EComponent loadedItem = iter.next();
				QuizMining item = new QuizMining();
				if(eCTypes.get(loadedItem.getType()) != null)
				{
					item.setId(loadedItem.getId());
					item.setTitle(loadedItem.getName());
					item.setTimeopen(TimeConverter.getTimestamp(loadedItem.getStartDate()));
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					if(eCompo.get(loadedItem.getId()) != null)
						item.setTimeclose(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getEndDate()));
					quizzes.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + quizzes.size() + " QuizMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return quizzes;
	}
	

	
	public static HashMap<Long, ForumMining> generateForumMining()
	{
		
		HashMap<Long, ForumMining> forums = new HashMap<Long, ForumMining>();
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(Iterator<EComponentType> iter1 = eComponentType.iterator(); iter1.hasNext();)
			{
				EComponentType loadedType = iter1.next();
				if(loadedType.getCharacteristicId() == 2L)
					eCTypes.put(iter1.next().getComponent(), loadedType);
			}
			for(Iterator<EComponent> iter = eComponent.iterator(); iter.hasNext();)
			{
				EComponent loadedItem = iter.next();
				ForumMining item = new ForumMining();
				if(eCTypes.get(loadedItem.getType()) != null && eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("forum"))
				{
					item.setId(loadedItem.getId());
					item.setTitle(loadedItem.getName());
					item.setSummary(loadedItem.getDescription());
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					forums.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + forums.size() + " ForumMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return forums;
	}
	

	
	public static HashMap<Long, RoleMining> generateRoleMining()
	{
		HashMap<Long, RoleMining> roles = new HashMap<Long, RoleMining>();
		try{
			for(Iterator<PlatformGroup> iter = platformGroup.iterator(); iter.hasNext();)
			{
				PlatformGroup loadedItem = iter.next();
				RoleMining item = new RoleMining();
				item.setId(loadedItem.getTypeId());
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
			System.out.println("Generated " + roles.size() + " RoleMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return roles;
	}

	
	public static HashMap<Long, GroupMining> generateGroupMining()
	{
		HashMap<Long, GroupMining> groups = new HashMap<Long, GroupMining>();
		
		try{
			for(Iterator<PlatformGroup> iter = platformGroup.iterator(); iter.hasNext();)
			{
				PlatformGroup loadedItem = iter.next();
				GroupMining item = new GroupMining();
				item.setId(loadedItem.getId());
				item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreated()));
				
				groups.put(item.getId(), item);
			}
			System.out.println("Generated " + groups.size() + " GroupMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return groups;
	}

	
	public static HashMap<Long, WikiMining> generateWikiMining()
	{
		HashMap<Long, WikiMining> wikis = new HashMap<Long, WikiMining>();
		
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			for(Iterator<EComponentType> iter1 = eComponentType.iterator(); iter1.hasNext();)
			{
				EComponentType loadedType = iter1.next();
				if(loadedType.getCharacteristicId() == 2L)
					eCTypes.put(iter1.next().getComponent(), loadedType);
			}
			for(Iterator<EComponent> iter = eComponent.iterator(); iter.hasNext();)
			{
				EComponent loadedItem = iter.next();
				WikiMining item = new WikiMining();
				if(eCTypes.get(loadedItem.getType()) != null && eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("wiki"))
				{
					item.setId(loadedItem.getId());
					item.setTitle(loadedItem.getName());
					item.setSummary(loadedItem.getDescription());
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					
					wikis.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + wikis.size() + " WikiMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return wikis;
	}

	
	public static HashMap<Long, DepartmentMining> generateDepartmentMining()
	{
		HashMap<Long, DepartmentMining> departments = new HashMap<Long, DepartmentMining>();
		try{
		
			System.out.println("Generated " + departments.size() + " DepartmentMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return departments;
	}

	
	public static HashMap<Long, DegreeMining> generateDegreeMining()
	{
		HashMap<Long, DegreeMining> degrees = new HashMap<Long, DegreeMining>();
		try{
		
			System.out.println("Generated " + degrees.size() + " DegreeMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return degrees;
	}

	
	public static HashMap<Long, QuestionMining> generateQuestionMining()
	{
		HashMap<Long, QuestionMining> questions = new HashMap<Long, QuestionMining>();	
		
		try{
			HashMap<Long, T2Task> t2t = new HashMap<Long, T2Task>();
			for(Iterator<T2Task> iter1 = t2Task.iterator(); iter1.hasNext();)
			{
				T2Task loadedItem = iter1.next();
				t2t.put(loadedItem.getTopicId(), loadedItem);
			}
	
			for(Iterator<TQtiContent> iter = tQtiContent.iterator(); iter.hasNext();)
			{
				TQtiContent loadedItem = iter.next();
				QuestionMining item = new QuestionMining();
	
					item.setId(loadedItem.getId());
					item.setTitle(loadedItem.getName());
					if(t2t.get(loadedItem.getId()) != null)
					{
						item.setText(t2t.get(loadedItem.getId()).getQuestionText());
						item.setType(t2t.get(loadedItem.getId()).getTaskType() +"");
					}
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreated()));
	
					questions.put(item.getId(), item);
			}
			System.out.println("Generated " + questions.size() + " QuestionMining.");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return questions;
	}

	
	public static HashMap<Long, ScormMining> generateScormMining()
	{
		HashMap<Long, ScormMining> scorms = new HashMap<Long, ScormMining>();	
		
		try{
			HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
			HashMap<Long, EComposing> eCompo = new HashMap<Long, EComposing>();
			
			for(Iterator<EComponentType> iter1 = eComponentType.iterator(); iter1.hasNext();)
			{
				EComponentType loadedType = iter1.next();
				if(loadedType.getCharacteristicId() == 1L)
					eCTypes.put(iter1.next().getComponent(), loadedType);
			}
			for(Iterator<EComposing> iter2 = eComposing.iterator(); iter2.hasNext();)
			{
				EComposing loadedType = iter2.next();
				eCompo.put(iter2.next().getComposing(), loadedType);
			}
			for(Iterator<EComponent> iter = eComponent.iterator(); iter.hasNext();)
			{
				EComponent loadedItem = iter.next();
				ScormMining item = new ScormMining();
				if(eCTypes.get(loadedItem.getType()) != null && eCTypes.get(loadedItem.getType()).getUploadDir().toLowerCase().contains("scorm"))
				{
					item.setId(loadedItem.getId());
					item.setTitle(loadedItem.getName());
					item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setTimecreated(TimeConverter.getTimestamp(loadedItem.getCreationDate()));
					if(eCompo.get(loadedItem.getId()) != null)
					{
						item.setTimeopen(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getStartDate()));
						item.setTimeclose(TimeConverter.getTimestamp(eCompo.get(loadedItem.getId()).getEndDate()));
					}
					scorms.put(item.getId(), item);
				}
			}
			System.out.println("Generated " + scorms.size() + " ScormMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return scorms;
	}
	
	//Generators for relationships
	
	public static HashMap<Long, QuizQuestionMining> generateQuizQuestionMining()
	{
		HashMap<Long, QuizQuestionMining> quizQuestions = new HashMap<Long, QuizQuestionMining>();
		
		try{
			for(Iterator<TTestSpecification> iter = tTestSpecification.iterator(); iter.hasNext();)
			{
				TTestSpecification loadedItem = iter.next();
				QuizQuestionMining item = new QuizQuestionMining();
				item.setQuestion(loadedItem.getTest(), question_mining, old_question_mining);
				item.setQuiz(loadedItem.getTask(), quiz_mining, old_quiz_mining);
				//Id for QuizQuestion entry is a combination of the question-id and the quiz-id
				item.setId(Long.valueOf(item.getQuestion().getId()  + "010" + item.getQuiz().getId()));
				quizQuestions.put(item.getId(), item);
			}
			
			System.out.println("Generated " + quizQuestions.size() + " QuizQuestionMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return quizQuestions;
	}
	
	public static HashMap<Long, CourseScormMining> generateCourseScormMining()
	{
		HashMap<Long, CourseScormMining> courseScorms = new HashMap<Long, CourseScormMining>();
		
		try{
			for(Iterator<EComposing> iter = eComposing.iterator(); iter.hasNext();)
			{
				EComposing loadedItem = iter.next();
				if(loadedItem.getComposingType() == 1L && eComponentMap.get(loadedItem.getComponent()).getType() == 10L)
				{
					CourseScormMining item = new CourseScormMining();
					item.setCourse(loadedItem.getComposing(), course_mining, old_course_mining);
					item.setScorm(loadedItem.getComposing(), scorm_mining, old_scorm_mining);
					item.setId(loadedItem.getId());
					
					courseScorms.put(item.getId(), item);
				}
				
			}
			
			System.out.println("Generated " + courseScorms.size() + " CourseScormMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseScorms;
	}
	
	public static HashMap<Long, CourseAssignmentMining> generateCourseAssignmentMining()
	{
		HashMap<Long, CourseAssignmentMining> courseAssignments = new HashMap<Long, CourseAssignmentMining>();
		try{
			for(Iterator<EComposing> iter = eComposing.iterator(); iter.hasNext();)
			{
				EComposing loadedItem = iter.next();
				if(loadedItem.getComposingType() == 4L)
				{
					CourseAssignmentMining item = new CourseAssignmentMining();
					item.setCourse(loadedItem.getComposing(), course_mining, old_course_mining);
					item.setAssignment(loadedItem.getComposing(), assignment_mining, old_assignment_mining);
					item.setId(loadedItem.getId());
					
					courseAssignments.put(item.getId(), item);
				}
				
			}
			System.out.println("Generated " + courseAssignments.size() + " CourseAssignmentMining.");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseAssignments;
	}
	
	public static HashMap<Long, CourseResourceMining> generateCourseResourceMining()
	{
		HashMap<Long, CourseResourceMining> courseResources = new HashMap<Long, CourseResourceMining>();
		
		try{
			for(Iterator<EComposing> iter = eComposing.iterator(); iter.hasNext();)
			{
				EComposing loadedItem = iter.next();
				if(loadedItem.getComposingType() == 1L)
				{
					CourseResourceMining item = new CourseResourceMining();
					item.setCourse(loadedItem.getComposing(), course_mining, old_course_mining);
					item.setResource(loadedItem.getComposing(), resource_mining, old_resource_mining);
					item.setId(loadedItem.getId());
					
					courseResources.put(item.getId(), item);
				}
				
			}
			System.out.println("Generated " + courseResources.size() + " CourseResourceMining.");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseResources;
	}
	
	public static HashMap<Long, CourseQuizMining> generateCourseQuizMining()
	{
		HashMap<Long, CourseQuizMining> courseQuizzes = new HashMap<Long, CourseQuizMining>();
		
		try{
			for(Iterator<EComposing> iter = eComposing.iterator(); iter.hasNext();)
			{
				EComposing loadedItem = iter.next();
				if(loadedItem.getComposingType() == 1L)
				{
					CourseQuizMining item = new CourseQuizMining();
					item.setCourse(loadedItem.getComposing(), course_mining, old_course_mining);
					item.setQuiz(loadedItem.getComponent(), quiz_mining, old_quiz_mining);
					item.setId(loadedItem.getId());
					
					courseQuizzes.put(item.getId(), item);
				}
				
			}
			System.out.println("Generated " + courseQuizzes.size() + " CourseQuizMining.");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseQuizzes;
	}
	
	public static  HashMap<Long, QuizUserMining> generateQuizUserMining()
	{
		HashMap<Long, QuizUserMining> quizUsers = new HashMap<Long, QuizUserMining>();
		try{
			for(Iterator<TQtiEvalAssessment> iter = tQtiEvalAssessment.iterator(); iter.hasNext();)
			{
				TQtiEvalAssessment loadedItem = iter.next();
				QuizUserMining item = new QuizUserMining();
				item.setId(loadedItem.getId());
				item.setCourse(loadedItem.getComponent(), course_mining, old_course_mining);
				item.setUser(loadedItem.getCandidate(), user_mining, old_user_mining);
				item.setQuiz(loadedItem.getAssessment(), quiz_mining, old_quiz_mining);
				item.setFinalgrade(loadedItem.getEvaluatedScore());
				item.setTimemodified(TimeConverter.getTimestamp(loadedItem.getLastInvocation()));
			}
			System.out.println("Generated " + quizUsers.size() + " QuizUserMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return quizUsers;
	}
	
	public static HashMap<Long, CourseUserMining> generateCourseUserMining()
	{
		HashMap<Long, CourseUserMining> courseUsers = new HashMap<Long, CourseUserMining>();
		try{
		
			System.out.println("Generated " + courseUsers.size() + " CourseUserMining.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseUsers;
	}
	
	public static HashMap<Long, CourseWikiMining> generateCourseWikiMining()
	{
		HashMap<Long, CourseWikiMining> courseWikis = new HashMap<Long, CourseWikiMining>();
		try{
		
			System.out.println("Generated " + courseWikis.size() + " CourseWikiMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseWikis;
	}
	
	public static HashMap<Long, DegreeCourseMining> generateDegreeCourseMining()
	{
		HashMap<Long, DegreeCourseMining> degreeCourses = new HashMap<Long, DegreeCourseMining>();
		
		try{
			System.out.println("Generated " + degreeCourses.size() + " DegreeCourseMining.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return degreeCourses;
	}
	
	public static HashMap<Long, DepartmentDegreeMining> generateDepartmentDegreeMining()
	{
		HashMap<Long, DepartmentDegreeMining> departmentDegrees = new HashMap<Long, DepartmentDegreeMining>();
		try{
			System.out.println("Generated " + departmentDegrees.size() + " DepartmentDegreeMining.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return departmentDegrees;
	}
	
	public static HashMap<Long, GroupUserMining> generateGroupUserMining()
	{
		HashMap<Long, GroupUserMining> groupUsers = new HashMap<Long, GroupUserMining>();
		
		try{
			System.out.println("Generated " + groupUsers.size() + " GroupUserMining.");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return groupUsers;
	}
	
	public static HashMap<Long, CourseGroupMining> generateCourseGroupMining()
	{
		HashMap<Long, CourseGroupMining> courseGroups = new HashMap<Long, CourseGroupMining>();
		try{
			System.out.println("Generated " + courseGroups.size() + " CourseGroupMining.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseGroups;
	}
	
	public static HashMap<Long, CourseForumMining> generateCourseForumMining()
	{
		HashMap<Long, CourseForumMining> courseForum = new HashMap<Long, CourseForumMining>();
		try{
			
			System.out.println("Generated " + courseForum.size() + " CourseForumMining.");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return courseForum;
	}
	
	//Generators for logs
	

	
	public static HashMap<Long, ForumLogMining> generateForumLogMining()
	{
		HashMap<Long, ForumLogMining> forumLogs = new HashMap<Long, ForumLogMining>();
		
		try{
			HashMap<Long, EComposing> ecMap = new HashMap<Long, EComposing>();
			for(Iterator<EComposing> iter = eComposing.iterator(); iter.hasNext();)
			{
				EComposing ec = iter.next();
				ecMap.put(ec.getComponent(), ec);
			}
			for(Iterator<ForumEntry> iter = forumEntry.iterator(); iter.hasNext();)
			{
				ForumEntry loadedItem = iter.next();
				ForumLogMining item = new ForumLogMining();
				item.setId(loadedItem.getId());
				item.setForum(loadedItem.getForum(), forum_mining, old_forum_mining);
				item.setUser(loadedItem.getLastUpdater(), user_mining, old_user_mining);
				item.setSubject(loadedItem.getTitle());
				item.setMessage(loadedItem.getContent());
				item.setAction("Post");
				
				if(ecMap.get(loadedItem.getForum()) != null)
					item.setCourse(ecMap.get(loadedItem.getForum()).getComponent(), course_mining, old_course_mining);
				
				if(item.getUser() != null && item.getForum() != null )
					forumLogs.put(item.getId(), item);
			}
			
			for(Iterator<ForumEntryState> iter = forumEntryState.iterator(); iter.hasNext();)
			{
				ForumEntryState loadedItem = iter.next();
				ForumLogMining item = new ForumLogMining();
				item.setId(loadedItem.getId().hashCode());
				item.setForum(loadedItem.getForum(), forum_mining, old_forum_mining);
				item.setUser(loadedItem.getUser(), user_mining, old_user_mining);
				item.setAction("View");
				if(forumLogs.get(loadedItem.getEntry()) != null)
						item.setSubject(forumLogs.get(loadedItem.getEntry()).getSubject());
				item.setMessage("");
				
				if(ecMap.get(loadedItem.getForum()) != null)
					item.setCourse(ecMap.get(loadedItem.getForum()).getComponent(), course_mining, old_course_mining);
				
				if(item.getUser() != null && item.getForum() != null )
					forumLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + forumLogs.size() + " ForumLogMining.");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return forumLogs;
	}
	
	
	public static HashMap<Long, WikiLogMining> generateWikiLogMining()
	{
		HashMap<Long, WikiLogMining> wikiLogs = new HashMap<Long, WikiLogMining>();
		try{
			for(Iterator<WikiEntry> iter = wikiEntry.iterator(); iter.hasNext();)
			{
				WikiEntry loadedItem = iter.next();
				WikiLogMining item = new WikiLogMining();
				item.setId(loadedItem.getId());
				item.setWiki(loadedItem.getComponent(), wiki_mining, old_wiki_mining);
				
				wikiLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + wikiLogs.size() + " WikiLogMining.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return wikiLogs;
	}
	
	
	public static HashMap<Long, CourseLogMining> generateCourseLogMining()
	{
		HashMap<Long, CourseLogMining> courseLogs = new HashMap<Long, CourseLogMining>();
		try{
			for(Iterator<PortfolioLog> iter = portfolioLog.iterator(); iter.hasNext();)
			{
				PortfolioLog loadedItem = iter.next();
				CourseLogMining item = new CourseLogMining();
				item.setId(loadedItem.getId());
				item.setCourse(loadedItem.getComponent(), course_mining, old_course_mining);
				item.setUser(loadedItem.getPerson(), user_mining, old_user_mining);
				item.setAction(loadedItem.getTypeOfModification()+"");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
				
				courseLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + courseLogs.size() + " CourseLogMining.");
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return courseLogs;
	}
	
	
	public static HashMap<Long, QuestionLogMining> generateQuestionLogMining()
	{
		HashMap<Long, QuestionLogMining> questionLogs = new HashMap<Long, QuestionLogMining>();
		
		try{
			for(Iterator<TAnswerPosition> iter = tAnswerPosition.iterator(); iter.hasNext();)
			{
				TAnswerPosition loadedItem = iter.next();
				QuestionLogMining item = new QuestionLogMining();
				
				item.setUser(loadedItem.getPerson(), user_mining, old_user_mining);
				item.setQuiz(loadedItem.getTest(), quiz_mining, old_quiz_mining);
				item.setQuestion(loadedItem.getQuestion(), question_mining, old_question_mining);
				item.setCourse(loadedItem.getTest(), course_mining, old_course_mining);
				item.setId(loadedItem.getId().hashCode());
				questionLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + questionLogs.size() + " QuestionLogMining.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return questionLogs;
	}
	
	
	public static HashMap<Long, QuizLogMining> generateQuizLogMining()
	{
		HashMap<Long, QuizLogMining> quizLogs = new HashMap<Long, QuizLogMining>();
		try{
			for(Iterator<TQtiEvalAssessment> iter = tQtiEvalAssessment.iterator(); iter.hasNext();)
			{
				TQtiEvalAssessment loadedItem = iter.next();
				QuizLogMining item = new QuizLogMining();
				
				item.setId(loadedItem.getId());
				item.setCourse(loadedItem.getComponent(), course_mining, old_course_mining);
				item.setUser(loadedItem.getCandidate(), user_mining, old_user_mining);
				item.setQuiz(loadedItem.getAssessment(), quiz_mining, old_quiz_mining);
				item.setGrade(loadedItem.getEvaluatedScore());
				if(loadedItem.getEvalCount() == 0L)
					item.setAction("Try");
				else
					item.setAction("Submit");
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastInvocation()));
				
				quizLogs.put(item.getId(), item);
				
			}
			System.out.println("Generated " + quizLogs.size() + " QuizLogMining.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return quizLogs;
	}
	
	
	public static HashMap<Long, AssignmentLogMining> generateAssignmentLogMining()
	{
		HashMap<Long, AssignmentLogMining> assignmentLogs = new HashMap<Long, AssignmentLogMining>();
		try{
			HashMap<Long, EComposing> ecMap = new HashMap<Long, EComposing>();
			for(Iterator<EComposing> iter = eComposing.iterator(); iter.hasNext();)
			{
				EComposing ec = iter.next();
				ecMap.put(ec.getComponent(), ec);
			}
			for(Iterator<ExercisePersonalised> iter = exercisePersonalised.iterator(); iter.hasNext();)
			{
				ExercisePersonalised loadedItem = iter.next();
				AssignmentLogMining item = new AssignmentLogMining();
				
				item.setAssignment(loadedItem.getExercise(), assignment_mining, old_assignment_mining);
				item.setUser(loadedItem.getUser(), user_mining, old_user_mining);
				item.setGrade(loadedItem.getPoints());
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getUploadDate()));
				if(ecMap.get(loadedItem.getExercise()) != null)
					item.setCourse(ecMap.get(loadedItem.getExercise()).getId(), course_mining, old_course_mining);
				
				item.setId(loadedItem.getId().hashCode());
				
				if(item.getCourse() != null && item.getAssignment() != null && item.getUser() != null)
					assignmentLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + assignmentLogs.size() + " AssignmentLogMining.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return assignmentLogs;
	}
	
	
	public static HashMap<Long, ScormLogMining> generateScormLogMining()
	{
		HashMap<Long, ScormLogMining> scormLogs = new HashMap<Long, ScormLogMining>();
		try {
			for(Iterator<ScormSessionTimes> iter = scormSessionTimes.iterator(); iter.hasNext();)
			{
				ScormSessionTimes loadedItem = iter.next();
				ScormLogMining item = new ScormLogMining();
				item.setUser(loadedItem.getPerson(), user_mining, old_user_mining);
				try{
					if(loadedItem.getScore() != null)
						item.setGrade(Double.parseDouble(loadedItem.getScore()));
					item.setScorm(loadedItem.getComponent(), scorm_mining, old_scorm_mining);
					item.setAction(loadedItem.getStatus());
					item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
					item.setId(loadedItem.getId().hashCode());
					
					scormLogs.put(item.getId(), item);
				}catch(NullPointerException e)
				{
					System.out.println("Couldn't parse grade: "+ loadedItem.getScore());
				}
					
			}
			System.out.println("Generated " + scormLogs.size() + " ScormLogMining.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return scormLogs;
	}
	
	
	public static HashMap<Long, ResourceLogMining> generateResourceLogMining()
	{
		HashMap<Long, ResourceLogMining> resourceLogs = new HashMap<Long, ResourceLogMining>();
		try{
			for(Iterator<BiTrackContentImpressions> iter = biTrackContentImpressions.iterator(); iter.hasNext();)
			{
				BiTrackContentImpressions loadedItem = iter.next();
				ResourceLogMining item = new ResourceLogMining();
				item.setResource(loadedItem.getContent(), resource_mining, old_resource_mining);
				item.setUser(loadedItem.getUser(), user_mining, old_user_mining);
				item.setCourse(loadedItem.getContainer(), course_mining, old_course_mining);
				item.setAction("View");
				item.setDuration(1L);
				BiTrackContentImpressionsPK id = new BiTrackContentImpressionsPK(loadedItem.getCharacteristic(), loadedItem.getContent(), loadedItem.getDayOfAccess(), loadedItem.getContainer(), loadedItem.getUser());
				item.setId(id.hashCode());
				
				resourceLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + resourceLogs.size() + " ResourceLogMining.");
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return resourceLogs;
	}
	
	
	public static HashMap<Long, ChatLogMining> generateChatLogMining()
	{
		HashMap<Long, ChatLogMining> chatLogs = new HashMap<Long, ChatLogMining>();
		try{
			for(Iterator<ChatProtocol> iter = chatProtocol.iterator(); iter.hasNext();)
			{
				ChatProtocol loadedItem = iter.next();
				ChatLogMining item = new ChatLogMining();
				
				item.setId(loadedItem.getId());
				item.setChat(loadedItem.getChatroom(), chat_mining, old_chat_mining);
				item.setUser(loadedItem.getPerson(), user_mining, old_user_mining);
				item.setMessage(loadedItem.getChatSource());
				item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
	
				chatLogs.put(item.getId(), item);
			}
			System.out.println("Generated " + chatLogs.size() + " ChatLogMining.");
		
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
	        platformGroupSpecification = new ArrayList<PlatformGroupSpecification>();
	        platformGroup = new ArrayList<PlatformGroup>();
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
	    	System.out.println("Reading server log...");
	    	BufferedReader input =  new BufferedReader(new FileReader("c://users//s.schwarzrock//desktop//clixDB.txt"));
	    	String line = null;
    		while (( line = input.readLine()) != null)
	    	{
    			String[] pre = new String[20];
    			int pos = 0;
    			while(line.indexOf("ä$") > -1)
    				{
    					
    					pre[pos] = line.substring(0, line.indexOf("ä$"));
    					line = line.substring(line.indexOf("ä$") +2);
    					pos++;
    				}
    				pre[pos] = line;
    			
    			if(pre[0].equals("BiTrackContentImpressions"))
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
    			else if(pre[0].equals("ChatProtocol"))
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
    			else if(pre[0].equals("Chatroom"))
    			{
    				Chatroom item = new Chatroom();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				item.setLastUpdated(sa[2]);
    				item.setTitle(sa[3]);
    				
    				chatroom.add(item);
    			}
    			else if(pre[0].equals("EComponent"))
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
    			else if(pre[0].equals("EComponentType"))
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
    			else if(pre[0].equals("EComposing"))
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
    			
    			else if(pre[0].equals("ExercisePersonalised"))
    			{
    				ExercisePersonalised item = new ExercisePersonalised();
    				String[] sa = pre;
    				if(!sa[3].equals("null"))
    					item.setCommunity(Long.valueOf(sa[3]));
    				if(!sa[4].equals("null"))
    					item.setExercise(Long.valueOf(sa[4]));
    				if(!sa[5].equals("null"))
    					item.setExerciseSheet(Long.valueOf(sa[5]));
    				if(!sa[6].equals("null"))
    					item.setPoints(Long.valueOf(sa[6]));
    				
    				item.setUploadDate(sa[2]);
    				if(!sa[7].equals("null"))
    					item.setUser(Long.valueOf(sa[7]));
    				
    				ExercisePersonalisedPK id = new ExercisePersonalisedPK();
    				id.setCommunity(item.getCommunity());
    				id.setExercise(item.getExercise());
    				id.setExerciseSheet(item.getExerciseSheet());
    				id.setUser(item.getUser());
    				item.setId(id);
    				
    				exercisePersonalised.add(item);
    			}
    			else if(pre[0].equals("ForumEntry"))
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
    			else if(pre[0].equals("ForumEntryState"))
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
    			else if(pre[0].equals("Person"))
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
    					item.setSex(Long.valueOf(sa[5]));
    				
    				person.add(item);
    			}
    			else if(pre[0].equals("PlatformGroup"))
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
    			else if(pre[0].equals("PlatformGroupSpecification"))
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
    			else if(pre[0].equals("PortfolioLog"))
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
    			else if(pre[0].equals("ScormSessionTimes"))
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
    			else if(pre[0].equals("T2Task"))
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
    			else if(pre[0].equals("TAnswerPosition"))
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
    			else if(pre[0].equals("TeamExerciseComposingExt"))
    			{
    				TeamExerciseComposingExt item = new TeamExerciseComposingExt();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[3].equals("null"))
    					item.seteComposingId(Long.valueOf(sa[3]));
    				item.setSubmissionDeadline(sa[2]);
    				
    				teamExerciseComposingExt.add(item);
    			}
    			else if(pre[0].equals("TeamExerciseGroup"))
    			{
       				TeamExerciseGroup item = new TeamExerciseGroup();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setComponent(Long.valueOf(sa[2]));
    				
    				teamExerciseGroup.add(item);
    			}
    			else if(pre[0].equals("TeamExerciseGroupMember"))
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
    			else if(pre[0].equals("TGroupFullSpecification"))
    			{
    				TGroupFullSpecification item = new TGroupFullSpecification();
    				String[] sa = pre;
    				if(!sa[1].equals("null"))
    					item.setGroup(Long.valueOf(sa[1]));
    				if(!sa[2].equals("null"))
    					item.setPerson(Long.valueOf(sa[2]));
    				
    				tGroupFullSpecification.add(item);
    			}
    			else if(pre[0].equals("TQtiContent"))
    			{
    				TQtiContent item = new TQtiContent();
    				String[] sa = pre;
    				item.setId(Long.valueOf(sa[1]));
    				item.setCreated(sa[2]);
    				item.setLastUpdated(sa[3]);
    				item.setName(sa[4]);
    				
    				tQtiContent.add(item);
    			}
    			else if(pre[0].equals("TQtiEvalAssessment"))
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
    			else if(pre[0].equals("TTestSpecification"))
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
    			else if(pre[0].equals("WikiEntry"))
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
    		
	        for(int i = 0; i < eComponent.size(); i++)
	        {
	        	eComponentMap.put(eComponent.get(i).getId(), eComponent.get(i));
	        }
	        System.out.println("Loaded "+biTrackContentImpressions.size()+" biTrackContentImpressions");
	        System.out.println("Loaded "+chatProtocol.size()+" chatProtocols");
	        System.out.println("Loaded "+chatroom.size()+" chatroom");
	        System.out.println("Loaded "+eComponent.size()+" eComponent");
	        System.out.println("Loaded "+eComponentType.size()+" eComponentType");
	        System.out.println("Loaded "+eComposing.size()+" eComposing");
	        System.out.println("Loaded "+exercisePersonalised.size()+" exercisePersonalised");
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
        al.addAll(exercisePersonalised);
        al.addAll(forumEntry);
        al.addAll(forumEntryState);
        al.addAll(person);
        al.addAll(platformGroupSpecification);
        al.addAll(platformGroup);
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
