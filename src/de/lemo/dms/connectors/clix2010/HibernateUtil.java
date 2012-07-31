package de.lemo.dms.connectors.clix2010;

import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class HibernateUtil {

  private static SessionFactory sessionFactoryDynamic = null;
  
  
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
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ChatProtocol.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/EComponentType.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/EComposing.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ExercisePersonalised.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntry.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntryState.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/Person.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroup.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroupSpecification.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PortfolioLog.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/T2Task.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TAnswerPosition.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TeamExerciseComposingExt.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TeamExerciseGroup.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TeamExerciseGroupMember.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TGroupFullSpecification.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiContent.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiEvalAssessment.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TTestSpecification.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/WikiEntry.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/Chatroom.hbm.xml");
				
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ScormSessionTimes.hbm.xml");
				s.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/BiTrackContentImpressions.hbm.xml");
				
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
