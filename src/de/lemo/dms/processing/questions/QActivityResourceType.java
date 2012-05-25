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
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.ChatLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListResourceRequestInfo;
import de.lemo.dms.service.EResourceType;

@QuestionID("activityresourcetype")
public class QActivityResourceType extends Question{

    private static final String COURSE_IDS = "course_ids";
    private static final String STARTTIME = "starttime";
    private static final String ENDTIME = "endtime";
    private static final String TYPES = "types";

    @Override
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
                Interval.create(long.class, ENDTIME, "End time", "", 0L, now, now)
                );
        return parameters;
	}
	
    @GET
    public ResultListResourceRequestInfo compute(@QueryParam(COURSE_IDS) List<Long> courses, @QueryParam(STARTTIME) long startTime, @QueryParam(ENDTIME) long endTime, @QueryParam(TYPES) List<EResourceType> resourceTypes) 
    {
		boolean all = false;
		ResultListResourceRequestInfo list = new ResultListResourceRequestInfo();
		if(resourceTypes.size() == 0)
			all = true;
		//Check arguments
		if(startTime < endTime)
		{
			
			//Set up db-connection
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
	        Session session = dbHandler.getSession(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
        
			//Create and initialize array for results
			if(resourceTypes.contains(EResourceType.ASSIGNMENT) || all)
			{
				 Criteria criteria = session.createCriteria(AssignmentLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 List<AssignmentLogMining> ilm = criteria.list();
				 HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getAssignment() != null)
						 if(rri.get(ilm.get(i)) == null)
							 rri.put(ilm.get(i).getAssignment().getId(), new ResourceRequestInfo(ilm.get(i).getAssignment().getId(), EResourceType.ASSIGNMENT, 1L, ilm.get(i).getAssignment().getTitle()));
						 else
							 rri.get(ilm.get(i).getAssignment().getId()).incRequests();
				 if(rri.values() != null)
					 list.addAll(rri.values());
			}        
			if(resourceTypes.contains(EResourceType.FORUM) || all)
			{
				 Criteria criteria = session.createCriteria(ForumLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 List<ForumLogMining> ilm = criteria.list();
				 HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getForum() != null)
						 if(rri.get(ilm.get(i)) == null)
							 rri.put(ilm.get(i).getForum().getId(), new ResourceRequestInfo(ilm.get(i).getForum().getId(), EResourceType.FORUM, 1L, ilm.get(i).getForum().getTitle()));
						 else
							 rri.get(ilm.get(i).getForum().getId()).incRequests();
				 if(rri.values() != null)
					 list.addAll(rri.values());
			}
			if(resourceTypes.contains(EResourceType.QUESTION) || all)
			{
				 Criteria criteria = session.createCriteria(QuestionLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 List<QuestionLogMining> ilm = criteria.list();
				 HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getQuestion() != null)
						 if(rri.get(ilm.get(i)) == null )
							 rri.put(ilm.get(i).getQuestion().getId(), new ResourceRequestInfo(ilm.get(i).getQuestion().getId(), EResourceType.QUESTION, 1L, ilm.get(i).getQuestion().getTitle()));
						 else
							 rri.get(ilm.get(i).getQuestion().getId()).incRequests();
				 if(rri.values() != null)
					 list.addAll(rri.values());
			}
			if(resourceTypes.contains(EResourceType.QUIZ) || all)
			{
				 Criteria criteria = session.createCriteria(QuizLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 List<QuizLogMining> ilm = criteria.list();
				 HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getQuiz() != null)
						 if(rri.get(ilm.get(i)) == null)
							 rri.put(ilm.get(i).getQuiz().getId(), new ResourceRequestInfo(ilm.get(i).getQuiz().getId(), EResourceType.QUIZ, 1L, ilm.get(i).getQuiz().getTitle()));
						 else
							 rri.get(ilm.get(i).getQuiz().getId()).incRequests();
				 if(rri.values() != null)
					 list.addAll(rri.values());
			}
			if(resourceTypes.contains(EResourceType.RESOURCE) || all)
			{
				 Criteria criteria = session.createCriteria(ResourceLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 List<ResourceLogMining> ilm = criteria.list();
				 HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getResource() != null)
						 if(rri.get(ilm.get(i)) == null)
							 rri.put(ilm.get(i).getResource().getId(), new ResourceRequestInfo(ilm.get(i).getResource().getId(), EResourceType.RESOURCE, 1L, ilm.get(i).getResource().getTitle()));
						 else
							 rri.get(ilm.get(i).getResource().getId()).incRequests();
				 if(rri.values() != null)
					 list.addAll(rri.values());
			}
			if(resourceTypes.contains(EResourceType.SCORM) || all)
			{
				Criteria criteria = session.createCriteria(ScormLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 List<ScormLogMining> ilm = criteria.list();
				 HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getScorm() != null)
						 if(rri.get(ilm.get(i)) == null)
							 rri.put(ilm.get(i).getScorm().getId(), new ResourceRequestInfo(ilm.get(i).getScorm().getId(), EResourceType.SCORM, 1L, ilm.get(i).getScorm().getTitle()));
						 else
							 rri.get(ilm.get(i).getScorm().getId()).incRequests();
				 if(rri.values() != null)
					 list.addAll(rri.values());
			}
			if(resourceTypes.contains(EResourceType.WIKI) || all)
			{
				Criteria criteria = session.createCriteria(WikiLogMining.class, "log");
				 criteria.add(Restrictions.in("log.course.id", courses))
	                .add(Restrictions.between("log.timestamp", startTime, endTime));
				 List<WikiLogMining> ilm = criteria.list();
				 HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
				 for(int i = 0 ; i < ilm.size(); i++)
					 if(ilm.get(i).getWiki() != null)
						 if(rri.get(ilm.get(i)) == null)
							 rri.put(ilm.get(i).getWiki().getId(), new ResourceRequestInfo(ilm.get(i).getWiki().getId(), EResourceType.WIKI, 1L, ilm.get(i).getWiki().getTitle()));
						 else
							 rri.get(ilm.get(i).getWiki().getId()).incRequests();
				 if(rri.values() != null)
					 list.addAll(rri.values());
			}
		}
		return list;
    }
	
	
}
