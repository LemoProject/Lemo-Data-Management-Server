/**
 * File ./main/java/de/lemo/dms/connectors/ESourcePlatform.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
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
