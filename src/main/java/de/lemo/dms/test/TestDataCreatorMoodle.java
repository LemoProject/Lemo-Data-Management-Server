/**
 * File ./main/java/de/lemo/dms/test/TestDataCreatorMoodle.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.AssignmentLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.AssignmentSubmissionsLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.ChatLogLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.ChatLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.ContextLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.CourseCategoriesLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.CourseLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.ForumLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.ForumDiscussionsLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.ForumPostsLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.GradeGradesLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.GradeItemsLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.GroupsLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.GroupsMembersLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.LogLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.QuestionLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.QuestionStatesLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.QuizLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.QuizGradesLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.QuizQuestionInstancesLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.ResourceLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.RoleLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.RoleAssignmentsLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.ScormLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.UserLMS;
import de.lemo.dms.connectors.moodleNumericId.moodleDBclass.WikiLMS;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
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
import de.lemo.dms.db.miningDBclass.LevelCourseMining;
import de.lemo.dms.db.miningDBclass.LevelMining;
import de.lemo.dms.db.miningDBclass.LevelAssociationMining;
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
	private ArrayList<ForumMining> forumList;
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
	private ArrayList<WikiMining> wikiList;
	private ArrayList<WikiLogMining> wikiLogList;
	private ArrayList<LevelMining> departmentList;
	private ArrayList<LevelMining> degreeList;
	private ArrayList<LevelAssociationMining> departmentDegreeList;
	private ArrayList<LevelCourseMining> degreeCourseList;

	private static List<LogLMS> log_lms = new ArrayList<LogLMS>();
	private static List<ResourceLMS> resource_lms = new ArrayList<ResourceLMS>();
	private static List<CourseLMS> course_lms = new ArrayList<CourseLMS>();
	private static List<ForumLMS> forum_lms = new ArrayList<ForumLMS>();
	private static List<WikiLMS> wiki_lms = new ArrayList<WikiLMS>();
	private static List<UserLMS> user_lms = new ArrayList<UserLMS>();
	private static List<QuizLMS> quiz_lms = new ArrayList<QuizLMS>();
	private static List<QuizQuestionInstancesLMS> quiz_question_instances_lms = new ArrayList<QuizQuestionInstancesLMS>();
	private static List<QuestionLMS> question_lms = new ArrayList<QuestionLMS>();
	private static List<GroupsLMS> group_lms = new ArrayList<GroupsLMS>();
	private static List<GroupsMembersLMS> group_members_lms = new ArrayList<GroupsMembersLMS>();
	private static List<QuestionStatesLMS> question_states_lms = new ArrayList<QuestionStatesLMS>();
	private static List<ForumPostsLMS> forum_posts_lms = new ArrayList<ForumPostsLMS>();
	private static List<RoleLMS> role_lms = new ArrayList<RoleLMS>();
	private static List<ContextLMS> context_lms = new ArrayList<ContextLMS>();
	private static List<RoleAssignmentsLMS> role_assignments_lms = new ArrayList<RoleAssignmentsLMS>();
	private static List<AssignmentLMS> assignment_lms = new ArrayList<AssignmentLMS>();
	private static List<AssignmentSubmissionsLMS> assignment_submission_lms = new ArrayList<AssignmentSubmissionsLMS>();
	private static List<QuizGradesLMS> quiz_grades_lms = new ArrayList<QuizGradesLMS>();
	private static List<ForumDiscussionsLMS> forum_discussions_lms = new ArrayList<ForumDiscussionsLMS>();
	private static List<ScormLMS> scorm_lms = new ArrayList<ScormLMS>();
	private static List<GradeGradesLMS> grade_grades_lms = new ArrayList<GradeGradesLMS>();
	private static List<GradeItemsLMS> grade_items_lms = new ArrayList<GradeItemsLMS>();
	private static List<ChatLMS> chat_lms = new ArrayList<ChatLMS>();
	private static List<ChatLogLMS> chat_log_lms = new ArrayList<ChatLogLMS>();
	private static List<CourseCategoriesLMS> course_categories_lms = new ArrayList<CourseCategoriesLMS>();

	private HashMap<Long, CourseMining> couAssMap;
	private HashMap<Long, CourseMining> couForMap;
	private HashMap<Long, CourseMining> couGroMap;
	private HashMap<Long, CourseMining> couQuiMap;
	private HashMap<Long, CourseMining> couUseMap;
	private HashMap<Long, CourseMining> couResMap;
	private HashMap<Long, CourseMining> couScoMap;
	private HashMap<Long, CourseMining> couWikMap;

	private HashMap<Long, LevelMining> degCouMap;
	private HashMap<Long, LevelMining> depDegMap;

	private void generateUserLMS()
	{
		for (final UserMining item : this.userList)
		{
			final UserLMS lms = new UserLMS();

			lms.setId(item.getId());
			lms.setCurrentlogin(item.getCurrentLogin());
			lms.setLastaccess(item.getLastAccess());
			lms.setFirstaccess(item.getFirstAccess());
			lms.setLastlogin(item.getLastLogin());

			TestDataCreatorMoodle.user_lms.add(lms);
		}
	}

	private void generateRoleLMS()
	{
		for (final RoleMining item : this.roleList)
		{
			final RoleLMS lms = new RoleLMS();

			lms.setId(item.getId());
			lms.setName(item.getName());
			lms.setShortname(item.getShortname());
			lms.setSortorder(item.getSortOrder());
			lms.setDescription(item.getDescription());

			TestDataCreatorMoodle.role_lms.add(lms);
		}
	}

	private void generateWikiLMS()
	{
		for (final WikiMining item : this.wikiList)
		{
			final WikiLMS lms = new WikiLMS();
			lms.setId(item.getId());
			lms.setName(item.getTitle());
			lms.setTimemodified(item.getTimeModified());
			lms.setSummary(item.getSummary());
			lms.setCourse(this.couWikMap.get(item.getId()).getId());

			TestDataCreatorMoodle.wiki_lms.add(lms);
		}
	}

	private void generateScormLMS()
	{
		for (final ScormMining item : this.scormList)
		{
			final ScormLMS lms = new ScormLMS();
			lms.setId(item.getId());
			lms.setName(item.getTitle());
			lms.setTimemodified(item.getTimeModified());
			lms.setMaxgrade(item.getMaxGrade());
			lms.setTimemodified(item.getTimeModified());
			lms.setCourse(this.couScoMap.get(item.getId()).getId());

			TestDataCreatorMoodle.scorm_lms.add(lms);
		}
	}

	private void generateContextLMS()
	{

		final HashMap<Long, ContextLMS> depIdMap = new HashMap<Long, ContextLMS>();
		final HashMap<Long, ContextLMS> degIdMap = new HashMap<Long, ContextLMS>();
		final HashMap<Long, ContextLMS> couIdMap = new HashMap<Long, ContextLMS>();

		// Create entries (context, course_categories) for all departments
		for (final LevelMining item : this.departmentList)
		{
			final ContextLMS lms = new ContextLMS();
			final CourseCategoriesLMS lms2 = new CourseCategoriesLMS();

			lms2.setId(TestDataCreatorMoodle.course_categories_lms.size() + 1);
			lms2.setTitle(item.getTitle());
			lms2.setDepth(1);
			lms2.setPath("/" + item.getId());

			lms.setContextlevel(40);
			lms.setDepth(2);
			lms.setInstanceid(lms2.getId());
			lms.setId(TestDataCreatorMoodle.context_lms.size() + 1);
			lms.setPath("/1/" + lms.getId());

			TestDataCreatorMoodle.course_categories_lms.add(lms2);

			TestDataCreatorMoodle.context_lms.add(lms);
			depIdMap.put(item.getId(), lms);
		}

		// Create entries (context, course_categories) for all degrees
		for (final LevelMining item : this.degreeList)
		{
			final CourseCategoriesLMS lms2 = new CourseCategoriesLMS();

			final LevelMining dm = this.depDegMap.get(item.getId());

			lms2.setId(TestDataCreatorMoodle.course_categories_lms.size() + 1);
			lms2.setTitle(item.getTitle());
			lms2.setDepth(2);
			lms2.setPath("/" + depIdMap.get(dm.getId()).getId() + "/" + lms2.getId());

			final ContextLMS lms = new ContextLMS();
			lms.setContextlevel(40);
			lms.setDepth(3);
			lms.setInstanceid(lms2.getId());
			lms.setId(TestDataCreatorMoodle.context_lms.size() + 1);
			final ContextLMS cl = depIdMap.get(dm.getId());
			lms.setPath(cl.getPath() + "/" + lms.getId());

			degIdMap.put(item.getId(), lms);
			TestDataCreatorMoodle.context_lms.add(lms);

			TestDataCreatorMoodle.course_categories_lms.add(lms2);

		}
		// Create entries (context) for all courses
		for (final LevelCourseMining item : this.degreeCourseList)
		{
			final ContextLMS lms = new ContextLMS();

			lms.setId(TestDataCreatorMoodle.context_lms.size() + 1);
			lms.setDepth(4);
			lms.setContextlevel(50);
			lms.setInstanceid(item.getCourse().getId());
			final ContextLMS cl = degIdMap.get(item.getLevel().getId());
			lms.setPath(cl.getPath() + "/" + lms.getId());

			couIdMap.put(item.getId(), lms);

			TestDataCreatorMoodle.context_lms.add(lms);
		}
		// Create entries (role_assignments) for all users
		for (final CourseUserMining item : this.courseUserList)
		{
			final RoleAssignmentsLMS lms2 = new RoleAssignmentsLMS();

			lms2.setId(item.getId());
			lms2.setRoleid(item.getRole().getId());
			lms2.setUserid(item.getUser().getId());
			lms2.setTimeend(item.getEnrolend());
			lms2.setTimestart(item.getEnrolstart());
			lms2.setContextid(couIdMap.get(item.getCourse().getId()).getId());

			TestDataCreatorMoodle.role_assignments_lms.add(lms2);
		}

	}

	private void generateLogLMS()
	{
		final List<ILogMining> logs = new ArrayList<ILogMining>();
		logs.addAll(this.resourceLogList);
		logs.addAll(this.assignmentLogList);
		logs.addAll(this.forumLogList);
		logs.addAll(this.courseLogList);
		logs.addAll(this.quizLogList);
		logs.addAll(this.wikiLogList);
		logs.addAll(this.scormLogList);

		Collections.sort(logs);

		for (int i = 0; i < logs.size(); i++)
		{
			logs.get(i).setId(i + 1);
		}

		for (final ResourceLogMining item : this.resourceLogList)
		{
			final LogLMS lms = new LogLMS();

			lms.setId(item.getId());
			lms.setCourse(item.getCourse().getId());
			lms.setModule("resource");
			lms.setTime(item.getTimestamp());
			lms.setAction(item.getAction());
			lms.setInfo(item.getResource().getId() + "");
			lms.setUserid(item.getUser().getId());

			TestDataCreatorMoodle.log_lms.add(lms);
		}
		final HashMap<Long, ForumDiscussionsLMS> forDisSet = new HashMap<Long, ForumDiscussionsLMS>();
		for (final ForumLogMining item : this.forumLogList)
		{
			final LogLMS lms = new LogLMS();
			final ForumPostsLMS lms2 = new ForumPostsLMS();

			lms2.setId(TestDataCreatorMoodle.forum_posts_lms.size() + 1);
			lms2.setMessage(item.getMessage());
			lms2.setSubject(item.getSubject());
			lms2.setUserid(item.getUser().getId());
			lms2.setCreated(item.getTimestamp());
			lms2.setModified(item.getTimestamp());

			TestDataCreatorMoodle.forum_posts_lms.add(lms2);

			if ((item.getAction().equals("add discussion") || item.getAction().equals("view discussion")))
			{
				final ForumDiscussionsLMS lms3 = new ForumDiscussionsLMS();

				lms3.setId(item.getForum().getId());
				lms3.setForum(item.getForum().getId());

				forDisSet.put(lms3.getId(), lms3);

			}
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("forum");
			lms.setInfo(item.getForum().getId() + "");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());

			TestDataCreatorMoodle.log_lms.add(lms);
		}
		TestDataCreatorMoodle.forum_discussions_lms.addAll(forDisSet.values());
		for (final AssignmentLogMining item : this.assignmentLogList)
		{
			final LogLMS lms = new LogLMS();

			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("assignment");
			lms.setInfo(item.getAssignment().getId() + "");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());

			TestDataCreatorMoodle.log_lms.add(lms);

			if (item.getAction().equals("upload"))
			{
				final AssignmentSubmissionsLMS lms2 = new AssignmentSubmissionsLMS();

				lms2.setGrade(item.getGrade().longValue());
				lms2.setAssignment(item.getAssignment().getId());
				lms2.setUserid(item.getUser().getId() + "");
				lms2.setTimemodified(item.getTimestamp());
				lms2.setId(TestDataCreatorMoodle.assignment_submission_lms.size() + 1);

				TestDataCreatorMoodle.assignment_submission_lms.add(lms2);
			}
		}
		for (final QuizLogMining item : this.quizLogList)
		{
			final LogLMS lms = new LogLMS();
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("quiz");
			lms.setInfo(item.getQuiz().getId() + "");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());

			if (!item.getAction().equals("review"))
			{
				final QuizGradesLMS lms2 = new QuizGradesLMS();
				lms2.setGrade(item.getGrade());
				lms2.setTimemodified(item.getTimestamp());
				lms2.setUserid(item.getUser().getId() + "");
				lms2.setQuiz(item.getQuiz().getId());
				lms2.setId(TestDataCreatorMoodle.quiz_grades_lms.size() + 1);

				TestDataCreatorMoodle.quiz_grades_lms.add(lms2);
			}

			TestDataCreatorMoodle.log_lms.add(lms);
		}
		/*
		 * for(QuestionLogMining item : questionLogList)
		 * {
		 * Log_LMS lms = new Log_LMS();
		 * lms.setId(item.getId());
		 * lms.setAction(item.getAction());
		 * lms.setModule("forum");
		 * lms.setInfo(item.getQuestion().getId()+"");
		 * lms.setTime(item.getTimestamp());
		 * lms.setCourse(item.getCourse().getId());
		 * lms.setUserid(item.getUser().getId()+"");
		 * log_lms.add(lms);
		 * }
		 */
		for (final ScormLogMining item : this.scormLogList)
		{
			final LogLMS lms = new LogLMS();
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("scorm");
			lms.setInfo(item.getScorm().getId() + "");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());

			TestDataCreatorMoodle.log_lms.add(lms);
		}
		for (final WikiLogMining item : this.wikiLogList)
		{
			final LogLMS lms = new LogLMS();
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("wiki");
			lms.setInfo(item.getWiki().getId() + "");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());
			lms.setCmid(item.getWiki().getId());

			TestDataCreatorMoodle.log_lms.add(lms);
		}
		for (final CourseLogMining item : this.courseLogList)
		{
			final LogLMS lms = new LogLMS();
			lms.setId(item.getId());
			lms.setAction(item.getAction());
			lms.setModule("course");
			lms.setTime(item.getTimestamp());
			lms.setCourse(item.getCourse().getId());
			lms.setUserid(item.getUser().getId());

			TestDataCreatorMoodle.log_lms.add(lms);
		}
	}

	private void generateResourceLMS()
	{
		for (final ResourceMining item : this.resourceList)
		{
			final ResourceLMS lms = new ResourceLMS();
			lms.setId(item.getId());
			lms.setType(item.getType());
			lms.setName(item.getTitle());
			lms.setTimemodified(item.getTimeModified());
			if (this.couResMap.get(item.getId()) != null) {
				lms.setCourse(this.couResMap.get(item.getId()).getId());
			}

			TestDataCreatorMoodle.resource_lms.add(lms);
		}
	}

	private void generateForumLMS()
	{
		final HashMap<Long, Long> cFMap = new HashMap<Long, Long>();
		for (final Iterator<CourseForumMining> iter = this.courseForumList.iterator(); iter.hasNext();)
		{
			final CourseForumMining item = iter.next();
			cFMap.put(item.getForum().getId(), item.getCourse().getId());
		}
		for (final ForumMining item : this.forumList)
		{
			final ForumLMS lms = new ForumLMS();
			lms.setId(item.getId());
			lms.setTimemodified(item.getTimeModified());
			lms.setName(item.getTitle());
			lms.setIntro(item.getSummary());
			lms.setCourse(cFMap.get(lms.getId()));

			TestDataCreatorMoodle.forum_lms.add(lms);

		}

	}

	private void generateCourseLMS()
	{

		for (final CourseMining item : this.courseList)
		{
			final CourseLMS lms = new CourseLMS();
			lms.setId(item.getId());
			lms.setFullname(item.getTitle());
			lms.setShortname(item.getShortname());
			lms.setTimemodified(item.getTimeModified());
			lms.setTimecreated(item.getTimeCreated());
			lms.setEnrolstartdate(item.getEnrolStart());
			lms.setEnrolenddate(item.getEnrolEnd());
			lms.setStartdate(item.getStartDate());

			TestDataCreatorMoodle.course_lms.add(lms);
		}
	}

	private void generateChatLogLMS()
	{
		final HashMap<Long, Long> couId = new HashMap<Long, Long>();

		for (final ChatLogMining item : this.chatLogList)
		{
			final ChatLogLMS lms = new ChatLogLMS();
			lms.setMessage(item.getMessage());

			lms.setTimestamp(item.getTimestamp());
			lms.setChat(item.getChat().getId());
			lms.setUser(item.getUser().getId());
			lms.setId(item.getId());

			couId.put(item.getChat().getId(), item.getCourse().getId());

			TestDataCreatorMoodle.chat_log_lms.add(lms);
		}

		for (final ChatMining item : this.chatList)
		{
			final ChatLMS lms = new ChatLMS();
			lms.setId(item.getId());
			if (couId.get(item.getId()) != null) {
				lms.setCourse(couId.get(item.getId()));
			}
			lms.setTitle(item.getTitle());
			lms.setDescription(item.getDescription());
			lms.setChattime(item.getChatTime());

			TestDataCreatorMoodle.chat_lms.add(lms);
		}
	}

	private void generateAssignmentLMS()
	{
		for (final AssignmentMining item : this.assignmentList)
		{
			final AssignmentLMS lms = new AssignmentLMS();
			lms.setId(item.getId());
			lms.setName(item.getTitle());
			lms.setTimeavailable(item.getTimeOpen());
			lms.setTimedue(item.getTimeClose());
			lms.setTimemodified(item.getTimeModified());
			lms.setDescription("description");
			if (this.couAssMap.get(item.getId()) != null) {
				lms.setCourse(this.couAssMap.get(item.getId()).getId());
			}

			final GradeItemsLMS lms2 = new GradeItemsLMS();
			lms2.setIteminstance(item.getId());
			lms2.setItemmodule("assignment");
			lms2.setGrademax(item.getMaxGrade());
			lms2.setId(TestDataCreatorMoodle.grade_items_lms.size());

			TestDataCreatorMoodle.grade_items_lms.add(lms2);
			TestDataCreatorMoodle.assignment_lms.add(lms);
		}
	}

	@SuppressWarnings("unchecked")
	public void getDataFromDB()
	{
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = dbHandler.getMiningSession();
		session.clear();

		final Query assQuery = session.createQuery("from AssignmentMining x order by x.id asc");
		this.assignmentList = (ArrayList<AssignmentMining>) assQuery.list();

		final Query assLogQuery = session.createQuery("from AssignmentLogMining x order by x.id asc");
		this.assignmentLogList = (ArrayList<AssignmentLogMining>) assLogQuery.list();

		final Query chaQuery = session.createQuery("from ChatMining x order by x.id asc");
		this.chatList = (ArrayList<ChatMining>) chaQuery.list();

		final Query chaLogQuery = session.createQuery("from ChatLogMining x order by x.id asc");
		this.chatLogList = (ArrayList<ChatLogMining>) chaLogQuery.list();

		final Query couQuery = session.createQuery("from CourseMining x order by x.id asc");
		this.courseList = (ArrayList<CourseMining>) couQuery.list();

		final Query couLogQuery = session.createQuery("from CourseLogMining x order by x.id asc");
		this.courseLogList = (ArrayList<CourseLogMining>) couLogQuery.list();

		final Query couAssQuery = session.createQuery("from CourseAssignmentMining x order by x.id asc");
		this.courseAssignmentList = (ArrayList<CourseAssignmentMining>) couAssQuery.list();

		final Query couForumQuery = session.createQuery("from CourseForumMining x order by x.id asc");
		this.courseForumList = (ArrayList<CourseForumMining>) couForumQuery.list();

		final Query couGroupQuery = session.createQuery("from CourseGroupMining x order by x.id asc");
		this.courseGroupList = (ArrayList<CourseGroupMining>) couGroupQuery.list();

		final Query couQuizQuery = session.createQuery("from CourseQuizMining x order by x.id asc");
		this.courseQuizList = (ArrayList<CourseQuizMining>) couQuizQuery.list();

		final Query couResQuery = session.createQuery("from CourseResourceMining x order by x.id asc");
		this.courseResourceList = (ArrayList<CourseResourceMining>) couResQuery.list();

		final Query couScormQuery = session.createQuery("from CourseScormMining x order by x.id asc");
		this.courseScormList = (ArrayList<CourseScormMining>) couScormQuery.list();

		final Query couUserQuery = session.createQuery("from CourseUserMining x order by x.id asc");
		this.courseUserList = (ArrayList<CourseUserMining>) couUserQuery.list();

		final Query couWikiQuery = session.createQuery("from CourseWikiMining x order by x.id asc");
		this.courseWikiList = (ArrayList<CourseWikiMining>) couWikiQuery.list();

		final Query degQuery = session.createQuery("from DegreeMining x order by x.id asc");
		this.degreeList = (ArrayList<LevelMining>) degQuery.list();

		final Query degCouQuery = session.createQuery("from DegreeCourseMining x order by x.id asc");
		this.degreeCourseList = (ArrayList<LevelCourseMining>) degCouQuery.list();

		final Query depQuery = session.createQuery("from DepartmentMining x order by x.id asc");
		this.departmentList = (ArrayList<LevelMining>) depQuery.list();

		final Query depDegQuery = session.createQuery("from DepartmentDegreeMining x order by x.id asc");
		this.departmentDegreeList = (ArrayList<LevelAssociationMining>) depDegQuery.list();

		final Query forQuery = session.createQuery("from ForumMining x order by x.id asc");
		this.forumList = (ArrayList<ForumMining>) forQuery.list();

		final Query forLogQuery = session.createQuery("from ForumLogMining x order by x.id asc");
		this.forumLogList = (ArrayList<ForumLogMining>) forLogQuery.list();

		final Query groupQuery = session.createQuery("from GroupMining x order by x.id asc");
		this.groupList = (ArrayList<GroupMining>) groupQuery.list();

		final Query groupUserQuery = session.createQuery("from GroupUserMining x order by x.id asc");
		this.groupUserList = (ArrayList<GroupUserMining>) groupUserQuery.list();

		final Query queQuery = session.createQuery("from QuestionMining x order by x.id asc");
		this.questionList = (ArrayList<QuestionMining>) queQuery.list();

		final Query queLogQuery = session.createQuery("from QuestionLogMining x order by x.id asc");
		this.questionLogList = (ArrayList<QuestionLogMining>) queLogQuery.list();

		final Query quiLogQuery = session.createQuery("from QuizLogMining x order by x.id asc");
		this.quizLogList = (ArrayList<QuizLogMining>) quiLogQuery.list();

		final Query quiQuery = session.createQuery("from QuizMining x order by x.id asc");
		this.quizList = (ArrayList<QuizMining>) quiQuery.list();

		final Query quiQuestionQuery = session.createQuery("from QuizQuestionMining x order by x.id asc");
		this.quizQuestionList = (ArrayList<QuizQuestionMining>) quiQuestionQuery.list();

		final Query quiUserQuery = session.createQuery("from QuizUserMining x order by x.id asc");
		this.quizUserList = (ArrayList<QuizUserMining>) quiUserQuery.list();

		final Query resQuery = session.createQuery("from ResourceMining x order by x.id asc");
		this.resourceList = (ArrayList<ResourceMining>) resQuery.list();

		final Query resLogQuery = session.createQuery("from ResourceLogMining x order by x.id asc");
		this.resourceLogList = (ArrayList<ResourceLogMining>) resLogQuery.list();

		final Query roleQuery = session.createQuery("from RoleMining x order by x.id asc");
		this.roleList = (ArrayList<RoleMining>) roleQuery.list();

		final Query scormQuery = session.createQuery("from ScormMining x order by x.id asc");
		this.scormList = (ArrayList<ScormMining>) scormQuery.list();

		final Query scormLogQuery = session.createQuery("from ScormLogMining x order by x.id asc");
		this.scormLogList = (ArrayList<ScormLogMining>) scormLogQuery.list();

		final Query userQuery = session.createQuery("from UserMining x order by x.id asc");
		this.userList = (ArrayList<UserMining>) userQuery.list();

		final Query wikQuery = session.createQuery("from WikiMining x order by x.id asc");
		this.wikiList = (ArrayList<WikiMining>) wikQuery.list();

		final Query wikLogQuery = session.createQuery("from WikiLogMining x order by x.id asc");
		this.wikiLogList = (ArrayList<WikiLogMining>) wikLogQuery.list();

		this.couAssMap = new HashMap<Long, CourseMining>();
		this.couForMap = new HashMap<Long, CourseMining>();
		this.couGroMap = new HashMap<Long, CourseMining>();
		this.couQuiMap = new HashMap<Long, CourseMining>();
		this.couResMap = new HashMap<Long, CourseMining>();
		this.couScoMap = new HashMap<Long, CourseMining>();
		this.couUseMap = new HashMap<Long, CourseMining>();
		this.couWikMap = new HashMap<Long, CourseMining>();

		this.degCouMap = new HashMap<Long, LevelMining>();
		this.depDegMap = new HashMap<Long, LevelMining>();

		for (final CourseAssignmentMining ca : this.courseAssignmentList) {
			this.couAssMap.put(ca.getAssignment().getId(), ca.getCourse());
		}

		for (final CourseGroupMining ca : this.courseGroupList) {
			this.couGroMap.put(ca.getGroup().getId(), ca.getCourse());
		}

		for (final CourseQuizMining ca : this.courseQuizList) {
			this.couQuiMap.put(ca.getQuiz().getId(), ca.getCourse());
		}

		for (final CourseUserMining ca : this.courseUserList) {
			this.couUseMap.put(ca.getUser().getId(), ca.getCourse());
		}

		for (final CourseForumMining ca : this.courseForumList) {
			this.couForMap.put(ca.getForum().getId(), ca.getCourse());
		}

		for (final CourseResourceMining cr : this.courseResourceList) {
			this.couResMap.put(cr.getResource().getId(), cr.getCourse());
		}

		for (final CourseWikiMining cw : this.courseWikiList) {
			this.couWikMap.put(cw.getWiki().getId(), cw.getCourse());
		}

		for (final CourseScormMining cs : this.courseScormList) {
			this.couScoMap.put(cs.getScorm().getId(), cs.getCourse());
		}

		for (final LevelCourseMining dc : this.degreeCourseList) {
			this.degCouMap.put(dc.getCourse().getId(), dc.getLevel());
		}

		for (final LevelAssociationMining dd : this.departmentDegreeList) {
			this.depDegMap.put(dd.getLower().getId(), dd.getUpper());
		}

		session.clear();
		session.close();

	}

	private void generateGroupLMS()
	{
		for (final GroupMining item : this.groupList)
		{
			final GroupsLMS lms = new GroupsLMS();
			lms.setId(item.getId());
			lms.setCourseid(this.couGroMap.get(item.getId()).getId());
			lms.setTimecreated(item.getTimeCreated());
			lms.setTimemodified(item.getTimeModified());

			TestDataCreatorMoodle.group_lms.add(lms);

		}
	}

	private void generateGroupMembersLMS()
	{
		for (final GroupUserMining item : this.groupUserList)
		{
			final GroupsMembersLMS lms = new GroupsMembersLMS();

			lms.setId(item.getId());
			lms.setGroupid(item.getGroup().getId());
			lms.setUserid(item.getUser().getId());
			lms.setTimeadded(item.getTimestamp());

			TestDataCreatorMoodle.group_members_lms.add(lms);
		}
	}

	private void generateQuizLMS()
	{
		final HashMap<Long, Long> qQMap = new HashMap<Long, Long>();
		for (final Iterator<CourseQuizMining> iter = this.courseQuizList.iterator(); iter.hasNext();)
		{
			final CourseQuizMining item = iter.next();
			qQMap.put(item.getQuiz().getId(), item.getCourse().getId());
		}
		for (final QuizMining item : this.quizList)
		{
			final QuizLMS lms = new QuizLMS();
			final GradeItemsLMS lms2 = new GradeItemsLMS();

			lms.setId(item.getId());
			lms.setCourse(qQMap.get(lms.getId()));
			lms.setName(item.getTitle());
			lms.setTimeopen(item.getTimeOpen());
			lms.setTimeclose(item.getTimeClose());
			lms.setTimecreated(item.getTimeCreated());
			lms.setTimemodified(item.getTimeModified());

			lms2.setId(TestDataCreatorMoodle.grade_items_lms.size() + 1);
			lms2.setGrademax(item.getMaxGrade());
			lms2.setIteminstance(item.getId());
			lms2.setItemmodule("quiz");

			TestDataCreatorMoodle.grade_items_lms.add(lms2);
			TestDataCreatorMoodle.quiz_lms.add(lms);
		}
	}

	private void generateQuestionLMS()
	{
		for (final QuestionMining item : this.questionList)
		{
			final QuestionLMS lms = new QuestionLMS();

			lms.setId(item.getId());
			lms.setName(item.getTitle());
			lms.setQuestiontext(item.getText());
			lms.setQtype(item.getType());
			lms.setTimecreated(item.getTimeCreated());
			lms.setTimemodified(item.getTimeModified());

			TestDataCreatorMoodle.question_lms.add(lms);
		}
	}

	private void generateQuizQuestionInstancesLMS()
	{
		// HashMap<Long, Quiz_question_instances_LMS> tempMap = new HashMap<Long, Quiz_question_instances_LMS>();
		for (final QuizQuestionMining item : this.quizQuestionList)
		{
			final QuizQuestionInstancesLMS lms = new QuizQuestionInstancesLMS();

			lms.setId(item.getId());
			lms.setQuestion(item.getQuestion().getId());
			lms.setQuiz(item.getQuiz().getId());

			// tempMap.put(lms.getId(), lms);
			TestDataCreatorMoodle.quiz_question_instances_lms.add(lms);

		}
		// quiz_question_instances_lms.addAll(tempMap.values());
	}

	private void generateGradeGradesLMS()
	{
		for (final QuizUserMining item : this.quizUserList)
		{
			final GradeGradesLMS lms = new GradeGradesLMS();

			lms.setId(item.getId());
			lms.setRawgrade(item.getRawGrade());
			lms.setFinalgrade(item.getFinalGrade());
			lms.setUserid(item.getUser().getId());
			lms.setTimemodified(item.getTimeModified());
			lms.setItemid(TestDataCreatorMoodle.grade_items_lms.size() + 1);

			final GradeItemsLMS lms2 = new GradeItemsLMS();
			lms2.setCourseid(item.getCourse().getId());
			lms2.setIteminstance(item.getQuiz().getId());
			lms2.setId(lms.getItemid());

			TestDataCreatorMoodle.grade_items_lms.add(lms2);
			TestDataCreatorMoodle.grade_grades_lms.add(lms);
		}
	}

	private void generateQuestionStatesLMS()
	{
		for (final QuestionLogMining item : this.questionLogList)
		{
			final QuestionStatesLMS lms = new QuestionStatesLMS();

			lms.setId(item.getId());
			lms.setAnswer(item.getAnswers());
			lms.setQuestion(item.getQuestion().getId());
			lms.setPenalty(item.getPenalty());
			lms.setTimestamp(item.getTimestamp());

			if (item.getAction().equals("OPEN")) {
				lms.setEvent((short) 0);
			} else if (item.getAction().equals("NAVIGATE")) {
				lms.setEvent((short) 1);
			} else if (item.getAction().equals("SAVE")) {
				lms.setEvent((short) 2);
			} else if (item.getAction().equals("GRADE")) {
				lms.setEvent((short) 3);
			} else if (item.getAction().equals("DUPLICATE")) {
				lms.setEvent((short) 4);
			} else if (item.getAction().equals("VALIDATE")) {
				lms.setEvent((short) 5);
			} else if (item.getAction().equals("CLOSEANDGRADE")) {
				lms.setEvent((short) 6);
			} else if (item.getAction().equals("SUBMIT")) {
				lms.setEvent((short) 7);
			} else if (item.getAction().equals("CLOSE")) {
				lms.setEvent((short) 8);
			} else if (item.getAction().equals("MANUALGRADE")) {
				lms.setEvent((short) 9);
			}

			TestDataCreatorMoodle.question_states_lms.add(lms);
		}
	}

	public void writeSourceDB()
	{
		final List<Collection<?>> all = new ArrayList<Collection<?>>();

		this.generateUserLMS();
		this.generateAssignmentLMS();
		this.generateResourceLMS();
		this.generateForumLMS();
		this.generateCourseLMS();
		this.generateGroupLMS();
		this.generateRoleLMS();

		this.generateScormLMS();
		this.generateWikiLMS();
		this.generateGroupMembersLMS();
		this.generateQuizLMS();
		this.generateQuestionLMS();
		this.generateQuizQuestionInstancesLMS();
		this.generateGradeGradesLMS();
		this.generateQuestionStatesLMS();

		this.generateLogLMS();
		this.generateContextLMS();
		this.generateChatLogLMS();

		all.add(TestDataCreatorMoodle.user_lms);
		all.add(TestDataCreatorMoodle.quiz_lms);
		all.add(TestDataCreatorMoodle.quiz_question_instances_lms);
		all.add(TestDataCreatorMoodle.question_lms);
		all.add(TestDataCreatorMoodle.assignment_lms);
		all.add(TestDataCreatorMoodle.chat_lms);
		all.add(TestDataCreatorMoodle.group_members_lms);
		all.add(TestDataCreatorMoodle.group_lms);
		all.add(TestDataCreatorMoodle.resource_lms);
		all.add(TestDataCreatorMoodle.role_lms);

		all.add(TestDataCreatorMoodle.forum_lms);
		all.add(TestDataCreatorMoodle.course_lms);
		all.add(TestDataCreatorMoodle.scorm_lms);
		all.add(TestDataCreatorMoodle.wiki_lms);
		all.add(TestDataCreatorMoodle.chat_log_lms);
		all.add(TestDataCreatorMoodle.log_lms);
		all.add(TestDataCreatorMoodle.forum_posts_lms);
		all.add(TestDataCreatorMoodle.forum_discussions_lms);
		all.add(TestDataCreatorMoodle.context_lms);
		all.add(TestDataCreatorMoodle.course_categories_lms);
		all.add(TestDataCreatorMoodle.role_assignments_lms);
		all.add(TestDataCreatorMoodle.grade_items_lms);
		all.add(TestDataCreatorMoodle.grade_grades_lms);
		all.add(TestDataCreatorMoodle.question_states_lms);
		all.add(TestDataCreatorMoodle.quiz_grades_lms);
		all.add(TestDataCreatorMoodle.assignment_submission_lms);

		final IDBHandler dbHandler = new HibernateDBHandler();
		final Session session = dbHandler.getMiningSession();
		dbHandler.saveCollectionToDB(session, all);
		dbHandler.closeSession(session);
	}
}
