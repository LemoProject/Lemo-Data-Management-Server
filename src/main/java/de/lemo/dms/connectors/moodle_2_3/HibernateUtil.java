package de.lemo.dms.connectors.moodle_2_3;

import java.util.HashMap;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.common.collect.Maps;

import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class HibernateUtil {

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

    private static SessionFactory createSessionFactory(DBConfigObject dbconfig) {
        Configuration cfg = new Configuration();

        // add properties from files
        for(Entry<String, String> entry : dbconfig.getProperties().entrySet()) {
            cfg.setProperty(entry.getKey(), entry.getValue());
        }

        // Add mapping classes

        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Assign_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Assign_Plugin_Config_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Enrol_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/User_Enrolments_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Modules_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Course_Modules_LMS.hbm.xml");

        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Course_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Forum_discussions_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Forum_posts_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Forum_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Grade_grades_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Groups_members_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Groups_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Log_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Question_states_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Question_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_grades_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_attempts_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Quiz_question_instances_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Wiki_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Resource_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/User_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Role_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Context_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Role_assignments_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Assignment_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Assignment_submission_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Scorm_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Grade_items_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Chat_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ChatLog_LMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle_2_3/moodleDBclass/CourseCategories_LMS.hbm.xml");

        return cfg.buildSessionFactory();
    }

}
