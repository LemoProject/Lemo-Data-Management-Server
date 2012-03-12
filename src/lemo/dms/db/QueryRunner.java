package lemo.dms.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lemo.dms.core.ServerConfigurationHardCoded;
import lemo.dms.processing.Query;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * 
 * @author Leonard Kappe
 */
public class QueryRunner {

	private Logger log = ServerConfigurationHardCoded.getInstance().getLogger();
	private BasicDataSource dataSource;

	public QueryRunner() {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("jdbc.driver.DBDriver");
		dataSource.setUsername("user");
		dataSource.setPassword("password");
		dataSource.setUrl("uri");
	}

	public void closeDataSource() throws SQLException {
		dataSource.close();
	}

	public ResultSet run(Query query) throws SQLException {
		log.info("Running query " + query);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;

		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery(query.getSqlQuery());
			log.info("Query " + query + " excecuted");

			int j = 0;
			int numcols = result.getMetaData().getColumnCount();
			while (result.next()) {
				for (int i = 1; i <= numcols; i++) {
					log.debug("Row: " + j + ", Column: " + i + ", Result: "
							+ result.getString(i));
				}
				j++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (result != null)
				result.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return result;
	}

}
