package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.START_TIME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

@Path("activecourseusers")
public class QCourseUsers extends Question {
    
    @POST
    public ResultListLongObject compute(
            @FormParam(COURSE_IDS) List<Long> courseIds,
            @FormParam(START_TIME) Long startTime,
            @FormParam(END_TIME) Long endTime) {

        // Check arguments
        if(startTime >= endTime)
            return null;

        // Set up db-connection
        IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
         Session session = dbHandler.getMiningSession();

        Criteria criteria = session.createCriteria(CourseLogMining.class, "log");

        criteria.add(Restrictions.in("log.course.id", courseIds))
                .add(Restrictions.between("log.timestamp", startTime, endTime));

        @SuppressWarnings("unchecked")
        ArrayList<ILogMining> logs = (ArrayList<ILogMining>) criteria.list();

        HashSet<Long> users = new HashSet<Long>();
        for(ILogMining log : logs) {
            if(log.getUser() == null) {
                continue;
            }
            users.add(log.getUser().getId());
        }

        return new ResultListLongObject(new ArrayList<Long>(users));
    }

}
