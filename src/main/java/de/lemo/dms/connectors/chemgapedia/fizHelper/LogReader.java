/**
 * File ./main/java/de/lemo/dms/connectors/chemgapedia/fizHelper/LogReader.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
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
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.UserMining;

/**
 * The Class LogReader. Reads Chemgapedia's server-logs and saves the found objects into the database.
 */
public class LogReader {

	/**
	 * User-objects of previous connector-runs
	 */
	private final HashMap<String, UserMining> oldUsers = new HashMap<String, UserMining>();
	/**
	 * User-objects of current connector-run
	 */
	private HashMap<String, UserMining> newUsers = new HashMap<String, UserMining>();
	/**
	 * Resource-objects of previous connector-runs
	 */
	private final HashMap<String, ResourceMining> oldResources = new HashMap<String, ResourceMining>();
	/**
	 * Resource-objects of current connector-run
	 */
	private final HashMap<String, ResourceMining> newResources = new HashMap<String, ResourceMining>();
	/**
	 * CourseResource-objects found in database
	 */
	private final HashMap<String, CourseResourceMining> courseResources = new HashMap<String, CourseResourceMining>();
	/**
	 * IDMapping-objects of previous connector-runs
	 */
	private HashMap<String, IDMappingMining> id_mapping = new HashMap<String, IDMappingMining>();
	/**
	 * IDMapping-objects of current connector-run
	 */
	private HashMap<String, IDMappingMining> new_id_mapping = new HashMap<String, IDMappingMining>();
	/**
	 * HashMap storing all logged accesses, with according user-login as key
	 */
	private final HashMap<String, ArrayList<LogObject>> userHistories = new HashMap<String, ArrayList<LogObject>>();
	/**
	 * Course-objects of previous connector-runs
	 */
	private final HashMap<String, CourseMining> oldCourses = new HashMap<String, CourseMining>();
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
	 * Database's largest id used in ResourceMining
	 */
	private Long resIdCount;
	/**
	 * Database's largest timestamp used in ResourceLogMining
	 */
	private final IConnector connector;

	/**
	 * Creates a new LogReader-object, imports necessary objects from Mining-Database and sets counters.
	 * 
	 * @param connector
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	public LogReader(final IConnector connector, final Session session)
	{
		this.connector = connector;
		final long platformId = connector.getPlatformId();
		try {
			this.new_id_mapping = new HashMap<String, IDMappingMining>();

			Criteria c = session.createCriteria(IDMappingMining.class, "idmap");
			c.add(Restrictions.eq("idmap.platform", platformId));

			final List<IDMappingMining> ids = c.list();

			// Load previously saved idMappingMining, used to identify resources
			this.id_mapping = new HashMap<String, IDMappingMining>();
			for (int i = 0; i < ids.size(); i++)
			{
				ids.get(i).setPlatform(connector.getPlatformId());
				this.id_mapping.put(ids.get(i).getHash(), ids.get(i));
			}
			System.out.println("Read " + ids.size() + " IDMappings from database.");

			// Load previously saved UserMining-objects
			c = session.createCriteria(UserMining.class, "users");
			c.add(Restrictions.eq("users.platform", platformId));
			final List<UserMining> us = c.list();
			for (int i = 0; i < us.size(); i++) {
				this.oldUsers.put(us.get(i).getLogin(), us.get(i));
			}
			System.out.println("Read " + us.size() + " UserMinings from database.");

			// Load previously saved ResourceMining-objects
			c = session.createCriteria(ResourceMining.class, "resources");
			c.add(Restrictions.eq("resources.platform", platformId));
			final List<ResourceMining> rt = c.list();
			for (final ResourceMining res : rt) {
				this.oldResources.put(res.getUrl(), res);
			}
			System.out.println("Read " + rt.size() + " ResourceMinings from database.");

			// Load previously saved CourseMining-objects
			c = session.createCriteria(CourseMining.class, "courses");
			c.add(Restrictions.eq("courses.platform", platformId));
			final List<CourseMining> cm = c.list();
			for (int i = 0; i < cm.size(); i++)
			{
				this.oldCourses.put(cm.get(i).getTitle(), cm.get(i));
			}
			System.out.println("Read " + cm.size() + " CourseMinings from database.");

			// Load previously saved CourseResourceMining-objects
			c = session.createCriteria(CourseResourceMining.class, "coursesResources");
			c.add(Restrictions.eq("coursesResources.platform", platformId));
			final List<CourseResourceMining> courseResource = c.list();
			for (int i = 0; i < courseResource.size(); i++) {
				this.courseResources.put(courseResource.get(i).getResource().getUrl(), courseResource.get(i));
			}
			System.out.println("Read " + courseResource.size() + " CourseResourceMinings from database.");

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

			final Query logCount = session
					.createQuery("select max(log.id) from ResourceLogMining log where log.platform=" + platformId + "");
			if (logCount.list().size() > 0) {
				this.resLogId = ((ArrayList<Long>) logCount.list()).get(0);
			}
			if (this.resLogId == null) {
				this.resLogId = 0L;
			}

		} catch (final Exception e)
		{
			System.out.println(e.getMessage());
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

		for (final IDMappingMining mapping : this.new_id_mapping.values()) {
			this.id_mapping.put(mapping.getHash(), mapping);
		}

		this.clearMaps();

	}

	public void clearMaps()
	{
		this.newResources.clear();
		this.newUsers.clear();
		this.userHistories.clear();
		this.new_id_mapping.clear();
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
			System.out.println("Filtered " + (old - tempUsers.size()) + " suspicious users  out of " + old + " ("
					+ new DecimalFormat("0.00").format(cutUsePerc) + "%), eliminating " + linesDeleted + " log lines ("
					+ new DecimalFormat("0.00").format(cutLinPerc) + "%).");
			this.newUsers = tempUsers;
		} catch (final Exception e)
		{
			System.out.println("Error while filtering log-file:");
			e.printStackTrace();
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
			System.out.println("Reading server log " + inFile);
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
					if (logLine.isValid())
					{
						final LogObject lo = new LogObject();
						String name;

						name = logLine.getId();

						long id = -1;
						// Set user-id
						if (this.id_mapping.get(name) != null)
						{
							id = this.id_mapping.get(name).getId();
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
							// CourseMining co;
							if (this.courseResources.get(lo.getUrl()) != null) {
								lo.setCourse(c.getCourse());
								/*
								 * try{
								 * HibernateProxy prox = (HibernateProxy)c.getCourse();
								 * co = (CourseMining)prox.getHibernateLazyInitializer().getImplementation();
								 * }catch(ClassCastException e)
								 * {
								 * co = c.getCourse();
								 * }
								 * catch(NullPointerException e)
								 * {
								 * co = null;
								 * }
								 * if(c != null)
								 * lo.setCourse(co);
								 */
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

							long resource_id = -1;
							if (this.id_mapping.get(lo.getUrl()) != null)
							{
								resource_id = this.id_mapping.get(lo.getUrl()).getId();
								lo.setId(resource_id);
							}
							if (resource_id == -1)
							{
								resource_id = this.resIdCount + 1;
								this.resIdCount = resource_id;
								this.id_mapping.put(
										lo.getUrl(),
										new IDMappingMining(
												Long.valueOf(this.connector.getPrefix() + "" + resource_id), lo
														.getUrl(), this.connector.getPlatformId()));
								this.new_id_mapping.put(
										lo.getUrl(),
										new IDMappingMining(
												Long.valueOf(this.connector.getPrefix() + "" + resource_id), lo
														.getUrl(), this.connector.getPlatformId()));
								resource_id = Long.valueOf(this.connector.getPrefix() + "" + resource_id);
								lo.setId(resource_id);
							}

							r.setId(resource_id);
							r.setUrl(lo.getUrl());
							// Regex used to prevent the inclusion of assignment-pages
							if (newRes && !r.getUrl().matches("[0-9a-z]{32}[-]{1}[0-9]++"))
							{
								if (lo.getUrl().endsWith("/index.html"))
								{
									final int inPos = lo.getUrl().substring(0, lo.getUrl().lastIndexOf("/") - 1)
											.lastIndexOf("/");
									String urlCut = lo.getUrl().substring(0, inPos);
									urlCut = urlCut.substring(urlCut.lastIndexOf("/") + 1, urlCut.length());
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
							h = h.substring(0, h.indexOf("."));
							String f = "";
							if (h.length() > 0) {
								f = Character.toUpperCase(h.charAt(0)) + "";
							}
							if (h.length() > 0) {
								h = f + h.substring(1);
							} else {
								System.out.println("URL doesn't match pattern: " + lo.getUrl());
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

						// logList.add(lo);
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
						System.out.println("Line doesn't match pattern.");
					} else {
						System.out.println("Line's timestamp is to old.");
					}
				}
				if (filterLog) {
					this.filterServerLogFile();
				}
			}

			finally
			{
				System.out.println("Read " + count + " lines.");
				input.close();
			}
		} catch (final Exception ex)
		{
			ex.printStackTrace();
		}

	}

	/**
	 * Writes the data to the database.
	 */
	public void save(Session session)
	{
		final List<Collection<?>> l = new ArrayList<Collection<?>>();
		final ArrayList<ResourceLogMining> resourceLogMining = new ArrayList<ResourceLogMining>();
		final Collection<UserMining> it = this.newUsers.values();
		final Collection<IDMappingMining> idmap = this.new_id_mapping.values();
		System.out.println("Found " + it.size() + " users.");
		l.add(it);
		l.add(idmap);

		for (final ArrayList<LogObject> loadedItem : this.userHistories.values())
		{
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
				rl.setId(this.resLogId + 1);
				this.resLogId++;

				resourceLogMining.add(rl);
			}
		}
		Collections.sort(resourceLogMining);
		l.add(this.newResources.values());
		l.add(resourceLogMining);
		System.out.println("");
		if (session.isOpen()) {
			this.dbHandler.saveCollectionToDB(session, l);
		} else
		{
			session = this.dbHandler.getMiningSession();
			this.dbHandler.saveCollectionToDB(session, l);
		}
		System.out.println("");
	}

}
