package de.lemo.dms.connectors.moodleNumericId;

import de.lemo.dms.core.Clock;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

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
/** The main class of the extraction process. 
 * Inherit from this class to make an extract class for a specific LMS. 
 * Contains bundle of static fields as container for LMS objects,
 * which are used for linking the tables. */
public abstract class ExtractAndMap{

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
	

	
//	@SuppressWarnings("unchecked")
	/** A list of objects used for submitting them to the DB. */
	static List<Collection<?>> updates;
	/** A list of timestamps of the previous runs of the extractor. */	
	static List<Timestamp> config_mining_timestamp;
//output helper
//	static String msg;
	
//timestamp variables
//	static long readingtimestamp;
	/** Designates which entries should be read from the LMS Database during the process.  */
	static long starttime;
	
	/**Database-handler**/
	static IDBHandler dbHandler;
	
	/** value of the highest user-id in the dataset. Used for creating new numeric ids **/
	static long largestId;
	
	/**Logger **/
	private static Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();
	
	Clock c;

	/** Session object for the mining DB access. */
	//static Session mining_session;
	
/**
 * Starts the extraction process by calling getLMS_tables() and saveMining_tables(). 
 * A timestamp can be given as optional argument. 
 * When the argument is used the extraction begins after that timestamp. 
 * When no argument is given the program starts with the timestamp of the last run.
 * @param args Optional arguments for the process. Used for the selection of the ExtractAndMap Implementation and timestamp when the extraction should start.
 * **/	
	public void start(String[] args, DBConfigObject sourceDBConf) {
		
		dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		c = new Clock();
		starttime = System.currentTimeMillis()/1000;
		
		Session session = dbHandler.getMiningSession();
		
//get the status of the mining DB; load timestamp of last update and objects needed for associations		
		long readingtimestamp = getMiningInitial();
		
		System.out.println("Initialized database in " + c.getAndReset());
//default call without parameter	        
		if(args.length == 1)
		{	    	
			//get the needed tables from LMS DB
			c.reset();
			getLMStables(sourceDBConf, readingtimestamp);
			System.out.println("Loaded data from source in " + c.getAndReset());
			
			//create and write the mining database tables
			saveMiningTables();	
		}
//call with parameter timestamp
		else{
			if(args[1].matches("[0-9]+"))
			{
				readingtimestamp = Long.parseLong(args[1]);
				long readingtimestamp2 = Long.parseLong(args[1]) + 172800;				
				long currenttimestamp = starttime;
				logger.info("starttime:" + currenttimestamp);
				logger.info("parameter:" + readingtimestamp);

//first read & save LMS DB tables from 0 to starttime for timestamps which are not set(which are 0)				
				if(config_mining_timestamp.get(0) == null){
					c.reset();
					getLMStables(sourceDBConf, 0, readingtimestamp);	
					System.out.println("Loaded data from source in " + c.getAndReset());
					//create and write the mining database tables
					saveMiningTables();		
				}
				
//read & save LMS DB in steps of 2 days
				for(long looptimestamp = readingtimestamp -1; looptimestamp < currenttimestamp;)
				{
					logger.info("looptimestamp:" + looptimestamp);
					c.reset();
					getLMStables(sourceDBConf, looptimestamp +1, readingtimestamp2);
					System.out.println("Loaded data from source in " + c.getAndReset());
					looptimestamp += 172800;
					readingtimestamp2 += 172800;
					logger.info("currenttimestamp:" + currenttimestamp);
					saveMiningTables();	
					prepareMiningData();
					clearMiningTables();
				}
			}
			else{
				//Fehlermeldung Datenformat
				logger.info("wrong data format in parameter:" + args[1]);
			}
		}
	
	    //calculate running time of extract process
		//Transaction tx = mining_session.beginTransaction();
		long endtime = System.currentTimeMillis()/1000;		
	    ConfigMining config = new ConfigMining();
	    config.setLastmodified(System.currentTimeMillis());
	    config.setElapsed_time((endtime) - (starttime));	
	    config.setLargestId(largestId);
	    config.setPlatform("Moodle19_numeric");
	    dbHandler.saveToDB(session, config);
	    //mining_session.saveOrUpdate(config);

	    //tx.commit();
	    //mining_session.clear();	    
		logger.info("Elapsed time: " + (endtime - starttime) + "s");
		dbHandler.closeSession(session);
	}

	/**
	 * Reads the Mining Database.
	 * Initial informations needed to start the process of updating are collected here.
	 * The Timestamp of the last run of the extractor is read from the config table
	 * and the objects which might been needed to associate are read and saved here.
	 * @return The timestamp of the last run of the extractor. If this is the first run it will be set 0.
	 * **/		
	@SuppressWarnings("unchecked")
	static public long getMiningInitial(){
					
		//open a DB connection
		//mining_session.clear();
		Session session = dbHandler.getMiningSession();
		
		config_mining_timestamp = (List<Timestamp>) dbHandler.getMiningSession().createQuery("select max(lastmodified) from ConfigMining x order by x.id asc").list();//mining_session.createQuery("select max(lastmodified) from ConfigMining x order by x.id asc").list();
		
		List<Long> l = (List<Long>) (dbHandler.performQuery(session, EQueryType.SQL, "select largestId from ConfigMining x order by x.id asc"));
		if(l != null && l.size() > 0)
			largestId = l.get(l.size()-1);
		else
			largestId = 0;
		
		if(config_mining_timestamp.get(0) == null){
			config_mining_timestamp.set(0, new Timestamp(0));
		}
		
		long readingtimestamp = config_mining_timestamp.get(0).getTime();
	
		
//load objects which are already in Mining DB for associations
		
		List<?> t;
		t = dbHandler.performQuery(session, EQueryType.HQL, "from CourseMining x order by x.id asc");//mining_session.createQuery("from CourseMining x order by x.id asc").list();
		old_course_mining = new HashMap<Long, CourseMining>();
		for(int i = 0; i < t.size(); i++)
			old_course_mining.put(((CourseMining)(t.get(i))).getId(), (CourseMining)t.get(i));
		System.out.println("Loaded " + old_course_mining.size() + " CourseMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from QuizMining x order by x.id asc");//mining_session.createQuery("from QuizMining x order by x.id asc").list();
		old_quiz_mining = new HashMap<Long, QuizMining>();
		for(int i = 0; i < t.size(); i++)
			old_quiz_mining.put(((QuizMining)(t.get(i))).getId(), (QuizMining)t.get(i));
		System.out.println("Loaded " + old_quiz_mining.size() + " QuizMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from AssignmentMining x order by x.id asc");// mining_session.createQuery("from AssignmentMining x order by x.id asc").list();
		old_assignment_mining = new HashMap<Long, AssignmentMining>();
		for(int i = 0; i < t.size(); i++)
			old_assignment_mining.put(((AssignmentMining)(t.get(i))).getId(), (AssignmentMining)t.get(i));
		System.out.println("Loaded " + old_assignment_mining.size() + " AssignmentMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ScormMining x order by x.id asc");//mining_session.createQuery("from ScormMining x order by x.id asc").list();
		old_scorm_mining = new HashMap<Long, ScormMining>();
		for(int i = 0; i < t.size(); i++)
			old_scorm_mining.put(((ScormMining)(t.get(i))).getId(), (ScormMining)t.get(i));
		System.out.println("Loaded " + old_scorm_mining.size() + " ScormMining objects from the mining database.");		
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ForumMining x order by x.id asc");//mining_session.createQuery("from ForumMining x order by x.id asc").list();
		old_forum_mining = new HashMap<Long, ForumMining>();
		for(int i = 0; i < t.size(); i++)
			old_forum_mining.put(((ForumMining)(t.get(i))).getId(), (ForumMining)t.get(i));
		System.out.println("Loaded " + old_forum_mining.size() + " ForumMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ResourceMining x order by x.id asc");//mining_session.createQuery("from ResourceMining x order by x.id asc").list();
		old_resource_mining = new HashMap<Long, ResourceMining>();
		for(int i = 0; i < t.size(); i++)
			old_resource_mining.put(((ResourceMining)(t.get(i))).getId(), (ResourceMining)t.get(i));
		System.out.println("Loaded " + old_resource_mining.size() + " ResourceMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from UserMining x order by x.id asc");//mining_session.createQuery("from UserMining x order by x.id asc").list();
		old_user_mining = new HashMap<Long, UserMining>();
		for(int i = 0; i < t.size(); i++)
			old_user_mining.put(((UserMining)(t.get(i))).getId(), (UserMining)t.get(i));
		System.out.println("Loaded " + old_user_mining.size() + " UserMining objects from the mining database.");
		
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from WikiMining x order by x.id asc");//mining_session.createQuery("from WikiMining x order by x.id asc").list();
		old_wiki_mining = new HashMap<Long, WikiMining>();
		for(int i = 0; i < t.size(); i++)
			old_wiki_mining.put(((WikiMining)(t.get(i))).getId(), (WikiMining)t.get(i));
		System.out.println("Loaded " + old_wiki_mining.size() + " WikiMining objects from the mining database.");
		
		t =dbHandler.performQuery(session, EQueryType.HQL, "from GroupMining x order by x.id asc");//mining_session.createQuery("from GroupMining x order by x.id asc").list();
		old_group_mining = new HashMap<Long, GroupMining>();
		for(int i = 0; i < t.size(); i++)
			old_group_mining.put(((GroupMining)(t.get(i))).getId(), (GroupMining)t.get(i));
		System.out.println("Loaded " + old_group_mining.size() + " GroupMining objects from the mining database.");
		
		
		t =  dbHandler.performQuery(session, EQueryType.HQL, "from QuestionMining x order by x.id asc");//mining_session.createQuery("from QuestionMining x order by x.id asc").list();
		old_question_mining = new HashMap<Long, QuestionMining>();
		for(int i = 0; i < t.size(); i++)
			old_question_mining.put(((QuestionMining)(t.get(i))).getId(), (QuestionMining)t.get(i));
		System.out.println("Loaded " + old_quiz_mining.size() + " QuestionMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from RoleMining x order by x.id asc");//mining_session.createQuery("from RoleMining x order by x.id asc").list();
		old_role_mining = new HashMap<Long, RoleMining>();
		for(int i = 0; i < t.size(); i++)
			old_role_mining.put(((RoleMining)(t.get(i))).getId(), (RoleMining)t.get(i));
		System.out.println("Loaded " + old_role_mining.size() + " RoleMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from QuizQuestionMining x order by x.id asc");//mining_session.createQuery("from QuizQuestionMining x order by x.id asc").list();
		old_quiz_question_mining = new HashMap<Long, QuizQuestionMining>();
		for(int i = 0; i < t.size(); i++)
			old_quiz_question_mining.put(((QuizQuestionMining)(t.get(i))).getId(), (QuizQuestionMining)t.get(i));
		System.out.println("Loaded " + old_quiz_question_mining.size() + " QuizQuestionMining objects from the mining database.");
		
		t =  dbHandler.performQuery(session, EQueryType.HQL, "from DepartmentMining x order by x.id asc");//mining_session.createQuery("from DepartmentMining x order by x.id asc").list();
		old_department_mining = new HashMap<Long, DepartmentMining>();
		for(int i = 0; i < t.size(); i++)
			old_department_mining.put(((DepartmentMining)(t.get(i))).getId(), (DepartmentMining)t.get(i));
		System.out.println("Loaded " + old_department_mining.size() + " DepartmentMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from DegreeMining x order by x.id asc");//mining_session.createQuery("from DegreeMining x order by x.id asc").list();
		old_degree_mining = new HashMap<Long, DegreeMining>();
		for(int i = 0; i < t.size(); i++)
			old_degree_mining.put(((DegreeMining)(t.get(i))).getId(), (DegreeMining)t.get(i));
		System.out.println("Loaded " + old_degree_mining.size() + " DegreeMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ChatMining x order by x.id asc");//mining_session.createQuery("from ChatMining x order by x.id asc").list();
		old_chat_mining = new HashMap<Long, ChatMining>();
		for(int i = 0; i < t.size(); i++)
			old_chat_mining.put(((ChatMining)(t.get(i))).getId(), (ChatMining)t.get(i));
		System.out.println("Loaded " + old_chat_mining.size() + " ChatMining objects from the mining database.");
		
		dbHandler.closeSession(session);

	    return readingtimestamp;
	}
	
	
	/**
	 * Has to read the LMS Database.
	 * It starts reading elements with timestamp readingtimestamp and higher.
	 * It is supposed to be used for frequently and small updates.
	 * For a greater Mass of Data it is suggested to use getLMS_tables(long, long);
	 * If Hibernate is used to access the LMS DB too,
	 * it is suggested to write the found tables into lists of
	 * hibernate object model classes, which have to
	 * be created as global variables in this class.
	 * So they can be used in the generate methods of this class.
	 *
	 * @param readingfromtimestamp Only elements with timestamp readingtimestamp and higher are read here.
	 * *
	 * @return the lM stables
	 */
	abstract public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp);
	
	/**
	 * Has to read the LMS Database.
	 * It reads elements with timestamp between readingfromtimestamp and readingtotimestamp.
	 * This method is used to read great DB in a step by step procedure.
	 * That is necessary for a great mass of Data when handeling an existing DB for example.
	 * If Hibernate is used to access the LMS DB too,
	 * it is suggested to write the found tables into lists of
	 * hibernate object model classes, which have to
	 * be created as global variables in this class.
	 * So they can be used in the generate methods of this class.
	 *
	 * @param readingfromtimestamp Only elements with timestamp readingfromtimestamp and higher are read here.
	 * @param readingtotimestamp Only elements with timestamp readingtotimestamp and lower are read here.
	 * *
	 * @return the lM stables
	 */
	abstract public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp, long readingtotimestamp);
	
	/**
	 * Has to clear the lists of LMS tables*.
	 */
	abstract public void clearLMStables();
	
	/**
	 * Clears the lists of mining tables.
	 * **/
	static public void clearMiningTables(){
		course_mining.clear();
		quiz_mining.clear();
		assignment_mining.clear();
		scorm_mining.clear();
		forum_mining.clear();
		resource_mining.clear();
		user_mining.clear();
		wiki_mining.clear();
		group_mining.clear();
		question_mining.clear();
		role_mining.clear();
		degree_mining.clear();
		department_mining.clear();
		chat_mining.clear();
		chat_mining.clear();
	}	
	
	/**
	 * Only for successive readings. This is meant to be done, when the gathered mining data has already 
	 * been saved and before the mining tables are cleared for the next iteration.
	 */
	static public void prepareMiningData()
	{
		old_course_mining.putAll(course_mining);
		old_quiz_mining.putAll(quiz_mining);
		old_assignment_mining.putAll(assignment_mining);
		old_scorm_mining.putAll(scorm_mining);
		old_forum_mining.putAll(forum_mining);
		old_resource_mining.putAll(resource_mining);
		old_user_mining.putAll(user_mining);
		old_wiki_mining.putAll(wiki_mining);
		old_group_mining.putAll(group_mining);
		old_question_mining.putAll(question_mining);
		old_role_mining.putAll(role_mining);
		old_degree_mining.putAll(degree_mining);
		old_department_mining.putAll(department_mining);
		old_chat_mining.putAll(chat_mining);
	}
	
	/**
	 * Generates and save the new tables for the mining DB.
	 * We call the genereate-methods for each mining table to get the new entries.
	 * At last we create a Transaction and save the new entries to the DB.
	 * **/		
	public void saveMiningTables() {

		
//generate & save new mining tables
		updates = new ArrayList<Collection<?>>();

		//generate mining tables
		if(user_mining == null){
			
			c.reset();
			course_mining = generateCourseMining();
			System.out.println("Generated " + course_mining.size() + " CourseMining entries in "+ c.getAndReset() +" s. ");
			updates.add(course_mining.values());
			quiz_mining = generateQuizMining();
			System.out.println("Generated " + quiz_mining.size() + " QuizMining entries in "+ c.getAndReset() +" s. ");
			updates.add(quiz_mining.values());
			assignment_mining = generateAssignmentMining();
			System.out.println("Generated " + assignment_mining.size() + " AssignmentMining entries in "+ c.getAndReset() +" s. ");
			updates.add(assignment_mining.values());
			scorm_mining = generateScormMining();
			System.out.println("Generated " + scorm_mining.size() + " ScormMining entries in "+ c.getAndReset() +" s. ");
			updates.add(scorm_mining.values());
			forum_mining = generateForumMining();
			System.out.println("Generated " + forum_mining.size() + " ForumMining entries in "+ c.getAndReset() +" s. ");
			updates.add(forum_mining.values());
			resource_mining = generateResourceMining();
			System.out.println("Generated " + resource_mining.size() + " ResourceMining entries in "+ c.getAndReset() +" s. ");
			updates.add(resource_mining.values());
			user_mining = generateUserMining();
			System.out.println("Generated " + user_mining.size() + " UserMining entries in "+ c.getAndReset() +" s. ");
			updates.add(user_mining.values());
			wiki_mining = generateWikiMining();
			System.out.println("Generated " + wiki_mining.size() + " WikiMining entries in "+ c.getAndReset() +" s. ");
			updates.add(wiki_mining.values());
			group_mining = generateGroupMining();
			System.out.println("Generated " + group_mining.size() + " GroupMining entries in "+ c.getAndReset() +" s. ");
			updates.add(group_mining.values());
			question_mining = generateQuestionMining();
			System.out.println("Generated " + question_mining.size() + " QuestionMining entries in "+ c.getAndReset() +" s. ");
			updates.add(question_mining.values());
			role_mining = generateRoleMining();
			System.out.println("Generated " + role_mining.size() + " RoleMining entries in "+ c.getAndReset() +" s. ");
			updates.add(role_mining.values());
			department_mining = generateDepartmentMining();
			System.out.println("Generated " + department_mining.size() + " DepartmentMining entries in "+ c.getAndReset() +" s. ");
			updates.add(department_mining.values());
			degree_mining = generateDegreeMining();
			System.out.println("Generated " + degree_mining.size() + " DegreeMining entries in "+ c.getAndReset() +" s. ");
			updates.add(degree_mining.values());
			chat_mining = generateChatMining();
			System.out.println("Generated " + chat_mining.size() + " ChatMining entries in "+ c.getAndReset() +" s. ");
			updates.add(chat_mining.values());
			
			updates.add(generateQuizQuestionMining().values());
			System.out.println("Generated " + updates.get(updates.size()-1).size() + " QuizQuestionMining entries in "+ c.getAndReset() +" s. ");
			updates.add(generateCourseQuizMining().values());
			System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseQuizMining entries in "+ c.getAndReset() +" s. ");
			updates.add(generateCourseAssignmentMining().values());
			System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseAssignmentMining entries in "+ c.getAndReset() +" s. ");
			updates.add(generateCourseScormMining().values());
			System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseScormMining entries in "+ c.getAndReset() +" s. ");
		}		
		updates.add(generateCourseUserMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseUserMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateCourseForumMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseForumMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateCourseGroupMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseGroupMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateCourseResourceMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseResourceMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateAssignmentLogMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " AssignmentLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateCourseLogMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateCourseWikiMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseWikiMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateForumLogMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " ForumLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateGroupUserMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " GroupUserMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateQuizLogMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " QuizLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateQuestionLogMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " QuestionLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateScormLogMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " ScormLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateQuizUserMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " QuizUserMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateResourceLogMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " ResourceLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateWikiLogMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " WikiLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateChatLogMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " chatLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateDepartmentDegreeMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " DepartmentDegreeMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateDegreeCourseMining().values());
		System.out.println("Generated " + updates.get(updates.size()-1).size() + " DegreeCourseMining entries in "+ c.getAndReset() +" s. ");
		

		Session session = dbHandler.getMiningSession();
		dbHandler.saveCollectionToDB(session, updates);
		dbHandler.closeSession(session);
		
		/*
		int i= new Integer(0);
		//save in the session
	    for ( Iterator<List<?>> iter = updates.iterator(); iter.hasNext();) 
	    {
	    	List<?> l = iter.next();
	    	for ( Iterator<?> iter2 = l.iterator(); iter2.hasNext();) {
	    		Object o = iter2.next();
	    		dbHandler.getSession().saveOrUpdate(o);
	    		i++;
	    		
	    	    if ( i % 60 == 0 ) {
	    	        //flush a batch of inserts and release memory:
	    	    	dbHandler.getSession().flush();
	    	    	dbHandler.getSession().clear();
	    	    }
	    	}
	    }	    
	    //hibernate session finish and close
	    tx.commit();
	    dbHandler.getSession().clear();
	    */
	    
	    
	    
	    clearLMStables();
		updates.clear();

	}	
	
//methods for create and fill the mining-table instances
	/**
	 * Has to create and fill the course_user table.
	 * This table describes which user is enrolled in which course in which timesspan.
	 * The attributes are discribed in the dokumantation of the course_user_mining class.
	 * Please use the getter and setter predifined in the course_user_mining class to fill the tables within this method.
	 * @return A list of instances of the course_user table representing class.
	 * **/	    
    abstract HashMap<Long, CourseUserMining> generateCourseUserMining();
    
	/**
	 * Has to create and fill the course_forum table.
	 * This table describes which forum is used in which course.
	 * The attributes are discribed in the dokumantation of the course_forum_mining class.
	 * Please use the getter and setter predifined in the course_forum_mining class to fill the tables within this method.
	 * @return A list of instances of the course_forum table representing class.
	 * **/	  
    abstract HashMap<Long, CourseForumMining> generateCourseForumMining();
    
	/**
	 * Has to create and fill the course table.
	 * This table describes the courses in the LMS.
	 * The attributes are discribed in the dokumantation of the course_mining class.
	 * Please use the getter and setter predifined in the course_mining class to fill the tables within this method.
	 * @return A list of instances of the course table representing class.
	 * **/	    
    abstract HashMap<Long, CourseMining> generateCourseMining();    
    
	/**
	 * Has to create and fill the course_group table.
	 * This table describes which groups are used in which courses.
	 * The attributes are discribed in the dokumantation of the course_group_mining class.
	 * Please use the getter and setter predifined in the course_group_mining class to fill the tables within this method.
	 * @return A list of instances of the course_group table representing class.
	 * **/	    
	abstract HashMap<Long, CourseGroupMining> generateCourseGroupMining();
    
	/**
	 * Has to create and fill the course_quiz table.
	 * This table describes which quiz are used in which courses.
	 * The attributes are discribed in the dokumantation of the course_quiz_mining class.
	 * Please use the getter and setter predifined in the course_quiz_mining class to fill the tables within this method.
	 * @return A list of instances of the course_quiz table representing class.
	 * **/	    
    abstract HashMap<Long, CourseQuizMining> generateCourseQuizMining();
    
	/**
	 * Has to create and fill the course_assignment table.
	 * This table describes which assignment are used in which courses.
	 * The attributes are discribed in the dokumantation of the course_assignment_mining class.
	 * Please use the getter and setter predifined in the course_assignment_mining class to fill the tables within this method.
	 * @return A list of instances of the course_assignment table representing class.
	 * **/	    
    abstract HashMap<Long, CourseAssignmentMining> generateCourseAssignmentMining();
    
	/**
	 * Has to create and fill the course_scorm table.
	 * This table describes which scorm packages are used in which courses.
	 * The attributes are discribed in the dokumantation of the course_scorm_mining class.
	 * Please use the getter and setter predifined in the course_scorm_mining class to fill the tables within this method.
	 * @return A list of instances of the course_scorm table representing class.
	 * **/	    
    abstract HashMap<Long, CourseScormMining> generateCourseScormMining();
    
	/**
	 * Has to create and fill the course_resource table.
	 * This table describes which resources are used in which courses.
	 * The attributes are discribed in the dokumantation of the course_resource_mining class.
	 * Please use the getter and setter predifined in the course_resource_mining class to fill the tables within this method.
	 * @return A list of instances of the course_resource table representing class.
	 * **/	     
    abstract HashMap<Long, CourseResourceMining> generateCourseResourceMining();
    
	/**
	 * Has to create and fill the course_log table.
	 * This table contains the actions which are done on courses.
	 * The attributes are discribed in the dokumantation of the course_log_mining class.
	 * Please use the getter and setter predifined in the course_log_mining class to fill the tables within this method.
	 * @return A list of instances of the course_log table representing class.
	 * **/	    
    abstract HashMap<Long, CourseLogMining> generateCourseLogMining();
    
	/**
	 * Has to create and fill the course_wiki table.
	 * This table describes which wikis are used in which courses.
	 * The attributes are discribed in the dokumantation of the course_wiki_mining class.
	 * Please use the getter and setter predifined in the course_wiki_mining class to fill the tables within this method.
	 * @return A list of instances of the course_wiki table representing class.
	 * **/	     
    abstract HashMap<Long, CourseWikiMining> generateCourseWikiMining();

	/**
	 * Has to create and fill the forum_log table.
	 * This table contains the actions which are done on forums.
	 * The attributes are discribed in the dokumantation of the forum_log_mining class.
	 * Please use the getter and setter predifined in the forum_log_mining class to fill the tables within this method.
	 * @return A list of instances of the forum_log table representing class.
	 * **/    
    abstract HashMap<Long, ForumLogMining> generateForumLogMining(); 
    
	/**
	 * Has to create and fill the forum table.
	 * This table describes the forums in the LMS.
	 * The attributes are discribed in the dokumantation of the forum_mining class.
	 * Please use the getter and setter predifined in the forum_mining class to fill the tables within this method.
	 * @return A list of instances of the forum table representing class.
	 * **/	    
    abstract HashMap<Long, ForumMining> generateForumMining();

	/**
	 * Has to create and fill the group_user table.
	 * This table describes which user are in which groups.
	 * The attributes are discribed in the dokumantation of the group_user_mining class.
	 * Please use the getter and setter predifined in the group_user_mining class to fill the tables within this method.
	 * @return A list of instances of the group_user table representing class.
	 * **/	    
	abstract HashMap<Long, GroupUserMining> generateGroupUserMining();
	
	/**
	 * Has to create and fill the group table.
	 * This table describes the groups in the LMS.
	 * The attributes are discribed in the dokumantation of the group_mining class.
	 * Please use the getter and setter predifined in the group_mining class to fill the tables within this method.
	 * @return A list of instances of the group table representing class.
	 * **/		
    abstract HashMap<Long, GroupMining> generateGroupMining();
    
	/**
	 * Has to create and fill the question_log table.
	 * This table contains the actions which are done on questions.
	 * The attributes are discribed in the dokumantation of the question_log_mining class.
	 * Please use the getter and setter predifined in the question_log_mining class to fill the tables within this method.
	 * @return A list of instances of the question_log table representing class.
	 * **/     
    abstract HashMap<Long, QuestionLogMining> generateQuestionLogMining();
    
	/**
	 * Has to create and fill the quiz_log table.
	 * This table contains the actions which are done on quiz.
	 * The attributes are discribed in the dokumantation of the quiz_log_mining class.
	 * Please use the getter and setter predifined in the quiz_log_mining class to fill the tables within this method.
	 * @return A list of instances of the quiz_log table representing class.
	 * **/     
    abstract HashMap<Long, QuizLogMining> generateQuizLogMining();
 
	/**
	 * Has to create and fill the assignment_log table.
	 * This table contains the actions which are done on assignment.
	 * The attributes are discribed in the dokumantation of the assignment_log_mining class.
	 * Please use the getter and setter predifined in the assignment_log_mining class to fill the tables within this method.
	 * @return A list of instances of the assignment_log table representing class.
	 * **/     
    abstract HashMap<Long, AssignmentLogMining> generateAssignmentLogMining();
    
	/**
	 * Has to create and fill the scorm_log table.
	 * This table contains the actions which are done on scorm.
	 * The attributes are discribed in the dokumantation of the scorm_log_mining class.
	 * Please use the getter and setter predifined in the scorm_log_mining class to fill the tables within this method.
	 * @return A list of instances of the scorm_log table representing class.
	 * **/     
    abstract HashMap<Long, ScormLogMining> generateScormLogMining();
    
	/**
	 * Has to create and fill the quiz_user table.
	 * This table describes which user gets which grade in which quiz.
	 * The attributes are discribed in the dokumantation of the quiz_user_mining class.
	 * Please use the getter and setter predifined in the quiz_user_mining class to fill the tables within this method.
	 * @return A list of instances of the quiz_user table representing class.
	 * **/	 
    abstract HashMap<Long, QuizUserMining> generateQuizUserMining();
    
	/**
	 * Has to create and fill the quiz table.
	 * This table describes the quiz in the LMS.
	 * The attributes are discribed in the dokumantation of the quiz_mining class.
	 * Please use the getter and setter predifined in the quiz_mining class to fill the tables within this method.
	 * @return A list of instances of the quiz table representing class.
	 * **/    
    abstract HashMap<Long, QuizMining> generateQuizMining();

	/**
	 * Has to create and fill the assignment table.
	 * This table describes the assignment in the LMS.
	 * The attributes are discribed in the dokumantation of the assignment_mining class.
	 * Please use the getter and setter predifined in the assignment_mining class to fill the tables within this method.
	 * @return A list of instances of the assignment table representing class.
	 * **/    
    abstract HashMap<Long, AssignmentMining> generateAssignmentMining();
    
	/**
	 * Has to create and fill the scorm table.
	 * This table describes the scorm packages in the LMS.
	 * The attributes are discribed in the dokumantation of the scorm_mining class.
	 * Please use the getter and setter predifined in the scorm_mining class to fill the tables within this method.
	 * @return A list of instances of the scorm table representing class.
	 * **/    
    abstract HashMap<Long, ScormMining> generateScormMining();
    
	/**
	 * Has to create and fill the quiz_question table.
	 * This table describes which question is used in which quiz.
	 * The attributes are discribed in the dokumantation of the quiz_question_mining class.
	 * Please use the getter and setter predifined in the quiz_question_mining class to fill the tables within this method.
	 * @return A list of instances of the quiz_question table representing class.
	 * **/    
    abstract HashMap<Long, QuizQuestionMining> generateQuizQuestionMining(); 

	/**
	 * Has to create and fill the question table.
	 * This table describes the question in the LMS.
	 * The attributes are discribed in the dokumantation of the question_mining class.
	 * Please use the getter and setter predifined in the question_mining class to fill the tables within this method.
	 * @return A list of instances of the question table representing class.
	 * **/     
    abstract HashMap<Long, QuestionMining> generateQuestionMining();
    
	/**
	 * Has to create and fill the resource table.
	 * This table describes the resource in the LMS.
	 * The attributes are discribed in the dokumantation of the resource_mining class.
	 * Please use the getter and setter predifined in the resource_mining class to fill the tables within this method.
	 * @return A list of instances of the resource table representing class.
	 * **/    
    abstract HashMap<Long, ResourceMining> generateResourceMining();
    
	/**
	 * Has to create and fill the resource_log table.
	 * This table contains the actions which are done on resource.
	 * The attributes are discribed in the dokumentation of the resource_log_mining class.
	 * Please use the getter and setter predifined in the resource_log_mining class to fill the tables within this method.
	 * @return A list of instances of the resource_log table representing class.
	 * **/
    abstract HashMap<Long, ResourceLogMining> generateResourceLogMining();
    
	/**
	 * Has to create and fill the user table.
	 * This table describes the user in the LMS.
	 * The attributes are discribed in the dokumantation of the user_mining class.
	 * Please use the getter and setter predifined in the user_mining class to fill the tables within this method.
	 * @return A list of instances of the user table representing class.
	 * **/
    abstract HashMap<Long, UserMining> generateUserMining();
    
	/**
	 * Has to create and fill the wiki_log table.
	 * This table contains the actions which are done on wiki.
	 * The attributes are discribed in the dokumantation of the wiki_log_mining class.
	 * Please use the getter and setter predifined in the wiki_log_mining class to fill the tables within this method.
	 * @return A list of instances of the wiki_log table representing class.
	 * **/
    abstract HashMap<Long, WikiLogMining> generateWikiLogMining(); 
    
	/**
	 * Has to create and fill the wiki table.
	 * This table describes the wiki in the LMS.
	 * The attributes are discribed in the dokumantation of the wiki_mining class.
	 * Please use the getter and setter predifined in the wiki_mining class to fill the tables within this method.
	 * @return A list of instances of the wiki table representing class.
	 * **/
	abstract HashMap<Long, WikiMining> generateWikiMining();
	
	/**
	 * Has to create and fill the role table.
	 * This table describes the roles of users in the LMS.
	 * The attributes are discribed in the dokumantation of the role_mining class.
	 * Please use the getter and setter predifined in the role_mining class to fill the tables within this method.
	 * @return A list of instances of the role table representing class.
	 * **/
	abstract HashMap<Long, RoleMining> generateRoleMining();
	
	
	/**
	 * Generate degree mining.
	 *
	 * @return the list
	 */
	abstract HashMap<Long, DegreeMining> generateDegreeMining();
	
	/**
	 * Generate department mining.
	 *
	 * @return the list
	 */
	abstract HashMap<Long, DepartmentMining> generateDepartmentMining();
	
	/**
	 * Generate department degree mining.
	 *
	 * @return the list
	 */
	abstract HashMap<Long, DepartmentDegreeMining> generateDepartmentDegreeMining();
	
	/**
	 * Generate degree course mining.
	 *
	 * @return the list
	 */
	abstract HashMap<Long, DegreeCourseMining> generateDegreeCourseMining();
	
	/**
	 * Generate chat mining.
	 *
	 * @return the list
	 */
	abstract HashMap<Long, ChatMining> generateChatMining();
	
	/**
	 * Generate chat log mining.
	 *
	 * @return the list
	 */
	abstract HashMap<Long, ChatLogMining> generateChatLogMining();
	
	
	
	
}
