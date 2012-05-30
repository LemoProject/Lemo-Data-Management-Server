package de.lemo.dms.processing.questions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

@QuestionID("activecourseusers")
public class QCourseUsers {

	private static final String STARTTIME = "startTime";
	private static final String ENDTIME = "endTime";
	private static final String COURSE_IDS = "course_ids";

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
                Interval.create(long.class, STARTTIME, "Start time", "", 0L, now, 0L), 
                Interval.create(long.class, ENDTIME, "End time", "", 0L, now, now)
                );
        return parameters;
	}
	
	@GET
    public ResultListLongObject compute(
    		@QueryParam(COURSE_IDS) List<Long> courseIds, 
    		@QueryParam(STARTTIME) long startTime, 
    		@QueryParam(ENDTIME) long endTime) {
		
		HashMap<Long, Long> users = new HashMap<Long, Long>();
		//Check arguments
		if(startTime < endTime)
		{
			
			//Set up db-connection
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());

	        Session session = dbHandler.getSession(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
	        
	        Criteria criteria = session.createCriteria(ILogMining.class, "log");
	        
	        criteria.add(Restrictions.in("log.course.id", courseIds))
            .add(Restrictions.between("log.timestamp", startTime, endTime));
	        
	        ArrayList<ILogMining> logs = (ArrayList<ILogMining>) criteria.list();
	
	        for(int i = 0; i < logs.size() ; i++)
	        {
	        	if(logs.get(i).getUser() == null || users.get(logs.get(i).getUser().getId()) != null)
	        		continue;
	        	else
	        	{
	        		users.put(logs.get(i).getId(), logs.get(i).getId());
	        	}
	        }
			
		}
		
		return new ResultListLongObject(new ArrayList<Long>(users.values()));
	}
	
}
