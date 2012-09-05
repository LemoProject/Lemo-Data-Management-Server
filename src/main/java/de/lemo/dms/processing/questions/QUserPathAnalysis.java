package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.parameter.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.parameter.MetaParam.END_TIME;
import static de.lemo.dms.processing.parameter.MetaParam.LOGOUT_FLAG;
import static de.lemo.dms.processing.parameter.MetaParam.START_TIME;
import static de.lemo.dms.processing.parameter.MetaParam.TYPES;
import static de.lemo.dms.processing.parameter.MetaParam.USER_IDS;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;
import de.lemo.dms.service.ELearnObjType;

@QuestionID("userpathanalysis")
public class QUserPathAnalysis extends Question {

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
                       Parameter.create(LOGOUT_FLAG, "Logout flag", "Determines whether user-logouts cut user-paths"),
                       Interval.create(Long.class, START_TIME, "Start time", "", 0L, now, 0L),
                       Interval.create(Long.class, END_TIME, "End time", "", 0L, now, now)
                );
        return parameters;
    }

    /**
     * Returns a list of Nodes and edges, representing the user-navigation
     * matching the requirements given by the parameters.
     * 
     * @see ELearnObjType
     * 
     * @param courseIds
     *            List of course-identifiers
     * @param userIds
     *            List of user-identifiers
     * @param types
     *            List of learn object types (see ELearnObjType)
     * @param considerLogouts
     *            If user-paths should be cut when a logout appears this must be
     *            set to "true".
     * @param startTime
     *            LongInteger time stamp
     * @param endTime
     *            LongInteger time stamp
     * @return
     */
    @POST
    public ResultListUserPathGraph compute(
            @FormParam(COURSE_IDS) List<Long> courseIds,
            @FormParam(USER_IDS) List<Long> userIds,
            @FormParam(TYPES) List<String> types,
            @FormParam(LOGOUT_FLAG) Boolean considerLogouts,
            @FormParam(START_TIME) Long startTime,
            @FormParam(END_TIME) Long endTime) {

        logger.info("Params: " + courseIds + "/" + userIds + "/" + types + "/" + considerLogouts + "/" + startTime
                + "/" + endTime);

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

        // Map for UserPathObjects
        LinkedHashMap<String, UserPathObject> pathObjects = Maps.newLinkedHashMap();

        // Map for user histories
        HashMap<Long, List<ILogMining>> userHis = Maps.newHashMap();

        int skippedUsers = 0;
        // Generate user histories
        for(ILogMining log : list) {
            if(log.getUser() == null) {
                skippedUsers++;
                continue;
            }
            boolean typeOk = true;
            if(!types.isEmpty()) {
                typeOk = false;
                for(String type : types) {
                    // Check if ILog-object has acceptable learningObjectType
                    if(ELearnObjType.validate(log, type))
                    {
                        typeOk = true;
                        break;
                    }
                }
            }

            if(typeOk)
                if(userHis.get(log.getUser().getId()) == null)
                {
                    // If user is new create a new entry in the hash map and add
                    // log item
                    userHis.put(log.getUser().getId(), new ArrayList<ILogMining>());
                    userHis.get(log.getUser().getId()).add(log);
                }
                else
                    userHis.get(log.getUser().getId()).add(log);
        }

        logger.info("Skipped entries with missing user id: " + skippedUsers);

        int skippedLogs = 0;
        // Generate paths from user histories
        for(List<ILogMining> l : userHis.values()) {
            String predNode = null;
            Long pathId = 0L;
            for(int i = 0; i < l.size(); i++)
                if(l.get(i) != null && l.get(i).getUser() != null)
                {
                    ILogMining current = l.get(i);

                    Long learnObjId = current.getLearnObjId();
                    if(learnObjId == null) {
                        skippedLogs++;
                        continue;
                    }
                    String learnObjType = ELearnObjType.valueOf(current).toString();
                    String cId = learnObjId + "-" + learnObjType;
                    // Determines whether it's a new path (no predecessor for
                    // current node) or not

                    UserPathObject knownPath;
                    if(predNode != null) {
                        String cIdPos = null;
                        if((knownPath = pathObjects.get(pathId + "_" + cId)) == null)
                        {
                            // If the node is new create entry in hash map
                            cIdPos = String.valueOf(pathObjects.size());
                            pathObjects.put(pathId + "_" + cId, new UserPathObject(cIdPos, current.getTitle(), 1L, learnObjType,
                                    Double.valueOf(current.getDuration()), 1L, pathId));
                        }
                        else
                        {
                            // If the node is already known, increase weight
                            pathObjects.get(pathId + "_" + cId).increaseWeight(Double.valueOf(current.getDuration()));
                            cIdPos = knownPath.getId();
                        }

                        // Increment or create predecessor edge
                        pathObjects.get(pathId + "_" + predNode).addEdgeOrIncrement(cIdPos);
                    }
                    else if(pathObjects.get(pathId + "_" + cId) == null)
                    {
                        String cIdPos = String.valueOf(pathObjects.size());
                        pathObjects.put(pathId + "_" + cId, new UserPathObject(cIdPos, current.getTitle(), 1L,
                                current.getClass().toString(), Double.valueOf(current.getDuration()), 1L, pathId));
                    }
                    else
                        pathObjects.get(pathId + "_" + cId).increaseWeight(Double.valueOf(current.getDuration()));

                    if(considerLogouts && current.getDuration() == -1L)
                        predNode = null;
                    else
                        predNode = cId;
                }
        }
        logger.info("Skipped entries with missing learn object id: " + skippedLogs);

        ArrayList<UserPathNode> nodes = Lists.newArrayList();
        ArrayList<UserPathLink> links = Lists.newArrayList();

        for(Entry<String, UserPathObject> pathEntry : pathObjects.entrySet()) {

            UserPathObject path = pathEntry.getValue();
            path.setPathId(pathEntry.getValue().getPathId());
            nodes.add(new UserPathNode(path));
            String sourcePos = path.getId();

            for(Entry<String, Integer> linkEntry : pathEntry.getValue().getEdges().entrySet()) {
                UserPathLink link = new UserPathLink();
                link.setSource(sourcePos);
                link.setPathId(path.getPathId());
                link.setTarget(linkEntry.getKey());
                link.setValue(String.valueOf(linkEntry.getValue()));
                if(link.getSource() != link.getTarget())
                    links.add(link);
            }
        }
        return new ResultListUserPathGraph(nodes, links);
    }

}
