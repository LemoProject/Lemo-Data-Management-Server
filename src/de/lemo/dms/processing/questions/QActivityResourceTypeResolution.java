package de.lemo.dms.processing.questions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

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
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListRRITypes;
import de.lemo.dms.service.EResourceType;

@QuestionID("activityresourcetyperesolution")
public class QActivityResourceTypeResolution {
	private static final String COURSE_IDS = "course_ids";
    private static final String STARTTIME = "starttime";
    private static final String ENDTIME = "endtime";
    private static final String TYPES = "types";
    private static final String RESOLUTION = "resolution";

    
    protected List<ParameterMetaData<?>> createParamMetaData() {
	    List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();
        
        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
        List<?> latest = dbHandler.performQuery(EQueryType.SQL, "Select max(timestamp) from resource_log");
        Long now = System.currentTimeMillis()/1000;
        
        if(latest.size() > 0)
        	now = ((BigInteger)latest.get(0)).longValue();
     
        Collections.<ParameterMetaData<?>> addAll( parameters,
                Parameter.create(COURSE_IDS,"Courses","List of courses."),
                Parameter.create(TYPES, "ResourceTypes","List of resource types."),
                Interval.create(long.class, STARTTIME, "Start time", "", 0L, now, 0L), 
                Interval.create(long.class, ENDTIME, "End time", "", 0L, now, now),
                Interval.create(long.class, RESOLUTION, "Resolution", "", 1L, 300L, 100L)
                );
        return parameters;
	}
	
    @GET
    public ResultListRRITypes compute(@QueryParam(COURSE_IDS) List<Long> courses, @QueryParam(STARTTIME) long startTime, @QueryParam(ENDTIME) long endTime, @QueryParam(RESOLUTION) long resolution, @QueryParam(TYPES) List<String> resourceTypes) 
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
			if(resourceTypes.contains(EResourceType.ASSIGNMENT.toString()) || all)
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
								 rri.put(pos.toString() + "-1" + "", new ResourceRequestInfo(ilm.get(i).getAssignment().getId(), EResourceType.ASSIGNMENT, 1L, "Unknown", pos));
							 else
							 {
								rri.get(pos.toString() + "-1").incRequests();
							 }
						 else
							 if(rri.get(pos.toString() + ilm.get(i).getAssignment().getId()) == null)
								 rri.put(pos.toString() + ilm.get(i).getAssignment().getId() + "", new ResourceRequestInfo(ilm.get(i).getAssignment().getId(), EResourceType.ASSIGNMENT, 1L, ilm.get(i).getAssignment().getTitle(), pos));
							 else
							 {
								 
								rri.get(pos.toString() + ilm.get(i).getAssignment().getId()).incRequests();
							 }
					 }
				 if(rri.values() != null)
					 list.setAssignmentRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}        
			if(resourceTypes.contains(EResourceType.FORUM.toString()) || all)
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
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getForum().getId(), EResourceType.FORUM, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else{
							 if(rri.get(pos.toString() + ilm.get(i).getForum().getId()) == null)
								 rri.put(pos.toString() +ilm.get(i).getForum().getId(), new ResourceRequestInfo(ilm.get(i).getForum().getId(), EResourceType.FORUM, 1L, ilm.get(i).getForum().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getForum().getId()).incRequests();
						 }
					 }
				 if(rri.values() != null)
					 list.setForumRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(EResourceType.QUESTION.toString()) || all)
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
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getQuestion().getId(), EResourceType.QUESTION, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getQuestion().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getQuestion().getId(), new ResourceRequestInfo(ilm.get(i).getQuestion().getId(), EResourceType.QUESTION, 1L, ilm.get(i).getQuestion().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getQuestion().getId()).incRequests();
						 }
						 
					 }
				 if(rri.values() != null)
					 list.setQuestionRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(EResourceType.QUIZ.toString()) || all)
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
								 rri.put(pos.toString() + "-1" , new ResourceRequestInfo(ilm.get(i).getQuiz().getId(), EResourceType.QUIZ, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getQuiz().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getQuiz().getId(), new ResourceRequestInfo(ilm.get(i).getQuiz().getId(), EResourceType.QUIZ, 1L, ilm.get(i).getQuiz().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getQuiz().getId()).incRequests();
						 }
					 }
				 if(rri.values() != null)
					 list.setQuizRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(EResourceType.RESOURCE.toString()) || all)
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
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getResource().getId(), EResourceType.RESOURCE, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getResource().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getResource().getId(), new ResourceRequestInfo(ilm.get(i).getResource().getId(), EResourceType.RESOURCE, 1L, ilm.get(i).getResource().getTitle(), pos));
							 else
								 rri.get(pos.toString() + ilm.get(i).getResource().getId()).incRequests();
						 }
					 }
				 if(rri.values() != null)
					 list.setResourceRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(EResourceType.SCORM.toString()) || all)
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
							 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getScorm().getId(), EResourceType.SCORM, 1L, "Unknown", pos));
						 else
							 rri.get(pos.toString() + "-1").incRequests();
					 }
					 else
					 {
						 if(rri.get(pos.toString() + ilm.get(i).getScorm().getId()) == null )
							 rri.put(pos.toString() + ilm.get(i).getScorm().getId(), new ResourceRequestInfo(ilm.get(i).getScorm().getId(), EResourceType.SCORM, 1L, ilm.get(i).getScorm().getTitle(), pos));
						 else
							 rri.get(pos.toString() + ilm.get(i).getScorm().getId()).incRequests();
					 }
				 }
				 if(rri.values() != null)
					 list.setScormRRI(new ArrayList<ResourceRequestInfo>(rri.values()));
			}
			if(resourceTypes.contains(EResourceType.WIKI.toString()) || all)
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
								 rri.put(pos + "-1" , new ResourceRequestInfo(ilm.get(i).getWiki().getId(), EResourceType.WIKI, 1L, "Unknown", pos));
							 else
								 rri.get(pos.toString() + "-1").incRequests();
						 }
						 else
						 {
							 if(rri.get(pos.toString() + ilm.get(i).getWiki().getId()) == null )
								 rri.put(pos.toString() + ilm.get(i).getWiki().getId(), new ResourceRequestInfo(ilm.get(i).getWiki().getId(), EResourceType.WIKI, 1L, ilm.get(i).getWiki().getTitle(), pos));
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
