/**
 * File ./src/main/java/de/lemo/dms/connectors/mooc/ExtractAndMap.java
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
 * File ./main/java/de/lemo/dms/connectors/iversity/ExtractAndMap.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.mooc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.Attribute;
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseAttribute;
import de.lemo.dms.db.mapping.CourseLearning;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningAttribute;
import de.lemo.dms.db.mapping.LearningObj;
import de.lemo.dms.db.mapping.AccessLog;
import de.lemo.dms.db.mapping.Config;
import de.lemo.dms.db.mapping.LearningType;
import de.lemo.dms.db.mapping.Role;
import de.lemo.dms.db.mapping.AssessmentLog;
import de.lemo.dms.db.mapping.UserAssessment;
import de.lemo.dms.db.mapping.User;
import de.lemo.dms.db.mapping.UserAttribute;

/**
 * The main class of the extraction process.
 * Inherit from this class to make an extract class for a specific LMS.
 * Contains bundle of fields as container for LMS objects,
 * which are used for linking the tables.
 */
public abstract class ExtractAndMap {

	// lists of object tables which are new found in LMS DB
	/** A List of new entries in the course table found in this run of the process. */

	protected Map<Long, Course> courseMining;
	protected Map<String, Attribute> attributeMining;
	protected Map<Long, LearningObj> learningObjectMining;
	protected Map<Long, User> userMining;
	protected Map<Long, Role> roleMining;	
	protected Map<Long, UserAssessment> userAssessmentMining;
	protected Map<Long, CourseLearning> courseLearningMining;
	protected Map<Long, CourseUser> courseUserMining;	
	
	protected Map<Long, CourseAttribute> courseAttributeMining;	
	protected Map<Long, UserAttribute> userAttributeMining;	
	protected Map<Long, LearningAttribute> learningAttributeMining;	
	
	protected Map<String, LearningType> learningTypeMining;	
	
	protected Map<Long, Course> oldCourseMining;
	protected Map<String, Attribute> oldAttributeMining;
	protected Map<Long, LearningObj> oldLearningObjectMining;
	protected Map<Long, User> oldUserMining;
	protected Map<Long, Role> oldRoleMining;	
	protected Map<Long, UserAssessment> oldUserAssessmentMining;
	protected Map<Long, CourseLearning> oldCourseLearningObjectMining;
	protected Map<Long, CourseUser> oldCourseUserMining;	
	
	protected Map<Long, CourseAttribute> oldCourseAttributeMining;	
	protected Map<Long, UserAttribute> oldUserAttributeMining;	
	protected Map<Long, LearningAttribute> oldLearningAttributeMining;	
	
	protected Map<String, LearningType> oldLearningTypeMining;
	



	/** A list of objects used for submitting them to the DB. */
	List<Collection<?>> updates;
	/** A list of timestamps of the previous runs of the extractor. */
	List<Timestamp> configMiningTimestamp;
	/** Designates which entries should be read from the LMS Database during the process. */
	private long starttime;

	/** Database-handler **/
	IDBHandler dbHandler;

	private final Logger logger = Logger.getLogger(this.getClass());

	private final IConnector connector;

	public ExtractAndMap(final IConnector connector) {
		this.connector = connector;
	}

	protected Long accessLogMax;

	protected Long collaborationLogMax;

	protected Long assessmentLogMax;
	
	protected Long learningObjectTypeMax;
	
	protected Long courseAttributeIdMax;
	
	protected Long userAttributeIdMax;
	
	protected Long learningAttributeIdMax;
	
	protected Long attributeIdMax;

	protected Long maxLog = 0L;
	
	protected Long userAssessmentMax = 0L;
	
	private Clock c;

	/**
	 * Starts the extraction process by calling getLMS_tables() and saveMining_tables().
	 * A timestamp can be given as optional argument.
	 * When the argument is used the extraction begins after that timestamp.
	 * When no argument is given the program starts with the timestamp of the last run.
	 * 
	 * @param args
	 *            Optional arguments for the process. Used for the selection of the ExtractAndMap Implementation and
	 *            timestamp when the extraction should start.
	 **/
	public void start(final String[] args, final DBConfigObject sourceDBConf, List<Long> courses, List<String> logins) {

		this.dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		this.c = new Clock();
		this.starttime = System.currentTimeMillis() / 1000;
		final Session session = this.dbHandler.getMiningSession();

		// get the status of the mining DB; load timestamp of last update and objects needed for associations
		long readingtimestamp = this.getMiningInitial();

		logger.info("Initialized database in " + this.c.getAndReset());
		// default call without parameter
		if (args.length == 1)
		{
			// get the needed tables from LMS DB
			this.c.reset();
			this.getLMStables(sourceDBConf, readingtimestamp, courses, logins);
			logger.info("Loaded data from source in " + this.c.getAndReset());

			// create and write the mining database tables
			this.saveMiningTables();
		}
		// call with parameter timestamp
		else {
			if (args[1].matches("[0-9]+"))
			{
				readingtimestamp = Long.parseLong(args[1]);
				long readingtimestamp2 = Long.parseLong(args[1]) + 172800;
				final long currenttimestamp = this.starttime;
				this.logger.info("starttime:" + currenttimestamp);
				this.logger.info("parameter:" + readingtimestamp);

				// first read & save LMS DB tables from 0 to starttime for timestamps which are not set(which are 0)
				if (this.configMiningTimestamp.get(0) == null) {
					this.c.reset();
					this.getLMStables(sourceDBConf, 0, readingtimestamp, courses, logins);
					logger.info("Loaded data from source in " + this.c.getAndReset());
					// create and write the mining database tables
					this.saveMiningTables();
				}

				// read & save LMS DB in steps of 2 days
				for (long looptimestamp = readingtimestamp - 1; looptimestamp < currenttimestamp;)
				{
					this.logger.info("looptimestamp:" + looptimestamp);
					this.c.reset();
					this.getLMStables(sourceDBConf, looptimestamp + 1, readingtimestamp2, courses, logins);
					logger.info("Loaded data from source in " + this.c.getAndReset());
					looptimestamp += 172800;
					readingtimestamp2 += 172800;
					this.logger.info("currenttimestamp:" + currenttimestamp);
					this.saveMiningTables();
				}
			}
			else {
				// Fehlermeldung Datenformat
				this.logger.info("wrong data format in parameter:" + args[1]);
			}
		}

		// calculate running time of extract process
		final long endtime = System.currentTimeMillis() / 1000;
		final Config config = new Config();
		config.setLastModifiedLong(System.currentTimeMillis());
		config.setElapsedTime((endtime) - (this.starttime));
		config.setDatabaseModel("2.0");
		config.setPlatform(this.connector.getPlatformId());
		config.setLatestTimestamp(maxLog);
		this.dbHandler.saveToDB(session, config);
		this.logger.info("Elapsed time: " + (endtime - this.starttime) + "s");
		this.dbHandler.closeSession(session);
	}

	/**
	 * Reads the Mining Database.
	 * Initial informations needed to start the process of updating are collected here.
	 * The Timestamp of the last run of the extractor is read from the config table
	 * and the objects which might been needed to associate are read and saved here.
	 * 
	 * @return The timestamp of the last run of the extractor. If this is the first run it will be set 0.
	 **/
	public long getMiningInitial() {

		final Session session = this.dbHandler.getMiningSession();

		List<?> t;

		Long readingtimestamp;
		readingtimestamp = (Long) session.createQuery("Select max(latestTimestamp) from Config where platform=" + this.connector.getPlatformId()).uniqueResult();
		
		if(readingtimestamp == null)
		{
			readingtimestamp = -1L;
		}

		// load objects which are already in Mining DB for associations

		Criteria criteria = session.createCriteria(Course.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldCourseMining = new HashMap<Long, Course>();
		for (int i = 0; i < t.size(); i++) {
			this.oldCourseMining.put(((Course) (t.get(i))).getId(), (Course) t.get(i));
		}
		logger.info("Loaded " + this.oldCourseMining.size() + " Course objects from the mining database.");
		
		criteria = session.createCriteria(User.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldUserMining = new HashMap<Long, User>();
		for (int i = 0; i < t.size(); i++) {
			this.oldUserMining.put(((User) (t.get(i))).getId(), (User) t.get(i));
		}
		logger.info("Loaded " + this.oldUserMining.size() + " User objects from the mining database.");
		
		criteria = session.createCriteria(LearningObj.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldLearningObjectMining = new HashMap<Long, LearningObj>();
		for (int i = 0; i < t.size(); i++) {
			this.oldLearningObjectMining.put(((LearningObj) (t.get(i))).getId(), (LearningObj) t.get(i));
		}
		logger.info("Loaded " + this.oldLearningObjectMining.size() + " LearningObj objects from the mining database.");
		
		criteria = session.createCriteria(Attribute.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldAttributeMining = new HashMap<String, Attribute>();
		for (int i = 0; i < t.size(); i++) {
			this.oldAttributeMining.put(((Attribute) (t.get(i))).getName(), (Attribute) t.get(i));
		}
		logger.info("Loaded " + this.oldAttributeMining.size() + " Attribute objects from the mining database.");
		
		criteria = session.createCriteria(Role.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldRoleMining = new HashMap<Long, Role>();
		for (int i = 0; i < t.size(); i++) {
			this.oldRoleMining.put(((Role) (t.get(i))).getId(), (Role) t.get(i));
		}
		logger.info("Loaded " + this.oldRoleMining.size() + " Role objects from the mining database.");
		
		criteria = session.createCriteria(LearningType.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldLearningTypeMining = new HashMap<String, LearningType>();
		for (int i = 0; i < t.size(); i++) {
			this.oldLearningTypeMining.put(((LearningType) (t.get(i))).getType(), (LearningType) t.get(i));
		}
		logger.info("Loaded " + this.oldLearningTypeMining.size() + " LearningType objects from the mining database.");
		
		criteria = session.createCriteria(CourseAttribute.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldCourseAttributeMining = new HashMap<Long, CourseAttribute>();
		for (int i = 0; i < t.size(); i++) {
			this.oldCourseAttributeMining.put(((CourseAttribute) (t.get(i))).getId(), (CourseAttribute) t.get(i));
		}
		logger.info("Loaded " + this.oldCourseAttributeMining.size() + " CourseAttribute objects from the mining database.");
		
		criteria = session.createCriteria(UserAttribute.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldUserAttributeMining = new HashMap<Long, UserAttribute>();
		for (int i = 0; i < t.size(); i++) {
			this.oldUserAttributeMining.put(((UserAttribute) (t.get(i))).getId(), (UserAttribute) t.get(i));
		}
		logger.info("Loaded " + this.oldUserAttributeMining.size() + " UserAttribute objects from the mining database.");
		
		criteria = session.createCriteria(LearningAttribute.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldLearningAttributeMining = new HashMap<Long, LearningAttribute>();
		for (int i = 0; i < t.size(); i++) {
			this.oldLearningAttributeMining.put(((LearningAttribute) (t.get(i))).getId(), (LearningAttribute) t.get(i));
		}
		logger.info("Loaded " + this.oldUserAttributeMining.size() + " LearningAttribute objects from the mining database.");
		
		
		criteria = session.createCriteria(CourseLearning.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldCourseLearningObjectMining = new HashMap<Long, CourseLearning>();
		for (int i = 0; i < t.size(); i++) {
			this.oldCourseLearningObjectMining.put(((CourseLearning) (t.get(i))).getId(), (CourseLearning) t.get(i));
		}
		logger.info("Loaded " + this.oldCourseLearningObjectMining.size() + " CourseResource objects from the mining database.");
		
		criteria = session.createCriteria(UserAssessment.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldUserAssessmentMining = new HashMap<Long, UserAssessment>();
		for (int i = 0; i < t.size(); i++) {
			this.oldUserAssessmentMining.put(((UserAssessment) (t.get(i))).getId(), (UserAssessment) t.get(i));
		}
		logger.info("Loaded " + this.oldUserAssessmentMining.size() + " UserAssessment objects from the mining database.");
		
		criteria = session.createCriteria(CourseUser.class, "obj");
		criteria.addOrder(Property.forName("obj.id").asc());
		t = criteria.list();
		this.oldCourseUserMining = new HashMap<Long, CourseUser>();
		for (int i = 0; i < t.size(); i++) {
			this.oldCourseUserMining.put(((CourseUser) (t.get(i))).getId(), (CourseUser) t.get(i));
		}
		logger.info("Loaded " + this.oldCourseUserMining.size() + " CourseUser objects from the mining database.");
		
		

		criteria = session.createCriteria(AccessLog.class);
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.max("id"));
		criteria.setProjection(pl);
		this.accessLogMax = (Long) criteria.list().get(0);
		if (this.accessLogMax == null) {
			this.accessLogMax = 0L;
		}
		
		criteria = session.createCriteria(CollaborationLog.class);
		criteria.setProjection(pl);
		this.collaborationLogMax = (Long) criteria.list().get(0);
		if (this.collaborationLogMax == null) {
			this.collaborationLogMax = 0L;
		}
		
		criteria = session.createCriteria(AssessmentLog.class);
		criteria.setProjection(pl);
		this.assessmentLogMax = (Long) criteria.list().get(0);
		if (this.assessmentLogMax == null) {
			this.assessmentLogMax = 0L;
		}
	
		criteria = session.createCriteria(LearningType.class);
		criteria.setProjection(pl);
		this.learningObjectTypeMax = (Long) criteria.list().get(0);
		if (this.learningObjectTypeMax == null) {
			this.learningObjectTypeMax = 0L;
		}
		
		criteria = session.createCriteria(UserAssessment.class);
		criteria.setProjection(pl);
		this.userAssessmentMax = (Long) criteria.list().get(0);
		if (this.userAssessmentMax == null) {
			this.userAssessmentMax = 0L;
		}
		
		criteria = session.createCriteria(Attribute.class);
		criteria.setProjection(pl);
		this.attributeIdMax = (Long) criteria.list().get(0);
		if (this.attributeIdMax == null) {
			this.attributeIdMax = 0L;
		}
		
		criteria = session.createCriteria(CourseAttribute.class);
		criteria.setProjection(pl);
		this.courseAttributeIdMax = (Long) criteria.list().get(0);
		if (this.courseAttributeIdMax == null) {
			this.courseAttributeIdMax = 0L;
		}
		
		criteria = session.createCriteria(UserAttribute.class);
		criteria.setProjection(pl);
		this.userAttributeIdMax = (Long) criteria.list().get(0);
		if (this.userAttributeIdMax == null) {
			this.userAttributeIdMax = 0L;
		}
		
		criteria = session.createCriteria(LearningAttribute.class);
		criteria.setProjection(pl);
		this.learningAttributeIdMax = (Long) criteria.list().get(0);
		if (this.learningAttributeIdMax == null) {
			this.learningAttributeIdMax = 0L;
		}
		
		//this.dbHandler.closeSession(session);
		return readingtimestamp;
	}

	/**
	 * Has to read the LMS Database.
	 * It starts reading elements with timestamp readingtimestamp and higher.
	 * It is supposed to be used for frequently and small updates.
	 * For a greater Mass of Data it is suggested to use getLMS_tables(long, long);
	 * If Hibernate is used to access the LMS DB too,
	 * it is suggested to write the found tables into lists of
	 * hibernate object model classes, which have to
	 * be created as global variables in this class.
	 * So they can be used in the generate methods of this class.
	 * 
	 * @param readingfromtimestamp
	 *            Only elements with timestamp readingtimestamp and higher are read here.
	 *            *
	 * @return the lM stables
	 */
	public abstract void getLMStables(DBConfigObject dbConf, long readingfromtimestamp, List<Long> courses, List<String> logins);

	/**
	 * Has to read the LMS Database.
	 * It reads elements with timestamp between readingfromtimestamp and readingtotimestamp.
	 * This method is used to read great DB in a step by step procedure.
	 * That is necessary for a great mass of Data when handeling an existing DB for example.
	 * If Hibernate is used to access the LMS DB too,
	 * it is suggested to write the found tables into lists of
	 * hibernate object model classes, which have to
	 * be created as global variables in this class.
	 * So they can be used in the generate methods of this class.
	 * 
	 * @param readingfromtimestamp
	 *            Only elements with timestamp readingfromtimestamp and higher are read here.
	 * @param readingtotimestamp
	 *            Only elements with timestamp readingtotimestamp and lower are read here.
	 *            *
	 * @return the lM stables
	 */
	public abstract void getLMStables(DBConfigObject dbConf, long readingfromtimestamp, long readingtotimestamp, List<Long> courses, List<String> logins);

	/**
	 * Has to clear the lists of LMS tables*.
	 */
	public abstract void clearLMStables();

	/**
	 * Clears the lists of mining tables.
	 **/
	public void clearMiningTables() {

		this.courseMining.clear();
		this.learningObjectMining.clear();
		this.userMining.clear();
		this.roleMining.clear();
		this.attributeMining.clear();		
		this.learningTypeMining.clear();
		this.courseAttributeMining.clear();
		this.userAttributeMining.clear();
		this.learningAttributeMining.clear();
		
		this.courseLearningMining.clear();
		this.courseUserMining.clear();
		this.userAssessmentMining.clear();
	}

	/**
	 * Only for successive readings. This is meant to be done, when the gathered mining data has already
	 * been saved and before the mining tables are cleared for the next iteration.
	 */
	public void prepareMiningData()
	{
		this.oldCourseMining.putAll(this.courseMining);
		this.oldLearningObjectMining.putAll(this.learningObjectMining);
		this.oldUserMining.putAll(this.userMining);
		this.oldRoleMining.putAll(this.roleMining);
		this.oldAttributeMining.putAll(this.attributeMining);
		this.oldLearningTypeMining.putAll(this.learningTypeMining);
		this.oldCourseAttributeMining.putAll(this.courseAttributeMining);
		this.oldUserAttributeMining.putAll(this.userAttributeMining);
		this.oldLearningAttributeMining.putAll(this.learningAttributeMining);
		this.oldUserAssessmentMining.putAll(this.userAssessmentMining);
		this.oldCourseLearningObjectMining.putAll(this.courseLearningMining);
		this.oldCourseUserMining.putAll(this.courseUserMining);
		
	}

	/**
	 * Generates and save the new tables for the mining DB.
	 * We call the genereate-methods for each mining table to get the new entries.
	 * At last we create a Transaction and save the new entries to the DB.
	 **/
	public void saveMiningTables() {

		// generate & save new mining tables
		this.updates = new ArrayList<Collection<?>>();

		Long objects = 0L;

		// generate mining tables
		if (this.userMining == null) {

			this.c.reset();
			logger.info("\nObject tables:\n");

			this.courseMining = this.generateCourses();
			objects += this.courseMining.size();
			logger.info("Generated " + this.courseMining.size() + " Course entries in "
					+ this.c.getAndReset() + " s. ");
			this.updates.add(this.courseMining.values());
			
			this.learningObjectMining = this.generateLearningObjs();
			
			this.learningTypeMining = this.generateLearningTypes();
			objects += this.learningTypeMining.size();
			logger.info("Generated " + this.learningTypeMining.size()
					+ " LearningObjectType entries in " + this.c.getAndReset() + " s. ");
			this.updates.add(this.learningTypeMining.values());
			
			objects += this.learningObjectMining.size();
			logger.info("Generated " + this.learningObjectMining.size() + " LearningObject entries in "
					+ this.c.getAndReset() + " s. ");
			this.updates.add(this.learningObjectMining.values());

			this.roleMining = this.generateRoles();
			objects += this.roleMining.size();
			logger.info("Generated " + this.roleMining.size() + " Role entries in " + this.c.getAndReset()
					+ " s. ");
			this.updates.add(this.roleMining.values());

			this.userMining = this.generateUsers();
			objects += this.userMining.size();
			logger.info("Generated " + this.userMining.size() + " User entries in " + this.c.getAndReset()
					+ " s. ");
			this.updates.add(this.userMining.values());
			
			this.attributeMining = this.generateAttributes();
			objects += this.attributeMining.size();
			logger.info("Generated " + this.attributeMining.size() + " Attribute entries in " + this.c.getAndReset()
					+ " s. ");
			this.updates.add(this.attributeMining.values());


			logger.info("\nAssociation tables:\n");
			
			


			this.courseLearningMining = generateCourseLearnings();
			objects += this.courseLearningMining.size();
			logger.info("Generated " + this.courseLearningMining.size()
					+ " CourseLearningObject entries in " + this.c.getAndReset() + " s. ");
			this.updates.add(this.courseLearningMining.values());
			
			this.courseAttributeMining = generateCourseAttributes();
			objects += this.courseAttributeMining.size();
			logger.info("Generated " + this.courseAttributeMining.size()
					+ " CourseAttribute entries in " + this.c.getAndReset() + " s. ");
			this.updates.add(this.courseAttributeMining.values());

		}

		
		this.courseUserMining = generateCourseUsers();
		objects += this.updates.get(this.updates.size() - 1).size();
		this.updates.add(this.courseUserMining.values());
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " CourseUser entries in " + this.c.getAndReset() + " s. ");

		this.updates.add(this.generateUserAssessments().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " UserAssessment entries in " + this.c.getAndReset() + " s. ");
		
		logger.info("\nLog tables:\n");

		this.updates.add(this.generateAccessLogs().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " AccessLog entries in " + this.c.getAndReset() + " s. ");
		
		this.updates.add(this.generateCollaborativeLogs().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " CollaborationLog entries in " + this.c.getAndReset() + " s. ");
		
		this.updates.add(this.generateAssessmentLogs().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " AssessmentLog entries in " + this.c.getAndReset() + " s. ");
		
		if (objects > 0)
		{
			final Session session = this.dbHandler.getMiningSession();
			logger.info("Writing to DB");
			this.dbHandler.saveCollectionToDB(session, this.updates);
		}

		this.clearLMStables();
		this.updates.clear();

	}

	// methods for create and fill the mining-table instances
	abstract Map<Long, Course> generateCourses();
	
	abstract Map<Long, LearningObj> generateLearningObjs();

	abstract Map<Long, User> generateUsers();

	abstract Map<Long, Role> generateRoles();
	
	abstract Map<String, LearningType> generateLearningTypes();
	
	abstract Map<Long, CourseAttribute> generateCourseAttributes();
	
	abstract Map<Long, UserAttribute> generateUserAttributes();
	
	abstract Map<Long, LearningAttribute> generateLearningAttributes();
	
	abstract Map<Long, CourseUser> generateCourseUsers();

	abstract Map<Long, CourseLearning> generateCourseLearnings();

	abstract Map<Long, UserAssessment> generateUserAssessments();
	
	abstract Map<Long, AccessLog> generateAccessLogs();
	
	abstract Map<String, Attribute> generateAttributes();

	abstract Map<Long, CollaborationLog> generateCollaborativeLogs();

	abstract Map<Long, AssessmentLog> generateAssessmentLogs();



}
