package de.lemo.dms.processing.questions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResultListUserPathObject;
import de.lemo.dms.processing.resulttype.UserPathObject;

@QuestionID("userpathanalysis")
public class QUserPathAnalysis {
	
	 private static final String STARTTIME = "start_time";
	    private static final String ENDTIME = "end_time";
		private static final String USER_IDS = "user_ids";
		private static final String COURSE_IDS = "course_ids";
		private static final String TYPES = "types";
		private static final String LOGOUT_FLAG = "logout_flag";

	    protected List<ParameterMetaData<?>> createParamMetaData(){
	        List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();

	        IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
	        dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
	        List<?> latest = dbHandler.performQuery(EQueryType.SQL, "Select max(timestamp) from resource_log");
	        Long now = System.currentTimeMillis() / 1000;

	        if(latest.size() > 0)
	            now = ((BigInteger) latest.get(0)).longValue();

	        Collections.<ParameterMetaData<?>>
	                addAll(parameters,
	                       Interval.create(Long.class, STARTTIME, "Start time", "", 0L, now, 0L),
	                       Parameter.create(USER_IDS, "Users", "List of users-ids."),
	                       Parameter.create(COURSE_IDS, "Courses", "List of course-ids."),
	                       Parameter.create(TYPES, "Types", "Types of learning objects"),
	                       Parameter.create(LOGOUT_FLAG, "Logout flag", "Determines whether user-logouts cut user-paths"),
	                       Interval.create(Long.class, ENDTIME, "End time", "", 0L, now, now)
	                );
	        return parameters;
	    }
	    
	    /**
	     * Returns a list of Nodes and edges, representing the user-navigation matching the requirements given by the parameters.
	     * 
	     * @param courseIds			List of course-identifiers
	     * @param userIds			List of user-identifiers
	     * @param types				List of learn object types
	     * @param considerLogouts	If user-paths should be cut when a logout appears this must be set to "true".  
	     * @param startTime			LongInteger time stamp  
	     * @param endTime			LongInteger	time stamp 
	     * @return
	     */
	    @GET
	    public ResultListUserPathObject compute(@QueryParam(COURSE_IDS) List<Long> courseIds, @QueryParam(USER_IDS) List<Long> userIds, @QueryParam(TYPES) List<String> types,   @QueryParam(LOGOUT_FLAG) boolean considerLogouts, @QueryParam(STARTTIME) Long startTime, @QueryParam(ENDTIME) Long endTime){
	    	
	    	
	    	//DB-initialization 
	    	  IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
	          Session session = dbHandler.getSession(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
	          
	          //Create criteria for log-file-search
	          Criteria criteria = session.createCriteria(ILogMining.class, "log");
	          
	          criteria.add(Restrictions.between("log.timestamp", startTime, endTime));
	          
	          criteria.add(Restrictions.in("log.user.id", userIds));
	          if(courseIds != null && courseIds.size() > 0)
	        	  criteria.add(Restrictions.in("log.course.id", courseIds));
	          
	          
	          List<ILogMining> list = criteria.list();
	          
	          //Map for UserPathObjects
	          HashMap<String, UserPathObject> pathObjects = new HashMap<String, UserPathObject>();
	          
	          //Map for user histories
	          HashMap<Long, List<ILogMining>> userHis = new HashMap<Long, List<ILogMining>>();
	          
	          //Generate user histories
	          for(int i = 0 ; i < list.size(); i++)
	          {
	        	  boolean typeOk = false;
	        	  ILogMining curr = list.get(i);
	        	  if(types != null && types.size() > 0)
		        	  for(int j = 0; j < types.size(); j++)
		        	  {
		        		  //Check if ILog-object has acceptable learningObjectType
		        		  if(curr.getClass().toString().toLowerCase().contains(types.get(j)))
		        		  {
		        			  typeOk = true;
		        			  break;
		        		  }
		        	  }
	        	  else
	        		  typeOk = true;
	        	  if(typeOk)
		        	  if(userHis.get(curr.getUser().getId()) == null)
		        	  {
		        		  //If user is new create a new entry in the hash map and add log item
		        		  userHis.put(curr.getUser().getId(), new ArrayList<ILogMining>());
		        		  userHis.get(curr.getUser().getId()).add(curr);
		        	  }
		        	  else
		        		  userHis.get(curr.getUser().getId()).add(curr);
	          }
	          
	          
	         //Generate paths from user histories
	          for(Iterator<List<ILogMining>> iter = userHis.values().iterator(); iter.hasNext();)
	          {
	        	  String predNode = "";
	        	  List<ILogMining> l = iter.next();
		          for(int i = 0; i < l.size(); i++)
		        	  if(l.get(i) != null && l.get(i).getUser() != null)
		        	  {
			        	  ILogMining current = l.get(i);
			        	  String cId = current.getLearnObjId() + current.getClass().toString();
			        	  //Determines whether it's a new path (no predecessor for current node) or not
			        	  if(!predNode.equals(""))
			        		  if(pathObjects.get(cId) == null)
			        		  {
			        			  //If the node is new create entry in hash map			        			  
			        			  pathObjects.put(cId, new UserPathObject(cId, current.getTitle(), 1L, current.getClass().toString(), Double.valueOf(current.getDuration()), 1L));
			        			  pathObjects.get(predNode).addEdge(cId);
			        		  }
			        		  else
			        		  {
			        			  //If the node is already known, just add edge to predecessor and increment weight of the node
			        			  pathObjects.get(predNode).addEdge(cId);
			        			  pathObjects.get(cId).increaseWeight(Double.valueOf(current.getDuration()));
			        		  }
			        	  else
			        		  if(pathObjects.get(cId) == null)
			        			  pathObjects.put(cId, new UserPathObject(cId, current.getTitle(), 1L, current.getClass().toString(), Double.valueOf(current.getDuration()), 1L));
			        		  else
			        			  pathObjects.get(cId).increaseWeight(Double.valueOf(current.getDuration()));
			        	  
			        	  if(considerLogouts && current.getDuration() == -1L)
			        		  predNode = "";
			        	  else
			        		  predNode = cId;
		        	  }
	          }	       
	          
	          ArrayList<UserPathObject> al = new ArrayList<UserPathObject>(pathObjects.values());
	          for(int i = 0; i < al.size(); i++)
	          {
	        	  if(al.get(i).getEdges().size() > 1)
	        	  {
	        		  UserPathObject p = al.get(i);
	        		  System.out.println();
	        	  }
	          }
	          ResultListUserPathObject rlupo = new ResultListUserPathObject(new ArrayList<UserPathObject>(pathObjects.values()));	          
	    	return rlupo;
	    }

}
