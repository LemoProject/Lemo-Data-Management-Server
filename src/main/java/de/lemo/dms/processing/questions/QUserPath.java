package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.USER_IDS;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;


@Path("userpath")
public class QUserPath extends Question {

    @POST
    public JSONObject compute(
            @FormParam(COURSE_IDS) List<Long> courseIds,
            @FormParam(USER_IDS) List<Long> userIds,
            @FormParam(START_TIME) Long startTime,
            @FormParam(END_TIME) Long endTime) throws JSONException {

        /*
         * This is the first usage of Criteria API in the project and therefore a bit more documented than usual, to
         * serve as example implementation for other analyses.
         */

        if(endTime == null) {
            endTime = new Date().getTime();
        }

        if(startTime >= endTime || courseIds.isEmpty()) {
            return null;
        }

        Stopwatch stopWatch = new Stopwatch();

        stopWatch.start();

        /* A criteria is created from the session. */
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getMiningSession();
        
        /*
         * HQL-like equivalent: "Select from ILogMining". ILogMining is an interface, so Hibernate will load all classes
         * implementing it. The string argument is an alias.
         */
        Criteria criteria = session.createCriteria(ILogMining.class, "log");

        /*
         * Restrictions equivalent to HQL where:
         * 
         * where course in ( ... ) and timestamp between " + startTime + " AND " + endTime;
         */
        criteria.add(Restrictions.in("log.course.id", courseIds))
                .add(Restrictions.between("log.timestamp", startTime, endTime))
                .add(Restrictions.eq("log.action", "view"));

        /* Calling list() eventually performs the query */
        @SuppressWarnings("unchecked")
        List<ILogMining> logs = criteria.list();

        Set<Long/* user id */> users = Sets.newHashSet();
        for(ILogMining log : logs) {
            UserMining user = log.getUser();
            if(user == null)
                continue;
            users.add(user.getId());
        }

        logger.info("Found " + users.size() + " actions. " + +stopWatch.elapsedTime(TimeUnit.SECONDS));

        Criteria exdendedCriteria = session.createCriteria(ILogMining.class, "log");
        exdendedCriteria.add(Restrictions.in("log.user.id", users))
                .add(Restrictions.between("log.timestamp", startTime, endTime))
                .add(Restrictions.eq("log.action", "view"));

        @SuppressWarnings("unchecked")
        List<ILogMining> extendedLogs = exdendedCriteria.list();

        Map<Long /* user id */, List<Long> /* course ids */> userPaths = Maps.newHashMap();

        logger.info("Paths fetched: " + extendedLogs.size() + ". " + stopWatch.elapsedTime(TimeUnit.SECONDS));

        for(ILogMining log : extendedLogs) {
            UserMining user = log.getUser();
            if(user == null || log.getCourse() == null)
                continue;
            long userId = log.getUser().getId();

            List<Long> courseIDs = userPaths.get(userId);
            if(courseIDs == null) {
                courseIDs = Lists.newArrayList();
                userPaths.put(userId, courseIDs);
            }
            courseIDs.add(log.getCourse().getId());
        }

        logger.info("userPaths: " + userPaths.size());

        Map<Long /* course id */, List<JSONObject> /* edge */> coursePaths = Maps.newHashMap();

        for(Entry<Long, List<Long>> userEntry : userPaths.entrySet()) {

            JSONObject lastPath = null;
            Long userID = userEntry.getKey();

            for(Long courseID : userEntry.getValue()) {
                List<JSONObject> edge = coursePaths.get(courseID);
                if(edge == null) {
                    edge = Lists.newArrayList();
                    coursePaths.put(courseID, edge);
                }
                JSONObject path = new JSONObject();

                path.put("user", userID);

                if(lastPath != null) {
                    lastPath.put("to", courseID);
                }
                lastPath = path;
            }
        }
        stopWatch.stop();
        logger.info("coursePaths: " + coursePaths.size());
        logger.info("Total Fetched log entries: " + (logs.size() + extendedLogs.size()) + " log entries."
                + stopWatch.elapsedTime(TimeUnit.SECONDS));

        /*
         * TODO Shouldn't we close the session at some point?
         */

        JSONObject result = new JSONObject();
        JSONArray nodes = new JSONArray();
        for(Entry<Long, List<JSONObject>> courseEntry : coursePaths.entrySet()) {
            JSONObject node = new JSONObject();
            node.put("id", courseEntry.getKey());
            node.put("wheight", courseEntry.getValue().size());
            node.put("edges", new JSONArray(courseEntry.getValue()));
            if(courseIds.contains(courseEntry.getKey()))
            	node.put("groupId", 0);
            else
            	node.put("groupId", 1);
            nodes.put(node);
        }

        result.put("nodes", nodes);
        return result;
    }
}
