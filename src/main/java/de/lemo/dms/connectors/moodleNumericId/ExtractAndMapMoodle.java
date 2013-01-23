package de.lemo.dms.connectors.moodleNumericId;
//import miningDBclass.Config_mining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Assignment_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Assignment_submissions_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.ChatLog_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Chat_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Context_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.CourseCategories_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Course_LMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.Course_Modules_LMS;
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
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.ForumMining;
import de.lemo.dms.db.miningDBclass.GroupMining;
import de.lemo.dms.db.miningDBclass.GroupUserMining;
import de.lemo.dms.db.miningDBclass.LevelAssociationMining;
import de.lemo.dms.db.miningDBclass.LevelCourseMining;
import de.lemo.dms.db.miningDBclass.LevelMining;
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

/** The main class of the extraction process. 
 * Implementation of the abstract extract class for the LMS Moodle.
 */
public class ExtractAndMapMoodle extends ExtractAndMap {//Versionsnummer in Namen einf�gen

//LMS tables instances lists

	private  List<Log_LMS> logLms;
	private  List<Resource_LMS> resourceLms;
	private  List<Course_LMS> courseLms;
	private  List<Forum_LMS> forumLms;
	private  List<Wiki_LMS> wikiLms;
	private  List<User_LMS> userLms;
	private  List<Quiz_LMS> quizLms;
	private  List<Quiz_question_instances_LMS> quizQuestionInstancesLms;
	private  List<Question_LMS> questionLms;
	private  List<Groups_LMS> groupLms;
	private  List<Groups_members_LMS> groupMembersLms;
	private  List<Question_states_LMS> questionStatesLms;
	private  List<Forum_posts_LMS> forumPostsLms;
	private  List<Role_LMS> roleLms;
	private  List<Context_LMS> contextLms;
	private  List<Role_assignments_LMS> roleAssignmentsLms;
	private  List<Assignment_LMS> assignmentLms;	
	private  List<Assignment_submissions_LMS> assignmentSubmissionLms;
	private  List<Quiz_grades_LMS> quizGradesLms;
	private  List<Forum_discussions_LMS> forumDiscussionsLms;
	private  List<Scorm_LMS> scormLms;
	private  List<Grade_grades_LMS> gradeGradesLms;
	private  List<Grade_items_LMS> gradeItemsLms;
	private  List<Chat_LMS> chatLms;
	private  List<ChatLog_LMS> chatLogLms;
	private  List<CourseCategories_LMS> courseCategoriesLms;
	private  List<Course_Modules_LMS> courseModulesLms;
	
		
	private Logger logger = Logger.getLogger(getClass());
	private IConnector connector;
	
	public ExtractAndMapMoodle(IConnector connector) {
       super(connector);
       this.connector = connector; 
    }
	
	@SuppressWarnings("unchecked")	
	public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp) {  
	        Session session = MoodleNumericHibernateUtil.getSessionFactory(dbConf).openSession();
	        Transaction tx = session.beginTransaction();

	        // Just for testing. has to be set to Long.MaxValue if not longer needed.
	        long ceiling = Long.MAX_VALUE;
	        
	        // reading the LMS Database, create tables as lists of instances of the DB-table classes
	        
	        Query log = session.createQuery("from Log_LMS x where x.time>=:readingtimestamp and x.time<=:ceiling order by x.id asc");
	        log.setParameter("readingtimestamp", readingfromtimestamp);
	        log.setParameter("ceiling", ceiling);
	        logLms = log.list();	        
	        System.out.println("log_lms tables: " + logLms.size());       
	        
	    	Query resource = session.createQuery("from Resource_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	resource.setParameter("readingtimestamp", readingfromtimestamp);
	    	resource.setParameter("ceiling", ceiling);
	    	resourceLms = resource.list();		        
	    	System.out.println("resource_lms tables: " + resourceLms.size());
	    	
	      	Query courseMod = session.createQuery("from Course_Modules_LMS x order by x.id asc");
	    	courseModulesLms = courseMod.list();		        
	    	System.out.println("course_modules_lms tables: " + courseModulesLms.size());
	    	
	    	
	    	Query chat = session.createQuery("from Chat_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	chat.setParameter("readingtimestamp", readingfromtimestamp);
	    	chat.setParameter("ceiling", ceiling);
	    	chatLms = chat.list();		        
	    	System.out.println("chat_lms tables: " + chatLms.size());
	    	
	    	Query chatlog = session.createQuery("from ChatLog_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:ceiling order by x.id asc");
	    	chatlog.setParameter("readingtimestamp", readingfromtimestamp);
	    	chatlog.setParameter("ceiling", ceiling);
	    	chatLogLms = chatlog.list();		        
	    	System.out.println("chat_log_lms tables: " + chatLogLms.size());
	    	
	    	Query courseCategories = session.createQuery("from CourseCategories_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	courseCategories.setParameter("readingtimestamp", readingfromtimestamp);
	    	courseCategories.setParameter("ceiling", ceiling);
	    	courseCategoriesLms = courseCategories.list();		        
	    	System.out.println("course_categories_lms tables: " + courseCategoriesLms.size());
	    	
	    	
	    	Query course = session.createQuery("from Course_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	course.setParameter("readingtimestamp", readingfromtimestamp);
	    	course.setParameter("ceiling", ceiling);
	    	courseLms = course.list();		        	        
	    	System.out.println("course_lms tables: " + courseLms.size());
	    	
	    	Query forum_posts = session.createQuery("from Forum_posts_LMS x where x.modified>=:readingtimestamp and x.modified<=:ceiling order by x.id asc");
	    	forum_posts.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_posts.setParameter("ceiling", ceiling);
	    	forumPostsLms = forum_posts.list();	
	    	System.out.println("forum_posts_lms tables: " + forumPostsLms.size()); 
	    	
	    	Query forum = session.createQuery("from Forum_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	forum.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum.setParameter("ceiling", ceiling);
	    	forumLms = forum.list();		        
	    	System.out.println("forum_lms tables: " + forumLms.size());
	    	
	    	Query group = session.createQuery("from Groups_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	group.setParameter("readingtimestamp", readingfromtimestamp);
	    	group.setParameter("ceiling", ceiling);
	    	groupLms = group.list();	        
	    	System.out.println("group_lms tables: " + groupLms.size());
	    	
	    	Query quiz = session.createQuery("from Quiz_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	quiz.setParameter("readingtimestamp", readingfromtimestamp);
	    	quiz.setParameter("ceiling", ceiling);
	    	quizLms = quiz.list();		        
	    	System.out.println("quiz_lms tables: " + quizLms.size());	
	    	
	    	Query wiki = session.createQuery("from Wiki_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	wiki.setParameter("readingtimestamp", readingfromtimestamp);
	    	wiki.setParameter("ceiling", ceiling);
	    	wikiLms = wiki.list();		        
	    	System.out.println("wiki_lms tables: " + wikiLms.size());
	    	
	    	Query group_members = session.createQuery("from Groups_members_LMS x where x.timeadded>=:readingtimestamp and x.timeadded<=:ceiling order by x.id asc");
	    	group_members.setParameter("readingtimestamp", readingfromtimestamp);
	    	group_members.setParameter("ceiling", ceiling);
	    	groupMembersLms = group_members.list();		    	
	        System.out.println("group_members_lms tables: " + groupMembersLms.size());
	    	
	    	Query question_states = session.createQuery("from Question_states_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:ceiling order by x.id asc");
	    	question_states.setParameter("readingtimestamp", readingfromtimestamp);
	    	question_states.setParameter("ceiling", ceiling);
	    	questionStatesLms = question_states.list();	    	
	        System.out.println("question_states_lms tables: " + questionStatesLms.size());
	    	
	    	Query quiz_question_instances = session.createQuery("from Quiz_question_instances_LMS x order by x.id asc");
	    	quizQuestionInstancesLms = quiz_question_instances.list();		    	
	        System.out.println("quiz_question_instances_lms tables: " + quizQuestionInstancesLms.size()); 
	    	
	    	Query question = session.createQuery("from Question_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	question.setParameter("readingtimestamp", readingfromtimestamp);
	    	question.setParameter("ceiling", ceiling);
	    	questionLms = question.list();		    	
	        System.out.println("question_lms tables: " + questionLms.size());
	    	
  
	    	Query user = session.createQuery("from User_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	user.setParameter("ceiling", ceiling);
	    	user.setParameter("readingtimestamp", readingfromtimestamp);
	    	userLms = user.list();		    	
	        System.out.println("user_lms tables: " + userLms.size());	 
	    	
	    	Query role = session.createQuery("from Role_LMS x order by x.id asc");
	    	roleLms = role.list();
	        System.out.println("role_lms tables: " + roleLms.size());
	    	
	    	Query context = session.createQuery("from Context_LMS x order by x.id asc");
	    	contextLms = context.list();		    	
	        System.out.println("context_lms tables: " + contextLms.size());
	    	
	    	Query role_assignments = session.createQuery("from Role_assignments_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	role_assignments.setParameter("ceiling", ceiling);
	    	role_assignments.setParameter("readingtimestamp", readingfromtimestamp);
	    	roleAssignmentsLms = role_assignments.list();		    	
	        System.out.println("role_assignments_lms tables: " + roleAssignmentsLms.size());
	    	
	    	Query assignments = session.createQuery("from Assignment_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	assignments.setParameter("ceiling", ceiling);
	    	assignments.setParameter("readingtimestamp", readingfromtimestamp);
	    	assignmentLms = assignments.list();		    	
	        System.out.println("assignment_lms tables: " + assignmentLms.size());
	    	
	        
	    	Query assignment_submission = session.createQuery("from Assignment_submissions_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	assignment_submission.setParameter("ceiling", ceiling);
	    	assignment_submission.setParameter("readingtimestamp", readingfromtimestamp);
	    	assignmentSubmissionLms = assignment_submission.list();		    	
	        System.out.println("assignment_submission_lms tables: " + assignmentSubmissionLms.size());


	    	Query quiz_grades = session.createQuery("from Quiz_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	quiz_grades.setParameter("ceiling", ceiling);
	    	quiz_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	quizGradesLms = quiz_grades.list();		    	
	        System.out.println("quiz_grades_lms tables: " + quizGradesLms.size());
	        
	    	Query forum_discussions = session.createQuery("from Forum_discussions_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	forum_discussions.setParameter("ceiling", ceiling);
	    	forum_discussions.setParameter("readingtimestamp", readingfromtimestamp);
	    	forumDiscussionsLms = forum_discussions.list();		    	
	        System.out.println("forum_discussions_lms tables: " + forumDiscussionsLms.size());	        

	    	Query scorm = session.createQuery("from Scorm_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	scorm.setParameter("ceiling", ceiling);
	    	scorm.setParameter("readingtimestamp", readingfromtimestamp);
	    	scormLms = scorm.list();		    	
	        System.out.println("scorm_lms tables: " + scormLms.size());	        

	    	Query grade_grades = session.createQuery("from Grade_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	grade_grades.setParameter("ceiling", ceiling);
	    	grade_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	gradeGradesLms = grade_grades.list();		    	
	        System.out.println("grade_grades_lms tables: " + gradeGradesLms.size());		        

	    	Query grade_items = session.createQuery("from Grade_items_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	grade_items.setParameter("ceiling", ceiling);
	    	grade_items.setParameter("readingtimestamp", readingfromtimestamp);
	    	gradeItemsLms = grade_items.list();		    	
	        System.out.println("grade_items_lms tables: " + gradeItemsLms.size());
	        	        
//hibernate session finish and close
	        tx.commit();        
	        session.close();
	        
	}

	@SuppressWarnings("unchecked")
	public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp, long readingtotimestamp) {
	        Session session = MoodleNumericHibernateUtil.getSessionFactory(dbConf).openSession();
	        Transaction tx = session.beginTransaction();
	        
	       

            // reading the LMS Database, create tables as lists of instances of the DB-table classes

	        if(userLms == null){

	        	Query resource = session.createQuery("from Resource_LMS x order by x.id asc");
	        	resourceLms = resource.list();	
	        	System.out.println("Resource tables: " + resourceLms.size());	  
	    	
	        	Query course = session.createQuery("from Course_LMS x order by x.id asc");
	        	courseLms = course.list();		     
	        	System.out.println("Course_LMS tables: " + courseLms.size());	  
	        	
	        	Query chat = session.createQuery("from Chat_LMS x order by x.id asc");
		    	chatLms = chat.list();		       
		    	System.out.println("Chat_LMS tables: " + chatLms.size());	
		    			    	
		    	Query courseCategories = session.createQuery("from CourseCategories_LMS x order by x.id asc");
		    	courseCategoriesLms = courseCategories.list();		  
		    	System.out.println("CourseCategories_LMS tables: " + courseCategoriesLms.size());	
	    	
	        	Query forum = session.createQuery("from Forum_LMS x order by x.id asc");
	        	forumLms = forum.list();	
	        	System.out.println("Forum_LMS tables: " + forumLms.size());	
	        	
		      	Query courseMod = session.createQuery("from Course_Modules_LMS x order by x.id asc");
		    	courseModulesLms = courseMod.list();		        
		    	System.out.println("course_modules_lms tables: " + courseModulesLms.size());
	    	
	        	Query group = session.createQuery("from Groups_LMS x order by x.id asc");
	        	groupLms = group.list();	        
	        	System.out.println("Groups_LMS tables: " + groupLms.size());	
	    	
	        	Query quiz = session.createQuery("from Quiz_LMS x order by x.id asc");
	        	quizLms = quiz.list();		
	        	System.out.println("Quiz_LMS tables: " + quizLms.size());	

	        	Query wiki = session.createQuery("from Wiki_LMS x order by x.id asc");
	        	wikiLms = wiki.list();		
	        	System.out.println("Wiki_LMS tables: " + wikiLms.size());	
	    	
	        	Query quiz_question_instances = session.createQuery("from Quiz_question_instances_LMS x order by x.id asc");
	        	quizQuestionInstancesLms = quiz_question_instances.list();		 
	        	System.out.println("Quiz_question_instances_LMS tables: " + quizQuestionInstancesLms.size());	
	    	
	        	Query question = session.createQuery("from Question_LMS x order by x.id asc");
	        	questionLms = question.list();		
	        	System.out.println("Question_LMS tables: " + questionLms.size());	
    	
	        	Query user = session.createQuery("from User_LMS x order by x.id asc");
	        	userLms = user.list();		   
	        	System.out.println("User_LMS tables: " + userLms.size());	
	    	
	        	Query role = session.createQuery("from Role_LMS x order by x.id asc");
	        	roleLms = role.list();		
	        	System.out.println("Role_LMS tables: " + roleLms.size());	

	        	session.clear();
	    	
	        	Query context = session.createQuery("from Context_LMS x order by x.id asc");
	        	contextLms = context.list();
	        	System.out.println("Context_LMS tables: " + contextLms.size());	
	    	
	        	Query assignments = session.createQuery("from Assignment_LMS x order by x.id asc");
	        	assignmentLms = assignments.list();	
	        	System.out.println("Assignment_LMS tables: " + assignmentLms.size());	
	        	
		    	Query scorm = session.createQuery("from Scorm_LMS x order by x.id asc");
		    	scormLms = scorm.list();		
		    	System.out.println("Scorm_LMS tables: " + scormLms.size());	
		    	
		    	Query grade_items = session.createQuery("from Grade_items_LMS x order by x.id asc");
		    	gradeItemsLms = grade_items.list();
		    	System.out.println("Grade_items_LMS tables: " + gradeItemsLms.size());	
	        }
	    	
	    	Query log = session.createQuery("from Log_LMS x where x.time>=:readingtimestamp and x.time<=:readingtimestamp2 order by x.id asc");
	        log.setParameter("readingtimestamp", readingfromtimestamp);
	        log.setParameter("readingtimestamp2", readingtotimestamp);
	        logLms = log.list();	  
	        System.out.println("Log_LMS tables: " + logLms.size());	
	        
	    	Query chatlog = session.createQuery("from ChatLog_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:readingtimestamp2 order by x.id asc");
	    	chatlog.setParameter("readingtimestamp", readingfromtimestamp);
	    	chatlog.setParameter("readingtimestamp2", readingtotimestamp);	
	    	chatLogLms = chatlog.list();	
	    	System.out.println("ChatLog_LMS tables: " + chatLogLms.size());	
	    	
	    	Query forum_posts = session.createQuery("from Forum_posts_LMS x where x.created>=:readingtimestamp and x.created<=:readingtimestamp2 order by x.id asc");
	    	forum_posts.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_posts.setParameter("readingtimestamp2", readingtotimestamp);	    	
	    	forumPostsLms = forum_posts.list();
	    	System.out.println("Forum_posts_LMS tables: " + forumPostsLms.size());	
	    	
	    	Query forum_posts_modified = session.createQuery("from Forum_posts_LMS x where x.modified>=:readingtimestamp and x.modified<=:readingtimestamp2 order by x.id asc");
	    	forum_posts_modified.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_posts_modified.setParameter("readingtimestamp2", readingtotimestamp);
	    	forumPostsLms.addAll(forum_posts_modified.list());
	    	System.out.println("Forum_posts_LMS tables: " + forumPostsLms.size());	

	        session.clear();
	    	
	    	Query group_members = session.createQuery("from Groups_members_LMS x where x.timeadded>=:readingtimestamp and x.timeadded<=:readingtimestamp2 order by x.id asc");
	    	group_members.setParameter("readingtimestamp", readingfromtimestamp);
	    	group_members.setParameter("readingtimestamp2", readingtotimestamp);
	    	groupMembersLms = group_members.list();		 
	    	System.out.println("Groups_members_LMS tables: " + groupMembersLms.size());	
	    	
	    	Query question_states = session.createQuery("from Question_states_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:readingtimestamp2 order by x.id asc");
	    	question_states.setParameter("readingtimestamp", readingfromtimestamp);
	    	question_states.setParameter("readingtimestamp2", readingtotimestamp);
	    	questionStatesLms = question_states.list();	
	    	System.out.println("Question_states_LMS tables: " + questionStatesLms.size());	
	    	
 	
	    	
	    	Query role_assignments = session.createQuery("from Role_assignments_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	role_assignments.setParameter("readingtimestamp", readingfromtimestamp);
	    	role_assignments.setParameter("readingtimestamp2", readingtotimestamp);
	    	roleAssignmentsLms = role_assignments.list();		 
	    	System.out.println("Role_assignments_LMS tables: " + roleAssignmentsLms.size());	
	    		    	
	    
	    	Query assignment_submission = session.createQuery("from Assignment_submissions_LMS x where x.timecreated>=:readingtimestamp and x.timecreated<=:readingtimestamp2 order by x.id asc");
	    	assignment_submission.setParameter("readingtimestamp", readingfromtimestamp);
	    	assignment_submission.setParameter("readingtimestamp2", readingtotimestamp);
	    	assignmentSubmissionLms = assignment_submission.list();	
	    	System.out.println("Assignment_submissions_LMS tables: " + assignmentSubmissionLms.size());	


	    	Query quiz_grades = session.createQuery("from Quiz_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	quiz_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	quiz_grades.setParameter("readingtimestamp2", readingtotimestamp);
	    	quizGradesLms = quiz_grades.list();		  
	    	System.out.println("Quiz_grades_LMS tables: " + quizGradesLms.size());	

	    	Query forum_discussions = session.createQuery("from Forum_discussions_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	forum_discussions.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_discussions.setParameter("readingtimestamp2", readingtotimestamp);
	    	forumDiscussionsLms = forum_discussions.list();		    	
	    	System.out.println("Forum_discussions_LMS tables: " + forumDiscussionsLms.size());
    	
	    	Query grade_grades = session.createQuery("from Grade_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	grade_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	grade_grades.setParameter("readingtimestamp2", readingtotimestamp);
	    	gradeGradesLms = grade_grades.list();
	    	System.out.println("Grade_grades_LMS tables: " + gradeGradesLms.size());	
	        	        
	        session.clear();
	        
	    	
//hibernate session finish and close
	        tx.commit();
	        session.close();
 
	}
	
	public void clearLMStables(){
		logLms.clear();
		resourceLms.clear();
		courseLms.clear();
		forumLms.clear();
		wikiLms.clear();
		userLms.clear();
		quizLms.clear();
		gradeGradesLms.clear();
		groupLms.clear();
		groupMembersLms.clear();
		questionStatesLms.clear();
		forumPostsLms.clear();
		roleLms.clear();
		roleAssignmentsLms.clear();
		assignmentSubmissionLms.clear();
	}
	
//methods for create and fill the mining-table instances
    
    public HashMap<Long, CourseUserMining> generateCourseUserMining(){
    	
    	HashMap<Long, CourseUserMining> course_user_mining = new HashMap<Long, CourseUserMining>();
    	    	
    	for (Context_LMS loadedItem : contextLms) 
    	{
       		if(loadedItem.getContextlevel() == 50){
       			for (Role_assignments_LMS loadedItem2 : roleAssignmentsLms) 
       			{
       	       		if(loadedItem2.getContextid() == loadedItem.getId()){
       	       			CourseUserMining insert = new CourseUserMining();
       	       			insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getId()));
       	       			insert.setRole(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getRoleid()), roleMining, oldRoleMining);
       	       			insert.setPlatform(connector.getPlatformId());
       	       			insert.setUser(loadedItem2.getUserid(), userMining, oldUserMining);   	       			
       	       			insert.setEnrolstart(loadedItem2.getTimestart());
       	       			insert.setEnrolend(loadedItem2.getTimeend());
       	                insert.setCourse(Long.valueOf(connector.getPrefix() + "" +  loadedItem.getInstanceid()), courseMining, oldCourseMining);
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
    	    	
    	for (Forum_LMS loadedItem : forumLms) 
    	{
    		CourseForumMining insert = new CourseForumMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" +  loadedItem.getId()));
            insert.setCourse(Long.valueOf(connector.getPrefix() +"" + loadedItem.getCourse()),courseMining, oldCourseMining);
            insert.setForum(Long.valueOf(connector.getPrefix() +  "" + loadedItem.getId()),forumMining, oldForumMining);
            insert.setPlatform(connector.getPlatformId());
            if(insert.getCourse()!= null && insert.getForum()!= null){
            	course_forum_mining.put(insert.getId(), insert);
            }
        }
		return course_forum_mining;
    }
    
    
    public HashMap<Long, CourseMining> generateCourseMining() {
    	
    	HashMap<Long, CourseMining> course_mining = new HashMap<Long, CourseMining>();
       	for (Course_LMS loadedItem : courseLms) 
       	{
        	CourseMining insert = new CourseMining();
        
        	insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
        	insert.setStartDate(loadedItem.getStartdate());
        	insert.setEnrolStart(loadedItem.getEnrolstartdate());
        	insert.setEnrolEnd(loadedItem.getEnrolenddate());
        	insert.setTimeCreated(loadedItem.getTimecreated());
        	insert.setTimeModified(loadedItem.getTimemodified());
        	insert.setTitle(loadedItem.getFullname());
        	insert.setShortname(loadedItem.getShortname());
        	insert.setPlatform(connector.getPlatformId());
        	
        	course_mining.put(insert.getId(), insert);
        }
		return course_mining;
    }    
    
    
	public HashMap<Long, CourseGroupMining> generateCourseGroupMining(){
    	
		HashMap<Long, CourseGroupMining> course_group_mining = new HashMap<Long, CourseGroupMining>();
    	    	
    	for (Groups_LMS loadedItem : groupLms) 
    	{
    		CourseGroupMining insert = new CourseGroupMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
            insert.setGroup(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()),groupMining, oldGroupMining);
            insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourseid()),courseMining, oldCourseMining);
            insert.setPlatform(connector.getPlatformId());
            if(insert.getCourse()!= null && insert.getGroup()!= null){
            	course_group_mining.put(insert.getId(), insert);
            }
        }
		return course_group_mining;
    }
    
    
    public HashMap<Long, CourseQuizMining> generateCourseQuizMining(){
    	
    	HashMap<Long, CourseQuizMining> course_quiz_mining = new HashMap<Long, CourseQuizMining>();
    	    	
    	for (Quiz_LMS loadedItem : quizLms) 
    	{
    		CourseQuizMining insert = new CourseQuizMining();
    	
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
    		insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()),courseMining, oldCourseMining);
    		insert.setQuiz(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()), quizMining, oldQuizMining);
    		insert.setPlatform(connector.getPlatformId());
    		if(insert.getCourse() != null && insert.getQuiz() != null){            
					course_quiz_mining.put(insert.getQuiz().getId(), insert);
    		}
    		if(insert.getQuiz() == null){
    			logger.info("In Course_quiz_mining, quiz(quiz) not found: " + loadedItem.getId());
	    		}   
        }    	
		return course_quiz_mining;
    }

   public HashMap<Long, CourseAssignmentMining> generateCourseAssignmentMining(){
    	
	   HashMap<Long, CourseAssignmentMining> course_assignment_mining = new HashMap<Long, CourseAssignmentMining>();
    	    	   	
    	for (Assignment_LMS loadedItem : assignmentLms)
    	{
    		CourseAssignmentMining insert = new CourseAssignmentMining();
    	
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
            insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()),courseMining, oldCourseMining);
            insert.setPlatform(connector.getPlatformId());
			if(insert.getCourse()==null){
				logger.info("course not found for course-assignment: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
			}
            insert.setAssignment(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()), assignmentMining, oldAssignmentMining);
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
	   	for (Scorm_LMS loadedItem : scormLms) 
	   	{
	   		CourseScormMining insert = new CourseScormMining();
	   	
	   		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
	   		insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()),courseMining, oldCourseMining);
	   		insert.setScorm(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()), scormMining, oldScormMining);
	   		insert.setPlatform(connector.getPlatformId());
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
    	    	
    	for (Resource_LMS loadedItem : resourceLms) 
    	{
    		CourseResourceMining insert = new CourseResourceMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
            insert.setCourse(Long.valueOf(connector.getPrefix() + "" +  loadedItem.getCourse()), courseMining, oldCourseMining);
            insert.setResource(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()), resourceMining, oldResourceMining);
            insert.setPlatform(connector.getPlatformId());
            if(insert.getCourse()!= null && insert.getResource() != null){  
            	course_resource_mining.put(insert.getId(), insert);
            }
        }
		return course_resource_mining;
    }
    
    
    public HashMap<Long, CourseLogMining> generateCourseLogMining(){
    	HashMap<Long, CourseLogMining> courseLogMining = new HashMap<Long, CourseLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	
    	for (Log_LMS loadedItem : logLms )
    	{
    		
    		 long uid = Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid());
     		
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
    		
    		if(loadedItem.getModule().equals("course"))
    		{    		
    			CourseLogMining insert = new CourseLogMining();
    			
    			insert.setId(courseLogMining.size() + 1 + courseLogMax);

    			insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()), courseMining, oldCourseMining);
    			insert.setPlatform(connector.getPlatformId());
    			insert.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid()), userMining, oldUserMining);
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
    
    
    public HashMap<Long, CourseWikiMining> generateCourseWikiMining(){
    	
    	HashMap<Long, CourseWikiMining> course_wiki_mining = new HashMap<Long, CourseWikiMining>();
    	    	
    	for (Wiki_LMS loadedItem : wikiLms) 
    	{
    		CourseWikiMining insert = new CourseWikiMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
            insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()), courseMining, oldCourseMining);
            insert.setWiki(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()), wikiMining, oldWikiMining);
            insert.setPlatform(connector.getPlatformId());
            if(insert.getCourse()!= null && insert.getWiki()!= null){
            	course_wiki_mining.put(insert.getId(), insert);
            }
        }
		return course_wiki_mining;
    }

    
    public HashMap<Long, ForumLogMining> generateForumLogMining() {
    	HashMap<Long, ForumLogMining> forumLogMining = new HashMap<Long, ForumLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	HashMap<Long, Forum_discussions_LMS> forumDis = new HashMap<Long, Forum_discussions_LMS>();
    	
    	for(Forum_discussions_LMS fd : forumDiscussionsLms)
    	{
    		forumDis.put(fd.getId(), fd);
    	}
    	    		
    	for (Log_LMS loadedItem : logLms) {
    		
            long uid = Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid());
    		 
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
    			insert.setPlatform(connector.getPlatformId());
    			insert.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid()), userMining, oldUserMining);
    			if((loadedItem.getAction().equals("view forum") || loadedItem.getAction().equals("subscribe")) && loadedItem.getInfo().matches("[0-9]+")){
    				insert.setForum(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInfo()), forumMining, oldForumMining);
    			}
    			else{
    				if((loadedItem.getAction().equals("add discussion") || loadedItem.getAction().equals("view discussion")) && loadedItem.getInfo().matches("[0-9]+")){
   			    		if(forumDis.get(Long.valueOf(loadedItem.getId())) != null)
    			    			insert.setForum(Long.valueOf(connector.getPrefix() + "" + forumDis.get(Long.valueOf(loadedItem.getId())).getForum()), forumMining, oldForumMining);
    				}
    			}
    			insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()), courseMining, oldCourseMining);
    			insert.setAction(loadedItem.getAction());
   				for (Forum_posts_LMS loadedItem2 : forumPostsLms) 
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
	
    	for ( Forum_LMS loadedItem : forumLms)
    	{
   	    	ForumMining insert = new ForumMining();
    	
   	    	insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
            insert.setTimeModified(loadedItem.getTimemodified());
   	    	insert.setTitle(loadedItem.getName());
   	    	insert.setSummary(loadedItem.getIntro());   
   	    	insert.setPlatform(connector.getPlatformId());
   	    	forum_mining.put(insert.getId(), insert);  
    	} 
    	
       	for ( Log_LMS loadedItem : logLms) 
       	{
           	if(forum_mining.get(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCmid())) != null && (forum_mining.get(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCmid())).getTimeCreated() == 0 || forum_mining.get(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCmid())).getTimeCreated() > loadedItem.getTime()))
           	{           		
           		forum_mining.get(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCmid())).setTimeCreated(loadedItem.getTime());
       		}
       	}   
		return forum_mining;
    }

    
	public HashMap<Long, GroupUserMining> generateGroupUserMining(){
    	
		HashMap<Long, GroupUserMining> group_members_mining = new HashMap<Long, GroupUserMining>();
    	    	
    	for (Groups_members_LMS loadedItem : groupMembersLms) 
    	{
    		GroupUserMining insert = new GroupUserMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
           	insert.setGroup(Long.valueOf(connector.getPrefix() + "" + loadedItem.getGroupid()), groupMining, oldGroupMining);
           	
          	insert.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid()), userMining, oldUserMining);
           	insert.setTimestamp(loadedItem.getTimeadded());
           	insert.setPlatform(connector.getPlatformId());
           	if(insert.getUser() != null && insert.getGroup() != null)
           		group_members_mining.put(insert.getId(), insert);
        }
		return group_members_mining;
    }
	
	
    public HashMap<Long, GroupMining> generateGroupMining(){
    	
    	HashMap<Long, GroupMining> group_mining = new HashMap<Long, GroupMining>();
    	    	
    	for (Groups_LMS loadedItem : groupLms)
    	{
    		GroupMining insert = new GroupMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
           	insert.setTimeCreated(loadedItem.getTimecreated());
           	insert.setTimeModified(loadedItem.getTimemodified());
           	insert.setPlatform(connector.getPlatformId());
           	group_mining.put(insert.getId(), insert);
        }
		return group_mining;    	
    }
    
    
    public HashMap<Long, QuestionLogMining> generateQuestionLogMining(){
    	HashMap<Long, QuestionLogMining> questionLogMiningtmp = new HashMap<Long, QuestionLogMining>();
    	HashMap<Long, QuestionLogMining> questionLogMining = new HashMap<Long, QuestionLogMining>();
    	HashMap<String, Long> timestampIdMap = new HashMap<String, Long>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	    	
    	for (Question_states_LMS loadedItem : questionStatesLms) {
    		
    		QuestionLogMining insert = new QuestionLogMining();
    	
    		insert.setId(questionLogMiningtmp.size() + 1 + questionLogMax); //ID
			insert.setQuestion(Long.valueOf(connector.getPrefix() + "" + loadedItem.getQuestion()), questionMining, oldQuestionMining); //Question 
			insert.setPenalty(loadedItem.getPenalty());	
			insert.setAnswers(loadedItem.getAnswer());
			insert.setTimestamp(loadedItem.getTimestamp());			
			insert.setPlatform(connector.getPlatformId());
			
			
			//Set Grades
			if(loadedItem.getEvent() == 3 || loadedItem.getEvent() == 6 || loadedItem.getEvent() == 9){
				insert.setRawGrade(loadedItem.getRaw_grade());
				insert.setFinalGrade(loadedItem.getGrade());
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
            if(insert.getQuestion() != null && quizQuestionMining.get(insert.getQuestion().getId()) != null )
            {
            	insert.setQuiz(quizQuestionMining.get(insert.getQuestion().getId()).getQuiz());
            	if(courseQuizMining.get(insert.getQuiz().getId()) != null)
            		insert.setCourse(courseQuizMining.get(insert.getQuiz().getId()).getCourse());
            }
            else if(insert.getQuestion() != null && oldQuizQuestionMining.get(insert.getQuestion().getId()) != null && oldCourseQuizMining.get(insert.getQuiz().getId()) != null)
            {
            	insert.setQuiz(oldQuizQuestionMining.get(insert.getQuestion().getId()).getQuiz());
            	if(oldCourseQuizMining.get(insert.getQuiz().getId()) != null)
            		insert.setCourse(courseQuizMining.get(insert.getQuiz().getId()).getCourse());
            }
            /*
			for(Quiz_question_instances_LMS loadedItem1 : quiz_question_instances_lms)
			{
				if(loadedItem1.getQuestion() == (loadedItem.getQuestion())){
					insert.setQuiz(Long.valueOf(connector.getPrefix() + "" + loadedItem1.getQuiz()), quiz_mining, old_quiz_mining);
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
			for(Question_LMS loadedItem2 : questionLms)
			{
				if(loadedItem2.getId() == (loadedItem.getQuestion())){
					insert.setType(loadedItem2.getQtype());//Type
					break;
				}
			}			
			if(insert.getType() == null && oldQuestionMining.get(loadedItem.getQuestion()) != null)
				insert.setType(oldQuestionMining.get(loadedItem.getQuestion()).getType());			
			if(insert.getType() == null){
				logger.info("In Question_log_mining, type not found for question_states: " + loadedItem.getId() +" and question: " + loadedItem.getQuestion() +" question list size: "+ questionLms.size() );
			}  	
							
			

			if(insert.getQuestion() != null && insert.getQuiz() != null && insert.getCourse() != null)
			{
				
				timestampIdMap.put(insert.getTimestamp() + " " + insert.getQuiz().getId(), insert.getId());
    			questionLogMiningtmp.put(insert.getId(), insert);
			}			
    	}	  
    	
    	//Set Course and 
    	for(Log_LMS loadedItem : logLms)
    	{
    		 long uid1 = Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid());
     		
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
    		
    		if(uid1 != -1 && loadedItem.getModule().equals("quiz") && timestampIdMap.get(loadedItem.getTime() + " " + connector.getPrefix() + "" + loadedItem.getInfo()) != null){
    			{
    				QuestionLogMining qlm = questionLogMiningtmp.get(timestampIdMap.get(loadedItem.getTime() + " " + connector.getPrefix() + "" + loadedItem.getInfo()));
    				qlm.setUser(uid1, userMining, oldUserMining);
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
    	    	
    	for (Log_LMS loadedItem : logLms) {
    		
    		 long uid = Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid()); 
     		
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
    		if(loadedItem.getModule().equals("quiz"))
    		{
    			QuizLogMining insert = new QuizLogMining();
    			
    		    insert.setId(quizLogMining.size() + 1 + quizLogMax);
    			insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()), courseMining, oldCourseMining);
    			insert.setPlatform(connector.getPlatformId());
    			
    			insert.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid()), userMining, oldUserMining);
    			if(loadedItem.getInfo().matches("[0-9]+"))
    			{
    				insert.setQuiz(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInfo()), quizMining, oldQuizMining);
    			}
    			insert.setAction(loadedItem.getAction());
    			insert.setTimestamp(loadedItem.getTime());
    			if(insert.getQuiz() != null && insert.getUser() != null && loadedItem.getAction() != "review")
    			{    
    				for (Quiz_grades_LMS loadedItem2 : quizGradesLms) 
    				{
    					long id = Long.valueOf(connector.getPrefix() + "" + loadedItem2.getUserid());
    					if(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getQuiz()) == insert.getQuiz().getId() && id == insert.getUser().getId() && loadedItem2.getTimemodified() == insert.getTimestamp()){
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

    public HashMap<Long, AssignmentLogMining> generateAssignmentLogMining(){
	  
    	HashMap<Long, AssignmentLogMining> assignmentLogMining = new HashMap<Long, AssignmentLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	HashMap<Long, ArrayList<Assignment_submissions_LMS>> asSub = new HashMap<Long, ArrayList<Assignment_submissions_LMS>>();
    	
    	//Arranging all assignment_submissions due to performance issues
    	for(Assignment_submissions_LMS as : assignmentSubmissionLms)
    	{
    		if(asSub.get(as.getAssignment()) == null)
    		{
    			ArrayList<Assignment_submissions_LMS> a = new ArrayList<Assignment_submissions_LMS>();
    			a.add(as);
    			asSub.put(as.getAssignment(), a);
    		}
    		else
    			asSub.get(as.getAssignment()).add(as);
    		
    	}
    		
    	
    	for(Log_LMS loadedItem : logLms) {
    		
    		 long uid = Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid());
    				 
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
			if(loadedItem.getModule().equals("assignment") && loadedItem.getInfo().matches("[0-9]++"))
			{
				AssignmentLogMining insert = new AssignmentLogMining();
			    insert.setId(assignmentLogMining.size() + 1 + assignmentLogMax);
				insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()), courseMining, oldCourseMining);
				
				insert.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid()), userMining, oldUserMining);
				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());
				insert.setAssignment(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInfo()), assignmentMining, oldAssignmentMining);
				
	
				if(insert.getAssignment() != null && insert.getUser() != null && insert.getCourse() != null)//&& insert.getAction().equals("upload"))
				{   
					if(asSub.get(Long.valueOf(loadedItem.getInfo())) != null)
					for (Assignment_submissions_LMS loadedItem2 : asSub.get(Long.valueOf(loadedItem.getInfo()))) 
					{						
						if(loadedItem2.getAssignment() == Long.valueOf(loadedItem.getInfo()) && loadedItem2.getUserid().equals(loadedItem.getUserid()))// && loadedItem2.getTimemodified() == loadedItem.getTime())
				{

							insert.setGrade(Double.valueOf(loadedItem2.getGrade()));
							break;
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
				insert.setPlatform(connector.getPlatformId());
				
			if(insert.getUser() != null && insert.getAssignment() != null && insert.getCourse() != null)
				assignmentLogMining.put(insert.getId(), insert);
    			}    			
    	    	}
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
  	    	
  	for (Log_LMS loadedItem : logLms ) {
  		
  		 long uid = Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid());
 		
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
  			insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()), courseMining, oldCourseMining);
			insert.setPlatform(connector.getPlatformId());
  			
			insert.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid()), userMining, oldUserMining);
  			insert.setAction(loadedItem.getAction());
  			insert.setTimestamp(loadedItem.getTime());
  			if(loadedItem.getInfo().matches("[0-9]+")){
  				insert.setScorm(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInfo()), scormMining, oldScormMining);

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
    	    	
    	for (Quiz_LMS loadedItem : quizLms) 
    	{

    		QuizMining insert = new QuizMining();
    		
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId())); 		
    		insert.setTitle(loadedItem.getName());
    		insert.setTimeOpen(loadedItem.getTimeopen());
    		insert.setTimeClose(loadedItem.getTimeclose());
    		insert.setTimeCreated(loadedItem.getTimecreated());
    		insert.setTimeModified(loadedItem.getTimemodified());
    		insert.setQtype("quiz");
			insert.setPlatform(connector.getPlatformId());
        	for (Grade_items_LMS loadedItem2 : gradeItemsLms) 
        	{
        		if(loadedItem2.getIteminstance() != null && loadedItem2.getItemmodule() != null){
        			logger.info("Iteminstance"+loadedItem2.getIteminstance()+" QuizId"+loadedItem.getId());
        			if(loadedItem.getId()==loadedItem2.getIteminstance().longValue() && loadedItem2.getItemmodule().equals("quiz")){
        				insert.setMaxGrade(loadedItem2.getGrademax());
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
    	    	
    	for (Assignment_LMS loadedItem : assignmentLms) 
    	{
    		AssignmentMining insert = new AssignmentMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId())); 
           	insert.setTitle(loadedItem.getName());
           	insert.setTimeOpen(loadedItem.getTimeavailable());
           	insert.setTimeClose(loadedItem.getTimedue());
           	insert.setTimeModified(loadedItem.getTimemodified());
           	insert.setPlatform(connector.getPlatformId());
        	for (Grade_items_LMS loadedItem2 : gradeItemsLms) 
        	{
        		if(loadedItem2.getIteminstance() != null && loadedItem2.getItemmodule() != null)
        		{
        			logger.info("Iteminstance " + loadedItem2.getIteminstance() + " AssignmentId" + loadedItem.getId());
        			if(loadedItem.getId() == loadedItem2.getIteminstance().longValue() && loadedItem2.getItemmodule().equals("assignment")){
        				insert.setMaxGrade(loadedItem2.getGrademax());
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
 
    	for (Scorm_LMS loadedItem : scormLms) 
    	{
    		ScormMining insert = new ScormMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
           	insert.setTitle(loadedItem.getName());
           	insert.setTimeModified(loadedItem.getTimemodified());
    		insert.setMaxGrade(loadedItem.getMaxgrade());
			insert.setPlatform(connector.getPlatformId());

    		
    		scorm_mining.put(insert.getId(), insert);
        }  
		return scorm_mining;    	
    }
    
    public HashMap<Long, QuizQuestionMining> generateQuizQuestionMining(){
    	
    	HashMap<Long, QuizQuestionMining> quiz_question_mining = new HashMap<Long, QuizQuestionMining>();
    	    	
    	for (Quiz_question_instances_LMS loadedItem : quizQuestionInstancesLms) 
    	{
    		QuizQuestionMining insert = new QuizQuestionMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
           	insert.setQuiz(Long.valueOf(connector.getPrefix() + "" + loadedItem.getQuiz()), quizMining, oldQuizMining);
           	insert.setQuestion(Long.valueOf(connector.getPrefix() + "" + loadedItem.getQuestion()), questionMining, oldQuestionMining);
			insert.setPlatform(connector.getPlatformId());
            if(insert.getQuiz() != null && insert.getQuestion() != null)
            {  
            	quiz_question_mining.put(insert.getQuestion().getId(), insert);
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
    	    	
    	for (Question_LMS loadedItem : questionLms) 
    	{
    		QuestionMining insert = new QuestionMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
           	insert.setTitle(loadedItem.getName());
           	insert.setText(loadedItem.getQuestiontext());
           	insert.setType(loadedItem.getQtype());
           	insert.setTimeCreated(loadedItem.getTimecreated());
			insert.setPlatform(connector.getPlatformId());
           	insert.setTimeModified(loadedItem.getTimemodified());
           	
           	question_mining.put(insert.getId(), insert);
        }
		return question_mining;    	
    }
    
    public HashMap<Long, QuizUserMining> generateQuizUserMining(){

    	HashMap<Long, QuizUserMining> quiz_user_mining = new HashMap<Long, QuizUserMining>();
    	for (Grade_grades_LMS loadedItem : gradeGradesLms) 
    	{
    		QuizUserMining insert = new QuizUserMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
			insert.setPlatform(connector.getPlatformId());
           	if(loadedItem.getFinalgrade()!=null){
           		insert.setFinalGrade(loadedItem.getFinalgrade());
           	}
           	if(loadedItem.getRawgrade()!=null){
           		insert.setRawGrade(loadedItem.getRawgrade());	
           	}
           	if(loadedItem.getTimemodified()!=null){
           		insert.setTimeModified(loadedItem.getTimemodified());
           	}
           	
			insert.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid()), userMining, oldUserMining);
           	for (Grade_items_LMS loadedItem2 : gradeItemsLms) 
           	{
        		if(loadedItem2.getId() == loadedItem.getItemid() && loadedItem2.getIteminstance() != null){
        			insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getCourseid()), courseMining, oldCourseMining);
        			insert.setQuiz(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getIteminstance()), quizMining, oldQuizMining);
           		   	if(insert.getQuiz()!= null && insert.getUser() != null){
           		   		
           		   	if(insert.getCourse() != null)
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
    	
        for ( Resource_LMS loadedItem : resourceLms) 
        {
            ResourceMining insert = new ResourceMining();
        
            insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
           	insert.setType(loadedItem.getType());
           	insert.setTitle(loadedItem.getName());
			insert.setPlatform(connector.getPlatformId());
           	
           	//Get time of creation
        	
           	insert.setTimeModified(loadedItem.getTimemodified());
           	
       		resource.put(insert.getId(), insert);    		
        }
        
       	for ( Log_LMS loadedItem : logLms) 
       	{
           	if(resource.get(loadedItem.getCmid()) != null && (resource.get(loadedItem.getCmid()).getTimeCreated() == 0 || resource.get(loadedItem.getCmid()).getTimeCreated() > loadedItem.getTime()))
           	{           		
           		resource.get(loadedItem.getCmid()).setTimeCreated(loadedItem.getTime());
       		}
       	}           
		return resource;
    }
    
    
    public HashMap<Long, ResourceLogMining> generateResourceLogMining() {
    	HashMap<Long, ResourceLogMining> resourceLogMining = new HashMap<Long, ResourceLogMining>();
    	//A HashMap of list of timestamps. Every key represents one user, the according value is a list of his/her requests times.
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
        
        for (Log_LMS loadedItem : logLms) 
        {
            
            long uid = Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid());
    		
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
    			insert.setPlatform(connector.getPlatformId());
            	
            	insert.setId(resourceLogMining.size() + 1 + resourceLogMax);
            	
    			insert.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid()), userMining, oldUserMining);
            	insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()), courseMining, oldCourseMining);
            	insert.setAction(loadedItem.getAction()); 
            	
            	
            	
            	if(loadedItem.getInfo().matches("[0-9]+")){
    				insert.setResource(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInfo()), resourceMining, oldResourceMining);
    			}    				
            	insert.setTimestamp(loadedItem.getTime());
    			if(insert.getResource()== null && !(loadedItem.getAction().equals("view all"))){
    				logger.info("In Resource_log_mining, resource not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo()+ " and action: " + loadedItem.getAction());
    			}
    			if(insert.getCourse() != null && insert.getResource() != null && insert.getUser() != null)
    				resourceLogMining.put(insert.getId(), insert);
    			
            }
        }
        //For 
        for(ResourceLogMining r : resourceLogMining.values())
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
    	    	
    	for (User_LMS loadedItem : userLms) 
    	{
    	
    		UserMining insert = new UserMining();
    		
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
           	insert.setLastLogin(loadedItem.getLastlogin());
           	insert.setFirstAccess(loadedItem.getFirstaccess());
           	insert.setLastAccess(loadedItem.getLastaccess());
           	insert.setCurrentLogin(loadedItem.getCurrentlogin());
			insert.setPlatform(connector.getPlatformId());
			insert.setLogin(Encoder.createMD5(loadedItem.getLogin()));
           	user_mining.put(insert.getId(), insert);
        }
		return user_mining;    	
    }
    
    
    public HashMap<Long, WikiLogMining> generateWikiLogMining(){
    	HashMap<Long, WikiLogMining> wikiLogMining = new HashMap<Long, WikiLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	HashMap<Long, Course_Modules_LMS> couMod = new HashMap<Long, Course_Modules_LMS>();
    	
    	for(Course_Modules_LMS cm : courseModulesLms)
    	{
    		couMod.put(cm.getId(), cm);
    	}
    	
    	
        for (Log_LMS loadedItem : logLms) {
            
            long uid = Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid());
    		
    		
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
            
    		if(loadedItem.getModule().equals("wiki"))
    		{
    			WikiLogMining insert = new WikiLogMining();
    			
    			insert.setId(wikiLogMining.size() + 1 + wikiLogMax);
    			//Cannot tell, how to extract the correct wiki-id - so it'll always be null
    			if(couMod.get(loadedItem.getCmid()) != null)
   					insert.setWiki(Long.valueOf(connector.getPrefix() + "" + couMod.get(loadedItem.getCmid()).getInstance()), wikiMining, oldWikiMining);


    			insert.setPlatform(connector.getPlatformId());
    			insert.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getUserid()), userMining, oldUserMining);

    			insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()), courseMining, oldCourseMining);
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
    	
    	for ( Wiki_LMS loadedItem : wikiLms) 
    	{
    		WikiMining insert = new WikiMining();
    	
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
    		insert.setTitle(loadedItem.getName());
    		insert.setSummary(loadedItem.getSummary());
    		insert.setTimeModified(loadedItem.getTimemodified());
			insert.setPlatform(connector.getPlatformId());
    		wiki_mining.put(insert.getId(), insert);
    	}
       	for (Log_LMS loadedItem : logLms) 
       	{
           	if(loadedItem.getModule().equals("Wiki") && wiki_mining.get(loadedItem.getCmid()) != null && (wiki_mining.get(loadedItem.getCmid()).getTimeCreated() == 0 || wiki_mining.get(loadedItem.getCmid()).getTimeCreated() > loadedItem.getTime()))
           	{           		
           		wiki_mining.get(loadedItem.getCmid()).setTimeCreated(loadedItem.getTime());
       		}
       	} 
    	return wiki_mining;
    }
	
	public HashMap<Long, RoleMining> generateRoleMining(){
//generate role tables
		HashMap<Long, RoleMining> role_mining = new HashMap<Long, RoleMining>();
    	
    	for ( Role_LMS loadedItem : roleLms)
    	{
    		RoleMining insert = new RoleMining();
    	
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
    		insert.setName(loadedItem.getName());
    		insert.setShortname(loadedItem.getShortname());
    		insert.setDescription(loadedItem.getDescription());
    		insert.setSortOrder(loadedItem.getSortorder());
			insert.setPlatform(connector.getPlatformId());
    		
    		role_mining.put(insert.getId(), insert);
    	}
		return role_mining;
    }
	

	public HashMap<Long, LevelMining> generateLevelMining() {
		HashMap<Long, LevelMining> level_mining = new HashMap<Long, LevelMining>();
		
		for( CourseCategories_LMS loadedItem : courseCategoriesLms)
		{
			LevelMining insert = new LevelMining();
				
			
			insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setPlatform(connector.getPlatformId());
			insert.setDepth(loadedItem.getDepth());
			level_mining.put(insert.getId(), insert);
			
		}
		return level_mining;
	}


	public HashMap<Long, LevelAssociationMining> generateLevelAssociationMining() {
		HashMap<Long, LevelAssociationMining> level_association = new HashMap<Long, LevelAssociationMining>();
		
		for(CourseCategories_LMS loadedItem : courseCategoriesLms)
		{
			String[] s = loadedItem.getPath().split("/");
			if(s.length >= 3)
			{
				LevelAssociationMining insert = new LevelAssociationMining();
				insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
				insert.setLower(Long.valueOf(connector.getPrefix() + "" + s[s.length - 1]), levelMining, oldLevelMining);
				insert.setUpper(Long.valueOf(connector.getPrefix() + "" + s[s.length - 2]), levelMining, oldLevelMining);
				insert.setPlatform(connector.getPlatformId());

				if(insert.getLower() != null && insert.getUpper() != null)
					level_association.put(insert.getId(), insert);
			}
		}
		return level_association;
	}

	@Override
	public HashMap<Long, LevelCourseMining> generateLevelCourseMining() {
		HashMap<Long, LevelCourseMining> level_course = new HashMap<Long, LevelCourseMining>();
		
		for( Context_LMS loadedItem : contextLms)
		{
			if(loadedItem.getDepth() >=4 && loadedItem.getContextlevel() == 50)
			{
				LevelCourseMining insert = new LevelCourseMining();
				
				String[] s = loadedItem.getPath().split("/");
				insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
				insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInstanceid()), courseMining, oldCourseMining);
				insert.setPlatform(connector.getPlatformId());
				for ( Context_LMS loadedItem2 : contextLms)
				{
					if(loadedItem2.getContextlevel() == 40 && loadedItem2.getId() == Integer.parseInt(s[3]))
					{
						insert.setLevel(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getInstanceid()), levelMining, oldLevelMining);
						break;
					}
				}
				if(insert.getLevel() != null && insert.getCourse() != null)
						level_course.put(insert.getId(), insert);
			}
		}
		return level_course;
	}

	public HashMap<Long, ChatMining> generateChatMining() {
		HashMap<Long, ChatMining> chat_mining = new HashMap<Long, ChatMining>();
		
		for( Chat_LMS loadedItem : chatLms)
		{
			ChatMining insert = new ChatMining();
			insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
			insert.setChatTime(loadedItem.getChattime());
			insert.setDescription(loadedItem.getDescription());
			insert.setTitle(loadedItem.getTitle());
			insert.setPlatform(connector.getPlatformId());
			insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()), courseMining, oldCourseMining);
			
			if(insert.getCourse() != null)
			
			chat_mining.put(insert.getId(), insert);
        }

		return chat_mining;
	}


	public HashMap<Long, ChatLogMining> generateChatLogMining() {
		HashMap<Long, ChatLogMining> chatLogMining = new HashMap<Long, ChatLogMining>();
    	
        for (ChatLog_LMS loadedItem : chatLogLms) 
        {
        	ChatLogMining insert = new ChatLogMining();
        	insert.setId(chatLogMining.size() + 1 + chatLogMax);
        	insert.setChat(Long.valueOf(connector.getPrefix() + "" + loadedItem.getChat_Id()), chatMining, oldChatMining);
        	insert.setMessage(loadedItem.getMessage());
        	insert.setTimestamp(loadedItem.getTimestamp());
        	insert.setPlatform(connector.getPlatformId());
        	if(insert.getChat() != null)
        		insert.setCourse(insert.getChat().getCourse().getId(), courseMining, oldCourseMining);
        	insert.setDuration(0L);
        	
        	insert.setUser(Long.valueOf(connector.getPrefix() + "" + loadedItem.getUser_Id()), userMining, oldUserMining);
  			if(insert.getUser()==null){
  				logger.info("In Chat_log_mining(chat part), user not found for log: " + loadedItem.getId() +" and user: " + loadedItem.getUser_Id());
  			}
  			if(insert.getChat()==null){
  				logger.info("In Chat_log_mining(chat part), chat not found for log: " + loadedItem.getId() +" and chat: " + loadedItem.getChat_Id());
  			}
  			if(insert.getChat() != null && insert.getUser() != null && insert.getCourse() != null)
					
  				chatLogMining.put(insert.getId(), insert);
  			
        }
		return chatLogMining;
	}


	
}
