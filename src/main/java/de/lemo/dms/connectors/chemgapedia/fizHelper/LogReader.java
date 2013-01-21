package de.lemo.dms.connectors.chemgapedia.fizHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.UserMining;

/**
 * The Class LogReader. Reads Chemgapedia's server-logs and saves the found objects into the database.
 */
public class LogReader {

	/**
	 * User-objects of previous connector-runs
	 */
	private HashMap<String, UserMining> oldUsers = new HashMap<String, UserMining>();
	/**
	 * User-objects of current connector-run
	 */
	private HashMap<String, UserMining> newUsers = new HashMap<String, UserMining>();
	/**
	 * Resource-objects of previous connector-runs
	 */
	private HashMap<String, ResourceMining> oldResources = new HashMap<String, ResourceMining>();
	/**
	 * Resource-objects of current connector-run
	 */
	private HashMap<String, ResourceMining> newResources = new HashMap<String, ResourceMining>();
	/**
	 * CourseResource-objects found in database
	 */
	private HashMap<String, CourseResourceMining> courseResources = new HashMap<String, CourseResourceMining>();
	/**
	 * IDMapping-objects of previous connector-runs
	 */
	private HashMap<String, IDMappingMining> id_mapping = new HashMap<String, IDMappingMining>();
	/**
	 * IDMapping-objects of current connector-run
	 */
	private HashMap<String, IDMappingMining> new_id_mapping = new HashMap<String, IDMappingMining>();
	/**
	 * HashMap storing all logged accesses, with according user-login as key
	 */
	private HashMap<String, ArrayList<LogObject>> userHistories = new HashMap<String, ArrayList<LogObject>>();
	/**
	 * Course-objects of previous connector-runs
	 */
	private HashMap<String, CourseMining> oldCourses = new HashMap<String, CourseMining>();
	/**
	 * Internal clock-object for statistics
	 */
	private Clock clock = new Clock();
	/**
	 * DBHandler-object, for connection to Mining-Database
	 */
	private IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
	/**
	 * Database's largest id used in ResourceLogMining
	 */
	private Long resLogId;
	/**
	 * Database's largest id used in UserMining
	 */
	private Long userIdCount;
	/**
	 * Database's largest id used in ResourceMining
	 */
	private Long resIdCount;
	/**
	 * Database's largest timestamp used in ResourceLogMining
	 */
	private Long resLogTime;
	private IConnector connector;
	
	/**
	 * Creates a new LogReader-object, imports necessary objects from Mining-Database and sets counters.
	 * 
	 * @param connector
	 * @param session
	 */
	@SuppressWarnings("unchecked")
    public LogReader(IConnector connector, Session session)
	{
	    this.connector = connector;
	    long platformId = connector.getPlatformId();
		try{
			new_id_mapping = new HashMap<String, IDMappingMining>();
	    	
	    	Criteria c = session.createCriteria(IDMappingMining.class, "idmap");
	    	c.add(Restrictions.eq("idmap.platform", platformId));
			
			List<IDMappingMining> ids = c.list();
			
			//Load previously saved idMappingMining, used to identify resources
			id_mapping = new HashMap<String, IDMappingMining>();
			for(int i = 0; i < ids.size(); i++)
			{
				ids.get(i).setPlatform(connector.getPlatformId());
				id_mapping.put(ids.get(i).getHash(), ids.get(i));
			}
			System.out.println("Read "+ids.size() + " IDMappings from database.");
			
			//Load previously saved UserMining-objects
			c = session.createCriteria(UserMining.class, "users");
			c.add(Restrictions.eq("users.platform", platformId));
	    	List<UserMining> us  = c.list();
	    	for(int i = 0; i < us.size(); i++)
	    		this.oldUsers.put(us.get(i).getLogin(), us.get(i));
	    	System.out.println("Read "+us.size() + " UserMinings from database.");
	    	
	    	//Load previously saved ResourceMining-objects
	    	c = session.createCriteria(ResourceMining.class, "resources");
	    	c.add(Restrictions.eq("resources.platform", platformId));
	    	List<ResourceMining> rt  = c.list();
	    	for(ResourceMining res : rt)
	    		this.oldResources.put(res.getUrl(), res);
	    	System.out.println("Read "+rt.size() + " ResourceMinings from database.");
	    	
	    	//Load previously saved CourseMining-objects
	    	c = session.createCriteria(CourseMining.class, "courses");
	    	c.add(Restrictions.eq("courses.platform", platformId));
	    	List<CourseMining> cm  = c.list();
	    	for(int i = 0; i < cm.size(); i++)
	    	{
	    		this.oldCourses.put(cm.get(i).getTitle(), cm.get(i));
	    	}
	    	System.out.println("Read "+cm.size() + " CourseMinings from database.");
	    
	    	//Load previously saved CourseResourceMining-objects
	    	c = session.createCriteria(CourseResourceMining.class, "coursesResources");
	    	c.add(Restrictions.eq("coursesResources.platform", platformId));
	    	List<CourseResourceMining> courseResource = c.list();
	    	for(int i = 0; i < courseResource.size(); i++)
	    		this.courseResources.put(((CourseResourceMining)courseResource.get(i)).getResource().getUrl(), ((CourseResourceMining)courseResource.get(i)));
	    	System.out.println("Read "+courseResource.size() + " CourseResourceMinings from database.");
	    	
	    	Query resCount = session.createQuery("select max(res.id) from ResourceMining res where res.platform="+platformId +"");
	    	if(resCount.list().size() > 0)
	    		resIdCount = ((ArrayList<Long>) resCount.list()).get(0);
            if(resIdCount != null && resIdCount != 0)
            	resIdCount = Long.valueOf(resIdCount.toString().substring(connector.getPrefix().toString().length()));
            else
            	resIdCount = 0L;
	    		
	    	Query userCount = session.createQuery("select max(user.id) from UserMining user where user.platform="+ platformId +"");
            if(userCount.list().size() > 0)
            	userIdCount = ((ArrayList<Long>) userCount.list()).get(0);
            if(userIdCount != null && userIdCount != 0)
            	userIdCount = Long.valueOf(userIdCount.toString().substring(connector.getPrefix().toString().length()));
            else
            	userIdCount = 0L;
	    		
	    	Query logCount = session.createQuery("select max(log.id) from ResourceLogMining log where log.platform="+ platformId +"");
            if(logCount.list().size() > 0)
            	resLogId = ((ArrayList<Long>) logCount.list()).get(0);
            if(resLogId == null)
            	resLogId = 0L;
            
	    	Query logTime = session.createQuery("select max(log.timestamp) from ResourceLogMining log where log.platform="+ platformId +"");
            if(logCount.list().size() > 0)
            	resLogTime = ((ArrayList<Long>) logCount.list()).get(0);
            if(resLogTime == null)
            	resLogTime = 0L;
            
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Updates the data.
	 * 
	 * @param inFile
	 * @return
	 */
	private void update(boolean filterLog, Session session )
	{
		if(filterLog)
			filterServerLogFile();
		save(session);
		
		for(ResourceMining resource : newResources.values())
			oldResources.put(resource.getUrl(), resource);
		
		
		for(UserMining user : newUsers.values())
			oldUsers.put(user.getLogin(), user);
		
		
		for(IDMappingMining mapping : new_id_mapping.values())
			id_mapping.put(mapping.getHash(), mapping);
		
		clearMaps();
		
	}
		
	public void clearMaps()
	{
		newResources.clear();
		newUsers.clear();
		userHistories.clear();
		new_id_mapping.clear();
	}
	
	
	/**
	 * Filters irrelevant lines (bots, spiders) out of the log file and writes a new one.
	 *
	 * @param outFile filename of the new filtered log file.
	 */
	private void filterServerLogFile()
	{
		try{
		clock.reset();
		ArrayList<LogObject>  a;
		Object[] user = this.newUsers.values().toArray();
			HashMap<String, UserMining> tempUsers = new HashMap<String, UserMining>();
		double old = Long.parseLong(newUsers.size() +"");
		int totalLines = 0;
		int linesDeleted = 0;
		BotFinder bf = new BotFinder();
		for(int i= 0; i < user.length; i++)
		{
			int susp1 = 0;
			int susp2 = 0;
			int susp3 = 0;
			
				a = userHistories.get(((UserMining)user[i]).getLogin());
				Collections.sort(a);
			
			if(a != null && a.size() > 0)
			{
				totalLines += a.size();
				susp1 = bf.checkFastOnes(a, 1).size();
				susp2 = bf.checkPeriods(a, 5);
				susp3 = bf.checkForRepetitions(a, 10);
				if(susp1 < 1 && susp2 == 0 && susp3 == 0)
						tempUsers.put(((UserMining)user[i]).getLogin(), (UserMining)user[i]);
				else
					{
						
					linesDeleted += a.size();
						userHistories.remove(((UserMining)user[i]).getLogin());
			}
		}
			}
		
		double cutUsePerc = (old - Long.valueOf(""+tempUsers.size())) / (old / 100);
			double tmp = totalLines / 100.0d;
			double cutLinPerc = linesDeleted / tmp;
		System.out.println("Filtered " + (old - tempUsers.size()) + " suspicious users  out of " + old + " (" + new DecimalFormat("0.00").format(cutUsePerc) + "%), eliminating " + linesDeleted + " log lines (" + new DecimalFormat("0.00").format(cutLinPerc) + "%).");
		this.newUsers = tempUsers;
		}catch(Exception e)
		{
			System.out.println("Error while filtering log-file:");
			e.printStackTrace();
	}
	}
	
	/**
	 * Loads the data from the server-log-file.
	 *
	 * @param inFile the path of the server log file
	 * @param linesPerRun Number of log-lines that are read before data is stored to database
	 * @param filterLog Determines whether suspicious logs are ignored or not
	 */
	public void loadServerLogData(String inFile, Long linesPerRun, boolean filterLog, Session session)
	{	    
	    try
	    {	    
	    	System.out.println("Reading server log " + inFile);
	    	BufferedReader input =  new BufferedReader(new FileReader(inFile));
	    	int count = 0;
	    	try 
	    	{
	    		String line = null;
	    		clock.reset();
	    		Long i = 0L;
	    		while ((line = input.readLine()) != null)
	    		{
	    			i++;
	    			if(linesPerRun != 0 && i > 0 && i % linesPerRun == 0)
	    			{
	    				update(filterLog, session);
	    			}
	    			count++;
	    			boolean newRes = false;
	    			LogLine logLine = new LogLine(line);
	    			
	    			//The line is only processed, if it is readable and not older the the line before
	    			if(logLine.isValid())
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
       	       				id = userIdCount + 1;
       	       				userIdCount = id;
       	       				lo.setId(Long.valueOf(this.connector.getPrefix() + "" + id));
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
	    					//CourseMining co;
	    					if(this.courseResources.get(lo.getUrl()) != null)
	    						lo.setCourse(c.getCourse());
	    					/*
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
	    						*/		
	    				}

	    				//Check if users is known
	    				if(this.oldUsers.get(logLine.getId()) != null || this.newUsers.get(logLine.getId()) != null)
	    				{
	    					UserMining u;
	    					  if(this.oldUsers.get(logLine.getId()) != null)
	    						  u = oldUsers.get(logLine.getId());
	    					  else
	    						  u = newUsers.get(logLine.getId());
	    					  
		    				  //Check if the user is known and if he has 'logged out' since last request
		    				  if(!lo.getReferrer().equals("-") && !lo.getReferrer().contains("www.google."))
		    				  {
		    					  ArrayList<LogObject> tlo = userHistories.get(logLine.getId());
		    					  if(tlo != null && tlo.get(tlo.size()-1).getReferrer().equals(lo.getUrl()))
		    					  {
		    						  userHistories.get(logLine.getId()).get(userHistories.get(logLine.getId()).size()-1).setDuration(lo.getTime() - u.getLastaccess());
		    					  }		    					  
		    					  if(lo.getTime() > u.getLastaccess())
		    					  u.setLastaccess(lo.getTime());	    					  
		    				  }
		    				  else
		    				  {
		    					  u.setLastlogin(u.getCurrentlogin());
		    					  
		    					  if(u.getCurrentlogin() < lo.getTime())
		    					  u.setCurrentlogin(lo.getTime());
		    					  if(u.getLastaccess() < lo.getTime())
		    					  u.setLastaccess(lo.getTime());
		    				  }
		    				  u.setPlatform(connector.getPlatformId());
		    				  newUsers.put(u.getLogin(), u);
		    				  lo.setUser(u);
		    			  }
		    			  else
		    			  {
		    				  //If the user is unknown, create new user-object
			    			  UserMining u = new UserMining();
			    			  u.setId(lo.getId());
			    			  u.setFirstaccess(lo.getTime());
			    			  u.setLastaccess(lo.getTime());
			    			  //google-referrers aren't replaced with "-" although they are external
			    			  if(lo.getReferrer().equals("-") || lo.getReferrer().startsWith("www.google."))
			    				  u.setCurrentlogin(lo.getTime());
			    			  else
			    				  u.setCurrentlogin(0);  
			    			  u.setPlatform(connector.getPlatformId());
			    			  u.setLogin(logLine.getId());
			    			  newUsers.put(logLine.getId(), u);
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
		       	       				resource_id = resIdCount + 1;
		       	       				resIdCount = resource_id;
		       	       				id_mapping.put(lo.getUrl(), new IDMappingMining(Long.valueOf(this.connector.getPrefix() + "" + resource_id), lo.getUrl(), this.connector.getPlatformId()));
		       	       				new_id_mapping.put(lo.getUrl(), new IDMappingMining(Long.valueOf(this.connector.getPrefix() + "" + resource_id), lo.getUrl(), this.connector.getPlatformId()));
		       	       				resource_id = Long.valueOf(connector.getPrefix() + "" + resource_id);
		       	       				lo.setId(resource_id);
		       	       			}
		    				  
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
		    				  
		    				  r.setPlatform(connector.getPlatformId());
		    				  this.newResources.put(r.getUrl(), r);
		    			  }
		    			  
		    			  //logList.add(lo);
		    			  if(userHistories.get(logLine.getId()) == null)
		    			  {
		    				  ArrayList<LogObject> a = new ArrayList<LogObject>();
		    				  a.add(lo);
		    				  userHistories.put(logLine.getId(), a);
		    			  }
		    			  else
		    			  {
		    				 
		    				  userHistories.get(logLine.getId()).add(lo);
		    			  }
		    		  }
		    		  else
		    			  if(!logLine.isValid())
		    				  System.out.println("Line doesn't match pattern.");
		    			  else
		    				  System.out.println("Line's timestamp is to old.");
		    	  }	   
	    		if(filterLog)
	    			filterServerLogFile();
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
	 * Writes the data to the database.
	 */
	public void save(Session session)
	{
		List<Collection<?>> l = new ArrayList<Collection<?>>();
		ArrayList<ResourceLogMining> resourceLogMining = new ArrayList<ResourceLogMining>();
		Collection<UserMining> it = (Collection<UserMining>)this.newUsers.values();
		Collection<IDMappingMining> idmap = (Collection<IDMappingMining>)new_id_mapping.values();
		System.out.println("Found " + it.size() + " users.");
		l.add(it);
		l.add(idmap);

		for(ArrayList<LogObject> loadedItem : this.userHistories.values())
		{
			for(int i =0; i < loadedItem.size(); i++)
			{
					ResourceLogMining rl = new ResourceLogMining();
				
				//Set Url for resource-object
				if(newResources.get(loadedItem.get(i).getUrl()) != null)
						rl.setResource(newResources.get(loadedItem.get(i).getUrl()));
					else
						rl.setResource(oldResources.get(loadedItem.get(i).getUrl()));

					rl.setCourse(loadedItem.get(i).getCourse());
					rl.setUser(loadedItem.get(i).getUser());
					rl.setTimestamp(loadedItem.get(i).getTime());
					rl.setDuration(loadedItem.get(i).getDuration());
					rl.setAction("View");
					rl.setPlatform(connector.getPlatformId());
				rl.setId(resLogId + 1);
				resLogId++;
					
					resourceLogMining.add(rl);
				}
			}
		Collections.sort(resourceLogMining);
		l.add(this.newResources.values());
		l.add(resourceLogMining);			
		System.out.println("");
		if(session.isOpen())
		dbHandler.saveCollectionToDB(session, l);
		else
		{
			session = dbHandler.getMiningSession();
			dbHandler.saveCollectionToDB(session, l);
	}
		System.out.println("");
	}
	
	
}
