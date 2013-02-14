/**
 * File ./main/java/de/lemo/dms/processing/questions/QActivityResourceType.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.questions;

import java.util.HashMap;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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
import de.lemo.dms.processing.resulttype.ResultListResourceRequestInfo;

/**
 * Checks which resources are used in certain courses
 * 
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 *
 */
@Path("activityresourcetype")
public class QActivityResourceType extends Question {

	@POST
	public ResultListResourceRequestInfo compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.TYPES) final List<String> resourceTypes)
	{
		boolean all = false;
		final ResultListResourceRequestInfo list = new ResultListResourceRequestInfo();
		if (resourceTypes.size() == 0) {
			all = true;
		}
		// Check arguments
		if (startTime < endTime)
		{

			final Session session = this.dbHandler.getMiningSession();

			// Create and initialize array for results
			if (resourceTypes.contains(ELearningObjectType.ASSIGNMENT.toString().toLowerCase()) || all)
			{
				final Criteria criteria = session.createCriteria(AssignmentLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
						.add(Restrictions.between("log.timestamp", startTime, endTime));

				@SuppressWarnings("unchecked")
				final List<AssignmentLogMining> ilm = criteria.list();
				final HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				for (int i = 0; i < ilm.size(); i++) {
					if (ilm.get(i).getAssignment() != null)
					{
						logger.info(ilm.get(i).getAssignment().getId());
						if (rri.get(ilm.get(i).getAssignment().getId()) == null) {
							rri.put(ilm.get(i).getAssignment().getId(), new ResourceRequestInfo(ilm.get(i)
									.getAssignment().getId(), ELearningObjectType.ASSIGNMENT, 1L, 1L, ilm.get(i)
									.getAssignment().getTitle(), 0L));
						} else
						{

							rri.get(ilm.get(i).getAssignment().getId()).incRequests();
						}
					}
				}
				if (rri.values() != null) {
					list.addAll(rri.values());
				}
			}
			if (resourceTypes.contains(ELearningObjectType.FORUM.toString().toLowerCase()) || all)
			{
				final Criteria criteria = session.createCriteria(ForumLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
						.add(Restrictions.between("log.timestamp", startTime, endTime));
				@SuppressWarnings("unchecked")
				final List<ForumLogMining> ilm = criteria.list();
				final HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				for (int i = 0; i < ilm.size(); i++) {
					if (ilm.get(i).getForum() != null) {
						if (rri.get(ilm.get(i).getForum().getId()) == null) {
							rri.put(ilm.get(i).getForum().getId(), new ResourceRequestInfo(ilm.get(i).getForum()
									.getId(), ELearningObjectType.FORUM, 1L, 1L, ilm.get(i).getForum().getTitle(), 0L));
						} else {
							rri.get(ilm.get(i).getForum().getId()).incRequests();
						}
					}
				}
				if (rri.values() != null) {
					list.addAll(rri.values());
				}
			}
			if (resourceTypes.contains(ELearningObjectType.QUESTION.toString().toLowerCase()) || all)
			{
				final Criteria criteria = session.createCriteria(QuestionLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
						.add(Restrictions.between("log.timestamp", startTime, endTime));
				@SuppressWarnings("unchecked")
				final List<QuestionLogMining> ilm = criteria.list();
				final HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				for (int i = 0; i < ilm.size(); i++) {
					if (ilm.get(i).getQuestion() != null) {
						if (rri.get(ilm.get(i).getQuestion().getId()) == null) {
							rri.put(ilm.get(i).getQuestion().getId(), new ResourceRequestInfo(ilm.get(i).getQuestion()
									.getId(), ELearningObjectType.QUESTION, 1L, 1L,
									ilm.get(i).getQuestion().getTitle(), 0L));
						} else {
							rri.get(ilm.get(i).getQuestion().getId()).incRequests();
						}
					}
				}
				if (rri.values() != null) {
					list.addAll(rri.values());
				}
			}
			if (resourceTypes.contains(ELearningObjectType.QUIZ.toString().toLowerCase()) || all)
			{
				final Criteria criteria = session.createCriteria(QuizLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
						.add(Restrictions.between("log.timestamp", startTime, endTime));
				@SuppressWarnings("unchecked")
				final List<QuizLogMining> ilm = criteria.list();
				final HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				for (int i = 0; i < ilm.size(); i++) {
					if (ilm.get(i).getQuiz() != null) {
						if (rri.get(ilm.get(i).getQuiz().getId()) == null) {
							rri.put(ilm.get(i).getQuiz().getId(), new ResourceRequestInfo(ilm.get(i).getQuiz().getId(),
									ELearningObjectType.QUIZ, 1L, 1L, ilm.get(i).getQuiz().getTitle(), 0L));
						} else {
							rri.get(ilm.get(i).getQuiz().getId()).incRequests();
						}
					}
				}
				if (rri.values() != null) {
					list.addAll(rri.values());
				}
			}
			if (resourceTypes.contains(ELearningObjectType.RESOURCE.toString().toLowerCase()) || all)
			{
				final Criteria criteria = session.createCriteria(ResourceLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
						.add(Restrictions.between("log.timestamp", startTime, endTime));
				@SuppressWarnings("unchecked")
				final List<ResourceLogMining> ilm = criteria.list();
				final HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				for (int i = 0; i < ilm.size(); i++) {
					if (ilm.get(i).getResource() != null) {
						if (rri.get(ilm.get(i).getResource().getId()) == null) {
							rri.put(ilm.get(i).getResource().getId(), new ResourceRequestInfo(ilm.get(i).getResource()
									.getId(), ELearningObjectType.RESOURCE, 1L, 1L,
									ilm.get(i).getResource().getTitle(), 0L));
						} else {
							rri.get(ilm.get(i).getResource().getId()).incRequests();
						}
					}
				}
				if (rri.values() != null) {
					list.addAll(rri.values());
				}
			}
			if (resourceTypes.contains(ELearningObjectType.SCORM.toString().toLowerCase()) || all)
			{
				final Criteria criteria = session.createCriteria(ScormLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
						.add(Restrictions.between("log.timestamp", startTime, endTime));
				@SuppressWarnings("unchecked")
				final List<ScormLogMining> ilm = criteria.list();
				final HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				for (int i = 0; i < ilm.size(); i++) {
					if (ilm.get(i).getScorm() != null) {
						if (rri.get(ilm.get(i).getScorm().getId()) == null) {
							rri.put(ilm.get(i).getScorm().getId(), new ResourceRequestInfo(ilm.get(i).getScorm()
									.getId(), ELearningObjectType.SCORM, 1L, 1L, ilm.get(i).getScorm().getTitle(), 0L));
						} else {
							rri.get(ilm.get(i).getScorm().getId()).incRequests();
						}
					}
				}
				if (rri.values() != null) {
					list.addAll(rri.values());
				}
			}
			if (resourceTypes.contains(ELearningObjectType.WIKI.toString().toLowerCase()) || all)
			{
				final Criteria criteria = session.createCriteria(WikiLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
						.add(Restrictions.between("log.timestamp", startTime, endTime));
				@SuppressWarnings("unchecked")
				final List<WikiLogMining> ilm = criteria.list();
				final HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				for (int i = 0; i < ilm.size(); i++) {
					if (ilm.get(i).getWiki() != null) {
						if (rri.get(ilm.get(i).getWiki().getId()) == null) {
							rri.put(ilm.get(i).getWiki().getId(), new ResourceRequestInfo(ilm.get(i).getWiki().getId(),
									ELearningObjectType.WIKI, 1L, 1L, ilm.get(i).getWiki().getTitle(), 0L));
						} else {
							rri.get(ilm.get(i).getWiki().getId()).incRequests();
						}
					}
				}
				if (rri.values() != null) {
					list.addAll(rri.values());
				}
			}
		}
		return list;
	}

}
