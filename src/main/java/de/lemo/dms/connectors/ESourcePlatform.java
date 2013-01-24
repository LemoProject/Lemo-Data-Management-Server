/**
 * File ./main/java/de/lemo/dms/connectors/ESourcePlatform.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors;

import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.clix2010.ConnectorClix;
import de.lemo.dms.db.DBConfigObject;

public enum ESourcePlatform {

	Moodle_1_9,
	Moodle_1_9_Numeric,
	Moodle_2_3,
	Clix_2010,
	Chemgaroo,
	Dummy, ;

	public IConnector newConnector(final Long id, final String name, final DBConfigObject config) {
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
				throw new RuntimeException("No Connector implementation");
		}

		connector.setPlatformId(id);
		connector.setPlatformType(this);
		connector.setName(name);

		return connector;

	}
}
