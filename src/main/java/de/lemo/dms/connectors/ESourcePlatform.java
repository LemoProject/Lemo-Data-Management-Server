package de.lemo.dms.connectors;

import de.lemo.dms.connectors.chemgapedia.ConnectorChemgapedia;
import de.lemo.dms.connectors.clix2010.ConnectorClix;

public enum ESourcePlatform {

    Moodle_1_9(de.lemo.dms.connectors.moodle.ConnectorMoodle.class),
    Moodle_1_9_Numeric(de.lemo.dms.connectors.moodleNumericId.ConnectorMoodle.class),
    Clix(ConnectorClix.class),
    Chemgaroo(ConnectorChemgapedia.class),
    Dummy(ConnectorDummy.class), ;

    private Class<? extends IConnector> connectorType;

    private ESourcePlatform(Class<? extends IConnector> connectorType) {
        this.connectorType = connectorType;
    }

    public Class<? extends IConnector> getConnectorType() {
        return connectorType;
    }
}
