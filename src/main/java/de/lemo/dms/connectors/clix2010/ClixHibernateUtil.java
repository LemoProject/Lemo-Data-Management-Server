package de.lemo.dms.connectors.clix2010;

import java.util.HashMap;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.common.collect.Maps;

import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class ClixHibernateUtil {
    
    private static HashMap<DBConfigObject, SessionFactory> sessionFactories = Maps.newHashMap();

    public static SessionFactory getSessionFactory(DBConfigObject dbconfig) {
        SessionFactory sessionFactory = sessionFactories.get(dbconfig);
        if(sessionFactory == null) {
            sessionFactory = createSessionFactory(dbconfig);
            sessionFactories.put(dbconfig, sessionFactory);
        }
        return sessionFactory;
    }

    public static void closeSessionFactory(DBConfigObject dbconfig) {
        SessionFactory sessionFactory = sessionFactories.remove(dbconfig);
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }

    private static SessionFactory createSessionFactory(DBConfigObject dbConfig) {
        Configuration cfg = new Configuration();

        // add properties from files
        for(Entry<String, String> entry : dbConfig.getProperties().entrySet()) {
            cfg.setProperty(entry.getKey(), entry.getValue());
        }

        // add mapping classes
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/EComponent.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ChatProtocol.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/EComponentType.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/EComposing.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ExercisePersonalised.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntry.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ForumEntryState.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/Person.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroup.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PlatformGroupSpecification.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/Portfolio.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PortfolioLog.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/T2Task.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TAnswerPosition.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TeamExerciseComposingExt.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TeamExerciseGroup.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TeamExerciseGroupMember.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TGroupFullSpecification.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiContent.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TQtiEvalAssessment.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/TTestSpecification.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/WikiEntry.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/Chatroom.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ExerciseGroup.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/PersonComponentAssignment.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/ScormSessionTimes.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/clix2010/clixDBClass/BiTrackContentImpressions.hbm.xml");

        return cfg.buildSessionFactory();
    }

}
