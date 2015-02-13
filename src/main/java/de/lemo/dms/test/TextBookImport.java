package de.lemo.dms.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.sonatype.guice.plexus.config.Roles;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.AccessLog;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseLearning;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningObj;
import de.lemo.dms.db.mapping.LearningType;
import de.lemo.dms.db.mapping.Role;
import de.lemo.dms.db.mapping.User;

/**
 * This is just a small "connector" to import Digital Textbook data to LeMo.
 * 
 * 
 * @author sschwarzrock
 *
 */
public class TextBookImport {
	
	Map<String, User> users = new HashMap<String, User>();
	Map<Long, Course> courses = new HashMap<Long, Course>();
	Map<String, LearningObj> objects = new HashMap<String, LearningObj>();
	Map<Long, CourseLearning> courseLearnings = new HashMap<Long, CourseLearning>();
	Map<Long, CourseUser> courseUsers = new HashMap<Long, CourseUser>();
	List<AccessLog> accessLogs = new ArrayList<AccessLog>();
	Map<String, LearningType> types = new HashMap<String, LearningType>();
	List<Role> roles = new ArrayList<Role>();
	
	
	/**
	 * Reads data from csv line
	 * 
	 * @param line
	 */
	private void readLine(String line)
	{
		String[] arguments = line.split(",");
		if(arguments.length == 4)
		{
			if(!this.users.containsKey(arguments[0]))
			{
				User user = new User();
				user.setId(users.size() + 1);
				user.setLogin(arguments[0]);
				
				users.put(user.getLogin(), user);
				
				CourseUser cu = new CourseUser();
				cu.setId(courseUsers.size() + 1);
				cu.setCourse(courses.get(12L));
				cu.setRole(roles.get(0));
				cu.setUser(user);
				
				courseUsers.put(cu.getId(), cu);
			}
			if(!this.objects.containsKey(arguments[1] + " " + arguments[2]))
			{
				LearningObj object = new LearningObj();
				object.setId(objects.size() + 1);
				object.setTitle(arguments[1] + " " + arguments[2]);
				object.setInteractionType("ACCESS");
				if(!types.containsKey(arguments[2]))
				{
					LearningType type = new LearningType();
					type.setId(types.size() + 1);
					type.setType(arguments[2]);
					
					types.put(type.getType(), type);
				}
				object.setType(types.get(arguments[2]));
				objects.put(object.getTitle(), object);
				
				CourseLearning cl = new CourseLearning();
				cl.setId(courseLearnings.size() + 1);
				cl.setCourse(courses.get(12L));
				cl.setLearning(object);
				
				courseLearnings.put(cl.getId(), cl);
			}
			AccessLog alog = new AccessLog();
			alog.setId(accessLogs.size() +1);
			alog.setUser(users.get(arguments[0]));
			alog.setCourse(courses.get(12L));
			alog.setLearning(objects.get(arguments[1] + " " + arguments[2]));
			
			String[] s = arguments[3].split("\\.");
			
			int year = Integer.valueOf(arguments[3].split("\\.")[0]);
			int month = Integer.valueOf(arguments[3].split("\\.")[1]);
			int day = Integer.valueOf(arguments[3].split("\\.")[2].split(" ")[0]);
			int hour = Integer.valueOf(arguments[3].split("\\.")[2].split(" ")[1].split("\\:")[0]);
			int min = Integer.valueOf(arguments[3].split("\\.")[2].split(" ")[1].split("\\:")[1]);
			
			Date now = new Date(year + 100, month, day, hour, min, 00); 
			
			alog.setTimestamp(new Long(now.getTime()/1000));
			alog.setAction(arguments[1]);

			accessLogs.add(alog);
		}
		
		if(accessLogs.size() % 1000 == 0)
			System.out.println("Wrote " + accessLogs.size() + " logs");
	}
	
	
	
	public static void main(final String[] args)
	{
		ServerConfiguration.getInstance().loadConfig("/lemo");
		TextBookImport that = new TextBookImport();
		Course c = new Course();
		c.setId(12L);
		c.setTitle("Digital Textbook");
		that.courses.put(c.getId(), c);
		
		Role r = new Role();
		r.setId(1L);
		r.setSortOrder(0);
		r.setTitle("student");
		r.setType(2);
		
		that.roles.add(r);
		
		//Read data from csv
		try{
			BufferedReader br = new BufferedReader(new FileReader("DigitalTextbook_v1.txt"));
		    try {
		        String line = br.readLine();
		        while (line != null) {
		            that.readLine(line);
		            line = br.readLine();
		        }
		    } finally {
		        br.close();
		    }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		
		List<Collection<?>> cl= new ArrayList<Collection<?>>();
		cl.add(that.courses.values());
		cl.add(that.types.values());
		cl.add(that.users.values());
		cl.add(that.roles);
		cl.add(that.objects.values());
		cl.add(that.courseLearnings.values());
		cl.add(that.courseUsers.values());
		cl.add(that.accessLogs);
		
		dbHandler.saveCollectionToDB(session, cl);
		
		
		
	}

}
