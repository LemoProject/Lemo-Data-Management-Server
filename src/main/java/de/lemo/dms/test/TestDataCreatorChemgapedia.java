package de.lemo.dms.test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.Query;
import org.hibernate.Session;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.DegreeCourseMining;
import de.lemo.dms.db.miningDBclass.DegreeMining;
import de.lemo.dms.db.miningDBclass.DepartmentDegreeMining;
import de.lemo.dms.db.miningDBclass.DepartmentMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;


public class TestDataCreatorChemgapedia {
	
	private ArrayList<ResourceLogMining> resourceLogList = new ArrayList<ResourceLogMining>();
	private ArrayList<ResourceMining> resourceList = new ArrayList<ResourceMining>();
	private ArrayList<DepartmentDegreeMining> departmentDegreeList = new ArrayList<DepartmentDegreeMining>();
	private ArrayList<DegreeCourseMining> degreeCourseList = new ArrayList<DegreeCourseMining>();
	private ArrayList<CourseResourceMining> courseResourceList = new ArrayList<CourseResourceMining>();
	
	private HashMap<Long, CourseMining> couResMap = new HashMap<Long, CourseMining>();
	private HashMap<Long, DegreeMining> degCouMap = new HashMap<Long, DegreeMining>();
	private HashMap<Long, DepartmentMining> depDegMap = new HashMap<Long, DepartmentMining>();
	
	@SuppressWarnings("unchecked")
	public void getDataFromDB()
	{
		IDBHandler dbHandler = ServerConfiguration.getInstance().getDBHandler();
		
		//accessing DB by creating a session and a transaction using HibernateUtil
        Session session = dbHandler.getMiningSession();
        session.clear();		
        
        Query resLogQuery = session.createQuery("from ResourceLogMining x order by x.id asc");
        resourceLogList = (ArrayList<ResourceLogMining>) resLogQuery.list();
        
        Query resQuery = session.createQuery("from ResourceMining x order by x.id asc");
        resourceList = (ArrayList<ResourceMining>) resQuery.list();

        
        Query degCouQuery = session.createQuery("from DegreeCourseMining x order by x.id asc");
        degreeCourseList = (ArrayList<DegreeCourseMining>) degCouQuery.list();
        
        Query depDegQuery = session.createQuery("from DepartmentDegreeMining x order by x.id asc");
        departmentDegreeList = (ArrayList<DepartmentDegreeMining>) depDegQuery.list();
        
        Query couResQuery = session.createQuery("from CourseResourceMining x order by x.id asc");
        courseResourceList = (ArrayList<CourseResourceMining>) couResQuery.list();
        
        for(CourseResourceMining cr : courseResourceList)
        	couResMap.put(cr.getResource().getId(), cr.getCourse());
        for(DegreeCourseMining dc : degreeCourseList)
        	degCouMap.put(dc.getCourse().getId(), dc.getDegree());
        for(DepartmentDegreeMining dd : departmentDegreeList)
        	depDegMap.put(dd.getDegree().getId(), dd.getDepartment());
	}
	
	public void getData(List<Collection<?>> objects)
	{
		
	}
	
	public void createFile(String path, String url, String title, String specialism, String name, String area, String audienceLevel, String timeValue, ArrayList<String> subResourceTitles, ArrayList<String> subResourceUrls)
	{
		try{
	        FileWriter out = new FileWriter(path);
	    	PrintWriter pout = new PrintWriter(out);
	    	
	    	pout.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    	pout.println("<!DOCTYPE vlunode PUBLIC \"-//Vernetztes Studium - Chemie//DTD vlu 1.0//EN\" \"http://www.vs-c.de/schema/vlunode.dtd\">");
	    	pout.println("<vlunode state=\"1\" version=\"0.1\" xlink:href=\"" + url + "\" xml:lang=\"de\" xmlns=\"http://www.vs-c.de/schema/2002\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
	    	pout.println("<title>" + title + "</title>");
	    	pout.println("<subject area=\"" + area + "\" name=\"" + name + "\" specialism=\"" + specialism + "\"/>");
	    	pout.println("<audience level=\"" + audienceLevel + "\"/>");
	    	pout.println("<time value=\"" + timeValue + "\"/>");
	    	pout.println("<content>");
	    	for(int i = 0; i < subResourceTitles.size(); i++)
	    		pout.println("<page xlink:title=\"" + subResourceTitles.get(i) + "\" xlink:href=\"" + subResourceUrls.get(i) + "\"/>");
	    	pout.println("</content>");
	    	pout.println("</vlunode>");
	    	
	    	pout.close();
	    	
		}catch(Exception e)
		{
			e.printStackTrace();			
		}
	}
	
	public void createXMLFile(String path, String url, String title, String specialism, String name, String area, String audienceLevel, String timeValue, ArrayList<String> subResourceTitles, ArrayList<String> subResourceUrls)
	{
		try {
            /////////////////////////////
            //Creating an empty XML Document

            //We need a Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            dbfac.setNamespaceAware(true);
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            
            DOMImplementation domImpl = docBuilder.getDOMImplementation();

            
            //Document doc = docBuilder.newDocument();
            Document doc = domImpl.createDocument("xlink", "bla", null);
            
            ////////////////////////
            //Creating the XML tree

            //create the root element and add it to the document
            Element root = doc.createElement("vlunode");
            root.setAttribute("xlink:href", url);
            doc.appendChild(root);
            
            Element titleEl = doc.createElement("title");
            Text text = doc.createTextNode(title);
            titleEl.appendChild(text);
            root.appendChild(titleEl);

            //create child element, add an attribute, and add to root
            Element subject = doc.createElement("subject");
            subject.setAttribute("area", area);
            subject.setAttribute("name", name);
            subject.setAttribute("specialism", specialism);
            root.appendChild(subject);
            
            Element audience = doc.createElement("audience");
            audience.setAttribute("level", audienceLevel);
            root.appendChild(audience);
            
            Element time = doc.createElement("time");
            time.setAttribute("value", timeValue);
            root.appendChild(time);
            
            //Element content = doc.createElement("content");
           /** for(int i = 0; i < subResourceTitles.size(); i++)
            {
            	Element page = doc.createElement("page");
            	page.setAttribute("xlink:href", subResourceUrls.get(i));
            	page.setAttribute("xlink:title", subResourceTitles.get(i));
            	content.appendChild(page);
            }
            root.appendChild(content);
            */

            /////////////////
            //Output the XML

            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            trans.setOutputProperty(OutputKeys.METHOD, "xml");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();

            FileWriter out = new FileWriter(path);
	    	PrintWriter pout = new PrintWriter(out);
	    	pout.print(xmlString);
	    	pout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void writeDataSource(String logFile, String metaPackage)
	{
		try
	    {	
	    	FileWriter out = new FileWriter(logFile);
	    	PrintWriter pout = new PrintWriter(out);
	    	HashMap<Long, String> ips = new HashMap<Long, String>();
	    	HashMap<Long, String> refs = new HashMap<Long, String>();
	    	
	    	//Write "server-logs"
    		for(ResourceLogMining r : resourceLogList)
    		{
    			Random randy = new Random();
    			String time = r.getTimestamp() + "";
    			String url = r.getResource().getUrl().substring(r.getResource().getUrl().indexOf("/vsengine/"));
    			String ref = "-";
    			if(refs.get(r.getUser().getId()) != null)
    				ref = refs.get(r.getUser().getId());
    			String ip;
    			String cookie = "-";
    			if(ips.get(r.getUser().getId()) == null)
    			{
    				
    				
    				ip = randy.nextInt(256) + "." + randy.nextInt(256) + "." +randy.nextInt(256) + "." + randy.nextInt(256);
    				if(randy.nextInt(8) != 0 && ips.get(r.getUser().getId()) == null)
    					cookie = ip + "/" + r.getTimestamp();
    				ips.put(r.getUser().getId(), ip);
    			}
    			else
    				ip = ips.get(r.getUser().getId());
    			
    			String line = time + "\t" + "200\t" + ip +"\t" + cookie + "\t" + url + "\t" + ref;
    			pout.println(line);
    			if(r.getDuration() != -1)
    				refs.put(r.getUser().getId(), r.getResource().getUrl());
    			else
    				refs.put(r.getUser().getId(), "-");
    		}
	    	pout.close();
	    	//Write meta-data-packages
	    	ArrayList<String> subResourceTitles = new ArrayList<String>();
			ArrayList<String> subResourceUrls = new ArrayList<String>();
			
			String title ="";
			String url ="";
			String specialism ="";
			String name ="";
			String area ="";
			String audienceLevel ="";
			String timeValue ="";
			String path ="";
			
			boolean newResource = false;
			
	    	for(ResourceMining resource : resourceList)
	    	{
	    		if(		resource.getType().equals("VLU") || 
	    				resource.getType().equals("Page") && 
	    				couResMap.get(resource.getId()) != null )
	    		{
	    			
	    			if(resource.getType().equals("VLU"))
	    			{
	    				
	    				if(newResource)
	    					createFile(path, url, title, specialism, name, area, audienceLevel, timeValue, subResourceTitles, subResourceUrls);
	    			
	    				   					
	    				newResource = true;
	    				subResourceTitles  = new ArrayList<String>();
	    				subResourceUrls =  new ArrayList<String>();
	    				
		    			CourseMining course = couResMap.get(resource.getId());
		    			DegreeMining degree = degCouMap.get(course.getId());
		    			DepartmentMining department = depDegMap.get(degree.getId());
		    			
		    			

		    			String dir = metaPackage + "\\" + department.getTitle()  + "\\" + degree.getTitle() + "\\" + course.getTitle() + "\\";
		    			File f = new File(dir);
		    			f.mkdirs();
		    			
		    			path = metaPackage + "\\" + department.getTitle()  + "\\" + degree.getTitle() + "\\" + course.getTitle() + "\\" + resource.getTitle() + ".vlu";
		    			
		    			title = resource.getTitle();
			    		url = resource.getUrl().substring(resource.getUrl().indexOf("/vsc/"), resource.getUrl().lastIndexOf("."));
			    		specialism = course.getTitle();
			    		name = department.getTitle();
			    		area = degree.getTitle();
			    		audienceLevel = resource.getDifficulty();
			    		timeValue = resource.getProcessingTime() + "";
		    		
	    			}
	    			else if(resource.getType().equals("Page") )
	    			{
	    				subResourceTitles.add(resource.getTitle());
	    				subResourceUrls.add(resource.getUrl().substring(resource.getUrl().lastIndexOf("/vsc/"), resource.getUrl().lastIndexOf(".")));
	    			}
		    		
			    	
	    		}
	    	}
	    	createFile(path, url, title, specialism, name, area, audienceLevel, timeValue, subResourceUrls, subResourceUrls);
	    	
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}

}
