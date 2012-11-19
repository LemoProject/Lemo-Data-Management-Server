package de.lemo.dms.processing.questions;
import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.RESOLUTION;
import static de.lemo.dms.processing.MetaParam.ROLE_IDS;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.TYPES;
import static de.lemo.dms.processing.MetaParam.USER_IDS;

import java.util.ArrayList;
import java.util.Collections;
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
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

@Path("courseactivity")
public class QCourseActivity extends Question{ 
	
    /**
     * Returns a list with the length of 'resolution'. Each entry holds the number of requests in the interval.
     * 
     * @param courses		(Mandatory) Course-identifiers of the courses that should be processed.
     * @param roles			(Optional)	Role-identifiers 
     * @param startTime		(Mandatory) 
     * @param endTime		(Mandatory)
     * @param resolution 	(Mandatory)
     * @param resourceTypes (Optional)
     * @return
     */
    @SuppressWarnings("unchecked")
	@POST
    public HashMap<Long, ResultListLongObject> compute(
            @FormParam(COURSE_IDS) List<Long> courses,
            @FormParam(ROLE_IDS) List<Long> roles,
            @FormParam(USER_IDS) List<Long> users,
            @FormParam(START_TIME) Long startTime,
            @FormParam(END_TIME) Long endTime,
            @FormParam(RESOLUTION) Integer resolution,
            @FormParam(TYPES) List<String> resourceTypes) {
		
		HashMap<Long, ResultListLongObject> result = new HashMap<Long, ResultListLongObject>();
		//Check arguments
		if(startTime < endTime && resolution > 0)
		{
			
			//Set up db-connection
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			Session session = dbHandler.getMiningSession();
			
			//Calculate size of time intervalls
			double intervall = (endTime - startTime) / (resolution);
			
			//Create and initialize array for results
			for( int j =0; j < courses.size(); j++)
			{
				
				Long[] resArr = new Long[resolution];
				for(int i =  0; i < resArr.length; i++)
					resArr[i] = 0L;
				List<Long> l = new ArrayList<Long>();
				Collections.addAll(l, resArr);
				result.put(courses.get(j), new ResultListLongObject(l));
			}
			
			
			if(resourceTypes != null && resourceTypes.size() > 0)
	    		for(int i = 0; i < resourceTypes.size(); i++){
	    			logger.info("Course Activity Request - CA Selection: " + resourceTypes.get(i));
	    		}
	    	else logger.info("Course Activity Request - CA Selection: NO Items selected ");

			
			List<CourseUserMining> ilm = null;
			
			if(roles != null && roles.size() > 0)
			{
				Criteria criteria = session.createCriteria(CourseUserMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
				 	.add(Restrictions.in("log.role.id", roles));
				 if(users != null && users.size() > 0)
					 criteria.add(Restrictions.in("log.user.id", users));
				  ilm = criteria.list();
			}
			 List<Long> userList = new ArrayList<Long>();
			 
			 if(ilm != null)
				 for(int i = 0; i < ilm.size(); i++)
				 {
					 if(ilm.get(i).getUser() != null)
						 userList.add(ilm.get(i).getUser().getId());
				 }
			 List<ILogMining> logs = null;

			 Criteria criteria2 = session.createCriteria(ILogMining.class, "log");
			 criteria2.add(Restrictions.in("log.course.id", courses));
			 
			 
			 if(users.size() > 0)
			 	criteria2.add(Restrictions.in("log.user.id", users));
			 else if(userList.size() > 0)
				 criteria2.add(Restrictions.in("log.user.id", users));
			 
			 criteria2.add(Restrictions.between("log.timestamp", startTime, endTime));
			
            logs = criteria2.list();

			for(int i = 0 ; i < logs.size(); i++)
			{
				boolean isInRT = false;
				if(resourceTypes != null && resourceTypes.size() > 0)
					for(int j = 0; j < resourceTypes.size(); j++)
						if(logs.get(i).getClass().toString().toLowerCase().contains(resourceTypes.get(j)))
						{
							isInRT = true;
							break;
						}
				if(resourceTypes == null || resourceTypes.size() == 0 || isInRT)
				{
					Integer pos = new Double((logs.get(i).getTimestamp() - startTime) / intervall).intValue();
					if(pos > resolution - 1)
						pos = resolution - 1;
					result.get(logs.get(i).getCourse().getId()).getElements().set(pos, result.get(logs.get(i).getCourse().getId()).getElements().get(pos) + 1);
				}
			}			
		}
        return result;
    }
}
