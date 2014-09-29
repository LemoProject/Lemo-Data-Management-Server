/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QActivityResourceType.java
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
 * File ./main/java/de/lemo/dms/processing/questions/QActivityResourceType.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.mapping.abstractions.ICourseLORelation;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListResourceRequestInfo;

/**
 * Checks which resources are used in certain courses
 * 
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
@Path("activityresourcetype")
public class QActivityResourceType extends Question {

	@SuppressWarnings("unchecked")
	@POST
	public ResultListResourceRequestInfo compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.TYPES) List<String> resourceTypes,
			@FormParam(MetaParam.GENDER) List<Long> gender,
			@FormParam(MetaParam.LEARNING_OBJ_IDS) List<Long> learningObjects){

		validateTimestamps(startTime, endTime);

		final ResultListResourceRequestInfo result = new ResultListResourceRequestInfo();
		boolean allTypes = resourceTypes.isEmpty();
		final Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		
		List<Long> users = new ArrayList<Long>(StudentHelper.getCourseStudentsAliasKeys(courses, gender).values());
		if(users.isEmpty()) {
			logger.warn("Could not find associated users.");
			return new ResultListResourceRequestInfo();
		}
		
		Criteria criteria = session.createCriteria(ILog.class, "log");
		criteria.add(Restrictions.in("log.course.id", courses))
				.add(Restrictions.between("log.timestamp", startTime, endTime))
				.add(Restrictions.in("log.user.id", users));
		
		if(!learningObjects.isEmpty())
		{
			criteria.add(Restrictions.in("log.learning.id", learningObjects));
		}

		final List<ILog> logs = criteria.list();		
		
		final Map<Long, ResourceRequestInfo> rriMap = new HashMap<Long, ResourceRequestInfo>();
		final Map<Long, Set<Long>> userMap = new HashMap<Long, Set<Long>>();
		for(ILog log : logs)
		{
			String type = log.getLearning().getLOType();
			if (log.getUser() != null && (resourceTypes.contains(type) || allTypes))
			{
				Long id = Long.valueOf(log.getLearningId());
				if (rriMap.get(id) == null) {
					Set<Long> userSet = new HashSet<Long>();
					userSet.add(log.getUser().getId());
					userMap.put(id, userSet);
					rriMap.put(id, new ResourceRequestInfo(id, type, 1L, 1L, log.getLearning().getTitle(), 0L));
				} else
				{
					userMap.get(id).add(log.getUser().getId());
					rriMap.get(id).incRequests();
					rriMap.get(id).setUsers(((Integer)userMap.get(id).size()).longValue());
				}
			}
		}
		
		//Add unused Objects
		criteria = session.createCriteria(ICourseLORelation.class, "aso");
		criteria.add(Restrictions.in("aso.course.id", courses));
		List<ICourseLORelation> asoList = criteria.list();
		
		for(ICourseLORelation aso : asoList)
		{
			Long id = Long.valueOf(aso.getLearning().getId());
			if(!rriMap.containsKey(id))
			{
				String type = aso.getLearning().getClass().getSimpleName().toUpperCase();
				if(type.contains("MINING"))
				{
					type = type.substring(0, type.indexOf("MINING"));
				}
				if(allTypes || resourceTypes.contains(type))
				{			
					final ResourceRequestInfo rri = new ResourceRequestInfo(id,
							aso.getLearning().getLOType(), 0L, 0L,
							aso.getLearning().getTitle(), 0L);
					result.add(rri);
					id++;
				}
			}
		}
		
		if (rriMap.values() != null) {
			result.addAll(rriMap.values());
		}
		session.close();
		return result;
	}

}
