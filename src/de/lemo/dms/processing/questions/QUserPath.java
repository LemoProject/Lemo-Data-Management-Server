package de.lemo.dms.processing.questions;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.google.common.util.concurrent.AtomicLongMap;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResultListLog;

@QuestionID("userpath")
public class QUserPath extends Question {

    private static final String COURSE_IDS = "course_ids";
    private static final String STARTTIME = "start_time";
    private static final String ENDTIME = "end_time";

    @Override
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
                       Parameter.create(COURSE_IDS, "Courses", "List of courses."),
                       Interval.create(Long.class, STARTTIME, "Start time", "", 0L, now, 0L),
                       Interval.create(Long.class, ENDTIME, "End time", "", 0L, now, now)
                );
        return parameters;
    }

    @GET
    public ResultListLog compute(
            @QueryParam(COURSE_IDS) List<Long> courseIds,
            @QueryParam(STARTTIME) Long startTime,
            @QueryParam(ENDTIME) Long endTime) {

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

        /* A criteria is created from the session. */
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        Session session = dbHandler.getSession(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());

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
        logger.info("Result: " + logs.size() + " log entries.");

        AtomicLongMap<Long> actionCounter = AtomicLongMap.create(); // AtomicLong is a mutable long

        /* count entries and remove invalid */
        int skipped = 0;
        for(Iterator<ILogMining> iterator = logs.iterator(); iterator.hasNext();) {
            ILogMining log = iterator.next();
            UserMining user = log.getUser();
            if(user == null) {
                // @TODO why do some entries don't have a user?
                skipped++;
                iterator.remove();
                continue;
            }
            actionCounter.getAndIncrement(user.getId());
        }
        logger.info("Skipped " + skipped + " log entries with invalid users.");

        /*
         * @TODO Shouldn't we close the session at some point?
         */
        return new ResultListLog(logs);
    }
}
