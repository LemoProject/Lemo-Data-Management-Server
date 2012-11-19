package de.lemo.dms.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

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
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.*;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;

public class TestDataCreatorMoodle {

	private ArrayList<AssignmentMining> assignmentList;
	private ArrayList<AssignmentLogMining> assignmentLogList;
	private ArrayList<ChatMining> chatList;
	private ArrayList<ChatLogMining> chatLogList;
	private ArrayList<CourseMining> courseList;
	private ArrayList<CourseLogMining> courseLogList;
	private ArrayList<CourseAssignmentMining> courseAssignmentList;
	private ArrayList<CourseForumMining> courseForumList;
	private ArrayList<CourseGroupMining> courseGroupList;
	private ArrayList<CourseQuizMining> courseQuizList;
	private ArrayList<CourseResourceMining> courseResourceList;
	private ArrayList<CourseScormMining> courseScormList;
	private ArrayList<CourseUserMining> courseUserList;
	private ArrayList<CourseWikiMining> courseWikiList;
	private ArrayList<DegreeMining> degreeList;
	private ArrayList<DegreeCourseMining> degreeCourseList;
	private ArrayList<DepartmentMining> departmentList;
	private ArrayList<DepartmentDegreeMining> departmentDegreeList;
	private ArrayList<ForumMining>forumList;
	private ArrayList<ForumLogMining> forumLogList;
	private ArrayList<GroupMining> groupList;
	private ArrayList<GroupUserMining> groupUserList;
	private ArrayList<QuestionMining> questionList;
	private ArrayList<QuestionLogMining> questionLogList;
	private ArrayList<QuizMining> quizList;
	private ArrayList<QuizLogMining> quizLogList;
	private ArrayList<QuizQuestionMining> quizQuestionList;
	private ArrayList<QuizUserMining> quizUserList;
	private ArrayList<ResourceLogMining> resourceLogList;
	private ArrayList<ResourceMining> resourceList;
	private ArrayList<RoleMining> roleList;
	private ArrayList<ScormMining> scormList;
	private ArrayList<ScormLogMining> scormLogList;
	private ArrayList<UserMining> userList;
	private ArrayList<WikiMining>wikiList;
	private ArrayList<WikiLogMining> wikiLogList;

	private static List<Log_LMS> log_lms = new ArrayList<Log_LMS>();
	private static List<Resource_LMS> resource_lms = new ArrayList<Resource_LMS>();
	private static List<Course_LMS> course_lms = new ArrayList<Course_LMS>();
	private static List<Forum_LMS> forum_lms = new ArrayList<Forum_LMS>();
	private static List<Wiki_LMS> wiki_lms = new ArrayList<Wiki_LMS>();
	private static List<User_LMS> user_lms = new ArrayList<User_LMS>();
	private static List<Quiz_LMS> quiz_lms = new ArrayList<Quiz_LMS>();
	private static List<Quiz_question_instances_LMS> quiz_question_instances_lms = new ArrayList<Quiz_question_instances_LMS>();
	private static List<Question_LMS> question_lms = new ArrayList<Question_LMS>();
	private static List<Groups_LMS> group_lms = new ArrayList<Groups_LMS>();
	private static List<Groups_members_LMS> group_members_lms = new ArrayList<Groups_members_LMS>();
	private static List<Question_states_LMS> question_states_lms = new ArrayList<Question_states_LMS>();
	private static List<Forum_posts_LMS> forum_posts_lms = new ArrayList<Forum_posts_LMS>();
	private static List<Role_LMS> role_lms = new ArrayList<Role_LMS>();
	private static List<Context_LMS> context_lms = new ArrayList<Context_LMS>();
	private static List<Role_assignments_LMS> role_assignments_lms = new ArrayList<Role_assignments_LMS>();
	private static List<Assignment_LMS> assignment_lms = new ArrayList<Assignment_LMS>();	
	private static List<Assignment_submissions_LMS> assignment_submission_lms = new ArrayList<Assignment_submissions_LMS>();	
	private static List<Quiz_grades_LMS> quiz_grades_lms= new ArrayList<Quiz_grades_LMS>();	
	private static List<Forum_discussions_LMS> forum_discussions_lms= new ArrayList<Forum_discussions_LMS>();	
	private static List<Scorm_LMS> scorm_lms= new ArrayList<Scorm_LMS>();	
	private static List<Grade_grades_LMS> grade_grades_lms= new ArrayList<Grade_grades_LMS>();	
	private static List<Grade_items_LMS> grade_items_lms= new ArrayList<Grade_items_LMS>();
	private static List<Chat_LMS> chat_lms= new ArrayList<Chat_LMS>();
	private static List<ChatLog_LMS> chat_log_lms= new ArrayList<ChatLog_LMS>();
	private static List<CourseCategories_LMS> course_categories_lms= new ArrayList<CourseCategories_LMS>();
	
	private HashMap<Long, CourseMining> couAssMap;
	private HashMap<Long, CourseMining> couForMap;
	private HashMap<Long, CourseMining> couGroMap;
	private HashMap<Long, CourseMining> couQuiMap;
	private HashMap<Long, CourseMining> couUseMap;
	private HashMap<Long, CourseMining> couResMap;
	private HashMap<Long, CourseMining> couScoMap;
	private HashMap<Long, CourseMining> couWikMap;
	
	
	private HashMap<Long, DegreeMining> degCouMap;
	private HashMap<Long, DepartmentMining> depDegMap;


	private void generateUserLMS()
	{
		for(UserMining item : userList)
		{
			User_LMS lms = new User_LMS();
			
			lms.setId(item.getId());
			lms.setCurrentlogin(item.getCurrentlogin());
			lms.setLastaccess(item.getLastaccess());
			lms.setFirstaccess(item.getFirstaccess());
			lms.setLastlogin(item.getLastlogin());
			
			user_lms.add(lms);
		}
	}
	
	private void generateRoleLMS()
	{
		for(RoleMining item : roleList)
		{
			Role_LMS lms = new Role_LMS();
			
			lms.setId(item.getId());
			lms.setName(item.getName());
			lms.setShortname(item.getShortname());
			lms.setSortorder(item.getSortorder());
			lms.setDescription(item.getDescription());
			
			role_lms.add(lms);
		}
	}
	
	private void generateWikiLMS()
	{
		for(WikiMining item : wikiList)
		{
			Wiki_LMS lms = new Wiki_LMS();
			lms.setId(item.getId());
			lms.setName(item.getTitle());
			lms.setTimemodified(item.getTimemodified());
			lms.setSummary(item.getSummary());
			lms.setCourse(couWikMap.get(item.getId()).getId());
			
			wiki_lms.add(lms);
		}
	}
	
	private void generateScormLMS()
	{
		for(ScormMining item : scormList)
		{
			Scorm_LMS lms = new Scorm_LMS();
			lms.setId(item.getId());
			lms.setName(item.getTitle());
			lms.setTimemodified(item.getTimemodified());
			lms.setMaxgrade(item.getMaxgrade());
			lms.setTimemodified(item.getTimemodified());
			lms.setCourse(couScoMap.get(item.getId()).getId());
			
			scorm_lms.add(lms);
		}
	}
	
	private void generateContextLMS()
	{
		
		HashMap<Long, Context_LMS> depIdMap = new HashMap<Long, Context_LMS>();
		HashMap<Long, Context_LMS> degIdMap = new HashMap<Long, Context_LMS>();
		HashMap<Long, Context_LMS> couIdMap = new HashMap<Long, Context_LMS>();
		
		//Create entries (context, course_categories) for all departments
		for(DepartmentMining item : departmentList)
		{
			Context_LMS lms = new Context_LMS();
			CourseCategories_LMS lms2 = new CourseCategories_LMS();
			
			lms2.setId(course_categories_lms.size() + 1);
			lms2.setTitle(item.getTitle());
			lms2.setDepth(1);
			lms2.setPath("/" + item.getId());
			
			lms.setContextlevel(40);
			lms.setDepth(2);
			lms.setInstanceid(lms2.getId());
			lms.setId(context_lms.size() + 1);
			lms.setPath("/1/"+lms.getId());
			
			
			
			course_categories_lms.add(lms2);
			
			context_lms.add(lms);
			depIdMap.put(item.getId(), lms);
		}
		
		//Create entries (context, course_categories) for all degrees
		for(DegreeMining item : degreeList)
		{
			CourseCategories_LMS lms2 = new CourseCategories_LMS();
			
			DepartmentMining dm = depDegMap.get(item.getId());
			
			lms2.setId(course_categories_lms.size() + 1);
			lms2.setTitle(item.getTitle());
			lms2.setDepth(2);
			lms2.setPath("/" + depIdMap.get(dm.getId()).getId() + "/" + lms2.getId());
			
			Context_LMS lms = new Context_LMS();
			lms.setContextlevel(40);
			lms.setDepth(3);
			lms.setInstanceid(lms2.getId());
			lms.setId(context_lms.size() + 1);
			Context_LMS cl = depIdMap.get(dm.getId());
			lms.setPath(cl.getPath() + "/" + lms.getId());
			
			degIdMap.put(item.getId(), lms);
			context_lms.add(lms);
			
			course_categories_lms.add(lms2);
			
			
		}
		//Create entries (context) for all courses
		for(DegreeCourseMining item : degreeCourseList)
		{
			Context_LMS lms = new Context_LMS();
			
			lms.setId(context_lms.size() + 1);
			lms.setDepth(4);
			lms.setContextlevel(50);
			lms.setInstanceid(item.getCourse().getId());
			Context_LMS cl = degIdMap.get(item.getDegree().getId());
			lms.setPath(cl.getPath() + "/" + lms.getId());

			couIdMap.put(item.getId(), lms);
			
			context_lms.add(lms);
		}
		//Create entries (role_assignments) for all users
		for(CourseUserMining item : courseUserList)
		{
			Role_assignments_LMS lms2 = new Role_assignments_LMS();
			
			lms2.setId(item.getId());
			lms2.setRoleid(item.getRole().getId());
			lms2.setUserid(item.getUser().getId());
			lms2.setTimeend(item.getEnrolend());
			lms2.setTimestart(item.getEnrolstart());
			lms2.setContextid(couIdMap.get(item.getCourse().getId()).getId());
			
			role_assignments_lms.add(lms2);
		}
		
		
		
	}
	
	private void generateLogLMS()
	{
		List<ILogMining> logs = new ArrayList<ILogMining>();
		logs.addAll(resourceLogList);
		logs.addAll(assignmentLogList);
		logs.addAll(forumLogList);
		logs.addAll(courseLogList);
		logs.addAll(quizLogList);
		logs.addAll(wikiLogList);
		logs.addAll(scormLogList);
		
		Collections.sort(logs);
		
		for(int i = 0; i < logs.size(); i++)
		{
			logs.get(i).setId(i + 1);
		}
		
		
		for(ResourceLogMining item : resourceLogList)
		{
			Log_LMS lms = new Log_LMS();
			
			lms.setId(item.getId());
			lms.setCourse(item.getCourse().getId());
			lms.setModule("resource");
			lms.setTime(item.getTimestamp());
			lms.setAction(item.getAction());
			lms.setInfo(item.getResource().getId()+"");
			lms.setUserid(item.getUser().getId());
			
			log_lms.add(lms);
		}
		HashMap<Long, Forum_discussions_LMS> forDisSet = new HashMap<Long, Forum_discussions_LMS>();
		for(ForumLogMining item : forumLogList)
		{
			Log_LMS lms = new Log_LMS();
			Forum_posts_LMS lms2 = new Forum_posts_LMS();
			
			lms2.setId(forum_posts_lms.size() + 1);
			lms2.setMessage(item.getMessage());
			lms2.setSubject(item.getSubject());
			lms2.setUserid(item.getUser().getId());
			lms2.setCreated(item.getTimestamp());
			lms2.setModified(item.getTimestamp());
			
			forum_posts_lms.add(lms2);
			
			if((item.getAction().equals("add discussion") || item.getAction().equals("view discussion")))
			{
				Forum_discussions_LMS lms3 = new Forum_discussions_LMS();
				
				lms3.setId(item.getForum().getId());
				lms3.setForum(item.getForum().getId());
				
				forDisSet.put(lms3.getId(), lms3);
				
			}
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("forum");
			lms.setInfo(item.getForum().getId()+"");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());
			
			log_lms.add(lms);
		}
		forum_discussions_lms.addAll(forDisSet.values());
		for(AssignmentLogMining item : assignmentLogList)
		{
			Log_LMS lms = new Log_LMS();
			
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("assignment");
			lms.setInfo(item.getAssignment().getId()+"");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());
			
			log_lms.add(lms);
			
			if(item.getAction().equals("upload"))
			{
				Assignment_submissions_LMS lms2 = new Assignment_submissions_LMS();
				
				lms2.setGrade((long)item.getGrade());
				lms2.setAssignment(item.getAssignment().getId());
				lms2.setUserid(item.getUser().getId()+"");
				lms2.setTimemodified(item.getTimestamp());			
				lms2.setId(assignment_submission_lms.size() + 1);
				
				assignment_submission_lms.add(lms2);
			}
		}
		for(QuizLogMining item : quizLogList)
		{
			Log_LMS lms = new Log_LMS();
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("quiz");
			lms.setInfo(item.getQuiz().getId()+"");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());
			
			if(!item.getAction().equals("review"))
			{
				Quiz_grades_LMS lms2 = new Quiz_grades_LMS();
				lms2.setGrade(item.getGrade());
				lms2.setTimemodified(item.getTimestamp());
				lms2.setUserid(item.getUser().getId()+"");
				lms2.setQuiz(item.getQuiz().getId());
				lms2.setId(quiz_grades_lms.size() + 1);
				
				quiz_grades_lms.add(lms2);
			}
			
			log_lms.add(lms);
		}
		/*
		for(QuestionLogMining item : questionLogList)
		{
			Log_LMS lms = new Log_LMS();
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("forum");
			lms.setInfo(item.getQuestion().getId()+"");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId()+"");
			log_lms.add(lms);
		}*/
		for(ScormLogMining item : scormLogList)
		{
			Log_LMS lms = new Log_LMS();
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("scorm");
			lms.setInfo(item.getScorm().getId()+"");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());
			
			log_lms.add(lms);
		}
		for(WikiLogMining item : wikiLogList)
		{
			Log_LMS lms = new Log_LMS();
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("wiki");
			lms.setInfo(item.getWiki().getId()+"");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());
			lms.setCmid(item.getWiki().getId());
			
			log_lms.add(lms);
		}
		for(CourseLogMining item : courseLogList)
		{
			Log_LMS lms = new Log_LMS();
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("course");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());

			log_lms.add(lms);
		}
	}
	
	private void generateResourceLMS()
	{
		for(ResourceMining item : resourceList)
		{
			Resource_LMS lms = new Resource_LMS();
			lms.setId(item.getId());
			lms.setType(item.getType());
			lms.setName(item.getTitle());
			lms.setTimemodified(item.getTimemodified());
			if(couResMap.get(item.getId()) != null)
				lms.setCourse(couResMap.get(item.getId()).getId());		
			
			resource_lms.add(lms);
		}
	}
	
	private void generateForumLMS()
	{
		HashMap<Long, Long> cFMap = new HashMap<Long, Long>();
		for(Iterator<CourseForumMining> iter = courseForumList.iterator(); iter.hasNext();)
		{
			CourseForumMining item = iter.next();
			cFMap.put(item.getForum().getId(), item.getCourse().getId());
		}
		for(ForumMining item : forumList)
		{
			Forum_LMS lms = new Forum_LMS();
			lms.setId(item.getId());
			lms.setTimemodified(item.getTimemodified());
			lms.setName(item.getTitle());
			lms.setIntro(item.getSummary());
			lms.setCourse(cFMap.get(lms.getId()));
			
			forum_lms.add(lms);
			
		}
		

	}
	
	private void generateCourseLMS()
	{
		
		for(CourseMining item : courseList)
		{
			Course_LMS lms = new Course_LMS();
			lms.setId(item.getId());
			lms.setFullname(item.getTitle());
			lms.setShortname(item.getShortname());
			lms.setTimemodified(item.getTimemodified());
			lms.setTimecreated(item.getTimecreated());
			lms.setEnrolstartdate(item.getEnrolstart());
			lms.setEnrolenddate(item.getEnrolend());
			lms.setStartdate(item.getStartdate());
			
			course_lms.add(lms);
		}
	}
	
	private void generateChatLogLMS()
	{
		HashMap<Long, Long> couId = new HashMap<Long, Long>();
		
		for(ChatLogMining item : chatLogList)
		{
			ChatLog_LMS lms = new ChatLog_LMS();
			lms.setMessage(item.getMessage());
			
			lms.setTimestamp(item.getTimestamp());
			lms.setChatId(item.getChat().getId());
			lms.setUserId(item.getUser().getId());
			lms.setId(item.getId());
			
			couId.put(item.getChat().getId(), item.getCourse().getId());
			
			chat_log_lms.add(lms);
		}
		
		for(ChatMining item : chatList)
		{
			Chat_LMS lms = new Chat_LMS();
			lms.setId(item.getId());
			if(couId.get(item.getId()) != null)
				lms.setCourse(couId.get(item.getId()));
			lms.setTitle(item.getTitle());
			lms.setDescription(item.getDescription());
			lms.setChattime(item.getChattime());
			
			
			chat_lms.add(lms);
		}
	}
	
	private void generateAssignmentLMS()
	{
		for(AssignmentMining item : assignmentList)
		{
			Assignment_LMS lms = new Assignment_LMS();
			lms.setId(item.getId());
			lms.setName(item.getTitle());
			lms.setTimeavailable(item.getTimeopen());
			lms.setTimedue(item.getTimeclose());
			lms.setTimemodified(item.getTimemodified());
			lms.setDescription("description");
			if(couAssMap.get(item.getId()) != null)
				lms.setCourse(couAssMap.get(item.getId()).getId());
			
			Grade_items_LMS lms2 = new Grade_items_LMS();
			lms2.setIteminstance(item.getId());
			lms2.setItemmodule("assignment");
			lms2.setGrademax(item.getMaxgrade());
			lms2.setId(grade_items_lms.size());
			
			grade_items_lms.add(lms2);			
			assignment_lms.add(lms);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void getDataFromDB()
	{
		IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
		
		//accessing DB by creating a session and a transaction using HibernateUtil
        Session session = dbHandler.getMiningSession();
        session.clear();	
        

        
        Query assQuery = session.createQuery("from AssignmentMining x order by x.id asc");
        assignmentList = (ArrayList<AssignmentMining>) assQuery.list();
        
        Query assLogQuery = session.createQuery("from AssignmentLogMining x order by x.id asc");
        assignmentLogList = (ArrayList<AssignmentLogMining>) assLogQuery.list();
        
        Query chaQuery = session.createQuery("from ChatMining x order by x.id asc");
        chatList = (ArrayList<ChatMining>) chaQuery.list();
        
        Query chaLogQuery = session.createQuery("from ChatLogMining x order by x.id asc");
        chatLogList = (ArrayList<ChatLogMining>) chaLogQuery.list();
        
        Query couQuery = session.createQuery("from CourseMining x order by x.id asc");
        courseList = (ArrayList<CourseMining>) couQuery.list();
        
        Query couLogQuery = session.createQuery("from CourseLogMining x order by x.id asc");
        courseLogList = (ArrayList<CourseLogMining>) couLogQuery.list();
        
        Query couAssQuery = session.createQuery("from CourseAssignmentMining x order by x.id asc");
        courseAssignmentList = (ArrayList<CourseAssignmentMining>) couAssQuery.list();
        
        Query couForumQuery = session.createQuery("from CourseForumMining x order by x.id asc");
        courseForumList = (ArrayList<CourseForumMining>) couForumQuery.list();
        
        Query couGroupQuery = session.createQuery("from CourseGroupMining x order by x.id asc");
        courseGroupList = (ArrayList<CourseGroupMining>) couGroupQuery.list();
        
        Query couQuizQuery = session.createQuery("from CourseQuizMining x order by x.id asc");
        courseQuizList = (ArrayList<CourseQuizMining>) couQuizQuery.list();
        
        Query couResQuery = session.createQuery("from CourseResourceMining x order by x.id asc");
        courseResourceList = (ArrayList<CourseResourceMining>) couResQuery.list();
        
        Query couScormQuery = session.createQuery("from CourseScormMining x order by x.id asc");
        courseScormList = (ArrayList<CourseScormMining>) couScormQuery.list();
        
        Query couUserQuery = session.createQuery("from CourseUserMining x order by x.id asc");
        courseUserList = (ArrayList<CourseUserMining>) couUserQuery.list();
        
        Query couWikiQuery = session.createQuery("from CourseWikiMining x order by x.id asc");
        courseWikiList = (ArrayList<CourseWikiMining>) couWikiQuery.list();
        
        Query degQuery = session.createQuery("from DegreeMining x order by x.id asc");
        degreeList = (ArrayList<DegreeMining>) degQuery.list();
        
        Query degCouQuery = session.createQuery("from DegreeCourseMining x order by x.id asc");
        degreeCourseList = (ArrayList<DegreeCourseMining>) degCouQuery.list();
        
        Query depQuery = session.createQuery("from DepartmentMining x order by x.id asc");
        departmentList = (ArrayList<DepartmentMining>) depQuery.list();
        
        Query depDegQuery = session.createQuery("from DepartmentDegreeMining x order by x.id asc");
        departmentDegreeList = (ArrayList<DepartmentDegreeMining>) depDegQuery.list();
                
        Query forQuery = session.createQuery("from ForumMining x order by x.id asc");
        forumList = (ArrayList<ForumMining>) forQuery.list();
        
        Query forLogQuery = session.createQuery("from ForumLogMining x order by x.id asc");
        forumLogList = (ArrayList<ForumLogMining>) forLogQuery.list();
        
        Query groupQuery = session.createQuery("from GroupMining x order by x.id asc");
        groupList = (ArrayList<GroupMining>) groupQuery.list();
        
        Query groupUserQuery = session.createQuery("from GroupUserMining x order by x.id asc");
        groupUserList = (ArrayList<GroupUserMining>) groupUserQuery.list();
        
        Query queQuery = session.createQuery("from QuestionMining x order by x.id asc");
        questionList = (ArrayList<QuestionMining>) queQuery.list();
        
        Query queLogQuery = session.createQuery("from QuestionLogMining x order by x.id asc");
        questionLogList = (ArrayList<QuestionLogMining>) queLogQuery.list();
        
        Query quiLogQuery = session.createQuery("from QuizLogMining x order by x.id asc");
        quizLogList = (ArrayList<QuizLogMining>) quiLogQuery.list();
        
        Query quiQuery = session.createQuery("from QuizMining x order by x.id asc");
        quizList = (ArrayList<QuizMining>) quiQuery.list();
        
        Query quiQuestionQuery = session.createQuery("from QuizQuestionMining x order by x.id asc");
        quizQuestionList = (ArrayList<QuizQuestionMining>) quiQuestionQuery.list();
        
        Query quiUserQuery = session.createQuery("from QuizUserMining x order by x.id asc");
        quizUserList = (ArrayList<QuizUserMining>) quiUserQuery.list();
        
        Query resQuery = session.createQuery("from ResourceMining x order by x.id asc");
        resourceList = (ArrayList<ResourceMining>) resQuery.list();
        
        Query resLogQuery = session.createQuery("from ResourceLogMining x order by x.id asc");
        resourceLogList = (ArrayList<ResourceLogMining>) resLogQuery.list();
        
        Query roleQuery = session.createQuery("from RoleMining x order by x.id asc");
        roleList = (ArrayList<RoleMining>) roleQuery.list();
        
        Query scormQuery = session.createQuery("from ScormMining x order by x.id asc");
        scormList = (ArrayList<ScormMining>) scormQuery.list();
        
        Query scormLogQuery = session.createQuery("from ScormLogMining x order by x.id asc");
        scormLogList = (ArrayList<ScormLogMining>) scormLogQuery.list();
        
        Query userQuery = session.createQuery("from UserMining x order by x.id asc");
        userList = (ArrayList<UserMining>) userQuery.list();

        Query wikQuery = session.createQuery("from WikiMining x order by x.id asc");
        wikiList = (ArrayList<WikiMining>) wikQuery.list();
        
        Query wikLogQuery = session.createQuery("from WikiLogMining x order by x.id asc");
        wikiLogList = (ArrayList<WikiLogMining>) wikLogQuery.list();

        couAssMap = new HashMap<Long, CourseMining>();
        couForMap = new HashMap<Long, CourseMining>();
        couGroMap = new HashMap<Long, CourseMining>();
        couQuiMap = new HashMap<Long, CourseMining>();
        couResMap = new HashMap<Long, CourseMining>();
        couScoMap = new HashMap<Long, CourseMining>();
        couUseMap= new HashMap<Long, CourseMining>();
        couWikMap = new HashMap<Long, CourseMining>();
        
        degCouMap = new HashMap<Long, DegreeMining>();
        depDegMap = new HashMap<Long, DepartmentMining>();
        
        for(CourseAssignmentMining ca : courseAssignmentList)
        	couAssMap.put(ca.getAssignment().getId(), ca.getCourse());
        
        for(CourseGroupMining ca : courseGroupList)
        	couGroMap.put(ca.getGroup().getId(), ca.getCourse());
        
        for(CourseQuizMining ca : courseQuizList)
        	couQuiMap.put(ca.getQuiz().getId(), ca.getCourse());
        
        for(CourseUserMining ca : courseUserList)
        	couUseMap.put(ca.getUser().getId(), ca.getCourse());
        
        for(CourseForumMining ca : courseForumList)
        	couForMap.put(ca.getForum().getId(), ca.getCourse());
        
        for(CourseResourceMining cr : courseResourceList)
       		couResMap.put(cr.getResource().getId(), cr.getCourse());
        
        for(CourseWikiMining cw : courseWikiList)
        	couWikMap.put(cw.getWiki().getId(), cw.getCourse());
        
        for(CourseScormMining cs : courseScormList)
        	couScoMap.put(cs.getScorm().getId(), cs.getCourse());
        
        
        for(DegreeCourseMining dc : degreeCourseList)
        	degCouMap.put(dc.getCourse().getId(), dc.getDegree());
        
        for(DepartmentDegreeMining dd : departmentDegreeList)
        	depDegMap.put(dd.getDegree().getId(), dd.getDepartment());
        

        session.clear();
        session.close();
        
	}
	
	private void generateGroupLMS()
	{
		for(GroupMining item : groupList)
		{
			Groups_LMS lms = new Groups_LMS();
			lms.setId(item.getId());
			lms.setCourseid(couGroMap.get(item.getId()).getId());
			lms.setTimecreated(item.getTimecreated());
			lms.setTimemodified(item.getTimemodified());
			
			group_lms.add(lms);
			
		}
	}
	
	private void generateGroupMembersLMS()
	{
		for(GroupUserMining item : groupUserList)
		{
			Groups_members_LMS lms = new Groups_members_LMS();
			
			lms.setId(item.getId());
			lms.setGroupid(item.getGroup().getId());
			lms.setUserid(item.getUser().getId());
			lms.setTimeadded(item.getTimestamp());
			
			group_members_lms.add(lms);
		}
	}
	
	private void generateQuizLMS()
	{
		HashMap<Long, Long> qQMap = new HashMap<Long, Long>();
		for(Iterator<CourseQuizMining> iter = courseQuizList.iterator(); iter.hasNext();)
		{
			CourseQuizMining item = iter.next();
			qQMap.put(item.getQuiz().getId(), item.getCourse().getId());
		}
		for(QuizMining item : quizList)
		{
			Quiz_LMS lms = new Quiz_LMS();
			Grade_items_LMS lms2 = new Grade_items_LMS();
			
			lms.setId(item.getId());
			lms.setCourse(qQMap.get(lms.getId()));
    		lms.setName(item.getTitle());
    		lms.setTimeopen(item.getTimeopen());
    		lms.setTimeclose(item.getTimeclose());
    		lms.setTimecreated(item.getTimecreated());
    		lms.setTimemodified(item.getTimemodified());
    		

    		lms2.setId(grade_items_lms.size() + 1);
    		lms2.setGrademax(item.getMaxgrade());
    		lms2.setIteminstance(item.getId());
    		lms2.setItemmodule("quiz");
    		
    		grade_items_lms.add(lms2);
    		quiz_lms.add(lms);
		}
	}
	
	private void generateQuestionLMS()
	{
		for(QuestionMining item : questionList)
		{
			Question_LMS lms = new Question_LMS();
			
			lms.setId(item.getId());
			lms.setName(item.getTitle());
			lms.setQuestiontext(item.getText());
			lms.setQtype(item.getType());
			lms.setTimecreated(item.getTimecreated());
			lms.setTimemodified(item.getTimemodified());
		
			question_lms.add(lms);
		}
	}
	
	private void generateQuizQuestionInstancesLMS()
	{
		//HashMap<Long, Quiz_question_instances_LMS> tempMap = new HashMap<Long, Quiz_question_instances_LMS>(); 
		for(QuizQuestionMining item : quizQuestionList)
		{
			Quiz_question_instances_LMS lms = new Quiz_question_instances_LMS();
			
			lms.setId(item.getId());
			lms.setQuestion(item.getQuestion().getId());
			lms.setQuiz(item.getQuiz().getId());

			//tempMap.put(lms.getId(), lms);
			quiz_question_instances_lms.add(lms);
			
			
		}
		//quiz_question_instances_lms.addAll(tempMap.values());
	}
	
	private void generateGradeGradesLMS()
	{
		for(QuizUserMining item : quizUserList)
		{
			Grade_grades_LMS lms = new Grade_grades_LMS();
			
			lms.setId(item.getId());
			lms.setRawgrade(item.getRawgrade());
			lms.setFinalgrade(item.getFinalgrade());
			lms.setUserid(item.getUser().getId());
			lms.setTimemodified(item.getTimemodified());
			lms.setItemid(grade_items_lms.size() + 1);
			
			Grade_items_LMS lms2 = new Grade_items_LMS();
			lms2.setCourseid(item.getCourse().getId());
			lms2.setIteminstance(item.getQuiz().getId());
			lms2.setId(lms.getItemid());
			
			grade_items_lms.add(lms2);
			grade_grades_lms.add(lms);
		}
	}
	
	private void generateQuestionStatesLMS()
	{
		for(QuestionLogMining item : questionLogList)
		{
			Question_states_LMS lms = new Question_states_LMS();
			
			lms.setId(item.getId());
			lms.setAnswer(item.getAnswers());
			lms.setQuestion(item.getQuestion().getId());
			lms.setPenalty(item.getPenalty());
			lms.setTimestamp(item.getTimestamp());
			

			if(item.getAction().equals("OPEN"))
				lms.setEvent((short) 0);
			else if(item.getAction().equals("NAVIGATE"))
				lms.setEvent((short) 1);
			else if(item.getAction().equals("SAVE"))
				lms.setEvent((short) 2);
			else if(item.getAction().equals("GRADE"))
				lms.setEvent((short) 3);
			else if(item.getAction().equals("DUPLICATE"))
				lms.setEvent((short) 4);
			else if(item.getAction().equals("VALIDATE"))
				lms.setEvent((short) 5);
			else if(item.getAction().equals("CLOSEANDGRADE"))
				lms.setEvent((short) 6);
			else if(item.getAction().equals("SUBMIT"))
				lms.setEvent((short) 7);
			else if(item.getAction().equals("CLOSE"))
				lms.setEvent((short) 8);
			else if(item.getAction().equals("MANUALGRADE"))
				lms.setEvent((short) 9);
			
			question_states_lms.add(lms);
		}
	}
	
	public void writeSourceDB()
	{
		List<Collection<?>> all = new ArrayList<Collection<?>>();
		
		generateUserLMS();
		generateAssignmentLMS();
		generateResourceLMS();
		generateForumLMS();
		generateCourseLMS();
		generateGroupLMS();
		generateRoleLMS();
		
		generateScormLMS();
		generateWikiLMS();
		generateGroupMembersLMS();
		generateQuizLMS();
		generateQuestionLMS();
		generateQuizQuestionInstancesLMS();
		generateGradeGradesLMS();
		generateQuestionStatesLMS();
		
		
		
		generateLogLMS();
		generateContextLMS();
		generateChatLogLMS();
		
		
		all.add(user_lms);
		all.add(quiz_lms);
		all.add(quiz_question_instances_lms);
		all.add(question_lms);
		all.add(assignment_lms);
		all.add(chat_lms);
		all.add(group_members_lms);
		all.add(group_lms);
		all.add(resource_lms);
		all.add(role_lms);
		
		all.add(forum_lms);
		all.add(course_lms);
		all.add(scorm_lms);
		all.add(wiki_lms);
		all.add(chat_log_lms);
		all.add(log_lms);
		all.add(forum_posts_lms);
		all.add(forum_discussions_lms);
		all.add(context_lms);
		all.add(course_categories_lms);
		all.add(role_assignments_lms);
		all.add(grade_items_lms);
		all.add(grade_grades_lms);
		all.add(question_states_lms);
		all.add(quiz_grades_lms);
		all.add(assignment_submission_lms);
		
		IDBHandler dbHandler = new HibernateDBHandler();
		Session session = dbHandler.getMiningSession();
		dbHandler.saveCollectionToDB(session, all);
		dbHandler.closeSession(session);
	}
}
