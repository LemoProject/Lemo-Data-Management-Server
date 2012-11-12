package de.lemo.dms.connectors.moodleNumericId;
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


import org.apache.log4j.Logger;
import org.hibernate.*;

import de.lemo.dms.connectors.moodleNumericId.HibernateUtil;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Assignment_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Assignment_submissions_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.ChatLog_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Chat_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Context_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.CourseCategories_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Course_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Forum_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Forum_discussions_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Forum_posts_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Grade_grades_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Grade_items_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Groups_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Groups_members_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Log_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Question_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Question_states_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Quiz_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Quiz_grades_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Quiz_question_instances_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Resource_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Role_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Role_assignments_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Scorm_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.User_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Wiki_LMS;
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
	
	
	private Logger logger = Logger.getLogger(getClass());

	
	@SuppressWarnings("unchecked")	
	public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp) {
		   
		   	//accessing DB by creating a session and a transaction using HibernateUtil
	        Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
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
		group_lms.clear();
		group_members_lms.clear();
		question_states_lms.clear();
		forum_posts_lms.clear();
		role_lms.clear();
		role_assignments_lms.clear();
		assignment_submission_lms.clear();
	}
	
//methods for create and fill the mining-table instances
    
	
	/**
	 * Creates all CourseUserMining objects using the tables "context" and "role_assignments" from Moodle's database.
	 * 
	 * @return	A HashMap of CourseUserMining objects, using the object's id as the key and the object itself as the value
	 */
    public HashMap<Long, CourseUserMining> generateCourseUserMining(){
    	
    	HashMap<Long, CourseUserMining> course_user_mining = new HashMap<Long, CourseUserMining>();
    	    	
    	for (Context_LMS loadedItem : context_lms) 
    	{
       		if(loadedItem.getContextlevel() == 50){
       			for (Role_assignments_LMS loadedItem2 : role_assignments_lms) 
       			{
       	       		if(loadedItem2.getContextid() == loadedItem.getId()){
       	       			CourseUserMining insert = new CourseUserMining();
       	       			insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem2.getId()));
       	       			insert.setRole(Long.valueOf(platform.getPrefix() + "" + loadedItem2.getRoleid()), role_mining, old_role_mining);
       	       			insert.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem2.getUserid()), user_mining, old_user_mining);
       	       			
       	       			insert.setEnrolstart(loadedItem2.getTimestart());
       	       			insert.setEnrolend(loadedItem2.getTimeend());
       	                insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getInstanceid()), course_mining, old_course_mining);
       	       			if(insert.getUser()!= null && insert.getCourse() != null && insert.getRole()!= null){       	       			
       	       				course_user_mining.put(insert.getId(), insert);
       	       			}    	       			
       	       		}
       			}   			
       		}
    	}
    	
		return course_user_mining;
    }
    
	/**
	 * Creates all CourseForumMining objects using the tables "forum" from Moodle's database.
	 * 
	 * @return	A HashMap of CourseForumMining objects, using the object's id as the key and the object itself as the value
	 */
    public HashMap<Long, CourseForumMining> generateCourseForumMining(){
    	
    	HashMap<Long, CourseForumMining> course_forum_mining = new HashMap<Long, CourseForumMining>();
    	    	
    	for (Forum_LMS loadedItem : forum_lms) 
    	{
    		CourseForumMining insert = new CourseForumMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
            insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()),course_mining, old_course_mining);
            insert.setForum(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()),forum_mining, old_forum_mining);
            
            if(insert.getCourse()!= null && insert.getForum()!= null){
            	course_forum_mining.put(insert.getId(), insert);
            }
        }
		return course_forum_mining;
    }
    
    
	/**
	 * Creates all CourseMining objects using the tables "course" from Moodle's database.
	 * 
	 * @return	A HashMap of CourseMining objects, using the object's id as the key and the object itself as the value
	 */
    public HashMap<Long, CourseMining> generateCourseMining() {
    	
    	HashMap<Long, CourseMining> course_mining = new HashMap<Long, CourseMining>();
       	for (Course_LMS loadedItem : course_lms) 
       	{
        	CourseMining insert = new CourseMining();
        
        	insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
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
    
	/**
	 * Creates all CourseGroupMining objects using the tables "group" from Moodle's database.
	 * 
	 * @return	A HashMap of CourseGroupMining objects, using the object's id as the key and the object itself as the value
	 */
	public HashMap<Long, CourseGroupMining> generateCourseGroupMining(){
    	
		HashMap<Long, CourseGroupMining> course_group_mining = new HashMap<Long, CourseGroupMining>();
    	    	
    	for (Groups_LMS loadedItem : group_lms) 
    	{
    		CourseGroupMining insert = new CourseGroupMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
            insert.setGroup(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()),group_mining, old_group_mining);
            insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourseid()),course_mining, old_course_mining);
            
            if(insert.getCourse()!= null && insert.getGroup()!= null){
            	course_group_mining.put(insert.getId(), insert);
            }
        }
		return course_group_mining;
    }
    
    
	/**
	 * Creates all CourseQuizMining objects using the tables "quiz" from Moodle's database.
	 * 
	 * @return	A HashMap of CourseQuizMining objects, using the object's id as the key and the object itself as the value
	 */
    public HashMap<Long, CourseQuizMining> generateCourseQuizMining(){
    	
    	HashMap<Long, CourseQuizMining> course_quiz_mining = new HashMap<Long, CourseQuizMining>();
    	    	
    	for (Quiz_LMS loadedItem : quiz_lms) 
    	{
    		CourseQuizMining insert = new CourseQuizMining();
    	
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
    		insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()),course_mining, old_course_mining);
    		insert.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()), quiz_mining, old_quiz_mining);
    		if(insert.getCourse() != null && insert.getQuiz() != null){            
    			course_quiz_mining.put(insert.getId(), insert);
    		}
    		
    		if(insert.getQuiz() == null){
    			logger.info("In Course_quiz_mining, quiz(quiz) not found: " + loadedItem.getId());
	    		}   
        }    	
		return course_quiz_mining;
    }

	/**
	 * Creates all CourseAssignmentMining objects using the tables "assignment" from Moodle's database.
	 * 
	 * @return	A HashMap of CourseAssignmentMining objects, using the object's id as the key and the object itself as the value
	 */
   public HashMap<Long, CourseAssignmentMining> generateCourseAssignmentMining(){
    	
	   HashMap<Long, CourseAssignmentMining> course_assignment_mining = new HashMap<Long, CourseAssignmentMining>();
    	    	   	
    	for (Assignment_LMS loadedItem : assignment_lms) 
    	{
    		CourseAssignmentMining insert = new CourseAssignmentMining();
    		
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
            insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()),course_mining, old_course_mining);
			if(insert.getCourse()==null){
				logger.info("course not found for course-assignment: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
			}
            insert.setAssignment(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()), assignment_mining, old_assignment_mining);
            if(insert.getCourse()!= null && insert.getAssignment() != null){            
            	course_assignment_mining.put(insert.getId(), insert);
            }
    		if(insert.getAssignment()==null){
    			logger.info("In Course_assignment_mining, assignment not found: " + loadedItem.getId());
	    		}   
        }
		return course_assignment_mining;
    }
   
	/**
	 * Creates all CourseScormMining objects using the tables "scorm" from Moodle's database.
	 * 
	 * @return	A HashMap of CourseScormMining objects, using the object's id as the key and the object itself as the value
	 */
   public HashMap<Long, CourseScormMining> generateCourseScormMining(){
   	
	   	HashMap<Long, CourseScormMining> course_scorm_mining = new HashMap<Long, CourseScormMining>();   	    	
	   	
	   	for (Scorm_LMS loadedItem : scorm_lms) 
	   	{
	   		CourseScormMining insert = new CourseScormMining();
	   	
	   		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
	   		insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()),course_mining, old_course_mining);
	   		insert.setScorm(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()), scorm_mining, old_scorm_mining);
	   		
	   		if(insert.getCourse()!= null && insert.getScorm() != null){            
	   			course_scorm_mining.put(insert.getId(), insert);
	   		}
	   		if(insert.getScorm()==null){
	   			logger.info("In Course_scorm_mining, scorm not found: " + loadedItem.getId());
	   		}      	
	   	}
			return course_scorm_mining;
   }
    
   
	/**
	 * Creates all CourseResourceMining objects using the tables "resource" from Moodle's database.
	 * 
	 * @return	A HashMap of CourseResourceMining objects, using the object's id as the key and the object itself as the value
	 */
    public HashMap<Long, CourseResourceMining> generateCourseResourceMining(){
    	
    	HashMap<Long, CourseResourceMining> course_resource_mining = new HashMap<Long, CourseResourceMining>();
    	    	
    	for (Resource_LMS loadedItem : resource_lms) 
    	{
    		CourseResourceMining insert = new CourseResourceMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
            insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()), course_mining, old_course_mining);
            insert.setResource(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()), resource_mining, old_resource_mining);
            
            if(insert.getCourse()!= null && insert.getResource() != null){  
            	course_resource_mining.put(insert.getId(), insert);
            }
        }
		return course_resource_mining;
    }
    
    

	/**
	 * Creates all CourseLogMining objects using the tables "log" from Moodle's database.
	 * 
	 * @return	A HashMap of CourseLogMining objects, using the object's id as the key and the object itself as the value
	 */
    public HashMap<Long, CourseLogMining> generateCourseLogMining(){
    	HashMap<Long, CourseLogMining> courseLogMining = new HashMap<Long, CourseLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	
    	for (Log_LMS loadedItem : log_lms) 
    	{
    	
    		 long uid = Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid());
             
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
    			insert.setId(courseLogMining.size() + 1 + courseLogMax);
    			insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()), course_mining, old_course_mining);
    			
    			insert.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid()), user_mining, old_user_mining);
    			
    			if(insert.getUser() != null && insert.getUser().getId() > largestId)
    				largestId = insert.getUser().getId();
    			insert.setAction(loadedItem.getAction());
    			insert.setTimestamp(loadedItem.getTime());
    			if(insert.getUser() != null && insert.getCourse() != null)
    				courseLogMining.put(insert.getId(), insert);
    			
    		}
        }
    	
    	for( CourseLogMining r : courseLogMining.values())
        {
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
    	
		return courseLogMining;
    }
    
    
	/**
	 * Creates all CourseWikiMining objects using the tables "wiki" from Moodle's database.
	 * 
	 * @return	A HashMap of CourseWikiMining objects, using the object's id as the key and the object itself as the value
	 */
    public HashMap<Long, CourseWikiMining> generateCourseWikiMining(){
    	
    	HashMap<Long, CourseWikiMining> course_wiki_mining = new HashMap<Long, CourseWikiMining>();
    	    	
    	for (Wiki_LMS loadedItem : wiki_lms) 
    	{
    		CourseWikiMining insert = new CourseWikiMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
            insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()), course_mining, old_course_mining);
            insert.setWiki(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()), wiki_mining, old_wiki_mining);
            
            if(insert.getCourse()!= null && insert.getWiki()!= null){
            	course_wiki_mining.put(insert.getId(), insert);
            }
        }
		return course_wiki_mining;
    }

    
	/**
	 * Creates all ForumLogMining objects using the tables "log","forum_discussions" and "forum_posts" from Moodle's database.
	 * 
	 * @return	A HashMap of ForumLogMining objects, using the object's id as the key and the object itself as the value
	 */
    public HashMap<Long, ForumLogMining> generateForumLogMining() {
    	HashMap<Long, ForumLogMining> forumLogMining = new HashMap<Long, ForumLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	    		
    	for ( Log_LMS loadedItem : log_lms) 
    	{
    		long uid = Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid());
    		 
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
    			insert.setId(forumLogMining.size() + 1 + forumLogMax);
    			
    			insert.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid()), user_mining, old_user_mining);
    			if((loadedItem.getAction().equals("view forum") || loadedItem.getAction().equals("subscribe")) && loadedItem.getInfo().matches("[0-9]+")){
    				insert.setForum(Long.valueOf(platform.getPrefix() + "" + Long.valueOf(loadedItem.getInfo())), forum_mining, old_forum_mining);
    			}
    			else{
    				if((loadedItem.getAction().equals("add discussion") || loadedItem.getAction().equals("view discussion")) && loadedItem.getInfo().matches("[0-9]+")){
    					for ( Forum_discussions_LMS loadedItem2 : forum_discussions_lms) 
    					{
    			    		if(loadedItem2.getId() == Long.valueOf(loadedItem.getInfo()))
    			    		{
    			    			insert.setForum(Long.valueOf(platform.getPrefix() + "" + loadedItem2.getForum()), forum_mining, old_forum_mining);
    			    			break;
    			    		}
    					}
    				}
    			}
    			insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()), course_mining, old_course_mining);
    			insert.setAction(loadedItem.getAction());
   				for (Forum_posts_LMS loadedItem2 : forum_posts_lms) 
   				{
   					if(loadedItem2.getUserid() == loadedItem.getUserid() && (loadedItem2.getCreated() == loadedItem.getTime()||loadedItem2.getModified() == loadedItem.getTime())){
   						insert.setMessage(loadedItem2.getMessage());
    					insert.setSubject(loadedItem2.getSubject());
    					break;
    				}
    			}
    			insert.setTimestamp(loadedItem.getTime());
    			if(insert.getCourse() != null && insert.getForum() != null && insert.getUser() != null)
    				forumLogMining.put(insert.getId(), insert);
    			
    		}
    	}
    	
    	for( ForumLogMining r : forumLogMining.values())
        {
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
    	return forumLogMining;    	
    } 
    
    
    public HashMap<Long, ForumMining> generateForumMining() {
    	
    	HashMap<Long, ForumMining> forum_mining = new HashMap<Long, ForumMining>();
	
    	for ( Forum_LMS loadedItem : forum_lms) 
    	{
   	    	ForumMining insert = new ForumMining();
    	
   	    	insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
            insert.setTimemodified(loadedItem.getTimemodified());
   	    	insert.setTitle(loadedItem.getName());
   	    	insert.setSummary(loadedItem.getIntro());   
    		
   	    	forum_mining.put(insert.getId(), insert);  
    	} 
    	
       	for (Log_LMS loadedItem : log_lms) 
       	{
           	if(forum_mining.get(loadedItem.getCmid()) != null && (forum_mining.get(loadedItem.getCmid()).getTimecreated() == 0 || forum_mining.get(loadedItem.getCmid()).getTimecreated() > loadedItem.getTime()))
           	{           		
           		forum_mining.get(loadedItem.getCmid()).setTimecreated(loadedItem.getTime());
       		}
       	}   
		return forum_mining;
    }

    
	public HashMap<Long, GroupUserMining> generateGroupUserMining(){
    	
		HashMap<Long, GroupUserMining> group_members_mining = new HashMap<Long, GroupUserMining>();
    	    	
    	for (Groups_members_LMS loadedItem : group_members_lms) 
    	{
    		GroupUserMining insert = new GroupUserMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
           	insert.setGroup(Long.valueOf(platform.getPrefix() + "" + loadedItem.getGroupid()), group_mining, old_group_mining);
			insert.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid()), user_mining, old_user_mining);
           	insert.setTimestamp(loadedItem.getTimeadded());
           	
           	if(insert.getUser() != null && insert.getGroup() != null)
           		group_members_mining.put(insert.getId(), insert);
        }
		return group_members_mining;
    }
	
	
    public HashMap<Long, GroupMining> generateGroupMining(){
    	
    	HashMap<Long, GroupMining> group_mining = new HashMap<Long, GroupMining>();
    	    	
    	for (Groups_LMS loadedItem : group_lms) 
    	{
    		GroupMining insert = new GroupMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
           	insert.setTimecreated(loadedItem.getTimecreated());
           	insert.setTimemodified(loadedItem.getTimemodified());
           	
           	group_mining.put(insert.getId(), insert);
        }
		return group_mining;    	
    }
    
    
    public HashMap<Long, QuestionLogMining> generateQuestionLogMining(){
    	HashMap<Long, QuestionLogMining> questionLogMiningtmp = new HashMap<Long, QuestionLogMining>();
    	HashMap<Long, QuestionLogMining> questionLogMining = new HashMap<Long, QuestionLogMining>();
    	HashMap<String, Long> timestampIdMap = new HashMap<String, Long>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	    	
    	for (Question_states_LMS loadedItem : question_states_lms) {
    		
    		QuestionLogMining insert = new QuestionLogMining();
    		
    		insert.setId(questionLogMiningtmp.size() + 1 + questionLogMax); //ID
			insert.setQuestion(Long.valueOf(platform.getPrefix() + "" + loadedItem.getQuestion()), question_mining, old_question_mining); //Question 
			insert.setPenalty(loadedItem.getPenalty());	
			insert.setAnswers(loadedItem.getAnswer());
			insert.setTimestamp(loadedItem.getTimestamp());	
			insert.setPlatform(platform.getId());
			
			
			//Set Grades
			if(loadedItem.getEvent() == 3 || loadedItem.getEvent() == 6 || loadedItem.getEvent() == 9){
				insert.setRawgrade(loadedItem.getRaw_grade());
				insert.setFinalgrade(loadedItem.getGrade());
			}			
			
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
            if(insert.getQuestion() != null && quiz_question_mining.get(insert.getQuestion().getId()) != null )
            {
            	insert.setQuiz(quiz_question_mining.get(insert.getQuestion().getId()).getQuiz());
            	if(course_quiz_mining.get(insert.getQuiz().getId()) != null)
            		insert.setCourse(course_quiz_mining.get(insert.getQuiz().getId()).getCourse());
            }
            else if(insert.getQuestion() != null && old_quiz_question_mining.get(insert.getQuestion().getId()) != null && old_course_quiz_mining.get(insert.getQuiz().getId()) != null)
            {
            	insert.setQuiz(old_quiz_question_mining.get(insert.getQuestion().getId()).getQuiz());
            	if(old_course_quiz_mining.get(insert.getQuiz().getId()) != null)
            		insert.setCourse(course_quiz_mining.get(insert.getQuiz().getId()).getCourse());
            }
            /*
			for(Quiz_question_instances_LMS loadedItem1 : quiz_question_instances_lms)
			{
				if(loadedItem1.getQuestion() == (loadedItem.getQuestion())){
					insert.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem1.getQuiz()), quiz_mining, old_quiz_mining);
					break;
				}
			}	
			
			if(insert.getQuiz() == null)
				for(QuizQuestionMining loadedItem1 : old_quiz_question_mining.values())
				{
					if(loadedItem1.getQuestion().getId() == (loadedItem.getQuestion())){
						insert.setQuiz(loadedItem1.getQuiz());//Quiz
						break;
					}
				}	
				*/
			
			//Set Type
			for(Question_LMS loadedItem2 : question_lms)
			{
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
							
			

			if(insert.getQuestion() != null && insert.getQuiz() != null && insert.getCourse() != null)
			{
				
				timestampIdMap.put(insert.getTimestamp() + " " + insert.getQuiz().getId(), insert.getId());
    			questionLogMiningtmp.put(insert.getId(), insert);
			}			
    	}	  
    	
    	//Set Course and 
    	for(Log_LMS loadedItem : log_lms)
    	{
    		 long uid1 = Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid());
     		
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
                 		
    		if(uid1 != -1 && loadedItem.getModule().equals("quiz") && timestampIdMap.get(loadedItem.getTime() + " " + platform.getPrefix() + "" + loadedItem.getInfo()) != null){
    			{
    				
    				QuestionLogMining qlm = questionLogMiningtmp.get(timestampIdMap.get(loadedItem.getTime() + " " + platform.getPrefix() + "" + loadedItem.getInfo()));
    				qlm.setUser(uid1, user_mining, old_user_mining);
    				questionLogMining.put(qlm.getId(), qlm);
    			}
    		}	
		}    	
    	
    	for( QuestionLogMining r : questionLogMining.values())
        {
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
    	
    	
    	
		return questionLogMining;
    }    
    
    public HashMap<Long, QuizLogMining> generateQuizLogMining(){
    	HashMap<Long, QuizLogMining> quizLogMining = new HashMap<Long, QuizLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	    	
    	for (Log_LMS loadedItem : log_lms) 
    	{
    		 long uid = Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid());
     		
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
    		    insert.setId(quizLogMining.size() + 1 + quizLogMax);
    			insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()), course_mining, old_course_mining);
    			
    			insert.setUser(loadedItem.getUserid(), user_mining, old_user_mining);
    			if(loadedItem.getInfo().matches("[0-9]+")){
    				insert.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getInfo()), quiz_mining, old_quiz_mining);
    			}
    			insert.setAction(loadedItem.getAction());
    			insert.setTimestamp(loadedItem.getTime());
    			if(insert.getQuiz() != null && insert.getUser() != null && loadedItem.getAction()!="review"){    
    				for (Quiz_grades_LMS loadedItem2 : quiz_grades_lms) 
    				{
    					long id = Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid());
    					if(loadedItem2.getQuiz() == Long.valueOf(loadedItem.getInfo()) && id == insert.getUser().getId() && loadedItem2.getTimemodified() == insert.getTimestamp()){
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
    				quizLogMining.put(insert.getId(), insert);
    			
    		}          	
       	}
    	
    	for( QuizLogMining r : quizLogMining.values())
        {
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
		return quizLogMining;
    }

    
	/**
	 * Creates all AssignmentLogMining objects using the tables "log" and "assignment_submissions" from Moodle's database.
	 * 
	 * @return	A HashMap of AssignmentLogMining, using the object's id as the key and the object itself as the value
	 */
    public HashMap<Long, AssignmentLogMining> generateAssignmentLogMining(){
	  
    	HashMap<Long, AssignmentLogMining> assignmentLogMining = new HashMap<Long, AssignmentLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	
    	for(Log_LMS loadedItem : log_lms) 
    	{
    		
    		 long uid = Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid());
    				 
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
		    
			insert.setId(assignmentLogMining.size() + 1 + assignmentLogMax);
			insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()), course_mining, old_course_mining);
			insert.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid()), user_mining, old_user_mining);
			insert.setAction(loadedItem.getAction());
			insert.setTimestamp(loadedItem.getTime());
			insert.setAssignment(Long.valueOf(platform.getPrefix() + "" + loadedItem.getInfo()), assignment_mining, old_assignment_mining);
			
			if(insert.getAssignment() != null && insert.getUser() != null && insert.getAction().equals("upload")){    
				for (Assignment_submissions_LMS loadedItem2 : assignment_submission_lms) 
				{
					long id = Long.valueOf(loadedItem2.getUserid());
					if(loadedItem2.getAssignment() == Long.valueOf(loadedItem.getInfo()) && id == loadedItem.getUserid() && loadedItem2.getTimemodified() == insert.getTimestamp()){
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
				assignmentLogMining.put(insert.getId(), insert);
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
    	
    	
    	for( AssignmentLogMining r : assignmentLogMining.values())
        {
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
    	
		return assignmentLogMining;
    }

  public HashMap<Long, ScormLogMining> generateScormLogMining(){
	  HashMap<Long, ScormLogMining> scormLogMining = new HashMap<Long, ScormLogMining>();
	  HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
  	    	
  	for (Log_LMS loadedItem : log_lms) 
  	{
  		 long uid = Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid());
 		
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
  		    insert.setId(scormLogMining.size() + 1 + scormLogMax);
  			insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()), course_mining, old_course_mining);
  			insert.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid()), user_mining, old_user_mining);
  			insert.setAction(loadedItem.getAction());
  			insert.setTimestamp(loadedItem.getTime());
  			if(loadedItem.getInfo().matches("[0-9]+")){
  				insert.setScorm(Long.valueOf(platform.getPrefix() + "" + loadedItem.getInfo()), scorm_mining, old_scorm_mining);
  			}
  			if(insert.getScorm() != null && insert.getCourse() != null && insert.getUser() != null)
  				scormLogMining.put(insert.getId(), insert);
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
  	for( ScormLogMining r : scormLogMining.values())
    {
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
  	
		return scormLogMining;
  }
  
    public HashMap<Long, QuizMining> generateQuizMining(){
    	
    	HashMap<Long, QuizMining> quiz_mining = new HashMap<Long, QuizMining>();
    	    	
    	for (Quiz_LMS loadedItem : quiz_lms) 
    	{
    		QuizMining insert = new QuizMining();
    		
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId())); 		
    		insert.setTitle(loadedItem.getName());
    		insert.setTimeopen(loadedItem.getTimeopen());
    		insert.setTimeclose(loadedItem.getTimeclose());
    		insert.setTimecreated(loadedItem.getTimecreated());
    		insert.setTimemodified(loadedItem.getTimemodified());
    		insert.setQtype("quiz");
        	for (Grade_items_LMS loadedItem2 : grade_items_lms) 
        	{
        		if(loadedItem2.getIteminstance() != null && loadedItem2.getItemmodule() != null){
        			logger.info("Iteminstance"+loadedItem2.getIteminstance()+" QuizId"+loadedItem.getId());
        			if(loadedItem.getId() == loadedItem2.getIteminstance().longValue() && loadedItem2.getItemmodule().equals("quiz")){
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
 
	/**
	 * Creates all AssignmentMining objects using the tables "assignment" from Moodle's database.
	 * 
	 * @return	A HashMap of AssignmentMining, using the object's id as the key and the object itself as the value
	 */
    public HashMap<Long, AssignmentMining> generateAssignmentMining(){
    	
    	HashMap<Long, AssignmentMining> assignment_mining = new HashMap<Long, AssignmentMining>();
    	    	
    	for (Assignment_LMS loadedItem : assignment_lms) 
    	{
    		AssignmentMining insert = new AssignmentMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId())); 
           	insert.setTitle(loadedItem.getName());
           	insert.setTimeopen(loadedItem.getTimeavailable());
           	insert.setTimeclose(loadedItem.getTimedue());
           	insert.setTimemodified(loadedItem.getTimemodified());
        	for (Grade_items_LMS loadedItem2 : grade_items_lms) 
        	{
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
 
    	for (Scorm_LMS loadedItem : scorm_lms) 
    	{
    		ScormMining insert = new ScormMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
           	insert.setTitle(loadedItem.getName());
           	insert.setTimemodified(loadedItem.getTimemodified());
    		insert.setMaxgrade(loadedItem.getMaxgrade());
    		
    		scorm_mining.put(insert.getId(), insert);
        }  
		return scorm_mining;    	
    }
    
    public HashMap<Long, QuizQuestionMining> generateQuizQuestionMining(){
    	
    	HashMap<Long, QuizQuestionMining> quiz_question_mining = new HashMap<Long, QuizQuestionMining>();
    	    	
    	for (Quiz_question_instances_LMS loadedItem : quiz_question_instances_lms) 
    	{
    		QuizQuestionMining insert = new QuizQuestionMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
           	insert.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem.getQuiz()), quiz_mining, old_quiz_mining);
           	insert.setQuestion(Long.valueOf(platform.getPrefix() + "" + loadedItem.getQuestion()), question_mining, old_question_mining);
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
    	    	
    	for (Question_LMS loadedItem : question_lms) 
    	{
    		QuestionMining insert = new QuestionMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
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
    	for (Grade_grades_LMS loadedItem : grade_grades_lms) 
    	{
    		QuizUserMining insert = new QuizUserMining();
        
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
           	if(loadedItem.getFinalgrade()!=null){
           		insert.setFinalgrade(loadedItem.getFinalgrade());
           	}
           	if(loadedItem.getRawgrade()!=null){
           		insert.setRawgrade(loadedItem.getRawgrade());	
           	}
           	if(loadedItem.getTimemodified()!=null){
           		insert.setTimemodified(loadedItem.getTimemodified());
           	}
           	
			insert.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid()), user_mining, old_user_mining);
           	for (Grade_items_LMS loadedItem2 : grade_items_lms) 
           	{
        		if(loadedItem2.getId() == loadedItem.getItemid() && loadedItem2.getIteminstance() != null){
        			insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem2.getCourseid()), course_mining, old_course_mining);
        			insert.setQuiz(Long.valueOf(platform.getPrefix() + "" + loadedItem2.getIteminstance()), quiz_mining, old_quiz_mining);
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
    	
        for ( Resource_LMS loadedItem : resource_lms) 
        {
            ResourceMining insert = new ResourceMining();
        
            insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
           	insert.setType(loadedItem.getType());
           	insert.setTitle(loadedItem.getName());
           	
           	//Get time of creation
        	
           	insert.setTimemodified(loadedItem.getTimemodified());
           	
       		resource.put(insert.getId(), insert);    		
        }
        
       	for ( Log_LMS loadedItem : log_lms) 
       	{
           	if(resource.get(loadedItem.getCmid()) != null && (resource.get(loadedItem.getCmid()).getTimecreated() == 0 || resource.get(loadedItem.getCmid()).getTimecreated() > loadedItem.getTime()))
           	{           		
           		resource.get(loadedItem.getCmid()).setTimecreated(loadedItem.getTime());
       		}
       	}           
		return resource;
    }
    
    
    public HashMap<Long, ResourceLogMining> generateResourceLogMining() {
    	HashMap<Long, ResourceLogMining> resourceLogMining = new HashMap<Long, ResourceLogMining>();
    	//A HashMap of list of timestamps. Every key represents one user, the according value is a list of his/her requests times.
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
        
        for ( Log_LMS loadedItem : log_lms) 
        {
            long uid = Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid());
    		
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
            	insert.setId(resourceLogMining.size() + 1 + resourceLogMax);
            	insert.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid()), user_mining, old_user_mining);
            	insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()), course_mining, old_course_mining);
            	insert.setAction(loadedItem.getAction()); 
            	
            	
            	
            	if(loadedItem.getInfo().matches("[0-9]+")){
    				insert.setResource(Long.valueOf(platform.getPrefix() + "" + loadedItem.getInfo()), resource_mining, old_resource_mining);
    			}    				
            	insert.setTimestamp(loadedItem.getTime());
    			if(insert.getResource() == null && !(loadedItem.getAction().equals("view all"))){
    				logger.info("In Resource_log_mining, resource not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo()+ " and action: " + loadedItem.getAction());
    			}
    			if(insert.getCourse() != null && insert.getResource() != null && insert.getUser() != null)
    				resourceLogMining.put(insert.getId(), insert);
    			
            }
        }
        //For 
        for( ResourceLogMining r : resourceLogMining.values())
        {
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
		return resourceLogMining;
    }
    
    
    public HashMap<Long, UserMining> generateUserMining(){
    	
    	HashMap<Long, UserMining> user_mining = new HashMap<Long, UserMining>();
    	    	
    	for (User_LMS loadedItem : user_lms) 
    	{
    		UserMining insert = new UserMining();
    		
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
           	insert.setLastlogin(loadedItem.getLastlogin());
           	insert.setFirstaccess(loadedItem.getFirstaccess());
           	insert.setLastaccess(loadedItem.getLastaccess());
           	insert.setCurrentlogin(loadedItem.getCurrentlogin());
           	user_mining.put(insert.getId(), insert);
        }
		return user_mining;    	
    }
    
    
    public HashMap<Long, WikiLogMining> generateWikiLogMining(){
    	HashMap<Long, WikiLogMining> wikiLogMining = new HashMap<Long, WikiLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	
        for (Log_LMS loadedItem : log_lms) 
        {
            long uid = Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid());
    		
    		
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
    			insert.setId(wikiLogMining.size() + 1 + wikiLogMax);
    			//Cannot tell, how to extract the correct wiki-id - so it'll always be null
    			insert.setWiki(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCmid()), wiki_mining, old_wiki_mining);
    			insert.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserid()), user_mining, old_user_mining);    			
    			insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()), course_mining, old_course_mining);
    			insert.setAction(loadedItem.getAction());
    			insert.setTimestamp(loadedItem.getTime());
    			
    			if(insert.getUser() != null && insert.getCourse() != null && insert.getWiki() != null)
    				wikiLogMining.put(insert.getId(), insert);
    		}
        }
        
        for( WikiLogMining r : wikiLogMining.values())
        {
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
    	return wikiLogMining;
    } 
    
    
	public HashMap<Long, WikiMining> generateWikiMining(){
    	
		HashMap<Long, WikiMining> wiki_mining = new HashMap<Long, WikiMining>();
    	
    	for ( Wiki_LMS loadedItem : wiki_lms) 
    	{
    		WikiMining insert = new WikiMining();
    	
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
    		insert.setTitle(loadedItem.getName());
    		insert.setSummary(loadedItem.getSummary());
    		insert.setTimemodified(loadedItem.getTimemodified());
    		
    		wiki_mining.put(insert.getId(), insert);
    	}
       	for ( Log_LMS loadedItem : log_lms) 
       	{
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
    	
    	for ( Role_LMS loadedItem : role_lms) 
    	{
    		RoleMining insert = new RoleMining();
    	
    		insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
    		insert.setName(loadedItem.getName());
    		insert.setShortname(loadedItem.getShortname());
    		insert.setDescription(loadedItem.getDescription());
    		insert.setSortorder(loadedItem.getSortorder());
    		
    		role_mining.put(insert.getId(), insert);
    	}
		return role_mining;
    }

	
	/**
	 * Creates all DegreeMining objects using the tables "course_categories" from Moodle's database.
	 * 
	 * @return	A HashMap of DegreeMining objects, using the object's id as the key and the object itself as the value
	 */
	public HashMap<Long, DegreeMining> generateDegreeMining() {
		HashMap<Long, DegreeMining> degree_mining = new HashMap<Long, DegreeMining>();
		
		for( CourseCategories_LMS loadedItem : course_categories_lms)
		{
			if(loadedItem.getDepth() == 2)
			{
				DegreeMining insert = new DegreeMining();
				insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				insert.setTitle(loadedItem.getTitle());
				degree_mining.put(insert.getId(), insert);
			}
		}
		return degree_mining;
	}

	/**
	 * Creates all DepartmentMining objects using the tables "course_categories" from Moodle's database.
	 * 
	 * @return	A HashMap of DepartmentMining objects, using the object's id as the key and the object itself as the value
	 */
	public HashMap<Long, DepartmentMining> generateDepartmentMining() {
		HashMap<Long, DepartmentMining> department_mining = new HashMap<Long, DepartmentMining>();
		
		for( CourseCategories_LMS loadedItem : course_categories_lms)
		{
			if(loadedItem.getDepth() == 1)
			{
				DepartmentMining insert = new DepartmentMining();
				insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				insert.setTitle(loadedItem.getTitle());
				department_mining.put(insert.getId(), insert);
			}
		}
		return department_mining;
	}

	/**
	 * Creates all DepartmentDegreeMining objects using the tables "course_categories" from Moodle's database.
	 * 
	 * @return	A HashMap of DepartmentDegreeMining objects, using the object's id as the key and the object itself as the value
	 */
	public HashMap<Long, DepartmentDegreeMining> generateDepartmentDegreeMining() {
		
		HashMap<Long, DepartmentDegreeMining> department_degree = new HashMap<Long, DepartmentDegreeMining>();
		
		for( CourseCategories_LMS loadedItem : course_categories_lms)
		{
			if(loadedItem.getDepth() == 2)
			{
				String[] s = loadedItem.getPath().split("/");
				if(s.length == 3)
				{
					DepartmentDegreeMining insert = new DepartmentDegreeMining();
					insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
					insert.setDegree(Long.valueOf(platform.getPrefix() + "" + s[s.length - 1]), degree_mining, old_degree_mining);
					insert.setDepartment(Long.valueOf(platform.getPrefix() + "" + s[s.length - 2]), department_mining, old_department_mining);
					if(insert.getDegree() != null && insert.getDepartment() != null)
						department_degree.put(insert.getId(), insert);
				}
			}
		}
		return department_degree;
	}


	/**
	 * Creates all DegreeCourseMining objects using the table "context" from Moodle's database.
	 * 
	 * @return	A HashMap of DegreeCourseMinings, using the object's id as the key and the object itself as the value
	 */
	public HashMap<Long, DegreeCourseMining> generateDegreeCourseMining() 
	{
		
		HashMap<Long, DegreeCourseMining> degree_course = new HashMap<Long, DegreeCourseMining>();
		
		for( Context_LMS loadedItem : context_lms)
		{
			if(loadedItem.getDepth() == 4 && loadedItem.getContextlevel() == 50)
			{
				String[] s = loadedItem.getPath().split("/");
				DegreeCourseMining insert = new DegreeCourseMining();
				insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
				insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getInstanceid()), course_mining, old_course_mining);
				for ( Context_LMS loadedItem2 : context_lms)
				{
					if(loadedItem2.getContextlevel() == 40 && loadedItem2.getId() == Integer.parseInt(s[3]))
					{
						insert.setDegree(Long.valueOf(platform.getPrefix() + "" + loadedItem2.getInstanceid()), degree_mining, old_degree_mining);
						break;
					}
				}
				if(insert.getDegree() != null && insert.getCourse() != null)
					degree_course.put(insert.getId(), insert);
			}
		}
		return degree_course;
	}

	/**
	 * Creates all ChatMining objects using the tables "chat" from Moodle's database.
	 * 
	 * @return	A HashMap of ChatMining, using the object's id as the key and the object itself as the value
	 */
	public HashMap<Long, ChatMining> generateChatMining() {
		HashMap<Long, ChatMining> chat_mining = new HashMap<Long, ChatMining>();
		
		for( Chat_LMS loadedItem : chat_lms)
		{
			ChatMining insert = new ChatMining();
		
			insert.setId(Long.valueOf(platform.getPrefix() + "" + loadedItem.getId()));
			insert.setChattime(loadedItem.getChattime());
			insert.setDescription(loadedItem.getDescription());
			insert.setTitle(loadedItem.getTitle());
			insert.setCourse(Long.valueOf(platform.getPrefix() + "" + loadedItem.getCourse()), course_mining, old_course_mining);
			
			
			chat_mining.put(insert.getId(), insert);
        }

		return chat_mining;
	}

	/**
	 * Creates all ChatLogMining objects using the tables "chat_log" from Moodle's database.
	 * 
	 * @return	A HashMap of ChatLogMining, using the object's id as the key and the object itself as the value
	 */
	public HashMap<Long, ChatLogMining> generateChatLogMining() {
		HashMap<Long, ChatLogMining> chatLogMining = new HashMap<Long, ChatLogMining>();
    	
        for ( ChatLog_LMS loadedItem : chat_log_lms) 
        {
        	ChatLogMining insert = new ChatLogMining();
        	
        	insert.setId(chatLogMining.size() + 1 + chatLogMax);
        	insert.setChat(Long.valueOf(platform.getPrefix() + "" + loadedItem.getChatId()), chat_mining, old_chat_mining);
        	insert.setMessage(loadedItem.getMessage());
        	insert.setTimestamp(loadedItem.getTimestamp());
        	insert.setCourse(Long.valueOf(platform.getPrefix() + "" + insert.getChat().getCourse().getId()), course_mining, old_course_mining);
        	insert.setUser(Long.valueOf(platform.getPrefix() + "" + loadedItem.getUserId()), user_mining, old_user_mining);
        	
  			if(insert.getUser()==null){
  				logger.info("In Chat_log_mining(chat part), user not found for log: " + loadedItem.getId() +" and user: " + loadedItem.getUserId());
  			}
  			if(insert.getChat()==null){
  				logger.info("In Chat_log_mining(chat part), chat not found for log: " + loadedItem.getId() +" and chat: " + loadedItem.getChatId());
  			}
  			if(insert.getChat() != null && insert.getUser() != null && insert.getCourse() != null)
  				chatLogMining.put(insert.getId(), insert);
  			
        }
		return chatLogMining;
	}
	
}