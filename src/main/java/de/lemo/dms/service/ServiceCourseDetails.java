package de.lemo.dms.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;

@Path("courses")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceCourseDetails extends BaseService {

    @GET
    @Path("{cid}")
    public CourseObject getCourseDetails(@PathParam("cid") Long id) {
           
        // Set up db-connection
        Session session = dbHandler.getMiningSession();

        @SuppressWarnings("unchecked")
        ArrayList<CourseMining> ci = (ArrayList<CourseMining>) dbHandler.performQuery(session, EQueryType.HQL,
                "from CourseMining where id = " + id);
        CourseObject co = new CourseObject();
        if(ci != null && ci.size() >= 1) {
            @SuppressWarnings("unchecked")
            ArrayList<Long> parti = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
                    "Select count(DISTINCT user) from CourseUserMining where course=" + ci.get(0).getId());
            @SuppressWarnings("unchecked")
            ArrayList<Long> latest = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
                    "Select max(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(0).getId());
            @SuppressWarnings("unchecked")
            ArrayList<Long> first = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
                    "Select min(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(0).getId());
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
        dbHandler.closeSession(session);
        return co;
    }

    @GET
    public ResultListCourseObject getCoursesDetails(@QueryParam("course_id") List<Long> ids) {

        ArrayList<CourseObject> courses = new ArrayList<CourseObject>();
        
        if(ids.isEmpty())
            return new ResultListCourseObject(courses);

        // Set up db-connection
        Session session = dbHandler.getMiningSession();

        String query = "";
        for(int i = 0; i < ids.size(); i++) {
            if(i == 0)
                query += "(" + ids.get(i);
            else
                query += "," + ids.get(i);
            if(i == ids.size() - 1)
                query += ")";
        }

        @SuppressWarnings("unchecked")
        ArrayList<CourseMining> ci = (ArrayList<CourseMining>) dbHandler.performQuery(session, EQueryType.HQL,
                "from CourseMining where id in " + query);

        for(int i = 0; i < ci.size(); i++) {
            @SuppressWarnings("unchecked")
            ArrayList<Long> parti = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
                    "Select count(DISTINCT user) from CourseUserMining where course=" + ci.get(i).getId());
            @SuppressWarnings("unchecked")
            ArrayList<Long> latest = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
                    "Select max(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(i).getId());
            @SuppressWarnings("unchecked")
            ArrayList<Long> first = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
                    "Select min(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(i).getId());
            Long c_pa = 0L;
            if(parti.size() > 0 && parti.get(0) != null)
                c_pa = parti.get(0);
            Long c_la = 0L;
            if(latest.size() > 0 && latest.get(0) != null)
                c_la = latest.get(0);
            Long c_fi = 0L;
            if(first.size() > 0 && first.get(0) != null)
                c_fi = first.get(0);
            CourseObject co = new CourseObject(ci.get(i).getId(), ci.get(i).getShortname(), ci.get(i).getTitle(), c_pa,
                    c_la, c_fi);
            courses.add(co);
        }
        return new ResultListCourseObject(courses);
    }

}
