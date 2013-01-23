package de.lemo.dms.connectors.moodleNumericId;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;
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
/** The main class of the extraction process. 
 * Inherit from this class to make an extract class for a specific LMS. 

 * Contains bundle of  fields as container for LMS objects,
 * which are used for linking the tables. 
 * 
 * @author Benjamin Wolf
 * @author Sebastian Schwarzrock
 * */
public abstract class ExtractAndMap{

//lists of object tables which are new found in LMS DB
	/** A map containing all CourseMining entries found in the database	 */
	protected Map<Long, CourseMining> courseMining;

	/** A map containing all PlatformMining entries found in the database	 */
	protected Map<Long, PlatformMining> platformMining;

	/** A map containing all QuizQuestionMining entries found in the database	 */
	protected Map<Long, QuizQuestionMining> quizQuestionMining;

	/** A map containing all CourseQuizMining entries found in the database	 */
	protected Map<Long, CourseQuizMining> courseQuizMining;
	
	/** A map containing all QuizMining entries found in the database	 */
	protected Map<Long, QuizMining> quizMining;

	/** A map containing all AssignmentMining entries found in the database	 */
	protected Map<Long, AssignmentMining> assignmentMining;

	/** A map containing all ScormMining entries found in the database	 */
	protected Map<Long, ScormMining> scormMining;

	/** A map containing all ForumMining entries found in the database	 */
	protected Map<Long, ForumMining> forumMining;

	/** A map containing all ResourceMining entries found in the database	 */	
	protected Map<Long, ResourceMining> resourceMining;
	
	/** A map containing all UserMining entries found in the database	 */	
	protected Map<Long, UserMining> userMining;

	/** A map containing all WikiMining entries found in the database	 */	
	protected Map<Long, WikiMining> wikiMining;

	/** A map containing all GroupMining entries found in the database	 */
	protected Map<Long, GroupMining> groupMining;
	
	/** A map containing all QuestionMining entries found in the database	 */
	protected Map<Long, QuestionMining> questionMining;
	
	/** A map containing all RoleMining entries found in the database	 */
	protected Map<Long, RoleMining> roleMining;
	
	/** A map containing all LevelMining entries found in the database	 */
	protected Map<Long, LevelMining> levelMining;

	/** A map containing all ChatMining entries found in the database	 */
	protected Map<Long, ChatMining> chatMining;
	
	/** A map containing all ChatLogMining entries found in the database	 */
	protected Map<Long, ChatLogMining> chatLogMining;
	
	/** A map containing all PlatformMining entries found in the mining-database	 */
	protected Map<Long, PlatformMining> oldPlatformMining;

	/** A map containing all CourseMining entries found in the mining-database	 */
	protected Map<Long, CourseMining> oldCourseMining;

	/** A map containing all QuizMining entries found in the mining-database	 */
	protected Map<Long, QuizMining> oldQuizMining;
		
	/** A map containing all AssignmentMining entries found in the mining-database	 */
	protected Map<Long, AssignmentMining> oldAssignmentMining;
	
	/** A map containing all ScormMining entries found in the mining-database	 */
	protected Map<Long, ScormMining> oldScormMining;
	
	/** A map containing all ForumMining entries found in the mining-database	 */
	protected Map<Long, ForumMining> oldForumMining;
	
	/** A map containing all ResourceMining entries found in the mining-database	 */
	protected Map<Long, ResourceMining> oldResourceMining;
	
	/** A map containing all UserMining entries found in the mining-database	 */
	protected Map<Long, UserMining> oldUserMining;
	
	/** A map containing all WikiMining entries found in the mining-database	 */
	protected Map<Long, WikiMining> oldWikiMining;
	
	/** A map containing all GroupMining entries found in the mining-database	 */
	protected Map<Long, GroupMining> oldGroupMining;
	
	/** A map containing all QuestionMining entries found in the mining-database	 */
	protected Map<Long, QuestionMining> oldQuestionMining;
	
	/** A map containing all RoleMining entries found in the mining-database	 */
	protected Map<Long, RoleMining> oldRoleMining;
	
	/** A map containing all QuizQuestionMining entries found in the mining-database	 */
	protected Map<Long, QuizQuestionMining> oldQuizQuestionMining;
	
	/** A map containing all CourseQuizMining entries found in the mining-database	 */
	protected Map<Long, CourseQuizMining> oldCourseQuizMining;
	
	/** A map containing all LevelMining entries found in the mining-database	 */
	protected Map<Long, LevelMining> oldLevelMining;
	
	/** A map containing all ChatMining entries found in the mining-database	 */
	protected Map<Long, ChatMining> oldChatMining;
	
	 
	/** A map containing all ChatLogMining entries found in the mining-database	 */
	protected Map<Long, ChatLogMining> oldChatLogMining;
	
	/** A list of objects used for submitting them to the DB. */
	protected List<Collection<?>> updates;
	/** A list of time stamps of the previous runs of the extractor. */	
	protected List<Timestamp> configMiningTimestamp;
 
	/** value of the highest user-id in the data set. Used for creating new numeric ids **/
	protected long largestId;
	
	/** Designates which entries should be read from the LMS Database during the process.  */
	private long starttime;
 
	private IDBHandler dbHandler;
	
	private Logger logger = Logger.getLogger(getClass());
	
	private Clock c;
	

	protected Long resourceLogMax;
	protected Long chatLogMax;
	protected Long assignmentLogMax;
	protected Long courseLogMax;
	protected Long forumLogMax;
	protected Long questionLogMax;
	protected Long quizLogMax;
	protected Long scormLogMax;
	protected Long wikiLogMax;


    private IConnector connector;

    public ExtractAndMap(IConnector connector) {
        this.connector = connector;
    }

/**
 * Starts the extraction process by calling getLMS_tables() and saveMining_tables(). 
 * A time stamp can be given as optional argument. 
 * When the argument is used the extraction begins after that time stamp. 
 * When no argument is given the program starts with the time stamp of the last run.
 * @param args Optional arguments for the process. Used for the selection of the ExtractAndMap Implementation and time stamp when the extraction should start.
 * **/	
	public void start(String[] args, DBConfigObject sourceDBConf) {
		
		dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		c = new Clock();
		starttime = System.currentTimeMillis()/1000;
		
		Session session = dbHandler.getMiningSession();
		
//get the status of the mining DB; load timestamp of last update and objects needed for associations		
		long readingtimestamp = getMiningInitial();
		
		dbHandler.saveToDB(session, new PlatformMining(connector.getPlatformId(), connector.getName(), connector.getPlattformType().toString(), connector.getPrefix()));
			
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
				if(configMiningTimestamp.get(0) == null){
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
	    config.setLastModifiedLong(System.currentTimeMillis());
	    config.setElapsedTime((endtime) - (starttime));	
	    config.setDatabaseModel("1.2");
	    config.setPlatform(connector.getPlatformId());
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
	 * The time stamp of the last run of the extractor is read from the config table
	 * and the objects which might been needed to associate are read and saved here.
	 * @return The time stamp of the last run of the extractor. If this is the first run it will be set 0.
	 * **/		
	@SuppressWarnings("unchecked")
	public long getMiningInitial(){
					
		//open a DB connection
		//mining_session.clear();
		Session session = dbHandler.getMiningSession();
		
		List<?> t;
	    t = dbHandler.performQuery(session, EQueryType.HQL, "from PlatformMining x order by x.id asc");//mining_session.createQuery("from CourseMining x order by x.id asc").list();
		oldPlatformMining = new HashMap<Long, PlatformMining>();
		if(t!=null)
			for(int i = 0; i < t.size(); i++)
			oldPlatformMining.put(((PlatformMining)(t.get(i))).getId(), (PlatformMining)t.get(i));
		System.out.println("Loaded " + oldPlatformMining.size() + " PlatformMining objects from the mining database.");
		


		platformMining = new HashMap<Long, PlatformMining>();

		
		configMiningTimestamp = (List<Timestamp>) dbHandler.getMiningSession().createQuery("select max(lastmodified) from ConfigMining x order by x.id asc").list();//mining_session.createQuery("select max(lastmodified) from ConfigMining x order by x.id asc").list();
			
		Query large = session.createQuery("select max(user.id) from UserMining user where user.platform="+ connector.getPlatformId() +"");
        if(large.list().size() > 0)
        	largestId = ((ArrayList<Long>) large.list()).get(0);
		
		if(configMiningTimestamp.get(0) == null){
			configMiningTimestamp.set(0, new Timestamp(0));
		}
		
		long readingtimestamp = configMiningTimestamp.get(0).getTime();
	
	    //load objects which are already in Mining DB for associations
		
		Query logCount = session.createQuery("select max(log.id) from resource_log where platform="+ connector.getPlatformId());
        resourceLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(resourceLogMax == null)
        	resourceLogMax = 0L;
        
        logCount = session.createSQLQuery("select max(id) from chat_log where platform="+ connector.getPlatformId());
        chatLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(chatLogMax == null)
        	chatLogMax = 0L;
        
        
        logCount = session.createSQLQuery("select max(id) from assignment_log where platform="+ connector.getPlatformId());
        assignmentLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(assignmentLogMax == null)
        	assignmentLogMax = 0L;
        
        logCount = session.createSQLQuery("select max(id) from course_log where platform="+ connector.getPlatformId());
        courseLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(courseLogMax == null)
        	courseLogMax = 0L;
        
        logCount = session.createSQLQuery("select max(id) from forum_log where platform="+ connector.getPlatformId());
        forumLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(forumLogMax == null)
        	forumLogMax = 0L;
        
        logCount = session.createSQLQuery("select max(id) from question_log where platform="+ connector.getPlatformId());
        questionLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(questionLogMax == null)
        	questionLogMax = 0L;
        
        logCount = session.createSQLQuery("select max(id) from quiz_log where platform="+ connector.getPlatformId());
        quizLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(quizLogMax == null)
        	quizLogMax = 0L;
        
        logCount = session.createSQLQuery("select max(id) from scorm_log where platform="+ connector.getPlatformId());
        scormLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(scormLogMax == null)
        	scormLogMax = 0L;
        
        logCount = session.createSQLQuery("select max(id) from wiki_log where platform="+ connector.getPlatformId());
        wikiLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(wikiLogMax == null)
        	wikiLogMax = 0L;
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from CourseMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from CourseMining x order by x.id asc").list();
		oldCourseMining = new HashMap<Long, CourseMining>();

		for(int i = 0; i < t.size(); i++)
			oldCourseMining.put(((CourseMining)(t.get(i))).getId(), (CourseMining)t.get(i));
		System.out.println("Loaded " + oldCourseMining.size() + " CourseMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from QuizMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from QuizMining x order by x.id asc").list();
		oldQuizMining = new HashMap<Long, QuizMining>();
		for(int i = 0; i < t.size(); i++)
			oldQuizMining.put(((QuizMining)(t.get(i))).getId(), (QuizMining)t.get(i));
		System.out.println("Loaded " + oldQuizMining.size() + " QuizMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from AssignmentMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");// mining_session.createQuery("from AssignmentMining x order by x.id asc").list();
		oldAssignmentMining = new HashMap<Long, AssignmentMining>();
		for(int i = 0; i < t.size(); i++)
			oldAssignmentMining.put(((AssignmentMining)(t.get(i))).getId(), (AssignmentMining)t.get(i));
		System.out.println("Loaded " + oldAssignmentMining.size() + " AssignmentMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ScormMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from ScormMining x order by x.id asc").list();
		oldScormMining = new HashMap<Long, ScormMining>();
		for(int i = 0; i < t.size(); i++)
			oldScormMining.put(((ScormMining)(t.get(i))).getId(), (ScormMining)t.get(i));
		System.out.println("Loaded " + oldScormMining.size() + " ScormMining objects from the mining database.");		
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ForumMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from ForumMining x order by x.id asc").list();
		oldForumMining = new HashMap<Long, ForumMining>();
		for(int i = 0; i < t.size(); i++)
			oldForumMining.put(((ForumMining)(t.get(i))).getId(), (ForumMining)t.get(i));
		System.out.println("Loaded " + oldForumMining.size() + " ForumMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ResourceMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from ResourceMining x order by x.id asc").list();
		oldResourceMining = new HashMap<Long, ResourceMining>();
		for(int i = 0; i < t.size(); i++)
			oldResourceMining.put(((ResourceMining)(t.get(i))).getId(), (ResourceMining)t.get(i));
		System.out.println("Loaded " + oldResourceMining.size() + " ResourceMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from UserMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from UserMining x order by x.id asc").list();
		oldUserMining = new HashMap<Long, UserMining>();
		for(int i = 0; i < t.size(); i++)
			oldUserMining.put(((UserMining)(t.get(i))).getId(), (UserMining)t.get(i));
		System.out.println("Loaded " + oldUserMining.size() + " UserMining objects from the mining database.");
		
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from WikiMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from WikiMining x order by x.id asc").list();
		oldWikiMining = new HashMap<Long, WikiMining>();
		
		for(int i = 0; i < t.size(); i++)
			oldWikiMining.put(((WikiMining)(t.get(i))).getId(), (WikiMining)t.get(i));
		System.out.println("Loaded " + oldWikiMining.size() + " WikiMining objects from the mining database.");
		

		t = dbHandler.performQuery(session, EQueryType.HQL, "from GroupMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from GroupMining x order by x.id asc").list();
		oldGroupMining = new HashMap<Long, GroupMining>();
		for(int i = 0; i < t.size(); i++)
			oldGroupMining.put(((GroupMining)(t.get(i))).getId(), (GroupMining)t.get(i));
		System.out.println("Loaded " + oldGroupMining.size() + " GroupMining objects from the mining database.");

		

		t = dbHandler.performQuery(session, EQueryType.HQL, "from QuestionMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from QuestionMining x order by x.id asc").list();
		oldQuestionMining = new HashMap<Long, QuestionMining>();
		for(int i = 0; i < t.size(); i++)
			oldQuestionMining.put(((QuestionMining)(t.get(i))).getId(), (QuestionMining)t.get(i));
		System.out.println("Loaded " + oldQuizMining.size() + " QuestionMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from RoleMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from RoleMining x order by x.id asc").list();
		oldRoleMining = new HashMap<Long, RoleMining>();
		for(int i = 0; i < t.size(); i++)
			oldRoleMining.put(((RoleMining)(t.get(i))).getId(), (RoleMining)t.get(i));
		System.out.println("Loaded " + oldRoleMining.size() + " RoleMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from QuizQuestionMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from QuizQuestionMining x order by x.id asc").list();
		oldQuizQuestionMining = new HashMap<Long, QuizQuestionMining>();
		for(int i = 0; i < t.size(); i++)
			oldQuizQuestionMining.put(((QuizQuestionMining)(t.get(i))).getId(), (QuizQuestionMining)t.get(i));
		System.out.println("Loaded " + oldQuizQuestionMining.size() + " QuizQuestionMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from LevelMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from DepartmentMining x order by x.id asc").list();
		oldLevelMining = new HashMap<Long, LevelMining>();
		for(int i = 0; i < t.size(); i++)
			oldLevelMining.put(((LevelMining)(t.get(i))).getId(), (LevelMining)t.get(i));
		System.out.println("Loaded " + oldLevelMining.size() + " LevelMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ChatMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from ChatMining x order by x.id asc").list();
		oldChatMining = new HashMap<Long, ChatMining>();
		for(int i = 0; i < t.size(); i++)
			oldChatMining.put(((ChatMining)(t.get(i))).getId(), (ChatMining)t.get(i));
		System.out.println("Loaded " + oldChatMining.size() + " ChatMining objects from the mining database.");
		
		dbHandler.closeSession(session);

	    return readingtimestamp;
	}
	
	
	/**
	 * Has to read the LMS Database.
	 * It starts reading elements with time stamp readingtimestamp and higher.
	 * It is supposed to be used for frequently and small updates.
	 * For a greater Mass of Data it is suggested to use getLMS_tables(long, long);
	 * If Hibernate is used to access the LMS DB too,
	 * it is suggested to write the found tables into lists of
	 * Hibernate object model classes, which have to
	 * be created as global variables in this class.
	 * So they can be used in the generate methods of this class.
	 *
	 * @param readingfromtimestamp Only elements with time stamp readingtimestamp and higher are read here.
	 * *
	 * @return the lM stables
	 */
	abstract public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp);
	
	/**
	 * Has to read the LMS Database.
	 * It reads elements with time stamp between readingfromtimestamp and readingtotimestamp.
	 * This method is used to read great DB in a step by step procedure.
	 * That is necessary for a great mass of Data when handling an existing DB for example.
	 * If Hibernate is used to access the LMS DB too,
	 * it is suggested to write the found tables into lists of
	 * Hibernate object model classes, which have to
	 * be created as global variables in this class.
	 * So they can be used in the generate methods of this class.
	 *
	 * @param readingfromtimestamp Only elements with time stamp readingfromtimestamp and higher are read here.
	 * @param readingtotimestamp Only elements with time stamp readingtotimestamp and lower are read here.
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
	 public void clearMiningTables(){
		courseMining.clear();
		quizMining.clear();
		assignmentMining.clear();
		scormMining.clear();
		forumMining.clear();
		resourceMining.clear();
		userMining.clear();
		wikiMining.clear();
		groupMining.clear();
		questionMining.clear();
		roleMining.clear();
		levelMining.clear();
		chatMining.clear();
		chatMining.clear();
	}	
	
	/**
	 * Only for successive readings. This is meant to be done, when the gathered mining data has already 
	 * been saved and before the mining tables are cleared for the next iteration.
	 */

	 public void prepareMiningData()
	{
		oldCourseMining.putAll(courseMining);
		oldQuizMining.putAll(quizMining);
		oldAssignmentMining.putAll(assignmentMining);
		oldScormMining.putAll(scormMining);
		oldForumMining.putAll(forumMining);
		oldResourceMining.putAll(resourceMining);
		oldUserMining.putAll(userMining);
		oldWikiMining.putAll(wikiMining);
		oldGroupMining.putAll(groupMining);
		oldQuestionMining.putAll(questionMining);
		oldRoleMining.putAll(roleMining);
		oldLevelMining.putAll(levelMining);
		oldChatMining.putAll(chatMining);
		oldQuizQuestionMining.putAll(quizQuestionMining);
		oldCourseQuizMining.putAll(courseQuizMining);
	}
	
	/**
	 * Generates and save the new tables for the mining DB.
	 * We call the generate-methods for each mining table to get the new entries.
	 * At last we create a Transaction and save the new entries to the DB.
	 * **/		
	public void saveMiningTables() {

		
		//generate & save new mining tables
				updates = new ArrayList<Collection<?>>();

				Long objects = 0L;
				
				//generate mining tables
				if(userMining == null){
					
					c.reset();
					System.out.println("\nObject tables:\n");
					
					assignmentMining = generateAssignmentMining();
					objects += assignmentMining.size();
					System.out.println("Generated " + assignmentMining.size() + " AssignmentMining entries in "+ c.getAndReset() +" s. ");
					updates.add(assignmentMining.values());
					
					//Screwing up the alphabetical order, CourseMinings have to be calculated BEFORE ChatMinings due to the temporal foreign key "course" in ChatMining
					courseMining = generateCourseMining();
					objects += courseMining.size();
					System.out.println("Generated " + courseMining.size() + " CourseMining entries in "+ c.getAndReset() +" s. ");
					updates.add(courseMining.values());
					
					chatMining = generateChatMining();
					objects += chatMining.size();
					updates.add(chatMining.values());
					System.out.println("Generated " + chatMining.size() + " ChatMining entries in "+ c.getAndReset() +" s. ");
			
					levelMining = generateLevelMining();
					objects += levelMining.size();
					System.out.println("Generated " + levelMining.size() + " LevelMining entries in "+ c.getAndReset() +" s. ");
					updates.add(levelMining.values());
					
					forumMining = generateForumMining();
					objects += forumMining.size();
					System.out.println("Generated " + forumMining.size() + " ForumMining entries in "+ c.getAndReset() +" s. ");
					updates.add(forumMining.values());
					
					groupMining = generateGroupMining();
					objects += groupMining.size();
					System.out.println("Generated " + groupMining.size() + " GroupMining entries in "+ c.getAndReset() +" s. ");
					updates.add(groupMining.values());
					
					questionMining = generateQuestionMining();
					objects += questionMining.size();
					System.out.println("Generated " + questionMining.size() + " QuestionMining entries in "+ c.getAndReset() +" s. ");
					updates.add(questionMining.values());
					
					quizMining = generateQuizMining();
					objects += quizMining.size();
					updates.add(quizMining.values());
					System.out.println("Generated " + quizMining.size() + " QuizMining entries in "+ c.getAndReset() +" s. ");

					resourceMining = generateResourceMining();
					objects += resourceMining.size();
					System.out.println("Generated " + resourceMining.size() + " ResourceMining entries in "+ c.getAndReset() +" s. ");
					updates.add(resourceMining.values());

					roleMining = generateRoleMining();
					objects += roleMining.size();
					System.out.println("Generated " + roleMining.size() + " RoleMining entries in "+ c.getAndReset() +" s. ");
					updates.add(roleMining.values());
					
					scormMining = generateScormMining();
					objects += scormMining.size();
					System.out.println("Generated " + scormMining.size() + " ScormMining entries in "+ c.getAndReset() +" s. ");
					updates.add(scormMining.values());
					
					
					userMining = generateUserMining();
					objects += userMining.size();
					System.out.println("Generated " + userMining.size() + " UserMining entries in "+ c.getAndReset() +" s. ");
					updates.add(userMining.values());
					
					wikiMining = generateWikiMining();
					objects += wikiMining.size();
					System.out.println("Generated " + wikiMining.size() + " WikiMining entries in "+ c.getAndReset() +" s. ");
					updates.add(wikiMining.values());
					
					updates.add(generateCourseAssignmentMining().values());
					objects += updates.get(updates.size()-1).size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseAssignmentMining entries in "+ c.getAndReset() +" s. ");
					
					updates.add(generateCourseScormMining().values());
					objects += updates.get(updates.size()-1).size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseScormMining entries in "+ c.getAndReset() +" s. ");
					
					/*
					updates.add(generateDegreeCourseMining().values());
					objects += updates.get(updates.size()-1).size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " DegreeCourseMining entries in "+ c.getAndReset() +" s. ");
					*/
					
					updates.add(generateLevelAssociationMining().values());
					objects += updates.get(updates.size()-1).size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " LevelAssociationMining entries in "+ c.getAndReset() +" s. ");
					
					updates.add(generateLevelCourseMining().values());
					objects += updates.get(updates.size()-1).size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " LevelCourseMining entries in "+ c.getAndReset() +" s. ");
					
					/*
					updates.add(generateDepartmentDegreeMining().values());
					objects += updates.get(updates.size()-1).size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " DepartmentDegreeMining entries in "+ c.getAndReset() +" s. ");
					*/
					
					quizQuestionMining = generateQuizQuestionMining();
					objects += quizQuestionMining.size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " QuizQuestionMining entries in "+ c.getAndReset() +" s. ");

					updates.add(quizQuestionMining.values());			
					
					updates.add(generateCourseGroupMining().values());
					objects += updates.get(updates.size()-1).size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseGroupMining entries in "+ c.getAndReset() +" s. ");
					
					courseQuizMining = generateCourseQuizMining();
					objects += courseQuizMining.size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseQuizMining entries in "+ c.getAndReset() +" s. ");

					updates.add(courseQuizMining.values());updates.add(generateCourseResourceMining().values());
					objects += updates.get(updates.size()-1).size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseResourceMining entries in "+ c.getAndReset() +" s. ");
					
					updates.add(generateCourseWikiMining().values());
					objects += updates.get(updates.size()-1).size();
					System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseWikiMining entries in "+ c.getAndReset() +" s. ");
					
					
				}		

				updates.add(generateGroupUserMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " GroupUserMining entries in "+ c.getAndReset() +" s. ");
				
				updates.add(generateQuizUserMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " QuizUserMining entries in "+ c.getAndReset() +" s. ");
				
				updates.add(generateCourseUserMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseUserMining entries in "+ c.getAndReset() +" s. ");
				
				
				System.out.println("\nLog tables:\n");
				
				updates.add(generateAssignmentLogMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " AssignmentLogMining entries in "+ c.getAndReset() +" s. ");
				updates.add(generateChatLogMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " ChatLogMining entries in "+ c.getAndReset() +" s. ");
				updates.add(generateCourseLogMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " CourseLogMining entries in "+ c.getAndReset() +" s. ");
				updates.add(generateForumLogMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " ForumLogMining entries in "+ c.getAndReset() +" s. ");
				updates.add(generateQuestionLogMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " QuestionLogMining entries in "+ c.getAndReset() +" s. ");
				updates.add(generateQuizLogMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " QuizLogMining entries in "+ c.getAndReset() +" s. ");
				updates.add(generateResourceLogMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " ResourceLogMining entries in "+ c.getAndReset() +" s. ");
				updates.add(generateScormLogMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " ScormLogMining entries in "+ c.getAndReset() +" s. ");
				updates.add(generateWikiLogMining().values());
				objects += updates.get(updates.size()-1).size();
				System.out.println("Generated " + updates.get(updates.size()-1).size() + " WikiLogMining entries in "+ c.getAndReset() +" s. ");

				
				if(objects > 0)
				{
					Session session = dbHandler.getMiningSession();
					System.out.println("Writing to DB");
					dbHandler.saveCollectionToDB(session, updates);
				}
			    
			    clearLMStables();
				updates.clear();

	}	
	
//methods for create and fill the mining-table instances
	/**
	 * Has to create and fill the course_user table.
	 * This table describes which user is enrolled in which course in which time span.
	 * The attributes are described in the documentation of the course_user_mining class.
	 * Please use the getter and setter predefined in the course_user_mining class to fill the tables within this method.
	 * @return A list of instances of the course_user table representing class.
	 * **/	    
    abstract Map<Long, CourseUserMining> generateCourseUserMining();
    
	/**
	 * Has to create and fill the course_forum table.
	 * This table describes which forum is used in which course.
	 * The attributes are described in the documentation of the course_forum_mining class.
	 * Please use the getter and setter predefined in the course_forum_mining class to fill the tables within this method.
	 * @return A list of instances of the course_forum table representing class.
	 * **/	  
    abstract Map<Long, CourseForumMining> generateCourseForumMining();
    
	/**
	 * Has to create and fill the course table.
	 * This table describes the courses in the LMS.
	 * The attributes are described in the documentation of the course_mining class.
	 * Please use the getter and setter predefined in the course_mining class to fill the tables within this method.
	 * @return A list of instances of the course table representing class.
	 * **/	    
    abstract Map<Long, CourseMining> generateCourseMining();    
    
	/**
	 * Has to create and fill the course_group table.
	 * This table describes which groups are used in which courses.
	 * The attributes are described in the documentation of the course_group_mining class.
	 * Please use the getter and setter predefined in the course_group_mining class to fill the tables within this method.
	 * @return A list of instances of the course_group table representing class.
	 * **/	    
	abstract Map<Long, CourseGroupMining> generateCourseGroupMining();
    
	/**
	 * Has to create and fill the course_quiz table.
	 * This table describes which quiz are used in which courses.
	 * The attributes are described in the documentation of the course_quiz_mining class.
	 * Please use the getter and setter predefined in the course_quiz_mining class to fill the tables within this method.
	 * @return A list of instances of the course_quiz table representing class.
	 * **/	    
    abstract Map<Long, CourseQuizMining> generateCourseQuizMining();
    
	/**
	 * Has to create and fill the course_assignment table.
	 * This table describes which assignment are used in which courses.
	 * The attributes are described in the documentation of the course_assignment_mining class.
	 * Please use the getter and setter predefined in the course_assignment_mining class to fill the tables within this method.
	 * @return A list of instances of the course_assignment table representing class.
	 * **/	    
    abstract Map<Long, CourseAssignmentMining> generateCourseAssignmentMining();
    
	/**
	 * Has to create and fill the course_scorm table.
	 * This table describes which scorm packages are used in which courses.
	 * The attributes are described in the documentation of the course_scorm_mining class.
	 * Please use the getter and setter predefined in the course_scorm_mining class to fill the tables within this method.
	 * @return A list of instances of the course_scorm table representing class.
	 * **/	    
    abstract Map<Long, CourseScormMining> generateCourseScormMining();
    
	/**
	 * Has to create and fill the course_resource table.
	 * This table describes which resources are used in which courses.
	 * The attributes are described in the documentation of the course_resource_mining class.
	 * Please use the getter and setter predefined in the course_resource_mining class to fill the tables within this method.
	 * @return A list of instances of the course_resource table representing class.
	 * **/	     
    abstract Map<Long, CourseResourceMining> generateCourseResourceMining();
    
	/**
	 * Has to create and fill the course_log table.
	 * This table contains the actions which are done on courses.
	 * The attributes are described in the documentation of the course_log_mining class.
	 * Please use the getter and setter predefined in the course_log_mining class to fill the tables within this method.
	 * @return A list of instances of the course_log table representing class.
	 * **/	    
    abstract Map<Long, CourseLogMining> generateCourseLogMining();
    
	/**
	 * Has to create and fill the course_wiki table.
	 * This table describes which wikis are used in which courses.
	 * The attributes are described in the documentation of the course_wiki_mining class.
	 * Please use the getter and setter predefined in the course_wiki_mining class to fill the tables within this method.
	 * @return A list of instances of the course_wiki table representing class.
	 * **/	     
    abstract Map<Long, CourseWikiMining> generateCourseWikiMining();

	/**
	 * Has to create and fill the forum_log table.
	 * This table contains the actions which are done on forums.
	 * The attributes are described in the documentation of the forum_log_mining class.
	 * Please use the getter and setter predefined in the forum_log_mining class to fill the tables within this method.
	 * @return A list of instances of the forum_log table representing class.
	 * **/    
    abstract Map<Long, ForumLogMining> generateForumLogMining(); 
    
	/**
	 * Has to create and fill the forum table.
	 * This table describes the forums in the LMS.
	 * The attributes are described in the documentation of the forum_mining class.
	 * Please use the getter and setter predefined in the forum_mining class to fill the tables within this method.
	 * @return A list of instances of the forum table representing class.
	 * **/	    
    abstract Map<Long, ForumMining> generateForumMining();

	/**
	 * Has to create and fill the group_user table.
	 * This table describes which user are in which groups.
	 * The attributes are described in the documentation of the group_user_mining class.
	 * Please use the getter and setter predefined in the group_user_mining class to fill the tables within this method.
	 * @return A list of instances of the group_user table representing class.
	 * **/	    
	abstract Map<Long, GroupUserMining> generateGroupUserMining();
	
	/**
	 * Has to create and fill the group table.
	 * This table describes the groups in the LMS.
	 * The attributes are described in the documentation of the group_mining class.
	 * Please use the getter and setter predefined in the group_mining class to fill the tables within this method.
	 * @return A list of instances of the group table representing class.
	 * **/		
    abstract Map<Long, GroupMining> generateGroupMining();
    
	/**
	 * Has to create and fill the question_log table.
	 * This table contains the actions which are done on questions.
	 * The attributes are described in the documentation of the question_log_mining class.
	 * Please use the getter and setter predefined in the question_log_mining class to fill the tables within this method.
	 * @return A list of instances of the question_log table representing class.
	 * **/     
    abstract Map<Long, QuestionLogMining> generateQuestionLogMining();
    
	/**
	 * Has to create and fill the quiz_log table.
	 * This table contains the actions which are done on quiz.
	 * The attributes are described in the documentation of the quiz_log_mining class.
	 * Please use the getter and setter predefined in the quiz_log_mining class to fill the tables within this method.
	 * @return A list of instances of the quiz_log table representing class.
	 * **/     
    abstract Map<Long, QuizLogMining> generateQuizLogMining();
 
	/**
	 * Has to create and fill the assignment_log table.
	 * This table contains the actions which are done on assignment.
	 * The attributes are described in the documentation of the assignment_log_mining class.
	 * Please use the getter and setter predefined in the assignment_log_mining class to fill the tables within this method.
	 * @return A list of instances of the assignment_log table representing class.
	 * **/     
    abstract Map<Long, AssignmentLogMining> generateAssignmentLogMining();
    
	/**
	 * Has to create and fill the scorm_log table.
	 * This table contains the actions which are done on scorm.
	 * The attributes are described in the documentation of the scorm_log_mining class.
	 * Please use the getter and setter predefined in the scorm_log_mining class to fill the tables within this method.
	 * @return A list of instances of the scorm_log table representing class.
	 * **/     
    abstract Map<Long, ScormLogMining> generateScormLogMining();
    
	/**
	 * Has to create and fill the quiz_user table.
	 * This table describes which user gets which grade in which quiz.
	 * The attributes are described in the documentation of the quiz_user_mining class.
	 * Please use the getter and setter predefined in the quiz_user_mining class to fill the tables within this method.
	 * @return A list of instances of the quiz_user table representing class.
	 * **/	 
    abstract Map<Long, QuizUserMining> generateQuizUserMining();
    
	/**
	 * Has to create and fill the quiz table.
	 * This table describes the quiz in the LMS.
	 * The attributes are described in the documentation of the quiz_mining class.
	 * Please use the getter and setter predefined in the quiz_mining class to fill the tables within this method.
	 * @return A list of instances of the quiz table representing class.
	 * **/    
    abstract Map<Long, QuizMining> generateQuizMining();

	/**
	 * Has to create and fill the assignment table.
	 * This table describes the assignment in the LMS.
	 * The attributes are described in the documentation of the assignment_mining class.
	 * Please use the getter and setter predefined in the assignment_mining class to fill the tables within this method.
	 * @return A list of instances of the assignment table representing class.
	 * **/    
    abstract Map<Long, AssignmentMining> generateAssignmentMining();
    
	/**
	 * Has to create and fill the scorm table.
	 * This table describes the scorm packages in the LMS.
	 * The attributes are described in the documentation of the scorm_mining class.
	 * Please use the getter and setter predefined in the scorm_mining class to fill the tables within this method.
	 * @return A list of instances of the scorm table representing class.
	 * **/    
    abstract Map<Long, ScormMining> generateScormMining();
    
	/**
	 * Has to create and fill the quiz_question table.
	 * This table describes which question is used in which quiz.
	 * The attributes are described in the documentation of the quiz_question_mining class.
	 * Please use the getter and setter predefined in the quiz_question_mining class to fill the tables within this method.
	 * @return A list of instances of the quiz_question table representing class.
	 * **/    
    abstract Map<Long, QuizQuestionMining> generateQuizQuestionMining(); 

	/**
	 * Has to create and fill the question table.
	 * This table describes the question in the LMS.
	 * The attributes are described in the documentation of the question_mining class.
	 * Please use the getter and setter predefined in the question_mining class to fill the tables within this method.
	 * @return A list of instances of the question table representing class.
	 * **/     
    abstract Map<Long, QuestionMining> generateQuestionMining();
    
	/**
	 * Has to create and fill the resource table.
	 * This table describes the resource in the LMS.
	 * The attributes are described in the documentation of the resource_mining class.
	 * Please use the getter and setter predefined in the resource_mining class to fill the tables within this method.
	 * @return A list of instances of the resource table representing class.
	 * **/    
    abstract Map<Long, ResourceMining> generateResourceMining();
    
	/**
	 * Has to create and fill the resource_log table.
	 * This table contains the actions which are done on resource.
	 * The attributes are described in the documentation of the resource_log_mining class.
	 * Please use the getter and setter predefined in the resource_log_mining class to fill the tables within this method.
	 * @return A list of instances of the resource_log table representing class.
	 * **/
    abstract Map<Long, ResourceLogMining> generateResourceLogMining();
    
	/**
	 * Has to create and fill the user table.
	 * This table describes the user in the LMS.
	 * The attributes are described in the documentation of the user_mining class.
	 * Please use the getter and setter predefined in the user_mining class to fill the tables within this method.
	 * @return A list of instances of the user table representing class.
	 * **/
    abstract Map<Long, UserMining> generateUserMining();
    
	/**
	 * Has to create and fill the wiki_log table.
	 * This table contains the actions which are done on wiki.
	 * The attributes are described in the documentation of the wiki_log_mining class.
	 * Please use the getter and setter predefined in the wiki_log_mining class to fill the tables within this method.
	 * @return A list of instances of the wiki_log table representing class.
	 * **/
    abstract Map<Long, WikiLogMining> generateWikiLogMining(); 
    
	/**
	 * Has to create and fill the wiki table.
	 * This table describes the wiki in the LMS.
	 * The attributes are described in the documentation of the wiki_mining class.
	 * Please use the getter and setter predefined in the wiki_mining class to fill the tables within this method.
	 * @return A list of instances of the wiki table representing class.
	 * **/
	abstract Map<Long, WikiMining> generateWikiMining();
	
	/**
	 * Has to create and fill the role table.
	 * This table describes the roles of users in the LMS.
	 * The attributes are described in the documentation of the role_mining class.
	 * Please use the getter and setter predefined in the role_mining class to fill the tables within this method.
	 * @return A list of instances of the role table representing class.
	 * **/
	abstract Map<Long, RoleMining> generateRoleMining();
	
	
	/**
	 * Generate degree mining.
	 *
	 * @return the list
	 */
	//abstract Map<Long, DegreeMining> generateDegreeMining();
	
	/**
	 * Generate department mining.
	 *
	 * @return the list
	 */
	//abstract Map<Long, DepartmentMining> generateDepartmentMining();
	
	/**
	 * Generate level mining.
	 *
	 * @return the list
	 */
	abstract Map<Long, LevelMining> generateLevelMining();
	
	
	/**
	 * Generate department degree mining.
	 *
	 * @return the list
	 */
	//abstract Map<Long, DepartmentDegreeMining> generateDepartmentDegreeMining();
	
	/**
	 * Generate level association mining.
	 *
	 * @return the list
	 */
	abstract Map<Long, LevelAssociationMining> generateLevelAssociationMining();
	
	/**
	 * Generate degree course mining.
	 *
	 * @return the list
	 */
	//abstract Map<Long, DegreeCourseMining> generateDegreeCourseMining();
	
	/**
	 * Generate level course mining.
	 *
	 * @return the list
	 */
	abstract Map<Long, LevelCourseMining> generateLevelCourseMining();
	
	/**
	 * Generate chat mining.
	 *
	 * @return the list
	 */
	abstract Map<Long, ChatMining> generateChatMining();
	
	/**
	 * Generate chat log mining.
	 *
	 * @return the list
	 */
	abstract Map<Long, ChatLogMining> generateChatLogMining();
	
	
	
	
}
