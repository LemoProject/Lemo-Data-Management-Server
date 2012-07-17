package de.lemo.dms.connectors.clix2010;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.lemo.dms.connectors.clix2010.clixDBClass.BiTrackContentImpressions;
import de.lemo.dms.connectors.clix2010.clixDBClass.BiTrackContentImpressionsPK;
import de.lemo.dms.connectors.clix2010.clixDBClass.ChatProtocol;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponentType;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComposing;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponent;
import de.lemo.dms.connectors.clix2010.clixDBClass.ExercisePersonalised;
import de.lemo.dms.connectors.clix2010.clixDBClass.ForumEntry;
import de.lemo.dms.connectors.clix2010.clixDBClass.ForumEntryState;
import de.lemo.dms.connectors.clix2010.clixDBClass.Person;
import de.lemo.dms.connectors.clix2010.clixDBClass.PlatformGroup;
import de.lemo.dms.connectors.clix2010.clixDBClass.PlatformGroupSpecification;
import de.lemo.dms.connectors.clix2010.clixDBClass.PortfolioLog;
import de.lemo.dms.connectors.clix2010.clixDBClass.ScormSessionTimes;
import de.lemo.dms.connectors.clix2010.clixDBClass.ScormSessionTimesPK;
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
		
	
	
	public static void getClixData()
	{
		Long starttime = System.currentTimeMillis()/1000;
		
		Long largestId = -1L;		

		
		//Do Import
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
		ArrayList updates = new ArrayList();
		resource_mining = generateResourceMining();
		updates.addAll(resource_mining.values());
		course_mining = generateCourseMining();
		updates.addAll(course_mining.values());
		user_mining = generateUserMining();
		updates.addAll(user_mining.values());
		assignment_mining = generateAssignmentMining();
		updates.addAll(assignment_mining.values());
		forum_mining = generateForumMining();
		updates.addAll(forum_mining.values());
		group_mining = generateGroupMining();
		updates.addAll(group_mining.values());
		ServerConfigurationHardCoded.getInstance().getDBHandler().saveCollectionToDB(updates);
	}
	
	
	@SuppressWarnings("unchecked")
	private static void loadData()
	{
		 IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		//accessing DB by creating a session and a transaction using HibernateUtil
        Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
        //Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
        session.clear();
        Transaction tx = session.beginTransaction();
        
        //List<Timestamp> ts = (List<Timestamp>) session.createQuery("select max(lastmodified) from ConfigMining x order by x.id asc");
        Long readingFromTimestamp = 0L;
        //if(ts != null && ts.size() > 0)
        //	readingFromTimestamp = ts.get(ts.size()-1).getTime();
    

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
        
        Query eCompo = session.createQuery("from EComposing x order by x.id asc");
        eComposing = eCompo.list();	        
        System.out.println("EComposing tables: " + eComposing.size());  
        
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

    	
	}
	
	private void loadData(Long start, Long end)
	{
		//accessing DB by creating a session and a transaction using HibernateUtil
        Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
        //Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
        session.clear();
        Transaction tx = session.beginTransaction();
        
        
	}
	
	//Generators for objects
	
	public static HashMap<Long, ResourceMining> generateResourceMining()
	{
		HashMap<Long, EComponentType> eCTypes = new HashMap<Long, EComponentType>();
		for(Iterator<EComponentType> iter1 = eComponentType.iterator(); iter1.hasNext();)
		{
			EComponentType loadedType = iter1.next();
			if(loadedType.getCharacteristicId() == 1L)
				eCTypes.put(iter1.next().getComponent(), loadedType);
		}
		HashMap<Long, ResourceMining> resources = new HashMap<Long, ResourceMining>();
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
		return resources;
	}
	
	public static HashMap<Long, CourseMining> generateCourseMining()
	{
		HashMap<Long, CourseMining> courses = new HashMap<Long, CourseMining>();
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
		return courses;
	}
	
	public static HashMap<Long, UserMining> generateUserMining()
	{
		HashMap<Long, UserMining> users = new HashMap<Long, UserMining>();
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
		return users;
	}
	
	public static HashMap<Long, AssignmentMining> generateAssignmentMining()
	{
		HashMap<Long, AssignmentMining> assignments = new HashMap<Long, AssignmentMining>();
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
		return assignments;
	}
	
	public HashMap<Long, QuizMining> generateQuizMining()
	{
		HashMap<Long, QuizMining> quizzes = new HashMap<Long, QuizMining>();
		
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
	

	
	public HashMap<Long, RoleMining> generateRoleMining()
	{
		HashMap<Long, RoleMining> roles = new HashMap<Long, RoleMining>();
		
		for(Iterator<PlatformGroup> iter = platformGroup.iterator(); iter.hasNext();)
		{
			PlatformGroup loadedItem = iter.next();
			RoleMining item = new RoleMining();
			item.setId(loadedItem.getTypeId());
			
		}
		System.out.println("Generated " + roles.size() + " RoleMining.");
		return roles;
	}

	
	public static HashMap<Long, GroupMining> generateGroupMining()
	{
		HashMap<Long, GroupMining> groups = new HashMap<Long, GroupMining>();
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
		return groups;
	}

	
	public static HashMap<Long, WikiMining> generateWikiMining()
	{
		HashMap<Long, WikiMining> wikis = new HashMap<Long, WikiMining>();
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
		return wikis;
	}

	
	public HashMap<Long, DepartmentMining> generateDepartmentMining()
	{
		HashMap<Long, DepartmentMining> departments = new HashMap<Long, DepartmentMining>();
		
		
		return departments;
	}

	
	public HashMap<Long, DegreeMining> generateDegreeMining()
	{
		HashMap<Long, DegreeMining> degrees = new HashMap<Long, DegreeMining>();
		
		
		return degrees;
	}

	
	public HashMap<Long, QuestionMining> generateQuestionMining()
	{
		HashMap<Long, QuestionMining> questions = new HashMap<Long, QuestionMining>();	
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
		
		
		return questions;
	}

	
	public HashMap<Long, ScormMining> generateScormMining()
	{
		HashMap<Long, ScormMining> scorms = new HashMap<Long, ScormMining>();		
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
		
		return scorms;
	}
	
	//Generators for relationships
	
	public HashMap<Long, QuizQuestionMining> generateQuizQuestionMining()
	{
		HashMap<Long, QuizQuestionMining> quizQuestions = new HashMap<Long, QuizQuestionMining>();
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
		
		return quizQuestions;
	}
	
	public HashMap<Long, CourseScormMining> generateCourseScormMining()
	{
		HashMap<Long, CourseScormMining> courseScorms = new HashMap<Long, CourseScormMining>();
		
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
		
		
		return courseScorms;
	}
	
	public HashMap<Long, CourseAssignmentMining> generateCourseAssignmentMining()
	{
		HashMap<Long, CourseAssignmentMining> courseAssignments = new HashMap<Long, CourseAssignmentMining>();
		
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
		
		return courseAssignments;
	}
	
	public HashMap<Long, CourseResourceMining> generateCourseResourceMining()
	{
		HashMap<Long, CourseResourceMining> courseResources = new HashMap<Long, CourseResourceMining>();
		
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
		
		return courseResources;
	}
	
	public HashMap<Long, CourseQuizMining> generateCourseQuizMining()
	{
		HashMap<Long, CourseQuizMining> courseQuizzes = new HashMap<Long, CourseQuizMining>();
		
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
		
		return courseQuizzes;
	}
	
	public HashMap<Long, QuizUserMining> generateQuizUserMining()
	{
		HashMap<Long, QuizUserMining> quizUsers = new HashMap<Long, QuizUserMining>();
		
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
		return quizUsers;
	}
	
	public HashMap<Long, CourseUserMining> generateCourseUserMining()
	{
		HashMap<Long, CourseUserMining> courseUsers = new HashMap<Long, CourseUserMining>();
		
		
		
		return courseUsers;
	}
	
	public HashMap<Long, CourseWikiMining> generateCourseWikiMining()
	{
		HashMap<Long, CourseWikiMining> courseWikis = new HashMap<Long, CourseWikiMining>();
		
		
		
		return courseWikis;
	}
	
	public HashMap<Long, DegreeCourseMining> generateDegreeCourseMining()
	{
		HashMap<Long, DegreeCourseMining> degreeCourses = new HashMap<Long, DegreeCourseMining>();
		
		
		return degreeCourses;
	}
	
	public HashMap<Long, DepartmentDegreeMining> generateDepartmentDegreeMining()
	{
		HashMap<Long, DepartmentDegreeMining> departmentDegrees = new HashMap<Long, DepartmentDegreeMining>();
		
		
		return departmentDegrees;
	}
	
	public HashMap<Long, GroupUserMining> generateGroupUserMining()
	{
		HashMap<Long, GroupUserMining> groupUsers = new HashMap<Long, GroupUserMining>();
		
		
		return groupUsers;
	}
	
	public HashMap<Long, CourseGroupMining> generateCourseGroupMining()
	{
		HashMap<Long, CourseGroupMining> courseGroups = new HashMap<Long, CourseGroupMining>();
		
		
		return courseGroups;
	}
	
	public HashMap<Long, CourseForumMining> generateCourseForumMining()
	{
		HashMap<Long, CourseForumMining> courseForum = new HashMap<Long, CourseForumMining>();
		
		
		return courseForum;
	}
	
	//Generators for logs
	

	
	public HashMap<Long, ForumLogMining> generateForumLogMining()
	{
		HashMap<Long, ForumLogMining> forumLogs = new HashMap<Long, ForumLogMining>();
		
		for(Iterator<ForumEntry> iter = forumEntry.iterator(); iter.hasNext();)
		{
			ForumEntry loadedItem = iter.next();
			ForumLogMining item = new ForumLogMining();
			item.setForum(loadedItem.getForum(), forum_mining, old_forum_mining);
			item.setUser(loadedItem.getLastUpdater(), user_mining, old_user_mining);
			item.setSubject(loadedItem.getTitle());
			item.setMessage(loadedItem.getContent());
			
			forumLogs.put(item.getId(), item);
		}
		
		return forumLogs;
	}
	
	
	public HashMap<Long, WikiLogMining> generateWikiLogMining()
	{
		HashMap<Long, WikiLogMining> wikiLogs = new HashMap<Long, WikiLogMining>();
		
		for(Iterator<WikiEntry> iter = wikiEntry.iterator(); iter.hasNext();)
		{
			WikiEntry loadedItem = iter.next();
			WikiLogMining item = new WikiLogMining();
			item.setId(loadedItem.getId());
			item.setWiki(loadedItem.getComponent(), wiki_mining, old_wiki_mining);
			
			wikiLogs.put(item.getId(), item);
		}
		
		return wikiLogs;
	}
	
	
	public HashMap<Long, CourseLogMining> generateCourseLogMining()
	{
		HashMap<Long, CourseLogMining> courseLogs = new HashMap<Long, CourseLogMining>();
		
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
		
		return courseLogs;
	}
	
	
	public HashMap<Long, QuestionLogMining> generateQuestionLogMining()
	{
		HashMap<Long, QuestionLogMining> questionLogs = new HashMap<Long, QuestionLogMining>();
		
		for(Iterator<TAnswerPosition> iter = tAnswerPosition.iterator(); iter.hasNext();)
		{
			TAnswerPosition loadedItem = iter.next();
			QuestionLogMining item = new QuestionLogMining();
			
			item.setUser(loadedItem.getPerson(), user_mining, old_user_mining);
			item.setQuiz(loadedItem.getTest(), quiz_mining, old_quiz_mining);
			item.setQuestion(loadedItem.getQuestion(), question_mining, old_question_mining);
			item.setCourse(loadedItem.getTest(), course_mining, old_course_mining);
			
			questionLogs.put(item.getId(), item);
		}
		
		return questionLogs;
	}
	
	
	public HashMap<Long, QuizLogMining> generateQuizLogMining()
	{
		HashMap<Long, QuizLogMining> quizLogs = new HashMap<Long, QuizLogMining>();
		
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
		
		return quizLogs;
	}
	
	
	public HashMap<Long, AssignmentLogMining> generateAssignmentLogMining()
	{
		HashMap<Long, AssignmentLogMining> assignmentLogs = new HashMap<Long, AssignmentLogMining>();
		
		for(Iterator<ExercisePersonalised> iter = exercisePersonalised.iterator(); iter.hasNext();)
		{
			ExercisePersonalised loadedItem = iter.next();
			AssignmentLogMining item = new AssignmentLogMining();
			
			item.setAssignment(loadedItem.getExercise(), assignment_mining, old_assignment_mining);
			item.setUser(loadedItem.getUser(), user_mining, old_user_mining);
			item.setGrade(loadedItem.getPoints());
			item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getUploadDate()));
			
			assignmentLogs.put(item.getId(), item);
		}
		
		return assignmentLogs;
	}
	
	
	public HashMap<Long, ScormLogMining> generateScormLogMining()
	{
		HashMap<Long, ScormLogMining> scormLogs = new HashMap<Long, ScormLogMining>();
		
		for(Iterator<ScormSessionTimes> iter = scormSessionTimes.iterator(); iter.hasNext();)
		{
			ScormSessionTimes loadedItem = iter.next();
			ScormLogMining item = new ScormLogMining();
			item.setUser(loadedItem.getPerson(), user_mining, old_user_mining);
			item.setScorm(loadedItem.getComponent(), scorm_mining, old_scorm_mining);
			item.setGrade(Long.valueOf(loadedItem.getScore()));
			item.setAction(loadedItem.getStatus());
			item.setTimestamp(TimeConverter.getTimestamp(loadedItem.getLastUpdated()));
//			ScormSessionTimesPK id = new ScormSessionTimesPK(loadedItem.getComponent(), loadedItem.getPerson());
	//		item.setId(id.hashCode());
			
			scormLogs.put(item.getId(), item);
			
		}
		
		return scormLogs;
	}
	
	
	public HashMap<Long, ResourceLogMining> generateResourceLogMining()
	{
		HashMap<Long, ResourceLogMining> resourceLogs = new HashMap<Long, ResourceLogMining>();
		
		for(Iterator<BiTrackContentImpressions> iter = biTrackContentImpressions.iterator(); iter.hasNext();)
		{
			BiTrackContentImpressions loadedItem = iter.next();
			ResourceLogMining item = new ResourceLogMining();
			item.setResource(loadedItem.getContent(), resource_mining, old_resource_mining);
			item.setUser(loadedItem.getUser(), user_mining, old_user_mining);
			item.setCourse(loadedItem.getContainer(), course_mining, old_course_mining);
			item.setAction("View");
			item.setDuration(1L);
			//BiTrackContentImpressionsPK id = new BiTrackContentImpressionsPK(loadedItem.getCharacteristic(), loadedItem.getContent(), loadedItem.getDayOfAccess(), loadedItem.getContainer(), loadedItem.getUser());
			//item.setId(id.hashCode());
			
			resourceLogs.put(item.getId(), item);
		}
		
		return resourceLogs;
	}
	
	
	public HashMap<Long, ChatLogMining> generateChatLogMining()
	{
		HashMap<Long, ChatLogMining> chatLogs = new HashMap<Long, ChatLogMining>();
		
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
		
		return chatLogs;
	}
	
	

	

}
