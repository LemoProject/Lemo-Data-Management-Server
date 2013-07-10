/**
 * File ./src/main/java/de/lemo/dms/core/Version.java
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
 * File ./main/java/de/lemo/dms/core/Version.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.core;

import java.io.File;
import java.io.FileReader;
import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.hibernate.Criteria;
import org.hibernate.Session;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.ConfigMining;

/**
 * read the version numbers from server and db
 * 
 * @author Boris Wenzlaff
 */
public class Version {

	private static final String POM = "pom.xml";
	private final Logger logger = Logger.getLogger(this.getClass());
	//private 
	
	/**
	 * read the version number from the pom.xml
	 * 
	 * @return version number from dms
	 */
	public String getServerVersion() {
		String version = "unknown";
		final File pomfile = new File(Version.POM);
		this.logger.info("looking for Pom.xml at " + pomfile.getAbsolutePath());
		Model model = null;
		FileReader reader = null;
		final MavenXpp3Reader mavenreader = new MavenXpp3Reader();
		try {
			reader = new FileReader(pomfile);
			model = mavenreader.read(reader);
			model.setPomFile(pomfile);
			version = model.getVersion();
		} catch (final Exception ex) {
			this.logger.warn("Can't read from pom.xml\n" + ex.getMessage());
		}
		return version;
	}

	/**
	 * read the version number from the db
	 * 
	 * @return version number db
	 */
	public String getDBVersion() {
		String version = "unknown";
		try {
			IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
			final Session session = dbHandler.getMiningSession();
			final Criteria criteria = session.createCriteria(ConfigMining.class, "config");
			criteria.setMaxResults(1);
			final ConfigMining prop = (ConfigMining) criteria.list().get(0);
			version = prop.getDatabaseModel().toString();

		} catch (final Exception ex) {
			this.logger.warn("cant read version from db\n" + ex.getMessage());
		}
		return version;
	}
}
