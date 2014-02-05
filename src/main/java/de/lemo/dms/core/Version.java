/**
 * File ./src/main/java/de/lemo/dms/core/Version.java Lemo-Data-Management-Server for learning analytics. Copyright (C)
 * 2013 Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 **/

/**
 * File ./main/java/de/lemo/dms/core/Version.java Date 2013-01-24 Project Lemo Learning Analytics
 */

package de.lemo.dms.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.Config;

/**
 * Provides version numbers of the server and the mining database.
 * 
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 */
public class Version {

    private static final String VERSION_FILE_PATH = "/lemo.properties";
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * Reads the server's version number property.
     * 
     * @return the DMS version
     */
    public String getServerVersion() {
        String version = "unknown";
        InputStream stream = Version.class.getResourceAsStream(VERSION_FILE_PATH);
        Properties prop = new Properties();
        try {
            prop.load(stream);
            version = prop.getProperty("lemo.version");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * Loads the database scheme's version number from the database.
     * 
     * @return the database version
     */
    public String getDBVersion() {
        String version = "unknown";
        try {
            IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
            final Session session = dbHandler.getMiningSession();
            final Criteria criteria = session.createCriteria(Config.class, "config");
            criteria.setMaxResults(1);
            final Config prop = (Config) criteria.list().get(0);
            version = prop.getDatabaseModel().toString();

        } catch (final Exception ex) {
            this.logger.error("Unable to read database version.", ex);
        }
        return version;
    }
}
