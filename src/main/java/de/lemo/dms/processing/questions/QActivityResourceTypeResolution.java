package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.parameter.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.parameter.MetaParam.END_TIME;
import static de.lemo.dms.processing.parameter.MetaParam.RESOLUTION;
import static de.lemo.dms.processing.parameter.MetaParam.START_TIME;
import static de.lemo.dms.processing.parameter.MetaParam.TYPES;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.MetaParam;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListRRITypes;
import de.lemo.dms.service.ELearnObjType;

@QuestionID("activityresourcetyperesolution")
public class QActivityResourceTypeResolution extends Question {
    
    protected List<MetaParam<?>> createParamMetaData() {
	    List<MetaParam<?>> parameters = new LinkedList<MetaParam<?>>();
        
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
        List<?> latest = dbHandler.performQuery(EQueryType.SQL, "Select max(timestamp) from resource_log");
        Long now = System.currentTimeMillis()/1000;
        
        if(latest.size() > 0)
        	now = ((BigInteger)latest.get(0)).longValue();
     
        Collections.<MetaParam<?>> addAll( parameters,
                Parameter.create(COURSE_IDS,"Courses","List of courses."),
                Parameter.create(TYPES, "ResourceTypes","List of resource types."),
                Interval.create(Long.class, START_TIME, "Start time", "", 0L, now, 0L), 
                Interval.create(Long.class, END_TIME, "End time", "", 0L, now, now),
                Interval.create(Long.class, RESOLUTION, "Resolution", "", 1L, 300L, 100L)
                );
        return parameters;
	}
	
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
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
	        Session session = dbHandler.getSession(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
        
			//Create and initialize array for results
			if(resourceTypes.contains(ELearnObjType.ASSIGNMENT.toString()) || all)
			{
				 Criteria criteria = session.createCriteria(AssignmentLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
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
								 rri.put(pos.toString() + "-1" + "", new ResourceRequestInfo(ilm.get(i).getAssignment().getId(), ELearnObjType.ASSIGNMENT, 1L, "Unknown", pos));
							 else
							 {
								rri.get(pos.toString() + "-1").incRequests();
							 }
						 else
							 if(rri.get(pos.toString() + ilm.get(i).getAssignment().getId()) == null)
								 rri.put(pos.toString() + ilm.get(i).getAssignment().getId() + "", new ResourceRequestInfo(ilm.get(i).getAssignment().getId(), ELearnObjType.ASSIGNMENT, 1L, ilm.get(i).getAssignment().getTitle(), pos));
							 else
							 {
								 
								rri.get(pos.toString() + ilm.get(i).getAssignment().getId()).incRequests();
							 }
					 }
				 if(rri.values() != null)
					 list.setAssignmentRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}        
			if(resourceTypes.contains(ELearnObjType.FORUM.toString()) || all)
			{
				 Criteria criteria = session.createCriteria(ForumLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
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
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getForum().getId(), ELearnObjType.FORUM, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else{
							 if(rri.get(pos.toString() + ilm.get(i).getForum().getId()) == null)
								 rri.put(pos.toString() +ilm.get(i).getForum().getId(), new ResourceRequestInfo(ilm.get(i).getForum().getId(), ELearnObjType.FORUM, 1L, ilm.get(i).getForum().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getForum().getId()).incRequests();
						 }
					 }
				 if(rri.values() != null)
					 list.setForumRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(ELearnObjType.QUESTION.toString()) || all)
			{
				 Criteria criteria = session.createCriteria(QuestionLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
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
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getQuestion().getId(), ELearnObjType.QUESTION, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getQuestion().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getQuestion().getId(), new ResourceRequestInfo(ilm.get(i).getQuestion().getId(), ELearnObjType.QUESTION, 1L, ilm.get(i).getQuestion().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getQuestion().getId()).incRequests();
						 }
						 
					 }
				 if(rri.values() != null)
					 list.setQuestionRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(ELearnObjType.QUIZ.toString()) || all)
			{
				 Criteria criteria = session.createCriteria(QuizLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
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
								 rri.put(pos.toString() + "-1" , new ResourceRequestInfo(ilm.get(i).getQuiz().getId(), ELearnObjType.QUIZ, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getQuiz().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getQuiz().getId(), new ResourceRequestInfo(ilm.get(i).getQuiz().getId(), ELearnObjType.QUIZ, 1L, ilm.get(i).getQuiz().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getQuiz().getId()).incRequests();
						 }
					 }
				 if(rri.values() != null)
					 list.setQuizRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(ELearnObjType.RESOURCE.toString()) || all)
			{
				 Criteria criteria = session.createCriteria(ResourceLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
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
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getResource().getId(), ELearnObjType.RESOURCE, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getResource().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getResource().getId(), new ResourceRequestInfo(ilm.get(i).getResource().getId(), ELearnObjType.RESOURCE, 1L, ilm.get(i).getResource().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getResource().getId()).incRequests();
						 }
					 }
				 if(rri.values() != null)
					 list.setResourceRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(ELearnObjType.SCORM.toString()) || all)
			{
				Criteria criteria = session.createCriteria(ScormLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
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
							 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getScorm().getId(), ELearnObjType.SCORM, 1L, "Unknown", pos));
						 else
							 rri.get(pos.toString() + "-1").incRequests();
					 }
					 else
					 {
						 if(rri.get(pos.toString() + ilm.get(i).getScorm().getId()) == null )
							 rri.put(pos.toString() + ilm.get(i).getScorm().getId(), new ResourceRequestInfo(ilm.get(i).getScorm().getId(), ELearnObjType.SCORM, 1L, ilm.get(i).getScorm().getTitle(), pos));
						 else
							 rri.get(pos.toString() + ilm.get(i).getScorm().getId()).incRequests();
					 }
				 }
				 if(rri.values() != null)
					 list.setScormRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(ELearnObjType.WIKI.toString()) || all)
			{
				Criteria criteria = session.createCriteria(WikiLogMining.class, "log");
				criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
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
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getWiki().getId(), ELearnObjType.WIKI, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getWiki().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getWiki().getId(), new ResourceRequestInfo(ilm.get(i).getWiki().getId(), ELearnObjType.WIKI, 1L, ilm.get(i).getWiki().getTitle(), pos));
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
