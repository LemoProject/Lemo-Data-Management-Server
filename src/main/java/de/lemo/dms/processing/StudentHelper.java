/**
 * File ./src/main/java/de/lemo/dms/processing/StudentHelper.java
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
	public static Map<Long, Long> getCourseStudentsAliasKeys(List<Long> courses, List<Long> genders)
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
				if(genders.isEmpty() || genders.contains(cu.getUser().getGender()))
				{
					users.put(i, cu.getUser().getId());
					i++;
				}
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
	public static Map<Long, Long> getCourseStudentsRealKeys(List<Long> courses, List<Long> genders)
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
				if(genders.isEmpty() || genders.contains(cu.getUser().getGender()))
				{
					users.put(cu.getUser().getId(), i);
					i++;
				}
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
	public static boolean getGenderSupport(Long id)
	{
		final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();

		Criteria criteria = session.createCriteria(CourseUserMining.class, "cu");
		criteria.add(Restrictions.eq("cu.course.id", id));
		@SuppressWarnings("unchecked")
		List<CourseUserMining> courseUsers = (List<CourseUserMining>) criteria.list();
		int fem = 0;
		int mal = 0;
		
		for (final CourseUserMining cu : courseUsers) {
			// Only use students (type = 2) 
			if (cu.getUser() != null && cu.getRole().getType() == 2)
			{
				if(cu.getUser().getGender() == 1)
					fem++;
				else if(cu.getUser().getGender() == 2)
					mal++;
				if(mal > 4 && fem > 4)
				{
					session.close();
					return true;
				}
			}
		}
		session.close();
		return false;
	}
}
