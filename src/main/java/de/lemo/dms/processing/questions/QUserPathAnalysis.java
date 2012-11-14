package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.LOGOUT_FLAG;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.TYPES;
import static de.lemo.dms.processing.MetaParam.USER_IDS;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;
import de.lemo.dms.service.ELearnObjType;

@Path("userpathanalysis")
public class QUserPathAnalysis extends Question {


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

        Collections.sort(list);
        
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
                    String type = current.getClass().toString().substring(current.getClass().toString().lastIndexOf(".")+1, current.getClass().toString().lastIndexOf("Log"));
                    String cId = learnObjId + "-" + learnObjType;
                    // Determines whether it's a new path (no predecessor for
                    // current node) or not

                    UserPathObject knownPath;
                    if(predNode != null) 
                    {
                        String cIdPos = null;
                        if((knownPath = pathObjects.get(cId)) == null)
                        {
                            // If the node is new create entry in hash map
                            cIdPos = String.valueOf(pathObjects.size());
                            pathObjects.put(cId, new UserPathObject(cIdPos, current.getTitle(), 1L, type,
                                    Double.valueOf(current.getDuration()), 1L, 0L, 0L, 0L));
                        }
                        else
                        {
                            // If the node is already known, increase weight
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
                                type, Double.valueOf(current.getDuration()), 1L, 0L, 0L, 0L));
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

        for(UserPathObject pathEntry : pathObjects.values()) {

            UserPathObject path = pathEntry;
            path.setPathId(pathEntry.getPathId());
            nodes.add(new UserPathNode(path));
            String sourcePos = path.getId();

            for(Entry<String, Integer> linkEntry : pathEntry.getEdges().entrySet()) 
            {
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
