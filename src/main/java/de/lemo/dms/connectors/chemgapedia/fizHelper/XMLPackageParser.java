/**
 * File ./main/java/de/lemo/dms/connectors/chemgapedia/fizHelper/XMLPackageParser.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.chemgapedia.fizHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.IDMappingMining;
import de.lemo.dms.db.miningDBclass.LevelAssociationMining;
import de.lemo.dms.db.miningDBclass.LevelCourseMining;
import de.lemo.dms.db.miningDBclass.LevelMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;

/**
 * The Class XMLPackageParser. Created for the processing of Chemgapedia's VLU-files
 */
public class XMLPackageParser {

	/** The level objects. */
	private final Map<String, LevelMining> levelObj = new HashMap<String, LevelMining>();

	/** The level association objects . */
	private final Map<Long, LevelAssociationMining> levelAssociations = new HashMap<Long, LevelAssociationMining>();

	/** The level course objects. */
	private final Map<Long, LevelCourseMining> levelCourses = new HashMap<Long, LevelCourseMining>();

	/** The largest level id of previous runs. */
	private Long levId = 0L;

	/** The largest level association id of previous runs. */
	private Long levAscId = 0L;

	/** The largest level course id of previous runs. */
	private Long levCouId = 0L;

	/** The list of course objects. */
	private final Map<String, CourseMining> courseObj = new HashMap<String, CourseMining>();

	/** The course resources objects. */
	private final Map<Long, CourseResourceMining> courseResources = new HashMap<Long, CourseResourceMining>();

	/** The list of resource objects. */
	private final Map<String, ResourceMining> resourceObj = new HashMap<String, ResourceMining>();

	/** The list of file names */
	private final List<String> fileNames = new ArrayList<String>();

	/** The map containing the IDMapping-objects. */
	private final Map<String, IDMappingMining> idmapping;

	/** The largest course id of previous runs. */
	private Long couId = 0L;
	/** The resource level id of previous runs. */
	private Long resId = 0L;
	/** The largest course resource id of previous runs. */
	private Long couResId = 0L;
	private final IConnector connector;

	private final IDBHandler dbHandler;
	
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Constructor. Creates an object of XMLPackageParser.
	 * 
	 * @param platform
	 *            Name of the Chemgapedia-platform
	 */
	@SuppressWarnings("unchecked")
	public XMLPackageParser(final IConnector connector, final List<Long> courses)
	{
		this.connector = connector;
		
	
		final long platformId = connector.getPlatformId();

		this.dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = this.dbHandler.getMiningSession();

		final List<IDMappingMining> ids = (List<IDMappingMining>) this.dbHandler.performQuery(session, EQueryType.HQL,
				"from IDMappingMining x where x.platform=" + platformId + " order by x.id asc");

		this.idmapping = new HashMap<String, IDMappingMining>();
		for (int i = 0; i < ids.size(); i++) {
			this.idmapping.put(ids.get(i).getHash(), ids.get(i));
		}
		logger.info("Loaded " + this.idmapping.size() + " IDMappingMining objects from the mining database.");

		final List<LevelMining> levs = (List<LevelMining>) this.dbHandler.performQuery(session, EQueryType.HQL,
				"FROM LevelMining x where x.platform=" + platformId + " order by x.id asc");
		if (levs.size() > 0) {
			this.levId = levs.get(levs.size() - 1).getId();
		}
		for (int i = 0; i < levs.size(); i++) {
			this.levelObj.put(levs.get(i).getTitle(), levs.get(i));
		}
		logger.info("Loaded " + this.levelObj.size() + " LevelMining objects from the mining database.");

		final List<CourseMining> cous = (List<CourseMining>) this.dbHandler.performQuery(session, EQueryType.HQL,
				"FROM CourseMining x where x.platform=" + platformId + " order by x.id asc");
		if (cous.size() > 0) {
			this.couId = cous.get(cous.size() - 1).getId();
		}
		for (int i = 0; i < cous.size(); i++) {
			this.courseObj.put(cous.get(i).getTitle(), cous.get(i));
		}
		logger.info("Loaded " + this.courseObj.size() + " CourseMining objects from the mining database.");

		final List<ResourceMining> ress = (List<ResourceMining>) this.dbHandler.performQuery(session, EQueryType.HQL,
				"FROM ResourceMining x where x.platform=" + platformId + " order by x.id asc");
		if (ress.size() > 0) {
			this.resId = ress.get(ress.size() - 1).getId();
		}
		for (int i = 0; i < ress.size(); i++) {
			this.resourceObj.put(ress.get(i).getUrl(), ress.get(i));
		}
		logger.info("Loaded " + this.resourceObj.size() + " ResourceMining objects from the mining database.");

		final List<LevelAssociationMining> levAsc = (List<LevelAssociationMining>) this.dbHandler.performQuery(session,
				EQueryType.HQL, "FROM LevelAssociationMining x where x.platform=" + platformId + " order by x.id asc");
		if (levAsc.size() > 0) {
			this.levAscId = levAsc.get(levAsc.size() - 1).getId();
		}
		for (int i = 0; i < levAsc.size(); i++) {
			this.levelAssociations.put(levAsc.get(i).getLower().getId(), levAsc.get(i));
		}
		logger.info("Loaded " + this.levelAssociations.size()
				+ " LevelAssociationMining objects from the mining database.");

		final List<LevelCourseMining> levCou = (List<LevelCourseMining>) this.dbHandler.performQuery(session,
				EQueryType.HQL, "FROM LevelCourseMining x where x.platform=" + platformId + " order by x.id asc");
		if (levCou.size() > 0) {
			this.levCouId = levCou.get(levCou.size() - 1).getId();
		}
		for (int i = 0; i < levCou.size(); i++) {
			this.levelCourses.put(levCou.get(i).getCourse().getId(), levCou.get(i));
		}
		logger.info("Loaded " + this.levelCourses.size()
				+ " LevelCourseMining objects from the mining database.");

		final List<CourseResourceMining> couRes = (List<CourseResourceMining>) this.dbHandler.performQuery(session,
				EQueryType.HQL, "FROM CourseResourceMining x where x.platform=" + platformId + " order by x.id asc");
		if (couRes.size() > 0) {
			this.couResId = couRes.get(couRes.size() - 1).getId();
		}
		for (int i = 0; i < couRes.size(); i++) {
			this.courseResources.put(couRes.get(i).getResource().getId(), couRes.get(i));
		}
		logger.info("Loaded " + this.courseResources.size()
				+ " CourseResourceMining objects from the mining database.");

		this.dbHandler.closeSession(session);
	}

	/**
	 * Opens a vlu - file, creates objects of the type "Department","Degree","Course","Resource",
	 * "DepartmentDegree", "DegreeCourse" and "CourseResource" containing the given information and saves the objects to
	 * the global lists.
	 * 
	 * @param filename
	 *            Absolute path of the file containing the data.
	 */
	private void readVLU(final String filename)
	{
		final ResourceMining resource = new ResourceMining();
		try {
			// ---- Parse XML file ----
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new File(filename));

			// ---- Get list of nodes to given element tag name ----
			NamedNodeMap t = null;

			final NodeList content = document.getElementsByTagName("content");
			// Set resource type
			resource.setType("VLU");

			// set resource title
			if (document.getElementsByTagName("title").getLength() > 0)
			{
				resource.setTitle(document.getElementsByTagName("title").item(0).getTextContent());
			}

			// Set resource difficulty
			if (document.getElementsByTagName("audience").getLength() > 0)
			{
				resource.setDifficulty(((Element) document.getElementsByTagName("audience").item(0))
						.getAttribute("level"));
			}

			try {
				if (document.getElementsByTagName("time").getLength() > 0)
				{
					resource.setProcessingTime(Long.valueOf(((Element) document.getElementsByTagName("time").item(0))
							.getAttribute("value")));
				}
			} catch (final NumberFormatException e)
			{
				resource.setProcessingTime(0L);
			}

			String level1 = "";
			String level2 = "";
			String level3 = "";

			if (document.getElementsByTagName("subject").getLength() > 0) {
				level1 = ((Element) document.getElementsByTagName("subject").item(0)).getAttribute("name");
			}

			if (document.getElementsByTagName("subject").getLength() > 0) {
				level2 = ((Element) document.getElementsByTagName("subject").item(0)).getAttribute("area");
			}

			if (document.getElementsByTagName("subject").getLength() > 0) {
				level3 = ((Element) document.getElementsByTagName("subject").item(0)).getAttribute("specialism");
			}

			LevelMining lev1 = new LevelMining();
			LevelMining lev2 = new LevelMining();
			LevelMining lev3 = new LevelMining();
			CourseMining course = new CourseMining();

			final Long platformId = this.connector.getPlatformId();
			final Long platformPrefix = this.connector.getPrefix();

			if (this.courseObj.get(resource.getTitle()) == null)
			{
				course.setTitle(resource.getTitle());
				course.setId(Long.valueOf(platformPrefix + "" + (this.couId + 1)));
				course.setPlatform(platformId);
				this.couId++;

				this.courseObj.put(course.getTitle(), course);
			} else {
				course = this.courseObj.get(resource.getTitle());
			}
			
			if (this.levelObj.get(level1) == null )
			{
				lev1.setTitle(level1);
				lev1.setId(Long.valueOf(platformPrefix + "" + (this.levId + 1)));
				lev1.setPlatform(platformId);
				lev1.setDepth(1);
				this.levId++;
				this.levelObj.put(lev1.getTitle(), lev1);
			} else {
				lev1 = this.levelObj.get(level1);
			}
			if (this.levelObj.get(level2) == null)
			{

				lev2.setTitle(level2);
				lev2.setId(Long.valueOf(platformPrefix + "" + (this.levId + 1)));
				lev2.setPlatform(platformId);
				lev2.setDepth(2);
				this.levId++;
				this.levelObj.put(lev2.getTitle(), lev2);
			} else {
				lev2 = this.levelObj.get(level2);
			}
			if (this.levelObj.get(level3) == null)
			{

				lev3.setTitle(level3);
				lev3.setId(Long.valueOf(platformPrefix + "" + (this.levId + 1)));
				lev3.setPlatform(platformId);
				lev3.setDepth(3);
				this.levId++;
				
				this.levelObj.put(lev3.getTitle(), lev3);
			} else {
				lev3 = this.levelObj.get(level3);
			}

			

			// Set URL
			final NodeList root = document.getElementsByTagName("vlunode");
			for (int i = 0; i < root.getLength(); i++)
			{
				t = root.item(i).getAttributes();
				for (int j = 0; j < t.getLength(); j++)
				{
					if (t.item(j).getNodeName().equals("xlink:href"))
					{
						resource.setUrl("http://www.chemgapedia.de/vsengine/vlu" + t.item(j).getNodeValue() + ".html");
						break;
					}
				}
			}

			if (!resource.getUrl().contains("/0/"))
			{
				resource.setPosition(0);
				this.resourceObj.get(resource.getUrl());
				long rid = -1;
				if (this.idmapping.get(resource.getUrl()) != null)
				{
					rid = this.idmapping.get(resource.getUrl()).getId();
					resource.setId(rid);
				}
				if (rid == -1)
				{
					rid = this.resId + 1;
					this.resId = rid;
					rid = Long.valueOf(this.connector.getPrefix() + "" + rid);
					this.idmapping.put(resource.getUrl(), new IDMappingMining(rid, resource.getUrl(), platformId));
					resource.setId(rid);
				}

				resource.setPlatform(this.connector.getPlatformId());
				this.resourceObj.put(resource.getUrl(), resource);
				this.fileNames.add(filename);
				// Save department - degree relation locally
				if (this.levelAssociations.get(lev2.getId()) == null)
				{
					final LevelAssociationMining ddm = new LevelAssociationMining();
					ddm.setLower(lev2);
					ddm.setUpper(lev1);
					ddm.setId(Long.valueOf(this.connector.getPrefix() + "" + (this.levAscId + 1)));
					ddm.setPlatform(this.connector.getPlatformId());
					this.levAscId++;
					this.levelAssociations.put(lev2.getId(), ddm);
				}
				if (this.levelAssociations.get(lev3.getId()) == null)
				{
					final LevelAssociationMining ddm = new LevelAssociationMining();
					ddm.setLower(lev3);
					ddm.setUpper(lev2);
					ddm.setId(Long.valueOf(platformPrefix + "" + (this.levAscId + 1)));
					ddm.setPlatform(platformId);
					this.levAscId++;
					this.levelAssociations.put(lev3.getId(), ddm);
				}

				// Find out whether there already is a association between the course and a hierarchy layer. If not
				// create one.
				if (this.levelCourses.get(course.getId()) == null)
				{
					final LevelCourseMining dcm = new LevelCourseMining();
					dcm.setLevel(lev3);
					dcm.setCourse(course);
					dcm.setId(Long.valueOf(platformPrefix + "" + (this.levCouId + 1)));
					dcm.setPlatform(platformId);
					this.levCouId++;
					this.levelCourses.put(course.getId(), dcm);
				}
				// Find out whether there already is a association between this resource and a course. If not create
				// one.
				if (this.courseResources.get(resource.getId()) == null)
				{
					final CourseResourceMining crm = new CourseResourceMining();
					crm.setResource(resource);
					crm.setCourse(course);
					crm.setId(Long.valueOf(this.connector.getPrefix() + "" + (this.couResId + 1)));
					this.couResId++;
					crm.setPlatform(this.connector.getPlatformId());
					this.courseResources.put(resource.getId(), crm);
				}

				int pos = 1;
				// Create Resource-objects for the all the pages included in the vlu
				final ArrayList<ResourceMining> tempRes = new ArrayList<ResourceMining>();
				if (content.getLength() > 0)
				{
					for (int i = 0; i < content.item(0).getChildNodes().getLength(); i++)
					{
						if (content.item(0).getChildNodes().item(i).hasAttributes())
						{
							final ResourceMining r1 = new ResourceMining();
							t = content.item(0).getChildNodes().item(i).getAttributes();
							for (int j = 0; j < t.getLength(); j++)
							{
								final Node node = t.item(j);

								if (node.getTextContent() != null)
								{

									if (node.getNodeName().equals("xlink:href"))
									{

										r1.setDifficulty(resource.getDifficulty());

										r1.setType("Page");
										r1.setUrl(resource.getUrl().substring(0, resource.getUrl().length() - 5)
												+ "/Page" + node.getTextContent() + ".html");
									}

									if (node.getNodeName().equals("xlink:title"))
									{
										r1.setTitle(node.getTextContent());
									}

								}
							}
							long resourceid = -1;
							if (this.idmapping.get(r1.getUrl()) != null)
							{
								resourceid = this.idmapping.get(r1.getUrl()).getId();
								r1.setId(resourceid);
							}
							if (resourceid == -1)
							{
								resourceid = this.resId + 1;
								this.resId = resourceid;
								resourceid = Long.valueOf(this.connector.getPrefix() + "" + resourceid);
								this.idmapping.put(r1.getUrl(), new IDMappingMining(resourceid, r1.getUrl(),
											platformId));

								r1.setId(resourceid);
							}

							r1.setPosition(pos);
							r1.setPlatform(this.connector.getPlatformId());
							tempRes.add(r1);
							this.resourceObj.put(r1.getUrl(), r1);
							this.fileNames.add(filename + "*");
							if (this.courseResources.get(r1.getId()) == null)
							{
								final CourseResourceMining crm = new CourseResourceMining();
								crm.setResource(r1);
								crm.setCourse(course);
								crm.setId(Long.valueOf(this.connector.getPrefix() + "" + (this.couResId + 1)));
								this.couResId++;
								crm.setPlatform(this.connector.getPlatformId());
								this.courseResources.put(r1.getId(), crm);
							}
							pos++;

						}
					}
					long posT = 0;
					try {
						posT = (resource.getProcessingTime() / pos) - 1;

					} catch (final NumberFormatException e)
					{
					}

					for (int i = 0; i < tempRes.size(); i++)
					{

						tempRes.get(i).setProcessingTime(posT);
					}
					// Add the unlisted summary for each vlu
					final ResourceMining r1 = new ResourceMining();
					r1.setDifficulty(resource.getDifficulty());
					r1.setTitle(resource.getTitle());
					r1.setType("Summary");
					r1.setProcessingTime(resource.getProcessingTime() / content.getLength());
					r1.setUrl(resource.getUrl().substring(0, resource.getUrl().length() - 5) + "/Page/summary.html");

					if (this.resourceObj.get(r1.getUrl()) == null)
					{
						r1.setId(Long.valueOf(this.connector.getPrefix() + "" + (this.resId + 1)));
						this.resId++;
					}

					r1.setPosition(pos);

					if (this.resourceObj.get(r1.getUrl()) == null)
					{
						r1.setPlatform(this.connector.getPlatformId());
						this.resourceObj.put(r1.getUrl(), r1);
						this.idmapping.put(r1.getUrl(),
								new IDMappingMining(r1.getId(), r1.getUrl(), this.connector.getPlatformId()));
						this.fileNames.add(filename + "*");
						final CourseResourceMining crm = new CourseResourceMining();
						crm.setResource(r1);
						crm.setCourse(course);
						crm.setId(Long.valueOf(this.connector.getPrefix() + "" + (this.couResId + 1)));
						this.couResId++;
						crm.setPlatform(this.connector.getPlatformId());
						this.courseResources.put(r1.getId(), crm);
					}
				}
			}

			// ---- Error handling ----
		} catch (final SAXParseException spe) {
			logger.info("\n** Parsing error, line " + spe.getLineNumber() + ", uri " + spe.getSystemId());
		} catch (final SAXException sxe) {
			logger.info("\n** SAX error!");
		} catch (final ParserConfigurationException pce) {
			logger.info("ParserConfigurationException: " + pce.getMessage());
		} catch (final IOException ioe) {
			logger.info("IOException: " + ioe.getMessage());
		}
	}

	/**
	 * Returns a list of all files with the specified suffix in the directory and its subdirectories.
	 * 
	 * @param directory
	 *            Directory containing the files and subdirectories.
	 * @param suffix
	 *            File extension (returns all files if the string is empty)
	 * @return An ArrayList containing all file names (absolute paths) contained in the given directory.
	 */
	private List<String> getFilenames(final String directory, final String suffix)
	{
		final ArrayList<String> all = new ArrayList<String>();
		try {
			File f = new File(directory);
			logger.info("Gathering filenames from path: " + f.getAbsolutePath());
			final ArrayList<String> dirs = new ArrayList<String>();
			for (int i = 0; i < f.list().length; i++) {
				dirs.add(i, directory + "\\" + f.list()[i]);
			}
			while (!dirs.isEmpty())
			{
				f = new File(dirs.get(0));
				if (f.isDirectory())
				{
					for (int i = 0; i < f.list().length; i++) {
						dirs.add(f + "\\" + f.list()[i]);
					}
				}
				if (f.isFile() && f.toString().endsWith(suffix))
				{
					all.add(f.toString());
				}
				dirs.remove(0);
			}
		} catch (final Exception e)
		{
			logger.info("Exception @ getFilenames" + e.getMessage());
		}
		return all;
	}

	/**
	 * Saves all objects of the XMLPackageParser into the Database.
	 * Affected tables: Resource, DepartmentDegree, DegreeCourse, CourseResource
	 * 
	 * @return Largest id in the id-mapping-table
	 */
	public void saveAllToDB()
	{
		final List<Collection<?>> li = new ArrayList<Collection<?>>();
		li.add(this.levelObj.values());
		li.add(this.courseObj.values());
		li.add(this.resourceObj.values());
		li.add(this.levelAssociations.values());
		li.add(this.levelCourses.values());
		li.add(this.courseResources.values());
		li.add(this.idmapping.values());

		final Session session = this.dbHandler.getMiningSession();
		this.dbHandler.saveCollectionToDB(session, li);
	}

	/**
	 * Reads all VLUs contained in the specified directory.
	 * 
	 * @param directory
	 *            the directory
	 */
	public void readAllVlus(final String directory)
	{
		try {
			final Clock c = new Clock();
			this.logger.info("Gathering filenames in directory...");
			final List<String> all = this.getFilenames(directory, ".vlu");
			Collections.sort(all);
			this.logger.info("Found " + all.size() + " files in directory." + c.getAndReset());
			this.logger.info("Reading all vlu-files in directory...");
			for (int i = 0; i < all.size(); i++)
			{
				this.readVLU(all.get(i));
				if ((i > 0) && ((i % (all.size() / 10)) == 0)) {
					this.logger.info("Read " + (i / (all.size() / 10)) + "0 % in " + c.get());
				}
			}
		} catch (final Exception e)
		{
			this.logger.error("Exception @ readAllVLUs ", e);
		}
	}

	public void clearMaps()
	{
		this.courseObj.clear();
		this.courseResources.clear();
		this.fileNames.clear();
		this.idmapping.clear();
		this.levelAssociations.clear();
		this.levelCourses.clear();
		this.levelObj.clear();
		this.resourceObj.clear();
	}
}
