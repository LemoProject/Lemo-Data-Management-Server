package de.lemo.dms.connectors.chemgapedia.fizHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.proxy.HibernateProxy;

import de.lemo.dms.core.Clock;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;

import de.lemo.dms.connectors.chemgapedia.fizHelper.LogLine;

/**
 * The Class LogReader.
 */
public class LogReader {
	
	//private ArrayList<LogObject> logList = new ArrayList<LogObject>();
	
	long startIndex = 0;

	private HashMap<Long, UserMining> users = new HashMap<Long, UserMining>();
	/** The resources. */
	private HashMap<String, ResourceMining> oldResources = new HashMap<String, ResourceMining>();
	
	private HashMap<String, ResourceMining> newResources = new HashMap<String, ResourceMining>();
	
	private HashMap<String, CourseResourceMining> courseResources = new HashMap<String, CourseResourceMining>();
	
	static HashMap<String, IDMappingMining> id_mapping;
	
	static HashMap<String, IDMappingMining> new_id_mapping;
	
	private HashMap<Long, ArrayList<LogObject>> userHistories = new HashMap<Long, ArrayList<LogObject>>();
	
	private HashMap<String, CourseMining> oldCourses = new HashMap<String, CourseMining>();
	
	ArrayList<ResourceLogMining> resourceLog = new ArrayList<ResourceLogMining>();
	
	Clock clock = new Clock();
	
	IDBHandler dbHandler;
	
	Long largestId;
	
	Long lastTimestamp = 0L;
	
	public LogReader(Long idcount)
	{
		try{
			
			new_id_mapping = new HashMap<String, IDMappingMining>();
			
			this.dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
			this.dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
			
			List<IDMappingMining> ids = (List<IDMappingMining>) dbHandler.performQuery(EQueryType.HQL, "from IDMappingMining x Where platform='Chemgapedia' order by x.id asc");
			
			id_mapping = new HashMap<String, IDMappingMining>();
			for(int i = 0; i < ids.size(); i++)
			{
				id_mapping.put(ids.get(i).getHash(), ids.get(i));
			}
			
	    	List<UserMining> us  = (List<UserMining>) dbHandler.performQuery(EQueryType.HQL, "FROM UserMining");
	    	for(int i = 0; i < us.size(); i++)
	    		this.users.put(us.get(i).getId(), us.get(i));
	    	System.out.println("Read "+us.size() + " UserMinings from database.");
	    	
	    	List<ResourceMining> rt  = (List<ResourceMining>) dbHandler.performQuery(EQueryType.HQL, "FROM ResourceMining");
	    	for(int i = 0; i < rt.size(); i++)
	    		this.oldResources.put(rt.get(i).getUrl(), rt.get(i));
	    	System.out.println("Read "+rt.size() + " ResourceMinings from database.");
	    	
	    	List<CourseMining> cm  = (List<CourseMining>)dbHandler.performQuery(EQueryType.HQL, "FROM CourseMining");
	    	for(int i = 0; i < cm.size(); i++)
	    	{
	    		this.oldCourses.put(cm.get(i).getTitle(), cm.get(i));
	    	}
	    	System.out.println("Read "+cm.size() + " CourseMinings from database.");
	    
	    	List<?> courseResource = (List<?>) dbHandler.performQuery(EQueryType.HQL, "FROM CourseResourceMining");
	    	for(int i = 0; i < courseResource.size(); i++)
	    		this.courseResources.put(((CourseResourceMining)courseResource.get(i)).getResource().getUrl(), ((CourseResourceMining)courseResource.get(i)));
	    	System.out.println("Read "+courseResource.size() + " CourseResourceMinings from database.");
	    	
	    	if(idcount == -1)
	    	{
	    		List<Long> ts = (List<Long>)(dbHandler.performQuery(EQueryType.HQL, "Select max(timestamp) from ResourceLogMining"));
				if(ts != null && ts.size() > 0 && ts.get(ts.size()-1) != null)
					lastTimestamp = ts.get(ts.size()-1);
	    		
	    		
	    		List<Long> l = (List<Long>) (dbHandler.performQuery(EQueryType.HQL, "Select largestId from ConfigMining x order by x.id asc"));
				if(l != null && l.size() > 0)
					largestId = l.get(l.size()-1);
				else
					largestId = 0L;
			}
	    	else
	    		largestId = idcount;
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
	
	
	/**
	 * Filters irrelevant lines (bots, spiders) out of the log file and writes a new one.
	 *
	 * @param outFile filename of the new filtered log file.
	 */
	public void filterServerLogFile()
	{
		clock.reset();
		ArrayList<LogObject>  a;
		Object[] user = this.users.values().toArray();
		HashMap<Long, UserMining> tempUsers = new HashMap<Long, UserMining>();
		int old = users.size();
		int totalLines = 0;
		int linesDeleted = 0;
		BotFinder bf = new BotFinder();
		for(int i= 0; i < user.length; i++)
		{
			int susp1 = 0;
			int susp2 = 0;
			int susp3 = 0;
			
			a = userHistories.get(((UserMining)user[i]).getId());
			
			if(a != null && a.size() > 0)
			{
				totalLines += a.size();
				susp1 = bf.checkFastOnes(a, 1).size();
				susp2 = bf.checkPeriods(a, 5);
				susp3 = bf.checkForRepetitions(a, 10);
				if(susp1 < 1 && susp2 == 0 && susp3 == 0)
					tempUsers.put(((UserMining)user[i]).getId(), (UserMining)user[i]);
				else
					linesDeleted += a.size();
			}
		}
		System.out.println("Filtered " + (old - tempUsers.size()) + " suspicious users  out of " + old + " (" + new DecimalFormat("0.00").format((double)(old-tempUsers.size())/(tempUsers.size()/100)) + "%), eliminating " + linesDeleted + " log lines (" + new DecimalFormat("0.00").format((double)(linesDeleted)/(totalLines/100)) + "%).");
		this.users = tempUsers;
	}
	
	/*
	private void prepareFile(String inFile, String outFile)
	{
		try
	    {
			FileWriter outFiles = new FileWriter(outFile);
		    PrintWriter out = new PrintWriter(outFiles);
			BufferedReader input =  new BufferedReader(new FileReader(inFile));
			String line = null;
			while (( line = input.readLine()) != null)
			{
				String[] sarr = line.split("\t");
				if(sarr.length > 5 && !sarr[4].contains("adsense.html") && !sarr[4].contains("/vsengine/dummy.html"))
				{
					String id;
					if(sarr[2].split("/").length == 2)
	    				  id = Encoder.createMD5(sarr[2].split("/")[0].trim()) + "/" +  sarr[2].split("/")[1] + "\t" + Encoder.createMD5(sarr[2].split("/")[0].trim());	
	    				  
	    			  else
	    				  id = "-\t" + Encoder.createMD5(sarr[3].trim());
					if(sarr[5].contains("www.google."))
						sarr[5] = "-";
					out.println(sarr[0].trim() + "\t" + sarr[1].trim() + "\t" + id + "\t" + "http://www.chemgapedia.de"+sarr[4].trim() + "\t" + sarr[5].trim());
				}
			}
		}catch(Exception e)
	    {
			System.out.println(e.getMessage());
	    }
	}*/
	
	/**
	 * Loads the data from the server-log-file.
	 *
	 * @param inFile the path of the server log file
	 */
	public void loadServerLogData(String inFile)
	{	    
	    try
	    {	    
	    	System.out.println("Reading server log...");
	    	BufferedReader input =  new BufferedReader(new FileReader(inFile));
	    	int count = 0;
	    	try 
	    	{
	    		String line = null;
	    		clock.reset();
	    		while (( line = input.readLine()) != null)
	    		{
	    			count++;
	    			boolean newRes = false;
	    			LogLine logLine = new LogLine(line);
	    			if(logLine.isValid() && logLine.getTimestamp() > lastTimestamp)
	    			{
	    				LogObject lo = new LogObject();
	    				String name;
	    				
	    				name = logLine.getId();
	    					
       	    			long id = -1;
       	    			//Set user-id
       	       			if(id_mapping.get(name) != null)
       	       			{
       	       				id = id_mapping.get(name).getId();
       	       				lo.setId(id);
       	       			}
       	       			if(id == -1 )
       	       			{
       	       				id = largestId + 1;
       	       				largestId = id;
       	       				id_mapping.put(name, new IDMappingMining(id, name, "Chemgapedia"));
       	       				new_id_mapping.put(name, new IDMappingMining(id, name, "Chemgapedia"));
       	       				lo.setId(id);
       	       			}
       	       			
       	       			//Set timestamp
	    				lo.setTime(logLine.getTimestamp());	
	    				//Set url
	    				lo.setUrl(logLine.getUrl());
	    				//Set HTTP-status
	    				lo.setStatus(logLine.getHttpStatus());	
	    				//Set referrer
	    				lo.setReferrer(logLine.getReferrer());	
	    				//Set duration to standard (will be calculated later on)
	    				lo.setDuration(-1);
	    				
	    				//Check if resource is already known. If yes, set course. Else create new resource later on.
	    				if(this.oldResources.get(lo.getUrl()) == null && this.newResources.get(lo.getUrl()) == null)
	    				{
	    					newRes = true;
	    					lo.setCourse(null);
	    				}
	    				else
	    				{	    					
	    					CourseResourceMining c = this.courseResources.get(lo.getUrl());
	    					CourseMining co;
	    					try{
	    						HibernateProxy prox = (HibernateProxy)c.getCourse();
	    						co = (CourseMining)prox.getHibernateLazyInitializer().getImplementation();
	    					}catch(ClassCastException e)
	    					{
	    						co = c.getCourse();
	    					}
	    					catch(NullPointerException e)
	    					{
	    						co = null;
	    					}
	    					if(c != null)
	    						lo.setCourse(co);				
	    				}

	    				//Check if users is known
	    				if(this.users.get(lo.getId()) != null)
	    				{
		    				  UserMining u = users.get(lo.getId());
		    				  //Check if the user is known and if he has 'logged out' since last request
		    				  if(!lo.getReferrer().equals("-") && !lo.getReferrer().contains("www.google."))
		    				  {
		    					  ArrayList<LogObject> tlo = userHistories.get(lo.getId());
		    					  if(tlo != null && tlo.get(tlo.size()-1).getReferrer().equals(lo.getUrl()))
		    					  {
		    						  userHistories.get(lo.getId()).get(userHistories.get(lo.getId()).size()-1).setDuration(lo.getTime() - u.getLastaccess());
		    					  }		    					  
		    					  u.setLastaccess(lo.getTime());	    					  
		    				  }
		    				  else
		    				  {
		    					  u.setLastlogin(u.getCurrentlogin());
		    					  u.setCurrentlogin(lo.getTime());
		    					  u.setLastaccess(lo.getTime());
		    				  }
		    				  users.put(u.getId(), u);
		    				  lo.setUser(u);
		    			  }
		    			  else
		    			  {
			    			  UserMining u = new UserMining();
			    			  u.setId(lo.getId());
			    			  u.setFirstaccess(lo.getTime());
			    			  u.setLastaccess(lo.getTime());
			    			  if(lo.getReferrer().equals("-") || lo.getReferrer().startsWith("www.google."))
			    				  u.setCurrentlogin(lo.getTime());
			    			  else
			    				  u.setCurrentlogin(0);  
			    			  users.put(lo.getId(), u);
			    			  lo.setUser(u);
		    			  }
		    			  
		    			  //Save viewed glossary entries to the resource-list because they aren't registered within XML-packages
		    			  if((newRes && lo.getUrl().endsWith(".html")))
		    			  {
		    				  	ResourceMining r = new ResourceMining();
		    				  
		       	    			long resource_id = -1;
		       	       			if(id_mapping.get(lo.getUrl()) != null)
		       	       			{
		       	       				resource_id = id_mapping.get(lo.getUrl()).getId();
		       	       				lo.setId(resource_id);
		       	       			}
		       	       			if(resource_id == -1 )
		       	       			{
		       	       				resource_id = largestId + 1;
		       	       				largestId = resource_id;
		       	       				id_mapping.put(lo.getUrl(), new IDMappingMining(resource_id, lo.getUrl(), "Chemgapedia"));
		       	       				new_id_mapping.put(lo.getUrl(), new IDMappingMining(resource_id, lo.getUrl(), "Chemgapedia"));
		       	       				lo.setId(resource_id);
		       	       			}
		       	       			if(lo.getId() > largestId)
		       	       				largestId = lo.getId();
		    				  
		    				  r.setId(resource_id);
		    				  r.setUrl(lo.getUrl());
		    				  //Regex used to prevent the inclusion of assignment-pages
		    				  if(newRes && !r.getUrl().matches("[0-9a-z]{32}[-]{1}[0-9]++"))
		    				  {
		    					  if(lo.getUrl().endsWith("/index.html"))
		    					  {
		    						  int inPos = lo.getUrl().substring(0, lo.getUrl().lastIndexOf("/")-1).lastIndexOf("/");
		    						  String urlCut = lo.getUrl().substring(0, inPos);
		    						  urlCut = urlCut.substring(urlCut.lastIndexOf("/")+1, urlCut.length());
		    						  r.setType("Index");
		    					  }
		    					  else
		    						  r.setType("Unknown");
		    					  r.setPosition(-1);
		    				  }
		    				  else
		    				  {
		    					  r.setType("GlossaryEntry");
		    					  r.setPosition(-5);
		    				  }
		    				  
		    				  //Construct resource title from URL
		    				  String h = lo.getUrl().substring(lo.getUrl().lastIndexOf("/")+1, lo.getUrl().length());
		    				  h = h.substring(0, h.indexOf("."));
		    				  String f = "";
		    				  if(h.length() > 0)
		    					  f = Character.toUpperCase(h.charAt(0))+"";
		    				  if(h.length() > 0)
		    					  h = f + h.substring(1);
		    				  else
		    					  System.out.println("URL doesn't match pattern: " + lo.getUrl());
		    				  r.setTitle(h);
		    				  
		    				  //cutting out supplements
		    				  if(r.getUrl().contains("/vsengine/supplement/"))
		    					  r.setType("Supplement");
		    				  
		    				  if(r.getUrl().contains("/mindmap/"))
		    					  r.setType("Mindmap");
		    				  
		    				  this.newResources.put(r.getUrl(), r);
		    			  }
		    			  
		    			  //logList.add(lo);
		    			  if(userHistories.get(lo.getId()) == null)
		    			  {
		    				  ArrayList<LogObject> a = new ArrayList<LogObject>();
		    				  a.add(lo);
		    				  userHistories.put(lo.getId(), a);
		    			  }
		    			  else
		    			  {
		    				  userHistories.get(lo.getId()).add(lo);
		    			  }
		    		  }
		    		  else
		    			  if(!logLine.isValid())
		    				  System.out.println("Line doesn't match pattern.");
		    			  else
		    				  System.out.println("Line's timestamp is to old.");
		    	  }	   
		      }
	      finally 
	      {
	    	  System.out.println("Read " + count + " lines.");
	        input.close();
	      }
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    
	    
	}
	 
	/**
	 * Calculates the duration of every view and saves the values to the global logList.
	 * Keep in mind, that these values can be misleading as the duration of a view is calculated by 
	 * the difference between a views time stamp and the the time stamp of the next view by the same user
	 * with an internal referrer. If there is no view matching these criteria the value is set to -1.
	 * 
	 */
	private void calculateDurations()
	{
		for(Iterator<ArrayList<LogObject>> iter = this.userHistories.values().iterator(); iter.hasNext();)
		{
			ArrayList<LogObject> loadedItem = iter.next();
			for(int i = 0; i < loadedItem.size()-1; i++)
			{
				Long nextRequest = loadedItem.get(i+1).getTime();
				Long dur = nextRequest - loadedItem.get(i).getTime();
				if(dur > 3600L)
					dur = 3600L;
				loadedItem.get(i).setDuration(dur);
			}
		}
	}
	
	
	/**
	 * Writes users to the database.
	 */
	public void usersToDB()
	{
		//try{	
			List<Collection<?>> l = new ArrayList<Collection<?>>();
			Collection<UserMining> it = (Collection<UserMining>)this.users.values();
			Collection<IDMappingMining> idmap = (Collection<IDMappingMining>)new_id_mapping.values();
			l.add(it);
			l.add(idmap);
			dbHandler.saveCollectionToDB(l);
			
			
			/*}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}*/
	}
	
	/**
	 * Writes resource logs to the database.
	 */
	public Long resourceLogsToDB()
	{
		calculateDurations();
		List<Collection<?>> l = new ArrayList<Collection<?>>();
		
		//HashMap<Long, ResourceLogMining> resourceLogMining = new HashMap<Long, ResourceLogMining>();
		ArrayList<ResourceLogMining> resourceLogMining = new ArrayList<ResourceLogMining>();
		try{	
			long li = (Long)(dbHandler.performQuery(EQueryType.HQL, "Select count(*) from ResourceLogMining").get(0));
			if (li > 0)
			startIndex = 1 + li;
			for(Iterator<ArrayList<LogObject>> iter = this.userHistories.values().iterator(); iter.hasNext();)
			{
				ArrayList<LogObject> loadedItem = iter.next();
				for(int i =0; i < loadedItem.size(); i++)
				{
					if(this.users.get(loadedItem.get(i).getUser().getId()) != null)
					{
						//startIndex++;
						ResourceLogMining rl = new ResourceLogMining();
						//rl.setId(startIndex);
						if(oldResources.get(loadedItem.get(i).getUrl()) == null)
							rl.setResource(newResources.get(loadedItem.get(i).getUrl()));
						else
							rl.setResource(oldResources.get(loadedItem.get(i).getUrl()));

						rl.setCourse(loadedItem.get(i).getCourse());
						rl.setUser(loadedItem.get(i).getUser());
						rl.setTimestamp(loadedItem.get(i).getTime());
						rl.setDuration(loadedItem.get(i).getDuration());
						rl.setAction("View");
						
						resourceLogMining.add(rl);
					}
				}
			}
//			ArrayList<ResourceLogMining> rlm = new ArrayList<ResourceLogMining>(resourceLogMining.values());
			Collections.sort(resourceLogMining);
			for(int i = 0; i < resourceLogMining.size(); i++)
			{
				startIndex++;
				resourceLogMining.get(i).setId(startIndex);
			}
			l.add(this.newResources.values());
			l.add(resourceLogMining);			
			dbHandler.saveCollectionToDB(l);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return largestId;
	}
}
