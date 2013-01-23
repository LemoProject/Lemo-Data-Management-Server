package de.lemo.dms.connectors.moodle;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import de.lemo.dms.db.miningDBclass.IDMappingMining;
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
 * Contains bundle of static fields as container for LMS objects,
 * which are used for linking the tables. */
public abstract class ExtractAndMap{

//lists of object tables which are new found in LMS DB
	/** A List of new entries in the course table found in this run of the process. */
	protected Map<Long, CourseMining> courseMining;
	
	protected Map<Long, PlatformMining> platformMining;
	
	/** A List of new entries in the quiz table found in this run of the process. */
	protected Map<Long, QuizMining> quizMining;
	
	/** A List of new entries in the assignment table found in this run of the process. */
	protected Map<Long, AssignmentMining> assignmentMining;
	
	/** A List of new entries in the assignment table found in this run of the process. */
	protected Map<Long, ScormMining> scormMining;
	
	/** A List of new entries in the forum table found in this run of the process. */
	protected Map<Long, ForumMining> forumMining;
	
	/** A List of entries in the new role table found in this run of the process. */
	protected Map<Long, LevelMining> levelMining;
	
	/** A List of entries in the new role table found in this run of the process. */
	protected Map<Long, LevelAssociationMining> levelAssociationMining;
	
	/** A List of new entries in the resource table found in this run of the process. */	
	protected Map<Long, ResourceMining> resourceMining;
	
	/** A List of new entries in the user table found in this run of the process. */	
	protected Map<Long, UserMining> userMining;
	
	/** A List of new entries in the wiki table found in this run of the process. */	
	protected Map<Long, WikiMining> wikiMining;
	
	/** A List of new entries in the group table found in this run of the process. */
	protected Map<Long, GroupMining> groupMining;
	
	/** A List of entries in the new question table found in this run of the process. */
	protected Map<Long, QuestionMining> questionMining;
	
	/** A List of entries in the new question table found in this run of the process. */
	protected Map<Long, QuizQuestionMining> quizQuestionMining;
	
	protected Map<Long, CourseQuizMining> courseQuizMining;
	
	protected Map<Long, CourseAssignmentMining> courseAssignmentMining;
	
	protected Map<Long, CourseScormMining> courseScormMining;
	
	/** A List of entries in the new role table found in this run of the process. */
	protected Map<Long, RoleMining> roleMining;
	
	/** The chat_mining. */
	protected Map<Long, ChatMining> chatMining;
	
	/** The chat_log_mining. */
	protected Map<Long, ChatLogMining> chatLogMining;
	
	/** The table that maps user-ids of the source database (string) onto numeric values.*/
	protected Map<String, IDMappingMining> idMapping;
	
	protected Map<Long, PlatformMining> oldPlatformMining;
	
	/** The table that maps user-ids of the source database (string) onto numeric values.*/
	protected Map<String, IDMappingMining> oldIdMapping;
	
//lists of object tables which are already in the mining DB
	/** A List of entries in the course table, needed for linking reasons in the process. */
	protected Map<Long, CourseMining> oldCourseMining;
	
	/** A List of entries in the quiz table, needed for linking reasons in the process. */
	protected Map<Long, QuizMining> oldQuizMining;
	
	/** The old_department_mining. */
	protected Map<Long, LevelMining> oldLevelMining;
		
	/** A List of entries in the assignment table, needed for linking reasons in the process. */
	protected Map<Long, AssignmentMining> oldAssignmentMining;
	
	/** A List of entries in the scorm table, needed for linking reasons in the process. */
	protected Map<Long, ScormMining> oldScormMining;
	
	/** A List of entries in the forum table, needed for linking reasons in the process. */
	protected Map<Long, ForumMining> oldForumMining;
	
	/** A List of entries in the resource table, needed for linking reasons in the process. */
	protected Map<Long, ResourceMining> oldResourceMining;
	
	/** A List of entries in the user table, needed for linking reasons in the process. */
	protected Map<Long, UserMining> oldUserMining;
	
	/** A List of entries in the wiki table, needed for linking reasons in the process. */
	protected Map<Long, WikiMining> oldWikiMining;
	
	/** A List of entries in the group table, needed for linking reasons in the process. */
	protected Map<Long, GroupMining> oldGroupMining;
	
	/** A List of entries in the question table, needed for linking reasons in the process. */
	protected Map<Long, QuestionMining> oldQuestionMining;
	
	/** A List of entries in the role table, needed for linking reasons in the process. */
	protected Map<Long, RoleMining> oldRoleMining;
	
	/** A List of entries in the quiz_question table, needed for linking reasons in the process. */
	protected Map<Long, QuizQuestionMining> oldQuizQuestionMining;
	
	/** A List of entries in the course_quiz table, needed for linking reasons in the process. */
	protected Map<Long, CourseQuizMining> oldCourseQuizMining;
	
	/** The old_chat_mining. */
	protected Map<Long, ChatMining> oldChatMining;
	
	/** The old_chat_log_mining. */
	protected Map<Long, ChatLogMining> oldChatLogMining;
	
	/** A list of objects used for submitting them to the DB. */
	List<Collection<?>> updates;
	/** A list of timestamps of the previous runs of the extractor. */	
    protected List<Timestamp> configMiningTimestamp;

    /** value of the highest user-id in the dataset. Used for creating new numeric ids **/
    protected long largestId;
    
    protected Long questionLogMax;
    protected Long forumLogMax;
    protected Long courseLogMax;
    protected Long assignmentLogMax;
    protected Long quizLogMax;
    protected Long scormLogMax;
    protected Long wikiLogMax;
    protected Long chatLogMax;
    protected Long resourceLogMax;
	
    /** Designates which entries should be read from the LMS Database during the process.  */
    private long starttime;
    
    /** Database-handler **/
    private IDBHandler dbHandler;
    
    private Logger logger = Logger.getLogger(getClass());
	
    private Clock c;

    private IConnector connector;
 
    public ExtractAndMap(IConnector connector) {
        this.connector = connector;
    }

    /**
     * Starts the extraction process by calling getLMS_tables() and saveMining_tables(). 
     * A timestamp can be given as optional argument. 
     * When the argument is used the extraction begins after that timestamp. 
     * When no argument is given the program starts with the timestamp of the last run.
     * @param args Optional arguments for the process. Used for the selection of the ExtractAndMap Implementation and timestamp when the extraction should start.
     * **/	
	public void start(String[] args, DBConfigObject sourceDBConf) {
		dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		c = new Clock();
		starttime = System.currentTimeMillis()/1000;
		Session session = dbHandler.getMiningSession();
		
       
		
		//get the status of the mining DB; load timestamp of last update and objects needed for associations		
		long readingtimestamp = getMiningInitial();

		
		
		dbHandler.saveToDB(session, new PlatformMining(connector.getPlatformId(), connector.getName(), connector.getPlattformType().toString(), connector.getPrefix()));
		logger.info("Initialized database in " + c.getAndReset());
		//default call without parameter	        
		if(args.length == 1)
		{	    	
			//get the needed tables from LMS DB
			c.reset();
			getLMStables(sourceDBConf, readingtimestamp);
			logger.info("Loaded data from source in " + c.getAndReset());
			
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
					logger.info("Loaded data from source in " + c.getAndReset());
					//create and write the mining database tables
					saveMiningTables();		
				}
				
//read & save LMS DB in steps of 2 days
				for(long looptimestamp = readingtimestamp -1; looptimestamp < currenttimestamp;)
				{
					logger.info("looptimestamp:" + looptimestamp);
					c.reset();
					getLMStables(sourceDBConf, looptimestamp +1, readingtimestamp2);
					logger.info("Loaded data from source in " + c.getAndReset());
					looptimestamp += 172800;
					readingtimestamp2 += 172800;
					logger.info("currenttimestamp:" + currenttimestamp);
					saveMiningTables();	
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
	    config.setPlatform(connector.getPlatformId());
	    config.setDatabaseModel("1.2");
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
	public long getMiningInitial(){
					
	    Session session = dbHandler.getMiningSession();
	    
	    List<?> t;
	    long platformId = connector.getPlatformId();
	    
		configMiningTimestamp = (List<Timestamp>) dbHandler.performQuery(session, EQueryType.HQL, "select max(lastModified) from ConfigMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("select max(lastmodified) from ConfigMining x order by x.id asc").list();
		if(configMiningTimestamp.get(0) == null){
			configMiningTimestamp.set(0, new Timestamp(0));
		}
		
		
		if(configMiningTimestamp.get(0) == null){
			configMiningTimestamp.set(0, new Timestamp(0));
		}
        Query large = session.createQuery("select max(user.id) from UserMining user where user.platform="+ platformId +"");
        if(large.list().size() > 0 && large.list().get(0) != null)
        	largestId = ((ArrayList<Long>) large.list()).get(0);
		
		Query logCount = session.createQuery("select max(log.id) from ResourceLogMining log where log.platform="+ platformId +"");
        resourceLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(resourceLogMax == null)
        	resourceLogMax = 0L;
        
        logCount = session.createQuery("select max(log.id) from ChatLogMining log where log.platform="+ platformId +"");
        chatLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(chatLogMax == null)
        	chatLogMax = 0L;
        
        
        logCount = session.createQuery("select max(log.id) from AssignmentLogMining log where log.platform="+ platformId +"");
        assignmentLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(assignmentLogMax == null)
        	assignmentLogMax = 0L;
        
        logCount = session.createQuery("select max(log.id) from CourseLogMining log where log.platform="+ platformId +"");
        courseLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(courseLogMax == null)
        	courseLogMax = 0L;
        
        logCount = session.createQuery("select max(log.id) from ForumLogMining log where log.platform="+ platformId +"");
        forumLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(forumLogMax == null)
        	forumLogMax = 0L;
        
        logCount = session.createQuery("select max(log.id) from QuestionLogMining log where log.platform="+ platformId +"");
        questionLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(questionLogMax == null)
        	questionLogMax = 0L;
        
        logCount = session.createQuery("select max(log.id) from QuizLogMining log where log.platform="+ platformId +"");
        quizLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(quizLogMax == null)
        	quizLogMax = 0L;
        
        logCount = session.createQuery("select max(log.id) from ScormLogMining log where log.platform="+ platformId +"");
        scormLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(scormLogMax == null)
        	scormLogMax = 0L;
        
        logCount = session.createQuery("select max(log.id) from WikiLogMining log where log.platform="+ platformId +"");
        wikiLogMax = ((ArrayList<Long>) logCount.list()).get(0);
        if(wikiLogMax == null)
        	wikiLogMax = 0L;
		
		long readingtimestamp = configMiningTimestamp.get(0).getTime();
	
		
//load objects which are already in Mining DB for associations
		
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from CourseMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from CourseMining x order by x.id asc").list();
		oldCourseMining = new HashMap<Long, CourseMining>();
		for(int i = 0; i < t.size(); i++)
			oldCourseMining.put(((CourseMining)(t.get(i))).getId(), (CourseMining)t.get(i));
		logger.info("Loaded " + oldCourseMining.size() + " CourseMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from QuizMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from QuizMining x order by x.id asc").list();
		oldQuizMining = new HashMap<Long, QuizMining>();
		for(int i = 0; i < t.size(); i++)
			oldQuizMining.put(((QuizMining)(t.get(i))).getId(), (QuizMining)t.get(i));
		logger.info("Loaded " + oldQuizMining.size() + " QuizMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from AssignmentMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");// mining_session.createQuery("from AssignmentMining x order by x.id asc").list();
		oldAssignmentMining = new HashMap<Long, AssignmentMining>();
		for(int i = 0; i < t.size(); i++)
			oldAssignmentMining.put(((AssignmentMining)(t.get(i))).getId(), (AssignmentMining)t.get(i));
		logger.info("Loaded " + oldAssignmentMining.size() + " AssignmentMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ScormMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from ScormMining x order by x.id asc").list();
		oldScormMining = new HashMap<Long, ScormMining>();
		for(int i = 0; i < t.size(); i++)
			oldScormMining.put(((ScormMining)(t.get(i))).getId(), (ScormMining)t.get(i));
		logger.info("Loaded " + oldScormMining.size() + " ScormMining objects from the mining database.");		

		t = dbHandler.performQuery(session, EQueryType.HQL, "from ForumMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from ForumMining x order by x.id asc").list();
		oldForumMining = new HashMap<Long, ForumMining>();
		for(int i = 0; i < t.size(); i++)
			oldForumMining.put(((ForumMining)(t.get(i))).getId(), (ForumMining)t.get(i));
		logger.info("Loaded " + oldForumMining.size() + " ForumMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ResourceMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from ResourceMining x order by x.id asc").list();
		oldResourceMining = new HashMap<Long, ResourceMining>();
		for(int i = 0; i < t.size(); i++)
			oldResourceMining.put(((ResourceMining)(t.get(i))).getId(), (ResourceMining)t.get(i));
		logger.info("Loaded " + oldResourceMining.size() + " ResourceMining objects from the mining database.");

		t = dbHandler.performQuery(session, EQueryType.HQL, "from UserMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from UserMining x order by x.id asc").list();
		oldUserMining = new HashMap<Long, UserMining>();
		for(int i = 0; i < t.size(); i++)
			oldUserMining.put(((UserMining)(t.get(i))).getId(), (UserMining)t.get(i));
		logger.info("Loaded " + oldUserMining.size() + " UserMining objects from the mining database.");

		t = dbHandler.performQuery(session, EQueryType.HQL, "from WikiMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from WikiMining x order by x.id asc").list();
		oldWikiMining = new HashMap<Long, WikiMining>();
		for(int i = 0; i < t.size(); i++)
			oldWikiMining.put(((WikiMining)(t.get(i))).getId(), (WikiMining)t.get(i));
		logger.info("Loaded " + oldWikiMining.size() + " WikiMining objects from the mining database.");
		

		t =dbHandler.performQuery(session, EQueryType.HQL, "from GroupMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from GroupMining x order by x.id asc").list();
		oldGroupMining = new HashMap<Long, GroupMining>();
		for(int i = 0; i < t.size(); i++)
			oldGroupMining.put(((GroupMining)(t.get(i))).getId(), (GroupMining)t.get(i));
		logger.info("Loaded " + oldGroupMining.size() + " GroupMining objects from the mining database.");
		
		t =  dbHandler.performQuery(session, EQueryType.HQL, "from QuestionMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from QuestionMining x order by x.id asc").list();
		oldQuestionMining = new HashMap<Long, QuestionMining>();
		for(int i = 0; i < t.size(); i++)
			oldQuestionMining.put(((QuestionMining)(t.get(i))).getId(), (QuestionMining)t.get(i));
		logger.info("Loaded " + oldQuizMining.size() + " QuestionMining objects from the mining database.");

		t = dbHandler.performQuery(session, EQueryType.HQL, "from RoleMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from RoleMining x order by x.id asc").list();
		oldRoleMining = new HashMap<Long, RoleMining>();
		for(int i = 0; i < t.size(); i++)
			oldRoleMining.put(((RoleMining)(t.get(i))).getId(), (RoleMining)t.get(i));
		logger.info("Loaded " + oldRoleMining.size() + " RoleMining objects from the mining database.");

		t = dbHandler.performQuery(session, EQueryType.HQL, "from QuizQuestionMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from QuizQuestionMining x order by x.id asc").list();
		oldQuizQuestionMining = new HashMap<Long, QuizQuestionMining>();
		for(int i = 0; i < t.size(); i++)
			oldQuizQuestionMining.put(((QuizQuestionMining)(t.get(i))).getQuestion().getId(), (QuizQuestionMining)t.get(i));
		logger.info("Loaded " + oldQuizQuestionMining.size() + " QuizQuestionMining objects from the mining database.");

		t = dbHandler.performQuery(session, EQueryType.HQL, "from LevelMining x where x.platform="+ connector.getPlatformId() + " order by x.id asc");//mining_session.createQuery("from DepartmentMining x order by x.id asc").list();
		oldLevelMining = new HashMap<Long, LevelMining>();
		for(int i = 0; i < t.size(); i++)
			oldLevelMining.put(((LevelMining)(t.get(i))).getId(), (LevelMining)t.get(i));
		logger.info("Loaded " + oldLevelMining.size() + " LevelMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ChatMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from ChatMining x order by x.id asc").list();
		oldChatMining = new HashMap<Long, ChatMining>();
		for(int i = 0; i < t.size(); i++)
			oldChatMining.put(((ChatMining)(t.get(i))).getId(), (ChatMining)t.get(i));
		logger.info("Loaded " + oldChatMining.size() + " ChatMining objects from the mining database.");
		
		t = dbHandler.performQuery(session, EQueryType.HQL, "from ChatMining x where x.platform="+ connector.getPlatformId() +" order by x.id asc");//mining_session.createQuery("from ChatMining x order by x.id asc").list();
		oldChatMining = new HashMap<Long, ChatMining>();
		for(int i = 0; i < t.size(); i++)
			oldChatMining.put(((ChatMining)(t.get(i))).getId(), (ChatMining)t.get(i));
		logger.info("Loaded " + oldChatMining.size() + " ChatMining objects from the mining database.");
		
		
		List<IDMappingMining> ids = (List<IDMappingMining>) dbHandler.performQuery(session, EQueryType.HQL, "from IDMappingMining x WHERE x.platform=" + platformId + " order by x.id asc");
		
		dbHandler.closeSession(session);
		
		oldIdMapping = new HashMap<String, IDMappingMining>();
		for(int i = 0; i < ids.size(); i++)
		{
			oldIdMapping.put(ids.get(i).getHash(), ids.get(i));
		}
		//mining_session.clear();		

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
		chatMining.clear();
		idMapping.clear();
		chatMining.clear();
		levelMining.clear();
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
		//old_degree_mining.putAll(degree_mining);
		//old_department_mining.putAll(department_mining);
		oldChatMining.putAll(chatMining);
		oldIdMapping.putAll(idMapping);
		oldQuizQuestionMining.putAll(quizQuestionMining);
		oldCourseQuizMining.putAll(courseQuizMining);
		oldLevelMining.putAll(levelMining);
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
			
			idMapping = new HashMap<String, IDMappingMining>();
			c.reset();
			logger.info("Now generating object tables:");
			
			assignmentMining = generateAssignmentMining();
			objects += assignmentMining.size();
			logger.info("Generated " + assignmentMining.size() + " AssignmentMining entries in "+ c.getAndReset() +" s. ");
			updates.add(assignmentMining.values());
			
			//Screwing up the alphabetical order, CourseMinings have to be calculated BEFORE ChatMinings due to the temporal foreign key "course" in ChatMining
			courseMining = generateCourseMining();
			objects += courseMining.size();
			logger.info("Generated " + courseMining.size() + " CourseMining entries in "+ c.getAndReset() +" s. ");
			updates.add(courseMining.values());
			
			chatMining = generateChatMining();
			objects += chatMining.size();
			updates.add(chatMining.values());
			logger.info("Generated " + chatMining.size() + " ChatMining entries in "+ c.getAndReset() +" s. ");
			
			levelMining = generateLevelMining();
			objects += levelMining.size();
			logger.info("Generated " + levelMining.size() + " LevelMining entries in "+ c.getAndReset() +" s. ");
			updates.add(levelMining.values());
			
			forumMining = generateForumMining();
			objects += forumMining.size();
			logger.info("Generated " + forumMining.size() + " ForumMining entries in "+ c.getAndReset() +" s. ");
			updates.add(forumMining.values());
			
			groupMining = generateGroupMining();
			objects += groupMining.size();
			logger.info("Generated " + groupMining.size() + " GroupMining entries in "+ c.getAndReset() +" s. ");
			updates.add(groupMining.values());
			
			questionMining = generateQuestionMining();
			objects += questionMining.size();
			logger.info("Generated " + questionMining.size() + " QuestionMining entries in "+ c.getAndReset() +" s. ");
			updates.add(questionMining.values());
			
			quizMining = generateQuizMining();
			objects += quizMining.size();
			updates.add(quizMining.values());
			logger.info("Generated " + quizMining.size() + " QuizMining entries in "+ c.getAndReset() +" s. ");

			resourceMining = generateResourceMining();
			objects += resourceMining.size();
			logger.info("Generated " + resourceMining.size() + " ResourceMining entries in "+ c.getAndReset() +" s. ");
			updates.add(resourceMining.values());

			roleMining = generateRoleMining();
			objects += roleMining.size();
			logger.info("Generated " + roleMining.size() + " RoleMining entries in "+ c.getAndReset() +" s. ");
			updates.add(roleMining.values());
			
			scormMining = generateScormMining();
			objects += scormMining.size();
			logger.info("Generated " + scormMining.size() + " ScormMining entries in "+ c.getAndReset() +" s. ");
			updates.add(scormMining.values());
			
			
			userMining = generateUserMining();
			objects += userMining.size();
			logger.info("Generated " + userMining.size() + " UserMining entries in "+ c.getAndReset() +" s. ");
			updates.add(userMining.values());
			
			wikiMining = generateWikiMining();
			objects += wikiMining.size();
			logger.info("Generated " + wikiMining.size() + " WikiMining entries in "+ c.getAndReset() +" s. ");
			updates.add(wikiMining.values());
			
			updates.add(generateCourseAssignmentMining().values());
			objects += updates.get(updates.size()-1).size();
			logger.info("Generated " + updates.get(updates.size()-1).size() + " CourseAssignmentMining entries in "+ c.getAndReset() +" s. ");
			
			updates.add(generateCourseScormMining().values());
			objects += updates.get(updates.size()-1).size();
			logger.info("Generated " + updates.get(updates.size()-1).size() + " CourseScormMining entries in "+ c.getAndReset() +" s. ");
			
			updates.add(generateLevelAssociationMining().values());
			objects += updates.get(updates.size()-1).size();
			logger.info("Generated " + updates.get(updates.size()-1).size() + " LevelAssociationMining entries in "+ c.getAndReset() +" s. ");
			
			updates.add(generateLevelCourseMining().values());
			objects += updates.get(updates.size()-1).size();
			logger.info("Generated " + updates.get(updates.size()-1).size() + " LevelCourseMining entries in "+ c.getAndReset() +" s. ");
			
			quizQuestionMining = generateQuizQuestionMining();
			objects += quizQuestionMining.size();
			logger.info("Generated " + quizQuestionMining.size() + " QuizQuestionMining entries in "+ c.getAndReset() +" s. ");
			updates.add(quizQuestionMining.values());
			
			logger.info("Now generating association tables");
			
			updates.add(generateCourseForumMining().values());
			objects += updates.get(updates.size()-1).size();
			logger.info("Generated " + updates.get(updates.size()-1).size() + " CourseForumMining entries in "+ c.getAndReset() +" s. ");
			updates.add(generateCourseGroupMining().values());
			objects += updates.get(updates.size()-1).size();
			logger.info("Generated " + updates.get(updates.size()-1).size() + " CourseGroupMining entries in "+ c.getAndReset() +" s. ");
			
			courseQuizMining = generateCourseQuizMining();
			objects += courseQuizMining.size();
			logger.info("Generated " + courseQuizMining.size() + " CourseQuizMining entries in "+ c.getAndReset() +" s. ");
			updates.add(courseQuizMining.values());
			
			updates.add(generateCourseResourceMining().values());
			objects += updates.get(updates.size()-1).size();
			logger.info("Generated " + updates.get(updates.size()-1).size() + " CourseResourceMining entries in "+ c.getAndReset() +" s. ");
			updates.add(generateCourseWikiMining().values());
			objects += updates.get(updates.size()-1).size();
			logger.info("Generated " + updates.get(updates.size()-1).size() + " CourseWikiMining entries in "+ c.getAndReset() +" s. ");

			updates.add(idMapping.values());
			
		}
		
		updates.add(generateCourseUserMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " CourseUserMining entries in "+ c.getAndReset() +" s. ");
		
		
		updates.add(generateGroupUserMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " GroupUserMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateQuizUserMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " QuizUserMining entries in "+ c.getAndReset() +" s. ");
		
		logger.info("Now generating log tables:");
		
		updates.add(generateAssignmentLogMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " AssignmentLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateChatLogMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " ChatLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateCourseLogMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " CourseLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateForumLogMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " ForumLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateQuestionLogMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " QuestionLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateQuizLogMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " QuizLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateResourceLogMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " ResourceLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateScormLogMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " ScormLogMining entries in "+ c.getAndReset() +" s. ");
		updates.add(generateWikiLogMining().values());
		objects += updates.get(updates.size()-1).size();
		logger.info("Generated " + updates.get(updates.size()-1).size() + " WikiLogMining entries in "+ c.getAndReset() +" s. ");

		
		if(objects > 0)
		{
			Session session = dbHandler.getMiningSession();
			logger.info("Writing to DB");
			dbHandler.saveCollectionToDB(session, updates);
		}
	    
	    clearLMStables();
		updates.clear();

	}	
	
//methods for create and fill the mining-table instances
	/**
	 * Has to create and fill the course_user table.
	 * This table describes which user is enrolled in which course in which timesspan.
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
	 * Generate department degree mining.
	 *
	 * @return the list
	 */
	//abstract Map<Long, DepartmentDegreeMining> generateDepartmentDegreeMining();
	
	/**
	 * Generate degree course mining.
	 *
	 * @return the list
	 */
	//abstract Map<Long, DegreeCourseMining> generateDegreeCourseMining();
	
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
