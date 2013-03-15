/**
 * File ./main/java/de/lemo/dms/processing/questions/QActivityResourceTypeResolution.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
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
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.ELearningObjectType;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.StudentHelper;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListRRITypes;

/**
 * Checks which resources are used in certain courses
 * an extra parameter specifies the resolution of the data
 * 
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
@Path("activityresourcetyperesolution")
public class QActivityResourceTypeResolution extends Question {

	@SuppressWarnings("unchecked")
	@POST
	public ResultListRRITypes compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.RESOLUTION) final Long resolution,
			@FormParam(MetaParam.TYPES) final List<String> resourceTypes) {

		validateTimestamps(startTime, endTime, resolution);

		final ResultListRRITypes result = new ResultListRRITypes();
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		final double intervall = (endTime - startTime) / (resolution);
		final boolean allTypes = resourceTypes.isEmpty();

		for (ELearningObjectType loType : ELearningObjectType.values()) {
			if (allTypes || resourceTypes.contains(loType.name().toLowerCase())) {
				Criteria criteria;
				List<Long> users = StudentHelper.getCourseStudents(courses);
				criteria = session.createCriteria(loType.getLogMiningType(), "log")
						.add(Restrictions.between("log.timestamp", startTime, endTime))
						.add(Restrictions.in("log.user.id", users));
				if(users.size() > 0)
					criteria.add(Restrictions.in("log.course.id", courses));
				
				final List<ILogMining> logs = criteria.list();
				HashMap<String, ResourceRequestInfo> rri = loadLogMining(logs, loType,
						startTime, endTime, resolution, intervall);
				ArrayList<ResourceRequestInfo> rriList = new ArrayList<ResourceRequestInfo>(rri.values());

				switch (loType) {
					case ASSIGNMENT:
						result.setQuestionRRI(rriList);
						break;
					case CHAT:
						result.setChatRRI(rriList);
						break;
					case FORUM:
						result.setForumRRI(rriList);
						break;
					case QUESTION:
						result.setQuestionRRI(rriList);
						break;
					case QUIZ:
						result.setQuizRRI(rriList);
						break;
					case RESOURCE:
						result.setResourceRRI(rriList);
						break;
					case SCORM:
						result.setScormRRI(rriList);
						break;
					case WIKI:
						result.setWikiRRI(rriList);
						break;

					default:
						break;
				}
			}
		}

		return result;
	}

	private HashMap<String, ResourceRequestInfo> loadLogMining(List<ILogMining> logs, ELearningObjectType type,
			long startTime, long endTime, long resolution, double intervall) {

		final HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
		for (ILogMining log : logs) {
			Long id = log.getLearnObjId();
			if (id != null) {
				Long pos = new Double((log.getTimestamp() - startTime) / intervall).longValue();
				if (pos > (resolution - 1)) {
					pos = resolution - 1;
				}

				if (log.getTitle().isEmpty()) {
					String key = pos + "-1";
					if (rri.get(key) == null) {
						rri.put(key, new ResourceRequestInfo(id, type, 1L, 1L, "Unknown", pos));
					} else {
						rri.get(key).incRequests();
					}
				} else {
					String key = pos.toString() + id;
					if (rri.get(key) == null) {
						rri.put(key, new ResourceRequestInfo(id, type, 1L, 1L, log.getTitle(), pos));
					} else {
						rri.get(key).incRequests();
					}
				}
			}
		}
		return rri;
	}

}
