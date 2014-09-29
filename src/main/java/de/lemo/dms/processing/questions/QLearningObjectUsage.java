/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QLearningObjectUsage.java
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
 * File ./main/java/de/lemo/dms/processing/questions/QLearningObjectUsage.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.abstractions.ICourseLORelation;
import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListResourceRequestInfo;

/**
 * Use of learning objects
 * 
 * @author Sebastian Schwarzrock
 */
@Path("learningobjectusage")
public class QLearningObjectUsage extends Question {

	/**
	 * Returns a list of resources and their respective statistics of usage.
	 * 
	 * @see ELearningObjectType
	 * @param courses
	 *            List of course-identifiers
	 * @param users
	 *            List of user-identifiers
	 * @param types
	 *            List of learn object types (see ELearnObjType)
	 * @param startTime
	 *            LongInteger time stamp
	 * @param endTime
	 *            LongInteger time stamp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	public ResultListResourceRequestInfo compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) List<Long> users,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.GENDER) final List<Long> gender,
			@FormParam(MetaParam.LEARNING_OBJ_IDS) final List<Long> learningObjects) {

		validateTimestamps(startTime, endTime);
		if (users.isEmpty()) {
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
		

		final ResultListResourceRequestInfo result = new ResultListResourceRequestInfo();
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		// Create criteria for log-file-search
		Criteria criteria = session.createCriteria(ILog.class, "log")
				.add(Restrictions.between("log.timestamp", startTime, endTime));
		if (!courses.isEmpty()) {
			criteria.add(Restrictions.in("log.course.id", courses));
		}
		if (!users.isEmpty()) {
			criteria.add(Restrictions.in("log.user.id", users));
		}
		if (!learningObjects.isEmpty()) {
			criteria.add(Restrictions.in("log.learning.id", learningObjects));
		}

		final List<ILog> logs = criteria.list();

		this.logger.debug("Total matched entries: " + logs.size());

		final HashMap<String, ArrayList<Long>> requests = new HashMap<String, ArrayList<Long>>();
		HashSet<String> requestedObjects = new HashSet<String>();
		
		for (final ILog ilo : logs)
		{
			// TODO use Class.getSimpleName() instead?
			final String obType = ilo.getLearning().getLOType();
			/*
					.getClass()
					.toString()
					.substring(ilo.getClass().toString().lastIndexOf(".") + 1,
							ilo.getClass().toString().lastIndexOf("Log"));*/

			if ((types == null) || (types.isEmpty()) || types.contains(obType))
			{
				requestedObjects.add(ilo.getType() + " " + ilo.getLearningId());
				
				final String id;
				/*
				if(ilo.getClass().getSimpleName().toUpperCase().contains("FORUM"))
				{
					String title = ((ForumLogMining) ilo).getSubject();
					if(title != null)
						id = ilo.getPrefix() + "_" + ilo.getLearningObjectId() + "?" + obType + "$" + title;
					else
						id = ilo.getPrefix() + "_" + ilo.getLearningObjectId() + "?" + obType + "$" + ilo.getTitle();
				}
				else
				{*/
				id = ilo.getLearningId() + "?" + obType + "$" + ilo.getLearning().getTitle();
				//}
				if (requests.get(id) == null)
				{
					final ArrayList<Long> al = new ArrayList<Long>();
					al.add(ilo.getUser().getId());
					requests.put(id, al);
				} else {
					requests.get(id).add(ilo.getUser().getId());
				}
				
				
				
			}
		}
		
		//Adding RRIs for unused Objects
		criteria = session.createCriteria(ICourseLORelation.class, "aso");
		criteria.add(Restrictions.in("aso.course.id", courses));
		List<ICourseLORelation> asoList = criteria.list();
		
		
		Long id = 1L;
		
		for(ICourseLORelation aso : asoList)
		{
			String obId = aso.getType() + " " + aso.getLearning().getId();
			if(!requestedObjects.contains(obId))
			{
				ILearningObject ilo = aso.getLearning();
				ilo.getId();

				if(types.isEmpty() || types.contains(ilo.getLOType()))
				{			
					final ResourceRequestInfo rri = new ResourceRequestInfo(id,
							aso.getLearning().getLOType(), 0L, 0L,
							aso.getLearning().getTitle(), 0L);
					result.add(rri);
					id++;
				}
			}
		}
		
		

		for (final Entry<String, ArrayList<Long>> item : requests.entrySet())
		{
			final String title = item.getKey().substring(item.getKey().indexOf("$") + 1);
			final String type = item.getKey().substring(item.getKey().indexOf("?") + 1, item.getKey().indexOf("$"));
			final ResourceRequestInfo rri = new ResourceRequestInfo(id,
					type, Long.valueOf(item.getValue().size()),
					Long.valueOf(new HashSet<Long>(item.getValue()).size()), title, 0L);
			id++;
			result.add(rri);
		}
		this.logger.debug("Total returned entries: " + result.getResourceRequestInfos().size());
		session.close();
		return result;
	}
}
