package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.RESOLUTION;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.TYPES;

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
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.processing.ELearningObjectType;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListRRITypes;

@Path("activityresourcetyperesolution")
public class QActivityResourceTypeResolution extends Question {
	
    @POST
    public ResultListRRITypes compute(
            @FormParam(COURSE_IDS) List<Long> courses,
            @FormParam(START_TIME) Long startTime, 
            @FormParam(END_TIME) Long endTime,
            @FormParam(RESOLUTION) Long resolution,
            @FormParam(TYPES) List<String> resourceTypes) 
    {
		boolean all = false;
		ResultListRRITypes list = new ResultListRRITypes();
		//List<EResourceType> resourceTypes = new ArrayList<EResourceType>();
		if(resourceTypes.size() == 0)
			all = true;
		//Calculate size of time intervalls
		double intervall = (endTime - startTime) / (resolution);
		//Check arguments
		if(startTime < endTime)
		{
			
			//Set up db-connection
			IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

	        Session session = dbHandler.getMiningSession();
        
			//Create and initialize array for results
			if(resourceTypes.contains(ELearningObjectType.ASSIGNMENT.toString()) || all)
			{
				 Criteria criteria = session.createCriteria(AssignmentLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 @SuppressWarnings("unchecked")
                 List<AssignmentLogMining> ilm = criteria.list();
				 HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getAssignment() != null)
					 {						 
						 Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
						 if(pos > resolution - 1)
							 pos = resolution-1;							
						 
						 if(ilm.get(i).getAssignment().getTitle().equals(""))
							 if(rri.get(pos + "-1") == null)
								 rri.put(pos.toString() + "-1" + "", new ResourceRequestInfo(ilm.get(i).getAssignment().getId(), ELearningObjectType.ASSIGNMENT, 1L, 1L, "Unknown", pos));
							 else
							 {
								rri.get(pos.toString() + "-1").incRequests();
							 }
						 else
							 if(rri.get(pos.toString() + ilm.get(i).getAssignment().getId()) == null)
								 rri.put(pos.toString() + ilm.get(i).getAssignment().getId() + "", new ResourceRequestInfo(ilm.get(i).getAssignment().getId(), ELearningObjectType.ASSIGNMENT, 1L, 1L, ilm.get(i).getAssignment().getTitle(), pos));
							 else
							 {
								 
								rri.get(pos.toString() + ilm.get(i).getAssignment().getId()).incRequests();
							 }
					 }
				 if(rri.values() != null)
					 list.setAssignmentRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}        
			if(resourceTypes.contains(ELearningObjectType.FORUM.toString()) || all)
			{
				 Criteria criteria = session.createCriteria(ForumLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 @SuppressWarnings("unchecked")
				 List<ForumLogMining> ilm = criteria.list();
				 HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getForum() != null)
					 {
						 Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
						 if(pos > resolution - 1)
							 pos = resolution-1;							

						 if(ilm.get(i).getForum().getTitle().equals(""))
						 {
							 if(rri.get(pos + "-1") == null)
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getForum().getId(), ELearningObjectType.FORUM, 1L, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else{
							 if(rri.get(pos.toString() + ilm.get(i).getForum().getId()) == null)
								 rri.put(pos.toString() +ilm.get(i).getForum().getId(), new ResourceRequestInfo(ilm.get(i).getForum().getId(), ELearningObjectType.FORUM, 1L, 1L, ilm.get(i).getForum().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getForum().getId()).incRequests();
						 }
					 }
				 if(rri.values() != null)
					 list.setForumRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(ELearningObjectType.QUESTION.toString()) || all)
			{
				 Criteria criteria = session.createCriteria(QuestionLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 @SuppressWarnings("unchecked")
				 List<QuestionLogMining> ilm = criteria.list();
				 HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getQuestion() != null)
					 {
						 Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
						 if(pos > resolution - 1)
							 pos = resolution-1;							

						 if(ilm.get(i).getQuestion().getTitle().equals(""))
						 {
							 if(rri.get(pos.toString() + "-1") == null)
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getQuestion().getId(), ELearningObjectType.QUESTION, 1L, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getQuestion().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getQuestion().getId(), new ResourceRequestInfo(ilm.get(i).getQuestion().getId(), ELearningObjectType.QUESTION, 1L, 1L, ilm.get(i).getQuestion().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getQuestion().getId()).incRequests();
						 }
						 
					 }
				 if(rri.values() != null)
					 list.setQuestionRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(ELearningObjectType.QUIZ.toString()) || all)
			{
				 Criteria criteria = session.createCriteria(QuizLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 @SuppressWarnings("unchecked")
				 List<QuizLogMining> ilm = criteria.list();
				 HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getQuiz() != null)
					 {
						 Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
						 if(pos > resolution - 1)
							 pos = resolution-1;							

						 if(ilm.get(i).getQuiz().getTitle().equals(""))
						 {
							 if(rri.get(pos.toString() + "-1") == null)
								 rri.put(pos.toString() + "-1" , new ResourceRequestInfo(ilm.get(i).getQuiz().getId(), ELearningObjectType.QUIZ, 1L, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getQuiz().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getQuiz().getId(), new ResourceRequestInfo(ilm.get(i).getQuiz().getId(), ELearningObjectType.QUIZ, 1L, 1L, ilm.get(i).getQuiz().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getQuiz().getId()).incRequests();
						 }
					 }
				 if(rri.values() != null)
					 list.setQuizRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(ELearningObjectType.RESOURCE.toString()) || all)
			{
				 Criteria criteria = session.createCriteria(ResourceLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 @SuppressWarnings("unchecked")
				 List<ResourceLogMining> ilm = criteria.list();
				 HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getResource() != null)
					 {
						 Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
						 if(pos > resolution - 1)
							 pos = resolution-1;				
						 
						 if(ilm.get(i).getResource().getTitle().equals(""))
						 {
							 if(rri.get(pos.toString() + "-1") == null)
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getResource().getId(), ELearningObjectType.RESOURCE, 1L, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getResource().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getResource().getId(), new ResourceRequestInfo(ilm.get(i).getResource().getId(), ELearningObjectType.RESOURCE, 1L, 1L, ilm.get(i).getResource().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getResource().getId()).incRequests();
						 }
					 }
				 if(rri.values() != null)
					 list.setResourceRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(ELearningObjectType.SCORM.toString()) || all)
			{
				Criteria criteria = session.createCriteria(ScormLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 @SuppressWarnings("unchecked") 
				 List<ScormLogMining> ilm = criteria.list();
				 HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
				 {
					 Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
					 if(pos > resolution - 1)
						 pos = resolution-1;							
					 
					 if(ilm.get(i).getScorm().getTitle().equals(""))
					 {
						 if(rri.get(pos.toString() + "-1") == null)
							 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getScorm().getId(), ELearningObjectType.SCORM, 1L, 1L, "Unknown", pos));
						 else
							 rri.get(pos.toString() + "-1").incRequests();
					 }
					 else
					 {
						 if(rri.get(pos.toString() + ilm.get(i).getScorm().getId()) == null )
							 rri.put(pos.toString() + ilm.get(i).getScorm().getId(), new ResourceRequestInfo(ilm.get(i).getScorm().getId(), ELearningObjectType.SCORM, 1L, 1L, ilm.get(i).getScorm().getTitle(), pos));
						 else
							 rri.get(pos.toString() + ilm.get(i).getScorm().getId()).incRequests();
					 }
				 }
				 if(rri.values() != null)
					 list.setScormRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(ELearningObjectType.WIKI.toString()) || all)
			{
				Criteria criteria = session.createCriteria(WikiLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				@SuppressWarnings("unchecked") 
				List<WikiLogMining> ilm = criteria.list();
				 HashMap<String, ResourceRequestInfo> rri = new HashMap<String, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getWiki() != null)
					 {
						 Long pos = new Double((ilm.get(i).getTimestamp() - startTime) / intervall).longValue();
						 if(pos > resolution - 1)
							 pos = resolution-1;	
						 
						 if(ilm.get(i).getWiki().getTitle().equals(""))
						 {
							 if(rri.get(pos.toString() + "-1") == null)
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getWiki().getId(), ELearningObjectType.WIKI, 1L, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getWiki().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getWiki().getId(), new ResourceRequestInfo(ilm.get(i).getWiki().getId(), ELearningObjectType.WIKI, 1L, 1L, ilm.get(i).getWiki().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getWiki().getId()).incRequests();
						 }
					 }
				 if(rri.values() != null)
					 list.setWikiRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
		}
		return list;
    }
	
	
}
