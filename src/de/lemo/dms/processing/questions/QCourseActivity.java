package de.lemo.dms.processing.questions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

@QuestionID("courseactivity")
public class QCourseActivity extends Question{

    private static final String COURSE_IDS = "course_ids";
    private static final String ROLE_IDS = "role_ids";
    private static final String STARTTIME = "starttime";
    private static final String ENDTIME = "endtime";
    private static final String RESOLUTION = "resolution";

    @Override
	protected List<ParameterMetaData<?>> createParamMetaData() {
	    List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();
        
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
        List<?> latest = dbHandler.performQuery(EQueryType.SQL, "Select max(timestamp) from resource_log");
        Long now = System.currentTimeMillis()/1000;
        
        if(latest.size() > 0)
        	now = ((BigInteger)latest.get(0)).longValue();
     
        Collections.<ParameterMetaData<?>> addAll( parameters,
                Parameter.create(COURSE_IDS,"Courses","List of courses."),
                Parameter.create(ROLE_IDS, "Roles","List of roles."),
                Interval.create(long.class, STARTTIME, "Start time", "", 0L, now, 0L), 
                Interval.create(long.class, ENDTIME, "End time", "", 0L, now, now),
                Parameter.create(RESOLUTION, "Resolution", "")
                );
        return parameters;
	}
	
	
    /**
     * Returns a list with the length of 'resolution'. Each entry holds the number of requests in the interval.
     * 
     * @param courses		(Mandatory) Course-identifiers of the courses that should be processed.
     * @param roles			(Optional)	Role-identifiers 
     * @param startTime		(Mandatory) 
     * @param endTime		(Mandatory)
     * @param resolution 	(Mandatory)
     * @return
     */
    @SuppressWarnings("unchecked")
	@POST
    public ResultListLongObject compute(
            @FormParam(COURSE_IDS) List<Long> courses,
            @FormParam(ROLE_IDS) List<Long> roles,
            @FormParam(STARTTIME) long startTime,
            @FormParam(ENDTIME) long endTime,
            @FormParam(RESOLUTION) int resolution) {
		
		List<Long> list = new ArrayList<Long>();
		//Check arguments
		if(startTime < endTime && resolution > 0)
		{
			
			//Set up db-connection
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
			
			//Calculate size of time intervalls
			double intervall = (endTime - startTime) / (resolution);
			
			//Create and initialize array for results
			Long[] resArr = new Long[resolution];
			for(int i =  0; i < resArr.length; i++)
				resArr[i] = 0L;
			
			Session session = dbHandler.getSession();
			
			List<CourseUserMining> ilm = null;
			if(roles != null && roles.size() > 0)
			{
				Criteria criteria = session.createCriteria(CourseUserMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
				 	.add(Restrictions.in("log.role.id", roles));
				  ilm = criteria.list();
			}
			 List<Long> users = new ArrayList<Long>();
			 
			 if(ilm != null)
				 for(int i = 0; i < ilm.size(); i++)
				 {
					 if(ilm.get(i).getUser() != null)
						 users.add(ilm.get(i).getUser().getId());
				 }
			 
			 List<ILogMining> logs = null;

			 Criteria criteria2 = session.createCriteria(ILogMining.class, "log");
			 criteria2.add(Restrictions.in("log.course.id", courses));
			 
			 
			 if(users.size() > 0)
			 	criteria2.add(Restrictions.in("log.user.id", users));
			 
			 criteria2.add(Restrictions.between("log.timestamp", startTime, endTime));
			
            logs = criteria2.list();

			for(int i = 0 ; i < logs.size(); i++)
			{
				Integer pos = new Double((logs.get(i).getTimestamp() - startTime) / intervall).intValue();
				if(pos>resolution-1)
					pos = resolution-1;
				else
					resArr[pos] = resArr[pos] + 1;
			}			
			Collections.addAll(list, resArr);
		}
        return new ResultListLongObject(list);
    }
}
