package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.miningDBclass.CourseUserMining;


public class StudentHelper {

	@SuppressWarnings("unchecked")
	public static ArrayList<Long> getCourseStudents(List<Long> courses)
	{
		final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		
		ArrayList<Long> users = new ArrayList<Long>();
		Criteria criteria;
		criteria = session.createCriteria(CourseUserMining.class, "cu")
			.add(Restrictions.in("cu.course.id", courses));
		for (final CourseUserMining cu : (List<CourseUserMining>)criteria.list()) {
			if (cu.getUser() == null && cu.getRole().getType() == 2)
				users.add(cu.getUser().getId());
		}
		
		return users;
	}
	
	
}
