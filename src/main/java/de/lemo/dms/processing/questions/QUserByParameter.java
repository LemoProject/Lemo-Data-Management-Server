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
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

/**
 * Service for retrieval of user-identifiers (Long) that are filtered by the given restrictions.
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 *
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
	@POST
	@SuppressWarnings("unchecked")
	public ResultListLongObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.LOG_OBJECT_IDS) final List<String> objects,
			@FormParam(MetaParam.ROLE_IDS) final List<Long> roles,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime) {

		ResultListLongObject user_ids = null;

		if (startTime < endTime)
		{
			// Database initialization
			final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
			final Session session = dbHandler.getMiningSession();

			// Global list for log items
			final List<ILogMining> logs = new ArrayList<ILogMining>();

			// look up if types-restriction is set or the object-list is empty
			if (((types != null) && (types.size() > 0)) || ((objects != null) && (objects.size() > 1)))
			{
				// look up if types contain 'assignment' or if there are assignment-objects in the object list
				if (types.contains("assignment") || objects.contains("assignment"))
				{
					final List<Long> ids = new ArrayList<Long>();
					// If object list contains assignments go through list and get every assignment identifier of
					// interest
					if (objects.contains("assignment"))
					{
						Long value = 0L;
						for (int i = 0; i < objects.size(); i++)
						{
							// Due to the structure of the object list the identifier is read first
							if ((i % 2) == 0) {
								value = Long.valueOf(objects.get(i));
							}
							// If the object's type is 'assignment' the identifier is added to the 'ids' list
							if ((i % 2) != 0) {
								if (objects.get(i) == "assignment") {
									ids.add(value);
								}
							}
						}
					}

					// Create new criteria to retrieve all assignment-logs fulfilling the requirements.
					final Criteria criteria = session.createCriteria(AssignmentLogMining.class, "log");

					// Timestamps are mandatory.
					criteria.add(Restrictions.between("log.timestamp", startTime, endTime));

					// If course-identifiers are defined
					if (courses.size() > 0) {
						criteria.add(Restrictions.in("log.course.id", courses));
					}
					// If any assignment-identifiers have been found in the object list
					if (ids.size() > 0) {
						criteria.add(Restrictions.in("log.assignment.id", ids));
					}
					// Add matching log entries to global list
					logs.addAll(criteria.list());

				}
				if (types.contains("course") || objects.contains("course"))
				{
					final List<Long> ids = new ArrayList<Long>();
					if (objects.contains("course"))
					{
						Long value = 0L;
						for (int i = 0; i < objects.size(); i++)
						{
							if ((i % 2) == 0) {
								value = Long.valueOf(objects.get(i));
							}
							if ((i % 2) != 0) {
								if (objects.get(i) == "course") {
									ids.add(value);
								}
							}
						}
					}

					final Criteria criteria = session.createCriteria(CourseLogMining.class, "log")
							.add(Restrictions.between("log.timestamp", startTime, endTime));

					if (courses.size() > 0) {
						criteria.add(Restrictions.in("log.course.id", courses));
					}
					if (ids.size() > 0) {
						criteria.add(Restrictions.in("log.course.id", ids));
					}
					logs.addAll(criteria.list());

				}
				if (types.contains("forum") || objects.contains("forum"))
				{
					final List<Long> ids = new ArrayList<Long>();
					if (objects.contains("forum"))
					{
						Long value = 0L;
						for (int i = 0; i < objects.size(); i++)
						{
							if ((i % 2) == 0) {
								value = Long.valueOf(objects.get(i));
							}
							if ((i % 2) != 0) {
								if (objects.get(i) == "forum") {
									ids.add(value);
								}
							}
						}
					}

					final Criteria criteria = session.createCriteria(ForumLogMining.class, "log")
							.add(Restrictions.between("log.timestamp", startTime, endTime));

					if (courses.size() > 0) {
						criteria.add(Restrictions.in("log.course.id", courses));
					}
					if (ids.size() > 0) {
						criteria.add(Restrictions.in("log.forum.id", ids));
					}
					logs.addAll(criteria.list());

				}
				if (types.contains("question") || objects.contains("question"))
				{
					final List<Long> ids = new ArrayList<Long>();
					if (objects.contains("question"))
					{
						Long value = 0L;
						for (int i = 0; i < objects.size(); i++)
						{
							if ((i % 2) == 0) {
								value = Long.valueOf(objects.get(i));
							}
							if ((i % 2) != 0) {
								if (objects.get(i) == "question") {
									ids.add(value);
								}
							}
						}
					}

					final Criteria criteria = session.createCriteria(QuestionLogMining.class, "log")
							.add(Restrictions.between("log.timestamp", startTime, endTime));

					if (courses.size() > 0) {
						criteria.add(Restrictions.in("log.course.id", courses));
					}
					if (ids.size() > 0) {
						criteria.add(Restrictions.in("log.question.id", ids));
					}
					logs.addAll(criteria.list());
				}
				if (types.contains("quiz") || objects.contains("quiz"))
				{
					final List<Long> ids = new ArrayList<Long>();
					if (objects.contains("quiz"))
					{
						Long value = 0L;
						for (int i = 0; i < objects.size(); i++)
						{
							if ((i % 2) == 0) {
								value = Long.valueOf(objects.get(i));
							}
							if ((i % 2) != 0) {
								if (objects.get(i) == "quiz") {
									ids.add(value);
								}
							}
						}
					}

					final Criteria criteria = session.createCriteria(QuizLogMining.class, "log")
							.add(Restrictions.between("log.timestamp", startTime, endTime));

					if (courses.size() > 0) {
						criteria.add(Restrictions.in("log.course.id", courses));
					}
					if (ids.size() > 0) {
						criteria.add(Restrictions.in("log.quiz.id", ids));
					}
					logs.addAll(criteria.list());

				}
				if (types.contains("resource") || objects.contains("resource"))
				{
					final List<Long> ids = new ArrayList<Long>();
					if (objects.contains("resource"))
					{
						Long value = 0L;
						for (int i = 0; i < objects.size(); i++)
						{
							if ((i % 2) == 0) {
								value = Long.valueOf(objects.get(i));
							}
							if ((i % 2) != 0) {
								if (objects.get(i) == "resource") {
									ids.add(value);
								}
							}
						}
					}

					final Criteria criteria = session.createCriteria(ResourceLogMining.class, "log")
							.add(Restrictions.between("log.timestamp", startTime, endTime));

					if (courses.size() > 0) {
						criteria.add(Restrictions.in("log.course.id", courses));
					}
					if (ids.size() > 0) {
						criteria.add(Restrictions.in("log.resource.id", ids));
					}
					logs.addAll(criteria.list());

				}
				if (types.contains("scorm") || objects.contains("scorm"))
				{
					final List<Long> ids = new ArrayList<Long>();
					if (objects.contains("scorm"))
					{
						Long value = 0L;
						for (int i = 0; i < objects.size(); i++)
						{
							if ((i % 2) == 0) {
								value = Long.valueOf(objects.get(i));
							}
							if ((i % 2) != 0) {
								if (objects.get(i) == "scorm") {
									ids.add(value);
								}
							}
						}
					}

					final Criteria criteria = session.createCriteria(ScormLogMining.class, "log")
							.add(Restrictions.between("log.timestamp", startTime, endTime));

					if (courses.size() > 0) {
						criteria.add(Restrictions.in("log.course.id", courses));
					}
					if (ids.size() > 0) {
						criteria.add(Restrictions.in("log.scorm.id", ids));
					}
					logs.addAll(criteria.list());

				}
				if (types.contains("wiki") || objects.contains("wiki"))
				{
					final List<Long> ids = new ArrayList<Long>();
					if (objects.contains("wiki"))
					{
						Long value = 0L;
						for (int i = 0; i < objects.size(); i++)
						{
							if ((i % 2) == 0) {
								value = Long.valueOf(objects.get(i));
							}
							if ((i % 2) != 0) {
								if (objects.get(i) == "wiki") {
									ids.add(value);
								}
							}
						}
					}

					final Criteria criteria = session.createCriteria(WikiLogMining.class, "log")
							.add(Restrictions.between("log.timestamp", startTime, endTime));

					if (courses.size() > 0) {
						criteria.add(Restrictions.in("log.course.id", courses));
					}
					if (ids.size() > 0) {
						criteria.add(Restrictions.in("log.wiki.id", ids));
					}
					logs.addAll(criteria.list());

				}

			}
			// If object list and type restriction aren't set, do a more general scan of the database
			else
			{
				final Criteria criteria = session.createCriteria(ILogMining.class, "log")
						.add(Restrictions.between("log.timestamp", startTime, endTime));
				if (courses.size() > 0) {
					criteria.add(Restrictions.in("log.course.id", courses));
				}

				logs.addAll(criteria.list());
			}

			HashMap<Long, Long> usersWithinRoles = null;

			// Doesn't make any sense unless role-identifiers are linked with respective course-identifiers. Standard
			// setting should be 1 course n roles.
			// Working with several courses and roles-per-course will only mess up everything
			if ((roles != null) && (roles.size() > 0))
			{
				usersWithinRoles = new HashMap<Long, Long>();
				final Criteria criteria = session.createCriteria(CourseUserMining.class, "log")
						.add(Restrictions.in("log.role.id", roles));
				criteria.add(Restrictions.in("log.course.id", courses));

				final List<CourseUserMining> uwr = criteria.list();

				for (int i = 0; i < uwr.size(); i++)
				{
					if (uwr.get(i).getUser() != null) {
						usersWithinRoles.put(uwr.get(i).getUser().getId(), uwr.get(i).getUser().getId());
					}
				}

			}

			// Create HashMap for user-identifiers to prevent multiple entries for the same user-identifier
			final HashMap<Long, Long> users = new HashMap<Long, Long>();
			for (int i = 0; i < logs.size(); i++)
			{
				if (users.get(logs.get(i).getUser().getId()) == null) {
					if (usersWithinRoles == null) {
						users.put(logs.get(i).getUser().getId(), logs.get(i).getUser().getId());
					}
				}
				// If there are role restrictions, only user with the specified role get added
				if ((usersWithinRoles != null) && (usersWithinRoles.get(logs.get(i).getUser().getId()) != null)) {
					users.put(logs.get(i).getUser().getId(), logs.get(i).getUser().getId());
				}
			}

			user_ids = new ResultListLongObject(new ArrayList<Long>(users.values()));
		}
		return user_ids;

	}

}
