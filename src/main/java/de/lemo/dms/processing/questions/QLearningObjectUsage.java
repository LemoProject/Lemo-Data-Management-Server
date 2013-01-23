package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.TYPES;
import static de.lemo.dms.processing.MetaParam.USER_IDS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.ELearningObjectType;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListResourceRequestInfo;

@Path("learningobjectusage")
public class QLearningObjectUsage extends Question {


    /**
     * Returns a list of resources and their respective statistics of usage.
     * 
     * @see ELearningObjectType
     * 
     * @param courseIds
     *            List of course-identifiers
     * @param userIds
     *            List of user-identifiers
     * @param types
     *            List of learn object types (see ELearnObjType)
     * @param startTime
     *            LongInteger time stamp
     * @param endTime
     *            LongInteger time stamp
     * @return
     */
    @POST
    public ResultListResourceRequestInfo compute(
            @FormParam(COURSE_IDS) List<Long> courseIds,
            @FormParam(USER_IDS) List<Long> userIds,
            @FormParam(TYPES) List<String> types,
            @FormParam(START_TIME) Long startTime,
            @FormParam(END_TIME) Long endTime) {

        logger.info("Params: " + courseIds + "/" + userIds + "/" + types + "/" + startTime
                + "/" + endTime);

        ResultListResourceRequestInfo result = new ResultListResourceRequestInfo();
        // DB-initialization
        IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
        Session session = dbHandler.getMiningSession();
        
        // Create criteria for log-file-search
        Criteria criteria = session.createCriteria(ILogMining.class, "log");

        if(startTime == null || endTime == null || startTime >= endTime) {
            logger.info("Invalid time params.");
            return null;
        }
        
        if(types!=null && types.size()>0)
    		for(int i=0; i<types.size();i++){
    			logger.info("LO Request - LO Selection: "+types.get(i));
    		}
    	else logger.info("LO Request - LO Selection: NO Items selected ");

        criteria.add(Restrictions.between("log.timestamp", startTime, endTime));

        if(!courseIds.isEmpty())
            criteria.add(Restrictions.in("log.course.id", courseIds));

        if(!userIds.isEmpty())
            criteria.add(Restrictions.in("log.user.id", userIds));

        @SuppressWarnings("unchecked")
        List<ILogMining> list = criteria.list();

        logger.info("Total matched entries: " + list.size());
        
        HashMap<String, ArrayList<Long>> requests = new HashMap<String, ArrayList<Long>>();
        
        for(ILogMining ilo : list)
        {
        	String obType = ilo.getClass().toString().substring(ilo.getClass().toString().lastIndexOf(".") + 1, ilo.getClass().toString().lastIndexOf("Log"));
        	
        	if(types == null || types.size() == 0 || types.contains(obType.toUpperCase()))
        	{
	        	String id = ilo.getPrefix() +"_"+ ilo.getLearnObjId() + "?" + obType +"$"+ ilo.getTitle();
	        	if(requests.get(id) == null)
	        	{
	        		ArrayList<Long> al = new ArrayList<Long>();
	        		al.add(ilo.getUser().getId());
	        		requests.put(id, al);
	        	}
	        	else
	        		requests.get(id).add(ilo.getUser().getId());
        	}
        }
        Long id = 0L;
        for(Entry<String, ArrayList<Long>> item : requests.entrySet())
        {
        	String title = item.getKey().substring(item.getKey().indexOf("$") + 1);
        	String type = item.getKey().substring(item.getKey().indexOf("?") + 1, item.getKey().indexOf("$"));
        	ResourceRequestInfo rri = new ResourceRequestInfo(id, ELearningObjectType.valueOf(type.toUpperCase()), Long.valueOf(item.getValue().size()), Long.valueOf(new HashSet<Long>(item.getValue()).size()), title, 0L);
        	result.add(rri);
        }
        logger.info("Total returned entries: " + result.getResourceRequestInfos().size());
        
        return result;
    }

}
