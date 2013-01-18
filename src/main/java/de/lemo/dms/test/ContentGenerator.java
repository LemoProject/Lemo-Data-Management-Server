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

public class ContentGenerator {

	
	private ESystem system;
	
	
	enum EResourceType{
		File,
		Directory,
		Glossary,
		Summary,
		Page,
	}
	
	enum EQuestionType{
		Pathetic,
		Easy,
		Mediocre,
		Tricky,
		Sphinxesque		
	}
	

	
	enum ESystem{
		fiz,
		moodle,
		clix
	}
	
	
	public  List<Collection<?>> generateMiningDB(Integer topLevels, Integer levelPerTopLevel, Integer coursesPerLevel, Long startdate, int logsPerLearnObject)
	{
		
		system = ESystem.moodle;
		
		List<Collection<?>> all = new ArrayList<Collection<?>>();
		
		//Object-containers
		ArrayList<LevelMining> levelList = new ArrayList<LevelMining>();
		ArrayList<CourseMining> courseList = new ArrayList<CourseMining>();
		ArrayList<ResourceMining> resourceList = new ArrayList<ResourceMining>();
		ArrayList<WikiMining> wikiList = new ArrayList<WikiMining>();
		ArrayList<AssignmentMining> assignmentList = new ArrayList<AssignmentMining>();
		ArrayList<ForumMining> forumList = new ArrayList<ForumMining>();
		ArrayList<ScormMining> scormList = new ArrayList<ScormMining>();
		ArrayList<QuizMining> quizList = new ArrayList<QuizMining>();
		ArrayList<QuestionMining> questionList = new ArrayList<QuestionMining>();
		ArrayList<ChatMining> chatList = new ArrayList<ChatMining>();
		ArrayList<UserMining> userList = new ArrayList<UserMining>();
		ArrayList<RoleMining> roleList = new ArrayList<RoleMining>();
		ArrayList<GroupMining> groupList = new ArrayList<GroupMining>();
		ArrayList<PlatformMining> platformList = new ArrayList<PlatformMining>();
		
		//Association-containers
		ArrayList<LevelCourseMining> levelCourseList = new ArrayList<LevelCourseMining>();
		ArrayList<LevelAssociationMining> levelAssociationList = new ArrayList<LevelAssociationMining>();
		
		ArrayList<CourseResourceMining> courseResourceList = new ArrayList<CourseResourceMining>();
		ArrayList<CourseWikiMining> courseWikiList = new ArrayList<CourseWikiMining>();
		ArrayList<CourseAssignmentMining> courseAssignmentList = new ArrayList<CourseAssignmentMining>();
		ArrayList<CourseForumMining> courseForumList = new ArrayList<CourseForumMining>();
		ArrayList<CourseScormMining> courseScormList = new ArrayList<CourseScormMining>();
		ArrayList<CourseQuizMining> courseQuizList = new ArrayList<CourseQuizMining>();
		ArrayList<QuizQuestionMining> quizQuestionList = new ArrayList<QuizQuestionMining>();
		ArrayList<GroupUserMining> groupUserList = new ArrayList<GroupUserMining>();
		ArrayList<CourseUserMining> courseUserList = new ArrayList<CourseUserMining>();
		ArrayList<CourseGroupMining> courseGroupList = new ArrayList<CourseGroupMining>();
		ArrayList<QuizUserMining> quizUserList = new ArrayList<QuizUserMining>();
		
		//Log-containers
		ArrayList<CourseLogMining> courseLogList = new ArrayList<CourseLogMining>();
		ArrayList<ILogMining> resourceLogList = new ArrayList<ILogMining>();
		ArrayList<WikiLogMining> wikiLogList = new ArrayList<WikiLogMining>();
		ArrayList<AssignmentLogMining> assignmentLogList = new ArrayList<AssignmentLogMining>();
		ArrayList<ForumLogMining> forumLogList = new ArrayList<ForumLogMining>();
		ArrayList<ScormLogMining> scormLogList = new ArrayList<ScormLogMining>();
		ArrayList<QuizLogMining> quizLogList = new ArrayList<QuizLogMining>();
		ArrayList<QuestionLogMining> questionLogList = new ArrayList<QuestionLogMining>();
		ArrayList<ChatLogMining> chatLogList = new ArrayList<ChatLogMining>();
		
			
		String[] forumAction = new String[4];
		forumAction[0] = "view forum";
		forumAction[1] = "subscribe";
		forumAction[2] = "add discussion";
		forumAction[3] = "view discussion";
		
		String[] assignmentActionTeacher = new String[4];
		assignmentActionTeacher[0] = "add";
		assignmentActionTeacher[1] = "update";
		assignmentActionTeacher[2] = "update grades";
		assignmentActionTeacher[3] = "view submissions";
		
		String[] assignmentActionStudent = new String[2];
		assignmentActionStudent[0] = "upload";
		assignmentActionStudent[1] = "view";
		
		Random randy = new Random(15768000);
		
		int offset;
		int year = 31536000;
		int month = 2592000;
		int week = 604800;
		int day = 86400;
		
		Date dt = new Date();
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

		
		PlatformMining platform = new PlatformMining();
		platform.setId(1L);
		platform.setName("ContentGenerator @ " + df.format( dt ));
		platform.setPrefix(22L);
		platform.setType("ContentGenerator");
		
		platformList.add(platform);
		

		
		
		//Create users
		for(int i = 0; i < 100; i++)
		{
			UserMining user = new UserMining();
			user.setId(Long.valueOf(platform.getPrefix() + "" + (userList.size() + 1)));
			user.setFirstaccess(startdate + randy.nextInt(4 * week));
			user.setLastlogin(year - week - randy.nextInt(month));
			user.setLastaccess(user.getLastlogin() + randy.nextInt(day));
			user.setCurrentlogin(user.getLastaccess() + randy.nextInt(week));
			user.setGender(i % 2 == 0);
			user.setLogin(Encoder.createMD5(i +" "+ df.format(dt)));
			user.setPlatform(platform.getId());
			
			userList.add(user);
		}
		
		//Create roles

		RoleMining r0 = new RoleMining();
		r0.setId(Long.valueOf(platform.getPrefix() + "" + (roleList.size() +1)));
		r0.setName("Administrator");
		r0.setShortname("Admin");
		r0.setDescription("Administatoren dürfen alles.");			
		r0.setSortorder(0);
		r0.setPlatform(platform.getId());
		
		roleList.add(r0);
		
		RoleMining r1 = new RoleMining();
		r1.setId(Long.valueOf(platform.getPrefix() + "" + (roleList.size() +1)));
		r1.setName("Dozent");
		r1.setShortname("Doz");
		r1.setDescription("Dozenten dürfen in ihren Kursen alles.");			
		r1.setSortorder(1);
		r1.setPlatform(platform.getId());
		
		roleList.add(r1);
		
		RoleMining r2 = new RoleMining();
		r2.setId(Long.valueOf(platform.getPrefix() + "" + (roleList.size() +1)));
		r2.setName("Student");
		r2.setShortname("Stud");
		r2.setDescription("Studenten dürfen alles lernen.");			
		r2.setSortorder(2);
		r2.setPlatform(platform.getId());
		
		roleList.add(r2);
			
		//Create groups
		for(int i = 0; i < coursesPerLevel * levelPerTopLevel * topLevels; i++)
		{
			GroupMining gr = new GroupMining();
			gr.setId(Long.valueOf(platform.getPrefix() + "" + (groupList.size() + 1)));
			
			if(i % 2 == 0)
				offset = 15768000;
			else
				offset = 0;
			
			gr.setTimecreated(startdate + offset + randy.nextInt(week));
			gr.setTimemodified(gr.getTimecreated() + randy.nextInt(month));
			gr.setPlatform(platform.getId());
			
			groupList.add(gr);
			
			for(int j = i * 5; j < i * 5 + 10; j ++)
			{
				GroupUserMining gu = new GroupUserMining();
				gu.setId(Long.valueOf(platform.getPrefix() + "" + (groupUserList.size() + 1)));
				gu.setUser(userList.get(j % userList.size()));
				gu.setGroup(gr);
				gu.setPlatform(platform.getId());
				
				groupUserList.add(gu);
			}
		}
			
		
		for(int i = 1; i <= topLevels; i++)
		{
			//Create departments
			LevelMining dep = new LevelMining();
			dep.setId(Long.valueOf(platform.getPrefix() + "" + (levelList.size()+1)));
			dep.setPlatform(platform.getId());
			dep.setTitle("Department "+i);
			dep.setDepth(1);
			levelList.add(dep);
			for(int j = 1 ; j <= levelPerTopLevel; j++)
			{
				//Create degrees
				LevelMining deg = new LevelMining();
				deg.setId(Long.valueOf(platform.getPrefix() + "" + (levelList.size()+1)));
				deg.setPlatform(platform.getId());
				deg.setTitle("Degree "+i+"."+j);
				deg.setDepth(2);
				levelList.add(deg);
				
				LevelAssociationMining depDeg = new LevelAssociationMining();
				depDeg.setLower(deg);
				depDeg.setUpper(dep);
				depDeg.setPlatform(platform.getId());
				depDeg.setId(Long.valueOf(platform.getPrefix() + "" + (levelAssociationList.size()+1)));
				
				levelAssociationList.add(depDeg);
				
				for(int k = 1 ; k <= coursesPerLevel ; k++)
				{
					//Create courses
					if(k % 2 == 0)
						offset = 15768000;
					else
						offset = 0;
					
					CourseMining cou = new CourseMining();
					cou.setTitle("Course "+i+"."+j+"."+k);
					
					int ts = (int) (startdate + offset);
					
					cou.setTimecreated(ts + randy.nextInt(week));
					
					cou.setEnrolstart(ts + 	week + randy.nextInt(week));
					cou.setEnrolend(cou.getEnrolstart() + 3 * week);	
					cou.setTimemodified(ts + randy.nextInt(year / 2));
					cou.setStartdate(cou.getEnrolstart() + 2 * week);
					cou.setShortname(i+""+j+""+k);					
					cou.setId(Long.valueOf(platform.getPrefix() + "" + (courseList.size()+1)));
					
					LevelCourseMining degCou = new LevelCourseMining();
					degCou.setLevel(deg);
					degCou.setCourse(cou);
					degCou.setId(Long.valueOf(platform.getPrefix() + "" + (levelCourseList.size()+1)));
					cou.setPlatform(platform.getId());
					degCou.setPlatform(platform.getId());
					
					courseList.add(cou);
					levelCourseList.add(degCou);
					
					//Associate users with course
					int userSwitch = 25 + ((k-1) % 3) * 25;
					for(int l = 0; l < userSwitch; l ++)
					{
						CourseUserMining cu = new CourseUserMining();
						cu.setId(Long.valueOf(platform.getPrefix() + "" + (courseUserList.size() + 1)));
						if(l == 0)
							cu.setRole(roleList.get(0));
						else if(l == 1)
							cu.setRole(roleList.get(1));
						else
							cu.setRole(roleList.get(2));
						
						cu.setCourse(cou);
						cu.setEnrolstart(cou.getEnrolstart());
						cu.setEnrolend(cou.getEnrolend());
						cu.setUser(userList.get((  (courseList.size() -1) * 5 + l   ) % userList.size()));
						cu.setPlatform(platform.getId());
						
						courseUserList.add(cu);
					}
					
					CourseGroupMining cg = new CourseGroupMining();
					cg.setId(Long.valueOf(platform.getPrefix() + "" + (courseGroupList.size() + 1)));
					cg.setCourse(cou);
					cg.setGroup(groupList.get(courseList.size() -1 ));
					cg.setPlatform(platform.getId());
					
					courseGroupList.add(cg);
					
					//Now create LearningObjects
					//Create resources
					int pt = (randy.nextInt(6)+ 1) * 15;
					int resSwitch = 15 + (((k-1) / 3) % 3) * 15;
					for(int l = 1; l < resSwitch; l++)
					{
						ResourceMining r = new ResourceMining();
						r.setId(Long.valueOf(platform.getPrefix() + "" + (resourceList.size() + 1)));
						r.setDifficulty("Level " + randy.nextInt(10));
						r.setPosition(l);
						r.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						r.setTimemodified(r.getTimecreated() + randy.nextInt(year/2));
						r.setTitle("Resource "+i+"."+j+"."+k+"."+l);
						r.setPlatform(platform.getId());
						if(system == ESystem.fiz)
						{
							if( l == 1)
							{
								r.setType("VLU");
								r.setProcessingTime(pt);
								r.setUrl("http://www.chemgapedia.de/vsengine/vlu/vsc/" + r.getTitle() +".vlu.html");
							}
							else if(l < 10)
							{
								r.setType("Page");
								r.setProcessingTime(pt / 8);
								r.setUrl("http://www.chemgapedia.de/vsengine/vlu/vsc/Resource "+i+"."+j+"."+k+".1.vlu/Page/vsc/" + r.getTitle() +".vscml.html");
							}
							else
							{
								r.setType("Summary");
								r.setProcessingTime(pt);
								r.setUrl("http://www.chemgapedia.de/vsengine/vlu/vsc/Resource "+i+"."+j+"."+k+".1.vlu/Page/summary.html");
							}
						}
						else
						{
							r.setType(EResourceType.values()[randy.nextInt(EResourceType.values().length)].toString());
							r.setProcessingTime(pt);
							r.setUrl("http://www.lemo.com/" + r.getTitle() +".html");
						}
						CourseResourceMining couRes = new CourseResourceMining();
						couRes.setId(Long.valueOf(platform.getPrefix() + "" + (courseResourceList.size()+1)));
						couRes.setCourse(cou);
						couRes.setResource(r);
						couRes.setPlatform(platform.getId());
						
						resourceList.add(r);
						courseResourceList.add(couRes);
					}
					//Create wikis
					for(int l = 1; l < 3; l++)
					{
						WikiMining w = new WikiMining();
						w.setId(Long.valueOf(platform.getPrefix() + "" + (wikiList.size() + 1)));
						w.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						w.setTimemodified(w.getTimecreated() + randy.nextInt(year/2));
						w.setTitle("Wiki "+i+"."+j+"."+k+"."+l);
						w.setSummary("This is wiki " + i + "." + j + "." + k + "." + l + "'s summary.");
						
						CourseWikiMining couWik = new CourseWikiMining();
						couWik.setId(Long.valueOf(platform.getPrefix() + "" + (courseWikiList.size()+1)));
						couWik.setCourse(cou);
						couWik.setWiki(w);
						
						couWik.setPlatform(platform.getId());
						w.setPlatform(platform.getId());
						
						wikiList.add(w);
						courseWikiList.add(couWik);
					}
					//Create Assignments
					for(int l = 1; l < 3; l++)
					{
						AssignmentMining a = new AssignmentMining();
						a.setId(Long.valueOf(platform.getPrefix() + "" + (assignmentList.size() + 1)));
						a.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						a.setTimemodified(a.getTimecreated() + randy.nextInt(year/2));
						a.setTitle("Assignment "+i+"."+j+"."+k+"."+l);
						a.setMaxgrade(Double.parseDouble("" + (randy.nextInt(19) + 1)) * 5);
						a.setTimeopen(a.getTimecreated() + randy.nextInt(month));
						a.setTimeclose(a.getTimeopen() + 2 * week);
						a.setType("Assignment");
						a.setPlatform(platform.getId());
						
						CourseAssignmentMining couAss = new CourseAssignmentMining();
						couAss.setId(Long.valueOf(platform.getPrefix() + "" + (courseAssignmentList.size()+1)));
						couAss.setCourse(cou);
						couAss.setAssignment(a);
						couAss.setPlatform(platform.getId());
						
						assignmentList.add(a);
						courseAssignmentList.add(couAss);
					}
					//Create Forums
					for(int l = 1; l < 3; l++)
					{
						ForumMining f = new ForumMining();
						f.setId(Long.valueOf(platform.getPrefix() + "" + (forumList.size() + 1)));
						f.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						f.setTimemodified(f.getTimecreated() + randy.nextInt(year/2));
						f.setTitle("Forum "+i+"."+j+"."+k+"."+l);
						f.setSummary("Forum " + i + "." + j + "." + k + "." + l + "'s summary...");
						f.setPlatform(platform.getId());
						
						CourseForumMining couFor = new CourseForumMining();
						couFor.setId(Long.valueOf(platform.getPrefix() + "" + (courseForumList.size()+1)));
						couFor.setCourse(cou);
						couFor.setForum(f);
						couFor.setPlatform(platform.getId());
						
						forumList.add(f);
						courseForumList.add(couFor);
					}
					//Create Quizzes
					for(int l = 1; l < 3; l++)
					{
						QuizMining q = new QuizMining();
						q.setId(Long.valueOf(platform.getPrefix() + "" + (quizList.size() + 1)));
						q.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						q.setTimemodified(q.getTimecreated() + randy.nextInt(year/2));
						q.setTitle("Quiz "+i+"."+j+"."+k+"."+l);
						q.setMaxgrade(Double.parseDouble("" + (randy.nextInt(19) + 1)) * 5);
						q.setQtype("QuizType " + randy.nextInt(10));
						q.setTimeopen(q.getTimecreated() + randy.nextInt(month));
						q.setTimeclose(q.getTimeopen() + 2 * week);
						q.setPlatform(platform.getId());
						
						CourseQuizMining couQui = new CourseQuizMining();
						couQui.setId(Long.valueOf(platform.getPrefix() + "" + (courseQuizList.size()+1)));
						couQui.setCourse(cou);
						couQui.setQuiz(q);
						couQui.setPlatform(platform.getId());
						
						quizList.add(q);
						courseQuizList.add(couQui);
						
						for(int m  = 1; m < 11; m++)
						{
							QuestionMining que = new QuestionMining();
							que.setId(Long.valueOf(platform.getPrefix() + "" + (questionList.size() + 1)));
							que.setTimecreated(q.getTimecreated() + randy.nextInt(week));
							que.setTimemodified(que.getTimecreated() + randy.nextInt(year/2));
							
							que.setTitle("Question " + i + "." + j + "." + k + "." + l + "." + m);
							que.setText("This is the text for " + que.getTitle() + " of quiz " + q.getTitle() );
							que.setType(EQuestionType.values()[randy.nextInt(EQuestionType.values().length-1)].toString());
							que.setPlatform(platform.getId());
							
							
							QuizQuestionMining qqm = new QuizQuestionMining();
							qqm.setId(Long.valueOf(platform.getPrefix() + "" + (quizQuestionList.size() + 1)));
							qqm.setQuiz(q);
							qqm.setQuestion(que);
							qqm.setPlatform(platform.getId());
							
							
							questionList.add(que);
							quizQuestionList.add(qqm);
						}
						for(int m = 9; m >= 1; m--)
						{
							QuizUserMining qu = new QuizUserMining();
							qu.setId(Long.valueOf(platform.getPrefix() + "" + (quizUserList.size() + 1)));
							qu.setQuiz(q);
							qu.setCourse(cou);
							qu.setRawgrade(randy.nextInt(q.getMaxgrade().intValue()));
							qu.setPlatform(platform.getId());
							
							if(qu.getRawgrade() > 0)
								qu.setFinalgrade(qu.getRawgrade() - randy.nextInt((int)qu.getRawgrade()));
							else
								qu.setFinalgrade(0.0d);
							qu.setTimemodified(q.getTimeopen() + randy.nextInt(Integer.valueOf("" + (q.getTimeclose() - q.getTimeopen()))));
							qu.setUser(courseUserList.get(courseUserList.size() - m).getUser());
					
							quizUserList.add(qu);
						}
						
					}					
					//Create Chats
					for(int l = 1; l < 2; l++)
					{
						ChatMining c = new ChatMining();
						c.setId(Long.valueOf(platform.getPrefix() + "" + (forumList.size() + 1)));
						c.setCourse(cou);
						c.setTitle("Chat "+i+"."+j+"."+k+"."+l);
						c.setDescription("Chat " + i + "." + j + "." + k + "." + l + "'s description...");
						c.setChattime(cou.getTimecreated() + randy.nextInt(week));
						c.setPlatform(platform.getId());
						
						chatList.add(c);
					}
					//Create scorms
					for(int l = 1; l < 3; l++)
					{
						ScormMining sco = new ScormMining();
						sco.setId(Long.valueOf(platform.getPrefix() + "" + (scormList.size() + 1)));
						sco.setMaxgrade(Double.parseDouble("" + (randy.nextInt(19) + 1)) * 5);
						sco.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						sco.setTimemodified(sco.getTimecreated() + randy.nextInt(year/2));
						sco.setTimeopen(sco.getTimecreated() + randy.nextInt(month));
						sco.setTimeclose(sco.getTimeopen() + 2 * week);
						sco.setTitle("Scorm " + i + "." + j + "."  + k + "." + l);
						sco.setType("Scormtype");
						sco.setPlatform(platform.getId());
						
						CourseScormMining cs = new CourseScormMining();
						cs.setId(Long.valueOf(platform.getPrefix() + "" + (courseScormList.size() + 1)));
						cs.setCourse(cou);
						cs.setScorm(sco);
						cs.setPlatform(platform.getId());
						
						scormList.add(sco);
						courseScormList.add(cs);
					}
					
					//Create log-entries
					//Create AssignmentLogs
					int logSwitch = 500 + (((k-1) / 9) % 3) * 500;
					for(int log = 0; log < logSwitch; log++)
					{
						
						//_________________ResourceLogs___________________________________________________
						ResourceLogMining rLog = new ResourceLogMining();
						//rLog.setId(resourceLogList.size() + 1);
						rLog.setCourse(cou);
						rLog.setResource(resourceList.get((resourceList.size() - 1) - randy.nextInt(10)));
						rLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(userSwitch)) % userList.size()));
						ResourceMining r = rLog.getResource();
						
						int mult = (int)(startdate + year - r.getTimecreated()) / Integer.valueOf(cou.getShortname()) ;
						int time = (int)r.getTimecreated() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
						rLog.setTimestamp(time);
						
						rLog.setAction("view");
						rLog.setPlatform(platform.getId());
						
						resourceLogList.add(rLog);
						
						//_________________AssignmentLogs___________________________________________________
						
						AssignmentLogMining aLog = new AssignmentLogMining();
						//aLog.setId(assignmentLogList.size());
						aLog.setCourse(cou);
						aLog.setAssignment(assignmentList.get((assignmentList.size() - 1) - randy.nextInt(2)));
						aLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(10)) % userList.size()));
						AssignmentMining a = aLog.getAssignment();
						
						mult = (int)(a.getTimeclose() - a.getTimeopen()) / Integer.valueOf(cou.getShortname()) ;
						time = (int)a.getTimeopen() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
						aLog.setTimestamp(time);
						aLog.setPlatform(platform.getId());
						
						for(int h = 0; h < courseUserList.size(); h++)
							if(aLog.getUser() == (courseUserList.get(h).getUser()) && courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix() +"" + 2) && a.getMaxgrade() > 0)
							{
								aLog.setGrade(a.getMaxgrade() - randy.nextInt(a.getMaxgrade().intValue()));
								aLog.setAction(assignmentActionTeacher[randy.nextInt(assignmentActionTeacher.length)]);
								
							}
						if(aLog.getAction() == null)
							aLog.setAction(assignmentActionStudent[randy.nextInt(assignmentActionStudent.length)]);
						
						
						assignmentLogList.add(aLog);		
						
						//_________________ChatLogs___________________________________________________
						
						ChatLogMining cLog = new ChatLogMining();
						cLog.setCourse(cou);
						cLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(10)) % userList.size()));
						cLog.setChat(chatList.get((chatList.size() - 1)));
						ChatMining chat = cLog.getChat();
						
						mult = (int)(startdate + year - chat.getChattime()) / Integer.valueOf(cou.getShortname()) ;
						time = (int) chat.getChattime() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
						cLog.setTimestamp(time);
						cLog.setMessage("Generated chat message for chat "+ cou.getShortname() + " @ " + cLog.getTimestamp());
						cLog.setPlatform(platform.getId());
						
						chatLogList.add(cLog);
						
						//_________________CourseLogs___________________________________________________
						
						CourseLogMining couLog = new CourseLogMining();
						couLog.setCourse(cou);
						
						
						if(log < 10)
						{
							couLog.setUser(userList.get(( (courseList.size() -1) * 5 + log) % userList.size()));
							couLog.setAction("enrol");
							
							mult = (int)(cou.getEnrolend() - cou.getEnrolstart()) / Integer.valueOf(cou.getShortname()) ;
							time = (int) cou.getEnrolstart() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
							couLog.setTimestamp(time);
						}
						else
						{
							couLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(10)) % userList.size()));
							couLog.setAction("view");
							
							mult = (int)(startdate + year - cou.getTimecreated()) / Integer.valueOf(cou.getShortname()) ;
							time = (int) cou.getTimecreated() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
							couLog.setTimestamp(time);
						}
						couLog.setPlatform(platform.getId());
						
						courseLogList.add(couLog);
						
						//_________________ForumLogs___________________________________________________
						
						ForumLogMining fLog = new ForumLogMining();
						fLog.setCourse(cou);
						fLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(10)) % userList.size()));
						fLog.setForum(forumList.get((forumList.size() - 1) - randy.nextInt(2)));
						ForumMining forum = fLog.getForum();
						
						mult = (int)(startdate + year - forum.getTimecreated()) / Integer.valueOf(cou.getShortname()) ;
						time = (int) forum.getTimecreated() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
						fLog.setTimestamp(time);

						fLog.setAction(forumAction[randy.nextInt(forumAction.length)]);
						fLog.setSubject("Subject No." + randy.nextInt(5));
						fLog.setMessage("Message in forum " + forum.getTitle() + " @"+fLog.getTimestamp());
						fLog.setPlatform(platform.getId());
						
						forumLogList.add(fLog);
						
						//_________________WikiLogs___________________________________________________
						
						WikiLogMining wLog = new WikiLogMining();
						wLog.setCourse(cou);
						wLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(10)) % userList.size()));
						wLog.setWiki(wikiList.get((wikiList.size() - 1) - randy.nextInt(2)));
						WikiMining wiki = wLog.getWiki();
						
						mult = (int)(startdate + year - wiki.getTimecreated()) / Integer.valueOf(cou.getShortname()) ;
						time = (int) wiki.getTimecreated() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
						wLog.setTimestamp(time);
						
						wLog.setAction("view");
						wLog.setPlatform(platform.getId());
						
						wikiLogList.add(wLog);
						
						//_________________ScormLogs___________________________________________________
						
						ScormLogMining sLog = new ScormLogMining();
						sLog.setCourse(cou);
						sLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(10)) % userList.size()));
						sLog.setScorm(scormList.get((scormList.size() - 1) - randy.nextInt(2)));
						ScormMining scorm = sLog.getScorm();
						
						mult = (int)(scorm.getTimeclose() - scorm.getTimeopen()) / Integer.valueOf(cou.getShortname()) ;
						time = (int) scorm.getTimeopen() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
						sLog.setTimestamp(time);
						
						for(int h = 0; h < courseUserList.size(); h++)
							if(sLog.getUser() == (courseUserList.get(h).getUser()) && courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix() +"" + 2) && scorm.getMaxgrade() > 0)
							{
								sLog.setGrade(scorm.getMaxgrade() - randy.nextInt(scorm.getMaxgrade().intValue()));
								sLog.setAction("report");
								
							}
						if(sLog.getAction() == null)
							sLog.setAction("view");
				
						sLog.setPlatform(platform.getId());
						scormLogList.add(sLog);
						
						//_________________QuizLogs___________________________________________________
						
						QuizLogMining qLog = new QuizLogMining();
						qLog.setCourse(cou);
						qLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(10)) % userList.size()));
						qLog.setQuiz(quizList.get((quizList.size() - 1) - randy.nextInt(2)));
						QuizMining quiz = qLog.getQuiz();
						
						
						mult = (int)(quiz.getTimeclose() - quiz.getTimeopen()) / Integer.valueOf(cou.getShortname()) ;
						time = (int) quiz.getTimeopen() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
						qLog.setTimestamp(time);
						
						for(int h = 0; h < courseUserList.size(); h++)
							if(qLog.getUser() == (courseUserList.get(h).getUser()) && courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix() +"" + 2) && quiz.getMaxgrade() > 0)
							{
								qLog.setGrade(quiz.getMaxgrade() - randy.nextInt(quiz.getMaxgrade().intValue()));
								qLog.setAction("report");
								
							}
						if(qLog.getAction() == null)
							qLog.setAction("attempt");
			
						qLog.setPlatform(platform.getId());
						
						quizLogList.add(qLog);
						
						//_________________QuestionLogs___________________________________________________
						
						QuestionLogMining queLog = new QuestionLogMining();
						queLog.setCourse(cou);
						queLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(10)) % userList.size()));
						queLog.setQuiz(quizList.get((quizList.size() - 1) - randy.nextInt(2)));
						queLog.setQuestion(questionList.get((questionList.size() - 1) - randy.nextInt(10)));
						quiz = queLog.getQuiz();
						
						mult = (int)(quiz.getTimeclose() - quiz.getTimeopen()) / Integer.valueOf(cou.getShortname()) ;
						time = (int) quiz.getTimeopen() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
						queLog.setTimestamp(time);
						
						for(int h = 0; h < courseUserList.size(); h++)
							if(qLog.getUser() == (courseUserList.get(h).getUser()) && courseUserList.get(h).getRole().getId() == Long.valueOf(platform.getPrefix() +"" + 2) && quiz.getMaxgrade() > 0)
							{
								int qNumber = 10;
								if(quiz.getMaxgrade() < qNumber)
									queLog.setRawgrade(randy.nextDouble() * quiz.getMaxgrade() / qNumber);
								queLog.setFinalgrade(queLog.getRawgrade() - randy.nextDouble() * queLog.getRawgrade());
								queLog.setPenalty(queLog.getRawgrade() - queLog.getFinalgrade());
								queLog.setAction("CLOSE");
								
							}
						if(queLog.getAction() == null)
							queLog.setAction("OPEN");
						queLog.setAnswers("Answers for " + queLog.getQuestion().getTitle());
						queLog.setType("text");
			
						queLog.setPlatform(platform.getId());
						
						questionLogList.add(queLog);
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
		
		
		ArrayList<ILogMining> allLogs = new ArrayList<ILogMining>();
		allLogs.addAll(courseLogList);
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
		
		//Create and save config-object
		ConfigMining config = new ConfigMining();
	    config.setLastmodified(System.currentTimeMillis());
	    config.setElapsed_time(1);	
	    config.setDatabaseModel("1.2");
	    config.setPlatform(platform.getId());
	    config.setExtractcycle(1);
	    
	    ArrayList<ConfigMining> confList = new ArrayList<ConfigMining>();
	    confList.add(config);
	    
	    all.add(confList);
	
		
		return all;
	}
	
}
