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
import de.lemo.dms.db.mapping.Attribute;
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.Config;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseAttribute;
import de.lemo.dms.db.mapping.CourseLearning;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningAttribute;
import de.lemo.dms.db.mapping.LearningObj;
import de.lemo.dms.db.mapping.AccessLog;
import de.lemo.dms.db.mapping.LearningType;
import de.lemo.dms.db.mapping.Role;
import de.lemo.dms.db.mapping.AssessmentLog;
import de.lemo.dms.db.mapping.UserAssessment;
import de.lemo.dms.db.mapping.User;
import de.lemo.dms.db.mapping.UserAttribute;
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


	
	enum EResourceType {
		Document,
		Video,
		Url,
	}
	
	enum EUserName {
		Sabine,
		Albrecht,
		Liane,
		Marcus,
		Agathe,
		Sebastian,
		Margarita,
		Leonard,
		Mareike,
		Andreas,
		Petra,
		Boris,
		


	}

	
	enum EAccessActionType {
		Play,
		Stop,
		Pause,
	}
	
	enum ECollaborationActionType {
		Post,
		Answer,
		View,
	}
	
	enum EAssessmentActionType {
		Submit,
		Try,
		View,
	}
	
	enum ECollaborationType {
		Wiki,
		Forum,
		Chat,
	}
	
	enum EAssessmentType {
		Assignment,
		Test,
		Feedback,
	}

	
	enum EQuestionType {
		Pathetic,
		Easy,
		Mediocre,
		Tricky
	}
	
	enum ESystem {
		fiz,
		moodle,
		clix
	}
	
	public List<Collection<?>> generateMiningDB(final Integer coursesPerLevel, final Integer users, final Long startdate, final int logsPerLearnObject)
	{


		final List<Collection<?>> all = new ArrayList<Collection<?>>();

		// Object-containers
		final ArrayList<Course> courseList = new ArrayList<Course>();
		final ArrayList<LearningObj> learningObjects = new ArrayList<LearningObj>();
		final ArrayList<LearningObj> collaborativeObjects = new ArrayList<LearningObj>();
		final ArrayList<LearningObj> assessmentObjects = new ArrayList<LearningObj>();
		final ArrayList<User> userList = new ArrayList<User>();
		final ArrayList<Role> roleList = new ArrayList<Role>();
		final ArrayList<LearningType> loTypes = new ArrayList<LearningType>();
		final ArrayList<LearningType> collTypes = new ArrayList<LearningType>();
		final ArrayList<LearningType> asseTypes = new ArrayList<LearningType>();
		final ArrayList<Attribute> attributes = new ArrayList<Attribute>(); 
		final ArrayList<LearningAttribute> learnAttributes = new ArrayList<LearningAttribute>(); 
		final ArrayList<CourseAttribute> courseAttributes = new ArrayList<CourseAttribute>(); 
		final ArrayList<UserAttribute> userAttributes = new ArrayList<UserAttribute>(); 
		
		// Association-containers
		final ArrayList<CourseLearning> courseLearningObjects = new ArrayList<CourseLearning>();

		final ArrayList<CourseUser> courseUserList = new ArrayList<CourseUser>();

		// Log-containers
		final ArrayList<AccessLog> learnLog = new ArrayList<AccessLog>();
		final ArrayList<ILog> resourceLogList = new ArrayList<ILog>();
		final ArrayList<CollaborationLog> collLog = new ArrayList<CollaborationLog>();
		final ArrayList<AssessmentLog> taskLogs = new ArrayList<AssessmentLog>();
		
		final Map<String, UserAssessment> taskUserMap = new HashMap<String, UserAssessment>();
		final Map<Long, Long> assMG = new HashMap<Long, Long>();
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
		Long id = 0L;

		final Random randy = new Random(15768000);

		int offset;
		final int year = 31536000;

		final Date dt = new Date();
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Attribute attMaxGrade = new Attribute();
		attMaxGrade.setId(0);
		attMaxGrade.setName("MaxGrade");
		
		attributes.add(attMaxGrade);
		
		Attribute attUserName = new Attribute();
		attUserName.setId(1);
		attUserName.setName("User Name");
		
		attributes.add(attUserName);
		
		Attribute attUserGender = new Attribute();
		attUserGender.setId(3);
		attUserGender.setName("User Gender");
		
		attributes.add(attUserGender);
		
		Attribute attCourseDescription = new Attribute();
		attCourseDescription.setId(2);
		attCourseDescription.setName("Course Description");
		
		attributes.add(attCourseDescription);

		// Create users
		for (int i = 0; i < users; i++)
		{
			final User user = new User();
			user.setId(userList.size() + 1);
			user.setLogin(Encoder.createMD5(i + " " + df.format(dt)));

			UserAttribute ua = new UserAttribute();
			ua.setId(userAttributes.size());
			ua.setAttribute(attUserName);
			ua.setValue(EUserName.values()[i % EUserName.values().length].toString());
			ua.setUser(user);
			
			userAttributes.add(ua);
			
			UserAttribute ug = new UserAttribute();
			ug.setId(userAttributes.size());
			ug.setAttribute(attUserGender);
			ug.setValue((i % 2) + 1 +"");
			ug.setUser(user);
			
			userAttributes.add(ug);
			
			userList.add(user);
		}

		// Create roles

		final Role r0 = new Role();
		r0.setId(roleList.size() + 1);
		r0.setTitle("Administrator");
		r0.setSortOrder(0);
		r0.setType(0);
		
		roleList.add(r0);

		final Role r1 = new Role();
		r1.setId(roleList.size() + 1);
		r1.setTitle("Dozent");
		r1.setSortOrder(1);
		r1.setType(1);
		
		roleList.add(r1);

		final Role r2 = new Role();
		r2.setId(roleList.size() + 1);
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
		}
		for(int i = 0; i < 3 ; i++)
		{
			LearningType t = new LearningType();
			t.setId(i + 3);
			t.setType(ECollaborationType.values()[i].toString());
			collTypes.add(t);
		}
		for(int i = 0; i < 3 ; i++)
		{
			LearningType t = new LearningType();
			t.setId(i + 6);
			t.setType(EAssessmentType.values()[i].toString());
			asseTypes.add(t);
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

			final int ts = (int) (startdate + offset);

			cou.setTitle("Course " + k);
			cou.setId(courseList.size() + 1);
			
			CourseAttribute ca = new CourseAttribute();
			ca.setId(cou.getId());
			ca.setAttribute(attCourseDescription);
			ca.setValue("This course is about the number " + k + ".");
			ca.setCourse(cou);
			
			courseAttributes.add(ca);

			courseList.add(cou);
			
			// Associate users with course
			final int userSwitch = 25 + (((k - 1) % MAGIC_THREE) * 25);
			for (int l = 0; l < userSwitch; l++)
			{
				final CourseUser cu = new CourseUser();
				cu.setId(courseUserList.size() + 1);
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
				r.setId(id);
				id++;
				r.setTitle("LearningObj " + loTypes.get(l % 3).getType() +" "+ k + "." + l);
				r.setType(loTypes.get(l % 3));
				r.setInteractionType("ACCESS");
				
				final CourseLearning couRes = new CourseLearning();
				couRes.setId(courseLearningObjects.size() + 1);
				couRes.setCourse(cou);
				couRes.setLearning(r);
				
				if(l % 3 == 0)
					r.setParent(learningObjects.get(learningObjects.size() - 1));

				learningObjects.add(r);
				courseLearningObjects.add(couRes);
			}
			// Create collaboratives
			for (int l = 1; l <=  MAGIC_FIVE; l++)
			{
				final LearningObj w = new LearningObj();
				w.setId(id);
				id++;
				w.setType(collTypes.get(l % 3));
				w.setInteractionType("COLLABORATION");
				w.setTitle("Collaborative " + collTypes.get(l % 3).getType() +" " + k + "." + l);
				final CourseLearning couWik = new CourseLearning();
				couWik.setId(courseLearningObjects.size() + 1);
				couWik.setCourse(cou);
				couWik.setLearning(w);

				if(l % 3 == 0)
					w.setParent(learningObjects.get(learningObjects.size() - 1));
				
				collaborativeObjects.add(w);
				courseLearningObjects.add(couWik);
			}
			// Create tasks
			for (int l = 1; l <= MAGIC_FIVE; l++)
			{
				final LearningObj a = new LearningObj();
				a.setId(id);
				id++;
				a.setTitle("Assessment " + asseTypes.get(l % 3).getType() + " " + k + "." + l);
				a.setType(asseTypes.get(l % 3));
				a.setInteractionType("ASSESSMENT");
				final CourseLearning couAss = new CourseLearning();
				couAss.setId(courseLearningObjects.size() + 1);
				couAss.setCourse(cou);
				couAss.setLearning(a);
				if(l % 3 == 0)
					a.setParent(learningObjects.get(learningObjects.size() - 1));
				LearningAttribute la = new LearningAttribute();
				la.setId(learnAttributes.size() + 1);
				la.setAttribute(attMaxGrade);
				la.setLearning(a);
				la.setValue((randy.nextInt(3) + 10 ) * 10L +"");
				
				learnAttributes.add(la);
				assMG.put(a.getId(), Long.valueOf(la.getValue()));

				assessmentObjects.add(a);
				courseLearningObjects.add(couAss);
			}
			
			// Create log-entries
			// Create AssignmentLogs
			final int logSwitch = logsPerLearnObject + ((((k - 1) / 9) % MAGIC_THREE) * logsPerLearnObject);
			for (int log = 0; log < logSwitch; log++)
			{

				
				// _________________LearningObjectLogs___________________________________________________
				int time = (int) (startdate + log * (year / logSwitch));
				final AccessLog rLog = new AccessLog();
				rLog.setCourse(cou);
				rLog.setLearning(learningObjects.get((learningObjects.size() - 1) - randy.nextInt(MAGIC_TEN)));
				rLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(userSwitch))
						% userList.size()));
				rLog.setDuration(Long.valueOf(randy.nextInt(350) + 2));
				rLog.setAction(EAccessActionType.values()[time % 3].toString());
				time = (int) (startdate
					+ (randy.nextInt(year) ));
				
				rLog.setTimestamp(Long.valueOf(time));
				if(rLog.getTimestamp() > maxLog)
				{
					maxLog = rLog.getTimestamp();
				}

				learnLog.add(rLog);
				
				// _________________TaskLogs___________________________________________________


					
				final AssessmentLog aLog = new AssessmentLog();
				aLog.setCourse(cou);
				aLog.setLearning(assessmentObjects.get((assessmentObjects.size() - 1) - randy.nextInt(MAGIC_FIVE)));
				aLog.setUser(userList.get((((courseList.size() - 1) * MAGIC_FIVE) + randy.nextInt(MAGIC_TEN)) % userList.size()));
				aLog.setAction(EAssessmentActionType.values()[time % 3].toString());
				final LearningObj a = aLog.getLearning();
				aLog.setDuration(Long.valueOf(randy.nextInt(350) + 2));
				time = (int) (startdate
						+ (randy.nextInt(year) ));
				aLog.setTimestamp(Long.valueOf(time));
				if(aLog.getAction().equals("Try"))
					aLog.setText("Generated assessment message");
				
				if(!taskUserMap.containsKey(aLog.getUser().getId() + "#" + aLog.getLearning().getId()) && aLog.getAction().equals("Submit"))
				{
					UserAssessment tu = new UserAssessment();
					tu.setId(taskUserMap.size() + 1);
					tu.setLearning(a);
					tu.setUser(aLog.getUser());
					tu.setCourse(cou);
					tu.setTimemodified(time);
					tu.setFeedback("Generated feedback message");
					tu.setGrade(Double.valueOf(randy.nextInt(assMG.get(a.getId()).intValue())));
					
					taskUserMap.put(aLog.getUser().getId() + "#" + aLog.getLearning().getId(), tu);
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
				cLog.setLearning(collaborativeObjects.get((collaborativeObjects.size() - 1) - randy.nextInt(MAGIC_FIVE)));
				cLog.setAction(ECollaborationActionType.values()[time % 3].toString());
			
				time = (int) (startdate
						+ (randy.nextInt(year) ));
				cLog.setTimestamp(Long.valueOf(time));
				

				
				if(cLog.getTimestamp() > maxLog)
				{
					maxLog = cLog.getTimestamp();
				}
				cLog.setText("Generated collaborative message @ "
						+ cLog.getTimestamp());

				collLog.add(cLog);
			}
		}
		
		learningObjects.addAll(collaborativeObjects);
		learningObjects.addAll(assessmentObjects);
		loTypes.addAll(collTypes);
		loTypes.addAll(asseTypes);

		all.add(userList);

		all.add(roleList);
		all.add(loTypes);
		all.add(attributes);
		
		all.add(courseList);
		all.add(learningObjects);

		all.add(courseUserList);

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
		all.add(learnAttributes);
		all.add(courseAttributes);
		all.add(userAttributes);


		// Create and save config-object
		final Config config = new Config();
		config.setLastModifiedLong(System.currentTimeMillis());
		config.setElapsedTime(1);
		config.setDatabaseModel("2.0");
		config.setExtractCycle(1);
		config.setLatestTimestamp(maxLog);

		final ArrayList<Config> confList = new ArrayList<Config>();
		confList.add(config);

		all.add(confList);

		return all;
	}
	
	private void createIdsLL(List<AccessLog> logs)
	{
		int i = 1;
		Collections.sort(logs);
		for(ILog il : logs)
		{
			il.setId(i);
			i++;
		}
	}
	
	private void createIdsCL(List<CollaborationLog> logs)
	{
		int i = 1;
		Collections.sort(logs);
		for(ILog il : logs)
		{
			il.setId(i);
			i++;
		}
		
		
		for(int j = 0; j < logs.size(); j++)
		{
		//	if(logs.get(j).getAction().equals("Answer") && logs.size() > 0)
			//	logs.get(j).setReferrer(logs.get(logs.size() - 1));
		}
	}
	
	private void createIdsTL(List<AssessmentLog> logs)
	{
		int i = 1;
		Collections.sort(logs);
		for(ILog il : logs)
		{
			il.setId(i);
			i++;
		}
	}
	

}
