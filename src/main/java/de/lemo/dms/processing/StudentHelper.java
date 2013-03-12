package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.miningDBclass.CourseUserMining;

public class StudentHelper {

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
