/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/ExtractAndMapMoodle.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3;

// import miningDBclass.Config_mining;

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
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.AssignLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.AssignmentLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.AssignmentSubmissionsLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.ChatLogLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.ChatLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.ContextLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.CourseCategoriesLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.CourseLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.CourseModulesLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.EnrolLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.ForumLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.ForumDiscussionsLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.ForumPostsLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.GradeGradesLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.GradeItemsLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.GroupsLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.GroupsMembersLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.LogLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.ModulesLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.QuestionLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.QuestionStatesLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.QuizLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.QuizAttemptsLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.QuizGradesLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.QuizQuestionInstancesLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.ResourceLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.RoleLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.RoleAssignmentsLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.ScormLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.UserEnrolmentsLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.UserLMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.WikiLMS;
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

/**
 * The main class of the extraction process.
 * Implementation of the abstract extract class for the LMS Moodle.
 */
public class ExtractAndMapMoodle extends ExtractAndMap {// Versionsnummer in Namen einf�gen

	// LMS tables instances lists
	/** The log_lms. */
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
	private List<AssignLMS> assignLms;
	private List<EnrolLMS> enrolLms;
	private List<UserEnrolmentsLMS> userEnrolmentsLms;
	private List<ModulesLMS> modulesLms;
	private List<CourseModulesLMS> courseModulesLms;
	private List<QuizAttemptsLMS> quizAttemptsLms;

	private final Logger logger = Logger.getLogger(this.getClass());

	private final IConnector connector;

	public ExtractAndMapMoodle(final IConnector connector) {
		super(connector);
		this.connector = connector;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void getLMStables(final DBConfigObject dbConfig, final long readingfromtimestamp) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConfig).openSession();
		session.clear();
		final Transaction tx = session.beginTransaction();

		// Just for testing. has to be set to Long.MaxValue if not longer needed.
		final long ceiling = Long.MAX_VALUE;

		// reading the LMS Database, create tables as lists of instances of the DB-table classes
		final Query assign = session.createQuery("from AssignLMS order by id asc");
		this.assignLms = assign.list();
		System.out.println("AssignLMS tables: " + this.assignLms.size());

		final Query enrol = session.createQuery("from EnrolLMS x order by x.id asc");
		this.enrolLms = enrol.list();
		System.out.println("EnrolLMS tables: " + this.enrolLms.size());

		final Query modules = session.createQuery("from ModulesLMS x order by x.id asc");
		this.modulesLms = modules.list();
		System.out.println("ModulesLMS tables: " + this.modulesLms.size());

		final Query userEnrol = session.createQuery("from UserEnrolmentsLMS x order by x.id asc");
		this.userEnrolmentsLms = userEnrol.list();
		System.out.println("UserEnrolmentsLMS tables: " + this.userEnrolmentsLms.size());

		final Query coursMod = session.createQuery("from CourseModulesLMS x order by x.id asc");
		this.courseModulesLms = coursMod.list();
		System.out.println("CourseModulesLMS tables: " + this.courseModulesLms.size());

		final Query log = session
				.createQuery("from LogLMS x where x.time>=:readingtimestamp and x.time<=:ceiling order by x.id asc");
		log.setParameter("readingtimestamp", readingfromtimestamp);
		log.setParameter("ceiling", ceiling);
		this.logLms = log.list();
		System.out.println("LogLMS tables: " + this.logLms.size());

		final Query resource = session
				.createQuery("from ResourceLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		resource.setParameter("readingtimestamp", readingfromtimestamp);
		resource.setParameter("ceiling", ceiling);
		this.resourceLms = resource.list();
		System.out.println("ResourceLMS tables: " + this.resourceLms.size());

		final Query quizAttempts = session
				.createQuery("from QuizAttemptsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		quizAttempts.setParameter("readingtimestamp", readingfromtimestamp);
		quizAttempts.setParameter("ceiling", ceiling);
		this.quizAttemptsLms = quizAttempts.list();
		System.out.println("QuizAttemptsLMS tables: " + this.quizAttemptsLms.size());

		final Query chat = session
				.createQuery("from ChatLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		chat.setParameter("readingtimestamp", readingfromtimestamp);
		chat.setParameter("ceiling", ceiling);
		this.chatLms = chat.list();
		System.out.println("ChatLMS tables: " + this.chatLms.size());

		final Query chatlog = session
				.createQuery("from ChatLogLMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:ceiling order by x.id asc");
		chatlog.setParameter("readingtimestamp", readingfromtimestamp);
		chatlog.setParameter("ceiling", ceiling);
		this.chatLogLms = chatlog.list();
		System.out.println("ChatLogLMS tables: " + this.chatLogLms.size());

		final Query courseCategories = session
				.createQuery("from CourseCategoriesLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		courseCategories.setParameter("readingtimestamp", readingfromtimestamp);
		courseCategories.setParameter("ceiling", ceiling);
		this.courseCategoriesLms = courseCategories.list();
		System.out.println("CourseCategoriesLMS tables: " + this.courseCategoriesLms.size());

		final Query course = session
				.createQuery("from CourseLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		course.setParameter("readingtimestamp", readingfromtimestamp);
		course.setParameter("ceiling", ceiling);
		this.courseLms = course.list();
		System.out.println("CourseLMS tables: " + this.courseLms.size());

		final Query forumPosts = session
				.createQuery("from ForumPostsLMS x where x.modified>=:readingtimestamp and x.modified<=:ceiling order by x.id asc");
		forumPosts.setParameter("readingtimestamp", readingfromtimestamp);
		forumPosts.setParameter("ceiling", ceiling);
		this.forumPostsLms = forumPosts.list();
		System.out.println("ForumPostsLMS tables: " + this.forumPostsLms.size());

		final Query forum = session
				.createQuery("from ForumLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		forum.setParameter("readingtimestamp", readingfromtimestamp);
		forum.setParameter("ceiling", ceiling);
		this.forumLms = forum.list();
		System.out.println("ForumLMS tables: " + this.forumLms.size());

		final Query group = session
				.createQuery("from GroupsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		group.setParameter("readingtimestamp", readingfromtimestamp);
		group.setParameter("ceiling", ceiling);
		this.groupLms = group.list();
		System.out.println("GroupsLMS tables: " + this.groupLms.size());

		final Query quiz = session
				.createQuery("from QuizLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		quiz.setParameter("readingtimestamp", readingfromtimestamp);
		quiz.setParameter("ceiling", ceiling);
		this.quizLms = quiz.list();
		System.out.println("QuizLMS tables: " + this.quizLms.size());

		final Query wiki = session
				.createQuery("from WikiLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		wiki.setParameter("readingtimestamp", readingfromtimestamp);
		wiki.setParameter("ceiling", ceiling);
		this.wikiLms = wiki.list();
		System.out.println("WikiLMS tables: " + this.wikiLms.size());

		final Query groupMembers = session
				.createQuery("from GroupsMembersLMS x where x.timeadded>=:readingtimestamp and x.timeadded<=:ceiling order by x.id asc");
		groupMembers.setParameter("readingtimestamp", readingfromtimestamp);
		groupMembers.setParameter("ceiling", ceiling);
		this.groupMembersLms = groupMembers.list();
		System.out.println("GroupsMembersLMS tables: " + this.groupMembersLms.size());

		final Query questionStates = session
				.createQuery("from QuestionStatesLMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:ceiling order by x.id asc");
		questionStates.setParameter("readingtimestamp", readingfromtimestamp);
		questionStates.setParameter("ceiling", ceiling);
		this.questionStatesLms = questionStates.list();
		System.out.println("QuestionStatesLMS tables: " + this.questionStatesLms.size());

		final Query quizQuestionInstances = session
				.createQuery("from QuizQuestionInstancesLMS x order by x.id asc");
		this.quizQuestionInstancesLms = quizQuestionInstances.list();
		System.out.println("QuizQuestionInstancesLMS tables: " + this.quizQuestionInstancesLms.size());

		final Query question = session
				.createQuery("from QuestionLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		question.setParameter("readingtimestamp", readingfromtimestamp);
		question.setParameter("ceiling", ceiling);
		this.questionLms = question.list();
		System.out.println("QuestionLMS tables: " + this.questionLms.size());

		final Query user = session
				.createQuery("from UserLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		user.setParameter("ceiling", ceiling);
		user.setParameter("readingtimestamp", readingfromtimestamp);
		this.userLms = user.list();
		System.out.println("UserLMS tables: " + this.userLms.size());

		final Query role = session.createQuery("from RoleLMS x order by x.id asc");
		this.roleLms = role.list();
		System.out.println("RoleLMS tables: " + this.roleLms.size());

		final Query context = session.createQuery("from ContextLMS x order by x.id asc");
		this.contextLms = context.list();
		System.out.println("ContextLMS tables: " + this.contextLms.size());

		final Query roleAssignments = session
				.createQuery("from RoleAssignmentsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		roleAssignments.setParameter("ceiling", ceiling);
		roleAssignments.setParameter("readingtimestamp", readingfromtimestamp);
		this.roleAssignmentsLms = roleAssignments.list();
		System.out.println("RoleAssignmentsLMS tables: " + this.roleAssignmentsLms.size());

		final Query assignments = session
				.createQuery("from AssignmentLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		assignments.setParameter("ceiling", ceiling);
		assignments.setParameter("readingtimestamp", readingfromtimestamp);
		this.assignmentLms = assignments.list();
		System.out.println("AssignmentLMS tables: " + this.assignmentLms.size());

		final Query assignmentSubmission = session
				.createQuery("from AssignmentSubmissionsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		assignmentSubmission.setParameter("ceiling", ceiling);
		assignmentSubmission.setParameter("readingtimestamp", readingfromtimestamp);
		this.assignmentSubmissionLms = assignmentSubmission.list();
		System.out.println("AssignmentSubmissionsLMS tables: " + this.assignmentSubmissionLms.size());

		final Query quizGrades = session
				.createQuery("from QuizGradesLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		quizGrades.setParameter("ceiling", ceiling);
		quizGrades.setParameter("readingtimestamp", readingfromtimestamp);
		this.quizGradesLms = quizGrades.list();
		System.out.println("QuizGradesLMS tables: " + this.quizGradesLms.size());

		final Query forumDiscussions = session
				.createQuery("from ForumDiscussionsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		forumDiscussions.setParameter("ceiling", ceiling);
		forumDiscussions.setParameter("readingtimestamp", readingfromtimestamp);
		this.forumDiscussionsLms = forumDiscussions.list();
		System.out.println("ForumDiscussionsLMS tables: " + this.forumDiscussionsLms.size());

		final Query scorm = session
				.createQuery("from ScormLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		scorm.setParameter("ceiling", ceiling);
		scorm.setParameter("readingtimestamp", readingfromtimestamp);
		this.scormLms = scorm.list();
		System.out.println("ScormLMS tables: " + this.scormLms.size());

		final Query gradeGrades = session
				.createQuery("from GradeGradesLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		gradeGrades.setParameter("ceiling", ceiling);
		gradeGrades.setParameter("readingtimestamp", readingfromtimestamp);
		this.gradeGradesLms = gradeGrades.list();
		System.out.println("GradeGradesLMS tables: " + this.gradeGradesLms.size());

		final Query gradeItems = session
				.createQuery("from GradeItemsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		gradeItems.setParameter("ceiling", ceiling);
		gradeItems.setParameter("readingtimestamp", readingfromtimestamp);
		this.gradeItemsLms = gradeItems.list();
		System.out.println("GradeItemsLMS tables: " + this.gradeItemsLms.size());

		// hibernate session finish and close
		tx.commit();
		session.close();

	}

	@Override
	@SuppressWarnings("unchecked")
	public void getLMStables(final DBConfigObject dbConf, final long readingfromtimestamp, final long readingtotimestamp) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConf).openSession();
		// Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19",
		// "datamining", "LabDat1#").openSession();
		session.clear();
		final Transaction tx = session.beginTransaction();

		// reading the LMS Database, create tables as lists of instances of the DB-table classes

		if (this.userLms == null) {

			final Query assign = session.createQuery("from AssignLMS x order by x.id asc");
			this.assignLms = assign.list();
			System.out.println("AssignLMS tables: " + this.assignLms.size());

			final Query enrol = session.createQuery("from EnrolLMS x order by x.id asc");
			this.enrolLms = enrol.list();
			System.out.println("EnrolLMS tables: " + this.enrolLms.size());

			final Query modules = session.createQuery("from ModulesLMS x order by x.id asc");
			this.modulesLms = modules.list();
			System.out.println("ModulesLMS tables: " + this.modulesLms.size());

			final Query userEnrol = session.createQuery("from UserEnrolmentsLMS x order by x.id asc");
			this.userEnrolmentsLms = userEnrol.list();
			System.out.println("UserEnrolmentsLMS tables: " + this.userEnrolmentsLms.size());

			final Query coursMod = session.createQuery("from CourseModulesLMS x order by x.id asc");
			this.courseModulesLms = coursMod.list();
			System.out.println("CourseModulesLMS tables: " + this.courseModulesLms.size());

			final Query resource = session.createQuery("from ResourceLMS x order by x.id asc");
			this.resourceLms = resource.list();
			System.out.println("ResourceLMS tables: " + this.resourceLms.size());

			final Query course = session.createQuery("from CourseLMS x order by x.id asc");
			this.courseLms = course.list();
			System.out.println("CourseLMS tables: " + this.courseLms.size());

			final Query chat = session.createQuery("from ChatLMS x order by x.id asc");
			this.chatLms = chat.list();
			System.out.println("ChatLMS tables: " + this.chatLms.size());

			final Query courseCategories = session.createQuery("from CourseCategoriesLMS x order by x.id asc");
			this.courseCategoriesLms = courseCategories.list();
			System.out.println("CourseCategoriesLMS tables: " + this.courseCategoriesLms.size());

			final Query forum = session.createQuery("from ForumLMS x order by x.id asc");
			this.forumLms = forum.list();
			System.out.println("ForumLMS tables: " + this.forumLms.size());

			final Query group = session.createQuery("from GroupsLMS x order by x.id asc");
			this.groupLms = group.list();
			System.out.println("GroupsLMS tables: " + this.groupLms.size());

			final Query quiz = session.createQuery("from QuizLMS x order by x.id asc");
			this.quizLms = quiz.list();
			System.out.println("QuizLMS tables: " + this.quizLms.size());

			final Query wiki = session.createQuery("from WikiLMS x order by x.id asc");
			this.wikiLms = wiki.list();
			System.out.println("WikiLMS tables: " + this.wikiLms.size());

			final Query quizQuestionInstances = session
					.createQuery("from QuizQuestionInstancesLMS x order by x.id asc");
			this.quizQuestionInstancesLms = quizQuestionInstances.list();
			System.out.println("QuizQuestionInstancesLMS tables: " + this.quizQuestionInstancesLms.size());

			final Query question = session.createQuery("from QuestionLMS x order by x.id asc");
			this.questionLms = question.list();
			System.out.println("QuestionLMS tables: " + this.questionLms.size());

			final Query user = session.createQuery("from UserLMS x order by x.id asc");
			this.userLms = user.list();
			System.out.println("UserLMS tables: " + this.userLms.size());

			final Query role = session.createQuery("from RoleLMS x order by x.id asc");
			this.roleLms = role.list();
			System.out.println("RoleLMS tables: " + this.roleLms.size());

			session.clear();

			final Query context = session.createQuery("from ContextLMS x order by x.id asc");
			this.contextLms = context.list();
			System.out.println("ContextLMS tables: " + this.contextLms.size());

			final Query assignments = session.createQuery("from AssignmentLMS x order by x.id asc");
			this.assignmentLms = assignments.list();
			System.out.println("AssignmentLMS tables: " + this.assignmentLms.size());

			final Query scorm = session.createQuery("from ScormLMS x order by x.id asc");
			this.scormLms = scorm.list();
			System.out.println("ScormLMS tables: " + this.scormLms.size());

			final Query gradeItems = session.createQuery("from GradeItemsLMS x order by x.id asc");
			this.gradeItemsLms = gradeItems.list();
			System.out.println("GradeItemsLMS tables: " + this.gradeItemsLms.size());
		}

		final Query quizAttempts = session
				.createQuery("from QuizAttemptsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtotimestamp order by x.id asc");
		quizAttempts.setParameter("readingtimestamp", readingfromtimestamp);
		quizAttempts.setParameter("readingtimestamp2", readingtotimestamp);
		this.quizAttemptsLms = quizAttempts.list();
		System.out.println("QuizAttemptsLMS tables: " + this.quizAttemptsLms.size());

		final Query log = session
				.createQuery("from LogLMS x where x.time>=:readingtimestamp and x.time<=:readingtimestamp2 order by x.id asc");
		log.setParameter("readingtimestamp", readingfromtimestamp);
		log.setParameter("readingtimestamp2", readingtotimestamp);
		this.logLms = log.list();
		System.out.println("LogLMS tables: " + this.logLms.size());

		final Query chatlog = session
				.createQuery("from ChatLogLMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:readingtimestamp2 order by x.id asc");
		chatlog.setParameter("readingtimestamp", readingfromtimestamp);
		chatlog.setParameter("readingtimestamp2", readingtotimestamp);
		this.chatLogLms = chatlog.list();
		System.out.println("ChatLogLMS tables: " + this.chatLogLms.size());

		final Query forumPosts = session
				.createQuery("from ForumPostsLMS x where x.created>=:readingtimestamp and x.created<=:readingtimestamp2 order by x.id asc");
		forumPosts.setParameter("readingtimestamp", readingfromtimestamp);
		forumPosts.setParameter("readingtimestamp2", readingtotimestamp);
		this.forumPostsLms = forumPosts.list();
		System.out.println("ForumPostsLMS tables: " + this.forumPostsLms.size());

		final Query forumPostsModified = session
				.createQuery("from ForumPostsLMS x where x.modified>=:readingtimestamp and x.modified<=:readingtimestamp2 order by x.id asc");
		forumPostsModified.setParameter("readingtimestamp", readingfromtimestamp);
		forumPostsModified.setParameter("readingtimestamp2", readingtotimestamp);
		this.forumPostsLms.addAll(forumPostsModified.list());
		System.out.println("ForumPostsLMS tables: " + this.forumPostsLms.size());

		session.clear();

		final Query groupMembers = session
				.createQuery("from GroupsMembersLMS x where x.timeadded>=:readingtimestamp and x.timeadded<=:readingtimestamp2 order by x.id asc");
		groupMembers.setParameter("readingtimestamp", readingfromtimestamp);
		groupMembers.setParameter("readingtimestamp2", readingtotimestamp);
		this.groupMembersLms = groupMembers.list();
		System.out.println("GroupsMembersLMS tables: " + this.groupMembersLms.size());

		final Query questionStates = session
				.createQuery("from QuestionStatesLMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:readingtimestamp2 order by x.id asc");
		questionStates.setParameter("readingtimestamp", readingfromtimestamp);
		questionStates.setParameter("readingtimestamp2", readingtotimestamp);
		this.questionStatesLms = questionStates.list();
		System.out.println("QuestionStatesLMS tables: " + this.questionStatesLms.size());

		final Query roleAssignments = session
				.createQuery("from RoleAssignmentsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
		roleAssignments.setParameter("readingtimestamp", readingfromtimestamp);
		roleAssignments.setParameter("readingtimestamp2", readingtotimestamp);
		this.roleAssignmentsLms = roleAssignments.list();
		System.out.println("RoleAssignmentsLMS tables: " + this.roleAssignmentsLms.size());

		final Query assignmentSubmission = session
				.createQuery("from AssignmentSubmissionsLMS x where x.timecreated>=:readingtimestamp and x.timecreated<=:readingtimestamp2 order by x.id asc");
		assignmentSubmission.setParameter("readingtimestamp", readingfromtimestamp);
		assignmentSubmission.setParameter("readingtimestamp2", readingtotimestamp);
		this.assignmentSubmissionLms = assignmentSubmission.list();
		System.out.println("AssignmentSubmissionsLMS tables: " + this.assignmentSubmissionLms.size());

		final Query quizGrades = session
				.createQuery("from QuizGradesLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
		quizGrades.setParameter("readingtimestamp", readingfromtimestamp);
		quizGrades.setParameter("readingtimestamp2", readingtotimestamp);
		this.quizGradesLms = quizGrades.list();
		System.out.println("QuizGradesLMS tables: " + this.quizGradesLms.size());

		final Query forumDiscussions = session
				.createQuery("from ForumDiscussionsLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
		forumDiscussions.setParameter("readingtimestamp", readingfromtimestamp);
		forumDiscussions.setParameter("readingtimestamp2", readingtotimestamp);
		this.forumDiscussionsLms = forumDiscussions.list();
		System.out.println("ForumDiscussionsLMS tables: " + this.forumDiscussionsLms.size());

		final Query gradeGrades = session
				.createQuery("from GradeGradesLMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
		gradeGrades.setParameter("readingtimestamp", readingfromtimestamp);
		gradeGrades.setParameter("readingtimestamp2", readingtotimestamp);
		this.gradeGradesLms = gradeGrades.list();
		System.out.println("GradeGradesLMS tables: " + this.gradeGradesLms.size());

		session.clear();

		// hibernate session finish and close
		tx.commit();
		session.close();

	}

	@Override
	public void clearLMStables() {
		this.logLms.clear();
		this.resourceLms.clear();
		this.courseLms.clear();
		this.forumLms.clear();
		this.wikiLms.clear();
		this.userLms.clear();
		this.quizLms.clear();
		this.gradeGradesLms.clear();
		this.groupLms.clear();
		this.groupMembersLms.clear();
		this.questionStatesLms.clear();
		this.forumPostsLms.clear();
		this.roleLms.clear();
		this.roleAssignmentsLms.clear();
		this.assignmentSubmissionLms.clear();
	}

	// methods for create and fill the mining-table instances

	@Override
	public Map<Long, CourseUserMining> generateCourseUserMining() {

		final HashMap<Long, CourseUserMining> courseUserMining = new HashMap<Long, CourseUserMining>();

		for (final ContextLMS loadedItem : this.contextLms)
		{
			if (loadedItem.getContextlevel() == 50) {
				for (final RoleAssignmentsLMS loadedItem2 : this.roleAssignmentsLms)
				{
					if (loadedItem2.getContextid() == loadedItem.getId()) {
						final CourseUserMining insert = new CourseUserMining();

						insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getId()));
						insert.setRole(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getRoleid()),
								this.roleMining, this.oldRoleMining);
						insert.setPlatform(this.connector.getPlatformId());
						insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getUserid()),
								this.userMining, this.oldUserMining);
						insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInstanceid()),
								this.courseMining, this.oldCourseMining);
						if ((insert.getUser() != null) && (insert.getCourse() != null) && (insert.getRole() != null)) {
							courseUserMining.put(insert.getId(), insert);
						}
					}
				}
			}
		}

		for (final CourseUserMining courseUser : courseUserMining.values())
		{
			long enrolid = 0;
			for (final EnrolLMS loadedItem : this.enrolLms)
			{
				if (Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourseid()) == courseUser.getCourse()
						.getId())
				{
					enrolid = loadedItem.getId();
					break;
				}
			}
			for (final UserEnrolmentsLMS loadedItem : this.userEnrolmentsLms)
			{
				if (loadedItem.getEnrolid() == enrolid)
				{
					courseUser.setEnrolstart(loadedItem.getTimestart());
					courseUser.setEnrolend(loadedItem.getTimeend());
				}
			}
		}

		return courseUserMining;
	}

	@Override
	public Map<Long, CourseForumMining> generateCourseForumMining() {

		final HashMap<Long, CourseForumMining> courseForumMining = new HashMap<Long, CourseForumMining>();

		for (final ForumLMS loadedItem : this.forumLms)
		{
			final CourseForumMining insert = new CourseForumMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setForum(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.forumMining,
					this.oldForumMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getForum() != null)) {
				courseForumMining.put(insert.getId(), insert);
			}
		}
		return courseForumMining;
	}

	@Override
	public Map<Long, CourseMining> generateCourseMining() {

		final HashMap<Long, CourseMining> courseMining = new HashMap<Long, CourseMining>();
		for (final CourseLMS loadedItem : this.courseLms)
		{
			final CourseMining insert = new CourseMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setStartDate(loadedItem.getStartdate());
			insert.setEnrolStart(loadedItem.getEnrolstartdate());
			insert.setEnrolEnd(loadedItem.getEnrolenddate());
			insert.setTimeCreated(loadedItem.getTimecreated());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setTitle(loadedItem.getFullname());
			insert.setShortname(loadedItem.getShortname());
			insert.setPlatform(this.connector.getPlatformId());

			courseMining.put(insert.getId(), insert);
		}
		return courseMining;
	}

	@Override
	public Map<Long, CourseGroupMining> generateCourseGroupMining() {

		final HashMap<Long, CourseGroupMining> courseGroupMining = new HashMap<Long, CourseGroupMining>();

		for (final GroupsLMS loadedItem : this.groupLms)
		{
			final CourseGroupMining insert = new CourseGroupMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setGroup(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.groupMining,
					this.oldGroupMining);
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourseid()),
					this.courseMining, this.oldCourseMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getGroup() != null)) {
				courseGroupMining.put(insert.getId(), insert);
			}
		}
		return courseGroupMining;
	}

	@Override
	public Map<Long, CourseQuizMining> generateCourseQuizMining() {

		final HashMap<Long, CourseQuizMining> courseQuizMining = new HashMap<Long, CourseQuizMining>();

		for (final QuizLMS loadedItem : this.quizLms)
		{
			final CourseQuizMining insert = new CourseQuizMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.quizMining,
					this.oldQuizMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getQuiz() != null)) {
				courseQuizMining.put(insert.getQuiz().getId(), insert);
			}
			if (insert.getQuiz() == null) {
				this.logger.info("In CourseQuizMining, quiz(quiz) not found: " + loadedItem.getId());
			}
		}
		return courseQuizMining;
	}

	@Override
	public Map<Long, CourseAssignmentMining> generateCourseAssignmentMining() {

		final HashMap<Long, CourseAssignmentMining> courseAssignmentMining = new HashMap<Long, CourseAssignmentMining>();

		for (final AssignmentLMS loadedItem : this.assignmentLms)
		{
			final CourseAssignmentMining insert = new CourseAssignmentMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setPlatform(this.connector.getPlatformId());
			if (insert.getCourse() == null) {
				this.logger.info("course not found for course-assignment: " + loadedItem.getId() + " and course: "
						+ loadedItem.getCourse());
			}
			insert.setAssignment(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()),
					this.assignmentMining, this.oldAssignmentMining);
			if ((insert.getCourse() != null) && (insert.getAssignment() != null)) {
				courseAssignmentMining.put(insert.getId(), insert);
			}
			if (insert.getAssignment() == null) {
				this.logger.info("In CourseAssignmentMining, assignment not found: " + loadedItem.getId());
			}
		}
		return courseAssignmentMining;
	}

	@Override
	public Map<Long, CourseScormMining> generateCourseScormMining() {

		final HashMap<Long, CourseScormMining> courseScormMining = new HashMap<Long, CourseScormMining>();
		for (final ScormLMS loadedItem : this.scormLms)
		{
			final CourseScormMining insert = new CourseScormMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setScorm(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.scormMining,
					this.oldScormMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getScorm() != null)) {
				courseScormMining.put(insert.getId(), insert);
			}
			if (insert.getScorm() == null) {
				this.logger.info("In CourseScormMining, scorm not found: " + loadedItem.getId());
			}
		}
		return courseScormMining;
	}

	@Override
	public Map<Long, CourseResourceMining> generateCourseResourceMining() {

		final HashMap<Long, CourseResourceMining> courseResourceMining = new HashMap<Long, CourseResourceMining>();

		for (final ResourceLMS loadedItem : this.resourceLms)
		{
			final CourseResourceMining insert = new CourseResourceMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setResource(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.resourceMining,
					this.oldResourceMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getResource() != null)) {
				courseResourceMining.put(insert.getId(), insert);
			}
		}
		return courseResourceMining;
	}

	@Override
	public Map<Long, CourseLogMining> generateCourseLogMining() {
		final HashMap<Long, CourseLogMining> courseLogMining = new HashMap<Long, CourseLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final LogLMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction().equals("login")) {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			if (loadedItem.getModule().equals("course"))
			{
				final CourseLogMining insert = new CourseLogMining();

				insert.setId(courseLogMining.size() + 1 + this.courseLogMax);
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setPlatform(this.connector.getPlatformId());
				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);

				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());
				if ((insert.getUser() != null) && (insert.getCourse() != null)) {
					courseLogMining.put(insert.getId(), insert);
				}

			}
		}

		for (final CourseLogMining r : courseLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}

		return courseLogMining;
	}

	@Override
	public Map<Long, CourseWikiMining> generateCourseWikiMining() {

		final HashMap<Long, CourseWikiMining> courseWikiMining = new HashMap<Long, CourseWikiMining>();

		for (final WikiLMS loadedItem : this.wikiLms)
		{
			final CourseWikiMining insert = new CourseWikiMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setWiki(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.wikiMining,
					this.oldWikiMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getWiki() != null)) {
				courseWikiMining.put(insert.getId(), insert);
			}
		}
		return courseWikiMining;
	}

	@Override
	public Map<Long, ForumLogMining> generateForumLogMining() {
		final HashMap<Long, ForumLogMining> forumLogMining = new HashMap<Long, ForumLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final LogLMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());
			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction().equals("login")) {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			if (loadedItem.getModule().equals("forum")) {

				final ForumLogMining insert = new ForumLogMining();

				insert.setId(forumLogMining.size() + 1 + this.forumLogMax);
				insert.setPlatform(this.connector.getPlatformId());
				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);

				if ((loadedItem.getAction().equals("view forum") || loadedItem.getAction().equals("subscribe"))
						&& loadedItem.getInfo().matches("[0-9]+")) {
					insert.setForum(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInfo()),
							this.forumMining, this.oldForumMining);
				}
				else {
					if ((loadedItem.getAction().equals("add discussion") || loadedItem.getAction().equals(
							"view discussion"))
							&& loadedItem.getInfo().matches("[0-9]+")) {
						for (final ForumDiscussionsLMS loadedItem2 : this.forumDiscussionsLms)
						{
							if (loadedItem2.getId() == Long.valueOf(loadedItem.getInfo()))
							{
								insert.setForum(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getForum()),
										this.forumMining, this.oldForumMining);
								break;
							}
						}
					}
				}

				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setAction(loadedItem.getAction());
				for (final ForumPostsLMS loadedItem2 : this.forumPostsLms)
				{
					if ((loadedItem2.getUserid() == loadedItem.getUserid())
							&& ((loadedItem2.getCreated() == loadedItem.getTime()) || (loadedItem2.getModified() == loadedItem
									.getTime()))) {
						insert.setMessage(loadedItem2.getMessage());
						insert.setSubject(loadedItem2.getSubject());
						break;
					}
				}
				insert.setTimestamp(loadedItem.getTime());
				if ((insert.getCourse() != null) && (insert.getForum() != null) && (insert.getUser() != null)) {
					forumLogMining.put(insert.getId(), insert);
				}
			}
		}

		for (final ForumLogMining r : forumLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}
		return forumLogMining;
	}

	@Override
	public Map<Long, ForumMining> generateForumMining() {

		final HashMap<Long, ForumMining> forumMining = new HashMap<Long, ForumMining>();

		for (final ForumLMS loadedItem : this.forumLms)
		{
			final ForumMining insert = new ForumMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setTitle(loadedItem.getName());
			insert.setSummary(loadedItem.getIntro());
			insert.setPlatform(this.connector.getPlatformId());
			forumMining.put(insert.getId(), insert);
		}

		for (final LogLMS loadedItem : this.logLms)
		{
			if ((forumMining.get(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCmid())) != null)
					&& ((forumMining.get(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCmid()))
							.getTimeCreated() == 0) || (forumMining.get(
							Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCmid())).getTimeCreated() > loadedItem
							.getTime())))
			{
				forumMining.get(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCmid())).setTimeCreated(
						loadedItem.getTime());
			}
		}
		return forumMining;
	}

	@Override
	public Map<Long, GroupUserMining> generateGroupUserMining() {

		final HashMap<Long, GroupUserMining> groupMembersMining = new HashMap<Long, GroupUserMining>();

		for (final GroupsMembersLMS loadedItem : this.groupMembersLms)
		{
			final GroupUserMining insert = new GroupUserMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setGroup(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getGroupid()), this.groupMining,
					this.oldGroupMining);

			insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
					this.oldUserMining);

			insert.setTimestamp(loadedItem.getTimeadded());
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getUser() != null) && (insert.getGroup() != null)) {
				groupMembersMining.put(insert.getId(), insert);
			}
		}
		return groupMembersMining;
	}

	@Override
	public Map<Long, GroupMining> generateGroupMining() {

		final HashMap<Long, GroupMining> groupMining = new HashMap<Long, GroupMining>();

		for (final GroupsLMS loadedItem : this.groupLms)
		{
			final GroupMining insert = new GroupMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTimeCreated(loadedItem.getTimecreated());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setPlatform(this.connector.getPlatformId());
			groupMining.put(insert.getId(), insert);
		}
		return groupMining;
	}

	@Override
	public Map<Long, QuestionLogMining> generateQuestionLogMining()
	{

		final HashMap<Long, QuestionLogMining> questionLogMiningtmp = new HashMap<Long, QuestionLogMining>();
		final HashMap<Long, QuestionLogMining> questionLogMining = new HashMap<Long, QuestionLogMining>();
		final HashMap<String, Long> timestampIdMap = new HashMap<String, Long>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final QuestionStatesLMS loadedItem : this.questionStatesLms) {

			final QuestionLogMining insert = new QuestionLogMining();

			insert.setId(questionLogMiningtmp.size() + 1 + this.questionLogMax); // ID
			insert.setQuestion(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getQuestion()),
					this.questionMining, this.oldQuestionMining); // Question
			insert.setPenalty(loadedItem.getPenalty());
			insert.setAnswers(loadedItem.getAnswer());
			insert.setTimestamp(loadedItem.getTimestamp());
			insert.setPlatform(this.connector.getPlatformId());

			// Set Grades
			if ((loadedItem.getEvent() == 3) || (loadedItem.getEvent() == 6) || (loadedItem.getEvent() == 9)) {
				insert.setRawGrade(loadedItem.getRawGrade());
				insert.setFinalGrade(loadedItem.getGrade());
			}

			switch (loadedItem.getEvent())
			{
				case 0:
					insert.setAction("OPEN");
					break;
				case 1:
					insert.setAction("NAVIGATE");
					break;
				case 2:
					insert.setAction("SAVE");
					break;
				case 3:
					insert.setAction("GRADE");
					break;
				case 4:
					insert.setAction("DUPLICATE");
					break;
				case 5:
					insert.setAction("VALIDATE");
					break;
				case 6:
					insert.setAction("CLOSEANDGRADE");
					break;
				case 7:
					insert.setAction("SUBMIT");
					break;
				case 8:
					insert.setAction("CLOSE");
					break;
				case 9:
					insert.setAction("MANUALGRADE");
					break;
				default:
					insert.setAction("UNKNOWN");
			}

			// Set quiz type
			if ((insert.getQuestion() != null) && (this.quizQuestionMining.get(insert.getQuestion().getId()) != null))
			{
				insert.setQuiz(this.quizQuestionMining.get(insert.getQuestion().getId()).getQuiz());
				if (this.courseQuizMining.get(insert.getQuiz().getId()) != null) {
					insert.setCourse(this.courseQuizMining.get(insert.getQuiz().getId()).getCourse());
				}
			}
			else if ((insert.getQuestion() != null)
					&& (this.oldQuizQuestionMining.get(insert.getQuestion().getId()) != null)
					&& (this.oldCourseQuizMining.get(insert.getQuiz().getId()) != null))
			{
				insert.setQuiz(this.oldQuizQuestionMining.get(insert.getQuestion().getId()).getQuiz());
				if (this.oldCourseQuizMining.get(insert.getQuiz().getId()) != null) {
					insert.setCourse(this.courseQuizMining.get(insert.getQuiz().getId()).getCourse());
				}
			}

			// Set Type
			for (final QuestionLMS loadedItem2 : this.questionLms)
			{
				if (loadedItem2.getId() == (loadedItem.getQuestion())) {
					insert.setType(loadedItem2.getQtype());// Type
					break;
				}
			}
			if ((insert.getType() == null) && (this.oldQuestionMining.get(loadedItem.getQuestion()) != null)) {
				insert.setType(this.oldQuestionMining.get(loadedItem.getQuestion()).getType());
			}
			if (insert.getType() == null) {
				this.logger.info("In QuestionLogMining, type not found for questionStates: " + loadedItem.getId()
						+ " and question: " + loadedItem.getQuestion() + " question list size: "
						+ this.questionLms.size());
			}

			if ((insert.getQuestion() != null) && (insert.getQuiz() != null) && (insert.getCourse() != null))
			{

				timestampIdMap.put(insert.getTimestamp() + " " + insert.getQuiz().getId(), insert.getId());
				questionLogMiningtmp.put(insert.getId(), insert);
			}
		}

		// Set Course and
		for (final LogLMS loadedItem : this.logLms)
		{
			final long uid1 = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid1) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid1, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid1);
				if (loadedItem.getAction().equals("login")) {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid1, times);
			}

			if (loadedItem.getModule().equals("quiz")
					&& (timestampIdMap.get(loadedItem.getTime() + " " + this.connector.getPrefix() + ""
							+ loadedItem.getInfo()) != null)) {
				{
					final QuestionLogMining qlm = questionLogMiningtmp.get(timestampIdMap.get(loadedItem.getTime()
							+ " " + this.connector.getPrefix() + "" + loadedItem.getInfo()));
					qlm.setUser(uid1, this.userMining, this.oldUserMining);
					questionLogMining.put(qlm.getId(), qlm);
				}
			}
		}

		for (final QuestionLogMining r : questionLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}

		return questionLogMining;
	}

	@Override
	public Map<Long, QuizLogMining> generateQuizLogMining() {
		final HashMap<Long, QuizLogMining> quizLogMining = new HashMap<Long, QuizLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final LogLMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			// insert quiz in quiz_log
			if (loadedItem.getModule().equals("quiz"))
			{
				final QuizLogMining insert = new QuizLogMining();

				insert.setId(quizLogMining.size() + 1 + this.quizLogMax);
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setPlatform(this.connector.getPlatformId());

				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);

				if (loadedItem.getInfo().matches("[0-9]+"))
				{
					insert.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInfo()),
							this.quizMining, this.oldQuizMining);
				}
				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());
				if ((insert.getQuiz() != null) && (insert.getUser() != null) 
						&& (!loadedItem.getAction().equals("review")))
				{
					for (final QuizAttemptsLMS loadedItem2 : this.quizAttemptsLms)
					{
						final long id = Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getUserid());

						if ((Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getQuiz()) == insert.getQuiz()
								.getId())
								&& (id == insert.getUser().getId())
								&& (loadedItem2.getTimemodified() == insert.getTimestamp()))
						{
							insert.setGrade(loadedItem2.getSumgrades());
						}
					}
				}
				if ((insert.getQuiz() == null) && !(loadedItem.getAction().equals("view all"))) {
					this.logger.info("In QuizLogMining, quiz(quiz) not found for log: " + loadedItem.getId()
							+ " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo()
							+ " and action: " + loadedItem.getAction());
				}
				if (insert.getUser() == null) {
					this.logger.info("In QuizLogMining(quiz), user not found for log: " + loadedItem.getId()
							+ " and user: " + loadedItem.getUserid());
				}
				if (insert.getCourse() == null) {
					this.logger.info("In QuizLogMining(quiz), course not found for log: " + loadedItem.getId()
							+ " and course: " + loadedItem.getCourse());
				}
				if ((insert.getCourse() != null) && (insert.getQuiz() != null) && (insert.getUser() != null)) {
					quizLogMining.put(insert.getId(), insert);
				}

			}
		}

		for (final QuizLogMining r : quizLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}
		return quizLogMining;
	}

	@Override
	public Map<Long, AssignmentLogMining> generateAssignmentLogMining() {

		final HashMap<Long, AssignmentLogMining> assignmentLogMining = new HashMap<Long, AssignmentLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
		final HashMap<Long, ArrayList<AssignmentSubmissionsLMS>> asSub = new HashMap<Long, ArrayList<AssignmentSubmissionsLMS>>();

		for (final AssignmentSubmissionsLMS as : this.assignmentSubmissionLms)
		{
			if (asSub.get(as.getAssignment()) == null)
			{
				final ArrayList<AssignmentSubmissionsLMS> a = new ArrayList<AssignmentSubmissionsLMS>();
				a.add(as);
				asSub.put(as.getAssignment(), a);
			} else {
				asSub.get(as.getAssignment()).add(as);
			}

		}

		for (final LogLMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			// insert assignments in assignment_log
			if (loadedItem.getModule().equals("assignment") && loadedItem.getInfo().matches("[0-9]++"))
			{
				final AssignmentLogMining insert = new AssignmentLogMining();
				insert.setId(assignmentLogMining.size() + 1 + this.assignmentLogMax);
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);
				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());
				insert.setAssignment(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInfo()),
						this.assignmentMining, this.oldAssignmentMining);
				insert.setPlatform(this.connector.getPlatformId());

				if ((insert.getAssignment() != null) && (insert.getUser() != null) && (insert.getCourse() != null))// &&
																													// insert.getAction().equals("upload"))
				{
					if (asSub.get(Long.valueOf(loadedItem.getInfo())) != null) {
						for (final AssignmentSubmissionsLMS loadedItem2 : asSub
								.get(Long.valueOf(loadedItem.getInfo())))
						{
							if ((loadedItem2.getAssignment() == Long.valueOf(loadedItem.getInfo()))
									&& loadedItem2.getUserid().equals(loadedItem.getUserid()))// &&
																								// loadedItem2.getTimemodified()
																								// ==
																								// loadedItem.getTime())
							{
								insert.setGrade((double) loadedItem2.getGrade());
								break;
							}
						}
					}
				}

				if (insert.getAssignment() == null) {
					this.logger.info("In AssignmentLogMining, assignment not found for log: " + loadedItem.getId()
							+ " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo());
				}
				if (insert.getCourse() == null) {
					this.logger.info("In AssignmentLogMining, course not found for log: " + loadedItem.getId()
							+ " and course: " + loadedItem.getCourse());
				}
				if (insert.getUser() == null) {
					this.logger.info("In AssignmentLogMining, user not found for log: " + loadedItem.getId()
							+ " and user: " + loadedItem.getUserid());
				}
				insert.setPlatform(this.connector.getPlatformId());

				if ((insert.getUser() != null) && (insert.getAssignment() != null) && (insert.getCourse() != null)) {
					assignmentLogMining.put(insert.getId(), insert);
				}
			}
		}
		for (final AssignmentLogMining r : assignmentLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}

		return assignmentLogMining;
	}

	@Override
	public Map<Long, ScormLogMining> generateScormLogMining() {
		final HashMap<Long, ScormLogMining> scormLogMining = new HashMap<Long, ScormLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final LogLMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			// insert scorm in scorm_log
			if (loadedItem.getModule().equals("scorm")) {
				final ScormLogMining insert = new ScormLogMining();

				insert.setId(scormLogMining.size() + 1 + this.scormLogMax);

				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setPlatform(this.connector.getPlatformId());

				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);

				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());
				if (loadedItem.getInfo().matches("[0-9]+")) {
					insert.setScorm(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInfo()),
							this.scormMining, this.oldScormMining);
				}
				if ((insert.getScorm() != null) && (insert.getCourse() != null) && (insert.getUser() != null)) {
					scormLogMining.put(insert.getId(), insert);
				}
				if (insert.getScorm() == null) {
					this.logger.info("In ScormLogMining, scorm package not found for log: " + loadedItem.getId()
							+ " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo());
				}
				if (insert.getCourse() == null) {
					this.logger.info("In ScormLogMining(scorm part), course not found for log: " + loadedItem.getId()
							+ " and course: " + loadedItem.getCourse());
				}
				if (insert.getUser() == null) {
					this.logger.info("In ScormLogMining(scorm part), user not found for log: " + loadedItem.getId()
							+ " and user: " + loadedItem.getUserid());
				}
			}
		}
		for (final ScormLogMining r : scormLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}

		return scormLogMining;
	}

	@Override
	public Map<Long, QuizMining> generateQuizMining() {

		final HashMap<Long, QuizMining> quizMining = new HashMap<Long, QuizMining>();

		for (final QuizLMS loadedItem : this.quizLms)
		{

			final QuizMining insert = new QuizMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setTimeOpen(loadedItem.getTimeopen());
			insert.setTimeClose(loadedItem.getTimeclose());
			insert.setTimeCreated(loadedItem.getTimecreated());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setQtype("quiz");
			insert.setPlatform(this.connector.getPlatformId());
			for (final GradeItemsLMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getIteminstance() != null) && (loadedItem2.getItemmodule() != null)) {
					this.logger.info("Iteminstance" + loadedItem2.getIteminstance() + " QuizId" + loadedItem.getId());
					if ((loadedItem.getId() == loadedItem2.getIteminstance().longValue())
							&& loadedItem2.getItemmodule().equals("quiz")) {
						insert.setMaxGrade(loadedItem2.getGrademax());
						break;
					}
				}
				else {
					this.logger.info("Iteminstance or Itemmodule not found for QuizId" + loadedItem.getId()
							+ " and type quiz and Iteminstance " + loadedItem2.getIteminstance() + " Itemmodule:"
							+ loadedItem2.getItemmodule());
				}
			}
			quizMining.put(insert.getId(), insert);
		}
		return quizMining;
	}

	@Override
	public Map<Long, AssignmentMining> generateAssignmentMining() {

		final HashMap<Long, AssignmentMining> assignmentMining = new HashMap<Long, AssignmentMining>();

		// Getting assignmentMining from Moodle's 'assignment'-table should be abandoned as quickly as possible due to
		// overlapping
		// primary-identifiers (assignment|assign)

		for (final AssignmentLMS loadedItem : this.assignmentLms)
		{
			final AssignmentMining insert = new AssignmentMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setTimeOpen(loadedItem.getTimeavailable());
			insert.setTimeClose(loadedItem.getTimedue());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setPlatform(this.connector.getPlatformId());
			for (final GradeItemsLMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getIteminstance() != null) && (loadedItem2.getItemmodule() != null))
				{
					this.logger.info("Iteminstance " + loadedItem2.getIteminstance() + " AssignmentId"
							+ loadedItem.getId());
					if ((loadedItem.getId() == loadedItem2.getIteminstance().longValue())
							&& loadedItem2.getItemmodule().equals("assignment")) {
						insert.setMaxGrade(loadedItem2.getGrademax());
						break;
					}
				}
				else {
					this.logger.info("Iteminstance or Itemmodule not found for AssignmentId" + loadedItem.getId()
							+ " and type quiz and Iteminstance " + loadedItem2.getIteminstance() + " Itemmodule:"
							+ loadedItem2.getItemmodule());
				}
			}
			assignmentMining.put(insert.getId(), insert);
		}

		final HashMap<Long, AssignmentMining> amTmp = new HashMap<Long, AssignmentMining>();
		long moduleid = 0;
		for (final ModulesLMS loadedItem : this.modulesLms)
		{
			if (loadedItem.getName().equals("assign"))
			{
				moduleid = loadedItem.getId();
				break;
			}
		}

		for (final AssignLMS loadedItem : this.assignLms)
		{
			final AssignmentMining insert = new AssignmentMining();

			final long course = loadedItem.getCourse();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setPlatform(this.connector.getPlatformId());

			for (final GradeItemsLMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getIteminstance() != null) && (loadedItem2.getItemmodule() != null))
				{
					this.logger.info("Iteminstance " + loadedItem2.getIteminstance() + " AssignmentId"
							+ loadedItem.getId());
					if ((loadedItem.getId() == loadedItem2.getIteminstance().longValue())
							&& loadedItem2.getItemmodule().equals("assignment")) {
						insert.setMaxGrade(loadedItem2.getGrademax());
						break;
					}
				}
				else {
					this.logger.info("Iteminstance or Itemmodule not found for AssignmentId" + loadedItem.getId()
							+ " and type quiz and Iteminstance " + loadedItem2.getIteminstance() + " Itemmodule:"
							+ loadedItem2.getItemmodule());
				}
			}

			for (final CourseModulesLMS loadedItem3 : this.courseModulesLms)
			{
				if ((loadedItem3.getCourse() == course) && (loadedItem3.getModule() == moduleid))
				{
					insert.setTimeOpen(loadedItem3.getAvailablefrom());
					insert.setTimeClose(loadedItem3.getAvailableuntil());
					break;
				}
			}

			amTmp.put(insert.getId(), insert);
		}

		assignmentMining.putAll(amTmp);

		return assignmentMining;
	}

	@Override
	public Map<Long, ScormMining> generateScormMining() {

		final HashMap<Long, ScormMining> scormMining = new HashMap<Long, ScormMining>();

		for (final ScormLMS loadedItem : this.scormLms)
		{
			final ScormMining insert = new ScormMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setMaxGrade(loadedItem.getMaxgrade());
			insert.setPlatform(this.connector.getPlatformId());

			scormMining.put(insert.getId(), insert);
		}
		return scormMining;
	}

	@Override
	public Map<Long, QuizQuestionMining> generateQuizQuestionMining() {

		final HashMap<Long, QuizQuestionMining> quizQuestionMining = new HashMap<Long, QuizQuestionMining>();

		for (final QuizQuestionInstancesLMS loadedItem : this.quizQuestionInstancesLms)
		{
			final QuizQuestionMining insert = new QuizQuestionMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getQuiz()), this.quizMining,
					this.oldQuizMining);
			insert.setQuestion(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getQuestion()),
					this.questionMining, this.oldQuestionMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getQuiz() != null) && (insert.getQuestion() != null))
			{
				quizQuestionMining.put(insert.getQuestion().getId(), insert);
			}
			else
			{
				this.logger.info("In QuizQuestionMining, quiz not found: " + loadedItem.getQuiz());
			}
		}
		return quizQuestionMining;
	}

	@Override
	public Map<Long, QuestionMining> generateQuestionMining() {

		final HashMap<Long, QuestionMining> questionMining = new HashMap<Long, QuestionMining>();

		for (final QuestionLMS loadedItem : this.questionLms)
		{
			final QuestionMining insert = new QuestionMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setText(loadedItem.getQuestiontext());
			insert.setType(loadedItem.getQtype());
			insert.setTimeCreated(loadedItem.getTimecreated());
			insert.setPlatform(this.connector.getPlatformId());
			insert.setTimeModified(loadedItem.getTimemodified());

			questionMining.put(insert.getId(), insert);
		}
		return questionMining;
	}

	@Override
	public Map<Long, QuizUserMining> generateQuizUserMining() {

		final HashMap<Long, QuizUserMining> quizUserMining = new HashMap<Long, QuizUserMining>();
		for (final GradeGradesLMS loadedItem : this.gradeGradesLms)
		{
			final QuizUserMining insert = new QuizUserMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setPlatform(this.connector.getPlatformId());
			if (loadedItem.getFinalgrade() != null) {
				insert.setFinalGrade(loadedItem.getFinalgrade());
			}
			if (loadedItem.getRawgrade() != null) {
				insert.setRawGrade(loadedItem.getRawgrade());
			}
			if (loadedItem.getTimemodified() != null) {
				insert.setTimeModified(loadedItem.getTimemodified());
			}
			insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
					this.oldUserMining);

			for (final GradeItemsLMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getId() == loadedItem.getItemid()) && (loadedItem2.getIteminstance() != null)) {
					insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getCourseid()),
							this.courseMining, this.oldCourseMining);
					insert.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getIteminstance()),
							this.quizMining, this.oldQuizMining);
					if ((insert.getQuiz() != null) && (insert.getUser() != null)) {
						quizUserMining.put(insert.getId(), insert);
					}
					else {
						this.logger.info("In QuizUserMining, quiz not found for: Iteminstance: "
								+ loadedItem2.getIteminstance() + " Itemmodule: " + loadedItem2.getItemmodule()
								+ " course: " + loadedItem2.getCourseid() + " user: " + loadedItem.getUserid());
					}
				}
			}
		}
		return quizUserMining;
	}

	@Override
	public Map<Long, ResourceMining> generateResourceMining() {

		final HashMap<Long, ResourceMining> resource = new HashMap<Long, ResourceMining>();

		for (final ResourceLMS loadedItem : this.resourceLms)
		{
			final ResourceMining insert = new ResourceMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			// insert.setType(loadedItem.getType());
			insert.setTitle(loadedItem.getName());
			insert.setPlatform(this.connector.getPlatformId());

			// Get time of creation

			insert.setTimeModified(loadedItem.getTimemodified());

			resource.put(insert.getId(), insert);
		}

		for (final LogLMS loadedItem : this.logLms)
		{
			if ((resource.get(loadedItem.getCmid()) != null)
					&& ((resource.get(loadedItem.getCmid()).getTimeCreated() == 0) || (resource.get(
							loadedItem.getCmid()).getTimeCreated() > loadedItem.getTime())))
			{
				resource.get(loadedItem.getCmid()).setTimeCreated(loadedItem.getTime());
			}
		}
		return resource;
	}

	@Override
	public Map<Long, ResourceLogMining> generateResourceLogMining() {
		final HashMap<Long, ResourceLogMining> resourceLogMining = new HashMap<Long, ResourceLogMining>();
		// A HashMap of list of timestamps. Every key represents one user, the according value is a list of his/her
		// requests times.
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final LogLMS loadedItem : this.logLms)
		{

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			if (loadedItem.getModule().equals("resource")) {
				final ResourceLogMining insert = new ResourceLogMining();
				insert.setPlatform(this.connector.getPlatformId());

				insert.setId(resourceLogMining.size() + 1 + this.resourceLogMax);

				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setAction(loadedItem.getAction());

				if (loadedItem.getInfo().matches("[0-9]+")) {
					insert.setResource(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInfo()),
							this.resourceMining, this.oldResourceMining);
				}
				insert.setTimestamp(loadedItem.getTime());
				if ((insert.getResource() == null) && !(loadedItem.getAction().equals("view all"))) {
					this.logger.info("In ResourceLogMining, resource not found for log: " + loadedItem.getId()
							+ " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo()
							+ " and action: " + loadedItem.getAction());
				}
				if ((insert.getCourse() != null) && (insert.getResource() != null) && (insert.getUser() != null)) {
					resourceLogMining.put(insert.getId(), insert);
				}

			}
		}
		// For
		for (final ResourceLogMining r : resourceLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}
		return resourceLogMining;
	}

	@Override
	public Map<Long, UserMining> generateUserMining() {

		final HashMap<Long, UserMining> userMining = new HashMap<Long, UserMining>();

		for (final UserLMS loadedItem : this.userLms)
		{

			final UserMining insert = new UserMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));

			insert.setLastLogin(loadedItem.getLastlogin());
			insert.setFirstAccess(loadedItem.getFirstaccess());
			insert.setLastAccess(loadedItem.getLastaccess());
			insert.setCurrentLogin(loadedItem.getCurrentlogin());
			insert.setPlatform(this.connector.getPlatformId());
			insert.setLogin(Encoder.createMD5(loadedItem.getUsername()));

			userMining.put(insert.getId(), insert);
		}
		return userMining;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.lemo.dms.connectors.moodle_2_3.ExtractAndMap#generateWikiLogMining()
	 */
	@Override
	public Map<Long, WikiLogMining> generateWikiLogMining() {
		final HashMap<Long, WikiLogMining> wikiLogMining = new HashMap<Long, WikiLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
		final HashMap<Long, CourseModulesLMS> couMod = new HashMap<Long, CourseModulesLMS>();

		for (final CourseModulesLMS cm : this.courseModulesLms)
		{
			couMod.put(cm.getId(), cm);
		}

		for (final LogLMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			if (loadedItem.getModule().equals("wiki"))
			{
				final WikiLogMining insert = new WikiLogMining();

				insert.setId(wikiLogMining.size() + 1 + this.wikiLogMax);
				if (couMod.get(loadedItem.getCmid()) != null) {
					insert.setWiki(
							Long.valueOf(this.connector.getPrefix() + ""
									+ couMod.get(loadedItem.getCmid()).getInstance()), this.wikiMining,
							this.oldWikiMining);
				}

				insert.setPlatform(this.connector.getPlatformId());
				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());

				if ((insert.getUser() != null) && (insert.getCourse() != null) && (insert.getWiki() != null)) {
					wikiLogMining.put(insert.getId(), insert);
				}
			}
		}

		for (final WikiLogMining r : wikiLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}
		return wikiLogMining;
	}

	@Override
	public Map<Long, WikiMining> generateWikiMining() {

		final HashMap<Long, WikiMining> wikiMining = new HashMap<Long, WikiMining>();

		for (final WikiLMS loadedItem : this.wikiLms)
		{
			final WikiMining insert = new WikiMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setSummary(loadedItem.getSummary());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setPlatform(this.connector.getPlatformId());
			wikiMining.put(insert.getId(), insert);
		}
		for (final LogLMS loadedItem : this.logLms)
		{
			if (loadedItem.getModule().equals("Wiki")
					&& (wikiMining.get(loadedItem.getCmid()) != null)
					&& ((wikiMining.get(loadedItem.getCmid()).getTimeCreated() == 0) || (wikiMining.get(
							loadedItem.getCmid()).getTimeCreated() > loadedItem.getTime())))
			{
				wikiMining.get(loadedItem.getCmid()).setTimeCreated(loadedItem.getTime());
			}
		}
		return wikiMining;
	}

	@Override
	public Map<Long, RoleMining> generateRoleMining() {
		// generate role tables
		final HashMap<Long, RoleMining> roleMining = new HashMap<Long, RoleMining>();

		for (final RoleLMS loadedItem : this.roleLms)
		{
			final RoleMining insert = new RoleMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			insert.setShortname(loadedItem.getShortname());
			insert.setDescription(loadedItem.getDescription());
			insert.setSortOrder(loadedItem.getSortorder());
			insert.setPlatform(this.connector.getPlatformId());
			if(loadedItem.getArchetype().equals("manager") || loadedItem.getArchetype().equals("coursecreator")) {
				insert.setType(0);
			}
			else if(loadedItem.getArchetype().equals("teacher") || loadedItem.getArchetype().equals("editingteacher")) {
				insert.setType(1);
			}
			else {
				insert.setType(2);
			}
			roleMining.put(insert.getId(), insert);
		}
		return roleMining;
	}

	@Override
	public Map<Long, LevelMining> generateLevelMining() {
		final HashMap<Long, LevelMining> levelMining = new HashMap<Long, LevelMining>();

		for (final CourseCategoriesLMS loadedItem : this.courseCategoriesLms)
		{
			final LevelMining insert = new LevelMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setPlatform(this.connector.getPlatformId());
			insert.setDepth(loadedItem.getDepth());
			levelMining.put(insert.getId(), insert);

		}
		return levelMining;
	}

	@Override
	public Map<Long, LevelAssociationMining> generateLevelAssociationMining() {
		final HashMap<Long, LevelAssociationMining> levelAssociation = new HashMap<Long, LevelAssociationMining>();

		for (final CourseCategoriesLMS loadedItem : this.courseCategoriesLms)
		{
			final String[] s = loadedItem.getPath().split("/");
			if (s.length >= 3)
			{
				final LevelAssociationMining insert = new LevelAssociationMining();
				insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				insert.setLower(Long.valueOf(this.connector.getPrefix() + "" + s[s.length - 1]), this.levelMining,
						this.oldLevelMining);
				insert.setUpper(Long.valueOf(this.connector.getPrefix() + "" + s[s.length - 2]), this.levelMining,
						this.oldLevelMining);
				insert.setPlatform(this.connector.getPlatformId());

				if ((insert.getLower() != null) && (insert.getUpper() != null)) {
					levelAssociation.put(insert.getId(), insert);
				}
			}
		}
		return levelAssociation;
	}

	@Override
	public Map<Long, LevelCourseMining> generateLevelCourseMining() {
		final HashMap<Long, LevelCourseMining> levelCourse = new HashMap<Long, LevelCourseMining>();

		for (final ContextLMS loadedItem : this.contextLms)
		{
			if ((loadedItem.getDepth() >= 4) && (loadedItem.getContextlevel() == 50))
			{
				final LevelCourseMining insert = new LevelCourseMining();

				final String[] s = loadedItem.getPath().split("/");
				insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInstanceid()),
						this.courseMining, this.oldCourseMining);
				insert.setPlatform(this.connector.getPlatformId());
				for (final ContextLMS loadedItem2 : this.contextLms)
				{
					if ((loadedItem2.getContextlevel() == 40) && (loadedItem2.getId() == Integer.parseInt(s[3])))
					{
						insert.setLevel(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getInstanceid()),
								this.levelMining, this.oldLevelMining);
						break;
					}
				}
				if ((insert.getLevel() != null) && (insert.getCourse() != null)) {
					levelCourse.put(insert.getId(), insert);
				}
			}
		}
		return levelCourse;
	}

	@Override
	public Map<Long, ChatMining> generateChatMining() {
		final HashMap<Long, ChatMining> chatMining = new HashMap<Long, ChatMining>();

		for (final ChatLMS loadedItem : this.chatLms)
		{
			final ChatMining insert = new ChatMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setChatTime(loadedItem.getChattime());
			insert.setDescription(loadedItem.getDescription());
			insert.setTitle(loadedItem.getTitle());
			insert.setPlatform(this.connector.getPlatformId());
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);

			if (insert.getCourse() != null) {
				chatMining.put(insert.getId(), insert);
			}
		}

		return chatMining;
	}

	@Override
	public Map<Long, ChatLogMining> generateChatLogMining() {
		final HashMap<Long, ChatLogMining> chatLogMining = new HashMap<Long, ChatLogMining>();

		for (final ChatLogLMS loadedItem : this.chatLogLms)
		{
			final ChatLogMining insert = new ChatLogMining();
			insert.setId(chatLogMining.size() + 1 + this.chatLogMax);
			insert.setChat(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getChat()), this.chatMining,
					this.oldChatMining);
			insert.setMessage(loadedItem.getMessage());
			insert.setTimestamp(loadedItem.getTimestamp());
			insert.setPlatform(this.connector.getPlatformId());
			if (insert.getChat() != null) {
				insert.setCourse(insert.getChat().getCourse().getId(), this.courseMining, this.oldCourseMining);
			}
			insert.setDuration(0L);
			insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUser()), this.userMining,
					this.oldUserMining);

			if (insert.getUser() == null) {
				this.logger.info("In ChatLogMining(chat part), user not found for log: " + loadedItem.getId()
						+ " and user: " + loadedItem.getUser());
			}
			if (insert.getChat() == null) {
				this.logger.info("In ChatLogMining(chat part), chat not found for log: " + loadedItem.getId()
						+ " and chat: " + loadedItem.getChat());
			}
			if ((insert.getChat() != null) && (insert.getUser() != null) && (insert.getCourse() != null)) {
				chatLogMining.put(insert.getId(), insert);
			}

		}
		return chatLogMining;
	}

}
