/**
 * File ./main/java/de/lemo/dms/processing/questions/QUserLogHistory.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListUserLogObject;
import de.lemo.dms.processing.resulttype.UserLogObject;

/**
 * Look up for spezial log events
 * @author Sebastian Schwarzrock
 *
 */
@Path("userloghistory")
public class QUserLogHistory extends Question {

	/**
	 * Returns all logged events matching the requirements given by the parameters.
	 * 
	 * @param courseIds
	 *            List of course-identifiers
	 * @param userIds
	 *            List of user-identifiers
	 * @param startTime
	 *            LongInteger time stamp
	 * @param endTime
	 *            LongInteger time stamp
	 * @return
	 */
	@POST
	public ResultListUserLogObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courseIds,
			@FormParam(MetaParam.USER_IDS) final List<Long> userIds,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime) {

		validateTimestamps(startTime, endTime);

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		final Criteria criteria = session.createCriteria(ILogMining.class, "log");

		criteria.add(Restrictions.between("log.timestamp", startTime, endTime))
				.add(Restrictions.in("log.user.id", userIds));
		if ((courseIds != null) && (courseIds.size() > 0)) {
			criteria.add(Restrictions.in("log.course.id", courseIds));
		}

		@SuppressWarnings("unchecked")
		final List<ILogMining> logs = criteria.list();

		// HashMap for all user-histories
		final HashMap<Long, List<UserLogObject>> userPaths = new HashMap<Long, List<UserLogObject>>();

		// Iterate through all found log-items for saving log data into UserPathObjects
		for (int i = 0; i < logs.size(); i++)
		{

			String title = "";
			String type = "";
			ILogMining ilm = null;

			// the log entry has to be cast to its respective class to get its title
			if (logs.get(i).getClass().equals(AssignmentLogMining.class)
					&& (((AssignmentLogMining) logs.get(i)).getAssignment() != null))
			{
				ilm = logs.get(i);
				type = "assignment";
				title = ((AssignmentLogMining) ilm).getAssignment().getTitle();
			}
			if (logs.get(i).getClass().equals(ForumLogMining.class)
					&& (((ForumLogMining) logs.get(i)).getForum() != null))
			{
				ilm = logs.get(i);
				type = "forum";
				title = ((ForumLogMining) ilm).getForum().getTitle();
			}

			if (logs.get(i).getClass().equals(QuizLogMining.class) && (((QuizLogMining) logs.get(i)).getQuiz() != null))
			{
				ilm = logs.get(i);
				type = "quiz";
				title = ((QuizLogMining) ilm).getQuiz().getTitle();
			}
			if (logs.get(i).getClass().equals(QuestionLogMining.class)
					&& (((QuestionLogMining) logs.get(i)).getQuestion() != null))
			{
				ilm = logs.get(i);
				type = "question";
				title = ((QuestionLogMining) ilm).getQuestion().getTitle();
			}
			if (logs.get(i).getClass().equals(ResourceLogMining.class)
					&& (((ResourceLogMining) logs.get(i)).getResource() != null))
			{
				ilm = logs.get(i);
				type = "resource";
				title = ((ResourceLogMining) ilm).getResource().getTitle();
			}
			if (logs.get(i).getClass().equals(WikiLogMining.class) && (((WikiLogMining) logs.get(i)).getWiki() != null))
			{
				ilm = logs.get(i);
				type = "wiki";
				title = ((WikiLogMining) ilm).getWiki().getTitle();
			}

			if (logs.get(i).getClass().equals(ScormLogMining.class)
					&& (((ScormLogMining) logs.get(i)).getScorm() != null))
			{
				ilm = logs.get(i);
				type = "scorm";
				title = ((ScormLogMining) ilm).getScorm().getTitle();
			}
			if (ilm != null) {
				if (userPaths.get(logs.get(i).getUser().getId()) == null)
				{
					final ArrayList<UserLogObject> uP = new ArrayList<UserLogObject>();
					// If the user isn't already in the map, create new entry and insert the UserPathObject
					uP.add(new UserLogObject(ilm.getUser().getId(), ilm.getTimestamp(), title, ilm.getId(), type, ilm
							.getCourse().getId(), ""));
					userPaths.put(logs.get(i).getUser().getId(), uP);
				} else {
					// If the user is known, just add the UserPathObject to the user's history
					userPaths.get(ilm.getUser().getId()).add(
							new UserLogObject(ilm.getUser().getId(), ilm.getTimestamp(), title, ilm.getId(), type, ilm
									.getCourse().getId(), ""));
				}
			} else {
				//TODO wozu das else statement?
				//System.out.println();
			}
		}

		// List for UserPathObjects
		final List<UserLogObject> l = new ArrayList<UserLogObject>();
		// Insert all entries of all user-histories to the list
		for (final Iterator<List<UserLogObject>> iter = userPaths.values().iterator(); iter.hasNext();) {
			l.addAll(iter.next());
		}
		// Sort the list (first by user and time stamp)
		Collections.sort(l);
		for (int i = 0; i < l.size(); i++) {
			logger.info(l.get(i).getType());
		}

		final ResultListUserLogObject rlupo = new ResultListUserLogObject(l);

		return rlupo;
	}
}
