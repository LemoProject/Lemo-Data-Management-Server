package de.lemo.dms.connectors.moodle;
//import miningDBclass.Config_mining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.moodle.moodleDBclass.AssignmentLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.AssignmentSubmissionsLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.ChatLogLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.ChatLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.ContextLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.CourseCategoriesLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.CourseLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.CourseModulesLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.ForumLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.ForumDiscussionsLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.ForumPostsLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.GradeGradesLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.GradeItemsLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.GroupsLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.GroupsMembersLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.LogLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.QuestionLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.QuestionStatesLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.QuizLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.QuizGradesLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.QuizQuestionInstancesLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.ResourceLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.RoleLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.RoleAssignmentsLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.ScormLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.UserLMS;
import de.lemo.dms.connectors.moodle.moodleDBclass.WikiLMS;
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
import de.lemo.dms.db.miningDBclass.IDMappingMining;
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
public class ExtractAndMapMoodle extends ExtractAndMap{//Versionsnummer in Namen einf�gen

//LMS tables instances lists
	private List<LogLMS> logLms;
	private List<ResourceLMS> resourceLms;
	private List<CourseLMS> courseLms;
	private List<ForumLMS> forumLms;
	private List<WikiLMS> wikiLms;
	private List<UserLMS> userLms;
	private List<QuizLMS> quizLms;
	private List<QuizQuestionInstancesLMS> quizQuestionInstancesLms;
	private List<QuestionLMS> questionLms;
	private List<GroupsLMS> groupLms;
	private List<GroupsMembersLMS> groupMembersLms;
	private List<QuestionStatesLMS> questionStatesLms;
	private List<ForumPostsLMS> forumPostsLms;
	private List<RoleLMS> roleLms;
	private List<ContextLMS> contextLms;
	private List<RoleAssignmentsLMS> roleAssignmentsLms;
	private List<AssignmentLMS> assignmentLms;	
	private List<AssignmentSubmissionsLMS> assignmentSubmissionLms;
	private List<QuizGradesLMS> quizGradesLms;
	private List<ForumDiscussionsLMS> forumDiscussionsLms;
	private List<ScormLMS> scormLms;
	private List<GradeGradesLMS> gradeGradesLms;
	private List<GradeItemsLMS> gradeItemsLms;
	private List<ChatLMS> chatLms;
	private List<ChatLogLMS> chatLogLms;
	private List<CourseCategoriesLMS> courseCategoriesLms;
	private List<CourseModulesLMS> courseModulesLms;
	
	private boolean numericUserId = false;
	
	private Logger logger = Logger.getLogger(getClass());
	
	private IConnector connector;
	
    public ExtractAndMapMoodle(IConnector connector) {
        super(connector);
        this.connector = connector;
    }
	
	@SuppressWarnings("unchecked")	
	public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp) {
		   
	        Session session = MoodleHibernateUtil.getSessionFactory(dbConf).openSession();
	        Transaction tx = session.beginTransaction();

	        //Just for testing. has to be set to Long.MaxValue if not longer needed.
	        long ceiling = Long.MAX_VALUE;
	        
	        //reading the LMS Database, create tables as lists of instances of the DB-table classes
	        

	        Query log = session.createQuery("from LogLMS x where x.time>=:readingtimestamp and x.time<=:ceiling order by x.id asc");
	        log.setParameter("readingtimestamp", readingfromtimestamp);
	        log.setParameter("ceiling", ceiling);
	        logLms = log.list();	
	        logger.info("logLms tables: " + logLms.size());       
	        
	    	Query resource = session.createQuery("from ResourceLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	resource.setParameter("readingtimestamp", readingfromtimestamp);
	    	resource.setParameter("ceiling", ceiling);
	    	resourceLms = resource.list();		        
	    	logger.info("resourceLms tables: " + resourceLms.size());
	    	
	      	Query courseMod = session.createQuery("from CourseModulesLMS x order by x.id asc");
	    	courseModulesLms = courseMod.list();		        
	    	logger.info("courseModulesLms tables: " + courseModulesLms.size());
	    	
	    	
	    	Query chat = session.createQuery("from ChatLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	chat.setParameter("readingtimestamp", readingfromtimestamp);
	    	chat.setParameter("ceiling", ceiling);
	    	chatLms = chat.list();		        
	    	logger.info("chatLms tables: " + chatLms.size());
	    	
	    	Query chatlog = session.createQuery("from ChatLogLMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:ceiling order by x.id asc");
	    	chatlog.setParameter("readingtimestamp", readingfromtimestamp);
	    	chatlog.setParameter("ceiling", ceiling);
	    	chatLogLms = chatlog.list();		        
	    	logger.info("chatloglms tables: " + chatLogLms.size());
	    	
	    	Query courseCategories = session.createQuery("from CourseCategoriesLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	courseCategories.setParameter("readingtimestamp", readingfromtimestamp);
	    	courseCategories.setParameter("ceiling", ceiling);
	    	courseCategoriesLms = courseCategories.list();		        
	    	logger.info("coursecategorieslms tables: " + courseCategoriesLms.size());
	    	
	    	
	    	Query course = session.createQuery("from CourseLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	course.setParameter("readingtimestamp", readingfromtimestamp);
	    	course.setParameter("ceiling", ceiling);
	    	courseLms = course.list();		        	        
	    	logger.info("courselms tables: " + courseLms.size());
	    	
	    	Query forumPosts = session.createQuery("from ForumPostsLMS x where x.modified>=:readingtimestamp and x.modified<=:ceiling order by x.id asc");
	    	forumPosts.setParameter("readingtimestamp", readingfromtimestamp);
	    	forumPosts.setParameter("ceiling", ceiling);
	    	forumPostsLms = forumPosts.list();	
	    	logger.info("forumPostsLms tables: " + forumPostsLms.size()); 
	    	
	    	Query forum = session.createQuery("from ForumLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	forum.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum.setParameter("ceiling", ceiling);
	    	forumLms = forum.list();		        
	    	logger.info("forum_lms tables: " + forumLms.size());
	    	
	    	Query group = session.createQuery("from GroupsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	group.setParameter("readingtimestamp", readingfromtimestamp);
	    	group.setParameter("ceiling", ceiling);
	    	groupLms = group.list();	        
	    	logger.info("group_lms tables: " + groupLms.size());
	    	
	    	Query quiz = session.createQuery("from QuizLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	quiz.setParameter("readingtimestamp", readingfromtimestamp);
	    	quiz.setParameter("ceiling", ceiling);
	    	quizLms = quiz.list();		        
	    	logger.info("quiz_lms tables: " + quizLms.size());	
	    	
	    	Query wiki = session.createQuery("from WikiLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	wiki.setParameter("readingtimestamp", readingfromtimestamp);
	    	wiki.setParameter("ceiling", ceiling);
	    	wikiLms = wiki.list();		        
	    	logger.info("wiki_lms tables: " + wikiLms.size());
	    	
	    	Query group_members = session.createQuery("from GroupsMembersLMS x where x.timeadded>=:readingtimestamp and x.timeadded<=:ceiling order by x.id asc");
	    	group_members.setParameter("readingtimestamp", readingfromtimestamp);
	    	group_members.setParameter("ceiling", ceiling);
	    	groupMembersLms = group_members.list();		    	
	        logger.info("group_members_lms tables: " + groupMembersLms.size());
	    	
	    	Query question_states = session.createQuery("from QuestionStatesLMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:ceiling order by x.id asc");
	    	question_states.setParameter("readingtimestamp", readingfromtimestamp);
	    	question_states.setParameter("ceiling", ceiling);
	    	questionStatesLms = question_states.list();	    	
	        logger.info("question_states_lms tables: " + questionStatesLms.size());
	    	
	    	Query quiz_question_instances = session.createQuery("from QuizQuestionInstancesLMS x order by x.id asc");
	    	quizQuestionInstancesLms = quiz_question_instances.list();		    	
	        logger.info("quiz_question_instances_lms tables: " + quizQuestionInstancesLms.size()); 
	    	
	    	Query question = session.createQuery("from QuestionLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	question.setParameter("readingtimestamp", readingfromtimestamp);
	    	question.setParameter("ceiling", ceiling);
	    	questionLms = question.list();		    	
	        logger.info("question_lms tables: " + questionLms.size());
	    	
  
	    	Query user = session.createQuery("from UserLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	user.setParameter("ceiling", ceiling);
	    	user.setParameter("readingtimestamp", readingfromtimestamp);
	    	userLms = user.list();		    	
	        logger.info("user_lms tables: " + userLms.size());	 
	    	
	    	Query role = session.createQuery("from RoleLMS x order by x.id asc");
	    	roleLms = role.list();
	        logger.info("role_lms tables: " + roleLms.size());
	    	
	    	Query context = session.createQuery("from ContextLMS x order by x.id asc");
	    	contextLms = context.list();		    	
	        logger.info("context_lms tables: " + contextLms.size());
	    	
	    	Query role_assignments = session.createQuery("from RoleAssignmentsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	role_assignments.setParameter("ceiling", ceiling);
	    	role_assignments.setParameter("readingtimestamp", readingfromtimestamp);
	    	roleAssignmentsLms = role_assignments.list();		    	
	        logger.info("role_assignments_lms tables: " + roleAssignmentsLms.size());
	    	
	    	Query assignments = session.createQuery("from AssignmentLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	assignments.setParameter("ceiling", ceiling);
	    	assignments.setParameter("readingtimestamp", readingfromtimestamp);
	    	assignmentLms = assignments.list();		    	
	        logger.info("assignment_lms tables: " + assignmentLms.size());
	    	
	        
	    	Query assignment_submission = session.createQuery("from AssignmentSubmissionsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	assignment_submission.setParameter("ceiling", ceiling);
	    	assignment_submission.setParameter("readingtimestamp", readingfromtimestamp);
	    	assignmentSubmissionLms = assignment_submission.list();		    	
	        logger.info("assignment_submission_lms tables: " + assignmentSubmissionLms.size());


	    	Query quiz_grades = session.createQuery("from QuizGradesLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	quiz_grades.setParameter("ceiling", ceiling);
	    	quiz_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	quizGradesLms = quiz_grades.list();		    	
	        logger.info("quiz_grades_lms tables: " + quizGradesLms.size());
	        
	    	Query forum_discussions = session.createQuery("from ForumDiscussionsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	forum_discussions.setParameter("ceiling", ceiling);
	    	forum_discussions.setParameter("readingtimestamp", readingfromtimestamp);
	    	forumDiscussionsLms = forum_discussions.list();		    	
	        logger.info("forum_discussions_lms tables: " + forumDiscussionsLms.size());	        

	    	Query scorm = session.createQuery("from ScormLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	scorm.setParameter("ceiling", ceiling);
	    	scorm.setParameter("readingtimestamp", readingfromtimestamp);
	    	scormLms = scorm.list();		    	
	        logger.info("scorm_lms tables: " + scormLms.size());	        

	    	Query grade_grades = session.createQuery("from GradeGradesLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	grade_grades.setParameter("ceiling", ceiling);
	    	grade_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	gradeGradesLms = grade_grades.list();		    	
	        logger.info("grade_grades_lms tables: " + gradeGradesLms.size());		        

	    	Query grade_items = session.createQuery("from GradeItemsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
	    	grade_items.setParameter("ceiling", ceiling);
	    	grade_items.setParameter("readingtimestamp", readingfromtimestamp);
	    	gradeItemsLms = grade_items.list();		    	
	        logger.info("grade_items_lms tables: " + gradeItemsLms.size());
	        	        
	        // hibernate session finish and close
	        tx.commit();        
	        session.close();
	        
	}

	@SuppressWarnings("unchecked")
	public void getLMStables(DBConfigObject dbConf, long readingfromtimestamp, long readingtotimestamp) {
		   
	        // accessing DB by creating a session and a transaction using HibernateUtil
	        Session session = MoodleHibernateUtil.getSessionFactory(dbConf).openSession();
			//Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19", "datamining", "LabDat1#").openSession();
	        session.clear();
	        Transaction tx = session.beginTransaction();
	        
	        // reading the LMS Database, create tables as lists of instances of the DB-table classes

	        if(userLms == null){

	        	Query resource = session.createQuery("from ResourceLMS x order by x.id asc");
	        	resourceLms = resource.list();	
	        	logger.info("Resource tables: " + resourceLms.size());	  
	    	
	        	Query course = session.createQuery("from CourseLMS x order by x.id asc");
	        	courseLms = course.list();		     
	        	logger.info("Course_LMS tables: " + courseLms.size());	  
	        	
	        	Query chat = session.createQuery("from ChatLMS x order by x.id asc");
		    	chatLms = chat.list();		       
		    	logger.info("Chat_LMS tables: " + chatLms.size());	
		    			    	
		    	Query courseCategories = session.createQuery("from CourseCategoriesLMS x order by x.id asc");
		    	courseCategoriesLms = courseCategories.list();		  
		    	logger.info("CourseCategories_LMS tables: " + courseCategoriesLms.size());	
	    	
	        	Query forum = session.createQuery("from ForumLMS x order by x.id asc");
	        	forumLms = forum.list();	
	        	logger.info("Forum_LMS tables: " + forumLms.size());	
	        	
		      	Query courseMod = session.createQuery("from CourseModulesLMS x order by x.id asc");
		    	courseModulesLms = courseMod.list();		        
		    	logger.info("course_modules_lms tables: " + courseModulesLms.size());
	    	
	        	Query group = session.createQuery("from GroupsLMS x order by x.id asc");
	        	groupLms = group.list();	        
	        	logger.info("Groups_LMS tables: " + groupLms.size());	
	    	
	        	Query quiz = session.createQuery("from QuizLMS x order by x.id asc");
	        	quizLms = quiz.list();		
	        	logger.info("Quiz_LMS tables: " + quizLms.size());	

	        	Query wiki = session.createQuery("from WikiLMS x order by x.id asc");
	        	wikiLms = wiki.list();		
	        	logger.info("Wiki_LMS tables: " + wikiLms.size());	
	    	
	        	Query quiz_question_instances = session.createQuery("from QuizQuestionInstancesLMS x order by x.id asc");
	        	quizQuestionInstancesLms = quiz_question_instances.list();		 
	        	logger.info("Quiz_question_instances_LMS tables: " + quizQuestionInstancesLms.size());	
	    	
	        	Query question = session.createQuery("from QuestionLMS x order by x.id asc");
	        	questionLms = question.list();		
	        	logger.info("Question_LMS tables: " + questionLms.size());	
	    	
	        	Query user = session.createQuery("from UserLMS x order by x.id asc");
	        	userLms = user.list();		   
	        	logger.info("User_LMS tables: " + userLms.size());	
	    	
	        	Query role = session.createQuery("from RoleLMS x order by x.id asc");
	        	roleLms = role.list();		
	        	logger.info("Role_LMS tables: " + roleLms.size());	

	        	session.clear();
	    	
	        	Query context = session.createQuery("from ContextLMS x order by x.id asc");
	        	contextLms = context.list();
	        	logger.info("Context_LMS tables: " + contextLms.size());	
	    	
	        	Query assignments = session.createQuery("from AssignmentLMS x order by x.id asc");
	        	assignmentLms = assignments.list();	
	        	logger.info("Assignment_LMS tables: " + assignmentLms.size());	
	        	
		    	Query scorm = session.createQuery("from ScormLMS x order by x.id asc");
		    	scormLms = scorm.list();		
		    	logger.info("Scorm_LMS tables: " + scormLms.size());	
		    	
		    	Query grade_items = session.createQuery("from GradeItemsLMS x order by x.id asc");
		    	gradeItemsLms = grade_items.list();
		    	logger.info("Grade_items_LMS tables: " + gradeItemsLms.size());	
	        }
	    	
	    	Query log = session.createQuery("from LogLMS x where x.time>=:readingtimestamp and x.time<=:readingtimestamp2 order by x.id asc");
	        log.setParameter("readingtimestamp", readingfromtimestamp);
	        log.setParameter("readingtimestamp2", readingtotimestamp);
	        logLms = log.list();	  
	        logger.info("Log_LMS tables: " + logLms.size());	
	        
	    	Query chatlog = session.createQuery("from ChatLogLMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:readingtimestamp2 order by x.id asc");
	    	chatlog.setParameter("readingtimestamp", readingfromtimestamp);
	    	chatlog.setParameter("readingtimestamp2", readingtotimestamp);	
	    	chatLogLms = chatlog.list();	
	    	logger.info("ChatLog_LMS tables: " + chatLogLms.size());	
	    	
	    	Query forum_posts = session.createQuery("from ForumPostsLMS x where x.created>=:readingtimestamp and x.created<=:readingtimestamp2 order by x.id asc");
	    	forum_posts.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_posts.setParameter("readingtimestamp2", readingtotimestamp);	    	
	    	forumPostsLms = forum_posts.list();
	    	logger.info("Forum_posts_LMS tables: " + forumPostsLms.size());	
	    	
	    	Query forum_posts_modified = session.createQuery("from ForumPostsLMS x where x.modified>=:readingtimestamp and x.modified<=:readingtimestamp2 order by x.id asc");
	    	forum_posts_modified.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_posts_modified.setParameter("readingtimestamp2", readingtotimestamp);
	    	forumPostsLms.addAll(forum_posts_modified.list());
	    	logger.info("Forum_posts_LMS tables: " + forumPostsLms.size());	

	        session.clear();
	    	
	    	Query group_members = session.createQuery("from GroupsMembersLMS x where x.timeadded>=:readingtimestamp and x.timeadded<=:readingtimestamp2 order by x.id asc");
	    	group_members.setParameter("readingtimestamp", readingfromtimestamp);
	    	group_members.setParameter("readingtimestamp2", readingtotimestamp);
	    	groupMembersLms = group_members.list();		 
	    	logger.info("Groups_members_LMS tables: " + groupMembersLms.size());	
	    	
	    	Query question_states = session.createQuery("from QuestionStatesLMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:readingtimestamp2 order by x.id asc");
	    	question_states.setParameter("readingtimestamp", readingfromtimestamp);
	    	question_states.setParameter("readingtimestamp2", readingtotimestamp);
	    	questionStatesLms = question_states.list();	
	    	logger.info("Question_states_LMS tables: " + questionStatesLms.size());	
	    	
 	
	    	
	    	Query role_assignments = session.createQuery("from RoleAssignmentsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	role_assignments.setParameter("readingtimestamp", readingfromtimestamp);
	    	role_assignments.setParameter("readingtimestamp2", readingtotimestamp);
	    	roleAssignmentsLms = role_assignments.list();		 
	    	logger.info("Role_assignments_LMS tables: " + roleAssignmentsLms.size());	
	    		    	
	    
	    	Query assignment_submission = session.createQuery("from AssignmentSubmissionsLMS x where x.timecreated>=:readingtimestamp and x.timecreated<=:readingtimestamp2 order by x.id asc");
	    	assignment_submission.setParameter("readingtimestamp", readingfromtimestamp);
	    	assignment_submission.setParameter("readingtimestamp2", readingtotimestamp);
	    	assignmentSubmissionLms = assignment_submission.list();	
	    	logger.info("Assignment_submissions_LMS tables: " + assignmentSubmissionLms.size());	


	    	Query quiz_grades = session.createQuery("from QuizGradesLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	quiz_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	quiz_grades.setParameter("readingtimestamp2", readingtotimestamp);
	    	quizGradesLms = quiz_grades.list();		  
	    	logger.info("Quiz_grades_LMS tables: " + quizGradesLms.size());	

	    	Query forum_discussions = session.createQuery("from ForumDiscussionsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	forum_discussions.setParameter("readingtimestamp", readingfromtimestamp);
	    	forum_discussions.setParameter("readingtimestamp2", readingtotimestamp);
	    	forumDiscussionsLms = forum_discussions.list();		    	
	    	logger.info("Forum_discussions_LMS tables: " + forumDiscussionsLms.size());
    	
	    	Query grade_grades = session.createQuery("from GradeGradesLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
	    	grade_grades.setParameter("readingtimestamp", readingfromtimestamp);
	    	grade_grades.setParameter("readingtimestamp2", readingtotimestamp);
	    	gradeGradesLms = grade_grades.list();
	    	logger.info("Grade_grades_LMS tables: " + gradeGradesLms.size());	
	        	        
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
    
    public Map<Long, CourseUserMining> generateCourseUserMining(){
    	
    	HashMap<Long, CourseUserMining> course_user_mining = new HashMap<Long, CourseUserMining>();
    	    	
    	for (ContextLMS loadedItem : contextLms) 
    	{
       		if(loadedItem.getContextlevel() == 50){
       			for (RoleAssignmentsLMS loadedItem2 : roleAssignmentsLms) 
       			{
       	       		if(loadedItem2.getContextid() == loadedItem.getId()){
       	       			CourseUserMining insert = new CourseUserMining();
       	       			insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getId()));
       	       			insert.setRole(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getRoleid()), roleMining, oldRoleMining);
       	       			insert.setPlatform(connector.getPlatformId());
       	       			
       	       			if(!numericUserId)
       	       			{
	       	    			long id = -1;
	       	       			if(idMapping.get(loadedItem2.getUserid()) != null)
	       	       			{
	       	       				id = idMapping.get(loadedItem2.getUserid()).getId();
	       	       				insert.setUser(id,userMining, oldUserMining);
	       	       			}
	       	       			if(id == -1 && oldIdMapping.get(loadedItem2.getUserid()) != null)
	       	       			{
	       	       				id = oldIdMapping.get(loadedItem2.getUserid()).getId();
	       	       				insert.setUser(id,userMining, oldUserMining);
	       	       			}
	       	       			if(id == -1 )
	       	       			{
	       	       				id = largestId + 1;
	       	       				largestId = id;
	       	       				idMapping.put(loadedItem2.getUserid(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id), loadedItem2.getUserid(), connector.getPlatformId()));
	       	       				insert.setUser(Long.valueOf(connector.getPrefix() + "" + id),userMining, oldUserMining);
	       	       			}
       	       			}
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
    
    
    public Map<Long, CourseForumMining> generateCourseForumMining(){
    	
    	HashMap<Long, CourseForumMining> course_forum_mining = new HashMap<Long, CourseForumMining>();
    	    	
    	for (ForumLMS loadedItem : forumLms) 
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
    
    
    public Map<Long, CourseMining> generateCourseMining() {
    	
    	HashMap<Long, CourseMining> course_mining = new HashMap<Long, CourseMining>();
       	for (CourseLMS loadedItem : courseLms) 
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
    
    
	public Map<Long, CourseGroupMining> generateCourseGroupMining(){
    	
		HashMap<Long, CourseGroupMining> course_group_mining = new HashMap<Long, CourseGroupMining>();
    	    	
    	for (GroupsLMS loadedItem : groupLms) 
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
    
    
    public Map<Long, CourseQuizMining> generateCourseQuizMining(){
    	
    	HashMap<Long, CourseQuizMining> course_quiz_mining = new HashMap<Long, CourseQuizMining>();
    	    	
    	for (QuizLMS loadedItem : quizLms) 
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
    			logger.debug("In Course_quiz_mining, quiz(quiz) not found: " + loadedItem.getId());
	    		}   
        }    	
		return course_quiz_mining;
    }

   public Map<Long, CourseAssignmentMining> generateCourseAssignmentMining(){
    	
	   HashMap<Long, CourseAssignmentMining> course_assignment_mining = new HashMap<Long, CourseAssignmentMining>();
    	    	   	
    	for (AssignmentLMS loadedItem : assignmentLms)
    	{
    		CourseAssignmentMining insert = new CourseAssignmentMining();
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
    		if(insert.getId() == 112865)
    			System.out.println();
            insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()),courseMining, oldCourseMining);
            insert.setPlatform(connector.getPlatformId());
			if(insert.getCourse()==null){
				logger.debug("course not found for course-assignment: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
			}
            insert.setAssignment(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()), assignmentMining, oldAssignmentMining);
            if(insert.getCourse()!= null && insert.getAssignment() != null){   
					
            	course_assignment_mining.put(insert.getId(), insert);
            }
    		if(insert.getAssignment()==null){
    			logger.debug("In Course_assignment_mining, assignment not found: " + loadedItem.getId());
	    		}   
        }
		return course_assignment_mining;
    }
   
   public Map<Long, CourseScormMining> generateCourseScormMining(){
   	
	   	HashMap<Long, CourseScormMining> course_scorm_mining = new HashMap<Long, CourseScormMining>();   	    	
	   	for (ScormLMS loadedItem : scormLms) 
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
	   			logger.debug("In Course_scorm_mining, scorm not found: " + loadedItem.getId());
	   		}      	
	   	}
			return course_scorm_mining;
   }
    
    public Map<Long, CourseResourceMining> generateCourseResourceMining(){
    	
    	HashMap<Long, CourseResourceMining> course_resource_mining = new HashMap<Long, CourseResourceMining>();
    	    	
    	for (ResourceLMS loadedItem : resourceLms) 
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
    
    
    public Map<Long, CourseLogMining> generateCourseLogMining(){
    	HashMap<Long, CourseLogMining> courseLogMining = new HashMap<Long, CourseLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	
    	for (LogLMS loadedItem : logLms ) {
    		
    		 long uid = -1;
             
             if(idMapping.get(loadedItem.getUserid()) != null)
     			uid = idMapping.get(loadedItem.getUserid()).getId();
     		if(uid == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
     			uid = oldIdMapping.get(loadedItem.getUserid()).getId();
     		
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
    			
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(idMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = idMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);

	       			}
	       			if(id == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = oldIdMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				idMapping.put(loadedItem.getUserid(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id), loadedItem.getUserid(), connector.getPlatformId()));
	       				insert.setUser(Long.valueOf(connector.getPrefix() + "" + id),userMining, oldUserMining);
	       			}
    			}
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
    
    
    public Map<Long, CourseWikiMining> generateCourseWikiMining(){
    	
    	HashMap<Long, CourseWikiMining> course_wiki_mining = new HashMap<Long, CourseWikiMining>();
    	    	
    	for (WikiLMS loadedItem : wikiLms) 
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

    
    public Map<Long, ForumLogMining> generateForumLogMining() {
    	HashMap<Long, ForumLogMining> forumLogMining = new HashMap<Long, ForumLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	HashMap<Long, ForumDiscussionsLMS> forumDis = new HashMap<Long, ForumDiscussionsLMS>();
    	for(ForumDiscussionsLMS fd : forumDiscussionsLms)
    	{
    		forumDis.put(fd.getId(), fd);
    	}
    	    		
    	for (LogLMS loadedItem : logLms) {
    		
    		 long uid = -1;
             
             if(idMapping.get(loadedItem.getUserid()) != null)
     			uid = idMapping.get(loadedItem.getUserid()).getId();
     		if(uid == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
     			uid = oldIdMapping.get(loadedItem.getUserid()).getId();
     		
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
    			
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(idMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = idMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);
	       			}
	       			if(id == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = oldIdMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				idMapping.put(loadedItem.getUserid(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id), loadedItem.getUserid(), connector.getPlatformId()));
	       				insert.setUser(Long.valueOf(connector.getPrefix() + "" + id),userMining, oldUserMining);
	       			}
    			}
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
   				for (ForumPostsLMS loadedItem2 : forumPostsLms) 
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
	
    	for ( ForumLMS loadedItem : forumLms)
    	{
   	    	ForumMining insert = new ForumMining();
   	    	insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
            insert.setTimeModified(loadedItem.getTimemodified());
   	    	insert.setTitle(loadedItem.getName());
   	    	insert.setSummary(loadedItem.getIntro()); 
   	    	insert.setPlatform(connector.getPlatformId());
    		forum_mining.put(insert.getId(), insert);  
    	} 
    	
       	for ( LogLMS loadedItem : logLms) 
       	{
           	if(forum_mining.get(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCmid())) != null && (forum_mining.get(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCmid())).getTimeCreated() == 0 || forum_mining.get(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCmid())).getTimeCreated() > loadedItem.getTime()))
           	{           		
           		forum_mining.get(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCmid())).setTimeCreated(loadedItem.getTime());
       		}
       	}   
		return forum_mining;
    }

    
	public Map<Long, GroupUserMining> generateGroupUserMining(){
    	
		HashMap<Long, GroupUserMining> group_members_mining = new HashMap<Long, GroupUserMining>();
    	    	
    	for (GroupsMembersLMS loadedItem : groupMembersLms) 
    	{
    		GroupUserMining insert = new GroupUserMining();
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
           	insert.setGroup(Long.valueOf(connector.getPrefix() + "" + loadedItem.getGroupid()), groupMining, oldGroupMining);
			if(!numericUserId)
			{
    			long id = -1;
       			if(idMapping.get(loadedItem.getUserid()) != null)
       			{
       				id = idMapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id,userMining, oldUserMining);
       			}
       			if(id == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
       			{
       				id = oldIdMapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id,userMining, oldUserMining);

       			}
       			if(id == -1 )
       			{
       				id = largestId + 1;
       				largestId = id;
       				idMapping.put(loadedItem.getUserid(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id), loadedItem.getUserid(), connector.getPlatformId()));
       				insert.setUser(Long.valueOf(connector.getPrefix() + "" + id),userMining, oldUserMining);
       				
       			}
			}
           	insert.setTimestamp(loadedItem.getTimeadded());
           	insert.setPlatform(connector.getPlatformId());
           	if(insert.getUser() != null && insert.getGroup() != null)
           		group_members_mining.put(insert.getId(), insert);
        }
		return group_members_mining;
    }
	
	
    public HashMap<Long, GroupMining> generateGroupMining(){
    	
    	HashMap<Long, GroupMining> group_mining = new HashMap<Long, GroupMining>();
    	    	
    	for (GroupsLMS loadedItem : groupLms)
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
    
    
    public Map<Long, QuestionLogMining> generateQuestionLogMining(){
    	HashMap<Long, QuestionLogMining> questionLogMiningtmp = new HashMap<Long, QuestionLogMining>();
    	HashMap<Long, QuestionLogMining> questionLogMining = new HashMap<Long, QuestionLogMining>();
    	HashMap<String, Long> timestampIdMap = new HashMap<String, Long>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	    	
    	for (QuestionStatesLMS loadedItem : questionStatesLms) {
    		
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
			for(QuestionLMS loadedItem2 : questionLms)
			{
				if(loadedItem2.getId() == (loadedItem.getQuestion())){
					insert.setType(loadedItem2.getQtype());//Type
					break;
				}
			}			
			if(insert.getType() == null && oldQuestionMining.get(loadedItem.getQuestion()) != null)
				insert.setType(oldQuestionMining.get(loadedItem.getQuestion()).getType());			
			if(insert.getType() == null){
				logger.debug("In Question_log_mining, type not found for question_states: " + loadedItem.getId() +" and question: " + loadedItem.getQuestion() +" question list size: "+ questionLms.size() );
			}  	
							
			

			if(insert.getQuestion() != null && insert.getQuiz() != null && insert.getCourse() != null)
			{
				
				timestampIdMap.put(insert.getTimestamp() + " " + insert.getQuiz().getId(), insert.getId());
    			questionLogMiningtmp.put(insert.getId(), insert);
			}			
    	}	  
    	
    	//Set Course and 
    	for(LogLMS loadedItem : logLms)
    	{
    		 long uid1 = -1;
             
             if(idMapping.get(loadedItem.getUserid()) != null)
     			uid1 = idMapping.get(loadedItem.getUserid()).getId();
     		if(uid1 == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
     			uid1 = oldIdMapping.get(loadedItem.getUserid()).getId();
     		
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
    				qlm.setUser(uid1,userMining, oldUserMining);
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
    
    public Map<Long, QuizLogMining> generateQuizLogMining(){
    	HashMap<Long, QuizLogMining> quizLogMining = new HashMap<Long, QuizLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	    	
    	for (LogLMS loadedItem : logLms) {
    		
    		 long uid = -1;
             
             if(idMapping.get(loadedItem.getUserid()) != null)
     			uid = idMapping.get( loadedItem.getUserid()).getId();
     		if(uid == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
     			uid = oldIdMapping.get(loadedItem.getUserid()).getId();
     		
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
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(idMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = idMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);
	       			
	       			}
	       			if(id == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = oldIdMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);
	       			
	       			}
    			}
    			if(loadedItem.getInfo().matches("[0-9]+"))
    			{
    				insert.setQuiz(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInfo()), quizMining, oldQuizMining);
    			}
    			insert.setAction(loadedItem.getAction());
    			insert.setTimestamp(loadedItem.getTime());
    			if(insert.getQuiz() != null && insert.getUser() != null && loadedItem.getAction() != "review")
    			{    
    				for (QuizGradesLMS loadedItem2 : quizGradesLms) 
    				{
    					long id = -1;
    	    			if(!numericUserId)
    	    			{    		    			
    		       			if(idMapping.get(loadedItem.getUserid()) != null)
    		       			{
    		       				id = idMapping.get(loadedItem.getUserid()).getId();
    		       			
    		       			}
    		       			if(id == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
    		       			{
    		       				id = oldIdMapping.get(loadedItem.getUserid()).getId();
    		       			
    		       			}
    	    			}
    					if(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getQuiz()) == insert.getQuiz().getId() && id == insert.getUser().getId() && loadedItem2.getTimemodified() == insert.getTimestamp()){
    						insert.setGrade(loadedItem2.getGrade());
    					}
    				}
    			}
    			if(insert.getQuiz() == null && !(loadedItem.getAction().equals("view all"))){
    				logger.debug("In Quiz_log_mining, quiz(quiz) not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid()+ " and info: " + loadedItem.getInfo()+ " and action: " + loadedItem.getAction());
    			}
    			if(insert.getUser() == null){
    				logger.debug("In Quiz_log_mining(quiz), user not found for log: " + loadedItem.getId() + " and user: " + loadedItem.getUserid());
    			}
    			if(insert.getCourse() == null){
    				logger.debug("In Quiz_log_mining(quiz), course not found for log: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
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

    public Map<Long, AssignmentLogMining> generateAssignmentLogMining(){
	  
    	HashMap<Long, AssignmentLogMining> assignmentLogMining = new HashMap<Long, AssignmentLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	HashMap<Long, ArrayList<AssignmentSubmissionsLMS>> asSub = new HashMap<Long, ArrayList<AssignmentSubmissionsLMS>>();
    	
    	//Arranging all assignment_submissions due to performance issues
    	for(AssignmentSubmissionsLMS as : assignmentSubmissionLms)
    	{
    		if(asSub.get(as.getAssignment()) == null)
    		{
    			ArrayList<AssignmentSubmissionsLMS> a = new ArrayList<AssignmentSubmissionsLMS>();
    			a.add(as);
    			asSub.put(as.getAssignment(), a);
    		}
    		else
    			asSub.get(as.getAssignment()).add(as);
    		
    	}
    		
    	
    	for(LogLMS loadedItem : logLms) {
    		
    		 long uid = -1;
             
             if(idMapping.get(loadedItem.getUserid()) != null)
     			uid = idMapping.get(loadedItem.getUserid()).getId();
     		if(uid == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
     			uid = oldIdMapping.get(loadedItem.getUserid()).getId();
     		
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
				
				if(!numericUserId)
				{
	    			long id = -1;
	       			if(idMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = idMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);	       			
	       			}
	       			if(id == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = oldIdMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);	       		
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				idMapping.put(loadedItem.getUserid(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id), loadedItem.getUserid(), connector.getPlatformId()));
	       				insert.setUser(Long.valueOf(connector.getPrefix() + "" + id),userMining, oldUserMining);
	       			}
				}    			
				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());
				insert.setAssignment(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInfo()), assignmentMining, oldAssignmentMining);
											
				if(insert.getAssignment() != null && insert.getUser() != null && insert.getCourse() != null)//&& insert.getAction().equals("upload"))
				{   
					if(asSub.get(Long.valueOf(loadedItem.getInfo())) != null)
					for (AssignmentSubmissionsLMS loadedItem2 : asSub.get(Long.valueOf(loadedItem.getInfo()))) 
					{						
						if(loadedItem2.getAssignment() == Long.valueOf(loadedItem.getInfo()) && loadedItem2.getUserid().equals(loadedItem.getUserid()))// && loadedItem2.getTimemodified() == loadedItem.getTime())
		    			{

								insert.setGrade(Double.valueOf(loadedItem2.getGrade()));
							break;
							}
						}
				}			
				
				if(insert.getAssignment()==null){
		    		logger.debug("In Assignment_log_mining, assignment not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid()+ " and info: " + loadedItem.getInfo());
		    	}
				if(insert.getCourse()==null){
					logger.debug("In Assignment_log_mining, course not found for log: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
				}    			
				if(insert.getUser()==null){
					logger.debug("In Assignment_log_mining, user not found for log: " + loadedItem.getId() +" and user: " + loadedItem.getUserid());
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

  public Map<Long, ScormLogMining> generateScormLogMining(){
	  HashMap<Long, ScormLogMining> scormLogMining = new HashMap<Long, ScormLogMining>();
	  HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
  	    	
  	for (LogLMS loadedItem : logLms ) {
  		
  		 long uid = -1;
         
         if(idMapping.get(loadedItem.getUserid()) != null)
 			uid = idMapping.get(loadedItem.getUserid()).getId();
 		if(uid == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
 			uid = oldIdMapping.get(loadedItem.getUserid()).getId();
 		
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
  			
			if(!numericUserId)
			{
    			long id = -1;
       			if(idMapping.get(loadedItem.getUserid()) != null)
       			{
       				id = idMapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id,userMining, oldUserMining);
       			
       			}
       			if(id == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
       			{
       				id = oldIdMapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id,userMining, oldUserMining);
       		
       			}
       			if(id == -1 )
       			{
       				id = largestId + 1;
       				largestId = id;
       				idMapping.put(loadedItem.getUserid(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id), loadedItem.getUserid(), connector.getPlatformId()));
       				insert.setUser(Long.valueOf(connector.getPrefix() + "" + id),userMining, oldUserMining);
       			}
			}
  			insert.setAction(loadedItem.getAction());
  			insert.setTimestamp(loadedItem.getTime());
  			if(loadedItem.getInfo().matches("[0-9]+")){
  				insert.setScorm(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInfo()), scormMining, oldScormMining);
  			}
  			if(insert.getScorm() != null && insert.getCourse() != null && insert.getUser() != null)
  				scormLogMining.put(insert.getId(), insert);
  			if(insert.getScorm()==null){
  	    		logger.debug("In Scorm_log_mining, scorm package not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid()+ " and info: " + loadedItem.getInfo());
  	    	}
  			if(insert.getCourse()==null){
  				logger.debug("In Scorm_log_mining(scorm part), course not found for log: " + loadedItem.getId() + " and course: " + loadedItem.getCourse());
  			}    			
  			if(insert.getUser()==null){
  				logger.debug("In Scorm_log_mining(scorm part), user not found for log: " + loadedItem.getId() +" and user: " + loadedItem.getUserid());
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
  
    public Map<Long, QuizMining> generateQuizMining(){
    	
    	HashMap<Long, QuizMining> quiz_mining = new HashMap<Long, QuizMining>();
    	    	
    	for (QuizLMS loadedItem : quizLms) 
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
        	for (GradeItemsLMS loadedItem2 : gradeItemsLms) 
        	{
        		if(loadedItem2.getIteminstance() != null && loadedItem2.getItemmodule() != null){
        			logger.debug("Iteminstance"+loadedItem2.getIteminstance()+" QuizId"+loadedItem.getId());
        			if(loadedItem.getId()==loadedItem2.getIteminstance().longValue() && loadedItem2.getItemmodule().equals("quiz")){
        				insert.setMaxGrade(loadedItem2.getGrademax());
		    			break;
        			}
        		}
        		else{
        			logger.debug("Iteminstance or Itemmodule not found for QuizId"+loadedItem.getId() +" and type quiz and Iteminstance " +loadedItem2.getIteminstance()+" Itemmodule:" +loadedItem2.getItemmodule());
        		}
        	}
    		quiz_mining.put(insert.getId(), insert);    
        }
		return quiz_mining;    	
    }
 
    public HashMap<Long, AssignmentMining> generateAssignmentMining(){
    	
    	HashMap<Long, AssignmentMining> assignment_mining = new HashMap<Long, AssignmentMining>();
    	    	
    	for (AssignmentLMS loadedItem : assignmentLms) 
    	{
    		AssignmentMining insert = new AssignmentMining();
        
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId())); 
           	insert.setTitle(loadedItem.getName());
           	insert.setTimeOpen(loadedItem.getTimeavailable());
           	insert.setTimeClose(loadedItem.getTimedue());
           	insert.setTimeModified(loadedItem.getTimemodified());
           	insert.setPlatform(connector.getPlatformId());
        	for (GradeItemsLMS loadedItem2 : gradeItemsLms) 
        	{
        		if(loadedItem2.getIteminstance() != null && loadedItem2.getItemmodule() != null)
        		{
        			logger.debug("Iteminstance " + loadedItem2.getIteminstance() + " AssignmentId" + loadedItem.getId());
        			if(loadedItem.getId() == loadedItem2.getIteminstance().longValue() && loadedItem2.getItemmodule().equals("assignment")){
        				insert.setMaxGrade(loadedItem2.getGrademax());
        				break;
        			}
        		}
        		else{
        			logger.debug("Iteminstance or Itemmodule not found for AssignmentId"+loadedItem.getId() +" and type quiz and Iteminstance " +loadedItem2.getIteminstance()+" Itemmodule:" +loadedItem2.getItemmodule());
        		}
        	}
        	assignment_mining.put(insert.getId(),insert);
        }

		return assignment_mining;    	
    }
    
   public Map<Long, ScormMining> generateScormMining(){
    	
	   HashMap<Long, ScormMining> scorm_mining = new HashMap<Long, ScormMining>();
 
    	for (ScormLMS loadedItem : scormLms) 
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
    
    public Map<Long, QuizQuestionMining> generateQuizQuestionMining(){
    	
    	HashMap<Long, QuizQuestionMining> quiz_question_mining = new HashMap<Long, QuizQuestionMining>();
    	    	
    	for (QuizQuestionInstancesLMS loadedItem : quizQuestionInstancesLms) 
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
            	logger.debug("In Quiz_question_mining, quiz not found: " + loadedItem.getQuiz());
            }
    	}    	
		return quiz_question_mining;
    } 

    
    public Map<Long, QuestionMining> generateQuestionMining(){
    	
    	HashMap<Long, QuestionMining> question_mining = new HashMap<Long, QuestionMining>();
    	    	
    	for (QuestionLMS loadedItem : questionLms) 
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
    
    public Map<Long, QuizUserMining> generateQuizUserMining(){

    	HashMap<Long, QuizUserMining> quiz_user_mining = new HashMap<Long, QuizUserMining>();
    	for (GradeGradesLMS loadedItem : gradeGradesLms) 
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
           	
			if(!numericUserId)
			{
    			long id = -1;
       			if(idMapping.get(loadedItem.getUserid()) != null)
       			{
       				id = idMapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id,userMining, oldUserMining);
       		
       			}
       			if(id == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
       			{
       				id = oldIdMapping.get( loadedItem.getUserid()).getId();
       				insert.setUser(id,userMining, oldUserMining);
       			
       			}
       			if(id == -1 )
       			{
       				id = largestId + 1;
       				largestId = id;
       				idMapping.put( loadedItem.getUserid(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id),  loadedItem.getUserid(), connector.getPlatformId()));
       				insert.setUser(Long.valueOf(connector.getPrefix() + "" + id),userMining, oldUserMining);
       			}
			}
           	for (GradeItemsLMS loadedItem2 : gradeItemsLms) 
           	{
        		if(loadedItem2.getId() == loadedItem.getItemid() && loadedItem2.getIteminstance() != null){
        			insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getCourseid()), courseMining, oldCourseMining);
        			insert.setQuiz(Long.valueOf(connector.getPrefix() + "" + loadedItem2.getIteminstance()), quizMining, oldQuizMining);
           		   	if(insert.getQuiz()!= null && insert.getUser() != null){
           		   		
           		   	if(insert.getCourse() != null)
        		   		quiz_user_mining.put(insert.getId(), insert);
        		   	}
        		   	else{
        		   		logger.debug("In Quiz_user_mining, quiz not found for: Iteminstance: " + loadedItem2.getIteminstance() + " Itemmodule: " + loadedItem2.getItemmodule() +" course: " + loadedItem2.getCourseid() + " user: " + loadedItem.getUserid());
        		   	}
        		}
        	}			
    	}
    	return quiz_user_mining;
    }
    
    public Map<Long, ResourceMining> generateResourceMining() {
    	
    	HashMap<Long, ResourceMining> resource = new HashMap<Long, ResourceMining>();
    	
        for ( ResourceLMS loadedItem : resourceLms) 
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
        
       	for ( LogLMS loadedItem : logLms) 
       	{
           	if(resource.get(loadedItem.getCmid()) != null && (resource.get(loadedItem.getCmid()).getTimeCreated() == 0 || resource.get(loadedItem.getCmid()).getTimeCreated() > loadedItem.getTime()))
           	{           		
           		resource.get(loadedItem.getCmid()).setTimeCreated(loadedItem.getTime());
       		}
       	}           
		return resource;
    }
    
    
    public Map<Long, ResourceLogMining> generateResourceLogMining() {
    	HashMap<Long, ResourceLogMining> resourceLogMining = new HashMap<Long, ResourceLogMining>();
    	//A HashMap of list of timestamps. Every key represents one user, the according value is a list of his/her requests times.
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
        
        for (LogLMS loadedItem : logLms) 
        {
            
            long uid = -1;
            
            if(idMapping.get(loadedItem.getUserid()) != null)
    			uid = idMapping.get(loadedItem.getUserid()).getId();
    		if(uid == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
    			uid = oldIdMapping.get(loadedItem.getUserid()).getId();
    		
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
            	
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(idMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = idMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);
	       			
	       			}
	       			if(id == -1 && oldIdMapping.get( loadedItem.getUserid()) != null)
	       			{
	       				id = oldIdMapping.get( loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);
	       			
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				idMapping.put(loadedItem.getUserid(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id),  loadedItem.getUserid(), connector.getPlatformId()));
	       				insert.setUser(Long.valueOf(connector.getPrefix() + "" + id),userMining, oldUserMining);
	       			}
    			}
            	insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getCourse()), courseMining, oldCourseMining);
            	insert.setAction(loadedItem.getAction()); 
            	
            	
            	
            	if(loadedItem.getInfo().matches("[0-9]+")){
    				insert.setResource(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInfo()), resourceMining, oldResourceMining);
    			}    				
            	insert.setTimestamp(loadedItem.getTime());
    			if(insert.getResource()== null && !(loadedItem.getAction().equals("view all"))){
    				logger.debug("In Resource_log_mining, resource not found for log: " + loadedItem.getId() + " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo()+ " and action: " + loadedItem.getAction());
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
    
    
    public Map<Long, UserMining> generateUserMining(){
    	
    	HashMap<Long, UserMining>userMining = new HashMap<Long, UserMining>();
    	    	
    	for (UserLMS loadedItem : userLms) 
    	{
    	
    		UserMining insert = new UserMining();
    		
    		if(!numericUserId)
    		{
				long id = largestId + 1;
	   			largestId = id;
	   			idMapping.put(loadedItem.getId(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id), loadedItem.getId(), connector.getPlatformId()));
	   			insert.setId(Long.valueOf(connector.getPrefix() + "" +  id));
    		}
    		insert.setLogin(Encoder.createMD5(loadedItem.getLogin()));
           	insert.setLastLogin(loadedItem.getLastlogin());
           	insert.setFirstAccess(loadedItem.getFirstaccess());
           	insert.setLastAccess(loadedItem.getLastaccess());
           	insert.setCurrentLogin(loadedItem.getCurrentlogin());
			insert.setPlatform(connector.getPlatformId());
			userMining.put(insert.getId(), insert);
        }
		return userMining;    	
    }
    
    
    public Map<Long, WikiLogMining> generateWikiLogMining(){
    	HashMap<Long, WikiLogMining> wikiLogMining = new HashMap<Long, WikiLogMining>();
    	HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
    	HashMap<Long, CourseModulesLMS> couMod = new HashMap<Long, CourseModulesLMS>();
    	
    	for(CourseModulesLMS cm : courseModulesLms)
    	{
    		couMod.put(cm.getId(), cm);
    	}
    	
    	
        for (LogLMS loadedItem : logLms) {
            
            long uid = -1;
            
            if(idMapping.get(loadedItem.getUserid()) != null)
    			uid = idMapping.get(loadedItem.getUserid()).getId();
    		if(uid == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
    			uid = oldIdMapping.get(loadedItem.getUserid()).getId();
    		
    		
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
    			
    			if(!numericUserId)
    			{
	    			long id = -1;
	       			if(idMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = idMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);
	       			
	       			}
	       			if(id == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
	       			{
	       				id = oldIdMapping.get(loadedItem.getUserid()).getId();
	       				insert.setUser(id,userMining, oldUserMining);
	       				
	       			}
	       			if(id == -1 )
	       			{
	       				id = largestId + 1;
	       				largestId = id;
	       				idMapping.put(loadedItem.getUserid(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id), loadedItem.getUserid(), connector.getPlatformId()));
	       				insert.setUser(Long.valueOf(connector.getPrefix() + "" + id),userMining, oldUserMining);

	       			}
    			}
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
    
    
	public Map<Long, WikiMining> generateWikiMining(){
    	
		HashMap<Long, WikiMining> wiki_mining = new HashMap<Long, WikiMining>();
    	
    	for ( WikiLMS loadedItem : wikiLms) 
    	{
    		WikiMining insert = new WikiMining();
    	
    		insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
    		insert.setTitle(loadedItem.getName());
    		insert.setSummary(loadedItem.getSummary());
    		insert.setTimeModified(loadedItem.getTimemodified());
			insert.setPlatform(connector.getPlatformId());
    		wiki_mining.put(insert.getId(), insert);
    	}
       	for (LogLMS loadedItem : logLms) 
       	{
           	if(loadedItem.getModule().equals("Wiki") && wiki_mining.get(loadedItem.getCmid()) != null && (wiki_mining.get(loadedItem.getCmid()).getTimeCreated() == 0 || wiki_mining.get(loadedItem.getCmid()).getTimeCreated() > loadedItem.getTime()))
           	{           		
           		wiki_mining.get(loadedItem.getCmid()).setTimeCreated(loadedItem.getTime());
       		}
       	} 
    	return wiki_mining;
    }
	
	public Map<Long, RoleMining> generateRoleMining(){
//generate role tables
		HashMap<Long, RoleMining> role_mining = new HashMap<Long, RoleMining>();
    	
    	for ( RoleLMS loadedItem : roleLms)
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
	
	public Map<Long, LevelMining> generateLevelMining() {
		HashMap<Long, LevelMining> level_mining = new HashMap<Long, LevelMining>();
		
		for( CourseCategoriesLMS loadedItem : courseCategoriesLms)
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


	public Map<Long, LevelAssociationMining> generateLevelAssociationMining() {
		HashMap<Long, LevelAssociationMining> level_association = new HashMap<Long, LevelAssociationMining>();
		
		for(CourseCategoriesLMS loadedItem : courseCategoriesLms)
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
	public Map<Long, LevelCourseMining> generateLevelCourseMining() {
		HashMap<Long, LevelCourseMining> level_course = new HashMap<Long, LevelCourseMining>();
		
		for( ContextLMS loadedItem : contextLms)
		{
			if(loadedItem.getDepth() >=4 && loadedItem.getContextlevel() == 50)
			{
				LevelCourseMining insert = new LevelCourseMining();
				
				String[] s = loadedItem.getPath().split("/");
				insert.setId(Long.valueOf(connector.getPrefix() + "" + loadedItem.getId()));
				insert.setCourse(Long.valueOf(connector.getPrefix() + "" + loadedItem.getInstanceid()), courseMining, oldCourseMining);
				insert.setPlatform(connector.getPlatformId());
				for ( ContextLMS loadedItem2 : contextLms)
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

	public Map<Long, ChatMining> generateChatMining() {
		HashMap<Long, ChatMining> chat_mining = new HashMap<Long, ChatMining>();
		
		for( ChatLMS loadedItem : chatLms)
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


	public Map<Long, ChatLogMining> generateChatLogMining() {
		HashMap<Long, ChatLogMining> chatLogMining = new HashMap<Long, ChatLogMining>();
    	
        for (ChatLogLMS loadedItem : chatLogLms) 
        {
        	ChatLogMining insert = new ChatLogMining();
        	insert.setId(chatLogMining.size() + 1 + chatLogMax);
        	insert.setChat(Long.valueOf(connector.getPrefix() + "" + loadedItem.getChat_id()), chatMining, oldChatMining);
        	insert.setMessage(loadedItem.getMessage());
        	insert.setTimestamp(loadedItem.getTimestamp());
        	insert.setPlatform(connector.getPlatformId());
        	if(insert.getChat() != null)
        		insert.setCourse(insert.getChat().getCourse().getId(), courseMining, oldCourseMining);
        	insert.setDuration(0L);
        	
        	
			if(!numericUserId)
			{
    			long id = -1;
       			if(idMapping.get(loadedItem.getUserid()) != null)
       			{
       				id = idMapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id,userMining, oldUserMining);
       			
       			}
       			if(id == -1 && oldIdMapping.get(loadedItem.getUserid()) != null)
       			{
       				id = oldIdMapping.get(loadedItem.getUserid()).getId();
       				insert.setUser(id,userMining, oldUserMining);
       				
       			}
       			if(id == -1 )
       			{
       				id = largestId + 1;
       				largestId = id;
       				idMapping.put(loadedItem.getUserid(), new IDMappingMining(Long.valueOf(connector.getPrefix() + "" + id), loadedItem.getUserid(), connector.getPlatformId()));
       				insert.setUser(Long.valueOf(connector.getPrefix() + "" + id),userMining, oldUserMining);
       			}
       			
			}
  			if(insert.getUser()==null){
  				logger.debug("In Chat_log_mining(chat part), user not found for log: " + loadedItem.getId() +" and user: " + loadedItem.getUserid());
  			}
  			if(insert.getChat()==null){
  				logger.debug("In Chat_log_mining(chat part), chat not found for log: " + loadedItem.getId() +" and chat: " + loadedItem.getChat_id());
  			}
  			if(insert.getChat() != null && insert.getUser() != null && insert.getCourse() != null)
					
  				chatLogMining.put(insert.getId(), insert);
  			
        }
		return chatLogMining;
	}
	
}
