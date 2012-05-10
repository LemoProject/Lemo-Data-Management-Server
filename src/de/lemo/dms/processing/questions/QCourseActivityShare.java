package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import de.lemo.dms.core.LongTupelHelper;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResultList;

@QuestionID("courseactivityshare")
public class QCourseActivityShare extends Question{

	@Override
	protected List<ParameterMetaData<?>> createParamMetaData() {
		List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();
		Collections.<Parameter<?>> addAll( parameters,
		    Parameter.create("course_id","course","Id of the course."),
		    Parameter.create("starttime","Start time","Start time."),
		    Parameter.create("endtime","End time","End time")
        );
        return parameters;
	}
	
	 
    @SuppressWarnings("unchecked")
    @GET
    public ResultList getCourseActivityShare(@QueryParam("course_id") Long course_id, @QueryParam("starttime") Long starttime,
            @QueryParam("endtime") Long endtime) {
		
		HashMap<Long, LongTupelHelper> user_a = new HashMap<Long, LongTupelHelper>();
 
		if (endtime == null) endtime = new Date().getTime(); 
		if(starttime < endtime && course_id != null)
		{
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());

			String constraints = " where course=" + course_id +" and timestamp between "+ starttime + " AND " +endtime;
			
			List<ArrayList<ILogMining>> logs = new LinkedList<ArrayList<ILogMining>>();
            Collections.addAll( logs,
                (ArrayList<ILogMining>)dbHandler.performQuery(EQueryType.HQL, "from AssignmentLogMining" + constraints),
                (ArrayList<ILogMining>)dbHandler.performQuery(EQueryType.HQL, "from ResourceLogMining" + constraints),
                (ArrayList<ILogMining>)dbHandler.performQuery(EQueryType.HQL, "from WikiLogMining" + constraints),
                (ArrayList<ILogMining>)dbHandler.performQuery(EQueryType.HQL, "from QuizLogMining" + constraints),
                (ArrayList<ILogMining>)dbHandler.performQuery(EQueryType.HQL, "from ForumLogMining" + constraints),
                (ArrayList<ILogMining>)dbHandler.performQuery(EQueryType.HQL, "from ScormLogMining" + constraints)
            );

            for(ArrayList<ILogMining> log : logs) {
                for(int i = 0; i < log.size(); i++) {
                    long id = log.get(i).getUser().getId();
                    LongTupelHelper tuple = user_a.get(id);
                    if(tuple == null)
                        user_a.put(id, new LongTupelHelper(id));
                    else
                        tuple.incValue();
                }
            }
            
		}
		
		ArrayList<Long> result = new ArrayList<Long>();
		
		for(Iterator<LongTupelHelper> iter = user_a.values().iterator(); iter.hasNext();)
		{
			LongTupelHelper lth = iter.next();
			result.add(lth.getId());
			result.add(lth.getValue());
		}
		ResultList r = new ResultList(result);
		
		return r;
	}

}
