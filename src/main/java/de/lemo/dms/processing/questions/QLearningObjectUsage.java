package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.parameter.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.parameter.MetaParam.END_TIME;
import static de.lemo.dms.processing.parameter.MetaParam.START_TIME;
import static de.lemo.dms.processing.parameter.MetaParam.TYPES;
import static de.lemo.dms.processing.parameter.MetaParam.USER_IDS;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.MetaParam;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;
import de.lemo.dms.service.ELearnObjType;

@QuestionID("learningobjectusage")
public class QLearningObjectUsage extends Question {

    protected List<MetaParam<?>> createParamMetaData() {
        List<MetaParam<?>> parameters = new LinkedList<MetaParam<?>>();

        Session session = dbHandler.getMiningSession();
        List<?> latest = dbHandler.performQuery(session,EQueryType.SQL, "Select max(timestamp) from resource_log");
        dbHandler.closeSession(session); 
        Long now = System.currentTimeMillis() / 1000;

        if(latest.size() > 0)
            now = ((BigInteger) latest.get(0)).longValue();

        Collections.<MetaParam<?>>
                addAll(parameters,
                       Parameter.create(USER_IDS, "Users", "List of users-ids."),
                       Parameter.create(COURSE_IDS, "Courses", "List of course-ids."),
                       Parameter.create(TYPES, "Types", "Types of learning objects"),
                       Interval.create(Long.class, START_TIME, "Start time", "", 0L, now, 0L),
                       Interval.create(Long.class, END_TIME, "End time", "", 0L, now, now)
                );
        return parameters;
    }

    /**
     * Returns a list of resources and their respective statistics of usage.
     * 
     * @see ELearnObjType
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
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();
        
        // Create criteria for log-file-search
        Criteria criteria = session.createCriteria(ILogMining.class, "log");

        if(startTime == null || endTime == null || startTime >= endTime) {
            logger.info("Invalid time params.");
            return null;
        }

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
        	
        	if(types == null || types.size() == 0 || types.contains(obType))
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
        	ResourceRequestInfo rri = new ResourceRequestInfo(id, ELearnObjType.valueOf(type.toUpperCase()), Long.valueOf(item.getValue().size()), Long.valueOf(new HashSet<Long>(item.getValue()).size()), title, 0L);
        	result.add(rri);
        }
        return result;
    }

}
