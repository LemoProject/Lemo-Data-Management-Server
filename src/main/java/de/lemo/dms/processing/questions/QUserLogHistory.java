package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.USER_IDS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListUserLogObject;
import de.lemo.dms.processing.resulttype.UserLogObject;

@Path("userloghistory")
public class QUserLogHistory extends Question {
 
    /**
     * Returns all logged events matching the requirements given by the parameters.
     * 
     * @param courseIds		List of course-identifiers
     * @param userIds		List of user-identifiers
     * @param startTime		LongInteger time stamp  
     * @param endTime		LongInteger	time stamp 
     * @return
     */
    @POST
    public ResultListUserLogObject compute(
            @FormParam(COURSE_IDS) List<Long> courseIds,
            @FormParam(USER_IDS) List<Long> userIds,
            @FormParam(START_TIME) Long startTime,
            @FormParam(END_TIME) Long endTime){

        if(endTime == null) {
            endTime = new Date().getTime();
        }

        if(startTime >= endTime || userIds.isEmpty()) {
            return null;
        }

        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();


        Criteria criteria = session.createCriteria(ILogMining.class, "log");

        criteria.add(Restrictions.between("log.timestamp", startTime, endTime))
                .add(Restrictions.in("log.user.id", userIds));
        if(courseIds != null && courseIds.size() > 0)
        	criteria.add(Restrictions.in("log.course.id", courseIds));

        @SuppressWarnings("unchecked")
        List<ILogMining> logs = criteria.list();
        
        // HashMap for all user-histories
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
	        		uP.add(new UserLogObject(ilm.getUser().getId(), ilm.getTimestamp(), title, ilm.getId(), type, ilm.getCourse().getId(), "" ));
	        		userPaths.put(logs.get(i).getUser().getId(), uP);
	        	}
	        	else
	        		//If the user is known, just add the UserPathObject to the user's history
	        		userPaths.get(ilm.getUser().getId()).add(new UserLogObject(ilm.getUser().getId(), ilm.getTimestamp(), title, ilm.getId(), type, ilm.getCourse().getId(), "" ));
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
