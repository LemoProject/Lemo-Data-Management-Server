/**
 * File ./src/main/java/de/lemo/dms/connectors/ESourcePlatform.java Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013 Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
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

package de.lemo.dms.connectors;

import java.util.List;

import org.apache.log4j.Logger;

//import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
//import de.lemo.dms.connectors.clix2010.ConnectorClix;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;

/**
 * Supported LMS platforms. Enum constant names may be used case insensitive in the {@link ServerConfiguration}.
 */
public enum ESourcePlatform {

    //Moodle_1_9,
    //Moodle_1_9_Numeric,
    // Moodle_2_1,
    // Moodle_2_2,
    Moodle_2_3,
    Moodle_2_4,
    Moodle_2_5,
    Moodle_2_7,
    Mooc,
    //Clix_2010,
    //Chemgaroo,
    Dummy, ;

    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * Create a new connector for this platform.
     * 
     * @param id
     *            connector ID
     * @param name
     *            human readable platform name
     * @param config
     *            connector configuration
     * @param filter
     *            list of course IDs to be loaded - loads all courses if empty
     * @return
     */
    public IConnector newConnector(final Long id, final String name, final DBConfigObject config, List<Long> filter, List<String> logins) {
        AbstractConnector connector;
        switch(this) {

        /*case Chemgaroo:
            connector = new ConnectorChemgapedia(config);
            break;

        case Clix_2010:
            connector = new ConnectorClix(config);
            break;*/

        case Dummy:
            connector = new ConnectorDummy();
            break;
            
        case Moodle_2_7:
            connector = new de.lemo.dms.connectors.moodle_2_7.ConnectorMoodle(config);
            break;

       /* case Moodle_1_9:
            connector = new de.lemo.dms.connectors.moodle_1_9.ConnectorMoodle(config);
            break;*/

        // case Moodle_2_1:
        // case Moodle_2_2:
        case Moodle_2_3:
        case Moodle_2_4:
        case Moodle_2_5:
            connector = new de.lemo.dms.connectors.moodle_2_3.ConnectorMoodle(config);
            break;
            
        case Mooc:
        	connector = new de.lemo.dms.connectors.mooc.ConnectorMooc(config);
            break;

/*        case Moodle_1_9_Numeric:
            connector = new de.lemo.dms.connectors.moodle_1_9.ConnectorMoodle(config);
            break;*/

        default:
            throw new RuntimeException("No Connector implementation found for '" + this.name() + "'.");
        }

        connector.setPlatformId(id);
        connector.setPlatformType(this);
        connector.setName(name);
        connector.setCourseIdFilter(filter);
        connector.setCourseLoginFilter(logins);

        logger.debug("Created connector " + connector);
        logger.debug("Course filters: " + filter);
        logger.debug("Database configuration: " + config.getProperties());

        return connector;

    }
}
