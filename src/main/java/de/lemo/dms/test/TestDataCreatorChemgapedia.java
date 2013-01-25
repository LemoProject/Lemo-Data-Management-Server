/**
 * File ./main/java/de/lemo/dms/test/TestDataCreatorChemgapedia.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.hibernate.Query;
import org.hibernate.Session;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.LevelAssociationMining;
import de.lemo.dms.db.miningDBclass.LevelCourseMining;
import de.lemo.dms.db.miningDBclass.LevelMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;

public class TestDataCreatorChemgapedia {

	private ArrayList<ResourceLogMining> resourceLogList = new ArrayList<ResourceLogMining>();
	private ArrayList<ResourceMining> resourceList = new ArrayList<ResourceMining>();
	private ArrayList<LevelAssociationMining> departmentDegreeList = new ArrayList<LevelAssociationMining>();
	private ArrayList<LevelCourseMining> degreeCourseList = new ArrayList<LevelCourseMining>();
	private ArrayList<CourseResourceMining> courseResourceList = new ArrayList<CourseResourceMining>();

	private final HashMap<Long, CourseMining> couResMap = new HashMap<Long, CourseMining>();
	private final HashMap<Long, LevelMining> degCouMap = new HashMap<Long, LevelMining>();
	private final HashMap<Long, LevelMining> depDegMap = new HashMap<Long, LevelMining>();

	/**
	 * Extracts Mining-data from the Mining-database
	 */
	@SuppressWarnings("unchecked")
	public void getDataFromDB()
	{
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = dbHandler.getMiningSession();
		session.clear();

		final Query resLogQuery = session.createQuery("from ResourceLogMining x order by x.id asc");
		this.resourceLogList = (ArrayList<ResourceLogMining>) resLogQuery.list();

		final Query resQuery = session.createQuery("from ResourceMining x order by x.id asc");
		this.resourceList = (ArrayList<ResourceMining>) resQuery.list();

		final Query degCouQuery = session.createQuery("from LevelCourseMining x order by x.id asc");
		this.degreeCourseList = (ArrayList<LevelCourseMining>) degCouQuery.list();

		final Query depDegQuery = session.createQuery("from DepartmentDegreeMining x order by x.id asc");
		this.departmentDegreeList = (ArrayList<LevelAssociationMining>) depDegQuery.list();

		final Query couResQuery = session.createQuery("from CourseResourceMining x order by x.id asc");
		this.courseResourceList = (ArrayList<CourseResourceMining>) couResQuery.list();

		for (final CourseResourceMining cr : this.courseResourceList) {
			this.couResMap.put(cr.getResource().getId(), cr.getCourse());
		}
		for (final LevelCourseMining dc : this.degreeCourseList) {
			this.degCouMap.put(dc.getCourse().getId(), dc.getLevel());
		}
		for (final LevelAssociationMining dd : this.departmentDegreeList) {
			this.depDegMap.put(dd.getLower().getId(), dd.getUpper());
		}
	}

	private void createFile(final String path, final String url, final String title, final String specialism,
			final String name, final String area, final String audienceLevel, final String timeValue,
			final ArrayList<String> subResourceTitles, final ArrayList<String> subResourceUrls)
	{
		try {
			final FileWriter out = new FileWriter(path);
			final PrintWriter pout = new PrintWriter(out);

			pout.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			pout.println("<!DOCTYPE vlunode PUBLIC \"-//Vernetztes Studium - Chemie//DTD vlu 1.0//EN\" \"http://www.vs-c.de/schema/vlunode.dtd\">");
			pout.println("<vlunode state=\"1\" version=\"0.1\" xlink:href=\""
					+ url
					+ "\" xml:lang=\"de\" xmlns=\"http://www.vs-c.de/schema/2002\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
			pout.println("<title>" + title + "</title>");
			pout.println("<subject area=\"" + area + "\" name=\"" + name + "\" specialism=\"" + specialism + "\"/>");
			pout.println("<audience level=\"" + audienceLevel + "\"/>");
			pout.println("<time value=\"" + timeValue + "\"/>");
			pout.println("<content>");
			for (int i = 0; i < subResourceTitles.size(); i++) {
				pout.println("<page xlink:title=\"" + subResourceTitles.get(i) + "\" xlink:href=\""
						+ subResourceUrls.get(i) + "\"/>");
			}
			pout.println("</content>");
			pout.println("</vlunode>");

			pout.close();

		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Writes the data, extracted from the Mining-database into a pseudo-server-log-file and pseudo-vlu-packages
	 * 
	 * @param logFile
	 *            path for the pseudo-log-file
	 * @param metaPackage
	 *            path for pseudo-vlu-packages-folder
	 */
	public void writeDataSource(final String logFile, final String metaPackage)
	{
		try
		{
			final FileWriter out = new FileWriter(logFile);
			final PrintWriter pout = new PrintWriter(out);
			final HashMap<Long, String> ips = new HashMap<Long, String>();
			final HashMap<Long, String> refs = new HashMap<Long, String>();

			// Write "server-logs"
			for (final ResourceLogMining r : this.resourceLogList)
			{
				final Random randy = new Random();
				final String time = r.getTimestamp() + "";
				final String url = r.getResource().getUrl().substring(r.getResource().getUrl().indexOf("/vsengine/"));
				String ref = "-";
				if (refs.get(r.getUser().getId()) != null) {
					ref = refs.get(r.getUser().getId());
				}
				String ip;
				String cookie = "-";
				if (ips.get(r.getUser().getId()) == null)
				{

					ip = randy.nextInt(256) + "." + randy.nextInt(256) + "." + randy.nextInt(256) + "."
							+ randy.nextInt(256);
					if ((randy.nextInt(8) != 0) && (ips.get(r.getUser().getId()) == null)) {
						cookie = ip + "/" + r.getTimestamp();
					}
					ips.put(r.getUser().getId(), ip);
				} else {
					ip = ips.get(r.getUser().getId());
				}

				final String line = time + "\t" + "200\t" + ip + "\t" + cookie + "\t" + url + "\t" + ref;
				pout.println(line);
				if (r.getDuration() != -1) {
					refs.put(r.getUser().getId(), r.getResource().getUrl());
				} else {
					refs.put(r.getUser().getId(), "-");
				}
			}
			pout.close();
			// Write meta-data-packages
			ArrayList<String> subResourceTitles = new ArrayList<String>();
			ArrayList<String> subResourceUrls = new ArrayList<String>();

			String title = "";
			String url = "";
			String specialism = "";
			String name = "";
			String area = "";
			String audienceLevel = "";
			String timeValue = "";
			String path = "";

			boolean newResource = false;

			for (final ResourceMining resource : this.resourceList)
			{
				if (resource.getType().equals("VLU") ||
						(resource.getType().equals("Page") &&
						(this.couResMap.get(resource.getId()) != null)))
				{

					if (resource.getType().equals("VLU"))
					{

						if (newResource) {
							this.createFile(path, url, title, specialism, name, area, audienceLevel, timeValue,
									subResourceTitles, subResourceUrls);
						}

						newResource = true;
						subResourceTitles = new ArrayList<String>();
						subResourceUrls = new ArrayList<String>();

						final CourseMining course = this.couResMap.get(resource.getId());
						final LevelMining degree = this.degCouMap.get(course.getId());
						final LevelMining department = this.depDegMap.get(degree.getId());

						final String dir = metaPackage + "\\" + department.getTitle() + "\\" + degree.getTitle() + "\\"
								+ course.getTitle() + "\\";
						final File f = new File(dir);
						f.mkdirs();

						path = metaPackage + "\\" + department.getTitle() + "\\" + degree.getTitle() + "\\"
								+ course.getTitle() + "\\" + resource.getTitle() + ".vlu";

						title = resource.getTitle();
						url = resource.getUrl().substring(resource.getUrl().indexOf("/vsc/"),
								resource.getUrl().lastIndexOf("."));
						specialism = course.getTitle();
						name = department.getTitle();
						area = degree.getTitle();
						audienceLevel = resource.getDifficulty();
						timeValue = resource.getProcessingTime() + "";

					}
					else if (resource.getType().equals("Page"))
					{
						subResourceTitles.add(resource.getTitle());
						subResourceUrls.add(resource.getUrl().substring(resource.getUrl().lastIndexOf("/vsc/"),
								resource.getUrl().lastIndexOf(".")));
					}

				}
			}
			this.createFile(path, url, title, specialism, name, area, audienceLevel, timeValue, subResourceUrls,
					subResourceUrls);

		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

}
