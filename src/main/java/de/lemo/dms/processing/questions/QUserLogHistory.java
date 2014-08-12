/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QUserLogHistory.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/processing/questions/QUserLogHistory.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
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
	 * @param courses
	 *            List of course-identifiers
	 * @param users
	 *            List of user-identifiers
	 * @param startTime
	 *            LongInteger time stamp
	 * @param endTime
	 *            LongInteger time stamp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	public ResultListUserLogObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) List<Long> users,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.GENDER) final List<Long> gender,
			@FormParam(MetaParam.LEARNING_OBJ_IDS) final List<Long> learningObjects) {

		validateTimestamps(startTime, endTime);

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		
		Criteria criteria;
		if(users == null || users.size() == 0)
		{
			users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses, gender).values());
		}
		else
		{
			Map<Long, Long> userMap = StudentHelper.getCourseStudentsAliasKeys(courses, gender);
			List<Long> tmp = new ArrayList<Long>();
			for(int i = 0; i < users.size(); i++)
			{
				tmp.add(userMap.get(users.get(i)));
			}
			users = tmp;
		}
		criteria = session.createCriteria(ILog.class, "log");
		criteria.add(Restrictions.between("log.timestamp", startTime, endTime));
		if(users != null && users.size() > 0)
			criteria.add(Restrictions.in("log.user.id", users));
		if ((courses != null) && (courses.size() > 0)) {
			criteria.add(Restrictions.in("log.course.id", courses));
		}
		if (!learningObjects.isEmpty()) {
			criteria.add(Restrictions.in("log.learning.id", learningObjects));
		}

		final List<ILog> logs = criteria.list();

		// HashMap for all user-histories
		final HashMap<Long, List<UserLogObject>> userPaths = new HashMap<Long, List<UserLogObject>>();

		// Iterate through all found log-items for saving log data into UserPathObjects
		for (int i = 0; i < logs.size(); i++)
		{


			ILog ilm = logs.get(i);
			String type = ilm.getLearning().getLOType();
			String title = ilm.getLearning().getTitle();
			
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
			logger.debug(l.get(i).getType());
		}

		final ResultListUserLogObject rlupo = new ResultListUserLogObject(l);

		session.close();
		return rlupo;
	}
}
