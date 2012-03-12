package lemo.dms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import lemo.dms.core.ServerConfigurationHardCoded;
import lemo.dms.processing.Query;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * 
 * @author Leonard Kappe
 */
public class QueryRunner {
	private Logger log = ServerConfigurationHardCoded.getInstance().getLogger();
	private Connection connection;

	public void connect() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", "username"); 
		connectionProps.put("password", "password");
		connection = DriverManager.getConnection("connection",
				connectionProps); // TODO use a real connection string 

		log.info("Database connected");
	}

	public ResultSet run(Query query) throws SQLException {
		log.info("Running query " + query);
		Statement statement = connection.createStatement();
		return statement.executeQuery(query.getSqlQuery());
	}

}
