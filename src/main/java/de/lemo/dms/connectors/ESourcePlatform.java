/**
 * File ./src/main/java/de/lemo/dms/connectors/ESourcePlatform.java
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
 * File ./main/java/de/lemo/dms/connectors/ESourcePlatform.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.util.List;
import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.clix2010.ConnectorClix;
import de.lemo.dms.db.DBConfigObject;

/**
 * enum for the avsailable platforms
 */
public enum ESourcePlatform {

	Moodle_1_9,
	Moodle_1_9_Numeric,
	Moodle_2_3,
	Clix_2010,
	Chemgaroo,
	Dummy, ;

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
	public IConnector newConnector(final Long id, final String name, final DBConfigObject config, List<Long> filter) {
		AbstractConnector connector;
		switch (this) {

			case Chemgaroo:
				connector = new ConnectorChemgapedia(config);
				break;

			case Clix_2010:
				connector = new ConnectorClix(config);
				break;

			case Dummy:
				connector = new ConnectorDummy();
				break;

			case Moodle_1_9:
				connector = new de.lemo.dms.connectors.moodle.ConnectorMoodle(config);
				break;

			case Moodle_2_3:
				connector = new de.lemo.dms.connectors.moodle_2_3.ConnectorMoodle(config);
				break;

			case Moodle_1_9_Numeric:
				connector = new de.lemo.dms.connectors.moodleNumericId.ConnectorMoodle(config);
				break;

			default:
				throw new RuntimeException("No Connector implementation found for '" + this.name() + "'.");
		}

		connector.setPlatformId(id);
		connector.setPlatformType(this);
		connector.setName(name);
		connector.setCourseIdFilter(filter);

		return connector;

	}
}
