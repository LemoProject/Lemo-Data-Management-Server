package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.MIN_SUP;
import static de.lemo.dms.processing.MetaParam.SESSION_WISE;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.USER_IDS;
import static de.lemo.dms.processing.MetaParam.TYPES;
import static de.lemo.dms.processing.MetaParam.MIN_LENGTH;
import static de.lemo.dms.processing.MetaParam.MAX_LENGTH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ca.pfv.spmf.sequentialpatterns.AlgoFournierViger08;
import ca.pfv.spmf.sequentialpatterns.SequenceDatabase;
import ca.pfv.spmf.sequentialpatterns.Sequences;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.lemo.dms.core.Clock;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListUserPathGraph;
import de.lemo.dms.processing.resulttype.UserPathLink;
import de.lemo.dms.processing.resulttype.UserPathNode;
import de.lemo.dms.processing.resulttype.UserPathObject;

@Path("frequentPathsViger")
public class QFrequentPathsViger extends Question{

	private static HashMap<String, ILogMining> idToLogM = new HashMap<String, ILogMining>();
	private static HashMap<String, ArrayList<Long>> requests = new HashMap<String, ArrayList<Long>>();
	private static HashMap<String, Integer> idToInternalId = new HashMap<String, Integer>();
	private static HashMap<Integer, String> internalIdToId = new HashMap<Integer, String>();
	
	
    @POST
    public ResultListUserPathGraph compute(
    		@FormParam(COURSE_IDS) List<Long> courses, 
    		@FormParam(USER_IDS) List<Long> users, 
    		@FormParam(TYPES) List<String> types,
    		@FormParam(MIN_LENGTH) Long minLength,
    		@FormParam(MAX_LENGTH) Long maxLength,
    		@FormParam(MIN_SUP) Double minSup, 
    		@FormParam(SESSION_WISE) boolean sessionWise,
    		@FormParam(START_TIME) Long startTime,
    		@FormParam(END_TIME) Long endTime) {
		
        ArrayList<UserPathNode> nodes = Lists.newArrayList();
        ArrayList<UserPathLink> links = Lists.newArrayList();
		
        if(courses!=null && courses.size() > 0)
        {
        	System.out.print("Parameter list: Courses: " + courses.get(0));
        	for(int i = 1; i < courses.size(); i++)
        		System.out.print(", " + courses.get(i));
        	System.out.println();
        }
        if(users!=null && users.size() > 0)
        {
        	System.out.print("Parameter list: Users: " + users.get(0));
        	for(int i = 1; i < users.size(); i++)
        		System.out.print(", " + users.get(i));
        	System.out.println();
        }
        if(types != null && types.size() > 0)
        {
        	System.out.print("Parameter list: Types: : "+types.get(0));
        	for(int i = 1; i < types.size(); i++)
        		System.out.print(", " + types.get(i));
        	System.out.println();
        }
        if(minLength != null && maxLength != null && minLength < maxLength)
        {
            System.out.println("Parameter list: Minimum path length: : "+minLength);
            System.out.println("Parameter list: Maximum path length: : "+maxLength);
        }
        System.out.println("Parameter list: Minimum Support: : "+minSup);
        System.out.println("Parameter list: Session Wise: : "+sessionWise);
        System.out.println("Parameter list: Start time: : "+startTime);
        System.out.println("Parameter list: End time: : "+endTime);
        
		try
		{
			SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
			
			sequenceDatabase.loadLinkedList(generateLinkedList(courses, users, types, minLength, maxLength, startTime, endTime));
				//sequenceDatabase.loadFile(generateInputFile(courseIds, userIds, startTime, endTime));
				
			AlgoFournierViger08 algo  = new AlgoFournierViger08(minSup, 0L, 1L, 0L, 1000L , null,  true, false);
			
			// execute the algorithm
			Clock c = new Clock();
			Sequences res = algo.runAlgorithm(sequenceDatabase); 
			System.out.println("Time for Hirate-calculation: " + c.get() );
			
			LinkedHashMap<String, UserPathObject> pathObjects = Maps.newLinkedHashMap();
			Long pathId = 0L;
			System.out.println();
			for(int i = 0; i < res.getLevelCount(); i++)
			{
				for(int j = 0; j < res.getLevel(i).size(); j++)
				{
					String predecessor = null;
					Long absSup = Long.valueOf(res.getLevel(i).get(j).getAbsoluteSupport());
					pathId++;
					System.out.println("New "+ i +"-Sequence. Support : " + res.getLevel(i).get(j).getAbsoluteSupport());
					for(int k = 0; k < res.getLevel(i).get(j).size(); k++)
					{
						
						String obId = internalIdToId.get(res.getLevel(i).get(j).get(k).getItems().get(0).getId());
						
						ILogMining ilo = idToLogM.get(obId);
						
						String type = ilo.getClass().getSimpleName();
				
						String posId = String.valueOf(pathObjects.size());
						
						if(predecessor != null)
						{
							pathObjects.put(posId, new UserPathObject(posId, ilo.getTitle(), absSup, type, 
		                       		 Double.valueOf(ilo.getDuration()), ilo.getPrefix(), pathId, 
		                       		 Long.valueOf(requests.get(obId).size()), Long.valueOf(new HashSet<Long>(requests.get(obId)).size())));
								
							// Increment or create predecessor edge
							pathObjects.get(predecessor).addEdgeOrIncrement(posId);
						}
						else
						{
	                        pathObjects.put(posId, new UserPathObject(posId, ilo.getTitle(), absSup,
	                                type, Double.valueOf(ilo.getDuration()), ilo.getPrefix(), pathId,  Long.valueOf(requests.get(obId).size()), Long.valueOf(new HashSet<Long>(requests.get(obId)).size())));
						}
						predecessor = posId;
					}
					
				}
			}
			System.out.println("\n");
	
			for(UserPathObject pathEntry : pathObjects.values()) {
	
	            UserPathObject path = pathEntry;
	            path.setWeight(path.getWeight());
	            path.setPathId(pathEntry.getPathId());
	            nodes.add(new UserPathNode(path,true));
	            String sourcePos = path.getId();
	
	            for(Entry<String, Integer> linkEntry : pathEntry.getEdges().entrySet()) {
	                UserPathLink link = new UserPathLink();
	                link.setSource(sourcePos);
	                link.setPathId(path.getPathId());
	                link.setTarget(linkEntry.getKey());
	                link.setValue(String.valueOf(linkEntry.getValue()));
	                links.add(link);
	            }
	        }
        
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			requests.clear();
			idToLogM.clear();
			internalIdToId.clear();
			idToInternalId.clear();
		}
		return new ResultListUserPathGraph(nodes, links);
	}
	
	
	
	/**
	 * Generates the necessary list of input-strings, containing the sequences (user paths) for the BIDE+ algorithm
	 * 
	 * @param courses	Course-Ids
	 * @param users		User-Ids
	 * @param starttime	Start time
	 * @param endtime	End time
	 * 
	 * @return	The path to the generated file 
	 */
	@SuppressWarnings("unchecked")
	private static LinkedList<String> generateLinkedListSessionBound(List<Long> courses, List<Long> users, List<String> types, Long minLength, Long maxLength, Long starttime, Long endtime)
	{
		LinkedList<String> result = new LinkedList<String>();
		try{
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			
			Session session =  dbHandler.getMiningSession();
			
			Criteria criteria = session.createCriteria(ILogMining.class, "log");
			if(courses.size() > 0)
				criteria.add(Restrictions.in("log.course.id", courses));
			if(users.size() > 0)
				criteria.add(Restrictions.in("log.user.id", users));
			criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
			ArrayList<ILogMining> list = (ArrayList<ILogMining>)criteria.list();
			
			System.out.println("Read "+ list.size()+" logs.");
			ArrayList<ArrayList<ILogMining>> uhis = new ArrayList<ArrayList<ILogMining>>();
			
			HashMap<Long, ArrayList<ILogMining>> logMap = new HashMap<Long, ArrayList<ILogMining>>();
			int max = 0;
			for(int i = 0; i < list.size(); i++)
			{
				if(list.get(i).getUser() != null && list.get(i).getLearnObjId() != null)
					if(logMap.get(list.get(i).getUser().getId()) == null)
					{
						ArrayList<ILogMining> a = new ArrayList<ILogMining>();
						a.add(list.get(i));
						logMap.put(list.get(i).getUser().getId(), a);
						if(logMap.get(list.get(i).getUser().getId()).size() > max)
							max = logMap.get(list.get(i).getUser().getId()).size();
					}
					else
					{
						
						logMap.get(list.get(i).getUser().getId()).add(list.get(i));
						Collections.sort(logMap.get(list.get(i).getUser().getId()));
						if(logMap.get(list.get(i).getUser().getId()).size() > max)
							max = logMap.get(list.get(i).getUser().getId()).size();
						if(list.get(i).getDuration() == -1)
						{
							uhis.add(new ArrayList<ILogMining>(logMap.get(list.get(i).getUser().getId())));
							logMap.remove(list.get(i).getUser().getId());
						}
					}	
			}
			
			uhis.addAll(logMap.values());
			
			Integer[] lengths = new Integer[max /10 +1];
			for(int i = 0; i < lengths.length; i++)
				lengths[i] = 0;
			
			for(int i = 0; i< uhis.size(); i++)
			{
				lengths[uhis.get(i).size() / 10]++;				
			}

			for(int i = 0 ; i < lengths.length; i++)
				if(lengths[i] != 0)
				{
					System.out.println("Paths of length " + i + "0 - " + ( i +1 ) + "0: " +lengths[i]);
				}
			
			System.out.println("Generated "+ uhis.size()+" user histories. Max length @ "+ max);
	    	
	    	int z = 0;
	    	
	    	//Write data to output file
			for(Iterator<ArrayList<ILogMining>> iter = uhis.iterator(); iter.hasNext();)
			{
				ArrayList<ILogMining> l = iter.next();
				if(l.size() > 5)
				{
					String line = "";
					for(int i = 0; i < l.size(); i++)
					{
						if(idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
							idToLogM.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), l.get(i));
						
						if(requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
						{
								ArrayList<Long> us = new ArrayList<Long>();
								us.add(l.get(i).getUser().getId());
								requests.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), us);
						}
						else
							requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()).add(l.get(i).getUser().getId());
						
						line += "<" + i + "> " + l.get(i).getPrefix() + "" + l.get(i).getLearnObjId() + " -1 ";
					}
					line += "-2";
					result.add(line);
					z++;
				}
				
			}
	    	System.out.println("Wrote "+ z+" user histories.");
	    	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	

	/**
	 * Generates the necessary list of input-strings, containing the sequences (user paths) for the BIDE+ algorithm
	 * 
	 * @param courses	Course-Ids
	 * @param users		User-Ids
	 * @param starttime	Start time
	 * @param endtime	End time
	 * 
	 * @return	The path to the generated file 
	 */
	@SuppressWarnings("unchecked")
	private static LinkedList<String> generateLinkedList(List<Long> courses, List<Long> users, List<String> types, Long minLength, Long maxLength, Long starttime, Long endtime)
	{
		LinkedList<String> result = new LinkedList<String>();
		boolean hasBorders = minLength != null && maxLength != null && maxLength > 0 && minLength < maxLength;
		boolean hasTypes = types != null && types.size() > 0;
		try{
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			
			Session session =  dbHandler.getMiningSession();
			
			Criteria criteria = session.createCriteria(ILogMining.class, "log");
			if(courses.size() > 0)
				criteria.add(Restrictions.in("log.course.id", courses));
			if(users.size() > 0)
				criteria.add(Restrictions.in("log.user.id", users));
			criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
			ArrayList<ILogMining> list = (ArrayList<ILogMining>)criteria.list();
			
			System.out.println("Read "+ list.size()+" logs.");
			
			int max=0;
			
			HashMap<Long, ArrayList<ILogMining>> logMap = new HashMap<Long, ArrayList<ILogMining>>();
			
			// int pre = 1000;
			
			for(int i = 0; i < list.size(); i++)
			{
				if(list.get(i).getUser() != null && list.get(i).getLearnObjId() != null)
					//If there isn't a user history for this user-id create a new one
					if(logMap.get(list.get(i).getUser().getId()) == null)
					{
						//User histories are saved in an ArrayList of ILogMining-objects
						ArrayList<ILogMining> a = new ArrayList<ILogMining>();
						//Add current ILogMining-object to user-history
						a.add(list.get(i));
						//Add user history to the user history map
						logMap.put(list.get(i).getUser().getId(), a);
					}
					else
					{
						//Add current ILogMining-object to user-history
						logMap.get(list.get(i).getUser().getId()).add(list.get(i));
						//Sort the user's history (by time stamp)
						Collections.sort(logMap.get(list.get(i).getUser().getId()));
					}	
			}
			
			//Just changing the container for the user histories
			ArrayList<ArrayList<ILogMining>> uhis = new ArrayList<ArrayList<ILogMining>>();//(logMap.values());
			int id = 1;
			for(ArrayList<ILogMining> uLog : logMap.values())
			{
				
				ArrayList<ILogMining> tmp = new ArrayList<ILogMining>();
				boolean containsType = false;
				for(ILogMining iLog : uLog)
				{
					if(idToInternalId.get(iLog.getPrefix() + " " + iLog.getLearnObjId()) == null)
					{
						internalIdToId.put(id, iLog.getPrefix() + " " + iLog.getLearnObjId());
						idToInternalId.put(iLog.getPrefix() + " " + iLog.getLearnObjId(), id);
						id++;						
					}
					if(hasTypes)
						for(String type : types)
						{
							if(iLog.getClass().getSimpleName().toLowerCase().contains(type.toLowerCase()))
							{
								containsType = true;
								tmp.add(iLog);
								break;
							}
							
						}
					if(!hasTypes)
						tmp.add(iLog);					
				}
				if((!hasBorders || (tmp.size() >= minLength && tmp.size() <= maxLength)) && (!hasTypes || containsType))
				{
					uhis.add(tmp);
					if(tmp.size() > max)
						max = tmp.size();
				}
			}
			
			//This part is only for statistics - group histories of similar length together and display there respective lengths
			Integer[] lengths = new Integer[max /10 +1];
			for(int i = 0; i < lengths.length; i++)
				lengths[i] = 0;
			
			for(int i = 0; i< uhis.size(); i++)
				lengths[uhis.get(i).size() / 10]++;				


			for(int i = 0 ; i < lengths.length; i++)
				if(lengths[i] != 0)
				{
					System.out.println("Paths of length " + i + "0 - " + ( i +1 ) + "0: " +lengths[i]);
				}
			
			System.out.println("Generated "+ uhis.size()+" user histories. Max length @ "+ max);
			
			int z = 0;
			
			//Convert all user histories or "paths" into the format, that is requested by the BIDE-algorithm-class
	    	for(ArrayList<ILogMining> l : uhis)
			{
    			String line = "";
    			for(int i = 0; i < l.size(); i++)
				{
					if(idToLogM.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
						idToLogM.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), l.get(i));
					
					//Update request numbers
					if(requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) == null)
					{
							ArrayList<Long> us = new ArrayList<Long>();
							us.add(l.get(i).getUser().getId());
							requests.put(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId(), us);
					}
					else
						requests.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()).add(l.get(i).getUser().getId());
					//The id of the object gets the prefix, indicating it's class. This is important for distinction between objects of different ILogMining-classes but same ids
					line += "<" + i + "> " + idToInternalId.get(l.get(i).getPrefix() + " " + l.get(i).getLearnObjId()) + " -1 ";
				}
				line += "-2";
				
				result.add(line);
				z++;
			}
	    	System.out.println("Wrote "+ z+" logs.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
}
