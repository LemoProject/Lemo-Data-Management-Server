/**
 * File ./src/main/java/de/lemo/dms/test/ContentGenerator.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/test/ContentGenerator.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.AssignmentMining;
import de.lemo.dms.db.miningDBclass.ChatLogMining;
import de.lemo.dms.db.miningDBclass.ChatMining;
import de.lemo.dms.db.miningDBclass.ConfigMining;
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
import de.lemo.dms.db.miningDBclass.PlatformMining;
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

/**
 * Generates data for an test db system
 * @author Sebastian Schwarzrock
 *
 */
public class ContentGenerator {

	private ESystem system;
	private static final int MAGIC_THREE = 3;
	private static final int MAGIC_FIVE = 5;
	private static final int MAGIC_EIGHT = 8;
	private static final int MAGIC_TEN = 10;
	private Long maxLog = 0L;

	/**
	 * Enum for resource types
	 * @author Sebastian Schwarzrock
	 *
	 */
	enum EResourceType {
		File,
		Directory,
		Glossary,
		Summary,
		Page,
	}

	/**
	 * Enum for question types
	 * @author Sebastian Schwarzrock
	 *
	 */
	enum EQuestionType {
		Pathetic,
		Easy,
		Mediocre,
		Tricky,
		Sphinxesque
	}
	
	/**
	 * Enum for LM systems
	 * @author Sebastian Schwarzrock
	 *
	 */
	enum ESystem {
		fiz,
		moodle,
		clix
	}

	/**
	 * 
	 * @param topLevels
	 * @param levelPerTopLevel
	 * @param coursesPerLevel
	 * @param users
	 * @param startdate
	 * @param logsPerLearnObject
	 * @param characteristic	1=semi-authentic, 2=squares
	 * @return
	 */
	public List<Collection<?>> generateMiningDB(final Integer topLevels, final Integer levelPerTopLevel,
			final Integer coursesPerLevel, final Integer users, final Long startdate, final int logsPerLearnObject, int characteristic)
	{

		this.system = ESystem.moodle;

		final List<Collection<?>> all = new ArrayList<Collection<?>>();

		// Object-containers
		final ArrayList<LevelMining> levelList = new ArrayList<LevelMining>();
		final ArrayList<CourseMining> courseList = new ArrayList<CourseMining>();
		final ArrayList<ResourceMining> resourceList = new ArrayList<ResourceMining>();
		final ArrayList<WikiMining> wikiList = new ArrayList<WikiMining>();
		final ArrayList<AssignmentMining> assignmentList = new ArrayList<AssignmentMining>();
		final ArrayList<ForumMining> forumList = new ArrayList<ForumMining>();
		final ArrayList<ScormMining> scormList = new ArrayList<ScormMining>();
		final ArrayList<QuizMining> quizList = new ArrayList<QuizMining>();
		final ArrayList<QuestionMining> questionList = new ArrayList<QuestionMining>();
		final ArrayList<ChatMining> chatList = new ArrayList<ChatMining>();
		final ArrayList<UserMining> userList = new ArrayList<UserMining>();
		final ArrayList<RoleMining> roleList = new ArrayList<RoleMining>();
		final ArrayList<GroupMining> groupList = new ArrayList<GroupMining>();
		final ArrayList<PlatformMining> platformList = new ArrayList<PlatformMining>();

		// Association-containers
		final ArrayList<LevelCourseMining> levelCourseList = new ArrayList<LevelCourseMining>();
		final ArrayList<LevelAssociationMining> levelAssociationList = new ArrayList<LevelAssociationMining>();

		final ArrayList<CourseResourceMining> courseResourceList = new ArrayList<CourseResourceMining>();
		final ArrayList<CourseWikiMining> courseWikiList = new ArrayList<CourseWikiMining>();
		final ArrayList<CourseAssignmentMining> courseAssignmentList = new ArrayList<CourseAssignmentMining>();
		final ArrayList<CourseForumMining> courseForumList = new ArrayList<CourseForumMining>();
		final ArrayList<CourseScormMining> courseScormList = new ArrayList<CourseScormMining>();
		final ArrayList<CourseQuizMining> courseQuizList = new ArrayList<CourseQuizMining>();
		final ArrayList<QuizQuestionMining> quizQuestionList = new ArrayList<QuizQuestionMining>();
		final ArrayList<GroupUserMining> groupUserList = new ArrayList<GroupUserMining>();
		final ArrayList<CourseUserMining> courseUserList = new ArrayList<CourseUserMining>();
		final ArrayList<CourseGroupMining> courseGroupList = new ArrayList<CourseGroupMining>();
		final ArrayList<QuizUserMining> quizUserList = new ArrayList<QuizUserMining>();

		// Log-containers
		final ArrayList<CourseLogMining> courseLogList = new ArrayList<CourseLogMining>();
		final ArrayList<ILogMining> resourceLogList = new ArrayList<ILogMining>();
		final ArrayList<WikiLogMining> wikiLogList = new ArrayList<WikiLogMining>();
		final ArrayList<AssignmentLogMining> assignmentLogList = new ArrayList<AssignmentLogMining>();
		final ArrayList<ForumLogMining> forumLogList = new ArrayList<ForumLogMining>();
		final ArrayList<ScormLogMining> scormLogList = new ArrayList<ScormLogMining>();
		final ArrayList<QuizLogMining> quizLogList = new ArrayList<QuizLogMining>();
		final ArrayList<QuestionLogMining> questionLogList = new ArrayList<QuestionLogMining>();
		final ArrayList<ChatLogMining> chatLogList = new ArrayList<ChatLogMining>();

		final String[] forumAction = new String[4];
		forumAction[0] = "view forum";
		forumAction[1] = "subscribe";
		forumAction[2] = "add discussion";
		forumAction[MAGIC_THREE] = "view discussion";

		final String[] assignmentActionTeacher = new String[4];
		assignmentActionTeacher[0] = "add";
		assignmentActionTeacher[1] = "update";
		assignmentActionTeacher[2] = "update grades";
		assignmentActionTeacher[MAGIC_THREE] = "view submissions";

		final String[] assignmentActionStudent = new String[2];
		assignmentActionStudent[0] = "upload";
		assignmentActionStudent[1] = "view";

		final Random randy = new Random(15768000);

		int offset;
		final int year = 31536000;
		final int month = 2592000;
		final int week = 604800;
		final int day = 86400;

		final Date dt = new Date();
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		final PlatformMining platform = new PlatformMining();
		platform.setId(1L);
		platform.setName("ContentGenerator @ " + df.format(dt));
		platform.setPrefix(22L);
		platform.setType("ContentGenerator");

		platformList.add(platform);

		// Create users
		for (int i = 0; i < users; i++)
		{
			final UserMining user = new UserMining();
			user.setId(Long.valueOf(platform.getPrefix() + "" + (userList.size() + 1)));
			user.setFirstAccess(startdate + randy.nextInt(4 * week));
			user.setLastLogin(year - week - randy.nextInt(month));
			user.setLastAccess(user.getLastLogin() + randy.nextInt(day));
			user.setCurrentLogin(user.getLastAccess() + randy.nextInt(week));
			user.setGender((i % 2) + 1);
			user.setLogin(Encoder.createMD5(i + " " + df.format(dt)));
			user.setPlatform(platform.getId());

			userList.add(user);
		}

		// Create roles

		final RoleMining r0 = new RoleMining();
		r0.setId(Long.valueOf(platform.getPrefix() + "" + (roleList.size() + 1)));
		r0.setName("Administrator");
		r0.setShortname("Admin");
		r0.setDescription("Administatoren dürfen alles.");
		r0.setSortOrder(0);
		r0.setPlatform(platform.getId());
		r0.setType(0);
		
		roleList.add(r0);

		final RoleMining r1 = new RoleMining();
		r1.setId(Long.valueOf(platform.getPrefix() + "" + (roleList.size() + 1)));
		r1.setName("Dozent");
		r1.setShortname("Doz");
		r1.setDescription("Dozenten dürfen in ihren Kursen alles.");
		r1.setSortOrder(1);
		r1.setPlatform(platform.getId());
		r1.setType(1);
		
		roleList.add(r1);

		final RoleMining r2 = new RoleMining();
		r2.setId(Long.valueOf(platform.getPrefix() + "" + (roleList.size() + 1)));
		r2.setName("Student");
		r2.setShortname("Stud");
		r2.setDescription("Studenten dürfen alles lernen.");
		r2.setSortOrder(2);
		r2.setType(2);
		r2.setPlatform(platform.getId());

		roleList.add(r2);

		// Create groups
		for (int i = 0; i < (coursesPerLevel * levelPerTopLevel * topLevels); i++)
		{
			final GroupMining gr = new GroupMining();
			gr.setId(Long.valueOf(platform.getPrefix() + "" + (groupList.size() + 1)));

			if ((i % 2) == 0) {
				offset = 15768000;
			} else {
				offset = 0;
			}

			gr.setTimeCreated(startdate + offset + randy.nextInt(week));
			if(characteristic != 1) {
				gr.setTimeCreated(startdate);
			}
			gr.setTimeModified(gr.getTimeCreated() + randy.nextInt(month));
			gr.setPlatform(platform.getId());

			groupList.add(gr);

			for (int j = i * MAGIC_FIVE; j < ((i * MAGIC_FIVE) + MAGIC_TEN); j++)
			{
				final GroupUserMining gu = new GroupUserMining();
				gu.setId(Long.valueOf(platform.getPrefix() + "" + (groupUserList.size() + 1)));
				gu.setUser(userList.get(j % userList.size()));
				gu.setGroup(gr);
				gu.setPlatform(platform.getId());

				groupUserList.add(gu);
			}
		}

		for (int i = 1; i <= topLevels; i++)
		{
			// Create departments
			final LevelMining dep = new LevelMining();
			dep.setId(Long.valueOf(platform.getPrefix() + "" + (levelList.size() + 1)));
			dep.setPlatform(platform.getId());
			dep.setTitle("Department " + i);
			dep.setDepth(1);
			levelList.add(dep);
			for (int j = 1; j <= levelPerTopLevel; j++)
			{
				// Create degrees
				final LevelMining deg = new LevelMining();
				deg.setId(Long.valueOf(platform.getPrefix() + "" + (levelList.size() + 1)));
				deg.setPlatform(platform.getId());
				deg.setTitle("Degree " + i + "." + j);
				deg.setDepth(2);
				levelList.add(deg);

				final LevelAssociationMining depDeg = new LevelAssociationMining();
				depDeg.setLower(deg);
				depDeg.setUpper(dep);
				depDeg.setPlatform(platform.getId());
				depDeg.setId(Long.valueOf(platform.getPrefix() + "" + (levelAssociationList.size() + 1)));

				levelAssociationList.add(depDeg);

				for (int k = 1; k <= coursesPerLevel; k++)
				{
					// Create courses
					if ((k % 2) == 0) {
						offset = 15768000;
					} else {
						offset = 0;
					}

					final CourseMining cou = new CourseMining();
					cou.setTitle("Course " + i + "." + j + "." + k);

					final int ts = (int) (startdate + offset);

					cou.setTimeCreated(ts + randy.nextInt(week));
					if(characteristic != 1) {
						cou.setTimeCreated(startdate);
					}
					cou.setEnrolStart(ts + week + randy.nextInt(week));
					cou.setEnrolEnd(cou.getEnrolStart() + (MAGIC_THREE * week));
					cou.setTimeModified(ts + randy.nextInt(year / 2));
					cou.setStartDate(cou.getEnrolStart() + (2 * week));
					cou.setShortname(i + "" + j + "" + k);
					cou.setId(Long.valueOf(platform.getPrefix() + "" + (courseList.size() + 1)));

					final LevelCourseMining degCou = new LevelCourseMining();
					degCou.setLevel(deg);
					degCou.setCourse(cou);
					degCou.setId(Long.valueOf(platform.getPrefix() + "" + (levelCourseList.size() + 1)));
					cou.setPlatform(platform.getId());
					degCou.setPlatform(platform.getId());

					courseList.add(cou);
					levelCourseList.add(degCou);

					// Associate users with course
					final int userSwitch = 25 + (((k - 1) % MAGIC_THREE) * 25);
					for (int l = 0; l < userSwitch; l++)
					{
						final CourseUserMining cu = new CourseUserMining();
						cu.setId(Long.valueOf(platform.getPrefix() + "" + (courseUserList.size() + 1)));
						if (l == 0) {
							cu.setRole(roleList.get(0));
						} else if (l == 1) {
							cu.setRole(roleList.get(1));
						} else {
							cu.setRole(roleList.get(2));
						}

						cu.setCourse(cou);
						cu.setEnrolstart(cou.getEnrolStart());
						cu.setEnrolend(cou.getEnrolEnd());
						cu.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + l) % userList.size()));
						cu.setPlatform(platform.getId());

						courseUserList.add(cu);
					}

					final CourseGroupMining cg = new CourseGroupMining();
					cg.setId(Long.valueOf(platform.getPrefix() + "" + (courseGroupList.size() + 1)));
					cg.setCourse(cou);
					cg.setGroup(groupList.get(courseList.size() - 1));
					cg.setPlatform(platform.getId());

					courseGroupList.add(cg);

					// Now create LearningObjects
					// Create resources
					final int pt = (randy.nextInt(6) + 1) * 15;
					final int resSwitch = 15 + ((((k - 1) / MAGIC_THREE) % MAGIC_THREE) * 15);
					for (int l = 1; l < resSwitch; l++)
					{
						final ResourceMining r = new ResourceMining();
						r.setId(Long.valueOf(platform.getPrefix() + "" + (resourceList.size() + 1)));
						r.setDifficulty("Level " + randy.nextInt(MAGIC_TEN));
						r.setPosition(l);
						r.setTimeCreated(cou.getTimeCreated() + randy.nextInt(week));
						if(characteristic != 1) {
							r.setTimeCreated(startdate);
						}
						r.setTimeModified(r.getTimeCreated() + randy.nextInt(year / 2));
						r.setTitle("Resource " + i + "." + j + "." + k + "." + l);
						r.setPlatform(platform.getId());
						if (this.system == ESystem.fiz)
						{
							if (l == 1)
							{
								r.setType("VLU");
								r.setProcessingTime(pt);
								r.setUrl("http://www.chemgapedia.de/vsengine/vlu/vsc/" + r.getTitle() + ".vlu.html");
							}
							else if (l < MAGIC_TEN)
							{
								r.setType("Page");
								r.setProcessingTime(pt / MAGIC_EIGHT);
								r.setUrl("http://www.chemgapedia.de/vsengine/vlu/vsc/Resource " + i + "." + j + "." + k
										+ ".1.vlu/Page/vsc/" + r.getTitle() + ".vscml.html");
							}
							else
							{
								r.setType("Summary");
								r.setProcessingTime(pt);
								r.setUrl("http://www.chemgapedia.de/vsengine/vlu/vsc/Resource " + i + "." + j + "." + k
										+ ".1.vlu/Page/summary.html");
							}
						}
						else
						{
							r.setType(EResourceType.values()[randy.nextInt(EResourceType.values().length)].toString());
							r.setProcessingTime(pt);
							r.setUrl("http://www.lemo.com/" + r.getTitle() + ".html");
						}
						final CourseResourceMining couRes = new CourseResourceMining();
						couRes.setId(Long.valueOf(platform.getPrefix() + "" + (courseResourceList.size() + 1)));
						couRes.setCourse(cou);
						couRes.setResource(r);
						couRes.setPlatform(platform.getId());

						resourceList.add(r);
						courseResourceList.add(couRes);
					}
					// Create wikis
					for (int l = 1; l < MAGIC_THREE; l++)
					{
						final WikiMining w = new WikiMining();
						w.setId(Long.valueOf(platform.getPrefix() + "" + (wikiList.size() + 1)));
						w.setTimeCreated(cou.getTimeCreated() + randy.nextInt(week));
						if(characteristic != 1) {
							w.setTimeCreated(startdate);
						}
						w.setTimeModified(w.getTimeCreated() + randy.nextInt(year / 2));
						w.setTitle("Wiki " + i + "." + j + "." + k + "." + l);
						w.setSummary("This is wiki " + i + "." + j + "." + k + "." + l + "'s summary.");

						final CourseWikiMining couWik = new CourseWikiMining();
						couWik.setId(Long.valueOf(platform.getPrefix() + "" + (courseWikiList.size() + 1)));
						couWik.setCourse(cou);
						couWik.setWiki(w);

						couWik.setPlatform(platform.getId());
						w.setPlatform(platform.getId());

						wikiList.add(w);
						courseWikiList.add(couWik);
					}
					// Create Assignments
					for (int l = 1; l < MAGIC_THREE; l++)
					{
						final AssignmentMining a = new AssignmentMining();
						a.setId(Long.valueOf(platform.getPrefix() + "" + (assignmentList.size() + 1)));
						a.setTimeCreated(cou.getTimeCreated() + randy.nextInt(week));
						if(characteristic != 1) {
							a.setTimeCreated(startdate);
						}
						a.setTimeModified(a.getTimeCreated() + randy.nextInt(year / 2));
						a.setTitle("Assignment " + i + "." + j + "." + k + "." + l);
						a.setMaxGrade(Double.parseDouble("" + (randy.nextInt(19) + 1)) * MAGIC_FIVE);
						a.setTimeOpen(a.getTimeCreated() + randy.nextInt(month));
						a.setTimeClose(a.getTimeOpen() + (2 * week));
						a.setType("Assignment");
						a.setPlatform(platform.getId());

						final CourseAssignmentMining couAss = new CourseAssignmentMining();
						couAss.setId(Long.valueOf(platform.getPrefix() + "" + (courseAssignmentList.size() + 1)));
						couAss.setCourse(cou);
						couAss.setAssignment(a);
						couAss.setPlatform(platform.getId());

						assignmentList.add(a);
						courseAssignmentList.add(couAss);
					}
					// Create Forums
					for (int l = 1; l < MAGIC_THREE; l++)
					{
						final ForumMining f = new ForumMining();
						f.setId(Long.valueOf(platform.getPrefix() + "" + (forumList.size() + 1)));
						f.setTimeCreated(cou.getTimeCreated() + randy.nextInt(week));
						if(characteristic != 1) {
							f.setTimeCreated(startdate);
						}
						f.setTimeModified(f.getTimeCreated() + randy.nextInt(year / 2));
						f.setTitle("Forum " + i + "." + j + "." + k + "." + l);
						f.setSummary("Forum " + i + "." + j + "." + k + "." + l + "'s summary...");
						f.setPlatform(platform.getId());

						final CourseForumMining couFor = new CourseForumMining();
						couFor.setId(Long.valueOf(platform.getPrefix() + "" + (courseForumList.size() + 1)));
						couFor.setCourse(cou);
						couFor.setForum(f);
						couFor.setPlatform(platform.getId());

						forumList.add(f);
						courseForumList.add(couFor);
					}
					// Create Quizzes
					for (int l = 1; l < MAGIC_THREE; l++)
					{
						final QuizMining q = new QuizMining();
						q.setId(Long.valueOf(platform.getPrefix() + "" + (quizList.size() + 1)));
						q.setTimeCreated(cou.getTimeCreated() + randy.nextInt(week));
						if(characteristic != 1) {
							q.setTimeCreated(startdate);
						}
						q.setTimeModified(q.getTimeCreated() + randy.nextInt(year / 2));
						q.setTitle("Quiz " + i + "." + j + "." + k + "." + l);
						q.setMaxGrade(Double.parseDouble("" + (randy.nextInt(19) + 1)) * MAGIC_FIVE);
						q.setQtype("QuizType " + randy.nextInt(MAGIC_TEN));
						q.setTimeOpen(q.getTimeCreated() + randy.nextInt(month));
						q.setTimeClose(q.getTimeOpen() + (2 * week));
						q.setPlatform(platform.getId());

						final CourseQuizMining couQui = new CourseQuizMining();
						couQui.setId(Long.valueOf(platform.getPrefix() + "" + (courseQuizList.size() + 1)));
						couQui.setCourse(cou);
						couQui.setQuiz(q);
						couQui.setPlatform(platform.getId());

						quizList.add(q);
						courseQuizList.add(couQui);

						for (int m = 1; m < 11; m++)
						{
							final QuestionMining que = new QuestionMining();
							que.setId(Long.valueOf(platform.getPrefix() + "" + (questionList.size() + 1)));
							que.setTimeCreated(q.getTimeCreated() + randy.nextInt(week));
							if(characteristic != 1) {
								que.setTimeCreated(startdate);
							}
							que.setTimeModified(que.getTimeCreated() + randy.nextInt(year / 2));

							que.setTitle("Question " + i + "." + j + "." + k + "." + l + "." + m);
							que.setText("This is the text for " + que.getTitle() + " of quiz " + q.getTitle());
							que.setType(EQuestionType.values()[randy.nextInt(EQuestionType.values().length - 1)]
									.toString());
							que.setPlatform(platform.getId());

							final QuizQuestionMining qqm = new QuizQuestionMining();
							qqm.setId(Long.valueOf(platform.getPrefix() + "" + (quizQuestionList.size() + 1)));
							qqm.setQuiz(q);
							qqm.setQuestion(que);
							qqm.setPlatform(platform.getId());

							questionList.add(que);
							quizQuestionList.add(qqm);
						}
						for (int m = 9; m >= 1; m--)
						{
							final QuizUserMining qu = new QuizUserMining();
							qu.setId(Long.valueOf(platform.getPrefix() + "" + (quizUserList.size() + 1)));
							qu.setQuiz(q);
							qu.setCourse(cou);
							qu.setRawGrade(randy.nextInt(q.getMaxGrade().intValue()));
							qu.setPlatform(platform.getId());

							if (qu.getRawGrade() > 0) {
								qu.setFinalGrade(qu.getRawGrade() - randy.nextInt((int) qu.getRawGrade()));
							} else {
								qu.setFinalGrade(0.0d);
							}
							qu.setTimeModified(q.getTimeOpen()
									+ randy.nextInt(Integer.valueOf("" + (q.getTimeClose() - q.getTimeOpen()))));
							qu.setUser(courseUserList.get(courseUserList.size() - m).getUser());

							quizUserList.add(qu);
						}

					}
					// Create Chats
					for (int l = 1; l < 2; l++)
					{
						final ChatMining c = new ChatMining();
						c.setId(Long.valueOf(platform.getPrefix() + "" + (forumList.size() + 1)));
						c.setCourse(cou);
						c.setTitle("Chat " + i + "." + j + "." + k + "." + l);
						c.setDescription("Chat " + i + "." + j + "." + k + "." + l + "'s description...");
						c.setChatTime(cou.getTimeCreated() + randy.nextInt(week));
						c.setPlatform(platform.getId());

						chatList.add(c);
					}
					// Create scorms
					for (int l = 1; l < MAGIC_THREE; l++)
					{
						final ScormMining sco = new ScormMining();
						sco.setId(Long.valueOf(platform.getPrefix() + "" + (scormList.size() + 1)));
						sco.setMaxGrade(Double.parseDouble("" + (randy.nextInt(19) + 1)) * MAGIC_FIVE);
						sco.setTimeCreated(cou.getTimeCreated() + randy.nextInt(week));
						if(characteristic != 1) {
							sco.setTimeCreated(startdate);
						}
						sco.setTimeModified(sco.getTimeCreated() + randy.nextInt(year / 2));
						sco.setTimeOpen(sco.getTimeCreated() + randy.nextInt(month));
						sco.setTimeClose(sco.getTimeOpen() + (2 * week));
						sco.setTitle("Scorm " + i + "." + j + "." + k + "." + l);
						sco.setType("Scormtype");
						sco.setPlatform(platform.getId());

						final CourseScormMining cs = new CourseScormMining();
						cs.setId(Long.valueOf(platform.getPrefix() + "" + (courseScormList.size() + 1)));
						cs.setCourse(cou);
						cs.setScorm(sco);
						cs.setPlatform(platform.getId());

						scormList.add(sco);
						courseScormList.add(cs);
					}

					// Create log-entries
					// Create AssignmentLogs
					final int logSwitch = logsPerLearnObject + ((((k - 1) / 9) % MAGIC_THREE) * logsPerLearnObject);
					for (int log = 0; log < logSwitch; log++)
					{

						
						// _________________ResourceLogs___________________________________________________
						int time = (int) (startdate + log * (year / logSwitch));
						int mult;
						if(characteristic == 1 )
						{
							final ResourceLogMining rLog = new ResourceLogMining();
							rLog.setCourse(cou);
							rLog.setResource(resourceList.get((resourceList.size() - 1) - randy.nextInt(MAGIC_TEN)));
							rLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(userSwitch))
									% userList.size()));
							final ResourceMining r = rLog.getResource();
	
							mult = (int) ((startdate + year) - r.getTimeCreated())
									/ Integer.valueOf(cou.getShortname());
							
	
								time = (int) r.getTimeCreated()
									+ (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
							
								rLog.setTimestamp(time);
								if(rLog.getTimestamp() > maxLog)
								{
									maxLog = rLog.getTimestamp();
								}
	
								rLog.setAction("view");
								rLog.setPlatform(platform.getId());
	
								resourceLogList.add(rLog);
						}
						else
						{
							for(int x = 0 ; x < log + 1 % (year / MAGIC_EIGHT); x++)
							{
								final ResourceLogMining rLog1 = new ResourceLogMining();
								rLog1.setCourse(cou);
								rLog1.setResource(resourceList.get((resourceList.size() - 1) - randy.nextInt(MAGIC_TEN)));
								rLog1.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(userSwitch))
										% userList.size()));
								
								rLog1.setTimestamp(time);
								if(rLog1.getTimestamp() > maxLog)
								{
									maxLog = rLog1.getTimestamp();
								}
	
								rLog1.setAction("view");
								rLog1.setPlatform(platform.getId());
	
								resourceLogList.add(rLog1);

							}
						}
						// _________________AssignmentLogs___________________________________________________

						if(characteristic == 1)
						{
							
							final AssignmentLogMining aLog = new AssignmentLogMining();
							aLog.setCourse(cou);
							aLog.setAssignment(assignmentList.get((assignmentList.size() - 1) - randy.nextInt(2)));
							aLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
							final AssignmentMining a = aLog.getAssignment();
	
							mult = (int) (a.getTimeClose() - a.getTimeOpen()) / Integer.valueOf(cou.getShortname());
							time = (int) a.getTimeOpen() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
							aLog.setTimestamp(time);
							if(aLog.getTimestamp() > maxLog)
							{
								maxLog = aLog.getTimestamp();
							}
							aLog.setPlatform(platform.getId());
	
							for (int h = 0; h < courseUserList.size(); h++) {
								if ((aLog.getUser() == (courseUserList.get(h).getUser()))
										&& (courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix()
												+ "" + 3)) && (a.getMaxGrade() > 0))
								{
									aLog.setGrade(a.getMaxGrade() - randy.nextInt(a.getMaxGrade().intValue()));
									aLog.setAction(assignmentActionTeacher[randy.nextInt(assignmentActionTeacher.length)]);
	
								}
							}
							if (aLog.getAction() == null) {
								aLog.setAction(assignmentActionStudent[randy.nextInt(assignmentActionStudent.length)]);
							}
	
							assignmentLogList.add(aLog);
						}
						else
						{
							
							for(int x = 0 ; x < log + 1 % (year / MAGIC_EIGHT); x++)
							{
								final AssignmentLogMining aLog = new AssignmentLogMining();
								aLog.setCourse(cou);
								aLog.setAssignment(assignmentList.get((assignmentList.size() - 1) - randy.nextInt(2)));
								aLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
								final AssignmentMining a = aLog.getAssignment();
								aLog.setTimestamp(time);
								if(aLog.getTimestamp() > maxLog)
								{
									maxLog = aLog.getTimestamp();
								}
								aLog.setPlatform(platform.getId());
		
								for (int h = 0; h < courseUserList.size(); h++) {
									if ((aLog.getUser() == (courseUserList.get(h).getUser()))
											&& (courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix()
													+ "" + 3)) && (a.getMaxGrade() > 0))
									{
										aLog.setGrade(a.getMaxGrade() - randy.nextInt(a.getMaxGrade().intValue()));
										aLog.setAction(assignmentActionTeacher[randy.nextInt(assignmentActionTeacher.length)]);
		
									}
								}
								if (aLog.getAction() == null) {
									aLog.setAction(assignmentActionStudent[randy.nextInt(assignmentActionStudent.length)]);
								}
		
								assignmentLogList.add(aLog);
							}
						}

						// _________________ChatLogs___________________________________________________
						
						if(characteristic == 1)
						{
							final ChatLogMining cLog = new ChatLogMining();
							cLog.setCourse(cou);
							cLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
							cLog.setChat(chatList.get((chatList.size() - 1)));
							final ChatMining chat = cLog.getChat();
	
							mult = (int) ((startdate + year) - chat.getChatTime()) / Integer.valueOf(cou.getShortname());
							time = (int) chat.getChatTime() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
							cLog.setTimestamp(time);
							if(cLog.getTimestamp() > maxLog)
							{
								maxLog = cLog.getTimestamp();
							}
							cLog.setMessage("Generated chat message for chat " + cou.getShortname() + " @ "
									+ cLog.getTimestamp());
							cLog.setPlatform(platform.getId());
	
							chatLogList.add(cLog);
						}
						else
						{
							
							for(int x = 0 ; x < log + 1 % (year / MAGIC_EIGHT); x++)
							{
								final ChatLogMining cLog = new ChatLogMining();
								cLog.setCourse(cou);
								cLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
								cLog.setChat(chatList.get((chatList.size() - 1)));
								cLog.setTimestamp(time);
								if(cLog.getTimestamp() > maxLog)
								{
									maxLog = cLog.getTimestamp();
								}
								cLog.setMessage("Generated chat message for chat " + cou.getShortname() + " @ "
										+ cLog.getTimestamp());
								cLog.setPlatform(platform.getId());
		
								chatLogList.add(cLog);
							}
						}

						// _________________CourseLogs___________________________________________________

						if(characteristic == 1)
						{
							final CourseLogMining couLog = new CourseLogMining();
							couLog.setCourse(cou);
	
							if (log < MAGIC_TEN)
							{
								couLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + log) % userList.size()));
								couLog.setAction("enrol");
	
								mult = (int) (cou.getEnrolEnd() - cou.getEnrolStart())
										/ Integer.valueOf(cou.getShortname());
								time = (int) cou.getEnrolStart()
										+ (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
								couLog.setTimestamp(time);
								if(couLog.getTimestamp() > maxLog)
								{
									maxLog = couLog.getTimestamp();
								}
							}
							else
							{
								couLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN))
										% userList.size()));
								couLog.setAction("view");
	
								mult = (int) ((startdate + year) - cou.getTimeCreated())
										/ Integer.valueOf(cou.getShortname());
								time = (int) cou.getTimeCreated()
										+ (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
								couLog.setTimestamp(time);
								if(couLog.getTimestamp() > maxLog)
								{
									maxLog = couLog.getTimestamp();
								}
							}
							couLog.setPlatform(platform.getId());
	
							courseLogList.add(couLog);							
						}
						else
						{
							for(int x = 0 ; x < log + 1 % (year / MAGIC_EIGHT); x++)
							{
								final CourseLogMining couLog = new CourseLogMining();
								couLog.setCourse(cou);
								
								couLog.setTimestamp(time);
								if(couLog.getTimestamp() > maxLog)
								{
									maxLog = couLog.getTimestamp();
								}
								if (log < MAGIC_TEN)
								{
									couLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + log) % userList.size()));
									couLog.setAction("enrol");
								}
								else
								{
									couLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN))
											% userList.size()));
									couLog.setAction("view");
								}
								couLog.setPlatform(platform.getId());
		
								courseLogList.add(couLog);	
							}
						}
						
						// _________________ForumLogs___________________________________________________

						if(characteristic == 1)
						{
							final ForumLogMining fLog = new ForumLogMining();
							fLog.setCourse(cou);
							fLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
							fLog.setForum(forumList.get((forumList.size() - 1) - randy.nextInt(2)));
							final ForumMining forum = fLog.getForum();
	
							mult = (int) ((startdate + year) - forum.getTimeCreated())
									/ Integer.valueOf(cou.getShortname());
							time = (int) forum.getTimeCreated()
									+ (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
							fLog.setTimestamp(time);
							if(fLog.getTimestamp() > maxLog)
							{
								maxLog = fLog.getTimestamp();
							}
	
							fLog.setAction(forumAction[randy.nextInt(forumAction.length)]);
							fLog.setSubject("Subject No." + randy.nextInt(MAGIC_FIVE));
							fLog.setMessage("Message in forum " + forum.getTitle() + " @" + fLog.getTimestamp());
							fLog.setPlatform(platform.getId());
	
							forumLogList.add(fLog);
						}
						else
						{
							
							for(int x = 0 ; x < log + 1 % (year / MAGIC_EIGHT); x++)
							{
								final ForumLogMining fLog = new ForumLogMining();
								fLog.setCourse(cou);
								fLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
								fLog.setForum(forumList.get((forumList.size() - 1) - randy.nextInt(2)));
								final ForumMining forum = fLog.getForum();
								fLog.setTimestamp(time);
								if(fLog.getTimestamp() > maxLog)
								{
									maxLog = fLog.getTimestamp();
								}
		
								fLog.setAction(forumAction[randy.nextInt(forumAction.length)]);
								fLog.setSubject("Subject No." + randy.nextInt(MAGIC_FIVE));
								fLog.setMessage("Message in forum " + forum.getTitle() + " @" + fLog.getTimestamp());
								fLog.setPlatform(platform.getId());
		
								forumLogList.add(fLog);
							}
						}

						// _________________WikiLogs___________________________________________________
						
						if(characteristic == 1)
						{
							final WikiLogMining wLog = new WikiLogMining();
							wLog.setCourse(cou);
							wLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
							wLog.setWiki(wikiList.get((wikiList.size() - 1) - randy.nextInt(2)));
							final WikiMining wiki = wLog.getWiki();
	
							mult = (int) ((startdate + year) - wiki.getTimeCreated()) / Integer.valueOf(cou.getShortname());
							time = (int) wiki.getTimeCreated()
									+ (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
							wLog.setTimestamp(time);
							if(wLog.getTimestamp() > maxLog)
							{
								maxLog = wLog.getTimestamp();
							}
	
							wLog.setAction("view");
							wLog.setPlatform(platform.getId());
	
							wikiLogList.add(wLog);
						}
						else
						{
							
							for(int x = 0 ; x < log + 1 % (year / MAGIC_EIGHT); x++)
							{
								final WikiLogMining wLog = new WikiLogMining();
								wLog.setCourse(cou);
								wLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
								wLog.setWiki(wikiList.get((wikiList.size() - 1) - randy.nextInt(2)));
								wLog.setTimestamp(time);
								if(wLog.getTimestamp() > maxLog)
								{
									maxLog = wLog.getTimestamp();
								}
								wLog.setAction("view");
								wLog.setPlatform(platform.getId());
		
								wikiLogList.add(wLog);
							}
						}

						// _________________ScormLogs___________________________________________________

						if(characteristic == 1)
						{
							final ScormLogMining sLog = new ScormLogMining();
							sLog.setCourse(cou);
							sLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
							sLog.setScorm(scormList.get((scormList.size() - 1) - randy.nextInt(2)));
							final ScormMining scorm = sLog.getScorm();
	
							mult = (int) (scorm.getTimeClose() - scorm.getTimeOpen()) / Integer.valueOf(cou.getShortname());
							time = (int) scorm.getTimeOpen() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
							sLog.setTimestamp(time);
							if(sLog.getTimestamp() > maxLog)
							{
								maxLog = sLog.getTimestamp();
							}
	
							for (int h = 0; h < courseUserList.size(); h++) {
								if ((sLog.getUser() == (courseUserList.get(h).getUser()))
										&& (courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix()
												+ "" + 3)) && (scorm.getMaxGrade() > 0))
								{
									sLog.setGrade(scorm.getMaxGrade() - randy.nextInt(scorm.getMaxGrade().intValue()));
									sLog.setAction("report");
	
								}
							}
							if (sLog.getAction() == null) {
								sLog.setAction("view");
							}
	
							sLog.setPlatform(platform.getId());
							scormLogList.add(sLog);
						}
						else
						{
							
							for(int x = 0 ; x < log + 1 % (year / MAGIC_EIGHT); x++)
							{
								final ScormLogMining sLog = new ScormLogMining();
								sLog.setCourse(cou);
								sLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
								sLog.setScorm(scormList.get((scormList.size() - 1) - randy.nextInt(2)));
								final ScormMining scorm = sLog.getScorm();
								sLog.setTimestamp(time);
								if(sLog.getTimestamp() > maxLog)
								{
									maxLog = sLog.getTimestamp();
								}
		
								for (int h = 0; h < courseUserList.size(); h++) {
									if ((sLog.getUser() == (courseUserList.get(h).getUser()))
											&& (courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix()
													+ "" + 3)) && (scorm.getMaxGrade() > 0))
									{
										sLog.setGrade(scorm.getMaxGrade() - randy.nextInt(scorm.getMaxGrade().intValue()));
										sLog.setAction("report");
		
									}
								}
								if (sLog.getAction() == null) {
									sLog.setAction("view");
								}
		
								sLog.setPlatform(platform.getId());
								scormLogList.add(sLog);
							}
						}
						
						
						// _________________QuizLogs___________________________________________________
						
						if(characteristic == 1)
						{

							final QuizLogMining qLog = new QuizLogMining();
							qLog.setCourse(cou);
							qLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
							qLog.setQuiz(quizList.get((quizList.size() - 1) - randy.nextInt(2)));
							QuizMining quiz = qLog.getQuiz();
	
							mult = (int) (quiz.getTimeClose() - quiz.getTimeOpen()) / Integer.valueOf(cou.getShortname());
							time = (int) quiz.getTimeOpen() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
							qLog.setTimestamp(time);
							if(qLog.getTimestamp() > maxLog)
							{
								maxLog = qLog.getTimestamp();
							}
	
							for (int h = 0; h < courseUserList.size(); h++) {
								if ((qLog.getUser() == (courseUserList.get(h).getUser()))
										&& (courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix()
												+ "" + 3)) && (quiz.getMaxGrade() > 0))
								{
									qLog.setGrade(quiz.getMaxGrade() - randy.nextInt(quiz.getMaxGrade().intValue()));
									qLog.setAction("report");
	
								}
							}
							if (qLog.getAction() == null) {
								qLog.setAction("attempt");
							}
	
							qLog.setPlatform(platform.getId());
	
							quizLogList.add(qLog);
						

							// _________________QuestionLogs___________________________________________________
	
							final QuestionLogMining queLog = new QuestionLogMining();
							queLog.setCourse(cou);
							queLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN))
									% userList.size()));
							queLog.setQuiz(quizList.get((quizList.size() - 1) - randy.nextInt(2)));
							queLog.setQuestion(questionList.get((questionList.size() - 1) - randy.nextInt(MAGIC_TEN)));
							quiz = queLog.getQuiz();
	
							mult = (int) (quiz.getTimeClose() - quiz.getTimeOpen()) / Integer.valueOf(cou.getShortname());
							time = (int) quiz.getTimeOpen() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
							queLog.setTimestamp(time);
							if(qLog.getTimestamp() > maxLog)
							{
								maxLog = qLog.getTimestamp();
							}
	
							for (int h = 0; h < courseUserList.size(); h++) {
								if ((qLog.getUser() == (courseUserList.get(h).getUser()))
										&& (courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix()
												+ "" + 3)) && (quiz.getMaxGrade() > 0))
								{
									final int qNumber = MAGIC_TEN;
									if (quiz.getMaxGrade() < qNumber) {
										queLog.setRawGrade((randy.nextDouble() * quiz.getMaxGrade()) / qNumber);
									}
									queLog.setFinalGrade(queLog.getRawGrade() - (randy.nextDouble() * queLog.getRawGrade()));
									queLog.setPenalty(queLog.getRawGrade() - queLog.getFinalGrade());
									queLog.setAction("CLOSE");
	
								}
							}
							if (queLog.getAction() == null) {
								queLog.setAction("OPEN");
							}
							queLog.setAnswers("Answers for " + queLog.getQuestion().getTitle());
							queLog.setType("text");
	
							queLog.setPlatform(platform.getId());
	
							questionLogList.add(queLog);
						}
						else
						{
							
							for(int x = 0 ; x < log + 1 % (year / MAGIC_EIGHT); x++)
							{
								final QuizLogMining qLog = new QuizLogMining();
								qLog.setCourse(cou);
								qLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
								qLog.setQuiz(quizList.get((quizList.size() - 1) - randy.nextInt(2)));
								QuizMining quiz = qLog.getQuiz();
		
								qLog.setTimestamp(time);
								if(qLog.getTimestamp() > maxLog)
								{
									maxLog = qLog.getTimestamp();
								}
		
								for (int h = 0; h < courseUserList.size(); h++) {
									if ((qLog.getUser() == (courseUserList.get(h).getUser()))
											&& (courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix()
													+ "" + 3)) && (quiz.getMaxGrade() > 0))
									{
										qLog.setGrade(quiz.getMaxGrade() - randy.nextInt(quiz.getMaxGrade().intValue()));
										qLog.setAction("report");
		
									}
								}
								if (qLog.getAction() == null) {
									qLog.setAction("attempt");
								}
		
								qLog.setPlatform(platform.getId());
		
								quizLogList.add(qLog);
							

								// _________________QuestionLogs___________________________________________________
		
								final QuestionLogMining queLog = new QuestionLogMining();
								queLog.setCourse(cou);
								queLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN))
										% userList.size()));
								queLog.setQuiz(quizList.get((quizList.size() - 1) - randy.nextInt(2)));
								queLog.setQuestion(questionList.get((questionList.size() - 1) - randy.nextInt(MAGIC_TEN)));
								quiz = queLog.getQuiz();
		
								queLog.setTimestamp(time);
								if(queLog.getTimestamp() > maxLog)
								{
									maxLog = queLog.getTimestamp();
								}
		
								for (int h = 0; h < courseUserList.size(); h++) {
									if ((qLog.getUser() == (courseUserList.get(h).getUser()))
											&& (courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix()
													+ "" + 3)) && (quiz.getMaxGrade() > 0))
									{
										final int qNumber = MAGIC_TEN;
										if (quiz.getMaxGrade() < qNumber) {
											queLog.setRawGrade((randy.nextDouble() * quiz.getMaxGrade()) / qNumber);
										}
										queLog.setFinalGrade(queLog.getRawGrade() - (randy.nextDouble() * queLog.getRawGrade()));
										queLog.setPenalty(queLog.getRawGrade() - queLog.getFinalGrade());
										queLog.setAction("CLOSE");
		
									}
								}
								if (queLog.getAction() == null) {
									queLog.setAction("OPEN");
								}
								queLog.setAnswers("Answers for " + queLog.getQuestion().getTitle());
								queLog.setType("text");
		
								queLog.setPlatform(platform.getId());
		
								questionLogList.add(queLog);
							
							}
						}
					}

				}
			}
		}

		all.add(userList);
		all.add(roleList);
		all.add(groupList);
		all.add(levelList);
		all.add(courseList);
		all.add(resourceList);
		all.add(forumList);
		all.add(questionList);
		all.add(quizList);
		all.add(chatList);
		all.add(scormList);
		all.add(assignmentList);
		all.add(platformList);

		all.add(groupUserList);
		all.add(courseUserList);
		all.add(levelAssociationList);
		all.add(levelCourseList);
		all.add(courseQuizList);
		all.add(courseScormList);
		all.add(courseForumList);
		all.add(courseAssignmentList);
		all.add(courseWikiList);
		all.add(courseResourceList);
		all.add(quizQuestionList);
		all.add(quizUserList);
		all.add(courseGroupList);

		Collections.sort(resourceLogList);
		Collections.sort(assignmentLogList);
		Collections.sort(forumLogList);
		Collections.sort(courseLogList);
		Collections.sort(wikiLogList);
		Collections.sort(scormLogList);
		Collections.sort(chatLogList);
		Collections.sort(quizLogList);
		Collections.sort(questionLogList);

		final ArrayList<ILogMining> allLogs = new ArrayList<ILogMining>();
		
		LogDurationCalculator.createIds(courseLogList, 1);
		allLogs.addAll(resourceLogList);
		LogDurationCalculator.createIds(resourceLogList, 1);
		allLogs.addAll(wikiLogList);
		LogDurationCalculator.createIds(wikiLogList, 1);
		allLogs.addAll(assignmentLogList);
		LogDurationCalculator.createIds(assignmentLogList, 1);
		allLogs.addAll(forumLogList);
		LogDurationCalculator.createIds(forumLogList, 1);
		allLogs.addAll(scormLogList);
		LogDurationCalculator.createIds(scormLogList, 1);
		allLogs.addAll(quizLogList);
		LogDurationCalculator.createIds(quizLogList, 1);
		allLogs.addAll(questionLogList);
		LogDurationCalculator.createIds(questionLogList, 1);
		allLogs.addAll(chatLogList);
		LogDurationCalculator.createIds(chatLogList, 1);

		LogDurationCalculator.calculateDurationsForGeneratedData(allLogs);

		all.add(courseLogList);
		all.add(resourceLogList);
		all.add(wikiLogList);
		all.add(assignmentLogList);
		all.add(forumLogList);
		all.add(scormLogList);
		all.add(quizLogList);
		all.add(questionLogList);
		all.add(chatLogList);

		// Create and save config-object
		final ConfigMining config = new ConfigMining();
		config.setLastModifiedLong(System.currentTimeMillis());
		config.setElapsedTime(1);
		config.setDatabaseModel("1.3");
		config.setPlatform(platform.getId());
		config.setExtractCycle(1);
		config.setLatestTimestamp(maxLog);

		final ArrayList<ConfigMining> confList = new ArrayList<ConfigMining>();
		confList.add(config);

		all.add(confList);

		return all;
	}

}
