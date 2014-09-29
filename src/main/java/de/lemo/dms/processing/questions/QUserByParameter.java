/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QUserByParameter.java
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
 * File ./main/java/de/lemo/dms/processing/questions/QUserByParameter.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

/**
 * Service for retrieval of user-identifiers (Long) that are filtered by the given restrictions.
 * 
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
@Path("userbyparameter")
public class QUserByParameter extends Question {

	/**
	 * Service for retrieval of user-identifiers (Long) that are filtered by the given restrictions.
	 * 
	 * @param courses
	 *            (Optional, can be combined with types and objects) Only users are returned, that performed actions in
	 *            courses with the given course-identifiers (Long)
	 * @param types
	 *            (Optional, can be combined with courses and objects) Only users are returned, that performed actions
	 *            on the given types of learning-objects.
	 * @param objects
	 *            (Optional, can be combined with types and courses) Only users are returned, that performed actions
	 *            specified learning objects. The list should contain pairs of user-identifiers and object-types
	 *            (["1234"],["assignment"] for example).
	 * @param roles
	 * @param startTime
	 *            (Mandatory) Only users are returned, that performed actions after the given time.
	 * @param endTime
	 *            (Mandatory) Only users are returned, that performed actions before the given time.
	 * @return
	 */
	/*
	@POST
	public ResultListLongObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.LOG_OBJECT_IDS) final List<String> objects,
			@FormParam(MetaParam.ROLE_IDS) final List<Long> roles,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.LEARNING_OBJ_IDS) final List<Long> learningObjects) {

		validateTimestamps(startTime, endTime);

		ResultListLongObject userIds = null;

		// Database initialization
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		// Global list for log items
		List<ILog> logs = new ArrayList<ILog>();

		// look up if types-restriction is set or the object-list contains at least one pair
		if (!types.isEmpty() || objects.size() > 1) {
			for (String loType : ELearningObjectType.values()) {

				String typeName = loType.name().toLowerCase();
				if (types.contains(typeName) || objects.contains(typeName)) {
					Criteria criteria = session.createCriteria(loType.getLogMiningType(), "log");
					criteria.add(Restrictions.between("log.timestamp", startTime, endTime));
					if (!courses.isEmpty()) {
						criteria.add(Restrictions.in("log.course.id", courses));
					}
					if(!learningObjects.isEmpty())
					{
						criteria.add(Restrictions.in("log.learning.id", learningObjects));
					}
					List<ILog> loadLogMining = loadLogMining(criteria, objects, typeName);
					if (loadLogMining != null) {
						logs.addAll(loadLogMining);
					}
				}
			}
		} else {
			// If object list and type restriction aren't set, do a more general scan of the database
			final Criteria criteria = session.createCriteria(ILog.class, "log")
					.add(Restrictions.between("log.timestamp", startTime, endTime));
			if (!courses.isEmpty()) {
				criteria.add(Restrictions.in("log.course.id", courses));
			}

			@SuppressWarnings("unchecked")
			List<ILog> loadLogMining = criteria.list();
			logs = loadLogMining;
		}

		HashMap<Long, Long> usersWithinRoles = null;

		// Doesn't make any sense unless role-identifiers are linked with respective course-identifiers. Standard
		// setting should be 1 course n roles.
		// Working with several courses and roles-per-course will only mess up everything
		if (!roles.isEmpty()) {
			usersWithinRoles = new HashMap<Long, Long>();
			final Criteria criteria = session.createCriteria(CourseUser.class, "log")
					.add(Restrictions.in("log.role.id", roles))
					.add(Restrictions.in("log.course.id", courses));

			@SuppressWarnings("unchecked")
			final List<CourseUser> uwr = criteria.list();

			for (int i = 0; i < uwr.size(); i++) {
				if (uwr.get(i).getUser() != null) {
					usersWithinRoles.put(uwr.get(i).getUser().getId(), uwr.get(i).getUser().getId());
				}
			}

		}

		// Create HashMap for user-identifiers to prevent multiple entries for the same user-identifier
		final HashMap<Long, Long> users = new HashMap<Long, Long>();
		for (int i = 0; i < logs.size(); i++)
		{
			if ((users.get(logs.get(i).getUser().getId()) == null) && usersWithinRoles == null) {
					users.put(logs.get(i).getUser().getId(), logs.get(i).getUser().getId());
			}
			// If there are role restrictions, only user with the specified role get added
			if ((usersWithinRoles != null) && (usersWithinRoles.get(logs.get(i).getUser().getId()) != null)) {
				users.put(logs.get(i).getUser().getId(), logs.get(i).getUser().getId());
			}
		}

		userIds = new ResultListLongObject(new ArrayList<Long>(users.values()));
		session.close();
		return userIds;

	}

	@SuppressWarnings("unchecked")
	private List<ILog> loadLogMining(Criteria criteria, List<String> objects, String type) {

		List<Long> ids = new ArrayList<Long>(objects.size() / 2);
		if (objects.contains(type)) {
			Long value = 0L;
			for (int i = 0; i < objects.size(); i++) {
				if ((i % 2) == 0) {
					value = Long.valueOf(objects.get(i));
				}
				if ((i % 2) != 0 && objects.get(i).equals(type)) {
					ids.add(value);
				}
			}
		}
		if (!ids.isEmpty()) {
			criteria.add(Restrictions.in("log." + type + ".id", ids));
		}
		List<ILog> r = criteria.list();
		return r;

	}*/
}
