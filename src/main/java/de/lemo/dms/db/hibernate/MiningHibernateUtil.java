package de.lemo.dms.db.hibernate;

import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class MiningHibernateUtil {

    private static SessionFactory sessionFactory;

    public static void initSessionFactory(DBConfigObject dbConfig) {
        sessionFactory = createSessionFactory(dbConfig);
    }

    /**
     * a getter for the SessionFactory of the Mining DB
     * 
     * @return a session Factory for Mining DB sessions
     */
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
            throw new IllegalStateException("No mining config set.");
        }
        return sessionFactory;
    }

    private static SessionFactory createSessionFactory(DBConfigObject dbConfig) {
        Configuration cfg = new Configuration();

        // add properties from files
        for(Entry<String, String> entry : dbConfig.getProperties().entrySet()) {
            cfg.setProperty(entry.getKey(), entry.getValue());
        }

        // Meta
        cfg.addResource("de/lemo/dms/db/miningDBclass/ConfigMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/IDMappingMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/PlatformMining.hbm.xml");

        // Object-classes
        cfg.addResource("de/lemo/dms/db/miningDBclass/AssignmentMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/ChatMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/CourseMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/DegreeMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/DepartmentMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/ForumMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/GroupMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/QuestionMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/QuizMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/ResourceMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/RoleMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/ScormMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/UserMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/WikiMining.hbm.xml");

        // Association-classes
        cfg.addResource("de/lemo/dms/db/miningDBclass/CourseAssignmentMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/CourseForumMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/CourseGroupMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/CourseQuizMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/CourseResourceMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/CourseScormMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/CourseUserMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/CourseWikiMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/DegreeCourseMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/DepartmentDegreeMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/GroupUserMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/QuizQuestionMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/QuizUserMining.hbm.xml");

        // Log-classes
        cfg.addResource("de/lemo/dms/db/miningDBclass/AssignmentLogMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/ChatLogMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/CourseLogMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/ForumLogMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/ResourceLogMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/ScormLogMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/QuestionLogMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/QuizLogMining.hbm.xml");
        cfg.addResource("de/lemo/dms/db/miningDBclass/WikiLogMining.hbm.xml");

        return cfg.buildSessionFactory();
    }

}