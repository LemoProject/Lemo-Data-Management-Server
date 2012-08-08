package de.lemo.dms.connectors.moodleNumericId;

import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class HibernateUtil {

  private static SessionFactory sessionFactoryDynamic = null;
  
  
  public static SessionFactory getDynamicSourceDBFactoryMoodle(DBConfigObject dbconfig)
  {
	  if(sessionFactoryDynamic == null)
	  {
		  try{
			  Configuration s = new Configuration();
	
			  //add dynamic db-properties
			  for(Iterator<String> iter = dbconfig.getProperties().keySet().iterator(); iter.hasNext();)
			  {
				  String key = iter.next();
				  s.setProperty(key, dbconfig.getPropertyValue(key));
			  }
			  
				//Add mapping classes
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Course_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Forum_discussions_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Forum_posts_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Forum_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Grade_grades_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Groups_members_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Groups_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Log_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Question_states_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Question_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Quiz_grades_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Quiz_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Quiz_question_instances_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Wiki_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Resource_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/User_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Role_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Context_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Role_assignments_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Assignment_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Assignment_submission_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Scorm_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Grade_items_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Chat_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/ChatLog_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodleNumericId/moodleDBclass/CourseCategories_LMS.hbm.xml");  
			  
			  sessionFactoryDynamic = s.buildSessionFactory();
	
		  }catch (Throwable ex) {
		       throw new ExceptionInInitializerError(ex);
		  }
	  }
	  return sessionFactoryDynamic;
  }
  
  public static SessionFactory getDynamicSourceDBFactoryClix(DBConfigObject dbconfig)
  {
	  if(sessionFactoryDynamic == null)
	  {
		  try{
			  Configuration s = new Configuration();
	
			  //add dynamic db-properties
			  for(Iterator<String> iter = dbconfig.getProperties().keySet().iterator(); iter.hasNext();)
			  {
				  String key = iter.next();
				  s.setProperty(key, dbconfig.getPropertyValue(key));
			  }
			  
				//Add mapping classes
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/EComponent.hbm.xml");
				
			  sessionFactoryDynamic = s.buildSessionFactory();
	
		  }catch (Throwable ex) {
		       throw new ExceptionInInitializerError(ex);
		  }
	  }
	  return sessionFactoryDynamic;
  }
  
  public static void shutdownFactoryDynamic()
  {
	  sessionFactoryDynamic.close();
  }
 
}
