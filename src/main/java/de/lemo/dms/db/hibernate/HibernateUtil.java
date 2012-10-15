package de.lemo.dms.db.hibernate;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.lemo.dms.core.ApplicationProperties;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class HibernateUtil {

    private static Logger logger = Logger.getLogger(HibernateUtil.class);

    private static SessionFactory sessionFactoryMining = null;

    /**
     * a getter for the SessionFactory of the Mining DB
     * 
     * @return a session Factory for Mining DB sessions
     */
    public static SessionFactory getSessionFactoryMining() {

        if(sessionFactoryMining == null) {

            Configuration config = null;
            try {
                config = loadHibernateConfig();
            } catch (NamingException e) {
                e.printStackTrace();
            }

            logger.info("Using mining database: " + config.getProperty("hibernate.connection.url"));

            config.addResource("de/lemo/dms/db/miningDBclass/ConfigMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/IDMappingMining.hbm.xml");

            // Object-classes
            config.addResource("de/lemo/dms/db/miningDBclass/AssignmentMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/ChatMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/CourseMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/DegreeMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/DepartmentMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/ForumMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/GroupMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/QuestionMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/QuizMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/ResourceMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/RoleMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/ScormMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/UserMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/WikiMining.hbm.xml");

            // Association-classes
            config.addResource("de/lemo/dms/db/miningDBclass/CourseAssignmentMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/CourseForumMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/CourseGroupMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/CourseQuizMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/CourseResourceMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/CourseScormMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/CourseUserMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/CourseWikiMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/DegreeCourseMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/DepartmentDegreeMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/GroupUserMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/QuizQuestionMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/QuizUserMining.hbm.xml");

            // Log-classes
            config.addResource("de/lemo/dms/db/miningDBclass/AssignmentLogMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/ChatLogMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/CourseLogMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/ForumLogMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/ResourceLogMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/ScormLogMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/QuestionLogMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/QuizLogMining.hbm.xml");
            config.addResource("de/lemo/dms/db/miningDBclass/WikiLogMining.hbm.xml");

            sessionFactoryMining = config.buildSessionFactory();

        }
        return sessionFactoryMining;
    }

    /**
     * Loads a hibernate configuration file.
     * 
     * If no hibernate file is found in the class path (it won't get deployed), it uses the deployment profile name
     * (specified in app.config.properties) to look up an environment variable (which may be defined in the servers
     * context.xml) to get the config file's path.
     * 
     * @return
     * @throws NamingException
     */
    private static Configuration loadHibernateConfig() throws NamingException {
        try {
            return new Configuration().configure();
        } catch (HibernateException e) {
            String systemName = ApplicationProperties.getPropertyValue("lemo.system-name");
            String configPath = systemName + ".hibernate.cfg.xml";
            return new Configuration().configure(configPath);
        }
    }

    /**
     * shuts down the SessionFactory for Mining
     */
    public static void shutdownMining() {
        sessionFactoryMining.close();
    }
}
