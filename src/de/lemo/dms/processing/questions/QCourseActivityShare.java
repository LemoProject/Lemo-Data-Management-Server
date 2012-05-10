package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import de.lemo.dms.core.LongTupelHelper;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResultList;

@QuestionID("courseactivityshare")
public class QCourseActivityShare extends Question{

	@Override
	protected List<ParameterMetaData<?>> createParamMetaData() {
		// TODO Auto-generated method stub
		List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();
		
		Collections.<Parameter<?>> addAll( parameters,
		    Parameter.create("course_id","course","Id of the course."),
		    Parameter.create("starttime","Start time","Start time."),
		    Parameter.create("endtime","End time","End time")
        );
        
        return parameters;
	}
	
	@GET
    public ResultList getCourseActivityShare(@QueryParam("course_id") Long course_id, @QueryParam("starttime") Long starttime,
            @QueryParam("endtime") Long endtime) {
		
		HashMap<Long, LongTupelHelper> user_a = new HashMap<Long, LongTupelHelper>();
 
		if (endtime == null) endtime = new Date().getTime(); 
		if(starttime < endtime && course_id != null)
		{
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());

			ArrayList<AssignmentLogMining> ass = (ArrayList<AssignmentLogMining>)dbHandler.performQuery(EQueryType.HQL, "from AssignmentLogMining where course=" + course_id +" and timestamp between "+ starttime + " AND " +endtime);
			ArrayList<ResourceLogMining> res = (ArrayList<ResourceLogMining>)dbHandler.performQuery(EQueryType.HQL, "from ResourceLogMining where course=" + course_id +" and timestamp between "+ starttime + " AND " +endtime);
			ArrayList<WikiLogMining> wik = (ArrayList<WikiLogMining>)dbHandler.performQuery(EQueryType.HQL, "from WikiLogMining where course=" + course_id +" and timestamp between "+ starttime + " AND " +endtime);
			ArrayList<QuizLogMining> qui = (ArrayList<QuizLogMining>)dbHandler.performQuery(EQueryType.HQL, "from QuizLogMining where course=" + course_id +" and timestamp between "+ starttime + " AND " +endtime);
			ArrayList<ForumLogMining> fom = (ArrayList<ForumLogMining>)dbHandler.performQuery(EQueryType.HQL, "from ForumLogMining where course=" + course_id +" and timestamp between "+ starttime + " AND " +endtime);
			ArrayList<ScormLogMining> sco = (ArrayList<ScormLogMining>)dbHandler.performQuery(EQueryType.HQL, "from ScormLogMining where course=" + course_id +" and timestamp between "+ starttime + " AND " +endtime);
			
			
			for(int i = 0 ; i < ass.size(); i++)
				if(user_a.get(ass.get(i).getUser().getId()) == null)
					user_a.put(ass.get(i).getUser().getId(), new LongTupelHelper(ass.get(i).getUser().getId()));
				else
				{
					Long l  = user_a.get(ass.get(i).getUser().getId()).getValue();
					l++;
					user_a.put(ass.get(i).getUser().getId(), new LongTupelHelper(ass.get(i).getUser().getId(), l));
				}
			
			for(int i = 0 ; i < res.size(); i++)
				if(user_a.get(res.get(i).getUser().getId()) == null)
					user_a.put(res.get(i).getUser().getId(), new LongTupelHelper(res.get(i).getUser().getId()));
				else
				{
					Long l  = user_a.get(res.get(i).getUser().getId()).getValue();
					l++;
					user_a.put(res.get(i).getUser().getId(), new LongTupelHelper(res.get(i).getUser().getId(), l));
				}
			
			for(int i = 0 ; i < wik.size(); i++)
				if(user_a.get(wik.get(i).getUser().getId()) == null)
					user_a.put(wik.get(i).getUser().getId(), new LongTupelHelper(wik.get(i).getUser().getId()));
				else
				{
					Long l  = user_a.get(wik.get(i).getUser().getId()).getValue();
					l++;
					user_a.put(wik.get(i).getUser().getId(), new LongTupelHelper(wik.get(i).getUser().getId(), l));
				}
			
			for(int i = 0 ; i < fom.size(); i++)
				if(user_a.get(fom.get(i).getUser().getId()) == null)
					user_a.put(fom.get(i).getUser().getId(), new LongTupelHelper(fom.get(i).getUser().getId()));
				else
				{
					Long l  = user_a.get(fom.get(i).getUser().getId()).getValue();
					l++;
					user_a.put(fom.get(i).getUser().getId(), new LongTupelHelper(fom.get(i).getUser().getId(), l));
				}
			
			for(int i = 0 ; i < qui.size(); i++)
				if(user_a.get(qui.get(i).getUser().getId()) == null)
					user_a.put(qui.get(i).getUser().getId(), new LongTupelHelper(qui.get(i).getUser().getId()));
				else
				{
					Long l  = user_a.get(qui.get(i).getUser().getId()).getValue();
					l++;
					user_a.put(qui.get(i).getUser().getId(), new LongTupelHelper(qui.get(i).getUser().getId(), l));
				}
			
			for(int i = 0 ; i < sco.size(); i++)
				if(user_a.get(sco.get(i).getUser().getId()) == null)
					user_a.put(sco.get(i).getUser().getId(), new LongTupelHelper(sco.get(i).getUser().getId()));
				else
				{
					Long l  = user_a.get(sco.get(i).getUser().getId()).getValue();
					l++;
					user_a.put(sco.get(i).getUser().getId(), new LongTupelHelper(sco.get(i).getUser().getId(), l));
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
