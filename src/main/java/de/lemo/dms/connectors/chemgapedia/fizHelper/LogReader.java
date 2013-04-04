/**
 * File ./main/java/de/lemo/dms/connectors/chemgapedia/fizHelper/LogReader.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.chemgapedia.fizHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.RoleMining;
import de.lemo.dms.db.miningDBclass.UserMining;

/**
 * The Class LogReader. Reads Chemgapedia's server-logs and saves the found objects into the database.
 */
public class LogReader {

	/**
	 * User-objects of previous connector-runs
	 */
	private final Map<String, UserMining> oldUsers = new HashMap<String, UserMining>();
	/**
	 * Role-objects of previous connector-runs
	 */
	private final Map<Long, RoleMining> oldRoles = new HashMap<Long, RoleMining>();
	/**
	 * CourseUser-objects of previous connector-runs
	 */
	private final Map<String, CourseUserMining> oldCourseUsers = new HashMap<String, CourseUserMining>();
	/**
	 * User-objects of current connector-run
	 */
	private Map<String, UserMining> newUsers = new HashMap<String, UserMining>();
	/**
	 * Resource-objects of previous connector-runs
	 */
	private final Map<String, ResourceMining> oldResources = new HashMap<String, ResourceMining>();
	/**
	 * Resource-objects of current connector-run
	 */
	private final Map<String, ResourceMining> newResources = new HashMap<String, ResourceMining>();
	/**
	 * CourseResource-objects found in database
	 */
	private final Map<String, CourseResourceMining> courseResources = new HashMap<String, CourseResourceMining>();
	/**
	 * IDMapping-objects of previous connector-runs
	 */
	private Map<String, IDMappingMining> idMapping = new HashMap<String, IDMappingMining>();
	/**
	 * IDMapping-objects of current connector-run
	 */
	private Map<String, IDMappingMining> newIdMapping = new HashMap<String, IDMappingMining>();
	/**
	 * HashMap storing all logged accesses, with according user-login as key
	 */
	private final Map<String, ArrayList<LogObject>> userHistories = new HashMap<String, ArrayList<LogObject>>();
	/**
	 * Course-objects of previous connector-runs
	 */
	private final Map<String, CourseMining> oldCourses = new HashMap<String, CourseMining>();
	/**
	 * Internal clock-object for statistics
	 */
	private final Clock clock = new Clock();
	/**
	 * DBHandler-object, for connection to Mining-Database
	 */
	private final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
	/**
	 * Database's largest id used in ResourceLogMining
	 */
	private Long resLogId;
	/**
	 * Database's largest id used in UserMining
	 */
	private Long userIdCount;
	/**
	 * Database's largest id used in CourseUserMining
	 */
	private Long courseUserIdCount;
	/**
	 * Database's largest id used in ResourceMining
	 */
	private Long resIdCount;
	
	private Long startTime;
	
	/**
	 * Database's largest timestamp used in ResourceLogMining
	 */
	private final IConnector connector;

	private Logger logger = Logger.getLogger(this.getClass());
	
	private RoleMining standardRole;
	/**
	 * Creates a new LogReader-object, imports necessary objects from Mining-Database and sets counters.
	 * 
	 * @param connector
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	public LogReader(final IConnector connector, final Session session, List<Long> courses)
	{
		this.connector = connector;
		final long platformId = connector.getPlatformId();
		try {
			
			this.startTime = (Long) session.createQuery("Select max(latestTimestamp) from ConfigMining where platform=" + this.connector.getPlatformId()).uniqueResult();
			
			if(this.startTime == null)
			{
				this.startTime = 0L;
			}

			this.newIdMapping = new HashMap<String, IDMappingMining>();

			Criteria c = session.createCriteria(IDMappingMining.class, "idmap");
			c.add(Restrictions.eq("idmap.platform", platformId));

			final List<IDMappingMining> ids = c.list();

			// Load previously saved idMappingMining, used to identify resources
			this.idMapping = new HashMap<String, IDMappingMining>();
			for (int i = 0; i < ids.size(); i++)
			{
				ids.get(i).setPlatform(connector.getPlatformId());
				this.idMapping.put(ids.get(i).getHash(), ids.get(i));
			}
			logger.info("Read " + ids.size() + " IDMappings from database.");

			// Load previously saved UserMining-objects
			c = session.createCriteria(UserMining.class, "users");
			c.add(Restrictions.eq("users.platform", platformId));
			final List<UserMining> us = c.list();
			for (int i = 0; i < us.size(); i++) {
				this.oldUsers.put(us.get(i).getLogin(), us.get(i));
			}
			logger.info("Read " + us.size() + " UserMinings from database.");

			// Load previously saved ResourceMining-objects
			c = session.createCriteria(ResourceMining.class, "resources");
			c.add(Restrictions.eq("resources.platform", platformId));
			final List<ResourceMining> rt = c.list();
			for (final ResourceMining res : rt) {
				this.oldResources.put(res.getUrl(), res);
			}
			logger.info("Read " + rt.size() + " ResourceMinings from database.");
			
			// Load previously saved CourseUserMining-objects
			c = session.createCriteria(CourseUserMining.class, "courseUsers");
			c.add(Restrictions.eq("courseUsers.platform", platformId));
			final List<CourseUserMining> cu = c.list();
			for (final CourseUserMining res : cu) {
				this.oldCourseUsers.put(res.getCourse().getId() + "" + res.getUser().getId(), res);
			}
			logger.info("Read " + cu.size() + " CourseUserMinings from database.");

			// Load previously saved CourseMining-objects
			c = session.createCriteria(CourseMining.class, "courses");
			c.add(Restrictions.eq("courses.platform", platformId));
			final List<CourseMining> cm = c.list();
			for (int i = 0; i < cm.size(); i++)
			{
				this.oldCourses.put(cm.get(i).getTitle(), cm.get(i));
			}
			logger.info("Read " + cm.size() + " CourseMinings from database.");
			
			// Load previously saved CourseMining-objects
			c = session.createCriteria(RoleMining.class, "roles");
			c.add(Restrictions.eq("roles.platform", platformId));
			final List<RoleMining> roles = c.list();
			for (int i = 0; i < roles.size(); i++)
			{
				this.oldRoles.put(roles.get(i).getId(), roles.get(i));
			}
			logger.info("Read " + cm.size() + " RoleMinings from database.");
			
			if(this.oldRoles.size() == 0)
			{
				RoleMining role = new RoleMining();
				role.setId(Long.valueOf(connector.getPrefix() + "" + 0));
				role.setDescription("Standard Chemgapedia-Nutzer");
				role.setName("Student");
				role.setPlatform(connector.getPlatformId());
				role.setShortname("STD");
				role.setType(2);
				
				this.standardRole = role;
				this.oldRoles.put(role.getId(), role);
			}

			// Load previously saved CourseResourceMining-objects
			c = session.createCriteria(CourseResourceMining.class, "coursesResources");
			c.add(Restrictions.eq("coursesResources.platform", platformId));
			final List<CourseResourceMining> courseResource = c.list();
			for (int i = 0; i < courseResource.size(); i++) {
				this.courseResources.put(courseResource.get(i).getResource().getUrl(), courseResource.get(i));
			}
			logger.info("Read " + courseResource.size() + " CourseResourceMinings from database.");

			final Query resCount = session.createQuery("select max(res.id) from ResourceMining res where res.platform="
					+ platformId + "");
			if (resCount.list().size() > 0) {
				this.resIdCount = ((ArrayList<Long>) resCount.list()).get(0);
			}
			if ((this.resIdCount != null) && (this.resIdCount != 0)) {
				this.resIdCount = Long.valueOf(this.resIdCount.toString().substring(
						connector.getPrefix().toString().length()));
			} else {
				this.resIdCount = 0L;
			}

			final Query userCount = session.createQuery("select max(user.id) from UserMining user where user.platform="
					+ platformId + "");
			if (userCount.list().size() > 0) {
				this.userIdCount = ((ArrayList<Long>) userCount.list()).get(0);
			}
			if ((this.userIdCount != null) && (this.userIdCount != 0)) {
				this.userIdCount = Long.valueOf(this.userIdCount.toString().substring(
						connector.getPrefix().toString().length()));
			} else {
				this.userIdCount = 0L;
			}
			
			final Query courseUserCount = session.createQuery("select max(courseUser.id) from CourseUserMining courseUser where courseUser.platform="
					+ platformId + "");
			if (courseUserCount.list().size() > 0) {
				this.courseUserIdCount = ((ArrayList<Long>) courseUserCount.list()).get(0);
			}
			if ((this.courseUserIdCount != null) && (this.courseUserIdCount != 0)) {
				this.courseUserIdCount = Long.valueOf(this.courseUserIdCount.toString().substring(
						connector.getPrefix().toString().length()));
			} else {
				this.courseUserIdCount = 0L;
			}

			final Query logCount = session
					.createQuery("select max(log.id) from ResourceLogMining log");
			if (logCount.list().size() > 0) {
				this.resLogId = ((ArrayList<Long>) logCount.list()).get(0);
			}
			if (this.resLogId == null) {
				this.resLogId = 0L;
			}
			
			session.close();

		} catch (final Exception e)
		{
			logger.error(e.getMessage());
		}

	}

	/**
	 * Updates the data.
	 * 
	 * @param inFile
	 * @return
	 */
	private void update(final boolean filterLog, final Session session)
	{
		if (filterLog) {
			this.filterServerLogFile();
		}
		this.save(session);

		for (final ResourceMining resource : this.newResources.values()) {
			this.oldResources.put(resource.getUrl(), resource);
		}

		for (final UserMining user : this.newUsers.values()) {
			this.oldUsers.put(user.getLogin(), user);
		}

		for (final IDMappingMining mapping : this.newIdMapping.values()) {
			this.idMapping.put(mapping.getHash(), mapping);
		}

		this.clearMaps();

	}

	public void clearMaps()
	{
		this.newResources.clear();
		this.newUsers.clear();
		this.userHistories.clear();
		this.newIdMapping.clear();
	}

	/**
	 * Filters irrelevant lines (bots, spiders) out of the log file and writes a new one.
	 * 
	 * @param outFile
	 *            filename of the new filtered log file.
	 */
	private void filterServerLogFile()
	{
		try {
			this.clock.reset();
			ArrayList<LogObject> a;
			final Object[] user = this.newUsers.values().toArray();
			final HashMap<String, UserMining> tempUsers = new HashMap<String, UserMining>();
			final double old = Long.parseLong(this.newUsers.size() + "");
			int totalLines = 0;
			int linesDeleted = 0;
			final BotFinder bf = new BotFinder();
			for (int i = 0; i < user.length; i++)
			{
				int susp1 = 0;
				int susp2 = 0;
				int susp3 = 0;

				a = this.userHistories.get(((UserMining) user[i]).getLogin());
				Collections.sort(a);

				if ((a != null) && (a.size() > 0))
				{
					totalLines += a.size();
					susp1 = bf.checkFastOnes(a, 1).size();
					susp2 = bf.checkPeriods(a, 5);
					susp3 = bf.checkForRepetitions(a, 10);
					if ((susp1 < 1) && (susp2 == 0) && (susp3 == 0)) {
						tempUsers.put(((UserMining) user[i]).getLogin(), (UserMining) user[i]);
					} else
					{

						linesDeleted += a.size();
						this.userHistories.remove(((UserMining) user[i]).getLogin());
					}
				}
			}

			final double cutUsePerc = (old - Long.valueOf("" + tempUsers.size())) / (old / 100);
			final double tmp = totalLines / 100.0d;
			final double cutLinPerc = linesDeleted / tmp;
			logger.info("Filtered " + (old - tempUsers.size()) + " suspicious users  out of " + old + " ("
					+ new DecimalFormat("0.00").format(cutUsePerc) + "%), eliminating " + linesDeleted + " log lines ("
					+ new DecimalFormat("0.00").format(cutLinPerc) + "%).");
			this.newUsers = tempUsers;
		} catch (final Exception e)
		{
			logger.error("While filtering log-file:");
		}
	}

	/**
	 * Loads the data from the server-log-file.
	 * 
	 * @param inFile
	 *            the path of the server log file
	 * @param linesPerRun
	 *            Number of log-lines that are read before data is stored to database
	 * @param filterLog
	 *            Determines whether suspicious logs are ignored or not
	 */
	public void loadServerLogData(final String inFile, final Long linesPerRun, final boolean filterLog,
			final Session session)
	{
		try
		{
			logger.info("Reading server log " + inFile);
			final BufferedReader input = new BufferedReader(new FileReader(inFile));
			int count = 0;
			try
			{
				String line = null;
				this.clock.reset();
				Long i = 0L;
				while ((line = input.readLine()) != null)
				{
					i++;
					if ((linesPerRun != 0) && (i > 0) && ((i % linesPerRun) == 0))
					{
						this.update(filterLog, session);
					}
					count++;
					boolean newRes = false;
					final LogLine logLine = new LogLine(line);

					// The line is only processed, if it is readable and not older the the line before
					if (logLine.isValid() && this.startTime < logLine.getTimestamp())
					{
						final LogObject lo = new LogObject();
						String name;

						name = logLine.getId();

						long id = -1;
						// Set user-id
						if (this.idMapping.get(name) != null)
						{
							id = this.idMapping.get(name).getId();
							lo.setId(id);
						}
						if (id == -1)
						{
							id = this.userIdCount + 1;
							this.userIdCount = id;
							lo.setId(Long.valueOf(this.connector.getPrefix() + "" + id));
						}

						// Set timestamp
						lo.setTime(logLine.getTimestamp());
						// Set url
						lo.setUrl(logLine.getUrl());
						// Set HTTP-status
						lo.setStatus(logLine.getHttpStatus());
						// Set referrer
						lo.setReferrer(logLine.getReferrer());
						// Set duration to standard (will be calculated later on)
						lo.setDuration(-1);

						// Check if resource is already known. If yes, set course. Else create new resource later on.
						if ((this.oldResources.get(lo.getUrl()) == null)
								&& (this.newResources.get(lo.getUrl()) == null))
						{
							newRes = true;
							lo.setCourse(null);
						}
						else
						{
							final CourseResourceMining c = this.courseResources.get(lo.getUrl());
							if (this.courseResources.get(lo.getUrl()) != null) {
								lo.setCourse(c.getCourse());
							}
						}

						// Check if users is known
						if ((this.oldUsers.get(logLine.getId()) != null)
								|| (this.newUsers.get(logLine.getId()) != null))
						{
							UserMining u;
							if (this.oldUsers.get(logLine.getId()) != null) {
								u = this.oldUsers.get(logLine.getId());
							} else {
								u = this.newUsers.get(logLine.getId());
							}

							// Check if the user is known and if he has 'logged out' since last request
							if (!lo.getReferrer().equals("-") && !lo.getReferrer().contains("www.google."))
							{
								final ArrayList<LogObject> tlo = this.userHistories.get(logLine.getId());
								if ((tlo != null) && tlo.get(tlo.size() - 1).getReferrer().equals(lo.getUrl()))
								{
									this.userHistories.get(logLine.getId())
											.get(this.userHistories.get(logLine.getId()).size() - 1)
											.setDuration(lo.getTime() - u.getLastAccess());
								}
								if (lo.getTime() > u.getLastAccess()) {
									u.setLastAccess(lo.getTime());
								}
							}
							else
							{
								u.setLastLogin(u.getCurrentLogin());

								if (u.getCurrentLogin() < lo.getTime()) {
									u.setCurrentLogin(lo.getTime());
								}
								if (u.getLastAccess() < lo.getTime()) {
									u.setLastAccess(lo.getTime());
								}
							}
							u.setPlatform(this.connector.getPlatformId());
							this.newUsers.put(u.getLogin(), u);
							lo.setUser(u);
						}
						else
						{
							// If the user is unknown, create new user-object
							final UserMining u = new UserMining();
							u.setId(lo.getId());
							u.setFirstAccess(lo.getTime());
							u.setLastAccess(lo.getTime());
							// google-referrers aren't replaced with "-" although they are external
							if (lo.getReferrer().equals("-") || lo.getReferrer().startsWith("www.google.")) {
								u.setCurrentLogin(lo.getTime());
							} else {
								u.setCurrentLogin(0);
							}
							u.setPlatform(this.connector.getPlatformId());
							u.setLogin(logLine.getId());
							this.newUsers.put(logLine.getId(), u);
							lo.setUser(u);
						}

						// Save viewed glossary entries to the resource-list because they aren't registered within
						// XML-packages
						if ((newRes && lo.getUrl().endsWith(".html")))
						{
							final ResourceMining r = new ResourceMining();

							long resourceId = -1;
							if (this.idMapping.get(lo.getUrl()) != null)
							{
								resourceId = this.idMapping.get(lo.getUrl()).getId();
								lo.setId(resourceId);
							}
							if (resourceId == -1)
							{
								resourceId = this.resIdCount + 1;
								this.resIdCount = resourceId;
								this.idMapping.put(
										lo.getUrl(),
										new IDMappingMining(
												Long.valueOf(this.connector.getPrefix() + "" + resourceId), lo
														.getUrl(), this.connector.getPlatformId()));
									this.newIdMapping.put(
											lo.getUrl(),
											new IDMappingMining(
													Long.valueOf(this.connector.getPrefix() + "" + resourceId), lo
															.getUrl(), this.connector.getPlatformId()));
								resourceId = Long.valueOf(this.connector.getPrefix() + "" + resourceId);
								lo.setId(resourceId);
							}

							r.setId(resourceId);
							r.setUrl(lo.getUrl());
							// Regex used to prevent the inclusion of assignment-pages
							if (newRes && !r.getUrl().matches("[0-9a-z]{32}[-]{1}[0-9]++"))
							{
								if (lo.getUrl().endsWith("/index.html"))
								{
									final int inPos = lo.getUrl().substring(0, lo.getUrl().lastIndexOf("/") - 1)
											.lastIndexOf("/");
									String urlCut = lo.getUrl().substring(0, inPos);
									urlCut = urlCut.substring(urlCut.lastIndexOf('/') + 1, urlCut.length());
									r.setType("Index");
								} else {
									r.setType("Unknown");
								}
								r.setPosition(-1);
							}
							else
							{
								r.setType("GlossaryEntry");
								r.setPosition(-5);
							}

							// Construct resource title from URL
							String h = lo.getUrl().substring(lo.getUrl().lastIndexOf("/") + 1, lo.getUrl().length());
							h = h.substring(0, h.indexOf('.'));
							String f = "";
							if (h.length() > 0) {
								f = Character.toUpperCase(h.charAt(0)) + "";
							}
							if (h.length() > 0) {
								h = f + h.substring(1);
							} else {
								logger.debug("URL doesn't match pattern: " + lo.getUrl());
							}
							r.setTitle(h);

							// cutting out supplements
							if (r.getUrl().contains("/vsengine/supplement/")) {
								r.setType("Supplement");
							}

							if (r.getUrl().contains("/mindmap/")) {
								r.setType("Mindmap");
							}

							r.setPlatform(this.connector.getPlatformId());
							this.newResources.put(r.getUrl(), r);
						}

						if (this.userHistories.get(logLine.getId()) == null)
						{
							final ArrayList<LogObject> a = new ArrayList<LogObject>();
							a.add(lo);
							this.userHistories.put(logLine.getId(), a);
						}
						else
						{

							this.userHistories.get(logLine.getId()).add(lo);
						}
					}
					else if (!logLine.isValid()) {
						logger.debug("Line doesn't match pattern.");
					} else {
						logger.debug("Line's timestamp is to old.");
					}
				}
				if (filterLog) {
					this.filterServerLogFile();
				}
			}

			finally
			{
				logger.info("Read " + count + " lines.");
				input.close();
			}
		} catch (final Exception ex)
		{
			logger.error(ex.getMessage());
		}

	}

	/**
	 * Writes the data to the database.
	 */
	public Long save(Session session)
	{
		final List<Collection<?>> l = new ArrayList<Collection<?>>();
		final ArrayList<ResourceLogMining> resourceLogMining = new ArrayList<ResourceLogMining>();
		ArrayList<CourseUserMining> courseUserMining = new ArrayList<CourseUserMining>();
		final Collection<UserMining> users = this.newUsers.values();
		final Collection<IDMappingMining> idmap = this.newIdMapping.values();
		logger.info("Found " + users.size() + " users.");
		l.add(users);
		logger.info("Found " + idmap.size() + " IDMappings.");
		l.add(idmap);
		
		for (final ArrayList<LogObject> loadedItem : this.userHistories.values())
		{
			final HashMap<Long, CourseUserMining> courseUserSingle = new HashMap<Long, CourseUserMining>();
			for (int i = 0; i < loadedItem.size(); i++)
			{
				
				final ResourceLogMining rl = new ResourceLogMining();

				// Set Url for resource-object
				if (this.newResources.get(loadedItem.get(i).getUrl()) != null) {
					rl.setResource(this.newResources.get(loadedItem.get(i).getUrl()));
				} else {
					rl.setResource(this.oldResources.get(loadedItem.get(i).getUrl()));
				}

				rl.setCourse(loadedItem.get(i).getCourse());
				rl.setUser(loadedItem.get(i).getUser());
				rl.setTimestamp(loadedItem.get(i).getTime());
				rl.setDuration(loadedItem.get(i).getDuration());
				rl.setAction("View");
				rl.setPlatform(this.connector.getPlatformId());

			
				if(rl.getCourse() != null)
				{
					rl.setId(this.resLogId + 1);
					this.resLogId++;
					
					CourseUserMining cu = courseUserSingle.get(rl.getCourse().getId());
					
					if(cu == null)
					{	
						
						cu = new CourseUserMining();
						cu.setCourse(rl.getCourse());
						cu.setUser(rl.getUser());
						cu.setEnrolend(rl.getTimestamp());
						cu.setEnrolstart(rl.getTimestamp());
						cu.setRole(this.standardRole);
						cu.setPlatform(connector.getPlatformId());
						
						Long id = this.courseUserIdCount + 1;
						this.courseUserIdCount = id;
						cu.setId(Long.valueOf(connector.getPrefix() + "" + id));
						courseUserSingle.put(rl.getCourse().getId(), cu);
					}					
					else
					{
						if(cu.getEnrolend() < rl.getTimestamp())
							cu.setEnrolend(rl.getTimestamp());
						if(cu.getEnrolstart() > rl.getTimestamp())
							cu.setEnrolstart(rl.getTimestamp());					
					}
					
					resourceLogMining.add(rl);
				}
				

			}
			courseUserMining.addAll(courseUserSingle.values());
		}
		Collections.sort(resourceLogMining);
		Long maxLog = 0L;
		if(resourceLogMining.size() > 0)
		{
			maxLog = resourceLogMining.get(resourceLogMining.size() -1 ).getTimestamp();
		}
		logger.info("Found " + newResources.values().size() + " resources.");
		l.add(this.newResources.values());
		logger.info("Found " + oldRoles.values().size() + " roles.");
		l.add(this.oldRoles.values());
		logger.info("Found " + courseUserMining.size() + " courseUsers.");
		l.add(courseUserMining);logger.info("Found " + resourceLogMining.size() + " resourceLogs.");
		l.add(resourceLogMining);
		logger.info("Writing to database...");
		if (session.isOpen()) {
			this.dbHandler.saveCollectionToDB(session, l);
		} else
		{
			session = this.dbHandler.getMiningSession();
			this.dbHandler.saveCollectionToDB(session, l);
		}
		
		return maxLog;
	}

}
