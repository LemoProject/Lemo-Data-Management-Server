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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.ServerConfigurationHardCoded;

import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.DegreeMining;
import de.lemo.dms.db.miningDBclass.DepartmentMining;
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.DegreeCourseMining;
import de.lemo.dms.db.miningDBclass.DepartmentDegreeMining;



/**
 * The Class XMLPackageParser.
 */
public class XMLPackageParser {
	
	/** The list of department objects. */
	private HashMap<String, DepartmentMining> departmentObj = new HashMap<String, DepartmentMining>();
	
	/** The list of degree objects. */
	private HashMap<String, DegreeMining> degreeObj = new HashMap<String, DegreeMining>();
	
	/** The list of course objects. */
	private HashMap<String, CourseMining> courseObj = new HashMap<String, CourseMining>();
	
	/** The list of department degree objects. */
	private HashMap<Long, DepartmentDegreeMining> departmentDegrees = new HashMap<Long, DepartmentDegreeMining>();
	
	/** The list of degree courses objects. */
	private HashMap<Long, DegreeCourseMining> degreeCourses = new HashMap<Long, DegreeCourseMining>();
	
	/** The course resources objects. */
	private HashMap<Long, CourseResourceMining> courseResources = new HashMap<Long, CourseResourceMining>();
	
	/** The list of resource objects. */
	private HashMap<String, ResourceMining> resourceObj = new HashMap<String, ResourceMining>();
	
	private ArrayList<String> fnames = new ArrayList<String>();
	
	private HashMap<String, IDMappingMining> id_mapping;
	
	private Long depId = 0L;
	private Long degId = 0L;
	private Long couId = 0L;
	private Long resId = 0L;
	private Long depDegId = 0L;
	private Long degCouId = 0L;
	private Long couResId = 0L;
	
	private static IDBHandler dbHandler;
	
	private static Long largestId;
	
	
	public XMLPackageParser()
	{
		dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		dbHandler.getConnection(ServerConfigurationHardCoded.getInstance().getMiningDBConfig());
		
		List<IDMappingMining> ids = (List<IDMappingMining>) dbHandler.performQuery(EQueryType.HQL, "from IDMappingMining x order by x.id asc");
		
		id_mapping = new HashMap<String, IDMappingMining>();
		for(int i = 0; i < ids.size(); i++)
		{
			id_mapping.put(ids.get(i).getHash(), ids.get(i));
		}
		
		List<DepartmentMining> deps = (List<DepartmentMining>) dbHandler.performQuery(EQueryType.HQL, "FROM DepartmentMining x order by x.id asc");
    	if(deps.size() > 0)
    		depId = deps.get(deps.size()-1).getId();
		for(int i = 0; i < deps.size(); i++)
    		this.departmentObj.put(deps.get(i).getTitle(), deps.get(i));
    	
		List<DegreeMining> degs = (List<DegreeMining>) dbHandler.performQuery(EQueryType.HQL, "FROM DegreeMining x order by x.id asc");
    	if(degs.size() > 0)
    		degId = degs.get(degs.size()-1).getId();
		for(int i = 0; i < degs.size(); i++)
    		this.degreeObj.put(degs.get(i).getTitle(), degs.get(i));
    	
		List<CourseMining> cous = (List<CourseMining>) dbHandler.performQuery(EQueryType.HQL, "FROM CourseMining x order by x.id asc");
    	if(cous.size() > 0)
    		couId = cous.get(cous.size()-1).getId();
		for(int i = 0; i < cous.size(); i++)
    		this.courseObj.put(cous.get(i).getTitle(), cous.get(i));
		
		List<ResourceMining> ress = (List<ResourceMining>) dbHandler.performQuery(EQueryType.HQL, "FROM ResourceMining x order by x.id asc");
    	if(ress.size() > 0)
    		resId = ress.get(ress.size()-1).getId();
		for(int i = 0; i < ress.size(); i++)
    		this.resourceObj.put(ress.get(i).getUrl(), ress.get(i));
		
		List<DepartmentDegreeMining> depDeg = (List<DepartmentDegreeMining>) dbHandler.performQuery(EQueryType.HQL, "FROM DepartmentDegreeMining x order by x.id asc");
    	if(depDeg.size() > 0)
    		resId = depDeg.get(depDeg.size()-1).getId();
		for(int i = 0; i < depDeg.size(); i++)
    		this.departmentDegrees.put(depDeg.get(i).getDegree().getId(), depDeg.get(i));
		
		List<DegreeCourseMining> degCou = (List<DegreeCourseMining>) dbHandler.performQuery(EQueryType.HQL, "FROM DegreeCourseMining x order by x.id asc");
    	if(degCou.size() > 0)
    		degCouId = degCou.get(degCou.size()-1).getId();
		for(int i = 0; i < degCou.size(); i++)
    		this.degreeCourses.put(degCou.get(i).getCourse().getId(), degCou.get(i));
		
		List<CourseResourceMining> couRes = (List<CourseResourceMining>) dbHandler.performQuery(EQueryType.HQL, "FROM CourseResourceMining x order by x.id asc");
    	if(couRes.size() > 0)
    		couResId = couRes.get(couRes.size()-1).getId();
		for(int i = 0; i < couRes.size(); i++)
    		this.courseResources.put(couRes.get(i).getResource().getId(), couRes.get(i));
		
    	
		List<Long> l = (List<Long>) (dbHandler.performQuery(EQueryType.HQL, "Select largestId from ConfigMining x order by x.id asc"));
		if(l != null && l.size() > 0)
			largestId = l.get(l.size()-1);
		else
			largestId = 0L;
	}
	/**
	 * Returns the URL of a vlu-overview.
	 * 
	 * @param filename Absolute path of the file representing the vlu.
	 * @return URL as string.
	 */
	private String getUrlFromVlu(String filename)
	{
		try {
		      // ---- Parse XML file ----
				NamedNodeMap t = null;
				DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
				DocumentBuilder        builder  = factory.newDocumentBuilder();
				Document               document = builder.parse( new File( filename ) );
				NodeList root = document.getElementsByTagName("vlunode");
				for(int i = 0 ; i < root.getLength(); i++)
				{
					t = root.item(i).getAttributes();
					for(int j = 0; j < t.getLength(); j++)
					{
						if(t.item(j).getNodeName() ==  "xlink:href")
						{
							return "http://www.chemgapedia.de/vsengine/vlu" + t.item(j).getNodeValue()+".html";
						}
					}
				}
		}catch (Exception e)
		{
			return "";
		}
		return "";
	}
	
	/**
	 * Opens a vlu - file, creates objects of the type "Department","Degree","Course","Resource",
	 * "DepartmentDegree", "DegreeCourse" and "CourseResource" containing the given information and saves the objects to the global lists.
	 *
	 * @param filename Absolute path of the file containing the data.
	 */
	private void readVLU(String filename)
	{
		ResourceMining r = new ResourceMining();
		try {
		      // ---- Parse XML file ----
		      DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
		      DocumentBuilder        builder  = factory.newDocumentBuilder();
		      Document               document = builder.parse( new File( filename ) );
		      
		      // ---- Get list of nodes to given element tag name ---- 
		      NamedNodeMap t = null;

		      NodeList content = document.getElementsByTagName("content");		      
		      //Set resource type
		      r.setType("VLU");
		      
		      //set resource title
		      if(document.getElementsByTagName("title").getLength() > 0)
		      {
		    	  r.setTitle(document.getElementsByTagName("title").item(0).getTextContent());
		      }
		      //Set resource difficulty
		      if(document.getElementsByTagName("audience").getLength() > 0)
		      {
		    	  r.setDifficulty(((Element)document.getElementsByTagName("audience").item(0)).getAttribute("level"));
		      }

		      try{
		    	  if(document.getElementsByTagName("time").getLength() > 0)
		    	  {
		    		  r.setProcessingTime( Long.valueOf(((Element)document.getElementsByTagName("time").item(0)).getAttribute("value")));
		    	  }
		      }catch(NumberFormatException e)
		      {
		    	  r.setProcessingTime(0L);
		      }
		      
		      String department ="";
		      String degree ="";
		      String course ="";
		      
		      if(document.getElementsByTagName("subject").getLength() > 0)
		      	department = ((Element)document.getElementsByTagName("subject").item(0)).getAttribute("name");
		      
		      if(document.getElementsByTagName("subject").getLength() > 0)
		    	  degree = ((Element)document.getElementsByTagName("subject").item(0)).getAttribute("area");
		      
		      if(document.getElementsByTagName("subject").getLength() > 0)
		    	  course = ((Element)document.getElementsByTagName("subject").item(0)).getAttribute("specialism");
		      
		      DepartmentMining dep = new DepartmentMining();
		      DegreeMining deg = new DegreeMining();
		      CourseMining cou = new CourseMining();
		      
		      if(departmentObj.get(department) == null)
		      {		    	 
		    	  dep.setTitle(department);
		    	  dep.setId(depId + 1);
		    	  depId++;
		    	  departmentObj.put(dep.getTitle(), dep);
		      }
		      else
		    	  dep = departmentObj.get(department);
		      if(degreeObj.get(degree) == null)
		      {
		    	  
		    	  deg.setTitle(degree);
		    	  deg.setId(degId + 1);
		    	  degId++;
		    	  degreeObj.put(deg.getTitle(), deg);
		      }
		      else
		    	  deg = degreeObj.get(degree);
		      if(courseObj.get(course) == null)
		      {
		    	 
		    	  cou.setTitle(course);
		    	  cou.setId(couId + 1);
		    	  couId++;
		    	  courseObj.put(cou.getTitle(), cou);
		      }
		      else
		    	  cou = courseObj.get(course);

		      //Set URL (has to be done in another method - otherwise it isn't working)
		      r.setUrl(getUrlFromVlu(filename));
		      r.setPosition(0);

		      this.resourceObj.get(r.getUrl());
		      //if(this.resourceObj.get(r.getUrl()) == null)
		      //{
				  	long r_id = -1;
 	       			if(id_mapping.get(r.getUrl()) != null)
 	       			{
 	       				r_id = id_mapping.get(r.getUrl()).getId();
 	       				r.setId(r_id);
 	       			}
 	       			if(r_id == -1 )
 	       			{
 	       				r_id = resId + 1;
 	       				resId = r_id;
 	       				id_mapping.put(r.getUrl(), new IDMappingMining(r_id, r.getUrl(), "Chemgapedia"));
 	       				largestId = resId;
 	       				r.setId(r_id);
 	       			}
		    	  
		    	  this.resourceObj.put(r.getUrl(), r);
		    	  this.fnames.add(filename);
		    	  //Save department - degree relation locally
			      if(this.departmentDegrees.get(deg.getId()) == null)
			      {			    	  
			    	  DepartmentDegreeMining ddm = new DepartmentDegreeMining();
			    	  ddm.setDegree(deg);
			    	  ddm.setDepartment(dep);
			    	  ddm.setId(depDegId + 1);
			    	  depDegId++;
			    	  this.departmentDegrees.put(deg.getId() , ddm);			    	  
			      }
			      
			      if(this.degreeCourses.get(cou.getId()) == null)
			      {			    	  
			    	  DegreeCourseMining dcm = new DegreeCourseMining();
			    	  dcm.setDegree(deg);
			    	  dcm.setCourse(cou);
			    	  dcm.setId(degCouId + 1);
			    	  degCouId++;
			    	  this.degreeCourses.put(cou.getId() , dcm);			    	  
			      }
			      
			      if(this.courseResources.get(r.getId()) == null)
			      {			    	  
			    	  CourseResourceMining crm = new CourseResourceMining();
			    	  crm.setResource(r);
			    	  crm.setCourse(cou);
			    	  crm.setId(couResId + 1);
			    	  couResId++;
			    	  this.courseResources.put(r.getId(), crm);			    	  
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
					    		  t = content.item(0).getChildNodes().item(i).getAttributes();
					    		  for(int j = 0; j < t.getLength(); j++)
					    		  {
					    			  if(t.item(j).getNodeName() == "xlink:href" && t.item(j).getTextContent() != null)
					    			  {
								    	  ResourceMining r1 = new ResourceMining();
								    	  
								    	  r1.setDifficulty(r.getDifficulty());
							    		  r1.setTitle(r.getTitle());
								    	  r1.setType("Page");
					    				  r1.setUrl(r.getUrl().substring(0, r.getUrl().length()-5)+ "/Page" + t.item(j).getTextContent()+".html");
					    				  
		    						  
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
				       	       				id_mapping.put(r1.getUrl(), new IDMappingMining(resource_id, r1.getUrl(), "Chemgapedia"));
				       	       				largestId = resId;
				       	       				r1.setId(resource_id);
				       	       			}
					    				  
					    				  
					    				  r1.setPosition(pos);
					    				  
				    				 // if(this.resourceObj.get(r1.getUrl()) == null)
				    				  //{
				    					  tempRes.add(r1);
				    					  this.resourceObj.put(r1.getUrl(), r1);
				    					  //this.resourceObj.add(r1);
				    					  this.fnames.add(filename + "*");
				    					  if(this.courseResources.get(r1.getId()) == null)
				    					  {
				    				    	  CourseResourceMining crm = new CourseResourceMining();
				    				    	  crm.setResource(r1);
				    				    	  crm.setCourse(cou);
				    				    	  crm.setId(couResId + 1);
				    				    	  couResId++;
				    				    	  this.courseResources.put(r1.getId(), crm);			    	  
				    					  }
				    					  pos++;
				    				//  }
					    				  
					    			  }
					    		  }
	
					    	  }				      
					      }
					      long posT = 0;
					      try{
					    	  posT = r.getProcessingTime() / pos-1;
					    	  
					      }catch(NumberFormatException e)
					      {}
					      
					      for(int i = 0; i < tempRes.size(); i++)
					      {
					    	  
					    	  tempRes.get(i).setProcessingTime(posT);
					      }
		    		  //Add the unlisted summary for each vlu
		    		  ResourceMining r1 = new ResourceMining();
		    		  r1.setDifficulty(r.getDifficulty());
		    		  r1.setTitle(r.getTitle());
			    	  r1.setType("Summary");
			    	  r1.setProcessingTime(r.getProcessingTime()/content.getLength());
					  r1.setUrl(r.getUrl().substring(0, r.getUrl().length()-5)+ "/Page/summary.html");
					  
					  if(resourceObj.get(r1.getUrl()) == null)
					  {
						  r1.setId(resId + 1);
						  resId++;
					  }
					  
					  
					  r1.setPosition(pos);
					  
					  if(this.resourceObj.get(r1.getUrl()) == null)
					  {
						  this.resourceObj.put(r1.getUrl(), r1);
						  id_mapping.put(r1.getUrl(), new IDMappingMining(r1.getId(), r1.getUrl(), "Chemgapedia"));
     	       			  largestId = resId;
						  this.fnames.add(filename + "*");
   				    	  CourseResourceMining crm = new CourseResourceMining();
				    	  crm.setResource(r1);
				    	  crm.setCourse(cou);
				    	  crm.setId(couResId + 1);
				    	  couResId++;
				    	  this.courseResources.put(r1.getId() , crm);	
					  }
			      }
		      else
		      {
		    	  //String s = fnames.get(this.resourceObj.get(r.getId()));
		    	  
		    	 // System.out.println(s.substring(s.indexOf("\\vsc")) + " : " + filename.substring(filename.indexOf("\\vsc")));
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
	 * @param xpp this
	 * @return true if transaction was successful, otherwise false
	 */
	public Long saveAllToDB()
	{
		List<Collection<?>> li = new ArrayList<Collection<?>>();
		li.add(this.departmentObj.values());
		li.add(this.degreeObj.values());
		li.add(this.courseObj.values());
		li.add(this.resourceObj.values());
		li.add(this.departmentDegrees.values());
		li.add(this.degreeCourses.values());
		li.add(this.courseResources.values());
		li.add(this.id_mapping.values());
		dbHandler.saveToDB(li);		
		return largestId;
	}
	
	/**
	 * Read all VLUs contained in the specified directory.
	 *
	 * @param directory the directory
	 */
	public void readAllVlus(String directory)
	{
		try {
			Clock c = new Clock();
			System.out.println("Gathering filenames in directory...");
			ArrayList<String> all = getFilenames(directory, ".vlu");
			Collections.sort(all);
			System.out.println("Found "+all.size()+" files in directory."+ c.getAndReset());
			System.out.println("Reading all vlu-files in directory...");
			for(int i= 0; i < all.size(); i++)
			{
				readVLU(all.get(i));	
				if(i % 100 == 0)
					System.out.println("Read " +i + " files in " + c.get());
			}
		}catch(Exception e)
		{
			System.out.println("Exception @ readAllVLUs " + e.getMessage());
		}
	}
}
