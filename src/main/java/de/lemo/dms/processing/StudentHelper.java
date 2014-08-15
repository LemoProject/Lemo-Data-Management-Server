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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.mapping.Attribute;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.UserAttribute;

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
	@SuppressWarnings("unchecked")
	public static Map<Long, Long> getCourseStudentsAliasKeys(List<Long> courses, List<Long> genders)
	{
		
		Map<Long, Long> users = new HashMap<Long, Long>();
		if(courses != null && !courses.isEmpty())
		{
			final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		
			Criteria criteria = session.createCriteria(CourseUser.class, "cu");
			criteria.add(Restrictions.in("cu.course.id", courses));
			criteria.addOrder(Order.asc("cu.user.id"));
			@SuppressWarnings("unchecked")
			List<CourseUser> courseUsers = (List<CourseUser>) criteria.list();
			
			List<Long> usersIds = new ArrayList<Long>();
			for(CourseUser cu : courseUsers)
			{
				usersIds.add(cu.getUser().getId());
			}
			
			Long genderId;
			Map<Long, Long> userGenders = new HashMap<Long, Long>();
			if(!genders.isEmpty())
			{
				criteria = session.createCriteria(Attribute.class, "attribute");
				criteria.add(Restrictions.like("attribute.name", "User Gender"));
				if(!criteria.list().isEmpty() && !usersIds.isEmpty())
				{
					genderId = ((List<Attribute>)criteria.list()).get(0).getId();
					criteria =session.createCriteria(UserAttribute.class, "userAttribute");
					criteria.add(Restrictions.in("userAttribute.user.id", usersIds));
					criteria.add(Restrictions.eq("userAttribute.attribute.id", genderId));
					for(UserAttribute ua : (List<UserAttribute>)criteria.list())
					{
						userGenders.put(ua.getUser().getId(), Long.valueOf(ua.getValue()));
					}
				}
			}
			
			Long i = 1L;
			for (final CourseUser cu : courseUsers) {
				// Only use students (type = 2) 
				if (cu.getUser() != null && cu.getRole().getType() == 2)
				{
					
					if(genders.isEmpty() || userGenders.get(cu.getUser().getId()) != null && genders.contains(userGenders.get(cu.getUser().getId())))
					{
						users.put(i, cu.getUser().getId());
						i++;
					}
				}
			}
			session.close();
		}
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
		
		Map<Long, Long> users = new HashMap<Long, Long>();
		
		if(courses != null && !courses.isEmpty())
		{
			final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();			
			Criteria criteria = session.createCriteria(CourseUser.class, "cu");
			criteria.add(Restrictions.in("cu.course.id", courses));
			criteria.addOrder(Order.asc("cu.user.id"));
			@SuppressWarnings("unchecked")
			List<CourseUser> courseUsers = (List<CourseUser>) criteria.list();
			List<Long> usersIds = new ArrayList<Long>();
			for(CourseUser cu : courseUsers)
			{
				usersIds.add(cu.getUser().getId());
			}
			Long i = 1L;
			Long genderId;
			Map<Long, Long> userGenders = new HashMap<Long, Long>();
			if(!genders.isEmpty())
			{
				criteria = session.createCriteria(Attribute.class, "attribute");
				criteria.add(Restrictions.like("attribute.name", "User Gender"));
				if(!criteria.list().isEmpty() && !usersIds.isEmpty())
				{
					genderId = ((List<Attribute>)criteria.list()).get(0).getId();
					criteria =session.createCriteria(UserAttribute.class, "userAttribute");
					criteria.add(Restrictions.in("userAttribute.user.id", usersIds));
					criteria.add(Restrictions.eq("userAttribute.attribute.id", genderId));
					for(UserAttribute ua : (List<UserAttribute>)criteria.list())
					{
						userGenders.put(ua.getUser().getId(), Long.valueOf(ua.getValue()));
					}
				}
			}
			for (final CourseUser cu : courseUsers) {
				// Only use students (type = 2) 
				if (cu.getUser() != null && cu.getRole().getType() == 2)
				{
					if(genders.isEmpty() || userGenders.get(cu.getUser().getId()) != null && genders.contains(userGenders.get(cu.getUser().getId())))
					{
						users.put(cu.getUser().getId(), i);
						i++;
					}
				}
			}
			session.close();
		}
		return users;
	}
	
	/**
	 * Returns a map containing all ids of students registered within the courses as keys and replacement-aliases as values.
	 * 
	 * @param courses List of course identifiers
	 * @return	Map<Long, Long> of identifiers (key-set) of students within the specified courses
	 */
	public static boolean getGenderSupport(Long courseId)
	{		
		if(courseId != null)
		{
			final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
	
			Criteria criteria = session.createCriteria(CourseUser.class, "cu");
			criteria.add(Restrictions.eq("cu.course.id", courseId));
			@SuppressWarnings("unchecked")
			List<CourseUser> courseUsers = (List<CourseUser>) criteria.list();
			
			List<Long> usersIds = new ArrayList<Long>();
			for(CourseUser cu : courseUsers)
			{
				usersIds.add(cu.getUser().getId());
			}
			Long i = 1L;
			Long genderId;
			Map<Long, Long> userGenders = new HashMap<Long, Long>();
			criteria = session.createCriteria(Attribute.class, "attribute");
			criteria.add(Restrictions.like("attribute.name", "User Gender"));
			if(!criteria.list().isEmpty() && !usersIds.isEmpty())
			{
				genderId = ((List<Attribute>)criteria.list()).get(0).getId();
				criteria =session.createCriteria(UserAttribute.class, "userAttribute");
				criteria.add(Restrictions.in("userAttribute.user.id", usersIds));
				criteria.add(Restrictions.eq("userAttribute.attribute.id", genderId));
				for(UserAttribute ua : (List<UserAttribute>)criteria.list())
				{
					userGenders.put(ua.getUser().getId(), Long.valueOf(ua.getValue()));
				}
			}
			int fem = 0;
			int mal = 0;
			
			if(!userGenders.isEmpty())
			{
				for (final CourseUser cu : courseUsers) {
					// Only use students (type = 2) 
					if (cu.getUser() != null && cu.getRole().getType() == 2)
					{
						if(userGenders.get(cu.getUser().getId()) != null && userGenders.get(cu.getUser().getId()) == 1)
							fem++;
						else if(userGenders.get(cu.getUser().getId()) != null && userGenders.get(cu.getUser().getId()) == 2)
							mal++;
						if(mal > 4 && fem > 4)
						{
							session.close();
							return true;
						}
					}
				}
			}
			session.close();
		}
		return false;
	}
}
