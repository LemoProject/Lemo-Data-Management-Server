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

    public IConnector newConnector(DBConfigObject config) {
        switch(this) {
        case Chemgaroo:
            return new ConnectorChemgapedia(config);

        case Clix:
            return new ConnectorClix(config);

        case Dummy:
            return new ConnectorDummy();

        case Moodle_1_9:
            return new de.lemo.dms.connectors.moodle.ConnectorMoodle(config);

        case Moodle_1_9_Numeric:
            return new de.lemo.dms.connectors.moodleNumericId.ConnectorMoodle(config);
            
        default:
            throw new RuntimeException("No Connector implementation");
        }

    }
}
