package de.lemo.dms.connectors.moodle;
//import miningDBclass.Config_mining;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.AssignmentMining;
import de.lemo.dms.db.miningDBclass.ChatLogMining;
import de.lemo.dms.db.miningDBclass.ChatMining;
import de.lemo.dms.db.miningDBclass.CourseAssignmentMining;
import de.lemo.dms.db.miningDBclass.CourseForumMining;
import de.lemo.dms.db.miningDBclass.CourseGroupMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseQuizMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.CourseScormMining;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.CourseWikiMining;
import de.lemo.dms.db.miningDBclass.DegreeCourseMining;
import de.lemo.dms.db.miningDBclass.DegreeMining;
import de.lemo.dms.db.miningDBclass.DepartmentDegreeMining;
import de.lemo.dms.db.miningDBclass.DepartmentMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.ForumMining;
import de.lemo.dms.db.miningDBclass.GroupMining;
import de.lemo.dms.db.miningDBclass.GroupUserMining;
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuestionMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.QuizMining;
import de.lemo.dms.db.miningDBclass.QuizQuestionMining;
import de.lemo.dms.db.miningDBclass.QuizUserMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.RoleMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.ScormMining;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.WikiMining;
import de.lemo.dms.connectors.moodle.moodleDBclass.Assignment_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Assignment_submissions_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.ChatLog_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Chat_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Context_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.CourseCategories_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Course_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Forum_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Forum_discussions_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Forum_posts_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Grade_grades_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Grade_items_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Groups_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Groups_members_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Log_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Question_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Question_states_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Quiz_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Quiz_attempts_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Quiz_grades_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Quiz_question_instances_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Resource_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Role_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Role_assignments_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Scorm_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.User_LMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.Wiki_LMS;

import org.apache.log4j.Logger;
import org.hibernate.*;

import de.lemo.dms.connectors.moodle.HibernateUtil;
import de.lemo.dms.core.ServerConfigurationHardCoded;

import java.util.*;

/** The main class of the extraction process. 
 * Implementation of the abstract extract class for the LMS Moodle.
 */
public class ExtractAndMapMoodle extends ExtractAndMap{//Versionsnummer in Namen einf�gen

//LMS tables instances lists
	private static List<Log_LMS> log_lms;
	private static List<Resource_LMS> resource_lms;
	private static List<Course_LMS> course_lms;
	private static List<Forum_LMS> forum_lms;
	private static List<Wiki_LMS> wiki_lms;
	private static List<User_LMS> user_lms;
	private static List<Quiz_LMS> quiz_lms;
	private static List<Quiz_attempts_LMS> quiz_attempts_lms;
	private static List<Quiz_question_instances_LMS> quiz_question_instances_lms;
	private static List<Question_LMS> question_lms;
	private static List<Groups_LMS> group_lms;
	private static List<Groups_members_LMS> group_members_lms;
	private static List<Question_states_LMS> question_states_lms;
	private static List<Forum_posts_LMS> forum_posts_lms;
	private static List<Role_LMS> role_lms;
	private static List<Context_LMS> context_lms;
	private static List<Role_assignments_LMS> role_assignments_lms;
	private static List<Assignment_LMS> assignment_lms;	
	private static List<Assignment_submissions_LMS> assignment_submission_lms;
	private static List<Quiz_grades_LMS> quiz_grades_lms;
	private static List<Forum_discussions_LMS> forum_discussions_lms;
	private static List<Scorm_LMS> scorm_lms;
	private static List<Grade_grades_LMS> grade_grades_lms;
	private static List<Grade_items_LMS> grade_items_lms;
	private static List<Chat_LMS> chat_lms;
	private static List<ChatLog_LMS> chat_log_lms;
	private static List<CourseCategories_LMS> course_categories_lms;
	
	
	private static boolean numericUserId = false;
	
	/**Logger**/
	private static Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();
	
	@SuppressWarnings("unchecked")	
	public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp) {
		   
		   	//accessing DB by creating a session and a transaction using HibernateUtil
	        Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
	        //Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
	        session.clear();
	        Transaction tx = session.beginTransaction();

	        //Just for testing. has to be set to Long.MaxValue if not longer needed.
	        long ceiling = Long.MAX_VALUE;
//reading the LMS Database, create tables as lists of instances of the DB-table classes
	        

	        Query log = session.createQuery("from Log_LMS x where x.time>=:readingtimestamp and x.time<=:ceiling order by x.id asc");
	        log.setParameter("readingtimestamp", readingfromtimestamp);
	        log.setParameter("ceiling", ceiling);
	        log_lms = log.list();	        
	        System.out.println("log_lms tables: " + log_lms.size());       
	        
	    	Query resource = session.createQuery("from Resource_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	resource.setParameter("readingtimestamp", readingfromtimestamp);
	    	resource.setParameter("ceiling", ceiling);
	    	resource_lms = resource.list();		        
	    	System.out.println("resource_lms tables: " + resource_lms.size());
	    	
	    	
	    	Query chat = session.createQuery("from Chat_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	chat.setParameter("readingtimestamp", readingfromtimestamp);
	    	chat.setParameter("ceiling", ceiling);
	    	chat_lms = chat.list();		        
	    	System.out.println("chat_lms tables: " + chat_lms.size());
	    	
	    	Query chatlog = session.createQuery("from ChatLog_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:ceiling order by x.id asc");
	    	chatlog.setParameter("readingtimestamp", readingfromtimestamp);
	    	chatlog.setParameter("ceiling", ceiling);
	    	chat_log_lms = chatlog.list();		        
	    	System.out.println("chat_log_lms tables: " + chat_log_lms.size());
	    	
	    	Query courseCategories = session.createQuery("from CourseCategories_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	courseCategories.setParameter("readingtimestamp", readingfromtimestamp);
	    	courseCategories.setParameter("ceiling", ceiling);
	    	course_categories_lms = courseCategories.list();		        
	    	System.out.println("course_categories_lms tables: " + course_categories_lms.size());
	    	
	    	
	    	Query course = session.createQuery("from Course_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	course.setParameter("readingtimestamp", readingfromtimestamp);
	    	course.setParameter("ceiling", ceiling);
	    	course_lms = course.list();		        	        
	    	System.out.println("course_lms tables: " + course_lms.size());
	    	
	    	Query forum_posts = session.createQuery("from Forum_posts_LMS x where x.modified>=:readingtimestamp and x.modified<=:ceiling order by x.id asc");
	    	forum_posts.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_posts.setParameter("ceiling", ceiling);
	    	forum_posts_lms = forum_posts.list();	
	    	System.out.println("forum_posts_lms tables: " + forum_posts_lms.size()); 
	    	
	    	Query forum = session.createQuery("from Forum_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	forum.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum.setParameter("ceiling", ceiling);
	    	forum_lms = forum.list();		        
	    	System.out.println("forum_lms tables: " + forum_lms.size());
	    	
	    	Query group = session.createQuery("from Groups_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	group.setParameter("readingtimestamp", readingfromtimestamp);
	    	group.setParameter("ceiling", ceiling);
	    	group_lms = group.list();	        
	    	System.out.println("group_lms tables: " + group_lms.size());
	    	
	    	Query quiz = session.createQuery("from Quiz_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	quiz.setParameter("readingtimestamp", readingfromtimestamp);
	    	quiz.setParameter("ceiling", ceiling);
	    	quiz_lms = quiz.list();		        
	    	System.out.println("quiz_lms tables: " + quiz_lms.size());	
	    	
	    	Query wiki = session.createQuery("from Wiki_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	wiki.setParameter("readingtimestamp", readingfromtimestamp);
	    	wiki.setParameter("ceiling", ceiling);
	    	wiki_lms = wiki.list();		        
	    	System.out.println("wiki_lms tables: " + wiki_lms.size());
	    	
	    	Query group_members = session.createQuery("from Groups_members_LMS x where x.timeadded>=:readingtimestamp and x.timeadded<=:ceiling order by x.id asc");
	    	group_members.setParameter("readingtimestamp", readingfromtimestamp);
	    	group_members.setParameter("ceiling", ceiling);
	    	group_members_lms = group_members.list();		    	
	        System.out.println("group_members_lms tables: " + group_members_lms.size());
	    	
	    	Query question_states = session.createQuery("from Question_states_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:ceiling order by x.id asc");
	    	question_states.setParameter("readingtimestamp", readingfromtimestamp);
	    	question_states.setParameter("ceiling", ceiling);
	    	question_states_lms = question_states.list();	    	
	        System.out.println("question_states_lms tables: " + question_states_lms.size());
	    	
	    	Query quiz_question_instances = session.createQuery("from Quiz_question_instances_LMS x order by x.id asc");
	    	quiz_question_instances_lms = quiz_question_instances.list();		    	
	        System.out.println("quiz_question_instances_lms tables: " + quiz_question_instances_lms.size()); 
	    	
	    	Query question = session.createQuery("from Question_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	question.setParameter("readingtimestamp", readingfromtimestamp);
	    	question.setParameter("ceiling", ceiling);
	    	question_lms = question.list();		    	
	        System.out.println("question_lms tables: " + question_lms.size());
	    	
	    	Query quiz_attempts = session.createQuery("from Quiz_attempts_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	quiz_attempts.setParameter("ceiling", ceiling);
	    	quiz_attempts.setParameter("readingtimestamp", readingfromtimestamp);
	    	quiz_attempts_lms = quiz_attempts.list();		    	
	        System.out.println("quiz_attempts_lms tables: " + quiz_attempts_lms.size());        
	        
	    	Query user = session.createQuery("from User_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	user.setParameter("ceiling", ceiling);
	    	user.setParameter("readingtimestamp", readingfromtimestamp);
	    	user_lms = user.list();		    	
	        System.out.println("user_lms tables: " + user_lms.size());	 
	    	
	    	Query role = session.createQuery("from Role_LMS x order by x.id asc");
	    	role_lms = role.list();
	        System.out.println("role_lms tables: " + role_lms.size());
	    	
	    	Query context = session.createQuery("from Context_LMS x order by x.id asc");
	    	context_lms = context.list();		    	
	        System.out.println("context_lms tables: " + context_lms.size());
	    	
	    	Query role_assignments = session.createQuery("from Role_assignments_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	role_assignments.setParameter("ceiling", ceiling);
	    	role_assignments.setParameter("readingtimestamp", readingfromtimestamp);
	    	role_assignments_lms = role_assignments.list();		    	
	        System.out.println("role_assignments_lms tables: " + role_assignments_lms.size());
	    	
	    	Query assignments = session.createQuery("from Assignment_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	assignments.setParameter("ceiling", ceiling);
	    	assignments.setParameter("readingtimestamp", readingfromtimestamp);
	    	assignment_lms = assignments.list();		    	
	        System.out.println("assignment_lms tables: " + assignment_lms.size());
	    	
	        
	    	Query assignment_submission = session.createQuery("from Assignment_submissions_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	assignment_submission.setParameter("ceiling", ceiling);
	    	assignment_submission.setParameter("readingtimestamp", readingfromtimestamp);
	    	assignment_submission_lms = assignment_submission.list();		    	
	        System.out.println("assignment_submission_lms tables: " + assignment_submission_lms.size());


	    	Query quiz_grades = session.createQuery("from Quiz_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	quiz_grades.setParameter("ceiling", ceiling);
	    	quiz_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	quiz_grades_lms = quiz_grades.list();		    	
	        System.out.println("quiz_grades_lms tables: " + quiz_grades_lms.size());
	        
	    	Query forum_discussions = session.createQuery("from Forum_discussions_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	forum_discussions.setParameter("ceiling", ceiling);
	    	forum_discussions.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_discussions_lms = forum_discussions.list();		    	
	        System.out.println("forum_discussions_lms tables: " + forum_discussions_lms.size());	        

	    	Query scorm = session.createQuery("from Scorm_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	scorm.setParameter("ceiling", ceiling);
	    	scorm.setParameter("readingtimestamp", readingfromtimestamp);
	    	scorm_lms = scorm.list();		    	
	        System.out.println("scorm_lms tables: " + scorm_lms.size());	        

	    	Query grade_grades = session.createQuery("from Grade_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	grade_grades.setParameter("ceiling", ceiling);
	    	grade_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	grade_grades_lms = grade_grades.list();		    	
	        System.out.println("grade_grades_lms tables: " + grade_grades_lms.size());		        

	    	Query grade_items = session.createQuery("from Grade_items_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	grade_items.setParameter("ceiling", ceiling);
	    	grade_items.setParameter("readingtimestamp", readingfromtimestamp);
	    	grade_items_lms = grade_items.list();		    	
	        System.out.println("grade_items_lms tables: " + grade_items_lms.size());
	        	        
//hibernate session finish and close
	        tx.commit();        
	        session.close();
	        
	}

	@SuppressWarnings("unchecked")
	public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp, long readingtotimestamp) {
		   
//accessing DB by creating a session and a transaction using HibernateUtil
	        Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle(dbConf).openSession();
			//Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
	        session.clear();
	        Transaction tx = session.beginTransaction();

//reading the LMS Database, create tables as lists of instances of the DB-table classes

	        if(user_lms == null){

	        	Query resource = session.createQuery("from Resource_LMS x order by x.id asc");
	        	resource_lms = resource.list();		        
	    	
	        	Query course = session.createQuery("from Course_LMS x order by x.id asc");
	        	course_lms = course.list();		        	        
	        	
	        	Query chat = session.createQuery("from Chat_LMS x order by x.id asc");
		    	chat_lms = chat.list();		        
		    			    	
		    	Query courseCategories = session.createQuery("from CourseCategories_LMS x order by x.id asc");
		    	course_categories_lms = courseCategories.list();		        
	    	
	        	Query forum = session.createQuery("from Forum_LMS x order by x.id asc");
	        	forum_lms = forum.list();		        
	    	
	        	Query group = session.createQuery("from Groups_LMS x order by x.id asc");
	        	group_lms = group.list();	        
	    	
	        	Query quiz = session.createQuery("from Quiz_LMS x order by x.id asc");
	        	quiz_lms = quiz.list();		        

	        	Query wiki = session.createQuery("from Wiki_LMS x order by x.id asc");
	        	wiki_lms = wiki.list();		        
	    	
	        	Query quiz_question_instances = session.createQuery("from Quiz_question_instances_LMS x order by x.id asc");
	        	quiz_question_instances_lms = quiz_question_instances.list();		    	
	    	
	        	Query question = session.createQuery("from Question_LMS x order by x.id asc");
	        	question_lms = question.list();		    	
	    	
	        	Query user = session.createQuery("from User_LMS x order by x.id asc");
	        	user_lms = user.list();		    	
	    	
	        	Query role = session.createQuery("from Role_LMS x order by x.id asc");
	        	role_lms = role.list();		    	

	        	session.clear();
	    	
	        	Query context = session.createQuery("from Context_LMS x order by x.id asc");
	        	context_lms = context.list();
	    	
	        	Query assignments = session.createQuery("from Assignment_LMS x order by x.id asc");
	        	assignment_lms = assignments.list();		    	
	        	
		    	Query scorm = session.createQuery("from Scorm_LMS x order by x.id asc");
		    	scorm_lms = scorm.list();		    	
		    	
		    	Query grade_items = session.createQuery("from Grade_items_LMS x order by x.id asc");
		    	grade_items_lms = grade_items.list();
	        }
	    	
	    	Query log = session.createQuery("from Log_LMS x where x.time>=:readingtimestamp and x.time<=:readingtimestamp2 order by x.id asc");
	        log.setParameter("readingtimestamp", readingfromtimestamp);
	        log.setParameter("readingtimestamp2", readingtotimestamp);
	        log_lms = log.list();	        
	        
	    	Query chatlog = session.createQuery("from ChatLog_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:readingtimestamp2 order by x.id asc");
	    	chatlog.setParameter("readingtimestamp", readingfromtimestamp);
	    	chatlog.setParameter("readingtimestamp2", readingtotimestamp);	
	    	chat_log_lms = chatlog.list();		        
	    	
	    	Query forum_posts = session.createQuery("from Forum_posts_LMS x where x.created>=:readingtimestamp and x.created<=:readingtimestamp2 order by x.id asc");
	    	forum_posts.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_posts.setParameter("readingtimestamp2", readingtotimestamp);	    	
	    	forum_posts_lms = forum_posts.list();
	    	
	    	Query forum_posts_modified = session.createQuery("from Forum_posts_LMS x where x.modified>=:readingtimestamp and x.modified<=:readingtimestamp2 order by x.id asc");
	    	forum_posts_modified.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_posts_modified.setParameter("readingtimestamp2", readingtotimestamp);
	    	forum_posts_lms.addAll(forum_posts_modified.list());

	        session.clear();
	    	
	    	Query group_members = session.createQuery("from Groups_members_LMS x where x.timeadded>=:readingtimestamp and x.timeadded<=:readingtimestamp2 order by x.id asc");
	    	group_members.setParameter("readingtimestamp", readingfromtimestamp);
	    	group_members.setParameter("readingtimestamp2", readingtotimestamp);
	    	group_members_lms = group_members.list();		    	
	    	
	    	Query question_states = session.createQuery("from Question_states_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:readingtimestamp2 order by x.id asc");
	    	question_states.setParameter("readingtimestamp", readingfromtimestamp);
	    	question_states.setParameter("readingtimestamp2", readingtotimestamp);
	    	question_states_lms = question_states.list();	    	
	    	
	    	Query quiz_attempts = session.createQuery("from Quiz_attempts_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	quiz_attempts.setParameter("readingtimestamp", readingfromtimestamp);
	    	quiz_attempts.setParameter("readingtimestamp2", readingtotimestamp);
	    	quiz_attempts_lms = quiz_attempts.list();		    	
	    	
	    	Query role_assignments = session.createQuery("from Role_assignments_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	role_assignments.setParameter("readingtimestamp", readingfromtimestamp);
	    	role_assignments.setParameter("readingtimestamp2", readingtotimestamp);
	    	role_assignments_lms = role_assignments.list();		    	
	    		    	
	    
	    	Query assignment_submission = session.createQuery("from Assignment_submissions_LMS x where x.timecreated>=:readingtimestamp and x.timecreated<=:readingtimestamp2 order by x.id asc");
	    	assignment_submission.setParameter("readingtimestamp", readingfromtimestamp);
	    	assignment_submission.setParameter("readingtimestamp2", readingtotimestamp);
	    	assignment_submission_lms = assignment_submission.list();		    	


	    	Query quiz_grades = session.createQuery("from Quiz_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	quiz_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	quiz_grades.setParameter("readingtimestamp2", readingtotimestamp);
	    	quiz_grades_lms = quiz_grades.list();		    	

	    	Query forum_discussions = session.createQuery("from Forum_discussions_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	forum_discussions.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_discussions.setParameter("readingtimestamp2", readingtotimestamp);
	    	forum_discussions_lms = forum_discussions.list();		    	
    	
	    	Query grade_grades = session.createQuery("from Grade_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	grade_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	grade_grades.setParameter("readingtimestamp2", readingtotimestamp);
	    	grade_grades_lms = grade_grades.list();
	        	        
	        session.clear();
	        
	    	
//hibernate session finish and close
	        tx.commit();
	        session.close();
 
	}
	
	public void clearLMStables(){
		log_lms.clear();
		resource_lms.clear();
		course_lms.clear();
		forum_lms.clear();
		wiki_lms.clear();
		user_lms.clear();
		quiz_lms.clear();
		grade_grades_lms.clear();
		quiz_attempts_lms.clear();
		group_lms.clear();
		group_members_lms.clear();
		question_states_lms.clear();
		forum_posts_lms.clear();
		role_lms.clear();
		role_assignments_lms.clear();
		assignment_submission_lms.clear();
	}
	
//methods for create and fill the mining-table instances
    
    public HashMap<Long, CourseUserMining> generateCourseUserMining(){
    	
    	HashMap<Long, CourseUserMining> course_user_mining = new HashMap<Long, CourseUserMining>();
    	    	
    	for (Iterator<Context_LMS> iter = context_lms.iterator(); iter.hasNext(); ) {
       		Context_LMS loadedItem = iter.next();
       		if(loadedItem.getContextlevel() == 50){
       			for (Iterator<Role_assignments_LMS> iter2 = role_assignments_lms.iterator(); iter2.hasNext(); ) {
       	       		Role_assignments_LMS loadedItem2 = iter2.next();
       	       		if(loadedItem2.getContextid() == loadedItem.getId()){
       	       			CourseUserMining insert = new CourseUserMining();
       	       			insert.setId(loadedItem2.getId());
       	       			insert.setRole(loadedItem2.getRoleid(), role_mining, old_role_mining);
       	       			
       	       			if(!numericUserId)
       	       			{
	       	    			long id = -1;
	       	       			if(id_mapping.get(loadedItem2.getUserid()) != null)
	       	       			{
	       	       				id = id_mapping.get(loadedItem2.getUserid()).getId();
	       	       				insert.setUser(id, user_mining, old_user_mining);
	       	       			}
	       	       			if(id == -1 && old_id_mapping.get(loadedItem2.getUserid()) != null)
	       	       			{
	       	       				id = old_id_mapping.get(loadedItem2.getUserid()).getId();
	       	       				insert.setUser(id, user_mining, old_user_mining);
	       	       			}
	       	       			if(id == -1 )
	       	       			{
	       	       				id = largestId + 1;
	       	       				largestId = id;
	       	       				id_mapping.put(loadedItem2.getUserid(), new IDMappingMining(id, loadedItem2.getUserid(), "Moodle19"));
	       	       				insert.setUser(id, user_mining, old_user_mining);
	       	       			}
	       	       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
	       	       				largestId = insert.getUser().getId();
       	       			}
       	       			insert.setEnrolstart(loadedItem2.getTimestart());
       	       			insert.setEnrolend(loadedItem2.getTimeend());
       	                insert.setCourse(loadedItem.getInstanceid(), course_mining, old_course_mining);
       	       			if(insert.getUser()!= null && insert.getCourse() != null && insert.getRole()!= null){       	       			
       	       				course_user_mining.put(insert.getId(), insert);
       	       			}    	       			
       	       		}
       			}   			
       		}
    	}
    	
		return course_user_mining;
    }
    
    
    public HashMap<Long, CourseForumMining> generateCourseForumMining(){
    	
    	HashMap<Long, CourseForumMining> course_forum_mining = new HashMap<Long, CourseForumMining>();
    	    	
    	for (Iterator<Forum_LMS> iter = forum_lms.iterator(); iter.hasNext(); ) {
    		Forum_LMS loadedItem = iter.next();
    		CourseForumMining insert = new CourseForumMining();
            insert.setId(loadedItem.getId());
            insert.setCourse(loadedItem.getCourse(),course_mining, old_course_mining);
            insert.setForum(loadedItem.getId(),forum_mining, old_forum_mining);
            if(insert.getCourse()!= null && insert.getForum()!= null){
            	course_forum_mining.put(insert.getId(), insert);
            }
        }
		return course_forum_mining;
    }
    
    
    public HashMap<Long, CourseMining> generateCourseMining() {
    	
    	HashMap<Long, CourseMining> course_mining = new HashMap<Long, CourseMining>();
       	for (Iterator<Course_LMS> iter = course_lms.iterator(); iter.hasNext(); ) {
        	Course_LMS loadedItem = iter.next();
        	CourseMining insert = new CourseMining();
        	insert.setId(loadedItem.getId());
        	insert.setStartdate(loadedItem.getStartdate());
        	insert.setEnrolstart(loadedItem.getEnrolstartdate());
        	insert.setEnrolend(loadedItem.getEnrolenddate());
        	insert.setTimecreated(loadedItem.getTimecreated());
        	insert.setTimemodified(loadedItem.getTimemodified());
        	insert.setTitle(loadedItem.getFullname());
        	insert.setShortname(loadedItem.getShortname());
        	course_mining.put(insert.getId(), insert);
        }
		return course_mining;
    }    
    
    
	public HashMap<Long, CourseGroupMining> generateCourseGroupMining(){
    	
		HashMap<Long, CourseGroupMining> course_group_mining = new HashMap<Long, CourseGroupMining>();
    	    	
    	for (Iterator<Groups_LMS> iter = group_lms.iterator(); iter.hasNext(); ) {
    		Groups_LMS loadedItem = iter.next();
    		CourseGroupMining insert = new CourseGroupMining();
            insert.setId(loadedItem.getId());
            insert.setGroup(loadedItem.getId(),group_mining, old_group_mining);
            insert.setCourse(loadedItem.getCourseid(),course_mining, old_course_mining);
            if(insert.getCourse()!= null && insert.getGroup()!= null){
            	course_group_mining.put(insert.getId(), insert);
            }
        }
		return course_group_mining;
    }
    
    
    public HashMap<Long, CourseQuizMining> generateCourseQuizMining(){
    	
    	HashMap<Long, CourseQuizMining> course_quiz_mining = new HashMap<Long, CourseQuizMining>();
    	    	
    	for (Iterator<Quiz_LMS> iter = quiz_lms.iterator(); iter.hasNext(); ) {
    		Quiz_LMS loadedItem = iter.next();
    		CourseQuizMining insert = new CourseQuizMining();
    		insert.setId(loadedItem.getId());
    		insert.setCourse(loadedItem.getCourse(),course_mining, old_course_mining);
    		insert.setQuiz(loadedItem.getId(), quiz_mining, old_quiz_mining);
    		if(insert.getCourse() != null && insert.getQuiz() != null){            
    			course_quiz_mining.put(insert.getId(), insert);
    		}
    		if(insert.getQuiz() == null){
    			logger.info("In Course_quiz_mining, quiz(quiz) not found: " + loadedItem.getId());
	    		}   
        }    	
		return course_quiz_mining;
    }

   public HashMap<Long, CourseAssignmentMining> generateCourseAssignmentMining(){
    	
	   HashMap<Long, CourseAssignmentMining> course_assignment_mining = new HashMap<Long, CourseAssignmentMining>();
    	    	   	
    	for (Iterator<Assignment_LMS> iter = assignment_lms.iterator(); iter.hasNext(); ) {
    		Assignment_LMS loadedItem = iter.next();
    		CourseAssignmentMining insert = new CourseAssignmentMining();
    		insert.setId(loadedItem.getId());
            insert.setCourse(loadedItem.getCourse(),course_mining, old_course_mining);
			if(insert.getCourse()==null){
				logger.info("course not found for course-assignment: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
			}
            insert.setAssignment(loadedItem.getId(), assignment_mining, old_assignment_mining);
            if(insert.getCourse()!= null && insert.getAssignment() != null){            
            	course_assignment_mining.put(insert.getId(), insert);
            }
    		if(insert.getAssignment()==null){
    			logger.info("In Course_assignment_mining, assignment not found: " + loadedItem.getId());
	    		}   
        }
		return course_assignment_mining;
    }
   
   public HashMap<Long, CourseScormMining> generateCourseScormMining(){
   	
	   	HashMap<Long, CourseScormMining> course_scorm_mining = new HashMap<Long, CourseScormMining>();   	    	
	   	for (Iterator<Scorm_LMS> iter = scorm_lms.iterator(); iter.hasNext(); ) {
	   		Scorm_LMS loadedItem = iter.next();
	   		CourseScormMining insert = new CourseScormMining();
	   		insert.setId(loadedItem.getId());
	   		insert.setCourse(loadedItem.getCourse(),course_mining, old_course_mining);
	   		insert.setScorm(loadedItem.getId(), scorm_mining, old_scorm_mining);
	   		if(insert.getCourse()!= null && insert.getScorm() != null){            
	   			course_scorm_mining.put(insert.getId(), insert);
	   		}
	   		if(insert.getScorm()==null){
	   			logger.info("In Course_scorm_mining, scorm not found: " + loadedItem.getId());
	   		}      	
	   	}
			return course_scorm_mining;
   }
    
    public HashMap<Long, CourseResourceMining> generateCourseResourceMining(){
    	
    	HashMap<Long, CourseResourceMining> course_resource_mining = new HashMap<Long, CourseResourceMining>();
    	    	
    	for (Iterator<Resource_LMS> iter = resource_lms.iterator(); iter.hasNext(); ) {
    		Resource_LMS loadedItem = iter.next();
    		CourseResourceMining insert = new CourseResourceMining();
            insert.setId(loadedItem.getId());
            insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
            insert.setResource(loadedItem.getId(), resource_mining, old_resource_mining);
            if(insert.getCourse()!= null && insert.getResource() != null){  
            	course_resource_mining.put(insert.getId(), insert);
            }
        }
		return course_resource_mining;
    }
    
    
    public HashMap<Long, CourseLogMining> generateCourseLogMining(){
    	HashMap<Long, CourseLogMining> course_log_mining = new HashMap<Long, CourseLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	
    	for (Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) {
    		Log_LMS loadedItem = iter.next();
    		
    		 long uid = -1;
             
             if(id_mapping.get(loadedItem.getUserid()) != null)
     			uid = id_mapping.get(loadedItem.getUserid()).getId();
     		if(uid == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
     			uid = old_id_mapping.get(loadedItem.getUserid()).getId();
     		
     		//Creates a list of time stamps for every user indicating requests
     		//We later use this lists to determine the time a user accessed a resource
             if(users.get(uid) == null)
         	{
             	//if the user isn't already listed, create a new key
         		ArrayList<Long> times = new ArrayList<Long>();
         		times.add(loadedItem.getTime());
         		users.put(uid, times);
         	}
         	else
         	{
         		ArrayList<Long> times = users.get(uid);
         		if(loadedItem.getAction() == "login")
         			times.add(0L);
         		if(!times.contains(loadedItem.getTime()))
         			times.add(loadedItem.getTime());
         		users.put(uid, times);
         	}
    		
    		if(loadedItem.getModule().equals("course")){    		
    			CourseLogMining insert = new CourseLogMining();
    			insert.setId(loadedItem.getId());
    			insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
    			
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);

	       			}
	       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
	       				insert.setUser(id, user_mining, old_user_mining);
	       			}
	       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
	       				largestId = insert.getUser().getId();
    			}
    			
    			if(insert.getUser() != null && insert.getUser().getId() > largestId)
    				largestId = insert.getUser().getId();
    			insert.setAction(loadedItem.getAction());
    			insert.setTimestamp(loadedItem.getTime());
    			if(insert.getUser() != null && insert.getCourse() != null)
    				course_log_mining.put(insert.getId(), insert);
    			
    		}
        }
    	
    	for( Iterator<CourseLogMining> iter = course_log_mining.values().iterator(); iter.hasNext();)
        {
        	CourseLogMining r = iter.next();
        	if(r.getUser() != null)
        	{	
	        	long duration = -1;
	        	ArrayList<Long> times = users.get(r.getUser().getId());
	        
	        	int pos = times.indexOf(r.getTimestamp());
	        	if( pos > -1 && pos < times.size()-1)
	        		if(times.get(pos + 1) != 0)
	        			duration = times.get(pos + 1) - times.get(pos);
	        	//All duration that are longer than one hour are cut to an hour
	        	if(duration > 3600)
	        		duration = 3600;
	        	r.setDuration(duration);
        	}
        }
    	
		return course_log_mining;
    }
    
    
    public HashMap<Long, CourseWikiMining> generateCourseWikiMining(){
    	
    	HashMap<Long, CourseWikiMining> course_wiki_mining = new HashMap<Long, CourseWikiMining>();
    	    	
    	for (Iterator<Wiki_LMS> iter = wiki_lms.iterator(); iter.hasNext(); ) {
    		Wiki_LMS loadedItem = iter.next();
    		CourseWikiMining insert = new CourseWikiMining();
            insert.setId(loadedItem.getId());
            insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
            insert.setWiki(loadedItem.getId(), wiki_mining, old_wiki_mining);
            if(insert.getCourse()!= null && insert.getWiki()!= null){
            	course_wiki_mining.put(insert.getId(), insert);
            }
        }
		return course_wiki_mining;
    }

    
    public HashMap<Long, ForumLogMining> generateForumLogMining() {
    	HashMap<Long, ForumLogMining> forum_log_mining = new HashMap<Long, ForumLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	    		
    	for ( Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) {
    		Log_LMS loadedItem = iter.next();
    		
    		 long uid = -1;
             
             if(id_mapping.get(loadedItem.getUserid()) != null)
     			uid = id_mapping.get(loadedItem.getUserid()).getId();
     		if(uid == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
     			uid = old_id_mapping.get(loadedItem.getUserid()).getId();
     		
     		//Creates a list of time stamps for every user indicating requests
     		//We later use this lists to determine the time a user accessed a resource
             if(users.get(uid) == null)
         	{
             	//if the user isn't already listed, create a new key
         		ArrayList<Long> times = new ArrayList<Long>();
         		times.add(loadedItem.getTime());
         		users.put(uid, times);
         	}
         	else
         	{
         		ArrayList<Long> times = users.get(uid);
         		if(loadedItem.getAction() == "login")
         			times.add(0L);
         		if(!times.contains(loadedItem.getTime()))
         			times.add(loadedItem.getTime());
         		users.put(uid, times);
         	}
    		
    		
    		if(loadedItem.getModule().equals("forum")){
    			ForumLogMining insert = new ForumLogMining();
    			insert.setId(loadedItem.getId());
    			
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);
	       			}
	       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
	       				insert.setUser(id, user_mining, old_user_mining);
	       			}
	       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
	       				largestId = insert.getUser().getId();
    			}
    			if((loadedItem.getAction().equals("view forum") || loadedItem.getAction().equals("subscribe")) && loadedItem.getInfo().matches("[0-9]+")){
    				insert.setForum(Long.valueOf(loadedItem.getInfo()), forum_mining, old_forum_mining);
    			}
    			else{
    				if((loadedItem.getAction().equals("add discussion") || loadedItem.getAction().equals("view discussion")) && loadedItem.getInfo().matches("[0-9]+")){
    					for ( Iterator<Forum_discussions_LMS> iter2 = forum_discussions_lms.iterator(); iter2.hasNext(); ) 
    					{
    			    		Forum_discussions_LMS loadedItem2 = iter2.next();
    			    		if(loadedItem2.getId() == Long.valueOf(loadedItem.getInfo()))
    			    		{
    			    			insert.setForum(loadedItem2.getForum(), forum_mining, old_forum_mining);
    			    			break;
    			    		}
    					}
    				}
    			}
    			insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
    			insert.setAction(loadedItem.getAction());
   				for (Iterator<Forum_posts_LMS> iter2 = forum_posts_lms.iterator(); iter2.hasNext(); ) {
    					Forum_posts_LMS loadedItem2 = iter2.next();
    					if(loadedItem2.getUserid() == loadedItem.getUserid()&&(loadedItem2.getCreated() == loadedItem.getTime()||loadedItem2.getModified() == loadedItem.getTime())){
    						insert.setMessage(loadedItem2.getMessage());
    						insert.setSubject(loadedItem2.getSubject());
    						break;
    					}
    			}
    			insert.setTimestamp(loadedItem.getTime());
    			if(insert.getCourse() != null && insert.getForum() != null && insert.getUser() != null)
    				forum_log_mining.put(insert.getId(), insert);
    			
    		}
    	}
    	
    	for( Iterator<ForumLogMining> iter = forum_log_mining.values().iterator(); iter.hasNext();)
        {
        	ForumLogMining r = iter.next();
        	if(r.getUser() != null)
        	{	
	        	long duration = -1;
	        	ArrayList<Long> times = users.get(r.getUser().getId());
	        
	        	int pos = times.indexOf(r.getTimestamp());
	        	if( pos > -1 && pos < times.size()-1)
	        		if(times.get(pos + 1) != 0)
	        			duration = times.get(pos + 1) - times.get(pos);
	        	//All duration that are longer than one hour are cut to an hour
	        	if(duration > 3600)
	        		duration = 3600;
	        	r.setDuration(duration);
        	}
        }
    	return forum_log_mining;    	
    } 
    
    
    public HashMap<Long, ForumMining> generateForumMining() {
    	
    	HashMap<Long, ForumMining> forum_mining = new HashMap<Long, ForumMining>();
	
    	for ( Iterator<Forum_LMS> iter = forum_lms.iterator(); iter.hasNext(); ) {
   	    	Forum_LMS loadedItem = iter.next();
   	    	ForumMining insert = new ForumMining();
    	   	insert.setId(loadedItem.getId());
            insert.setTimemodified(loadedItem.getTimemodified());
   	    	insert.setTitle(loadedItem.getName());
   	    	insert.setSummary(loadedItem.getIntro());   
    		forum_mining.put(insert.getId(), insert);  
    	} 
    	
       	for ( Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) {
           	Log_LMS loadedItem = iter.next();
           	if(forum_mining.get(loadedItem.getCmid()) != null && (forum_mining.get(loadedItem.getCmid()).getTimecreated() == 0 || forum_mining.get(loadedItem.getCmid()).getTimecreated() > loadedItem.getTime()))
           	{           		
           		forum_mining.get(loadedItem.getCmid()).setTimecreated(loadedItem.getTime());
       		}
       	}   
		return forum_mining;
    }

    
	public HashMap<Long, GroupUserMining> generateGroupUserMining(){
    	
		HashMap<Long, GroupUserMining> group_members_mining = new HashMap<Long, GroupUserMining>();
    	    	
    	for (Iterator<Groups_members_LMS> iter = group_members_lms.iterator(); iter.hasNext(); ) {
    		Groups_members_LMS loadedItem = iter.next();
    		GroupUserMining insert = new GroupUserMining();
            insert.setId(loadedItem.getId());
           	insert.setGroup(loadedItem.getGroupid(), group_mining, old_group_mining);
           	
			if(!numericUserId)
			{
    			long id = -1;
       			if(id_mapping.get(loadedItem.getUserid()) != null)
       			{
       				id = id_mapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id, user_mining, old_user_mining);
       			}
       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
       			{
       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id, user_mining, old_user_mining);

       			}
       			if(id == -1 )
       			{
       				id = largestId + 1;
       				largestId = id;
       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
       				insert.setUser(id, user_mining, old_user_mining);
       			}
       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
       				largestId = insert.getUser().getId();
			}
           	insert.setTimestamp(loadedItem.getTimeadded());
           	if(insert.getUser() != null && insert.getGroup() != null)
           		group_members_mining.put(insert.getId(), insert);
        }
		return group_members_mining;
    }
	
	
    public HashMap<Long, GroupMining> generateGroupMining(){
    	
    	HashMap<Long, GroupMining> group_mining = new HashMap<Long, GroupMining>();
    	    	
    	for (Iterator<Groups_LMS> iter = group_lms.iterator(); iter.hasNext(); ) {
    		Groups_LMS loadedItem = iter.next();
    		GroupMining insert = new GroupMining();
           	insert.setId(loadedItem.getId());
           	insert.setTimecreated(loadedItem.getTimecreated());
           	insert.setTimemodified(loadedItem.getTimemodified());
           	group_mining.put(insert.getId(), insert);
        }
		return group_mining;    	
    }
    
    
    public HashMap<Long, QuestionLogMining> generateQuestionLogMining(){
    	HashMap<Long, QuestionLogMining> question_log_mining = new HashMap<Long, QuestionLogMining>();
    	HashMap<Long, Long> timestampIdMap = new HashMap<Long,Long>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	    	
    	for (Iterator<Question_states_LMS> iter = question_states_lms.iterator(); iter.hasNext(); ) {
    		Question_states_LMS loadedItem = iter.next();
    		QuestionLogMining insert = new QuestionLogMining();
    		insert.setId(loadedItem.getId()); //ID
			insert.setQuestion(loadedItem.getQuestion(), question_mining, old_question_mining); //Question 
			insert.setPenalty(loadedItem.getPenalty());	
			insert.setAnswers(loadedItem.getAnswer());
			insert.setTimestamp(loadedItem.getTimestamp());			
			
            switch(loadedItem.getEvent())
            {            
	            case 0:   
	            	insert.setAction("OPEN");break;
	            case 1:	
	            	insert.setAction("NAVIGATE");break;
	            case 2:	
	            	insert.setAction("SAVE");break;
	            case 3:	
	            	insert.setAction("GRADE");break;
	            case 4:	
	            	insert.setAction("DUPLICATE");break;
	            case 5:	
	            	insert.setAction("VALIDATE");break;
	            case 6:	
	            	insert.setAction("CLOSEANDGRADE");break;
	            case 7:	
	            	insert.setAction("SUBMIT");break;
	            case 8:	
	            	insert.setAction("CLOSE");break;
	            case 9:	
	            	insert.setAction("MANUALGRADE");break;
	            default:	
	            	insert.setAction("UNKNOWN");
            }
			
			//Set quiz type
			for(Iterator<Quiz_question_instances_LMS> iter1 = quiz_question_instances_lms.iterator(); iter1.hasNext(); ){
				Quiz_question_instances_LMS loadedItem1 = iter1.next();
				if(loadedItem1.getQuestion() == (loadedItem.getQuestion())){
					insert.setQuiz(loadedItem1.getQuiz(), quiz_mining, old_quiz_mining);
					break;
				}
			}			
			if(insert.getQuiz() != null)
				for(Iterator<QuizQuestionMining> iter1 = old_quiz_question_mining.values().iterator(); iter1.hasNext(); ){
					QuizQuestionMining loadedItem1 = iter1.next();
					if(loadedItem1.getQuestion().getId() == (loadedItem.getQuestion())){
						insert.setQuiz(loadedItem1.getQuiz());//Quiz
						break;
					}
				}	
			
			//Set Type
			for(Iterator<Question_LMS> iter2 = question_lms.iterator(); iter2.hasNext(); ){
				Question_LMS loadedItem2 = iter2.next();
				if(loadedItem2.getId() == (loadedItem.getQuestion())){
					insert.setType(loadedItem2.getQtype());//Type
					break;
				}
			}			
			if(insert.getType() == null && old_question_mining.get(loadedItem.getQuestion()) != null)
				insert.setType(old_question_mining.get(loadedItem.getQuestion()).getType());			
			if(insert.getType() == null){
				logger.info("In Question_log_mining, type not found for question_states: " + loadedItem.getId() +" and question: " + loadedItem.getQuestion() +" question list size: "+ question_lms.size() );
			}  	
							
			//Set Grades
			if(loadedItem.getEvent() == 3 || loadedItem.getEvent() == 6 || loadedItem.getEvent() == 9){
				insert.setRawgrade(loadedItem.getRaw_grade());
				insert.setFinalgrade(loadedItem.getGrade());
			}			

			if(insert.getQuestion() != null && insert.getQuiz() != null)
			{
				timestampIdMap.put(insert.getTimestamp(), insert.getId());
    			question_log_mining.put(insert.getId(), insert);
			}			
    	}	  
    	
    	//Set Course and 
    	for(Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ){
    		Log_LMS loadedItem = iter.next();
    		
    		 long uid1 = -1;
             
             if(id_mapping.get(loadedItem.getUserid()) != null)
     			uid1 = id_mapping.get(loadedItem.getUserid()).getId();
     		if(uid1 == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
     			uid1 = old_id_mapping.get(loadedItem.getUserid()).getId();
     		
     		//Creates a list of time stamps for every user indicating requests
     		//We later use this lists to determine the time a user accessed a resource
             if(users.get(uid1) == null)
         	{
             	//if the user isn't already listed, create a new key
         		ArrayList<Long> times = new ArrayList<Long>();
         		times.add(loadedItem.getTime());
         		users.put(uid1, times);
         	}
         	else
         	{
         		ArrayList<Long> times = users.get(uid1);
         		if(loadedItem.getAction() == "login")
         			times.add(0L);
         		if(!times.contains(loadedItem.getTime()))
         			times.add(loadedItem.getTime());
         		users.put(uid1, times);
         	}
    		
    		if(timestampIdMap.get(loadedItem.getTime()) != null && loadedItem.getModule().equals("quiz")){
    			long uid = -1;
    			if(id_mapping.get(loadedItem.getUserid()) != null)
    				uid = id_mapping.get(loadedItem.getUserid()).getId();
    			else if(old_id_mapping.get(loadedItem.getUserid()) != null)
    				uid = old_id_mapping.get(loadedItem.getUserid()).getId();
    			if(uid != -1)
    			{
    				question_log_mining.get(timestampIdMap.get(loadedItem.getTime())).setUser(uid, user_mining, old_user_mining);
    				question_log_mining.get(timestampIdMap.get(loadedItem.getTime())).setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
    			}
    			if(question_log_mining.get(timestampIdMap.get(loadedItem.getTime())).getCourse() == null || question_log_mining.get(timestampIdMap.get(loadedItem.getTime())).getUser() == null)
    				question_log_mining.remove(timestampIdMap.get(loadedItem.getTime()));
    		}	
		}    	
    	
    	for( Iterator<QuestionLogMining> iter = question_log_mining.values().iterator(); iter.hasNext();)
        {
        	QuestionLogMining r = iter.next();
        	if(r.getUser() != null)
        	{	
	        	long duration = -1;
	        	ArrayList<Long> times = users.get(r.getUser().getId());
	        
	        	int pos = times.indexOf(r.getTimestamp());
	        	if( pos > -1 && pos < times.size()-1)
	        		if(times.get(pos + 1) != 0)
	        			duration = times.get(pos + 1) - times.get(pos);
	        	//All duration that are longer than one hour are cut to an hour
	        	if(duration > 3600)
	        		duration = 3600;
	        	r.setDuration(duration);
        	}
        }
		return question_log_mining;
    }    
    
    public HashMap<Long, QuizLogMining> generateQuizLogMining(){
    	HashMap<Long, QuizLogMining> quiz_log_mining = new HashMap<Long, QuizLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	    	
    	for (Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) {
    		Log_LMS loadedItem = iter.next();
    		
    		 long uid = -1;
             
             if(id_mapping.get(loadedItem.getUserid()) != null)
     			uid = id_mapping.get(loadedItem.getUserid()).getId();
     		if(uid == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
     			uid = old_id_mapping.get(loadedItem.getUserid()).getId();
     		
     		//Creates a list of time stamps for every user indicating requests
     		//We later use this lists to determine the time a user accessed a resource
             if(users.get(uid) == null)
         	{
             	//if the user isn't already listed, create a new key
         		ArrayList<Long> times = new ArrayList<Long>();
         		times.add(loadedItem.getTime());
         		users.put(uid, times);
         	}
         	else
         	{
         		ArrayList<Long> times = users.get(uid);
         		if(loadedItem.getAction() == "login")
         			times.add(0L);
         		if(!times.contains(loadedItem.getTime()))
         			times.add(loadedItem.getTime());
         		users.put(uid, times);
         	}
    		
    		
//insert quiz in quiz_log
    		if(loadedItem.getModule().equals("quiz")){
    			QuizLogMining insert = new QuizLogMining();
    		    insert.setId(loadedItem.getId());
    			insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
    			
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);
	       			
	       			}
	       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);
	       			
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
	       				insert.setUser(id, user_mining, old_user_mining);
	       			}
	       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
	       				largestId = insert.getUser().getId();
    			}
    			
    			
    			if(loadedItem.getInfo().matches("[0-9]+")){
    				insert.setQuiz(Long.valueOf(loadedItem.getInfo()), quiz_mining, old_quiz_mining);
    			}
    			insert.setAction(loadedItem.getAction());
    			insert.setTimestamp(loadedItem.getTime());
    			if(insert.getQuiz() != null && insert.getUser() != null && loadedItem.getAction()!="review"){    
    				for (Iterator<Quiz_grades_LMS> iter2 = quiz_grades_lms.iterator(); iter2.hasNext(); ) {
    					Quiz_grades_LMS loadedItem2 = iter2.next();
    					
    					long id = -1;
    	    			if(!numericUserId)
    	    			{
    		    			
    		       			if(id_mapping.get(loadedItem.getUserid()) != null)
    		       			{
    		       				id = id_mapping.get(loadedItem.getUserid()).getId();
    		       			
    		       			}
    		       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
    		       			{
    		       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
    		       			
    		       			}
    	    			}
    					if(loadedItem2.getQuiz() == insert.getQuiz().getId() && id == insert.getUser().getId() && loadedItem2.getTimemodified() == insert.getTimestamp()){
    						insert.setGrade(loadedItem2.getGrade());
    					}
    				}
    			}
    			if(insert.getQuiz() == null && !(loadedItem.getAction().equals("view all"))){
    				logger.info("In Quiz_log_mining, quiz(quiz) not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid()+ " and info: " + loadedItem.getInfo()+ " and action: " + loadedItem.getAction());
    			}
    			if(insert.getUser() == null){
    				logger.info("In Quiz_log_mining(quiz), user not found for log: " + loadedItem.getId() + " and user: " + loadedItem.getUserid());
    			}
    			if(insert.getCourse() == null){
    				logger.info("In Quiz_log_mining(quiz), course not found for log: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
    			}
    			if(insert.getCourse() != null && insert.getQuiz() != null && insert.getUser() != null)
    				quiz_log_mining.put(insert.getId(), insert);
    			
    		}          	
       	}
    	
    	for( Iterator<QuizLogMining> iter = quiz_log_mining.values().iterator(); iter.hasNext();)
        {
        	QuizLogMining r = iter.next();
        	if(r.getUser() != null)
        	{	
	        	long duration = -1;
	        	ArrayList<Long> times = users.get(r.getUser().getId());
	        
	        	int pos = times.indexOf(r.getTimestamp());
	        	if( pos > -1 && pos < times.size()-1)
	        		if(times.get(pos + 1) != 0)
	        			duration = times.get(pos + 1) - times.get(pos);
	        	//All duration that are longer than one hour are cut to an hour
	        	if(duration > 3600)
	        		duration = 3600;
	        	r.setDuration(duration);
        	}
        }
		return quiz_log_mining;
    }

    public HashMap<Long, AssignmentLogMining> generateAssignmentLogMining(){
	  
    	HashMap<Long, AssignmentLogMining> assignment_log_mining = new HashMap<Long, AssignmentLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	
    	for(Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) {
    		Log_LMS loadedItem = iter.next();
    		
    		 long uid = -1;
             
             if(id_mapping.get(loadedItem.getUserid()) != null)
     			uid = id_mapping.get(loadedItem.getUserid()).getId();
     		if(uid == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
     			uid = old_id_mapping.get(loadedItem.getUserid()).getId();
     		
     		//Creates a list of time stamps for every user indicating requests
     		//We later use this lists to determine the time a user accessed a resource
             if(users.get(uid) == null)
         	{
             	//if the user isn't already listed, create a new key
         		ArrayList<Long> times = new ArrayList<Long>();
         		times.add(loadedItem.getTime());
         		users.put(uid, times);
         	}
         	else
         	{
         		ArrayList<Long> times = users.get(uid);
         		if(loadedItem.getAction() == "login")
         			times.add(0L);
         		if(!times.contains(loadedItem.getTime()))
         			times.add(loadedItem.getTime());
         		users.put(uid, times);
         	}
   	
//insert assignments in assignment_log
		if(loadedItem.getModule().equals("assignment") && loadedItem.getInfo().matches("[0-9]++")){
			AssignmentLogMining insert = new AssignmentLogMining();
		    insert.setId(loadedItem.getId());
			insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
			
			if(!numericUserId)
			{
    			long id = -1;
       			if(id_mapping.get(loadedItem.getUserid()) != null)
       			{
       				id = id_mapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id, user_mining, old_user_mining);	       			
       			}
       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
       			{
       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id, user_mining, old_user_mining);	       		
       			}
       			if(id == -1 )
       			{
       				id = largestId + 1;
       				largestId = id;
       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
       				insert.setUser(id, user_mining, old_user_mining);
       			}
       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
       				largestId = insert.getUser().getId();
			}    			
			insert.setAction(loadedItem.getAction());
			insert.setTimestamp(loadedItem.getTime());
			insert.setAssignment(Long.valueOf(loadedItem.getInfo()), assignment_mining, old_assignment_mining);
			
			if(insert.getAssignment() != null && insert.getUser() != null && insert.getAction().equals("upload")){    
				for (Iterator<Assignment_submissions_LMS> iter2 = assignment_submission_lms.iterator(); iter2.hasNext(); ) {
					Assignment_submissions_LMS loadedItem2 = iter2.next();
					
					long id = -1;
	    			if(!numericUserId)
	    			{
		       			if(id_mapping.get(loadedItem2.getUserid()) != null)
		       			{
		       				id = id_mapping.get(loadedItem2.getUserid()).getId();
		       			}
		       			if(id == -1 && old_id_mapping.get(loadedItem2.getUserid()) != null)
		       			{
		       				id = old_id_mapping.get(loadedItem2.getUserid()).getId();
		       			}
	    			}
					if(loadedItem2.getAssignment() == insert.getAssignment().getId() && id == insert.getUser().getId() && loadedItem2.getTimemodified() == insert.getTimestamp()){
					{
							insert.setGrade(loadedItem2.getGrade());
							insert.setFinalgrade(loadedItem2.getGrade());
						}
					}
				}
			}
			
			
			if(insert.getAssignment()==null){
	    		logger.info("In Assignment_log_mining, assignment not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid()+ " and info: " + loadedItem.getInfo());
	    	}
			if(insert.getCourse()==null){
				logger.info("In Assignment_log_mining, course not found for log: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
			}    			
			if(insert.getUser()==null){
				logger.info("In Assignment_log_mining, user not found for log: " + loadedItem.getId() +" and user: " + loadedItem.getUserid());
			}    			
			if(insert.getUser() != null && insert.getAssignment() != null && insert.getCourse() != null)
				assignment_log_mining.put(insert.getId(), insert);
		}
		
    }

	  
/*	  
    	for (Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) {
    		Log_LMS loadedItem = iter.next();
       	
//insert assignments in assignment_log
    		if(loadedItem.getInfo().matches("[0-9]+")){
    			AssignmentLogMining insert = new AssignmentLogMining();
    		    insert.setId(loadedItem.getId());
    			insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
    			
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);	       			
	       			}
	       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);	       		
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
	       				insert.setUser(id, user_mining, old_user_mining);
	       			}
	       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
	       				largestId = insert.getUser().getId();
    			}    			
    			insert.setAction(loadedItem.getAction());
    			insert.setTimestamp(loadedItem.getTime());

    			if(insert.getAction().equals("submit"))
    				;
    			
    			insert.setAssignment(Long.valueOf(loadedItem.getInfo()), assignment_mining, old_assignment_mining);
    			if(insert.getAssignment()==null){
    	    		logger.info("In Assignment_log_mining, assignment not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid()+ " and info: " + loadedItem.getInfo());
    	    	}
    			if(insert.getCourse()==null){
    				logger.info("In Assignment_log_mining, course not found for log: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
    			}    			
    			if(insert.getUser()==null){
    				logger.info("In Assignment_log_mining, user not found for log: " + loadedItem.getId() +" and user: " + loadedItem.getUserid());
    			}    			
    			if(insert.getUser() != null && insert.getAssignment() != null && insert.getCourse() != null)
    				assignment_log_mining.put(insert.getId(), insert);
    		}
    		
        }
        */
    	
    	
    	for( Iterator<AssignmentLogMining> iter = assignment_log_mining.values().iterator(); iter.hasNext();)
        {
        	AssignmentLogMining r = iter.next();
        	if(r.getUser() != null)
        	{	
	        	long duration = -1;
	        	ArrayList<Long> times = users.get(r.getUser().getId());
	        
	        	int pos = times.indexOf(r.getTimestamp());
	        	if( pos > -1 && pos < times.size()-1)
	        		if(times.get(pos + 1) != 0)
	        			duration = times.get(pos + 1) - times.get(pos);
	        	//All duration that are longer than one hour are cut to an hour
	        	if(duration > 3600)
	        		duration = 3600;
	        	r.setDuration(duration);
        	}
        }
    	
		return assignment_log_mining;
    }

  public HashMap<Long, ScormLogMining> generateScormLogMining(){
	  HashMap<Long, ScormLogMining> scorm_log_mining = new HashMap<Long, ScormLogMining>();
	  HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
  	    	
  	for (Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) {
  		Log_LMS loadedItem = iter.next();
  		
  		 long uid = -1;
         
         if(id_mapping.get(loadedItem.getUserid()) != null)
 			uid = id_mapping.get(loadedItem.getUserid()).getId();
 		if(uid == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
 			uid = old_id_mapping.get(loadedItem.getUserid()).getId();
 		
 		//Creates a list of time stamps for every user indicating requests
 		//We later use this lists to determine the time a user accessed a resource
         if(users.get(uid) == null)
     	{
         	//if the user isn't already listed, create a new key
     		ArrayList<Long> times = new ArrayList<Long>();
     		times.add(loadedItem.getTime());
     		users.put(uid, times);
     	}
     	else
     	{
     		ArrayList<Long> times = users.get(uid);
     		if(loadedItem.getAction() == "login")
     			times.add(0L);
     		if(!times.contains(loadedItem.getTime()))
     			times.add(loadedItem.getTime());
     		users.put(uid, times);
     	}
  		
  		
//insert scorm in scorm_log
  		if(loadedItem.getModule().equals("scorm")){
  			ScormLogMining insert = new ScormLogMining();
  		    insert.setId(loadedItem.getId());
  			insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
  			
			if(!numericUserId)
			{
    			long id = -1;
       			if(id_mapping.get(loadedItem.getUserid()) != null)
       			{
       				id = id_mapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id, user_mining, old_user_mining);
       			
       			}
       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
       			{
       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id, user_mining, old_user_mining);
       		
       			}
       			if(id == -1 )
       			{
       				id = largestId + 1;
       				largestId = id;
       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
       				insert.setUser(id, user_mining, old_user_mining);
       			}
       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
       				largestId = insert.getUser().getId();
			}
  			insert.setAction(loadedItem.getAction());
  			insert.setTimestamp(loadedItem.getTime());
  			if(loadedItem.getInfo().matches("[0-9]+")){
  				insert.setScorm(Long.valueOf(loadedItem.getInfo()), scorm_mining, old_scorm_mining);
  			}
  			if(insert.getScorm() != null && insert.getCourse() != null && insert.getUser() != null)
  				scorm_log_mining.put(insert.getId(), insert);
  			if(insert.getScorm()==null){
  	    		logger.info("In Scorm_log_mining, scorm package not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid()+ " and info: " + loadedItem.getInfo());
  	    	}
  			if(insert.getCourse()==null){
  				logger.info("In Scorm_log_mining(scorm part), course not found for log: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
  			}    			
  			if(insert.getUser()==null){
  				logger.info("In Scorm_log_mining(scorm part), user not found for log: " + loadedItem.getId() +" and user: " + loadedItem.getUserid());
  			}     		
  		}
      }
  	for( Iterator<ScormLogMining> iter = scorm_log_mining.values().iterator(); iter.hasNext();)
    {
    	ScormLogMining r = iter.next();
    	if(r.getUser() != null)
    	{	
        	long duration = -1;
        	ArrayList<Long> times = users.get(r.getUser().getId());
        
        	int pos = times.indexOf(r.getTimestamp());
        	if( pos > -1 && pos < times.size()-1)
        		if(times.get(pos + 1) != 0)
        			duration = times.get(pos + 1) - times.get(pos);
        	//All duration that are longer than one hour are cut to an hour
        	if(duration > 3600)
        		duration = 3600;
        	r.setDuration(duration);
    	}
    }
  	
		return scorm_log_mining;
  }
  
    public HashMap<Long, QuizMining> generateQuizMining(){
    	
    	HashMap<Long, QuizMining> quiz_mining = new HashMap<Long, QuizMining>();
    	    	
    	for (Iterator<Quiz_LMS> iter = quiz_lms.iterator(); iter.hasNext(); ) {

    		Quiz_LMS loadedItem = iter.next();

    		QuizMining insert = new QuizMining();
    		
    		insert.setId(loadedItem.getId()); 		
    		insert.setTitle(loadedItem.getName());
    		insert.setTimeopen(loadedItem.getTimeopen());
    		insert.setTimeclose(loadedItem.getTimeclose());
    		insert.setTimecreated(loadedItem.getTimecreated());
    		insert.setTimemodified(loadedItem.getTimemodified());
    		insert.setQtype("quiz");
        	for (Iterator<Grade_items_LMS> iter2 = grade_items_lms.iterator(); iter2.hasNext(); ) {
        		Grade_items_LMS loadedItem2 = iter2.next();
        		if(loadedItem2.getIteminstance() != null && loadedItem2.getItemmodule() != null){
        			logger.info("Iteminstance"+loadedItem2.getIteminstance()+" QuizId"+loadedItem.getId());
        			if(loadedItem.getId()==loadedItem2.getIteminstance().longValue() && loadedItem2.getItemmodule().equals("quiz")){
        				insert.setMaxgrade(loadedItem2.getGrademax());
		    			break;
        			}
        		}
        		else{
        			logger.info("Iteminstance or Itemmodule not found for QuizId"+loadedItem.getId() +" and type quiz and Iteminstance " +loadedItem2.getIteminstance()+" Itemmodule:" +loadedItem2.getItemmodule());
        		}
        	}
    		quiz_mining.put(insert.getId(), insert);    
        }
		return quiz_mining;    	
    }
 
    public HashMap<Long, AssignmentMining> generateAssignmentMining(){
    	
    	HashMap<Long, AssignmentMining> assignment_mining = new HashMap<Long, AssignmentMining>();
    	    	
    	for (Iterator<Assignment_LMS> iter = assignment_lms.iterator(); iter.hasNext(); ) {
    		Assignment_LMS loadedItem = iter.next();
    		AssignmentMining insert = new AssignmentMining();
           	insert.setId(loadedItem.getId()); 
           	insert.setTitle(loadedItem.getName());
           	insert.setTimeopen(loadedItem.getTimeavailable());
           	insert.setTimeclose(loadedItem.getTimedue());
           	insert.setTimemodified(loadedItem.getTimemodified());
        	for (Iterator<Grade_items_LMS> iter2 = grade_items_lms.iterator(); iter2.hasNext(); ) {
        		Grade_items_LMS loadedItem2 = iter2.next();
        		if(loadedItem2.getIteminstance() != null && loadedItem2.getItemmodule() != null)
        		{
        			logger.info("Iteminstance " + loadedItem2.getIteminstance() + " AssignmentId" + loadedItem.getId());
        			if(loadedItem.getId() == loadedItem2.getIteminstance().longValue() && loadedItem2.getItemmodule().equals("assignment")){
        				insert.setMaxgrade(loadedItem2.getGrademax());
        				break;
        			}
        		}
        		else{
        			logger.info("Iteminstance or Itemmodule not found for AssignmentId"+loadedItem.getId() +" and type quiz and Iteminstance " +loadedItem2.getIteminstance()+" Itemmodule:" +loadedItem2.getItemmodule());
        		}
        	}
        	assignment_mining.put(insert.getId(),insert);
        }

		return assignment_mining;    	
    }
    
   public HashMap<Long, ScormMining> generateScormMining(){
    	
	   HashMap<Long, ScormMining> scorm_mining = new HashMap<Long, ScormMining>();
 
    	for (Iterator<Scorm_LMS> iter = scorm_lms.iterator(); iter.hasNext(); ) {
    		Scorm_LMS loadedItem = iter.next();
    		ScormMining insert = new ScormMining();
           	insert.setId(loadedItem.getId());
           	insert.setTitle(loadedItem.getName());
           	insert.setTimemodified(loadedItem.getTimemodified());
    		insert.setMaxgrade(loadedItem.getMaxgrade());
    		scorm_mining.put(insert.getId(), insert);
        }  
		return scorm_mining;    	
    }
    
    public HashMap<Long, QuizQuestionMining> generateQuizQuestionMining(){
    	
    	HashMap<Long, QuizQuestionMining> quiz_question_mining = new HashMap<Long, QuizQuestionMining>();
    	    	
    	for (Iterator<Quiz_question_instances_LMS> iter = quiz_question_instances_lms.iterator(); iter.hasNext(); ) {
    		Quiz_question_instances_LMS loadedItem = iter.next();
    		QuizQuestionMining insert = new QuizQuestionMining();
           	insert.setId(loadedItem.getId());
           	insert.setQuiz(loadedItem.getQuiz(), quiz_mining, old_quiz_mining);
           	insert.setQuestion(loadedItem.getQuestion(), question_mining, old_question_mining);
            if(insert.getQuiz() != null && insert.getQuestion() != null)
            {  
            	quiz_question_mining.put(insert.getId(), insert);
            }
            else
            {
            	logger.info("In Quiz_question_mining, quiz not found: " + loadedItem.getQuiz());
            }
    	}    	
		return quiz_question_mining;
    } 

    
    public HashMap<Long, QuestionMining> generateQuestionMining(){
    	
    	HashMap<Long, QuestionMining> question_mining = new HashMap<Long, QuestionMining>();
    	    	
    	for (Iterator<Question_LMS> iter = question_lms.iterator(); iter.hasNext(); ) {
    		Question_LMS loadedItem = iter.next();
    		QuestionMining insert = new QuestionMining();
           	insert.setId(loadedItem.getId());
           	insert.setTitle(loadedItem.getName());
           	insert.setText(loadedItem.getQuestiontext());
           	insert.setType(loadedItem.getQtype());
           	insert.setTimecreated(loadedItem.getTimecreated());
           	insert.setTimemodified(loadedItem.getTimemodified());
           	question_mining.put(insert.getId(), insert);
        }
		return question_mining;    	
    }
    
    public HashMap<Long, QuizUserMining> generateQuizUserMining(){

    	HashMap<Long, QuizUserMining> quiz_user_mining = new HashMap<Long, QuizUserMining>();
    	for (Iterator<Grade_grades_LMS> iter = grade_grades_lms.iterator(); iter.hasNext(); ) {
    		Grade_grades_LMS loadedItem = iter.next();
    		QuizUserMining insert = new QuizUserMining();
           	insert.setId(loadedItem.getId());
           	if(loadedItem.getFinalgrade()!=null){
           		insert.setFinalgrade(loadedItem.getFinalgrade());
           	}
           	if(loadedItem.getRawgrade()!=null){
           		insert.setRawgrade(loadedItem.getRawgrade());	
           	}
           	if(loadedItem.getTimemodified()!=null){
           		insert.setTimemodified(loadedItem.getTimemodified());
           	}
           	
			if(!numericUserId)
			{
    			long id = -1;
       			if(id_mapping.get(loadedItem.getUserid()) != null)
       			{
       				id = id_mapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id, user_mining, old_user_mining);
       		
       			}
       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
       			{
       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id, user_mining, old_user_mining);
       			
       			}
       			if(id == -1 )
       			{
       				id = largestId + 1;
       				largestId = id;
       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
       				insert.setUser(id, user_mining, old_user_mining);
       			}
       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
       				largestId = insert.getUser().getId();
			}
           	for (Iterator<Grade_items_LMS> iter2 = grade_items_lms.iterator(); iter2.hasNext(); ) {
        		Grade_items_LMS loadedItem2 = iter2.next();
        		if(loadedItem2.getId()==loadedItem.getItemid() && loadedItem2.getIteminstance()!=null){
        			insert.setCourse(loadedItem2.getCourseid(), course_mining, old_course_mining);
        			insert.setQuiz(loadedItem2.getIteminstance(), quiz_mining, old_quiz_mining);
           		   	if(insert.getQuiz()!= null && insert.getUser() != null){
        		   		quiz_user_mining.put(insert.getId(), insert);
        		   	}
        		   	else{
        		   		logger.info("In Quiz_user_mining, quiz not found for: Iteminstance: " + loadedItem2.getIteminstance() + " Itemmodule: " + loadedItem2.getItemmodule() +" course: " + loadedItem2.getCourseid() + " user: " + loadedItem.getUserid());
        		   	}
        		}
        	}			
    	}
    	return quiz_user_mining;
    }
    
    public HashMap<Long, ResourceMining> generateResourceMining() {
    	
    	HashMap<Long, ResourceMining> resource = new HashMap<Long, ResourceMining>();
    	
        for ( Iterator<Resource_LMS> iter = resource_lms.iterator(); iter.hasNext(); ) {
            Resource_LMS loadedItem = iter.next();
            ResourceMining insert = new ResourceMining();
            insert.setId(loadedItem.getId());
           	insert.setType(loadedItem.getType());
           	insert.setTitle(loadedItem.getName());
           	
           	//Get time of creation
        	
           	insert.setTimemodified(loadedItem.getTimemodified());
           	
       		resource.put(insert.getId(), insert);    		
        }
        
       	for ( Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) {
           	Log_LMS loadedItem = iter.next();
           	if(resource.get(loadedItem.getCmid()) != null && (resource.get(loadedItem.getCmid()).getTimecreated() == 0 || resource.get(loadedItem.getCmid()).getTimecreated() > loadedItem.getTime()))
           	{           		
           		resource.get(loadedItem.getCmid()).setTimecreated(loadedItem.getTime());
       		}
       	}           
		return resource;
    }
    
    
    public HashMap<Long, ResourceLogMining> generateResourceLogMining() {
    	HashMap<Long, ResourceLogMining> resource_log_mining = new HashMap<Long, ResourceLogMining>();
    	//A HashMap of list of timestamps. Every key represents one user, the according value is a list of his/her reqests times.
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
        
        for ( Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) 
        {
            Log_LMS loadedItem = iter.next();
            
            long uid = -1;
            
            if(id_mapping.get(loadedItem.getUserid()) != null)
    			uid = id_mapping.get(loadedItem.getUserid()).getId();
    		if(uid == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
    			uid = old_id_mapping.get(loadedItem.getUserid()).getId();
    		
    		//Creates a list of time stamps for every user indicating requests
    		//We later use this lists to determine the time a user accessed a resource
            if(users.get(uid) == null)
        	{
            	//if the user isn't already listed, create a new key
        		ArrayList<Long> times = new ArrayList<Long>();
        		times.add(loadedItem.getTime());
        		users.put(uid, times);
        	}
        	else
        	{
        		ArrayList<Long> times = users.get(uid);
        		if(loadedItem.getAction() == "login")
        			times.add(0L);
        		if(!times.contains(loadedItem.getTime()))
        			times.add(loadedItem.getTime());
        		users.put(uid, times);
        	}
            
            if(loadedItem.getModule().equals("resource")){
            	ResourceLogMining insert = new ResourceLogMining();
            	insert.setId(loadedItem.getId());
            	
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);
	       			
	       			}
	       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);
	       			
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
	       				insert.setUser(id, user_mining, old_user_mining);
	       			}
	       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
	       				largestId = insert.getUser().getId();
    			}
            	insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
            	insert.setAction(loadedItem.getAction()); 
            	
            	
            	
            	if(loadedItem.getInfo().matches("[0-9]+")){
    				insert.setResource(Long.valueOf(loadedItem.getInfo()), resource_mining, old_resource_mining);
    			}    				
            	insert.setTimestamp(loadedItem.getTime());
    			if(insert.getResource()== null && !(loadedItem.getAction().equals("view all"))){
    				logger.info("In Resource_log_mining, resource not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo()+ " and action: " + loadedItem.getAction());
    			}
    			if(insert.getCourse() != null && insert.getResource() != null && insert.getUser() != null)
    				resource_log_mining.put(insert.getId(), insert);
    			
            }
        }
        //For 
        for( Iterator<ResourceLogMining> iter = resource_log_mining.values().iterator(); iter.hasNext();)
        {
        	ResourceLogMining r = iter.next();
        	if(r.getUser() != null)
        	{	
	        	long duration = -1;
	        	ArrayList<Long> times = users.get(r.getUser().getId());
	        
	        	int pos = times.indexOf(r.getTimestamp());
	        	if( pos > -1 && pos < times.size()-1)
	        		if(times.get(pos + 1) != 0)
	        			duration = times.get(pos + 1) - times.get(pos);
	        	//All duration that are longer than one hour are cut to an hour
	        	if(duration > 3600)
	        		duration = 3600;
	        	r.setDuration(duration);
        	}
        }
		return resource_log_mining;
    }
    
    
    public HashMap<Long, UserMining> generateUserMining(){
    	
    	HashMap<Long, UserMining> user_mining = new HashMap<Long, UserMining>();
    	    	
    	for (Iterator<User_LMS> iter = user_lms.iterator(); iter.hasNext(); ) {
    		User_LMS loadedItem = iter.next();
    		UserMining insert = new UserMining();
    		
    		if(!numericUserId)
    		{
				long id = largestId + 1;
	   			largestId = id;
	   			id_mapping.put(loadedItem.getId(), new IDMappingMining(id, loadedItem.getId(), "Moodle19"));
	   			insert.setId(id);
	   				
	   			if(insert.getId() > largestId)
	   				largestId = insert.getId();
    		}
           	insert.setLastlogin(loadedItem.getLastlogin());
           	insert.setFirstaccess(loadedItem.getFirstaccess());
           	insert.setLastaccess(loadedItem.getLastaccess());
           	insert.setCurrentlogin(loadedItem.getCurrentlogin());
           	user_mining.put(insert.getId(), insert);
        }
		return user_mining;    	
    }
    
    
    public HashMap<Long, WikiLogMining> generateWikiLogMining(){
    	HashMap<Long, WikiLogMining> wiki_log_mining = new HashMap<Long, WikiLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	
        for (Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) {
            Log_LMS loadedItem = iter.next();
            
            long uid = -1;
            
            if(id_mapping.get(loadedItem.getUserid()) != null)
    			uid = id_mapping.get(loadedItem.getUserid()).getId();
    		if(uid == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
    			uid = old_id_mapping.get(loadedItem.getUserid()).getId();
    		
    		
    		//Creates a list of time stamps for every user indicating requests
    		//We later use this lists to determine the time a user accessed a resource
            if(users.get(uid) == null)
        	{
            	//if the user isn't already listed, create a new key
        		ArrayList<Long> times = new ArrayList<Long>();
        		times.add(loadedItem.getTime());
        		users.put(uid, times);
        	}
        	else
        	{
        		ArrayList<Long> times = users.get(uid);
        		if(loadedItem.getAction() == "login")
        			times.add(0L);
        		if(!times.contains(loadedItem.getTime()))
        			times.add(loadedItem.getTime());
        		users.put(uid, times);
        	}
            
    		if(loadedItem.getModule().equals("wiki")){
    			WikiLogMining insert = new WikiLogMining();
    			insert.setId(loadedItem.getId());
    			//Cannot tell, how to extract the correct wiki-id - so it'll always be null
    			insert.setWiki(loadedItem.getCmid(), wiki_mining, old_wiki_mining);
    			
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);
	       			
	       			}
	       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id, user_mining, old_user_mining);
	       				
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
	       				insert.setUser(id, user_mining, old_user_mining);
	       			}
	       			if(insert.getUser() != null && insert.getUser().getId() > largestId)
	       				largestId = insert.getUser().getId();
    			}
    			
    			insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
    			insert.setAction(loadedItem.getAction());
    			insert.setTimestamp(loadedItem.getTime());
    			
    			if(insert.getUser() != null && insert.getCourse() != null && insert.getWiki() != null)
    				wiki_log_mining.put(insert.getId(), insert);
    		}
        }
        
        for( Iterator<WikiLogMining> iter = wiki_log_mining.values().iterator(); iter.hasNext();)
        {
        	WikiLogMining r = iter.next();
        	if(r.getUser() != null)
        	{	
	        	long duration = -1;
	        	ArrayList<Long> times = users.get(r.getUser().getId());
	        
	        	int pos = times.indexOf(r.getTimestamp());
	        	if( pos > -1 && pos < times.size()-1)
	        		if(times.get(pos + 1) != 0)
	        			duration = times.get(pos + 1) - times.get(pos);
	        	//All duration that are longer than one hour are cut to an hour
	        	if(duration > 3600)
	        		duration = 3600;
	        	r.setDuration(duration);
        	}
        }
    	return wiki_log_mining;
    } 
    
    
	public HashMap<Long, WikiMining> generateWikiMining(){
    	
		HashMap<Long, WikiMining> wiki_mining = new HashMap<Long, WikiMining>();
    	
    	for ( Iterator<Wiki_LMS> iter = wiki_lms.iterator(); iter.hasNext(); ) {
    		Wiki_LMS loadedItem = iter.next();
    		WikiMining insert = new WikiMining();
    		insert.setId(loadedItem.getId());
    		insert.setTitle(loadedItem.getName());
    		insert.setSummary(loadedItem.getSummary());
    		insert.setTimemodified(loadedItem.getTimemodified());
    		wiki_mining.put(insert.getId(), insert);
    	}
       	for ( Iterator<Log_LMS> iter = log_lms.iterator(); iter.hasNext(); ) {
           	Log_LMS loadedItem = iter.next();
           	if(loadedItem.getModule().equals("Wiki") && wiki_mining.get(loadedItem.getCmid()) != null && (wiki_mining.get(loadedItem.getCmid()).getTimecreated() == 0 || wiki_mining.get(loadedItem.getCmid()).getTimecreated() > loadedItem.getTime()))
           	{           		
           		wiki_mining.get(loadedItem.getCmid()).setTimecreated(loadedItem.getTime());
       		}
       	} 
    	return wiki_mining;
    }
	
	public HashMap<Long, RoleMining> generateRoleMining(){
//generate role tables
		HashMap<Long, RoleMining> role_mining = new HashMap<Long, RoleMining>();
    	
    	for ( Iterator<Role_LMS> iter = role_lms.iterator(); iter.hasNext(); ) {
    		Role_LMS loadedItem = iter.next();
    		RoleMining insert = new RoleMining();
    		insert.setId(loadedItem.getId());
    		insert.setName(loadedItem.getName());
    		insert.setShortname(loadedItem.getShortname());
    		insert.setDescription(loadedItem.getDescription());
    		insert.setSortorder(loadedItem.getSortorder());
    		role_mining.put(insert.getId(), insert);
    	}
		return role_mining;
    }

	public HashMap<Long, DegreeMining> generateDegreeMining() {
		HashMap<Long, DegreeMining> degree_mining = new HashMap<Long, DegreeMining>();
		
		for( Iterator<CourseCategories_LMS> iter = course_categories_lms.iterator(); iter.hasNext();)
		{
			CourseCategories_LMS loadedItem = iter.next();
			if(loadedItem.getDepth() == 2)
			{
				DegreeMining insert = new DegreeMining();
				insert.setId(loadedItem.getId());
				insert.setTitle(loadedItem.getTitle());
				degree_mining.put(insert.getId(), insert);
			}
		}
		return degree_mining;
	}

	public HashMap<Long, DepartmentMining> generateDepartmentMining() {
		HashMap<Long, DepartmentMining> department_mining = new HashMap<Long, DepartmentMining>();
		
		for( Iterator<CourseCategories_LMS> iter = course_categories_lms.iterator(); iter.hasNext();)
		{
			CourseCategories_LMS loadedItem = iter.next();
			if(loadedItem.getDepth() == 1)
			{
				DepartmentMining insert = new DepartmentMining();
				insert.setId(loadedItem.getId());
				insert.setTitle(loadedItem.getTitle());
				department_mining.put(insert.getId(), insert);
			}
		}
		return department_mining;
	}

	public HashMap<Long, DepartmentDegreeMining> generateDepartmentDegreeMining() {
		HashMap<Long, DepartmentDegreeMining> department_degree = new HashMap<Long, DepartmentDegreeMining>();
		
		for( Iterator<CourseCategories_LMS> iter = course_categories_lms.iterator(); iter.hasNext();)
		{
			CourseCategories_LMS loadedItem = iter.next();
			if(loadedItem.getDepth() == 2)
			{
				String[] s = loadedItem.getPath().split("/");
				if(s.length == 3)
				{
					DepartmentDegreeMining insert = new DepartmentDegreeMining();
					insert.setId(loadedItem.getId());
					insert.setDegree(Integer.parseInt(s[s.length - 1]), degree_mining, old_degree_mining);
					insert.setDepartment(Integer.parseInt(s[s.length - 2]), department_mining, old_department_mining);
					if(insert.getDegree() != null && insert.getDepartment() != null)
						department_degree.put(insert.getId(), insert);
				}
			}
		}
		return department_degree;
	}

	public HashMap<Long, DegreeCourseMining> generateDegreeCourseMining() {
		
		HashMap<Long, DegreeCourseMining> degree_course = new HashMap<Long, DegreeCourseMining>();
		
		for( Iterator<Context_LMS> iter = context_lms.iterator(); iter.hasNext();)
		{
			Context_LMS loadedItem = iter.next();
			if(loadedItem.getDepth() == 4 && loadedItem.getContextlevel() == 50)
			{
				String[] s = loadedItem.getPath().split("/");
				DegreeCourseMining insert = new DegreeCourseMining();
				insert.setId(loadedItem.getId());
				insert.setCourse(loadedItem.getInstanceid(), course_mining, old_course_mining);
				for ( Iterator<Context_LMS> iter2 = context_lms.iterator(); iter2.hasNext();)
				{
					Context_LMS loadedItem2 = iter2.next();
					if(loadedItem2.getContextlevel() == 40 && loadedItem2.getId() == Integer.parseInt(s[3]))
					{
						insert.setDegree(loadedItem2.getInstanceid(), degree_mining, old_degree_mining);
						break;
					}
				}
				if(insert.getDegree() != null && insert.getCourse() != null)
					degree_course.put(insert.getId(), insert);
			}
		}
		return degree_course;
	}

	public HashMap<Long, ChatMining> generateChatMining() {
		HashMap<Long, ChatMining> chat_mining = new HashMap<Long, ChatMining>();
		
		for( Iterator<Chat_LMS> iter = chat_lms.iterator(); iter.hasNext();)
		{
			Chat_LMS loadedItem = iter.next();
			ChatMining insert = new ChatMining();
			insert.setId(loadedItem.getId());
			insert.setChattime(loadedItem.getChattime());
			insert.setDescription(loadedItem.getDescription());
			insert.setTitle(loadedItem.getTitle());
			insert.setCourse(loadedItem.getCourse(), course_mining, old_course_mining);
			chat_mining.put(insert.getId(), insert);
        }

		return chat_mining;
	}


	public HashMap<Long, ChatLogMining> generateChatLogMining() {
		HashMap<Long, ChatLogMining> chat_log_mining = new HashMap<Long, ChatLogMining>();
    	
        for ( Iterator<ChatLog_LMS> iter = chat_log_lms.iterator(); iter.hasNext(); ) 
        {
            ChatLog_LMS loadedItem = iter.next();
        	ChatLogMining insert = new ChatLogMining();
        	insert.setId(loadedItem.getId());
        	insert.setChat(loadedItem.getChat_id(), chat_mining, old_chat_mining);
        	insert.setMessage(loadedItem.getMessage());
        	insert.setTimestamp(loadedItem.getTimestamp());
        	insert.setCourse(loadedItem.getChat_id(), course_mining, old_course_mining);
        	
        	
			if(!numericUserId)
			{
    			long id = -1;
       			if(id_mapping.get(loadedItem.getUserid()) != null)
       			{
       				id = id_mapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id, user_mining, old_user_mining);
       			
       			}
       			if(id == -1 && old_id_mapping.get(loadedItem.getUserid()) != null)
       			{
       				id = old_id_mapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id, user_mining, old_user_mining);
       				
       			}
       			if(id == -1 )
       			{
       				id = largestId + 1;
       				largestId = id;
       				id_mapping.put(loadedItem.getUserid(), new IDMappingMining(id, loadedItem.getUserid(), "Moodle19"));
       				insert.setUser(id, user_mining, old_user_mining);
       			}
       			
       			if(insert.getUser() != null && insert.getUser().getId() > largestId )
       				largestId = insert.getUser().getId();
			}
  			if(insert.getUser()==null){
  				logger.info("In Chat_log_mining(chat part), user not found for log: " + loadedItem.getId() +" and user: " + loadedItem.getUserid());
  			}
  			if(insert.getChat()==null){
  				logger.info("In Chat_log_mining(chat part), chat not found for log: " + loadedItem.getId() +" and chat: " + loadedItem.getChat_id());
  			}
  			if(insert.getChat() != null && insert.getUser() != null && insert.getCourse() != null)
  				chat_log_mining.put(insert.getId(), insert);
  			
        }
		return chat_log_mining;
	}
	
}