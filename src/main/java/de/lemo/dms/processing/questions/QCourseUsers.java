/**
 * File ./main/java/de/lemo/dms/processing/questions/QCourseUsers.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

/**
 * All members of a course
 * 
 * @author Sebastian Schwarzrock
 */
@Path("activecourseusers")
public class QCourseUsers extends Question {

	@POST
	public ResultListLongObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.GENDER) List<Long> gender) {

		validateTimestamps(startTime, endTime);
		/*
		// Set up db-connection
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		final Criteria criteria = session.createCriteria(ILogMining.class, "log")
<<<<<<< HEAD
				.add(Restrictions.in("log.course.id", courseIds))
=======
				.add(Restrictions.in("log.course.id", courses))
>>>>>>> refs/remotes/origin/feature_sebs_sp12b1
				.add(Restrictions.between("log.timestamp", startTime, endTime));
	*/	
		/*
		  final Criteria criteria = session.createCriteria(CourseUserMining.class, "cu")
				.add(Restrictions.in("cu.course.id", courseIds));
		  
		 */
		
/*
		@SuppressWarnings("unchecked")
		final ArrayList<ILogMining> logs = (ArrayList<ILogMining>) criteria.list();

		final HashSet<Long> users = new HashSet<Long>();
		for (final ILogMining log : logs) {
			if (log.getUser() == null) {
				continue;
			}
			users.add(log.getUser().getId());
		}
*/		/*
		 for (final CourseUserMining cu : (List<CourseUserMining>)criteria.list()) {
			if (cu.getUser() == null) {
				continue;
			}
			if(cu.getRole.getType() == 2)
				users.add(cu.getUser().getId());
		}
		 */

		return new ResultListLongObject(new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses, gender).keySet()));
	}

}
