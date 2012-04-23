package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResultList;

@Path("courseactivity")
public class QCourseActivity extends Question{

	@Override
	protected List<ParameterMetaData<?>> createParamMetaData() {
	    List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();
        
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
        List<?> latest = dbHandler.performQuery(EQueryType.SQL, "Select max(timestamp) from resource_log");
        Long now = System.currentTimeMillis()/1000;
        
        if(latest.size() > 0)
        	now = (Long)latest.get(0);
     
        Collections.<ParameterMetaData<?>> addAll( parameters,
                Parameter.create("course_ids","Courses","List of courses."),
                Parameter.create("role_ids", "Roles","List of roles."),
                Interval.create(Long.class, "starttime", "Start time", "", 0L, now, 0L), 
                Interval.create(Long.class, "endtime", "End time", "", 0L, now, now), 
                Parameter.create("resolution", "Resolution", "")
                );
        return parameters;
	}
	
	
	@GET
    public ResultList compute(@QueryParam("course_ids") List<Long> courses, @QueryParam("role_ids") List<Long> roles,
            @QueryParam("starttime") Long starttime, @QueryParam("endtime") Long endtime, @QueryParam("resolution") int resolution) {
		
		List<Long> list = new ArrayList<Long>();
		//Check arguments
		if(starttime < endtime && resolution > 0)
		{
			if(courses == null)
				courses = new ArrayList<Long>();
			if(roles == null)
				roles = new ArrayList<Long>();
			
			//Set up db-connection
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
			
			//Calculate size of time intervalls
			double intervall = (endtime - starttime) / (resolution);
			
			//Create and initialize array for results
			Long[] resArr = new Long[resolution];
			for(int i =  0; i < resArr.length; i++)
				resArr[i] = 0L;
			
	
			
			//Create WHERE clause for course_ids
			String cou = "";
	
			for(int i = 0; i < courses.size(); i++)
				if(i == 0)
					cou += "course in ("+courses.get(i);
				else
					cou += "," + courses.get(i);
			if(cou != "")
				cou += ") AND";
			
			String rol = "";
			List<CourseUserMining> users = new ArrayList<CourseUserMining>();
			//Retrieve user-ids of users with specified roles in the courses
			if(roles.size() > 0)
			{
				
				for(int i = 0; i < roles.size(); i++)
					if(i == 0)
						rol += "role in ("+roles.get(i);
					else
						rol += "," + roles.get(i);
				if(rol != "")
					rol += ")";
				String query ="from CourseUserMining where "+ cou +" "+rol;
				users = (List<CourseUserMining>)dbHandler.performQuery(EQueryType.HQL, query);
			}			
			
			//Create WHERE clause for user_ids
			String use = "";
	
			for(int i = 0; i < users.size(); i++)
				if(i == 0)
					use += "user in ("+users.get(i).getUser().getId();
				else
					use += "," + users.get(i).getUser().getId();
			if(use != "")
				use += ") AND";			
			
			String query = "from ResourceLogMining x where "+ cou + " " + use + " x.timestamp between '" + starttime + "' AND '" + endtime +"' order by x.timestamp asc";
			List<ResourceLogMining> resource_logs = (List<ResourceLogMining>)dbHandler.performQuery(EQueryType.HQL, query);
			
			for(int i = 0 ; i < resource_logs.size(); i++)
			{
				Integer pos =	new Double((resource_logs.get(i).getTimestamp() - starttime) / intervall).intValue();
				if(pos>resolution-1)
					pos = resolution-1;
				else
					resArr[pos] = resArr[pos] + 1;
			}			
			Collections.addAll(list, resArr);
		}
        return new ResultList(list);
    }

}
