package de.lemo.dms.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.lemo.dms.db.miningDBclass.*;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

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
	
	
	public  List<Collection<?>> generateMiningDB(Integer departments, Integer degreesPerDepartment, Integer coursesPerDegree, Long startdate, int logsPerLearnObject)
	{
		
		system = ESystem.moodle;
		
		List<Collection<?>> all = new ArrayList<Collection<?>>();
		
		//Object-containers
		ArrayList<DepartmentMining> departmentList = new ArrayList<DepartmentMining>();
		ArrayList<DegreeMining> degreeList = new ArrayList<DegreeMining>();
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
		
		//Association-containers
		ArrayList<DegreeCourseMining> degreeCourseList = new ArrayList<DegreeCourseMining>();
		ArrayList<DepartmentDegreeMining> departmentDegreeList = new ArrayList<DepartmentDegreeMining>();
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
		
		
		ArrayList<ArrayList<ILogMining>> logList = new ArrayList<ArrayList<ILogMining>>();
		
		
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
		
		
		//Create users
		for(int i = 0; i < 100; i++)
		{
			UserMining user = new UserMining();
			user.setId(userList.size() + 1);
			user.setFirstaccess(startdate + randy.nextInt(4 * week));
			user.setLastlogin(year - week - randy.nextInt(month));
			user.setLastaccess(user.getLastlogin() + randy.nextInt(day));
			user.setCurrentlogin(user.getLastaccess() + randy.nextInt(week));
			
			userList.add(user);
		}
		
		//Create roles

		RoleMining r0 = new RoleMining();
		r0.setId(roleList.size() +1);
		r0.setName("Administrator");
		r0.setShortname("Admin");
		r0.setDescription("Administatoren dürfen alles.");			
		r0.setSortorder(0);
		
		roleList.add(r0);
		
		RoleMining r1 = new RoleMining();
		r1.setId(roleList.size() +1);
		r1.setName("Dozent");
		r1.setShortname("Doz");
		r1.setDescription("Dozenten dürfen in ihren Kursen alles.");			
		r1.setSortorder(1);
		
		roleList.add(r1);
		
		RoleMining r2 = new RoleMining();
		r2.setId(roleList.size() +1);
		r2.setName("Student");
		r2.setShortname("Stud");
		r2.setDescription("Studenten dürfen alles lernen.");			
		r2.setSortorder(2);
		
		roleList.add(r2);
			
		//Create groups
		for(int i = 0; i < coursesPerDegree * degreesPerDepartment * departments; i++)
		{
			GroupMining gr = new GroupMining();
			gr.setId(groupList.size() + 1);
			
			if(i % 2 == 0)
				offset = 15768000;
			else
				offset = 0;
			
			gr.setTimecreated(startdate + offset + randy.nextInt(week));
			gr.setTimemodified(gr.getTimecreated() + randy.nextInt(month));
			
			groupList.add(gr);
			
			for(int j = i * 5; j < i * 5 + 10; j ++)
			{
				GroupUserMining gu = new GroupUserMining();
				gu.setId(groupUserList.size() + 1);
				gu.setUser(userList.get(j % userList.size()));
				gu.setGroup(gr);
				
				groupUserList.add(gu);
			}
		}
			
		
		for(int i = 1; i <= departments; i++)
		{
			//Create departments
			DepartmentMining dep = new DepartmentMining();
			dep.setId(i);
			dep.setTitle("Department "+i);
			departmentList.add(dep);
			for(int j = 1 ; j <= degreesPerDepartment; j++)
			{
				//Create degrees
				DegreeMining deg = new DegreeMining();
				deg.setId(degreeList.size()+1);
				deg.setTitle("Degree "+i+"."+j);
				degreeList.add(deg);
				
				DepartmentDegreeMining depDeg = new DepartmentDegreeMining();
				depDeg.setDegree(deg);
				depDeg.setDepartment(dep);
				depDeg.setId(departmentDegreeList.size()+1);
				
				departmentDegreeList.add(depDeg);
				
				for(int k = 1 ; k <= coursesPerDegree ; k++)
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
					cou.setId(courseList.size()+1);
					
					DegreeCourseMining degCou = new DegreeCourseMining();
					degCou.setDegree(deg);
					degCou.setCourse(cou);
					degCou.setId(degreeCourseList.size()+1);
					
					
					courseList.add(cou);
					degreeCourseList.add(degCou);
					
					//Associate users with course
					for(int l = 0; l < 10; l ++)
					{
						CourseUserMining cu = new CourseUserMining();
						cu.setId(courseUserList.size() + 1);
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
						
						courseUserList.add(cu);
					}
					
					CourseGroupMining cg = new CourseGroupMining();
					cg.setId(courseGroupList.size() + 1);
					cg.setCourse(cou);
					cg.setGroup(groupList.get(courseList.size() -1 ));
					
					courseGroupList.add(cg);
					
					//Now create LearningObjects
					//Create resources
					int pt = (randy.nextInt(6)+ 1) * 15;
					for(int l = 1; l < 11; l++)
					{
						ResourceMining r = new ResourceMining();
						r.setId(resourceList.size() + 1);
						r.setDifficulty("Level " + randy.nextInt(10));
						r.setPosition(l);
						r.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						r.setTimemodified(r.getTimecreated() + randy.nextInt(year/2));
						r.setTitle("Resource "+i+"."+j+"."+k+"."+l);
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
						couRes.setId(courseResourceList.size()+1);
						couRes.setCourse(cou);
						couRes.setResource(r);
						
						resourceList.add(r);
						courseResourceList.add(couRes);
					}
					//Create wikis
					for(int l = 1; l < 3; l++)
					{
						WikiMining w = new WikiMining();
						w.setId(wikiList.size() + 1);
						w.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						w.setTimemodified(w.getTimecreated() + randy.nextInt(year/2));
						w.setTitle("Wiki "+i+"."+j+"."+k+"."+l);
						w.setSummary("This is wiki " + i + "." + j + "." + k + "." + l + "'s summary.");
						
						CourseWikiMining couWik = new CourseWikiMining();
						couWik.setId(courseWikiList.size()+1);
						couWik.setCourse(cou);
						couWik.setWiki(w);
						
						wikiList.add(w);
						courseWikiList.add(couWik);
					}
					//Create Assignments
					for(int l = 1; l < 3; l++)
					{
						AssignmentMining a = new AssignmentMining();
						a.setId(assignmentList.size() + 1);
						a.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						a.setTimemodified(a.getTimecreated() + randy.nextInt(year/2));
						a.setTitle("Assignment "+i+"."+j+"."+k+"."+l);
						a.setMaxgrade(Double.parseDouble("" + (randy.nextInt(19) + 1)) * 5);
						a.setTimeopen(a.getTimecreated() + randy.nextInt(month));
						a.setTimeclose(a.getTimeopen() + 2 * week);
						a.setType("Assignment");
						
						CourseAssignmentMining couAss = new CourseAssignmentMining();
						couAss.setId(courseAssignmentList.size()+1);
						couAss.setCourse(cou);
						couAss.setAssignment(a);
						
						assignmentList.add(a);
						courseAssignmentList.add(couAss);
					}
					//Create Forums
					for(int l = 1; l < 3; l++)
					{
						ForumMining f = new ForumMining();
						f.setId(forumList.size() + 1);
						f.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						f.setTimemodified(f.getTimecreated() + randy.nextInt(year/2));
						f.setTitle("Forum "+i+"."+j+"."+k+"."+l);
						f.setSummary("Forum " + i + "." + j + "." + k + "." + l + "'s summary...");
						
						CourseForumMining couFor = new CourseForumMining();
						couFor.setId(courseForumList.size()+1);
						couFor.setCourse(cou);
						couFor.setForum(f);
						
						forumList.add(f);
						courseForumList.add(couFor);
					}
					//Create Quizzes
					for(int l = 1; l < 3; l++)
					{
						QuizMining q = new QuizMining();
						q.setId(quizList.size() + 1);
						q.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						q.setTimemodified(q.getTimecreated() + randy.nextInt(year/2));
						q.setTitle("Quiz "+i+"."+j+"."+k+"."+l);
						q.setMaxgrade(Double.parseDouble("" + (randy.nextInt(19) + 1)) * 5);
						q.setQtype("QuizType " + randy.nextInt(10));
						q.setTimeopen(q.getTimecreated() + randy.nextInt(month));
						q.setTimeclose(q.getTimeopen() + 2 * week);
						
						CourseQuizMining couQui = new CourseQuizMining();
						couQui.setId(courseQuizList.size()+1);
						couQui.setCourse(cou);
						couQui.setQuiz(q);
						
						quizList.add(q);
						courseQuizList.add(couQui);
						
						for(int m  = 1; m < 11; m++)
						{
							QuestionMining que = new QuestionMining();
							que.setId(questionList.size() + 1);
							que.setTimecreated(q.getTimecreated() + randy.nextInt(week));
							que.setTimemodified(que.getTimecreated() + randy.nextInt(year/2));
							
							que.setTitle("Question " + i + "." + j + "." + k + "." + l + "." + m);
							que.setText("This is the text for " + que.getTitle() + " of quiz " + q.getTitle() );
							que.setType(EQuestionType.values()[randy.nextInt(EQuestionType.values().length-1)].toString());
							
							QuizQuestionMining qqm = new QuizQuestionMining();
							qqm.setId(quizQuestionList.size() + 1);
							qqm.setQuiz(q);
							qqm.setQuestion(que);
							
							questionList.add(que);
							quizQuestionList.add(qqm);
						}
						for(int m = 9; m >= 1; m--)
						{
							QuizUserMining qu = new QuizUserMining();
							qu.setId(quizUserList.size() + 1);
							qu.setQuiz(q);
							qu.setCourse(cou);
							qu.setRawgrade(randy.nextInt((int)q.getMaxgrade()));
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
						c.setId(forumList.size() + 1);
						c.setCourse(cou);
						c.setTitle("Chat "+i+"."+j+"."+k+"."+l);
						c.setDescription("Chat " + i + "." + j + "." + k + "." + l + "'s description...");
						c.setChattime(cou.getTimecreated() + randy.nextInt(week));
						
						
						chatList.add(c);
					}
					//Create scorms
					for(int l = 1; l < 3; l++)
					{
						ScormMining sco = new ScormMining();
						sco.setId(scormList.size() + 1);
						sco.setMaxgrade(Double.parseDouble("" + (randy.nextInt(19) + 1)) * 5);
						sco.setTimecreated(cou.getTimecreated() + randy.nextInt(week));
						sco.setTimemodified(sco.getTimecreated() + randy.nextInt(year/2));
						sco.setTimeopen(sco.getTimecreated() + randy.nextInt(month));
						sco.setTimeclose(sco.getTimeopen() + 2 * week);
						sco.setTitle("Scorm " + i + "." + j + "."  + k + "." + l);
						sco.setType("Scormtype");
						
						CourseScormMining cs = new CourseScormMining();
						cs.setId(courseScormList.size() + 1);
						cs.setCourse(cou);
						cs.setScorm(sco);
						
						scormList.add(sco);
						courseScormList.add(cs);
					}
					
					//Create log-entries
					//Create AssignmentLogs
					for(int log = 0; log < logsPerLearnObject; log++)
					{
						ResourceLogMining rLog = new ResourceLogMining();
						//rLog.setId(resourceLogList.size() + 1);
						rLog.setCourse(cou);
						rLog.setResource(resourceList.get((resourceList.size() - 1) - randy.nextInt(10)));
						rLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(10)) % userList.size()));
						ResourceMining r = rLog.getResource();
						
						int mult = (int)(startdate + year - r.getTimecreated()) / Integer.valueOf(cou.getShortname()) ;
						int time = (int)r.getTimecreated() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
						rLog.setTimestamp(time);
						
						rLog.setAction("view");
						
						resourceLogList.add(rLog);
						
						//_________________AssignmentLogs___________________________________________________
						
						AssignmentLogMining aLog = new AssignmentLogMining();
						aLog.setId(assignmentLogList.size());
						aLog.setCourse(cou);
						aLog.setAssignment(assignmentList.get((assignmentList.size() - 1) - randy.nextInt(2)));
						aLog.setUser(userList.get(( (courseList.size() -1) * 5 + randy.nextInt(10)) % userList.size()));
						AssignmentMining a = aLog.getAssignment();
						
						mult = (int)(a.getTimeclose() - a.getTimeopen()) / Integer.valueOf(cou.getShortname()) ;
						time = (int)a.getTimeopen() + (randy.nextInt(mult) * Integer.valueOf(cou.getShortname()));
						aLog.setTimestamp(time);
						
						for(int h = 0; h < courseUserList.size(); h++)
							if(aLog.getUser() == (courseUserList.get(h).getUser()) && courseUserList.get(h).getRole().getId() == 2 && a.getMaxgrade() > 0)
							{
								aLog.setGrade(a.getMaxgrade() - randy.nextInt((int)a.getMaxgrade()));
								aLog.setFinalgrade(aLog.getGrade() - randy.nextInt((int)aLog.getGrade()));
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
							if(sLog.getUser() == (courseUserList.get(h).getUser()) && courseUserList.get(h).getRole().getId() == 2 && scorm.getMaxgrade() > 0)
							{
								sLog.setGrade(scorm.getMaxgrade() - randy.nextInt((int)scorm.getMaxgrade()));
								sLog.setFinalgrade(aLog.getGrade() - randy.nextInt((int)sLog.getGrade()));
								sLog.setAction("report");
								
							}
						if(sLog.getAction() == null)
							sLog.setAction("view");
						
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
							if(qLog.getUser() == (courseUserList.get(h).getUser()) && courseUserList.get(h).getRole().getId() == 2 && quiz.getMaxgrade() > 0)
							{
								qLog.setGrade(quiz.getMaxgrade() - randy.nextInt((int)quiz.getMaxgrade()));
								qLog.setFinalgrade(qLog.getGrade() - randy.nextInt((int)qLog.getGrade()));
								qLog.setAction("report");
								
							}
						if(qLog.getAction() == null)
							qLog.setAction("attempt");
						
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
							if(qLog.getUser() == (courseUserList.get(h).getUser()) && courseUserList.get(h).getRole().getId() == 2 && quiz.getMaxgrade() > 0)
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
					
						
						questionLogList.add(queLog);
					}
					
				}
			}
		}
		
		all.add(userList);
		all.add(roleList);
		all.add(groupList);		
		all.add(departmentList);
		all.add(degreeList);
		all.add(courseList);
		all.add(resourceList);
		all.add(forumList);
		all.add(questionList);
		all.add(quizList);
		all.add(chatList);
		all.add(scormList);
		all.add(assignmentList);
			
		all.add(groupUserList);
		all.add(courseUserList);
		all.add(departmentDegreeList);
		all.add(degreeCourseList);
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
		
		return all;
	}
	
}
