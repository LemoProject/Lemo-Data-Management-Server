/**
 * File ./main/java/de/lemo/dms/processing/StudentHelper.java
 * Date 2013-03-15
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.processing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.miningDBclass.CourseUserMining;

/**
 * Helper class for course-student relations
 * 
 * @author s.schwarzrock
 *
 */
public class StudentHelper {

	/**
	 * Returns a map containing all ids of students registered within the courses as keys and replacement-aliases as values.
	 * 
	 * @param courses List of course identifiers
	 * @return	Map<Long, Long> of identifiers (key-set) of students within the specified courses
	 */
	public static Map<Long, Long> getCourseStudentsAliasKeys(List<Long> courses)
	{
		final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();

		Map<Long, Long> users = new HashMap<Long, Long>();
		Criteria criteria = session.createCriteria(CourseUserMining.class, "cu");
		criteria.add(Restrictions.in("cu.course.id", courses));
		criteria.addOrder(Order.asc("cu.user.id"));
		@SuppressWarnings("unchecked")
		List<CourseUserMining> courseUsers = (List<CourseUserMining>) criteria.list();
		Long i = 1L;
		for (final CourseUserMining cu : courseUsers) {
			// Only use students (type = 2) 
			if (cu.getUser() != null && cu.getRole().getType() == 2)
			{
				users.put(i, cu.getUser().getId());
				i++;
			}
		}
		session.close();
		return users;
	}
	
	/**
	 * Returns a map containing all ids of students registered within the courses as keys and replacement-aliases as values.
	 * 
	 * @param courses List of course identifiers
	 * @return	Map<Long, Long> of identifiers (key-set) of students within the specified courses
	 */
	public static Map<Long, Long> getCourseStudentsRealKeys(List<Long> courses)
	{
		final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();

		Map<Long, Long> users = new HashMap<Long, Long>();
		Criteria criteria = session.createCriteria(CourseUserMining.class, "cu");
		criteria.add(Restrictions.in("cu.course.id", courses));
		criteria.addOrder(Order.asc("cu.user.id"));
		@SuppressWarnings("unchecked")
		List<CourseUserMining> courseUsers = (List<CourseUserMining>) criteria.list();
		Long i = 1L;
		for (final CourseUserMining cu : courseUsers) {
			// Only use students (type = 2) 
			if (cu.getUser() != null && cu.getRole().getType() == 2)
			{
				users.put(cu.getUser().getId(), i);
				i++;
			}
		}
		session.close();
		return users;
	}
}
