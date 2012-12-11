package de.lemo.dms.service;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.RoleMining;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedObject;
import de.lemo.dms.processing.resulttype.ResultList;
import de.lemo.dms.processing.resulttype.ResultListRoleObject;
import de.lemo.dms.processing.resulttype.ResultListStringObject;
import de.lemo.dms.processing.resulttype.RoleObject;

@Path("ratedobjects")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceRatedObjects extends BaseService {

	/**
	 * Returns a list of all learning objects within the specified courses that have a grade attribute (assignments, quizzes, scorms)
	 * 
	 * @param courses Course-ids
	 * 
	 * @return	ResultList with 4 String elements per object(class name, class prefix, id, title)
	 */
    @GET
    public ResultListStringObject getRatedObjects(@QueryParam(COURSE_IDS) List<Long> courses) {

    	ArrayList<String> res = new ArrayList<String>();
    	
    	try{
	        // Set up db-connection
	        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
	        Session session = dbHandler.getMiningSession();
	
	        Criteria criteria = session.createCriteria(IRatedObject.class, "obj");
	        criteria.add(Restrictions.in("obj.course.id", courses));
	        
	        ArrayList<IRatedObject> list = (ArrayList<IRatedObject>) criteria.list();
	        
	        for(IRatedObject obj : list)
	        {
	        	res.add(obj.getClass().getSimpleName());
	        	res.add(obj.getPrefix().toString());
	        	res.add(obj.getId() + "");
	        	res.add(obj.getTitle());
	        }
	        
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}

        return new ResultListStringObject(res);
    }

}
