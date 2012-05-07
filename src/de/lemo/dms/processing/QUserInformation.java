package de.lemo.dms.processing;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;

@QuestionID("userinformation")
@Path("/information")
public class QUserInformation extends Question{

	@Override
	protected List<ParameterMetaData<?>> createParamMetaData() {
		// TODO Auto-generated method stub
		List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();
		
		Collections.<Parameter<?>> addAll( parameters,
		    Parameter.create("user_id","User","ID of the user."),
		    Parameter.create("course_count","Course count","Number of courses that should be displayed."),
		    Parameter.create("course_offset","Course offset","")
        );
        
        return parameters;
	}
	
	@GET
	@Path("/getCoursesByUser")
	@Produces("application/json")
    public ResultListCourseObject getCoursesByUser(@QueryParam("user_id") Long id, @QueryParam("course_count") Long count,
            @QueryParam("course_offset") Long offset) {
		
		ArrayList<CourseObject> courses = new ArrayList<CourseObject>();
		
		//Set up db-connection
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
		@SuppressWarnings("unchecked")
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
		
		if(cu.size() > 0 )
		{
			@SuppressWarnings("unchecked")
            ArrayList<CourseMining> ci = (ArrayList<CourseMining>) dbHandler.performQuery(EQueryType.HQL, "from CourseMining where id in " + query);
			for(int i = 0; i < ci.size(); i++)
			{
				@SuppressWarnings("unchecked")
                ArrayList<Long> parti = (ArrayList<Long>) dbHandler.performQuery(EQueryType.HQL, "Select count(DISTINCT user) from CourseUserMining where course="+ci.get(i).getId());
				@SuppressWarnings("unchecked")
                ArrayList<Long> latest = (ArrayList<Long>) dbHandler.performQuery(EQueryType.HQL, "Select max(timestamp) FROM ResourceLogMining x WHERE x.course="+ci.get(i).getId());
				@SuppressWarnings("unchecked")
				ArrayList<Long> first = (ArrayList<Long>) dbHandler.performQuery(EQueryType.HQL, "Select min(timestamp) FROM ResourceLogMining x WHERE x.course="+ci.get(i).getId());
				Long c_pa = 0L;
				if(parti.size() > 0 && parti.get(0) != null)
					c_pa = parti.get(0);
				Long c_la = 0L;
				if(latest.size() > 0 && latest.get(0) != null)
					c_la = latest.get(0);
				Long c_fi = 0L;
				if(first.size() > 0 && first.get(0) != null)
					c_fi = first.get(0);
				CourseObject co = new CourseObject(ci.get(i).getId(), ci.get(i).getShortname(), ci.get(i).getTitle(), c_pa, c_la, c_fi);
				courses.add(co);
			}		
		}
		return new ResultListCourseObject(courses);
	}
	
	
	@GET
	@Path("/getcoursedetails")
	@Produces("application/json")
    public CourseObject getCourseDetails(@QueryParam("course_id") Long id) {
		
		//Set up db-connection
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
		
		ArrayList<CourseMining> ci = (ArrayList<CourseMining>) dbHandler.performQuery(EQueryType.HQL, "from CourseMining where id = " + id);
		CourseObject co = new CourseObject();
		if(ci!=null && ci.size()>=1){
			@SuppressWarnings("unchecked")
            ArrayList<Long> parti = (ArrayList<Long>) dbHandler.performQuery(EQueryType.HQL, "Select count(DISTINCT user) from CourseUserMining where course="+ci.get(0).getId());
			@SuppressWarnings("unchecked")
            ArrayList<Long> latest = (ArrayList<Long>) dbHandler.performQuery(EQueryType.HQL, "Select max(timestamp) FROM ResourceLogMining x WHERE x.course="+ci.get(0).getId());
			@SuppressWarnings("unchecked")
			ArrayList<Long> first = (ArrayList<Long>) dbHandler.performQuery(EQueryType.HQL, "Select min(timestamp) FROM ResourceLogMining x WHERE x.course="+ci.get(0).getId());
			Long c_pa = 0L;
			if(parti.size() > 0 && parti.get(0) != null)
				c_pa = parti.get(0);
			Long c_la = 0L;
			if(latest.size() > 0 && latest.get(0) != null)
				c_la = latest.get(0);
			Long c_fi = 0L;
			if(first.size() > 0 && first.get(0) != null)
				c_fi = first.get(0);
			co = new CourseObject(ci.get(0).getId(), ci.get(0).getShortname(), ci.get(0).getTitle(), c_pa, c_la, c_fi);
		}		
		return co;
	}
	
	
	@GET
	@Path("/getcoursesdetails")
	@Produces("application/json")
    public ResultListCourseObject getCoursesDetails(@QueryParam("course_id") List<Long> ids) {
		
		ArrayList<CourseObject> courses = new ArrayList<CourseObject>();
		
		//Set up db-connection
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
		String query = "";
		for(int i = 0; i < ids.size(); i++)
		{
			if(i==0)
				query += "("+ids.get(i);
			else
				query += ","+ids.get(i);
			if(i == ids.size()-1)
				query += ")";
		}
		
		ArrayList<CourseMining> ci = (ArrayList<CourseMining>) dbHandler.performQuery(EQueryType.HQL, "from CourseMining where id in " + query);
		
		for(int i = 0; i < ci.size(); i++)
		{
			@SuppressWarnings("unchecked")
            ArrayList<Long> parti = (ArrayList<Long>) dbHandler.performQuery(EQueryType.HQL, "Select count(DISTINCT user) from CourseUserMining where course="+ci.get(i).getId());
			@SuppressWarnings("unchecked")
            ArrayList<Long> latest = (ArrayList<Long>) dbHandler.performQuery(EQueryType.HQL, "Select max(timestamp) FROM ResourceLogMining x WHERE x.course="+ci.get(i).getId());
			@SuppressWarnings("unchecked")
			ArrayList<Long> first = (ArrayList<Long>) dbHandler.performQuery(EQueryType.HQL, "Select min(timestamp) FROM ResourceLogMining x WHERE x.course="+ci.get(i).getId());
			Long c_pa = 0L;
			if(parti.size() > 0 && parti.get(0) != null)
				c_pa = parti.get(0);
			Long c_la = 0L;
			if(latest.size() > 0 && latest.get(0) != null)
				c_la = latest.get(0);
			Long c_fi = 0L;
			if(first.size() > 0 && first.get(0) != null)
				c_fi = first.get(0);
			CourseObject co = new CourseObject(ci.get(i).getId(), ci.get(i).getShortname(), ci.get(i).getTitle(), c_pa, c_la, c_fi);
			courses.add(co);
		}			
		return new ResultListCourseObject(courses);
	}

}
