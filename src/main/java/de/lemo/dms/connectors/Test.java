package de.lemo.dms.connectors;

import java.util.ArrayList;
import java.util.List;

import de.lemo.dms.core.config.ServerConfiguration;

public class Test {
	
	public static void main(String[] args)
	{
		ServerConfiguration.getInstance().loadConfig("/lemo");
		ConnectorManager cm = ConnectorManager.getInstance();
		List<String> properties = new ArrayList<String>();
		properties.add("hibernate.connection.url");
		properties.add("jdbc:mysql://localhost/moodle23?rewriteBatchedStatements=true");
		properties.add("hibernate.connection.username");
		properties.add("datamining");
		properties.add("hibernate.connection.password");
		properties.add("LabDat1#");
		properties.add("hibernate.dialect");
		properties.add("org.hibernate.dialect.MySQLDialect");
		properties.add("hibernate.connection.driver_class");
		properties.add("com.mysql.jdbc.Driver");
		properties.add("hibernate.default_schema");
		properties.add("");
		
		
		
		List<Long> courseIdFilter = new ArrayList<Long>();
		courseIdFilter.add(20L);
		
		
		
		IConnector connector = cm.createNewConnector(10L, 23L, "Testi", "Moodle_2_3", properties, courseIdFilter);
		
		cm.startUpdateData(connector);
	}

}
