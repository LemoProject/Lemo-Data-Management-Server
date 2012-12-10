package de.lemo.dms.connectors.moodle_2_3;

import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class HibernateUtil {

  private static SessionFactory sessionFactoryDynamic = null;
  
  
  public static SessionFactory getDynamicSourceDBFactoryMoodle23(DBConfigObject dbconfig)
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
			  
			  s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Assign_LMS.hbm.xml");
			  s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Assign_Plugin_Config_LMS.hbm.xml");
			  s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Enrol_LMS.hbm.xml");
			  s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/User_Enrolments_LMS.hbm.xml");
			  s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Modules_LMS.hbm.xml");
			  s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Course_Modules_LMS.hbm.xml");
			  
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Course_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Forum_discussions_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Forum_posts_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Forum_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Grade_grades_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Groups_members_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Groups_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Log_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Question_states_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Question_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_grades_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_attempts_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_question_instances_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Wiki_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Resource_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/User_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Role_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Context_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Role_assignments_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Assignment_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Assignment_submission_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Scorm_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Grade_items_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Chat_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ChatLog_LMS.hbm.xml");
				s.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/CourseCategories_LMS.hbm.xml");  
			  
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
