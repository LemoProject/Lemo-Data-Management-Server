package de.lemo.dms.connectors.moodle;

import java.util.HashMap;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.common.collect.Maps;

import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class MoodleHibernateUtil {

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
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Course_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Forum_discussions_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Forum_posts_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Forum_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Grade_grades_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Groups_members_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Groups_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Log_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Question_states_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Question_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Quiz_grades_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Quiz_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Quiz_question_instances_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Wiki_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Resource_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/User_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Role_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Context_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Role_assignments_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Assignment_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Assignment_submission_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Scorm_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Grade_items_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/Chat_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ChatLog_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/CourseCategories_LMS.hbm.xml");

        return cfg.buildSessionFactory();
    }

}
