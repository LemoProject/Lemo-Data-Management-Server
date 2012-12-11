package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.USER_IDS;
import static de.lemo.dms.processing.MetaParam.QUIZ_IDS;
import static de.lemo.dms.processing.MetaParam.RESOLUTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedLogObject;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

@Path("performanceHistogram")
public class QPerformanceHistogram extends Question{

	
	/**
	 * 
	 * @param courses (optional) List of course-ids that shall be included
	 * @param users	(optional) List of user-ids
	 * @param quizzes	(mandatory) List of the tuples: every learning object has successive entries, first the prefix of the learning-object-type (11 for "assignment", 14 for "quiz", 17 for "scorm") and the objects id
	 * @param resolution (mandatory) 
	 * @param startTime (mandatory) 
	 * @param endTime (mandatory) 
	 * @return
	 */
    @POST
    public ResultListLongObject compute(
    		@FormParam(COURSE_IDS) List<Long> courses, 
    		@FormParam(USER_IDS) List<Long> users, 
    		@FormParam(QUIZ_IDS) List<Long> quizzes,
    		@FormParam(RESOLUTION) int resolution,
    		@FormParam(START_TIME) Long startTime,
    		@FormParam(END_TIME) Long endTime) {

    	if(courses!=null && courses.size() > 0)
        {
        	System.out.print("Parameter list: Courses: " + courses.get(0));
        	for(int i = 1; i < courses.size(); i++)
        		System.out.print(", " + courses.get(i));
        	System.out.println();
        }
        if(users!=null && users.size() > 0)
        {
        	System.out.print("Parameter list: Users: " + users.get(0));
        	for(int i = 1; i < users.size(); i++)
        		System.out.print(", " + users.get(i));
        	System.out.println();
        }
        System.out.println("Parameter list: Resolution: : " + resolution);
        System.out.println("Parameter list: Start time: : "	+ startTime);
        System.out.println("Parameter list: End time: : " + endTime);
    	
    	if(quizzes == null || quizzes.size() < 1 || quizzes.size() % 2 != 0 || resolution <= 0 || startTime == null || endTime == null)
    	{
    		System.out.println("Calculation aborted. At least one of the mandatory parameters is not set properly.");
    		return new ResultListLongObject();
    	}
    	
    	//Determine length of result array
    	int objects = resolution * quizzes.size() / 2;
    	
    	Long[] results = new Long[objects]; 
    	//Initialize result array
    	for(int i = 0; i < results.length; i++)
    		results[i] = 0L;
		try
		{	
	        HashMap<Long, Integer> obj = new HashMap<Long, Integer>();
	        
	        int j = 0;
	        for(int i = 0; i < quizzes.size(); i++)
	        {
	        	if( i % 2 != 0)
	        	{
	        		obj.put(Long.valueOf(quizzes.get(i - 1) + "" + quizzes.get(i)), j);
	        		j++;
	        	}
	        }
        
	        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
	        Session session = dbHandler.getMiningSession();

	        Criteria criteria = session.createCriteria(IRatedLogObject.class, "log");
	        criteria.add(Restrictions.between("log.timestamp", startTime, endTime));
	        if(courses != null && courses.size() > 0)
	        	criteria.add(Restrictions.in("log.course.id", courses));
	        if(users != null && users.size() > 0)
	        	criteria.add(Restrictions.in("log.user.id", users));
	        
	        ArrayList<IRatedLogObject> list = (ArrayList<IRatedLogObject>) criteria.list();
	        
	        for(IRatedLogObject log : list)
	        {
	        	if(obj.get(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId())) != null && log.getFinalgrade() != null &&
	        			log.getMaxgrade() != null && log.getMaxgrade() > 0)
	        	{
	        		//Determine size of each interval
	        		 Double step = log.getMaxgrade() / resolution;
	        		// System.out.println(log.getMaxgrade());
	        		 if(step > 0d)
	        		 {
	        			 //Determine interval for specific grade
		        		int pos =  (int) (log.getFinalgrade() / step);
			     		if(pos > resolution - 1)
			     			pos = resolution - 1;
			     		//Increase count of specified interval
			     		results[(resolution * obj.get(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId()))) + pos] = results[(resolution * obj.get(Long.valueOf(log.getPrefix() + "" + log.getLearnObjId()))) + pos] + 1;
	        		 }
	        		 
	        	}
	        	
	        }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResultListLongObject(Arrays.asList(results));
	}
	
}
