package de.lemo.dms.core;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Order;

import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ConfigMining;
import de.lemo.dms.db.miningDBclass.RoleMining;
import de.lemo.dms.processing.resulttype.ResultListRoleObject;
import de.lemo.dms.processing.resulttype.RoleObject;

/**
 * read the version numbers from server and db
 * @author Boris Wenzlaff
 *
 */
public class Version {
	private final String pom = "pom.xml";
	private Logger logger = Logger.getLogger(this.getClass());
    private final IServerConfiguration config = ServerConfigurationHardCoded.getInstance();
    IDBHandler dbHandler = config.getDBHandler();

	/**
	 * read the version number from the pom.xml
	 * @return version number from dms
	 */
	public String getServerVersion() {
		String version = "unknown";
		File pomfile = new File(pom);
		Model model = null;
		FileReader reader = null;
		MavenXpp3Reader mavenreader = new MavenXpp3Reader();
		try {
		    reader = new FileReader(pomfile);
		    model = mavenreader.read(reader);
		    model.setPomFile(pomfile);
		    version = model.getVersion();
		}catch(Exception ex){
		
		}
		return version;
	}
	
	/**
	 * read the version number from the db
	 * @return version number db
	 */
	public String getDBVersion() {
		String version = "unknown";
		try {
			Session session = dbHandler.getMiningSession();

			/*@SuppressWarnings("unchecked")
			ArrayList<String> dbversion = (ArrayList<String>) dbHandler
					.performQuery(session, EQueryType.HQL,
							"SELECT platform FROM config ORDER BY lastmodified DESC LIMIT 1");
			version = dbversion.get(0);
			dbHandler.closeSession(session);*/
			Criteria criteria = session.createCriteria(ConfigMining.class, "config");
			criteria.setMaxResults(1);
			criteria.addOrder(org.hibernate.criterion.Order.desc("lastmodified"));
			ConfigMining prop = (ConfigMining) criteria.list().get(0);
			version = prop.getPlatform().toString();
			//SELECT platform FROM config ORDER BY lastmodified DESC LIMIT 1
			//ConfigMining prop = (ConfigMining) criteria.list().get(0);
			//prop.getPlatf
			
		} catch (Exception ex) {
			logger.warn("cant read version from db\n"+ex.getMessage());
		}
		return version;
	}
}
