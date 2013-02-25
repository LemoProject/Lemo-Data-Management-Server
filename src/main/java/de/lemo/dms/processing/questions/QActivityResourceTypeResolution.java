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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.processing.ELearningObjectType;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
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

	@POST
	public ResultListRRITypes compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.RESOLUTION) final Long resolution,
			@FormParam(MetaParam.TYPES) final List<String> resourceTypes) {

		validateTimestamps(startTime, endTime, resolution);

		boolean allTypes = resourceTypes.isEmpty();
		final ResultListRRITypes result = new ResultListRRITypes();

		// Calculate size of time intervalls
		final double intervall = (endTime - startTime) / (resolution);

		// Set up db-connection
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

		final Session session = dbHandler.getMiningSession();

		// Create and initialize array for results
		if (resourceTypes.contains(ELearningObjectType.ASSIGNMENT.toString()) || allTypes)
		{
			final Criteria criteria = session.createCriteria(AssignmentLogMining.class, "log");
			criteria.add(Restrictions.in("log.course.id", courses))
					.add(Restrictions.between("log.timestamp", startTime, endTime));
			@SuppressWarnings("unchecked")
			final List<AssignmentLogMining> ilm = criteria.list();
			final HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
			for (int i = 0; i < ilm.size(); i++) {
				if (ilm.get(i).getAssignment() != null)
				{
					Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
					if (pos > (resolution - 1)) {
						pos = resolution - 1;
					}

					if (ilm.get(i).getAssignment().getTitle().equals("")) {
						if (rri.get(pos + "-1") == null) {
							rri.put(pos.toString() + "-1" + "", new ResourceRequestInfo(ilm.get(i).getAssignment()
									.getId(), ELearningObjectType.ASSIGNMENT, 1L, 1L, "Unknown", pos));
						} else
						{
							rri.get(pos.toString() + "-1").incRequests();
						}
					} else if (rri.get(pos.toString() + ilm.get(i).getAssignment().getId()) == null) {
						rri.put(pos.toString() + ilm.get(i).getAssignment().getId() + "", new ResourceRequestInfo(
								ilm.get(i).getAssignment().getId(), ELearningObjectType.ASSIGNMENT, 1L, 1L, ilm
										.get(i).getAssignment().getTitle(), pos));
					} else
					{

						rri.get(pos.toString() + ilm.get(i).getAssignment().getId()).incRequests();
					}
				}
			}
			if (rri.values() != null) {
				result.setAssignmentRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
		}
		if (resourceTypes.contains(ELearningObjectType.FORUM.toString()) || allTypes)
		{
			final Criteria criteria = session.createCriteria(ForumLogMining.class, "log");
			criteria.add(Restrictions.in("log.course.id", courses))
					.add(Restrictions.between("log.timestamp", startTime, endTime));
			@SuppressWarnings("unchecked")
			final List<ForumLogMining> ilm = criteria.list();
			final HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
			for (int i = 0; i < ilm.size(); i++) {
				if (ilm.get(i).getForum() != null)
				{
					Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
					if (pos > (resolution - 1)) {
						pos = resolution - 1;
					}

					if (ilm.get(i).getForum().getTitle().equals(""))
					{
						if (rri.get(pos + "-1") == null) {
							rri.put(pos + "-1", new ResourceRequestInfo(ilm.get(i).getForum().getId(),
									ELearningObjectType.FORUM, 1L, 1L, "Unknown", pos));
						} else {
							rri.get(pos.toString() + "-1").incRequests();
						}
					}
					else {
						if (rri.get(pos.toString() + ilm.get(i).getForum().getId()) == null) {
							rri.put(pos.toString() + ilm.get(i).getForum().getId(), new ResourceRequestInfo(ilm
									.get(i).getForum().getId(), ELearningObjectType.FORUM, 1L, 1L, ilm.get(i)
									.getForum().getTitle(), pos));
						} else {
							rri.get(pos.toString() + ilm.get(i).getForum().getId()).incRequests();
						}
					}
				}
			}
			if (rri.values() != null) {
				result.setForumRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
		}
		if (resourceTypes.contains(ELearningObjectType.QUESTION.toString()) || allTypes)
		{
			final Criteria criteria = session.createCriteria(QuestionLogMining.class, "log");
			criteria.add(Restrictions.in("log.course.id", courses))
					.add(Restrictions.between("log.timestamp", startTime, endTime));
			@SuppressWarnings("unchecked")
			final List<QuestionLogMining> ilm = criteria.list();
			final HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
			for (int i = 0; i < ilm.size(); i++) {
				if (ilm.get(i).getQuestion() != null)
				{
					Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
					if (pos > (resolution - 1)) {
						pos = resolution - 1;
					}

					if (ilm.get(i).getQuestion().getTitle().equals(""))
					{
						if (rri.get(pos.toString() + "-1") == null) {
							rri.put(pos + "-1", new ResourceRequestInfo(ilm.get(i).getQuestion().getId(),
									ELearningObjectType.QUESTION, 1L, 1L, "Unknown", pos));
						} else {
							rri.get(pos.toString() + "-1").incRequests();
						}
					}
					else
					{
						if (rri.get(pos.toString() + ilm.get(i).getQuestion().getId()) == null) {
							rri.put(pos.toString() + ilm.get(i).getQuestion().getId(), new ResourceRequestInfo(ilm
									.get(i).getQuestion().getId(), ELearningObjectType.QUESTION, 1L, 1L, ilm.get(i)
									.getQuestion().getTitle(), pos));
						} else {
							rri.get(pos.toString() + ilm.get(i).getQuestion().getId()).incRequests();
						}
					}

				}
			}
			if (rri.values() != null) {
				result.setQuestionRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
		}
		if (resourceTypes.contains(ELearningObjectType.QUIZ.toString()) || allTypes)
		{
			final Criteria criteria = session.createCriteria(QuizLogMining.class, "log");
			criteria.add(Restrictions.in("log.course.id", courses))
					.add(Restrictions.between("log.timestamp", startTime, endTime));
			@SuppressWarnings("unchecked")
			final List<QuizLogMining> ilm = criteria.list();
			final HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
			for (int i = 0; i < ilm.size(); i++) {
				if (ilm.get(i).getQuiz() != null)
				{
					Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
					if (pos > (resolution - 1)) {
						pos = resolution - 1;
					}

					if (ilm.get(i).getQuiz().getTitle().equals(""))
					{
						if (rri.get(pos.toString() + "-1") == null) {
							rri.put(pos.toString() + "-1", new ResourceRequestInfo(ilm.get(i).getQuiz().getId(),
									ELearningObjectType.QUIZ, 1L, 1L, "Unknown", pos));
						} else {
							rri.get(pos.toString() + "-1").incRequests();
						}
					}
					else
					{
						if (rri.get(pos.toString() + ilm.get(i).getQuiz().getId()) == null) {
							rri.put(pos.toString() + ilm.get(i).getQuiz().getId(),
									new ResourceRequestInfo(ilm.get(i).getQuiz().getId(), ELearningObjectType.QUIZ,
											1L, 1L, ilm.get(i).getQuiz().getTitle(), pos));
						} else {
							rri.get(pos.toString() + ilm.get(i).getQuiz().getId()).incRequests();
						}
					}
				}
			}
			if (rri.values() != null) {
				result.setQuizRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
		}
		if (resourceTypes.contains(ELearningObjectType.RESOURCE.toString()) || allTypes)
		{
			final Criteria criteria = session.createCriteria(ResourceLogMining.class, "log");
			criteria.add(Restrictions.in("log.course.id", courses))
					.add(Restrictions.between("log.timestamp", startTime, endTime));
			@SuppressWarnings("unchecked")
			final List<ResourceLogMining> ilm = criteria.list();
			final HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
			for (int i = 0; i < ilm.size(); i++) {
				if (ilm.get(i).getResource() != null)
				{
					Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
					if (pos > (resolution - 1)) {
						pos = resolution - 1;
					}

					if (ilm.get(i).getResource().getTitle().equals(""))
					{
						if (rri.get(pos.toString() + "-1") == null) {
							rri.put(pos + "-1", new ResourceRequestInfo(ilm.get(i).getResource().getId(),
									ELearningObjectType.RESOURCE, 1L, 1L, "Unknown", pos));
						} else {
							rri.get(pos.toString() + "-1").incRequests();
						}
					}
					else
					{
						if (rri.get(pos.toString() + ilm.get(i).getResource().getId()) == null) {
							rri.put(pos.toString() + ilm.get(i).getResource().getId(), new ResourceRequestInfo(ilm
									.get(i).getResource().getId(), ELearningObjectType.RESOURCE, 1L, 1L, ilm.get(i)
									.getResource().getTitle(), pos));
						} else {
							rri.get(pos.toString() + ilm.get(i).getResource().getId()).incRequests();
						}
					}
				}
			}
			if (rri.values() != null) {
				result.setResourceRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
		}
		if (resourceTypes.contains(ELearningObjectType.SCORM.toString()) || allTypes)
		{
			final Criteria criteria = session.createCriteria(ScormLogMining.class, "log");
			criteria.add(Restrictions.in("log.course.id", courses))
					.add(Restrictions.between("log.timestamp", startTime, endTime));
			@SuppressWarnings("unchecked")
			final List<ScormLogMining> ilm = criteria.list();
			final HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
			for (int i = 0; i < ilm.size(); i++)
			{
				Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
				if (pos > (resolution - 1)) {
					pos = resolution - 1;
				}

				if (ilm.get(i).getScorm().getTitle().equals(""))
				{
					if (rri.get(pos.toString() + "-1") == null) {
						rri.put(pos + "-1", new ResourceRequestInfo(ilm.get(i).getScorm().getId(),
								ELearningObjectType.SCORM, 1L, 1L, "Unknown", pos));
					} else {
						rri.get(pos.toString() + "-1").incRequests();
					}
				}
				else
				{
					if (rri.get(pos.toString() + ilm.get(i).getScorm().getId()) == null) {
						rri.put(pos.toString() + ilm.get(i).getScorm().getId(), new ResourceRequestInfo(ilm.get(i)
								.getScorm().getId(), ELearningObjectType.SCORM, 1L, 1L, ilm.get(i).getScorm()
								.getTitle(), pos));
					} else {
						rri.get(pos.toString() + ilm.get(i).getScorm().getId()).incRequests();
					}
				}
			}
			if (rri.values() != null) {
				result.setScormRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
		}
		if (resourceTypes.contains(ELearningObjectType.WIKI.toString()) || allTypes)
		{
			final Criteria criteria = session.createCriteria(WikiLogMining.class, "log");
			criteria.add(Restrictions.in("log.course.id", courses))
					.add(Restrictions.between("log.timestamp", startTime, endTime));
			@SuppressWarnings("unchecked")
			final List<WikiLogMining> ilm = criteria.list();
			final HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
			for (int i = 0; i < ilm.size(); i++) {
				if (ilm.get(i).getWiki() != null)
				{
					Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
					if (pos > (resolution - 1)) {
						pos = resolution - 1;
					}

					if (ilm.get(i).getWiki().getTitle().equals(""))
					{
						if (rri.get(pos.toString() + "-1") == null) {
							rri.put(pos + "-1", new ResourceRequestInfo(ilm.get(i).getWiki().getId(),
									ELearningObjectType.WIKI, 1L, 1L, "Unknown", pos));
						} else {
							rri.get(pos.toString() + "-1").incRequests();
						}
					}
					else
					{
						if (rri.get(pos.toString() + ilm.get(i).getWiki().getId()) == null) {
							rri.put(pos.toString() + ilm.get(i).getWiki().getId(),
									new ResourceRequestInfo(ilm.get(i).getWiki().getId(), ELearningObjectType.WIKI,
											1L, 1L, ilm.get(i).getWiki().getTitle(), pos));
						} else {
							rri.get(pos.toString() + ilm.get(i).getWiki().getId()).incRequests();
						}
					}
				}
			}
			if (rri.values() != null) {
				result.setWikiRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
		}

		return result;
	}

}
