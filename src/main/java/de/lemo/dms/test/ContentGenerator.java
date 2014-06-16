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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.db.mapping.CollaborationObj;
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.CollaborationType;
import de.lemo.dms.db.mapping.Config;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseCollaboration;
import de.lemo.dms.db.mapping.CourseLearning;
import de.lemo.dms.db.mapping.CourseAssessment;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningObj;
import de.lemo.dms.db.mapping.LearningLog;
import de.lemo.dms.db.mapping.LearningType;
import de.lemo.dms.db.mapping.Platform;
import de.lemo.dms.db.mapping.Role;
import de.lemo.dms.db.mapping.Assessment;
import de.lemo.dms.db.mapping.AssessmentLog;
import de.lemo.dms.db.mapping.AssessmentType;
import de.lemo.dms.db.mapping.AssessmentUser;
import de.lemo.dms.db.mapping.User;
import de.lemo.dms.db.mapping.abstractions.ILog;

/**
 * Generates data for an test db system
 * @author Sebastian Schwarzrock
 *
 */
public class ContentGenerator {

	private static final int MAGIC_THREE = 3;
	private static final int MAGIC_FIVE = 5;
	private static final int MAGIC_TEN = 10;
	private Long maxLog = 0L;

	/**
	 * Enum for resource types
	 * @author Sebastian Schwarzrock
	 *
	 */
	
	
	enum EResourceType {
		Document,
		Video,
		Url,
	}
	
	enum ETaskType {
		Assignment,
		Test,
		Feedback,
	}
	
	enum ECollType {
		Wiki,
		Forum,
		Chat,
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
		Tricky
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
	
	public List<Collection<?>> generateMiningDB(final Integer coursesPerLevel, final Integer users, final Long startdate, final int logsPerLearnObject)
	{


		final List<Collection<?>> all = new ArrayList<Collection<?>>();

		// Object-containers
		final ArrayList<Course> courseList = new ArrayList<Course>();
		final ArrayList<LearningObj> learningObjects = new ArrayList<LearningObj>();
		final ArrayList<CollaborationObj> collaborativeObjects = new ArrayList<CollaborationObj>();
		final ArrayList<Assessment> tasks = new ArrayList<Assessment>();
		final ArrayList<User> userList = new ArrayList<User>();
		final ArrayList<Role> roleList = new ArrayList<Role>();
		final ArrayList<Platform> platformList = new ArrayList<Platform>();
		final ArrayList<LearningType> loTypes = new ArrayList<LearningType>();
		final ArrayList<CollaborationType> collTypes = new ArrayList<CollaborationType>();
		final ArrayList<AssessmentType> taskTypes = new ArrayList<AssessmentType>();

		// Association-containers
		final ArrayList<CourseLearning> courseLearningObjects = new ArrayList<CourseLearning>();
		final ArrayList<CourseCollaboration> courseCollaborativeObjects = new ArrayList<CourseCollaboration>();
		final ArrayList<CourseAssessment> courseTasks = new ArrayList<CourseAssessment>();

		final ArrayList<CourseUser> courseUserList = new ArrayList<CourseUser>();

		// Log-containers
		final ArrayList<LearningLog> learnLog = new ArrayList<LearningLog>();
		final ArrayList<ILog> resourceLogList = new ArrayList<ILog>();
		final ArrayList<CollaborationLog> collLog = new ArrayList<CollaborationLog>();
		final ArrayList<AssessmentLog> taskLogs = new ArrayList<AssessmentLog>();
		
		final Map<String, AssessmentUser> taskUserMap = new HashMap<String, AssessmentUser>();

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

		final Date dt = new Date();
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		final Platform platform = new Platform();
		platform.setId(1L);
		platform.setTitle("ContentGenerator @ " + df.format(dt));
		platform.setPrefix(22L);
		platform.setType("ContentGenerator");

		platformList.add(platform);

		// Create users
		for (int i = 0; i < users; i++)
		{
			final User user = new User();
			user.setId(Long.valueOf(platform.getPrefix() + "" + (userList.size() + 1)));
			user.setGender((i % 2) + 1);
			user.setLogin(Encoder.createMD5(i + " " + df.format(dt)));

			userList.add(user);
		}

		// Create roles

		final Role r0 = new Role();
		r0.setId(Long.valueOf(platform.getPrefix() + "" + (roleList.size() + 1)));
		r0.setTitle("Administrator");
		r0.setSortOrder(0);
		r0.setType(0);
		
		roleList.add(r0);

		final Role r1 = new Role();
		r1.setId(Long.valueOf(platform.getPrefix() + "" + (roleList.size() + 1)));
		r1.setTitle("Dozent");
		r1.setSortOrder(1);
		r1.setType(1);
		
		roleList.add(r1);

		final Role r2 = new Role();
		r2.setId(Long.valueOf(platform.getPrefix() + "" + (roleList.size() + 1)));
		r2.setTitle("Student");
		r2.setSortOrder(2);
		r2.setType(2);
		
		roleList.add(r2);
		
		for(int i = 0; i < 3 ; i++)
		{
			LearningType t = new LearningType();
			t.setId(i);
			t.setType(EResourceType.values()[i].toString());
			loTypes.add(t);
			
			CollaborationType c = new CollaborationType();
			c.setId(i);
			c.setType(ECollType.values()[i].toString());
			collTypes.add(c);
			
			AssessmentType tt = new AssessmentType();
			tt.setId(i);
			tt.setType(ECollType.values()[i].toString());
			taskTypes.add(tt);
			

		}
		
		for (int k = 1; k <= coursesPerLevel; k++)
		{
		// Create courses
			if ((k % 2) == 0) {
				offset = 15768000;
			} else {
				offset = 0;
			}

			final Course cou = new Course();
			cou.setTitle("Course " + k);
			cou.setPlatform(platform);

			final int ts = (int) (startdate + offset);

			cou.setTimeModified(ts + randy.nextInt(year / 2));
			cou.setTitle("Course " + k);
			cou.setId(Long.valueOf(platform.getPrefix() + "" + (courseList.size() + 1)));

			courseList.add(cou);
			
			// Associate users with course
			final int userSwitch = 25 + (((k - 1) % MAGIC_THREE) * 25);
			for (int l = 0; l < userSwitch; l++)
			{
				final CourseUser cu = new CourseUser();
				cu.setId(Long.valueOf(platform.getPrefix() + "" + (courseUserList.size() + 1)));
				if (l == 0) {
					cu.setRole(roleList.get(0));
				} else if (l == 1) {
					cu.setRole(roleList.get(1));
				} else {
					cu.setRole(roleList.get(2));
				}

				cu.setCourse(cou);
				cu.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + l) % userList.size()));
				
				courseUserList.add(cu);
			}

			
			// Now create LearningObjects
			// Create resources
			for (int l = 1; l <= MAGIC_TEN; l++)
			{
				final LearningObj r = new LearningObj();
				r.setId(Long.valueOf(platform.getPrefix() + "" + (learningObjects.size() + 1)));
				r.setTitle("LearningObj " + k + "." + l);
				r.setType(loTypes.get(l % 3));
				r.setUrl("http://lemo-generated.de/" + cou.getTitle().replace(" ", "-").replace(".", "_") + "/" + r.getTitle().replace(" ", "-").replace(".", "_") + ".html");
				
				final CourseLearning couRes = new CourseLearning();
				couRes.setId(Long.valueOf(platform.getPrefix() + "" + (courseLearningObjects.size() + 1)));
				couRes.setCourse(cou);
				couRes.setLearningObject(r);

				learningObjects.add(r);
				courseLearningObjects.add(couRes);
			}
			// Create collaboratives
			for (int l = 1; l <=  MAGIC_FIVE; l++)
			{
				final CollaborationObj w = new CollaborationObj();
				w.setId(Long.valueOf(platform.getPrefix() + "" + (collaborativeObjects.size() + 1)));
				w.setType(collTypes.get(l % 3));
				w.setTitle("Collaborative " + k + "." + l);
				w.setUrl("http://lemo-generated.de/" + cou.getTitle().replace(" ", "-").replace(".", "_") + "/" + w.getTitle().replace(" ", "-").replace(".", "_") + ".html");
				final CourseCollaboration couWik = new CourseCollaboration();
				couWik.setId(Long.valueOf(platform.getPrefix() + "" + (courseCollaborativeObjects.size() + 1)));
				couWik.setCourse(cou);
				couWik.setCollaborativeObject(w);

				collaborativeObjects.add(w);
				courseCollaborativeObjects.add(couWik);
			}
			// Create tasks
			for (int l = 1; l <= MAGIC_FIVE; l++)
			{
				final Assessment a = new Assessment();
				a.setId(Long.valueOf(platform.getPrefix() + "" + (tasks.size() + 1)));
				a.setTitle("Task "  + k + "." + l);
				a.setMaxGrade(Double.parseDouble("" + (randy.nextInt(19) + 1)) * MAGIC_FIVE);
				a.setType(taskTypes.get(l % 3));
				a.setUrl("http://lemo-generated.de/" + cou.getTitle().replace(" ", "-").replace(".", "_") + "/" + a.getTitle().replace(" ", "-").replace(".", "_") + ".html");

				final CourseAssessment couAss = new CourseAssessment();
				couAss.setId(Long.valueOf(platform.getPrefix() + "" + (courseTasks.size() + 1)));
				couAss.setCourse(cou);
				couAss.setTask(a);

				tasks.add(a);
				courseTasks.add(couAss);
			}
			
			// Create log-entries
			// Create AssignmentLogs
			final int logSwitch = logsPerLearnObject + ((((k - 1) / 9) % MAGIC_THREE) * logsPerLearnObject);
			for (int log = 0; log < logSwitch; log++)
			{

				
				// _________________LearningObjectLogs___________________________________________________
				int time = (int) (startdate + log * (year / logSwitch));
				final LearningLog rLog = new LearningLog();
				rLog.setCourse(cou);
				rLog.setLearningObject(learningObjects.get((learningObjects.size() - 1) - randy.nextInt(MAGIC_TEN)));
				rLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(userSwitch))
						% userList.size()));

				time = (int) (startdate
					+ (randy.nextInt(year) ));
				
				rLog.setTimestamp(time);
				if(rLog.getTimestamp() > maxLog)
				{
					maxLog = rLog.getTimestamp();
				}

				learnLog.add(rLog);
				
				// _________________TaskLogs___________________________________________________


					
				final AssessmentLog aLog = new AssessmentLog();
				aLog.setCourse(cou);
				aLog.setAssessment(tasks.get((tasks.size() - 1) - randy.nextInt(MAGIC_FIVE)));
				aLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
				final Assessment a = aLog.getAssessment();

				time = (int) (startdate
						+ (randy.nextInt(year) ));
				aLog.setTimestamp(time);
				
				if(!taskUserMap.containsKey(aLog.getUser().getId() + "#" + aLog.getAssessment().getId()))
				{
					AssessmentUser tu = new AssessmentUser();
					tu.setId(taskUserMap.size() + 1);
					tu.setTask(a);
					tu.setUser(aLog.getUser());
					tu.setCourse(cou);
					tu.setTimemodified(time);
					tu.setGrade(randy.nextInt(((Double)a.getMaxGrade()).intValue()));
					
					taskUserMap.put(aLog.getUser().getId() + "#" + aLog.getAssessment().getId(), tu);
				}
				
				if(aLog.getTimestamp() > maxLog)
				{
					maxLog = aLog.getTimestamp();
				}

				taskLogs.add(aLog);

				// _________________CollaborativeLogs___________________________________________________
				
				final CollaborationLog cLog = new CollaborationLog();
				cLog.setCourse(cou);
				cLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
				cLog.setCollaborativeObject(collaborativeObjects.get((collaborativeObjects.size() - 1) - randy.nextInt(MAGIC_FIVE)));

				time = (int) (startdate
						+ (randy.nextInt(year) ));
				cLog.setTimestamp(time);
				if(cLog.getTimestamp() > maxLog)
				{
					maxLog = cLog.getTimestamp();
				}
				cLog.setText("Generated collaborative message @ "
						+ cLog.getTimestamp());

				collLog.add(cLog);
			}
		}

		all.add(userList);

		all.add(platformList);
		all.add(roleList);
		all.add(loTypes);
		all.add(collTypes);
		all.add(taskTypes);
		
		all.add(courseList);
		all.add(learningObjects);

		all.add(tasks);
		all.add(collaborativeObjects);


		all.add(courseUserList);

		all.add(courseTasks);
		all.add(courseCollaborativeObjects);
		all.add(courseLearningObjects);

		all.add(taskUserMap.values());
		Collections.sort(taskLogs);
		Collections.sort(learnLog);
		Collections.sort(collLog);


		final ArrayList<ILog> allLogs = new ArrayList<ILog>();
		
		createIdsLL(learnLog);
		allLogs.addAll(collLog);
		createIdsCL(collLog);
		allLogs.addAll(taskLogs);
		createIdsTL(taskLogs);


		all.add(learnLog);
		all.add(resourceLogList);
		all.add(collLog);
		all.add(taskLogs);


		// Create and save config-object
		final Config config = new Config();
		config.setLastModifiedLong(System.currentTimeMillis());
		config.setElapsedTime(1);
		config.setDatabaseModel("2.0");
		config.setPlatform(platform.getId());
		config.setExtractCycle(1);
		config.setLatestTimestamp(maxLog);

		final ArrayList<Config> confList = new ArrayList<Config>();
		confList.add(config);

		all.add(confList);

		return all;
	}
	
	private void createIdsLL(List<LearningLog> logs)
	{
		int i = 1;
		for(ILog il : logs)
		{
			il.setId(i);
			i++;
		}
	}
	
	private void createIdsCL(List<CollaborationLog> logs)
	{
		int i = 1;
		for(ILog il : logs)
		{
			il.setId(i);
			i++;
		}
	}
	
	private void createIdsTL(List<AssessmentLog> logs)
	{
		int i = 1;
		for(ILog il : logs)
		{
			il.setId(i);
			i++;
		}
	}
	

}
