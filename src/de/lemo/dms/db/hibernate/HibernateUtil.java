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
		  	  
		  /*
		  s.addClass(de.lemo.dms.db.miningDBclass.AssignmentLogMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.AssignmentMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.ChatLogMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.ChatMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.ConfigMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.CourseAssignmentMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.CourseForumMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.CourseGroupMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.CourseLogMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.CourseMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.CourseQuizMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.CourseResourceMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.CourseScormMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.CourseUserMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.CourseWikiMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.DepartmentDegreeMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.DegreeCourseMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.DegreeMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.DepartmentMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.ForumLogMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.ForumMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.GroupMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.GroupUserMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.QuizQuestionMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.QuestionLogMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.QuestionMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.QuizLogMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.QuizMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.QuizUserMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.ResourceLogMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.ResourceMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.RoleMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.ScormLogMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.ScormMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.UserMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.WikiLogMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.WikiMining.class);
		  s.addClass(de.lemo.dms.db.miningDBclass.IDMappingMining.class);
		*/	
	  
	  	s.addResource("de/lemo/dms/db/miningDBclass/DepartmentDegreeMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ConfigMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/AssignmentMining.hbm.xml");
		

		
		s.addResource("de/lemo/dms/db/miningDBclass/ChatLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ChatMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseAssignmentMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseForumMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseGroupMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseQuizMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseResourceMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseScormMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseUserMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/CourseWikiMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/DegreeCourseMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/DegreeMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/DepartmentMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ForumLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ForumMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/GroupMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/GroupUserMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/QuestionLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/QuestionMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/QuizLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/QuizMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/QuizQuestionMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/QuizUserMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ResourceLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ResourceMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/RoleMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ScormLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/ScormMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/UserMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/WikiLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/WikiMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/AssignmentLogMining.hbm.xml");
		s.addResource("de/lemo/dms/db/miningDBclass/IDMappingMining.hbm.xml");
		
	
		//sessionFactoryMoodle = new Configuration().configure("de/lemo/dms/db/hibernate_MiningDB.cfg.xml").buildSessionFactory();
		
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
