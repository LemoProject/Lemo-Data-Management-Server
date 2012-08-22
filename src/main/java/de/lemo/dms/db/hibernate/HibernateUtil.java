package de.lemo.dms.db.hibernate;

import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class HibernateUtil {

  private static SessionFactory sessionFactoryMining = null;
  
  /**
   * a getter for the SessionFactory of the Mining DB
   * @return a session Factory for Mining DB sessions
   */  
  public static SessionFactory getSessionFactoryMining(DBConfigObject dbConf) {
		
	  if(sessionFactoryMining == null)
	  {
		  Configuration s = new Configuration();
			
		  //add dynamic db-properties
		  for(Iterator<String> iter = dbConf.getProperties().keySet().iterator(); iter.hasNext();)
		  {
			  String key = iter.next();
			  s.setProperty(key, dbConf.getPropertyValue(key));
		  }
		  	 
		
		s.addResource("de/lemo/dms/db/miningDBclass/ConfigMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/IDMappingMining.hbm.xml");
		

		//Object-classes
		s.addResource("de/lemo/dms/db/miningDBclass/AssignmentMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ChatMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/DegreeMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/DepartmentMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ForumMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/GroupMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/QuestionMining.hbm.xml");		
		s.addResource("de/lemo/dms/db/miningDBclass/QuizMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ResourceMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/RoleMining.hbm.xml");		
		s.addResource("de/lemo/dms/db/miningDBclass/ScormMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/UserMining.hbm.xml");		
		s.addResource("de/lemo/dms/db/miningDBclass/WikiMining.hbm.xml");
		
		
		//Association-classes
		s.addResource("de/lemo/dms/db/miningDBclass/CourseAssignmentMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseForumMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseGroupMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseQuizMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseResourceMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseScormMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseUserMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseWikiMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/DegreeCourseMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/DepartmentDegreeMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/GroupUserMining.hbm.xml");		
		s.addResource("de/lemo/dms/db/miningDBclass/QuizQuestionMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/QuizUserMining.hbm.xml");		
		
		
		//Log-classes
		s.addResource("de/lemo/dms/db/miningDBclass/AssignmentLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ChatLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ForumLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ResourceLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ScormLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/QuestionLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/QuizLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/WikiLogMining.hbm.xml");
		  
		sessionFactoryMining = s.buildSessionFactory();
	 
	//	sessionFactoryMining = new Configuration().configure("de/lemo/dms/db/hibernate/hibernate_MiningDB.cfg.xml").buildSessionFactory();

	  }
	  return sessionFactoryMining;
  }
  /**
   * shuts down the SessionFactory for Mining
   */ 
  public static void shutdownMining() {
	  sessionFactoryMining.close();
  }  
}
