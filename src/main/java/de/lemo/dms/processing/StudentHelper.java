/**
 * File ./main/java/de/lemo/dms/processing/StudentHelper.java
 * Date 2013-03-15
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
	 * Returns the list of all ids of students registered within the specified courses.
	 * 
	 * @param courses List of course identifiers
	 * @return	List of identifiers of students within the specified courses
	 */
	public static List<Long> getCourseStudents(List<Long> courses)
	{
		final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();

		List<Long> users = new ArrayList<Long>();
		Criteria criteria = session.createCriteria(CourseUserMining.class, "cu")
				.add(Restrictions.in("cu.course.id", courses));
		@SuppressWarnings("unchecked")
		List<CourseUserMining> courseUsers = (List<CourseUserMining>) criteria.list();
		for (final CourseUserMining cu : courseUsers) {
			// TODO clarify meaning of 'type 2'
			if (cu.getUser() != null && cu.getRole().getType() == 2)
				users.add(cu.getUser().getId());
		}

		return users;
	}

}
