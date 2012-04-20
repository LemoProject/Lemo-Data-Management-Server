package de.lemo.dms.processing;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultList;

public class QUserInformation extends Question{

	@Override
	protected List<Parameter<?>> getParameterDescription() {
		// TODO Auto-generated method stub
		List<Parameter<?>> parameters = new LinkedList<Parameter<?>>();
		Parameter<Long> id = new Parameter<Long>("user_id","User","ID of the user.");
		Parameter<Long> count = new Parameter<Long>("course_count","Course count","Number of courses that should be displayed.");
		Parameter<Long> offset = new Parameter<Long>("course_offset","Course offset","");
        Collections.<Parameter<?>> addAll(parameters, id, count, offset);
        
        return parameters;
	}
	
	@GET
    public ResultList getCoursesByUser(@QueryParam("user_id") Long id, @QueryParam("course_count") Long count,
            @QueryParam("course_offset") Long offset) {
		
		ArrayList<CourseObject> courses = new ArrayList<CourseObject>();
		
		//Set up db-connection
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
		ArrayList<Long> cu = (ArrayList<Long>)dbHandler.performQuery(EQueryType.SQL, "Select course_id from course_user where user_id=" + id);
		
		String query = "";
		for(int i = 0; i < cu.size(); i++)
		{
			if(i==0)
				query += "("+cu.get(i);
			else
				query += ","+cu.get(i);
			if(i == cu.size()-1)
				query += ")";
		}
		
		
		ArrayList<CourseMining> ci = (ArrayList<CourseMining>) dbHandler.performQuery(EQueryType.HQL, "from CourseMining where id in " + query);
		for(int i = 0; i < ci.size(); i++)
		{
			ArrayList<?> parti = (ArrayList<?>) dbHandler.performQuery(EQueryType.SQL, "Select count(DISTINCT user_id) from course_user where course_id="+ci.get(i).getId());
			ArrayList<?> latest = (ArrayList<?>) dbHandler.performQuery(EQueryType.SQL, "Select max(timestamp) from resource_log where course_id="+ci.get(i).getId());
			CourseObject co = new CourseObject(ci.get(i).getId(), ci.get(i).getShortname(), ci.get(i).getTitle(), ((BigInteger)parti.get(0)).longValue(), ((BigInteger)latest.get(0)).longValue() );
			courses.add(co);
		}		
		return new ResultList(courses);
	}

}
