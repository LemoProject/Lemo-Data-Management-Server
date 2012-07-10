package de.lemo.dms.processing.questions;

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
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;
import de.lemo.dms.service.ELearnObjType;

@QuestionID("userpathanalysis")
public class QUserPathAnalysis extends Question {

    private static final String STARTTIME = "start_time";
    private static final String ENDTIME = "end_time";
    private static final String USER_IDS = "user_ids";
    private static final String COURSE_IDS = "course_ids";
    private static final String TYPES = "types";
    private static final String LOGOUT_FLAG = "logout_flag";

    protected List<ParameterMetaData<?>> createParamMetaData() {
        List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();

        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
        List<?> latest = dbHandler.performQuery(EQueryType.SQL, "Select max(timestamp) from resource_log");
        Long now = System.currentTimeMillis() / 1000;

        if(latest.size() > 0)
            now = ((BigInteger) latest.get(0)).longValue();

        Collections.<ParameterMetaData<?>>
                addAll(parameters,
                       Parameter.create(USER_IDS, "Users", "List of users-ids."),
                       Parameter.create(COURSE_IDS, "Courses", "List of course-ids."),
                       Parameter.create(TYPES, "Types", "Types of learning objects"),
                       Parameter.create(LOGOUT_FLAG, "Logout flag", "Determines whether user-logouts cut user-paths"),
                       Interval.create(Long.class, STARTTIME, "Start time", "", 0L, now, 0L),
                       Interval.create(Long.class, ENDTIME, "End time", "", 0L, now, now)
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
            @FormParam(LOGOUT_FLAG) boolean considerLogouts,
            @FormParam(STARTTIME) Long startTime,
            @FormParam(ENDTIME) Long endTime) {

        logger.info("Params: " + courseIds + "/" + userIds + "/" + types + "/" + considerLogouts + "/" + startTime
                + "/" + endTime);

        // DB-initialization
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getSession(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());

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
                        if((knownPath = pathObjects.get(cId)) == null)
                        {
                            // If the node is new create entry in hash map
                            cIdPos = String.valueOf(pathObjects.size());
                            pathObjects.put(cId, new UserPathObject(cIdPos, current.getTitle(), 1L, learnObjType,
                                    Double.valueOf(current.getDuration()), 1L));
                        }
                        else
                        {
                            // If the node is already known, increment weight
                            pathObjects.get(cId).increaseWeight(Double.valueOf(current.getDuration()));
                            cIdPos = knownPath.getId();
                        }

                        // Increment or create predecessor edge
                        pathObjects.get(predNode).addEdgeOrIncrement(cIdPos);
                    }
                    else if(pathObjects.get(cId) == null)
                    {
                        String cIdPos = String.valueOf(pathObjects.size());
                        pathObjects.put(cId, new UserPathObject(cIdPos, current.getTitle(), 1L,
                                current.getClass().toString(), Double.valueOf(current.getDuration()), 1L));
                    }
                    else
                        pathObjects.get(cId).increaseWeight(Double.valueOf(current.getDuration()));

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
            nodes.add(new UserPathNode(path));
            String sourcePos = path.getId();

            for(Entry<String, Integer> linkEntry : pathEntry.getValue().getEdges().entrySet()) {
                UserPathLink link = new UserPathLink();
                link.setSource(Long.parseLong(sourcePos));
                link.setTarget(Long.parseLong(linkEntry.getKey()));
                link.setValue(linkEntry.getValue());
                if(link.getSource() != link.getTarget())
                    links.add(link);
            }
        }
        return new ResultListUserPathGraph(nodes, links);
    }

}