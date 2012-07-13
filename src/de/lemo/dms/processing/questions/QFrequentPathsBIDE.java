package de.lemo.dms.processing.questions;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ca.pfv.spmf.sequentialpatterns.AlgoBIDEPlus;
import ca.pfv.spmf.sequentialpatterns.SequenceDatabase;
import ca.pfv.spmf.sequentialpatterns.Sequences;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.QuestionID;
import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Parameter;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

@QuestionID("frequentPaths")
public class QFrequentPathsBIDE {

	private static final String STARTTIME = "startTime";
	private static final String ENDTIME = "endTime";
	private static final String MINSUP = "minimum support";
	private static final String SESSIONWISE = "heed sessions";
	private static final String COURSE_IDS = "course_ids";
	private static final String USER_IDS = "user_ids";

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
                Parameter.create(USER_IDS,"User","List of users."),
                Parameter.create(SESSIONWISE,"Heed sessions","Choose if user histories shall be devided into sessions."),
                Interval.create(double.class, MINSUP, "Start time", "", 0.0d, 1.0d, 1d), 
                Interval.create(long.class, STARTTIME, "Start time", "", 0L, now, 0L), 
                Interval.create(long.class, ENDTIME, "End time", "", 0L, now, now)
                );
        return parameters;
	}
	
	@GET
    public ResultListLongObject compute(
    		@QueryParam(COURSE_IDS) List<Long> courseIds, 
    		@QueryParam(USER_IDS) List<Long> userIds, 
    		@QueryParam(MINSUP) double minSup, 
    		@QueryParam(SESSIONWISE) boolean heedSessions, 
    		@QueryParam(STARTTIME) long startTime, 
    		@QueryParam(ENDTIME) long endTime) {
		
		ResultListLongObject result = new ResultListLongObject();
		try{
			
		
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
		Session session =  dbHandler.getSession();
		SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
		
		if(!heedSessions)
			sequenceDatabase.loadFile(generateInputFile(courseIds, userIds, startTime, endTime));
		else
			sequenceDatabase.loadFile(generateInputFileSessionBound(courseIds, userIds, startTime, endTime));
			
		AlgoBIDEPlus algo  = new AlgoBIDEPlus(minSup);
		
		// execute the algorithm
		Sequences res = algo.runAlgorithm(sequenceDatabase); 
		
		HashMap<Long, CourseMining> resToCouMap = new HashMap<Long, CourseMining>();
		
		Criteria cou = session.createCriteria(CourseResourceMining.class);
		
		List<CourseResourceMining> crl =  cou.list();
		for(int i = 0; i < crl.size(); i++)
			if(crl.get(i).getResource() != null && crl.get(i).getCourse() != null)
				resToCouMap.put(crl.get(i).getResource().getId(), crl.get(i).getCourse());

		FileWriter out = new FileWriter("./bide_res.txt");
    	PrintWriter pout = new PrintWriter(out);
    	
		for(int i = 0; i < res.getLevelCount(); i++)
		{
			
			for(int j = 0; j < res.getLevel(i).size(); j++)
			{
				System.out.println("New "+ i +"-Sequence. Support : " + res.getLevel(i).get(j).getAbsoluteSupport());
				pout.println("New "+ i +"-Sequence. Support : " + res.getLevel(i).get(j).getAbsoluteSupport());
				for(int k = 0; k < res.getLevel(i).get(j).size(); k++)
				{
					Criteria c = session.createCriteria(ResourceMining.class, "res");
					c.add(Restrictions.eq("res.id", Long.valueOf(res.getLevel(i).get(j).get(k).getItems().get(0).getId()) ));
					
					ResourceMining r = (ResourceMining) c.list().get(0);
					String s = "Unknown Course";
					if(resToCouMap.get(r.getId()) != null)
						s = resToCouMap.get(r.getId()).getTitle();
					System.out.println(r.getId() + "\t" + s + "\t" + r.getType() + "\t" +r.getTitle() + "\t"+r.getPosition());
					pout.println(r.getId() + "\t" + s + "\t" + r.getType() + "\t" +r.getTitle() + "\t"+r.getPosition());
				}
				System.out.println("");
				pout.println("");
			}
		}
		
		for(int i = 1; i < res.getLevelCount(); i++)
		{
			System.out.println("Number of sequences of length "+ i + ": "+res.getLevel(i).size());
			pout.println("Number of sequences of length "+ i + ": "+res.getLevel(i).size());
		}
		//algo.printStatistics(sequenceDatabase.size());
		pout.close();
		
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Generates the necessary input file, containing the sequences (user paths) for the BIDE+ algorithm
	 * 
	 * @param courses	Course-Ids
	 * @param users		User-Ids
	 * @param starttime	Start time
	 * @param endtime	End time
	 * 
	 * @return	The path to the generated file 
	 */
	private static String generateInputFile(List<Long> courses, List<Long> users, Long starttime, Long endtime)
	{
		String output = "./"+System.currentTimeMillis()+"_BIDEInput.txt";
		try{
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
			
			Session session =  dbHandler.getSession();
			
			
			Criteria criteria = session.createCriteria(ResourceLogMining.class, "log");
			if(courses.size() > 0)
				criteria.add(Restrictions.in("log.course.id", courses));
			if(users.size() > 0)
				criteria.add(Restrictions.in("log.user.id", users));
			criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
			ArrayList<ResourceLogMining> list = (ArrayList<ResourceLogMining>)criteria.list();
			
			System.out.println("Read "+ list.size()+" logs.");
			
			int max=0;
			
			HashMap<Long, ArrayList<ResourceLogMining>> logMap = new HashMap<Long, ArrayList<ResourceLogMining>>();
			for(int i = 0; i < list.size(); i++)
			{
				if(list.get(i).getUser() != null && list.get(i).getResource() != null
						&& !list.get(i).getResource().getType().equals("Unknown") && !list.get(i).getResource().getType().equals("Index") 
						&& !list.get(i).getResource().getType().equals("Supplement"))
					if(logMap.get(list.get(i).getUser().getId()) == null)
					{
						ArrayList<ResourceLogMining> a = new ArrayList<ResourceLogMining>();
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
					}	
			}
			
			ArrayList<ArrayList<ResourceLogMining>> uhis = new ArrayList<ArrayList<ResourceLogMining>>(logMap.values());
			
			System.out.println("Generated "+ uhis.size()+" user histories. Max length @ "+ max);
			
			Integer[] lengths = new Integer[max /10 +1];
			for(int i = 0; i < lengths.length; i++)
				lengths[i] = 0;
			
			for(int i = 0; i< uhis.size(); i++)
			{
				lengths[uhis.get(i).size() / 10]++;				
			}

			
			
			FileWriter out = new FileWriter(output);
			
	    	PrintWriter pout = new PrintWriter(out);
	    	
	    	pout.println("# LeMo - Sequential pattern data");
	    	pout.println("#");
	    	Date d = new Date(); 
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd.MMMM.yyyy - HH:mm:ss");
	        
	    	pout.println("# Generated @ "+ sdf.format(d));
	    	pout.println("# Containing "+ uhis.size()+" user histories. Max length @ "+ max);
	    	pout.println("#");
			for(int i = 0 ; i < lengths.length; i++)
				if(lengths[i] != 0)
				{
					pout.println("# Paths of length " + i + "0 - " + ( i +1 ) + "0: " +lengths[i]);
					System.out.println("Paths of length " + i + "0 - " + ( i +1 ) + "0: " +lengths[i]);
				}
			pout.println("#");
	    	
	    	int z = 0;
			for(Iterator<ArrayList<ResourceLogMining>> iter = uhis.iterator(); iter.hasNext();)
			{
				ArrayList<ResourceLogMining> l = iter.next();
				if(l.size() > 5)
				{
					for(int i = 0; i < l.size(); i++)
						pout.print(l.get(i).getLearnObjId() + " -1 ");
					pout.println("-2");
					z++;
				}
			}

			
			//session.close();
	    	pout.close();
	    	System.out.println("Wrote "+ z+" logs.");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return output;
	}
	
	private static String generateInputFileSessionBound(List<Long> courses, List<Long> users, Long starttime, Long endtime)
	{
		String output = "./"+System.currentTimeMillis()+"_BIDEInput_sb.txt";
		try{
			IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
			
			Session session =  dbHandler.getSession();
			
			Criteria criteria = session.createCriteria(ResourceLogMining.class, "log");
			if(courses.size() > 0)
				criteria.add(Restrictions.in("log.course.id", courses));
			if(users.size() > 0)
				criteria.add(Restrictions.in("log.user.id", users));
			criteria.add(Restrictions.between("log.timestamp", starttime, endtime));
			ArrayList<ResourceLogMining> list = (ArrayList<ResourceLogMining>)criteria.list();
			
			System.out.println("Read "+ list.size()+" logs.");
			ArrayList<ArrayList<ResourceLogMining>> uhis = new ArrayList<ArrayList<ResourceLogMining>>();
			
			HashMap<Long, ArrayList<ResourceLogMining>> logMap = new HashMap<Long, ArrayList<ResourceLogMining>>();
			int max = 0;
			for(int i = 0; i < list.size(); i++)
			{
				if(list.get(i).getUser() != null && list.get(i).getResource() != null
						&& !list.get(i).getResource().getType().equals("Unknown") && !list.get(i).getResource().getType().equals("Index")
					&& !list.get(i).getResource().getType().equals("Supplement"))
					if(logMap.get(list.get(i).getUser().getId()) == null)
					{
						ArrayList<ResourceLogMining> a = new ArrayList<ResourceLogMining>();
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
							uhis.add(new ArrayList<ResourceLogMining>(logMap.get(list.get(i).getUser().getId())));
							logMap.remove(list.get(i).getUser().getId());
						}
					}	
			}
			
			uhis.addAll(logMap.values());
			
			Integer[] lengths = new Integer[max+1];
			for(int i = 0; i < lengths.length; i++)
				lengths[i] = 0;
			
			for(int i = 0; i< uhis.size(); i++)
			{
				lengths[uhis.get(i).size() /10 + 1]++;				
			}
			
			
			System.out.println("Generated "+ uhis.size()+" user histories. Max length @ "+ max);
			FileWriter out = new FileWriter(output);
	    	PrintWriter pout = new PrintWriter(out);
	    	
	    	//Write header for the output file
	    	pout.println("# LeMo - Sequential pattern data");
	    	
	    	Date d = new Date(); 
	        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
	        SimpleDateFormat sdf = new SimpleDateFormat("dd.MMMM.yyyy - HH:mm:ss");
	        
	    	pout.println("# Generated @ "+ sdf.format(d));
	    	pout.println("# Containing "+ uhis.size()+" user histories. Max length @ "+ max);
	    	
	    	for(int i = 0 ; i < lengths.length; i++)
				if(lengths[i] != 0)
				{
					pout.println("# Paths of length " + i + "0 - " + ( i +1 ) + "0: " +lengths[i]);
					System.out.println("Paths of length " + i + "0 - " + ( i +1 ) + "0: " +lengths[i]);
				}
	    	
	    	int z = 0;
	    	
	    	//Write data to output file
			for(Iterator<ArrayList<ResourceLogMining>> iter = uhis.iterator(); iter.hasNext();)
			{
				ArrayList<ResourceLogMining> l = iter.next();
				if(l.size() > 5)
				{
					for(int i = 0; i < l.size(); i++)
						pout.print(l.get(i).getLearnObjId() + " -1 ");
					pout.println("-2");
					z++;
				}
				
			}
			
			//session.close();
	    	pout.close();
	    	System.out.println("Wrote "+ z+" user histories.");
	    	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return output;
	}
	
	
	
}