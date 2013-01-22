package de.lemo.dms.connectors.chemgapedia.fizHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.LevelAssociationMining;
import de.lemo.dms.db.miningDBclass.LevelCourseMining;
import de.lemo.dms.db.miningDBclass.LevelMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;

/**
 * The Class XMLPackageParser. Created for the processing of Chemgapedia's VLU-files
 */
public class XMLPackageParser {
	
	/** The level objects. */
	private HashMap<String, LevelMining> levelObj = new HashMap<String, LevelMining>();
	
	/** The level association objects . */
	private HashMap<Long, LevelAssociationMining> levelAssociations = new HashMap<Long, LevelAssociationMining>();
	
	/** The level course objects. */
	private HashMap<Long, LevelCourseMining> levelCourses = new HashMap<Long, LevelCourseMining>();
	
	/** The largest level id of previous runs. */
	private Long levId = 0L;
	
	/** The largest level association id of previous runs. */
	private Long levAscId = 0L;
	
	/** The largest level course id of previous runs. */
	private Long levCouId = 0L;

	/** The list of course objects. */
	private HashMap<String, CourseMining> courseObj = new HashMap<String, CourseMining>();
	
	/** The course resources objects. */
	private HashMap<Long, CourseResourceMining> courseResources = new HashMap<Long, CourseResourceMining>();

	/** The list of resource objects. */
	private HashMap<String, ResourceMining> resourceObj = new HashMap<String, ResourceMining>();
	
	/** The list of file names */
	private ArrayList<String> fileNames = new ArrayList<String>();
	
	/** The map containing the IDMapping-objects. */
	private HashMap<String, IDMappingMining> id_mapping;
	
	/** The largest course id of previous runs. */
	private Long couId = 0L;
	/** The resource level id of previous runs. */
	private Long resId = 0L;
	/** The largest course resource id of previous runs. */
	private Long couResId = 0L;
	private IConnector connector;
	
	private IDBHandler dbHandler;
	
    private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Constructor. Creates an object of XMLPackageParser.
	 * 
	 * @param platform Name of the Chemgapedia-platform
	 */
	@SuppressWarnings("unchecked")
    public XMLPackageParser(IConnector connector)
	{
	    this.connector = connector;
	    long platformId = connector.getPlatformId();
		
		dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();	
		Session session = dbHandler.getMiningSession();
		
		List<IDMappingMining> ids = (List<IDMappingMining>) dbHandler.performQuery(session, EQueryType.HQL, "from IDMappingMining x where x.platform=" +platformId + " order by x.id asc");
		
		id_mapping = new HashMap<String, IDMappingMining>();
		for(int i = 0; i < ids.size(); i++)
			id_mapping.put(ids.get(i).getHash(), ids.get(i));
		System.out.println("Loaded " + id_mapping.size() + " IDMappingMining objects from the mining database.");
		
		List<LevelMining> levs = (List<LevelMining>) dbHandler.performQuery(session, EQueryType.HQL, "FROM LevelMining x where x.platform=" + platformId + " order by x.id asc");
    	if(levs.size() > 0)
    		levId = levs.get(levs.size()-1).getId();
		for(int i = 0; i < levs.size(); i++)
    		this.levelObj.put(levs.get(i).getTitle(), levs.get(i));
		System.out.println("Loaded " + levelObj.size() + " LevelMining objects from the mining database.");
    	
    	
		List<CourseMining> cous = (List<CourseMining>) dbHandler.performQuery(session, EQueryType.HQL, "FROM CourseMining x where x.platform=" + platformId + " order by x.id asc");
    	if(cous.size() > 0)
    		couId = cous.get(cous.size()-1).getId();
		for(int i = 0; i < cous.size(); i++)
    		this.courseObj.put(cous.get(i).getTitle(), cous.get(i));
		System.out.println("Loaded " + courseObj.size() + " CourseMining objects from the mining database.");
		
		List<ResourceMining> ress = (List<ResourceMining>) dbHandler.performQuery(session, EQueryType.HQL, "FROM ResourceMining x where x.platform=" + platformId + " order by x.id asc");
    	if(ress.size() > 0)
    		resId = ress.get(ress.size()-1).getId();
		for(int i = 0; i < ress.size(); i++)
    		this.resourceObj.put(ress.get(i).getUrl(), ress.get(i));
		System.out.println("Loaded " + resourceObj.size() + " ResourceMining objects from the mining database.");
		
		List<LevelAssociationMining> levAsc = (List<LevelAssociationMining>) dbHandler.performQuery(session, EQueryType.HQL, "FROM LevelAssociationMining x where x.platform=" + platformId + " order by x.id asc");
    	if(levAsc.size() > 0)
    		levAscId = levAsc.get(levAsc.size()-1).getId();
		for(int i = 0; i < levAsc.size(); i++)
    		this.levelAssociations.put(levAsc.get(i).getLower().getId(), levAsc.get(i));
		System.out.println("Loaded " + levelAssociations.size() + " LevelAssociationMining objects from the mining database.");

		List<LevelCourseMining> levCou = (List<LevelCourseMining>) dbHandler.performQuery(session, EQueryType.HQL, "FROM LevelCourseMining x where x.platform=" + platformId + " order by x.id asc");
    	if(levCou.size() > 0)
    		levCouId = levCou.get(levCou.size()-1).getId();
		for(int i = 0; i < levCou.size(); i++)
    		this.levelCourses.put(levCou.get(i).getCourse().getId(), levCou.get(i));
		System.out.println("Loaded " + levelCourses.size() + " LevelCourseMining objects from the mining database.");
		
		List<CourseResourceMining> couRes = (List<CourseResourceMining>) dbHandler.performQuery(session, EQueryType.HQL, "FROM CourseResourceMining x where x.platform=" +platformId + " order by x.id asc");
    	if(couRes.size() > 0)
    		couResId = couRes.get(couRes.size()-1).getId();
		for(int i = 0; i < couRes.size(); i++)
    		this.courseResources.put(couRes.get(i).getResource().getId(), couRes.get(i));
		System.out.println("Loaded " + courseResources.size() + " CourseResourceMining objects from the mining database.");
		
        dbHandler.closeSession(session);
	}
	
	
	/**
	 * Opens a vlu - file, creates objects of the type "Department","Degree","Course","Resource",
	 * "DepartmentDegree", "DegreeCourse" and "CourseResource" containing the given information and saves the objects to the global lists.
	 *
	 * @param filename Absolute path of the file containing the data.
	 */
	private void readVLU(String filename)
	{
		ResourceMining resource = new ResourceMining();
		try {
		      // ---- Parse XML file ----
		      DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
		      DocumentBuilder        builder  = factory.newDocumentBuilder();
		      Document               document = builder.parse( new File( filename ) );
		      
		      // ---- Get list of nodes to given element tag name ---- 
		      NamedNodeMap t = null;
		      
		    

		      NodeList content = document.getElementsByTagName("content");		      
		      //Set resource type
		      resource.setType("VLU");
		      
		      //set resource title
		      if(document.getElementsByTagName("title").getLength() > 0)
		      {
		    	  resource.setTitle(document.getElementsByTagName("title").item(0).getTextContent());
		      }
		      
		    
		      
		      //Set resource difficulty
		      if(document.getElementsByTagName("audience").getLength() > 0)
		      {
		    	  resource.setDifficulty(((Element)document.getElementsByTagName("audience").item(0)).getAttribute("level"));
		      }

		      try{
		    	  if(document.getElementsByTagName("time").getLength() > 0)
		    	  {
		    		  resource.setProcessingTime( Long.valueOf(((Element)document.getElementsByTagName("time").item(0)).getAttribute("value")));
		    	  }
		      }catch(NumberFormatException e)
		      {
		    	  resource.setProcessingTime(0L);
		      }
		      
		      String level1 ="";
		      String level2 ="";
		      String level3 ="";
		      
		      if(document.getElementsByTagName("subject").getLength() > 0)
		    	  level1 = ((Element)document.getElementsByTagName("subject").item(0)).getAttribute("name");
		      
		      if(document.getElementsByTagName("subject").getLength() > 0)
		    	  level2 = ((Element)document.getElementsByTagName("subject").item(0)).getAttribute("area");
		      
		      if(document.getElementsByTagName("subject").getLength() > 0)
		    	  level3 = ((Element)document.getElementsByTagName("subject").item(0)).getAttribute("specialism");
		      
		      LevelMining lev1 = new LevelMining();
		      LevelMining lev2 = new LevelMining();
		      LevelMining lev3 = new LevelMining();
		      CourseMining course = new CourseMining();
		      
		      Long platformId = connector.getPlatformId();
		      Long platformPrefix = connector.getPrefix();
		      
		      if(levelObj.get(level1) == null)
		      {		    	 
		    	  lev1.setTitle(level1);
		    	  lev1.setId(Long.valueOf(platformPrefix + "" + (levId + 1)));
		    	  lev1.setPlatform(platformId);
		    	  lev1.setDepth(1);
		    	  levId++;
		    	  levelObj.put(lev1.getTitle(), lev1);
		      }
		      else
		    	  lev1 = levelObj.get(level1);
		      if(levelObj.get(level2) == null)
		      {
		    	  
		    	  lev2.setTitle(level2);
		    	  lev2.setId(Long.valueOf(platformPrefix + "" + (levId + 1)));
		    	  lev2.setPlatform(platformId);
		    	  lev2.setDepth(2);
		    	  levId++;
		    	  levelObj.put(lev2.getTitle(), lev2);
		      }
		      else
		    	  lev2 = levelObj.get(level2);
		      if(levelObj.get(level3) == null)
		      {
		    	 
		    	  lev3.setTitle(level3);
		    	  lev3.setId(Long.valueOf(platformPrefix + "" + (levId + 1)));
		    	  lev3.setPlatform(platformId);
		    	  lev3.setDepth(3);
		    	  levId++;
		    	  levelObj.put(lev3.getTitle(), lev3);
		      }
		      else
		    	  lev3 = levelObj.get(level3);
		      
		      if(courseObj.get(resource.getTitle()) == null)
		      {
		    	  course.setTitle(resource.getTitle());
		    	  course.setId(Long.valueOf(platformPrefix+ "" + (couId + 1)));
		    	  course.setPlatform(platformId);
		    	  couId++;
		    	  courseObj.put(course.getTitle(),course);
		      }
		      else
		    	  course = courseObj.get(resource.getTitle());

		      //Set URL
		      NodeList root = document.getElementsByTagName("vlunode");
				for(int i = 0 ; i < root.getLength(); i++)
				{
					t = root.item(i).getAttributes();
					for(int j = 0; j < t.getLength(); j++)
					{
						if(t.item(j).getNodeName() ==  "xlink:href")
						{
							resource.setUrl("http://www.chemgapedia.de/vsengine/vlu" + t.item(j).getNodeValue()+".html");
							break;
						}
					}
				}
		      
		      if(!resource.getUrl().contains("/0/"))
		      {		    	  
			      resource.setPosition(0);
			      this.resourceObj.get(resource.getUrl());
					  	long r_id = -1;
	 	       			if(id_mapping.get(resource.getUrl()) != null)
	 	       			{
	 	       				r_id = id_mapping.get(resource.getUrl()).getId();
	 	       				resource.setId(r_id);
	 	       			}
	 	       			if(r_id == -1 )
	 	       			{
	 	       				r_id = resId + 1;
	 	       				resId = r_id;
						r_id = Long.valueOf(this.connector.getPrefix() + "" + r_id);
						id_mapping.put(resource.getUrl(), new IDMappingMining(r_id, resource.getUrl(), platformId));
	 	       				resource.setId(r_id);
	 	       			}
			    	  
	 	       		  resource.setPlatform(connector.getPlatformId());
			    	  this.resourceObj.put(resource.getUrl(), resource);
			    	  this.fileNames.add(filename);
			    	  //Save department - degree relation locally
			    	  if(this.levelAssociations.get(lev2.getId()) == null)
				      {			    	  
				    	  LevelAssociationMining ddm = new LevelAssociationMining();
				    	  ddm.setLower(lev2);
				    	  ddm.setUpper(lev1);
				    	  ddm.setId(Long.valueOf(this.connector.getPrefix() + "" + (levAscId + 1)));
				    	  ddm.setPlatform(this.connector.getPlatformId());
				    	  levAscId++;
				    	  this.levelAssociations.put(lev2.getId() , ddm);			    	  
				      }
			    	  if(this.levelAssociations.get(lev3.getId()) == null)
				      {			    	  
				    	  LevelAssociationMining ddm = new LevelAssociationMining();
				    	  ddm.setLower(lev3);
				    	  ddm.setUpper(lev2);
				    	  ddm.setId(Long.valueOf(platformPrefix + "" + (levAscId + 1)));
				    	  ddm.setPlatform(platformId);
				    	  levAscId++;
				    	  this.levelAssociations.put(lev3.getId() , ddm);			    	  
				      }
				      
			    	  //Find out whether there already is a association between the course and a hierarchy layer. If not create one.
				      if(this.levelCourses.get(course.getId()) == null)
				      {			    	  
				    	  LevelCourseMining dcm = new LevelCourseMining();
				    	  dcm.setLevel(lev3);
				    	  dcm.setCourse(course);
				    	  dcm.setId(Long.valueOf(platformPrefix + "" + (levCouId + 1)));
				    	  dcm.setPlatform(platformId);
				    	  levCouId++;
				    	  this.levelCourses.put(course.getId() , dcm);			    	  
				      }
				      //Find out whether there already is a association between this resource and a course. If not create one.
				      if(this.courseResources.get(resource.getId()) == null)
				      {			    	  
				    	  CourseResourceMining crm = new CourseResourceMining();
				    	  crm.setResource(resource);
				    	  crm.setCourse(course);
				    	  crm.setId(Long.valueOf(this.connector.getPrefix() + "" + (couResId + 1)));
				    	  couResId++;
				    	  crm.setPlatform(connector.getPlatformId());
				    	  this.courseResources.put(resource.getId(), crm);			    	  
				      }
				      
				      int pos = 1;
				      //Create Resource-objects for the all the pages included in the vlu
				      ArrayList<ResourceMining> tempRes = new ArrayList<ResourceMining>();
				      if(content.getLength() > 0)		   
				      {
						      for(int i = 0; i < content.item(0).getChildNodes().getLength(); i++)
						      {				    	  
						    	  if(content.item(0).getChildNodes().item(i).hasAttributes())				      
						    	  {
						    		  ResourceMining r1 = new ResourceMining();
						    		  t = content.item(0).getChildNodes().item(i).getAttributes();
						    		  for(int j = 0; j < t.getLength(); j++)
						    		  {
						    			  Node node = t.item(j);
						    			  //int len = t.getLength();
						    			  
						    			  if(node.getTextContent() != null)
						    			  {
							    			  
							    			  
							    			  if(node.getNodeName() == "xlink:href")
							    			  {
										    	  //ResourceMining r1 = new ResourceMining();
										    	  
										    	  r1.setDifficulty(resource.getDifficulty());
									    		  
										    	  r1.setType("Page");
							    				  r1.setUrl(resource.getUrl().substring(0, resource.getUrl().length()-5)+ "/Page" + node.getTextContent()+".html");
							    			  }
							    			  
							    			  if(node.getNodeName() == "xlink:title")
							    			  {
							    				  r1.setTitle(node.getTextContent());
							    			  }
				    						  

						    			  }
						    		  }
		    						long resource_id = -1;
			       	       			if(id_mapping.get(r1.getUrl()) != null)
			       	       			{
			       	       				resource_id = id_mapping.get(r1.getUrl()).getId();
			       	       				r1.setId(resource_id);
			       	       			}
			       	       			if(resource_id == -1 )
			       	       			{
			       	       				resource_id = resId + 1;
			       	       				resId = resource_id;
			       	       				resource_id = Long.valueOf(this.connector.getPrefix() + "" + resource_id);
			       	       				id_mapping.put(r1.getUrl(), new IDMappingMining(resource_id, r1.getUrl(), platformId));
			       	       				
			       	       				r1.setId(resource_id);
			       	       			}
				    				  
				    				  
				    				  r1.setPosition(pos);
				    				  r1.setPlatform(connector.getPlatformId());
			    					  tempRes.add(r1);
			    					  this.resourceObj.put(r1.getUrl(), r1);
			    					  //this.resourceObj.add(r1);
			    					  this.fileNames.add(filename + "*");
			    					  if(this.courseResources.get(r1.getId()) == null)
			    					  {
			    				    	  CourseResourceMining crm = new CourseResourceMining();
			    				    	  crm.setResource(r1);
			    				    	  crm.setCourse(course);
			    				    	  crm.setId(Long.valueOf(connector.getPrefix() + "" + (couResId + 1)));
			    				    	  couResId++;
			    				    	  crm.setPlatform(this.connector.getPlatformId());
			    				    	  this.courseResources.put(r1.getId(), crm);			    	  
			    					  }
			    					  pos++;///////////
		
						    	  }				      
						      }
						      long posT = 0;
						      try{
						    	  posT = resource.getProcessingTime() / pos-1;
						    	  
						      }catch(NumberFormatException e)
						      {}
						      
						      for(int i = 0; i < tempRes.size(); i++)
						      {
						    	  
						    	  tempRes.get(i).setProcessingTime(posT);
						      }
			    		  //Add the unlisted summary for each vlu
			    		  ResourceMining r1 = new ResourceMining();
			    		  r1.setDifficulty(resource.getDifficulty());
			    		  r1.setTitle(resource.getTitle());
				    	  r1.setType("Summary");
				    	  r1.setProcessingTime(resource.getProcessingTime()/content.getLength());
						  r1.setUrl(resource.getUrl().substring(0, resource.getUrl().length()-5)+ "/Page/summary.html");
						  
						  if(resourceObj.get(r1.getUrl()) == null)
						  {
							  r1.setId(Long.valueOf(connector.getPrefix() + "" + (resId + 1)));
							  resId++;
						  }
						  
						  
						  r1.setPosition(pos);
						  
						  if(this.resourceObj.get(r1.getUrl()) == null)
						  {
							  r1.setPlatform(connector.getPlatformId());
							  this.resourceObj.put(r1.getUrl(), r1);
							  id_mapping.put(r1.getUrl(), new IDMappingMining(r1.getId(), r1.getUrl(), connector.getPlatformId()));
	     	       			  this.fileNames.add(filename + "*");
	   				    	  CourseResourceMining crm = new CourseResourceMining();
					    	  crm.setResource(r1);
					    	  crm.setCourse(course);
					    	  crm.setId(Long.valueOf(connector.getPrefix() + "" + (couResId + 1)));
					    	  couResId++;
					    	  crm.setPlatform(connector.getPlatformId());
					    	  this.courseResources.put(r1.getId() , crm);	
						  }
				      }
			      else
			      {
			    	  //String s = fnames.get(this.resourceObj.get(r.getId()));
			    	  
			    	 // System.out.println(s.substring(s.indexOf("\\vsc")) + " : " + filename.substring(filename.indexOf("\\vsc")));
			      }
		      }
			      
		      // ---- Error handling ----
		    } catch( SAXParseException spe ) {
		        System.out.println( "\n** Parsing error, line " + spe.getLineNumber() + ", uri "  + spe.getSystemId() );
		    } catch( SAXException sxe ) {
		    	 System.out.println( "\n** SAX error!");
		    } catch( ParserConfigurationException pce ) {
		       System.out.println("ParserConfigurationException: "+pce.getMessage());
		    } catch( IOException ioe ) {
		    	System.out.println("IOException: " + ioe.getMessage());
		    }
	}
	
	/**
	 * Returns a list of all files with the specified suffix in the directory and its subdirectories.
	 *
	 * @param directory Directory containing the files and subdirectories.
	 * @param suffix File extension (returns all files if the string is empty)
	 * @return An ArrayList containing all file names (absolute paths) contained in the given directory.
	 */
	private ArrayList<String> getFilenames(String directory, String suffix)
	{
		ArrayList<String> all = new ArrayList<String>();
		try{
			File f = new File(directory);
			System.out.println("Gathering filenames from path: " +f.getAbsolutePath());
			ArrayList<String> dirs = new ArrayList<String>();
			for(int i = 0; i < f.list().length; i++)
				dirs.add(i, directory +"\\"+f.list()[i]);
			while(!dirs.isEmpty())
			{
				f = new File(dirs.get(0));
				if(f.isDirectory())
				{					
					for(int i= 0 ; i < f.list().length; i++)
						dirs.add(f+"\\"+f.list()[i]);
				}
				if(f.isFile() && f.toString().endsWith(suffix))
				{					
					all.add(f.toString());					
				}
				dirs.remove(0);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception @ getFilenames" + e.getMessage());
		}
		return all;
	}
	
	/**
	 * Saves all objects of the XMLPackageParser into the Database.
	 * Affected tables: Resource, DepartmentDegree, DegreeCourse, CourseResource
	 *
	 * @return Largest id in the id-mapping-table
	 */
	public void saveAllToDB()
	{
		List<Collection<?>> li = new ArrayList<Collection<?>>();
		li.add(this.levelObj.values());
		li.add(this.courseObj.values());
		li.add(this.resourceObj.values());
		li.add(this.levelAssociations.values());
		li.add(this.levelCourses.values());
		li.add(this.courseResources.values());
		li.add(this.id_mapping.values());
		
		Session session = dbHandler.getMiningSession();
		dbHandler.saveCollectionToDB(session,li);	
	}
	
	/**
	 * Reads all VLUs contained in the specified directory.
	 *
	 * @param directory the directory
	 */
	public void readAllVlus(String directory)
	{
		try {
			Clock c = new Clock();
			logger.info("Gathering filenames in directory...");
			ArrayList<String> all = getFilenames(directory, ".vlu");
			Collections.sort(all);
			logger.info("Found "+all.size()+" files in directory."+ c.getAndReset());
			logger.info("Reading all vlu-files in directory...");
			for(int i= 0; i < all.size(); i++)
			{
				readVLU(all.get(i));	
				if(i > 0 && i % (all.size()/10) == 0)
					logger.info("Read " + i / (all.size() / 10) + "0 % in " + c.get());
			}
		}catch(Exception e)
		{
			logger.error("Exception @ readAllVLUs ", e);
		}
	}
	
	public void clearMaps()
	{
		courseObj.clear();
		courseResources.clear();
		fileNames.clear();
		id_mapping.clear();
		levelAssociations.clear();
		levelCourses.clear();
		levelObj.clear();
		resourceObj.clear();
	}
}
