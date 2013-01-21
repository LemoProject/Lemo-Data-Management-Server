package de.lemo.dms.connectors;

import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.clix2010.ConnectorClix;
import de.lemo.dms.db.DBConfigObject;

public enum ESourcePlatform {

    Moodle_1_9,
    Moodle_1_9_Numeric,
    Clix,
    Chemgaroo,
    Dummy, ;

    public IConnector newConnector(Long id, String name, DBConfigObject config) {
        AbstractConnector connector;
        switch(this) {

        case Chemgaroo:
            connector = new ConnectorChemgapedia(config);
            break;

        case Clix:
            connector = new ConnectorClix(config);
            break;

        case Dummy:
            connector = new ConnectorDummy();
            break;

        case Moodle_1_9:
            connector = new de.lemo.dms.connectors.moodle.ConnectorMoodle(config);
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
