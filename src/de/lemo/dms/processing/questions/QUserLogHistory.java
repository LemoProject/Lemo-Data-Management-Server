package de.lemo.dms.processing.questions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.UserLogObject;

import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;

import de.lemo.dms.processing.resulttype.ResultListUserLogObject;




@QuestionID("userloghistory")
public class QUserLogHistory extends Question {

    private static final String STARTTIME = "start_time";
    private static final String ENDTIME = "end_time";
	private static final String USER_IDS = "user_ids";
	private static final String COURSE_IDS = "course_ids";

    @Override
    protected List<ParameterMetaData<?>> createParamMetaData() {
        List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();

        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
        List<?> latest = dbHandler.performQuery(EQueryType.SQL, "Select max(timestamp) from resource_log");
        Long now = System.currentTimeMillis() / 1000;

        if(latest.size() > 0)
            now = ((BigInteger) latest.get(0)).longValue();

        Collections.<ParameterMetaData<?>>
                addAll(parameters,
                       Interval.create(Long.class, STARTTIME, "Start time", "", 0L, now, 0L),
                       Parameter.create(USER_IDS, "Users", "List of users-ids."),
                       Parameter.create(COURSE_IDS, "Courses", "List of course-ids."),
                       Interval.create(Long.class, ENDTIME, "End time", "", 0L, now, now)
                );
        return parameters;
    }

    /**
     * Returns all logged events matching the requirements given by the parameters.
     * 
     * @param courseIds		List of course-identifiers
     * @param userIds		List of user-identifiers
     * @param startTime		LongInteger time stamp  
     * @param endTime		LongInteger	time stamp 
     * @return
     */
    @GET
    public ResultListUserLogObject compute(
    		@QueryParam(COURSE_IDS) List<Long> courseIds,
            @QueryParam(USER_IDS) List<Long> userIds,
            @QueryParam(STARTTIME) Long startTime,
            @QueryParam(ENDTIME) Long endTime){

        /*
         * This is the first usage of Criteria API in the project and therefore a bit more documented than usual, to
         * serve as example implementation for other analyses.
         */

        if(endTime == null) {
            endTime = new Date().getTime();
        }

        if(startTime >= endTime || userIds.isEmpty()) {
            return null;
        }


        /* A criteria is created from the session. */
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getSession(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());

        /*
         * HQL-like equivalent: "Select from ILogMining". ILogMining is an interface, so Hibernate will load all classes
         * implementing it. The string argument is an alias.
         */
        Criteria criteria = session.createCriteria(ILogMining.class, "log");

        /*
         * Restrictions equivalent to HQL where:
         * 
         * where course in ( ... ) and timestamp between " + startTime + " AND " + endTime;
         */
        criteria.add(Restrictions.between("log.timestamp", startTime, endTime))
                .add(Restrictions.in("log.user.id", userIds));
        if(courseIds != null && courseIds.size() > 0)
        	criteria.add(Restrictions.in("log.course.id", courseIds));

        /* Calling list() eventually performs the query */
        @SuppressWarnings("unchecked")
        List<ILogMining> logs = criteria.list();
        
      //HashMap for all user-histories
        HashMap<Long, List<UserLogObject>> userPaths = new HashMap<Long, List<UserLogObject>>();
        
        //Iterate through all found log-items for saving log data into UserPathObjects
        for(int i = 0; i < logs.size(); i++)
        {
        	
        	String title ="";
    		String type ="";
    		ILogMining ilm = null;
    		
    		//the log entry has to be cast to its respective class to get its title
    		if(logs.get(i).getClass().equals(AssignmentLogMining.class) && ((AssignmentLogMining)logs.get(i)).getAssignment() != null)
    		{
    			ilm = (AssignmentLogMining)logs.get(i);
    			type = "assignment";
    			title = ((AssignmentLogMining)ilm).getAssignment().getTitle();
    		}
    		if(logs.get(i).getClass().equals(ForumLogMining.class) && ((ForumLogMining)logs.get(i)).getForum() != null)
    		{
    			ilm = (ForumLogMining)logs.get(i);
    			type = "forum";
    			title = ((ForumLogMining)ilm).getForum().getTitle();
    		}
    		if(logs.get(i).getClass().equals(CourseLogMining.class) && ((CourseLogMining)logs.get(i)).getCourse() != null)
    		{
    			ilm = (CourseLogMining)logs.get(i);
    			type = "course";
    			title = ((CourseLogMining)ilm).getCourse().getTitle();
    		}
    		if(logs.get(i).getClass().equals(QuizLogMining.class) && ((QuizLogMining)logs.get(i)).getQuiz() != null)
    		{
    			ilm = (QuizLogMining)logs.get(i);
    			type = "quiz";
    			title = ((QuizLogMining)ilm).getQuiz().getTitle();
    		}
    		if(logs.get(i).getClass().equals(QuestionLogMining.class) && ((QuestionLogMining)logs.get(i)).getQuestion() != null)
    		{
    			ilm = (QuestionLogMining)logs.get(i);
    			type = "question";
    			title = ((QuestionLogMining)ilm).getQuestion().getTitle();
    		}
    		if(logs.get(i).getClass().equals(ResourceLogMining.class) && ((ResourceLogMining)logs.get(i)).getResource() != null)
    		{
    			ilm = (ResourceLogMining)logs.get(i);
    			type = "resource";
    			title = ((ResourceLogMining)ilm).getResource().getTitle();
    		}
    		if(logs.get(i).getClass().equals(WikiLogMining.class) && ((WikiLogMining)logs.get(i)).getWiki() != null)
    		{
    			ilm = (WikiLogMining)logs.get(i);
    			type = "wiki";
    			title = ((WikiLogMining)ilm).getWiki().getTitle();
    		}

    		if(logs.get(i).getClass().equals(ScormLogMining.class) && ((ScormLogMining)logs.get(i)).getScorm() != null)
    		{
    			ilm = (ScormLogMining)logs.get(i);
    			type = "scorm";
    			title = ((ScormLogMining)ilm).getScorm().getTitle();
    		}
    		if(ilm != null)
	        	if(userPaths.get(logs.get(i).getUser().getId()) == null)
	        	{
	        		ArrayList<UserLogObject> uP = new ArrayList<UserLogObject>();
	        		//If the user isn't already in the map, create new entry and insert the UserPathObject
	        		uP.add(new UserLogObject(ilm.getUser().getId(), ilm.getTimestamp(), title, ilm.getId(), type, 0L, "" ));
	        		userPaths.put(logs.get(i).getUser().getId(), uP);
	        	}
	        	else
	        		//If the user is known, just add the UserPathObject to the user's history
	        		userPaths.get(ilm.getUser().getId()).add(new UserLogObject(ilm.getUser().getId(), ilm.getTimestamp(), title, ilm.getId(), type, 0L, "" ));
    		else
    			System.out.println();
        }

        //List for UserPathObjects
        List<UserLogObject> l = new ArrayList<UserLogObject>();
        //Insert all entries of all user-histories to the list
        for(Iterator<List<UserLogObject>> iter = userPaths.values().iterator(); iter.hasNext();)
        	l.addAll(iter.next());
        //Sort the list (first by user and time stamp)
        Collections.sort(l);
        for(int i = 0; i < l.size(); i++)
        	System.out.println(l.get(i).getType());

        ResultListUserLogObject rlupo = new ResultListUserLogObject(l);
        
        return rlupo;
    }
}



/*
        Set<Long> users = Sets.newHashSet();
        for(ILogMining log : logs) {
            UserMining user = log.getUser();
            if(user == null)
                continue;
            users.add(user.getId());
        }

        logger.info("Found " + users.size() + " actions. " + +stopWatch.elapsedTime(TimeUnit.SECONDS));

        Criteria exdendedCriteria = session.createCriteria(ILogMining.class, "log");
        exdendedCriteria.add(Restrictions.in("log.user.id", users))
                .add(Restrictions.between("log.timestamp", startTime, endTime))
                .add(Restrictions.eq("log.action", "view"));

        @SuppressWarnings("unchecked")
        List<ILogMining> extendedLogs = exdendedCriteria.list();

        Map<Long, List<Long>> userPaths = Maps.newHashMap();

        logger.info("Paths fetched: " + extendedLogs.size() + ". " + stopWatch.elapsedTime(TimeUnit.SECONDS));

        for(ILogMining log : extendedLogs) {
            UserMining user = log.getUser();
            if(user == null || log.getCourse() == null)
                continue;
            long userId = log.getUser().getId();

            List<Long> courseIDs = userPaths.get(userId);
            if(courseIDs == null) {
                courseIDs = Lists.newArrayList();
                userPaths.put(userId, courseIDs);
            }
            courseIDs.add(log.getCourse().getId());
        }

        logger.info("userPaths: " + userPaths.size());

        Map<Long, List<JSONObject>> coursePaths = Maps.newHashMap();

        for(Entry<Long, List<Long>> userEntry : userPaths.entrySet()) {

            JSONObject lastPath = null;
            Long userID = userEntry.getKey();

            for(Long courseID : userEntry.getValue()) {
                List<JSONObject> edge = coursePaths.get(courseID);
                if(edge == null) {
                    edge = Lists.newArrayList();
                    coursePaths.put(courseID, edge);
                }
                JSONObject path = new JSONObject();

                path.put("user", userID);

                if(lastPath != null) {
                    lastPath.put("to", courseID);
                }
                lastPath = path;
            }
        }
        stopWatch.stop();
        logger.info("coursePaths: " + coursePaths.size());
        logger.info("Total Fetched log entries: " + (logs.size() + extendedLogs.size()) + " log entries."
                + stopWatch.elapsedTime(TimeUnit.SECONDS));


        JSONObject result = new JSONObject();
        JSONArray nodes = new JSONArray();
        for(Entry<Long, List<JSONObject>> courseEntry : coursePaths.entrySet()) {
            JSONObject node = new JSONObject();
            node.put("id", courseEntry.getKey());
            node.put("wheight", courseEntry.getValue().size());
            node.put("edges", new JSONArray(courseEntry.getValue()));
            if(courseIds.contains(courseEntry.getKey()))
            	node.put("groupId", 0);
            else
            	node.put("groupId", 1);
            nodes.put(node);
        }

        result.put("nodes", nodes);
 */