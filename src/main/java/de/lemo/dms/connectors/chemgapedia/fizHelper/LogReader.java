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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;

import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.ConfigMining;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.PlatformMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.UserMining;

/**
 * The Class LogReader. Reads Chemgapedia's server-logs and saves the found objects into the database.
 */
public class LogReader {
	
	//private ArrayList<LogObject> logList = new ArrayList<LogObject>();
	
	long startIndex = 0;

	private HashMap<Long, UserMining> oldUsers = new HashMap<Long, UserMining>();
	
	private HashMap<Long, UserMining> newUsers = new HashMap<Long, UserMining>();
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
	
	IDBHandler dbHandler = ServerConfiguration.getInstance().getDBHandler();
	Session session;
	
	Long largestId;
	
	Long lastTimestamp = 0L;
	
	PlatformMining pf;
	
	/**
	 * Constructor. Creates a new LogReader-object. 
	 * 
	 * @param platform	Platform-object of the Chemgapedia instance
	 * @param idcount	
	 */
	@SuppressWarnings("unchecked")
    public LogReader(long platformId, Long idcount)
	{
	 
		try{
			
			new_id_mapping = new HashMap<String, IDMappingMining>();
					
			session = dbHandler.getMiningSession();
	    	
	    	Criteria c = session.createCriteria(IDMappingMining.class, "idmap");
	    	
	    	c.add(Restrictions.eq("idmap.platform", platformId));
			
			List<IDMappingMining> ids = c.list();
			
			id_mapping = new HashMap<String, IDMappingMining>();
			for(int i = 0; i < ids.size(); i++)
			{
				ids.get(i).setPlatform(pf.getId());
				id_mapping.put(ids.get(i).getHash(), ids.get(i));
			}
			System.out.println("Read "+ids.size() + " IDMappings from database.");
			
			
			c = session.createCriteria(UserMining.class, "users");
			c.add(Restrictions.eq("users.platform", platformId));
	    	List<UserMining> us  = c.list();
	    	for(int i = 0; i < us.size(); i++)
	    		this.oldUsers.put(us.get(i).getId(), us.get(i));
	    	System.out.println("Read "+us.size() + " UserMinings from database.");
	    	
	    	c = session.createCriteria(ResourceMining.class, "resources");
	    	c.add(Restrictions.eq("resources.platform", platformId));
	    	List<ResourceMining> rt  = c.list();
	    	for(ResourceMining res : rt)
	    		this.oldResources.put(res.getUrl(), res);
	    	System.out.println("Read "+rt.size() + " ResourceMinings from database.");
	    	
	    	c = session.createCriteria(CourseMining.class, "courses");
	    	c.add(Restrictions.eq("courses.platform", platformId));
	    	List<CourseMining> cm  = c.list();
	    	for(int i = 0; i < cm.size(); i++)
	    	{
	    		this.oldCourses.put(cm.get(i).getTitle(), cm.get(i));
	    	}
	    	System.out.println("Read "+cm.size() + " CourseMinings from database.");
	    
	    	c = session.createCriteria(CourseResourceMining.class, "coursesResources");
	    	c.add(Restrictions.eq("coursesResources.platform", platformId));
	    	List<CourseResourceMining> courseResource = c.list();
	    	for(int i = 0; i < courseResource.size(); i++)
	    		this.courseResources.put(((CourseResourceMining)courseResource.get(i)).getResource().getUrl(), ((CourseResourceMining)courseResource.get(i)));
	    	System.out.println("Read "+courseResource.size() + " CourseResourceMinings from database.");
	    	
	    	if(idcount == -1)
	    	{
	    		c = session.createCriteria(ResourceLogMining.class, "rLogs");
		    	c.add(Restrictions.eq("rLogs.platform", platformId));
	    		List<ResourceLogMining> l1 = c.list();
				if(l1 != null && l1.size() > 0 && l1.get(l1.size()-1) != null)
					lastTimestamp = l1.get(l1.size()-1).getTimestamp();
	    		
	    		
				c = session.createCriteria(ConfigMining.class, "conf");
				c.add(Restrictions.eq("conf.platform", platformId));
				List<ConfigMining>l = c.list();
				if(l != null && l.size() > 0)
				{
					largestId = Long.valueOf((l.get(l.size()-1).getLargestId() + "").substring(2));
				}
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
	 * Updates the data.
	 * 
	 * @param inFile
	 * @return
	 */
	public Long update(String inFile)
	{
		for(ResourceMining resource : newResources.values())
			oldResources.put(resource.getUrl(), resource);
		newResources.clear();
		
		
		for(UserMining user : newUsers.values())
			oldUsers.put(user.getId(), user);
		newUsers.clear();
		
		for(IDMappingMining mapping : new_id_mapping.values())
			id_mapping.put(mapping.getHash(), mapping);
		new_id_mapping.clear();
		
		userHistories.clear();
		resourceLog.clear();
		
		loadServerLogData(inFile);
		filterServerLogFile();
		return save();
		
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
		Object[] user = this.newUsers.values().toArray();
		HashMap<Long, UserMining> tempUsers = new HashMap<Long, UserMining>();
		double old = Long.parseLong(newUsers.size() +"");
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
		
		double cutUsePerc = (old - Long.valueOf(""+tempUsers.size())) / (old / 100);
		double cutLinPerc = linesDeleted / (totalLines / 100);
		System.out.println("Filtered " + (old - tempUsers.size()) + " suspicious users  out of " + old + " (" + new DecimalFormat("0.00").format(cutUsePerc) + "%), eliminating " + linesDeleted + " log lines (" + new DecimalFormat("0.00").format(cutLinPerc) + "%).");
		this.newUsers = tempUsers;
	}
	
	/**
	 * Loads the data from the server-log-file.
	 *
	 * @param inFile the path of the server log file
	 */
	public void loadServerLogData(String inFile)
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
       	       				id_mapping.put(name, new IDMappingMining(Long.valueOf(pf.getPrefix() + "" + id), name, pf.getId()));
       	       				new_id_mapping.put(name, new IDMappingMining(Long.valueOf(pf.getPrefix() + "" + id), name, pf.getId()));
       	       				lo.setId(Long.valueOf(pf.getPrefix() + "" + id));
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
	    				if(this.oldUsers.get(lo.getId()) != null)
	    				{
		    				  UserMining u = oldUsers.get(lo.getId());
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
		    				  u.setPlatform(pf.getId());
		    				  newUsers.put(u.getId(), u);
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
			    			  u.setPlatform(pf.getId());
			    			  newUsers.put(lo.getId(), u);
			    			  oldUsers.put(u.getId(), u);
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
		       	       				id_mapping.put(lo.getUrl(), new IDMappingMining(Long.valueOf(pf.getPrefix() + "" + resource_id), lo.getUrl(), pf.getId()));
		       	       				new_id_mapping.put(lo.getUrl(), new IDMappingMining(Long.valueOf(pf.getPrefix() + "" + resource_id), lo.getUrl(), pf.getId()));
		       	       				resource_id = Long.valueOf(pf.getPrefix() + "" + resource_id);
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
		    				  
		    				  r.setPlatform(pf.getId());
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
	 * Writes the data to the database.
	 */
	public Long save()
	{
		List<Collection<?>> l = new ArrayList<Collection<?>>();
		ArrayList<ResourceLogMining> resourceLogMining = new ArrayList<ResourceLogMining>();
		Collection<UserMining> it = (Collection<UserMining>)this.newUsers.values();
		Collection<IDMappingMining> idmap = (Collection<IDMappingMining>)new_id_mapping.values();
		System.out.println("Found " + it.size() + " users.");
		l.add(it);
		l.add(idmap);
		
		Session session = dbHandler.getMiningSession();
		
		long li = (Long)(dbHandler.performQuery(session, EQueryType.HQL, "Select count(*) from ResourceLogMining").get(0));
		if (li > 0)
		startIndex = 1 + li;
		for(Iterator<ArrayList<LogObject>> iter = this.userHistories.values().iterator(); iter.hasNext();)
		{
			ArrayList<LogObject> loadedItem = iter.next();
			for(int i =0; i < loadedItem.size(); i++)
			{
				if(this.newUsers.get(loadedItem.get(i).getUser().getId()) != null)
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
					rl.setPlatform(pf.getId());
					
					resourceLogMining.add(rl);
				}
			}
		}
		Collections.sort(resourceLogMining);
		for(int i = 0; i < resourceLogMining.size(); i++)
		{
			startIndex++;
			resourceLogMining.get(i).setId(startIndex);
		}
		session.close();
		session = dbHandler.getMiningSession();
		l.add(this.newResources.values());
		l.add(resourceLogMining);			
		
		dbHandler.saveCollectionToDB(session, l);
		
		return largestId;
	}
	
	
}
